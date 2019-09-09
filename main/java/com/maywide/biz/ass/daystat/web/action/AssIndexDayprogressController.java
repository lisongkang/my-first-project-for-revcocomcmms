package com.maywide.biz.ass.daystat.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.daystat.entity.AssIndexDayprogress;
import com.maywide.biz.ass.daystat.service.AssIndexDayprogressService;
import com.maywide.biz.ass.monstat.pojo.MonprogressParamBO;
import com.maywide.biz.ass.monstat.service.AssIndexMonprogressService;
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

@MetaData("[com.maywide].biz.ass.daystat.entity.AssIndexDayprogress管理")
public class AssIndexDayprogressController extends BaseController<AssIndexDayprogress,Long> {

    @Autowired
    private AssIndexDayprogressService assIndexDayprogressService;
    
    @Autowired
    private AssIndexMonprogressService assIndexMonprogressService;
    
    private MonprogressParamBO monprogressParamBO;
    
    public MonprogressParamBO getMonprogressParamBO() {
		return monprogressParamBO;
	}

	public void setMonprogressParamBO(MonprogressParamBO monprogressParamBO) {
		this.monprogressParamBO = monprogressParamBO;
	}

    @Override
    protected BaseService<AssIndexDayprogress, Long> getEntityService() {
        return assIndexDayprogressService;
    }
    
    @Override
    protected void checkEntityAclPermission(AssIndexDayprogress entity) {
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
    
    @MetaData("查询")
    public HttpHeaders sumFindByPage() {

		Pageable pageable = PropertyFilter
				.buildPageableFromHttpRequest(getRequest());
		try {
			String statime = getParameter("que_statime");
			if (StringUtils.isNotEmpty(statime)) {
				 String[] statimeArry = statime.split("～");
				 monprogressParamBO.setStartPeriod(statimeArry[0].trim());
				 monprogressParamBO.setEndPeriod(statimeArry[1].trim());
			}
		   
			PageImpl pageResult = assIndexDayprogressService.queSumDayprogress(
					pageable, monprogressParamBO);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<AssIndexDayprogress> list = new ArrayList<AssIndexDayprogress>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {

		Pageable pageable = PropertyFilter
				.buildPageableFromHttpRequest(getRequest());
		try {
			String statime = getParameter("que_statime");
			if (StringUtils.isNotEmpty(statime)) {
				 String[] statimeArry = statime.split("～");
				 monprogressParamBO.setStartPeriod(statimeArry[0].trim());
				 monprogressParamBO.setEndPeriod(statimeArry[1].trim());
			}
		   
			PageImpl pageResult = assIndexDayprogressService.queDayprogress(
					pageable, monprogressParamBO);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<AssIndexDayprogress> list = new ArrayList<AssIndexDayprogress>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
    }
    
    public Map<String, String> getFirstGrididMap() {
        Map<String, String> firstGrididMap = null;
        try {
        	firstGrididMap = assIndexMonprogressService.getFirstGrididMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return firstGrididMap;
    }
    
    public Map<String, String> getAssMap() {
        Map<String, String> assMap = null;
        try {
        	assMap = assIndexMonprogressService.getAssMap();
        } catch (Exception e) {
            
        }
        return assMap;
    }
}