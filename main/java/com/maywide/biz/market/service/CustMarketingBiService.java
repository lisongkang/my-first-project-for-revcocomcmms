package com.maywide.biz.market.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.market.dao.CustMarketingBiDao;
import com.maywide.biz.market.entity.CustMarketing;
import com.maywide.biz.market.entity.CustMarketingBi;
import com.maywide.biz.market.entity.MarketBatch;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class CustMarketingBiService extends BaseService<CustMarketingBi,Long>{
    
    @Autowired
    private CustMarketingBiDao custMarketingBiDao;
    
    @Autowired
	private PersistentService persistentService;
    
    @Autowired
    private PubService pubService;

    @Override
    protected BaseDao<CustMarketingBi, Long> getEntityDao() {
        return custMarketingBiDao;
    }
    
    public void doPush(String ids, String pri) throws Exception {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	if (loginInfo == null) {
    		throw new Exception("用户未登录或已过期");
    	}
    	String[] idarr = ids.split(",");
    	Long knowid = null;
    	MarketBatch batch = null;
    	SalespkgKnow salespkgKnow = null;
    	BizGridInfo grid = null;
    	CustMarketing custMarketing = null;
    	
    	if (StringUtils.isBlank(ids)) {
    		return;
    	}
    	
    	String batchno = persistentService.getSequence("SEQ_BIZ_CUST_MARKETING_BATCHNO").toString();
    	
    	for (String id : idarr) {
    		CustMarketingBi bi = custMarketingBiDao.findOne(new Long(id));
    		custMarketing = createDefaultCustMarketing(bi, loginInfo);
    		SalespkgKnow queryParam = new SalespkgKnow();
        	queryParam.setObjid(bi.getObjid());
        	if (StringUtils.isNotBlank(bi.getObjtype())) {
        		queryParam.setObjtype(bi.getObjtype());
        	}
        	List<SalespkgKnow> list = persistentService.find(queryParam);
        	if (list.size() == 0) {
        		throw new Exception("没有对应objtype[" + bi.getObjtype() + "],objid[" + bi.getObjid() + "]的知识库");
        	}
        	salespkgKnow = list.get(0);
    		
        	Long patchId = (bi.getPtach() == null) ? null : bi.getPtach().getId();
        	grid = pubService.getGrid4Biz(bi.getHouseid(), patchId, loginInfo);
        	if (grid == null) {
        		throw new Exception("没有对应Houseid[" + bi.getHouseid() + "],Ptach[" + bi.getPtach() + "]的网格信息");
        	}
        	BizGridManager gm = getGridManager(grid.getId());
        	if (gm == null) {
        		throw new Exception("没有配置gridid[" + grid.getId() + "]对应的网格经理");
        	}
        	
        	custMarketing.setKnowid(salespkgKnow.getKnowid());
        	custMarketing.setKnowname(salespkgKnow.getKnowname());
        	custMarketing.setBatchno(batchno);
        	custMarketing.setAreamger(gm.getOperid());
        	custMarketing.setPri(pri);
        	custMarketing.setDealstatus(SysConstant.ProdStatus.FINISH);
        	
        	persistentService.save(custMarketing);
        	
        	persistentService.executeUpdate("UPDATE CustMarketingBi SET dealstatus = ? WHERE id = ?", "1", new Long(id));
    	}
    }
    
    private BizGridManager getGridManager(Long gridid) throws Exception {
    	String HQL = "FROM BizGridManager WHERE gridid = ? AND isMain = ?";
    	List<BizGridManager> managers = persistentService.find(HQL, gridid, "Y");
    	if (managers.size() > 0) return managers.get(0);
    	return null;
    }
    
    private CustMarketing createDefaultCustMarketing(CustMarketingBi bi, LoginInfo loginInfo) {
    	CustMarketing cm = new CustMarketing();
    	cm.setCustid(bi.getCustid());
    	cm.setName(bi.getName());
    	cm.setLinkphone(bi.getLinkphone());
    	cm.setHouseid(bi.getHouseid());
    	cm.setWhladdr(bi.getWhladdr());
    	if (bi.getArea() != null) {
    		cm.setAreaid(bi.getArea().getId());
    	}
    	if (bi.getPtach() != null) {
    		cm.setPtachid(bi.getPtach().getId());
    	}
    	cm.setAppdate(new Date());
    	cm.setOperid(loginInfo.getOperid());
    	cm.setDealstatus("0"); //初始状态
    	cm.setAlnums(0L);
    	cm.setCity(loginInfo.getCity());
    	
    	return cm;
    }
    
}
