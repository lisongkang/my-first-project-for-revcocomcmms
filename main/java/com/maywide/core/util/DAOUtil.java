package com.maywide.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

public class DAOUtil {
	private static final String PACKAGE_PREFIX = "com.maywide.";

	/**
	 * 将结果集封装成VO
	 * 
	 * @param list
	 * @param hql
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static List convert(List<Object[]> list, String sql, Class cls)
			throws Exception {
		List result = new ArrayList();
		// sql = sql.toLowerCase();
		// String sqlbak = sql;
		int spos = 0, epos = 0;
		spos = sql.toLowerCase().indexOf("select") + 6;
		epos = sql.toLowerCase().indexOf("from");
		if (spos < 0 || epos < 0 || epos < spos) {
			throw new Exception("SQL不合法，找不到SELECT 或 FROM 关键字！");
		}
		String fieldsStr = sql.substring(spos, epos);
		if (null == fieldsStr) {
			return new ArrayList();
		}
		if ("*".equals(fieldsStr.trim())) {
			throw new Exception("请不要在查询结果里使用'*'，否则结果无法封装！");
		}
		String[] fields = fieldsStr.split(",");
		for (int i = 0; i < list.size(); i++) {
			Object o = cls.newInstance();
			try {
				Object[] objects = (Object[]) list.get(i);
				int j = 0;
				for (String field : fields) {
					field = field.trim();
					if (field.toLowerCase().indexOf(" as ") > 0)
						field = field.substring(
								field.toLowerCase().indexOf(" as ") + 4).trim();
					if (field.indexOf(" ") > 0)
						field = field.substring(field.indexOf(" ") + 1).trim();
					setProperty(o, field, objects[j++]);
				}
			} catch (ClassCastException e) {
				Object object = list.get(i);
				for (String field : fields) {
					field = field.trim();
					if (field.toLowerCase().indexOf(" as ") > 0)
						field = field.substring(
								field.toLowerCase().indexOf(" as ") + 4).trim();
					if (field.indexOf(" ") > 0)
						field = field.substring(field.indexOf(" ") + 1).trim();
					setProperty(o, field, object);
				}
			}

			result.add(o);
		}
		return result;
	}

	/**
	 * 递归方法，用于级联VO，将属性值set到VO里面
	 * 
	 * @param o
	 * @param field
	 * @param value
	 * @throws Exception
	 */
	public static void setProperty(Object o, String field, Object value)
			throws Exception {
		// 兼容"_"的格式的字段查询
		StringBuffer sb = new StringBuffer("");
		String[] fields = field.split("_");
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			String firstChar = fields[i].substring(0, 1);
			String endChar = fields[i].substring(1);
			sb.append(firstChar.toUpperCase()).append(endChar);
		}
		field = sb.toString();

