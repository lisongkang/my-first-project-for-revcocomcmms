package com.maywide.biz.ass.monstat.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.maywide.biz.ass.monstat.entity.AssIndexMonprogress;
import com.maywide.biz.ass.monstat.pojo.MonprogressParamBO;
import com.maywide.biz.ass.monstat.dao.AssIndexMonprogressDao;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.ass.topatch.service.AssIndexTopatchService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.pojo.GridTreeInfo;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssIndexMonprogressService extends BaseService<AssIndexMonprogress,Long>{
	
    private final static Logger log = LoggerFactory.getLogger(AssIndexMonprogressService.class);

    @Autowired
    private AssIndexMonprogressDao assIndexMonprogressDao;
    
    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private AssIndexTopatchService assIndexTopatchService;
    
    private DateFormat df = new SimpleDateFormat("yyyyMM");

    @Override
    protected BaseDao<AssIndexMonprogress, Long> getEntityDao() {
        return assIndexMonprogressDao;
    }
    
    public Map<String, String> getLowlvlFirstGrididMap(Long operid) throws Exception {
    	Map<String, String> retMap = new HashMap<String,String>();
    	
    	BizGridManager gridManager = new BizGridManager();
    	gridManager.setOperid(operid);
    	
    	List<BizGridManager> gridManagerList = persistentService.find(gridManager);
		if (gridManagerList != null || gridManagerList.size() > 0) {
			
			for (BizGridManager bizGridManager : gridManagerList) {
				BizGridInfo gridInfo = (BizGridInfo)persistentService.find(BizGridInfo.class, bizGridManager.getGridid());

				Map<Long, BizGridInfo> recurMap = new HashMap<Long, BizGridInfo>();
				recurMap.put(gridInfo.getId(), gridInfo);
				if (gridInfo.getStatid() != null && gridInfo.getStatid() != -1) {
					recurParentGrid(true, gridInfo, recurMap);
				}

				BizGridInfo rootGrid = recurMap.get(-1L);
				if (rootGrid == null) {
					continue;
				}

				BizGridInfo firstGrid = recurMap.get(rootGrid.getSubgridid());
				if (firstGrid == null) {
					continue;
				}
				retMap.put(firstGrid.getId()+"", firstGrid.getGridname());
			}
		} 

		return retMap;
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
		
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		String city = loginInfo.getCity();
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			return getLowlvlFirstGrididMap(loginInfo.getOperid());
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
        sql.append(" t.statid, ");
        sql.append(" t.patchid, ");
        sql.append(" (select p.patchname from res_patch p where p.patchid=t.patchid) patchname, ");
        sql.append(" t.memo ");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1  ");
        
        /******** 子查询begin*****************/
        sql.append(" AND EXISTS (  SELECT g.gridid  FROM biz_grid_info g  ");
        sql.append(" WHERE t.statid = g.gridid  ");
        sql.append(" AND g.statid= -1 ");
		if (city != null){
			sql.append(" AND g.gridcode= ? ");
			paramList.add(city);
		}
		sql.append(" ) ");
		/******** 子查询end*****************/
		
		sql.append(" ) v ");
		
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
     * 根据营维中心获取下级网格列表
     * @param firstGridid
     * @return
     * @throws Exception
     */
    public List<BizGridInfo> getSecondGrididByfirstGridid(String firstGridid) throws Exception {
    	if (StringUtils.isEmpty(firstGridid)) {
    		 List emptyGridlist = new ArrayList<BizGridInfo>();
    		 return emptyGridlist;
    	}
    	
    	Long lFirstGridid = Long.parseLong(firstGridid);
    	
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
        sql.append(" t.statid, ");
        sql.append(" t.patchid, ");
        sql.append(" (select p.patchname from res_patch p where p.patchid=t.patchid) patchname, ");
        sql.append(" t.memo ");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1  ");
		if (lFirstGridid != null){
			sql.append(" AND t.statid= ? ");
			paramList.add(lFirstGridid);
		}
		
		sql.append(" ) v ");
		
		persistentService.clear();
		List<BizGridInfo> gridlist = persistentService.find(sql.toString(), BizGridInfo.class, paramList.toArray());
		if (gridlist != null && gridlist.size() > 0) {
			return gridlist;
    	} else {
    		 gridlist = new ArrayList<BizGridInfo>();
    		 return gridlist;
    	}
    	
    }
    
    /**
     * 根据网格列表查询网格下的片区
     * @param gridids
     * @return
     * @throws Exception
     */
    public List<BizGridInfo> getThirdPatchidBySecondGridid(List<String> gridids) throws Exception {
    	List<BizGridInfo> retList = new ArrayList<BizGridInfo>();
    	if (gridids == null || gridids.size() == 0) {
    		return retList;
    	}
    	
    	for (String gridid : gridids) {
    		Long lGridid = Long.parseLong(gridid);
    		
    		List<BizGridInfo> list = assIndexTopatchService.getPatchsByPgridid(lGridid);
    		
    		retList.addAll(list);
		}
    	
    	return retList;
    }
    
    /**
     * 获取考核指标内容列表
     * @return
     * @throws Exception
     */
    public Map<String, String> getAssMap() throws Exception {
        // 保存assindexstore对象
     	LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
     	String city = loginInfo.getCity();
     		
//        String curDate = DateUtils.getFormatDateString(new Date(), "yyyy-MM-dd");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");

        sql.append(" SELECT s.ASSID mcode, s.ASSCONTENT mname ");
        sql.append("   FROM ass_index_store s ");
        sql.append("  WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(city)) {
            sql.append("    AND s.CITY = ? ");
            paramList.add(city);
        }
        
//        sql.append(" AND s.expdate >= STR_TO_DATE(?, '%Y-%m-%d')");
//        paramList.add(curDate);

        sql.append(" )v1  ");

        List<PrvSysparam> assList = persistentService.find(sql.toString(),
                PrvSysparam.class, paramList.toArray());

        Map<String, String> assMap = new HashMap<String, String>();
        if (!BeanUtil.isListNull(assList)) {
        	for (PrvSysparam prvSysparam : assList) {
        		assMap.put(prvSysparam.getMcode(), prvSysparam.getMname());
			}
        }

        return assMap;
    }
    
    /**
     * 查询月度统计数据
     * @param pageable
     * @param monprogressParamBO
     * @param startMonth
     * @param endMonth
     * @return
     * @throws Exception
     */
	public PageImpl queSumMonprogress(Pageable pageable,
			MonprogressParamBO monprogressParamBO, String startMonth,
			String endMonth) throws Exception {

		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			if (monprogressParamBO.getFirstGridid() == null) {
				List<AssIndexMonprogress> resultList = new ArrayList<AssIndexMonprogress>();
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
        
        sql.append(" SELECT v.tmonth, v.assid, v.asscontent, v.pgridid, ");
        sql.append(" (select gg.gridname from biz_grid_info gg where v.pgridid=gg.gridid) pgrididname, ");
        sql.append(" sum(v.total) total, sum(v.comnum) comnum, sum(v.curnum) curnum ");
        sql.append("  FROM (");
        
        sql.append(" SELECT t.staid as id, ");
        sql.append(" t.tmonth, ");
        sql.append(" t.assid, ");
        sql.append(" (select s.asscontent from ass_index_store s where s.assid=t.assid) asscontent, ");
        sql.append(" t.objtype, ");
        sql.append(" t.objid, ");
        sql.append(" (select p.patchname from res_patch p where p.patchid=t.objid) objname, ");
        sql.append(" (select g.statid from biz_grid_info g where g.patchid=t.objid) pgridid, ");
        sql.append(" t.total, ");
        sql.append(" t.comnum, ");
        sql.append(" t.curnum ");
        sql.append(" FROM ass_index_monprogress t ");
        sql.append(" WHERE 1 = 1  ");
		if (monprogressParamBO.getAssid() != null){
			sql.append(" AND t.assid= ? ");
			paramList.add(monprogressParamBO.getAssid());
		}
		
		if (inSql != null){
			sql.append(" AND t.tmonth in  " + inSql);
		}
		
		sql.append(getPatchidSql(monprogressParamBO));
		
		sql.append(" ) v  group by v.tmonth,v.assid,v.pgridid ");
		
		sql.append(" ) vv ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssIndexMonprogress.class, paramList.toArray());
		
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssIndexMonprogress> resultList = page.getResult();
			obtainFirstAndSecondGridInfo(resultList, false);
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<AssIndexMonprogress> resultList = new ArrayList<AssIndexMonprogress>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
    
    /**
     * 查询月度统计数据
     * @param pageable
     * @param monprogressParamBO
     * @param startMonth
     * @param endMonth
     * @return
     * @throws Exception
     */
	public PageImpl queMonprogress(Pageable pageable,
			MonprogressParamBO monprogressParamBO, String startMonth,
			String endMonth) throws Exception {

		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			if (monprogressParamBO.getFirstGridid() == null) {
				List<AssIndexMonprogress> resultList = new ArrayList<AssIndexMonprogress>();
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
        sql.append(" SELECT t.staid as id, ");
        sql.append(" t.tmonth, ");
        sql.append(" t.assid, ");
        sql.append(" (select s.asscontent from ass_index_store s where s.assid=t.assid) asscontent, ");
        sql.append(" t.objtype, ");
        sql.append(" t.objid, ");
        sql.append(" (select p.patchname from res_patch p where p.patchid=t.objid) objname, ");
        sql.append(" t.total, ");
        sql.append(" t.comnum, ");
        sql.append(" t.curnum ");
        sql.append(" FROM ass_index_monprogress t ");
        sql.append(" WHERE 1 = 1  ");
		if (monprogressParamBO.getAssid() != null){
			sql.append(" AND t.assid= ? ");
			paramList.add(monprogressParamBO.getAssid());
		}
		
		if (inSql != null){
			sql.append(" AND t.tmonth in  " + inSql);
		}
		
		sql.append(getPatchidSql(monprogressParamBO));
		
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssIndexMonprogress.class, paramList.toArray());
		
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssIndexMonprogress> resultList = page.getResult();
			obtainFirstAndSecondGridInfo(resultList, true);
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<AssIndexMonprogress> resultList = new ArrayList<AssIndexMonprogress>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }

	/**
	 * 获取片区的构造SQL条件
	 * @param monprogressParamBO
	 * @return
	 */
    public String getPatchidSql(MonprogressParamBO monprogressParamBO) {
    	List<BizGridInfo> patchids = getPatchidList(monprogressParamBO);
    	if (patchids == null || (patchids.size() == 0)) {
    		return "";
    	}
    	
    	StringBuilder inSql = new StringBuilder();
    	StringBuilder buff = new StringBuilder();
    	buff.append(" AND t.objtype = 2 ");
    	
    	for (BizGridInfo bizGridInfo : patchids) {
    		inSql.append("'").append(bizGridInfo.getPatchid()).append("'").append(",");
		}
    	
    	if (!StringUtils.isEmpty(inSql.toString())){
    		// 去掉最后一个逗号
        	inSql.delete(inSql.length()-1, inSql.length());
        	
    		buff.append(" AND t.objid in (");
    		buff.append(inSql.toString());
        	buff.append(")");
    	} 
    	return buff.toString();
    }
    
    /**
     * 获取要查询的片区列表
     * @param monprogressParamBO
     * @return
     */
    private List<BizGridInfo> getPatchidList(MonprogressParamBO monprogressParamBO) {
    	
    	Long firstGridid = monprogressParamBO.getFirstGridid();
    	List<Long> secondGridids = monprogressParamBO.getSecondGridids();
    	List<Long> patchids = monprogressParamBO.getThirdPatchids();
    	
    	// 片区不为空， 直接返回片区列表
    	if (patchids != null && patchids.size() > 0) {
    		boolean isExist = false;
    		List<BizGridInfo> patchidList = new ArrayList<BizGridInfo>();
    		for (Long patchid : patchids) {
    			if (patchid == null) continue;
    			isExist = true;
    			BizGridInfo bizGridInfo = new BizGridInfo();
    			bizGridInfo.setPatchid(patchid);
    			patchidList.add(bizGridInfo);
			}
    		
    		if (isExist) {
    			return patchidList;
    		}
    	}
    	
    	// 片区为空， 网格不为空，将网格下的所有片区返回
    	if (secondGridids != null && secondGridids.size() > 0) {
    		try {
    			boolean isExist = false;
    			List<BizGridInfo> retList = new ArrayList<BizGridInfo>();
        		for (Long lGridid : secondGridids) {
        			if (lGridid == null) continue;
        			isExist = true;
            		List<BizGridInfo> list = assIndexTopatchService.getPatchsByPgridid(lGridid);
            		if (list == null || list.size() == 0) {
            			// 构造一个不存在的patchid， 保证查不到记录。
            			BizGridInfo notExistGrid = new BizGridInfo();
            			notExistGrid.setPatchid(-9999999999999999L);
            			list.add(notExistGrid);
            		}
            		retList.addAll(list);
        		}
        		
        		if (isExist) {
        			return retList;
        		}
    		} catch (Exception e) {
    			
    		}
    	}
    	
    	// 片区为空、网格为空、营维中心不为空，将营维中心下的所有片区返回
    	if (firstGridid != null) {
    		try {
        		List<BizGridInfo> retList = new ArrayList<BizGridInfo>();
        		
            	List<BizGridInfo> list = assIndexTopatchService.getPatchsByGridid(firstGridid);
            	if (list == null || list.size() == 0) {
        			// 构造一个不存在的patchid， 保证查不到记录。
        			BizGridInfo notExistGrid = new BizGridInfo();
        			notExistGrid.setPatchid(-9999999999999999L);
        			list.add(notExistGrid);
        		}
            	retList.addAll(list);
        		
        		return retList;
    		} catch (Exception e) {
    			
    		}
    	}
    	
    	// 如果一级网格、二级网格、片区都为空，则不查任何数据
    	// 构造一个不存在的patchid， 保证查不到记录。
    	List<BizGridInfo> retList = new ArrayList<BizGridInfo>();
		BizGridInfo notExistGrid = new BizGridInfo();
		notExistGrid.setPatchid(-9999999999999999L);
		retList.add(notExistGrid);
		
    	return retList;
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
	 * 获取并设置当前片区对应的营维中心和网格
	 * @param monprogresslist
	 * @param isQuePatch 是否查询片区的一二级网格
	 * @throws Exception
	 */
	public void obtainFirstAndSecondGridInfo(List<AssIndexMonprogress> monprogresslist, boolean isQuePatch) throws Exception{
		for (AssIndexMonprogress assIndexMonprogress : monprogresslist) {
			Map<String, String> map = null;
			if (isQuePatch) {
				map = getFirstAndSecondGrid(assIndexMonprogress.getObjid());
			} else {
				BizGridInfo pGridInfo = (BizGridInfo)persistentService.find(BizGridInfo.class, assIndexMonprogress.getPgridid());
				map = getFirstAndSecondGrid(pGridInfo);
			}
			
			assIndexMonprogress.setFirstGridName(map.get("FIRST_SECOND_GRID_PATH"));
		}
	}
	
	/**
	 * 查找当前片区对应的营维中心和网格
	 * @param patchid
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getFirstAndSecondGrid(BizGridInfo gridInfo) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();

		Map<Long, BizGridInfo> recurMap = new HashMap<Long, BizGridInfo>();
		recurMap.put(gridInfo.getId(), gridInfo);
		if (gridInfo.getStatid() != null && gridInfo.getStatid() != -1) {
			recurParentGrid(true, gridInfo, recurMap);
		}

		BizGridInfo rootGrid = recurMap.get(-1L);
		if (rootGrid == null) {
			return retMap;
		}

		BizGridInfo firstGrid = recurMap.get(rootGrid.getSubgridid());
		if (firstGrid == null) {
			return retMap;
		}
		retMap.put("FIRST_GRID", firstGrid.getGridname());

		BizGridInfo secondGrid = recurMap.get(firstGrid.getSubgridid());
		if (secondGrid == null) {
			return retMap;
		}
		retMap.put("SECOND_GRID", secondGrid.getGridname());
		
		retMap.put("FIRST_SECOND_GRID_PATH", firstGrid.getGridname() + "->" + secondGrid.getGridname());

		return retMap;
	}
	
	/**
	 * 查找当前片区对应的营维中心和网格
	 * @param patchid
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getFirstAndSecondGrid(Long patchid) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		BizGridInfo patchGridInfo = new BizGridInfo();
		patchGridInfo.setPatchid(patchid);

		List<BizGridInfo> list = persistentService.find(patchGridInfo);
		if (list != null || list.size() > 0) {
			patchGridInfo = list.get(0);
		} else {
			return retMap;
		}

		return getFirstAndSecondGrid(patchGridInfo);
	}
	
	/**
	 * 递归查找当前片区对应的营维中心和网格
	 * @param curGridInfo
	 * @param map
	 * @throws Exception
	 */
	private void recurParentGrid(boolean isDataGrid, BizGridInfo curGridInfo, Map<Long, BizGridInfo> map) throws Exception{
		if (isDataGrid) {
			if (curGridInfo.getStatid() != null && curGridInfo.getStatid() != -1) {
				
				// 统计查询， 根据统计网格的statid往上找
				BizGridInfo pGridInfo = (BizGridInfo)persistentService.find(BizGridInfo.class, curGridInfo.getStatid());
				pGridInfo.setSubgridid(curGridInfo.getId());
				map.put(pGridInfo.getId(), pGridInfo);
				
				recurParentGrid(isDataGrid, pGridInfo, map);
				
			} else {
				BizGridInfo rootGridInfo = new BizGridInfo();
				BeanUtils.copyProperties(rootGridInfo, curGridInfo);
				rootGridInfo.setSubgridid(rootGridInfo.getId());
				
				map.put(curGridInfo.getStatid(), rootGridInfo);
			}
		} else {
			if (curGridInfo.getPrevid() != null && curGridInfo.getPrevid() != -1) {
				
				// 统计查询， 根据统计网格的statid往上找
				BizGridInfo pGridInfo = (BizGridInfo)persistentService.find(BizGridInfo.class, curGridInfo.getPrevid());
				pGridInfo.setSubgridid(curGridInfo.getId());
				map.put(pGridInfo.getId(), pGridInfo);
				
				recurParentGrid(isDataGrid, pGridInfo, map);
				
			} else {
				BizGridInfo rootGridInfo = new BizGridInfo();
				BeanUtils.copyProperties(rootGridInfo, curGridInfo);
				rootGridInfo.setSubgridid(rootGridInfo.getId());
				
				map.put(curGridInfo.getPrevid(), rootGridInfo);
			}
		}
		
	}
	
	/**
	 * 用于营销模块的树构造数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<GridTreeInfo> findGridTreeForMarket() throws Exception {
		return findGridTree(false);
	}

	/**
	 * 用于考核业绩模块的树构造数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<GridTreeInfo> findGridTreeForAss() throws Exception {
		return findGridTree(true);
	}
	
    /**
     * 用于网格树构造数据
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<GridTreeInfo> findGridTree(boolean isDataGrid) throws Exception {
        long l1 = System.currentTimeMillis();
        
        StringBuilder pgridsSql = new StringBuilder(""); // 非高权用户, 只选择操作员所属网格及其下级的网格
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		String city = loginInfo.getCity();
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
			getLowlvlGridSql(isDataGrid, loginInfo.getOperid(),pgridsSql);
		}

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT NEW com.maywide.biz.market.pojo.GridTreeInfo(");
        sql.append("o.id,o.gridcode,o.gridname,o.gtype,o.previd,o.statid,o.city)");
        sql.append(" FROM BizGridInfo o WHERE 1 = 1 ");
        sql.append(" AND ").append(isDataGrid?"o.statid":"o.previd").append(" IS NOT NULL ");
        
        if (StringUtils.isNotEmpty(pgridsSql.toString())) {
        	sql.append(pgridsSql.toString());
        }
        
        List<Object> paramList = new ArrayList<Object>();
        LoginInfo logininfo = AuthContextHolder.getLoginInfo();
        if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
            // 超级管理员查询全部地市的网格
            sql.append(" AND o.city = ?");
            paramList.add(city);
        }

        sql.append(" ORDER BY o.gtype,o.prio DESC,o.gridname");

        List<GridTreeInfo> gridTreeList = persistentService.find(sql.toString(), paramList.toArray());

        long l2 = System.currentTimeMillis();
        log.info("网格树查询数据库" + (l2 - l1) / 1000 + "秒");
        if (null == gridTreeList || gridTreeList.size() == 0) {
            return new ArrayList<GridTreeInfo>();
        } else {
            return gridTreeList;
        }
    }
    
    public void getLowlvlGridSql(boolean isDataGrid, Long operid, StringBuilder pgridsSql) throws Exception {
    	Set<Long> grididSet = new HashSet<Long>();
    	
    	BizGridManager gridManager = new BizGridManager();
    	gridManager.setOperid(operid); // 502345L
    	
    	List<BizGridManager> gridManagerList = persistentService.find(gridManager);
		if (gridManagerList != null || gridManagerList.size() > 0) {
			
			for (BizGridManager bizGridManager : gridManagerList) {
				BizGridInfo gridInfo = (BizGridInfo)persistentService.find(BizGridInfo.class, bizGridManager.getGridid());

				List<BizGridInfo> list = getMgrGridsByGridid(isDataGrid, gridInfo.getId());
				grididSet.add(gridInfo.getId());
				putInSet(list, grididSet);
				
				Map<Long, BizGridInfo> recurMap = new HashMap<Long, BizGridInfo>();
				recurMap.put(gridInfo.getId(), gridInfo);
				if (gridInfo.getStatid() != null && gridInfo.getStatid() != -1) {
					recurParentGrid(isDataGrid, gridInfo, recurMap);
				}

				BizGridInfo rootGrid = recurMap.get(-1L);
				if (rootGrid == null) {
					continue;
				}

				BizGridInfo firstGrid = recurMap.get(rootGrid.getSubgridid());
				if (firstGrid != null) {
					grididSet.add(firstGrid.getId());
				}
				
				BizGridInfo secondGrid = recurMap.get(firstGrid.getSubgridid());
				if (secondGrid != null) {
					grididSet.add(secondGrid.getId());
				}
				
				BizGridInfo thirdGrid = recurMap.get(secondGrid.getSubgridid());
				if (thirdGrid != null) {
					grididSet.add(thirdGrid.getId());
				}
				
			}
		} 
		
		String grids = "";
		for (Long gridid : grididSet) {
			grids += gridid +",";
		}
		
		if (StringUtils.isNotEmpty(grids)) {
			grids = grids.substring(0, grids.length()-1);
			
			pgridsSql.append(" AND o.id in (").append(grids).append(")");
		} else {
			// 构造一个不存在的gridid，保证查不到记录
			grids = "-9999999999999999";
			pgridsSql.append(" AND o.id in (").append(grids).append(")");
		}

    }
    
    private void putInSet(List<BizGridInfo> gridList, Set grididSet) {
    	for (BizGridInfo grid : gridList) {
    		grididSet.add(grid.getId());
		}
    }
    
    
    /**
     * 网格树的查询指定网格下所有的管理网格和片区网格
     * @param gridid
     * @return
     * @throws Exception
     */
    public List<BizGridInfo> getMgrGridsByGridid(boolean isDataGrid, Long gridid) throws Exception {
    	
    	String gridSet = getGridSet(isDataGrid, gridid);
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        // SELECT * FROM biz_grid_info WHERE FIND_IN_SET(previd, FatherList(4592)) AND gtype = '0';
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.gridid as id, ");
        sql.append(" t.gridname");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1  ");
		if (gridSet != null){
			if (isDataGrid) {
				sql.append(" AND FIND_IN_SET(t.statid, ?) ");
			} else {
				sql.append(" AND FIND_IN_SET(t.previd, ?) ");
			}
			
			paramList.add(gridSet);
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
    
    private String getGridSet(boolean isDataGrid,Long recurGridid) throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	Long operId = loginInfo.getOperid();
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
    	
    	sql.append(" SELECT *  FROM (");
    	
    	if (isDataGrid) {
    		sql.append(" SELECT  FatherList2(?) as memo ");
    	} else {
    		sql.append(" SELECT  FatherList(?) as memo ");
    	}
        
		if (recurGridid != null){
			paramList.add(recurGridid);
		} 
		
		sql.append(" ) v ");
		
		persistentService.clear();
		List<BizGridInfo> gridlist = persistentService.find(sql.toString(), BizGridInfo.class, paramList.toArray());
		if (gridlist != null && gridlist.size() > 0) {
			BizGridInfo bizGridInfo = gridlist.get(0);
			return bizGridInfo.getMemo();
    	} 
		
		return "";
    }
    
}
