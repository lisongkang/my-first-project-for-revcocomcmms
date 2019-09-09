package com.maywide.biz.ass.monstat.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.monstat.entity.AssIndexMonprogress;
import com.maywide.biz.ass.monstat.pojo.MonprogressParamBO;
import com.maywide.biz.ass.monstat.service.AssIndexMonprogressService;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.market.pojo.GridTreeInfo;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.view.OperationResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.ass.monstat.entity.AssIndexMonprogress管理")
public class AssIndexMonprogressController extends BaseController<AssIndexMonprogress,Long> {

    @Autowired
    private AssIndexMonprogressService assIndexMonprogressService;
    
    private MonprogressParamBO monprogressParamBO;

    @Override
    protected BaseService<AssIndexMonprogress, Long> getEntityService() {
        return assIndexMonprogressService;
    }
    
    public MonprogressParamBO getMonprogressParamBO() {
		return monprogressParamBO;
	}

	public void setMonprogressParamBO(MonprogressParamBO monprogressParamBO) {
		this.monprogressParamBO = monprogressParamBO;
	}

	@Override
    protected void checkEntityAclPermission(AssIndexMonprogress entity) {
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
			String startMonth = StringUtils
					.isEmpty(getParameter("_startMonth")) ? null
					: getParameter("_startMonth");
			String endMonth = StringUtils.isEmpty(getParameter("_endMonth")) ? null
					: getParameter("_endMonth");

			PageImpl pageResult = assIndexMonprogressService.queSumMonprogress(
					pageable, monprogressParamBO, startMonth, endMonth);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<AssIndexMonprogress> list = new ArrayList<AssIndexMonprogress>();
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
			String startMonth = StringUtils
					.isEmpty(getParameter("_startMonth")) ? null
					: getParameter("_startMonth");
			String endMonth = StringUtils.isEmpty(getParameter("_endMonth")) ? null
					: getParameter("_endMonth");

			PageImpl pageResult = assIndexMonprogressService.queMonprogress(
					pageable, monprogressParamBO, startMonth, endMonth);

			setModel(pageResult);
			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<AssIndexMonprogress> list = new ArrayList<AssIndexMonprogress>();
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
    
    public HttpHeaders getSecondGrididByfirstGridid() throws Exception {
    	String firstGridid = getParameter("firstGridid");
    	List<BizGridInfo> gridlist = assIndexMonprogressService.getSecondGrididByfirstGridid(firstGridid);
    	setModel(gridlist);
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders getThirdPatchidBySecondGridid() throws Exception {
    	
    	// json串转换AssIndexTogrid对象
    	String assIndexMonprogressJson = getParameter("assIndexMonprogress");
    	AssIndexMonprogress assIndexMonprogress = (AssIndexMonprogress) BeanUtil
				.jsonToObject(assIndexMonprogressJson, AssIndexMonprogress.class);
    	List<String> grids = assIndexMonprogress.getSecondGridids();
    	
    	List<BizGridInfo> patchidList = assIndexMonprogressService.getThirdPatchidBySecondGridid(grids);
    	setModel(patchidList);
        return buildDefaultHttpHeaders();
    }
    
    public Map<String, String> getAssMap() {
        Map<String, String> assMap = null;
        try {
        	assMap = assIndexMonprogressService.getAssMap();
        } catch (Exception e) {
            
        }
        return assMap;
    }
    
    @MetaData("考核模块显示网格树")
    public HttpHeaders gridTreeForManagerGrid() {
        try {
            List<GridTreeInfo> menuList = assIndexMonprogressService.findGridTreeForAss();
            setModel(OperationResult.buildSuccessResult("查询网格树成功", menuList));
        } catch (Exception e) {
            setModel(OperationResult.buildFailureResult("查询网格树失败：" + e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("营销模块显示网格树")
    public HttpHeaders gridTreeForMarket() {
        try {
            List<GridTreeInfo> menuList = assIndexMonprogressService.findGridTreeForMarket();
            setModel(OperationResult.buildSuccessResult("查询网格树成功", menuList));
        } catch (Exception e) {
            setModel(OperationResult.buildFailureResult("查询网格树失败：" + e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }
}