package com.maywide.biz.market.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.market.dao.ResPatchDao;
import com.maywide.biz.market.entity.ResPatch;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.SimpleSqlCreator;

@Service
@Transactional
public class ResPatchService extends BaseService<ResPatch,Long>{
    
    @Autowired
    private ResPatchDao resPatchDao;
    
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseDao<ResPatch, Long> getEntityDao() {
        return resPatchDao;
    }
    
    public void transPatch(List<ResPatch> list) throws Exception {
    	for (ResPatch patch : list) {
    		transPatch(patch);
    	}
    }
    
    public void transPatch(ResPatch patch) throws Exception {
    	patch.addExtraAttribute("areaname", getAreaName(patch.getAreaid()));
    }
    
    /**
     * 查询常用数据用于缓存
     * @return
     */
    public List<ResPatch> findAllPatchs() {
    	return Lists.newArrayList(resPatchDao.findAllCached());
    }
    
    private String getAreaName(Long areaid) throws Exception {
    	PrvArea area = (PrvArea) persistentService.find(PrvArea.class, areaid);
    	if (area == null) return "";
    	return area.getName();
    }

    @SuppressWarnings("unchecked")
    public PageImpl<ResPatch> findUnbindPatchByGridId(String queryParam, Pageable pageable, String orderField,
            String sortType, BizGridInfo gridInfo, List<BizGridInfo> bindedPatchList) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT * FROM(");
        sql.append(SimpleSqlCreator.createSelectAllFieldSql(ResPatch.class));
        sql.append(",").append(SimpleSqlCreator.getEntityTableName(PrvArea.class)).append(" a");
        sql.append(" WHERE o.areaid=a.areaid AND a.city=?");

        List<Object> paramList = new ArrayList<Object>();
        paramList.add(gridInfo.getCity());

        if (StringUtils.isNotBlank(queryParam)) {
            sql.append(" AND (o.patchname LIKE ? OR o.defcode LIKE ?)");
            paramList.add("%" + queryParam + "%");
            paramList.add("%" + queryParam + "%");
        }

        if (null != bindedPatchList && bindedPatchList.size() > 0) {
            sql.append(" AND o.patchid NOT IN(");
            for (int i = 0, size = bindedPatchList.size(); i < size; i++) {
                sql.append("?");
                if (i < size - 1) {
                    sql.append(",");
                }

                paramList.add(bindedPatchList.get(i).getPatchid());
            }
            sql.append(")");
        }

        if (StringUtils.isNotBlank(orderField) && !"createdDate".equals(orderField)) {
            sql.append(" ORDER BY o.").append(orderField);
            sql.append(StringUtils.isNotBlank(sortType) ? (" " + sortType) : "").append(") n");
        } else {
            sql.append(" ORDER BY o.defcode) n");
        }

        Page<ResPatch> page = new Page<ResPatch>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        persistentService.clear();
        page = persistentService.find(page, sql.toString(), ResPatch.class, paramList.toArray());

        PageImpl<ResPatch> pageResult = null;
        List<ResPatch> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<ResPatch>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<ResPatch>(new ArrayList<ResPatch>(), pageable, 0);
        }
        return pageResult;
    }
}
