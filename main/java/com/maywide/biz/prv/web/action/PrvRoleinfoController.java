package com.maywide.biz.prv.web.action;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.prv.entity.PrvRoleinfo;
import com.maywide.biz.prv.service.PrvMenuService;
import com.maywide.biz.prv.service.PrvRoleinfoService;
import com.maywide.common.sys.vo.TreeMenuVO;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prv.entity.PrvRoleinfo管理")
public class PrvRoleinfoController extends BaseController<PrvRoleinfo,Long> {

    @Autowired
    private PrvRoleinfoService prvRoleinfoService;
    
    @Autowired
    private PrvMenuService prvMenuService;
    
    @Autowired
	private ParamService paramService;

    @Override
    protected BaseService<PrvRoleinfo, Long> getEntityService() {
        return prvRoleinfoService;
    }
    
    @Override
    protected void checkEntityAclPermission(PrvRoleinfo entity) {
        // TODO Add acl check code logic
    }

    @MetaData("保存角色对应的菜单信息")
    public HttpHeaders saveRolePrivs() {
    	String menuIds = getParameter("menuIds");
    	Long roleId = bindingEntity.getId();
    	prvRoleinfoService.saveRolePrivs(roleId, menuIds);
    	
        setModel(OperationResult.buildSuccessResult("赋权角色菜单权限操作完成"));
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("显示角色赋权信息")
    public HttpHeaders privs() throws Exception {
    	return buildDefaultHttpHeaders("privs");
    }
    
    @MetaData("显示角色赋权信息")
    public HttpHeaders menus() throws Exception {
        try {
            Long roleId = new Long(getParameter("id"));
            List<TreeMenuVO> menuList = prvMenuService.findAllTreeMenus(roleId);
            setModel(OperationResult.buildSuccessResult("查询成功", menuList));
        } catch (Exception e) {
            setModel(OperationResult.buildFailureResult("查询失败：" + e.getMessage()));
        }
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
    	checkInputTime();
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
    	
    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());
        appendFilterProperty(groupFilter);
        
        String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
        if ("xls".equalsIgnoreCase(foramt)) {
            exportXlsForGrid(groupFilter, pageable.getSort());
        } else {
            setModel(this.getEntityService().findByPage(groupFilter, pageable));
        }
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("根据操作员的地市分页查询全部角色")
    public HttpHeaders findPageRoleByCity() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String queryParam = getParameter("search['CN_name']");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            setModel(prvRoleinfoService.findPageRoleByCity(queryParam, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("根据操作员的地市查询全部角色")
    public HttpHeaders findRoleByCity() {
        try {
            setModel(prvRoleinfoService.findRoleByCity());
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
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

    // 编辑表单用
    public Map<String, String> getPrvCityMap() throws Exception {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        String city = null;
        if (null != loginInfo && !loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)) {
            city = loginInfo.getCity();
        }

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("*", "全部");
        map.putAll(paramService.getSelectData("PRV_CITY", city));
        return map;
    }

    public HttpHeaders edit() {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//        if (null != loginInfo && !loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)) {
//            setContain(loginInfo.getCity());
//        }
        if (bindingEntity.isNotNew()) {
            Map<String, String> map = prvRoleinfoService.getAreaMapOrCity(bindingEntity.getAreas(), false);
            String city = map.get("city");
            bindingEntity.addExtraAttribute("city", city);
            bindingEntity.setLoginRolelevel(loginInfo.getRolelevel());
        }
        if(bindingEntity.getId() == null){
            bindingEntity.setRolelevel(loginInfo.getRolelevel());
            bindingEntity.setLoginRolelevel(loginInfo.getRolelevel());
        }
        return buildDefaultHttpHeaders("inputBasic");
    }

    // 编辑表单用
    public Map<String, String> getAreasMap() throws Exception {
        Map<String, String> map = new LinkedHashMap<String, String>();
        if (bindingEntity.isNotNew()) {
            map.putAll(prvRoleinfoService.getAreaMapOrCity(bindingEntity.getAreas(), true));
        }
        return map;
    }

    private String contain;

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
	}
}