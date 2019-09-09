package com.maywide.biz.tmpopenlimit.web.action;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.pojo.quetempopenplan.TempopenPlanInfoBO;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.service.PrvDepartmentService;
import com.maywide.biz.prv.service.PrvOperatorService;
import com.maywide.biz.prv.service.PrvOperroleService;
import com.maywide.biz.tmpopenlimit.entity.BizTmpOpenLimit;
import com.maywide.biz.tmpopenlimit.entity.BizTmpOpenLimitBO;
import com.maywide.biz.tmpopenlimit.service.BizTmpOpenLimitService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].channel.biz.entity.BizTmpOpenLimit管理")
public class BizTmpOpenLimitController extends BaseController<BizTmpOpenLimit,Long> {

    @Autowired
    private BizTmpOpenLimitService bizTmpOpenLimitService;
    @Autowired
    private PrvDepartmentService prvDepartmentService;
    @Autowired
    private PrvOperatorService prvOperatorService;
    @Autowired
    private PrvOperroleService prvOperroleService;
   
    
    public HttpHeaders index() {
        return buildDefaultHttpHeaders("index");
    }
    
    public HttpHeaders selectOpenTabs() throws Exception {
        setModel(new ArrayList());
        return buildDefaultHttpHeaders("openTabs");
    }
	
public HttpHeaders findTmpOpen() {
        try {
        	Pageable pageable = PropertyFilter
                    .buildPageableFromHttpRequest(getRequest());                      
            String areaid = null;   
            String objType = getParameter("objType");
            String objId = getParameter("objId");
            if("0".equals(objType)){
            	 PrvDepartment prvDepartment = prvDepartmentService.findOne(Long.parseLong(objId));
            	 if(prvDepartment!=null){
            	    areaid = Long.toString(prvDepartment.getAreaid());
            	 }else{
            	   setModel(OperationResult.buildFailureResult("所属部门没有方案！"));
            	   return buildDefaultHttpHeaders();
            	 }
            }else if("1".equals(objType)){
            	 areaid = prvDepartmentService.findAreaidsByOperid(pageable, objId);
            	if(areaid==null||"".equals(areaid)){
            	  setModel(OperationResult.buildFailureResult("该用户没有部门或所属部门没有方案！"));
            	  return buildDefaultHttpHeaders();
            	}
            	
            }
            setModel(bizTmpOpenLimitService.queryOpenPlan( areaid,pageable));           
        } catch (Exception e) {
        	if("查询点通方案:查询不到点通方案".equals(e.getMessage())){
        		Pageable pageable = PropertyFilter
                        .buildPageableFromHttpRequest(getRequest()); 
        		List<TempopenPlanInfoBO> resHouseList = new ArrayList<TempopenPlanInfoBO>();
        		setModel(new PageImpl(resHouseList, pageable, 0));
        	}else{
        	    throw new ServiceException(e.getMessage());
        	}
        }      
        return buildDefaultHttpHeaders();
    }

    @Override
    protected BaseService<BizTmpOpenLimit, Long> getEntityService() {
        return bizTmpOpenLimitService;
    }
    
    @Override
    protected void checkEntityAclPermission(BizTmpOpenLimit entity) {
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
    	BizTmpOpenLimit entity = (BizTmpOpenLimit)bindingEntity;
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	if(entity.isNew()){    		 
    		entity.setCity(loginInfo.getCity());
    		entity.setCreateOper(loginInfo.getOperid());
    		entity.setCreateTime(new Date());
    		 SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    		 Date updateTime=null;
			try {
				updateTime = sdf.parse("2099-12-31 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		entity.setUpdateTime(updateTime);
    	}else{
    		entity.setUpdateOper(loginInfo.getOperid());
    		entity.setUpdateTime(new Date());
    	}
    	try{
    	 getEntityService().save(entity);
    	 setModel(OperationResult.buildSuccessResult("数据保存成功", entity));
    	}catch(Exception e){
    		e.printStackTrace();
    		String exceMsg  = e.getMessage();
    		if(exceMsg != null
                    && (exceMsg.indexOf("Duplicate") > -1 || exceMsg.indexOf("UNIQUE") > -1 || exceMsg
                            .startsWith("ORA-02292"))) {
    			 setModel(OperationResult.buildFailureResult("已存在相同的配置，不允许重复添加"));
            }
    	}
      
         return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	 Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
         GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
         appendFilterProperty(groupFilter); 
         BizTmpOpenLimitBO tmpopen = new BizTmpOpenLimitBO();
         tmpopen.setName(getRequest().getParameter("name1")); 
         tmpopen.setObjType(getRequest().getParameter("objType1"));
         tmpopen.setTimeType(getRequest().getParameter("timeType1")); 
         tmpopen.setObjName(getRequest().getParameter("objName1"));
         Page result = null;
         String objName =  tmpopen.getObjName();
         String objType = tmpopen.getObjType();
		try {
			if(objName==null||"".equals(objName)){
			    result = bizTmpOpenLimitService.queryTmpOpenLimit(tmpopen, pageable);
			}else{
				if("0".equals(objType)){
					result = bizTmpOpenLimitService.queryTmpOpenLimitByDepartment(tmpopen, pageable);
				}else if("1".equals(objType)){
					result = bizTmpOpenLimitService.queryTmpOpenLimitByOperator(tmpopen, pageable);
				}else{
					result = bizTmpOpenLimitService.queryTmpOpenLimit(tmpopen, pageable);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
         List<BizTmpOpenLimit> list = result.getContent();
         List<BizTmpOpenLimitBO> listbo =new ArrayList<BizTmpOpenLimitBO>();
         for(BizTmpOpenLimit open : list){
        	 BizTmpOpenLimitBO bo = new BizTmpOpenLimitBO();
        	 try {
				BeanUtils.copyProperties(bo,open);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	 if("0".equals(bo.getObjType())){
        		 if(prvDepartmentService.findOne(bo.getObjId())!=null){
        			 bo.setObjName(prvDepartmentService.findOne(bo.getObjId()).getName());
        		 }
        		 
        	 }else{
        		 if(prvOperatorService.findOne(bo.getObjId())!=null){
        			 bo.setObjName(prvOperatorService.findOne(bo.getObjId()).getName());
        		 }
        		 
        	 }        	
        	 listbo.add(bo);
         }
         setModel(new PageImpl(listbo, pageable, listbo.size()));
         return buildDefaultHttpHeaders();
    }
    
}