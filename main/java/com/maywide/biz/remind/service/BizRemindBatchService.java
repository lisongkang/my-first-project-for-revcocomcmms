package com.maywide.biz.remind.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.entity.CustMarketing;
import com.maywide.biz.remind.entity.BizRemRulecfg;
import com.maywide.biz.remind.entity.BizRemindBatch;
import com.maywide.biz.remind.entity.BizRemindRecord;
import com.maywide.biz.remind.entity.BizRemindWarning;
import com.maywide.biz.remind.pojo.RemindBatchParamBO;
import com.maywide.biz.remind.dao.BizRemindBatchDao;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BizRemindBatchService extends BaseService<BizRemindBatch,Long>{
    
    @Autowired
    private BizRemindBatchDao bizRemindBatchDao;
    
    @Autowired
    private PersistentService persistentService;
    
    @Override
    protected BaseDao<BizRemindBatch, Long> getEntityDao() {
        return bizRemindBatchDao;
    }
    
    public PageImpl queRemindBatchs(Pageable pageable,
    		RemindBatchParamBO remindBatchParamBO) throws Exception {
		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		String inSql = getInSql(remindBatchParamBO.getStartPeriod(), remindBatchParamBO.getEndPeriod());
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.batid as id, ");
        sql.append(" t.remid, ");
        sql.append(" (select d.description from biz_remind_warning d where d.remid=t.remid) description, ");
        sql.append(" t.objtype, ");
        sql.append(" code2name(t.objtype, 'BIZ_REM_OBJTYPE') objtypename, ");
        sql.append(" t.buildate, ");
        sql.append(" t.edate, ");
        sql.append(" t.iscfm, ");
        sql.append(" t.deptid, ");
        sql.append(" (select d.name from prv_department d where d.deptid=t.deptid) deptname, ");
        sql.append(" t.operator, ");
        sql.append(" (select s.name from prv_operator s where s.operid=t.operator) opername, ");
        sql.append(" t.optime ");
        sql.append(" FROM biz_remind_batch t ");
        sql.append(" WHERE 1 = 1  ");
        
        if (StringUtils.isNotEmpty(remindBatchParamBO.getIscfm())){
			sql.append(" AND t.iscfm = ? ");
			paramList.add(remindBatchParamBO.getIscfm());
		}
        
		if (remindBatchParamBO.getRemobjtype() != null) {
			sql.append(" AND t.objtype = ? ");
			paramList.add(remindBatchParamBO.getRemobjtype());
		}
		
		if (inSql != null){
			sql.append(inSql);
		}
		
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), BizRemindBatch.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<BizRemindBatch> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<BizRemindBatch> resultList = new ArrayList<BizRemindBatch>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
    
    private String getInSql(String startDate, String endDate) {
    	if (startDate == null || endDate == null){
    		return null;
    	}
    	
    	startDate = startDate.replaceAll("-", "");
    	endDate = endDate.replaceAll("-", "");
    	
    	StringBuilder buff = new StringBuilder();
    	buff.append(" AND t.buildate >= DATE_FORMAT('").append(startDate)
    	.append("', '%Y%m%d')").append(" AND t.buildate <= DATE_FORMAT('").append(endDate)
    	.append("', '%Y%m%d') ");
    	
    	// tdate> DATE_FORMAT('20150901','%Y%m%d') AND tdate <= DATE_FORMAT('20150910','%Y%m%d')
    	
    	return buff.toString();
    }
    
    public void confirmRemindBatch(String ids) throws Exception {
    	String[] idsArr = ids.split(",");
    	for (int i = 0; i < idsArr.length; i++) {
    		BizRemindBatch bizRemindBatch = new BizRemindBatch();
    		bizRemindBatch.setId(Long.valueOf(idsArr[i]));
    		bizRemindBatch.setIscfm("0"); // 是否确认 0-是 1-否
    		
    		persistentService.update(bizRemindBatch);
		}
    }
    
    private Map<String, List<BizRemindWarning>> groupRemindWarning(List<BizRemindWarning> list) {
    	Map<String, List<BizRemindWarning>> groupMap =  new HashMap<String, List<BizRemindWarning>>();
    	 
    	for (BizRemindWarning bizRemindWarning : list) {
    		String groupKey = bizRemindWarning.getCity()+"_"+bizRemindWarning.getObjtype();
    		List<BizRemindWarning> rwlist = groupMap.get(groupKey);
    		if (rwlist == null) {
    			rwlist = new ArrayList<BizRemindWarning>();
    			rwlist.add(bizRemindWarning);
    			groupMap.put(groupKey, rwlist);
    		} else {
    			rwlist.add(bizRemindWarning);
    		}
		}
    	
    	return groupMap;
    }
    
    /**
     * 生成指定日期的预警结果
     * @param staDayDate
     * @throws Exception
     */
    public void handleRemindBatchEveryday(Date staDayDate) throws Exception{
    	
    	List<BizRemindWarning> remindWarningList = queRemindWarnings(staDayDate);
    	
    	for (BizRemindWarning bizRemindWarning : remindWarningList) {
    		Long remid = bizRemindWarning.getId();
    		BizRemRulecfg rulecfg = getRemRulecfg(remid);
    		
    		if (rulecfg != null) {
    			String trivalues = rulecfg.getTrivalues(); // 超时时长（小时）
        		String tritype = rulecfg.getTritype(); // 0-任务超时提醒
        		if ("0".equals(tritype)) {
        			Long nums = rulecfg.getNums(); // 提醒次数（每一次提醒会在BIZ_REMIND_BATCH表插入一条记录）
            		int batchCount = getRemindBatchCount(remid);
            		if (batchCount >= nums) {
            			continue;
            		}

            		// 添加BizRemindBatch
					BizRemindBatch remindBatch = addRemindBatch(remid,
							bizRemindWarning.getObjtype(), rulecfg.getIscfm(),
							bizRemindWarning.getDeptid(),
							bizRemindWarning.getOperid());
            		
            		if ("0".equals(bizRemindWarning.getObjtype())) { // 0-客户营销任务
            			// 添加BizRemindRecord列表
            			addRemindRecords(staDayDate, bizRemindWarning, trivalues, remindBatch);
            		}
        		}
    		}
    			
		}
    }
    
	private void addRemindRecords(Date staDayDate,
			BizRemindWarning bizRemindWarning, String trivalues,
			BizRemindBatch remindBatch) throws Exception {
		
		List<CustMarketing> custMarketingList = getCustMarketingList(
				staDayDate, bizRemindWarning, trivalues);
		
		for (CustMarketing custMarketing : custMarketingList) {
			BizRemindRecord bizRemindRecord = new BizRemindRecord();
			bizRemindRecord.setBatid(remindBatch.getId());
			bizRemindRecord.setObjid(custMarketing.getId() + "");
			bizRemindRecord.setObjcaption(custMarketing.getKnowname());
			bizRemindRecord.setCustid(custMarketing.getCustid());
			bizRemindRecord.setStatus("0"); // 0-未读 1-已读

			persistentService.save(bizRemindRecord);
		}
	}
    
	private List<CustMarketing> getCustMarketingList(Date staDayDate,
			BizRemindWarning bizRemindWarning, String trivalues)
			throws Exception {
		
		String objidsSQL = getObjidsSQL(bizRemindWarning);

		List plist = new ArrayList();
		StringBuilder sql = new StringBuilder();
    	
    	sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.markid as id, ");
        sql.append(" t.custid, ");
        sql.append(" t.knowid, ");
        sql.append(" (select k.knowname from prd_salespkg_know k where k.knowid=t.knowid) knowname ");
        sql.append(" FROM biz_market_batch b, biz_cust_marketing t ");
        sql.append(" WHERE 1 = 1  and t.batchno=b.batchno and t.dealstatus=0 ");
        
        if (objidsSQL != null) {
        	sql.append(objidsSQL);
        }
        
        sql.append(getAppDateSQL(staDayDate, trivalues));
        
        sql.append(")v ");
    	
		List<CustMarketing> resultlist = persistentService.find(sql.toString(),
				CustMarketing.class, plist.toArray());
		
    	if (resultlist != null && resultlist.size() > 0) {
    		return resultlist;
    	} else {
    		List<CustMarketing> emptylist = new ArrayList<CustMarketing>();
    		return emptylist;
    	}
    }
    
    private String getAppDateSQL(Date staDayDate, String trivalues) {
		String _staDate = DateUtils.getFormatDateString(staDayDate, "yyyy-MM-dd HH:mm:ss");
		StringBuilder buff = new StringBuilder();
		
		// AND DATE_ADD(t.`APPDATE`, INTERVAL '20' HOUR) <= STR_TO_DATE('2015-10-22 11:11:11', '%Y-%m-%d %h:%i:%s');
		buff.append(" AND DATE_ADD(b.appdate, INTERVAL ").append(trivalues)
				.append(" HOUR) <= STR_TO_DATE('").append(_staDate)
				.append("', '%Y-%m-%d %H:%i:%s')");
		
		return buff.toString();
    }
    
    private String getObjidsSQL(BizRemindWarning bizRemindWarning) {
    	StringBuilder buff = new StringBuilder();
    	String objids = bizRemindWarning.getObjids();
		if (!"*".equals(objids)) {
			buff.append("AND FIND_IN_SET (b.recid, '").append(objids).append("')");
			return buff.toString();
		} else {
			return null;
		}
    }
    
    private int getRemindBatchCount(Long remid) throws Exception {
    	BizRemindBatch queRemindBatch = new BizRemindBatch();
    	queRemindBatch.setRemid(remid);
		
		List<BizRemindBatch> list = persistentService.find(queRemindBatch);
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
    }
    
	private BizRemindBatch addRemindBatch(Long remid, String objtype, String iscfm, Long deptid,
			Long operid) throws Exception {
		Date edate = DateUtils.parseDate("2099-12-31");
		
		BizRemindBatch remindBatch = new BizRemindBatch();
		remindBatch.setRemid(remid);
		remindBatch.setIscfm(iscfm);
		remindBatch.setEdate(edate); // 2099-12-31
		remindBatch.setBuildate(new Date());
		remindBatch.setObjtype(objtype); // 0-客户营销任务
		remindBatch.setOperator(operid);
		remindBatch.setDeptid(deptid);
		remindBatch.setOptime(new Date());

		persistentService.save(remindBatch);
		
		return remindBatch;
	}
    
    private BizRemRulecfg getRemRulecfg(Long remid) throws Exception {
    	BizRemRulecfg queRulecfg = new BizRemRulecfg();
    	queRulecfg.setRemid(remid);
    	List<BizRemRulecfg> rulelist = persistentService.find(queRulecfg);
    	if (rulelist != null && rulelist.size() > 0) {
    		BizRemRulecfg rulecfg = rulelist.get(0);
    		return rulecfg;
    	}
    	return  null;
    }
    
    private List<BizRemindWarning> queRemindWarnings(Date staDayDate) throws Exception{
    	BizRemindWarning bizRemindWarning = new BizRemindWarning();
    	
    	String staDate = DateUtils.getFormatDateString(staDayDate, "yyyyMMdd");
		String _staDate = DateUtils.getFormatDateString(staDayDate, "yyyy-MM-dd");

    	List plist = new ArrayList();
    	StringBuilder sql = new StringBuilder();
    	
    	sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.remid as id, ");
        sql.append(" t.objtype, ");
        sql.append(" code2name(t.objtype, 'BIZ_REM_OBJTYPE') objtypename, ");
        sql.append(" t.objids, ");
//        sql.append(" (SELECT GROUP_CONCAT(r.knowid) FROM biz_market_batch r WHERE FIND_IN_SET(r.recid ,t.objids)) objidsname, ");
        sql.append(" (t.objids) objidsname, ");
        sql.append(" t.description, ");
        sql.append(" t.pri, ");
        sql.append(" t.edate, ");
        sql.append(" t.opdate, ");
        sql.append(" t.operid, ");
        sql.append(" (select s.name from prv_operator s where s.operid=t.operid) opername, ");
        sql.append(" t.deptid, ");
        sql.append(" (select d.name from prv_department d where d.deptid=t.deptid) deptname, ");
        sql.append(" t.city ");
        sql.append(" FROM biz_remind_warning t ");
        sql.append(" WHERE 1 = 1  ");
        sql.append(" AND t.edate >= STR_TO_DATE(?, '%Y-%m-%d') ");
    	plist.add(_staDate);
    	sql.append(" ORDER BY t.pri DESC");
    	
    	sql.append(")v ");
    	
		List<BizRemindWarning> resultlist = persistentService.find(sql.toString(),
				BizRemindWarning.class, plist.toArray());

		if (resultlist != null && resultlist.size() > 0) {
			return resultlist;
		} else {
			List<BizRemindWarning> emptylist = new ArrayList<BizRemindWarning>();
			return emptylist;
		}
    }
    
}
