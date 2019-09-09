package com.maywide.biz.prd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prd.dao.PrvPrdRuleDao;
import com.maywide.biz.prd.entity.PrvPrdRule;
import com.maywide.core.cons.Constant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
public class PrvPrdRuleService extends BaseService<PrvPrdRule, Long> {

	@Autowired
	private PrvPrdRuleDao prvPrdRuleDao;

	@Autowired
	private PersistentService persistentService;

	@Override
	protected BaseDao<PrvPrdRule, Long> getEntityDao() {
		return prvPrdRuleDao;
	}

	public PageImpl<PrvPrdRule> findPrdRuleByPage(Pageable pageable) throws Exception {
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		PageImpl<PrvPrdRule> pageResult = null;
		Page<PrvPrdRule> page = new Page<PrvPrdRule>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());

		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT p.`id`,p.`areaid`,p.`objtype`,p.`value`,p.`jumpmenuid`,p.`message`,p.`createoperid`,p.`createtime`,p.`exptime`, ");
		sql.append("CASE p.`objtype` WHEN '0' THEN s.salesname ELSE pkg.salespkgname END prdname, ");
		sql.append("o.`name` opername,m.`name` menuname ");
		sql.append("FROM `prv_prd_rule` p ");
		sql.append("LEFT JOIN prv_operator o ON p.`createoperid` = o.`operid` ");
		sql.append("LEFT JOIN prv_menudef m ON m.`menuid` = p.`jumpmenuid` ");
		sql.append("LEFT JOIN prd_sales s ON s.salescode= p.`value` ");
		sql.append("LEFT JOIN prd_salespkg pkg ON pkg.salespkgcode= p.`value` ");
		
		sql.append("where 1=1 ");
		if (!Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
			sql.append("and p.`areaid` IN (SELECT `areaid` FROM prv_area WHERE city = ? ) ");
			params.add(loginInfo.getCity());
		}
		sql.append("ORDER BY p.id DESC ");
		page = persistentService.find(page, sql.toString(), entityClass, params.toArray());
		if (page != null && page.getResult() != null) {
			pageResult = new PageImpl<PrvPrdRule>(page.getResult(), pageable, page.getTotalCount());
		} else {
			pageResult = new PageImpl<PrvPrdRule>(new ArrayList<PrvPrdRule>(), pageable, 0);
		}
		return pageResult;
	}

}
