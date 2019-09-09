package com.maywide.biz.market.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.entity.Grid;
import com.maywide.biz.market.entity.GridManager;
import com.maywide.biz.market.entity.GridObj;
import com.maywide.biz.market.service.GridManagerService;
import com.maywide.biz.market.service.GridObjService;
import com.maywide.biz.market.service.GridService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.market.entity.Grid管理")
public class GridController extends BaseController<Grid,Long> {

    @Autowired
    private GridService gridService;
    
    @Autowired
    private GridManagerService gridManagerService;
    
    @Autowired
    private GridObjService gridObjService;

    @Override
    protected BaseService<Grid, Long> getEntityService() {
        return gridService;
    }
    
    @Override
    protected void checkEntityAclPermission(Grid entity) {
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
    public HttpHeaders view() {
    	try {
    		gridService.transGrid(bindingEntity);
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
    	
        return buildDefaultHttpHeaders("viewBasic");
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	try {
    		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
            	Page<Grid> page = this.getEntityService().findByPage(groupFilter, pageable);
            	gridService.transGridList(page.getContent());
                setModel(page);
            }
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
    	
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders manager() {
    	return buildDefaultHttpHeaders("manager");
    }
    
    public HttpHeaders patch() {
    	return buildDefaultHttpHeaders("patch");
    }
    
    public HttpHeaders relation() {
    	return buildDefaultHttpHeaders("relation");
    }
    
    public HttpHeaders queryManager() {
    	try {
    		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(GridManager.class, getRequest());
            PropertyFilter propertyFilter = new PropertyFilter(MatchType.EQ, "gridid", bindingEntity.getId());
            groupFilter.append(propertyFilter);
            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
            	Page<GridManager> page = gridManagerService.findByPage(groupFilter, pageable);
            	gridService.transGridManagerList(page.getContent());
            	setModel(page);
            }
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
    	
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders queryPatch() {
    	try {
    		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(GridObj.class, getRequest());
            PropertyFilter propertyFilter = new PropertyFilter(MatchType.EQ, "gridid", bindingEntity.getId());
            groupFilter.append(propertyFilter);
            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
            	Page<GridObj> page = gridObjService.findByPage(groupFilter, pageable);
            	gridService.transPatchList(page.getContent());
            	setModel(page);
            }
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
    	
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders findAllArea() {
    	try {
    		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        	if (loginInfo == null) {
        		throw new Exception("用户未登录或已过期");
        	}
    		PrvArea queryParam = new PrvArea();
    		queryParam.setCity(loginInfo.getCity());
    		
    		setModel(gridService.findAllArea(queryParam));
		} catch (Exception e) {
			throw new WebException(e.getMessage(), e);
		}
    	return buildDefaultHttpHeaders();
    }
}