package com.maywide.biz.remind.web.action;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.remind.entity.BizRemindBatch;
import com.maywide.biz.remind.entity.BizRemindWarning;
import com.maywide.biz.remind.pojo.RemindBatchParamBO;
import com.maywide.biz.remind.service.BizRemindBatchService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.remind.entity.BizRemindBatch管理")
public class BizRemindBatchController extends BaseController<BizRemindBatch,Long> {

    @Autowired
    private BizRemindBatchService bizRemindBatchService;

    public RemindBatchParamBO remindBatchParamBO;
    
    public RemindBatchParamBO getRemindBatchParamBO() {
		return remindBatchParamBO;
	}

	public void setRemindBatchParamBO(RemindBatchParamBO remindBatchParamBO) {
		this.remindBatchParamBO = remindBatchParamBO;
	}

	@Override
    protected BaseService<BizRemindBatch, Long> getEntityService() {
        return bizRemindBatchService;
    }
    
    @Override
    protected void checkEntityAclPermission(BizRemindBatch entity) {
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
			
			String statime = getParameter("que_apptime");
			if (StringUtils.isNotEmpty(statime)) {
				 String[] statimeArry = statime.split("～");
				 remindBatchParamBO.setStartPeriod(statimeArry[0].trim());
				 remindBatchParamBO.setEndPeriod(statimeArry[1].trim());
			}

			PageImpl pageResult = bizRemindBatchService.queRemindBatchs(
					pageable, remindBatchParamBO);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<BizRemindWarning> list = new ArrayList<BizRemindWarning>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    
    }
    
    public HttpHeaders confirmRemindBatch() throws Exception {
    	String ids = getParameter("ids");
    	bizRemindBatchService.confirmRemindBatch(ids);
    	
    	setModel(OperationResult.buildSuccessResult("确认预警任务成功", bindingEntity));
		
    	return buildDefaultHttpHeaders();
    }
}