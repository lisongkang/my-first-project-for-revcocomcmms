package com.maywide.tool.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Maps;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;
import com.maywide.core.web.json.DateJsonSerializer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 基本CRUD框架代码生成工具类，Maven方式运行工程的pom.xml调用生成代码
 * 配置文件在：generator/entity_list.properties
 * 生成的代码在：generator/codes目录下，其中standalone是一个Entity一个目录，用于偶尔重复生成拷贝某一个Entity相关代码之用，integrate是整合到一起的目录结构
 * 注意：生成的代码都在当前tools工程中，需自行根据需要拷贝所需相关代码到对应的业务工程目录或Package下，然后在业务工程中基于框架代码添加相关业务逻辑代码
 * 
 * 模板文件位置：project-tools\src\main\resources\lab\s2jh\tool\builder\freemarker，可自行根据项目需要调整模板定义格式
 */
public class SourceCodeFrameworkBuilder {

    @SuppressWarnings({ "resource", "rawtypes", "unchecked" })
    public static void main(String[] args) throws Exception {
        Configuration cfg = new Configuration();
        // 设置FreeMarker的模版文件位置
        cfg.setClassForTemplateLoading(SourceCodeFrameworkBuilder.class, "/template/biz");
        cfg.setDefaultEncoding("UTF-8");
        String rootPath = "./generator/";//args[0];

        Set<String> entityNames = new HashSet<String>();

        String entityListFile = rootPath + "entity_list.properties";
        BufferedReader reader = new BufferedReader(new FileReader(entityListFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if (StringUtils.isNotBlank(line) && !line.startsWith("#")) {
                entityNames.add(line);
            }
        }

        String integrateRoot = "." + File.separator + "target" + File.separator + "generated-codes" + File.separator
                + "integrate" + File.separator;
        String standaloneRoot = "." + File.separator + "target" + File.separator + "generated-codes" + File.separator
                + "standalone" + File.separator;
        new File(integrateRoot).mkdirs();
        new File(standaloneRoot).mkdirs();

        for (String entityName : entityNames) {

            String integrateRootPath = integrateRoot;
            String standaloneRootPath = standaloneRoot;

            String rootPackage = StringUtils.substringBetween(entityName, "[", "]");
            String rootPackagePath = StringUtils.replace(rootPackage, ".", File.separator);

            String className = StringUtils.substringAfterLast(entityName, ".");
            String classFullName = StringUtils.replaceEach(entityName, new String[] { "[", "]" },
                    new String[] { "", "" });

            String modelName = StringUtils.substringBetween(entityName, "].", ".entity");
            String modelPath = StringUtils.replace(modelName, ".", "/");
            modelPath = "/" + modelPath;
            String modelPackagePath = StringUtils.replace(modelName, ".", File.separator);
            modelPackagePath = File.separator + modelPackagePath;

            Map<String, Object> root = new HashMap<String, Object>();
            String nameField = propertyToField(StringUtils.uncapitalize(className)).toLowerCase();
            root.put("model_name", modelName);
            root.put("model_path", modelPath);
            root.put("entity_name", className);
            root.put("entity_name_uncapitalize", StringUtils.uncapitalize(className));
            root.put("entity_name_field", nameField);
            root.put("full_entity_name_field", modelPath.replaceAll("/", "-").substring(1, modelPath.length()) + "-"
                    + nameField);
            root.put("root_package", rootPackage + "." + modelName);
            root.put("action_package", rootPackage);
            root.put("table_name", "tbl_TODO_" + className.toUpperCase());
            root.put("base", "${base}");
            Class entityClass = Class.forName(classFullName);
            root.put("id_type", entityClass.getMethod("getId").getReturnType().getSimpleName());
            MetaData classEntityComment = (MetaData) entityClass.getAnnotation(MetaData.class);
            if (classEntityComment != null) {
                root.put("model_title", classEntityComment.value());
            } else {
                root.put("model_title", entityName);
            }
            debug("Entity Data Map=" + root);

            Set<Field> fields = new HashSet<Field>();

            Field[] curfields = entityClass.getDeclaredFields();
            for (Field field : curfields) {
                fields.add(field);
            }

            Class superClass = entityClass.getSuperclass();
            while (superClass != null && !superClass.equals(PersistableEntity.class)) {
                Field[] superfields = superClass.getDeclaredFields();
                for (Field field : superfields) {
                    fields.add(field);
                }
                superClass = superClass.getSuperclass();
            }

            //定义用于OneToOne关联对象的Fetch参数
            Map<String, String> fetchJoinFields = Maps.newHashMap();
            List<EntityCodeField> entityFields = new ArrayList<EntityCodeField>();
            int cnt = 1;
            for (Field field : fields) {
                if ((field.getModifiers() & Modifier.FINAL) != 0 || "id".equals(field.getName())) {
                    continue;
                }
                debug(" - Field=" + field);
                Class fieldType = field.getType();

                EntityCodeField entityCodeField = null;
                if (fieldType.isEnum()) {
                    entityCodeField = new EntityCodeField();
                    entityCodeField.setListFixed(true);
                    entityCodeField.setListWidth(80);
                    entityCodeField.setFieldType(StringUtils.uncapitalize(fieldType.getName()));
                    entityCodeField.setListAlign("center");
                } else if (fieldType == Boolean.class) {
                    entityCodeField = new EntityCodeField();
                    entityCodeField.setListFixed(true);
                    entityCodeField.setListAlign("center");
                } else if (PersistableEntity.class.isAssignableFrom(fieldType)) {
                    entityCodeField = new EntityCodeField();
                    entityCodeField.setFieldType("Entity");

                } else if (Number.class.isAssignableFrom(fieldType)) {
                    entityCodeField = new EntityCodeField();
                    entityCodeField.setListFixed(true);
                    entityCodeField.setListWidth(60);
                    entityCodeField.setListAlign("right");
                } else if (fieldType == String.class) {
                    entityCodeField = new EntityCodeField();

                    //根据Hibernate注解的字符串类型和长度设定是否列表显示
                    Method getMethod = entityClass.getMethod("get" + StringUtils.capitalize(field.getName()));
                    Column fieldColumn = getMethod.getAnnotation(Column.class);
                    if (fieldColumn != null) {
                        int length = fieldColumn.length();
                        if (length > 255) {
                            length = 200;
                            entityCodeField.setList(false);
                        }
                        entityCodeField.setListWidth(length);
                    }
                    Lob fieldLob = getMethod.getAnnotation(Lob.class);
                    if (fieldLob != null) {
                        entityCodeField.setList(false);
                        entityCodeField.setListWidth(200);
                    }
                } else if (fieldType == Date.class) {
                    entityCodeField = new EntityCodeField();
                    entityCodeField.setListFixed(true);

                    //根据Json注解设定合理的列宽
                    entityCodeField.setListWidth(150);
                    Method getMethod = entityClass.getMethod("get" + StringUtils.capitalize(field.getName()));
                    JsonSerialize fieldJsonSerialize = getMethod.getAnnotation(JsonSerialize.class);
                    if (fieldJsonSerialize != null) {
                        if (DateJsonSerializer.class.equals(fieldJsonSerialize.using())) {
                            entityCodeField.setListWidth(90);
                        }
                    }
                    entityCodeField.setListAlign("center");
                }

                if (entityCodeField != null) {
                    if (fieldType.isEnum()) {
                        entityCodeField.setEnumField(true);
                    }
                    if (!"Entity".equals(entityCodeField.getFieldType())) {
                        entityCodeField.setFieldType(fieldType.getSimpleName());
                    }
                    entityCodeField.setFieldName(field.getName());
                    EntityAutoCode entityAutoCode = field.getAnnotation(EntityAutoCode.class);
                    if (entityAutoCode != null) {
                        entityCodeField.setListHidden(entityAutoCode.listHidden());
                        entityCodeField.setEdit(entityAutoCode.edit());
                        entityCodeField.setList(entityAutoCode.listHidden() || entityAutoCode.listShow());
                        entityCodeField.setOrder(entityAutoCode.order());
                    } else {
                        entityCodeField.setTitle(field.getName());
                        entityCodeField.setOrder(cnt++);
                    }

                    MetaData entityMetaData = field.getAnnotation(MetaData.class);
                    if (entityMetaData != null) {
                        entityCodeField.setTitle(entityMetaData.value());
                    }

                    Method getMethod = entityClass.getMethod("get" + StringUtils.capitalize(field.getName()));
                    JsonProperty fieldJsonProperty = getMethod.getAnnotation(JsonProperty.class);
                    if (fieldJsonProperty != null) {
                        entityCodeField.setList(true);
                    }

                    if (entityCodeField.getList() || entityCodeField.getListHidden()) {
                        JoinColumn fieldJoinColumn = getMethod.getAnnotation(JoinColumn.class);
                        if (fieldJoinColumn != null) {
                            if (fieldJoinColumn.nullable() == false) {
                                fetchJoinFields.put(field.getName(), "INNER");
                            } else {
                                fetchJoinFields.put(field.getName(), "LEFT");
                            }
                        }
                    }

                    entityFields.add(entityCodeField);
                }

            }
            Collections.sort(entityFields);
            root.put("entityFields", entityFields);
            if (fetchJoinFields.size() > 0) {
                root.put("fetchJoinFields", fetchJoinFields);
            }

            integrateRootPath = integrateRootPath + rootPackagePath + modelPackagePath;
            //process(cfg.getTemplate("Entity.ftl"), root, integrateRootPath + "\\entity" + File.separator, className + ".java");
            process(cfg.getTemplate("Dao.ftl"), root, integrateRootPath + File.separator + "dao" + File.separator,
                    className + "Dao.java");
            process(cfg.getTemplate("Service.ftl"), root, integrateRootPath + File.separator + "service"
                    + File.separator, className + "Service.java");
            process(cfg.getTemplate("Controller.ftl"), root, integrateRootPath + File.separator + "web"
                    + File.separator + "action" + File.separator, className + "Controller.java");
            process(cfg.getTemplate("JSP_Index.ftl"), root,
                    integrateRootPath + File.separator + "jsp" + File.separator, nameField + "-index.jsp");
            process(cfg.getTemplate("JS_Index.ftl"), root, integrateRootPath + File.separator + "jsp" + File.separator,
                    nameField + "-index.js");
            process(cfg.getTemplate("JSP_Input_Tabs.ftl"), root, integrateRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-inputTabs.jsp");
            process(cfg.getTemplate("JSP_Input_Basic.ftl"), root, integrateRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-inputBasic.jsp");
            process(cfg.getTemplate("JS_Input_Basic.ftl"), root, integrateRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-inputBasic.js");
            process(cfg.getTemplate("JSP_View_Tabs.ftl"), root, integrateRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-viewTabs.jsp");
            process(cfg.getTemplate("JSP_View_Basic.ftl"), root, integrateRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-viewBasic.jsp");

            standaloneRootPath = standaloneRootPath + rootPackagePath + modelPackagePath + File.separator + className;
            //process(cfg.getTemplate("Entity.ftl"), root, standaloneRootPath + "\\entity" + File.separator, className + ".java");
            process(cfg.getTemplate("Dao.ftl"), root, standaloneRootPath + File.separator + "dao" + File.separator,
                    className + "Dao.java");
            process(cfg.getTemplate("Service.ftl"), root, standaloneRootPath + File.separator + "service"
                    + File.separator, className + "Service.java");
            process(cfg.getTemplate("Controller.ftl"), root, standaloneRootPath + File.separator + "web"
                    + File.separator + "action" + File.separator, className + "Controller.java");
            process(cfg.getTemplate("JSP_Index.ftl"), root, standaloneRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-index.jsp");
            process(cfg.getTemplate("JS_Index.ftl"), root,
                    standaloneRootPath + File.separator + "jsp" + File.separator, nameField + "-index.js");
            process(cfg.getTemplate("JSP_Input_Tabs.ftl"), root, standaloneRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-inputTabs.jsp");
            process(cfg.getTemplate("JSP_Input_Basic.ftl"), root, standaloneRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-inputBasic.jsp");
            process(cfg.getTemplate("JS_Input_Basic.ftl"), root, standaloneRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-inputBasic.js");
            process(cfg.getTemplate("JSP_View_Tabs.ftl"), root, standaloneRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-viewTabs.jsp");
            process(cfg.getTemplate("JSP_View_Basic.ftl"), root, standaloneRootPath + File.separator + "jsp"
                    + File.separator, nameField + "-viewBasic.jsp");
        }
    }

    private static void debug(String message) {
        System.out.println(message);
    }

    private static void process(Template template, Map<String, Object> root, String dir, String fileName)
            throws Exception, FileNotFoundException {
        if ((dir + fileName).length() > 300) {
            throw new IllegalArgumentException("Dir path too long.");
        }
        File newsDir = new File(dir);
        if (!newsDir.exists()) {
            newsDir.mkdirs();
        }
        debug("Write to file: " + dir + fileName);
        Writer out = new OutputStreamWriter(new FileOutputStream(dir + fileName), "UTF-8");
        template.process(root, out);
    }

    /**
     * 对象属性转换为字段 例如：userName to user_name
     * 
     * @param property
     *            字段名
     * @return
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("-" + StringUtils.lowerCase(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString().toUpperCase();
    }

    public static class DemoEntity {

        private TestEnum testEnum;

        public enum TestEnum {
            Abc,
            Xyz
        }

        public TestEnum getTestEnum() {
            return testEnum;
        }

        public void setTestEnum(TestEnum testEnum) {
            this.testEnum = testEnum;
        }
    }

}
