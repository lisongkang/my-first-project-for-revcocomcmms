package com.maywide.biz.ad.adset.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.google.common.collect.Maps;
import com.maywide.biz.ad.adset.entity.AdConfig;
import com.maywide.biz.ad.adset.entity.AdConfigHis;
import com.maywide.biz.ad.adset.pojo.AdSearchParamsBo;
import com.maywide.biz.ad.adset.service.AdConfigService;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.cons.Constant;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.web.view.OperationResult;

/**
 * 
 *<p> 
 *  广告配置模块
 *<p>
 * Create at 2017-3-20
 *
 *@autor zhuangzhitang
 */
public class AdSetController  extends BaseController<AdConfig,Long>{

	@Autowired
	private AdConfigService adConfigService;
	
	private AdSearchParamsBo adSearchParamsBo;  //搜索影射对象
	
	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private ParamService paramService;
	
	@Override
	protected BaseService<AdConfig, Long> getEntityService() {
		
		return adConfigService;
	}
	
	/**
	 * 广告配置首页
	 */
	public HttpHeaders index(){
		return buildDefaultHttpHeaders("index");
	}
	
	/**
	 * 广告审核首页
	 */
	public HttpHeaders audit(){
		return buildDefaultHttpHeaders("audit");
	}
	
    public HttpHeaders edit(){
    	Map<String, Object>  map = new HashMap<String, Object>();
    	try{
    		if(bindingEntity!=null&& bindingEntity.getAdtype()!=null&&bindingEntity.getAdtype().toString().equals(BizConstant.AD_TYPE.AD_TYPE_1)){
        		SalespkgKnow know = new SalespkgKnow();
        		know.setKnowid(Long.parseLong(bindingEntity.getAdobj()));
        		know = (SalespkgKnow) persistentService.find(know).get(0);
        		map.put("objname",know.getKnowname());
        	}else{
        		map.put("objname", bindingEntity.getAdobj());
        	}
        	
        	bindingEntity.setExtraAttributes(map);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    
    	return buildDefaultHttpHeaders("inputBasic");
    }
    /**
     * 
     * 审核搜索分页
     */
    
    public HttpHeaders findByPageFromAudit(){
    	List<AdConfig> list = new ArrayList<AdConfig>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try{
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    		if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
    			adSearchParamsBo.setCity(loginInfo.getCity());
    		}
    		adSearchParamsBo.setType("1");
			setModel(adConfigService.findByPage(adSearchParamsBo,pageable));
			
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<AdConfig>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    }
    
	/**
	 * 分页搜索
	 */
	public HttpHeaders findByPage(){
		List<AdConfig> list = new ArrayList<AdConfig>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try{
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    		if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
    			adSearchParamsBo.setCity(loginInfo.getCity());
    		}
			setModel(adConfigService.findByPage(adSearchParamsBo,pageable));
			
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<AdConfig>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
	}

	/**
	 * 添加或者修改
	 * @return
	 */
	
	@Override
    public HttpHeaders doSave(){
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		try{
		//新建
		if(null==getId()||StringUtils.isBlank(getId().toString())){
			bindingEntity.setOptid(loginInfo.getOperid());
			bindingEntity.setOpttime(new Date());
			bindingEntity.setOpertype(Long.parseLong(BizConstant.SQL_OPER_TYPE.SQL_OPER_TYPE_2));
		}else{
		 //修改
		    bindingEntity.setOpertype(Long.parseLong(BizConstant.SQL_OPER_TYPE.SQL_OPER_TYPE_3));
		}
		
		 getEntityService().save(bindingEntity);
		 //记录轨迹
         adConfigService.registerAdConfigHis(bindingEntity);
		 setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
		}catch(Exception e){
			 e.printStackTrace();
			 setModel(OperationResult.buildFailureResult("保存操作失败"));
		}
		
		 return buildDefaultHttpHeaders();
	}
	
	 /**
     * 已保存的广告，广告的操作员可将该广告删除
     */
	public HttpHeaders doDelete(){
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        Map<Long, String> errorMessageMap = Maps.newLinkedHashMap();
        Collection<AdConfig> entities = this.getEntitiesByParameterIds();
        for(AdConfig entity : entities){
    	   String tstatus = entity.getAdstatus().toString();
    	   if((tstatus.equals(BizConstant.AD_STATUS.AD_STATUS_1)
    		    	  )&&loginInfo.getOperid().equals(entity.getOptid())){
    		   
    		   //删除轨迹表
    		   try{
        		   persistentService.executeUpdate("DELETE FROM AdConfigHis WHERE adid = ?", entity.getId());
    		   }catch(Exception e){
    			   e.printStackTrace();
    		   }
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
	               setModel(OperationResult.buildFailureResult("所有选取记录删除操作失败（允许已保存的广告，广告的录入操作员可将其删除）", errorMessageMap));
	           } else {
	               setModel(OperationResult.buildWarningResult("删除操作已处理. 成功:" + (entities.size() - rejectSize) + "条"
	                       + ",失败:" + rejectSize + "条（允许已保存的广告，广告的录入操作员可将其删除）", errorMessageMap));
	           }
	       }
		return buildDefaultHttpHeaders();
	}
	
	/**
	 * 编辑广告状态
	 * @return
	 * @throws Exception 
	 */
	public HttpHeaders editAdConfigStatus() throws Exception{
		   String bo = getParameter("adConfig");
		   AdConfig adconfig = (AdConfig) BeanUtil.jsonToObject(bo,
				   AdConfig.class);
		   bindingEntity = getEntityService().findOne(adconfig.getId());
		   LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		   bindingEntity.setAuditid(loginInfo.getOperid());
		   bindingEntity.setAudittime(new Date());
		   bindingEntity.setAdstatus(adconfig.getAdstatus());
		   getEntityService().save(bindingEntity);
		   
		   //记录轨迹
		   bindingEntity.setOpertype(Long.parseLong(BizConstant.SQL_OPER_TYPE.SQL_OPER_TYPE_3));
	       adConfigService.registerAdConfigHis(bindingEntity);
		   setModel(OperationResult.buildSuccessResult("评审操作成功", bindingEntity));
		   return buildDefaultHttpHeaders();
	}
	public Map<String, String> getAdtypeMap(){
		 Map<String, String> adtypeMap = new LinkedHashMap<String, String>();
		 try {
			 List<PrvSysparam> params =  paramService.getData("AD_TYPE");
			 for(PrvSysparam param :params){
				 adtypeMap.put(param.getMcode(), param.getMname());
			 }
			 
		 }
		 catch (Exception e) {
	            e.printStackTrace();
	     }
		 
		 return adtypeMap;
	}
	
	
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	    public Map<String, String> getAreaMap() {
	        String hql = "FROM PrvSysparam WHERE gcode = ? AND scope is null ORDER BY id";
	        List<PrvSysparam> areaList;
	        Map<String, String> areaMap = new LinkedHashMap<String, String>();
	        try {
	            areaList = persistentService.find(hql, "PRV_CITY");
	            Collections.sort(areaList, new Comparator() {
	                public int compare(Object o1, Object o2) {
	                    PrvSysparam param1 = (PrvSysparam) o1;
	                    PrvSysparam param2 = (PrvSysparam) o2;

	                    String pinyin1 = PinYinUtils.converterToFirstSpell(param1.getMname());
	                    String pinyin2 = PinYinUtils.converterToFirstSpell(param2.getMname());

	                    int rtnval = pinyin1.compareTo(pinyin2);

	                    return rtnval;
	                }
	            });
	            areaMap.put("*", "所有");
	            for (PrvSysparam param : areaList) {
	                areaMap.put(param.getMcode(), param.getDisplay());
	            }

	            return areaMap;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return areaMap;
	    }

	public AdSearchParamsBo getAdSearchParamsBo() {
		return adSearchParamsBo;
	}

	public void setAdSearchParamsBo(AdSearchParamsBo adSearchParamsBo) {
		this.adSearchParamsBo = adSearchParamsBo;
	}
	
	
}
