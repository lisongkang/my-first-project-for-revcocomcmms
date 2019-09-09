package com.maywide.core.web.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.cons.Constant;
import com.maywide.core.cons.Separator;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.util.PropertyUtil;
import com.maywide.core.util.PubUtil;

public class StaticData {
	private static Logger log = LoggerFactory.getLogger(StaticData.class);
	private static long durationMillis = -1;
	private static final ArrayList<String> transientList = new ArrayList<String>();
	private static StaticData instance = null;
	
	/* 列表的cache */
	static Map<String, List> listCache = Collections
			.synchronizedMap(new HashMap<String, List>());
	/* 列表是否过期的cache */
	static Map<String, Long> listCacheTimestamp = Collections
			.synchronizedMap(new HashMap<String, Long>());
	/* 单个对象的cache */
	static Map<String, Long> dataCacheTimestamp = Collections
			.synchronizedMap(new HashMap<String, Long>());
	/* 单个对象是否过期的cache */
	static Map<String, PrvSysparam> dataCache = Collections
			.synchronizedMap(new HashMap<String, PrvSysparam>());
	
	static {
		// 取参数保存时长以及不需要保存的列表
		try {
			durationMillis = Integer.parseInt(PropertyUtil
					.getValueFromProperites(Constant.APPLICATION,
							"STATIC_DATA_DURATION")) * 1000;
			String[] transientSql = PropertyUtil.getValueFromProperites(
					Constant.APPLICATION, "TRANSIENT_LIST").split(",");
			for (String string : transientSql) {
				transientList.add(string.trim());
			}
		} catch (Exception ex) {
			log.error("Init Params", ex);
		}
	}
	
	public static StaticData getInstance() {
		if (instance == null)
			instance = new StaticData();
		return instance;
	}
	
	public ParamService getSysparamManager() {
		return (ParamService) SpringContextHolder.getBean(ParamService.class);
	}
	
