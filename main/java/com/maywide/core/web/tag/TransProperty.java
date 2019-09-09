package com.maywide.core.web.tag;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.Assert;

import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtils;

public class TransProperty {
	private static PersistentService persistentService;
	private static Set<String> entitySet = new HashSet();
	private static TransProperty instance;
	
	public static TransProperty getInstance() {
		if (instance == null) {
			persistentService = SpringContextHolder.getBean(PersistentService.class);
			EntityManagerFactory entityManagerFactory = SpringContextHolder.getBean(EntityManagerFactory.class);
			SessionFactory sf = ((HibernateEntityManagerFactory) entityManagerFactory).getSessionFactory();
			
			Map<String, ClassMetadata> map = sf.getAllClassMetadata();
			entitySet.addAll(map.keySet());
			
			instance = new TransProperty();
		}
		
		return instance;
	}
	
	public String transProperty(String className, String propertyName, String conditionProperty, String conditionValue) {
		Assert.notNull(className, "className不能为空");
		Assert.notNull(propertyName, "propertyName不能为空");
		Assert.notNull(conditionProperty, "conditionProperty不能为空");
		Assert.notNull(conditionValue, "conditionValue不能为空");
		String fullName = getFullName(className);
		if (fullName == null) return null;
		
		try {
			Class clazz = Class.forName(fullName);
			Field field = BeanUtils.getDeclaredField(clazz, conditionProperty);
			Object propertyValue = getPropertyValue(field.getType(), conditionValue);
			Object obj = persistentService.findUniqueByProperty(clazz, conditionProperty, propertyValue);
			
			Object propertyObj = BeanUtils.getFieldValue(obj, propertyName);
			
			if (propertyObj != null) return propertyObj.toString();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		return null;
	}
	
	private String getFullName(String className) {
		for (String entityName : entitySet) {
			if (entityName.endsWith("." + className)) {
				return entityName;
			}
		}
		return null;
	}
	
	private Object getPropertyValue(Class clazz, String propertyValue) {
		Object retval = propertyValue;
		if ("short".equals(clazz.getSimpleName().toLowerCase())) {
			retval = Short.valueOf(propertyValue);
		} else if ("int".equals(clazz.getSimpleName().toLowerCase()) || "integer".equals(clazz.getSimpleName().toLowerCase())) {
			retval = Integer.valueOf(propertyValue);
		} else if ("long".equals(clazz.getSimpleName().toLowerCase())) {
			retval = Long.valueOf(propertyValue);
		}
		
		return retval;
	}
}
