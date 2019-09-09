package com.maywide.biz.inter.service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.inter.entity.CustBizNetWorkOrderPool;
import com.maywide.biz.inter.pojo.addNetBusOrderPool.AddNetBusOrderPoolReq;
import com.maywide.biz.inter.pojo.addNetBusOrderPool.AddNetBusOrderPoolResp;
import com.maywide.biz.inter.pojo.dataReport.GridInfoResp;
import com.maywide.biz.inter.pojo.queNetBusOrder.*;
import com.maywide.biz.inter.pojo.queNetBusOrderPool.*;
import com.maywide.core.dao.support.Page;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lisongkang on 2019/7/9 0001.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NetWorkBusOrderPoolService  extends CommonService {
    static final Logger logger = LoggerFactory.getLogger(NetWorkBusOrderPoolService.class);
    @Autowired
    private RuleService ruleService;

    private static Object object = new Object();

    @Autowired
    private ParamService paramService;

    @Autowired
    private PubService pubService;

    @Autowired
    private InterPrdDataService dataService;

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    /**
     * 调用BOSS接口查询组网业务单列表 排除本地订单池已有业务单
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo quePreaccept(QueNetBusOrderInterReq req, QueNetBusOrderInterResp resp) throws Exception{
        ReturnInfo returnInfo = initReturnInfo();
        LoginInfo loginInfo = getLoginInfo();
        if(req.getCustid()!= null && !"".equals(req.getCustid())){
            if(!isNumeric(req.getCustid())){
                CheckUtils.checkEmpty(null,"输入的客户编号不符合规范");
            }
        }
        //本地入参转化为BOSS入参
        QueNetBusOrderBossReq bossReq = getQueNetBusOrderBossReqforReq(req,loginInfo);
        bossReq.setResult("0");
        //调用BOSS接口
        String serverCode = BizConstant.BossInterfaceService.QUE_PREACCEPT;
        String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
        QueNetBusOrderBossResp bossResp = (QueNetBusOrderBossResp)this.copyBossResp2InterResp(resp,QueNetBusOrderBossResp.class,bossRespOutput);
        QueNetBusOrderInterResp resp2 = new QueNetBusOrderInterResp(bossResp);
        resp2.setOrderNum(countOperOrderNum(loginInfo.getOperid()));

        //复制对象
        BeanUtils.copyProperties(resp, resp2);
        if(resp.getRetlist()!= null){

            for (QueNetBusOrderBossInfo queNetBusOrderBossInfo:resp.getRetlist()
            ) {
                //翻译一下支付方式，编码转中文
                String payway = queNetBusOrderBossInfo.getPayway();
                List<Object> params = new ArrayList();
                StringBuffer sqlBuffer = new StringBuffer();
                sqlBuffer.append(" SELECT coalesce(mname,'暂无') mname  from prv_sysparam c where c.gcode = 'SYS_PAYWAY' and mcode = ? ");
                params.add(payway);
                payway = getDAO().findUniqueObject(sqlBuffer.toString(),params.toArray()).toString();
                queNetBusOrderBossInfo.setPayway(payway);
                //翻译受理人
                String createoper = queNetBusOrderBossInfo.getCreateoper();
                List<Object> paramsList = new ArrayList();
                StringBuffer sql = new StringBuffer();
                sql.append(" SELECT coalesce(name,'暂无') name  from prv_operator  where operid = ? ");
                paramsList.add(createoper);
                createoper = getDAO().findUniqueObject(sql.toString(),paramsList.toArray()).toString();
                queNetBusOrderBossInfo.setCreateoper(createoper);
                //转换一下部门
                String applydept = queNetBusOrderBossInfo.getApplydept();
                List<Object> paramsListT = new ArrayList();
                StringBuffer sqlT = new StringBuffer();
                sqlT.append(" SELECT coalesce(name,'暂无') name  from prv_department where deptid = ? ");
                paramsListT.add(applydept);
                applydept = getDAO().findUniqueObject(sqlT.toString(),paramsListT.toArray()).toString();
                queNetBusOrderBossInfo.setApplydept(applydept);

            }
        }
        return returnInfo;
    }

    /**
     * 过滤订单池的预受理流水号
     * @param req
     * @param loginInfo
     * @return
     * @throws Exception
     */
    public QueNetBusOrderBossReq getQueNetBusOrderBossReqforReq(QueNetBusOrderInterReq req,
                 LoginInfo loginInfo) throws Exception{

        QueNetBusOrderBossReq bossReq = new QueNetBusOrderBossReq(req);
        if(StringUtils.isBlank(bossReq.getAreaid())) {
            bossReq.setAreaid(loginInfo.getAreaid().toString());
        }
        if(StringUtils.isBlank(bossReq.getGridcode())) {
            ArrayList<GridInfoResp> gridResp = new ArrayList<GridInfoResp>();
            try {
                dataService.queGridDataInfo(gridResp);
            }catch(Exception ex) {
            }
            if(!gridResp.isEmpty()) {
                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < gridResp.size(); i++) {
                    sb.append(gridResp.get(i).getGridCode());
                    if(i != gridResp.size() - 1) {
                        sb.append(",");
                    }
                }
                bossReq.setGridcode(sb.toString());
            }

        }
        //添加过滤预受理流水号
        if (req.isFilter()) {
            CustBizNetWorkOrderPool entity = new CustBizNetWorkOrderPool();
            entity.setStatus("0");
            List<CustBizNetWorkOrderPool> lists = DAO.find(entity);
            if (null != lists && !lists.isEmpty()) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < lists.size(); i++) {
                    sb.append(lists.get(i).getPreserialno());
                    if (i != lists.size() - 1) {
                        sb.append(",");
                    }
                }
                bossReq.setFliterserialnos(sb.toString());
            }
        }

        return bossReq;
    }

    private int countOperOrderNum(Long operid) throws Exception {
        CustBizNetWorkOrderPool entity = new CustBizNetWorkOrderPool();
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT biznetorder_id from biz_networkbus_custorder_pool where operid = ? and status = 0 ");
        params.add(operid);
        List<CustBizNetWorkOrderPool> lists = DAO.find(sqlBuffer.toString(),
                CustBizNetWorkOrderPool.class, params.toArray());
        return lists == null ? 0 : lists.size();
    }

    /**
     * 根据预受理流水号加入订单池接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo addNetBusOrderPool(AddNetBusOrderPoolReq req, AddNetBusOrderPoolResp resp) throws Exception{
        ReturnInfo returnInfo = initReturnInfo();
        LoginInfo loginInfo = getLoginInfo();
        CheckUtils.checkEmpty(req.getBizorderid(), "订单流水号不能为空!");
        CheckUtils.checkEmpty(req.getPreserialno(), "预受理流水不能为空!");
        QueNetBusOrderBossReq bossReq = new QueNetBusOrderBossReq();
        bossReq.setPreacceptserialno(req.getPreserialno());
        bossReq.setPageno(1);
        bossReq.setPagesize(1);
        bossReq.setAreaid(loginInfo.getAreaid().toString());
        bossReq.setResult("0");//只查正常状态
        //调用BOSS接口
        QueNetBusOrderInterResp resp1 = new QueNetBusOrderInterResp();
        String serverCode = BizConstant.BossInterfaceService.QUE_PREACCEPT;
        String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
        QueNetBusOrderBossResp bossResp = (QueNetBusOrderBossResp)this.copyBossResp2InterResp(resp1,QueNetBusOrderBossResp.class,bossRespOutput);
        CheckUtils.checkNull(bossResp, "根据组网流水号【" + req.getPreserialno() + "】无法查询到相关组网订单!");
        if(null == bossResp.getRetlist() || bossResp.getRetlist().isEmpty()){
            CheckUtils.checkNull(null, "根据流水号【" + req.getPreserialno() + "】无法查询到相关组网订单!");
        }

        CustBizNetWorkOrderPool custBizNetWorkOrderPool = new CustBizNetWorkOrderPool();
        custBizNetWorkOrderPool.setPreserialno(req.getPreserialno());
        List<CustBizNetWorkOrderPool> list = DAO.find(custBizNetWorkOrderPool);
        if(null != list && !list.isEmpty()){
            CheckUtils.checkNull(null, "该组网业务订单已被其他业务员加入订单池!");
        }
        //加入本地订单池
        synchronized (object){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            List<QueNetBusOrderBossInfo> datas = bossResp.getRetlist();
            List<String> preserialnos = new ArrayList<String>();
            for(QueNetBusOrderBossInfo bossInfo:datas){
                CustBizNetWorkOrderPool orderPool = new CustBizNetWorkOrderPool();
                orderPool.setCustid(bossInfo.getCustid());
                orderPool.setCustname(bossInfo.getCustname());
                orderPool.setPreserialno(bossInfo.getPreserialno());
                orderPool.setLinkAddr(bossInfo.getLinkAddr());
                orderPool.setCreateoper(bossInfo.getCreateoper());
                orderPool.setCreatetime(bossInfo.getCreatetime());
                orderPool.setPretime(sdf.parse(bossInfo.getPretime()));
                orderPool.setDefName(bossInfo.getDefName());
                orderPool.setOpcode(bossInfo.getOpcode());
                orderPool.setDefdesc(bossInfo.getDefdesc());
                orderPool.setRemark(bossInfo.getRemark());
                orderPool.setApplydept(bossInfo.getApplydept());
                orderPool.setPayway(bossInfo.getPayway());
                orderPool.setResult(bossInfo.getResult());
                orderPool.setDatadesc(bossInfo.getDatadesc());
                orderPool.setWhgridcode(bossInfo.getWhgridcode());
                orderPool.setApplyoperid(bossInfo.getApplyoperid());
                orderPool.setOperid(loginInfo.getOperid());
                orderPool.setOptime(date);
                orderPool.setAreaid(bossInfo.getAreaid());
                orderPool.setPhone(bossInfo.getPhone());
                orderPool.setStatus("0");
                DAO.save(orderPool);
                preserialnos.add(bossInfo.getPreserialno());
            }
            resp.setDatas(preserialnos);
        }
        return returnInfo;
    }


    /**
     * 订单池列表列表查询接口
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo queNetBusOrderpool(QueNetBusOrderPoolReq req, QueNetBusOrderPoolResp resp) throws Exception{
        ReturnInfo returnInfo = initReturnInfo();
        LoginInfo loginInfo = getLoginInfo();
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(String.valueOf(req.getCurrentPage()),"请求页码不能为空");
        //查询数据
        List params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("			select * from ( ");
        sqlBuffer.append("SELECT a.biznetorder_id as id,a.custid,a.custname,a.preserialno,a.link_Addr as linkAddr,a.createoper ");
        sqlBuffer.append("	,a.createtime,a.pretime,a.def_Name defName ,a.opcode,a.defdesc,a.remark,a.applydept,a.payway,a.result,a.phone ");
        sqlBuffer.append("	,a.datadesc,a.whgridcode,a.applyoperid,a.operid,a.optime,a.areaid,a.status as status  ");
        sqlBuffer.append(" from biz_networkbus_custorder_pool a WHERE 1=1 ");

        if (null != req && null != req.getCustname() && !"".equals(req.getCustname())) {
            sqlBuffer.append(" AND a.custname LIKE ? ");
            String custname = "%" + req.getCustname() + "%";
            params.add(custname);
        }
        if (null != req && null != req.getLinkAddr() && !"".equals(req.getLinkAddr())) {
            sqlBuffer.append(" AND a.link_Addr LIKE ? ");
            String linkAddr = "%" + req.getLinkAddr() + "%";
            params.add(linkAddr);
        }
        if (null != req && null != req.getCreateStarttime() && !"".equals(req.getCreateStarttime())) {
            sqlBuffer.append(" AND a.createtime > ? ");
            String createStarttime = req.getCreateStarttime();
            params.add(createStarttime);
        }
        if (null != req && null != req.getCreateEndtime() && !"".equals(req.getCreateEndtime())) {
            sqlBuffer.append(" AND a.createtime < ? ");
            String createEndtime = req.getCreateEndtime();
            params.add(createEndtime);
        }

        if (null != req && null != req.getPreStarttime() && !"".equals(req.getPreStarttime())) {
            sqlBuffer.append(" AND a.pretime > ? ");
            String preStarttime = req.getPreStarttime();
            params.add(preStarttime);
        }
        if (null != req && null != req.getPreEndtime() && !"".equals(req.getPreEndtime())) {
            sqlBuffer.append(" AND a.pretime < ? ");
            String preEndtime = req.getPreEndtime();
            params.add(preEndtime);
        }
        sqlBuffer.append("		AND a.operid = ?");
        params.add(loginInfo.getOperid());
        sqlBuffer.append("		AND a.status = 0 ");
        sqlBuffer.append(" )v ");

        Page page = new Page();
        if (StringUtils.isNotBlank(String.valueOf(req.getPagesize()))) {
            page.setPageSize(req.getPagesize());
        }
        if (StringUtils.isNotBlank(String.valueOf(req.getCurrentPage()))) {
            page.setPageNo(req.getCurrentPage());
        }
        getDAO().clear();
        page = getDAO().find(page, sqlBuffer.toString(), CustBizNetWorkOrderPool.class, params.toArray());
        resp.setPageSize(req.getPagesize());
        resp.setCurrentPage(req.getCurrentPage());
        if(null != page && page.getResult() != null){
            List<CustBizNetWorkOrderPool> custBizNetWorkOrderPoolList = page.getResult();
            resp.setCustBizNetWorkOrderPoolList(custBizNetWorkOrderPoolList);
            resp.setTotalPage(page.getTotalPages());
            resp.setTotalSize(page.getTotalCount());
        }
        return returnInfo;
    }

    /**
     * 组网业务区取消接单
     * @return
     */
    public ReturnInfo cancellationOfOrder(CancellattionOrderInterReq req, CancellattionOrderInterResp resp)throws Exception{
        ReturnInfo returnInfo = initReturnInfo();
        LoginInfo loginInfo = getLoginInfo();
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getPreacceptserialno(),"没有预受理流水号");
        CheckUtils.checkEmpty(req.getReason(),"请填写取消原因");

        CancellattionOrderBossReq bossReq = new CancellattionOrderBossReq();
        bossReq.setReason(req.getReason());
        bossReq.setPreacceptserialno(req.getPreacceptserialno());
        bossReq.setPreacceptstatus("2");
        //调用BOSS接口
        String serverCode = BizConstant.BossInterfaceService.DEAL_PREACCEPT;
        String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);

        resp.setPreacceptserialno(req.getPreacceptserialno());

        return returnInfo;
    }

    /**
     * 移出订单池
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo deleteNetOrder(DeleteNetBusOrderReq req,DeleteNetBusOrderResp resp)throws Exception{
        ReturnInfo returnInfo = initReturnInfo();
        LoginInfo loginInfo = getLoginInfo();
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkNull(req.getId(), "操作异常,订单id不能为空");
        CustBizNetWorkOrderPool bizNetWorkOrderPool = (CustBizNetWorkOrderPool)DAO.find(CustBizNetWorkOrderPool.class,req.getId());
        CheckUtils.checkNull(bizNetWorkOrderPool, "操作异常,订单不在订单池中");
        CustBizNetWorkOrderPool pool = new CustBizNetWorkOrderPool();
        pool.setPreserialno(bizNetWorkOrderPool.getPreserialno());
        pool.setOperid(bizNetWorkOrderPool.getOperid());
        List<CustBizNetWorkOrderPool> lists = DAO.find(pool);
        if(null == lists || lists.isEmpty()) {
            CheckUtils.checkNull(null, "抱歉,您的订单池中不包含该订单!");
        }
        List<String> datas = new ArrayList<String>();
        for(CustBizNetWorkOrderPool custBizNetWorkOrderPool:lists){
            try {
                DAO.delete(custBizNetWorkOrderPool);
                datas.add(custBizNetWorkOrderPool.getId().toString());
            } catch (Exception e) {
                continue;
            }
        }
        resp.setDatas(datas);
        return returnInfo;
    }


}
