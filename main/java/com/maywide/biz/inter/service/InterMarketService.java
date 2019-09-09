package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.pojo.sta.CntBO;
import com.maywide.biz.core.pojo.sta.TmpStadataBO;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.bizuserfocus.AddUserFocusReq;
import com.maywide.biz.inter.pojo.bizuserfocus.DeleteUserFocusReq;
import com.maywide.biz.inter.pojo.bizuserfocus.QueryUserFocusReq;
import com.maywide.biz.inter.pojo.bizuserfocus.QueryUserFocusResp;
import com.maywide.biz.inter.pojo.dealcustmarketing.DealCustMarketingInterReq;
import com.maywide.biz.inter.pojo.queincome.IncomeRanksBO;
import com.maywide.biz.inter.pojo.queincome.IncomesBO;
import com.maywide.biz.inter.pojo.queincome.QueIncomeInterReq;
import com.maywide.biz.inter.pojo.queincome.QueIncomeInterResp;
import com.maywide.biz.inter.pojo.queordercnt.OrdercntsBO;
import com.maywide.biz.inter.pojo.queordercnt.QueOrdercntInterReq;
import com.maywide.biz.inter.pojo.queordercnt.QueOrdercntInterResp;
import com.maywide.biz.inter.pojo.queorderstatis.OrderStatisBO;
import com.maywide.biz.inter.pojo.queorderstatis.QueOrderStatisReq;
import com.maywide.biz.inter.pojo.queorderstatis.QueOrderStatisResp;
import com.maywide.biz.inter.pojo.querycustmarketing.CustMarketingInfoBO;
import com.maywide.biz.inter.pojo.querycustmarketing.QueryCustMarketingInterReq;
import com.maywide.biz.inter.pojo.querycustmarketing.QueryCustMarketingInterResp;
import com.maywide.biz.inter.pojo.querymarketbatch.MarketBatchInfoBO;
import com.maywide.biz.inter.pojo.querymarketbatch.QueryMarketBatchInterReq;
import com.maywide.biz.inter.pojo.querymarketbatch.QueryMarketBatchInterResp;
import com.maywide.biz.inter.pojo.savecustmarketing.SaveCustMarketingInterReq;
import com.maywide.biz.inter.pojo.savecustmarketing.SaveCustMarketingInterResp;
import com.maywide.biz.market.entity.BizUserFocus;
import com.maywide.biz.market.entity.CustMarketing;
import com.maywide.biz.market.entity.CustTransmit;
import com.maywide.biz.market.entity.MarketBatch;
import com.maywide.biz.market.entity.ResPatch;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.dao.support.Page;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional(rollbackFor = BusinessException.class)
public class InterMarketService extends CommonService {

    @Autowired
    private ParamService paramService;
    @Autowired
    private PubService pubService;

    @Transactional(readOnly = true)
    public ReturnInfo queryMarketBatch(QueryMarketBatchInterReq req,
            QueryMarketBatchInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        // HttpSession s = GlobalCacheMgr.getSession();
        // System.out.print(s);
        // LoginInfo loginInfo1 =
        // (LoginInfo)GlobalCacheMgr.getSession().getAttribute(SecurityFilter.LOGIN_INFO);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求对象不能为空");
        // CheckUtils.checkEmpty(req.getCity(), "匹配条件[city]不能为空！");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        // 拼sql
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT  ");
        sql.append("   t.recid, ");
        sql.append("   t.batchno, ");
        sql.append("   t.areaids, ");
        sql.append("   (SELECT  ");
        sql.append("     GROUP_CONCAT(DISTINCT NAME)  ");
        sql.append("   FROM ");
        sql.append("     prv_area a  ");
        sql.append("   WHERE INSTR( ");
        sql.append("       CONCAT(',', t.AREAIDS, ','), ");
        sql.append("       CONCAT(',', a.areaid, ',') ");
        sql.append("     ) > 0  ");
        sql.append("     AND t.AREAIDS IS NOT NULL  ");
        sql.append("     AND a.AREAID IS NOT NULL) areanames, ");
        sql.append("   t.knowid, ");
        sql.append("   (SELECT  ");
        sql.append("     k.knowname  ");
        sql.append("   FROM ");
        sql.append("     prd_salespkg_know k  ");
        sql.append("   WHERE k.knowid = t.KNOWID) knowname, ");
        sql.append("   t.nums, ");
        sql.append("   t.STATUS, ");
        sql.append("   t.appdate, ");
        sql.append("   DATE_FORMAT(t.APPDATE, '%Y-%m-%d %T') sappdate, ");
        sql.append("   t.operid, ");
        sql.append("   t.city  ");
        sql.append(" FROM ");
        sql.append("   BIZ_MARKET_BATCH T ");
        sql.append(" WHERE 1=1 ");

        if (StringUtils.isNotBlank(req.getAreaids())) {
            sql.append("   AND T.areaids = ? ");// 这里是不是要写包含--TODO
            paramList.add(req.getAreaids());
        }
        if (StringUtils.isNotBlank(req.getBatchno())) {
            sql.append("   AND T.batchno = ? ");
            paramList.add(req.getBatchno());
        }
        if (StringUtils.isNotBlank(req.getBeginDate())) {
            sql.append("   AND t.appdate>=STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s') ");
            paramList.add(req.getBeginDate());
        }
        if (StringUtils.isNotBlank(req.getCity())) {
            sql.append("   AND T.city = ? ");
            paramList.add(req.getCity());
        }
        if (StringUtils.isNotBlank(req.getEndDate())) {
            sql.append("   AND t.appdate<STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s') ");
            paramList.add(req.getEndDate());
        }
        if (StringUtils.isNotBlank(req.getKnowname())) {
            sql.append("   AND EXISTS  ");
            sql.append("   (SELECT  ");
            sql.append("     1  ");
            sql.append("   FROM ");
            sql.append("     prd_salespkg_know k  ");
            sql.append("   WHERE k.knowid = t.KNOWID  ");
            sql.append("     AND k.knowname = ?) ");// 这里是不是要写成like--TODO
            paramList.add(req.getKnowname());
        }
        if (StringUtils.isNotBlank(req.getOperid())) {
            sql.append("   AND t.operid = ? ");
            paramList.add(req.getOperid());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            sql.append("   AND t.STATUS = ? ");
            paramList.add(req.getStatus());
        }

        sql.append(" ) v ");

        Page page = new Page();
        page.setPageSize(10);
        if (StringUtils.isNotBlank(req.getPagesize())) {
            page.setPageSize(Integer.valueOf(req.getPagesize()));
        }
        if (StringUtils.isNotBlank(req.getCurrentPage())) {
            page.setPageNo(Integer.valueOf(req.getCurrentPage()));
        }

        getDAO().clear();
        page = getDAO().find(page, sql.toString(), MarketBatchInfoBO.class,
                paramList.toArray());

        List<MarketBatchInfoBO> marketBatchList = page.getResult();

        resp.setPagesize(BeanUtil.object2String(page.getPageSize()));
        resp.setCurrentPage(BeanUtil.object2String(page.getCntPageNo()));
        resp.setTotalRecords(BeanUtil.object2String(page.getTotalCount()));
        resp.setMarketBatchs(marketBatchList);

        return returnInfo;
    }

