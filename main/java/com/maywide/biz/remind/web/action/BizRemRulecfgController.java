package com.maywide.biz.remind.web.action;

import java.util.Date;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.remind.entity.BizRemRulecfg;
import com.maywide.biz.remind.entity.BizRemindWarning;
import com.maywide.biz.remind.pojo.RemindRulecfgBO;
import com.maywide.biz.remind.service.BizRemRulecfgService;
import com.maywide.biz.remind.service.BizRemindWarningService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.remind.entity.BizRemRulecfg管理")
public class BizRemRulecfgController extends BaseController<BizRemRulecfg,Long> {

    @Autowired
    private BizRemindWarningService bizRemindWarningService;
    
    @Autowired
    private BizRemRulecfgService bizRemRulecfgService;

    @Override
    protected BaseService<BizRemRulecfg, Long> getEntityService() {
        return bizRemRulecfgService;
    }
    
    @Override
    protected void checkEntityAclPermission(BizRemRulecfg entity) {
        // TODO Add acl check code logic
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        //TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }
    
    @Override
    @MetaData("创建")
    public HttpHeaders doCreate() {
        return super.doCreate();
    }

    @Override
    @MetaData("更新")
    public HttpHeaders doUpdate() {
        return super.doUpdate();
    }
    
    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
     	String city = loginInfo.getCity();
     	Long operid = loginInfo.getOperid();
     	Long deptid = loginInfo.getDeptid();
     	
     	if (this.bindingEntity != null) {
     		bindingEntity.setAppdate(new Date());
     		bindingEntity.setOperid(operid);
     		bindingEntity.setDeptid(deptid);
     		bindingEntity.setCity(city);
     	}
     	
        return super.doSave();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {

    	String remid = getParameter("remid");
    	
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
        
        if (StringUtils.isNotEmpty(remid)) {
        	PropertyFilter filter = new PropertyFilter(BizRemRulecfg.class,
    				PropertyFilter.MatchType.EQ + "_remid", remid);
            groupFilter.getFilters().add(filter);
        }
        
        appendFilterProperty(groupFilter);
        
        String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
        if ("xls".equalsIgnoreCase(foramt)) {
            exportXlsForGrid(groupFilter, pageable.getSort());
        } else {
            setModel(this.getEntityService().findByPage(groupFilter, pageable));
        }
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders toRuleInfo() throws Exception {
    	String remid = getParameter("remid");
    	BizRemindWarning remind = bizRemindWarningService.getRemindWarning(Long.parseLong(remid));
    	setModel(remind);
    	return buildDefaultHttpHeaders("index");
    }
    
    public HttpHeaders setRuleCfg() throws Exception {
    	String remid = getParameter("remid");
    	BizRemindWarning remind = bizRemindWarningService.getRemindWarning(Long.parseLong(remid));

    	RemindRulecfgBO remindRulecfgBO = new RemindRulecfgBO();
    	remindRulecfgBO.setBizRemindWarning(remind);
    	BizRemRulecfg cfg = bizRemRulecfgService.getBizRemRulecfg(Long.parseLong(remid));
    	if (cfg != null) {
    		remindRulecfgBO.setBizRemRulecfg(cfg);
    	}
    	
    	setModel(remindRulecfgBO);
    	return buildDefaultHttpHeaders("inputBasic");
    }
}