package com.maywide.biz.prv.web.action;

import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.prv.entity.PrvOperMenuCtrl;
import com.maywide.biz.prv.service.PrvOperMenuCtrlService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Controller
public class PrvOperMenuCtrlController extends BaseController<PrvOperMenuCtrl, Long> {

	@Autowired
	private PrvOperMenuCtrlService prvOperMenuCtrlService;
	
	@Autowired
	private ParamService paramService;
	
	@Override
	protected BaseService<PrvOperMenuCtrl, Long> getEntityService() {
		return prvOperMenuCtrlService;
	}

	@Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
	    Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
        appendFilterProperty(groupFilter);
        String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
        if ("xls".equalsIgnoreCase(foramt)) {
            exportXlsForGrid(groupFilter, pageable.getSort());
        } else {
            //Page l = this.getEntityService().findByPage(groupFilter, pageable);
            setModel(this.getEntityService().findByPage(groupFilter, pageable));
        }
        return buildDefaultHttpHeaders();
    }

	@Override
	public HttpHeaders doSave() {
		// TODO Auto-generated method stub
		return super.doSave();
	}

	@Override
	public HttpHeaders doDelete() {
		// TODO Auto-generated method stub
		return super.doDelete();
	}
	
	 public Map<String, String> getValuesrcMap() throws Exception {
	        return paramService.getSelectData("MENU_CONTROLCODE");
	    }
	
	

}
