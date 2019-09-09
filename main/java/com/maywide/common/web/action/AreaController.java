package com.maywide.common.web.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.SimpleController;

public class AreaController extends SimpleController{
	@Autowired
	private PersistentService persistentService;
	
	public HttpHeaders findAreaList() {
		String city = getParameter("city");
		if (StringUtils.isEmpty(city)) {
			return new DefaultHttpHeaders();
		}
		try {
			PrvArea queryParam = new PrvArea();
			queryParam.setCity(city);
			List<PrvArea> areas = persistentService.find(queryParam);
			setModel(areas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new DefaultHttpHeaders();
	}
	
	
	public HttpHeaders selectAreaMap() {
		String city = getParameter("city");
		String areaid = getParameter("areaid");
		
		PrvArea area = new PrvArea();
		
		if (StringUtils.isEmpty(city)) {
			return new DefaultHttpHeaders();
		}
		
		if (StringUtils.isEmpty(areaid)) {
			return new DefaultHttpHeaders();
		}
		area.setCity(city);
		area.setId(Long.parseLong(areaid));
		
		try {
			List<PrvArea> list = persistentService.find(area);
			setModel(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new DefaultHttpHeaders();
	}
}
