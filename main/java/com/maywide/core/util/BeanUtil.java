package com.maywide.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.googlecode.jsonplugin.JSONUtil;

public class BeanUtil {

	public static Object getMapObject(Map<String, String> map, String key,
			Class cls) throws Exception {
		String str = map.get(key);

		/* 基本类型 */
		if (String.class.equals(cls)) {
			return str;
		} else if (Integer.class.equals(cls) || int.class.equals(cls)) {
			if (str == null && Integer.class.equals(cls))
				return null;
			else if (str == null && int.class.equals(cls))
				return 0;
			else
				return Integer.parseInt(str);
		} else if (Double.class.equals(cls) || double.class.equals(cls)) {
			if (str == null && Double.class.equals(cls))
				return null;
			else if (str == null && double.class.equals(cls))
				return 0.00;
			else
				return Double.parseDouble(str);
		} else if (Float.class.equals(cls) || float.class.equals(cls)) {
			if (str == null && Float.class.equals(cls))
				return null;
			else if (str == null && float.class.equals(cls))
				return 0.00;
			else
				return Float.parseFloat(str);
		} else if (Boolean.class.equals(cls) || boolean.class.equals(cls)) {
			if (str == null && Boolean.class.equals(cls))
				return null;
			else if (str == null && boolean.class.equals(cls))
				return false;
			else
				return Boolean.parseBoolean(str);
		} else if (Short.class.equals(cls) || short.class.equals(cls)) {
			if (str == null && Short.class.equals(cls))
				return null;
			else if (str == null && short.class.equals(cls))
				return 0;
			else
				return Short.parseShort(str);
		} else if (Long.class.equals(cls) || long.class.equals(cls)) {
			if (str == null && Long.class.equals(cls))
				return null;
			else if (str == null && long.class.equals(cls))
				return 0;
			else
				return Long.parseLong(str);
		} else if (Byte.class.equals(cls) || byte.class.equals(cls)) {
			if (str == null && Byte.class.equals(cls))
				return null;
			else if (str == null && byte.class.equals(cls))
				return 0;
			else
				return Byte.parseByte(str);
		} else if (Character.class.equals(cls) || char.class.equals(cls)) {
			if (str == null && Character.class.equals(cls))
				return null;
			else if (str == null && char.class.equals(cls))
				return '0';
			else
				return str.charAt(0);
		} else if (Date.class.equals(cls)) {
			if (str == null)
				return null;

			else {
				str = str.replaceAll("T", " ");
				Date d1 = DateTimeUtil.parseDate(str,
						DateTimeUtil.PATTERN_DATETIME);
				return d1;
			}
		} else {
			if (str == null)
				return null;

			JSONObject jsonobj = new JSONObject(str);
			Map<String, String> paramMap = new HashMap<String, String>();

			Iterator<String> keys = jsonobj.keys();
			while (keys.hasNext()) {
				String keystr = (String) keys.next();
				paramMap.put(keystr, jsonobj.get(keystr).toString());
			}

			Object obj = cls.newInstance();

			List<Field> fs = BeanUtils.getDeclaredFields(cls);
			for (Field field : fs) {
				if (!field.isAccessible())
					field.setAccessible(true);
				field.set(
						obj,
						getMapObject(paramMap, field.getName(), field.getType()));
			}

			return obj;

		}
	}

