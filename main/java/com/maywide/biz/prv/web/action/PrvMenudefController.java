package com.maywide.biz.prv.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.prv.entity.PrvMenudef;
import com.maywide.biz.prv.service.PrvMenuService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.service.BaseService;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prv.entity.PrvMenudef管理")
public class PrvMenudefController extends BaseController<PrvMenudef,Long> {
	public static final Long ROOT_MENU_ID = 99L;

    @Autowired
    private PrvMenuService prvMenudefService;

    @Override
    protected BaseService<PrvMenudef, Long> getEntityService() {
        return prvMenudefService;
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
    	if (bindingEntity.getPremenuid() == null) {
    		bindingEntity.setPremenuid(ROOT_MENU_ID);
    	}
    	if (bindingEntity.getAttr() == null) {
    		bindingEntity.setAttr("Y");
    	}
    	bindingEntity.setPinyin(PinYinUtils.converterToFirstSpell(bindingEntity.getName()));
    	return super.doSave();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }
    
    public HttpHeaders mobileMenus(){
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
    	String name = getParameter("name");
    	try {
			setModel(prvMenudefService.mobileMenus(pageable,name));
		} catch (Exception e) {
			 throw new WebException(e.getMessage(), e);
		}
    	return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders menuForJump(){
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
    	String name = getParameter("name");
    	try {
			setModel(prvMenudefService.menuForJump(pageable,name));
		} catch (Exception e) {
			 throw new WebException(e.getMessage(), e);
		}
    	return buildDefaultHttpHeaders();
    }
    
    @Override
    protected void appendFilterProperty(GroupPropertyFilter groupPropertyFilter) {
        if (groupPropertyFilter.isEmpty()) {
        	groupPropertyFilter.append(new PropertyFilter(MatchType.EQ, "premenuid", ROOT_MENU_ID));
        }
        super.appendFilterProperty(groupPropertyFilter);
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	return super.findByPage();
    }
}