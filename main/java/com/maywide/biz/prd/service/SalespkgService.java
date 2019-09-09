package com.maywide.biz.prd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prd.dao.SalespkgDao;
import com.maywide.biz.prd.entity.Salespkg;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.SimpleSqlCreator;

@Service
@Transactional
public class SalespkgService extends BaseService<Salespkg,Long>{
    
    @Autowired
    private SalespkgDao salespkgDao;
    
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<Salespkg, Long> getEntityDao() {
        return salespkgDao;
    }

    public Object findByPageForJump(String codeOrNameField,String objtype, String searchCity, String areaid, Pageable pageable,
            String orderField, String sortType) throws Exception {
        PageImpl<Salespkg> pageResult = null;
        Page<Salespkg> page = new Page<Salespkg>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = SimpleSqlCreator.createSelectAllFieldSql(entityClass).append(" WHERE 1=1");
        List<Object> paramList = new ArrayList<Object>();

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		sql.append(" AND o.SALESPKGID   IN (SELECT  OBJID FROM prd_salespkg_know WHERE city=? ) ");
		paramList.add(loginInfo.getCity());
        
		if (StringUtils.isNotBlank(codeOrNameField)) {
			String searchValue = "%" + codeOrNameField + "%";
			sql.append(" AND (o.SALESCODE LIKE ?");
			sql.append(" OR o.SALESNAME LIKE ?) ");
			paramList.add(searchValue);
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

        List<Salespkg> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<Salespkg>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<Salespkg>(new ArrayList<Salespkg>(), pageable, 0);
        }
        return pageResult;
    }
    
}
