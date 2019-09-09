package com.maywide.biz.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.PrvSysparamParam;
import com.maywide.biz.system.entity.ParamObj;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.biz.system.entity.Querysql;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.util.PubUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class ParamService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PersistentService dao;

	/**
	 * 取得参数列表（以gcode为条件）
	 * 
	 * @param gcode
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<PrvSysparam> getData(String gcode) throws Exception {
		List<PrvSysparam> paramList = dao.findByProperty(PrvSysparam.class, "gcode", gcode);
		Collections.sort(paramList, new ParamComparator());
		return paramList;
	}
	
	@Transactional(readOnly = true)
	public List<PrvSysparam> getDataNotCompare(String gcode) throws Exception {
		List<PrvSysparam> paramList = dao.findByProperty(PrvSysparam.class, "gcode", gcode);
		return paramList;
	}
	
	
	@Transactional(readOnly = true)
	public List<PrvSysparam> getData(PrvSysparam queryParam) throws Exception {
		List<PrvSysparam> paramList = dao.find(queryParam, true);
		Collections.sort(paramList, new ParamComparator());
		return paramList;
	}

	//加操作员的权限关联来选角色级别
	@Transactional(readOnly = true)
	public List<PrvSysparam> getAuthRoleInfoData(PrvSysparam queryParam,String rolelevel ) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> param = new ArrayList<Object>();
		sql.append("SELECT paramid,GCODE,mcode,mname,data,fmt,sort,scope from  prv_sysparam where gcode = ?  ");
		param.add(queryParam.getGcode());
		if("9".equals(rolelevel)){
			sql.append(" and mcode in ('0','5','7','9')");
		}
		if("7".equals(rolelevel)){
			sql.append(" and mcode in ('0','5','7')");
		}
		if("5".equals(rolelevel)){
			sql.append(" and mcode in ('0','5')");
		}
		if("0".equals(rolelevel)){
			sql.append(" and mcode in ('0')");
		}
		List<PrvSysparam> paramList = dao.find(sql.toString(),PrvSysparam.class,param.toArray());
		Collections.sort(paramList, new ParamComparator());
		return paramList;
	}

	@Transactional(readOnly = true)
	public String getMname(String gcode, String mcode) throws Exception {
		List list = getData(gcode, mcode,null,null);
		if(null != list && list.size()>0)
			return ((PrvSysparam) list.get(0)).getMname();
		else return null;
	}

	@Transactional(readOnly = true)
	public String getMcode(String gcode) throws Exception {
		List<PrvSysparam> list = getDataNotCompare(gcode);
		if (null != list && list.size() > 0)
			return list.get(0).getMcode();
		else
			return null;
	}
	
	/**
	 * 取得单个参数对象，以gcode，mcode为条件
	 * 
	 * @param gcode
	 * @param mcode
	 * @return PrvSysparam
	 */
	@Transactional(readOnly = true)
	public PrvSysparam getData(String gcode, String mcode) throws Exception {
	    
	    //改成用新的getData
	    List<PrvSysparam> paramList= this.getData(gcode,
	            mcode, null, null);
	    if(BeanUtil.isListNotNull(paramList)){
	        return paramList.get(0);
	    } else {
	        //如果查不到，走原来的方法再查一次
	        PrvSysparam queryParam = new PrvSysparam();
	        queryParam.setGcode(gcode);
	        queryParam.setMcode(mcode);
	        List list = dao.find(queryParam, true);
	        if (list.size() == 0) {
	            return null;
	        } else
	            return (PrvSysparam) list.iterator().next();
	    }
	    
	}

	/**
	 * 取得单个参数对象，以主键id为条件
	 * 
	 * @param id
	 * @return PrvSysparam
	 */
	@Transactional(readOnly = true)
	public PrvSysparam getDataById(Long id) throws Exception {
		PrvSysparam queryParam = new PrvSysparam();
		queryParam.setId(id);
		List list = dao.find(queryParam, true);
		if (list.size() == 0) {
			return null;
		} else
			return (PrvSysparam) list.iterator().next();
	}

	/**
	 * 取得参数列表，以主键gcode，scode为条件
	 * 
	 * @param gcode
	 * @param scode
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<PrvSysparam> getSubData(String gcode, Long scope) throws Exception {
		PrvSysparam queryParam = new PrvSysparam();
		queryParam.setGcode(gcode);
		queryParam.setScope(scope);
		
		List<PrvSysparam> paramList = dao.find(queryParam, true);
		Collections.sort(paramList, new ParamComparator());
		return paramList;
	}

	/**
	 * 取得单个参数对象的data数组，以主键gcode，mcode为条件
	 * 
	 * @param gcode
	 * @param mcode
	 * @return
	 */
	@Transactional(readOnly = true)
	public String[] getPrvSysparamData(String gcode, String mcode)
			throws Exception {
		PrvSysparam prvSysparam = new PrvSysparam();
		prvSysparam = getData(gcode, mcode);
		String data = prvSysparam.getData();
		return PubUtil.split(data, "~");
	}

	/**
	 * data的格式： columnIndex:data1,data2~columnIndex:data3
	 * 
	 * @param gcode
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = true)
	public List getParamByData(String gcode, String data) throws Exception {
		List list = new ArrayList();
		try {
			List<PrvSysparam> paramList = getData(gcode);
			// 对每个元素进行循环
			for (PrvSysparam PrvSysparam : paramList) {
				String paramData = PrvSysparam.getData(); // 参数中定义好的data
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
						// 这里是或逻辑，即，如果参数定义中定义的是"*"（全部）或者入参中用","分隔某一项数据已经包含在参数定义中，当前列匹配结果即为真
						if ("*".equals(paramDatas[index])
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
				if (resultFlag) {
					list.add(PrvSysparam);
				}
			}
		} catch (Exception e) {
			logger.error("根据参数属性取得参数列表错误：参数代码为：" + gcode + "，参数属性为：" + data);
			logger.error("错误信息：" + e.getMessage());
			throw e;
		}
		return list;
	}

	@Transactional(readOnly = true)
	public List getDynamicParam(String gcode, String params) throws Exception {
		Querysql querysql = (Querysql) dao.find(Querysql.class, gcode);
		if (querysql == null)
			throw new Exception("querysql表没有定义id为[" + gcode + "]的查询语句");
		String sql = querysql.getSelectsql();
		return convert(getBySql(sql, params));
	}

	private List getBySql(String querySql, String params) throws Exception {
		int count = 0;
		String tmpsql = querySql;
		while (tmpsql.indexOf("?") > 0) {
			count++;
			tmpsql = tmpsql.substring(tmpsql.indexOf("?") + 1);
		}

		if (count > 0) {
			String[] param = PubUtil.split(params, ",");
			String[] paramsArray = new String[count];
			System.arraycopy(param, 0, paramsArray, 0, param.length);
			return dao.findObjectList(querySql, paramsArray);
		}

		return dao.findObjectList(querySql, null);
	}

	private List convert(List objects) {
		if (objects == null)
			return null;
		List PrvSysparams = new ArrayList();
		for (Iterator it = objects.iterator(); it.hasNext();) {
			Object[] item = (Object[]) it.next();
			ParamObj obj = new ParamObj();
			obj.setMcode(item[0] + "");
			obj.setMname(item[1] + "");
			PrvSysparams.add(obj);
		}
		return PrvSysparams;
	}


	

	//重写一次getData方法
	/**
	 * 如果是查询格式(即gcode=@FORMAT),则忽略mcode、mcodeSeparator参数
	 * 如果是查询数据，且是普通参数，则根据gcode、mcode、mcodeSeparator参数查询
	 * @param gcode
	 * @param mcode
	 * @param scope
	 * @return
	 * @throws Exception
	 */
	public List getData(String gcode,String mcode,String scope,String mcodeSeparator) throws Exception {

		// 如果是查询格式的
		if (SysConstant.PrvSysparam.FORMAT.equals(gcode)) {
			PrvSysparamParam param = new PrvSysparamParam();
			Set set = new HashSet();
			set.add(SysConstant.PrvSysparam.ROOT);
			set.add(SysConstant.PrvSysparam.DYNAMIC_SQL);
			param.set_in_gcode(set);
			return dao.find(param);
		}

		// 如果是查询数据的
		// 先查format
		PrvSysparam formatParam = new PrvSysparam();
		formatParam.setMcode(gcode);
		List formatList = dao.find(formatParam);
		//
		if (formatList != null && formatList.size() > 0) {
			PrvSysparam format = (PrvSysparam) formatList.get(0);
			// 如果是动态SQL的
			if (format.getGcode().equalsIgnoreCase(
					SysConstant.PrvSysparam.DYNAMIC_SQL)) {
				PrvSysparam dataParam = new PrvSysparam();
				dataParam.setGcode(gcode);
				if(StringUtils.isBlank(mcode)){
					dataParam.setMcode(SysConstant.PrvSysparam.DYNAMIC_SQL_LEVEL);
					List sqlList = dao.find(dataParam);
					if (sqlList != null && sqlList.size() > 0) {
						PrvSysparam sqlData = (PrvSysparam) sqlList.get(0);
						return getBySQL(sqlData.getData(), null,null);
					} 
				}
				//没有配置分级展示的参数 ，或都不是分级展示的参数 ，则查翻译展示的参数 
				dataParam.setMcode(SysConstant.PrvSysparam.DYNAMIC_SQL_DETAIL);
				List sqlList = dao.find(dataParam);
				if (sqlList != null && sqlList.size() > 0) {
					PrvSysparam sqlData = (PrvSysparam) sqlList.get(0);
					return getBySQL(sqlData.getData(), mcode, mcodeSeparator);
				}
				
			}
			// 如果是普通参数
			else {
				PrvSysparam param = new PrvSysparam();
				param.setGcode(gcode);
				if(StringUtils.isNotBlank(mcode)){
					param.setMcode(mcode);
				}
				if(StringUtils.isNotBlank(scope)){
					param.setScope(new Long(scope));
				}
				
				List<PrvSysparam> paramList = dao.find(param, true);
				Collections.sort(paramList, new ParamComparator());
				if(gcode.equals("SYS_DEV_UPROP")){
					int top = -1;
					for(int i = 0;i<paramList.size();i++){
						PrvSysparam sysparam = paramList.get(i);
						if(sysparam.getMname().equals("套餐配机")){
							top = i;
							break;
						}
					}
					if(top != -1){
						PrvSysparam sysparam = paramList.remove(top);
						paramList.add(0, sysparam);
					}
				}
				return paramList;
			}
		}else{
			PrvSysparam param = new PrvSysparam();
			if(StringUtils.isNotBlank(mcode)){
				param.setMcode(mcode);
			}
			if(StringUtils.isNotBlank(gcode)){
				param.setGcode(gcode);
			}
			List<PrvSysparam> paramList = dao.find(param, true);
			if(paramList != null && paramList.size() > 0){
				Collections.sort(paramList, new ParamComparator());
				return paramList;
			}
		}
		return new ArrayList();
	}
	
	
	
	/**
	 * 根据表中保存的SQL语句，得到结果 这一段逻辑相对复杂，注释多写了一些
	 *
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private List getBySQL(String sql, String params, String paramSeparator) throws Exception {
		//先copy一份备用
		String tmpsql = sql;
		List<PrvSysparam> list = null;
		
		// 如果参数为null，那么就是不需要翻译，则SQL执行时不需要与“？”相关的内容
		if (params == null) {
			// 先找到“？”的index
			int pos = sql.toLowerCase().indexOf("?");
			// 如果压根就没有“？”， 那么就不用做任何处理了
			if (pos > 0) {
				// “？”前的SQL
				String sqlBefore = sql.substring(0, pos);
				// “？”后的SQL
				String sqlAfter = sql.substring(pos + 1);
				// “？”后的SQL在正常使用时显然必须以“AND”开始，那么在AND之前都是无用的。
				// （需要子查询时，如果在这个子WHERE里面除了“？”外就没有其他的查询条件了，那么就在这里补上
				// 1=1，免得直接找到下一个“AND”去了
				// 继续想想办法能不能兼容
				if (sqlAfter.toLowerCase().indexOf("and") > 0)
					sqlAfter = sqlAfter.substring(sqlAfter.toLowerCase()
							.indexOf("and"));
				// “？”前的SQL，寻找这个“？”前面的条件是AND还是WHERE
				int posAnd = sqlBefore.toLowerCase().lastIndexOf("and");
				int posWhere = sqlBefore.toLowerCase().lastIndexOf("where");

				// 如果在“？”前的SQL中，最后一个WHERE的位置比最后一个AND的位置靠后，那么，“？”前面带的就是WHERE，（由此支持子查询）
				// 当然，如果要支持子查询的话，目前逻辑下，“？”后面必须要跟上
				// 1=1才可以，否则“？”后面的语句就直接找到下一个AND去了。
				if (posWhere > posAnd) {
					sql = sqlBefore.substring(0, posWhere);
					// 如果“？”后面没有了，就把where去掉，如果还有，就把“？”的那个条件和跟着的“AND”去掉
					if (sqlAfter != null && sqlAfter.trim().length() > 0) {
						sql = sqlBefore.substring(0, posWhere + 5);
						sqlAfter = sqlAfter.substring(sqlAfter.toLowerCase()
								.indexOf("and") + 3);
					} else
						sql = sqlBefore.substring(0, posWhere);
				}
				// 如果前面是“AND” 那么，直接去掉AND后到“？”就可以了
				else
					sql = sqlBefore.substring(0, posAnd);
				// 把两段合并
				sql = sql + " " + sqlAfter;
				
			}
			
			list = dao.find(sql, PrvSysparam.class);
			
			//return dao.find(sql, PrvSysparam.class);
		} else {
			//处理查询参数
			int count = 0;
			while (tmpsql.indexOf("?") > 0) {
				count++;
				tmpsql = tmpsql.substring(tmpsql.indexOf("?") + 1);
			}
			
			if(StringUtils.isNotBlank(paramSeparator)){
				String[] param = PubUtil.split(params, paramSeparator);
				if(null==param || param.length!=count){
					throw new BusinessException("查询动态参数:实际入参和动态sql参数不匹配");
				}
				
				List paramList = new ArrayList();
				for(String obj : param){
					paramList.add(obj);
				}
				
				list = dao.find(sql, PrvSysparam.class, paramList.toArray());
				
				//return dao.find(sql, PrvSysparam.class, paramList.toArray());
			} else {
				if(count>=2){
					throw new BusinessException("查询动态参数:实际入参和动态sql参数不匹配");
				}
			}
		}
		
		if (list == null) list = dao.find(sql, PrvSysparam.class, params);
		
		Collections.sort(list, new ParamComparator());
		
		//return dao.find(sql, PrvSysparam.class, params);
		return list;
	}
	
	public Map<String, String> getSelectData(String gcode) throws Exception{
		CheckUtils.checkEmpty(gcode, "获取选择数据：参数编码不能为空");
		
		Map<String, String> retMap = new LinkedHashMap<String, String>();
		List<PrvSysparam> list = getData(gcode,null,null,null);
		
		if (BeanUtil.isListNotNull(list)) {
			for (PrvSysparam param : list) {
				retMap.put(param.getMcode(), param.getMname());
			}
		}

		return retMap;
	}
	
		
	public Map<String, String> getSelectData(String gcode,String mcode) throws Exception{
		CheckUtils.checkEmpty(gcode, "获取选择数据：参数编码不能为空");
		
		Map<String, String> retMap = new LinkedHashMap<String, String>();
		List<PrvSysparam> list = getData(gcode,mcode,null,null);
		
		if (BeanUtil.isListNotNull(list)) {
			for (PrvSysparam param : list) {
				retMap.put(param.getMcode(), param.getMname());
			}
		}

		return retMap;
	}
	
	public List getSupData(Long formatId) throws Exception {
		try {
			PrvSysparam sysparam = (PrvSysparam) dao.find(
					PrvSysparam.class, formatId);
			// 如果是 -1
			if (sysparam.getScope() == null
					|| sysparam.getScope().equals(new Long(-1))) {
				return new ArrayList();
			}
			// 如果不是
			else {
				PrvSysparam sup = (PrvSysparam) dao.find(
						PrvSysparam.class, sysparam.getScope());
				
				List<PrvSysparam> paramList = getData(sup.getMcode());
				Collections.sort(paramList, new ParamComparator());
				return paramList;
			}

		} catch (Exception e) {
			//System.out.println(e.getCause());
			throw e;
		}
	}
	
	public class ParamComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			if (o1 == null) return 1;
			if (o2 == null) return -1;
			
			PrvSysparam param1 = (PrvSysparam) o1;
			PrvSysparam param2 = (PrvSysparam) o2;
			
			if (StringUtils.isEmpty(param1.getMname())) {
				return 1;
			}
			if (StringUtils.isEmpty(param2.getMname())) {
				return -1;
			}
			String pinyin1 = PinYinUtils.converterToFirstSpell(param1.getMname());
			String pinyin2 = PinYinUtils.converterToFirstSpell(param2.getMname());
			
			int rtnval = pinyin1.compareTo(pinyin2);
			
			return rtnval;
		}
	}
	/**
	 * ywp data是sql的数据取值
	 * 针对
	 * SELECT AREAID AS mcode,NAME as mname FROM PRV_AREA WHERE AREAID=?
	 * SELECT AREAID AS mcode,NAME as mname FROM PRV_AREA
	 * params参数
	 */
	public Map<String, String> getKeyValue(String gcode,String mcode,List params) throws Exception{
		Map<String, String> retMap = new LinkedHashMap<String, String>();
		List<PrvSysparam> list = getList(gcode,mcode);
		
		if (BeanUtil.isListNotNull(list)) {
			PrvSysparam item = list.get(0);
			String sql = item.getData();
			String[] sqlnowhere = sql.split("where");
			List<PrvSysparam> listKeyValue = getList(sqlnowhere[0],params);
			for(int i=0;i<listKeyValue.size();i++){
				PrvSysparam it = listKeyValue.get(i);
				retMap.put(it.getMcode(), it.getMname());
			}
		}
		return retMap;
	}
	/**
	 * ywp只用普通查询gcode,mcode
	 * @throws Exception 
	 */
	public List<PrvSysparam> getList(String gcode,String mcode) throws Exception{
		PrvSysparam param = new PrvSysparam();
		if(StringUtils.isNotBlank(mcode)){
			param.setMcode(mcode);
		}
		if(StringUtils.isNotBlank(gcode)){
			param.setGcode(gcode);
		}
		List<PrvSysparam> paramList = dao.find(param, true);
		if(paramList != null && paramList.size() > 0){
			Collections.sort(paramList, new ParamComparator());
		}
		return paramList;
	}
	public List<PrvSysparam> getList(String sql,List params) throws Exception{
		List<PrvSysparam> list = dao.find(sql,PrvSysparam.class, params.toArray());
		return list;
	}
	
	//ywp 用是否加上网格权限判断
	public boolean notJudgeGrid(String gcode,LoginInfo loginInfo) throws Exception{
		boolean flag = false;
		List<Object> sqlparam = new ArrayList<Object>();
		StringBuffer selectSQL = new StringBuffer("SELECT COUNT(*) FROM prv_sysparam a ");
		selectSQL.append("WHERE a.gcode=? AND (LOCATE(?,a.mcode) OR LOCATE('*',a.mcode))");
		
		sqlparam.add(gcode);
		sqlparam.add(loginInfo.getCity());
		
		long counter = dao.count(selectSQL.toString(), sqlparam.toArray());
		if(counter>0){
			flag = true;
		}
		return flag;
	}
}
