package com.maywide.biz.market.web.action;

import java.util.Date;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.market.service.GridInfoManagerService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.ass.topatch.entity.BizGridManager管理")
public class GridInfoManagerController extends BaseController<BizGridManager, Long> {

    @Autowired
    private GridInfoManagerService gridInfoManagerService;

    @Override
    protected BaseService<BizGridManager, Long> getEntityService() {
        return gridInfoManagerService;
    }

    @Override
    protected void checkEntityAclPermission(BizGridManager entity) {
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
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
        try {
            gridInfoManagerService.checkEntity(bindingEntity);
            String memo = bindingEntity.getMemo();
            if (null == memo || "".equals(memo)) {
                bindingEntity.setMemo(null);
            }
            bindingEntity.setOperid(bindingEntity.getAreamger());
            bindingEntity.setUpdatetime(new Date());
            return super.doSave();
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
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

    @MetaData("根据网格id查询网格经理")
    public HttpHeaders queryManagerByGridid() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
            groupFilter.append(new PropertyFilter(MatchType.EQ, "gridid", Long.parseLong(getParameter("gridid"))));
            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
                Page<BizGridManager> page = gridInfoManagerService.findByPage(groupFilter, pageable);
                gridInfoManagerService.transGridManagerList(page.getContent());
                setModel(page);
            }
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }

        return buildDefaultHttpHeaders();
    }
}