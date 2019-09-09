package com.maywide.core.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.hibernate.envers.RevisionType;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.audit.envers.EntityRevision;
import com.maywide.core.audit.envers.ExtDefaultRevisionEntity;
import com.maywide.core.audit.envers.ExtRevisionListener;
import com.maywide.core.entity.BaseEntity;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.def.OperationAuditable;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.util.DateUtils;
import com.maywide.core.util.ExtStringUtils;
import com.maywide.core.web.json.DateJsonSerializer;
import com.maywide.core.web.json.DateTimeJsonSerializer;
import com.maywide.core.web.view.OperationResult;
import com.opensymphony.xwork2.Preparable;

public abstract class PersistableController<T extends PersistableEntity<ID>, ID extends Serializable> extends
        SimpleController implements Preparable {

    private final Logger logger = LoggerFactory.getLogger(PersistableController.class);

    /** Autocomplete组件传递的查询参数的名称 */
    protected static final String PARAM_NAME_FOR_AUTOCOMPLETE = "term";

    /** 请求URL可提供此参数指定转向特定JSP页面，如有相同处理方法返回相同数据，但是不同业务功能需要按照不同页面显示则可以指定此参数转向特定显示JSP页面*/
    protected static final String PARAM_NAME_FOR_FORWARD_TO = "_to_";

    /** 分页查询方法特定的数据处理格式标识参数，默认标识返回查询JSON数据，可指定如xls标识导出对应的（不分页）查询数据 */
    protected static final String PARAM_NAME_FOR_EXPORT_FORMAT = "_format_";

    /** 子类指定泛型对应的实体Service接口对象 */
    abstract protected BaseService<T, ID> getEntityService();

    /** 泛型对应的Class定义 */
    protected Class<T> entityClass;

    /** 泛型对应的Class定义 */
    protected Class<ID> entityIdClass;

    /** 用于数据绑定的Entity对象实例 */
    protected T bindingEntity;

    /** 用于批量操作的数据绑定的Entity对象实例集合 */
    protected Collection<T> bindingEntities;

    public String getActionName() {

        //TODO 后期考虑优化为直接注入Struts的ActionNameBuilder方式
        String actionName = this.getClass().getSimpleName();
        String actionSuffix = "Controller";
        if (actionName.equals(actionSuffix))
            throw new IllegalStateException("The action name cannot be the same as the action suffix [" + actionSuffix
                    + "]");

        // Truncate Action suffix if found
        if (actionName.endsWith(actionSuffix)) {
            actionName = actionName.substring(0, actionName.length() - actionSuffix.length());
        }

        // Convert to underscores
        char[] ca = actionName.toCharArray();
        StringBuilder build = new StringBuilder("" + ca[0]);
        boolean lower = true;
        for (int i = 1; i < ca.length; i++) {
            char c = ca[i];
            if (Character.isUpperCase(c) && lower) {
                build.append("-");
                lower = false;
            } else if (!Character.isUpperCase(c)) {
                lower = true;
            }

            build.append(c);
        }

        actionName = build.toString();
        actionName = actionName.toLowerCase();

        return actionName;
    }

    /** 除ModelDriven返回的model对象以外，额外的控制参数Map结构数据，如根据业务逻辑控制页面按钮的disabled状态等 */
    private Map<String, Object> controlAttributes = new HashMap<String, Object>();

    public Map<String, Object> getControlAttributes() {
        return controlAttributes;
    }

    protected void addControlAttribute(String key, Object value) {
        controlAttributes.put(key, value);
    }

    /**
     * 初始化构造方法，计算相关泛型对象
     */
    @SuppressWarnings("unchecked")
    public PersistableController() {
        super();
        // 通过反射取得Entity的Class.
        try {
            Object genericClz = getClass().getGenericSuperclass();
            if (genericClz instanceof ParameterizedType) {
                entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
                entityIdClass = (Class<ID>) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[1];
            }
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
    }

    // ----------------------------------  
    // -----------相关接口回调方法------------
    // ----------------------------------
    /**
     * 通用的Preparable接口prepare回调方法，如果出现id参数，则提前准备binding绑定对象用于后续方法使用
     * 同时修改了Struts2默认的PrepareInterceptor实现方式：先执行prepare再执行相关的prepareXXX方法
     * @see com.maywide.core.web.interceptor.ExtPrepareInterceptor
     */
    @Override
    public void prepare() {
        ID id = getId("id");
        HttpServletRequest request = this.getRequest();
        if (id != null) {
            //如果是以POST方式请求数据，则获取Detach状态的对象，其他则保留Session方式以便获取Lazy属性
            if (request.getMethod().equalsIgnoreCase("POST")) {
                setupDetachedBindingEntity(getId());
            } else {
                bindingEntity = getEntityService().findOne(getId());
            }
            if (bindingEntity != null) {
                checkEntityAclPermission(bindingEntity);
            }
        } else {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                newBindingEntity();
            }
        }
    }

    protected void setupDetachedBindingEntity(ID id) {
        bindingEntity = getEntityService().findDetachedOne(id);
    }

    /**
     * 判断当前实体对象是否已持久化对象
     * 一般用于前端页面OGNL语法计算
     * @return
     */
    public boolean isPersistentedModel() {
        if (bindingEntity != null && bindingEntity.getId() != null
                && String.valueOf(bindingEntity.getId()).trim().length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据当前登录用户对数据对象进行访问控制权限检查
     * 为了严格控制用户非法的数据访问，设计为abstract强制要求子类提供实现定义
     * @param entity 待update可操作性检查对象
     * @exception 如果检查没有权限，则直接抛出运行异常即可
     */
    protected void checkEntityAclPermission(T entity) {}

    /**
     * ModelDriven接口回调实现方法
     */
    @Override
    public Object getModel() {
        if (model == null) {
            model = bindingEntity;
        }
        return model;
    }

    /**
     * 将id=123格式的字符串id参数转换为ID泛型对应的主键变量实例
     * 另外，页面也会以Struts标签获取显示当前操作对象的ID值
     * @return ID泛型对象实例
     */
    public ID getId() {
        return getId("id");
    }

    /**
     * 将指定参数转换为ID泛型对应的主键变量实例
     * 另外，页面也会以Struts标签获取显示当前操作对象的ID值
     * @return ID泛型对象实例
     */
    @SuppressWarnings("unchecked")
    public ID getId(String paramName) {
        String entityId = this.getParameter(paramName);
        //jqGrid inline edit新增数据传入id=负数标识 
        if (StringUtils.isBlank(entityId) || entityId.startsWith("-")) {
            return null;
        }
        if (String.class.isAssignableFrom(entityIdClass)) {
            return (ID) entityId;
        } else if (Long.class.isAssignableFrom(entityIdClass)) {
            return (ID) (Long.valueOf(entityId));
        }
        else if (Integer.class.isAssignableFrom(entityIdClass)) {
            return (ID) (Integer.valueOf(entityId));
        }else {
            throw new IllegalStateException("Undefine entity ID class: " + entityIdClass);
        }
    }

    // -------------------------------------
    // -----------通用的页面转向处理方法------------
    // -------------------------------------
    /**
     * 用于创建或更新时转向通用的Tabs页面
     * 一般创建是Tabs页面只有第一个Tab可编辑，其余为Disabled的状态
     * @return
     */
    public HttpHeaders inputTabs() {
        return buildDefaultHttpHeaders("inputTabs");
    }

    // -------------------------------------- 
    // -----------View查看处理相关逻辑------------
    // -------------------------------------
    /**
     * 显示查看Tabs页面
     * @return
     */
    public HttpHeaders viewTabs() {
        return buildDefaultHttpHeaders("viewTabs");
    }

    /**
     * 通用的prepare接口方法中已经实现根据ID准备好相关的binding对象
     * 如果子类需要根据其他如code代码等属性查看数据,则根据业务逻辑覆写此方法即可
     */
    public void prepareView() {

    }

    /**
     * 查看对象显示页面
     * @return
     */
    public HttpHeaders view() {
        return buildDefaultHttpHeaders("viewBasic");
    }

    // --------------------------------------  
    // -----------Create创建处理相关逻辑------------
    // --------------------------------------
    /**
     * 显示创建页面之前准备new实体对象
     */
    public void prepareCreate() {
        newBindingEntity();
    }

    /**
     * 转向创建对象录入页面
     * @return
     */
    public HttpHeaders create() {
        return buildDefaultHttpHeaders("inputBasic");
    }

    protected void newBindingEntity() {
        try {
            bindingEntity = entityClass.newInstance();
        } catch (InstantiationException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void prepareEdit() {
        String id = this.getParameter("id");
        if (StringUtils.isBlank(id)) {
            newBindingEntity();
        }
    }

    /**
     * 编辑对象显示页面，在通用的prepare接口方法中已经准备好相关的binding对象
     * 如果对象不需要区分create和update权限控制，则可以用此宽泛的编辑显示逻辑
     * @return
     */
    public HttpHeaders edit() {
        return buildDefaultHttpHeaders("inputBasic");
    }

    /**
     * 检查当前对象是否禁止create创建操作
     * 默认子类已经强制实现 @see #checkEntityAclPermission 方法
     * 则子类只需根据业务需要添加checkEntityAclPermission之外的create操作检查权限逻辑
     * 一般子类其他业务方法需要create对象，则需要根据情况调用此方法进行create检查
     * 此方法除了在prepareDoCreate方法中调用进行更新检查用途外,还可以用于前端页面以OGNL方式控制"创建"操作按钮的disabled状态
     * @param entity 待create可操作性检查对象
     * @return 返回不允许操作错误提示消息，如果为空则表示允许操作
     */
    public String isDisallowCreate() {
        return null;
    }

    /**
     * doCreate调用之前的Preparable接口自动回调方法
     * 准备new实体对象以备ParametersInterceptor进行参数绑定
     */
    public void prepareDoCreate() {
        String msg = isDisallowCreate();
        Assert.isNull(msg, msg);
        newBindingEntity();
    }

    /**
     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
     * @return JSON操作提示
     */
    @MetaData(value = "创建")
    protected HttpHeaders doCreate() {
        //检查提交的数据参数符合用户ACL权限，否则拒绝创建数据
        checkEntityAclPermission(bindingEntity);
        ExtRevisionListener.setOperationEvent(RevisionType.ADD.name());
        getEntityService().save(bindingEntity);
        setModel(OperationResult.buildSuccessResult("创建操作成功", bindingEntity));
        return buildDefaultHttpHeaders();
    }

    // ---------------------------------------  
    // -----------Update更新处理相关逻辑------------
    // ---------------------------------------
    /**
     * 更新对象显示页面，在通用的prepare接口方法中已经准备好相关的binding对象
     * 
     * @return
     */
    public HttpHeaders update() {
        return buildDefaultHttpHeaders("inputBasic");
    }

    /**
     * 检查当前对象是否禁止update更新操作
     * 默认子类已经强制实现 @see #checkEntityAclPermission 方法
     * 则子类只需根据业务需要添加checkEntityAclPermission之外的update操作检查权限逻辑
     * 一般子类其他业务方法需要更新对象，则需要根据情况调用此方法进行update更新检查
     * 此方法除了在prepareDoUpdate方法中调用进行更新检查用途外,还可以用于前端页面以OGNL方式控制"更新"操作按钮的disabled状态
     * @param entity 待update可操作性检查对象
     * @return 返回不允许操作错误提示消息，如果为空则表示允许操作
     */
    public String isDisallowUpdate() {
        return null;
    }

    /**
     * doUpdate调用之前的Preparable接口自动回调方法
     * 基类默认调用checkEntityUpdatePermission进行对象访问权限检查
     * 注意：之所以对象访问检查逻辑要放到prepare而不是对应的doXXX方法，是因为数据权限是基于数据库对象进行判断的
     * 在prepare中bindingEntity是刚从数据库取到的数据，还没有进行相关参数绑定
     * 而到了doXXX方法时的对象已经是完成了参数绑定的对象，通过此时用于权限判断的对象进行判断则有可能存在是被用户篡改权限属性值的风险
     * 同理：子类在其他业务方法处理时，如果也需要进行额外的数据检查时，也要注意此规则应该在对应的prepare回调方法中进行，而不是业务执行方法中
     */
    public void prepareDoUpdate() {
        String msg = isDisallowUpdate();
        Assert.isNull(msg, msg);
    }

    /**
     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
     * @return JSON操作提示
     */
    @MetaData(value = "更新")
    protected HttpHeaders doUpdate() {
        getEntityService().save(bindingEntity);
        setModel(OperationResult.buildSuccessResult("更新操作成功", bindingEntity));
        return buildDefaultHttpHeaders();
    }

    public void prepareDoSave() {
        ID id = getId("id");
        if (id == null) {
            newBindingEntity();
            String msg = isDisallowCreate();
            Assert.isNull(msg, msg);
        } else {
            String msg = isDisallowUpdate();
            Assert.isNull(msg, msg);
        }
    }

    /**
     * 通用的数据提交保存处理方法，包括创建和更新，对于大部分不区分二者权限控制的业务可简化使用此方法
     * 如果业务上需要分开控制创建和编辑权限，则分别请求doCreate和doUpdate，分别配置URL权限
     * @return
     */
    @MetaData(value = "保存")
    protected HttpHeaders doSave() {
        getEntityService().save(bindingEntity);
        setModel(OperationResult.buildSuccessResult("数据保存成功", bindingEntity));
        return buildDefaultHttpHeaders();
    }

    // --------------------------------------------- 
    // -----------Delete删除数据处理相关逻辑------------
    // ----------------------------------------------
    /**
     * 将ids=123,234,345等格式参数按照逗号切分并转换查询对应的Entity对象集合，方便使用
     * 一般用于如删除等批量操作
     * @return 实体对象集合
     */
    @SuppressWarnings("unchecked")
    protected Collection<T> getEntitiesByParameterIds() {
        Collection<T> entities = new ArrayList<T>();
        for (String id : getParameterIds()) {
            Object realId = null;
            if (String.class.isAssignableFrom(entityIdClass)) {
                realId = id;
            } else if (Long.class.isAssignableFrom(entityIdClass)) {
                realId = Long.valueOf(id);
            }
            else if (Integer.class.isAssignableFrom(entityIdClass)) {
                realId = Integer.valueOf(id);
            }else {
                throw new IllegalStateException("Undefine entity ID class: " + entityIdClass);
            }
            T entity = getEntityService().findOne((ID) realId);
            entities.add(entity);
        }
        return entities;
    }

    /**
     * 检查当前对象是否禁止delete更新操作
     * 默认子类已经强制实现 @see #checkEntityAclPermission 方法
     * 则子类只需根据业务需要添加checkEntityAclPermission之外的delete操作检查权限逻辑
     * 一般子类其他业务方法需要删除对象，则需要根据情况调用此方法进行delete检查
     * 此方法除了在delete方法中调用进行更新检查用途外,还可以用于前端页面以OGNL方式控制"删除"操作按钮的disabled状态
     * @param entity 待delete可操作性检查对象
     * @return 返回不允许删除错误提示消息，如果为空则表示可以删除
     */
    protected String isDisallowDelete(T entity) {
        if (entity.isNew()) {
            return "未保存数据";
        }
        return null;
    }

    /**
     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
     * @return JSON操作提示
     */
    @MetaData(value = "删除")
    protected HttpHeaders doDelete() {
        //删除失败的id和对应消息以Map结构返回，可用于前端批量显示错误提示和计算表格组件更新删除行项
        Map<ID, String> errorMessageMap = Maps.newLinkedHashMap();

        Set<T> enableDeleteEntities = Sets.newHashSet();
        Collection<T> entities = this.getEntitiesByParameterIds();
        for (T entity : entities) {
            //基础ACL访问权限检查
            checkEntityAclPermission(entity);
            //添加检查逻辑：当前对象是否允许被删除，如状态检查
            String msg = isDisallowDelete(entity);
            if (StringUtils.isBlank(msg)) {
                enableDeleteEntities.add(entity);
            } else {
                errorMessageMap.put(entity.getId(), msg);
            }
        }
        //对于批量删除,循环每个对象调用Service接口删除,则各对象删除事务分离
        //这样可以方便某些对象删除失败不影响其他对象删除
        //如果业务逻辑需要确保批量对象删除在同一个事务则请子类覆写调用Service的批量删除接口
        for (T entity : enableDeleteEntities) {
            try {
                getEntityService().delete(entity);
            } catch (Exception e) {
                logger.warn("entity delete failure", e);
                errorMessageMap.put(entity.getId(), e.getMessage());
            }
        }
        int rejectSize = errorMessageMap.size();
        if (rejectSize == 0) {
            setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + entities.size() + "条"));
        } else {
            if (rejectSize == entities.size()) {
                setModel(OperationResult.buildFailureResult("所有选取记录删除操作失败", errorMessageMap));
            } else {
                setModel(OperationResult.buildWarningResult("删除操作已处理. 成功:" + (entities.size() - rejectSize) + "条"
                        + ",失败:" + rejectSize + "条", errorMessageMap));
            }
        }
        return buildDefaultHttpHeaders();
    }

    /**
     * 供子类调用的批量数据处理回调方法
     * @param op 操作方法名称，如“取消”
     * @param entityCallback 回调匿名接口
     * @return
     */
    protected HttpHeaders processBatchEntities(String op, EntityProcessCallbackHandler<T> entityCallback) {
        //删除失败的id和对应消息以Map结构返回，可用于前端批量显示错误提示和计算表格组件更新删除行项
        Map<ID, String> errorMessageMap = Maps.newLinkedHashMap();

        Collection<T> entities = this.getEntitiesByParameterIds();
        for (T entity : entities) {
            try {
                entityCallback.processEntity(entity);
            } catch (Exception e) {
                logger.warn("entity batch operation failure", e);
                errorMessageMap.put(entity.getId(), e.getMessage());
            }
        }

        int rejectSize = errorMessageMap.size();
        if (rejectSize == 0) {
            setModel(OperationResult.buildSuccessResult("成功" + op + "所选选取记录:" + entities.size() + "条"));
        } else {
            if (rejectSize == entities.size()) {
                setModel(OperationResult.buildFailureResult("所有选取记录" + op + "操作失败", errorMessageMap));
            } else {
                setModel(OperationResult.buildWarningResult(op + "操作已处理. 成功:" + (entities.size() - rejectSize) + "条"
                        + ",失败:" + rejectSize + "条", errorMessageMap));
            }
        }
        return buildDefaultHttpHeaders();
    }

    // --------------------------------------------- 
    // -----------findByPage分页查询处理相关逻辑------------
    // ----------------------------------------------
    /**
     * 分页列表显示数据
     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
     * @return JSON集合数据
     */
    @MetaData(value = "查询")
    protected HttpHeaders findByPage() {
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
        appendFilterProperty(groupFilter);
        String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
        if ("xls".equalsIgnoreCase(foramt)) {
            exportXlsForGrid(groupFilter, pageable.getSort());
        } else {
            //Page l = this.getEntityService().findByPage(groupFilter, pageable);
            setModel(this.getEntityService().findByPage(groupFilter, pageable));
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData(value = "下拉框选项数据")
    protected HttpHeaders selectOptions() {
        Sort sort = PropertyFilter.buildSortFromHttpRequest(getRequest());
        GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
        appendFilterProperty(groupFilter);
        setModel(this.getEntityService().findByFilters(groupFilter, sort));
        return new DefaultHttpHeaders();
    }

    /**
     * 子类额外追加过滤限制条件的入口方法，一般基于当前登录用户强制追加过滤条件
     * 注意：凡是基于当前登录用户进行的控制参数，一定不要通过页面请求参数方式传递，存在用户篡改请求数据访问非法数据的风险
     * 因此一定要在Controller层面通过覆写此回调函数或自己的业务方法中强制追加过滤条件
     * @param filters 已基于Request组装好查询条件的集合对象
     */
    protected void appendFilterProperty(GroupPropertyFilter groupPropertyFilter) {

    }

    /**
     * 一般用于把没有分页的集合数据转换组装为对应的Page对象，传递到前端Grid组件以统一的JSON结构数据显示
     * @param list 泛型集合数据
     * @return 转换封装的Page分页结构对象
     */
    protected <S> Page<S> buildPageResultFromList(List<S> list) {
        Page<S> page = new PageImpl<S>(list);
        return page;
    }

    /**
     * 如果业务功能支持对分页查询导出Excel组件，子类覆写此方法
     * 基类的findByPage会根据{@link #PARAM_NAME_FOR_EXPORT_FORMAT} 自动回调此方法进行Excel数据导出
     * @param filters 已基于Request组装好查询条件的集合对象
     * @param sort 已基于Request组装好的排序对象
     * @param groupFilter 已基于Request组装好高级查询条件的集合对象
     */
    protected void exportXlsForGrid(GroupPropertyFilter groupFilter, Sort sort) {
        throw new UnsupportedOperationException();
    }

    /**
     * 基类基于子类提供的相关参数数据, 生成JXLS报表
     * @see #exportXlsForGrid(List, Sort, GroupPropertyFilter) 此方法中基于参数组装好相关的data数据后，调用此方法生成Excel响应
     * @param dataMap
     */
    protected void exportExcel(String templateFileName, String exportFileName, Map<String, Object> dataMap) {
        //日期格式定义
        dataMap.put("dateFormatter", new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT));
        dataMap.put("timeFormatter", new SimpleDateFormat(DateUtils.DEFAULT_TIME_FORMAT));

        HttpServletResponse response = ServletActionContext.getResponse();
        InputStream fis = null;
        OutputStream fos = null;
        try {
            Resource resource = new ClassPathResource("/template/xls/" + templateFileName);
            logger.debug("Open template file inputstream: {}", resource.getURL());
            fis = resource.getInputStream();

            XLSTransformer transformer = new XLSTransformer();
            // generate the excel workbook according to the template and
            // parameters
            Workbook workbook = transformer.transformXLS(fis, dataMap);
            String filename = exportFileName;
            filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            fos = response.getOutputStream();
            // output the generated excel file
            workbook.write(fos);
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * 字段值重复性校验
     * 唯一性验证URL示例：id=1&element=masterId&masterId=ABC&additional=referenceId
     * &referenceId=XYZ 处理额外补充参数，有些数据是通过两个字段共同决定唯一性，可以通过additional参数补充提供
     */
    public HttpHeaders checkUnique() {
        String element = this.getParameter("element");
        Assert.notNull(element);
        GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();

        String value = getRequest().getParameter(element);
        if (!ExtStringUtils.hasChinese(value)) {
            value = ExtStringUtils.encodeUTF8(value);
        }

        groupPropertyFilter.append(new PropertyFilter(entityClass, "EQ_" + element, value));

        // 处理额外补充参数，有些数据是通过两个字段共同决定唯一性，可以通过additional参数补充提供
        String additionalName = getRequest().getParameter("additional");
        if (StringUtils.isNotBlank(additionalName)) {
            String additionalValue = getRequest().getParameter(additionalName);
            if (!ExtStringUtils.hasChinese(additionalValue)) {
                additionalValue = ExtStringUtils.encodeUTF8(additionalValue);
            }
            groupPropertyFilter.append(new PropertyFilter(entityClass, additionalName, additionalValue));
        }
        String additionalName2 = getRequest().getParameter("additional2");
        if (StringUtils.isNotBlank(additionalName2)) {
            String additionalValue2 = getRequest().getParameter(additionalName2);
            if (!ExtStringUtils.hasChinese(additionalValue2)) {
                additionalValue2 = ExtStringUtils.encodeUTF8(additionalValue2);
            }
            groupPropertyFilter.append(new PropertyFilter(entityClass, additionalName2, additionalValue2));
        }

        List<T> entities = getEntityService().findByFilters(groupPropertyFilter);
        if (entities == null || entities.size() == 0) {// 未查到重复数据
            this.setModel(Boolean.TRUE);
        } else {
            if (entities.size() == 1) {// 查询到一条重复数据
                String id = getRequest().getParameter("id");
                if (StringUtils.isNotBlank(id)) {
                    Serializable entityId = entities.get(0).getId();
                    logger.debug("Check Unique Entity ID = {}", entityId);
                    if (id.equals(entityId.toString())) {// 查询到数据是当前更新数据，不算已存在
                        this.setModel(Boolean.TRUE);
                    } else {// 查询到数据不是当前更新数据，算已存在
                        this.setModel(Boolean.FALSE);
                    }
                } else {// 没有提供Sid主键，说明是创建记录，则算已存在
                    this.setModel(Boolean.FALSE);
                }
            } else {// 查询到多余一条重复数据，说明数据库数据本身有问题
                this.setModel(Boolean.FALSE);
                throw new WebException("error.check.unique.duplicate: " + element + "=" + value);
            }
        }
        return buildDefaultHttpHeaders();
    }

    // ----------------------------------------------------------------------------------------
    // -----------基于Hibernate Envers的数据修改记录审计功能------------
    // -----------------------------------------------------------------------------------------
    /**
     * 版本数据主界面页面转向
     * @return 在struts.xml中全局的revisionIndex Result定义
     */
    public HttpHeaders revisionIndex() {
        return buildDefaultHttpHeaders("revisionIndex");
    }

    /**
     * 用于版本属性下拉列表集合
     * @return
     */
    public Map<Field, String> getRevisionFields() {
        Map<Field, String> revisionFields = Maps.newLinkedHashMap();
        for (Field field : entityClass.getDeclaredFields()) {
            MetaData metaData = field.getAnnotation(MetaData.class);
            if (metaData != null && metaData.comparable()) {
                revisionFields.put(field, metaData != null ? metaData.value() : field.getName().toUpperCase());
            }
        }
        return revisionFields;
    }

    /**
     * Revision操作记录列表
     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
     * @return JSON集合数据
     */
    @MetaData(value = "版本数据列表")
    protected HttpHeaders revisionList() {
        String property = this.getParameter("property");
        Boolean hasChanged = null;
        String changed = this.getParameter("changed");
        if (StringUtils.isNotBlank(changed)) {
            hasChanged = BooleanUtils.toBooleanObject(changed);
        }
        List<EntityRevision> entityRevisions = getEntityService().findEntityRevisions(this.getId(), property,
                hasChanged);
        for (EntityRevision entityRevision : entityRevisions) {
            Object entity = entityRevision.getEntity();
            ExtDefaultRevisionEntity revEntity = entityRevision.getRevisionEntity();
            if (entity instanceof OperationAuditable) {
                OperationAuditable aae = (OperationAuditable) entity;
                revEntity.setOldStateDisplay(aae.convertStateToDisplay(revEntity.getOldState()));
                revEntity.setNewStateDisplay(aae.convertStateToDisplay(revEntity.getNewState()));
                revEntity.setOperationEventDisplay(revEntity.getOperationEvent());
            } else {
                revEntity.setOldStateDisplay(revEntity.getOldState());
                revEntity.setNewStateDisplay(revEntity.getNewState());
                revEntity.setOperationEventDisplay(revEntity.getOperationEvent());
            }
        }
        setModel(buildPageResultFromList(entityRevisions));
        return buildDefaultHttpHeaders();
    }

    /**
     * Revision版本数据对比显示
     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
     * @return 在struts.xml中全局的revisionCompare Result定义
     */
    @MetaData(value = "版本数据对比")
    protected HttpHeaders revisionCompare() {
        HttpServletRequest request = this.getRequest();
        ID id = this.getId();
        Long revLeft = Long.valueOf(this.getRequiredParameter("revLeft"));
        Long revRight = Long.valueOf(this.getRequiredParameter("revRight"));
        EntityRevision revLeftEntity = null;
        EntityRevision revRightEntity = null;
        List<EntityRevision> entityRevisions = getEntityService().findEntityRevisions(id, revLeft, revRight);
        for (EntityRevision entityRevision : entityRevisions) {
            if (entityRevision.getRevisionEntity().getRev().equals(revLeft)) {
                revLeftEntity = entityRevision;
            } else if (entityRevision.getRevisionEntity().getRev().equals(revRight)) {
                revRightEntity = entityRevision;
            }
        }

        List<Map<String, String>> revEntityProperties = Lists.newArrayList();
        for (Map.Entry<Field, String> me : getRevisionFields().entrySet()) {
            Field field = me.getKey();
            Map<String, String> revEntityProperty = Maps.newHashMap();
            revEntityProperty.put("name", me.getValue());
            if (revLeftEntity != null) {
                try {
                    Object value = FieldUtils.readDeclaredField(revLeftEntity.getEntity(), field.getName(), true);
                    String valueDisplay = convertPropertyDisplay(revLeftEntity.getEntity(), field, value);
                    revEntityProperty.put("revLeftPropertyValue", valueDisplay);
                } catch (IllegalAccessException e) {
                    throw new WebException(e.getMessage(), e);
                }
            }
            if (revRightEntity != null) {
                try {
                    Object value = FieldUtils.readDeclaredField(revRightEntity.getEntity(), field.getName(), true);
                    String valueDisplay = convertPropertyDisplay(revRightEntity.getEntity(), field, value);
                    revEntityProperty.put("revRightPropertyValue", valueDisplay);
                } catch (IllegalAccessException e) {
                    throw new WebException(e.getMessage(), e);
                }

            }
            revEntityProperties.add(revEntityProperty);
        }
        request.setAttribute("revLeftEntity", revLeftEntity);
        request.setAttribute("revRightEntity", revRightEntity);
        request.setAttribute("revEntityProperties", revEntityProperties);
        return buildDefaultHttpHeaders("revisionCompare");
    }

    /**
     * 将对象Value对象转换为显示字符串，子类可根据需要覆写此方法输出定制格式字符串
     * @param entity 版本数据实体对象
     * @param field 版本字段属性
     * @param value 版本属性数据值
     * @return 格式化后处理的字符串
     */
    protected String convertPropertyDisplay(Object entity, Field field, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof PersistableEntity) {
            @SuppressWarnings("rawtypes")
            PersistableEntity persistableEntity = (PersistableEntity) value;
            String label = "N/A";
            try {
                label = persistableEntity.getDisplay();
            } catch (EntityNotFoundException e) {
                //Hibernate Envers默认始终查询对应Audit版本数据，有可能关联对象之前没有Audit记录，从而会导致Envers抛出未找到数据异常
                //此处做Hack处理：如果没有找到关联Audit记录，则查询关联主对象记录
                try {
                    //从Hibernate AOP增强对象反查对应实体对象数据
                    JavassistLazyInitializer jli = (JavassistLazyInitializer) FieldUtils.readDeclaredField(value,
                            "handler", true);
                    Class entityClass = jli.getPersistentClass();
                    Serializable id = jli.getIdentifier();
                    Object obj = getEntityService().findEntity(entityClass, id);
                    PersistableEntity auditTargetEntity = (PersistableEntity) obj;
                    label = auditTargetEntity.getDisplay();
                } catch (IllegalAccessException iae) {
                    logger.warn(e.getMessage());
                }
            }
            return label;
        }
        return String.valueOf(value);
    }

    private static Map<Class<?>, Map<String, Object>> entityValidationRulesMap = Maps.newHashMap();

    /**
     * 支持的转换规则列表：
     * <ul>
     * <li>@Email   email电子邮件格式</li>
     * <li>@Column(nullable=false)   required数据必须</li>
     * </ul> 
     */
    @MetaData(value = "表格数据编辑校验规则")
    public HttpHeaders buildValidateRules() {
        try {
            Map<String, Object> nameRules = entityValidationRulesMap.get(entityClass);
            if (nameRules == null) {
                nameRules = Maps.newHashMap();
                entityValidationRulesMap.put(entityClass, nameRules);

                Class<?> clazz = entityClass;
                Set<Field> fields = Sets.newHashSet(clazz.getDeclaredFields());
                clazz = clazz.getSuperclass();
                while (!clazz.equals(BaseEntity.class) && !clazz.equals(Object.class)) {
                    fields.addAll(Sets.newHashSet(clazz.getDeclaredFields()));
                    clazz = clazz.getSuperclass();
                }

                for (Field field : fields) {
                    if (Modifier.isStatic(field.getModifiers()) || !Modifier.isPrivate(field.getModifiers())
                            || Collection.class.isAssignableFrom(field.getType())) {
                        continue;
                    }
                    String name = field.getName();
                    if ("id".equals(name)) {
                        continue;
                    }
                    Map<String, Object> rules = Maps.newHashMap();

                    MetaData metaData = field.getAnnotation(MetaData.class);
                    if (metaData != null) {
                        String tooltips = metaData.tooltips();
                        if (StringUtils.isNotBlank(tooltips)) {
                            rules.put("tooltips", tooltips);
                        }
                    }

                    Method method = MethodUtils.getAccessibleMethod(entityClass, "get" + StringUtils.capitalize(name),
                            null);

                    if (method != null) {
                        Class<?> retType = method.getReturnType();
                        Column column = method.getAnnotation(Column.class);

                        if (column != null) {
                            if (retType != Boolean.class && column.nullable() == false) {
                                rules.put("required", true);
                            }
                            if (column.unique() == true) {
                                rules.put("unique", true);
                            }
                            if (column.updatable() == false) {
                                rules.put("readonly", true);
                            }
                            if (column.length() > 0 && retType == String.class
                                    && method.getAnnotation(Lob.class) == null) {
                                rules.put("maxlength", column.length());
                            }
                        }

                        JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
                        if (joinColumn != null) {
                            if (joinColumn.nullable() == false) {
                                rules.put("required", true);
                            }
                        }

                        if (retType == Date.class) {
                            JsonSerialize jsonSerialize = method.getAnnotation(JsonSerialize.class);
                            if (jsonSerialize != null) {
                                if (DateJsonSerializer.class == jsonSerialize.using()) {
                                    rules.put("date", true);
                                } else if (DateTimeJsonSerializer.class == jsonSerialize.using()) {
                                    rules.put("timestamp", true);
                                }
                            } else {
                                rules.put("date", true);
                            }
                        } else if (retType == BigDecimal.class) {
                            rules.put("number", true);
                        } else if (retType == Integer.class || retType == Long.class) {
                            rules.put("integer", true);
                        }

                        Size size = method.getAnnotation(Size.class);
                        if (size != null) {
                            if (size.min() > 0) {

                            }
                            if (size.max() < Integer.MAX_VALUE) {
                            }
                        }

                        Email email = method.getAnnotation(Email.class);
                        if (email != null) {
                            rules.put("email", true);
                        }

                        Pattern pattern = method.getAnnotation(Pattern.class);
                        if (pattern != null) {
                            rules.put("regex", pattern.regexp());
                        }

                        if (rules.size() > 0) {
                            nameRules.put(name, rules);
                            //如果是实体对象类型，一般表单元素name都定义为entity.id，因此额外追加对应id属性校验规则
                            if (PersistableEntity.class.isAssignableFrom(field.getType())) {
                                nameRules.put(name + ".id", rules);
                            }
                        }
                    }
                }
            }
            setModel(nameRules);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            setModel(OperationResult.buildFailureResult("系统处理异常"));
        }

        return new DefaultHttpHeaders();
    }

    /**
     * 基于分组和聚合属性返回Map结构分页数据
     * 判断规则：属性名称包含"("则标识为聚合属性，其余为分组属性
     * @param properties  属性列表，语法规则：sum = + , diff = - , prod = * , quot = / , case(condition,when,else)
     * 示例：
     *     sum(amount)
     *     sum(diff(amount,costAmount))
     *     min(case(equal(amount,0),-1,quot(diff(amount,costAmount),amount)))
     *     case(equal(sum(amount),0),-1,quot(sum(diff(amount,costAmount)),sum(amount)))
     * @param groupFilter  动态参数对象
     * @param pageable  分页排序对象
     * @return
     */
    protected Page<Map<String, Object>> findByGroupAggregate(GroupPropertyFilter groupFilter, Pageable pageable,
            String... properties) {
        return getEntityService().findByGroupAggregate(groupFilter, pageable, properties);
    }

    /**
     * 基于参数和排序调用分组查询 
     * @param properties
     * @param groupFilter  动态参数对象
     * @param sort  排序对象
     * @return
     */
    protected Page<Map<String, Object>> findByGroupAggregate(GroupPropertyFilter groupFilter, Sort sort,
            String... properties) {
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);
        return findByGroupAggregate(groupFilter, pageable, properties);
    }

    /**
     * 基于参数调用分组查询 
     * @param properties
     * @param groupFilter  动态参数对象
     * @return
     */
    protected Page<Map<String, Object>> findByGroupAggregate(GroupPropertyFilter groupFilter, String... properties) {
        return getEntityService().findByGroupAggregate(groupFilter, null, properties);
    }

    /**
     * 基于请求组装的参数调用分组查询 
     * @param properties
     * @return
     */
    protected Page<Map<String, Object>> findByGroupAggregate(String... properties) {
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
        appendFilterProperty(groupFilter);
        return findByGroupAggregate(groupFilter, pageable, properties);
    }

    /**
     * 子类定义可接受参数数组，然后调用次帮助类方法进行参数可接受检测
     * 对于需要给外部用户访问的Controller为了避免用户非法篡改数据
     * 数组类型参数，按照以星号作为数组下标匹配，abc[*].xyz格式定义
     * 以 @see ParameterNameAware 形式设置自动绑定参数白名单
     */
    protected boolean acceptableParameterName(String[] acceptableParameterNames, String parameterName) {
        if (parameterName.equals("struts.token.name") || parameterName.equals("token")
                || parameterName.equals("version")) {
            return true;
        }
        if (parameterName.equals("extraAttributes") || parameterName.indexOf("extraAttributes.") > -1) {
            return true;
        }
        //数组类型参数，转换以便匹配abc[*].xyz定义模式
        if (parameterName.indexOf("[") > -1) {
            parameterName = StringUtils.substringBefore(parameterName, "[") + "[*]"
                    + StringUtils.substringAfter(parameterName, "]");
        }
        for (String name : acceptableParameterNames) {
            if (name.equals(parameterName)) {
                return true;
            }
            if (name.indexOf("[") > -1) {
                name = name.replace("[*]", "");
            }
            //嵌套参数支持
            if (name.indexOf(parameterName + ".") > -1 || name.indexOf("." + parameterName) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对于一些复杂处理逻辑需要基于提交数据服务器校验后有提示警告信息需要用户二次确认
     * 判断当前表单是否已被用户confirm确认OK
     */
    protected boolean postNotConfirmedByUser() {
        return !BooleanUtils.toBoolean(getParameter("_serverValidationConfirmed_"));
    }
}
