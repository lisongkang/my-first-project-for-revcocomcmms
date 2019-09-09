package com.maywide.biz.inter.service;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.TokenReturnInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.pojo.salaryCents.*;
import com.maywide.biz.inter.pojo.salaryCents.CancelShareReq;
import com.maywide.biz.inter.pojo.salaryCents.ShareRep;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.pojo.BeforehandRealDetailBO;
import com.maywide.biz.salary.repbo.*;
import com.maywide.biz.salary.reqbo.*;
import com.maywide.biz.salary.service.BaseWageService;
import com.maywide.biz.salary.service.BeforehandRealService;
import com.maywide.biz.salary.service.ExplicationConfigService;
import com.maywide.biz.salary.service.OthersKpiService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class SalaryCentsService {
    private static Logger log = LoggerFactory.getLogger(SalesCommissionService.class);
    @Autowired
    private BeforehandRealService beforehandRealService;
    @Autowired
    private ExplicationConfigService explicationConfigService;
    @Autowired
    private OthersKpiService othersKpiService;
    @Autowired
    private BaseWageService baseWageService;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private ParamService paramService;

    /**
     * 查询实积分
     * @param req
     * @return
     * @throws Exception
     */
    public RealRep queryReal(QueryRealReq req) throws Exception{
        RealRep result = new RealRep();
        req.setCycleid(req.getCycleid().replace("-",""));
        CentSumReq sumReq = new CentSumReq();
        sumReq.setCycleid(req.getCycleid());
        sumReq.setOperator(req.getOperator());
        BizGridInfo grid = getGrids();
        sumReq.setWhgridcode(grid.getGridcode());
        sumReq.setStatus("1"); //审核通过
        sumReq.setPagesize(req.getPagesize());
        sumReq.setCurrentPage(req.getCurrentPage());
        CentSumRep rep = beforehandRealService.queryCentSum(sumReq);
        result.setCode(Long.valueOf(rep.getRtcode()));
        result.setMessage(rep.getMessage());
        result.setCurrentPage(Integer.valueOf(req.getCurrentPage()));
        result.setPagesize(Integer.valueOf(req.getPagesize()));
        Double realNum = 0D;
        if(rep!=null && rep.getCentlist()!=null && rep.getCentlist().size()>0){
            String real = rep.getCentlist().get(0).getSrccents();
            if(StringUtils.isNotBlank(real)){
                realNum = realNum + Double.valueOf(real);
            }
            //合并调整积分
            String adjust = rep.getCentlist().get(0).getAdjustcents();
            if(StringUtils.isNotBlank(adjust) &&
                "Y".equals(req.getExistsMerge())){
                realNum = realNum + Double.valueOf(adjust);
            }
        }
        result.setTotalcents(realNum);
        result.setTotalnums(rep.getTotalnums());
        result.setData(rep.getCentlist());
        return result;
    }

    /**
     * 查询预积分
     * @param req
     * @return
     * @throws Exception
    RealRep */
    public BeforehandRep queryBeforehand(QueryBeforehandReq req) throws Exception{
        BeforehandRep result = new BeforehandRep();
        req.setCycleid(req.getCycleid().replace("-",""));
        QueryBeforehandRep rep = beforehandRealService.queryBeforehand(req);
        //过滤积分为0数据
        if(rep!=null && rep.getCentlist()!=null && rep.getCentlist().size()>0) {
            Iterator<QueryBeforehandRep.Detail> it = rep.getCentlist().iterator();
            while (it.hasNext()) {
                QueryBeforehandRep.Detail detail = it.next();
                if (StringUtils.isEmpty(detail.getCent()) || "0".equals(detail.getCent())) {
                    it.remove();
                }
            }
        }
        result.setCode(Long.valueOf(rep.getRtcode()));
        result.setMessage(rep.getMessage());
        result.setCurrentPage(Integer.valueOf(req.getCurrentPage()));
        result.setPagesize(Integer.valueOf(req.getPagesize()));
        result.setTotalcents(rep.getTotalcents());
        result.setTotalnums(rep.getTotalnums());
        result.setData(rep.getCentlist());
        return result;
    }

    /**
     * 查询分享积分
     * @param req
     * @return
     * @throws Exception
     */
    public ShareRep queryShare(QueryShareReq req) throws Exception{
        ShareRep result =new ShareRep();
        //分享状态无效数据
        List<QueryShareRep.Detail> removes = new ArrayList<QueryShareRep.Detail>();
        req.setCurrentPage("1");
        req.setPagesize(Integer.MAX_VALUE+"");
        req.setCycleid(req.getCycleid().replace("-",""));

        QueryShareRep shareRep = new QueryShareRep();
        shareRep.setSharelist(new ArrayList<QueryShareRep.Detail>());
        shareRep.setTotalcents(0D);
        shareRep.setTotalnums(0);

        //----------------查询我分享给别人数据--------------------
        if(StringUtils.isNotEmpty(req.getSendoper())){
            QueryShareReq sendoperReq = new QueryShareReq();
            BeanUtils.copyProperties(req,sendoperReq);
            sendoperReq.setAccoper(null);
            QueryShareRep rep = beforehandRealService.queryShare(sendoperReq);
            //合并分享积分 我分享给别人

            if("0".equals(rep.getRtcode()) && rep.getSharelist()!=null) {
                //我分享给别人的转为负数
                for (QueryShareRep.Detail detail : rep.getSharelist()) {
                    if(Double.valueOf(detail.getCent())!=0) {
                        Double cent = -Double.valueOf(detail.getCent());
                        detail.setCent(cent.toString());
                    }
                }
                shareRep.getSharelist().addAll(rep.getSharelist());
                //汇总总积分字段  我分享别人的转为负数
                Double repTotalcents = rep.getTotalcents()==0?0:-rep.getTotalcents();
                shareRep.setTotalcents(shareRep.getTotalcents()+repTotalcents);
                shareRep.setTotalnums(shareRep.getTotalnums()+rep.getTotalnums());
            }
        }

        //-----------------查询别人分享给我数据----------------------
        if(StringUtils.isNotEmpty(req.getAccoper())){
            //查询别人分享给我数据
            QueryShareReq accoperReq = new QueryShareReq();
            BeanUtils.copyProperties(req,accoperReq);
            accoperReq.setSendoper(null);
            QueryShareRep rep2 = beforehandRealService.queryShare(accoperReq);
            //合并分享积分  别人分享给我
            if("0".equals(rep2.getRtcode()) && rep2.getSharelist()!=null) {
                shareRep.getSharelist().addAll(rep2.getSharelist());
                shareRep.setTotalcents(shareRep.getTotalcents()+rep2.getTotalcents());
                shareRep.setTotalnums(shareRep.getTotalnums()+rep2.getTotalnums());
            }
        }
        //----------------排除掉状态无效数据--------------------------
        for (QueryShareRep.Detail detail : shareRep.getSharelist()) {
            //排除状态为无效数据
            if("1".equals(detail.getStatus())){
                shareRep.setTotalcents(shareRep.getTotalcents());
                shareRep.setTotalnums(shareRep.getTotalnums());
                removes.add(detail);
            }
        }
        if(removes.size()>0) {
            shareRep.getSharelist().removeAll(removes);
        }


        result.setCode(0L);
        result.setMessage(shareRep.getMessage());
        result.setTotalcents(shareRep.getTotalcents());
        result.setTotalnums(shareRep.getTotalnums());
        result.setData(shareRep.getSharelist());
        return result;
    }

    /**
     * 查询剔除积分
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo queryAdjustment(QueryAdjustmentReq req) throws Exception{
        TokenReturnInfo result =new TokenReturnInfo();
        req.setCycleid(req.getCycleid().replace("-",""));
        double totalcents = 0d;
        //查询调整积分
        req.setOptype("0");
        QueryAdjustmentRep rep1 = beforehandRealService.queryAdjustment(req);
        if(StringUtils.isNotEmpty(rep1.getTotalcents())){
            totalcents = totalcents + Double.valueOf(rep1.getTotalcents());
        }
        //查询新增积分
        req.setOptype("1");
        QueryAdjustmentRep rep2 = beforehandRealService.queryAdjustment(req);
        if(StringUtils.isNotEmpty(rep2.getTotalcents())){
            totalcents = totalcents + Double.valueOf(rep2.getTotalcents());
        }
        result.setCode(0L);
        result.setMessage("");
        result.setData(totalcents);
        return result;
    }

    /**
     * 查询积分排名
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo rankCent(RankCentReq req) throws Exception {
        TokenReturnInfo result = new TokenReturnInfo();
        req.setScycleid(req.getScycleid().replace("-",""));
        req.setEcycleid(req.getEcycleid().replace("-",""));
        req.setSorttype("0");//分公司排名
        RankCentRep rep = beforehandRealService.rankCent(req);
        req.setSorttype("1");//业务区排名
        RankCentRep rep2 = beforehandRealService.rankCent(req);
        List<RankCentRep.Detail> list = rep.getSortlist();
        List<RankCentRep.Detail> list2 = rep2.getSortlist();
        if(list!=null && list2!=null && list.size()>0 && list2.size()>0){
            result.setCode(Long.valueOf(rep.getRtcode()));
            result.setMessage("");
            JSONObject obj = new JSONObject();
            obj.put("cityorder",list.get(0).getOrder());
            obj.put("areaorder",list2.get(0).getOrder());
            result.setData(obj);
        }else{
            result.setCode(Long.valueOf(rep.getRtcode()));
            result.setMessage(rep.getMessage());
        }
        return result;
    }

    /**
     * 搜索用户
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo searchOperator(SalaryReq req) throws Exception {
        TokenReturnInfo result = new TokenReturnInfo();
        JSONArray ja = beforehandRealService.searchOperator(req.getSearch());
        result.setCode(Long.valueOf(0));
        result.setMessage("");
        result.setData(ja);
        return result;
    }

    /**
     * 分享积分
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo beforehandShare(ShareReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        String[] accopers = req.getAccoper().split(",");
        String[] accdepts =  req.getAccdept().split(",");
        String[] cents =  req.getCent().split(",");
        result.setCode(0L);
        result.setMessage("分享成功!");
        for (int i=0;i<accopers.length ;i++) {
            ShareReq sr = new ShareReq();
            BeanUtils.copyProperties(req,sr);
            sr.setAccoper(accopers[i]);
            sr.setAccdept(accdepts[i]);
            sr.setCent(cents[i]);
            com.maywide.biz.salary.repbo.ShareRep rep = beforehandRealService.beforehandShare(sr);
            if(!"0".equals(rep.getRtcode())){
                result.setCode(Long.valueOf(rep.getRtcode()));
                result.setMessage(rep.getMessage());
                break;
            }
        }
        result.setData("");
        return result;
    }
    /**
     * 取消分享积分
     * @param req
     * @return
     * @throws Exception
     */
    public TokenReturnInfo cancelShare(CancelShareReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        req.setCycleid(req.getCycleid().replace("-",""));
        CancelShareRep rep = beforehandRealService.cancelShare(req.getBeforehandSrcid(),
                req.getCycleid(),req.getOperid(),req.getDisreason());
        if(rep == null){
            result.setCode(-1L);
            result.setMessage("找不到分享积分，无法取消");
            return result;
        }
        result.setCode(Long.valueOf(rep.getRtcode()));
        result.setMessage(rep.getMessage());
        result.setData("");
        return result;
    }

    /**
     * 查询页面说明项
     * @return
     * @throws Exception
    */
    public TokenReturnInfo querySalaryConfig(SalaryReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        BizGridInfo grid = getGrids();
        JSONArray ja = explicationConfigService.queryByAreaid(req.getCity(),req.getAreaid(),req.getDateMonth(),grid.getId()+"");
        result.setCode(Long.valueOf(0));
        result.setMessage("");
        result.setData(ja);
        return result;
    }

    /**
     * 首页-实积分分组查询
     * @return
     * @throws Exception
     */
    public TokenReturnInfo othersKpiGroup(SalaryReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        req.setDateMonth(req.getDateMonth().replace("-",""));
//        BizGridInfo grid =getGrids();

        JSONArray ja = new JSONArray();
        if(StringUtils.isEmpty(req.getOperid()) || StringUtils.isEmpty(req.getDateMonth())){
            throw new Exception("网格人员id和月份不能为空!");
        }
        //--------------销售积分---------------
        JSONObject zsbusiJson = new JSONObject();
        CentSumReq csreq = new CentSumReq();
        csreq.setCycleid(req.getDateMonth());
        csreq.setOperator(req.getOperid());
        csreq.setGroupcode(SalaryConstants.OthersKpi.ZSBUSI);
//        csreq.setWhgridcode(grid.getGridcode());
        csreq.setStatus("1"); //审核通过
        csreq.setPagesize(Integer.MAX_VALUE+"");
        csreq.setCurrentPage("1");
        CentSumRep zsbusiRep = beforehandRealService.queryCentSum(csreq);
        Double zsbusiSrccents = 0D;
        Double zsbusiSharecents = 0D;
        if(zsbusiRep!=null && zsbusiRep.getCentlist()!=null && zsbusiRep.getCentlist().size()>0){
            for (CentSumRep.Detail detail : zsbusiRep.getCentlist()) {
                String srccents = StringUtils.isNotEmpty(detail.getSrccents())?
                        detail.getSrccents():"0";
                zsbusiSrccents = zsbusiSrccents + Double.valueOf(srccents);
                //是否有剔除积分，如果有则合并
                String adjust = detail.getAdjustcents();
                if(StringUtils.isNotBlank(adjust)){
                    zsbusiSrccents = zsbusiSrccents + Double.valueOf(adjust);
                }
                String share = detail.getSharecents();
                if(StringUtils.isNotEmpty(share)){
                    zsbusiSharecents = zsbusiSharecents + Double.valueOf(share);
                }
            }
        }
        zsbusiJson.put("srccents",zsbusiSrccents); //个人实积分
        zsbusiJson.put("sharecents",zsbusiSharecents);  //分享积分
        zsbusiJson.put("groupcode",SalaryConstants.OthersKpi.ZSBUSI);
        zsbusiJson.put("groupname","销售积分");
        ja.put(zsbusiJson);

        //---------------安装积分---------------
        JSONObject zssetupJson = new JSONObject();
        //查询个人实积分
        csreq.setGroupcode(SalaryConstants.OthersKpi.ZSSETUP);//安装积分
        CentSumRep zssetupRep = beforehandRealService.queryCentSum(csreq);
        Double zssetupSrccents = 0D;
        Double zssetupSharecents = 0D;
        if(zssetupRep!=null && zssetupRep.getCentlist()!=null && zssetupRep.getCentlist().size()>0){
            for (CentSumRep.Detail detail : zssetupRep.getCentlist()) {
                String srccents = StringUtils.isNotEmpty(detail.getSrccents())?
                        detail.getSrccents():"0";
                zssetupSrccents = zssetupSrccents + Double.valueOf(srccents);
                //是否有剔除积分，如果有则合并
                String adjust = detail.getAdjustcents();
                if(StringUtils.isNotBlank(adjust)){
                    zssetupSrccents = zssetupSrccents + Double.valueOf(adjust);
                }
                String share = detail.getSharecents();
                if(StringUtils.isNotBlank(share)){
                    zssetupSharecents = zssetupSharecents + Double.valueOf(share);
                }
            }
        }
        zssetupJson.put("srccents",zssetupSrccents); //个人实积分
        zssetupJson.put("sharecents",zssetupSharecents);  //分享积分
        zssetupJson.put("groupcode",SalaryConstants.OthersKpi.ZSSETUP);
        zssetupJson.put("groupname","安装积分");
        ja.put(zssetupJson);


        result.setCode(Long.valueOf(0));
        result.setMessage("");
        result.setData(ja);
        return result;
    }
    /**
     * 首页-预积分分组查询
     * @return
     * @throws Exception
     */
    public TokenReturnInfo preGroup(SalaryReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        req.setDateMonth(req.getDateMonth().replace("-",""));

        JSONArray ja = new JSONArray();
        if(StringUtils.isEmpty(req.getOperid()) || StringUtils.isEmpty(req.getDateMonth())){
            throw new Exception("网格人员id和月份不能为空!");
        }

        //--------------销售积分---------------
        //查询个人预积分汇总
        JSONObject zsbusiJson = new JSONObject();
        QueryBeforehandReq qbreq = new QueryBeforehandReq();
        qbreq.setCycleid(req.getDateMonth());
        qbreq.setOperator(req.getOperid());
        qbreq.setGroupcode(SalaryConstants.OthersKpi.ZSBUSI);
        qbreq.setStatus("Y");
        qbreq.setPagesize("1");
        qbreq.setCurrentPage("1");
        QueryBeforehandRep zsbusiRep = beforehandRealService.queryBeforehand(qbreq);
        //查询分享积分
        QueryShareReq qsreq = new QueryShareReq();
        qsreq.setCycleid(req.getDateMonth());
        qsreq.setSendoper(req.getOperid());
        qsreq.setAccoper(req.getOperid());
        qsreq.setGroupcode(SalaryConstants.OthersKpi.ZSBUSI);
        ShareRep zsbusisrep = queryShare(qsreq);

        Double zsbusiSrccents = 0D;
        Double zsbusiSharecents = 0D;
        if(zsbusiRep!=null && zsbusiRep.getTotalcents()!=null){
            zsbusiSrccents = zsbusiRep.getTotalcents();
        }
        if(zsbusisrep!=null && zsbusisrep.getTotalcents()!=null){
            zsbusiSharecents = zsbusisrep.getTotalcents();
        }
        zsbusiJson.put("srccents",zsbusiSrccents); //个人实积分
        zsbusiJson.put("sharecents",zsbusiSharecents);  //分享积分
        zsbusiJson.put("groupcode",SalaryConstants.OthersKpi.ZSBUSI);
        zsbusiJson.put("groupname","销售积分");
        ja.put(zsbusiJson);

        //---------------安装积分---------------
        JSONObject zssetupJson = new JSONObject();
        //查询个人预积分汇总
        qbreq.setGroupcode(SalaryConstants.OthersKpi.ZSSETUP);//安装积分
        QueryBeforehandRep zssetupRep = beforehandRealService.queryBeforehand(qbreq);
        //查询分享积分
        qsreq.setGroupcode(SalaryConstants.OthersKpi.ZSSETUP);//安装积分
        ShareRep zssetupsRep = queryShare(qsreq);
        Double zssetupSrccents = 0D;
        Double zssetupSharecents = 0D;
        if(zssetupRep!=null && zssetupRep.getTotalcents()!=null){
            zssetupSrccents = zssetupRep.getTotalcents();
        }
        if(zssetupsRep!=null && zssetupsRep.getTotalcents()!=null){
            zssetupSharecents = zssetupsRep.getTotalcents();
        }
        zssetupJson.put("srccents",zssetupSrccents); //个人实积分
        zssetupJson.put("sharecents",zssetupSharecents);  //分享积分
        zssetupJson.put("groupcode",SalaryConstants.OthersKpi.ZSSETUP);
        zssetupJson.put("groupname","安装积分");
        ja.put(zssetupJson);

        result.setCode(Long.valueOf(0));
        result.setMessage("");
        result.setData(ja);
        return result;
    }

    /**
     * 其他积分查询
     * @return
     * @throws Exception
     */
    public TokenReturnInfo othersKpi(SalaryReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        JSONArray obj = othersKpiService.othersKpi(req.getOperid(),req.getDateMonth());
        result.setCode(Long.valueOf(0));
        result.setMessage("");
        result.setData(obj);
        return result;
    }

    /**
     * 基本薪酬和运维薪酬
     * @return
     * @throws Exception
     */
    public TokenReturnInfo baseWage(SalaryReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        JSONArray ja = baseWageService.baseWage(req.getOperid(),req.getDateMonth());
        result.setCode(Long.valueOf(0));
        result.setMessage("");
        result.setData(ja);
        return result;
    }

    /**
     * 查询分组明细
     * @param req
     * @return
     * @throws Exception
     */
    public RealDetailRep othersKpiGroupDetail(OperatorDetailReq req) throws Exception{
        RealDetailRep result = new RealDetailRep();
        req.setCycleid(req.getCycleid().replace("-",""));

        List<BeforehandRealDetailBO> rep =  beforehandRealService.findDetail(req);
        List<BeforehandRealDetailBO> remvoes = new ArrayList<BeforehandRealDetailBO>();
        if(rep!=null && rep.size()>0){
            for (BeforehandRealDetailBO detail : rep) {
                //过滤积分为0的数据
                if((StringUtils.isEmpty(detail.getSrccents()) && StringUtils.isEmpty(detail.getAdjustcents())) ||
                        ("0".equals(detail.getSrccents()) && "0".equals(detail.getAdjustcents())) ){
                    remvoes.add(detail);
                    continue;
                }
                detail.setCents("0");
                if(StringUtils.isNotEmpty(detail.getSrccents())){
                    detail.setCents(detail.getSrccents());
                }
                if(StringUtils.isNotEmpty(detail.getAdjustcents())){
                    Double adjustcents = Double.valueOf(detail.getAdjustcents());
                    detail.setCents((Double.valueOf(detail.getCents())+adjustcents)+"");
                }
            }
            if(remvoes.size()>0) {
                rep.removeAll(remvoes);
            }
        }
        //输出
        result.setCode(0L);
        result.setMessage("");
        result.setData(rep);
        return result;
    }
    /**
     * 预积分分组
     * @return
     * @throws Exception
     */
    public TokenReturnInfo beforehandGroup(PreCentQryTotalReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        req.setCycleid(req.getCycleid().replace("-",""));
        PreCentQryTotalRep rep = beforehandRealService.beforehandGroup(req);
        if(rep!=null && rep.getScenelist()!=null && rep.getScenelist().size()>0){
            List<PreCentQryTotalRep.Detail> remvoes = new ArrayList<PreCentQryTotalRep.Detail>();
            for (PreCentQryTotalRep.Detail detail : rep.getScenelist()) {
                //过滤积分为0的数据
                if((StringUtils.isEmpty(detail.getTotalcents()) || "0".equals(detail.getTotalcents())) &&
                        (StringUtils.isEmpty(detail.getSrccents()) || "0".equals(detail.getSrccents()))){
                    remvoes.add(detail);
                    continue;
                }
                if(StringUtils.isNotEmpty(detail.getScene())) {
                    String sceneName = paramService.getMname(SalaryConstants.getSceneGcode(req.getGroupcode()), detail.getScene());
                    detail.setSceneName(sceneName);
                }else{
                    detail.setScene("OT");//如果为空的情况，则设置OT，在去调用列表明细的接口时传入这个字段
                    detail.setSceneName("其他");
                }
            }
            if(remvoes.size()>0) {
                rep.getScenelist().removeAll(remvoes);
            }
        }
        result.setCode(Long.valueOf(rep.getRtcode()));
        result.setMessage(rep.getMessage());
        result.setData(rep.getScenelist());
        return result;
    }
    /**
     * 实积分分组
     * @return
     * @throws Exception
     */
    public TokenReturnInfo realGroup(RealCentQryTotalReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        req.setCycleid(req.getCycleid().replace("-",""));
        RealCentQryTotalRep rep = beforehandRealService.realGroup(req);
        if(rep!=null && rep.getScenelist()!=null && rep.getScenelist().size()>0){
            List<RealCentQryTotalRep.Detail> remvoes = new ArrayList<RealCentQryTotalRep.Detail>();
            for (RealCentQryTotalRep.Detail detail : rep.getScenelist()) {
                //过滤积分为0的数据
                if((StringUtils.isEmpty(detail.getTotalcents()) || "0".equals(detail.getTotalcents())) &&
                        (StringUtils.isEmpty(detail.getSrccents()) || "0".equals(detail.getSrccents()))){
                    remvoes.add(detail);
                    continue;
                }
                if(StringUtils.isNotEmpty(detail.getAdjustcents())){
                    Double srccents = Double.valueOf(detail.getSrccents())+Double.valueOf(detail.getAdjustcents());
                    detail.setSrccents(srccents.toString());
                }
                if(StringUtils.isNotEmpty(detail.getScene())) {
                    String gcode = SalaryConstants.getSceneGcode(req.getGroupcode());
                    String sceneName = paramService.getMname(gcode,detail.getScene());
                    detail.setSceneName(sceneName);
                }else{
                    detail.setScene("OT");//如果为空的情况，则设置OT，在去调用列表明细的接口时传入这个字段
                    detail.setSceneName("其他");
                }
            }

            if(remvoes.size()>0) {
                rep.getScenelist().removeAll(remvoes);
            }
        }
        result.setCode(Long.valueOf(rep.getRtcode()));
        result.setMessage(rep.getMessage());
        result.setData(rep.getScenelist());
        return result;
    }

    /**
     * 获取积分用户数
     * @return
     */
    public TokenReturnInfo getSalaryUserNum(SalaryReq req) throws Exception {
        TokenReturnInfo result = new TokenReturnInfo();
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        BizGridInfo grid = getGrids();
        String sql="select a.stadate month ,a.whgridcode gridCode,a.whgridname gridName,a.kpiid flag ,b.kpiname " +
                "flagName,a.kpivalue flagSum,etl.code2name(a.city,'PRV_CITY') city from dm.dm_kpi_whgrid a,frntpc.TR_KPICODE_CFG " +
                "b where a.kpiid = b.kpiid and a.city =? and a.kpiid = 'S01001012021000' and a.stadate != " +
                "'NNN' and a.stadate =? and a.whgridcode =?  and a.areaid = 'NNN'" +
                "group by a.stadate,a.whgridcode,a.whgridname,a.kpiid,b.kpiname,a.kpivalue,a.city " +
                "order by a.stadate,a.whgridcode,a.whgridname,a.kpiid,b.kpiname,a.kpivalue,a.city";
       List<Object> list = SpringBeanUtil.getPersistentService().findObjectList(sql,loginInfo.getCity(),req.getDateMonth(),grid.getGridcode());
        result.setCode(Long.valueOf(0));
        result.setMessage("");
        if(list!=null && list.size()>0) {
            Object[] data = (Object[])list.get(0);
            result.setData(data[5]);
        }
        return result;
    }
    /**
     * 获取用户信息login
     * @return
     * @throws Exception
     */
     public TokenReturnInfo getTokenLoginInfo() throws Exception{
         TokenReturnInfo result = new TokenReturnInfo();
         LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
         result.setCode(Long.valueOf(0));
         result.setMessage("");
         result.setData(loginInfo);
        return result;
    }

    /**
     * 获取页面模块集合
     * @return
     * @throws Exception
    
    public TokenReturnInfo findPageModule(SalaryReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        if(req==null || req.getCity()==null){
            result.setCode(IErrorDefConstant.ERROR_PROPERTY_MISSING_CODE);
            result.setMessage("请求参数city不能为空!");
            return result;
        }
        String sql="select distinct id,module_type moduleType from SALARY_MODULE where status='0'" +
                " and hierarchy=1 and city=?";
        List<SalaryModule> modules = persistentService.find(sql,SalaryModule.class,req.getCity());
        result.setCode(Long.valueOf(0));
        result.setMessage("");
        result.setData(modules);
        return result;
    } */
 /**
     * 获取模块内容
     * @return
     * @throws Exception
     
    public TokenReturnInfo getModule(SalaryReq req) throws Exception{
        TokenReturnInfo result = new TokenReturnInfo();
        String sql="select distinct id,module_type moduleType from SALARY_MODULE where status='0' and hierarchy=1 and id=?";
        List<SalaryModule> modules = persistentService.find(sql,SalaryModule.class,req.getSearch());

        result.setCode(Long.valueOf(0));
        result.setMessage("");
        result.setData(modules);
        return result;
    }*/



    public BizGridInfo getGrids() throws Exception {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        Long operId = loginInfo.getOperid();

        StringBuffer sql = new StringBuffer();

        List paramList = new ArrayList();

        sql.append(" SELECT *  FROM (");
        sql.append(" SELECT t.gridid as id, ");
        sql.append("t.gridcode, ");
        sql.append("t.gridname, ");
        sql.append("t.gtype, ");
        sql.append("t.mnrid, ");
        sql.append("t.prio, ");
        sql.append("t.previd, ");
        sql.append("t.statid, ");
        sql.append("t.patchid, ");
        sql.append("t.memo ");
        sql.append(" FROM biz_grid_info t, biz_grid_manager m ");
        sql.append(" WHERE t.gridid=m.gridid  ");
        sql.append(" AND t.city = ? AND m.operid = ? AND ismain='Y'");
        paramList.add(loginInfo.getCity());
        paramList.add(operId);
        sql.append(" ) v ");

        persistentService.clear();
        List<BizGridInfo> gridlist = persistentService.find(sql.toString(), BizGridInfo.class, paramList.toArray());
        if (gridlist != null && gridlist.size() > 0) {
            return gridlist.get(0);
        }else{
            throw new BusinessException("查找不到当前登录地市关联的主网格!");
        }

    }
}
