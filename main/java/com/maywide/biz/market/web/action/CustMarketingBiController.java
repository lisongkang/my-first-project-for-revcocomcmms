package com.maywide.biz.market.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.entity.CustMarketingBi;
import com.maywide.biz.market.service.CustMarketingBiService;
import com.maywide.biz.prd.entity.Salespkg;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.market.entity.CustMarketingBi管理")
public class CustMarketingBiController extends BaseController<CustMarketingBi,Long> {

    @Autowired
    private CustMarketingBiService custMarketingBiService;

    @Override
    protected BaseService<CustMarketingBi, Long> getEntityService() {
        return custMarketingBiService;
    }
    
    @Override
    protected void checkEntityAclPermission(CustMarketingBi entity) {
        // TODO Add acl check code logic
    }

    public HttpHeaders push() {
    	String ids = getParameter("ids");
    	getRequest().setAttribute("ids", ids);
        return buildDefaultHttpHeaders("push");
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
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	if (loginInfo == null) {
    		throw new WebException("用户未登录或已过期");
    	}
    	
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
    	GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(Salespkg.class, getRequest());
    	groupFilter.append(new PropertyFilter(MatchType.EQ, "dealstatus", SysConstant.ProdStatus.SETTING));
    	groupFilter.append(new PropertyFilter(MatchType.EQ, "city", loginInfo.getCity()));
    	
    	if (StringUtils.isNotBlank(getParameter("name"))) {
    		groupFilter.append(new PropertyFilter(MatchType.CN, "name", getParameter("name")));
    	}
    	if (StringUtils.isNotBlank(getParameter("pname"))) {
    		groupFilter.append(new PropertyFilter(MatchType.CN, "pname", getParameter("pname")));
    	}
    	if (StringUtils.isNotBlank(getParameter("whladdr"))) {
    		groupFilter.append(new PropertyFilter(MatchType.CN, "whladdr", getParameter("whladdr")));
    	}
    	if (StringUtils.isNotBlank(getParameter("areaid"))) {
    		groupFilter.append(new PropertyFilter(MatchType.EQ, "area.id", getParameter("areaid")));
    	}
    	setModel(custMarketingBiService.findByPage(groupFilter, pageable));
    	
    	return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders doPush() {
    	try {
    		String ids = getParameter("ids");
    		String pri = getParameter("pri");
    		custMarketingBiService.doPush(ids, pri);
		} catch (Exception e) {
			setModel(OperationResult.buildFailureResult("信息推送失败，" + e.getMessage()));
			return buildDefaultHttpHeaders();
		}
		setModel(OperationResult.buildSuccessResult("信息推送成功"));
        return buildDefaultHttpHeaders();
    }
}