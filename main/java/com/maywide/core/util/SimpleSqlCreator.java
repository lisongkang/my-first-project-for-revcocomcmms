package com.maywide.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.maywide.core.exception.BusinessException;

/**
 * <p>
 * 构造单表查询的简单sql语句
 * <p>
 * Create at 2015年8月13日
 * 
 * @author pengjianqiang
 */
public class SimpleSqlCreator {
    /**
     * 根据实体类的@Table注解获取其对应表名
     * 
     * @param entityClass
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String getEntityTableName(Class entityClass) throws Exception {
        String exceptionMsg = entityClass.getSimpleName() + "类中";
        if (null == entityClass.getAnnotation(Entity.class)) {
            throw new BusinessException(exceptionMsg + "没有javax.persistence.Entity注解，不是实体类");
        }
        Table tableAnno = (Table) entityClass.getAnnotation(Table.class);
        if (null == tableAnno || "".equals(tableAnno.name())) {
            throw new BusinessException(exceptionMsg + "没有javax.persistence.Table注解或注解没有name值，需要指定对应表");
        }
        return tableAnno.name();
    }

    /**
     * entity实体类中属性名与数据库中字段名不一致时，用于代替select *（例如属性名为id，表中字段名为recid）<br/>
     * 适用场合：<br/>
     * 1.实体类用Entity注解;<br/>
     * 2.属性的get方法中添加@Column注解;<br/>
     * 3.且get方法与属性名匹配，即属性名为myName，则方法名为getMyName;<br/>
     * 4.数据库使用oracle（字段名一般为全大写）
     * 
     * @param entityClass
     * @return 返回构造好的sql语句(表的别名为o)
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static StringBuffer createSelectAllFieldSql(Class entityClass) throws Exception {
        String exceptionMsg = entityClass.getSimpleName() + "类中";
        String fieldName = null;
        String methodName = null;
        try {
            if (null == entityClass.getAnnotation(Entity.class)) {
                throw new BusinessException(exceptionMsg + "没有javax.persistence.Entity注解，不是实体类");
            }
            Table tableAnno = (Table) entityClass.getAnnotation(Table.class);
            if (null == tableAnno || "".equals(tableAnno.name())) {
                throw new BusinessException(exceptionMsg + "没有javax.persistence.Table注解或注解没有name值，需要指定对应表");
            }

            StringBuffer sql = new StringBuffer("SELECT ");
            Field[] fields = entityClass.getDeclaredFields();
            for (int i = 0, size = fields.length; i < size; i++) {
                Field field = fields[i];
                if (field.getModifiers() == 2) {
                    // 只处理private属性
                    fieldName = field.getName();
                    String nameInDB = null;

                    methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); // 以属性名构造get方法名
                    Method getMethod = entityClass.getDeclaredMethod(methodName);
                    if (getMethod.isAnnotationPresent(Column.class)) {
                        nameInDB = getMethod.getAnnotation(Column.class).name();
                        if ("".equals(nameInDB)) {
                            // @Column注解没有配置name值则使用属性名作为表字段名
                            nameInDB = new String(fieldName).toUpperCase();
                        }
                    } else {
                        if (!getMethod.isAnnotationPresent(Transient.class)) {
                            // 没有@Column和@Transient注解则直接使用属性名作为表字段名
                            nameInDB = new String(fieldName).toUpperCase();
                        }
                    }
                    if (null != nameInDB) {
                        sql.append("o.").append(nameInDB).append(" AS ").append(fieldName).append(",");
                    }
                }
            }

            sql.replace(sql.lastIndexOf(","), sql.length(), ""); // 先去掉最后的逗号
            sql.append(" FROM ").append(tableAnno.name()).append(" o ");
            return sql;
        } catch (NoSuchMethodException ex) {
            throw new BusinessException(exceptionMsg + "没有与" + fieldName + "属性对应的" + methodName + "方法");
        }
    }
}