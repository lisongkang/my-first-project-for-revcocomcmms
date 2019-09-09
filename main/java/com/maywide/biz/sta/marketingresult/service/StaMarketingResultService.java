package com.maywide.biz.sta.marketingresult.service;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.inter.pojo.queFitInfo.QueFitInfoBossChildResp;
import com.maywide.biz.sta.marketingresult.bo.GridInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.pojo.quecustorder.ApplyArrearBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyBankBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyInstallBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyProductBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyRefreshBO;
import com.maywide.biz.inter.pojo.quecustorder.ApplyTmpopenBO;
import com.maywide.biz.inter.pojo.quecustorder.CustorderDetBO;
import com.maywide.biz.inter.pojo.quecustorder.CustordersBO;
import com.maywide.biz.market.entity.ApplyProduct;
import com.maywide.biz.market.entity.ApplyTmpOpen;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.prd.entity.Pcode;
import com.maywide.biz.prd.entity.Salespkg;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.sta.marketingresult.bo.StaMarketResultParamBO;
import com.maywide.biz.sta.marketingresult.bo.StaMarketingResultBO;
import com.maywide.core.dao.support.Page;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateTimeUtil;
import com.maywide.core.util.SimpleSqlCreator;

@Service
@Transactional
public class StaMarketingResultService extends CommonService {
    @Autowired
    private PersistentService persistentService;

