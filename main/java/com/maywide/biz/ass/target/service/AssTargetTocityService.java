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
import com.maywide.biz.ass.target.dao.AssTargetTocityDao;
import com.maywide.biz.ass.target.entity.AssTargetStore;
import com.maywide.biz.ass.target.entity.AssTargetTocity;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.service.InterPrdService;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

@Service
@Transactional
public class AssTargetTocityService extends BaseService<AssTargetTocity,Long>{
    
    @Autowired
    private AssTargetTocityDao assTargetTocityDao;
    
    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private InterPrdService prdService;

    @Override
    protected BaseDao<AssTargetTocity,Long> getEntityDao() {
        return assTargetTocityDao;
    }
 
    /**
     * tocity 主页查询
     * @param flag togrid-下达网格， topatch-下达片区
     * @param map 参数
     * @param pageable 翻页
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public PageImpl<AssTargetStore> getTargetTocity(Map<String,String> param,Pageable pageable) throws Exception {
		
		PageImpl<AssTargetStore> pageResult = null;
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		
		Page<AssTargetStore> page = new Page<AssTargetStore>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
		sql.append(" SELECT t.ASSID as id,");
		sql.append(" t.ASSCODE,");
		sql.append(" tc.CITY,");
		sql.append(" tc.ASSCITY,");
		sql.append(" code2name(tc.CITY, 'PRV_CITY') cityName,");//这里必须要用tc.city
		sql.append(" code2name(tc.ASSCITY, 'PRV_CITY') assCityName,");//这里必须要用tc.city
		sql.append(" t.NAME,");
		sql.append(" t.FIELDID,");
		sql.append(" t.VISQL,");
		sql.append(" t.ASSCONTENT,");
		sql.append(" IFNULL(tg.ASSID,0) as TOPATCHSTATUS,");
		sql.append(" t.EXPDATE,");
		sql.append(" tc.TOCITY_ID");
		sql.append(" FROM ASS_TARGET_STORE t");
		sql.append(" INNER JOIN ASS_TARGET_TOCITY tc on t.assid = tc.assid ");
		sql.append(" LEFT JOIN (SELECT CITY,ASSID from ASS_TARGET_TOGRID GROUP BY CITY,ASSID) tg on tg.ASSID=tc.ASSID and tg.CITY=tc.CITY ");
		sql.append(" where t.ISDEL=0");
		
		if (param.get("name") != null){
			sql.append(" AND t.name like ? ");
			paramList.add("%" + param.get("name") + "%");
		} 
		if (param.get("city") != null){ //这里用asscity
			sql.append(" AND tc.asscity = ? ");
			paramList.add(param.get("city"));
		}
		
		// 超级管理员可以查看所有管理考核指标
		if (!loginInfo.isAdmin()) { //这里用city
			sql.append(" AND tc.city in('"+loginInfo.getCity()+"') ");
		}
		
		//优先以下发状态排序
		sql.append(" order by tg.ASSID,tc.ASSDATE desc");
		
		
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
    
    /**
     * tocity 获取某个市可下发的指标
     * @param map 参数
     * @param pageable 翻页
     * @return
     * @throws Exception
     */
//    @SuppressWarnings("unchecked")
//	public List<AssTargetStore> getCanPatchStores(Map<String,String> param) throws Exception {
//		
//		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//		
//		StringBuffer sql = new StringBuffer();
//        List<Object> paramList = new ArrayList<Object>();
//        
//		sql.append(" SELECT t.ASSID as id,");
//		sql.append(" t.ASSCODE,");
//		sql.append(" tc.CITY,");
//		sql.append(" code2name(tc.city, 'PRV_CITY') cityName,");//这里必须要用tc.city
//		sql.append(" t.NAME,");
//		sql.append(" t.FIELDID,");
//		sql.append(" t.VISQL,");
//		sql.append(" t.ASSCONTENT,");
//		sql.append(" t.STATUS,");
//		sql.append(" t.EXPDATE,tc.TOCITY_ID from ass_target_store t,ass_target_tocity tc");
//		sql.append(" WHERE t.assid = tc.assid and t.city=tc.asscity and t.ISDEL=0 ");
//
//		if (param.get("name") != null){
//			sql.append(" AND t.name like ? ");
//			paramList.add("%" + param.get("name") + "%");
//		} 
//		if (param.get("city") != null){
//			sql.append(" AND tc.city = ? ");
//			paramList.add( param.get("city"));
//		}
//		if (param.get("status") != null){
//			sql.append(" AND t.status like ? ");
//			paramList.add("%" + param.get("status") + "%");
//		}
//		if (param.get("assCode") != null){
//			sql.append(" AND t.ASSCODE like ? ");
//			paramList.add("%" + param.get("assCode") + "%");
//		}
//		
//		// 超级管理员可以查看所有管理考核指标
//		if (!loginInfo.isAdmin()) {
//			sql.append(" AND tc.city in('"+loginInfo.getCity()+"') ");
//		}
//		
//		sql.append(" ORDER BY city");
//	
//		List<AssTargetStore> list = persistentService.find(sql.toString(), AssTargetStore.class, paramList.toArray());
//		
//		return list;
//	
//    }
    
    /**
     * 获取地市指标将要选择的新数据
     * @param flag
     * @param param
     * @param pageable
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public PageImpl<AssTargetStore> getTargetOfSelect(String flag, Map<String,String> param,Pageable pageable) throws Exception {
    	
    	PageImpl<AssTargetStore> pageResult = null;
		
		Page<AssTargetStore> page = new Page<AssTargetStore>();
		page.setPageNo(pageable.getPageNumber() + 1);
		page.setPageSize(pageable.getPageSize());
		
		StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        
		sql.append(" SELECT t.ASSID as id,");
		sql.append(" t.ASSCODE,");
		sql.append(" t.CITY,");
		sql.append(" code2name(t.city, 'PRV_CITY') cityName,");
		sql.append(" t.NAME,");
		sql.append(" t.FIELDID,");
		sql.append(" t.VISQL,");
		sql.append(" t.ASSCONTENT,");
		sql.append(" t.STATUS,");
		sql.append(" t.EXPDATE from ass_target_store t");
		sql.append(" WHERE t.ISDEL=0 and concat(t.city,t.assid) not in(SELECT concat(c.asscity,c.assid) from ass_target_tocity c where c.city='"+AuthContextHolder.getLoginInfo().getCity()+"')");

		if (param.get("name") != null){
			sql.append(" AND t.name like ? ");
			paramList.add("%" + param.get("name") + "%");
		} 
		if (param.get("city") != null){
			sql.append(" AND t.city = ? ");
			paramList.add( param.get("city"));
		}
		if (param.get("assCode") != null){
			sql.append(" AND t.ASSCODE like ? ");
			paramList.add("%" + param.get("assCode") + "%");
		}

		// 非超级管理，只能查看本省和本市的指标
		if (!AuthContextHolder.getLoginInfo().isAdmin()) {
			sql.append(" AND t.city in('*','"+AuthContextHolder.getLoginInfo().getCity()+"') ");
		}
		
		sql.append(" order by t.ASSID desc");
		
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
