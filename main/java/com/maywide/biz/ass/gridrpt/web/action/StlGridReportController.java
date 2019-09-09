package com.maywide.biz.ass.gridrpt.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.gridrpt.entity.StlGridReport;
import com.maywide.biz.ass.gridrpt.service.StlGridReportService;
import com.maywide.biz.ass.monstat.entity.AssIndexMonprogress;
import com.maywide.biz.ass.monstat.pojo.MonprogressParamBO;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;
import com.maywide.core.annotation.MetaData;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("StlGridReport管理")
public class StlGridReportController extends BaseController<StlGridReport,Long> {

    @Autowired
    private StlGridReportService stlGridReportService;
    
    private MonprogressParamBO monprogressParamBO;
    
    public MonprogressParamBO getMonprogressParamBO() {
		return monprogressParamBO;
	}

	public void setMonprogressParamBO(MonprogressParamBO monprogressParamBO) {
		this.monprogressParamBO = monprogressParamBO;
	}

	@Override
    protected BaseService<StlGridReport, Long> getEntityService() {
        return stlGridReportService;
    }
    
    @Override
    protected void checkEntityAclPermission(StlGridReport entity) {
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
			String startMonth = StringUtils
					.isEmpty(getParameter("_startMonth")) ? null
					: getParameter("_startMonth");
			String endMonth = StringUtils.isEmpty(getParameter("_endMonth")) ? null
					: getParameter("_endMonth");

			PageImpl pageResult = stlGridReportService.queGridReportList(
					pageable, monprogressParamBO, startMonth, endMonth);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<StlGridReport> list = new ArrayList<StlGridReport>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();

    }
    /**
     * 查询报表详细
     * @return
     */
    public HttpHeaders doDetail() {
    	// 将ID放入Session缓存
		String rptId = getParameter("id");
		getRequest().getSession().setAttribute("rptId", rptId);
		
		return buildDefaultHttpHeaders("detail");
    }
    
    /**
     * 查询报表详细列表
     * @return
     */
    public HttpHeaders findByPageForDetail() {
		Pageable pageable = PropertyFilter
				.buildPageableFromHttpRequest(getRequest());
		try {
			String rptId = (String)getRequest().getSession().getAttribute("rptId");
			
			PageImpl pageResult = stlGridReportService.doDetail(pageable, rptId);

			setModel(pageResult);
			return buildDefaultHttpHeaders("detail");

		} catch (Exception e) {
			List<AssIndexMonprogress> list = new ArrayList<AssIndexMonprogress>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("detail");
    }
    
    /**
     * 获取所属网格
     * @return
     */
    public Map<String, String> getFirstGrididMap() {
        Map<String, String> firstGrididMap = null;
        try {
        	firstGrididMap = stlGridReportService.getFirstGrididMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return firstGrididMap;
    }
}