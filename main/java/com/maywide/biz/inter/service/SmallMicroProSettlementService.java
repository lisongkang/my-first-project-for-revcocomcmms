package com.maywide.biz.inter.service;

import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.entity.*;
import com.maywide.biz.inter.pojo.constructionQuota.cityDivdeInfoBO;
import com.maywide.biz.inter.pojo.constructionQuota.constructionQuotaInfoBO;
import com.maywide.biz.inter.pojo.constructionQuota.constructionQuotaInterReq;
import com.maywide.biz.inter.pojo.constructionQuota.constructionQuotaInterResp;
import com.maywide.biz.inter.pojo.materiel.*;
import com.maywide.biz.inter.pojo.settlement.*;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmallMicroProSettlementService extends CommonService {
    private static Logger log = LoggerFactory.getLogger(SmallMicroProSettlementService.class);
    @Autowired
    private ParamService paramService;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private RuleService ruleService;

    private String IF_GO_NC_NPRICE = "IF_GO_NC_NPRICE";

    /**
     * 查找地市对应的施工定额信息方法
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public ReturnInfo queryConstructionQuotaInfo(constructionQuotaInterReq req,
                                            constructionQuotaInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getCity(), "匹配条件[city]不能为空！");

        //通过city 查找地市类别和定额比列设置
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT a.city,a.citydivde,a.quotaratio FROM biz_city_divde a WHERE 1=1 AND a.city = ? ");
        params.add(req.getCity());
        List<cityDivdeInfoBO> cityDivdeInfoBOS = getDAO().find(sqlBuffer.toString(),
                cityDivdeInfoBO.class, params.toArray());
        String citydivde = cityDivdeInfoBOS.get(0).getCitydivde();
        Double quotaratio = Double.valueOf(cityDivdeInfoBOS.get(0).getQuotaratio());
        //查找施工定额信息
        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        // 拼sql
        sql.append(" SELECT constid,newnumber,oldnumber,constname,constdetail,company, ");
        if(citydivde.equals("1")){
            sql.append(" oneprice * " + quotaratio + " AS constprice, ");
        }
        if(citydivde.equals("2")){
            sql.append(" twoprice * " + quotaratio + " AS constprice, ");
        }
        if(citydivde.equals("3")){
            sql.append(" threeprice * " + quotaratio + " AS constprice, ");
        }
        if(citydivde.equals("4")){
            sql.append(" fourprice * " + quotaratio + " AS constprice, ");
        }
        sql.append(" constcontent FROM construction_settlement_unit_price where 1=1  ");
        sql.append(" AND city in('*',?)");
        paramList.add(req.getCity());
        getDAO().clear();
        List<constructionQuotaInfoBO> constructionQuotaInfoBOList =getDAO().find(sql.toString(),
                constructionQuotaInfoBO.class, paramList.toArray());

        resp.setConstructionQuotaInfoBOList(constructionQuotaInfoBOList);
        return returnInfo;
    }


    /**
     * 小额工程物料块地市查找业务区接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queareaByCity(queareaBycityReq req, queareaBycityResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getCity(), "匹配条件[city]不能为空！");
        //查找物料业务区
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT city city,areaid corpcode,name corpname from prv_area where city= ? ");
        params.add(req.getCity());
        List<areaInfoBQ> areaInfoBQList = getDAO().find(sqlBuffer.toString(),
                areaInfoBQ.class, params.toArray());
        resp.setAreaInfoBQList(areaInfoBQList);
        return returnInfo;
    }


    /**
     * 关键字搜素物料大类
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queinvclassByName(queinvclassByNameReq req, queinvclassByNameResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getInvclassname(), "匹配关键字不能为空！");
        //查找物料业务区
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT invclassid,invclassname from gdxegc_invclassname where invclassname like ? ");
        String invclassname =  "%" + req.getInvclassname() + "%";
        params.add(invclassname);
        List<invclassInfoBQ> invclassInfoBQList = getDAO().find(sqlBuffer.toString(),
                invclassInfoBQ.class, params.toArray());
        resp.setInvclassInfoBQList(invclassInfoBQList);
        return returnInfo;

    }

    /**
     * 本地查找物料ID
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queinvcode(queinvcodeReq req,queinvcodeResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getCorpcode(),"业务区编号不能为空");
        CheckUtils.checkEmpty(req.getInvclassid(),"物料大类id不能为空");
        CheckUtils.checkEmpty(req.getInvname(),"搜索关键字不能为空");

        // 转换一下cmms 业务区和NC物料库业务区的编码
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT coalesce(corpcode,'') as corpcode from gdxegc_cityarea where cmmsareaid= ? ");
        paramsTemp.add(req.getCorpcode());
        String corpcode =  getDAO().findUniqueObject(sqlBufferTemp.toString(),paramsTemp.toArray()).toString();
        if(corpcode== null && corpcode.equals("")){
            CheckUtils.checkNull(null, "未找到本地业务区与NC库对应的业务区");
        }
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select DISTINCT(invcode), corpcode,corpname, invname,invspec,invtype,measname from gdxegc_invview where 1 =1 ");
        sqlBuffer.append(" AND corpcode  = '"+corpcode+"'");
        sqlBuffer.append(" AND invclassid = '"+req.getInvclassid()+ "'");
        sqlBuffer.append(" AND invname like '%").append(req.getInvname()).append("%'");
        List<invcodeInfoBQ> invcodeInfoBQList = getDAO().find(sqlBuffer.toString(),
                invcodeInfoBQ.class, params.toArray());
        resp.setInvcodeInfoBQList(invcodeInfoBQList);
        return returnInfo;
    }

    /**
     * 查找物料实时单价
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queNprice(queNpriceReq req,queNpriceResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getCorpcode(),"业务区编号不能为空");
        CheckUtils.checkEmpty(req.getInvcode(),"搜索关键字不能为空");

        PersistentService ncPersistentService = SpringBeanUtil.getNcPersistentService();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT a.nprice FROM ( ");
        sqlBuffer.append("select decode(nprice,0,'0.00',trim(to_char(nprice,'99999999999999.99'))) as nprice from gdgdcs.gdxegc_invview  where 1 =1 ");
        sqlBuffer.append(" AND corpcode  = '"+req.getCorpcode()+"'");
        sqlBuffer.append(" AND invcode = '"+req.getInvcode()+ "'");
        sqlBuffer.append(" ORDER BY dbilldate desc) a WHERE rownum=1 ");
        try {
            Rule rule = ruleService.getRule("*", IF_GO_NC_NPRICE);
            String isncnprice = rule.getValue();
            if(isncnprice.equals("Y")){
                String nprice = ncPersistentService.findUniqueObject(sqlBuffer.toString()).toString();
                resp.setNprice(nprice);
            }
            else if(isncnprice.equals("N")){
                StringBuffer sqlBufferhost = new StringBuffer();
                sqlBufferhost.append("select ROUND(nprice,2) from gdxegc_invview  where 1 =1 ");
                sqlBufferhost.append(" AND corpcode  = '"+req.getCorpcode()+"'");
                sqlBufferhost.append(" AND invcode = '"+req.getInvcode()+ "'");
                sqlBufferhost.append(" ORDER BY dbilldate desc LIMIT 1  ");
                String nprice = getDAO().findUniqueObject(sqlBufferhost.toString()).toString();
                resp.setNprice(nprice);
            }
            else {
                returnInfo.setMessage("请检查获取单价规则是否配置");
            }
        }catch (Exception e){
            log.error("获取单价异常，请检查NC数据库是否正常");
            returnInfo.setMessage("获取单价异常，请稍后重试");
        }
        return returnInfo;
    }


    /**
     * 小额工程申清单结单共用接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo statementApplication(statementApplicationReq req, statementApplicationResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        String time = DateUtils.formatTimeNow();
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(String.valueOf(req.getProid()),"申请单对象不能为空");
        BizApplication bizApplication = new BizApplication();
        bizApplication.setId(req.getProid());
        if(req.getBizApplicationDistributionList() == null){
            CheckUtils.checkNull(req.getBizApplicationMaterielList(),"物料不能为空");
            CheckUtils.checkNull(req.getBizApplicationConstructionList(),"施工对象不能为空");
            bizApplication.setProcost(req.getProcost());
            bizApplication.setConstcost(req.getConstcost());
            bizApplication.setApplyacceptance(req.getApplyAcceptance());
            bizApplication.setStarttime(time);
            //判斷是否超過預算打回重新審核
            StringBuffer sqlTenmp = new StringBuffer();
            sqlTenmp.append(" select procost from biz_application where proid  = '"+req.getProid()+"'");
            String procostTemp = getDAO().findUniqueObject(sqlTenmp.toString()).toString();
            Double procostTempnew = Double.parseDouble(procostTemp);
            CheckUtils.checkEmpty(procostTemp,"请填写施工预算");
            if(Double.parseDouble(req.getProcost()) > procostTempnew){
                CheckUtils.checkNull(null, "施工费朝超过预算，请退回重新审核");
            }
            //计算总价
            Double procost = Double.parseDouble(bizApplication.getProcost());
            Double constcost = Double.parseDouble(bizApplication.getConstcost());
            Double totalprice = procost + constcost;
            bizApplication.setTotalprice(totalprice);
            //走提交验收
            bizApplication.setProstatus("8");
            //插入物料流水表
            //先清理以前的数据，防止重复提交导致数据重复
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM biz_application_materiel where proid  = '"+req.getProid()+"' ");
            persistentService.clear();
            persistentService.executeSql(sql.toString());
            if(req.getBizApplicationMaterielList() != null){
                List<BizApplicationMateriel> bizApplicationMaterielList = req.getBizApplicationMaterielList();
                for (BizApplicationMateriel BizApplicationMateriel : bizApplicationMaterielList) {
                    BizApplicationMateriel.setProid(String.valueOf(req.getProid()));
                    BizApplicationMateriel.setCity(req.getCity());
                    String edittime = DateUtils.formatTimeNow();
                    BizApplicationMateriel.setOperatetime(edittime);
                    getDAO().saveOrUpdate(BizApplicationMateriel);
                }
            }
            //先清理以前的数据，防止重复提交导致数据重复
            StringBuffer sqlconst = new StringBuffer();
            sqlconst.append("DELETE FROM biz_application_construction where proid  = '"+req.getProid()+"' ");
            persistentService.clear();
            persistentService.executeSql(sqlconst.toString());
            //插入施工服务流水表
            if(req.getBizApplicationConstructionList()!= null){

                List<BizApplicationConstruction> bizApplicationConstructionList = req.getBizApplicationConstructionList();
                for (BizApplicationConstruction BizApplicationConstruction : bizApplicationConstructionList
                ) {
                    String edittime = DateUtils.formatTimeNow();
                    BizApplicationConstruction.setOperatetime(edittime);
                    BizApplicationConstruction.setProid(String.valueOf(req.getProid()));
                    getDAO().saveOrUpdate(BizApplicationConstruction);
                }
                resp.setResult("success");
            }
            //插入完工验收通过流水
            if(req.getApplyAcceptance()!= null){
                //插入流水轨迹表
                String operationtype = "完工验收";
                String operationresult = "1";
                this.addBizApplicationTrack(String.valueOf(req.getProid()),req.getApplyAcceptance(),loginInfo.getOperid(),operationtype,operationresult);

            }

        }else {
            //先清理以前的数据，防止重复提交导致数据重复
            StringBuffer sqlconst = new StringBuffer();
            sqlconst.append("DELETE FROM biz_application_distribution where proid  = '"+req.getProid()+"' ");
            persistentService.clear();
            persistentService.executeSql(sqlconst.toString());
            //走结算
            bizApplication.setProstatus("7");
            StringBuffer constructor = new StringBuffer();
            List<BizApplicationDistribution> bizApplicationDistributionList = req.getBizApplicationDistributionList();
            for (BizApplicationDistribution BizApplicationDistribution : bizApplicationDistributionList
                 ) {
                String edittime = DateUtils.formatTimeNow();
                BizApplicationDistribution.setOperatetime(edittime);
                BizApplicationDistribution.setProid(String.valueOf(req.getProid()));
                constructor.append(BizApplicationDistribution.getConstructorname() + ",");
                getDAO().clear();
                getDAO().saveOrUpdate(BizApplicationDistribution);
            }
            bizApplication.setConstructors(constructor.toString());
            bizApplication.setEndtime(time);
            resp.setResult("success");
            //插入流水轨迹表
            String operationtype = "工程结算";
            String operationresult = "1";
            this.addBizApplicationTrack(String.valueOf(req.getProid()),"",loginInfo.getOperid(),operationtype,operationresult);
        }
        getDAO().update(bizApplication);
        return returnInfo;
    }



    /**
     * 超額退回審核接口 Excess Return Audit
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo excessReturnAudit(ExcessAuditReq req, statementApplicationResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(String.valueOf(req.getId()),"申请单对象不能为空");
        BizApplication bizApplication = new BizApplication();
        bizApplication.setId(req.getId());

        CheckUtils.checkNull(req.getBizApplicationConstructionList(),"施工对象不能为空");
        bizApplication.setProcost(req.getProcost());
        bizApplication.setConstcost(req.getConstcost());
        bizApplication.setApplyrecallopinion(req.getApplyrecallopinion());
        //计算总价
        Double procost = Double.parseDouble(bizApplication.getProcost());
        Double constcost = Double.parseDouble(bizApplication.getConstcost());
        Double totalprice = procost + constcost;
        bizApplication.setTotalprice(totalprice);
        //插入物料流水表
        //先清理以前的数据，防止重复提交导致数据重复
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM biz_application_materiel where proid  = '"+req.getId()+"' ");
        persistentService.clear();
        persistentService.executeSql(sql.toString());
        if(req.getBizApplicationMaterielList() != null){
                List<BizApplicationMateriel> bizApplicationMaterielList = req.getBizApplicationMaterielList();
                for (BizApplicationMateriel BizApplicationMateriel : bizApplicationMaterielList) {
                    String edittime = DateUtils.formatTimeNow();
                    BizApplicationMateriel.setOperatetime(edittime);
                    BizApplicationMateriel.setProid(String.valueOf(req.getId()));
                    BizApplicationMateriel.setCity(req.getCity());
                    getDAO().saveOrUpdate(BizApplicationMateriel);
                }
            resp.setResult("success");
        }
            //先清理以前的数据，防止重复提交导致数据重复
            StringBuffer sqlconst = new StringBuffer();
            sqlconst.append("DELETE FROM biz_application_construction where proid  = '"+req.getId()+"' ");
            persistentService.clear();
            persistentService.executeSql(sqlconst.toString());
            //插入施工服务流水表
            if(req.getBizApplicationConstructionList()!= null){

                List<BizApplicationConstruction> bizApplicationConstructionList = req.getBizApplicationConstructionList();
                for (BizApplicationConstruction BizApplicationConstruction : bizApplicationConstructionList
                ) {
                    String edittime = DateUtils.formatTimeNow();
                    BizApplicationConstruction.setOperatetime(edittime);
                    BizApplicationConstruction.setProid(String.valueOf(req.getId()));
                    getDAO().saveOrUpdate(BizApplicationConstruction);
                }
                resp.setResult("success");
            }
        //走超额退回审核
        bizApplication.setProstatus("9");
        getDAO().update(bizApplication);
        //插入完工超预算流水
        if(req.getApplyrecallopinion()!= null){
            //插入流水轨迹表
            String operationtype = "完工验收超预算";
            String operationresult = "0";
            this.addBizApplicationTrack(String.valueOf(req.getId()),req.getApplyrecallopinion(),loginInfo.getOperid(),operationtype,operationresult);

        }
        return returnInfo;
    }

    public void addBizApplicationTrack(String proid,String opinion,Long approveid,String operationtype,String operationresult) throws Exception{
        BizApplicationTrack bizApplicationTrack = new BizApplicationTrack();
        String subtime = DateUtils.formatTimeNow();
        bizApplicationTrack.setSubtime(subtime);
        bizApplicationTrack.setApproveid(approveid);
        bizApplicationTrack.setOperationresult(operationresult);
        bizApplicationTrack.setProid(proid);
        bizApplicationTrack.setOpinion(opinion);
        bizApplicationTrack.setOperationtype(operationtype);
        getDAO().saveOrUpdate(bizApplicationTrack);

    }

}
