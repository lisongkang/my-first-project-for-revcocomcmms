package com.maywide.biz.market.web.action;

import java.util.List;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.entity.ResPatch;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.biz.market.service.ResPatchService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.market.entity.ResPatch管理")
public class ResPatchController extends BaseController<ResPatch,Long> {

    @Autowired
    private ResPatchService resPatchService;
    
    @Autowired
    private GridInfoService gridInfoService;

    @Override
    protected BaseService<ResPatch, Long> getEntityService() {
        return resPatchService;
    }
    
    @Override
    protected void checkEntityAclPermission(ResPatch entity) {
        // TODO Add acl check code logic
    }
    
    @Override
    protected void appendFilterProperty(GroupPropertyFilter groupPropertyFilter) {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	if (loginInfo == null) {
    		throw new WebException("用户未登录或已过期");
    	}
        if (groupPropertyFilter.isEmpty()) {
        	groupPropertyFilter.append(new PropertyFilter(MatchType.EQ, "areaid", loginInfo.getAreaid()));
        }
        super.appendFilterProperty(groupPropertyFilter);
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
        return super.findByPage();
    }
    
    public HttpHeaders findAllPatchs() {
    	setModel(resPatchService.findAllPatchs());
        return buildDefaultHttpHeaders();
    }

    @MetaData("根据管理网格id查询未关联小区")
    public HttpHeaders findUnbindPatchByGridId() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String queryParam = getParameter("search['CN_patchname_OR_defcode']");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");

            Long gridid = Long.parseLong(getParameter("gridid"));
            BizGridInfo gridInfo = gridInfoService.findOne(gridid);

            // 根据gridid查询出其管理网格所属的小区网格
            List<BizGridInfo> bindedPatchList = gridInfoService.findBindedPatchByPreGridid(gridid);
            setModel(resPatchService.findUnbindPatchByGridId(queryParam, pageable, orderField, sortType, gridInfo,
                    bindedPatchList));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
}