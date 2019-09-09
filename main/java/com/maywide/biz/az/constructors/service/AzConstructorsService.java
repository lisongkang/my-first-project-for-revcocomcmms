package com.maywide.biz.az.constructors.service;

import com.maywide.biz.az.constructors.dao.AzConstructorsDao;
import com.maywide.biz.az.constructors.entity.PrvOperatorInfo;
import com.maywide.biz.az.constructors.pojo.AzConstParamsBo;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongkang on 2019/4/16 0001.
 */
@Service
@Transactional
public class AzConstructorsService extends BaseService<PrvOperatorInfo,Long> {
    @Autowired
    private AzConstructorsDao azConstructorsDao;
    @Autowired
    private PersistentService persistentService;
    @Override
    protected BaseDao<PrvOperatorInfo,Long> getEntityDao(){return azConstructorsDao;}

    @SuppressWarnings("unchecked")
    public PageImpl<PrvOperatorInfo> findByPage(AzConstParamsBo azConstParamsBo,Pageable pageable) throws Exception{
        PageImpl<PrvOperatorInfo> pageResult = null;
        Page<PrvOperatorInfo> page = new Page<PrvOperatorInfo>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT a.id,a.operid,a.loginname,a.name,a.idcard,a.bankcard ");
        sql.append("  from prv_operator_idbankcard a where 1=1 ");

        if(azConstParamsBo!= null){
            if(StringUtils.isNotEmpty(azConstParamsBo.getLoginname())){
                sql.append(" AND a.loginname like ? ");
                String loginame = "%" + azConstParamsBo.getLoginname() + "%";
                paramList.add(loginame);
            }
            if(StringUtils.isNotEmpty(azConstParamsBo.getName())){
                sql.append(" AND a.name like ? ");
                String name = "%" + azConstParamsBo.getName() + "%";
                paramList.add(name);
            }
        }
        sql.append(" order by a.id");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
        List<PrvOperatorInfo> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<PrvOperatorInfo>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<PrvOperatorInfo>(new ArrayList<PrvOperatorInfo>(), pageable, 0);
        }

        return pageResult;

    }
}
