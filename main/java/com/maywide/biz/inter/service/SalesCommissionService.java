package com.maywide.biz.inter.service;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.constant.QueConstant;
import com.maywide.biz.inter.pojo.queSalesCommTitle.queSalesCommTitleReq;
import com.maywide.biz.inter.pojo.queSalesCommTitle.queSalesCommTitleResp;
import com.maywide.biz.inter.pojo.queSalesCommTitle.salesTitleInfo;
import com.maywide.biz.inter.pojo.queSalesCommission.*;
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
import java.util.Date;
import java.util.List;

/**
 * Created by lisongkang on 2019/5/17 0001.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SalesCommissionService extends CommonService {
    private static Logger log = LoggerFactory.getLogger(SalesCommissionService.class);
    @Autowired
    private ParamService paramService;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private RuleService ruleService;

    /**
     * 销售提成titil加载接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queSalesTitleList(queSalesCommTitleReq req, queSalesCommTitleResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getCity(), "匹配条件[city]不能为空！");

        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT fieldname,name from biz_sales_title where city = ?");
        params.add(req.getCity());
        List<salesTitleInfo> salesTitleInfoList = getDAO().findObjectList(sqlBuffer.toString(),
                salesTitleInfo.class,params.toArray());
        resp.setSalesTitleInfoList(salesTitleInfoList);
        return returnInfo;
    }

    /**
     * 查询中山销售提成按日数据
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public ReturnInfo queDaySalesCommission(queDaySalesCommReq req, queDaySalesCommResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(String.valueOf(req.getOperid()), "操作员不能为空！");
        CheckUtils.checkEmpty(req.getStadate(), "日期不能为空！");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d1 = sdf.parse(req.getStadate());
        Date d2 = sdf.parse(req.getEndDate());
        long result = d1.getTime()-d2.getTime();
        if(result > 0){
            CheckUtils.checkNull(null, "起始日期不能大于结束日期");
        }
        //查询数据
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT A.TCTYPENAME,A.PRICE,A.NUMS,A.FEES FROM DM.TM_TCXQ_CMMS_D_V A WHERE 1=1 ");
        sqlBuffer.append(" AND A.STADATE >= ? AND A.STADATE <= ? AND A.OPERATOR = ?");
        params.add(req.getStadate());
        params.add(req.getEndDate());
        params.add(String.valueOf(req.getOperid()));
        if (null != req.getDeptid() && !"".equals(req.getDeptid())) {
            sqlBuffer.append(" AND A.OPRDEP = ?");
            params.add(req.getDeptid());
        }
        List<salesCommDayInfo> salesCommDayInfoList  =  SpringBeanUtil.getPersistentService().find(sqlBuffer.toString(),
                salesCommDayInfo.class, params.toArray());
        resp.setSalesCommDayInfoList(salesCommDayInfoList);
        Double totalsum = 0.0;
        for (salesCommDayInfo salesCommDayInfo:salesCommDayInfoList
             ) {
            Double constcost = Double.parseDouble(salesCommDayInfo.getFees());
            totalsum = totalsum + constcost;
        }
        resp.setTotalsum(totalsum);
       /* List<Object> paramsnew = new ArrayList();
        StringBuffer sqlBuffernew = new StringBuffer();
        sqlBuffernew.append("SELECT fieldname,name from biz_sales_title where city = ? and type ='day' ");
        paramsnew.add(req.getCity());
        List<salesTitleInfo> salesTitleInfoList = getDAO().findObjectList(sqlBuffernew.toString(),
                salesTitleInfo.class,paramsnew.toArray());
        resp.setSalesTitleInfoList(salesTitleInfoList);*/
        return returnInfo;
    }


    /**
     * 查询中山销售提成按月数据
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public ReturnInfo queMonthSalesCommission(queMonthSalesCommReq req, queMonthSalesCommResp resp) throws Exception{
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, QueConstant.CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        //CheckUtils.checkEmpty(req.getCity(), "匹配条件[city]不能为空！");
        CheckUtils.checkEmpty(String.valueOf(req.getOperid()), "操作员不能为空！");
        CheckUtils.checkEmpty(req.getStaMonth(), "月份不能为空！");
        //查询数据
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT A.TCTYPENAME,AVG(A.PRICE) AS PRICE,SUM(A.NUMS) AS NUMS,SUM(A.FEES) AS FEES FROM DM.TM_TCXQ_CMMS_D_V A WHERE 1=1 ");
        sqlBuffer.append(" AND A.STADATE LIKE ? AND A.OPERATOR = ?");
        String staMonth = req.getStaMonth() + "%";
        params.add(staMonth);
        params.add(String.valueOf(req.getOperid()));
        if (null != req.getDeptid() && !"".equals(req.getDeptid())) {
            sqlBuffer.append(" AND A.OPRDEP = ?");
            params.add(req.getDeptid());
        }
        sqlBuffer.append(" GROUP BY A.TCTYPENAME");
        List<salesCommMonthInfo> salesCommMonthInfoList  =  SpringBeanUtil.getPersistentService().find(sqlBuffer.toString(),
                salesCommMonthInfo.class, params.toArray());
        resp.setSalesCommMonthInfoList(salesCommMonthInfoList);
        Double totalsum = 0.0;
        for (salesCommMonthInfo salesCommMonthInfo:salesCommMonthInfoList
        ) {
            Double constcost = Double.parseDouble(salesCommMonthInfo.getFees());
            totalsum = totalsum + constcost;
        }
        resp.setTotalsum(totalsum);
        //提供最新的月份数据
        Calendar calendar = Calendar.getInstance();
        int yearxt = calendar.get(Calendar.YEAR);
        int monthxt = calendar.get(Calendar.MONTH) + 1;
        String monthxtt = String.valueOf(monthxt);
        if(String.valueOf(monthxt).length() == 1) {
            monthxtt = "0" + monthxtt;
        }
        String month = String.valueOf(yearxt) + monthxtt;
        List<Object> paramsnew = new ArrayList();
        StringBuffer sqlBuffernew = new StringBuffer();
        sqlBuffernew.append(" SELECT A.TCTYPENAME,AVG(A.PRICE) AS PRICE,SUM(A.NUMS) AS NUMS,SUM(A.FEES) AS FEES FROM DM.TM_TCXQ_CMMS_D_V A WHERE 1=1 ");
        sqlBuffernew.append(" AND A.STADATE LIKE ? AND A.OPERATOR = ? ");
        month = month +  "%";
        paramsnew.add(month);
        paramsnew.add(String.valueOf(req.getOperid()));
        if (null != req.getDeptid() && !"".equals(req.getDeptid())) {
            sqlBuffernew.append(" AND A.OPRDEP = ?");
            paramsnew.add(req.getDeptid());
        }
        sqlBuffernew.append(" GROUP BY A.TCTYPENAME");
        List<salesCommMonthInfo> salesCommMonthInfoListNew  =  SpringBeanUtil.getPersistentService().find(sqlBuffernew.toString(),
                salesCommMonthInfo.class, paramsnew.toArray());
        Double currentTotalsum = 0.0;
        for (salesCommMonthInfo salesCommMonthInfo:salesCommMonthInfoListNew
        ) {
            Double constcost = Double.parseDouble(salesCommMonthInfo.getFees());
            currentTotalsum = currentTotalsum + constcost;
        }
        resp.setCurrentMonth(String.valueOf(monthxt));
        resp.setCurrentTotalsum(currentTotalsum);
        return returnInfo;
    }


}
