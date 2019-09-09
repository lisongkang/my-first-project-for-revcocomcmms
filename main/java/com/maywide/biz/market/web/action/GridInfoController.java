package com.maywide.biz.market.web.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.pojo.GridTreeInfo;
import com.maywide.biz.market.service.GridInfoService;
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

@MetaData("[com.maywide].biz.ass.topatch.entity.BizGridInfo管理")
public class GridInfoController extends BaseController<BizGridInfo, Long> {
    @Autowired
    private GridInfoService gridInfoService;

    @Override
    protected BaseService<BizGridInfo, Long> getEntityService() {
        return gridInfoService;
    }

    @Override
    protected void checkEntityAclPermission(BizGridInfo entity) {
    }

    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        setModel(OperationResult.buildSuccessResult("TODO操作完成"));
        return buildDefaultHttpHeaders();
    }

    public HttpHeaders manager() {
        return buildDefaultHttpHeaders("manager");
    }

    public HttpHeaders asstogrid() {
        return buildDefaultHttpHeaders("asstogrid");
    }

    public HttpHeaders gridaddress() {
        return buildDefaultHttpHeaders("gridaddress");
    }

    @Override
    @MetaData("创建")
    public HttpHeaders doCreate() {
        return super.doCreate();
    }

    @Override
    @MetaData("打开编辑表单")
    public HttpHeaders edit() {
        try {
            bindingEntity.setPreGridName(gridInfoService.getPreGridName(bindingEntity.getPrevid()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders("inputBasic");
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
            if (bindingEntity.isNew()) {
                Long previd = bindingEntity.getPrevid();
                BizGridInfo preGridInfo = getEntityService().findOne(previd);
                bindingEntity.setCity(preGridInfo.getCity()); // 使用父网格的city
                bindingEntity.setPrio(100L);
                bindingEntity.setStatid(previd); // 暂时statid与previd一样，后续修改
            }
            String memo = bindingEntity.getMemo();
            if (null == memo || "".equals(memo)) {
                bindingEntity.setMemo(null);
            }
            this.getEntityService().save(bindingEntity);
            setModel(OperationResult.buildSuccessResult("数据保存成功", bindingEntity));
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @SuppressWarnings("unchecked")
    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        super.doDelete();

        // 重新构造OperationResult，方便页面处理
        OperationResult oldResult = (OperationResult) (this.getModel());
        if (!oldResult.getType().equals(OperationResult.OPERATION_RESULT_TYPE.success.name())) {
            Map<Long, String> errorMsgMap = (LinkedHashMap<Long, String>) oldResult.getUserdata();
            Set<Long> idSet = errorMsgMap.keySet();

            List<String> delSuccessIdList = new ArrayList<String>();
            for (String idStr : getParameterIds()) {
                if (!idSet.contains(Long.parseLong(idStr))) {
                    delSuccessIdList.add(idStr);
                }
            }

            // 把成功删除的部分id信息返回到页面
            errorMsgMap.put(-1L, Arrays.toString(delSuccessIdList.toArray()).replace("[", "").replace("]", ""));

            setModel(OperationResult.buildSuccessResult("error-" + oldResult.getMessage(), errorMsgMap));
        } else {
            setModel(OperationResult.buildSuccessResult("success-" + oldResult.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }

    @Override
    public String isDisallowDelete(BizGridInfo entity) {
        // doDelete中会调用进行验证
        return gridInfoService.checkCanDelete(entity);
    }

    @MetaData("显示基础网格树")
    public HttpHeaders gridTree() {
        try {
            List<GridTreeInfo> gridList = gridInfoService.findGridTree(1);
            setModel(OperationResult.buildSuccessResult("查询基础网格树成功", gridList));
        } catch (Exception e) {
            setModel(OperationResult.buildFailureResult("查询基础网格树失败：" + e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("显示数据网格树")
    public HttpHeaders gridTreeForDataGrid() {
        try {
            List<GridTreeInfo> gridList = gridInfoService.findGridTree(2);
            setModel(OperationResult.buildSuccessResult("查询数据网格树成功", gridList));
        } catch (Exception e) {
            setModel(OperationResult.buildFailureResult("查询数据网格树失败：" + e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, getRequest());

            String searchParam = getParameter("search['CN_gridname_OR_gridcode']");
            String pidName = getParameter("pidName");
            if (StringUtils.isBlank(pidName)) {
                pidName = "previd"; // 默认按previd查询根网格
            }

            if (StringUtils.isBlank(searchParam)) {
                // 如果没有输入查询条件，则添加额外参数，查询某一网格的下属网格；否则就按查询条件进行全网格的模糊查询
                String pidValue = getParameter("pidValue");
                Long pidValueLong = null;

                if (StringUtils.isBlank(pidValue)) {
                    // 该参数为空时，先从数据库查询第一个pidName=-1且最高排序优先级的网格
                    pidValueLong = gridInfoService.getFirstRootGrid(pidName).getId();
                } else {
                    pidValueLong = Long.parseLong(pidValue);
                }
                groupFilter.append(new PropertyFilter(MatchType.EQ, pidName, pidValueLong));
            } else {
                LoginInfo logininfo = AuthContextHolder.getLoginInfo();
                String city = logininfo.getCity();
                if (StringUtils.isNotEmpty(city) && !Constant.ADMINISTRATOR.equals(logininfo.getLoginname())) {
                    // 超级管理员查询全部地市的网格
                    groupFilter.append(new PropertyFilter(MatchType.EQ, "city", city));
                }
            }

            appendFilterProperty(groupFilter);
            String foramt = this.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
            if ("xls".equalsIgnoreCase(foramt)) {
                exportXlsForGrid(groupFilter, pageable.getSort());
            } else {
                Page<BizGridInfo> page = this.getEntityService().findByPage(groupFilter, pageable);
                gridInfoService.transGridList(page.getContent(), pidName);
                setModel(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("关联小区网格")
    public HttpHeaders bindPatch() {
        try {
            List<BizGridInfo> patchGridList = gridInfoService.bindPatch(getParameterIds(),
                    Long.parseLong(getParameter("gridid")));
            setModel(OperationResult.buildSuccessResult("关联小区网格成功", patchGridList));
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("关联网格经理")
    public HttpHeaders bindManager() {
        try {
            gridInfoService.bindManager(getParameterIds(), Long.parseLong(getParameter("gridid")));
            setModel(OperationResult.buildSuccessResult("关联网格经理成功")); // 页面不需要对关联的经理再做处理，所以不用返回关联对象到页面
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @SuppressWarnings("rawtypes")
    @MetaData("根据网格id查询考核内容")
    public HttpHeaders queryAssByGridid() {
        try {
            BizGridInfo gridInfo = gridInfoService.findOne(Long.parseLong(getParameter("gridid")));
            Long gtype = gridInfo.getGtype();
            boolean isManageGrid = (gtype == 0L ? true : (gtype == 2L ? true : false));

            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            PageImpl page = gridInfoService.queryAssByGridid(gridInfo.getId(), isManageGrid, pageable);
            gridInfoService.transAssList(page.getContent(), isManageGrid);
            setModel(page);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("更新所属数据网格")
    public HttpHeaders updatePreDataGrid() {
        try {
            gridInfoService.updatePreDataGrid(Long.parseLong(getParameter("newStatid")), getEntitiesByParameterIds());
            setModel(OperationResult.buildSuccessResult("修改成功"));
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    public HttpHeaders updatePreBaseGrid(){
    	try{
    		Long newPreid = Long.parseLong(getParameter("newPreid"));
        	gridInfoService.updatePreBaseGrid(newPreid,getEntitiesByParameterIds());
        	setModel(OperationResult.buildSuccessResult("修改成功"));
    	}catch(Exception e){
    		  throw new WebException(e.getMessage());
    	}
    	return buildDefaultHttpHeaders();
    }
    @MetaData("根据操作员的查询网格")
    public HttpHeaders queryGridsByOperid() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String operid = getParameter("operid");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            setModel(gridInfoService.queryGridsByOperid(operid, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    // 编辑表单用
    public Map<String, String> getGtypeMap() {
        Map<String, String> map = new HashMap<String, String>();
        if (bindingEntity.isNew()) {
            map.put(BizConstant.GridType.MANAGE_GRID + "",
                    BizConstant.GridType.GRID_TYPE_NAMES[BizConstant.GridType.MANAGE_GRID]);
            map.put(BizConstant.GridType.ADDRESS_GRID + "",
                    BizConstant.GridType.GRID_TYPE_NAMES[BizConstant.GridType.ADDRESS_GRID]);
        } else {
            int gtype = bindingEntity.getGtype().intValue();
            map.put(gtype + "", BizConstant.GridType.GRID_TYPE_NAMES[gtype]);
        }
        return map;
    }

    @SuppressWarnings("rawtypes")
    @MetaData("根据网格id查询关联地址")
    public HttpHeaders queryAddressByGridid() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            PageImpl page = gridInfoService.queryAddressByGridid(Long.parseLong(getParameter("gridid")), pageable);
            setModel(page);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("根据网格id查询未关联地址")
    public HttpHeaders findUnbindAddressByGridId() {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            String gridId = getParameter("gridid");
            String orderField = getParameter("sidx");
            String sortType = getParameter("sord");
            setModel(gridInfoService.findUnbindAddressByGridId(gridId, pageable, orderField, sortType));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("关联网格地址")
    public HttpHeaders bindAddress() {
        try {
            gridInfoService.bindAddress(getParameterIds(), Long.parseLong(getParameter("gridid")));
            setModel(OperationResult.buildSuccessResult("关联网格地址成功"));
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return buildDefaultHttpHeaders();
    }

    @MetaData("删除网格的关联地址")
    public HttpHeaders doDeleteAddress() {
        String[] addressIds = getParameterIds();
        int size = addressIds.length;
        Map<Long, String> errorMessageMap = gridInfoService.doDeleteAddress(addressIds,
                Long.parseLong(getParameter("gridid")));
        int rejectSize = errorMessageMap.size();
        if (rejectSize == 0) {
            setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + size + "条"));
        } else {
            if (rejectSize == size) {
                setModel(OperationResult.buildFailureResult("所有选取记录删除操作失败", errorMessageMap));
            } else {
                setModel(OperationResult.buildWarningResult("删除操作已处理. 成功:" + (size - rejectSize) + "条" + ",失败:"
                        + rejectSize + "条", errorMessageMap));
            }
        }
        return buildDefaultHttpHeaders();
    }
}