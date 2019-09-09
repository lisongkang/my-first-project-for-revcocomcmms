


package com.maywide.biz.salary.web.action;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.salary.entity.OthersKpi;
import com.maywide.biz.salary.entity.OthersKpiTextConfig;
import com.maywide.biz.salary.service.OthersKpiService;
import com.maywide.biz.salary.service.OthersKpiTextConfigService;
import com.maywide.core.cons.Constant;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * <p>
 * 积分项目配置
 * <p>

 */
public class OthersKpiTextConfigController extends SalaryController<OthersKpiTextConfig, Long> {

    @Autowired
    private OthersKpiTextConfigService othersKpiTextConfigService;
    @Autowired
    private PersistentService persistentService;

    private OthersKpiTextConfig othersKpiTextConfig;  //搜索影射对象

    @Override
    protected BaseService<OthersKpiTextConfig, Long> getEntityService() {
        return othersKpiTextConfigService;
    }

    /**
     * 分页搜索
     */
    public HttpHeaders findByPage() {
        List<OthersKpiTextConfig> list = new ArrayList<OthersKpiTextConfig>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            if (!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)) {
                othersKpiTextConfig.setCity(loginInfo.getCity());
            }
            setModel(othersKpiTextConfigService.findByPage(othersKpiTextConfig, pageable));

        } catch (Exception e) {
            e.printStackTrace();
            setModel(new PageImpl<OthersKpiTextConfig>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }

    /**
     * 新增或者修改
     *
     * @return
     */
    @Override
    public HttpHeaders doSave() {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        try {
            if (null == getId() || StringUtils.isBlank(getId().toString())) {
                boolean exist = othersKpiTextConfigService.exists(bindingEntity.getCity(),bindingEntity.getAreaid(),
                        bindingEntity.getType(),bindingEntity.getContext());
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
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("保存操作失败"));
        }

        return buildDefaultHttpHeaders();
    }
    /**
     * 删除
     */
    public HttpHeaders doDelete() {
        Collection<OthersKpiTextConfig> entities = this.getEntitiesByParameterIds();
        try {
            for (OthersKpiTextConfig entity : entities) {
                long count = persistentService.count("select 1 from salary_others_kpi where text_config_id=?",entity.getId());
                if(count>0){
                    throw new Exception("已有积分使用此项目("+entity.getContext()+")无法删除!");
                }
                getEntityService().delete(entity);
            }
            setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + entities.size() + "条"));
            return buildDefaultHttpHeaders();
        }catch (Exception e){
            setModel(OperationResult.buildSuccessResult("删除失败:"+e.getMessage()));
            return buildDefaultHttpHeaders();
        }
    }

    public Map<String, String> getTypeMap(){
        return getParamByGcode("SALARY-OTHERS-KPI-TEXT-CONFIG_TYPE",null);
    }
    public Map<String, String> getShowMap(){
        return getParamByGcode("SALARY-OTHERS-KPI-TEXT-CONFIG_SHOW",null);
    }


    public OthersKpiTextConfig getOthersKpiTextConfig() {
        return othersKpiTextConfig;
    }

    public void setOthersKpiTextConfig(OthersKpiTextConfig othersKpiTextConfig) {
        this.othersKpiTextConfig = othersKpiTextConfig;
    }
}
