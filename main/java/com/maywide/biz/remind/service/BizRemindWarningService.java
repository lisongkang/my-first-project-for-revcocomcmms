package com.maywide.biz.remind.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.remind.entity.BizRemRulecfg;
import com.maywide.biz.remind.entity.BizRemindBatch;
import com.maywide.biz.remind.entity.BizRemindWarning;
import com.maywide.biz.remind.pojo.RemindWarningParamBO;
import com.maywide.biz.remind.dao.BizRemindWarningDao;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BizRemindWarningService extends BaseService<BizRemindWarning,Long>{
    
    @Autowired
    private BizRemindWarningDao bizRemindWarningDao;
    
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<BizRemindWarning, Long> getEntityDao() {
        return bizRemindWarningDao;
    }
    
    public PageImpl queRemindWarnings(Pageable pageable,
    		RemindWarningParamBO remindWarningParamBO) throws Exception {
		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		String inSql = getInSql(remindWarningParamBO.getStartPeriod(), remindWarningParamBO.getEndPeriod());
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
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
        
        if (remindWarningParamBO.getCity() != null){
			sql.append(" AND t.city = ? ");
			paramList.add(remindWarningParamBO.getCity());
		}
        
		if (remindWarningParamBO.getDeptid() != null){
			sql.append(" AND t.deptid = ? ");
			paramList.add(remindWarningParamBO.getDeptid());
		}
		
		if (remindWarningParamBO.getRemobjtype() != null) {
			sql.append(" AND t.objtype = ? ");
			paramList.add(remindWarningParamBO.getRemobjtype());
		}
		
		if (inSql != null){
			sql.append(inSql);
		}
		
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), BizRemindWarning.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<BizRemindWarning> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<BizRemindWarning> resultList = new ArrayList<BizRemindWarning>();
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
    	buff.append(" AND t.opdate >= DATE_FORMAT('").append(startDate)
    	.append("', '%Y%m%d')").append(" AND t.opdate <= DATE_FORMAT('").append(endDate)
    	.append("', '%Y%m%d') ");
    	
    	// tdate> DATE_FORMAT('20150901','%Y%m%d') AND tdate <= DATE_FORMAT('20150910','%Y%m%d')
    	
    	return buff.toString();
    }
    
    public List<PrvDepartment> getDepts()  throws Exception {
     	LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
     	String city = loginInfo.getCity();
     	
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			List<PrvDepartment> deptlist = new ArrayList<PrvDepartment>(); 
			PrvDepartment prvDepartment = new PrvDepartment();
			prvDepartment.setId(loginInfo.getDeptid());
			prvDepartment.setName(loginInfo.getDeptname());
			deptlist.add(prvDepartment);
			
			return deptlist;
		}
     	
     		
        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");

        sql.append(" SELECT s.deptid id,");
        sql.append(" s.kind ,");
        sql.append(" s.name ,");
        sql.append(" s.preid ,");
        sql.append(" s.areaid ,");
        sql.append(" s.deplevel ,");
        sql.append(" s.sortby ,");
        sql.append(" s.depno ,");
        sql.append(" s.memo ,");
        sql.append(" s.city");
        sql.append("   FROM PRV_DEPARTMENT s ");
        sql.append("  WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(city)) {
            sql.append("    AND s.city = ? ");
            paramList.add(city);
        }

        sql.append(" )v1  ");

        List<PrvDepartment> deptList = persistentService.find(sql.toString(),
        		PrvDepartment.class, paramList.toArray());

        if (!BeanUtil.isListNull(deptList)) {
        	return deptList;
        } else {
        	List<PrvDepartment> emptyList = new ArrayList<PrvDepartment>();
        	return emptyList;
        }
    }
    
    public BizRemindWarning getRemindWarning(Long id) throws Exception {

    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
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
		if (id != null){
			sql.append(" AND t.remid= ? ");
			paramList.add(id);
		}
		
		sql.append(" ) v ");
		
		persistentService.clear();
		List<BizRemindWarning> blist = persistentService.find(sql.toString(), BizRemindWarning.class, paramList.toArray());
		
		if (blist != null && blist.size() > 0){
			return blist.get(0);
		} else {
			return null;
		}
    }
    
    public void checkRemindWarning(BizRemindWarning bizRemindWarning) throws Exception {
    	
    	BizRemindWarning queBO = new BizRemindWarning();
    	queBO.setCity(bizRemindWarning.getCity());
    	queBO.setObjtype(bizRemindWarning.getObjtype());// 0-客户营销任务
    	queBO.setObjids("*"); // 所有营销任务
    	List<BizRemindWarning> list = persistentService.find(queBO);
    	if (list != null && list.size() > 0) {
    		throw new ServiceException("已经配置所有客户营销任务，无需再配置。");
    	}
    	
    	checkObjids(bizRemindWarning);
    	
    }
    
    private void checkObjids(BizRemindWarning bizRemindWarning) throws Exception {
    	String objids = bizRemindWarning.getObjids();
    	if ("*".equals(objids)) {
    		BizRemindWarning queBO = new BizRemindWarning();
        	queBO.setCity(bizRemindWarning.getCity());
        	queBO.setObjtype(bizRemindWarning.getObjtype());// 0-客户营销任务
        	List<BizRemindWarning> list = persistentService.find(queBO);
        	if (list != null && list.size() > 0) {
        		throw new ServiceException("已经配置有了客户营销任务，不允许再配置所有客户营销任务。");
        	}
    	}
    	
    	List<String> recordList = new ArrayList<String>();
    	String[] objidArr = objids.split(",");
    	for (int i = 0; i < objidArr.length; i++) {
    		BizRemindWarning queBO = new BizRemindWarning();
        	queBO.setCity(bizRemindWarning.getCity());
        	queBO.setObjtype(bizRemindWarning.getObjtype());// 0-客户营销任务
        	List<BizRemindWarning> list = persistentService.find(queBO);
        	if (list != null && list.size() > 0) {
        		for (BizRemindWarning rw : list) {
					if(StringUtils.isEmpty(rw.getObjids())) {
						continue;
					}
					
					// biz_remind_warning表中已经存在objid
					if (rw.getObjids().indexOf(objidArr[i]) != -1) {
						recordList.add(objidArr[i]);
					}
				}
        		
        	}
		}
    	
    	if (recordList.size() > 0) {
    		StringBuilder buff = new StringBuilder();
    		String strObjids = "";
    		for (String objid : recordList) {
    			strObjids += objid + ",";
    		}
    		strObjids = strObjids.substring(0,strObjids.length()-1);
    		buff.append("已经配置了客户营销任务[").append(strObjids).append("], 请重新选择对象。");
    		throw new ServiceException(buff.toString());
    	}
    	
    }
    
    public void doDelete(String[] ids) throws Exception {
    	for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			
			BizRemRulecfg rulecfg = new BizRemRulecfg();
			rulecfg.setRemid(id);
			List<BizRemRulecfg> cfglist = persistentService.find(rulecfg);
			for (BizRemRulecfg bizRemRulecfg : cfglist) {
				persistentService.delete(bizRemRulecfg);
			}
			
			BizRemindWarning rw = (BizRemindWarning)persistentService.find(BizRemindWarning.class, id);
			persistentService.delete(rw);
		}
    }
}
