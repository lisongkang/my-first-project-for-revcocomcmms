package com.maywide.biz.prd.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.prd.entity.CatalogCondtion;
import com.maywide.biz.prd.service.CatalogCondtionService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prd.entity.CatalogCondtion管理")
public class CatalogCondtionController extends BaseController<CatalogCondtion,Long> {

    @Autowired
    private CatalogCondtionService catalogCondtionService;

    @Override
    protected BaseService<CatalogCondtion, Long> getEntityService() {
        return catalogCondtionService;
    }
    
    @Override
    protected void checkEntityAclPermission(CatalogCondtion entity) {
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
    	catalogCondtionService.checkEntity(bindingEntity);
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