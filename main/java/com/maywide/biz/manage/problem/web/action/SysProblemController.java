package com.maywide.biz.manage.problem.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.manage.problem.entity.SysProblem;
import com.maywide.biz.manage.problem.pojo.SysProblemPo;
import com.maywide.biz.manage.problem.service.SysProblemService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.support.Page;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;

@MetaData("[com.maywide].manage.tag.entity.SysProblem管理")
public class SysProblemController extends BaseController<SysProblem,Long>{

	@Autowired
	private SysProblemService sysProblemService;
	
	private SysProblemPo sysProblemPo;
	
	@Autowired
	private ParamService paramService;
	

	@Override
	protected BaseService<SysProblem, Long> getEntityService() {
		return sysProblemService;
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
    public HttpHeaders edit()  {
    	try{
    		SysProblem sysProblem =(SysProblem) sysProblemService.findSysProblemById(bindingEntity.getId());
    		Map<String, Object> extraAttributes = new HashMap<String, Object>();
    		extraAttributes.put("areaname", sysProblem.getAreaname());
    		extraAttributes.put("dealopername", sysProblem.getDealoperdeptname());
    		extraAttributes.put("dealoperdeptname", sysProblem.getDealoperdeptname());
    		extraAttributes.put("subopername", sysProblem.getSubopername());
    		extraAttributes.put("suboperdeptname", sysProblem.getSuboperdeptname());
    		bindingEntity.setExtraAttributes(extraAttributes);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return buildDefaultHttpHeaders("inputBasic");
    }
    
    public HttpHeaders inputTabs() {
        return buildDefaultHttpHeaders("inputTabs");
    }
    /**
     * 对待处理状态的问题进行处理
     * @return
     */
    public HttpHeaders doDeal(){
    	
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	bindingEntity.setDealoperid(loginInfo.getOperid());
    	bindingEntity.setDealoperdept(loginInfo.getDeptid());
    	bindingEntity.setPlstate(Long.parseLong(BizConstant.SYS_PLSTATE.SYS_PLSTATE_WAITFINISH));
    	bindingEntity.setDealtime(new Date());
    	
    	//下面添加的是为了页面显示
    	bindingEntity.setDealoperdeptname(loginInfo.getDeptname());
    	bindingEntity.setDealopername(loginInfo.getName());
    	return super.doSave();
    }
    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
    	try{
    		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    		if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
    			sysProblemPo.setCity(loginInfo.getCity());
    		}
    		setModel(sysProblemService.findbyParams(sysProblemPo,pageable));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return buildDefaultHttpHeaders();
    }
    
    protected void appendFilterProperty(GroupPropertyFilter groupPropertyFilter){
    	String subtime = getParameter("subtime");
    	if(StringUtils.isNotEmpty(subtime)){
    		PropertyFilter filter = new PropertyFilter(SysProblem.class,
    				PropertyFilter.MatchType.BT + "_subtime",subtime,subtime);
    		groupPropertyFilter.getFilters().add(filter);
    	}
    	
    }
    
    public SysProblemPo getSysProblemPo() {
		return sysProblemPo;
	}

	public void setSysProblemPo(SysProblemPo sysProblemPo) {
		this.sysProblemPo = sysProblemPo;
	}
	//获取问题类型
    public Map<String, String> getPltypeMap() throws Exception {
    	return paramService.getSelectData("SYS_PLTYPE");
    }
    
   //获取问题状态
    public Map<String, String> getPlstateMap() throws Exception {
    	return paramService.getSelectData("SYS_PLSTATE");
    }
    
   //获取地市
    public Map<String, String> getCityMap() throws Exception {
    	return paramService.getSelectData("PRV_CITY");
    }
}
