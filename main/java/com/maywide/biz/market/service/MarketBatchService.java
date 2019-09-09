package com.maywide.biz.market.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarkInfoRespBO;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarktInfoReq;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarktInfoResp;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.market.entity.CustMarketing;
import com.maywide.biz.market.entity.MarketBatch;
import com.maywide.biz.market.pojo.CustMarketInfo;
import com.maywide.biz.market.pojo.MarketBatchParamBO;
import com.maywide.biz.market.pojo.PushCustomerBO;
import com.maywide.biz.market.dao.MarketBatchDao;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarketBatchService extends BaseService<MarketBatch,Long>{
    
    @Autowired
    private MarketBatchDao marketBatchDao;
    
    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private PubService pubService;

    @Override
    protected BaseDao<MarketBatch, Long> getEntityDao() {
        return marketBatchDao;
    }
    
    public Map<String, String> getMarketBatchMap()  throws Exception {
    	return getMarketBatchMap(true);
    }
    
    
    public Map<String, String> getMarketBatchMap(boolean isBatid)  throws Exception {

     	LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
     	String city = loginInfo.getCity();
     		
//        String curDate = DateUtils.getFormatDateString(new Date(), "yyyy-MM-dd");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");
        sql.append(" SELECT ");
        sql.append(isBatid ? "s.recid mcode" : "s.knowid mcode");
        sql.append(", t.knowname mname ");
        sql.append("   FROM biz_market_batch s, prd_salespkg_know t ");
        sql.append("  WHERE s.knowid = t.knowid ");
        if (StringUtils.isNotBlank(city)) {
            sql.append("    AND s.city = ? ");
            paramList.add(city);
        }
        
//        sql.append(" AND s.appdate >= STR_TO_DATE(?, '%Y-%m-%d')");
//        paramList.add(curDate);

        sql.append(" )v1  ");

        List<PrvSysparam> assList = persistentService.find(sql.toString(),
                PrvSysparam.class, paramList.toArray());

        Map<String, String> map = new HashMap<String, String>();
        if (!BeanUtil.isListNull(assList)) {
        	for (PrvSysparam prvSysparam : assList) {
        		map.put(prvSysparam.getMcode(), prvSysparam.getMname());
			}
        }

        return map;
    }
    
    public List<PrvDepartment> getDepts()  throws Exception {
     	LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
     	String city = loginInfo.getCity();
     		
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

    public PageImpl queMarketBatchs(Pageable pageable,
    		MarketBatchParamBO marketBatchParamBO) throws Exception {

		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
//		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) && !"9".equals(rolelvl)) {
//			if (marketBatchParamBO.getMbid() == null) {
//				List<AssIndexMonprogress> resultList = new ArrayList<AssIndexMonprogress>();
//				pageResult = new PageImpl(resultList, pageable, 0);
//				return pageResult;
//			}
//		}
		
		String inSql = getInSql(marketBatchParamBO.getStartPeriod(), marketBatchParamBO.getEndPeriod());
		
		String deptSql =  getDeptInSql(marketBatchParamBO.getDeptids());
		
		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.recid as id, ");
        sql.append(" t.batchno, ");
        sql.append(" t.areaids, ");
        sql.append(" (SELECT GROUP_CONCAT(r.name) FROM prv_area r WHERE FIND_IN_SET(r.areaid ,t.areaids)) areanames, ");
        sql.append(" t.knowid, ");
        sql.append(" (select s.knowname from prd_salespkg_know s where s.knowid=t.knowid) knowname, ");
        sql.append(" t.nums, ");
        sql.append(" t.status, ");
        sql.append(" t.appdate, ");
        sql.append(" t.operid, ");
        sql.append(" t.city, ");
        sql.append(" t.deptid, ");
        sql.append(" (select d.name from prv_department d where d.deptid=t.deptid) deptname, ");
        sql.append(" t.memo ");
        sql.append(" FROM biz_market_batch t ");
        sql.append(" WHERE 1 = 1  ");
		if (marketBatchParamBO.getMbid() != null){
			sql.append(" AND t.recid= ? ");
			paramList.add(marketBatchParamBO.getMbid());
		}
		
		if (StringUtils.isNotEmpty(marketBatchParamBO.getStatus())) {
			sql.append(" AND t.status = ? ");
			paramList.add(marketBatchParamBO.getStatus());
		}
		
		if (deptSql != null) {
			sql.append(deptSql);
		}
		
		if (inSql != null){
			sql.append(inSql);
		}
		
//		sql.append(getPatchidSql(monprogressParamBO));
		
		sql.append(" ) v ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), MarketBatch.class, paramList.toArray());
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<MarketBatch> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<MarketBatch> resultList = new ArrayList<MarketBatch>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
    
    public MarketBatch getMarketBatch(Long id) throws Exception {

    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.recid as id, ");
        sql.append(" t.batchno, ");
        sql.append(" t.areaids, ");
        sql.append(" t.knowid, ");
        sql.append(" (select s.knowname from prd_salespkg_know s where s.knowid=t.knowid) knowname, ");
        sql.append(" t.nums, ");
        sql.append(" t.status, ");
        sql.append(" t.appdate, ");
        sql.append(" t.operid, ");
        sql.append(" t.city, ");
        sql.append(" t.deptid, ");
        sql.append(" (select d.name from prv_department d where d.deptid=t.deptid) deptname, ");
        sql.append(" t.memo ");
        sql.append(" FROM biz_market_batch t ");
        sql.append(" WHERE 1 = 1  ");
		if (id != null){
			sql.append(" AND t.recid= ? ");
			paramList.add(id);
		}
		
		sql.append(" ) v ");
		
		persistentService.clear();
		List<MarketBatch> blist = persistentService.find(sql.toString(), MarketBatch.class, paramList.toArray());
		
		if (blist != null && blist.size() > 0){
			return blist.get(0);
		} else {
			return null;
		}
    }
    
    
    
    private String getInSql(String startDate, String endDate) {
    	if (startDate == null || endDate == null){
    		return null;
    	}
    	
    	startDate = startDate.replaceAll("-", "");
    	endDate = endDate.replaceAll("-", "");
    	
    	StringBuilder buff = new StringBuilder();
    	buff.append(" AND t.appdate >= DATE_FORMAT('").append(startDate)
    	.append("', '%Y%m%d')").append(" AND t.appdate <= DATE_FORMAT('").append(endDate)
    	.append("', '%Y%m%d') ");
    	
    	// tdate> DATE_FORMAT('20150901','%Y%m%d') AND tdate <= DATE_FORMAT('20150910','%Y%m%d')
    	
    	return buff.toString();
    }
    
    private String getDeptInSql(List<Long> deptids) {
    	if (deptids == null || deptids.size() == 0) {
    		return null;
    	}
    	
    	StringBuilder buff = new StringBuilder();
    	buff.append(" AND t.deptid in (");
    	String depts = "";
    	for (Long deptid : deptids) {
    		if (deptid == null) continue;
    		depts += deptid + ",";
		}
    	
    	if (StringUtils.isNotEmpty(depts)) {
    		depts = depts.substring(0, depts.length() - 1);
    	} else {
    		return null;
    	}
    	
    	buff.append(depts).append(") ");
    	
    	return buff.toString();
    }
    
    public List<SalespkgKnow> getKnowids() throws Exception {

     	LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
     	String city = loginInfo.getCity();
     		
        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");

        sql.append(" SELECT s.knowid id,");
        sql.append(" s.knowname ,");
        sql.append(" s.brief ,");
//        sql.append(" s.objtype ,");
//        sql.append(" s.objid ,");
//        sql.append(" s.price ,");
//        sql.append(" s.opcodes ,");
//        sql.append(" s.tocust ,");
//        sql.append(" s.wordexp ,");
//        sql.append(" s.feeexp ,");
//        sql.append(" s.createoper ,");
//        sql.append(" s.createtime ,");
//        sql.append(" s.updateoper ,");
//        sql.append(" s.updatetime ,");
        sql.append(" s.city ");
//        sql.append(" s.maxtimes ,");
//        sql.append(" s.icon ");
        
        sql.append("   FROM PRD_SALESPKG_KNOW s ");
        sql.append("  WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(city)) {
            sql.append("    AND s.city = ? ");
            paramList.add(city);
        }

        sql.append(" )v1  ");
        
        List<SalespkgKnow> salespkgList = persistentService.find(sql.toString(),
        		SalespkgKnow.class, paramList.toArray());

        if (!BeanUtil.isListNull(salespkgList)) {
        	return salespkgList;
        } else {
        	List<SalespkgKnow> emptyList = new ArrayList<SalespkgKnow>();
        	return emptyList;
        }
    
    }
    
    public List<PrvArea> getAreaids() throws Exception {
     	LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
     	String city = loginInfo.getCity();
     		
        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");

        sql.append(" SELECT s.areaid id,");
        sql.append(" s.name ,");
        sql.append(" s.type ,");
        sql.append(" s.center ,");
        sql.append(" s.city ,");
        sql.append(" s.town ");
        
        sql.append("  FROM PRV_AREA s ");
        sql.append("  WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(city)) {
            sql.append("    AND s.city = ? ");
            paramList.add(city);
        }

        sql.append(" )v1  ");

        List<PrvArea> areaList = persistentService.find(sql.toString(),
        		PrvArea.class, paramList.toArray());

        if (!BeanUtil.isListNull(areaList)) {
        	return areaList;
        } else {
        	List<PrvArea> emptyList = new ArrayList<PrvArea>();
        	return emptyList;
        }
    }
    
    public void checkMarketBatch(String batchno, Long knowid, String areaids) throws Exception {
    	// 先检查营销批次号是否已经存在
    	MarketBatch queMarketBatch = new MarketBatch();
    	queMarketBatch.setBatchno(batchno);
    	queMarketBatch.setStatus(com.maywide.biz.cons.BizConstant.BizMarketBatchStatus.VALID);
    	
        List<MarketBatch> marketBatchList = persistentService.find(queMarketBatch);
        if (marketBatchList != null && marketBatchList.size() > 0) {
        	throw new ServiceException("营销批次号["+batchno+"]已存在。");
        }
    	
        // 再检查营销标识、业务区范围对应的记录是否已经存在
    	queMarketBatch = new MarketBatch();
    	queMarketBatch.setKnowid(knowid);
    	queMarketBatch.setStatus(com.maywide.biz.cons.BizConstant.BizMarketBatchStatus.VALID);
    	
        marketBatchList = persistentService.find(queMarketBatch);
        if (marketBatchList != null && marketBatchList.size() > 0) {
        	for (MarketBatch marketBatch : marketBatchList) {
        		String existAreaids = marketBatch.getAreaids();
        		if (StringUtils.isNotEmpty(existAreaids)) {
        			String[] existAreaidArr = existAreaids.split(",");
        			if (existAreaidArr != null && existAreaidArr.length > 0) {
        				for (int i = 0; i < existAreaidArr.length; i++) {
        					// 业务区已存在
							if (-1 != areaids.indexOf(existAreaidArr[i].trim())) {
								throw new ServiceException("业务区["+existAreaidArr[i]+"]已存在。");
							}
						}
        			}
        		}
			}
        }
    }
    
    /**
     * 获取网格人员列表
     * @return
     * @throws Exception
     */
    public List<BizGridManager> getGridOperList() throws Exception {
    	Set<Long> grididSet = new HashSet<Long>();
    	
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		String rolelvl = loginInfo.getRolelevel(); // 9高，5中，0低 权
		Long operid = loginInfo.getOperid();
		
		// 超级管理员和高权可以查看所有，否则取该操作员所属网格的网格人员，及所属网格下级所有的网格人员
		if (Constant.ADMINISTRATOR.equals(loginInfo.getLoginname()) || "9".equals(rolelvl)) {
			return getOperList(null); // 取BIZ_GRID_MANAGER表中所有的operid
		}
		
    	BizGridManager gridManager = new BizGridManager();
    	gridManager.setOperid(operid); // 502345L
    	
    	List<BizGridManager> gridManagerList = persistentService.find(gridManager);
		if (gridManagerList != null || gridManagerList.size() > 0) {
			for (BizGridManager bizGridManager : gridManagerList) {
				BizGridInfo gridInfo = (BizGridInfo)persistentService.find(BizGridInfo.class, bizGridManager.getGridid());

				List<BizGridInfo> gridList = getGridsByGridid(gridInfo.getId(), false);
				grididSet.add(gridInfo.getId());
				
				for (BizGridInfo grid : gridList) {
		    		grididSet.add(grid.getId());
				}
			}
			
			return getOperList(grididSet);
		} else {
			List<BizGridManager> emptylist = new ArrayList<BizGridManager>();
			return emptylist;
		}
    	
    }
    
    /**
     * 根据给定的管理网格ID列表，获取网格人员列表
     * @param gridSet
     * @throws Exception
     */
	private List<BizGridManager> getOperList(Set<Long> gridSet) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		String gridSql = getGridSql(gridSet);
		
		sql.append(" SELECT *  FROM (");
		sql.append(" SELECT distinct ");
//		sql.append(" SELECT t.mnrid as id, ");
//		sql.append(" t.gridid, ");
		sql.append(" t.operid, ");
		sql.append(" (select o.name from prv_operator o where o.operid=t.operid) opername ");
//		sql.append(" t.ismain, ");
//		sql.append(" t.updatetime, ");
//		sql.append(" t.memo ");
		sql.append(" FROM biz_grid_manager t ");
		sql.append(" WHERE 1 = 1  ");

		if (gridSql!=null) {
			sql.append(gridSql);
		}
		
		sql.append(" ) v ");

		persistentService.clear();
		List<BizGridManager> gridMgrlist = persistentService.find(sql.toString(),
				BizGridManager.class, paramList.toArray());
		if (gridMgrlist != null && gridMgrlist.size() > 0) {
			return gridMgrlist;
		} else {
			List<BizGridManager> emptylist = new ArrayList<BizGridManager>();
			return emptylist;
		}

	}
	
	private String getGridSql(Set<Long> grididSet) {
		if (grididSet == null) {
			return null;
		}
		
		StringBuilder pgridsSql = new StringBuilder();
		
		String grids = "";
		for (Long gridid : grididSet) {
			grids += gridid +",";
		}
		
		if (StringUtils.isNotEmpty(grids)) {
			grids = grids.substring(0, grids.length()-1);
			
			pgridsSql.append(" AND t.gridid in (").append(grids).append(")");
		} else {
			// 构造一个不存在的gridid，保证查不到记录
			grids = "-9999999999999999";
			pgridsSql.append(" AND t.gridid in (").append(grids).append(")");
		}
		
		return pgridsSql.toString();
	}
    
    
    /**
     * 网格树的查询指定网格下所有的管理网格或片区网格
     * @param gridid
     * @return
     * @throws Exception
     */
    public List<BizGridInfo> getGridsByGridid(Long gridid, boolean isQuePatchGrid) throws Exception {
    	
    	String gridSet = getGridSet(gridid);
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        
        // SELECT * FROM biz_grid_info WHERE FIND_IN_SET(previd, FatherList(4592)) AND gtype = '0';
        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.gridid as id, ");
        sql.append(" t.gridname");
        sql.append(" FROM biz_grid_info t ");
        sql.append(" WHERE 1 = 1   ");
        
        if (isQuePatchGrid) {
        	sql.append("AND t.gtype='1'");
        } else {
        	sql.append("AND t.gtype='0'");
        }
        
		if (gridSet != null){
			sql.append(" AND FIND_IN_SET(t.previd, ?) ");
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
    
    private String getGridSet(Long recurGridid) throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	Long operId = loginInfo.getOperid();
    	
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
    	
    	sql.append(" SELECT *  FROM (");
    	sql.append(" SELECT  FatherList(?) as memo ");
        
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
    
    private String getPatchids(String strGrids) throws Exception {
    	String patchids = "";
    	String[] gridArr = strGrids.split(",");
    	for (String grid : gridArr) {
			List<BizGridInfo> list = getGridsByGridid(Long.parseLong(grid), true);
			
			if (list != null && list.size() > 0) {
				for (BizGridInfo bizGridInfo : list) {
					patchids += bizGridInfo.getId() + ",";
				}
			}
		}
    	
    	if (StringUtils.isNotEmpty(patchids)) {
    		patchids = patchids.substring(0, patchids.length()-1);
    	}
    	return patchids;
    }
    
    
    private List<CustMarketInfo> filterExistCust(Long knowid, String custfilter, List<QueCustMarkInfoRespBO> resultList) throws Exception {
    	
    	boolean isCustFilter = "0".equals(custfilter); //'0':'过滤','1':'不过滤'
    	List<CustMarketInfo> retCustList = new ArrayList<CustMarketInfo>();
    	
    	for (QueCustMarkInfoRespBO queCustMarkInfoRespBO : resultList) {
    		
    		if (isCustFilter) {
    			String custid = queCustMarkInfoRespBO.getCustid();
        		String houseid = queCustMarkInfoRespBO.getHouseid();
        		
        		if (StringUtils.isEmpty(custid) || StringUtils.isEmpty(houseid)) {
        			continue;
        		}
        		
        		CustMarketing custMarketing = new CustMarketing();
        		custMarketing.setCustid(Long.parseLong(custid));
        		custMarketing.setHouseid(Long.parseLong(houseid));
        		custMarketing.setKnowid(knowid);
        		
        		// knowid、custid、houseid存在，表示已推送过，则过滤掉
        		List<CustMarketing> custList = persistentService.find(custMarketing);
        		if (custList != null && custList.size() > 0) {
        			continue;
        		}
    		}
    		
    		CustMarketInfo custMarketInfo = new CustMarketInfo();
    		BeanUtils.copyProperties(custMarketInfo, queCustMarkInfoRespBO);
    		
    		retCustList.add(custMarketInfo);
		}
    	
    	return retCustList;
    }
    
    public PageImpl findCust(Long knowid, String patchid, String netattr, Pageable pageable) throws Exception {
    	if (StringUtils.isEmpty(patchid) || StringUtils.isEmpty(netattr)) {
    		List<CustMarketInfo> resultList = new ArrayList<CustMarketInfo>();
			return new PageImpl(resultList, pageable, 0);
    	}
    	
    	String patchids = patchid; //"1505"
    	
		PageImpl pageResult = null;
		
		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		QueCustMarktInfoReq req = new QueCustMarktInfoReq();
//		req.setBizorderid(persistentService.getSequence("SEQ_BIZ_CUSTORDER_ID")
//                .toString());
		req.setBizorderid(getBizorderid());
        req.setPagesize(pageable.getPageSize() + "");
        req.setCurrentPage(String.valueOf(pageable.getPageNumber() + 1));
        req.setPatchids(patchids);
        req.setNetattr(netattr);
        req.setServtype("0"); // 0-主机 1-分机
		req.setIscust("Y"); // Y-按客户返回 N-按用户返回
		
		QueCustMarktInfoResp resp = new QueCustMarktInfoResp();
		pubService.queCustMarktInfo(req, resp);
		
		
		if (resp != null){
			int total = Integer.parseInt(resp.getTotalCount());
			List<QueCustMarkInfoRespBO> resultList = resp.getResult(); 
			
			// 暂时不做过滤， '0':'过滤','1':'不过滤'
			List<CustMarketInfo> custMarketList = filterExistCust(knowid, "1", resultList );
			
			pageResult = new PageImpl(custMarketList, pageable, total);
		} else {
			List<CustMarketInfo> resultList = new ArrayList<CustMarketInfo>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}
		
		return pageResult;
    }
    
    public void doSavePush(MarketBatchParamBO mbParamBO) throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	Long operId = loginInfo.getOperid();
    	Long deptid = loginInfo.getDeptid();
    	String city = loginInfo.getCity();
    	
    	// 获取biz_market_batch的ID
    	Long taskId = mbParamBO.getTaskId();
    	
    	// 获取选中的网格人员
    	List<Long> pushOperids = mbParamBO.getPushOperids();
    	
    	// 获取选中的客户列表
    	String pushCustomerObj = mbParamBO.getPushCustomerObj();
    	
    	 // 转换客户列表信息
    	PushCustomerBO params = (PushCustomerBO) BeanUtil.jsonToObject(
    			pushCustomerObj, PushCustomerBO.class);
		List<CustMarketInfo> custMarketInfoList = params.getCustMarketInfo();
		
		MarketBatch marketBatch = (MarketBatch)persistentService.find(MarketBatch.class, taskId);
		
		CustMarketing custMarketing = null;
		for (CustMarketInfo custMarketInfo : custMarketInfoList) {
			// 检查 knowid、custid、houseid是否在biz_cust_marketing存在
    		String custid = custMarketInfo.getCustid();
        	String houseid = custMarketInfo.getHouseid();
        		
        	if (StringUtils.isEmpty(custid) || StringUtils.isEmpty(houseid)) {
        		continue;
        	}
        		
			for (Long pushOperid : pushOperids) {
				
				CustMarketing tempCustMarketing = new CustMarketing();
	        	tempCustMarketing.setCustid(Long.parseLong(custid));
	        	tempCustMarketing.setHouseid(Long.parseLong(houseid));
	        	tempCustMarketing.setKnowid(marketBatch.getKnowid());
	        	tempCustMarketing.setAreamger(pushOperid);
	        	// knowid、custid、houseid、areamger存在，表示某个营销套餐某个客户已推送过给网格人员，不需要再推送，过滤掉
	        	List<CustMarketing> custList = persistentService.find(tempCustMarketing);
	        	if (custList != null && custList.size() > 0) {
	        		continue;
	        	}
				
				custMarketing = new CustMarketing();
				custMarketing.setBatchno(marketBatch.getBatchno());
				custMarketing.setCustid(Long.valueOf(custMarketInfo.getCustid()));
				custMarketing.setName(custMarketInfo.getCustname());
				custMarketing.setLinkphone(custMarketInfo.getMobile());
				custMarketing.setHouseid(Long.valueOf(custMarketInfo.getHouseid()));
				custMarketing.setWhladdr(custMarketInfo.getWhladdr());
				custMarketing.setAreaid(Long.valueOf(custMarketInfo.getAreaid()));
				custMarketing.setPtachid(Long.valueOf(custMarketInfo.getPatchid()));
				custMarketing.setPri("H");
				custMarketing.setKnowid(marketBatch.getKnowid());
				custMarketing.setAreamger(pushOperid);
//				custMarketing.setMgerphone("13411111111");
				custMarketing.setAppdate(new Date());
				custMarketing.setOperid(operId);
				custMarketing.setDeptid(deptid);
				custMarketing.setDealstatus("0");
				custMarketing.setAlnums(0L);
				custMarketing.setCity(city);
				
				persistentService.save(custMarketing);
			}
		}
		
    }
    
    /**
     * 删除营销任务以及对应的营销推送
     * @param ids
     * @throws Exception
     */
    public void deleteMarketBatch(String ids) throws Exception{
    	String[] idArr = ids.split(",");
    	for (int i = 0; i < idArr.length; i++) {
			String id = idArr[i];
			
			MarketBatch marketBatch = (MarketBatch)persistentService.find(MarketBatch.class, Long.parseLong(id));
			
			CustMarketing queCustMarketing = new CustMarketing();
			queCustMarketing.setBatchno(marketBatch.getBatchno());
			List<CustMarketing> custMarketingList = persistentService.find(queCustMarketing);
			for (CustMarketing custMarketing : custMarketingList) {
				persistentService.delete(custMarketing);
			}
			
			persistentService.delete(marketBatch);
		}
    }
    
}
