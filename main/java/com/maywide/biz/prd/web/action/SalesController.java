package com.maywide.biz.prd.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.prd.entity.Sales;
import com.maywide.biz.prd.service.SalesService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

/**
 * <p>
 * 商品controller
 * <p>
 * Create at 2016年6月6日
 * 
 * @author pengjianqiang
 */
@MetaData("[com.maywide].biz.prd.entity.Sales管理")
public class SalesController extends BaseController<Sales, Long> {

    @Autowired
    private SalesService salesService;

    @Override
    protected BaseService<Sales, Long> getEntityService() {
        return salesService;
    }

    @Override
    protected void checkEntityAclPermission(Sales entity) {
        // TODO Add acl check code logic
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        // TODO
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
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String codeOrNameField = getParameter("codeOrName");
            String city = getParameter("city");
            String areaid = getParameter("areaid");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            String objtype = getParameter("objtype");
            setModel(salesService.findPageSalesByCity(codeOrNameField,objtype,city, areaid, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders toSalesPage() {
        String to = this.getRequiredParameter(PARAM_NAME_FOR_FORWARD_TO);
        String objtype = this.getParameter("objtype");
        getRequest().setAttribute("objtype", objtype);
        return buildDefaultHttpHeaders(to);
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
            setModel(salesService.findByPageForJump(codeOrNameField,objtype,city, areaid, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
}