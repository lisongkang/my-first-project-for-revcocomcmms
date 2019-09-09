package com.maywide.biz.ass.target.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.store.entity.AssIndexStore;
import com.maywide.biz.ass.target.dao.AssTargetStoreDao;
import com.maywide.biz.ass.target.entity.AssTargetStore;
import com.maywide.biz.ass.togrid.entity.AssIndexTogrid;
import com.maywide.biz.ass.topatch.entity.AssIndexTopatch;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.pojo.querysalespkgknow.QuerySalespkgKnowInterReq;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgKnowsBO;
import com.maywide.biz.inter.service.InterPrdService;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

@Service
@Transactional
public class AssTargetStoreService extends BaseService<AssTargetStore,Long>{
    
    @Autowired
    private AssTargetStoreDao assTargetStoreDao;
    
    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private InterPrdService prdService;

    @Override
    protected BaseDao<AssTargetStore,Long> getEntityDao() {
        return assTargetStoreDao;
    }
 
    /**
     * 查询考核内容
     * @param flag togrid-下达网格， topatch-下达片区
     * @param map 参数
     * @param pageable 翻页
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public PageImpl<AssTargetStore> queAssTargetStores(String flag, Map<String,String> param,Pageable pageable) throws Exception {
		
		PageImpl<AssTargetStore> pageResult = null;
		
		Page<AssTargetStore> page = new Page<AssTargetStore>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		
		
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
		sql.append(" SELECT t.ASSID as id,");
		sql.append(" t.ASSCODE,");
		sql.append(" t.CITY,");
		sql.append(" CODE2NAME(t.city, 'PRV_CITY') cityName,");
		sql.append(" t.NAME,");
		sql.append(" t.FIELDID,");
		sql.append(" t.VISQL,");
		sql.append(" t.ASSCONTENT,");
		sql.append(" t.STATUS,");
		sql.append(" t.ASS_STT_CYCLE AS assSttCycle,");
		sql.append(" t.EXPDATE from ass_target_store t");
		sql.append(" WHERE (ISDEL=0 or ISDEL IS NULL) ");

		if (param.get("name") != null){
			sql.append(" AND t.name like ? ");
			paramList.add("%" + param.get("name") + "%");
		} 
		if (param.get("city") != null){
			sql.append(" AND t.city = ? ");
			paramList.add( param.get("city"));
		}
		if (param.get("status") != null){
			sql.append(" AND t.status like ? ");
			paramList.add("%" + param.get("status") + "%");
		}
		
		// 超级管理员可以查看所有管理考核指标
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if (!loginInfo.isAdmin()) {
			sql.append(" AND t.city in('*','"+loginInfo.getCity()+"')");
		}
		
		sql.append(" ORDER BY ASSID DESC ");
		
		persistentService.clear();
		page = persistentService.find(page, sql.toString(), AssTargetStore.class, paramList.toArray());
		
		
		if (page != null && page.getResult() != null){
			int total = page.getTotalCount();
			List<AssTargetStore> resultList = page.getResult();
			pageResult = new PageImpl<AssTargetStore>(resultList, pageable, total);
		} else {
			List<AssTargetStore> resultList = new ArrayList<AssTargetStore>();
			pageResult = new PageImpl<AssTargetStore>(resultList, pageable, 0);
		}
		
		return pageResult;
	
    }
    
    private String getToTaskCountSQL(String flag) {
    	StringBuilder sql = new StringBuilder();
    	if ("togrid".equals(flag)) {
    		sql.append(" (SELECT COUNT(*) FROM ass_index_togrid g WHERE t.assid=g.assid) gridcount");
            
    	} else {
    		sql.append(" (SELECT COUNT(*) FROM ass_index_topatch g WHERE t.assid=g.assid) gridcount");
    	}
    	
    	return sql.toString();
    }
    

    
    @SuppressWarnings("unchecked")
	public AssIndexStore getAssIndexStore(Long id) throws Exception{
    	
    	StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        

        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.assid as id, ");
        sql.append(" t.city, ");
        sql.append(" code2name(t.city, 'PRV_CITY') cityName, ");
        sql.append(" t.asscontent, ");
        sql.append(" t.assparam, ");
        sql.append(" code2name(t.assparam, 'BIZ_CHECKPARAM') assparamName, ");
        sql.append(" (SELECT l.assobj FROM ass_index_object l WHERE l.assid=t.assid) objid, ");
        sql.append(" (SELECT m.knowname FROM ass_index_object l, prd_salespkg_know m WHERE l.assid=t.assid AND l.assobj=m.objid LIMIT 1) assobj, ");
        sql.append(" t.unit, ");
        sql.append(" code2name(t.unit, 'BIZ_ASS_UNIT') unitname, ");
        sql.append(" t.taskunit, ");
        sql.append(" code2name(t.taskunit, 'BIZ_TASKUNIT') taskunitName, ");
        sql.append(" t.totalnum, ");
        sql.append(" t.expdate, ");
        sql.append(" t.operator, ");
        sql.append(" (SELECT name FROM prv_operator k WHERE k.operid=t.operator) opername,");
        sql.append(" t.opdate, ");
        sql.append(" t.depart, ");
        sql.append(" (SELECT name FROM prv_department k WHERE k.deptid=t.depart) deptName");
        sql.append(" FROM ass_index_store t ");
        sql.append(" WHERE 1 = 1 ");
        
		if (id != null){
			sql.append(" AND t.assid = ? ");
			paramList.add(id);
		} 
		
		sql.append(" ) v ");
		
		persistentService.clear();
		List<AssIndexStore> list = persistentService.find(sql.toString(), AssIndexStore.class, paramList.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
    }
    
    @SuppressWarnings("unchecked")
	public boolean checkExistRecord(AssIndexStore assIndexStore) throws Exception {
    	
    	StringBuilder buff = new StringBuilder();
    	List<Object> plist = new ArrayList<Object>();
    	String curDate = DateUtils.getFormatDateString(new Date(), "yyyy-MM-dd");
    	
    	buff.append(" SELECT a.* from ass_index_store a, ass_index_object b ");
    	buff.append(" WHERE 1=1 ");
    	buff.append(" AND a.assid = b.assid ");
    	buff.append(" AND a.city = ? ");
    	buff.append(" AND a.assparam = ? ");
    	buff.append(" AND b.assobj = ? ");
    	buff.append(" AND a.expdate >= STR_TO_DATE(?, '%Y-%m-%d')");
    	
    	plist.add(assIndexStore.getCity());
    	plist.add(assIndexStore.getAssparam());
    	plist.add(assIndexStore.getObjid());
    	plist.add(curDate);
    	
    	List<AssIndexStore> list = persistentService.find(buff.toString(), AssIndexStore.class, plist.toArray());
    	if (list != null && list.size() > 0) {
    		return true;
    	}
    	return false;
    	
    }
}