    @Transactional(readOnly = true)
    public ReturnInfo queryCustMarketing(QueryCustMarketingInterReq req,
            QueryCustMarketingInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求对象不能为空");
        // CheckUtils.checkEmpty(req.getBatchno(), "匹配条件[batchno]不能为空！");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        // 拼sql
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT markid, ");
        sql.append("        batchno, ");
        sql.append("        custid, ");
        sql.append("        NAME, ");
        sql.append("        linkphone, ");
        sql.append("        houseid, ");
        sql.append("        whladdr, ");
        sql.append("        areaid, ");
        sql.append("        (select a.name from prv_area a where a.areaid = t.areaid) areaname, ");
        sql.append("        city, ");
        sql.append("        code2name(t.city,'PRV_CITY') cityname, ");
        sql.append("        ptachid, ");
        sql.append("        (SELECT p.patchname FROM res_patch p WHERE p.patchid = t.PTACHID) patchname, ");
        sql.append("        pri, ");
        sql.append("        code2name(t.pri,'BIZ_CUST_MARKETING_PRI') priname, ");
        sql.append("        knowid, ");
        sql.append("        (SELECT k.knowname FROM prd_salespkg_know k WHERE k.knowid = t.KNOWID) knowname, ");
        sql.append("        areamger, ");
        sql.append("        (SELECT o.name FROM prv_operator o WHERE o.operid = t.AREAMGER) areamgername, ");
        sql.append("        mgerphone, ");
        sql.append("        appdate, ");
        sql.append("        operid, ");
        sql.append("        (select o.name from prv_operator o where o.operid=t.operid) opername, ");
        sql.append("        dealstatus, ");
        sql.append("        code2name(t.dealstatus,'BIZ_CUST_MARKETING_DEALSTATUS') dealstatusname, ");
        sql.append("        alnums, ");
        sql.append("        result, ");
        sql.append("        code2name(t.result,'BIZ_CUST_MARKETING_RESULT') resultname, ");
        sql.append("        resultexp ");
        sql.append("   FROM biz_cust_marketing t ");
        sql.append("  where 1 = 1 ");
        sql.append("	AND t.CITY = ?");
        paramList.add(loginInfo.getCity());
        if (StringUtils.isNotBlank(req.getMarkid())) {
            sql.append(" and t.markid = ? ");
            paramList.add(req.getMarkid());
        }
        if (StringUtils.isNotBlank(req.getBatchno())) {
            sql.append(" and t.batchno = ? ");
            paramList.add(req.getBatchno());
        }
        if (StringUtils.isNotBlank(req.getDealstatus())) {
            sql.append(" and t.dealstatus = ? ");
            paramList.add(req.getDealstatus());
        }
        if (StringUtils.isNotBlank(req.getAreamger())) {
            sql.append(" and t.areamger = ? ");
            paramList.add(req.getAreamger());
        }
        if (StringUtils.isNotBlank(req.getCustname())) {
            sql.append(" and t.name like ? ");
            paramList.add(req.getCustname() + "%");
        }
        if (StringUtils.isNotBlank(req.getAddr())) {
            sql.append(" and t.whladdr like ? ");
            paramList.add("%" + req.getAddr() + "%");
        }

        sql.append(" ) v ");

        Page page = new Page();
        page.setPageSize(10);
        if (StringUtils.isNotBlank(req.getPagesize())) {
            page.setPageSize(Integer.valueOf(req.getPagesize()));
        }
        if (StringUtils.isNotBlank(req.getCurrentPage())) {
            page.setPageNo(Integer.valueOf(req.getCurrentPage()));
        }

        getDAO().clear();
        page = getDAO().find(page, sql.toString(), CustMarketingInfoBO.class,
                paramList.toArray());

        List<CustMarketingInfoBO> custMarketingcList = page.getResult();

        resp.setPagesize(req.getPagesize());
        resp.setCurrentPage(req.getCurrentPage());
        resp.setTotalRecords(String.valueOf(page.getTotalCount()));
        resp.setCustMarketings(custMarketingcList);

        return returnInfo;
    }

