package com.maywide.biz.prd.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.prd.entity.SalesRule;
import com.maywide.biz.prd.service.SalesRuleService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prd.entity.SalesRule管理")
public class SalesRuleController extends BaseController<SalesRule,Long> {

    @Autowired
    private SalesRuleService salesRuleService;

    @Override
    protected BaseService<SalesRule, Long> getEntityService() {
        return salesRuleService;
    }
    
    @Override
    protected void checkEntityAclPermission(SalesRule entity) {
        // TODO Add acl check code logic
    }
    
    @Override
    protected void appendFilterProperty(GroupPropertyFilter groupPropertyFilter) {
    	groupPropertyFilter.append(new PropertyFilter(MatchType.EQ, "pcode.status", SysConstant.ProdStatus.PUBLISH));
    	super.appendFilterProperty(groupPropertyFilter);
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
        return super.findByPage();
    }
}