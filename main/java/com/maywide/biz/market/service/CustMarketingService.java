package com.maywide.biz.market.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.entity.PhotoOperCust;
import com.maywide.biz.inter.entity.PrvOperPhoto;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.GoodsBySeiesBo;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.QueGoodsBySeriesReq;
import com.maywide.biz.inter.pojo.goods.queGoodsBySeries.QueGoodsBySeriesResp;
import com.maywide.biz.inter.pojo.queryCustPhoto.QueryCustPhotoReq;
import com.maywide.biz.inter.pojo.queryCustPhoto.QueryCustPhotoResp;
import com.maywide.biz.inter.pojo.saveCustPhoto.SaveCustPhotoReq;
import com.maywide.biz.market.entity.CustMarketing;
import com.maywide.biz.market.entity.MarketBatch;
import com.maywide.biz.market.pojo.MarketBatchParamBO;
import com.maywide.biz.market.dao.CustMarketingDao;
import com.maywide.biz.survey.entity.BizPhotoList;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustMarketingService extends BaseService<CustMarketing, Long> {

	@Autowired
	private CustMarketingDao custMarketingDao;

	@Autowired
	private PersistentService persistentService;

	@Override
	protected BaseDao<CustMarketing, Long> getEntityDao() {
		return custMarketingDao;
	}
	
	public PageImpl queCustMarketings(Pageable pageable,
			MarketBatchParamBO marketBatchParamBO) throws Exception {

		PageImpl pageResult = null;

		// 超级管理员和高权可以查看所有，否则只有按营维中心选择进行查询
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		Page page = new Page();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());

		String operSql = getGridOperSql(marketBatchParamBO);

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT *  FROM (");
		sql.append(" SELECT t.markid as id, ");
		sql.append(" t.batchno, ");
		sql.append(" t.custid, ");
		sql.append(" t.name, ");
		sql.append(" t.linkphone, ");
		sql.append(" t.houseid, ");
		sql.append(" t.whladdr, ");
		sql.append(" t.areaid, ");
		sql.append(" (select s.name from prv_area s where s.areaid=t.areaid) areaname, ");
		sql.append(" t.ptachid, ");
		sql.append(" (select p.patchname from res_patch p where p.patchid=t.ptachid) patchname, ");
		sql.append(" t.pri, ");
		sql.append(" t.knowid, ");
		sql.append(" (select k.knowname from prd_salespkg_know k where k.knowid=t.knowid) knowname, ");
		sql.append(" t.areamger, ");
		sql.append(" (select o.name from prv_operator o where o.operid=t.areamger) areamgername, ");
		sql.append(" t.mgerphone, ");
		sql.append(" t.appdate, ");
		sql.append(" t.operid, ");
		sql.append(" t.deptid, ");
		sql.append(" (select d.name from prv_department d where d.deptid=t.deptid) deptname, ");
		sql.append(" t.dealstatus, ");
		sql.append(" t.alnums, ");
		sql.append(" t.result, ");
		sql.append(" t.resultexp, ");
		sql.append(" t.city	 ");
		sql.append(" FROM biz_cust_marketing t ");
		sql.append(" WHERE 1 = 1  ");
		if (marketBatchParamBO.getKnowid() != null) {
			sql.append(" AND t.knowid= ? ");
			paramList.add(marketBatchParamBO.getKnowid());
		}
		if (marketBatchParamBO.getDeptid() != null) {
			sql.append(" AND t.deptid= ? ");
			paramList.add(marketBatchParamBO.getDeptid());
		}

		if (StringUtils.isNotEmpty(marketBatchParamBO.getDealstatus())) {
			sql.append(" AND t.dealstatus = ? ");
			paramList.add(marketBatchParamBO.getDealstatus());
		}

		if (StringUtils.isNotEmpty(marketBatchParamBO.getCustname())) {
			sql.append(" AND t.name like ?  ");
			paramList.add("%" + marketBatchParamBO.getCustname() + "%");
		}

		if (operSql != null) {
			sql.append(operSql);
		}

		sql.append(" ) v ");

		persistentService.clear();
		page = persistentService.find(page, sql.toString(),
				CustMarketing.class, paramList.toArray());

		if (page != null && page.getResult() != null) {
			int total = page.getTotalCount();
			List<CustMarketing> resultList = page.getResult();
			pageResult = new PageImpl(resultList, pageable, total);
		} else {
			List<CustMarketing> resultList = new ArrayList<CustMarketing>();
			pageResult = new PageImpl(resultList, pageable, 0);
		}

		return pageResult;

	}

	private String getGridOperSql(MarketBatchParamBO marketBatchParamBO) {
		String operSql = "";
		String operidString = "";
		List<Long> operids = marketBatchParamBO.getQueOperids();
		if (operids == null || operids.size() == 0) {
			return null;
		}

		for (Long operid : operids) {
			if (operid == null)
				continue;

			operidString += operid + ",";
		}

		if (StringUtils.isNotEmpty(operidString)) {
			operidString = operidString.substring(0, operidString.length() - 1);
		} else {
			return null;
		}

		operSql = "AND t.areamger in (" + operidString + ")";

		return operSql;
	}
}
