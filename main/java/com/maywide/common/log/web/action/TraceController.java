package com.maywide.common.log.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.core.entity.AccessLog;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.market.entity.Trace;
import com.maywide.common.log.service.TraceService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@MetaData("[com.maywide].biz.market.entity.Trace管理")
public class TraceController extends BaseController<Trace,Long> {

    @Autowired
    private TraceService traceService;
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseService<Trace, Long> getEntityService() {
        return traceService;
    }
    
    @Override
    protected void checkEntityAclPermission(Trace entity) {
        // TODO Add acl check code logic
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
    
    @MetaData("业务信息")
    public HttpHeaders view() {
    	return super.view();
    }
    
    public HttpHeaders orderview() {
    	try {
    		Long orderid = new Long("1014033");//getRequiredParameter("orderid"));
    		CustOrder order = (CustOrder) persistentService.find(CustOrder.class, orderid);
    		getRequest().setAttribute("order", order);
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
    	
    	return buildDefaultHttpHeaders("orderview");
    }
    
    public HttpHeaders accessview() {
    	try {
    		Long logid = new Long(getRequiredParameter("accesslogid"));
    		AccessLog log = (AccessLog) persistentService.find(AccessLog.class, logid);
    		getRequest().setAttribute("log", log);
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
    	
    	return buildDefaultHttpHeaders("accessview");
    }
}