package com.maywide.biz.remind.service;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.remind.entity.BizRemindRecord;
import com.maywide.biz.remind.pojo.RemindRecordParamBO;
import com.maywide.biz.remind.dao.BizRemindRecordDao;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BizRemindRecordService extends BaseService<BizRemindRecord,Long>{
    
    @Autowired
    private BizRemindRecordDao bizRemindRecordDao;
    
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<BizRemindRecord, Long> getEntityDao() {
        return bizRemindRecordDao;
    }
    
    public PageImpl queRemindRecords(Pageable pageable, RemindRecordParamBO remindRecordParamBO) throws Exception{
		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.recid as id, ");
        sql.append(" t.batid, ");
        sql.append(" t.objcaption, ");
        sql.append(" t.custid, ");
        sql.append(" m.name, ");
        sql.append(" m.whladdr, ");
        sql.append(" m.areaid, ");
        sql.append(" (select s.name from prv_area s where s.areaid=m.areaid) areaname, ");
        sql.append(" m.knowid, ");
        sql.append(" (select k.knowname from prd_salespkg_know k where k.knowid=m.knowid) knowname, ");
        sql.append(" m.areamger, ");
        sql.append(" (select o.name from prv_operator o where o.operid=m.areamger) areamgername, ");
        sql.append(" t.status ");
        
        sql.append(" FROM biz_remind_record t, biz_cust_marketing m ");
        sql.append(" WHERE 1 = 1 and t.objid=m.markid ");
        
        if (remindRecordParamBO.getBatid() != null){
			sql.append(" AND t.batid = ? ");
			paramList.add(remindRecordParamBO.getBatid());
		}
        
        if (remindRecordParamBO.getKnowid() != null){
			sql.append(" AND m.knowid = ? ");
			paramList.add(remindRecordParamBO.getKnowid());
		}
        
		if (remindRecordParamBO.getAreaid() != null){
			sql.append(" AND m.areaid = ? ");
			paramList.add(remindRecordParamBO.getAreaid());
		}
		
		if (StringUtils.isNotEmpty(remindRecordParamBO.getStatus())) {
			sql.append(" AND t.status = ? ");
			paramList.add(remindRecordParamBO.getStatus());
		}
		
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), BizRemindRecord.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<BizRemindRecord> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<BizRemindRecord> resultList = new ArrayList<BizRemindRecord>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
}
