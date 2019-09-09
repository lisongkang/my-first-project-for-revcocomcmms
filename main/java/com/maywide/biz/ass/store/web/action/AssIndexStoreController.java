package com.maywide.biz.ass.store.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maywide.biz.ass.store.entity.AssIndexObject;
import com.maywide.biz.ass.store.entity.AssIndexStore;
import com.maywide.biz.ass.store.service.AssIndexStoreService;
import com.maywide.biz.ass.topatch.entity.AssIndexTopatch;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.web.view.OperationResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.ass.store.entity.AssIndexStore管理")
public class AssIndexStoreController extends BaseController<AssIndexStore,Long> {

    @Autowired
    private AssIndexStoreService assIndexStoreService;
    
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseService<AssIndexStore, Long> getEntityService() {
        return assIndexStoreService;
    }
    
    @Override
    protected void checkEntityAclPermission(AssIndexStore entity) {
        // TODO Add acl check code logic
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        //TODO
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders selectOrderPrds() throws Exception {
    	String objtype = "0";
		String assparam = getParameter("assparam");
		if("SALESPKG".equals(assparam)){
			objtype = "1";
		} else if ("PRODUCT".equals(assparam)) {
			objtype = "0";
		}
		
		setModel(assIndexStoreService.querySalespkgKnow(objtype));

		return buildDefaultHttpHeaders("orderPrds");
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
    
    private void checkDelete(String[] ids) throws Exception{
    	for (int i = 0; i < ids.length; i++) {
			AssIndexTopatch assIndexTopatch = new AssIndexTopatch();
			assIndexTopatch.setAssid(Long.parseLong(ids[i]));
			List<AssIndexTopatch> list = persistentService.find(assIndexTopatch);
        	if (list != null && list.size() > 0){
        		throw new Exception("考核指标任务["+ids[i]+"]已经下发，不能删除！");
        	}
		}
    }
    
    @Override
    @MetaData("更新")
    public HttpHeaders doDelete() {
    	
    	try {
    		String[] ids = getParameterIds();
    		checkDelete(ids);
        	
    		// 删除ass_index_object记录
        	for (int i = 0; i < ids.length; i++) {
        		AssIndexObject object = new AssIndexObject();
            	object.setAssid(Long.valueOf(ids[i]));
            	
            	List<AssIndexObject> objectList = persistentService.find(object);
            	if (objectList != null){
            		for (AssIndexObject assIndexObject : objectList) {
            			persistentService.delete(assIndexObject);
        			}
            	}
    		}
        	
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
        return super.doDelete();
    }
    
    private void updateAssIndexStore(String id, AssIndexStore assIndexStore) {	
    	try {
    		AssIndexStore store = (AssIndexStore)persistentService.find(AssIndexStore.class, Long.valueOf(id));
    		if (store != null){
    			
    			AssIndexObject object = new AssIndexObject();
    			object.setAssid(store.getId());
    			List<AssIndexObject> objectList = persistentService.find(object);
				if (objectList != null && objectList.size() > 0) {
					AssIndexObject tmpObject = objectList.get(0);

					// 更改了assobj，则进行更新
					if (!assIndexStore.getObjid().equals(tmpObject.getAssobj())) {
						tmpObject.setAssobj(assIndexStore.getObjid());
						persistentService.update(tmpObject);
					}

				} else {
					// 保存assindexobject对象
					AssIndexObject assIndexObject = new AssIndexObject();
					assIndexObject.setAssid(Long.valueOf(id));
					assIndexObject.setAssobj(assIndexStore.getObjid());

					persistentService.save(assIndexObject);
				}
    			
    			BeanUtils.copyProperties(assIndexStore, store);
    			
    			persistentService.update(store);
    		}
    		
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    }
    
    @Override
    @MetaData("保存")
    public HttpHeaders doSave()  {
    	
    	HttpHeaders httpHeaders = buildDefaultHttpHeaders();
    			
		try {

			// json串转换assIndexStore对象
			String assIndexStoreJson = getParameter("assIndexStore");
			AssIndexStore assIndexStore = (AssIndexStore) BeanUtil
					.jsonToObject(assIndexStoreJson, AssIndexStore.class);
			
			// expdate需要重新赋值
			assIndexStore.setExpdate(bindingEntity.getExpdate());
			
			// 检查：在expdate有效期内， assparam、objid是唯一的
			if (assIndexStoreService.checkExistRecord(assIndexStore)) {
				throw new ServiceException("在有效期内，已存在相同的考核对象!"); 
			}
						
			// 如果是id不为空，则认为是修改。
			String ids = getParameter("id");
			if (!StringUtils.isEmpty(ids)) {
				updateAssIndexStore(ids, assIndexStore);
				return httpHeaders;
			}

			// 保存assindexstore对象
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
			this.bindingEntity.setOperator(loginInfo.getOperid());
			this.bindingEntity.setOpdate(new Date());
			httpHeaders = super.doSave();
			
			// 保存assindexobject对象
			Long id = this.bindingEntity.getId();
			AssIndexObject assIndexObject = new AssIndexObject();
			assIndexObject.setAssid(id);
			assIndexObject.setAssobj(assIndexStore.getObjid());

			persistentService.save(assIndexObject);

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

        return httpHeaders;
    }

    public HttpHeaders deleteStore() {
    	try {
    		String id  = getParameter("id");
        	if (!StringUtils.isEmpty(id)){
        		AssIndexObject object = new AssIndexObject();
            	object.setAssid(Long.valueOf(id));
            	
            	List<AssIndexObject> objectList = persistentService.find(object);
            	if (objectList != null){
            		for (AssIndexObject assIndexObject : objectList) {
            			persistentService.delete(assIndexObject);
        			}
            	}
            	
            	AssIndexStore store = (AssIndexStore)persistentService.find(AssIndexStore.class, Long.valueOf(id));
            	if (store != null) {
            		persistentService.delete(store);
            	}
        	}
		} catch (Exception e) {
		}
    	
        return  buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try {
			String flag = getParameter("flag");
			 String asscontent = StringUtils.isEmpty(getParameter("asscontent"))? null : getParameter("asscontent");
			 
			 PageImpl pageResult = assIndexStoreService.queAssIndexStores(flag, asscontent,pageable);
		     setModel(pageResult);
			return buildDefaultHttpHeaders("index");
				
		} catch (Exception e) {
			List<AssIndexStore> list = new ArrayList<AssIndexStore>();
			setModel(new PageImpl(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("index");
	
    }
    
    @MetaData("查询")
    public HttpHeaders findByPageForTogrid() {
    	return findByPage();
    }
}