	/**
	 * 字符串转化为对象
	 * 
	 * @param str
	 *            字符串
	 * @param claz
	 *            类
	 * @return
	 * @throws Exception
	 */
	public static Object strToObject(String str, Class claz) throws Exception {
		if (str == null)
			return null;
		if (claz == null) {
			throw new Exception("类名不能为空");
		}

		// 根据类型,进行相应的字符串转对象
		// 如果字符串
		if (String.class.equals(claz)) {
			return str;
		}

		// 如果基本类型
		if (Integer.class.equals(claz) || int.class.equals(claz)) {
			return Integer.parseInt(str);
		} else if (Double.class.equals(claz) || double.class.equals(claz)) {
			return Double.parseDouble(str);
		} else if (Float.class.equals(claz) || float.class.equals(claz)) {
			return Float.parseFloat(str);
		} else if (Boolean.class.equals(claz) || boolean.class.equals(claz)) {
			return Boolean.parseBoolean(str);
		} else if (Short.class.equals(claz) || short.class.equals(claz)) {
			return Short.parseShort(str);
		} else if (Long.class.equals(claz) || long.class.equals(claz)) {
			return Long.parseLong(str);
		} else if (Byte.class.equals(claz) || byte.class.equals(claz)) {
			return Byte.parseByte(str);
		} else if (Character.class.equals(claz) || char.class.equals(claz)) {
			return str.charAt(0);
		}
		// 日期
		if (Date.class.equals(claz)) {
			Date d1 = DateTimeUtil
					.parseDate(str, DateTimeUtil.PATTERN_DATETIME);
			if (d1 == null) {
				Date d2 = DateTimeUtil.parseDate(str,
						DateTimeUtil.PATTERN_DEFAULT);
				if (d2 != null) {
					return d2;
				} else {
					return null;
				}
			} else {
				return d1;
			}
		}

		throw new Exception("类[" + claz.getName() + "]不支持字符串转为对象");
	}

	/**
	 * 把字符串赋值给对象的属性,系统自动会把字符串转为相应的对象
	 * 
	 * @param object
	 *            赋值的对象
	 * @param fieldName
	 *            属性名字
	 * @param str
	 *            字符串
	 * @throws Exception
	 */

	public static void setFieldValue(Object object, String fieldName, String str)
			throws Exception {
		Field field = null;
		try {
			field = BeanUtils.getDeclaredField(object, fieldName);
		} catch (Exception e) {
			throw new Exception("反射失败:不存在属性[" + fieldName + "]或"
					+ e.getMessage());
		}

		if (!(field.isAccessible()))
			field.setAccessible(true);

		Object value = strToObject(str, field.getType());
		field.set(object, value);
	}

