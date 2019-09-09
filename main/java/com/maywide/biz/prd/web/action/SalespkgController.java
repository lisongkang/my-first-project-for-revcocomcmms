package com.maywide.biz.prd.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.prd.entity.Salespkg;
import com.maywide.biz.prd.service.SalespkgService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prd.entity.Salespkg管理")
public class SalespkgController extends BaseController<Salespkg,Long> {

    @Autowired
    private SalespkgService salespkgService;
    
    @Override
    protected BaseService<Salespkg, Long> getEntityService() {
        return salespkgService;
    }
    
    @Override
    protected void checkEntityAclPermission(Salespkg entity) {
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
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
    	GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(Salespkg.class, getRequest());
    	groupFilter.append(new PropertyFilter(MatchType.EQ, "status", SysConstant.ProdStatus.PUBLISH));
    	if (StringUtils.isNotBlank(getParameter("areaid"))) {
    		PropertyFilter propertyFilter = new PropertyFilter(MatchType.CN, "areas", getParameter("areaid"));
    		GroupPropertyFilter gpFilter = GroupPropertyFilter.buildDefaultOrGroupFilter(propertyFilter);
    		
    		propertyFilter = new PropertyFilter(MatchType.EQ, "areas", "*");
    		gpFilter.append(propertyFilter);
    		
    		groupFilter.append(gpFilter);
    	}
    	setModel(salespkgService.findByPage(groupFilter, pageable));
    	
    	return buildDefaultHttpHeaders();
    }
    
	public HttpHeaders findByPageForJump() {
		try {
			Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
			String codeOrNameField = getParameter("codeOrName");
			String city = getParameter("city");
			String areaid = getParameter("areaid");
			String orderField = getParameter("sidx");
			String sortType = getParameter("sord");
			String objtype = getParameter("objtype");
			setModel(salespkgService.findByPageForJump(codeOrNameField, objtype, city, areaid, pageable, orderField, sortType));
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
		return buildDefaultHttpHeaders();
	}
    
}