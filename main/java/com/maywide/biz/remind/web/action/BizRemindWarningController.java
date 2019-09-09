package com.maywide.biz.remind.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.remind.entity.BizRemindWarning;
import com.maywide.biz.remind.pojo.RemindWarningParamBO;
import com.maywide.biz.remind.service.BizRemindWarningService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.remind.entity.BizRemindWarning管理")
public class BizRemindWarningController extends BaseController<BizRemindWarning,Long> {
	
    @Autowired
    private BizRemindWarningService bizRemindWarningService;

    @Override
    protected BaseService<BizRemindWarning, Long> getEntityService() {
        return bizRemindWarningService;
    }
    
    private RemindWarningParamBO remindWarningParamBO;
    
    public BizRemindWarningService getBizRemindWarningService() {
		return bizRemindWarningService;
	}

	public void setBizRemindWarningService(
			BizRemindWarningService bizRemindWarningService) {
		this.bizRemindWarningService = bizRemindWarningService;
	}

	public RemindWarningParamBO getRemindWarningParamBO() {
		return remindWarningParamBO;
	}

	public void setRemindWarningParamBO(RemindWarningParamBO remindWarningParamBO) {
		this.remindWarningParamBO = remindWarningParamBO;
	}

	@Override
    protected void checkEntityAclPermission(BizRemindWarning entity) {
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
    public HttpHeaders doSave()  {
    	
    	try {
    		LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
         	String city = loginInfo.getCity();
         	Long operid = loginInfo.getOperid();
         	Long deptid = loginInfo.getDeptid();
         	
         	if (this.bindingEntity != null) {
         		bindingEntity.setOpdate(new Date());
         		bindingEntity.setOperid(operid);
         		bindingEntity.setDeptid(deptid);
         		bindingEntity.setCity(city);
         		
         		bizRemindWarningService.checkRemindWarning(bindingEntity);
         	}
        	
            return super.doSave();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
    	try {
    		String[] ids = getParameterIds();
    		bizRemindWarningService.doDelete(ids);
    		
    		setModel(OperationResult.buildSuccessResult("删除预警任务成功", bindingEntity));
    		
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
				 remindWarningParamBO.setStartPeriod(statimeArry[0].trim());
				 remindWarningParamBO.setEndPeriod(statimeArry[1].trim());
			}

			PageImpl pageResult = bizRemindWarningService.queRemindWarnings(
					pageable, remindWarningParamBO);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<BizRemindWarning> list = new ArrayList<BizRemindWarning>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    
    }
    
    public HttpHeaders getDepts() throws Exception {
    	List<PrvDepartment> deptlist = bizRemindWarningService.getDepts();
    	setModel(deptlist);
        return buildDefaultHttpHeaders();
    }
}