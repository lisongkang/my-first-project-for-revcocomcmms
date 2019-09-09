package com.maywide.biz.az.quotabl.service;

import com.maywide.biz.az.quotabl.dao.AzQuotaDeblDao;
import com.maywide.biz.az.quotabl.entity.ConstSettleRatio;
import com.maywide.biz.az.quotabl.pojo.AzSerchBlParamsBo;
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
 * Created by lisongkang on 2019/4/12 0001.
 */
@Service
@Transactional
public class AzQuotablService extends BaseService<ConstSettleRatio,Long> {
    @Autowired
    private AzQuotaDeblDao azQuotaDeblDao;
    @Autowired
    private PersistentService persistentService;
    @Override
    protected BaseDao<ConstSettleRatio,Long> getEntityDao(){return azQuotaDeblDao;}

    @SuppressWarnings("unchecked")
    public PageImpl<ConstSettleRatio> findAzblByPage(AzSerchBlParamsBo azSerchBlParamsBo,
                                                     Pageable pageable, String city) throws Exception {
        PageImpl<ConstSettleRatio> pageResult = null;
        Page<ConstSettleRatio> page = new Page<ConstSettleRatio>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT a.numid as id,a.city as city,a.citydivde as citydivde,a.quotaratio as quotaratio  from  biz_city_divde a where 1=1  ");
        if(azSerchBlParamsBo != null){
            if(StringUtils.isNotEmpty(azSerchBlParamsBo.getCity())){
                sql.append(" AND a.city = ?");
                paramList.add(azSerchBlParamsBo.getCity());
            }else {
                //查所有
            }
        }else {
            if(StringUtils.isNotEmpty(city)){
                sql.append(" AND a.city = ?");
                paramList.add(city);
            }else {
                //查所有
            }
        }
        sql.append(" order by a.numid ");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
        List<ConstSettleRatio> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<ConstSettleRatio>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<ConstSettleRatio>(new ArrayList<ConstSettleRatio>(), pageable, 0);
        }
        return pageResult;
    }

}
