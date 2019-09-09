package com.maywide.biz.remind.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.maywide.biz.market.service.MarketBatchService;
import com.maywide.biz.remind.entity.BizRemindBatch;
import com.maywide.biz.remind.entity.BizRemindRecord;
import com.maywide.biz.remind.entity.BizRemindWarning;
import com.maywide.biz.remind.pojo.RemindRecordParamBO;
import com.maywide.biz.remind.service.BizRemindRecordService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.remind.entity.BizRemindRecord管理")
public class BizRemindRecordController extends BaseController<BizRemindRecord,Long> {

    @Autowired
    private BizRemindRecordService bizRemindRecordService;
    
    @Autowired
    private MarketBatchService marketBatchService;
    
    private RemindRecordParamBO remindRecordParamBO;

    public RemindRecordParamBO getRemindRecordParamBO() {
		return remindRecordParamBO;
	}

	public void setRemindRecordParamBO(RemindRecordParamBO remindRecordParamBO) {
		this.remindRecordParamBO = remindRecordParamBO;
	}

	@Override
    protected BaseService<BizRemindRecord, Long> getEntityService() {
        return bizRemindRecordService;
    }
    
    @Override
    protected void checkEntityAclPermission(BizRemindRecord entity) {
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
			
			PageImpl pageResult = bizRemindRecordService.queRemindRecords(
					pageable, remindRecordParamBO);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<BizRemindWarning> list = new ArrayList<BizRemindWarning>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders toRecord() throws Exception {
    	String batid = getParameter("batid");
    	
    	BizRemindBatch bizRemindBatch = new BizRemindBatch();
    	bizRemindBatch.setId(Long.parseLong(batid));
    	
    	setModel(bizRemindBatch);
    	
    	return buildDefaultHttpHeaders("index");
    	
    }
    
    public Map<String, String> getMarketBatchMap() {
        Map<String, String> map = null;
        try {
        	map = marketBatchService.getMarketBatchMap(false);
        } catch (Exception e) {
        }
        return map;
    }
}