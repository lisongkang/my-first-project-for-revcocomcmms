package com.maywide.biz.sys.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.inter.entity.VisitCommentRecord;
import com.maywide.biz.sys.service.VisitCommentRecordService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.util.DateUtils;

public class VisitCommentRecordController extends BaseController<VisitCommentRecord, Long> {

	@Autowired
	private VisitCommentRecordService visitCommentRecordService;
	
	@Override
	protected BaseService<VisitCommentRecord, Long> getEntityService() {
		return visitCommentRecordService;
	}

	@Override
	public HttpHeaders findByPage() {
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
        appendFilterProperty(groupFilter);
        String sendMethod = getRequest().getParameter("sendMethod1");
        String sendStatus = getRequest().getParameter("sendStatus1");
        String sendTime = getRequest().getParameter("sendTime1");
        String custName = getRequest().getParameter("custName1");
        String mobile = getRequest().getParameter("mobile1");
        
        VisitCommentRecord visitCommentRecord = new VisitCommentRecord();
        if(StringUtils.isNotBlank(sendMethod)) {
        	visitCommentRecord.setVisitMethod(sendMethod);
        }
        if(StringUtils.isNotBlank(sendStatus)) {
        	visitCommentRecord.setSendStatus(sendStatus);
        }
        if(StringUtils.isNotBlank(sendTime)) {
        	visitCommentRecord.setSendTime(DateUtils.parseDate(sendTime, DateUtils.DEFAULT_DATE_FORMATER));
        }
        if(StringUtils.isNotBlank(custName)) {
        	visitCommentRecord.setCustName(custName);
        }
        if(StringUtils.isNotBlank(mobile)) {
        	visitCommentRecord.setMobile(mobile);
        }
        try {
			Page result = visitCommentRecordService.findVisitCommentRecordList(visitCommentRecord, pageable);
			setModel(result);
		} catch (Exception e) {
			throw new WebException("错误异常:"+e.getMessage());
		}
        
        
		  return buildDefaultHttpHeaders();
	}
	
	

}
