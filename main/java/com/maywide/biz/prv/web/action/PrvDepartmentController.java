package com.maywide.biz.prv.web.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.service.PrvDepartmentService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prv.entity.PrvDepartment管理")
public class PrvDepartmentController extends BaseController<PrvDepartment,Long> {

    @Autowired
    private PrvDepartmentService prvDepartmentService;

    @Override
    protected BaseService<PrvDepartment, Long> getEntityService() {
        return prvDepartmentService;
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
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());

            String searchParam = getParameter("search['CN_name']");
            if (StringUtils.isBlank(searchParam)) {
                // 如果没有输入查询条件，则添加额外参数，查询某一部门的下属部门；否则就按查询条件进行全部门的模糊查询
                String preid = getParameter("preid");
                Long pidValueLong = null;
                if (StringUtils.isBlank(preid)) {
                    pidValueLong = prvDepartmentService.getFirstRootDept().getId();
                } else {
                    pidValueLong = Long.parseLong(preid);
                }
                groupFilter.append(new PropertyFilter(MatchType.EQ, "preid", pidValueLong));
            }

            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
                Page<PrvDepartment> page = this.getEntityService().findByPage(groupFilter, pageable);
                prvDepartmentService.transGridList(page.getContent());
                setModel(page);
            }
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders findAllDepartments() {
    	setModel(prvDepartmentService.findAllDepartments());
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("根据操作员查询部门")
    public HttpHeaders findDepartByOperid() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String operid = getParameter("operid");
            String deptName = getParameter("search['CN_name']");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            setModel(prvDepartmentService.findDepartByOperid(operid, deptName, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("根据操作员的地市分页查询部门")
    public HttpHeaders findPageDepartByCity() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String deptName = getParameter("search['CN_name']");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            setModel(prvDepartmentService.findPageDepartByCity(deptName, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("根据操作员的地市查询部门")
    public HttpHeaders findDepartByCity() {
        try {
            setModel(prvDepartmentService.findDepartByCity(false));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("显示部门树")
    public HttpHeaders deptTree() {
        try {
            List<PrvDepartment> gridList = prvDepartmentService.findDeptTree();
            setModel(OperationResult.buildSuccessResult("查询部门树成功", gridList));
        } catch (Exception e) {
            setModel(OperationResult.buildFailureResult("查询部门树失败：" + e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }
    
    public HttpHeaders findOneCityByPage() throws Exception {
   	    Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
   	    LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
   	    String city = loginInfo.getCity();
   	    String name = getParameter("name");
   	    setModel(prvDepartmentService.findOneCityByPage(pageable,name));
   	    return buildDefaultHttpHeaders();
   }
}