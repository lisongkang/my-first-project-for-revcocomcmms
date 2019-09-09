package com.maywide.biz.prd.web.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prd.service.SalespkgKnowService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Constant;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prd.entity.SalespkgKnow管理")
public class SalespkgKnowController extends BaseController<SalespkgKnow, Long> {

    @Autowired
    private SalespkgKnowService salespkgKnowService;

    @Autowired
    private ParamService        paramService;

    @Override
    protected BaseService<SalespkgKnow, Long> getEntityService() {
        return salespkgKnowService;
    }

    @Override
    protected void checkEntityAclPermission(SalespkgKnow entity) {
        // TODO Add acl check code logic
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        // TODO
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
            String[] opcodes = getParameterIds("r2ids");
            bindingEntity.setImage(bindingEntity.getIcon());
            salespkgKnowService.saveEntity(bindingEntity, opcodes);
        } catch (Exception e) {
            setModel(OperationResult.buildFailureResult(e.getMessage()));
            return buildDefaultHttpHeaders();
        }
        setModel(OperationResult.buildSuccessResult("知识库配置保存成功"));
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    public HttpHeaders edit() {
        try {
            List<PrvSysparam> opcodes = salespkgKnowService.getOpcodes(bindingEntity);
            this.getRequest().setAttribute("opcodes", opcodes);
            if (bindingEntity.getId() != null) {
                salespkgKnowService.transEntity(bindingEntity);
            }
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders("inputBasic");
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());

            LoginInfo logininfo = AuthContextHolder.getLoginInfo();
            String city = logininfo.getCity();
            if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(logininfo.getLoginname())) {
                // 超级管理员查询全部地市的记录
                groupFilter.append(new PropertyFilter(MatchType.EQ, "city", city));
            }

            appendFilterProperty(groupFilter);

            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
                Page<SalespkgKnow> page = this.getEntityService().findByPage(groupFilter, pageable);
                setModel(page);
            }
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }

        return buildDefaultHttpHeaders();
    }

    public Map<String, String> getAreaMap() {
        List<PrvSysparam> areaList;
        Map<String, String> areaMap = new LinkedHashMap<String, String>();
        try {
            areaList = paramService.getData("PRV_CITY");
            for (PrvSysparam param : areaList) {
                areaMap.put(param.getMcode(), param.getDisplay());
            }

            return areaMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaMap;

    }

    @MetaData("根据操作员的地市查询知识库")
    public HttpHeaders findAllKnowsByCity() {
        try {
            setModel(salespkgKnowService.findAllKnowsByCity());
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("根据操作员的地市分页查询营销方案")
    public HttpHeaders findPageKnowsByCity() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String searchField = getParameter("search['CN_knowname_OR_objcode_OR_objname']");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            setModel(salespkgKnowService.findPageKnowsByCity(searchField, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    public Map<String, String> getObjTypeMap() {
        Map<String, String> objTypeMap = new LinkedHashMap<String, String>();
        try {
            objTypeMap = salespkgKnowService.getObjTypeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objTypeMap;
    }
}