package com.maywide.biz.ass.target.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.target.entity.AssTargetStore;
import com.maywide.biz.ass.target.entity.AssTargetTocity;
import com.maywide.biz.ass.target.service.AssTargetStoreService;
import com.maywide.biz.ass.target.service.AssTargetTocityService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
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

@MetaData("[com.maywide].biz.ass.store.entity.AssTargetStore管理")
public class AssTargetController extends BaseController<AssTargetStore,Long> {

    @Autowired
    private AssTargetStoreService assTargetStoreService;
    
    @Autowired
    private PersistentService persistentService;
    
    @Autowired
    private AssTargetTocityService assTargetTocityService;
    

    @Override
    protected BaseService<AssTargetStore, Long> getEntityService() {
        return assTargetStoreService;
    }
    
    @Override
    protected void checkEntityAclPermission(AssTargetStore entity) {
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
    
    /**
     * 检测是否可删除
     * @param ids
     * @return
     * @throws Exception
     */
    private boolean checkDelete(String[] ids) throws Exception{
    	for (String id : ids) {
    		List list=assTargetTocityService.findByFilter(new PropertyFilter(MatchType.EQ, "assId",Long.valueOf(id)));
			if(list!=null && list.size()>0){
				return false;
			}
		}
    	return true;
    }
    
    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
    	
    	try {
    		String[] ids = getParameterIds();
    		if(this.checkDelete(ids)){
    			// 删除AssTargetStore记录
            	for (int i = 0; i < ids.length; i++) {
            		AssTargetStore object = new AssTargetStore();
                	object.setId(Long.valueOf(ids[i]));
                	
                	//逻辑删除，只更新一下状态
                	List<AssTargetStore> list = persistentService.find(object);
                	if (list != null){
                		for (AssTargetStore store : list) {
                			store.setIsDel(1);
                			persistentService.update(store);
            			}
                	}
        		}
            	
            	setModel(OperationResult.buildSuccessResult("指标已删除"));;
    		}
    		else{
    			setModel(OperationResult.buildFailureResult("当前指标已被地市选用，无法删除"));
    		}
        	
		} catch (Exception e) {
			setModel(OperationResult.buildFailureResult("指标删除发生异常，请联系管理员"));
			e.printStackTrace();
		}
    	
        return buildDefaultHttpHeaders();
    }
    
    private void updateAssTargetStore(String id, AssTargetStore assTargetStore) {	
		try {
			AssTargetStore store = (AssTargetStore) persistentService.find(
					AssTargetStore.class, Long.valueOf(id));
			if (store != null) {
				BeanUtils.copyProperties(assTargetStore, store);
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
			String assTargetStoreJson = getParameter("assTargetStore");
			AssTargetStore assTargetStore = (AssTargetStore) BeanUtil
					.jsonToObject(assTargetStoreJson, AssTargetStore.class);
						
			// 如果是id不为空，则认为是修改。
			String ids = getParameter("id");
			if (!StringUtils.isEmpty(ids)) {
				updateAssTargetStore(ids, assTargetStore);
				return httpHeaders;
			}

			// 保存assindexstore对象
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
			bindingEntity.setAssCode(AssTargetStore.genStoreCode());
			bindingEntity.setStatus(0);
			bindingEntity.setIsDel(0);
			bindingEntity.setOpttime(new Date());
			bindingEntity.setOperator(loginInfo.getOperid());
			httpHeaders = super.doSave();
			
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

        return httpHeaders;
    }


    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try {
			 String flag = getParameter("flag");
			 String name = StringUtils.isEmpty(getParameter("name"))? null : getParameter("name");
			 String city = StringUtils.isEmpty(getParameter("city"))? null : getParameter("city");
			 String status = StringUtils.isEmpty(getParameter("status"))? null : getParameter("status");
			 Map<String,String> param=new HashMap<String,String>();
			 param.put("name", name);
			 param.put("city", city);
			 param.put("status", status);
			 
			 PageImpl<AssTargetStore> pageResult = assTargetStoreService.queAssTargetStores(flag,param,pageable);
		     setModel(pageResult);
			return buildDefaultHttpHeaders("index");
				
		} catch (Exception e) {
			List<AssTargetStore> list = new ArrayList<AssTargetStore>();
			setModel(new PageImpl<AssTargetStore>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("index");
	
    }
    
}