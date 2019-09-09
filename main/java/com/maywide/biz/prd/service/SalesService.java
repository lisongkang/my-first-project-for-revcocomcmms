package com.maywide.biz.prd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prd.dao.SalesDao;
import com.maywide.biz.prd.entity.Sales;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.SimpleSqlCreator;

/**
 * <p>
 * 商品service
 * <p>
 * Create at 2016年6月6日
 * 
 * @author pengjianqiang
 */
@Service
@Transactional
public class SalesService extends BaseService<Sales, Long> {
    @Autowired
    private SalesDao          salesDao;

    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<Sales, Long> getEntityDao() {
        return salesDao;
    }

    @SuppressWarnings("unchecked")
    public Object findPageSalesByCity(String codeOrNameField,String objtype, String searchCity, String areaid, Pageable pageable,
            String orderField, String sortType) throws Exception {
        PageImpl<Sales> pageResult = null;
        Page<Sales> page = new Page<Sales>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = SimpleSqlCreator.createSelectAllFieldSql(entityClass).append(" WHERE 1=1");
        List<Object> paramList = new ArrayList<Object>();

        // sales表的city都为空，暂时不用作查询条件
        // LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        // String city = loginInfo.getCity();
        //
        // if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
        // // 超级管理员查询全部地市的部门
        // sql.append(" AND o.city=?");
        // paramList.add(city);
        // }
         if (StringUtils.isNotEmpty(searchCity)) {
	         sql.append(" AND (o.city=? or o.city = ?)");
	         paramList.add(searchCity);
	         paramList.add("*");
         }
         
         //如果是商品小类
         if(StringUtils.isNotEmpty(objtype)){
        	 if(objtype.equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)){
        		  sql.append(" AND O.isbasic='Y' AND O.isjoin='Y' ");
        	 }
         }
        if (StringUtils.isNotBlank(codeOrNameField)) {
            	String searchValue = "%" + codeOrNameField + "%";
       		    sql.append(" AND (o.SALESCODE LIKE ?");
                sql.append(" OR o.SALESNAME LIKE ?) ");
                paramList.add(searchValue);
                paramList.add(searchValue);
                
        }

        if (StringUtils.isNotBlank(areaid)) {
            String searchValue = "%" + areaid + "%";
            sql.append(" AND o.AREAID LIKE ?");
            paramList.add(searchValue);
        }

        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
            sql.append(" ORDER BY o.").append(orderField)
                    .append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "");
        } else {
            sql.append(" ORDER BY o.CREATETIME DESC");
        }

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<Sales> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<Sales>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<Sales>(new ArrayList<Sales>(), pageable, 0);
        }
        return pageResult;
    }
    

    public Object findByPageForJump(String codeOrNameField,String objtype, String searchCity, String areaid, Pageable pageable,
            String orderField, String sortType) throws Exception {
        PageImpl<Sales> pageResult = null;
        Page<Sales> page = new Page<Sales>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = SimpleSqlCreator.createSelectAllFieldSql(entityClass).append(" WHERE 1=1");
        List<Object> paramList = new ArrayList<Object>();

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		sql.append(" AND o.SALESID  IN (SELECT  OBJID FROM prd_salespkg_know WHERE city=? ) ");
		paramList.add(loginInfo.getCity());
        
		if (StringUtils.isNotBlank(codeOrNameField)) {
			String searchValue = "%" + codeOrNameField + "%";
			sql.append(" AND (o.SALESCODE LIKE ?");
			sql.append(" OR o.SALESNAME LIKE ?) ");
			paramList.add(searchValue);
			paramList.add(searchValue);
		}

        if (StringUtils.isNotBlank(areaid)) {
            String searchValue = "%" + areaid + "%";
            sql.append(" AND o.AREAID LIKE ?");
            paramList.add(searchValue);
        }

        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
            sql.append(" ORDER BY o.").append(orderField)
                    .append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "");
        } else {
            sql.append(" ORDER BY o.CREATETIME DESC");
        }

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());

        List<Sales> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<Sales>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<Sales>(new ArrayList<Sales>(), pageable, 0);
        }
        return pageResult;
    }
    
}
