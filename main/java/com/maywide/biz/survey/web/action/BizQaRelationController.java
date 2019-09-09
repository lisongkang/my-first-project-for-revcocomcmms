package com.maywide.biz.survey.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.maywide.biz.survey.entity.BizQaRelation;
import com.maywide.biz.survey.service.BizQaRelationService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@Controller
@MetaData("[com.maywide].biz.survey.entity.BizQaRelation管理")
public class BizQaRelationController extends BaseController<BizQaRelation,Long> {

	@Autowired
	private BizQaRelationService bizQaRelationService;
	
	@Override
	protected BaseService<BizQaRelation, Long> getEntityService() {
		// TODO Auto-generated method stub
		return bizQaRelationService;
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
    
    public HttpHeaders findAnSwerByQid(){
    	String qid = getParameter("qid");
    	setModel(bizQaRelationService.findAnSwerByQid(qid));
    	return buildDefaultHttpHeaders("detail");
    	
    }

	
}
