


package com.maywide.biz.salary.web.action;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.entity.OthersKpi;
import com.maywide.biz.salary.entity.OthersKpiAudit;
import com.maywide.biz.salary.pojo.OthersKpiAuditBO;
import com.maywide.biz.salary.service.OthersKpiAuditService;
import com.maywide.biz.salary.service.OthersKpiService;
import com.maywide.biz.salary.service.OthersKpiTextConfigService;
import com.maywide.core.cons.Constant;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
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
public class OthersKpiAuditController extends SalaryController<OthersKpiAudit, Long> {

    @Autowired
    private OthersKpiAuditService othersKpiAuditService;
    @Autowired
    private OthersKpiService othersKpiService;

    private OthersKpiAudit othersKpiAudit;  //搜索影射对象

    @Override
    protected BaseService<OthersKpiAudit, Long> getEntityService() {
        return othersKpiAuditService;
    }

    /**
     * 分页搜索
     */
    public HttpHeaders findByPage() {
        List<OthersKpiAuditBO> list = new ArrayList<OthersKpiAuditBO>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            othersKpiAudit.setAuditUser(loginInfo.getOperid());
//            othersKpiAudit.setStatus(SalaryConstants.OthersKpiAudit.AUDIT_STAUTS);
            setModel(othersKpiAuditService.findByPage(othersKpiAudit, pageable));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(new PageImpl<OthersKpiAuditBO>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }
    /**
     * 详细页
     * @return
     */
    public HttpHeaders edit(){
        OthersKpi kpi = othersKpiService.findOne(bindingEntity.getOthersKpiId());
        bindingEntity.addExtraAttribute("areaid", kpi.getAreaid());
        bindingEntity.addExtraAttribute("city", kpi.getCity());
        return buildDefaultHttpHeaders("inputBasic");
    }
    public HttpHeaders doSave(){
        try {
            List<OthersKpiAudit> list = new ArrayList<OthersKpiAudit>();
            list.add(bindingEntity);
            othersKpiAuditService.audit(list,bindingEntity.getStatus());
            setModel(OperationResult.buildSuccessResult("提交成功", bindingEntity));
        }catch (Exception e){
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("提交异常,"+e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }

    public HttpHeaders batchAudit(){
        try {
            Collection<OthersKpiAudit> entities =  getEntitiesByParameterIds();
            String status = getParameter("status");
            if(StringUtils.isEmpty(status)){
                throw new Exception("状态不能为空!");
            }
            othersKpiAuditService.audit((List<OthersKpiAudit>)entities,status);
            setModel(OperationResult.buildSuccessResult("成功提交所选选取记录:" + entities.size() + "条"));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("提交失败:"+e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }

    public Map<String, String> getStatusMap(){
        Map<String, String> statusMap =  new LinkedHashMap();
        statusMap.putAll(getParamByGcode("SALARY-OTHERS-KPI-AUDIT-STATUS",null));
        return statusMap;
    }

    public OthersKpiAudit getOthersKpiAudit() {
        return othersKpiAudit;
    }

    public void setOthersKpiAudit(OthersKpiAudit othersKpiAudit) {
        this.othersKpiAudit = othersKpiAudit;
    }
}
