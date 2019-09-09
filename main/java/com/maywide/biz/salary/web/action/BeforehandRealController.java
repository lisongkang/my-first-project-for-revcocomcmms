


package com.maywide.biz.salary.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.salary.reqbo.OperatorDetailReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.salary.pojo.BeforehandRealBO;
import com.maywide.biz.salary.pojo.BeforehandRealDetailBO;
import com.maywide.biz.salary.repbo.AdjustmentRep;
import com.maywide.biz.salary.reqbo.AdjustmentReq;
import com.maywide.biz.salary.service.BeforehandRealAuditService;
import com.maywide.biz.salary.service.BeforehandRealService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.web.SimpleController;
import com.maywide.core.web.view.OperationResult;

/**
 * <p>
 * 预转实管理
 * <p>

 */
public class BeforehandRealController extends SimpleController {

    @Autowired
    private BeforehandRealService beforehandRealService;
    @Autowired
    private BeforehandRealAuditService beforehandRealAuditService;

    private BeforehandRealBO beforehandRealBO;

    private AdjustmentReq adjustment;

    /**
     * 分页搜索
     */
    public HttpHeaders findByPage() {
        List<BeforehandRealBO> list = new ArrayList<BeforehandRealBO>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            beforehandRealBO.setCity(loginInfo.getCity());

            if(StringUtils.isEmpty(beforehandRealBO.getGrid())){
                setModel(OperationResult.buildFailureResult("查询失败，网格编码不能为空!"));
                return buildDefaultHttpHeaders();
            }
            setModel(beforehandRealService.findByPage(beforehandRealBO, pageable));

        } catch (Exception e) {
            e.printStackTrace();
            setModel(new PageImpl<BeforehandRealBO>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }
    /**
     * 明细
     */
    public HttpHeaders findDetail() {
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try {
            OperatorDetailReq req = new OperatorDetailReq();
            req.setCycleid(beforehandRealBO.getCycleid());
            req.setOperator(beforehandRealBO.getOperid());
            req.setStatus(beforehandRealBO.getStatus());

          List<BeforehandRealDetailBO> bos = beforehandRealService.findDetail(req);
            PageImpl<BeforehandRealDetailBO> pageResult = new PageImpl<BeforehandRealDetailBO>(bos, pageable, bos.size());
            setModel(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            setModel(new PageImpl<BeforehandRealDetailBO>(new ArrayList<BeforehandRealDetailBO>(), pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }
    /**
     * 积分调整
     */
    public HttpHeaders updateSrccents() {
        try {
            adjustment.setOptype("0");
            adjustment.setOperator(AuthContextHolder.getLoginInfo().getOperid()+"");
            if(StringUtils.isEmpty(adjustment.getSrcid())){
                adjustment.setOptype("1");
            }
            AdjustmentRep rep = beforehandRealService.adjustment(adjustment);
            if(!"0".equals(rep.getRtcode())){
               throw new Exception(rep.getMessage());
            }
            setModel(OperationResult.buildSuccessResult("保存操作成功", rep));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("保存操作失败:"+e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }
    /**
     * 新增或者修改
     *
     * @return
     */
    public HttpHeaders doSave() {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        try {

//            setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("保存操作失败,"+e.getMessage()));
        }

        return buildDefaultHttpHeaders();
    }



    public HttpHeaders batchAudit(){
        try {
//            Collection<OthersKpi> entities =  getEntitiesByParameterIds();
//            String auditUser = getParameter("auditUser");
//            if(StringUtils.isEmpty(auditUser)){
//                throw new Exception("审核人不能为空!");
//            }
//            othersKpiAuditService.batchAudit((List<OthersKpi>)entities,auditUser);
//            setModel(OperationResult.buildSuccessResult("成功提交所选选取记录:" + entities.size() + "条"));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("提交失败:"+e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }

    public HttpHeaders inputTabs() {
        return buildDefaultHttpHeaders("inputTabs");
    }

    public HttpHeaders edit(){
        String city=getParameter("city");
        String areaid=getParameter("areaid");
        String grid=getParameter("grid");
        String operid=getParameter("operid");
        String cycleid=getParameter("cycleid");
        String status=getParameter("status");
        beforehandRealBO = new BeforehandRealBO(city,areaid,grid,cycleid,operid,status);
        return buildDefaultHttpHeaders("inputBasic");
    }
    @MetaData(value = "表格数据编辑校验规则")
    public HttpHeaders buildValidateRules() {
        setModel(new HashMap<String,Object>());
        return new DefaultHttpHeaders();
    }
    public Map<String, String> getCityMap() throws Exception{
        ParamService paramService = SpringContextHolder.getBean(ParamService.class);
        Map<String, String> cityMap =  new LinkedHashMap();
        List<PrvSysparam> params =  paramService.getData("PRV_CITY");
        for(PrvSysparam param :params){
            cityMap.put(param.getMcode(), param.getMname());
        }
        return cityMap;
    }

    public Map<String, String> getAreaMap() throws Exception{
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        ParamService paramService = SpringContextHolder.getBean(ParamService.class);
        Map<String, String> map =  new LinkedHashMap();
        List<PrvSysparam> params =  paramService.getData("PRV_AREA_BY_CITY",loginInfo.getCity(),null,null);
        for(PrvSysparam param :params){
            map.put(param.getMcode(), param.getMname());
        }
        return map;
    }

    public Map<String, String> getOperatorMap() throws Exception{
        ParamService paramService = SpringContextHolder.getBean(ParamService.class);
        Map<String, String> map =  new LinkedHashMap();
        List<PrvSysparam> params =  paramService.getData("PRV_OPERATOR",null,null,null);
        for(PrvSysparam param :params){
            map.put(param.getMcode(), param.getMname());
        }
        return map;
    }
    public Map<String, String> getAuditMap() throws Exception{
        Map<String,String> map = new HashMap<String,String>();
        List<PrvOperator> list = beforehandRealAuditService.findAduitOperator();
        for (PrvOperator prvOperator : list) {
            map.put(prvOperator.getId().toString(),prvOperator.getName());
        }
        return map;
    }
    public BeforehandRealBO getBeforehandRealBO() {
        return beforehandRealBO;
    }

    public void setBeforehandRealBO(BeforehandRealBO beforehandRealBO) {
        this.beforehandRealBO = beforehandRealBO;
    }

    public AdjustmentReq getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(AdjustmentReq adjustment) {
        this.adjustment = adjustment;
    }
}
