


package com.maywide.biz.salary.web.action;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.salary.entity.ExplicationConfig;
import com.maywide.biz.salary.entity.OthersExchangeConfig;
import com.maywide.biz.salary.service.OthersExchangeConfigService;
import com.maywide.core.cons.Constant;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 积分奖励兑换规则
 * <p>

 */
public class OthersExchangeConfigController extends SalaryController<OthersExchangeConfig, Long> {

    @Autowired
    private OthersExchangeConfigService othersExchangeConfigService;

    private OthersExchangeConfig othersExchangeConfig;  //搜索影射对象

    @Override
    protected BaseService<OthersExchangeConfig, Long> getEntityService() {
        return othersExchangeConfigService;
    }
    /**
     * 新增修改页
     * @return
     */
    public HttpHeaders edit(){
        if(StringUtils.isNotEmpty(bindingEntity.getDisplay())) {
            String[] areaids = bindingEntity.getDisplay().split(",");
            bindingEntity.addExtraAttribute("areaids", areaids);
        }else{
            bindingEntity.addExtraAttribute("areaids", "*");
        }
        if(bindingEntity.getEcontrol()!=null)
        bindingEntity.setEcontrol(new BigDecimal(bindingEntity.getEcontrol().toString())
                .multiply(new BigDecimal(100)).doubleValue());
        if(bindingEntity.getScontrol()!=null)
        bindingEntity.setScontrol(new BigDecimal(bindingEntity.getScontrol().toString())
                .multiply(new BigDecimal(100)).doubleValue());
        if(bindingEntity.getMaxCentsPrice()!=null)
        bindingEntity.setMaxCentsPrice(new BigDecimal(bindingEntity.getMaxCentsPrice().toString())
                .multiply(new BigDecimal(100)).doubleValue());
        if(bindingEntity.getMinCentsPrice()!=null)
        bindingEntity.setMinCentsPrice(new BigDecimal(bindingEntity.getMinCentsPrice().toString())
                .multiply(new BigDecimal(100)).doubleValue());
        getEntityService().clear();
        return buildDefaultHttpHeaders("inputBasic");
    }
    /**
     * 分页搜索
     */
    public HttpHeaders findByPage() {
        List<OthersExchangeConfig> list = new ArrayList<OthersExchangeConfig>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            if (!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)) {
                othersExchangeConfig.setCity(loginInfo.getCity());
            }
            setModel(othersExchangeConfigService.findByPage(othersExchangeConfig, pageable));

        } catch (Exception e) {
            e.printStackTrace();
            setModel(new PageImpl<OthersExchangeConfig>(list, pageable, 1));
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
                bindingEntity.setStatus("0");
                bindingEntity.setCreateAt(new Date());
                bindingEntity.setCreateBy(loginInfo.getLoginname());
            }
            bindingEntity.setUpdateAt(new Date());
            bindingEntity.setUpdateBy(loginInfo.getLoginname());
            othersExchangeConfigService.doSave(bindingEntity);
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
        try {
            Collection<OthersExchangeConfig> entities = this.getEntitiesByParameterIds();
            for (OthersExchangeConfig entity : entities) {
                othersExchangeConfigService.doDelete(entity);
            }
            setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + entities.size() + "条"));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("删除操作失败"));
        }
        return buildDefaultHttpHeaders();
    }

    public HttpHeaders findList(){
        Map<String,Object> result = new HashMap<String, Object>();
        String city = getRequest().getParameter("city");
        String areaid = getRequest().getParameter("areaid");
        String grid = getRequest().getParameter("grid");
        OthersExchangeConfig config = new OthersExchangeConfig();
        config.setCity(city);
        config.setAreaid(areaid);
        config.setGrid(grid);
        try {
           List<OthersExchangeConfig> list = othersExchangeConfigService.findList(config);
            result.put("code","0");
            result.put("msg","");
            result.put("data",list);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code","1");
            result.put("msg","查询异常!");
            result.put("data",null);
        }
        setModel(result);
        return buildDefaultHttpHeaders();
    }
    public Map<String, String> getTypeMap(){
        return getParamByGcode("SALARY_OTHERS_EXCHANGE_CONFIG_TYPE",null);
    }

    public Map<String, String> getFormulaTypeMap(){
            return getParamByGcode("SALARY_OTHERS_EXCHANGE_CONFIG_FORMULA",null);
    }

    public OthersExchangeConfig getOthersExchangeConfig() {
        return othersExchangeConfig;
    }

    public void setOthersExchangeConfig(OthersExchangeConfig othersExchangeConfig) {
        this.othersExchangeConfig = othersExchangeConfig;
    }
}
