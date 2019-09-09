package com.maywide.core.dao.support;

import com.maywide.core.util.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.PropertyAccessException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

public class EscColumnToBean implements ResultTransformer {
	private static final long serialVersionUID = 1L;
	private final Class resultClass;
	private Setter[] setters;
	private PropertyAccessor propertyAccessor;

	public EscColumnToBean(Class resultClass) {
		if (resultClass == null)
			throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClass = resultClass;
		this.propertyAccessor = new ChainedPropertyAccessor(
				new PropertyAccessor[] {
						PropertyAccessorFactory.getPropertyAccessor(
								resultClass, null),
						PropertyAccessorFactory.getPropertyAccessor("field") });
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		try {
			if (this.setters == null) {
				this.setters = new Setter[aliases.length];
				for (int i = 0; i < aliases.length; ++i) {
					String alias = aliases[i];
					if (alias == null) {
						continue;
					}
					this.setters[i] = getSetterByColumnName(alias);
				}
			}

			result = this.resultClass.newInstance();

			for (int i = 0; i < aliases.length; ++i) {
				if (this.setters[i] != null) {
					Object value = tuple[i];
					if ((value instanceof BigDecimal)
							|| (value instanceof BigInteger)
							|| (value instanceof Integer)) {
						// 允许BigDecimal、BigInteger向其它类型转换、允许低级别类型转到更高级别
						// Integer->Long->Float->Double->String
						Method m = this.setters[i].getMethod();
						if ("Long".equalsIgnoreCase(m.getParameterTypes()[0]
								.getSimpleName())) {
							value = Long.valueOf(Long.parseLong(value
									.toString()));
						} else if ("Float".equalsIgnoreCase(m
								.getParameterTypes()[0].getSimpleName())) {
							value = Float.valueOf(Float.parseFloat(value
									.toString()));
						} else if ("Double".equalsIgnoreCase(m
								.getParameterTypes()[0].getSimpleName())) {
						    value = Double.valueOf(Double.parseDouble(value
                                    .toString()));
						} else if ("String".equalsIgnoreCase(m
								.getParameterTypes()[0].getSimpleName())) {
							value = value.toString();
						}

					} else if (value instanceof Long) {
						Method m = this.setters[i].getMethod();
						if ("Float".equalsIgnoreCase(m.getParameterTypes()[0]
								.getSimpleName())) {
							value = Float.valueOf(Float.parseFloat(value
									.toString()));
						} else if ("Double".equalsIgnoreCase(m
								.getParameterTypes()[0].getSimpleName())) {
						    value = Double.valueOf(Double.parseDouble(value
                                    .toString()));
						} else if ("String".equalsIgnoreCase(m
								.getParameterTypes()[0].getSimpleName())) {
							value = value.toString();
						}
					} else if (value instanceof Float) {
						Method m = this.setters[i].getMethod();
						if ("Double".equalsIgnoreCase(m.getParameterTypes()[0]
								.getSimpleName())) {
						    value = Double.valueOf(Double.parseDouble(value
                                    .toString()));
						} else if ("String".equalsIgnoreCase(m
								.getParameterTypes()[0].getSimpleName())) {
							value = value.toString();
						}
					} else if (value instanceof Double) {
						Method m = this.setters[i].getMethod();
						if ("String".equalsIgnoreCase(m.getParameterTypes()[0]
								.getSimpleName())) {
							value = value.toString();
						}
					}

					try {
						this.setters[i].set(result, value, null);
					} catch (PropertyAccessException e) {
						this.setters[i].set(result, (value == null) ? ""
								: value.toString(), null);
					} catch (IllegalArgumentException e) {
						this.setters[i].set(result, (value == null) ? ""
								: value.toString(), null);
					}
				}
			}
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: "
					+ this.resultClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: "
					+ this.resultClass.getName());
		}

		return result;
	}

	private Setter getSetterByColumnName(String alias) {
		List<Field> fields = BeanUtils.getDeclaredFields(this.resultClass);
		if ((fields == null) || (fields.size() == 0)) {
			throw new RuntimeException("实体" + this.resultClass.getName()
					+ "不含任何属性");
		}

		String proName = alias.replaceAll("_", "").toLowerCase();
		for (Field field : fields) {
			if (field.getName().toLowerCase().equals(proName)) {
				return this.propertyAccessor.getSetter(this.resultClass,
						field.getName());
			}
		}
		return null;
	}

	public List transformList(List collection) {
		return collection;
	}
}
