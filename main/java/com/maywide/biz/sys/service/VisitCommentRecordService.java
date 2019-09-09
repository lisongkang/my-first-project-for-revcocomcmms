package com.maywide.biz.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.entity.VisitCommentRecord;
import com.maywide.biz.sys.bo.VisitCommentRecordBO;
import com.maywide.biz.sys.dao.VisitCommentRecordDAO;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
public class VisitCommentRecordService extends BaseService<VisitCommentRecord, Long> {

	@Autowired
	private VisitCommentRecordDAO visitCommentRecordDAO;
	
	@Autowired
    private PersistentService persistentService;
	
	@Override
	protected BaseDao<VisitCommentRecord, Long> getEntityDao() {
		return visitCommentRecordDAO;
	}
	
	public Page findVisitCommentRecordList(VisitCommentRecord commentCord,Pageable pageable) throws Exception{
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if(!loginInfo.getLoginname().equalsIgnoreCase(Constant.ADMINISTRATOR)) {
			commentCord.setCity(loginInfo.getCity());
		}
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("		select a.orderid id,a.custid custid,a.cust_name custName,a.operid operid,");
		sb.append("		a.visit_method visitMethod,a.mobile mobile,a.send_content sendContent,a.send_time sendTime,");
		sb.append("		a.send_status sendStatus,a.city city,a.boss_serinol bossSerinol,a.comment_suggest commentSuggest,");
		sb.append("		a.comment_total commentTotal,a.comment_time commentTime,a.mobile_index mobileIndex,");
		sb.append("		a.deptid,a.send_system sendSystem,");
		sb.append("		b.areaid areaid,c.name areaName");
		sb.append("		from visit_comment_record a,");
		sb.append("		prv_department b, prv_area c");
		sb.append("		where a.deptid = b.deptid");
		sb.append("		and b.areaid = c.areaid");
		if(StringUtils.isNotBlank(commentCord.getCity())) {
			params.add(commentCord.getCity());
			sb.append("	and a.city = ?");
		}
		if(StringUtils.isNotBlank(commentCord.getCustName())) {
			params.add("%"+commentCord.getCustName()+"%");
			sb.append("	and a.cust_name like ?");
		}
		if(StringUtils.isNotBlank(commentCord.getMobile())) {
			params.add(commentCord.getMobile());
			sb.append("	and a.mobile = ?");
		}
		if(StringUtils.isNotBlank(commentCord.getSendStatus())) {
			params.add(commentCord.getSendStatus());
			sb.append("	and a.send_status = ?");
		}
		if(StringUtils.isNotBlank(commentCord.getVisitMethod())) {
			params.add(commentCord.getVisitMethod());
			sb.append("	and a.visit_method = ?");
		}
		if(commentCord.getSendTime()!= null) {
			params.add(commentCord.getSendTime());
			sb.append("	and TO_DAYS(a.send_time) = TO_DAYS(?)");
		}
		 List<VisitCommentRecordBO> recordList = persistentService.find(sb.toString(),
				 VisitCommentRecordBO.class, params.toArray());
	        Page retPage = new PageImpl(recordList, pageable, recordList.size());
	        return retPage;   	
		
	}

}
