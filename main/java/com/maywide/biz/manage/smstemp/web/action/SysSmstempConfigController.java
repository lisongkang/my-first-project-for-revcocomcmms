package com.maywide.biz.manage.smstemp.web.action;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.google.common.collect.Maps;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.manage.smstemp.entity.SysSmstempConfig;
import com.maywide.biz.manage.smstemp.pojo.SysSmstempConfigPo;
import com.maywide.biz.manage.smstemp.service.SysSmstempConfigService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.view.OperationResult;


@MetaData("[com.maywide].manage.smstemp.entity.SysSmstempConfig管理")
public class SysSmstempConfigController extends BaseController<SysSmstempConfig,Long> {

	@Autowired
	private SysSmstempConfigService sysSmstempConfigService;
	
	private SysSmstempConfigPo sysSmstempConfigPo;
	
	@Override
	protected BaseService<SysSmstempConfig, Long> getEntityService() {

		return sysSmstempConfigService;
	}


    @Override
    @MetaData("删除")
    /**
     * 未提交和审核不通过的模版，模版的操作员可将该模版删除
     */
    public HttpHeaders doDelete() {
       LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
       Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
       Collection<SysSmstempConfig> entities = this.getEntitiesByParameterIds();
       for(SysSmstempConfig entity : entities){
    	   String tstatus = entity.getTstatus().toString();
    	   if((tstatus.equals(BizConstant.SYS_SMSTEMP_STATU.SYS_SMSTEMP_STATU_NOSUBMIT)
    		    	  ||tstatus.equals(BizConstant.SYS_SMSTEMP_STATU.SYS_SMSTEMP_STATU_AUDITFAIL)
    		    	  )&&loginInfo.getOperid().equals(entity.getOpid())){
    		   getEntityService().delete(entity);
    	   }else{
	    	   errorMessageMap.put(entity.getId(), "");
	       }
       }
       int rejectSize = errorMessageMap.size();
       if (rejectSize == 0) {
           setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + entities.size() + "条"));
       } else {
           if (rejectSize == entities.size()) {
               setModel(OperationResult.buildFailureResult("所有选取记录删除操作失败（允许未提交和审核不通过的模版，模版的操作员可将其删除）", errorMessageMap));
           } else {
               setModel(OperationResult.buildWarningResult("删除操作已处理. 成功:" + (entities.size() - rejectSize) + "条"
                       + ",失败:" + rejectSize + "条（允许未提交和审核不通过的模版，模版的操作员可将其删除）", errorMessageMap));
           }
       }
       return buildDefaultHttpHeaders();
    }
    
	@Override
	@MetaData("修改或者创建")
    public HttpHeaders doSave(){
		 LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		 //新建
		 if(null==getId()||StringUtils.isBlank(getId().toString())){
			 bindingEntity.setCity(loginInfo.getCity());
			 bindingEntity.setOpid(loginInfo.getOperid());
			 bindingEntity.setOpdept(loginInfo.getDeptid());
		
		 }else{
		 //修改
			//bindingEntity = getEntityService().findOne(getId());
		 }
		 bindingEntity.setOptime(new Date());
		 if(StringUtils.isNotBlank(getParameter("tstatus"))){
			 bindingEntity.setTstatus(Long.parseLong(getParameter("tstatus")));
		 }
		
		 getEntityService().save(bindingEntity);
		 setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
		 return buildDefaultHttpHeaders();
    }

	@Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
    	try{
    		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    		if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
    			sysSmstempConfigPo.setCity(loginInfo.getCity());
    		}
    		setModel(sysSmstempConfigService.findSmstempbyParams(sysSmstempConfigPo,pageable));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return buildDefaultHttpHeaders();
    }
   /**
    * 短信模板评审，修改模板状态
    * @return
 * @throws Exception 
    */
   public HttpHeaders editSmstempStatus() throws Exception{
	   String bo = getParameter("sysSmstempConfig");
	   SysSmstempConfig smsconfig = (SysSmstempConfig) BeanUtil.jsonToObject(bo,
				SysSmstempConfig.class);
	   bindingEntity = getEntityService().findOne(smsconfig.getId());
	   LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
	   bindingEntity.setAuditdept(loginInfo.getDeptid());
	   bindingEntity.setAuditid(loginInfo.getOperid());
	   bindingEntity.setTstatus(smsconfig.getTstatus());
	   bindingEntity.setAudittime(new Date());
	   getEntityService().save(bindingEntity);
	   setModel(OperationResult.buildSuccessResult("评审操作成功", bindingEntity));
	   return buildDefaultHttpHeaders();
   }
   
   public HttpHeaders editSmsStatusToUseless(){
	   String id = getParameter("id");
	   bindingEntity = getEntityService().findOne(Long.parseLong(id));
	   bindingEntity.setTstatus(Long.parseLong(BizConstant.SYS_SMSTEMP_STATU.SYS_SMSTEMP_STATU_USERLESS));
	   getEntityService().save(bindingEntity);
	   setModel(OperationResult.buildSuccessResult("评审操作成功", bindingEntity));
	   return buildDefaultHttpHeaders();
   }
	
	public SysSmstempConfigPo getSysSmstempConfigPo() {
		return sysSmstempConfigPo;
	}

	public void setSysSmstempConfigPo(SysSmstempConfigPo sysSmstempConfigPo) {
		this.sysSmstempConfigPo = sysSmstempConfigPo;
	}


}
