package com.maywide.biz.prv.web.action;

import java.util.Date;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.prv.entity.PrvOperrole;
import com.maywide.biz.prv.service.PrvOperroleService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prv.entity.PrvOperrole管理")
public class PrvOperroleController extends BaseController<PrvOperrole,Long> {

    @Autowired
    private PrvOperroleService prvOperroleService;
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseService<PrvOperrole, Long> getEntityService() {
        return prvOperroleService;
    }
    
    @Override
    protected void checkEntityAclPermission(PrvOperrole entity) {
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
    	checkInputData();
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
    
    private void checkInputData() {
    	PrvOperrole operrole = new PrvOperrole();
    	operrole.setOperid(bindingEntity.getOperid());
    	operrole.getDepartment().setId(bindingEntity.getDepartment().getId());
    	if (bindingEntity.getId() != null) {
    		operrole.set_ne_id(bindingEntity.getId());
    	}
    	
    	try {
    		persistentService.clear();
    		if (persistentService.find(operrole).size() > 0) {
    			throw new ServiceException("该部门已经设置了角色，不可重复设置");
        	}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
    	if (bindingEntity.getStime() == null || bindingEntity.getEtime() == null) {
    		throw new ServiceException("启用时间和结束时间不能为空！");
    	}
    	
    	if (bindingEntity.getStime().getTime() > bindingEntity.getEtime().getTime()) {
    		throw new ServiceException("启用时间不能大于结束时间！");
    	}
    	
    	if (new Date().getTime() > bindingEntity.getEtime().getTime()) {
    		throw new ServiceException("结束时间必须大于当前时间！");
    	}
    }
}