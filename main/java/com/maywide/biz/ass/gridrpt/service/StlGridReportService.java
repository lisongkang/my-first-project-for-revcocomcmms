package com.maywide.biz.ass.gridrpt.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.gridrpt.entity.StlGridDetail;
import com.maywide.biz.ass.gridrpt.entity.StlGridReport;
import com.maywide.biz.ass.gridrpt.dao.StlGridReportDao;
import com.maywide.biz.ass.monstat.pojo.MonprogressParamBO;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.cons.Constant;
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
public class StlGridReportService extends BaseService<StlGridReport,Long>{
    
    @Autowired
    private StlGridReportDao stlGridReportDao;
    
    @Autowired
    private PersistentService persistentService;
    
    private DateFormat df = new SimpleDateFormat("yyyyMM");

    @Override
    protected BaseDao<StlGridReport, Long> getEntityDao() {
        return stlGridReportDao;
    }
    
    /**
     * 查询网格报表
     * @param pageable
     * @param monprogressParamBO
     * @param startMonth
     * @param endMonth
     * @return
     * @throws Exception
     */
    public PageImpl queGridReportList(Pageable pageable,
			MonprogressParamBO monprogressParamBO, String startMonth,
			String endMonth) throws Exception {

		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			if (monprogressParamBO.getFirstGridid() == null) {
				List<StlGridReport> resultList = new ArrayList<StlGridReport>();
				pageResult = new PageImpl(resultList, pageable, 0);
				return pageResult;
			}
		}
		
		String inSql = getInSql(startMonth, endMonth);
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.recid as id, ");
        sql.append(" t.feemonth, ");
        sql.append(" t.gridid, ");
        sql.append(" (SELECT gridname FROM biz_grid_info k WHERE k.gridid=t.gridid) gridname, ");
        sql.append(" t.pgridid, ");
        sql.append(" t.permark, ");
        sql.append(" t.nums, ");
        sql.append(" t.amount, ");
        sql.append(" t.setamount, ");
        sql.append(" t.netamount, ");
        sql.append(" t.optime ");
        sql.append(" FROM stl_grid_report t ");
        sql.append(" WHERE 1 = 1  ");
		if (monprogressParamBO.getFirstGridid() != null){
//			sql.append(" AND t.gridid= ? ");
//			paramList.add(monprogressParamBO.getFirstGridid());
			
			// 查询以firstGrid为根节点的树上的所有管理网格
			sql.append(gridSQL(monprogressParamBO.getFirstGridid()));
		}
		
