package com.maywide.biz.survey.web.action;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Maps;
import com.maywide.biz.survey.entity.BizQuestionList;
import com.maywide.biz.survey.pojo.BizQuestionBO;
import com.maywide.biz.survey.service.BizQaRelationService;
import com.maywide.biz.survey.service.BizQuestionListService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.view.OperationResult;

@Controller
@MetaData("[com.maywide].biz.survey.entity.BizQuestionList管理")
public class BizQuestionListController extends BaseController<BizQuestionList,Long> {

    @Autowired
    private BizQuestionListService bizQuestionListService;
    
    @Autowired
    private BizQaRelationService bizQaRelationService;
    
    private BizQuestionBO bizQuestionBO;
    
    public BizQuestionBO getBizQuestionBO() {
		return bizQuestionBO;
	}

	public void setBizQuestionBO(BizQuestionBO bizQuestionBO) {
		this.bizQuestionBO = bizQuestionBO;
	}

	@Override
    protected BaseService<BizQuestionList, Long> getEntityService() {
        return bizQuestionListService;
    }
    
    @Override
    protected void checkEntityAclPermission(BizQuestionList entity) {
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
    	String bizQuestionBoJson = getParameter("bizQuestionBO");
    	try {
    		bizQuestionBO = (BizQuestionBO) BeanUtil
					.jsonToObject(bizQuestionBoJson, BizQuestionBO.class);
    		bizQuestionListService.saveQuestionByIsOpen(bizQuestionBO);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 setModel(OperationResult.buildSuccessResult("数据保存成功"));
    	 return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
    	 //删除失败的id和对应消息以Map结构返回，可用于前端批量显示错误提示和计算表格组件更新删除行项
         Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
    	 Collection<BizQuestionList> entities = this.getEntitiesByParameterIds();
    	 List<Long> warnList = new ArrayList<Long>();//存取有问卷关联的qid，不能够被删除
        try{
    	 for(BizQuestionList bizQuestionList : entities){
    		 //判断将要删除的题目是否关联问卷，关联的不删除
        	 boolean flag = bizQuestionListService.isExistSuveryContainQue(bizQuestionList.getId());
        	 if(!flag){
        		 if(bizQuestionList.getIsopen().equals("N")){
        			 bizQaRelationService.deleteByQid(bizQuestionList.getId());
        		 }
        		 getEntityService().delete(bizQuestionList);
        	 }else{
        		 warnList.add(bizQuestionList.getId());
        		 errorMessageMap.put(bizQuestionList.getId(),"题目ID"+bizQuestionList.getId()+"关联问卷,不可删除");
        	 }
        }
       }catch(Exception e){
    	   e.printStackTrace();
       }
        if(warnList.size()>0){
        	setModel(OperationResult.buildWarningResult("删除操作已处理.其中有关联问卷的题目不可删除,题目ID："+warnList.toString(),errorMessageMap));
        }else{
        	 setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + entities.size() + "条"));
        }
       
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	Pageable pageable = PropertyFilter
				.buildPageableFromHttpRequest(getRequest());
    	String searchParam = getParameter("search['CN_operator_OR_qcontent']");
    	String intime = getParameter("search['CN_intime']");
		try {
			PageImpl pageResult = bizQuestionListService.queQuestionLists(searchParam,intime,pageable);
			setModel(pageResult);
		} catch (Exception e) {
			List<BizQuestionList> list = new ArrayList<BizQuestionList>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("打开编辑表单")
    public HttpHeaders edit() {
        return buildDefaultHttpHeaders("inputBasic");
    }
    
    @MetaData("查询空记录,弹窗选择页面使用")
    public HttpHeaders findEmptyResult() {
        setModel(new PageImpl<BizQuestionList>(new ArrayList<BizQuestionList>()));
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders findQuestionsBySid(){
    	String sid = getRequest().getParameter("sid");
    	try {
    		Pageable pageable = PropertyFilter
    				.buildPageableFromHttpRequest(getRequest());
    		List<BizQuestionList> list = bizQuestionListService.getQuestionListBySid(sid);
			setModel(new PageImpl(list, pageable, 1));
		} catch (Exception e) {
		}
    	return buildDefaultHttpHeaders();
    }
}