package com.maywide.biz.market.web.action;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.market.entity.CustMarketing;
import com.maywide.biz.market.entity.MarketBatch;
import com.maywide.biz.market.pojo.MarketBatchParamBO;
import com.maywide.biz.market.service.CustMarketingService;
import com.maywide.biz.market.service.MarketBatchService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.market.entity.CustMarketing管理")
public class CustMarketingController extends BaseController<CustMarketing,Long> {

    @Autowired
    private CustMarketingService custMarketingService;
    
    @Autowired
    private MarketBatchService marketBatchService;
    
    private MarketBatchParamBO marketBatchParamBO;
    
    public MarketBatchParamBO getMarketBatchParamBO() {
		return marketBatchParamBO;
	}

	public void setMarketBatchParamBO(MarketBatchParamBO marketBatchParamBO) {
		this.marketBatchParamBO = marketBatchParamBO;
	}

	@Override
    protected BaseService<CustMarketing, Long> getEntityService() {
        return custMarketingService;
    }
    
    @Override
    protected void checkEntityAclPermission(CustMarketing entity) {
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
        return super.doSave();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {

		Pageable pageable = PropertyFilter
				.buildPageableFromHttpRequest(getRequest());
		try {
			
			PageImpl pageResult = custMarketingService.queCustMarketings(
					pageable, marketBatchParamBO);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<MarketBatch> list = new ArrayList<MarketBatch>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    
    }
    
    public HttpHeaders toQueMarketPush() throws Exception {
    	String taskId = getParameter("taskId");
    	
    	// 根据taskId获取MarketBatch对象
    	MarketBatch marketBatch = marketBatchService.getMarketBatch(Long.parseLong(taskId)); 
    	
    	setModel(marketBatch);
    	return buildDefaultHttpHeaders("index");
    }
}