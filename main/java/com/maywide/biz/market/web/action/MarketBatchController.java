package com.maywide.biz.market.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.entity.MarketBatch;
import com.maywide.biz.market.pojo.CustMarketInfo;
import com.maywide.biz.market.pojo.MarketBatchParamBO;
import com.maywide.biz.market.service.MarketBatchService;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.market.entity.MarketBatch管理")
public class MarketBatchController extends BaseController<MarketBatch,Long> {

    @Autowired
    private MarketBatchService marketBatchService;
    
    @Autowired
    private PersistentService persistentService;
    
    private MarketBatchParamBO marketBatchParamBO;
    
    private SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
    
    public MarketBatchParamBO getMarketBatchParamBO() {
		return marketBatchParamBO;
	}

	public void setMarketBatchParamBO(MarketBatchParamBO marketBatchParamBO) {
		this.marketBatchParamBO = marketBatchParamBO;
	}

	@Override
    protected BaseService<MarketBatch, Long> getEntityService() {
        return marketBatchService;
    }
    
    @Override
    protected void checkEntityAclPermission(MarketBatch entity) {
        // TODO Add acl check code logic
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        //TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }
    
    @Override
    @MetaData("创建")
    public HttpHeaders doCreate() {
        return super.doCreate();
    }

    @Override
    @MetaData("更新")
    public HttpHeaders doUpdate() {
        return super.doUpdate();
    }
    
    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
    	try {
    		
    		settingMarketBatch();
        	
            return super.doSave();
    	} catch (Exception e) {
    		throw new ServiceException(e.getMessage());
    	}
    	
    }
    
    public HttpHeaders doSavePush() throws Exception {
    	
    	marketBatchService.doSavePush(marketBatchParamBO);
    	
    	setModel(OperationResult.buildSuccessResult("营销推送成功"));
    	
    	return buildDefaultHttpHeaders();
    }
    
    private void settingMarketBatch() throws Exception {
    	// 营销批次号
    	String batchno = persistentService.getSequence("SEQ_BIZ_CUST_MARKETING_BATCHNO")
                .toString();
    	
    	// 检查重复记录
    	if (bindingEntity != null) {
    		marketBatchService.checkMarketBatch(batchno, bindingEntity.getKnowid(), bindingEntity.getAreaids());
    	}
    	
    	String yymmdd = df.format(new Date());
    	batchno =  yymmdd + batchno; 
    	bindingEntity.setBatchno(batchno);
    	
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
     	String city = loginInfo.getCity();
     	Long deptid = loginInfo.getDeptid();
     	Long operid = loginInfo.getOperid();
     	
     	bindingEntity.setCity(city);
     	bindingEntity.setOperid(operid);
     	bindingEntity.setDeptid(deptid);
     	bindingEntity.setAppdate(new Date());
    }

    public HttpHeaders deleteMarketBatch() throws Exception{
        try {
    		String ids = getParameter("ids");
    		marketBatchService.deleteMarketBatch(ids);
    		
    		setModel(OperationResult.buildSuccessResult("删除任务成功", bindingEntity));
    		
		} catch (Exception e) {
			return buildDefaultHttpHeaders();
		}
        
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {

		Pageable pageable = PropertyFilter
				.buildPageableFromHttpRequest(getRequest());
		try {
			
			String statime = getParameter("que_apptime");
			if (StringUtils.isNotEmpty(statime)) {
				 String[] statimeArry = statime.split("～");
				 marketBatchParamBO.setStartPeriod(statimeArry[0].trim());
				 marketBatchParamBO.setEndPeriod(statimeArry[1].trim());
			}

			PageImpl pageResult = marketBatchService.queMarketBatchs(
					pageable, marketBatchParamBO);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<MarketBatch> list = new ArrayList<MarketBatch>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
		
    }
    
    public Map<String, String> getMarketBatchMap() {
        Map<String, String> map = null;
        try {
        	map = marketBatchService.getMarketBatchMap();
        } catch (Exception e) {
        }
        return map;
    }
    
    
    public Map<String, String> getStatusMap() {
        Map<String, String> statusMap = new HashMap<String,String>();
        	
        String invalid = com.maywide.biz.cons.BizConstant.BizMarketBatchStatus.INVALID;
        String valid = com.maywide.biz.cons.BizConstant.BizMarketBatchStatus.VALID;
        	
        statusMap.put(valid, "有效");
        statusMap.put(invalid, "无效");
        	
        return statusMap;
    }
    
    public HttpHeaders getGridOperList() throws Exception {
        List<BizGridManager> gridOperList = marketBatchService.getGridOperList();
    	setModel(gridOperList);
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders getDepts() throws Exception {
    	List<PrvDepartment> deptlist = marketBatchService.getDepts();
    	setModel(deptlist);
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders getAreaids() throws Exception {
    	List<PrvArea> arealist = marketBatchService.getAreaids();
    	setModel(arealist);
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders getKnowids() throws Exception {
    	List<SalespkgKnow> knowslist = marketBatchService.getKnowids();
    	setModel(knowslist);
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders toMarketPush() throws Exception {
    	String taskId = getParameter("taskId");
    	
    	// 根据taskId获取MarketBatch对象
    	MarketBatch marketBatch = marketBatchService.getMarketBatch(Long.parseLong(taskId)); 
    	
    	setModel(marketBatch);
    	return buildDefaultHttpHeaders("pushInputBasic");
    }
    
	public HttpHeaders selectCustMarket() throws Exception {
		String taskid = getParameter("taskid");
    	
    	// 根据taskId获取MarketBatch对象
    	MarketBatch marketBatch = marketBatchService.getMarketBatch(Long.parseLong(taskid)); 
    	
    	setModel(marketBatch);

        return buildDefaultHttpHeaders("custcomposition");
    }
	
    public HttpHeaders findCust() throws Exception {

    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try {
			 Long assid = StringUtils.isEmpty(getParameter("assid"))? null : Long.parseLong(getParameter("assid"));
			 
			 String strGrids = getParameter("strGrids");
			 String knowid = getParameter("knowid");
			 String patchid = getParameter("patchid");
			 String netattr = getParameter("netattr");
			 Long lKnowid = Long.parseLong(knowid);
			 
			 PageImpl pageResult = marketBatchService.findCust(lKnowid, patchid, netattr, pageable);
			 
//			 List<CustMarketInfo> list = new ArrayList<CustMarketInfo>();
//			 for (int i = 0; i < 20; i++) {
//				 CustMarketInfo custMarketInfo = new CustMarketInfo();
//				 custMarketInfo.setCustid((1000L+i));
//				 custMarketInfo.setCustname("李四"+i);
//				 custMarketInfo.setWhladdr("广州大道南368");
//				 custMarketInfo.setMobile("13433333333");
//				 custMarketInfo.setAreaid("1000");
//				 custMarketInfo.setHouseid("1000");
//				 custMarketInfo.setPatchid("10001");
//				 
//				 list.add(custMarketInfo);
//			 }
//			 
//			 PageImpl pageResult = new PageImpl(list);
					 
		     setModel(pageResult);
		     
			 return buildDefaultHttpHeaders();
			
		} catch (Exception e) {
			List<CustMarketInfo> list = new ArrayList<CustMarketInfo>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    
    }
    
    
}