    @SuppressWarnings("unchecked")
    public PageImpl<StaMarketingResultBO> querySaleOrderList(StaMarketResultParamBO param, Pageable pageable)
            throws Exception {
        CheckUtils.checkNull(param, "查询条件不能为空");
        List<Long> areaids = param.getAreaids();
        List<String> payStatuss = param.getPayStatuss();
        if (null == areaids || areaids.size() == 0) {
            throw new Exception("业务区不能为空");
        }
        CheckUtils.checkNull(param.getStime(), "统计开始时间不能为空");
        CheckUtils.checkNull(param.getEtime(), "统计结束时间不能为空");

        PageImpl<StaMarketingResultBO> pageResult = null;
        Page<StaMarketingResultBO> page = new Page<StaMarketingResultBO>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();

        sql.append("SELECT * FROM ("); // 要在最外层加select *，否则框架本身根据构造的语句查总记录数时的sql会错误
        sql.append("SELECT o.orderid AS orderid,o.OPERATOR as operid, o.areaid as areaid, ");
        sql.append(" (select name from prv_area where areaid = o.areaid) as areaName, ");
        sql.append("o.name AS customer,");

        sql.append("o.custid AS custid,");

        sql.append("o.orderstatus AS orderStatus,");
        sql.append("o.bossserialno AS bossSerialNo,");
        sql.append("o.opcode AS opCode,");
        sql.append("(SELECT pd.NAME FROM ").append(SimpleSqlCreator.getEntityTableName(PrvDepartment.class))
                .append(" pd WHERE pd.DEPTID=o.oprdep) AS depart,");
        sql.append("(SELECT po.NAME FROM ").append(SimpleSqlCreator.getEntityTableName(PrvOperator.class))
                .append(" po WHERE po.OPERID=o.operator) AS operator,");
        sql.append("o.optime AS optime");
        // sql.append("(SELECT CONCAT(CONCAT(bpo.status,'~'),DATE_FORMAT(bpo.paytime,'%Y-%m-%d')) FROM ")
        // .append(SimpleSqlCreator.getEntityTableName(BizPortalOrder.class))
        // .append(" bpo WHERE bpo.orderid=o.orderid) AS payInfo");
        sql.append(" FROM ").append(SimpleSqlCreator.getEntityTableName(CustOrder.class)).append(" o");

        sql.append(",biz_portal_order p WHERE o.ORDERID = p.orderid  ");
        if (null != payStatuss && payStatuss.size() > 0) {
            sql.append(" and p.status in (");
            for (int i = 0, size = payStatuss.size(); i < size; i++) {
                sql.append("?");
                paramList.add(payStatuss.get(i));
                if (i < size - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }

        sql.append(" and o.areaid IN(");
        for (int i = 0, size = areaids.size(); i < size; i++) {
            sql.append("?");
            paramList.add(areaids.get(i));
            if (i < size - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        List<Long> departs = param.getDeparts();
        if (null != departs && departs.size() > 0) {
            sql.append(" AND o.oprdep IN(");
            for (int i = 0, size = departs.size(); i < size; i++) {
                sql.append("?");
                paramList.add(departs.get(i));
                if (i < size - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }

        List<Long> operators = param.getOperators();
        if (null != operators && operators.size() > 0) {
            sql.append(" AND o.operator IN(");
            for (int i = 0, size = operators.size(); i < size; i++) {
                sql.append("?");
                paramList.add(operators.get(i));
                if (i < size - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }

        String customer = param.getCustomer();
        String custid = param.getCustid();
        if (null != customer && !"".equals(customer)) {
            sql.append(" AND o.name LIKE ?");
            paramList.add("%" + customer + "%");
        }

        if (null != custid && !"".equals(custid)) {
            sql.append(" AND o.custid = ?");
            paramList.add(custid);
        }

        String orderStatus = param.getOrderStatus();
        if (null != orderStatus && !"".equals(orderStatus)) {
            sql.append(" AND o.orderstatus=?");
            paramList.add(orderStatus);
        }
        String bossSerialNo = param.getBossSerialNo();
        if (null != bossSerialNo && !"".equals(bossSerialNo)) {
            sql.append(" AND o.bossserialno=?");
            paramList.add(bossSerialNo);
        }

        String opCode = param.getOpCode();
        if (null != opCode && !"".equals(opCode)) {
            sql.append(" AND o.opcode=?");
            paramList.add(opCode);
        }

        String dateFormat = "'%Y-%m-%d %H:%i:%s'";
        sql.append(" AND o.optime>=STR_TO_DATE(?,").append(dateFormat).append(")");
        sql.append(" AND o.optime<=STR_TO_DATE(?,").append(dateFormat).append(")");
        sql.append(" ORDER BY o.optime DESC) mr");
        paramList.add(param.getStime());
        paramList.add(param.getEtime());

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), StaMarketingResultBO.class, paramList.toArray());

        List<StaMarketingResultBO> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            addExtraInfo(resultList);
            String ifFilterRegression = param.getIfFilterRegression();
            if(ifFilterRegression.equals("Y") ||ifFilterRegression == null || ifFilterRegression.equals("")){
                for(int i = 0;i < resultList.size();i++){
                    StaMarketingResultBO staMarketingResultBO = resultList.get(i);
                    opCode = staMarketingResultBO.getOpCode().substring(0,2);
                    if("R_".equals(opCode)){
                        resultList.remove(staMarketingResultBO);
                        total--;
                        i--;//索引减1  不然会包索引异常
                    }
                }
            }
            pageResult = new PageImpl<StaMarketingResultBO>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<StaMarketingResultBO>(new ArrayList<StaMarketingResultBO>(), pageable, 0);
        }
        if(pageResult!= null && page != null){

            for (StaMarketingResultBO staMarketingResultBO:pageResult
            ) {
                StringBuffer sqlTemp = new StringBuffer();
                List<Object> paramListTemp = new ArrayList<Object>();
                sqlTemp.append(" SELECT gridcode as gridcode,gridname as gridname  FROM biz_grid_info i, biz_grid_manager m WHERE i.gridid = m.gridid AND m.operid = ? ");
                //sqlTemp.append(" select gridcode,gridname from statistic_marketing  where operid = ?  and AREAID = ? and OPCODE = ? order by statistic_date desc  LIMIT 1 ");
                //paramListTemp.add(staMarketingResultBO.getOperid());
                if(staMarketingResultBO.getOperid()!= null){
                    paramListTemp.add(staMarketingResultBO.getOperid());
                }
                //paramListTemp.add(staMarketingResultBO.getOpCode());

                List<GridInfo> gridInfoList = getDAO().find(sqlTemp.toString(),GridInfo.class,paramListTemp.toArray());
                if(gridInfoList != null && gridInfoList.size() > 0){
                    StringBuffer sbgridcode = new StringBuffer();
                    StringBuffer sbgridname = new StringBuffer();
                    for(int i = 0;i < gridInfoList.size();i++){
                        GridInfo gridInfo = gridInfoList.get(i);
                        sbgridcode.append(gridInfo.getGridcode());
                        sbgridname.append(gridInfo.getGridname());
                        if(i != gridInfoList.size()-1){
                            sbgridcode.append(",");
                            sbgridname.append(",");
                        }
                    }
                    staMarketingResultBO.setGridname(sbgridname.toString());
                    staMarketingResultBO.setGridcode(sbgridcode.toString());
                }

            }
        }
        return pageResult;
    }

    /**
     * 额外查询boss订购信息
     * 
     * @param resultList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void addExtraInfo(List<StaMarketingResultBO> resultList) throws Exception {
        for (StaMarketingResultBO smk : resultList) {
            Long orderid = smk.getOrderid();
            String opCode = smk.getOpCode();

            if (BizConstant.BossInterfaceService.BIZ_TEMPOPEN.equals(opCode)) {
                // 点通或体验授权
                ApplyTmpOpen atoParam = new ApplyTmpOpen();
                atoParam.setOrderid(orderid);
                List<ApplyTmpOpen> atoList = persistentService.find(atoParam);
                if (null != atoList && atoList.size() > 0) {
                    smk.setSaleName(atoList.get(0).getPlanname());
                }
            } else {
                BizPortalOrder bpo = (BizPortalOrder) persistentService.find(BizPortalOrder.class, orderid);
                if (null != bpo) {
                    smk.setPayStatus(bpo.getStatus());
                    smk.setPaytime(DateTimeUtil.formatDate(bpo.getPaytime()));
                    smk.setFees(bpo.getFees());
                }
                ApplyProduct apParam = new ApplyProduct();
                apParam.setOrderid(orderid);
                List<ApplyProduct> apList = persistentService.find(apParam);
                if (null != apList && apList.size() > 0) {
                    ApplyProduct ap = apList.get(0);
                    Long knowId = ap.getKnowid();
                    if (null != knowId) {
                        SalespkgKnow spk = (SalespkgKnow) persistentService.find(SalespkgKnow.class, knowId);
                        if (null != spk) {
                            smk.setSaleName(spk.getKnowname());
                        }
                    } else {
                        Long salePkgId = ap.getSalespkgid();
                        Long productId = ap.getPid();
                        if (null != salePkgId) {
                            Salespkg sp = (Salespkg) persistentService.find(Salespkg.class, salePkgId);
                            if (null != sp) {
                                smk.setSaleName(sp.getSalespkgname());
                            }
                        } else if (null != productId) {
                            Pcode product = (Pcode) persistentService.find(Pcode.class, productId);
                            if (null != product) {
                                smk.setSaleName(product.getPname());
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<CustordersBO> queCustOrderDetail(Long custorderid) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, "用户未登录或已过期");

        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(custorderid);

        // 拼sql
        // sql.append(" select areamger,areamgername,mobile,status,statusname from ( ");
        sql.append(" SELECT * from( ");
        sql.append(" SELECT t.orderid custorderid, ");
        sql.append("        t.ordercode, ");
        sql.append("        t.syncmode, ");
        sql.append("        code2name(t.syncmode, 'BIZ_CUSTORDER_SYNCMODE') syncmodename, ");
        sql.append("        t.bossserialno, ");
        sql.append("        t.orderstatus, ");
        sql.append("        t.verifyphone, ");
        sql.append("        code2name(t.orderstatus, 'BIZ_CUSTORDER_ORDERSTATUS') orderstatusname, ");
        sql.append("        (SELECT t1.status FROM biz_portal_order t1 WHERE t1.orderid = t.orderid) paystatus, ");
        // sql.append("        (SELECT CASE WHEN t1.status='2' THEN '已支付' ");
        // sql.append("                ELSE '未支付' END ");
        // sql.append("           FROM biz_portal_order t1 ");
        // sql.append("          WHERE t1.orderid = t.orderid) paystatusname, ");
        sql.append("        (SELECT code2name(t1.status, 'BIZ_PORTAL_ORDER_STATUS') ");
        sql.append("           FROM biz_portal_order t1 ");
        sql.append("          WHERE t1.orderid = t.orderid) paystatusname, ");
        sql.append("        (SELECT SUM(t1.fees) FROM biz_portal_order t1 WHERE t1.orderid = t.orderid) fees, ");
        sql.append("        (SELECT t1.paycode FROM biz_portal_order t1 WHERE t1.orderid = t.orderid) payway, ");
        sql.append("        code2name((SELECT t1.payway FROM biz_portal_order t1 WHERE t1.orderid = t.orderid), 'SYS_PAYWAY') paywayname, ");
        // sql.append("        t.canceltime, ");
        sql.append("        DATE_FORMAT(t.canceltime, '%Y-%m-%d %T') canceltime, ");
        sql.append("        t.lockoper, ");
        sql.append("        (SELECT t1.name FROM prv_operator t1 WHERE t1.operid = t.LOCKOPER) lockopername, ");
        sql.append("        t.custid, ");
        sql.append("        t.NAME custname, ");
        sql.append("        t.opcode, ");
        sql.append("        code2name(t.OPCODE, 'BIZ_OPCODE') opcodename, ");
        // sql.append("        t.optime, ");
        sql.append("        DATE_FORMAT(t.optime, '%Y-%m-%d %T') optime, ");
        sql.append("        t.operator, ");
        sql.append("        (SELECT t1.name FROM prv_operator t1 WHERE t1.operid = t.OPERATOR) operatorname, ");
        sql.append("        t.oprdep, ");
        sql.append("        (SELECT t1.name FROM prv_department t1 WHERE t1.deptid = t.OPRDEP) oprdepname, ");
        sql.append("        t.areaid, ");
        sql.append("        (SELECT t1.name FROM PRV_AREA t1 WHERE t1.areaid = t.AREAID) areaname, ");
        sql.append("        t.gridid, ");
        sql.append("        (SELECT t1.gridname FROM biz_grid_info t1 WHERE t1.gridid = t.GRIDID) gridname, ");
        sql.append("        t.descrip, ");
        sql.append("        t.addr, ");
        sql.append("        t.city, ");
        sql.append("        code2name(t.CITY, 'PRV_CITY') cityname ");
        sql.append("   FROM biz_custorder t ");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    AND t.orderid = ? ");
        sql.append(" ) v ");

        Page<CustordersBO> page = new Page<CustordersBO>();
        page.setPageSize(1);
        page.setPageNo(1);

        getDAO().clear();
        page = getDAO().find(page, sql.toString(), CustordersBO.class, paramList.toArray());

        List<CustordersBO> custordersList = page.getResult();

        if (null != custordersList && custordersList.size() > 0) {
            for (CustordersBO custorder : custordersList) {
                CustorderDetBO custorderDet = getCustorderDet(custorderid);
                custorder.setCustorderDet(custorderDet);
            }
        }

        return custordersList;
    }

    private CustorderDetBO getCustorderDet(Long custorderid) throws Exception {
        // 申请报装信息
        List<ApplyInstallBO> applyInstallList = getApplyInstall4getCustorderDet(custorderid);
        // 申请产品信息
        List<ApplyProductBO> applyProductList = getApplyProduct4getCustorderDet(custorderid);
        // 申请银行信息
        List<ApplyBankBO> applyBankList = getApplyBank4getCustorderDet(custorderid);
        // 体验授权信息
        List<ApplyTmpopenBO> applyTmpopenList = getApplyTmpopen4getCustorderDet(custorderid);
        // 刷新授权信息
        List<ApplyRefreshBO> applyRefreshList = getApplyRefresh4getCustorderDet(custorderid);
        // 缴纳欠费信息
        List<ApplyArrearBO> applyArrearList = getApplyArrear4getCustorderDet(custorderid);

        CustorderDetBO retCustorderDetBO = new CustorderDetBO();
        retCustorderDetBO.setApplyInstalls(applyInstallList);
        retCustorderDetBO.setApplyProducts(applyProductList);
        retCustorderDetBO.setApplyBanks(applyBankList);
        retCustorderDetBO.setApplyTmpopens(applyTmpopenList);
        retCustorderDetBO.setApplyRefreshs(applyRefreshList);
        retCustorderDetBO.setApplyArrears(applyArrearList);

        return retCustorderDetBO;
    }

    @SuppressWarnings("unchecked")
    private List<ApplyInstallBO> getApplyInstall4getCustorderDet(Long custorderid) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT t.cardtype, ");
        sql.append("        code2name(t.cardtype, 'SYS_CARD_TYPE') cardtypename, ");
        sql.append("        t.cardno, ");
        sql.append("        t.cardaddr, ");
        sql.append("        t.linkphone, ");
        sql.append("        t.whladdr, ");
        sql.append("        t.oservid, ");
        sql.append("        t.ologicdevno, ");
        sql.append("        t.devback, ");
        sql.append("        t.detaddr, ");
        sql.append("        t.predate, ");
        sql.append("        t.logicdevno, ");
        sql.append("        t.stbno ");
        sql.append("   FROM biz_apply_install t ");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    AND t.orderid = ? ");
        sql.append(" ) v ");

        List<ApplyInstallBO> applyInstallList = getDAO().find(sql.toString(), ApplyInstallBO.class,
                new Object[] { custorderid });

        return applyInstallList;
    }

    @SuppressWarnings("unchecked")
    private List<ApplyProductBO> getApplyProduct4getCustorderDet(Long custorderid) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT t.recid, ");
        sql.append("        t.ostatus, ");
        sql.append("        code2name(t.ostatus, 'BIZ_APPLY_PRODUCT_OSTATUS') ostatusname, ");
        sql.append("        t.servid, ");
        sql.append("        t.logicdevno, ");
        sql.append("        t.knowid, ");
        sql.append("        (SELECT k.knowname FROM prd_salespkg_know k WHERE k.knowid = t.KNOWID) knowname, ");
        // sql.append("        (SELECT k.objtype FROM prd_salespkg_know k WHERE k.knowid = t.KNOWID) knowobjtype, ");
        sql.append("        t.salespkgid, ");
        sql.append("        (SELECT s.salespkgname ");
        sql.append("           FROM prd_salespkg s ");
        sql.append("          WHERE s.salespkgid = t.salespkgid) salespkgname, ");
        sql.append("        t.pid, ");
        sql.append("        (SELECT s.pname FROM prd_pcode s WHERE s.pid = t.pid) pname, ");
        sql.append("        t.count, ");
        sql.append("        t.unit, ");
        sql.append("        code2name(t.unit, 'PRD_ORDERUNIT') unitname ");
        sql.append("   FROM biz_apply_product t ");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    AND t.orderid = ? ");
        sql.append(" ) v ");

        List<ApplyProductBO> applyProductList = getDAO().find(sql.toString(), ApplyProductBO.class,
                new Object[] { custorderid });

        return applyProductList;
    }

    @SuppressWarnings("unchecked")
    private List<ApplyBankBO> getApplyBank4getCustorderDet(Long custorderid) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT t.servid, ");
        sql.append("        t.payway, ");
        sql.append("        code2name(t.payway, 'SYS_FEEWAY') paywayname, ");
        sql.append("        t.acctname, ");
        sql.append("        t.bankcode, ");
        sql.append("        code2name(t.bankcode, 'SYS_BANK') bankcodename, ");
        sql.append("        t.acctno, ");
        sql.append("        t.acctkind, ");
        sql.append("        code2name(t.acctkind, 'SYS_ACCT_KIND') acctkindname, ");
        sql.append("        t.accttype, ");
        sql.append("        code2name(t.accttype, 'SYS_ACCT_TYPE') accttypename ");
        sql.append("   FROM biz_apply_bank t ");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    AND t.orderid = ? ");
        sql.append(" ) v ");

        List<ApplyBankBO> applyBankList = getDAO()
                .find(sql.toString(), ApplyBankBO.class, new Object[] { custorderid });

        return applyBankList;
    }

    @SuppressWarnings("unchecked")
    private List<ApplyTmpopenBO> getApplyTmpopen4getCustorderDet(Long custorderid) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT t.SERVID, ");
        sql.append("        t.LOGICDEVNO, ");
        sql.append("        t.LOGICSTBNO, ");
        sql.append("        t.PLANID, ");
        sql.append("        t.PLANNAME, ");
        sql.append("        t.PID, ");
        sql.append("        (SELECT s.pname FROM prd_pcode s WHERE s.pid = t.pid) pname, ");
        sql.append("        t.DAYS ");
        sql.append("   FROM biz_apply_tmpopen t ");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    AND t.orderid = ? ");
        sql.append(" ) v ");

        List<ApplyTmpopenBO> applyTmpopenList = getDAO().find(sql.toString(), ApplyTmpopenBO.class,
                new Object[] { custorderid });

        return applyTmpopenList;
    }

    @SuppressWarnings("unchecked")
    private List<ApplyRefreshBO> getApplyRefresh4getCustorderDet(Long custorderid) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT t.SERVID, ");
        sql.append("        t.LOGICDEVNO, ");
        sql.append("        t.LOGICSTBNO, ");
        sql.append("        t.OPKIND, ");
        sql.append("        code2name(t.OPKIND, 'SYS_FRESH_KIND') opkindname,  ");
        sql.append("        t.PID, ");
        sql.append("        (SELECT s.pname FROM prd_pcode s WHERE s.pid = t.pid) pname ");
        sql.append("   FROM biz_apply_reflesh t ");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    AND t.orderid = ? ");
        sql.append(" ) v ");

        List<ApplyRefreshBO> applyRefreshList = getDAO().find(sql.toString(), ApplyRefreshBO.class,
                new Object[] { custorderid });

        return applyRefreshList;
    }

    @SuppressWarnings("unchecked")
    private List<ApplyArrearBO> getApplyArrear4getCustorderDet(Long custorderid) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM ( ");
        sql.append(" SELECT t.custid, ");
        sql.append("        t.keyno, ");
        sql.append("        t.permark, ");
        sql.append("        t.pid, ");
        sql.append("        (SELECT s.pname FROM prd_pcode s WHERE s.pid = t.pid) pname, ");
        sql.append("        DATE_FORMAT(t.stime, '%Y-%m-%d %T') stime, ");
        sql.append("        DATE_FORMAT(t.etime, '%Y-%m-%d %T') etime, ");
        sql.append("        t.fees ");
        sql.append("   FROM biz_apply_arrear t ");
        sql.append("  WHERE 1 = 1 ");
        sql.append("    AND t.orderid = ? ");
        sql.append(" ) v ");

        List<ApplyArrearBO> applyArrearList = getDAO().find(sql.toString(), ApplyArrearBO.class,
                new Object[] { custorderid });

        return applyArrearList;
    }
}