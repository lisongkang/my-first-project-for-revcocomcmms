package com.maywide.biz.prd.web.action;

import java.util.Date;
import java.util.List;

import net.sf.jasperreports.web.actions.SaveAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prd.entity.PrvSalesPoint;
import com.maywide.biz.prd.pojo.QuePrvSalesPointParams;
import com.maywide.biz.prd.service.PrvSalesPointService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prd.entity.PrvSalesPoint管理")
public class PrvSalesPointController extends BaseController<PrvSalesPoint,Long> {

    @Autowired
    private PrvSalesPointService prvSalesPointService;
    
    private QuePrvSalesPointParams quePrvSalesPointParams;
    
    
    public QuePrvSalesPointParams getQuePrvSalesPointParams() {
		return quePrvSalesPointParams;
	}

	public void setQuePrvSalesPointParams(
			QuePrvSalesPointParams quePrvSalesPointParams) {
		this.quePrvSalesPointParams = quePrvSalesPointParams;
	}

	@Override
    protected BaseService<PrvSalesPoint, Long> getEntityService() {
        return prvSalesPointService;
    }
    
    @Override
    protected void checkEntityAclPermission(PrvSalesPoint entity) {
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
    	if(bindingEntity.getIsvalid() == 0){
    		  setModel(OperationResult.buildFailureResult("不能够修改为有效的状态"));
    		  return buildDefaultHttpHeaders(); 
    	}
    	bindingEntity.setEtime(new Date());
        return super.doUpdate();
    }
    
    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
    	try {
    	 bindingEntity.setEtime(new Date("2099/12/31")); //失效時間2099/12/31
		 bindingEntity.setIsvalid(0L);//【状态】默认为有效
		 bindingEntity.setIntime(new Date());
		 if(bindingEntity.getSalesid() != null){
			 String salename =  prvSalesPointService.getSalesname(bindingEntity.getSalesid());	
			 bindingEntity.setSalesname(salename);
		 }
    	}
		 catch (Exception e) {
			e.printStackTrace();
		 }
    	 return super.doSave();
    }

    /**
     * 这个是二次提交数据，当用户确定让已经存在的销售积分失效，并且重新写新的第一数据
     * @return
     */
    public HttpHeaders addNewDataAndUpdateData(){
    	 if(bindingEntity.getId()!=null){
    		 List<PrvSalesPoint> list =  prvSalesPointService.findAll(bindingEntity.getId());
    		 if(list.size()>0){
    			 PrvSalesPoint salesPoint = list.get(0);
    			 salesPoint.setIsvalid(1L);//设置为失效
    			 salesPoint.setEtime(new Date());
    			 prvSalesPointService.save(salesPoint);
    		 }
    		bindingEntity.setId(null);
    		
    	}
    	 return doSave();
    }
    public HttpHeaders checkSalePoint() throws Exception{
    //	String salePointJson = getParameter("prvSalesPoint");
    //	PrvSalesPoint prvSalesPoint = (PrvSalesPoint) BeanUtil.jsonToObject(salePointJson,PrvSalesPoint.class);
    
    	PrvSalesPoint prvSalesPoint  = prvSalesPointService.checkSalePoint(bindingEntity);
    	if(prvSalesPoint == null){
    		
    	    return	doSave();
    	}else{
    		bindingEntity.setId(prvSalesPoint.getId());
    		bindingEntity.setOpcodename(prvSalesPoint.getOpcodename());
    		bindingEntity.setCityname(prvSalesPoint.getCityname());
    		bindingEntity.setSalesname(prvSalesPoint.getSalesname());
    		setModel(OperationResult.buildSuccessResult("id",bindingEntity));
    		return buildDefaultHttpHeaders();
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
    	LoginInfo loginInfo =  AuthContextHolder.getLoginInfo();
    	String city = loginInfo.getCity();
    	String level = loginInfo.getRolelevel();
    	
    	if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(loginInfo.getLoginname())) {
    		quePrvSalesPointParams.setCity(city);
        }
    	/*if(StringUtils.isNotEmpty(level) && !level.equals("9")){
    		quePrvSalesPointParams.setAreaid(loginInfo.getAreaid());
    	}*/
    	
    	 try {
             Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
             setModel( prvSalesPointService.queryPrvSalesPointList(quePrvSalesPointParams, pageable));
         } catch (Exception e) {
             throw new WebException(e.getMessage(), e);
         }
    	 return buildDefaultHttpHeaders();
       
    }
    
    
    
}