	/**
	 * 字符串，通过正则表达式，转为json字符串：{a:'dd',b:'dd'}
	 */
	public static String pareseRegexTJson(String line, Pattern pattern,
			String[] fields) throws Exception {
		if (line == null)
			return null;

		Matcher matcher = pattern.matcher(line);
		// 先比较Field与分解字符串的数量
		if (!matcher.find()) {
			throw new Exception("数据格式错误");
		}

		StringBuilder sb = new StringBuilder("{");
		if (fields != null && fields.length > 0 && matcher.groupCount() > 0) {
			int groupCount = matcher.groupCount();
			sb.append(fields[0]);
			sb.append(":");
			sb.append("\"");
			sb.append(matcher.group(1));
			sb.append("\"");
			for (int i = 1; i < fields.length && i < groupCount; i++) {
				sb.append(",");
				sb.append(fields[i]);
				sb.append(":");
				sb.append("\"");
				sb.append(matcher.group(i + 1));
				sb.append("\"");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	/*
	 * 文件类型
	 */
	// public static RegexTStrResult parseFileTJson(File file, String regex,
	// String[] fields) {
	// RegexTStrResult result = new RegexTStrResult();
	//
	// StringBuilder data = new StringBuilder("[");
	// StringBuilder errdata = new StringBuilder("[");
	// int total = 0;
	// int errnum = 0;
	// int sucnum = 0;
	//
	// if (file != null) {
	// // 打开文件流,扫描数据
	// BufferedReader reader = null;
	// try {
	// // 读入文件流
	// reader = new BufferedReader(new InputStreamReader(
	// new FileInputStream(file)));
	//
	// // 初始化正则器
	// Pattern pattern = Pattern.compile(regex);
	// // 行号,用来判断第几行出错
	// int lineNum = 0;
	// String line = null;
	// String retstr = "";
	//
	// while ((line = reader.readLine()) != null) {
	// lineNum++;
	// line = line.trim();
	// if (StringUtils.isEmpty(line))
	// continue;
	//
	// // 对每行进行处理
	// try {
	// retstr = pareseRegexTJson(line, pattern, fields);
	// data.append(retstr);
	// data.append(",");
	// sucnum++;
	// } catch (Exception e) {
	// errdata.append("{line:\"");
	// errdata.append(line);
	// errdata.append("\",message:\"");
	// errdata.append(e.getMessage());
	// errdata.append("\",linenum:");
	// errdata.append(lineNum);
	// errdata.append("}");
	// errdata.append(",");
	// errnum++;
	// } finally {
	// total++;
	// }
	// }
	//
	// // 去掉后面的,号
	// if (data.length() > 1)
	// data.deleteCharAt(data.length() - 1);
	// data.append("]");
	//
	// if (errdata.length() > 1)
	// errdata.deleteCharAt(errdata.length() - 1);
	// errdata.append("]");
	// } catch (Exception e) {
	// result.err(e.getMessage());
	// } finally {
	// // 关闭文件流
	// try {
	// if (reader != null) {
	// reader.close();
	// }
	// } catch (Exception e) {
	// reader = null;
	// }
	// }
	//
	// // 组装返回值
	// result.setData(data.toString());
	// result.setErrdata(errdata.toString());
	// result.setErrnum(errnum);
	// result.setSucnum(sucnum);
	// result.setTotal(total);
	// if (total > sucnum)
	// result.suc("部分成功");
	// else
	// result.suc("全部成功");
	// // 返回
	// } else {
	// result.err("文件打开错误");
	// }
	// return result;
	// }

	public static Object jsonToObject(JSONObject json, Class cls)
			throws Exception {
		if (json == null)
			return null;
		Object obj = cls.newInstance();

		List<Field> fs = BeanUtils.getDeclaredFields(cls);
		String val = null;
		JSONArray jsonarray = null;
		for (Field field : fs) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}

			if (field.getType().equals(List.class)) {
				try {
					Object object = json.get(field.getName());
					if(object instanceof JSONArray) {
						jsonarray = (JSONArray) object;
					}else if(object instanceof List) {
						List list = (List) json.get(field.getName());
						jsonarray = new JSONArray(list);
					}
					
//					jsonarray = json.getJSONArray(field.getName());
					
				} catch (JSONException e) {
					e.printStackTrace();
					jsonarray = null;
				}
				field.set(
						obj,
						jsonToObject(jsonarray, ((ParameterizedType) field
								.getGenericType()).getActualTypeArguments()[0]));
			} else if (field.getType().equals(HashMap.class)
					|| field.getType().equals(Map.class)) {
				try {
					val = json.getString(field.getName());
				} catch (JSONException e) {
					val = null;
				}

				field.set(
						obj,
						jsonToMap(((ParameterizedType) field.getGenericType())
								.getActualTypeArguments(), val));
			} else if (field.getType().equals(HashSet.class)
					|| field.getType().equals(Set.class)) {
				try {
					jsonarray = json.getJSONArray(field.getName());
				} catch (JSONException e) {
					jsonarray = null;
				}
				field.set(
						obj,
						jsonToSet(jsonarray, ((ParameterizedType) field
								.getGenericType()).getActualTypeArguments()[0]));
			} else {
				try {
					val = json.getString(field.getName());
				} catch (JSONException e) {
					val = null;
				}

				field.set(obj, toFieldValue(field.getType(), val));
			}
		}

		return obj;
	}

	public static List jsonToObject(JSONArray json, Type cls) throws Exception {
		if (json == null)
			return null;
		List list = new ArrayList();
		for (int i = 0; i < json.length(); i++) {
			if (String.class.equals((Class) cls)) {
				list.add(json.getString(i));
			} else if (Long.class.equals((Class) cls)) {
				list.add(json.getLong(i));
			} else if (Integer.class.equals((Class) cls)) {
				list.add(json.getInt(i));
			} else if (Double.class.equals((Class) cls)) {
				list.add(json.getDouble(i));
			} else if (Float.class.equals((Class) cls)) {
				list.add(Float.parseFloat(json.getString(i)));
			} else if (Boolean.class.equals((Class) cls)) {
				list.add(json.getBoolean(i));
			} else {
				list.add(jsonToObject(json.getJSONObject(i),
						Class.forName(cls.toString().replaceAll("class ", ""))));
			}
		}
		return list;
	}

	public static Set jsonToSet(JSONArray json, Type cls) throws Exception {
		if (json == null)
			return null;
		Set set = new HashSet();
		for (int i = 0; i < json.length(); i++) {
			if (String.class.equals((Class) cls)) {
				set.add(json.getString(i));
			} else if (Long.class.equals((Class) cls)) {
				set.add(json.getLong(i));
			} else if (Integer.class.equals((Class) cls)) {
				set.add(json.getInt(i));
			} else if (Double.class.equals((Class) cls)) {
				set.add(json.getDouble(i));
			} else if (Float.class.equals((Class) cls)) {
				set.add(Float.parseFloat(json.getString(i)));
			} else if (Boolean.class.equals((Class) cls)) {
				set.add(json.getBoolean(i));
			} else {
				set.add(jsonToObject(json.getJSONObject(i),
						Class.forName(cls.toString().replaceAll("class ", ""))));
			}
		}
		return set;
	}

	// 目前先支持<String, Object>
	public static Map jsonToMap(Type[] types, String value) throws Exception {
		if (value == null || value.trim() == "null" || value.trim() == "") {
			return null;
		}

		Map result = new HashMap();
		HashMap strmap = (HashMap) JSONUtil.deserialize(value);
		Iterator keyiterator = strmap.keySet().iterator();

		String key = null;
		while (keyiterator.hasNext()) {
			key = (String) keyiterator.next();
			Class classType = (Class) types[1];

			if (String.class.equals(classType)) {
				if (strmap.get(key) == null) {
					result.put(key, null);
				} else {
					result.put(key,
							toFieldValue(classType, strmap.get(key).toString()));
				}
			} else if (Long.class.equals(classType)) {
				if (strmap.get(key) == null) {
					result.put(key, null);
				} else {
					result.put(key,
							toFieldValue(classType, strmap.get(key).toString()));
				}
			} else if (Integer.class.equals(classType)) {
				if (strmap.get(key) == null) {
					result.put(key, null);
				} else {
					result.put(key,
							toFieldValue(classType, strmap.get(key).toString()));
				}
			} else if (Double.class.equals(classType)) {
				if (strmap.get(key) == null) {
					result.put(key, null);
				} else {
					result.put(key,
							toFieldValue(classType, strmap.get(key).toString()));
				}
			} else if (Float.class.equals(classType)) {
				if (strmap.get(key) == null) {
					result.put(key, null);
				} else {
					result.put(key,
							toFieldValue(classType, strmap.get(key).toString()));
				}
			} else if (Boolean.class.equals(classType)) {
				if (strmap.get(key) == null) {
					result.put(key, null);
				} else {
					result.put(key,
							toFieldValue(classType, strmap.get(key).toString()));
				}
			} else {
				result.put(
						key,
						jsonToObject(new JSONObject((HashMap) strmap.get(key)),
								classType));
			}
		}
		return result;
	}

	public static Object toFieldValue(Class fieldtype, String value)
			throws Exception {
		if (String.class.equals(fieldtype)) {
			if (value == "null") {
				return null;
			}
			return value;
		} else if (Integer.class.equals(fieldtype)
				|| int.class.equals(fieldtype)) {
			if ((value == null || value == "null" || value.length()==0)
					&& Integer.class.equals(fieldtype))
				return null;
			else if (value == null && int.class.equals(fieldtype))
				return 0;
			else
				return Integer.parseInt(value);
		} else if (Double.class.equals(fieldtype)
				|| double.class.equals(fieldtype)) {
			if ((value == null || value == "null" || value.length()==0)
					&& Double.class.equals(fieldtype))
				return null;
			else if ((value == null || value.equals("null") || value.length()==0)
					&& double.class.equals(fieldtype))
				return 0.00;
			else
				return Double.parseDouble(value);
		} else if (Float.class.equals(fieldtype)
				|| float.class.equals(fieldtype)) {
			if ((value == null || value == "null" || value.length()==0)
					&& Float.class.equals(fieldtype))
				return null;
			else if (value == null && float.class.equals(fieldtype))
				return 0.00;
			else
				return Float.parseFloat(value);
		} else if (Boolean.class.equals(fieldtype)
				|| boolean.class.equals(fieldtype)) {
			if (value == null && Boolean.class.equals(fieldtype))
				return null;
			else if (value == null && boolean.class.equals(fieldtype))
				return false;
			else
				return Boolean.parseBoolean(value);
		} else if (Short.class.equals(fieldtype)
				|| short.class.equals(fieldtype)) {
			if (value == null && Short.class.equals(fieldtype))
				return null;
			else if (value == null && short.class.equals(fieldtype))
				return 0;
			else
				return Short.parseShort(value);
		} else if (Long.class.equals(fieldtype) || long.class.equals(fieldtype)) {
			if ((value == null || value == "null")
					&& Long.class.equals(fieldtype))
				return null;
			else if (value == null && long.class.equals(fieldtype))
				return 0;
			else
				return Long.parseLong(value);
		} else if (Byte.class.equals(fieldtype) || byte.class.equals(fieldtype)) {
			if (value == null && Byte.class.equals(fieldtype))
				return null;
			else if (value == null && byte.class.equals(fieldtype))
				return 0;
			else
				return Byte.parseByte(value);
		} else if (Character.class.equals(fieldtype)
				|| char.class.equals(fieldtype)) {
			if (value == null && Character.class.equals(fieldtype))
				return null;
			else if (value == null && char.class.equals(fieldtype))
				return '0';
			else
				return value.charAt(0);
		} else if (Date.class.equals(fieldtype)) {
			if (value == null)
				return null;

			else {
				value = value.replaceAll("T", " ");
				Date d1 = DateTimeUtil.parseDate(value,
						DateTimeUtil.PATTERN_DATETIME);
				return d1;
			}
		} else if (fieldtype instanceof Object) {
			if (value == null || value.trim() == "" || value.trim() == "null")
				return null;

			return jsonToObject(new JSONObject(value), fieldtype);
		}
		return null;
	}

	public static interface RetResult {
		public static String SUCESS = "1";
		public static String ERROR = "0";
	}

	/**
	 * 将列表数据拼成字符串，以separator分隔
	 * 
	 * @param collection
	 * @param separator
	 * @return
	 * @throws Exception
	 */
	public static String join(Collection collection, String separator)
			throws Exception {
		if (null == collection)
			return null;
		else {
			Iterator iterator = collection.iterator();
			if (iterator == null)
				return null;

			if (!iterator.hasNext())
				return "";
			Object first = iterator.next();
			if (!iterator.hasNext())
				return ObjectUtils.toString(first);
			StringBuffer buf = new StringBuffer(256);
			if (first != null)
				buf.append(first);
			do {
				if (!iterator.hasNext())
					break;
				if (separator != null)
					buf.append(separator);
				Object obj = iterator.next();
				if (obj != null)
					buf.append(obj);
			} while (true);
			return buf.toString();
		}
	}

	public static Object jsonToObject(String jsonStr, Class cls)
			throws Exception {
		if (StringUtils.isBlank(jsonStr) || "null".equalsIgnoreCase(jsonStr)) {
			return null;
		}

		JSONObject json = new JSONObject(jsonStr);
		Object retObj = BeanUtil.jsonToObject(json, cls);

		return retObj;
	}
	
	public static String object2String(Object obj)
			throws Exception {
		if (null == obj)
			return null;
		else {
			return obj.toString();
		}
	}
	
	public static boolean isListNotNull(List list)
			throws Exception {
		
		if (null != list && list.size()>0 && !list.isEmpty()){
			return true;
		}
			
		return false;
	}
	
	public static boolean isListNull(List list)
			throws Exception {
		
		return !isListNotNull(list);
	}

	public static Map beanToMap(Object bean) throws NoSuchFieldException{
		if (bean == null) {
			return new HashMap();
		} else {
			Map description = new HashMap();
			List<Field> filedList = BeanUtils.getDeclaredFields(bean.getClass());
			if(filedList!=null) {
				for (int i = 0; i < filedList.size(); ++i) {
					String key = filedList.get(i).getName();
					Object value = BeanUtils.getFieldValue(bean, key);
					if (value != null) {
						description.put(key, value);
					}
				}
			}
			return description;
		}
	}
}