		if (field.indexOf("#") > 0) {
			String fieldName = field.substring(0, field.indexOf("#")).trim();
			Object subo = PropertyUtils.getProperty(o, fieldName);

			// 自动初始化级联VO
			if (subo == null) {
				Field f = BeanUtils.getDeclaredField(o, fieldName);
				subo = f.getType().newInstance();
				setProperty(o, fieldName, subo);
			}

			setProperty(subo, field.substring(field.indexOf("#") + 1).trim(),
					value);
		} else {
			if (value == null)
				BeanUtils.setFieldValue(o, field.trim(), null);
			else
				org.apache.commons.beanutils.BeanUtils.setProperty(o,
						field.trim(), value);
		}
	}

	/**
	 * 按照实体得到写SQL的字段字符串
	 * 
	 * @param entity
	 * @param order
	 * @param seperator
	 * @return
	 * @throws Exception
	 */
	public static String getFields(Object entity, List<String> order,
			String seperator, String alias) throws Exception {
		String fields = "";
		List<Field> field = BeanUtils.getDeclaredFields(entity.getClass());

		String prefix = alias == null || alias.equals("") ? "" : alias + ".";

		for (Field property : field) {
			String tmpPropertyName = property.getName();
			String entityProperty = BeanUtils.getPropertyName(tmpPropertyName);
			String columnName = entityProperty;
			// 如果Annotation里面定义了列名，则按照Annotation里面定义的，否则，就按照entityProperty了
			Method method = BeanUtils.getGetter(entity.getClass(), property);
			javax.persistence.Column column = method
					.getAnnotation(javax.persistence.Column.class);
			if (column != null) {
				columnName = column.name();
			}
			Object value = PropertyUtils.getProperty(entity, tmpPropertyName);
			if (value != null) {
				if (value instanceof String
						&& StringUtils.isEmpty(value.toString().trim()))
					continue;
				if ((value.getClass().getName().indexOf(PACKAGE_PREFIX) > -1)) {
					fields += getFields(value, order, seperator, alias);
				} else if (tmpPropertyName.startsWith("_le_")) {
					fields += " " + seperator + " " + prefix + columnName
							+ " <= ? ";
					order.add(tmpPropertyName);
				} else if (tmpPropertyName.startsWith("_lt_")) {
					fields += " " + seperator + " " + prefix + columnName
							+ " < ? ";
					order.add(tmpPropertyName);
				} else if (tmpPropertyName.startsWith("_ge_")) {
					fields += " " + seperator + " " + prefix + columnName
							+ " >= ? ";
					order.add(tmpPropertyName);
				} else if (tmpPropertyName.startsWith("_gt_")) {
					fields += " " + seperator + " " + prefix + columnName
							+ " > ? ";
					order.add(tmpPropertyName);
				} else if (tmpPropertyName.startsWith("_ne_")) {
					fields += " " + seperator + " " + prefix + columnName
							+ " <> ? ";
					order.add(tmpPropertyName);
				} else if (tmpPropertyName.startsWith("_like_")
						&& value instanceof String) {
					fields += " " + seperator + " " + prefix + columnName
							+ " like ? ";
					order.add(tmpPropertyName);
				} else if (tmpPropertyName.startsWith("_in_")
						&& value instanceof Set) {
					Set v = (Set) value;
					Iterator it = v.iterator();
					if (it.hasNext()) {
						fields += " " + seperator + " " + prefix + columnName
								+ " in (? ";
						it.next();
						while (it.hasNext()) {
							fields += ", ? ";
							it.next();
						}
						fields += ")";
						order.add(tmpPropertyName);
					}
				} else {
					fields += " " + seperator + " " + prefix + columnName
							+ " = ? ";
					order.add(tmpPropertyName);
				}
			}
		}
		if (",".equals(seperator))
			fields = fields.substring(fields.indexOf(",") + 1);
		return fields;
	}

	// 取表名，先从Table Annotation开始取，如果取不到，则去取Entity的
	public static String getTable(Object entity) throws Exception {
		String tableName = "";
		javax.persistence.Table table = entity.getClass().getAnnotation(
				javax.persistence.Table.class);
		tableName = table.name();

		if (tableName == null || tableName.equals("")) {
			javax.persistence.Entity entityAnnotation = entity.getClass()
					.getAnnotation(javax.persistence.Entity.class);
			tableName = entityAnnotation.name();
		}
		if (tableName == null || tableName.equals("")) {
			throw new Exception("实体错误，找不到表名的Annotation！");
		}
		return tableName;
	}

	public static boolean checkEntity(Object entity) throws Exception {
		// 暂时放开
		// 检查html、sql的非法代码
		// String entityStr = ToStringBuilder.reflectionToString(entity,
		// ToStringStyle.SIMPLE_STYLE);
		// // 放开SQL的非法字符
		// //if (!Regexp.checkSqlInput(entityStr)) throw new
		// Exception("属性不能包含~#$%\\^\\\\'等非法sql字符");
		// if (!Regexp.checkHtmlInput(entityStr)) throw new
		// Exception("属性不能包含HTML/SCRIPT字符");
		return true;
	}

	/**
	 * 取得实体中定义为@Id的属性
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Field getPKField(Class clazz) throws Exception {
		Field field = null;
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {

			boolean id = method.getAnnotation(javax.persistence.Id.class) != null;
			boolean embeddedId = method
					.getAnnotation(javax.persistence.EmbeddedId.class) != null;
			if (id || embeddedId) {
				String pkName = method.getName().substring("get".length());
				pkName = Character.toLowerCase(pkName.charAt(0))
						+ pkName.substring(1);
				field = BeanUtils.getDeclaredField(clazz, pkName);
				break;
			}

		}
		return field;
	}

	/**
	 * 取得实体中定义为@Id的属性名称
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static String getPKName(Class clazz) throws Exception {
		String pkName = null;
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {

			boolean id = method.getAnnotation(javax.persistence.Id.class) != null;
			boolean embeddedId = method
					.getAnnotation(javax.persistence.EmbeddedId.class) != null;
			if (id || embeddedId) {
				pkName = method.getName().substring("get".length());
				pkName = Character.toLowerCase(pkName.charAt(0))
						+ pkName.substring(1);
				// field = BeanUtils.getDeclaredField(clazz, pkName);
				break;
			}

		}
		return pkName;
	}
	
	
}
