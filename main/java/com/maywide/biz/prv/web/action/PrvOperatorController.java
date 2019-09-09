package com.maywide.biz.prv.web.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.Assert;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.prv.entity.PrvRoleinfo;
import com.maywide.biz.prv.service.PrvOperatorService;
import com.maywide.biz.prv.service.PrvOperroleService;
import com.maywide.biz.prv.service.PrvRoleinfoService;
import com.maywide.biz.sta.gridmanagermsg.service.GridManagerMsgService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.captcha.ImageCaptchaServlet;
import com.maywide.core.web.view.OperationResult;


public class PrvOperatorController extends BaseController<PrvOperator, Long> {
	@Autowired
	private PrvOperatorService prvOperatorService;
	@Autowired
	private PrvRoleinfoService prvRoleinfoService;
	@Autowired
	private PrvOperroleService prvOperroleService;
	
	@Autowired
	private GridManagerMsgService gridManagerMsgService;
	
	@Override
    protected BaseService<PrvOperator, Long> getEntityService() {
        return prvOperatorService;
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
    
    public HttpHeaders inputTabs() {
    	//是否开通对接portal信息
    	try {
			setModel(gridManagerMsgService.isAccessPortal());
		} catch (Exception e) {
			setModel(false);
		}
        return buildDefaultHttpHeaders("inputTabs");
    }
    @Override
    @MetaData("保存")
    public HttpHeaders doSave() {
    	try {
    		checkInputTime();
    		//String[] roleIds = getParameterIds("r2ids");
        	prvOperatorService.registerOper(bindingEntity);
		} catch (Exception e) {
			setModel(OperationResult.buildFailureResult(e.getMessage()));
            return buildDefaultHttpHeaders();
		} 

        setModel(OperationResult.buildSuccessResult("账号信息保存成功", bindingEntity));
        return buildDefaultHttpHeaders();    	
    }
    
    @Override
    public HttpHeaders edit() {
    	try {
			List<PrvRoleinfo> roles = prvRoleinfoService.getOperRoles(bindingEntity.getId());
			this.getRequest().setAttribute("roles", roles);
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
        return buildDefaultHttpHeaders("inputBasic");
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
    
    public HttpHeaders register() {
    	HttpServletRequest request = ServletActionContext.getRequest();
        String jCaptcha = getRequiredParameter("j_captcha");
        if (!ImageCaptchaServlet.validateResponse(request, jCaptcha)) {
            setModel(OperationResult.buildFailureResult("验证码不正确，请重新输入"));
            return buildDefaultHttpHeaders();
        }
        
        try {
        	prvOperatorService.registerOper(bindingEntity);
		} catch (Exception e) {
			setModel(OperationResult.buildFailureResult(e.getMessage()));
            return buildDefaultHttpHeaders();
		} 

        setModel(OperationResult.buildSuccessResult("账号注册成功"));
        return buildDefaultHttpHeaders();
    }
    
    @MetaData(value = "密码修改显示")
    public HttpHeaders passwd() {
        return new DefaultHttpHeaders("passwd").disableCaching();
    }
    
    @MetaData(value = "密码修改处理")
    public HttpHeaders modifyPasswd() {
    	LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
    	
    	HttpServletRequest request = ServletActionContext.getRequest();
        String oldpasswd = request.getParameter("oldpasswd");
        String newpasswd = request.getParameter("newpasswd");
        Assert.isTrue(StringUtils.isNotBlank(oldpasswd));
        Assert.isTrue(StringUtils.isNotBlank(newpasswd));
        
        try {
        	prvOperatorService.modifyPasswd(loginInfo.getOperid(), newpasswd, oldpasswd);
		} catch (Exception e) {
			setModel(OperationResult.buildFailureResult(e.getMessage()));
            return buildDefaultHttpHeaders();
		}
        
		setModel(OperationResult.buildSuccessResult("密码修改成功,请在下次登录使用新密码"));
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("显示角色赋权信息")
    public HttpHeaders privs() throws Exception {
        return buildDefaultHttpHeaders("privs");
    }

    @MetaData("显示操作员关联网格")
    public HttpHeaders grids() throws Exception {
        return buildDefaultHttpHeaders("grids");
    }
    
    @MetaData("显示操作员关联的potal信息")
    public HttpHeaders portalinfo(){
    	try{
    		if (bindingEntity.getId() != null) {
                prvOperatorService.transEntity(bindingEntity);
            }
    	}catch(Exception e){
    		throw new WebException(e.getMessage());
    	}
    	
    	return buildDefaultHttpHeaders("portalinfo");
    }
    
    public HttpHeaders doSavePortalInfo(){
    	try {
			prvOperatorService.doSavePortalInfo(bindingEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e.getMessage().contains("Duplicate")){
				setModel(OperationResult.buildFailureResult("修改Portal信息失败,portal账户已经存在。"));
			}else{
				setModel(OperationResult.buildFailureResult("修改Portal信息失败"));
			}
			
			return buildDefaultHttpHeaders();
		}
    	setModel(OperationResult.buildSuccessResult("保存Portal信息成功"));
    	return buildDefaultHttpHeaders();
    }
    public HttpHeaders queryOperRole() {
    	PropertyFilter propertyFilter = new PropertyFilter(MatchType.EQ, "operid", bindingEntity.getId());
    	Pageable pageable = new PageRequest(0, 100, new Sort(Direction.ASC, "id"));
    	GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();
    	groupPropertyFilter.append(propertyFilter);
    	
    	setModel(prvOperroleService.findByPage(groupPropertyFilter, pageable));
    	
    	return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders queryDepartments() {
    	String loginname = getParameter("loginname");
    	setModel(prvOperatorService.queryDepartments(loginname));
    	
    	return buildDefaultHttpHeaders();
    } 
    
    public HttpHeaders findAllOperators() {
    	setModel(prvOperatorService.findAllOperators());
    	return buildDefaultHttpHeaders();
    }
    
    private void checkInputTime() {
    	if (bindingEntity.getStime() == null || bindingEntity.getEtime() == null) {
    		throw new WebException("启用时间和结束时间不能为空！");
    	}
    	
    	if (bindingEntity.getStime().getTime() > bindingEntity.getEtime().getTime()) {
    		throw new WebException("启用时间不能大于结束时间！");
    	}
    	
    	if (new Date().getTime() > bindingEntity.getEtime().getTime()) {
    		throw new WebException("结束时间必须大于当前时间！");
    	}
    }
    
    @MetaData("根据管理网格id查询未关联网格经理")
    public HttpHeaders findUnbindManagerByGridId() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String gridId = getParameter("gridid");
            String queryParam = getParameter("search['CN_loginname_OR_name']");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            setModel(prvOperatorService.findUnbindManagerByGridId(gridId, queryParam, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("根据操作员的地市分页查询全部操作员")
    public HttpHeaders findPageOpersByCity() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String queryParam = getParameter("search['CN_loginname_OR_name']");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            setModel(prvOperatorService.findPageOpersByCity(queryParam, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("根据操作员的地市查询全部操作员")
    public HttpHeaders findOpersByCity() {
        try {
            setModel(prvOperatorService.findOpersByCity());
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders findOneCityByPage() throws Exception {
   	    Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
   	    String name = getParameter("name");
   	    setModel(prvOperatorService.findOneCityByPage(pageable,name));
   	    return buildDefaultHttpHeaders();
   }
   
}
