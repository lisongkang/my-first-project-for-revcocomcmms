package com.maywide.biz.prd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.inter.entity.CityClzParam;
import com.maywide.biz.prd.dao.CityClzParamDao;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
public class CityClzParamService extends BaseService<CityClzParam, Long> {
	
	@Autowired
	private CityClzParamDao cityClzParamDao;
	
	@Autowired
    private PersistentService persistentService;

	
	@Override
	protected BaseDao<CityClzParam, Long> getEntityDao() {
		return cityClzParamDao;
	}

	 public void transGridList(List<CityClzParam> list) throws Exception {
	        for (CityClzParam param : list) {
	        	param.addExtraAttribute("areaname", getAreaname(param.getAreaid()));
	        }
	    }
	 private String getAreaname(Long areaid) throws Exception {
	        PrvArea area = (PrvArea) persistentService.find(PrvArea.class, areaid);
	        if (null != area) {
	            return area.getName();
	        } else {
	            return null;
	        }
	    }
}
