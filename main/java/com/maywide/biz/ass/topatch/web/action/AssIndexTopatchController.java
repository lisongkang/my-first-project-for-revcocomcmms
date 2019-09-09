package com.maywide.biz.ass.topatch.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.ass.store.entity.AssIndexStore;
import com.maywide.biz.ass.store.service.AssIndexStoreService;
import com.maywide.biz.ass.topatch.entity.AssIndexPhasenum;
import com.maywide.biz.ass.topatch.entity.AssIndexTopatch;
import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.TmpIndexPhasenum;
import com.maywide.biz.ass.topatch.entity.TmpIndexPhasenumList;
import com.maywide.biz.ass.topatch.service.AssIndexTopatchService;
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

@MetaData("下达片区管理")
public class AssIndexTopatchController extends BaseController<AssIndexTopatch,Long> {

	@Autowired
	private AssIndexStoreService assIndexStoreService;
	
    @Autowired
    private AssIndexTopatchService assIndexTopatchService;

    @Override
    protected BaseService<AssIndexTopatch, Long> getEntityService() {
        return assIndexTopatchService;
    }
    
    @Override
    protected void checkEntityAclPermission(AssIndexTopatch entity) {
        // TODO Add acl check code logic
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
    		// 保存assindexstore对象
    		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        	
			// json串转换AssIndexTogrid对象
        	String assIndexTopatchJson = getParameter("assIndexTopatch");
			AssIndexTopatch assIndexTopatch = (AssIndexTopatch) BeanUtil
					.jsonToObject(assIndexTopatchJson, AssIndexTopatch.class);
			
			assIndexTopatch.setBdate(bindingEntity.getBdate());
			assIndexTopatch.setOperator(loginInfo.getOperid());
			assIndexTopatch.setDepart(loginInfo.getDeptid());
			assIndexTopatch.setAssdate(new Date());
			
			assIndexTopatchService.saveAssIndexTopatchs(assIndexTopatch);
			
    	} catch(Exception e) {
    		throw new ServiceException(e.getMessage());
    	}
    	
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
            List<String> patchIds = assIndexTopatchService.parseToPatchBatTxt(tgridid, assid, attachmentId, departId,
                    operatorId);
            if (patchIds.size() == 0) {
                throw new Exception("上传文件格式有误、重复下达片区或没有对应片区号等，请检查文件");
            }

            // json串转换AssIndexTogrid对象
            String assIndexTopatchJson = getParameter("assIndexTopatch");
            AssIndexTopatch assIndexTopatch = (AssIndexTopatch) BeanUtil.jsonToObject(assIndexTopatchJson,
                    AssIndexTopatch.class);

            assIndexTopatch.setBdate(DateTimeUtil.parseDate(getParameter("bdate")));
            assIndexTopatch.setOperator(loginInfo.getOperid());
            assIndexTopatch.setDepart(loginInfo.getDeptid());
            assIndexTopatch.setAssdate(new Date());
            assIndexTopatch.setPatchids(patchIds);
            if ("9".equals(getParameter("mode"))) {
                assIndexTopatch.setAssnum(0D);
            }

            assIndexTopatchService.saveAssIndexTopatchs(assIndexTopatch);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        setModel(OperationResult.buildSuccessResult("批量下达成功！")); //必须setModel，否则请求会返回null，导致页面操作不了
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
    	try {
    		String[] ids = getParameterIds();
    		// 删除对应的ass_index_phasenum
    		assIndexTopatchService.deletePatch(ids);
		} catch (Exception e) {
			return buildDefaultHttpHeaders();
		}
        return super.doDelete();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try {
			 Long assid = StringUtils.isEmpty(getParameter("assid"))? null : Long.parseLong(getParameter("assid"));
			 
			 PageImpl pageResult = assIndexTopatchService.queTopatchTaskList(assid, pageable);
		     setModel(pageResult);
		     
			 return buildDefaultHttpHeaders("index");
			
		} catch (Exception e) {
			List<AssIndexTopatch> list = new ArrayList<AssIndexTopatch>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("index");
    }
    
    public HttpHeaders toAssnumPagelist() throws Exception {
    	try {
    		 String taskid = getParameter("taskid");
        	 String storeId = getParameter("storeId");
        	 AssIndexStore store = assIndexStoreService.getAssIndexStore(Long.valueOf(storeId));
        	
			 List<AssIndexPhasenum> list = assIndexTopatchService.quePhasenumList(store, Long.valueOf(taskid));
		     setModel(list);
		     
			 return buildDefaultHttpHeaders("assnumlist");
			
		} catch (Exception e) {
			List<AssIndexPhasenum> emptylist = new ArrayList<AssIndexPhasenum>();
			setModel(emptylist);
		}
		return buildDefaultHttpHeaders("assnumlist");
    }
    
    public HttpHeaders viewTopatchTabs() throws Exception {
        return forwardWithAssIndexStore("viewTabs");
    }
    
    public HttpHeaders taskTopatchList() throws Exception {
        return forwardWithAssIndexStore("index");
    }
    public HttpHeaders topatchTask() throws Exception {
        return forwardWithAssIndexStore("inputBasic");
    }
    
    public HttpHeaders toAssnumPage() throws Exception {
    	
    	try {
   		     String serialno = getParameter("serialno");
       	     String storeId = getParameter("storeId");
       	     AssIndexStore store = assIndexStoreService.getAssIndexStore(Long.valueOf(storeId));
       	     List<TmpIndexPhasenum> phaselist = new ArrayList<TmpIndexPhasenum>(); 
       	     
       	     if (StringUtils.isNotEmpty(serialno)) {
       	    	 phaselist = assIndexTopatchService.queTmpPhasenumList(store, serialno);
       	     } 
			 
			 TmpIndexPhasenumList tmphase = new TmpIndexPhasenumList(store.getUnit(), phaselist);
			 tmphase.setSerialno(serialno);
			 setModel(tmphase);
		     
			 return buildDefaultHttpHeaders("assnum");
			
		} catch (Exception e) {
			List<TmpIndexPhasenum> emptylist = new ArrayList<TmpIndexPhasenum>();
			setModel(emptylist);
		}
		return buildDefaultHttpHeaders("assnum");
    }
    
    public HttpHeaders toStoreInfo() throws Exception {
        return forwardWithAssIndexStore("viewBasic");
    }
    
    
    private HttpHeaders forwardWithAssIndexStore(String forward) throws Exception {
    	String storeId = getParameter("storeId");
//    	String grids = getParameter("grids");
    	AssIndexStore store = assIndexStoreService.getAssIndexStore(Long.valueOf(storeId));
    	assIndexStoreService.obtainPatch(store);
    	setModel(store);
        return buildDefaultHttpHeaders(forward);
    }
    
    public HttpHeaders getGrids() throws Exception {
    	setModel(assIndexTopatchService.getGrids());
    	
    	return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders getPatchsByGridid() throws Exception {
    	String sGridid = getParameter("gridid");
    	if (StringUtils.isNotEmpty(sGridid)) {
    		Long gridid = Long.parseLong(sGridid);
        	setModel(assIndexTopatchService.getPatchsByGridid(gridid));
    	} else {
    		List<BizGridInfo> emptylist = new ArrayList<BizGridInfo>();
    		setModel(emptylist);
    	}
    	
    	return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders saveTmpAssnum() throws Exception {
    	String assnumString = getParameter("assnumString");
    	String serialno = getParameter("serialno");
    	setModel(assIndexTopatchService.saveTmpAssnum(serialno, assnumString));
    	
    	return buildDefaultHttpHeaders();
    }
}