package com.maywide.biz.az.quota.service;

import com.maywide.biz.ass.target.entity.AssTargetTogrid;
import com.maywide.biz.az.quota.dao.AzQuotaDao;
import com.maywide.biz.az.quota.entity.ConstSettlement;
import com.maywide.biz.az.quota.pojo.AzSearchParamsBo;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongkang on 2019/4/9 0001.
 */
@Service
@Transactional
public class AzQuotaService extends BaseService<ConstSettlement,Long> {
    private static Logger log = LoggerFactory.getLogger(AzQuotaService.class);
    @Autowired
    private AzQuotaDao azQuotaDao;
    @Autowired
    private PersistentService persistentService;
    @Override
    protected BaseDao<ConstSettlement,Long> getEntityDao(){return azQuotaDao;}

    @SuppressWarnings("unchecked")
    public PageImpl<ConstSettlement> findByPage(AzSearchParamsBo azSearchParamsBo,
                                                Pageable pageable, String city, String newnumber, String constname) throws Exception {
        PageImpl<ConstSettlement> pageResult = null;
        Page<ConstSettlement> page = new Page<ConstSettlement>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT a.constid as id,a.city as city,a.newnumber,a.oldnumber,a.constname,a.constdetail, ");
        sql.append(" a.company,a.oneprice,a.twoprice,a.threeprice,a.fourprice,a.constcontent,a.fileids");
        sql.append(" from construction_settlement_unit_price a where 1=1");
        if(azSearchParamsBo!= null){
            if(StringUtils.isNotEmpty(azSearchParamsBo.getCity())){
                sql.append(" AND a.city = ?");
                paramList.add(azSearchParamsBo.getCity());
            }else {
                //没有定位地市就不限制地市
            }
        }else {//超级账号查所有及查地市
            if(StringUtils.isNotEmpty(city)){
                sql.append(" AND a.city = ?");
                paramList.add(city);
            }else {
                //查所有
            }

        }
        if(StringUtils.isNotEmpty(newnumber)){
            sql.append(" AND a.newnumber like ?");
            newnumber = "%" + newnumber + "%";
            paramList.add(newnumber);
        }
        if(StringUtils.isNotEmpty(constname)){
            sql.append(" AND a.constname like ?");
            constname = "%" + constname + "%";
            paramList.add(constname);
        }
        sql.append(" order by a.constid ");
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
        List<ConstSettlement> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<ConstSettlement>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<ConstSettlement>(new ArrayList<ConstSettlement>(), pageable, 0);
        }
        return pageResult;
    }


    public List<ConstSettlement> getConstSettlementByNewnumber(String newnumber){
        List<ConstSettlement> resultList = null;
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append(" select * from construction_settlement_unit_price a where 1=1");
        sql.append(" AND newnumber = ?");
        paramList.add(newnumber);
        try {
            persistentService.clear();
            resultList = persistentService.find(sql.toString(), entityClass, paramList.toArray());
        }catch (Exception e){
            log.error("获取施工服务对象异常");
        }
        return resultList;
    }

    /**
     * 检查数据是否重复
     * @param
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public boolean checkDataExists(AssTargetTogrid grid) throws Exception {

        StringBuilder buff = new StringBuilder();
        buff.append("SELECT GRIDID,CITY,ASSID,CYCLENUM from ASS_TARGET_TOGRID WHERE ISDEL=0");
        buff.append(" AND GRIDID="+grid.getGridId());
        buff.append(" AND CITY='"+grid.getCity()+"'");
        buff.append(" AND ASSID="+grid.getAssId());

        persistentService.clear();
        List list=persistentService.find(buff.toString(), AssTargetTogrid.class, null);
        if(list.size()>0) return true;

        return false;
    }
}
