package com.maywide.biz.inter.service;

import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.pojo.dataReport.GridInfoResp;
import com.maywide.biz.inter.pojo.queEarlyWarLoss.*;
import com.maywide.biz.inter.pojo.queMarketingComment.*;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;
import com.maywide.test.InterTest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/19 0001.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MarketingPilotService extends CommonService {
    private static Logger log = LoggerFactory.getLogger(MarketingPilotService.class);
    @Autowired
    private ParamService paramService;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private InterPrdDataService dataService;

    /**
     * 查詢最近一条评论和总数
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queRecentCommentOrCount(QueReCommOrCountReq req, QueReCommOrCountResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        Double average = 0.00;
        int count = 0;
        List<CommentInfo> commentInfoList = getCommentInfo(req,loginInfo);
        if(commentInfoList!= null){
            count= (commentInfoList==null ? 0 : commentInfoList.size());
            for (CommentInfo commentInfo:commentInfoList
            ) {
                average = average + Double.parseDouble(commentInfo.getCommentTotal());
            }
            DecimalFormat df   = new DecimalFormat("######0.00");
            average = Double.parseDouble(df.format((Double)average/count));
            CommentInfo commentInfo = commentInfoList.get(0);
            resp.setCommentInfo(commentInfo);
        }
        resp.setCount(count);
        resp.setAverage(average);
        return returnInfo;
    }

    /**
     * 查询操作员所有评论集合分页
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queAllComment(QueAllCommentReq req, QueAllCommentResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        Page page = new Page();
        page.setPageSize(10);
        if (StringUtils.isNotBlank(req.getPagesize())) {
            page.setPageSize(Integer.valueOf(req.getPagesize()));
        }
        if (StringUtils.isNotBlank(req.getCurrentPage())) {
            page.setPageNo(Integer.valueOf(req.getCurrentPage()));
        }
        page = getPageCommentInfo(req,loginInfo,page);
        List<CommentInfo> commentInfoList = page.getResult();

        resp.setCommentInfoList(commentInfoList);

        resp.setPagesize(BeanUtil.object2String(page.getPageSize()));
        resp.setCurrentPage(BeanUtil.object2String(page.getCntPageNo()));
        resp.setTotalRecords(BeanUtil.object2String(page.getTotalCount()));
        //求操作员所有平均分
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT coalesce(AVG(comment_total),0) average from visit_comment_record where operid = ?  AND send_status = ?  GROUP BY operid ");
        params.add(loginInfo.getOperid());
        params.add("2");
        Double average = null;
        try {
            average = (Double)getDAO().findUniqueObject(sqlBuffer.toString(),params.toArray());
            if(average == null){
                average = 0.00;
            }
        }catch (Exception e){
            log.info("查询异常");
        }finally {
            resp.setAverage(average);
        }
        return returnInfo;
    }


    public List<CommentInfo> getCommentInfo(QueReCommOrCountReq req,LoginInfo loginInfo) throws Exception{
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT a.comment_total commentTotal,a.custid custid,a.cust_name custName a.visit_method as visitMethod,a.mobile as mobile,a.comment_suggest as commentSuggest,DATE_FORMAT(a.comment_time, '%Y-%m-%d %H:%i:%S') as commentTime  from visit_comment_record a where 1 = 1 ");
        if (null != req.getCity() && !"".equals(req.getCity())) {
            sqlBuffer.append(" AND a.city = ? ");
            params.add(req.getCity());
        }
        if (null != req.getCustName() && !"".equals(req.getCustName())) {
            sqlBuffer.append(" AND a.cust_name like ?  ");
            params.add("%" + req.getCustName()+ "%");
        }
        sqlBuffer.append(" AND a.operid = ? ");
        if(req.getOperid()!= null &&!"".equals(req.getOperid())){
            params.add(req.getOperid());
        }else {
            params.add(loginInfo.getOperid());
        }
        if (null != req.getVisitMethod() && !"".equals(req.getVisitMethod())) {
            sqlBuffer.append(" AND a.visit_method = ? ");
            params.add(req.getVisitMethod());
        }
        if(req.getStime()!= null && req.getEtime()!= null){
            sqlBuffer.append(" AND a.comment_time >= ? and a.comment_time <= ? ");
            params.add(req.getStime());
            params.add(req.getEtime());
        }
        sqlBuffer.append(" AND send_status = 2 ");
        sqlBuffer.append(" ORDER BY comment_time desc ");
        getDAO().clear();
        List<CommentInfo> commentInfoList = getDAO().find(sqlBuffer.toString(),
                CommentInfo.class, params.toArray());
        return commentInfoList;
    }



    public Page getPageCommentInfo(QueAllCommentReq req,LoginInfo loginInfo,Page page) throws Exception{
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT a.comment_total commentTotal,a.custid custid,a.cust_name as custName,a.visit_method as visitMethod,a.mobile as mobile,a.comment_suggest as commentSuggest,DATE_FORMAT(a.comment_time, '%Y-%m-%d %H:%i:%S') as commentTime   from visit_comment_record a where 1 = 1 ");
        if (null != req.getCity() && !"".equals(req.getCity())) {
            sqlBuffer.append(" AND a.city = ? ");
            params.add(req.getCity());
        }
        if (null != req.getCustName() && !"".equals(req.getCustName())) {
            sqlBuffer.append(" AND a.cust_name  like ? ");
            params.add("%" + req.getCustName()+ "%");
        }
        sqlBuffer.append(" AND a.operid = ? ");
        if(req.getOperid()!= null &&!"".equals(req.getOperid())){
            params.add(req.getOperid());
        }else {
            params.add(loginInfo.getOperid());
        }
        if (null != req.getVisitMethod() && !"".equals(req.getVisitMethod())) {
            sqlBuffer.append(" AND a.visit_method = ? ");
            params.add(req.getVisitMethod());
        }
        if(req.getDays() == 1){
            sqlBuffer.append(" AND DATE_SUB(CURDATE(), INTERVAL 1 DAY) <= date(a.comment_time) ");
        }else if(req.getDays() == 3){
            sqlBuffer.append(" AND  DATE_SUB(CURDATE(), INTERVAL 3 DAY) <= date(a.comment_time) ");
        }else if(req.getDays() == 7){
            sqlBuffer.append(" AND  DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(a.comment_time)");
        }else if(req.getDays() == 30){
            sqlBuffer.append(" AND  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(a.comment_time)");
        }
        if(req.getStime()!= null && req.getEtime()!= null){
            sqlBuffer.append(" AND a.comment_time >= ? and a.comment_time <= ? ");
            params.add(req.getStime());
            params.add(req.getEtime());
        }
        sqlBuffer.append(" AND send_status = 2 ");
        sqlBuffer.append(" ORDER BY comment_time desc ");
        getDAO().clear();
        page = getDAO().find(page, sqlBuffer.toString(), CommentInfo.class, params.toArray());

        return page;
    }


    /**
     * 查询商品系列或者商品 指引和卖点
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queGoodsSellPointGuide(QueGoodsSellPointReq req, QueGoodsSellPointResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

//        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
//      	LoginInfo loginInfo = InterTest.getGridTestLoginInfo();
//        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getSeriesId(),"请求系列或者商品不能为空");
        CheckUtils.checkEmpty(req.getType(),"请选择指引项是系列还是商品");
        //开始查数据
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT series_id seriesId,sellpoint_show sellpointShow,sellpoint_imgpath sellpointImgpath,guide_show ");
        sqlBuffer.append(" guideShow,guide_imgpath guideImgpath,other other from goods_sellpoint_guide ");
        sqlBuffer.append(" where series_id = ? and status = ? and type = ? ");
        params.add(req.getSeriesId());
        params.add("1");
        params.add(req.getType());
        List<GoodsSellPointInfo> goodsSellPointInfoList = getDAO().find(sqlBuffer.toString(),
                GoodsSellPointInfo.class,params.toArray());
        for (GoodsSellPointInfo goodsSellPointInfo:goodsSellPointInfoList
             ) {
            if(goodsSellPointInfo.getSellpointShow() != null && !"".equals(goodsSellPointInfo.getSellpointShow())){
                String[] sellpointShow = goodsSellPointInfo.getSellpointShow().split("\n");
                List<String> sellpointShowList = new ArrayList<String>();
                for (String a:sellpointShow
                ) {
                    sellpointShowList.add(a);
                }
                goodsSellPointInfo.setSellpointShowList(sellpointShowList);
            }
            if(goodsSellPointInfo.getGuideShow()!= null && !"".equals(goodsSellPointInfo.getGuideShow())){
                String[] guideShow = goodsSellPointInfo.getGuideShow().split("\n");
                List<String> guideShowList  = new ArrayList<String>();
                for (String a:guideShow
                ) {
                    guideShowList.add(a);
                }
                goodsSellPointInfo.setGuideShowList(guideShowList);
            }
        }
        resp.setGoodsSellPointInfoList(goodsSellPointInfoList);
        return returnInfo;
    }

    /**
     * 中山试点营销流失预警查询
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queEarlyWarnLos(QueEarlyLossReq req, QueEarlyLossResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);

        req.setPageSize(100);
        //开始查数据
        //查询配置表  动态加载客户群
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT * from  prv_sysparam where gcode = ?  ORDER BY sort desc");
        paramsTemp.add("QUE_EARLYWARNLOS_CUSTOMER");
        List<PrvSysparam> warnLosPrvSysparamList = getDAO().find(sqlBufferTemp.toString(),PrvSysparam.class,paramsTemp.toArray());

        CheckUtils.checkNull(warnLosPrvSysparamList,"没有配置显示的流失预警客户群");
        //加业务区和网格权限
        List<Object> paramsJurDct = new ArrayList();
        StringBuffer sqlJurDct = new StringBuffer();
        sqlJurDct.append(" SELECT mnrid,gridid,operid,ismain,updatetime,memo,iscgrid FROM biz_grid_manager where operid = ? and iscgrid = ?  ");
        paramsJurDct.add(loginInfo.getOperid());
        paramsJurDct.add("Y");
        List<BizGridManager> bizGridManagerList = getDAO().find(sqlJurDct.toString(),
                BizGridManager.class, paramsJurDct.toArray());
        //查询操作员网格备用
        String gridcode = "";
        ArrayList<GridInfoResp> gridResp = new ArrayList<GridInfoResp>();
        try {
            dataService.queGridDataInfo(gridResp);
        }catch(Exception ex) {
            log.error("获取操作员网格权限发生异常");
        }
        if(!gridResp.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < gridResp.size(); i++) {
                sb.append(gridResp.get(i).getGridCode());
                if(i != gridResp.size() - 1) {
                    sb.append("','");
                }
            }
            gridcode = sb.toString();
        }

        //如果找到有一条为 Y 的， 都按业务区查， 否则都要按网格权限来查
        String type;
        if(bizGridManagerList != null && bizGridManagerList.size() > 0){
            //按业务区权限查询流失预警
            type = "areaid";
        }else {
            //按操作员网格来查询流失预警
            type = "whgrid";
        }

        List<EarlyLossTitleInfo> earlyLossTitleInfoList = new ArrayList<EarlyLossTitleInfo>();
        PersistentService persistentService = SpringBeanUtil.getPersistentService();
        if(req.getStatus() == 3){
            if(!warnLosPrvSysparamList.equals("")){
                for(int i = warnLosPrvSysparamList.size() - 1; i >= 0; i--) {
                    PrvSysparam prvSysparam = warnLosPrvSysparamList.get(i);
                    String title = prvSysparam.getMname().trim();
                    String tableNmae = prvSysparam.getMcode().trim();
                    String data = prvSysparam.getData().trim();
                    EarlyLossTitleInfo earlyLossTitleInfoArreas = getearlyLossTitleInfo(gridcode,type,req,title,tableNmae,data,persistentService);
                    earlyLossTitleInfoList.add(earlyLossTitleInfoArreas);
                }
            }

        }else if(req.getStatus() == 0 || req.getStatus() == 2){
            //直接查本地库表的待处理或者已处理的所有单子 在去看BI的库有没有 有就说明在定义的时间段内
            int status = req.getStatus();
            if(!warnLosPrvSysparamList.equals("")){
                for(int i = warnLosPrvSysparamList.size() - 1; i >= 0; i--) {
                    PrvSysparam prvSysparam = warnLosPrvSysparamList.get(i);
                    String title = prvSysparam.getMname().trim();
                    String tableNmae = prvSysparam.getMcode().trim();
                    String data = prvSysparam.getData().trim();
                    EarlyLossTitleInfo earlyLossTitleInfoArreas = getarrearListforLocal(gridcode,type,req,title,tableNmae,data,status);
                    earlyLossTitleInfoList.add(earlyLossTitleInfoArreas);
                }
            }

        }else if(req.getStatus() == 1){
            //查询未处理 从BI查出来所有，和本地状态对比   排除待处理和已完成的
            if(!warnLosPrvSysparamList.equals("")){
                for (int i = warnLosPrvSysparamList.size() - 1; i >= 0; i--) {
                    PrvSysparam prvSysparam = warnLosPrvSysparamList.get(i);
                    String title = prvSysparam.getMname().trim();
                    String tableNmae = prvSysparam.getMcode().trim();
                    String data = prvSysparam.getData().trim();
                    EarlyLossTitleInfo earlyLossTitleInfoArreas = deleteearlyLossTitleInfo(gridcode,type,req,title,tableNmae,data,persistentService);
                    earlyLossTitleInfoList.add(earlyLossTitleInfoArreas);
                }
            }
        }

        resp.setEarlyLossTitleInfoList(earlyLossTitleInfoList);
        return returnInfo;
    }


    /**
     * 中山试点营销流失预警查询  分頁
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo quePageEarlyWarnLos(QueEarlyLossPageReq req, QueEarlyLossPageResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkEmpty(req.getTableName(),"请选择相应模块");
        Page page = new Page();
        page.setPageSize(req.getPageSize());
        page.setPageNo(req.getCurrentPage());

        //开始查数据
        //查询配置表  动态加载客户群
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT * from  prv_sysparam where gcode = ? and mcode = ?   ORDER BY sort desc");
        paramsTemp.add("QUE_EARLYWARNLOS_CUSTOMER");
        paramsTemp.add(req.getTableName().trim());
        List<PrvSysparam> warnLosPrvSysparamList = getDAO().find(sqlBufferTemp.toString(),PrvSysparam.class,paramsTemp.toArray());

        CheckUtils.checkNull(warnLosPrvSysparamList,"没有配置显示的流失预警客户群");
        //加业务区和网格权限
        List<Object> paramsJurDct = new ArrayList();
        StringBuffer sqlJurDct = new StringBuffer();
        sqlJurDct.append(" SELECT mnrid,gridid,operid,ismain,updatetime,memo,iscgrid FROM biz_grid_manager where operid = ? and iscgrid = ?  ");
        paramsJurDct.add(loginInfo.getOperid());
        paramsJurDct.add("Y");
        List<BizGridManager> bizGridManagerList = getDAO().find(sqlJurDct.toString(),
                BizGridManager.class, paramsJurDct.toArray());
        //查询操作员网格备用
        String gridcode = "";
        ArrayList<GridInfoResp> gridResp = new ArrayList<GridInfoResp>();
        try {
            dataService.queGridDataInfo(gridResp);
        }catch(Exception ex) {
            log.error("获取操作员网格权限发生异常");
        }
        if(!gridResp.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < gridResp.size(); i++) {
                sb.append(gridResp.get(i).getGridCode());
                if(i != gridResp.size() - 1) {
                    sb.append("','");
                }
            }
            gridcode = sb.toString();
        }

        //如果找到有一条为 Y 的， 都按业务区查， 否则都要按网格权限来查
        String type;
        if(bizGridManagerList != null && bizGridManagerList.size() > 0){
            //按业务区权限查询流失预警
            type = "areaid";
        }else {
            //按操作员网格来查询流失预警
            type = "whgrid";
        }

        List<EarlyLossTitlePageInfo> earlyLossTitleInfoList = new ArrayList<EarlyLossTitlePageInfo>();
        PersistentService persistentService = SpringBeanUtil.getPersistentService();
        if(req.getStatus() == 3){
            if(!warnLosPrvSysparamList.equals("")){
                for(int i = warnLosPrvSysparamList.size() - 1; i >= 0; i--) {
                    PrvSysparam prvSysparam = warnLosPrvSysparamList.get(i);
                    String title = prvSysparam.getMname().trim();
                    String tableNmae = prvSysparam.getMcode().trim();
                    String data = prvSysparam.getData().trim();
                    EarlyLossTitlePageInfo earlyLossTitleInfoArreas = getearlyLossTitleInfoPage(page,gridcode,type,req,title,tableNmae,data,persistentService);
                    earlyLossTitleInfoList.add(earlyLossTitleInfoArreas);
                }
            }

        }else if(req.getStatus() == 0 || req.getStatus() == 2){
            //直接查本地库表的待处理或者已处理的所有单子 在去看BI的库有没有 有就说明在定义的时间段内
            int status = req.getStatus();
            if(!warnLosPrvSysparamList.equals("")){
                for(int i = warnLosPrvSysparamList.size() - 1; i >= 0; i--) {
                    PrvSysparam prvSysparam = warnLosPrvSysparamList.get(i);
                    String title = prvSysparam.getMname().trim();
                    String tableNmae = prvSysparam.getMcode().trim();
                    String data = prvSysparam.getData().trim();
                    EarlyLossTitlePageInfo earlyLossTitleInfoArreas = getarrearListforLocalPage(page,gridcode,type,req,title,tableNmae,data,status);
                    earlyLossTitleInfoList.add(earlyLossTitleInfoArreas);
                }
            }

        }else if(req.getStatus() == 1){
            //查询未处理 从BI查出来所有，和本地状态对比   排除待处理和已完成的
            if(!warnLosPrvSysparamList.equals("")){
                for (int i = warnLosPrvSysparamList.size() - 1; i >= 0; i--) {
                    PrvSysparam prvSysparam = warnLosPrvSysparamList.get(i);
                    String title = prvSysparam.getMname().trim();
                    String tableNmae = prvSysparam.getMcode().trim();
                    String data = prvSysparam.getData().trim();
                    EarlyLossTitlePageInfo earlyLossTitleInfoArreas = deleteearlyLossTitleInfoPage(page,gridcode,type,req,title,tableNmae,data,persistentService);
                    earlyLossTitleInfoList.add(earlyLossTitleInfoArreas);
                }
            }
        }

        resp.setEarlyLossTitleInfoList(earlyLossTitleInfoList);
        return returnInfo;
    }

    //本地库查询已处理的流失预警对象  在去BI库筛选有的
    private EarlyLossTitleInfo getarrearListforLocal(String gridcode,String type,QueEarlyLossReq req,String title,String tableNmae,String data,int status) throws Exception{
        PersistentService persistentService = SpringBeanUtil.getPersistentService();
        EarlyLossTitleInfo earlyLossTitleInfo = new EarlyLossTitleInfo();
        earlyLossTitleInfo.setTitle(title);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        //调用视图  返回客户类型和业务类型
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT getgoodslabel( ? ) ");
        paramsTemp.add(tableNmae);
        String patchName =  getDAO().findUniqueObject(sqlBufferTemp.toString(),paramsTemp.toArray()).toString();
        if(patchName != null && !"".equals(patchName)) {
            String[] temp = patchName.split(",");
            earlyLossTitleInfo.setCustType(temp[0]);
            earlyLossTitleInfo.setBusinessType(temp[1]);
        }
        if(data.equals("CMMS")){
            //针对上门收费特殊改造
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName, ");
            sqlBuffer.append(" whgridcode whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time ");
            sqlBuffer.append(" ,tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where 1=1 ");
            sqlBuffer.append(" AND table_name = ? AND status = ? ");
            params.add(tableNmae);
            params.add(status);
            //sqlBuffer.append(" and tomonth_arrrars <> 0.00 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }else {
                    sqlBuffer.append(" AND whgridcode in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" and address like ? ");
                params.add("%" + req.getAddress() + "%");
            }
            sqlBuffer.append(" ORDER BY time desc ");
            if (req.getAddress() == null || "".equals(req.getAddress())) {
                sqlBuffer.append(" LIMIT ? ");
                params.add(req.getPageSize());
            }
            //查询bi库所有对象
            List<EarlyLossInfo> arrearList = getDAO().find(sqlBuffer.toString(),
                    EarlyLossInfo.class, params.toArray());
            if (arrearList != null && arrearList.size() != 0) {
                for (int i = 0; i < arrearList.size(); i++) {
                    EarlyLossInfo earlyLossInfo = arrearList.get(i);
                    String addressCode = earlyLossInfo.getAddressCode();
                    String custid = earlyLossInfo.getCustid();
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT IFNULL(CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL( ");
                    sql.append(" ADDRESSNAME8,''),IFNULL(ADDRESSNAME9,'')),SUBSTRING(STAND_NAME,7)) as address,CITY CITY,CUSTID CUSTID, ");
                    sql.append(" CUSTNAME CUSTNAME,HOUSEID addressCode,MOBILE iphone,OwePastMonth tomothArreas,OweThisMonth thisArreas, ");
                    sql.append(" ReqTimeDesc time ,ywgridcode whgridCode,ywgridcode WHGRIDNAME,AREAID AREAID from ");
                    sql.append(tableNmae);
                    sql.append(" where CUSTID = ? and HOUSEID = ? ");
                    paramsList.add(custid);
                    paramsList.add(addressCode);
                    List<EarlyLossInfo> earlyLossInfoList =  getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        arrearList.remove(earlyLossInfo);
                        i--;
                    }
                }
                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
            }


        }else {
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName, ");
            sqlBuffer.append(" whgridcode whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time ");
            sqlBuffer.append(" ,tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where 1=1 ");
            sqlBuffer.append(" AND table_name = ? AND status = ? ");
            params.add(tableNmae);
            params.add(status);
            //sqlBuffer.append(" and tomonth_arrrars <> 0.00 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }else {
                    sqlBuffer.append(" AND whgridcode in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" and address like ? ");
                params.add("%" + req.getAddress() + "%");
            }
            sqlBuffer.append(" ORDER BY time desc ");
            if (req.getAddress() == null || "".equals(req.getAddress())) {
                sqlBuffer.append(" LIMIT ? ");
                params.add(req.getPageSize());
            }
            //查询bi库所有对象
            List<EarlyLossInfo> arrearList = getDAO().find(sqlBuffer.toString(),
                    EarlyLossInfo.class, params.toArray());
            if (arrearList != null && arrearList.size() != 0) {
                for (int i = 0; i < arrearList.size(); i++) {
                    EarlyLossInfo earlyLossInfo = arrearList.get(i);
                    String addressCode = earlyLossInfo.getAddressCode();
                    String custid = earlyLossInfo.getCustid();
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, CUSTID custid,CUSTNAME custName,HOUSEID addressCode,WHLADDR address,MOBILE iphone,ARREAR_HIS ");
                    sql.append(" tomothArreas,ARREAR_MON thisArreas,STATUSDATE time, WHGRIDCODE whgridCode,WHGRIDNAME whgridNmae,AREAID AREAID from ");
                    sql.append(tableNmae);
                    sql.append(" where CUSTID = ? and HOUSEID = ? ");
                    paramsList.add(custid);
                    paramsList.add(addressCode);
                    List<EarlyLossInfo> earlyLossInfoList = persistentService.find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        arrearList.remove(earlyLossInfo);
                        i--;
                    }
                }
                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
            }
        }
        return earlyLossTitleInfo;
    }
    //剔除本地已处理的状态
    private EarlyLossTitleInfo deleteearlyLossTitleInfo(String gridcode,String type,QueEarlyLossReq req,String title,String tableName,String data,PersistentService persistentService) throws Exception{
        EarlyLossTitleInfo earlyLossTitleInfo = new EarlyLossTitleInfo();
        earlyLossTitleInfo.setTitle(title);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        //调用视图  返回客户类型和业务类型
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT getgoodslabel( ? ) ");
        paramsTemp.add(tableName);
        String patchName =  getDAO().findUniqueObject(sqlBufferTemp.toString(),paramsTemp.toArray()).toString();
        if(patchName != null && !"".equals(patchName)) {
            String[] temp = patchName.split(",");
            earlyLossTitleInfo.setCustType(temp[0]);
            earlyLossTitleInfo.setBusinessType(temp[1]);
        }

        if(data.equals("CMMS")){
            //针对上门收费的特殊处理
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT IFNULL(CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL( ");
            sqlBuffer.append(" ADDRESSNAME8,''),IFNULL(ADDRESSNAME9,'')),SUBSTRING(STAND_NAME,7)) as address,CITY CITY,CUSTID CUSTID, ");
            sqlBuffer.append(" CUSTNAME CUSTNAME,HOUSEID addressCode,MOBILE iphone,OwePastMonth tomothArreas,OweThisMonth thisArreas, ");
            sqlBuffer.append(" ReqTimeDesc time ,ywgridcode whgridCode,ywgridcode WHGRIDNAME,AREAID AREAID from ");
            sqlBuffer.append(tableName);
            //sqlBuffer.append("  and  OwePastMonth <> 0.00 ");
            sqlBuffer.append(" where 1=1 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND YWGRIDCODE = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND YWGRIDCODE = ? ");
                    params.add(req.getGridcode());
                }else {
                    sqlBuffer.append(" AND YWGRIDCODE in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" AND CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL(ADDRESSNAME8,'') ");
                sqlBuffer.append(" ,IFNULL(ADDRESSNAME9,'')) LIKE ? ");
                params.add("%" + req.getAddress() + "%");
            }
            sqlBuffer.append(" order by ReqTimeDesc desc ");
            if(req.getAddress()==null ||"".equals(req.getAddress())){
                sqlBuffer.append(" LIMIT ? ");
                params.add(req.getPageSize());
            }
            //查询bi库所有对象
            List<EarlyLossInfo> arrearList = getDAO().find(sqlBuffer.toString(),
                    EarlyLossInfo.class,params.toArray());

            if (arrearList != null && arrearList.size() != 0) {
                for (int i = 0; i < arrearList.size(); i++) {
                    EarlyLossInfo earlyLossInfo = arrearList.get(i);
                    earlyLossInfo.setTableNmae(tableName);
                    //匹配本地已处理  去除对象
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName,whgridcode ");
                    sql.append(" whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time, ");
                    sql.append(" tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where table_name = ? and address_code = ?  ");
                    paramsList.add(tableName);
                    paramsList.add(earlyLossInfo.getAddressCode());
                    List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        earlyLossInfo.setStatus(1);
                    } else if (earlyLossInfoList.get(0).getStatus() == 2 || earlyLossInfoList.get(0).getStatus() == 0) {
                        arrearList.remove(earlyLossInfo);
                        i--;//索引减1  不然会包索引异常
                    } else if (earlyLossInfoList.get(0).getStatus() == 1) {
                        earlyLossInfo.setStatus(earlyLossInfoList.get(0).getStatus());
                    }
                }
                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
            }

        }else {
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT CITY city, CUSTID custid,CUSTNAME custName,HOUSEID addressCode,WHLADDR address,MOBILE iphone,ARREAR_HIS ");
            sqlBuffer.append(" tomothArreas,ARREAR_MON thisArreas,STATUSDATE time, WHGRIDCODE whgridCode,WHGRIDNAME whgridNmae,AREAID AREAID from ");
            sqlBuffer.append(tableName);
            sqlBuffer.append(" where 1=1 ");
           // sqlBuffer.append(" and  ARREAR_HIS <> 0 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }else {
                    sqlBuffer.append(" AND whgridcode in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" AND WHLADDR like ? ");
                params.add("%" + req.getAddress() + "%");
            } else {
                sqlBuffer.append(" and ROWNUM <= ? ");
                params.add(req.getPageSize());
            }
            sqlBuffer.append(" ORDER BY STATUSDATE desc ");
            List<EarlyLossInfo> arrearList = persistentService.find(sqlBuffer.toString(),
                    EarlyLossInfo.class, params.toArray());
            if (arrearList != null && arrearList.size() != 0) {
                for (int i = 0; i < arrearList.size(); i++) {
                    EarlyLossInfo earlyLossInfo = arrearList.get(i);
                    earlyLossInfo.setTableNmae(tableName);
                    //匹配本地已处理  去除对象
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName,whgridcode ");
                    sql.append(" whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time, ");
                    sql.append(" tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where table_name = ? and address_code = ?  ");
                    paramsList.add(tableName);
                    paramsList.add(earlyLossInfo.getAddressCode());
                    List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        earlyLossInfo.setStatus(1);
                    } else if (earlyLossInfoList.get(0).getStatus() == 2 || earlyLossInfoList.get(0).getStatus() == 0) {
                        arrearList.remove(earlyLossInfo);
                        i--;//索引减1  不然会包索引异常
                    } else if (earlyLossInfoList.get(0).getStatus() == 1) {
                        earlyLossInfo.setStatus(earlyLossInfoList.get(0).getStatus());
                    }
                }
                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
            }
        }
        return earlyLossTitleInfo;
    }
    //查询所有  然后去本地匹配状态 没有的默认 待处理 1
    private EarlyLossTitleInfo getearlyLossTitleInfo(String gridcode,String type,QueEarlyLossReq req,String title,String tableName,String data,PersistentService persistentService) throws Exception{
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        EarlyLossTitleInfo earlyLossTitleInfo = new EarlyLossTitleInfo();
        earlyLossTitleInfo.setTitle(title);
        //调用视图  返回客户类型和业务类型
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT getgoodslabel( ? ) ");
        paramsTemp.add(tableName);
        String patchName =  getDAO().findUniqueObject(sqlBufferTemp.toString(),paramsTemp.toArray()).toString();
        if(patchName != null && !"".equals(patchName)) {
            String[] temp = patchName.split(",");
            earlyLossTitleInfo.setCustType(temp[0]);
            earlyLossTitleInfo.setBusinessType(temp[1]);
        }

        if(data.equals("CMMS")){
            //针对上门收费的特殊处理
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT IFNULL(CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL( ");
            sqlBuffer.append(" ADDRESSNAME8,''),IFNULL(ADDRESSNAME9,'')),SUBSTRING(STAND_NAME,7)) as address,CITY CITY,CUSTID CUSTID, ");
            sqlBuffer.append(" CUSTNAME CUSTNAME,HOUSEID addressCode,MOBILE iphone,OwePastMonth tomothArreas,OweThisMonth thisArreas, ");
            sqlBuffer.append(" ReqTimeDesc time ,ywgridcode whgridCode,ywgridcode WHGRIDNAME,AREAID AREAID from ");
            sqlBuffer.append(tableName);
            sqlBuffer.append(" where 1=1 ");
           // sqlBuffer.append("  and  OwePastMonth <> 0.00 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND YWGRIDCODE = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND YWGRIDCODE = ? ");
                    params.add(req.getGridcode());
                }else {
                    sqlBuffer.append(" AND YWGRIDCODE in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" AND CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL(ADDRESSNAME8,'') ");
                sqlBuffer.append(" ,IFNULL(ADDRESSNAME9,'')) LIKE ? ");
                params.add("%" + req.getAddress() + "%");
            }
            sqlBuffer.append(" order by ReqTimeDesc desc ");
            if(req.getAddress()==null ||"".equals(req.getAddress())){
                sqlBuffer.append(" LIMIT ? ");
                params.add(req.getPageSize());
            }
            //查询bi库所有对象
            List<EarlyLossInfo> arrearList = getDAO().find(sqlBuffer.toString(),
                    EarlyLossInfo.class,params.toArray());

            if (arrearList != null && arrearList.size() != 0) {
                for (EarlyLossInfo earlyLossInfo : arrearList
                ) {
                    earlyLossInfo.setTableNmae(tableName);
                    earlyLossInfo.setTime(earlyLossInfo.getTime().substring(0,8));
                    //处理地址为空
                    if(earlyLossInfo.getAddress() == null || "".equals(earlyLossInfo.getAddress())){
                        List<Object> param = new ArrayList();
                        StringBuffer sqllist = new StringBuffer();
                        sqllist.append(" SELECT IFNULL(SUBSTRING(STAND_NAME,7),'') as addr from SMS_CUST_REQ where HOUSEID = ? ");
                        param.add(earlyLossInfo.getAddressCode());
                        String addr =  getDAO().findUniqueObject(sqllist.toString(),param.toArray()).toString();
                        earlyLossInfo.setAddress(addr);
                    }
                    //去本地对比 赋状态、
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName,whgridcode ");
                    sql.append(" whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time, ");
                    sql.append(" tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where table_name = ? and address_code = ?  ");
                    paramsList.add(tableName);
                    paramsList.add(earlyLossInfo.getAddressCode());
                    List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        earlyLossInfo.setStatus(1);
                    } else {
                        earlyLossInfo.setStatus(earlyLossInfoList.get(0).getStatus());
                    }
                }

                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
            }

        }else {
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT CITY city, CUSTID custid,CUSTNAME custName,HOUSEID addressCode,WHLADDR address,MOBILE iphone,ARREAR_HIS ");
            sqlBuffer.append(" tomothArreas,ARREAR_MON thisArreas,STATUSDATE time, WHGRIDCODE whgridCode,WHGRIDNAME whgridNmae,AREAID AREAID from ");
            sqlBuffer.append(tableName);
            sqlBuffer.append(" where 1=1 ");
            //sqlBuffer.append(" and  ARREAR_HIS <> 0 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }else {
                    sqlBuffer.append(" AND whgridcode in (?) ");
                    params.add(gridcode);
                }
            }
            if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                sqlBuffer.append(" AND whgridcode = ? ");
                params.add(req.getGridcode());
            }
            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" AND WHLADDR like ? ");
                params.add("%" + req.getAddress() + "%");
            } else {
                sqlBuffer.append(" AND ROWNUM <= ? ");
                params.add(req.getPageSize());
            }
            sqlBuffer.append(" ORDER BY STATUSDATE desc ");
            List<EarlyLossInfo> arrearList = persistentService.find(sqlBuffer.toString(),
                    EarlyLossInfo.class, params.toArray());
            if (arrearList != null && arrearList.size() != 0) {
                for (EarlyLossInfo earlyLossInfo : arrearList
                ) {
                    earlyLossInfo.setTableNmae(tableName);
                    //去本地对比 赋状态、
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName,whgridcode ");
                    sql.append(" whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time, ");
                    sql.append(" tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where table_name = ? and address_code = ?  ");
                    paramsList.add(tableName);
                    paramsList.add(earlyLossInfo.getAddressCode());
                    List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        earlyLossInfo.setStatus(1);
                    } else {
                        earlyLossInfo.setStatus(earlyLossInfoList.get(0).getStatus());
                    }
                }

                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
            }
        }
        return earlyLossTitleInfo;
    }


    //本地库查询已处理的流失预警对象  在去BI库筛选有的
    private EarlyLossTitlePageInfo getarrearListforLocalPage(Page page,String gridcode,String type,QueEarlyLossPageReq req,String title,String tableNmae,String data,int status) throws Exception{
        PersistentService persistentService = SpringBeanUtil.getPersistentService();
        EarlyLossTitlePageInfo earlyLossTitleInfo = new EarlyLossTitlePageInfo();
        earlyLossTitleInfo.setTitle(title);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        //调用视图  返回客户类型和业务类型
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT getgoodslabel( ? ) ");
        paramsTemp.add(tableNmae);
        String patchName =  getDAO().findUniqueObject(sqlBufferTemp.toString(),paramsTemp.toArray()).toString();
        if(patchName != null && !"".equals(patchName)) {
            String[] temp = patchName.split(",");
            earlyLossTitleInfo.setCustType(temp[0]);
            earlyLossTitleInfo.setBusinessType(temp[1]);
        }
        if(data.equals("CMMS")){
            //针对上门收费特殊改造
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName, ");
            sqlBuffer.append(" whgridcode whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time ");
            sqlBuffer.append(" ,tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where 1=1 ");
            sqlBuffer.append(" AND table_name = ? AND status = ? ");
            params.add(tableNmae);
            params.add(status);
           // sqlBuffer.append(" and tomonth_arrrars <> 0.00 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }else {

                    sqlBuffer.append(" AND whgridcode in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" and address like ? ");
                params.add("%" + req.getAddress() + "%");
            }

            sqlBuffer.append(" ORDER BY time desc ");
            if (req.getAddress() == null || "".equals(req.getAddress())) {
                sqlBuffer.append(" LIMIT ? ");
                params.add(req.getPageSize());
            }
            //查询bi库所有对象
            page = getDAO().find(page,sqlBuffer.toString(),
                    EarlyLossInfo.class, params.toArray());
            List<EarlyLossInfo> arrearList = page.getResult();
            if (arrearList != null && arrearList.size() != 0) {
                for (int i = 0; i < arrearList.size(); i++) {
                    EarlyLossInfo earlyLossInfo = arrearList.get(i);
                    String addressCode = earlyLossInfo.getAddressCode();
                    String custid = earlyLossInfo.getCustid();
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT IFNULL(CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL( ");
                    sql.append(" ADDRESSNAME8,''),IFNULL(ADDRESSNAME9,'')),SUBSTRING(STAND_NAME,7)) as address,CITY CITY,CUSTID CUSTID, ");
                    sql.append(" CUSTNAME CUSTNAME,HOUSEID addressCode,MOBILE iphone,OwePastMonth tomothArreas,OweThisMonth thisArreas, ");
                    sql.append(" ReqTimeDesc time ,ywgridcode whgridCode,ywgridcode WHGRIDNAME,AREAID AREAID from ");
                    sql.append(tableNmae);
                    sql.append(" where CUSTID = ? and HOUSEID = ? ");
                    paramsList.add(custid);
                    paramsList.add(addressCode);
                    List<EarlyLossInfo> earlyLossInfoList =  getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        arrearList.remove(earlyLossInfo);
                        i--;
                    }
                }
                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
                earlyLossTitleInfo.setCurrentPage(req.getCurrentPage());
                earlyLossTitleInfo.setPagesize(req.getPageSize());
                earlyLossTitleInfo.setTotalpage(page.getTotalPages());
                earlyLossTitleInfo.setTotalsize(page.getTotalCount());
            }


        }else {
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName, ");
            sqlBuffer.append(" whgridcode whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time ");
            sqlBuffer.append(" ,tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where 1=1 ");
            sqlBuffer.append(" AND table_name = ? AND status = ? ");
            params.add(tableNmae);
            params.add(status);
           // sqlBuffer.append(" and tomonth_arrrars <> 0.00 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }else {

                    sqlBuffer.append(" AND whgridcode in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" and address like ? ");
                params.add("%" + req.getAddress() + "%");
            }
            sqlBuffer.append(" ORDER BY time desc ");
            if (req.getAddress() == null || "".equals(req.getAddress())) {
                sqlBuffer.append(" LIMIT ? ");
                params.add(req.getPageSize());
            }
            //查询bi库所有对象
            page = getDAO().find(page,sqlBuffer.toString(),
                    EarlyLossInfo.class, params.toArray());
            List<EarlyLossInfo> arrearList = page.getResult();
            if (arrearList != null && arrearList.size() != 0) {
                for (int i = 0; i < arrearList.size(); i++) {
                    EarlyLossInfo earlyLossInfo = arrearList.get(i);
                    String addressCode = earlyLossInfo.getAddressCode();
                    String custid = earlyLossInfo.getCustid();
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, CUSTID custid,CUSTNAME custName,HOUSEID addressCode,WHLADDR address,MOBILE iphone,ARREAR_HIS ");
                    sql.append(" tomothArreas,ARREAR_MON thisArreas,STATUSDATE time, WHGRIDCODE whgridCode,WHGRIDNAME whgridNmae,AREAID AREAID from ");
                    sql.append(tableNmae);
                    sql.append(" where CUSTID = ? and HOUSEID = ? ");
                    paramsList.add(custid);
                    paramsList.add(addressCode);
                    List<EarlyLossInfo> earlyLossInfoList = persistentService.find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        arrearList.remove(earlyLossInfo);
                        i--;
                    }
                }
                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
                earlyLossTitleInfo.setCurrentPage(req.getCurrentPage());
                earlyLossTitleInfo.setPagesize(req.getPageSize());
                earlyLossTitleInfo.setTotalpage(page.getTotalPages());
                earlyLossTitleInfo.setTotalsize(page.getTotalCount());
            }
        }
        return earlyLossTitleInfo;
    }
    //剔除本地已处理的状态
    private EarlyLossTitlePageInfo deleteearlyLossTitleInfoPage(Page page,String gridcode,String type,QueEarlyLossPageReq req,String title,String tableName,String data,PersistentService persistentService) throws Exception{
        EarlyLossTitlePageInfo earlyLossTitleInfo = new EarlyLossTitlePageInfo();
        earlyLossTitleInfo.setTitle(title);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        //调用视图  返回客户类型和业务类型
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT getgoodslabel( ? ) ");
        paramsTemp.add(tableName);
        String patchName =  getDAO().findUniqueObject(sqlBufferTemp.toString(),paramsTemp.toArray()).toString();
        if(patchName != null && !"".equals(patchName)) {
            String[] temp = patchName.split(",");
            earlyLossTitleInfo.setCustType(temp[0]);
            earlyLossTitleInfo.setBusinessType(temp[1]);
        }

        if(data.equals("CMMS")){
            //针对上门收费的特殊处理
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT IFNULL(CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL( ");
            sqlBuffer.append(" ADDRESSNAME8,''),IFNULL(ADDRESSNAME9,'')),SUBSTRING(STAND_NAME,7)) as address,CITY CITY,CUSTID CUSTID, ");
            sqlBuffer.append(" CUSTNAME CUSTNAME,HOUSEID addressCode,MOBILE iphone,OwePastMonth tomothArreas,OweThisMonth thisArreas, ");
            sqlBuffer.append(" ReqTimeDesc time ,ywgridcode whgridCode,ywgridcode WHGRIDNAME,AREAID AREAID from ");
            sqlBuffer.append(tableName);
            sqlBuffer.append(" where 1=1 ");
            //sqlBuffer.append(" and OwePastMonth <> 0.00 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND YWGRIDCODE = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND YWGRIDCODE = ? ");
                    params.add(req.getGridcode());
                }else {

                    sqlBuffer.append(" AND YWGRIDCODE in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" AND CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL(ADDRESSNAME8,'') ");
                sqlBuffer.append(" ,IFNULL(ADDRESSNAME9,'')) LIKE ? ");
                params.add("%" + req.getAddress() + "%");
            }
            sqlBuffer.append(" order by ReqTimeDesc desc ");
            if(req.getAddress()==null ||"".equals(req.getAddress())){
                sqlBuffer.append(" LIMIT ? ");
                params.add(req.getPageSize());
            }
            //查询bi库所有对象
            page = getDAO().find(page,sqlBuffer.toString(),
                    EarlyLossInfo.class,params.toArray());
            List<EarlyLossInfo> arrearList = page.getResult();

            if (arrearList != null && arrearList.size() != 0) {
                for (int i = 0; i < arrearList.size(); i++) {
                    EarlyLossInfo earlyLossInfo = arrearList.get(i);
                    earlyLossInfo.setTableNmae(tableName);
                    //匹配本地已处理  去除对象
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName,whgridcode ");
                    sql.append(" whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time, ");
                    sql.append(" tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where table_name = ? and address_code = ?  ");
                    paramsList.add(tableName);
                    paramsList.add(earlyLossInfo.getAddressCode());
                    List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        earlyLossInfo.setStatus(1);
                    } else if (earlyLossInfoList.get(0).getStatus() == 2 || earlyLossInfoList.get(0).getStatus() == 0) {
                        arrearList.remove(earlyLossInfo);
                        i--;//索引减1  不然会包索引异常
                    } else if (earlyLossInfoList.get(0).getStatus() == 1) {
                        earlyLossInfo.setStatus(earlyLossInfoList.get(0).getStatus());
                    }
                }
                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
                earlyLossTitleInfo.setCurrentPage(req.getCurrentPage());
                earlyLossTitleInfo.setPagesize(req.getPageSize());
                earlyLossTitleInfo.setTotalpage(page.getTotalPages());
                earlyLossTitleInfo.setTotalsize(page.getTotalCount());
            }

        }else {
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT CITY city, CUSTID custid,CUSTNAME custName,HOUSEID addressCode,WHLADDR address,MOBILE iphone,ARREAR_HIS ");
            sqlBuffer.append(" tomothArreas,ARREAR_MON thisArreas,STATUSDATE time, WHGRIDCODE whgridCode,WHGRIDNAME whgridNmae,AREAID AREAID from ");
            sqlBuffer.append(tableName);
            sqlBuffer.append(" where 1=1 ");
            //sqlBuffer.append(" and ARREAR_HIS <> 0 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }else {

                    sqlBuffer.append(" AND whgridcode in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" AND WHLADDR like ? ");
                params.add("%" + req.getAddress() + "%");
            } else {
                sqlBuffer.append(" and ROWNUM <= ? ");
                params.add(req.getPageSize());
            }
            sqlBuffer.append(" ORDER BY STATUSDATE desc ");
            page = persistentService.find(page,sqlBuffer.toString(),
                    EarlyLossInfo.class, params.toArray());
            List<EarlyLossInfo> arrearList = page.getResult();
            if (arrearList != null && arrearList.size() != 0) {
                for (int i = 0; i < arrearList.size(); i++) {
                    EarlyLossInfo earlyLossInfo = arrearList.get(i);
                    earlyLossInfo.setTableNmae(tableName);
                    //匹配本地已处理  去除对象
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName,whgridcode ");
                    sql.append(" whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time, ");
                    sql.append(" tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where table_name = ? and address_code = ?  ");
                    paramsList.add(tableName);
                    paramsList.add(earlyLossInfo.getAddressCode());
                    List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        earlyLossInfo.setStatus(1);
                    } else if (earlyLossInfoList.get(0).getStatus() == 2 || earlyLossInfoList.get(0).getStatus() == 0) {
                        arrearList.remove(earlyLossInfo);
                        i--;//索引减1  不然会包索引异常
                    } else if (earlyLossInfoList.get(0).getStatus() == 1) {
                        earlyLossInfo.setStatus(earlyLossInfoList.get(0).getStatus());
                    }
                }
                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
                earlyLossTitleInfo.setCurrentPage(req.getCurrentPage());
                earlyLossTitleInfo.setPagesize(req.getPageSize());
                earlyLossTitleInfo.setTotalpage(page.getTotalPages());
                earlyLossTitleInfo.setTotalsize(page.getTotalCount());
            }
        }
        return earlyLossTitleInfo;
    }
    //查询所有  然后去本地匹配状态 没有的默认 待处理 1
    private EarlyLossTitlePageInfo getearlyLossTitleInfoPage(Page page,String gridcode,String type,QueEarlyLossPageReq req,String title,String tableName,String data,PersistentService persistentService) throws Exception{
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        EarlyLossTitlePageInfo earlyLossTitleInfo = new EarlyLossTitlePageInfo();
        earlyLossTitleInfo.setTitle(title);
        //调用视图  返回客户类型和业务类型
        List<Object> paramsTemp = new ArrayList();
        StringBuffer sqlBufferTemp = new StringBuffer();
        sqlBufferTemp.append("SELECT getgoodslabel( ? ) ");
        paramsTemp.add(tableName);
        String patchName =  getDAO().findUniqueObject(sqlBufferTemp.toString(),paramsTemp.toArray()).toString();
        if(patchName != null && !"".equals(patchName)) {
            String[] temp = patchName.split(",");
            earlyLossTitleInfo.setCustType(temp[0]);
            earlyLossTitleInfo.setBusinessType(temp[1]);
        }

        if(data.equals("CMMS")){
            //针对上门收费的特殊处理
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT IFNULL(CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL( ");
            sqlBuffer.append(" ADDRESSNAME8,''),IFNULL(ADDRESSNAME9,'')),SUBSTRING(STAND_NAME,7)) as address,CITY CITY,CUSTID CUSTID, ");
            sqlBuffer.append(" CUSTNAME CUSTNAME,HOUSEID addressCode,MOBILE iphone,OwePastMonth tomothArreas,OweThisMonth thisArreas, ");
            sqlBuffer.append(" ReqTimeDesc time ,ywgridcode whgridCode,ywgridcode WHGRIDNAME,AREAID AREAID from ");
            sqlBuffer.append(tableName);
            sqlBuffer.append(" where 1=1 ");
           // sqlBuffer.append(" and OwePastMonth <> 0.00 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND YWGRIDCODE = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND YWGRIDCODE = ? ");
                    params.add(req.getGridcode());
                }else {
                    sqlBuffer.append(" AND YWGRIDCODE in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" AND CONCAT(IFNULL(ADDRESSNAME5,''),IFNULL(ADDRESSNAME6,''),IFNULL(ADDRESSNAME7,''),IFNULL(ADDRESSNAME8,'') ");
                sqlBuffer.append(" ,IFNULL(ADDRESSNAME9,'')) LIKE ? ");
                params.add("%" + req.getAddress() + "%");
            }
            sqlBuffer.append(" order by ReqTimeDesc desc ");
            if(req.getAddress()==null ||"".equals(req.getAddress())){
                sqlBuffer.append(" LIMIT ? ");
                params.add(req.getPageSize());
            }
            //查询bi库所有对象
            page = getDAO().find(page,sqlBuffer.toString(),
                    EarlyLossInfo.class,params.toArray());
            List<EarlyLossInfo> arrearList = page.getResult();

            if (arrearList != null && arrearList.size() != 0) {
                for (EarlyLossInfo earlyLossInfo : arrearList
                ) {
                    earlyLossInfo.setTableNmae(tableName);
                    earlyLossInfo.setTime(earlyLossInfo.getTime().substring(0,8));
                    //处理地址为空
                    if(earlyLossInfo.getAddress() == null || "".equals(earlyLossInfo.getAddress())){
                        List<Object> param = new ArrayList();
                        StringBuffer sqllist = new StringBuffer();
                        sqllist.append(" SELECT IFNULL(SUBSTRING(STAND_NAME,7),'') as addr from SMS_CUST_REQ where HOUSEID = ? ");
                        param.add(earlyLossInfo.getAddressCode());
                        String addr =  getDAO().findUniqueObject(sqllist.toString(),param.toArray()).toString();
                        earlyLossInfo.setAddress(addr);
                    }
                    //去本地对比 赋状态、
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName,whgridcode ");
                    sql.append(" whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time, ");
                    sql.append(" tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where table_name = ? and address_code = ?  ");
                    paramsList.add(tableName);
                    paramsList.add(earlyLossInfo.getAddressCode());
                    List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        earlyLossInfo.setStatus(1);
                    } else {
                        earlyLossInfo.setStatus(earlyLossInfoList.get(0).getStatus());
                    }
                }

                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
                earlyLossTitleInfo.setCurrentPage(req.getCurrentPage());
                earlyLossTitleInfo.setPagesize(req.getPageSize());
                earlyLossTitleInfo.setTotalpage(page.getTotalPages());
                earlyLossTitleInfo.setTotalsize(page.getTotalCount());
            }

        }else {
            List<Object> params = new ArrayList();
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" SELECT CITY city, CUSTID custid,CUSTNAME custName,HOUSEID addressCode,WHLADDR address,MOBILE iphone,ARREAR_HIS ");
            sqlBuffer.append(" tomothArreas,ARREAR_MON thisArreas,STATUSDATE time, WHGRIDCODE whgridCode,WHGRIDNAME whgridNmae,AREAID AREAID from ");
            sqlBuffer.append(tableName);
            sqlBuffer.append(" where 1=1 ");
           // sqlBuffer.append(" and ARREAR_HIS <> 0 ");
            if(type.trim().equals("areaid")){
                sqlBuffer.append(" AND AREAID = ? ");
                params.add(loginInfo.getAreaid());
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }
            }
            if(type.trim().equals("whgrid")){
                if (req.getGridcode() != null && !"".equals(req.getGridcode())) {
                    sqlBuffer.append(" AND whgridcode = ? ");
                    params.add(req.getGridcode());
                }else {

                    sqlBuffer.append(" AND whgridcode in (?) ");
                    params.add(gridcode);
                }
            }

            if (req.getAddress() != null && !"".equals(req.getAddress())) {
                sqlBuffer.append(" AND WHLADDR like ? ");
                params.add("%" + req.getAddress() + "%");
            } else {
                sqlBuffer.append(" AND ROWNUM <= ? ");
                params.add(req.getPageSize());
            }
            sqlBuffer.append(" ORDER BY STATUSDATE desc ");
            page = persistentService.find(page,sqlBuffer.toString(),
                    EarlyLossInfo.class, params.toArray());
            List<EarlyLossInfo> arrearList = page.getResult();
            if (arrearList != null && arrearList.size() != 0) {
                for (EarlyLossInfo earlyLossInfo : arrearList
                ) {
                    earlyLossInfo.setTableNmae(tableName);
                    //去本地对比 赋状态、
                    List<Object> paramsList = new ArrayList();
                    StringBuffer sql = new StringBuffer();
                    sql.append(" SELECT CITY city, id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName,whgridcode ");
                    sql.append(" whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time, ");
                    sql.append(" tomonth_arrrars tomothArreas,this_arrrars thisArreas,AREAID AREAID from  earlyWarnLos_status where table_name = ? and address_code = ?  ");
                    paramsList.add(tableName);
                    paramsList.add(earlyLossInfo.getAddressCode());
                    List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sql.toString(), EarlyLossInfo.class, paramsList.toArray());
                    if (earlyLossInfoList == null || earlyLossInfoList.size() == 0) {
                        earlyLossInfo.setStatus(1);
                    } else {
                        earlyLossInfo.setStatus(earlyLossInfoList.get(0).getStatus());
                    }
                }

                earlyLossTitleInfo.setEarlyLossInfoList(arrearList);
                earlyLossTitleInfo.setCurrentPage(req.getCurrentPage());
                earlyLossTitleInfo.setPagesize(req.getPageSize());
                earlyLossTitleInfo.setTotalpage(page.getTotalPages());
                earlyLossTitleInfo.setTotalsize(page.getTotalCount());
            }
        }
        return earlyLossTitleInfo;
    }


    /**
     * 流失预警状态处理存本地记录接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo changeEarlyWarnLosToLocal(changeEarlyLossReq req, changeEarlyLossResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

     /*   LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);*/
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getAddressCode(),"地址编号不能为空");
        CheckUtils.checkEmpty(req.getCustid(),"客户编号不能为空");
        CheckUtils.checkEmpty(req.getTableNmae(),"BI表名不能为空");
        int status = req.getStatus();
        if(status >= 3){
            CheckUtils.checkEmpty(null,"你选择的状态有误");
        }
        int code = 0;
        //开始查数据
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT id id,bi_id biId,table_name tableNmae,status status,cust_id custid,cust_name custName, ");
        sqlBuffer.append(" whgridcode whgridCode,whgridname whgridNmae,address address,address_code addressCode,iphone iphone,time time");
        sqlBuffer.append(" ,tomonth_arrrars tomothArreas,this_arrrars thisArreas from  earlyWarnLos_status where table_name = ? ");
        sqlBuffer.append(" and address_code = ? ");
        params.add(req.getTableNmae());
        params.add(req.getAddressCode());
        List<EarlyLossInfo> earlyLossInfoList = getDAO().find(sqlBuffer.toString(),EarlyLossInfo.class,params.toArray());
        String updatetime = DateUtils.formatTimeNow();
        if(earlyLossInfoList!= null && earlyLossInfoList.size()!= 0){
            //说明已经改过状态  直接更新
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE earlyWarnLos_status SET time = '"+ req.getTime() + "',status= " + req.getStatus() + ",cust_id= '" + req.getCustid() + "',cust_name= '"+ req.getCustName() + "',areaid= '"+ req.getAreaid() + "',whgridcode='"+ req.getWhgridCode());
            sql.append("',whgridname = '"+ req.getWhgridNmae() + "',address= '" + req.getAddress()+ "',updatetime= '" + updatetime + "',iphone= '" + req.getIphone() + "',tomonth_arrrars= " + req.getTomothArreas() + ",this_arrrars= " + req.getThisArreas() + "  WHERE address_code = '" + req.getAddressCode());
            sql.append( "' AND table_name = '"+ req.getTableNmae() + "'");
            persistentService.clear();
            int i = persistentService.executeSql(sql.toString());
            if(i > 0){//成功
                code = 1;
            }else {
                code = 0;
            }

        }else {
            //没有改过   直接插入所有数据及状态
            String sql = "insert into earlyWarnLos_status(table_name,status,cust_id,cust_name,whgridcode,whgridname,address,address_code,iphone,time,tomonth_arrrars,this_arrrars,city,areaid,updatetime) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String tableName = req.getTableNmae();
            String custid = req.getCustid();
            String custName = req.getCustName();
            String whgridCode = req.getWhgridCode();
            String whgridNmae = req.getWhgridNmae();
            String address = req.getAddress();
            String addressCode = req.getAddressCode();
            String iphone = req.getIphone();
            String time = req.getTime();
            Double tomothArreas = req.getTomothArreas();
            Double thisArreas = req.getThisArreas();
            String city = req.getCity();
            String areaid = req.getAreaid();
            persistentService.clear();
            int i = persistentService.executeSql(sql,tableName,status,custid,custName,whgridCode,whgridNmae,address,addressCode,iphone,time,tomothArreas,thisArreas,city,areaid,updatetime);
            if(i > 0){//成功
                code = 1;
            }else {
                code = 0;
            }
        }
        resp.setCode(code);
        return returnInfo;
    }
}
