package com.maywide.biz.sys.web.action;

import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.sys.entity.SysCustVisitRule;
import com.maywide.biz.sys.service.SysCustVisitRuleService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;

public class SysCustVisitRuleController  extends BaseController<SysCustVisitRule,Long>{

	@Autowired
	private SysCustVisitRuleService sysCustVisitRuleService;
	
	
	
	@Override
	public HttpHeaders doDelete() {
		return super.doDelete();
	}

	@Override
	public HttpHeaders doSave() {
		return super.doSave();
	}

	@Override
	public HttpHeaders edit() {
		return super.edit();
	}

	@Override
	protected BaseService<SysCustVisitRule, Long> getEntityService() {
		return sysCustVisitRuleService;
	}

	@Override
	protected HttpHeaders findByPage() {
		
		return super.findByPage();
	}
	
	public HttpHeaders findPageByCity() {
		try {
			Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
			setModel(sysCustVisitRuleService.findPageByCity(pageable));
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
		return buildDefaultHttpHeaders();
	}
	
	public Map<String,String> getCityMap(){
		try {
			Map<String,String> cityMap = sysCustVisitRuleService.getCityMap();
			return cityMap;
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
	}
	
	public Map<String,String> getAreaMap(String city){
		try {
			Map<String,String> areaMap = sysCustVisitRuleService.getAreaMap(city);
			return areaMap;
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
	}
	
	public Map<String,String> getOpcodeMap(){
		Map<String,String> opcodeMap = sysCustVisitRuleService.getOpcodeMap();
		return opcodeMap;
	}
	
	public Map<String,String> getSendMethodMap(){
		Map<String,String> sendMethodMap = sysCustVisitRuleService.getSendMethodMap();
		return sendMethodMap;
	}
	
	public Map<String,String> getTypeMap(){
		Map<String,String> typeMap = sysCustVisitRuleService.getTypeMap();
		return typeMap;
	}
	
	public Map<String,String> getIndexMap(){
		Map<String,String> indexMap = sysCustVisitRuleService.getIndexMap();
		return indexMap;
	}
}
