package com.maywide.common.task.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.service.BaseService;
import com.maywide.common.task.entity.CfgTask;
import com.maywide.common.task.service.CfgTaskService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].core.task.entity.CfgTask管理")
public class CfgTaskController extends BaseController<CfgTask,Long> {

    @Autowired
    private CfgTaskService cfgTaskService;

    @Override
    protected BaseService<CfgTask, Long> getEntityService() {
        return cfgTaskService;
    }
    
    @Override
    protected void checkEntityAclPermission(CfgTask entity) {
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