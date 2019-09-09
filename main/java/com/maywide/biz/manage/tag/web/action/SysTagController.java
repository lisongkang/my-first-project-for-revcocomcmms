package com.maywide.biz.manage.tag.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.pojo.queStopUser.QueStopUserBossResp;
import com.maywide.biz.manage.tag.entity.SysTag;
import com.maywide.biz.manage.tag.service.SysTagService;
import com.maywide.biz.prd.entity.Catalog;
import com.maywide.biz.prv.entity.PrvRoleinfo;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.view.OperationResult;


@MetaData("[com.maywide].manage.tag.entity.SysTag管理")
public class SysTagController  extends BaseController<SysTag,Long> {

	@Autowired
	private SysTagService sysTagService;
	
	@Autowired
	private ParamService  paramService;
	
	@Override
	protected BaseService<SysTag, Long> getEntityService() {
		return sysTagService;
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
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	
    	return super.findByPage();
    }
    
    //根据地市过滤
    public void appendFilterProperty(GroupPropertyFilter groupPropertyFilter) {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if (loginInfo == null) {
			return;
		}
		// 超级管理员可以查看并操作所有订单
		if ("GZCYKFA0001".equals(loginInfo.getLoginname())) {
			return;
		}
		
		// 其他地市查个地市的标签
		PropertyFilter filter = new PropertyFilter(SysTag.class,
			       PropertyFilter.MatchType.EQ + "_city", loginInfo.getCity());
		
		
		groupPropertyFilter.append(filter);
    }
    @Override
    @MetaData("修改")
    public HttpHeaders doSave(){
    	return super.doSave();
    }
    @Override
    public HttpHeaders edit() {
        return buildDefaultHttpHeaders("inputBasic");
    }
    
    public Map<String, String> getPrvCityMap() throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	String city = null;
    	if(null != loginInfo && !loginInfo.getLoginname().equals("GZCYKFA0001"))
    		city = loginInfo.getCity();
    	
    	return paramService.getSelectData("PRV_CITY",city);
	}
    public HttpHeaders saveSysTag(){
    	String sysTagStr = getParameter("systag");
    	try{
    		SysTag  sysTag= (SysTag) BeanUtil.jsonToObject(sysTagStr,
        			SysTag.class);
        	sysTagService.saveSysTag(sysTag);
        	setModel(OperationResult.buildSuccessResult("成功"));
    	}catch(Exception e){
    		setModel(OperationResult.buildFailureResult(e.getMessage()));
    	}
    
    	
    	return buildDefaultHttpHeaders();
    }
    public HttpHeaders selection(){
    	String city = getParameter("city");
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
    	try{
    		if(city!=null&&!city.equals("")){
        		//setModel(sysTagService.getODSsysTag(city,pageable));
    			 List<SysTag> sysTagList = sysTagService.getCMPsysTagByInf(city);
    		     PageImpl<SysTag> result = new PageImpl<SysTag>(sysTagList, pageable,sysTagList.size());
    			 setModel(result);
        	}else{
        		setModel(new PageImpl<SysTag>(new ArrayList<SysTag>(), pageable, 0));

        	}
    	}catch(Exception e ){
    		setModel(new PageImpl<SysTag>(new ArrayList<SysTag>(), pageable, 0));
    	}
    	return buildDefaultHttpHeaders();
    }
}