		if (inSql != null){
			sql.append(" AND t.feemonth in  " + inSql);
		}
		
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), StlGridReport.class, paramList.toArray());
		
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<StlGridReport> resultList = page.getResult();
			obtainReportList(resultList);
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<StlGridReport> resultList = new ArrayList<StlGridReport>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
    
    private String gridSQL(Long firstGridid) throws Exception {
    	List<BizGridInfo> list = getSubgridsByGridid(firstGridid);
    	BizGridInfo pGrid = (BizGridInfo)persistentService.find(BizGridInfo.class, firstGridid);
    	list.add(pGrid);
    	
    	if (list == null || list.size() == 0) {
	    	return "";	
    	}
    	StringBuilder buff = new StringBuilder();
    	buff.append(" AND t.gridid in ( ");
    	for (BizGridInfo bizGridInfo : list) {
    		buff.append(bizGridInfo.getId()).append(',');
		}
    	buff.delete(buff.length()-1, buff.length());
    	buff.append(" )");
    	
    	return buff.toString();
    	
    }
    
    public List<BizGridInfo> getSubgridsByGridid(Long pgridid) throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	Long operId = loginInfo.getOperid();
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        // SELECT * FROM biz_grid_info WHERE FIND_IN_SET(previd, FatherList(4592)) AND gtype = '1';
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.gridid as id, ");
        sql.append("t.gridcode, ");
        sql.append("t.gridname, ");
        sql.append("t.gtype, ");
        sql.append("t.mnrid, ");
        sql.append("t.prio, ");
        sql.append("t.previd, ");
        sql.append("t.patchid, ");
        sql.append("(select p.patchname from res_patch p where p.patchid=t.patchid) patchname, ");
        sql.append("t.memo ");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1  ");
        sql.append(" AND gtype = '0' "); // 0-管理网格 1-片区网格
		if (pgridid != null){
			sql.append(" AND FIND_IN_SET(t.previd, FatherList(?)) ");
			paramList.add(pgridid);
		} 
		
		sql.append(" ) v ");
		
		persistentService.clear();
		List<BizGridInfo> gridlist = persistentService.find(sql.toString(), BizGridInfo.class, paramList.toArray());
		if (gridlist != null && gridlist.size() > 0) {
			return gridlist;
    	} else {
    		List<BizGridInfo> emptylist = new ArrayList<BizGridInfo>();
    		return emptylist;
    	}
    	
    }
    
    /**
     * 获取网格经理信息
     * @param resultList
     * @throws Exception
     */
    private void obtainReportList(List<StlGridReport> resultList) throws Exception{
    	for (StlGridReport stlGridReport : resultList) {
    		Long gridid = stlGridReport.getGridid();
    		
    		List<BizGridManager> list = getOperManagerList(gridid);
    		if (list != null && list.size() > 0) {
    			BizGridManager bizGridManager = list.get(0);
    			PrvOperator PrvOperator = (PrvOperator)persistentService.find(PrvOperator.class, bizGridManager.getOperid());
    			stlGridReport.setGridmanager(PrvOperator.getName());
    		} else {
    			stlGridReport.setGridmanager("未知");
    		}
		}
    }
    
    /**
	 * 获取查询月份的in SQL语句
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
    private String getInSql(String startMonth, String endMonth) {
    	if (startMonth == null || endMonth == null){
    		return null;
    	}
    	StringBuilder b = new StringBuilder();
    	try {
			
    		String startDate = startMonth;
			String endDate = endMonth;
			
			Calendar startDateCal = Calendar.getInstance();
	        startDateCal.setTime(df.parse(startDate));
	        
	        Calendar endDateCal = Calendar.getInstance();
	        endDateCal.setTime(df.parse(endDate));
				
	        String str = "";
			int monthCount = getDifferMonth(startDateCal, endDateCal);
			for (int i = 0; i < monthCount; i++) {
				String strMonth = df.format(startDateCal.getTime());
				str += "," + strMonth;
				startDateCal.add(Calendar.MONTH,1);
			}
			
			if (StringUtils.isEmpty(str)){
				return null;
			}
			
			str = str.substring(1, str.length());
			b.append('(').append(str).append(')');
			return b.toString();
    		
		} catch (Exception e) {
			return null;
		}
    }
    
	/**
	 * 计算两个时间之间的月数
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @return int
	 */
	public int getDifferMonth(Calendar startDateCal, Calendar endDateCal) 
	{
		try {
            if (endDateCal.before(startDateCal)){
            	return 0;
            }
			
        	int beginYear = startDateCal.get(Calendar.YEAR); 
        	int beginMonth = startDateCal.get(Calendar.MONTH);

        	int endYear = endDateCal.get(Calendar.YEAR); 
        	int endMonth = endDateCal.get(Calendar.MONTH);
			
        	return (endYear-beginYear)*12+(endMonth-beginMonth)+1;
		} catch (Exception e) {
			return 1;
		}
	}
    
	/**
	 * 获取报表明细
	 * @param pageable
	 * @param rptid
	 * @return
	 * @throws Exception
	 */
    public PageImpl doDetail(Pageable pageable, String rptid) throws Exception {

    	StlGridReport report = (StlGridReport)persistentService.find(StlGridReport.class, Long.parseLong(rptid));
    	
    	String feemonth = report.getFeemonth();
    	Long gridid = report.getGridid();
    	Long pgridid = report.getPgridid();
    	String permark = report.getPermark();
    	
		PageImpl pageResult = null;
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.recid as id, ");
        sql.append(" t.feemonth, ");
        sql.append(" t.gridid, ");
        sql.append(" t.pgridid, ");
        sql.append(" t.servid, ");
        sql.append(" t.patchid, ");
        sql.append(" (select p.patchname from res_patch p where p.patchid=t.patchid) patchname, ");
        sql.append(" t.servtype, ");
        sql.append(" code2name(t.servtype, 'SYS_SERV_TYPE') servtypename, ");
        sql.append(" t.permark, ");
        sql.append(" code2name(t.permark, 'SYS_PERMARK') permarkname, ");
        sql.append(" t.amount, ");
        sql.append(" t.setamount, ");
        sql.append(" t.netamount, ");
        sql.append(" t.ruleid ");
        sql.append(" FROM stl_grid_detail t, stl_grid_report s ");
        sql.append(" WHERE 1 = 1 AND t.feemonth=s.feemonth AND t.gridid=s.gridid "
        		+ " AND t.pgridid=s.pgridid AND t.permark=s.permark ");
		if (StringUtils.isNotEmpty(feemonth)){
			sql.append(" AND t.feemonth= ? ");
			paramList.add(feemonth);
		}
		
		if (gridid != null){
			sql.append(" AND t.gridid= ? ");
			paramList.add(gridid);
		}
		
		if (pgridid != null){
			sql.append(" AND t.pgridid= ? ");
			paramList.add(pgridid);
		}
		
		if (StringUtils.isNotEmpty(permark)){
			sql.append(" AND t.permark= ? ");
			paramList.add(permark);
		}
		
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), StlGridDetail.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<StlGridDetail> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<StlGridDetail> resultList = new ArrayList<StlGridDetail>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
    
    /**
     * 获取操作员对应的网格列表
     * @param operid
     * @return
     * @throws Exception
     */
    public List<BizGridManager> getOperGrididList(Long operid) throws Exception {
    	BizGridManager gridManager = new BizGridManager();
    	gridManager.setOperid(operid);
    	
    	List<BizGridManager> gridManagerList = persistentService.find(gridManager);
		if (gridManagerList != null || gridManagerList.size() > 0) {
			return gridManagerList;
		} else {
			List<BizGridManager> emptyList = new ArrayList<BizGridManager>();
			return emptyList;
		}
    }
    
    /**
     * 获取网格对应的网格经理
     * @param gridid
     * @return
     * @throws Exception
     */
    public List<BizGridManager> getOperManagerList(Long gridid) throws Exception {
    	BizGridManager gridManager = new BizGridManager();
    	gridManager.setGridid(gridid);
    	gridManager.setIsMain("Y");
    	List<BizGridManager> gridManagerList = persistentService.find(gridManager);
		if (gridManagerList != null || gridManagerList.size() > 0) {
			return gridManagerList;
		} else {
			List<BizGridManager> emptyList = new ArrayList<BizGridManager>();
			return emptyList;
		}
    }
    
    /**
     * 获取所有的营维中心列表
     * @return
     * @throws Exception
     */
    public Map<String, String> getFirstGrididMap() throws Exception {
    	Map<String, String> map = new HashMap<String,String>();
    	
    	// 保存assindexstore对象
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		List<BizGridManager> list = getOperGrididList(loginInfo.getOperid());
		if (list == null || list.size() == 0) {
			return map;
		}
		
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		
		for (BizGridManager bizGridManager : list) {
			BizGridInfo gridInfo = (BizGridInfo)persistentService.find(BizGridInfo.class, bizGridManager.getGridid());
			map.put(gridInfo.getId()+"", gridInfo.getGridname());
		}
		
		// 权限过滤，高权限：取该操作员上一级网格，列出上一级的网格下的所有子网格； 
		// 	       非高权限：取操作员所属的网格
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			return map;
		}
		
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.gridid as id, ");
        sql.append(" t.gridcode, ");
        sql.append(" t.gridname, ");
        sql.append(" t.gtype, ");
        sql.append(" t.mnrid, ");
        sql.append(" t.prio, ");
        sql.append(" t.previd, ");
        sql.append(" t.patchid, ");
        sql.append(" (select p.patchname from res_patch p where p.patchid=t.patchid) patchname, ");
        sql.append(" t.memo ");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1  ");
        
        /******** 子查询begin*****************/
        sql.append(" AND EXISTS (  SELECT g.previd  FROM biz_grid_info g  ");
        sql.append(" WHERE t.previd = g.previd  ");
		
        sql.append(getGrididSQL(list));
        
		sql.append(" ) ");
		/******** 子查询end*****************/
		
		sql.append(" ) v ");
		
		map.clear();
		persistentService.clear();
		List<BizGridInfo> gridlist = persistentService.find(sql.toString(), BizGridInfo.class, paramList.toArray());
		if (gridlist != null && gridlist.size() > 0) {
			for (BizGridInfo bizGridInfo : gridlist) {
				map.put(bizGridInfo.getId()+"", bizGridInfo.getGridname());
			}
    	} 
		
		return map;
    }
    
    /**
     * 获取网格的查询SQL
     * @param list
     * @return
     */
    private String getGrididSQL(List<BizGridManager> list) {
    	if (list == null || list.size() == 0) {
	    	return "";	
    	}
    	StringBuilder buff = new StringBuilder();
    	buff.append(" AND g.gridid in ( ");
    	for (BizGridManager bizGridManager : list) {
    		buff.append(bizGridManager.getGridid()).append(',');
		}
    	buff.delete(buff.length()-1, buff.length());
    	buff.append(" )");
    	
    	return buff.toString();
    }
}
