package com.maywide.biz.tmpopenlimit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.pojo.quetempopenplan.QueTempopenPlanInterReq;
import com.maywide.biz.inter.pojo.quetempopenplan.QueTempopenPlanInterResp;
import com.maywide.biz.inter.pojo.quetempopenplan.TempopenPlanInfoBO;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.tmpopenlimit.dao.BizTmpOpenLimitDao;
import com.maywide.biz.tmpopenlimit.entity.BizTmpOpenLimit;
import com.maywide.biz.tmpopenlimit.entity.BizTmpOpenLimitBO;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional
public class BizTmpOpenLimitService extends BaseService<BizTmpOpenLimit,Long>{
    
    @Autowired
    private BizTmpOpenLimitDao bizTmpOpenLimitDao;    
    @Autowired
    private PubService pubService;
    @Autowired
    private PersistentService persistentService;
    @Override
    protected BaseDao<BizTmpOpenLimit, Long> getEntityDao() {
        return bizTmpOpenLimitDao;
    }
    
    public Page queryOpenPlan( String areaid,Pageable pageable)
            throws Exception {   	
    	try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            CheckUtils.checkNull(loginInfo, "用户未登录或已过期");
            QueTempopenPlanInterReq req = new QueTempopenPlanInterReq();
//            Long bizorderid = persistentService.getSequence("SEQ_BIZ_CUSTORDER_ID");
//    		req.setBizorderid(String.valueOf(bizorderid));
            req.setBizorderid(getBizorderid());
            req.setPagesize("2000");
            req.setCurrentPage("1");  
//            if (null != pageable) {
//                if (pageable.getPageSize() > 0) {
//                    req.setPagesize(String.valueOf(pageable.getPageSize()));
//                }
//                if (pageable.getPageNumber() > 0) {
//                    req.setCurrentPage(String.valueOf(pageable.getPageNumber()+1));
//                }
//            } 
            List<TempopenPlanInfoBO> resHouseList = new ArrayList<TempopenPlanInfoBO>();  
            QueTempopenPlanInterResp resp = new QueTempopenPlanInterResp();
            if(areaid.indexOf(",")>0){            
            	String[] areas = areaid.split(",");
            	for(int i =0 ;i<areas.length;i++){
            		req.setAreaid(areas[i]);
            		pubService.queTempopenPlan(req, resp);
            		List<TempopenPlanInfoBO> resHouseListtmp = resp.getPlans();
            	    resHouseList.addAll(resHouseListtmp) ;
            	}
            }else{
            	    req.setAreaid(areaid);            	   
    	            pubService.queTempopenPlan(req, resp);
    	            resHouseList = resp.getPlans();
            }                               
            Page retPage = new PageImpl(resHouseList, pageable, resHouseList.size());
            return retPage;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}       
    }
    
    public BizTmpOpenLimit findLimitNums(String objType,String timeType, Long planId) throws Exception{
    	BizTmpOpenLimit tmpopen = new BizTmpOpenLimit();
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        sql.append(" SELECT * FROM biz_tmpopen_limit WHERE planid = ? AND objtype=? AND objid=? AND timetype=? ");   
        paramList.add(planId);
        paramList.add(objType);
        if("0".equals(objType)){
        	paramList.add(loginInfo.getDeptid());
        }else{
        	paramList.add(loginInfo.getOperid());
        }      
        paramList.add(timeType);

        List<BizTmpOpenLimit> bizTmpOpenLimitList = persistentService.find(sql.toString(),
        		BizTmpOpenLimit.class, paramList.toArray());
        if (BeanUtil.isListNull(bizTmpOpenLimitList)) {
        	tmpopen = null;
        } else {
        	tmpopen = bizTmpOpenLimitList.get(0);
        }
		return tmpopen;
    	
    }
    
    public Page queryTmpOpenLimit(BizTmpOpenLimitBO tmpopen,Pageable pageable) throws Exception{
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        sql.append(" SELECT recid id , t.* FROM biz_tmpopen_limit t WHERE 1=1   "); 
        if(tmpopen.getObjType()!=null&&!"".equals(tmpopen.getObjType())){
        	sql.append("  AND t.objtype=?  ");
        	 paramList.add(tmpopen.getObjType());
        }       
        if(tmpopen.getTimeType()!=null&&!"".equals(tmpopen.getTimeType())){
        	sql.append("  AND t.timetype=?  ");
        	 paramList.add(tmpopen.getTimeType());
        }
        if(tmpopen.getName()!=null&&!"".equals(tmpopen.getName())){
        	 sql.append("  AND t.name like ?  ");
        	 paramList.add("%" + tmpopen.getName() + "%");
        }
        if(!"".equals(loginInfo.getCity())&&null!=loginInfo.getCity()&&!"GD".equals(loginInfo.getCity())){
        	sql.append("  AND t.city=?  ");
       	 paramList.add(loginInfo.getCity());
       }
        List<BizTmpOpenLimit> bizTmpOpenLimitList = persistentService.find(sql.toString(),
        		BizTmpOpenLimit.class, paramList.toArray());
        Page retPage = new PageImpl(bizTmpOpenLimitList, pageable, bizTmpOpenLimitList.size());
        return retPage;   	
    }
    
    public Page queryTmpOpenLimitByDepartment(BizTmpOpenLimitBO tmpopen,Pageable pageable) throws Exception{
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        sql.append(" SELECT recid id , t.* FROM biz_tmpopen_limit t ,prv_department dep WHERE dep.deptid = t.OBJID  "); 
        if(tmpopen.getObjType()!=null&&!"".equals(tmpopen.getObjType())){
        	sql.append("  AND t.objtype=?  ");
        	 paramList.add(tmpopen.getObjType());
        }
        if(tmpopen.getObjName()!=null&&!"".equals(tmpopen.getObjName())){
        	sql.append("  AND dep.name like ?  ");
        	 paramList.add("%" + tmpopen.getObjName() + "%");
        }
        if(tmpopen.getTimeType()!=null&&!"".equals(tmpopen.getTimeType())){
        	sql.append("  AND t.timetype=?  ");
        	 paramList.add(tmpopen.getTimeType());
        }
        if(tmpopen.getName()!=null&&!"".equals(tmpopen.getName())){
        	 sql.append("  AND t.name like ?  ");
        	 paramList.add("%" + tmpopen.getName() + "%");
        }
        if(!"".equals(loginInfo.getCity())&&null!=loginInfo.getCity()&&!"GD".equals(loginInfo.getCity())){
        	sql.append("  AND t.city=?  ");
       	 paramList.add(loginInfo.getCity());
       }
        List<BizTmpOpenLimit> bizTmpOpenLimitList = persistentService.find(sql.toString(),
        		BizTmpOpenLimit.class, paramList.toArray());
        Page retPage = new PageImpl(bizTmpOpenLimitList, pageable, bizTmpOpenLimitList.size());
        return retPage;   	
    }
    
    public Page queryTmpOpenLimitByOperator(BizTmpOpenLimitBO tmpopen,Pageable pageable) throws Exception{
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        sql.append(" SELECT recid id , t.* FROM biz_tmpopen_limit t ,prv_operator ope WHERE ope.operid = t.OBJID  "); 
        if(tmpopen.getObjType()!=null&&!"".equals(tmpopen.getObjType())){
        	sql.append("  AND t.objtype=?  ");
        	 paramList.add(tmpopen.getObjType());
        }
        if(tmpopen.getObjName()!=null&&!"".equals(tmpopen.getObjName())){
        	sql.append("  AND ope.name like ? ");
        	 paramList.add("%" + tmpopen.getObjName() + "%");
        }
        if(tmpopen.getTimeType()!=null&&!"".equals(tmpopen.getTimeType())){
        	sql.append("  AND t.timetype=?  ");
        	 paramList.add(tmpopen.getTimeType());
        }
        if(tmpopen.getName()!=null&&!"".equals(tmpopen.getName())){
        	 sql.append("  AND t.name like ?  ");
        	 paramList.add("%" + tmpopen.getName() + "%");
        }
        if(!"".equals(loginInfo.getCity())&&null!=loginInfo.getCity()&&!"GD".equals(loginInfo.getCity())){
        	sql.append("  AND t.city=?  ");
       	 paramList.add(loginInfo.getCity());
       }
        List<BizTmpOpenLimit> bizTmpOpenLimitList = persistentService.find(sql.toString(),
        		BizTmpOpenLimit.class, paramList.toArray());
        Page retPage = new PageImpl(bizTmpOpenLimitList, pageable, bizTmpOpenLimitList.size());
        return retPage;   	
    }
    
	public Long queryOpenNums(String objType, String timeType, Long planid) throws Exception {
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();
		sql.append(
				" SELECT b.OPRDEP FROM BIZ_APPLY_TMPOPEN a ,BIZ_CUSTORDER  b WHERE a.orderid=b.orderid AND b.ORDERSTATUS ='SYNC' ");
		if ("0".equals(timeType)) {
			sql.append(" AND b.OPTIME>DATE_FORMAT(SYSDATE(),'%Y-%m') ");
		} else {
			sql.append(" AND b.OPTIME>DATE_FORMAT(SYSDATE(),'%Y-%m-%d') ");
		}
		sql.append(" AND a.planid=? ");
		paramList.add(planid);
		if ("0".equals(objType)) {
			sql.append(" AND b.OPRDEP=? ");
			paramList.add(loginInfo.getDeptid());
		} else {
			sql.append(" AND b.OPERATOR=? ");
			paramList.add(loginInfo.getOperid());
		}
		Long count = persistentService.count(sql.toString(), paramList.toArray());
		return count;
	}
}
