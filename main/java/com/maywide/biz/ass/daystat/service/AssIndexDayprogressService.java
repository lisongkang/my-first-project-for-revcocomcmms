package com.maywide.biz.ass.daystat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.daystat.entity.AssIndexDayprogress;
import com.maywide.biz.ass.daystat.dao.AssIndexDayprogressDao;
import com.maywide.biz.ass.monstat.entity.AssIndexMonprogress;
import com.maywide.biz.ass.monstat.pojo.MonprogressParamBO;
import com.maywide.biz.ass.monstat.service.AssIndexMonprogressService;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssIndexDayprogressService extends BaseService<AssIndexDayprogress,Long>{
    
    @Autowired
    private AssIndexDayprogressDao assIndexDayprogressDao;
    
    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private AssIndexMonprogressService assIndexMonprogressService;

    @Override
    protected BaseDao<AssIndexDayprogress, Long> getEntityDao() {
        return assIndexDayprogressDao;
    }
    
    public PageImpl queDayprogress(Pageable pageable,
			MonprogressParamBO monprogressParamBO) throws Exception {

		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			if (monprogressParamBO.getFirstGridid() == null) {
				List<AssIndexDayprogress> resultList = new ArrayList<AssIndexDayprogress>();
				pageResult = new PageImpl(resultList, pageable, 0);
				return pageResult;
			}
		}
		
		String inSql = getInSql(monprogressParamBO.getStartPeriod(), monprogressParamBO.getEndPeriod());
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.staid as id, ");
        sql.append(" t.tdate, ");
        sql.append(" t.assid, ");
        sql.append(" (select s.asscontent from ass_index_store s where s.assid=t.assid) asscontent, ");
        sql.append(" t.objtype, ");
        sql.append(" t.objid, ");
        sql.append(" (select p.patchname from res_patch p where p.patchid=t.objid) objname, ");
        sql.append(" t.total, ");
        sql.append(" t.curnum, ");
        sql.append(" t.rate ");
        sql.append(" FROM ass_index_dayprogress t ");
        sql.append(" WHERE 1 = 1  ");
		if (monprogressParamBO.getAssid() != null){
			sql.append(" AND t.assid= ? ");
			paramList.add(monprogressParamBO.getAssid());
		}
		
		if (inSql != null){
			sql.append(inSql);
		}
		
		sql.append(assIndexMonprogressService.getPatchidSql(monprogressParamBO));
		
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssIndexDayprogress.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssIndexDayprogress> resultList = page.getResult();
			obtainFirstAndSecondGridInfo(resultList, true);
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<AssIndexDayprogress> resultList = new ArrayList<AssIndexDayprogress>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
    
    public PageImpl queSumDayprogress(Pageable pageable,
			MonprogressParamBO monprogressParamBO) throws Exception {

		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			if (monprogressParamBO.getFirstGridid() == null) {
				List<AssIndexDayprogress> resultList = new ArrayList<AssIndexDayprogress>();
				pageResult = new PageImpl(resultList, pageable, 0);
				return pageResult;
			}
		}
		
		String inSql = getInSql(monprogressParamBO.getStartPeriod(), monprogressParamBO.getEndPeriod());
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        sql.append(" SELECT *  FROM (");
        
        sql.append(" SELECT v.tdate, v.assid, v.asscontent, v.pgridid, ");
        sql.append(" (select gg.gridname from biz_grid_info gg where v.pgridid=gg.gridid) pgrididname, ");
        sql.append(" sum(v.total) total, sum(v.curnum) curnum, ");
        sql.append(" CASE WHEN SUM(v.total)=0 THEN NULL ELSE  ROUND(SUM(v.curnum)/SUM(v.total),2) END  AS rate ");
        sql.append(" FROM (");
        
        sql.append(" SELECT t.staid as id, ");
        sql.append(" t.tdate, ");
        sql.append(" t.assid, ");
        sql.append(" (select s.asscontent from ass_index_store s where s.assid=t.assid) asscontent, ");
        sql.append(" t.objtype, ");
        sql.append(" t.objid, ");
        sql.append(" (select g.statid from biz_grid_info g where g.patchid=t.objid) pgridid, ");
        sql.append(" (select p.patchname from res_patch p where p.patchid=t.objid) objname, ");
        sql.append(" t.total, ");
        sql.append(" t.curnum, ");
        sql.append(" t.rate ");
        sql.append(" FROM ass_index_dayprogress t ");
        sql.append(" WHERE 1 = 1  ");
		if (monprogressParamBO.getAssid() != null){
			sql.append(" AND t.assid= ? ");
			paramList.add(monprogressParamBO.getAssid());
		}
		
		if (inSql != null){
			sql.append(inSql);
		}
		
		sql.append(assIndexMonprogressService.getPatchidSql(monprogressParamBO));
		
		sql.append(" ) v group by v.tdate, v.assid, v.pgridid ");
		
		sql.append(" ) vv ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssIndexDayprogress.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssIndexDayprogress> resultList = page.getResult();
			obtainFirstAndSecondGridInfo(resultList, false);
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<AssIndexDayprogress> resultList = new ArrayList<AssIndexDayprogress>();
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
    	buff.append(" AND t.tdate >= DATE_FORMAT('").append(startDate)
    	.append("', '%Y%m%d')").append(" AND t.tdate <= DATE_FORMAT('").append(endDate)
    	.append("', '%Y%m%d')");
    	
    	// tdate> DATE_FORMAT('20150901','%Y%m%d') AND tdate <= DATE_FORMAT('20150910','%Y%m%d')
    	
    	return buff.toString();
    }
    
    /**
	 * 获取并设置当前片区对应的营维中心和网格
	 * @param dayprogresslist
	 * @throws Exception
	 */
	public void obtainFirstAndSecondGridInfo(List<AssIndexDayprogress> dayprogresslist, boolean isQuePatch) throws Exception{
		for (AssIndexDayprogress assIndexDayprogress : dayprogresslist) {
			Map<String, String> map = null;
			
			if (isQuePatch) {
				map = assIndexMonprogressService.getFirstAndSecondGrid(assIndexDayprogress.getObjid());
			} else {
				BizGridInfo pGridInfo = (BizGridInfo)persistentService.find(BizGridInfo.class, assIndexDayprogress.getPgridid());
				map = assIndexMonprogressService.getFirstAndSecondGrid(pGridInfo);
			}
			
			assIndexDayprogress.setFirstGridName(map.get("FIRST_SECOND_GRID_PATH"));
		}
	}
}