    public ReturnInfo dealCustMarketing(DealCustMarketingInterReq req)
            throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getMarkid(), "匹配条件[markid]不能为空");
        CheckUtils.checkEmpty(req.getDealType(), "匹配条件[dealType]不能为空");

        if (req.getDealType().trim()
                .equals(BizConstant.DealCustMarketingDealtype.TRANS)) {
            // 处理类型是转派，转派的网格经理ID不能为空
            if (StringUtils.isBlank(req.getAreamger())) {
                throw new BusinessException("处理类型是为[0-转派]时，转派的网格经理ID不能为空");
            }
        }

        if (req.getDealType().trim()
                .equals(BizConstant.DealCustMarketingDealtype.SAVE)) {
            // 处理类型是保存结果，则处理结果不能为空
            if (StringUtils.isBlank(req.getResult())) {
                throw new BusinessException("处理类型是为[2-保存]时，处理结果不能为空");
            }
            if (StringUtils.isBlank(req.getResultexp())) {
                throw new BusinessException("处理类型是为[2-保存]时，结果说明不能为空");
            }
        }

        if (req.getDealType().trim()
                .equals(BizConstant.DealCustMarketingDealtype.TRANS)) {
            dealCustMarketingTrans(req, loginInfo);
        } else if (req.getDealType().trim()
                .equals(BizConstant.DealCustMarketingDealtype.RECEIVE)) {
            dealCustMarketingReceive(req, loginInfo);
        } else if (req.getDealType().trim()
                .equals(BizConstant.DealCustMarketingDealtype.DEAL)) {
            dealCustMarketingDeal(req, loginInfo);
        } else if (req.getDealType().trim()
                .equals(BizConstant.DealCustMarketingDealtype.SAVE)) {
            dealCustMarketingSave(req, loginInfo);
        } else {
            throw new BusinessException("处理类型[" + req.getDealType() + "]未定义");
        }

        return returnInfo;
    }

    private void dealCustMarketingReceive(DealCustMarketingInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "客户营销接单:请求对象不能为空");
        CheckUtils.checkEmpty(req.getMarkid(), "客户营销接单:营销主键不能为空");
        // CheckUtils.checkEmpty(req.getDealstatus(), "客户营销接单:处理结果不能为空");
        // CheckUtils.checkEmpty(req.getResult(), "客户营销接单:结果说明不能为空");

        getDAO().clear();

        CustMarketing custMarketing = (CustMarketing) getDAO().find(
                CustMarketing.class, Long.valueOf(req.getMarkid()));
        if (null == custMarketing) {
            throw new BusinessException("客户营销接单:根据营销主键查询不到客户营销信息");
        }

        boolean isCustMarketingCanOptReceive = custMarketing.getDealstatus()
                .equals(BizConstant.BizCustMarketingDealstatus.INIT);
        if (!isCustMarketingCanOptReceive) {
            throw new BusinessException("客户营销接单:客户营销当前状态为["
                    + custMarketing.getDealstatus() + "]，不能进行此操作");
        }

        custMarketing
                .setDealstatus(BizConstant.BizCustMarketingDealstatus.RECEIVED);

        // --TODO记录操作日志

        getDAO().save(custMarketing);

    }

    private void dealCustMarketingDeal(DealCustMarketingInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "客户营销接单:请求对象不能为空");
        CheckUtils.checkEmpty(req.getMarkid(), "客户营销接单:营销主键不能为空");
        // CheckUtils.checkEmpty(req.getDealstatus(), "客户营销接单:处理结果不能为空");
        // CheckUtils.checkEmpty(req.getResult(), "客户营销接单:结果说明不能为空");

        getDAO().clear();

        CustMarketing custMarketing = (CustMarketing) getDAO().find(
                CustMarketing.class, Long.valueOf(req.getMarkid()));
        if (null == custMarketing) {
            throw new BusinessException("客户营销接单:根据营销主键查询不到客户营销信息");
        }

        boolean isCustMarketingCanOptDeal = custMarketing.getDealstatus()
                .equals(BizConstant.BizCustMarketingDealstatus.RECEIVED);
        if (!isCustMarketingCanOptDeal) {
            throw new BusinessException("客户营销接单:客户营销当前状态为["
                    + custMarketing.getDealstatus() + "]，不能进行此操作");
        }

        custMarketing
                .setDealstatus(BizConstant.BizCustMarketingDealstatus.DEALING);

        // --TODO记录操作日志

        getDAO().save(custMarketing);

    }

    private void dealCustMarketingSave(DealCustMarketingInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "客户营销结果保存:请求对象不能为空");
        CheckUtils.checkEmpty(req.getMarkid(), "客户营销结果保存:营销主键不能为空");
        CheckUtils.checkEmpty(req.getResult(), "客户营销结果保存:处理结果不能为空");
        CheckUtils.checkEmpty(req.getResultexp(), "客户营销结果保存:结果说明不能为空");

        getDAO().clear();

        CustMarketing custMarketing = (CustMarketing) getDAO().find(
                CustMarketing.class, Long.valueOf(req.getMarkid()));
        if (null == custMarketing) {
            throw new BusinessException("客户营销结果保存:根据营销主键查询不到客户营销信息");
        }

        boolean isCustMarketingCanOptSave = custMarketing.getDealstatus()
                .equals(BizConstant.BizCustMarketingDealstatus.INIT)
                || custMarketing.getDealstatus().equals(
                        BizConstant.BizCustMarketingDealstatus.RECEIVED)
                || custMarketing.getDealstatus().equals(
                        BizConstant.BizCustMarketingDealstatus.DEALING);
        if (!isCustMarketingCanOptSave) {
            throw new BusinessException("客户营销结果保存:客户营销当前状态为["
                    + custMarketing.getDealstatus() + "]，不能进行此操作");
        }

        // 校验dealstatus
        if (!BizConstant.BizCustMarketingResult.SUCCESS.equals(req.getResult())
                && !BizConstant.BizCustMarketingResult.FAIL.equals(req
                        .getResult())) {
            throw new BusinessException("客户营销结果保存:处理结果:[" + req.getResult()
                    + "]未定义");
        }

        long alnums = 0L;
        if (null != custMarketing.getAlnums()) {
            alnums = custMarketing.getAlnums() + 1;
        }

        custMarketing.setAlnums(alnums);
        custMarketing
                .setDealstatus(BizConstant.BizCustMarketingDealstatus.DOWN);
        custMarketing.setResult(req.getResult());
        custMarketing.setResultexp((req.getResultexp()));

        // --TODO 记录处理日志

        getDAO().save(custMarketing);

    }

    private void dealCustMarketingTrans(DealCustMarketingInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "客户营销转派:请求对象不能为空");
        CheckUtils.checkEmpty(req.getMarkid(), "客户营销转派:营销主键不能为空");
        CheckUtils.checkEmpty(req.getAreamger(), "客户营销转派:转派的网格经理ID不能为空");
        CheckUtils.checkEmpty(req.getResultexp(), "客户营销转派:转派原因不能为空");
        CheckUtils.checkNull(loginInfo, "客户营销转派:操作员登录信息不能为空");

        getDAO().clear();

        CustMarketing custMarketing = (CustMarketing) getDAO().find(
                CustMarketing.class, Long.valueOf(req.getMarkid()));
        if (null == custMarketing) {
            throw new BusinessException("客户营销转派:根据营销主键查询不到客户营销信息");
        }

        boolean isCustMarketingCanOptSave = custMarketing.getDealstatus()
                .equals(BizConstant.BizCustMarketingDealstatus.INIT)
                || custMarketing.getDealstatus().equals(
                        BizConstant.BizCustMarketingDealstatus.RECEIVED);
        if (!isCustMarketingCanOptSave) {
            throw new BusinessException("客户营销转派:客户营销当前状态为["
                    + custMarketing.getDealstatus() + "]，不能进行此操作");
        }

        // 新的网格经理信息
        BizGridManager newGridManager = (BizGridManager) getDAO().find(
        		BizGridManager.class, Long.valueOf(req.getAreamger()));
        if (null == newGridManager) {
            throw new BusinessException("客户营销转派:根据转派的网格经理ID查询不到网格经理配置信息");
        }

        PrvOperator newGridManagerInfoVO = new PrvOperator();
        newGridManagerInfoVO.setId(Long.valueOf(newGridManager.getOperid()));
        List<PrvOperator> newGridManagerInfoList = getDAO().find(
                newGridManagerInfoVO);
        if (null == newGridManagerInfoList
                || newGridManagerInfoList.size() <= 0
                || newGridManagerInfoList.isEmpty()) {
            throw new BusinessException("客户营销转派:根据转派的网格经理ID查询不到网格经理信息");
        }

        PrvOperator newGridManagerInfo = newGridManagerInfoList.get(0);

        // 直接用主键查报错，后面再改进
        // PrvOperator newGridManagerInfo = (PrvOperator)
        // getDAO().find(
        // PrvOperinfo.class, Long.valueOf(newGridManager.getAreamger()));
        // if (null == newGridManagerInfo) {
        // throw new BusinessException("客户营销转派:根据转派的网格经理ID查询不到网格经理信息");
        // }

        if (!newGridManagerInfo.getStatus().equals(
                SysConstant.Operator.PRV_USE_STATUS_ENABLED)) {
            throw new BusinessException("保存客户营销信息:网格经理状态不合法，请检查配置信息");
        }
        if (StringUtils.isBlank(newGridManagerInfo.getOperinfo().getCallno())) {
            throw new BusinessException("客户营销转派:待转派的网格经理联系手机为空，请检查配置信息");
        }

        // 操作员登录信息

        // --copy一份备用
        CustMarketing custMarketingBO = new CustMarketing();
        BeanUtils.copyProperties(custMarketingBO, custMarketing);

        // 登记客户营销分配表(BIZ_CUST_TRANSMIT)
        CustTransmit custTransmit = new CustTransmit();
        custTransmit.setAreamger(newGridManagerInfo.getId());
        custTransmit.setCity(loginInfo.getCity());
        custTransmit.setMarkid(custMarketingBO.getId());
        custTransmit.setMgerphone(newGridManagerInfo.getOperinfo().getCallno());
        custTransmit.setMsg(req.getResultexp());
        custTransmit.setOareamger(custMarketingBO.getAreamger());
        custTransmit.setOpdate(new Date());
        custTransmit.setOperid(loginInfo.getOperid());
        // custTransmit.setRecid(recid);

        getDAO().save(custTransmit);

        // 更新客户营销表
        // 更新表字段AREAMGER，MGERPHONE。转派后状态变会0-初始
        custMarketing.setAreamger(newGridManagerInfo.getId());
        // custMarketing.setAreamgername(newGridManagerInfo.getName());没用的
        custMarketing
                .setMgerphone(newGridManagerInfo.getOperinfo().getCallno());
        custMarketing
                .setDealstatus(BizConstant.BizCustMarketingDealstatus.INIT);

        getDAO().save(custMarketing);

    }

    public ReturnInfo saveCustMarketing(SaveCustMarketingInterReq req,
            SaveCustMarketingInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "匹配条件[custid]不能为空");
        CheckUtils.checkEmpty(req.getKnowid(), "匹配条件[knowid]不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "匹配条件[patchid]不能为空");
        // CheckUtils.checkEmpty(req.getBizorderid(), "业务订单id不能为空");
        CheckUtils.checkEmpty(req.getAreaid(), "匹配条件[areaid]不能为空");
        CheckUtils.checkEmpty(req.getLinkphone(), "匹配条件[linkphone]不能为空");
        CheckUtils.checkEmpty(req.getName(), "匹配条件[name]不能为空");
        CheckUtils.checkEmpty(req.getPri(), "匹配条件[pri]不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "匹配条件[whladdr]不能为空");

        // 取营销批次号
        String batchno = getDAO().getSequence("SEQ_BIZ_CUST_MARKETING_BATCHNO")
                .toString();

        getDAO().clear();

        // 登记表客户营销批次表(BIZ_MARKET_BATCH)，客户营销表(BIZ_CUST_MARKETING)
        // BIZ_MARKET_BATCH.areaids怎么取
        regMarketBatch4saveCustMarketing(req, batchno, loginInfo);

        regCustMarketing4saveCustMarketing(req, batchno, loginInfo);

        resp.setBatchno(batchno);

        return returnInfo;
    }

    private MarketBatch regMarketBatch4saveCustMarketing(
            SaveCustMarketingInterReq req, String batchno, LoginInfo loginInfo)
            throws Exception {
        CheckUtils.checkNull(loginInfo, "保存客户营销批次信息:登录信息不能为空");

        CheckUtils.checkNull(req, "保存客户营销批次信息:请求对象不能为空");
        // CheckUtils.checkEmpty(req.getBizorderid(), "业务订单id不能为空");
        CheckUtils.checkEmpty(req.getAreaid(), "保存客户营销信息:业务区id不能为空");
        CheckUtils.checkEmpty(req.getKnowid(), "保存客户营销信息:营销id不能为空");

        CheckUtils.checkEmpty(batchno, "保存客户营销信息:营销批次号不能为空");

        MarketBatch marketBatch = new MarketBatch();
        marketBatch.setAppdate(new Date());
        marketBatch.setAreaids(req.getAreaid());
        // marketBatch.setAreanames(areanames);
        marketBatch.setBatchno(batchno);
        marketBatch.setCity(loginInfo.getCity());
        marketBatch.setKnowid(Long.valueOf(req.getKnowid()));
        // marketBatch.setKnowname(knowname);
        marketBatch.setNums(1L);
        marketBatch.setOperid(loginInfo.getOperid());
        // marketBatch.setRecid(recid);
        marketBatch.setStatus(BizConstant.BizMarketBatchStatus.VALID);

        getDAO().save(marketBatch);

        return marketBatch;
    }

    private CustMarketing regCustMarketing4saveCustMarketing(
            SaveCustMarketingInterReq req, String batchno, LoginInfo loginInfo)
            throws Exception {
        CheckUtils.checkNull(loginInfo, "保存客户营销信息:登录信息不能为空");

        CheckUtils.checkNull(req, "保存客户营销信息:请求对象不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "保存客户营销信息:客户id不能为空");
        CheckUtils.checkEmpty(req.getKnowid(), "保存客户营销信息:营销id不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "保存客户营销信息:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "保存客户营销信息:片区id不能为空");
        // CheckUtils.checkEmpty(req.getBizorderid(), "业务订单id不能为空");
        CheckUtils.checkEmpty(req.getAreaid(), "保存客户营销信息:业务区id不能为空");
        CheckUtils.checkEmpty(req.getLinkphone(), "保存客户营销信息:系统电话不能为空");
        CheckUtils.checkEmpty(req.getName(), "保存客户营销信息:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getPri(), "保存客户营销信息:优先级不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "保存客户营销信息:地址不能为空");

        CheckUtils.checkEmpty(batchno, "保存客户营销信息:营销批次号不能为空");

        if (!BizConstant.BizCustMarketingPri.HIGH.equals(req.getPri())
                && !BizConstant.BizCustMarketingPri.MID.equals(req.getPri())
                && !BizConstant.BizCustMarketingPri.LOW.equals(req.getPri())) {
            throw new BusinessException("保存客户营销信息:优先级[" + req.getPri() + "]未定义");
        }

        // 取片区信息
        ResPatch resPrach = (ResPatch) getDAO().find(ResPatch.class,
                Long.valueOf(req.getPatchid()));
        if (null == resPrach) {
            throw new BusinessException("保存客户营销信息:根据片区ID查询不到片区配置信息");
        }

        // 取网格经理信息
        String bossserialno = null;
        BizGridInfo bizGrid = pubService.getGrid4Biz(
                Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()),
                loginInfo);

        BizGridManager bizGridManagerVO = new BizGridManager();
        bizGridManagerVO.setGridid(bizGrid.getId());
        bizGridManagerVO.setIsMain(SysConstant.SysYesNo.YES);

        List<BizGridManager> bizGridManagerList = getDAO().find(bizGridManagerVO);
        if (null == bizGridManagerList || bizGridManagerList.size() <= 0
                || bizGridManagerList.isEmpty()) {
            throw new BusinessException("保存客户营销信息:根据网格经理ID查询不到网格经理配置信息");
        }

        PrvOperator bizGridManagerInfoVO = new PrvOperator();
        bizGridManagerInfoVO.setId(bizGridManagerList.get(0).getOperid());
        List<PrvOperator> bizGridManagerInfoList = getDAO().find(
                bizGridManagerInfoVO);
        if (null == bizGridManagerInfoList
                || bizGridManagerInfoList.size() <= 0
                || bizGridManagerInfoList.isEmpty()) {
            throw new BusinessException("保存客户营销信息:根据转派的网格经理ID【"
                    + bizGridManagerList.get(0).getAreamger() + "】查询不到网格经理信息");
        }

        PrvOperator bizGridManagerInfo = bizGridManagerInfoList.get(0);

        if (!bizGridManagerInfo.getStatus().equals(
                SysConstant.Operator.PRV_USE_STATUS_ENABLED)) {
            throw new BusinessException("保存客户营销信息:网格经理状态不合法，请检查配置信息");
        }
        if (StringUtils.isBlank(bizGridManagerInfo.getOperinfo().getCallno())) {
            throw new BusinessException("保存客户营销信息:网格经理【"
                    + bizGridManagerInfo.getName() + "】联系手机为空，请检查配置信息");
        }

        // 取营销方案信息
        SalespkgKnow slaespkgKnow = (SalespkgKnow) getDAO().find(
                SalespkgKnow.class, Long.valueOf(req.getKnowid()));
        if (null == slaespkgKnow) {
            throw new BusinessException("保存客户营销信息:根据营销ID查询不营销配置信息");
        }

        // 登记客户营销表(BIZ_CUST_MARKETING)
        CustMarketing custMarketing = new CustMarketing();
        custMarketing.setAlnums(0L);
        custMarketing.setAppdate(new Date());
        custMarketing.setAreaid(loginInfo.getAreaid());
        custMarketing.setAreamger(bizGridManagerInfo.getId());
        custMarketing.setAreamgername(bizGridManagerInfo.getName());
        custMarketing.setBatchno(batchno);
        custMarketing.setCity(loginInfo.getCity());
        custMarketing.setCustid(Long.valueOf(req.getCustid()));
        custMarketing
                .setDealstatus(BizConstant.BizCustMarketingDealstatus.INIT);
        if (StringUtils.isNotBlank(req.getHouseid())) {
            custMarketing.setHouseid(Long.valueOf(req.getHouseid()));
        }
        custMarketing.setKnowid(Long.valueOf(req.getKnowid()));
        custMarketing.setKnowname(slaespkgKnow.getKnowname());
        custMarketing.setLinkphone(req.getLinkphone());
        // custMarketing.setMarkid(markid);
        custMarketing
                .setMgerphone(bizGridManagerInfo.getOperinfo().getCallno());
        custMarketing.setName(req.getName());
        custMarketing.setOperid(loginInfo.getOperid());
        custMarketing.setPatchname(resPrach.getPatchname());
        custMarketing.setPri(req.getPri());
        custMarketing.setPtachid(resPrach.getPatchid());
        custMarketing.setResult(null);
        custMarketing.setResultexp(null);
        // custMarketing.setSappdate();
        custMarketing.setWhladdr(req.getWhladdr());

        getDAO().save(custMarketing);

        return custMarketing;

    }

    public ReturnInfo queOrdercnt(QueOrdercntInterReq req,
            QueOrdercntInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridmanager(), "匹配条件[gridmanager]不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "业务订单id不能为空");

        // 从业务查询要统计的明细
        List<OrdercntsBO> ordercntList = getOrdercntdets4queOrdercnt(req,
                loginInfo);

        // 将统计明细插入到临时表
        genTmpCntdata4queOrdercnt(req, loginInfo);

        // 从明细表计算出汇总
        CntBO totalCntBo = getTotalCntdata4queOrdercnt(req);

        Float tmpTotalCnt = Float.valueOf(totalCntBo.getTotalcnt());

        // 组装返回结果
        resp.setTotalcnt(String.valueOf(tmpTotalCnt.intValue()));
        resp.setRank(totalCntBo.getRank());
        resp.setOrdercnts(ordercntList);

        return returnInfo;
    }

    private CntBO getTotalCntdata4queOrdercnt(QueOrdercntInterReq req)
            throws Exception {

        List<CntBO> cntList = getTotalCntdataFromTmpCntdata(
                req.getBizorderid(), req.getGridmanager());

        CntBO retCntBO = new CntBO();
        retCntBO.setTotalcnt("0");
        retCntBO.setRank("0");
        if (BeanUtil.isListNotNull(cntList)) {
            retCntBO = cntList.get(0);
        } else {
            //
        }
        return retCntBO;
    }

    private void genTmpCntdata4queOrdercnt(QueOrdercntInterReq req,
            LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "业务订单号不能为空");

        List<TmpStadataBO> ordercntList = getTmpCntdata4queOrdercnt(req,
                loginInfo);

        genTmpCntdata(req.getBizorderid(), ordercntList);

    }

    private List<TmpStadataBO> getTmpCntdata4queOrdercnt(
            QueOrdercntInterReq req, LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "请求对象不能为空");
        // CheckUtils.checkEmpty(req.getGridmanager(), "匹配条件[gridmanager]不能为空");
        CheckUtils.checkNull(loginInfo, "登录信息不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");
        sql.append(" SELECT c.operator objid, COUNT(1) datanum ");
        sql.append("   FROM biz_custorder c ");
        sql.append("  WHERE 1 = 1 ");

        sql.append("    and c.city = ? ");
        paramList.add(loginInfo.getCity());

        if (StringUtils.isNotBlank(req.getStime())) {
            sql.append("    and c.optime >= STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
            paramList.add(req.getStime());
        } else {
            sql.append("    AND c.optime > DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY) ");
        }

        if (StringUtils.isNotBlank(req.getEtime())) {
            sql.append("    and c.optime < STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
            paramList.add(req.getEtime());
        }

        if (StringUtils.isNotBlank(req.getStatus())) {
            if (req.getStatus().equals("1")) {
                // 成功
                // 实时定单已支付，异步定单已同步到boss完成
                sql.append(" AND ( ");
                // 实时订单--需支付且支付成功的
                sql.append("    EXISTS (SELECT 1 ");
                sql.append("                   FROM biz_portal_order o ");
                sql.append("                  WHERE o.orderid = c.orderid  ");
                sql.append("                    AND o.status in('2'))  ");
                // 实时订单--不需支付的
                sql.append("    OR (NOT EXISTS (SELECT 1 ");
                sql.append("      FROM biz_portal_order o ");
                sql.append("     WHERE o.orderid = c.orderid ) ");
                sql.append("       AND c.syncmode = 'SYNC'  ");
                sql.append("       AND c.orderstatus = 'SYNC')  ");
                // 异步定单--已同步到boss完成的,orderstatus = 'SYNC'?
                sql.append("     OR (c.syncmode = 'ASYNC' AND c.orderstatus = 'SYNC') ");
                sql.append("    ) ");

            } else if (req.getStatus().equals("2")) {
                // 失败
                // 订单已取消，实时订单已过期
                sql.append(" AND ( ");
                // 实时订单--已过期已作废的
                sql.append("    EXISTS (SELECT 1 ");
                sql.append("                   FROM biz_portal_order o ");
                sql.append("                  WHERE o.orderid = c.orderid  ");
                sql.append("                    AND o.status in( '1','3') )  ");
                // 已取消的
                sql.append("     OR (c.orderstatus = 'CANCEL') ");
                sql.append("    ) ");

            } else if (req.getStatus().equals("3")) {
                // 未完成
                // 异步定单未同步到boss完成，实时订单未支付
                sql.append(" AND ( ");
                // 实时订单--未支付的
                sql.append("    EXISTS (SELECT 1 ");
                sql.append("                   FROM biz_portal_order o ");
                sql.append("                  WHERE o.orderid = c.orderid  ");
                sql.append("                    AND o.status in( '0') )  ");
                // 状态为初始、锁定的
                sql.append("     OR (c.orderstatus in('INIT','LOCK')) ");
                sql.append("    ) ");
            }
        }

        sql.append("  GROUP BY c.operator ");
        sql.append(" ) v ");

        List<TmpStadataBO> retTmpStadataList = getDAO().find(sql.toString(),
                TmpStadataBO.class, paramList.toArray());

        return retTmpStadataList;
    }

    private List<OrdercntsBO> getOrdercntdets4queOrdercnt(
            QueOrdercntInterReq req, LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridmanager(), "匹配条件[gridmanager]不能为空");
        CheckUtils.checkNull(loginInfo, "登录信息不能为空");

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");
        sql.append(" SELECT c.opcode, code2name(c.opcode, 'BIZ_OPCODE') opcodename, COUNT(1) cnt ");
        sql.append("   FROM biz_custorder c ");
        sql.append("  WHERE 1 = 1 ");

        sql.append("    and c.city = ? ");
        paramList.add(loginInfo.getCity());

        if (StringUtils.isNotBlank(req.getGridmanager())) {
            sql.append("    AND c.operator = ? ");
            paramList.add(req.getGridmanager());
        }

        if (StringUtils.isNotBlank(req.getStime())) {
            sql.append("    and c.optime >= STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
            paramList.add(req.getStime());
        } else {
            sql.append("    AND c.optime > DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY) ");
        }

        if (StringUtils.isNotBlank(req.getEtime())) {
            sql.append("    and c.optime < STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
            paramList.add(req.getEtime());
        }

        if (StringUtils.isNotBlank(req.getStatus())) {
            if (req.getStatus().equals("1")) {
                // 成功
                // 实时定单已支付，异步定单已同步到boss完成
                sql.append(" AND ( ");
                // 实时订单--需支付且支付成功的
                sql.append("    EXISTS (SELECT 1 ");
                sql.append("                   FROM biz_portal_order o ");
                sql.append("                  WHERE o.orderid = c.orderid  ");
                sql.append("                    AND o.status in('2'))  ");
                // 实时订单--不需支付的
                sql.append("    OR (NOT EXISTS (SELECT 1 ");
                sql.append("      FROM biz_portal_order o ");
                sql.append("     WHERE o.orderid = c.orderid ) ");
                sql.append("       AND c.syncmode = 'SYNC'  ");
                sql.append("       AND c.orderstatus = 'SYNC')  ");
                // 异步定单--已同步到boss完成的,orderstatus = 'SYNC'?
                sql.append("     OR (c.syncmode = 'ASYNC' AND c.orderstatus = 'SYNC') ");
                sql.append("    ) ");

            } else if (req.getStatus().equals("2")) {
                // 失败
                // 订单已取消，实时订单已过期
                sql.append(" AND ( ");
                // 实时订单--已过期已作废的
                sql.append("    EXISTS (SELECT 1 ");
                sql.append("                   FROM biz_portal_order o ");
                sql.append("                  WHERE o.orderid = c.orderid  ");
                sql.append("                    AND o.status in( '1','3') )  ");
                // 已取消的
                sql.append("     OR (c.orderstatus = 'CANCEL') ");
                sql.append("    ) ");

            } else if (req.getStatus().equals("3")) {
                // 未完成
                // 异步定单未同步到boss完成，实时订单未支付
                sql.append(" AND ( ");
                // 实时订单--未支付的
                sql.append("    EXISTS (SELECT 1 ");
                sql.append("                   FROM biz_portal_order o ");
                sql.append("                  WHERE o.orderid = c.orderid  ");
                sql.append("                    AND o.status in( '0') )  ");
                // 状态为初始、锁定的
                sql.append("     OR (c.orderstatus in('INIT','LOCK')) ");
                sql.append("    ) ");
            }
        }

        sql.append("  GROUP BY c.opcode, c.operator ");
        sql.append(" ) v ");

        List<OrdercntsBO> retOrdercntsList = getDAO().find(sql.toString(),
                OrdercntsBO.class, paramList.toArray());

        return retOrdercntsList;
    }

    public ReturnInfo queOrderStatis(QueOrderStatisReq req,
            QueOrderStatisResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getGridmanager(), "匹配条件[gridmanager]不能为空");
		CheckUtils.checkEmpty(req.getBizorderid(), "业务订单id不能为空");

		// 统计明细
		List<OrderStatisBO> ordercntList = getOrderStatis(req, loginInfo);
		// 组装返回结果
		resp.setOrderStatisList(ordercntList);

		return returnInfo;
    }

	private List<OrderStatisBO> getOrderStatis(QueOrderStatisReq req, LoginInfo loginInfo) throws Exception {
		CheckUtils.checkNull(req, "请求对象不能为空");
		CheckUtils.checkEmpty(req.getGridmanager(), "匹配条件[gridmanager]不能为空");
		CheckUtils.checkNull(loginInfo, "登录信息不能为空");

		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();

		sql.append(" SELECT  ");
		sql.append(" code2name (opcode, 'BIZ_OPCODE') businessName , ");
		sql.append("  COUNT( CASE WHEN o.status IN( '2') OR (o.ORDERID IS NULL AND c.syncmode = 'SYNC' AND c.orderstatus = 'SYNC') ");
		sql.append("     OR (c.syncmode = 'ASYNC' AND c.orderstatus = 'SYNC') THEN 1 END )successCount, ");
		sql.append("  COUNT( CASE WHEN o.status IN( '1','3') OR c.orderstatus = 'CANCEL' THEN 1 END )failedCount, ");
		sql.append("  COUNT( CASE WHEN o.status IN( '0') OR c.orderstatus IN('INIT','LOCK') THEN 1 END )unDoneCount, ");
		sql.append("  COUNT( 1 ) `count` ");
		sql.append(" FROM ");
		sql.append("   biz_custorder c  ");
		sql.append("   LEFT JOIN biz_portal_order o  ");
		sql.append("     ON o.ORDERID = c.ORDERID  ");
		sql.append("  WHERE 1 = 1 ");

		sql.append("    and c.city = ? ");
		paramList.add(loginInfo.getCity());

		if (StringUtils.isNotBlank(req.getGridmanager())) {
			sql.append("    AND c.operator = ? ");
			paramList.add(req.getGridmanager());
		}

		if (StringUtils.isNotBlank(req.getStime())) {
			sql.append("    and c.optime >= STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
			paramList.add(req.getStime());
		}

		if (StringUtils.isNotBlank(req.getEtime())) {
			sql.append("    and c.optime < STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
			paramList.add(req.getEtime());
		}
		sql.append("  GROUP BY c.opcode, c.operator ");

		List<OrderStatisBO> list = getDAO().find(sql.toString(), OrderStatisBO.class, paramList.toArray());
		return list;
	}

    /**
     * 营收统计接口
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queIncome(QueIncomeInterReq req, QueIncomeInterResp resp)
            throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求对象不能为空");
        // CheckUtils.checkEmpty(req.getGridmanager(), "匹配条件[gridmanager]不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "业务订单id不能为空");

        // 将统计明细插入到临时表
        genTmpCntdata4queIncome(req, loginInfo);

        // 从明细表计算出汇总
        List<CntBO> totalCntBoList = getTotalCntdata4queIncome(req);

        // 根据汇总组装返回结果及每一汇总对应的营收明细
        List<IncomeRanksBO> incomeRankList = getIncomeRankList4queIncome(
                totalCntBoList, req, loginInfo);

        resp.setIncomeRanks(incomeRankList);

        return returnInfo;
    }
    
    @Transactional(readOnly = true)
    public ReturnInfo queryUserFocus(QueryUserFocusReq req, QueryUserFocusResp resp) throws Exception {
    	ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        BizUserFocus queryParam = new BizUserFocus();
        queryParam.setOperid(req.getOperid());
        List<BizUserFocus> list = getDAO().find(queryParam);
        resp.setFocusList(list);
        
        return returnInfo;
    }
    
    public ReturnInfo addUserFocus(AddUserFocusReq req) throws Exception {
    	ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        getDAO().executeUpdate("DELETE FROM BizUserFocus WHERE operid = ? AND custid = ?", 
        		req.getOperid(), req.getCustid());
        
        BizUserFocus entity = new BizUserFocus();
        entity.setOperid(req.getOperid());
        entity.setCustid(req.getCustid());
        getDAO().clear();
        getDAO().save(entity);
        
        return returnInfo;
    }
    
    public ReturnInfo deleteUserFocus(DeleteUserFocusReq req) throws Exception {
    	ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        getDAO().clear();
        getDAO().executeUpdate("DELETE FROM BizUserFocus WHERE operid = ? AND custid = ?", 
        		req.getOperid(), req.getCustid());
        
        return returnInfo;
    }

    private List<IncomeRanksBO> getIncomeRankList4queIncome(
            List<CntBO> totalCntBoList, QueIncomeInterReq req,
            LoginInfo loginInfo) throws Exception {
        if (BeanUtil.isListNull(totalCntBoList)) {
            return null;
        }
        
        List<IncomeRanksBO> retIncomeRankList = new ArrayList();
        IncomeRanksBO incomeRanksBo = null;
        for (CntBO totalCntBo : totalCntBoList) {
            incomeRanksBo = new IncomeRanksBO();
            incomeRanksBo.setGridmgr(totalCntBo.getObjid());
            incomeRanksBo.setGridmgrname(totalCntBo.getObjname());
            incomeRanksBo.setTotalfees(String.valueOf(totalCntBo.getTotalcnt()));
            incomeRanksBo.setRank(totalCntBo.getRank());

            // 从查询要统计的明细
            List<IncomesBO> incomeList = null;
            if (StringUtils.isNotBlank(req.getGridmanager())) {
                // 如果传入的网格经理为空，这里为了性能考虑，不查明细
                incomeList = getIncomedets4queIncome(req, loginInfo);
            }
            incomeRanksBo.setIncomes(incomeList);

            retIncomeRankList.add(incomeRanksBo);
            incomeRanksBo = null;
        }
        
        return retIncomeRankList;
    }

    private List<CntBO> getTotalCntdata4queIncome(QueIncomeInterReq req)
            throws Exception {

        List<CntBO> cntList = getTotalCntdataFromTmpCntdata(
                req.getBizorderid(), req.getGridmanager());

        // CntBO retCntBO = new CntBO();
        // retCntBO.setTotalcnt("0");
        // retCntBO.setRank("0");
        // if (BeanUtil.isListNotNull(cntList)) {
        // retCntBO = cntList.get(0);
        // } else {
        // //
        // }
        return cntList;
    }

    private void genTmpCntdata4queIncome(QueIncomeInterReq req,
            LoginInfo loginInfo) throws Exception {

        List<TmpStadataBO> incomeList = getTmpCntdata4queIncome(req, loginInfo);

        genTmpCntdata(req.getBizorderid(), incomeList);

    }

    private List<TmpStadataBO> getTmpCntdata4queIncome(QueIncomeInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "请求对象不能为空");
        // CheckUtils.checkEmpty(req.getGridmanager(), "匹配条件[gridmanager]不能为空");
        CheckUtils.checkNull(loginInfo, "登录信息不能为空");

        // --后结改造成从报表的明细出数据--todo

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");
        sql.append(" SELECT c.operator objid, ");
        sql.append("        (SELECT po.name FROM prv_operator po WHERE po.operid=c.operator) objname, ");
        sql.append("        sum(o.fees) datanum ");
        sql.append("   FROM biz_custorder c ,biz_portal_order o");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    and c.orderid=o.orderid ");
        sql.append("    AND o.status in('2') ");

        sql.append("    and c.city = ? ");
        paramList.add(loginInfo.getCity());

        if (StringUtils.isNotBlank(req.getStime())) {
            sql.append("    and c.optime >= STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
            paramList.add(req.getStime());
        } else {
            sql.append("    AND c.optime > DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY) ");
        }

        if (StringUtils.isNotBlank(req.getEtime())) {
            sql.append("    and c.optime < STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
            paramList.add(req.getEtime());
        }

        if (StringUtils.isNotBlank(req.getOpcode())) {
            sql.append("    and c.opcode = ? ");
            paramList.add(req.getOpcode());
        }

        sql.append("  GROUP BY c.operator ");
        sql.append(" ) v ");

        List<TmpStadataBO> retTmpStadataList = getDAO().find(sql.toString(),
                TmpStadataBO.class, paramList.toArray());

        return retTmpStadataList;
    }

    private List<IncomesBO> getIncomedets4queIncome(QueIncomeInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getGridmanager(), "匹配条件[gridmanager]不能为空");
        CheckUtils.checkNull(loginInfo, "登录信息不能为空");

        // --后续改造成从报表的明细出数据--todo

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        sql.append(" SELECT * from ( ");
        sql.append(" SELECT c.opcode, code2name(c.opcode, 'BIZ_OPCODE') opcodename, sum(o.fees) fees ");
        sql.append("   FROM biz_custorder c ,biz_portal_order o");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    and c.orderid=o.orderid ");
        sql.append("    AND o.status in('2') ");

        sql.append("    and c.city = ? ");
        paramList.add(loginInfo.getCity());

        if (StringUtils.isNotBlank(req.getGridmanager())) {
            sql.append("    AND c.operator = ? ");
            paramList.add(req.getGridmanager());
        }

        if (StringUtils.isNotBlank(req.getStime())) {
            sql.append("    and c.optime >= STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
            paramList.add(req.getStime());
        } else {
            sql.append("    AND c.optime > DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY) ");
        }

        if (StringUtils.isNotBlank(req.getEtime())) {
            sql.append("    and c.optime < STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s')  ");
            paramList.add(req.getEtime());
        }

        if (StringUtils.isNotBlank(req.getOpcode())) {
            sql.append("    and c.opcode = ? ");
            paramList.add(req.getOpcode());
        }

        sql.append("  GROUP BY c.opcode, c.operator ");
        sql.append(" ) v ");

        List<IncomesBO> retIncomesList = getDAO().find(sql.toString(),
                IncomesBO.class, paramList.toArray());

        return retIncomesList;
    }

}
