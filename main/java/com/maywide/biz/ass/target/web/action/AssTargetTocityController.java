package com.maywide.biz.ass.target.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.target.bo.AssTargetPatchBo;
import com.maywide.biz.ass.target.entity.AssTargetStore;
import com.maywide.biz.ass.target.entity.AssTargetTocity;
import com.maywide.biz.ass.target.service.AssTargetStoreService;
import com.maywide.biz.ass.target.service.AssTargetTocityService;
import com.maywide.biz.ass.target.service.AssTargetTogridService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.view.OperationResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.ass.store.entity.AssTargetTocity管理")
public class AssTargetTocityController extends BaseController<AssTargetTocity,Long> {

	@Autowired
    private AssTargetStoreService assTargetStoreService;
	
    @Autowired
    private AssTargetTocityService assTargetTocityService;
    
    @Autowired
    private AssTargetTogridService assTargetTogridService;
    
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseService<AssTargetTocity, Long> getEntityService() {
        return assTargetTocityService;
    }
    
    @Override
    protected void checkEntityAclPermission(AssTargetTocity entity) {
        // TODO Add acl check code logic
    }

    /**
	 * 广告配置首页
	 */
	public HttpHeaders index(){
		return buildDefaultHttpHeaders("index");
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
    @MetaData("删除")
    public HttpHeaders doDelete() {
    	
    	try {
    		String[] ids = getParameterIds();
    		for(String id : ids){
    			AssTargetTocity tocity=this.assTargetTocityService.findOne(Long.valueOf(id));
    			AssTargetPatchBo bo=new AssTargetPatchBo();
    			bo.setAssId(tocity.getAssId());
    			bo.setCity(tocity.getCity());
    			if(StringUtils.isBlank(bo.getCity())||StringUtils.isBlank(bo.getAssId()+"")){
    				setModel(OperationResult.buildFailureResult("检测数据异常，不能删除"));
    				return this.buildDefaultHttpHeaders();
    			}
    			
    			List list=this.assTargetTogridService.checkAssIsExist(bo);
    			if(list.size()>0){
    				setModel(OperationResult.buildFailureResult("该指标已下发，不能删除"));
    				return this.buildDefaultHttpHeaders();
    			}
    		}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
        return super.doDelete();
    }
    
    @Override
    @MetaData("保存")
    public HttpHeaders doSave()  {
    	
		try {
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    		
			String ids = getParameter("ids");
			if (!StringUtils.isEmpty(ids)) {
				for(String id : ids.split(",")){
					AssTargetTocity tocity=new AssTargetTocity();
					tocity.setAssdate(new Date());
					tocity.setAssId(Long.valueOf(id));
					tocity.setOperator(loginInfo.getOperid());
					//设置地市
					AssTargetStore target=assTargetStoreService.findOne(Long.valueOf(id));
					if(target==null) throw new Exception("指标不存在");
					
					tocity.setAssCity(target.getCity());
					tocity.setCity(loginInfo.getCity());
					
					persistentService.save(tocity);
				}
				
				return buildDefaultHttpHeaders("index");
			}

		} catch (Exception e) {
			setModel(OperationResult.buildFailureResult("操作失败"));
			e.printStackTrace();
			throw new ServiceException("数据保存失败，请联系管理员");
		}

		return buildDefaultHttpHeaders();
    }
    
    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try {
			 String name = StringUtils.isEmpty(getParameter("name"))? null : getParameter("name");
			 String assCode = StringUtils.isEmpty(getParameter("assCode"))? null : getParameter("assCode");
			 String city = StringUtils.isEmpty(getParameter("city"))? null : getParameter("city");
			 String status = StringUtils.isEmpty(getParameter("status"))? null : getParameter("status");
			 Map<String,String> param=new HashMap<String,String>();
			 param.put("name", name);
			 param.put("assCode", assCode);
			 param.put("city", city);
			 param.put("status", status);
			 
			 PageImpl<AssTargetStore> pageResult = assTargetTocityService.getTargetTocity(param,pageable);
		     setModel(pageResult);
			return buildDefaultHttpHeaders("index");
				
		} catch (Exception e) {
			List<AssTargetStore> list = new ArrayList<AssTargetStore>();
			setModel(new PageImpl<AssTargetStore>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("index");
	
    }
    
    /**
     * 获取最新的指标信息
     * @author:liaoxiangjun
     * @return
     */
    public HttpHeaders getNewTarget(){
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try {
			 String flag = getParameter("flag");
			 String name = StringUtils.isEmpty(getParameter("name"))? null : getParameter("name");
			 String assCode = StringUtils.isEmpty(getParameter("assCode"))? null : getParameter("assCode");
			 String city = StringUtils.isEmpty(getParameter("city"))? null : getParameter("city");
			 String status = StringUtils.isEmpty(getParameter("status"))? null : getParameter("status");
			 Map<String,String> param=new HashMap<String,String>();
			 param.put("name", name);
			 param.put("assCode", assCode);
			 param.put("city", city);
			 
			 PageImpl<AssTargetStore> pageResult = assTargetTocityService.getTargetOfSelect(flag,param,pageable);
		     setModel(pageResult);
			return buildDefaultHttpHeaders("selects");
				
		} catch (Exception e) {
			List<AssTargetStore> list = new ArrayList<AssTargetStore>();
			setModel(new PageImpl<AssTargetStore>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("selects");
	}
}