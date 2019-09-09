package com.maywide.biz.sys.web.action;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.sys.entity.ReturnVisitTemplate;
import com.maywide.biz.sys.service.ReturnVisitTemplateService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.service.BaseService;

public class ReturnVisitTemplateController extends BaseController<ReturnVisitTemplate, Integer> {

	@Autowired
	private ReturnVisitTemplateService returnVisitTemplateService;
	
	@Override
	protected BaseService<ReturnVisitTemplate, Integer> getEntityService() {
		return returnVisitTemplateService;
	}

	@Override
	public HttpHeaders doSave() {
		return super.doSave();
	}

	@Override
	public HttpHeaders findByPage() {
		return super.findByPage();
	}

	public Map<String,String> getTypeMap(){
		Map<String,String> typeMap = new LinkedHashMap<String,String>();
		typeMap.put("0","短信");
		typeMap.put("1","微信公众号推送");
		return typeMap;
	}
	
	public HttpHeaders toTemplatePage() {
        String to = this.getRequiredParameter(PARAM_NAME_FOR_FORWARD_TO);
        String sendtype = this.getParameter("sendtype");
        getRequest().setAttribute("sendtype", sendtype);
        return buildDefaultHttpHeaders(to);
    }
	
}