	/**
	 * 取得参数列表（以gcode为条件），得到的是一个以Gcode为条件的列表 多用于为标签提供数据列表
	 * 如果该参数是动态SQL的，则会根据SQL语句，去掉查询条件得到列表
	 *
	 * @param gcode
	 * @return
	 */
	public List getData(String gcode) {
		List list = null;
		try {
			String key = buildKey(gcode, null, null);
			if (isListTimeout(key)) {
				list = getSysparamManager().getData(gcode,null,null,null);
				listCache.put(key, list);
				listCacheTimestamp.put(key, System.currentTimeMillis());
			} else {
				list = (List) listCache.get(key);
			}
		} catch (Exception e) {
			log.error("取得静态参数出错，类型=" + gcode);
			log.error("错误信息：" + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 取得单个参数对象，以主键gcode，mcode为条件，主要给参数翻译用的
	 *
	 * @param gcode
	 *            参数的gcode
	 * @param mcode
	 *            参数的mcode
	 * @return
	 */
	public PrvSysparam getData(String gcode, String mcode) {
		PrvSysparam sysparam = null;
		if(mcode == null || mcode.trim().equals(""))
			return sysparam;
		
		try {
			String key = buildKey(gcode, mcode, null);
			if (isDataTimeout(key)) {
				sysparam = getSysparamManager().getData(gcode, mcode);
				dataCache.put(key, sysparam);
				dataCacheTimestamp.put(key, System.currentTimeMillis());
			} else {
				sysparam = (PrvSysparam) dataCache.get(key);
			}
		} catch (Exception e) {
			log.error("取得静态参数出错，类型=" + gcode + "；参数代码=" + mcode);
			log.error("错误信息：" + e.getMessage());
			System.out.println("取得静态参数出错，类型=" + gcode + "；参数代码=" + mcode);
			e.printStackTrace();
		}
		return sysparam;
	}

	/**
	 * 取得参数列表（以gcode，scode的值为查询条件） 取得下级参数的列表，基本是提供给联动下拉框使用
	 *
	 * @param gcode
	 * @param scode
	 * @return
	 */
	public List getSubData(String gcode, String scode) {
		List list = null;
		try {
			String key = buildKey(gcode, null, scode);
			if (isListTimeout(key)) {
				list = getSysparamManager().getSubData(gcode, new Long(scode));
				listCache.put(key, list);
				listCacheTimestamp.put(key, System.currentTimeMillis());
			} else {
				list = (List) listCache.get(key);
			}
		} catch (Exception e) {
			log.error("根据上级参数取得下级参数列表错误：gcode为：" + gcode + "scode为：" + scode);
			log.error("错误信息：" + e.getMessage());
			System.out.println("根据上级参数取得下级参数列表错误：gcode为：" + gcode + "scode为：" + scode);
			e.printStackTrace();
		}
		return list;
	}

	public List getSupData(Long formatId) {
		List list = null;
		try {
			list = getSysparamManager().getSupData(formatId);
		} catch (Exception e) {
			log.error("根据格式编号取得上级参数列表错误：格式编号为：" + formatId);
			log.error("错误信息：" + e.getMessage());
		}
		return list;
	}

	/**
	 * data的格式： columnIndex:data1,data2~columnIndex:data3
	 *
	 * @param gcode
	 * @param data
	 * @return
	 */
	public List getParamByData(String gcode, String scode, String data) {
		List list = new ArrayList();
		try {
			List<PrvSysparam> paramList = null;
			if(StringUtils.isNotBlank(scode))
				paramList = getSubData(gcode,scode);
			else
				paramList = getData(gcode);
			// 对每个元素进行循环
			for (PrvSysparam sysparam : paramList) {
				String paramData = sysparam.getData(); // 参数中定义好的data
				String[] paramDatas = PubUtil.split(paramData, "~"); // 参数中定义好的data，用~拆分
				String[] columnDatas = PubUtil.split(data, "~"); // 入参用~拆分
				boolean resultFlag = true; // 参数对象是否匹配入参
				// 对入参做循环，去匹配
				for (String columnData : columnDatas) {
					boolean flag = false; // 当前列是否匹配
					String[] columns = PubUtil.split(columnData, ":"); // 对入参的每一列用:拆分，以确定是匹配到哪一列的，并且数据是什么
					int index = Integer.parseInt(columns[0]); // 这里是拿到匹配到哪一列的
					String[] properties = PubUtil.split(columns[1], ","); // 这里是入参要匹配的data
					for (String property : properties) {
						//这里是或逻辑，即，如果参数定义中定义的是"*"（全部）或者入参中用","分隔某一项数据已经包含在参数定义中，当前列匹配结果即为真
						if ("*".equals(paramDatas[index]) || "*".equals(property)
								|| ("," + paramDatas[index] + ",").indexOf(","
										+ property + ",") > -1) {
							flag = true;
							break;
						}
					}
					// 只要某一列不匹配，那么整个匹配结果即为假
					if (!flag) {
						resultFlag = false;
						break;
					}
				}
				// 如果匹配通过，那么这个参数对象即可放入结果列表中
				if(resultFlag){
					list.add(sysparam);
				}
			}
		} catch (Exception e) {
			log.error("根据参数属性取得参数列表错误：参数代码为：" + gcode + "，参数属性为：" + data);
			log.error("错误信息：" + e.getMessage());
		}
		return list;
	}

	/**
	 * 翻译参数
	 *
	 * @param gcode
	 *            参数的gcode
	 * @param mcode
	 *            参数的mcode
	 * @param index
	 *            参数，指明返回参数的第几列，从第0 列起算
	 * @return
	 */
	public String translate(String gcode, String mcode, int index) {
		String name = "";
		List list = null;
		try {
			list = getSysparamManager().getData(gcode, mcode,null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrvSysparam usysparam = null;
		if(null != list && list.size()>0)
			usysparam = (PrvSysparam) list.get(0);
		
		if (usysparam != null) {
			if (index == 0)
				name = usysparam.getMcode();
			else if (index == 1)
				name = usysparam.getMname();
			else if (index > 1) {
				String[] datas = PubUtil.split(usysparam.getData(), Separator.WAVE);
				if (datas.length > 0 && datas.length > index - 2)
					name = datas[index - 2];
			}
		}
		return name;
	}
	
	/**
	 * 翻译参数串
	 * 
	 * @param gcode
	 *            参数的gcode
	 * @param mcodes
	 *            参数的mcode串
	 * @param index
	 *            参数，指明返回参数的第几列，从第0 列起算
	 * @return
	 */
	public String translate(String gcode, String mcodes, String sepeartor,
			int index) {
		String name = "";
		List list = null;
		String[] mcode = PubUtil.split(mcodes, sepeartor);
		for (int i = 0; i < mcode.length; i++) {
			try {
				list = getSysparamManager().getData(gcode, mcode[i],null,null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			PrvSysparam usysparam = null;
			if(null != list && list.size()>0)
				usysparam = (PrvSysparam) list.get(0);
			if (usysparam != null) {
				if (index == 0)
					name += sepeartor + usysparam.getMcode();
				else if (index == 1)
					name += sepeartor + usysparam.getMname();
				else if (index > 1) {
					String[] datas = PubUtil.split(usysparam.getData(),
							Separator.WAVE);
					if (datas.length > 0 && datas.length > index - 2)
						name += sepeartor + datas[index - 2];
				}
			}
		}
		if (!StringUtils.isEmpty(name)) {
			name = name.substring(1);
		}
		return name;
	}

	/**
	 * 清除缓存，事实上cache与相对应的timestamp用的是同一个key
	 *
	 * @param gcode
	 */
	public void clear(String gcode) {
		Iterator<String> it = null;
		String key = null;
		List<String> keys = new ArrayList<String>();
		for (it = listCache.keySet().iterator(); it.hasNext();) {
			key = it.next();
			if (key != null && key.startsWith(gcode)) {
				keys.add(key);
			}
		}
		for(String k : keys){
			listCache.remove(k);
			listCacheTimestamp.remove(k);
		}
		keys.clear();

		for (it = dataCache.keySet().iterator(); it.hasNext();) {
			key = it.next();
			if (key != null && key.startsWith(gcode)) {
				keys.add(key);
			}
		}
		for(String k : keys){
			dataCache.remove(k);
			dataCacheTimestamp.remove(k);
		}
		keys = null;
		it = null;
	}

	/**
	 * 判定一个静态变量是否超时(当cache不存在时,也返回true)
	 *
	 * @param key
	 * @return
	 */
	private boolean isDataTimeout(String key) {
		return transientList.contains(key)
				|| !dataCacheTimestamp.containsKey(key)
				|| (dataCacheTimestamp.containsKey(key) && System
						.currentTimeMillis()
						- dataCacheTimestamp.get(key) > durationMillis);
	}

	/**
	 * 判定一个静态变量是否超时(当cache不存在时,也返回true)
	 *
	 * @param key
	 * @return
	 */
	private boolean isListTimeout(String key) {
		return transientList.contains(key)
				|| !listCacheTimestamp.containsKey(key)
				|| (listCacheTimestamp.containsKey(key) && System
						.currentTimeMillis()
						- listCacheTimestamp.get(key) > durationMillis);
	}

	/**
	 * 构建缓存的key
	 *
	 * @param gcode
	 * @param mcode
	 * @param scode
	 * @return
	 */
	private String buildKey(String gcode, String mcode, String scode) {
		String key = "";
		key = ((gcode == null) ? "" : gcode);
		if (mcode != null)
			key += "__" + mcode;
		if (scode != null)
			key += "___" + scode;
		return key;
	}
}
