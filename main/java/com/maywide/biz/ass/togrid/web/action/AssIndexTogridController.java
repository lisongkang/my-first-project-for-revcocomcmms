package com.maywide.biz.ass.togrid.web.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maywide.biz.ass.store.entity.AssIndexStore;
import com.maywide.biz.ass.store.service.AssIndexStoreService;
import com.maywide.biz.ass.togrid.entity.AssIndexTogrid;
import com.maywide.biz.ass.togrid.service.AssIndexTogridService;
import com.maywide.biz.ass.topatch.entity.AssIndexTopatch;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.DateTimeUtil;
import com.maywide.core.web.view.OperationResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.ass.togrid.entity.AssIndexTogrid管理")
public class AssIndexTogridController extends BaseController<AssIndexTogrid,Long> {

	@Autowired
	private AssIndexStoreService assIndexStoreService;
	
    @Autowired
    private AssIndexTogridService assIndexTogridService;
    
    @Override
    protected BaseService<AssIndexTogrid, Long> getEntityService() {
        return assIndexTogridService;
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
    		// 保存assindexstore对象
    		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        	
			// json串转换AssIndexTogrid对象
        	String assIndexTogridJson = getParameter("assIndexTogrid");
			AssIndexTogrid assIndexTogrid = (AssIndexTogrid) BeanUtil
					.jsonToObject(assIndexTogridJson, AssIndexTogrid.class);
			
			assIndexTogrid.setBdate(bindingEntity.getBdate());
			assIndexTogrid.setOperator(loginInfo.getOperid());
			assIndexTogrid.setDepart(loginInfo.getDeptid());
			assIndexTogrid.setAssdate(new Date());
			
			assIndexTogridService.saveAssIndexTogrids(assIndexTogrid);
			
    	} catch(Exception e) {
    		throw new ServiceException(e.getMessage());
    	}
    	
        return buildDefaultHttpHeaders();
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
		try {
			 Long assid = StringUtils.isEmpty(getParameter("assid"))? null : Long.parseLong(getParameter("assid"));
			 
			 PageImpl pageResult = assIndexTogridService.queTogridTaskList(assid, pageable);
		     setModel(pageResult);
		     
			 return buildDefaultHttpHeaders("index");
			
		} catch (Exception e) {
			List<AssIndexTogrid> list = new ArrayList<AssIndexTogrid>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("index");
    }
    
    
    public HttpHeaders viewTogridTabs() throws Exception {
        return forwardWithAssIndexStore("viewTabs");
    }
    
    public HttpHeaders taskTogridList() throws Exception {
        return forwardWithAssIndexStore("index");
    }
    public HttpHeaders togridTask() throws Exception {
        return forwardWithAssIndexStore("inputBasic");
    }
    
    private HttpHeaders forwardWithAssIndexStore(String forward) throws Exception {
    	String storeId = getParameter("storeId");
    	String grids = getParameter("grids");
    	AssIndexStore store = assIndexStoreService.getAssIndexStore(Long.valueOf(storeId));
    	
    	if (!StringUtils.isEmpty(grids)){
    		store.setGrids(grids);
    	}
    	setModel(store);
        return buildDefaultHttpHeaders(forward);
    }
    
    public HttpHeaders getGrids() throws Exception {
    	setModel(assIndexTogridService.getGrids());
    	
    	return buildDefaultHttpHeaders();
    }
    
    @MetaData("批量保存")
    public HttpHeaders doSaveBat() {
        try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            Long departId = loginInfo.getDeptid();
            Long operatorId = loginInfo.getOperid();

            Long tgridid = Long.parseLong(getParameter("tgridid"));
            Long assid = Long.parseLong(getParameter("assid"));
            String attachmentId = getParameter("attachmentId");
            List<String> gridIds = assIndexTogridService.parseToGridBatTxt(tgridid, assid, attachmentId, departId,
                    operatorId);
            if (gridIds.size() == 0) {
                throw new Exception("上传文件格式有误、重复下达网格或没有对应网格编号等，请检查文件");
            }

            // json串转换AssIndexTogrid对象
            String assIndexTogridJson = getParameter("assIndexTogrid");
            AssIndexTogrid assIndexTogrid = (AssIndexTogrid) BeanUtil.jsonToObject(assIndexTogridJson,
                    AssIndexTogrid.class);

            assIndexTogrid.setBdate(DateTimeUtil.parseDate(getParameter("bdate")));
            assIndexTogrid.setOperator(loginInfo.getOperid());
            assIndexTogrid.setDepart(loginInfo.getDeptid());
            assIndexTogrid.setAssdate(new Date());
            assIndexTogrid.setGridids(gridIds);
            if ("9".equals(getParameter("mode"))) {
            	assIndexTogrid.setAssnum(0D);
            }

            assIndexTogridService.saveAssIndexTogrids(assIndexTogrid);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        setModel(OperationResult.buildSuccessResult("批量下达成功！")); //必须setModel，否则请求会返回null，导致页面操作不了
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("查询关联网格下的所有网格")
    public HttpHeaders findGrids() throws Exception{
    	setModel(assIndexTogridService.findGridsAndSubGrids());
    	
    	return buildDefaultHttpHeaders();
    }
}