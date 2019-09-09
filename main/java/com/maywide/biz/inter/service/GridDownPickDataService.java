package com.maywide.biz.inter.service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.pojo.gridDownPickData.*;
import com.maywide.biz.inter.pojo.queDownPickData.*;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/27 0001.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GridDownPickDataService extends CommonService {
    private static Logger log = LoggerFactory.getLogger(GridDownPickDataService.class);
    @Autowired
    private ParamService paramService;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private RuleService ruleService;

    /**
     * 查询直属网格下拨数据接口 数据来源与数据结算系统(ods)
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queGridDownPickData(GridDownPickDataReq req, GridDownPickDataResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridCode(),"请选择网格");
        CheckUtils.checkEmpty(req.getMonth(),"月份不能为空");
        //开始查月度综合数据
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select type,cycleid,whgridcode,whgridname,subtype,subcode,partfees,memo,recid ");
        sqlBuffer.append(" from nods.wg_total_sum t ");
        sqlBuffer.append(" where t.cycleid = ? ");
        sqlBuffer.append(" and t.whgridcode = ? order by type, subcode ");
        params.add(req.getMonth());
        params.add(req.getGridCode());
        List<DownPickData> downPickDataList = persistentService.find(sqlBuffer.toString(),
               DownPickData.class, params.toArray());
        List<DownPickDataSalesComInfo> downPickDataSalesComInfoList = new ArrayList<DownPickDataSalesComInfo>();
        List<DownPickDataDefendInfo> downPickDataDefendInfoList = new ArrayList<DownPickDataDefendInfo>();
        List<DownPickDataDispoInfo> downPickDataDispoInfoList = new ArrayList<DownPickDataDispoInfo>();
        for (DownPickData downPickData:downPickDataList
             ) {
            if(downPickData.getType().trim().equals("维护收入")){
                DownPickDataDefendInfo downPickDataDefendInfo = new DownPickDataDefendInfo();
                copyDownToDefend(downPickDataDefendInfo,downPickData);
                downPickDataDefendInfoList.add(downPickDataDefendInfo);

            }else if(downPickData.getType().trim().equals("销售提成")){
                DownPickDataSalesComInfo downPickDataSalesComInfo = new DownPickDataSalesComInfo();
                copyDownToSales(downPickDataSalesComInfo,downPickData);
                downPickDataSalesComInfoList.add(downPickDataSalesComInfo);

            }else if(downPickData.getType().trim().equals("一次性收入")){
                DownPickDataDispoInfo downPickDataDispoInfo = new DownPickDataDispoInfo();
                copyDownToDispo(downPickDataDispoInfo,downPickData);
                downPickDataDispoInfoList.add(downPickDataDispoInfo);

            }else {
                log.info("该类型不做展示");
            }
        }
        //求月度总收入
        List<Object> paramList = new ArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append(" select ROUND(coalesce(sum(PARTFEES),0),2) totalIncome from nods.wg_total_sum t ");
        sql.append(" where t.cycleid = ? and t.whgridcode = ? ");
        paramList.add(req.getMonth());
        paramList.add(req.getGridCode());
        String totalIncome = persistentService.findUniqueObject(sql.toString(),paramList.toArray()).toString();
        Double totalIncomedoub = Double.parseDouble(totalIncome);
        //去查询奖罚数据
        List<DownPickDataAwardInfo> downPickDataAwardInfoList = new ArrayList<DownPickDataAwardInfo>();//奖罚对象
        List<Object> paramsAward = new ArrayList();
        StringBuffer sqlBufferAward = new StringBuffer();
        sqlBufferAward.append(" select t.tmonth cycleid,t.WHGRIDCODE whgridcode,t.WHGRIDNAME whgridname,t.PARTFEES partfees,t.RFEECODE subtype,t.RFEECODE subcode,t.NUMS memo");
        sqlBufferAward.append(" from nods.wg_total_cf_det t");
        sqlBufferAward.append(" where t.tMONTH = ? ");
        sqlBufferAward.append(" and t.WHGRIDCODE = ? order by t.PARTFEES ");
        paramsAward.add(req.getMonth());
        paramsAward.add(req.getGridCode());
        List<DownPickData> downPickAWardDataList = persistentService.find(sqlBufferAward.toString(),
                DownPickData.class, paramsAward.toArray());
        for (DownPickData downPickData:downPickAWardDataList
             ) {
            totalIncomedoub = totalIncomedoub + downPickData.getPartfees();
            DownPickDataAwardInfo downPickDataAwardInfo = new DownPickDataAwardInfo();
            downPickDataAwardInfo.setType("奖罚");
            downPickDataAwardInfo.setWhgridName(downPickData.getWhgridname());
            downPickDataAwardInfo.setSubCode(downPickData.getSubcode());
            downPickDataAwardInfo.setMemo(downPickData.getMemo());
            downPickDataAwardInfo.setRewardsPunishType(downPickData.getSubtype());
            downPickDataAwardInfo.setRewardsPunihMoney(downPickData.getPartfees());

            if(downPickData.getMemo()==null || "".equals(downPickData.getMemo())){
                downPickDataAwardInfo.setRewardsPunisNum(0L);
            }else {
                downPickDataAwardInfo.setRewardsPunisNum(Long.valueOf(downPickData.getMemo()));
            }
            if(downPickDataAwardInfo!= null){
                downPickDataAwardInfoList.add(downPickDataAwardInfo);
            }
        }
        resp.setTotalIncome(totalIncomedoub);
        resp.setDownPickDataDefendInfoList(downPickDataDefendInfoList);
        resp.setDownPickDataDispoInfoList(downPickDataDispoInfoList);
        resp.setDownPickDataSalesComInfoList(downPickDataSalesComInfoList);
        resp.setDownPickDataAwardInfoList(downPickDataAwardInfoList);
        resp.setMonth(req.getMonth());
        return returnInfo;
    }


    public DownPickDataDefendInfo  copyDownToDefend(DownPickDataDefendInfo downPickDataDefendInfo,DownPickData downPickData){
       downPickDataDefendInfo.setType(downPickData.getType());
       downPickDataDefendInfo.setWhgridName(downPickData.getWhgridname());
       downPickDataDefendInfo.setSubCode(downPickData.getSubcode());
       downPickDataDefendInfo.setMemo(downPickData.getMemo());
       downPickDataDefendInfo.setSubclass(downPickData.getSubtype());
       downPickDataDefendInfo.setTotalIncome(downPickData.getPartfees());
       String numbers[] = downPickData.getMemo().split(",");
       String writeOffUser = numbers[0].substring(numbers[0].indexOf(":")+1).trim();
       String netReceiptsUser =  numbers[1].substring(numbers[1].indexOf(":")+1).trim();
       String netReceiptsTotal =  numbers[2].substring(numbers[2].indexOf(":")+1).trim();
       downPickDataDefendInfo.setWriteOffUser(Long.valueOf(writeOffUser));
       downPickDataDefendInfo.setNetReceiptsUser(Long.valueOf(netReceiptsUser));
       downPickDataDefendInfo.setNetReceiptsTotal(Double.parseDouble(netReceiptsTotal));
       return downPickDataDefendInfo;
    }

    public DownPickDataSalesComInfo  copyDownToSales(DownPickDataSalesComInfo downPickDataSalesComInfo,DownPickData downPickData){
        downPickDataSalesComInfo.setType(downPickData.getType());
        downPickDataSalesComInfo.setWhgridName(downPickData.getWhgridname());
        downPickDataSalesComInfo.setSubclass(downPickData.getSubtype());
        downPickDataSalesComInfo.setSubCode(downPickData.getSubcode());
        downPickDataSalesComInfo.setMemo(downPickData.getMemo());
        downPickDataSalesComInfo.setTotalIncome(downPickData.getPartfees());
        String numbers[] = downPickData.getMemo().split(",");
        String writeOffUser = numbers[0].substring(numbers[0].indexOf(":")+1).trim();
        String netReceiptsUser =  numbers[1].substring(numbers[1].indexOf(":")+1).trim();
        downPickDataSalesComInfo.setBusinessNunber(Long.valueOf(writeOffUser));
        downPickDataSalesComInfo.setNetReceiptsTotal(Double.parseDouble(netReceiptsUser));
        return downPickDataSalesComInfo;
    }


    public DownPickDataDispoInfo  copyDownToDispo(DownPickDataDispoInfo downPickDataDispoInfo,DownPickData downPickData){
        downPickDataDispoInfo.setType(downPickData.getType());
        downPickDataDispoInfo.setWhgridName(downPickData.getWhgridname());
        downPickDataDispoInfo.setSubCode(downPickData.getSubcode());
        downPickDataDispoInfo.setMemo(downPickData.getMemo());
        downPickDataDispoInfo.setSubclass(downPickData.getSubtype());
        downPickDataDispoInfo.setTotalIncome(downPickData.getPartfees());
        String numbers[] = downPickData.getMemo().split(",");
        String orderNumber = numbers[0].substring(numbers[0].indexOf(":")+1).trim();
        String orderRealIncome =  numbers[1].substring(numbers[1].indexOf(":")+1).trim();
        String cancelNumber = numbers[2].substring(numbers[2].indexOf(":")+1).trim();
        String cancelRealIncome =  numbers[3].substring(numbers[3].indexOf(":")+1).trim();
        downPickDataDispoInfo.setOrderNumber(Long.valueOf(orderNumber));
        downPickDataDispoInfo.setOrderRealIncome(Double.parseDouble(orderRealIncome));
        downPickDataDispoInfo.setCancelNumber(Long.valueOf(cancelNumber));
        downPickDataDispoInfo.setCancelRealIncome(Double.parseDouble(cancelRealIncome));
        return downPickDataDispoInfo;

    }


    /**
     * 网格下拨销售类明细报表副页面
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queDownDataForSales(QueDownPickDataReq req, QueDownPickSalesDataResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridCode(),"请选择网格");
        CheckUtils.checkEmpty(req.getMonth(),"月份不能为空");
        CheckUtils.checkEmpty(req.getSubCode(),"子类不能为空");
        //开始查数据
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select t.SALESPKGNAME promotion,t.SELFCNT developMum,t.othercnt doorRece,t.INCOME netReceiptsMon,t.partfees totalIncome ");
        sqlBuffer.append(" from nods.wg_total_busi_det t");
        sqlBuffer.append(" where t.cycleid = ? and t.whgridcode = ? and t.groupcode = ? ");
        params.add(req.getMonth());
        params.add(req.getGridCode());
        params.add(req.getSubCode());
        List<BusinessNumInfo> businessNumInfoList = persistentService.find(sqlBuffer.toString(),
                BusinessNumInfo.class,params.toArray());
        if(businessNumInfoList != null ){
            resp.setBusinessNumInfoList(businessNumInfoList);
            //求总计
            List<Object> paramList = new ArrayList();
            StringBuffer sql = new StringBuffer();
            sql.append(" select ROUND(coalesce(sum(PARTFEES),0),2) totalIncome from nods.wg_total_busi_det t ");
            sql.append(" where t.cycleid = ? and t.whgridcode = ? and t.groupcode = ? ");
            paramList.add(req.getMonth());
            paramList.add(req.getGridCode());
            paramList.add(req.getSubCode());
            String totalIncome = persistentService.findUniqueObject(sql.toString(),paramList.toArray()).toString();
            resp.setTotalIncome(Double.parseDouble(totalIncome));
        }
        return returnInfo;
    }

    /**
     * 维护类二级界面查询
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queDownDataForDefend(QueDownPickDataReq req, QueDownPickDefendDataResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridCode(),"请选择网格");
        CheckUtils.checkEmpty(req.getMonth(),"月份不能为空");
        CheckUtils.checkEmpty(req.getSubCode(),"子类不能为空");
        //开始查数据
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select t.PATCHNAME redisenceName,t.PUBLIC_NUMS publicNumber,t.HOTEL_NUMS hotelNumber,t.INUMS busenterNumber");
        sqlBuffer.append(" from nods.wg_total_wh_det t");
        sqlBuffer.append(" where t.cycleid = ? and t.whgridcode = ? and t.permark = ? ");
        params.add(req.getMonth());
        params.add(req.getGridCode());
        params.add(req.getSubCode());
        List<WriteOffInfo> writeOffInfoList = persistentService.find(sqlBuffer.toString(),
                WriteOffInfo.class,params.toArray());
        if(writeOffInfoList != null ){
            resp.setWriteOffInfoList(writeOffInfoList);
        }
        return returnInfo;
    }

    /**
     * 一次性维护二级界面
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queDownDataForDispo(QueDownPickDataReq req, QueDownPickDispoDataResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridCode(),"请选择网格");
        CheckUtils.checkEmpty(req.getMonth(),"月份不能为空");
        CheckUtils.checkEmpty(req.getSubCode(),"子类不能为空");
        //开始查数据
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select t.PATCHNAME redisenceName,t.CNT installNumber ");
        sqlBuffer.append(" from nods.wg_total_once_det t");
        sqlBuffer.append(" where t.cycleid = ? and t.whgridcode = ? and t.rfeecode = ? ");
        params.add(req.getMonth());
        params.add(req.getGridCode());
        params.add(req.getSubCode());
        List<OrderNumsInfo> orderNumsInfoList = persistentService.find(sqlBuffer.toString(),
                OrderNumsInfo.class,params.toArray());
        resp.setOrderNumsInfoList(orderNumsInfoList);

        return returnInfo;
    }

    /**
     * 奖罚二级界面接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queDownDataForAward(QueDownPickDataReq req, QueDownPickAwardDataResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridCode(),"请选择网格");
        CheckUtils.checkEmpty(req.getMonth(),"月份不能为空");
        CheckUtils.checkEmpty(req.getSubCode(),"子类不能为空");
        //开始查数据
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select t.PARTFEES awardPunishMoney,t.RFEECODE awardPunishReason  ");
        sqlBuffer.append(" from nods.wg_total_cf_det t");
        sqlBuffer.append(" where t.tMONTH = ? ");
        sqlBuffer.append(" and t.WHGRIDCODE = ?  and t.RFEECODE = ? order by t.PARTFEES ");
        params.add(req.getMonth());
        params.add(req.getGridCode());
        params.add(req.getSubCode());
        List<AwardPunishInfo> awardPunishInfoList = persistentService.find(sqlBuffer.toString(),
                AwardPunishInfo.class,params.toArray());
        resp.setAwardPunishInfoList(awardPunishInfoList);

        return returnInfo;
    }

    /**
     * 直属5.2科目类收入报表查询接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queSubjectIncome(QueMonReceDataReq req,QueMonReceDataResp resp) throws Exception{

        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridCode(),"请选择网格");
        CheckUtils.checkEmpty(req.getMonth(),"月份不能为空");
        CheckUtils.checkEmpty(String.valueOf(req.getCurrentPage()),"请求页码不能为空");
        resp.setPagesize(req.getPageSize());
        resp.setCurrentPage(req.getCurrentPage());
        //开始查数据
        Page page = new Page();
        page.setPageSize(req.getPageSize());
        page.setPageNo(req.getCurrentPage());
        String sMonth = req.getMonth();
        String endMonth = sMonth.replace(sMonth.charAt(sMonth.length() - 1) + "","1");
        String city = loginInfo.getCity();
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select * from (select  t.whgridname as gridName,psc.secondname as seconIncomSub,psc.thirdname as levelThreeSub,PSC.INCOMENAME as levelFourSub, ");
        sqlBuffer.append(" sum(case when t.tmonth = ? then t.ifees else 0 end) as currCollection,");
        sqlBuffer.append(" sum(t.ifees) as annualIncome from NEDS.TW2_INCOME_MONTH_WHGRID t, ETL.PRV_SUBJECT_CONFIG@TO_EDA PSC ");
        sqlBuffer.append(" where t.city=? and t.tmonth>=? and t.tmonth<=?  ");
        sqlBuffer.append(" AND t.RFEECODE=PSC.INCOMECODE AND PSC.ISINCOME = 'Y' ");
        sqlBuffer.append(" and t.whgridcode =? group by t.whgridname, PSC.INCOMENAME,psc.secondname,psc.thirdname order by psc.secondname ) v ");
        params.add(req.getMonth());
        params.add(city);
        params.add(endMonth);
        params.add(sMonth);
        params.add(req.getGridCode());
        page = persistentService.find(page,sqlBuffer.toString(),
                SubMonReceDataInfo.class,params.toArray());
        if(page.getResult() != null && page != null ){
            List<SubMonReceDataInfo> subMonReceDataInfoList = page.getResult();
            resp.setDataInfoList(subMonReceDataInfoList);
            resp.setTotalsize(page.getTotalCount());
            resp.setTotalpage(page.getTotalPages());
        }
        //去查总数
        resp.setCurrentTitle("本期收入(含税)");
        resp.setAnnuaTitle("全年收入(含税)");
        StringBuffer sql = new StringBuffer();
        sql.append(" select sum(v.currCollection) as currCollection,sum(v.annualIncome )  as annualIncome  from (select  t.whgridname as gridName,psc.secondname as seconIncomSub,psc.thirdname as levelThreeSub,PSC.INCOMENAME as levelFourSub, ");
        sql.append(" sum(case when t.tmonth = ? then t.ifees else 0 end) as currCollection,");
        sql.append(" sum(t.ifees) as annualIncome from NEDS.TW2_INCOME_MONTH_WHGRID t, ETL.PRV_SUBJECT_CONFIG@TO_EDA PSC ");
        sql.append(" where t.city=? and t.tmonth>=? and t.tmonth<=?  ");
        sql.append(" AND t.RFEECODE=PSC.INCOMECODE AND PSC.ISINCOME = 'Y' ");
        sql.append(" and t.whgridcode =? group by t.whgridname, PSC.INCOMENAME,psc.secondname,psc.thirdname order by psc.secondname ) v ");
     /*   sql.append(" select ROUND(sum(case when t.tmonth=? then t.ifees else 0 end),2) as currCollection, ROUND(sum(t.ifees),2) as annualIncome ");
        sql.append(" from NEDS.TW2_INCOME_MONTH_WHGRID t,ETL.PRV_SUBJECT_CONFIG@TO_EDA PSC where t.city=?  ");
        sql.append(" and t.tmonth>=? and t.tmonth<=? AND t.RFEECODE=PSC.INCOMECODE AND PSC.ISINCOME = 'Y' and t.whgridcode =? ");*/
        List<SubMonReceDataInfo> sumSub = persistentService.find(sql.toString(),
                SubMonReceDataInfo.class,params.toArray());
        if(sumSub != null){
            SubMonReceDataInfo subMonReceDataInfo = sumSub.get(0);
            resp.setCurrCollections(subMonReceDataInfo.getCurrCollection());
            resp.setAnnualIncomes(subMonReceDataInfo.getAnnualIncome());
        }
        return returnInfo;
    }

    /**
     * 直属5.2部门类收入报表查询接口 queDepartmentIncome
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queDepartIncome(QueMonReceDataReq req,QueMonDeptmantResp resp) throws Exception{

        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridCode(),"请选择网格");
        CheckUtils.checkEmpty(req.getMonth(),"月份不能为空");
        CheckUtils.checkEmpty(String.valueOf(req.getCurrentPage()),"请求页码不能为空");
        resp.setPagesize(req.getPageSize());
        resp.setCurrentPage(req.getCurrentPage());
        //开始查数据
        Page page = new Page();
        page.setPageSize(req.getPageSize());
        page.setPageNo(req.getCurrentPage());
        String sdate = req.getMonth() + "01";
        String sMonth = req.getMonth();
        String SMonthDay = sMonth.replace(sMonth.charAt(sMonth.length() - 1) + "","1");
        SMonthDay = SMonthDay + "01";
        String city = loginInfo.getCity();
        //转化拿到当月最后一天
        String edate = req.getMonth().substring(0,4) + "-" + req.getMonth().substring(4,6);
        edate = getLastDayOfMonth(edate);

        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select * from ( select whgridname as gridName,get_deptname(a.deptid) as department,");
        sqlBuffer.append(" (SELECT c.mname FROM PRV_SYSPARAM@TO_DGBOSS_CRM C ");
        sqlBuffer.append(" WHERE a.OPCODE=C.MCODE AND C.GCODE = 'SYS_OPCODE') as operType, ");
        sqlBuffer.append(" SUM(case when TO_CHAR(A.TJDATE, 'YYYYMMDD') >= ? AND TO_CHAR(A.TJDATE, 'YYYYMMDD') <=?  then A.FEES else 0 end) as currCollection, ");
        sqlBuffer.append(" SUM(A.FEES) as annualIncome FROM  NEDS.TW1_STA_BILLFEE_DZ_DG A WHERE A.TJDATE>=to_date(?,'YYYYMMDD') ");
        sqlBuffer.append(" and A.TJDATE< = to_date(?,'YYYYMMDD')  AND A.CITY=?  and a.whgridcode=?  group by a.whgridname,a.deptid,a.OPCODE  order by a.deptid ) v ");
        params.add(sdate);
        params.add(edate);
        params.add(SMonthDay);
        params.add(edate);
        params.add(city);
        params.add(req.getGridCode());
        page = persistentService.find(page,sqlBuffer.toString(),
                DeptMonReceDataInfo.class,params.toArray());
        if(page.getResult() != null && page != null ){
            List<DeptMonReceDataInfo> deptMonReceDataInfoList = page.getResult();
            resp.setDataInfoList(deptMonReceDataInfoList);
            resp.setTotalsize(page.getTotalCount());
            resp.setTotalpage(page.getTotalPages());
        }
        //去查总数
        resp.setCurrentTitle("本期收款(含税)");
        resp.setAnnuaTitle("全年收款(含税)");
        StringBuffer sql = new StringBuffer();
        sql.append(" select sum(v.currCollection) as currCollection,sum(v.annualIncome )  as annualIncome from ( select whgridname as gridName,get_deptname(a.deptid) as department,");
        sql.append(" (SELECT c.mname FROM PRV_SYSPARAM@TO_DGBOSS_CRM C ");
        sql.append(" WHERE a.OPCODE=C.MCODE AND C.GCODE = 'SYS_OPCODE') as operType, ");
        sql.append(" SUM(case when TO_CHAR(A.TJDATE, 'YYYYMMDD') >= ? AND TO_CHAR(A.TJDATE, 'YYYYMMDD') <=?  then A.FEES else 0 end) as currCollection, ");
        sql.append(" SUM(A.FEES) as annualIncome FROM  NEDS.TW1_STA_BILLFEE_DZ_DG A WHERE A.TJDATE>=to_date(?,'YYYYMMDD') ");
        sql.append(" and A.TJDATE< = to_date(?,'YYYYMMDD')  AND A.CITY=?  and a.whgridcode=?  group by a.whgridname,a.deptid,a.OPCODE  order by a.deptid ) v ");
  /*      sql.append(" select ROUND(SUM(case when TO_CHAR(A.TJDATE, 'YYYYMMDD') >= ? AND   ");
        sql.append(" TO_CHAR(A.TJDATE, 'YYYYMMDD') <=? then A.FEES else 0 end) ,2) as currCollection,  ");
        sql.append(" ROUND(SUM(A.FEES),2) as annualIncome FROM  NEDS.TW1_STA_BILLFEE_DZ_DG A WHERE A.TJDATE>=to_date(?,'YYYYMMDD') ");
        sql.append(" and A.TJDATE<to_date(?,'YYYYMMDD')   AND A.CITY=? and a.whgridcode=? ");*/
        List<DeptMonReceDataInfo> sumSub = persistentService.find(sql.toString(),
                DeptMonReceDataInfo.class,params.toArray());
        if(sumSub != null){
            DeptMonReceDataInfo deptMonReceDataInfo = sumSub.get(0);
            resp.setCurrCollections(deptMonReceDataInfo.getCurrCollection());
            resp.setAnnualIncomes(deptMonReceDataInfo.getAnnualIncome());
        }
        return returnInfo;
    }


    /**
     * 直属5.2促銷类收入报表查询接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo quePromotionIncome(QueMonReceDataReq req,QueMonPromResp resp) throws Exception{

        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridCode(),"请选择网格");
        CheckUtils.checkEmpty(req.getMonth(),"月份不能为空");
        CheckUtils.checkEmpty(String.valueOf(req.getCurrentPage()),"请求页码不能为空");
        resp.setPagesize(req.getPageSize());
        resp.setCurrentPage(req.getCurrentPage());
        //开始查数据
        Page page = new Page();
        page.setPageSize(req.getPageSize());
        page.setPageNo(req.getCurrentPage());
        String sdate = req.getMonth() + "01";
        String sMonth = req.getMonth();
        String SMonthDay = sMonth.replace(sMonth.charAt(sMonth.length() - 1) + "","1");
        SMonthDay = SMonthDay + "01";
        //转化拿到当月最后一天
        String edate = req.getMonth().substring(0,4) + "-" + req.getMonth().substring(4,6);
        edate = getLastDayOfMonth(edate);
        String city = loginInfo.getCity();
        PersistentService persistentService = SpringBeanUtil.getDBPersistentService();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select * from (select whgridname as gridName,get_deptname(a.deptid) as department, ");
        sqlBuffer.append(" (SELECT c.mname FROM PRV_SYSPARAM@TO_DGBOSS_CRM C  WHERE a.OPCODE=C.MCODE AND C.GCODE = 'SYS_OPCODE') as operType,");
        sqlBuffer.append(" nvl(get_salespkgname(t.salespkgid),-1) as promConceName,SUM(case when A.OPTIME>= ? AND A.OPTIME<=? then A.PAYFEES else 0 end) as currCollection, ");
        sqlBuffer.append(" SUM(A.PAYFEES) as annualIncome FROM  EDS_E103_MONTH_REPORT_53 A left join TO_BIZ_PKGORDER_dg_").append(edate).append("  t ");
        sqlBuffer.append(" on a.salespkgid=t.salespkginsid and t.city=? WHERE A.OPTIME>=? and A.OPTIME<=? ");
        sqlBuffer.append(" AND A.CITY=?  and a.whgridcode=? group by a.whgridname,a.deptid,a.OPCODE,t.salespkgid  order by a.deptid ) v ");
        params.add(sdate);
        params.add(edate);
        params.add(city);
        params.add(SMonthDay);
        params.add(edate);
        params.add(city);
        params.add(req.getGridCode());
        page = persistentService.find(page,sqlBuffer.toString(),
                PromMonReceDataInfo.class,params.toArray());
        if(page.getResult() != null && page != null ){
            List<PromMonReceDataInfo> promMonReceDataInfoList = page.getResult();
            resp.setDataInfoList(promMonReceDataInfoList);
            resp.setTotalsize(page.getTotalCount());
            resp.setTotalpage(page.getTotalPages());
        }
        //去查总数
        resp.setCurrentTitle("本期收款(含税)");
        resp.setAnnuaTitle("全年收款(含税)");
        StringBuffer sql = new StringBuffer();
    /*    sql.append(" select ROUND(SUM(case when A.OPTIME>= ? AND  A.OPTIME<= ? then A.PAYFEES else 0 end),2) as currCollection, ");
        sql.append(" ROUND(SUM(A.PAYFEES),2) as annualIncome FROM EDS_E103_MONTH_REPORT_53 A ");
        sql.append(" left join TO_BIZ_PKGORDER_dg_").append(edate).append("  t ");
        sql.append(" on a.salespkgid=t.salespkginsid  and t.city=?  WHERE A.OPTIME>=?  ");
        sql.append(" and  A.OPTIME<=?    AND A.CITY=?   and a.whgridcode=?    ");*/

        sql.append(" select sum(v.currCollection) as currCollection,sum(v.annualIncome )  as annualIncome from (select whgridname as gridName,get_deptname(a.deptid) as department, ");
        sql.append(" (SELECT c.mname FROM PRV_SYSPARAM@TO_DGBOSS_CRM C  WHERE a.OPCODE=C.MCODE AND C.GCODE = 'SYS_OPCODE') as operType,");
        sql.append(" nvl(get_salespkgname(t.salespkgid),-1) as promConceName,SUM(case when A.OPTIME>= ? AND A.OPTIME<=? then A.PAYFEES else 0 end) as currCollection, ");
        sql.append(" SUM(A.PAYFEES) as annualIncome FROM  EDS_E103_MONTH_REPORT_53 A left join TO_BIZ_PKGORDER_dg_").append(edate).append("  t ");
        sql.append(" on a.salespkgid=t.salespkginsid and t.city=? WHERE A.OPTIME>=? and A.OPTIME<=? ");
        sql.append(" AND A.CITY=?  and a.whgridcode=? group by a.whgridname,a.deptid,a.OPCODE,t.salespkgid  order by a.deptid ) v ");
        List<PromMonReceDataInfo> sumSub = persistentService.find(sql.toString(),
                PromMonReceDataInfo.class,params.toArray());
        if(sumSub != null){
            PromMonReceDataInfo promMonReceDataInfo = sumSub.get(0);
            resp.setCurrCollections(promMonReceDataInfo.getCurrCollection());
            resp.setAnnualIncomes(promMonReceDataInfo.getAnnualIncome());
        }
        return returnInfo;

    }

    //获取当月的最后一天
    public static String getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
        int month = Integer.parseInt(yearMonth.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(cal.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getLastDayOfMonth("2019-05"));
    }

}
