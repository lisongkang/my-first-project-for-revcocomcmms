package com.maywide.biz.salary.web.action;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.salary.entity.ExplicationConfig;
import com.maywide.biz.salary.service.ExplicationConfigService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.cons.Constant;
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

import java.util.*;

/**
 * 
 *<p> 
 *  绩效积分说明
 *<p>
 * Create at 2017-3-20
 *
 *@autor zhuangzhitang
 */
public class ExplicationConfigController extends SalaryController<ExplicationConfig,Long>{

	@Autowired
	private ExplicationConfigService explicationConfigService;
	
	private ExplicationConfig explicationConfig;  //搜索影射对象
	@Autowired
	private PersistentService persistentService;
	@Override
	protected BaseService<ExplicationConfig, Long> getEntityService() {
		
		return explicationConfigService;
	}

	/**
	 * 分页搜索
	 */
	public HttpHeaders findByPage(){
		List<ExplicationConfig> list = new ArrayList<ExplicationConfig>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try{
			LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    		if(!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)){
				explicationConfig.setCity(loginInfo.getCity());
    		}
			setModel(explicationConfigService.findByPage(explicationConfig,pageable));
			
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<ExplicationConfig>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
	}

	/**
	 * 新增或者修改
	 * @return
	 */
	@Override
	public HttpHeaders doSave(){
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		try{
			if(null==getId()||StringUtils.isBlank(getId().toString())){
				boolean exist = explicationConfigService.exists(bindingEntity.getCity(),bindingEntity.getAreaid(),
						bindingEntity.getType());
				if(exist){
					setModel(OperationResult.buildFailureResult("保存失败，该条数据已存在无法重复创建!", bindingEntity));
					return buildDefaultHttpHeaders();
				}
				bindingEntity.setCreateAt(new Date());
				bindingEntity.setCreateBy(loginInfo.getLoginname());
			}
			bindingEntity.setUpdateAt(new Date());
			bindingEntity.setUpdateBy(loginInfo.getLoginname());
			bindingEntity.setAreaid(bindingEntity.getAreaid().replace(" ",""));
			getEntityService().save(bindingEntity);
			setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
		}catch(Exception e){
			e.printStackTrace();
			setModel(OperationResult.buildFailureResult("保存操作失败"));
		}

		return buildDefaultHttpHeaders();
	}

	public Map<String, String> getTypeMap(){
		return getParamByGcode("SALARY_EXPLICATION_TYPE",null);
	}

	public ExplicationConfig getExplicationConfig() {
		return explicationConfig;
	}

	public void setExplicationConfig(ExplicationConfig explicationConfig) {
		this.explicationConfig = explicationConfig;
	}
}
