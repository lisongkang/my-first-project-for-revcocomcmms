package com.maywide.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 反射的Utils函数集合.
 * 
 * 提供侵犯隐私的直接读取filed的能力.
 */
public class BeanUtils {

	protected static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	private BeanUtils() {
	}

	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 */
	public static Object getFieldValue(Object object, String fieldName)
			throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 */
	public static void setFieldValue(Object object, String fieldName,
			Object value) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 */
	public static Field getDeclaredField(Object object, String fieldName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * 循环向上转型,获取类的DeclaredField.
	 */
	@SuppressWarnings("unchecked")
	public static Field getDeclaredField(Class clazz, String fieldName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + fieldName);
	}

	/**
	 * 循环向上转型,获取类的所有private/protected DeclaredField.
	 */
	public static List<Field> getDeclaredFields(Class clazz) {
		List<Field> fields = new ArrayList<Field>();

		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			Field[] flds = superClass.getDeclaredFields();
			for (int i = 0; i < flds.length; i++) {
				Field field = flds[i];
				// 不取final/static属性
				// if (Modifier.isFinal(field.getModifiers())
				// || Modifier.isStatic(field.getModifiers()))
				// continue;
				//
				// if (Modifier.isPrivate(field.getModifiers())
				// || Modifier.isProtected(field.getModifiers())) {
				// fields.add(field);
				// }
				if ((!Modifier.isFinal(field.getModifiers()))
						&& (!Modifier.isStatic(field.getModifiers()))) {
					fields.add(field);
				}
			}
		}

		return fields;
	}

	/**
	 * 获得对象的超类.
	 */
	public static Class getSuperClass(Class clazz) {
		Class superClass = null;

		for (superClass = clazz; superClass.getSuperclass() != Object.class; superClass = superClass
				.getSuperclass()) {
		}

		return superClass;
	}

	/**
	 * 获得查询参数对应的属性名.
	 */
	public static String getPropertyName(String name) throws Exception {
		if (StringUtils.isEmpty(name.toString()))
			throw new Exception("属性名[ " + name + "]不能为空");

		if (name.startsWith("_")) {
			// name = name.substring(1, name.length());
			int pos = name.lastIndexOf("_");
			return name.substring(pos + 1);
		}
		return name;
	}

	/**
	 * 取得参数的getter
	 */
	public static Method getGetter(Class clazz, Field field)
			throws NoSuchMethodException {
		String fieldName = field.getName();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				String methodName = "get"
						+ Character.toUpperCase(fieldName.charAt(0))
						+ fieldName.substring(1);
				return superClass.getDeclaredMethod(methodName);
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchMethodException("No getter method for field: "
				+ clazz.getName() + '.' + fieldName);
	}

	/**
	 * 取得参数的getter
	 */
	public static Method getSetter(Class clazz, Field field) throws Exception {

		String fieldName = field.getName();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				String methodName = "set"
						+ Character.toUpperCase(fieldName.charAt(0))
						+ fieldName.substring(1);
				return superClass
						.getDeclaredMethod(methodName, field.getType());
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchMethodException("No setter method for field: "
				+ clazz.getName() + '.' + fieldName);
	}

	/**
	 * copy 值到另一个对象，因为apache的那个对于Date型值为null的报错，所以重新写一个简单的好了。
	 */
	public static void copyProperties(Object dest, Object orig)
			throws Exception {
		List<Field> field = getDeclaredFields(orig.getClass());
		for (Field property : field) {
			Object value = getFieldValue(orig, property.getName());
			// 因为要copy的话，名字一样的对应的值的类型必须一样，否则的话就Exception了，所以这里就不用apache的
			setFieldValue(dest, property.getName(), value);
		}
	}

	/**
	 * 检查一个object是否所有属性都为空。
	 */
	public static boolean isNullObject(Object object) throws Exception {
		List<Field> fields = getDeclaredFields(object.getClass());
		for (Field field : fields) {
			field.setAccessible(true);
			Object o = field.get(object);
			if (o == null)
				continue;
			if (o instanceof String) {
				if (StringUtils.isNotEmpty(o.toString()))
					return false;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 不复制超类的字段信息，调用BOSS接口用
	 * 
	 * @param dest
	 * @param orig
	 * @throws Exception
	 */
	public static void copyPropertiesNotSuperClass(Object dest, Object orig)
			throws Exception {
		List<Field> field = getDeclaredFieldsNotSuperClass(orig.getClass());
		for (Field property : field) {
			Object value = getFieldValue(orig, property.getName());
			// 因为要copy的话，名字一样的对应的值的类型必须一样，否则的话就Exception了，所以这里就不用apache的
			setFieldValue(dest, property.getName(), value);
		}
	}

	public static List<Field> getDeclaredFieldsNotSuperClass(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		Class superClass = clazz;
		if (superClass != Object.class) {
			Field[] flds = superClass.getDeclaredFields();
			for (int i = 0; i < flds.length; i++) {
				Field field = flds[i];
				// 不取final/static属性
				if (Modifier.isFinal(field.getModifiers())
						|| Modifier.isStatic(field.getModifiers()))
					continue;
				if (Modifier.isPrivate(field.getModifiers())
						|| Modifier.isProtected(field.getModifiers())) {
					fields.add(field);
				}
			}
		}

		return fields;
	}
}
