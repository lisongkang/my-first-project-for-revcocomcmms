package com.maywide.biz.inter.service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.pojo.queTerminalInfo.TerminalInfoBossReq;
import com.maywide.biz.inter.pojo.queTerminalInfo.TerminalInfoBossResp;
import com.maywide.biz.inter.pojo.queTerminalInfo.TerminalInfoInterReq;
import com.maywide.biz.inter.pojo.queTerminalInfo.TerminallnfoInterResp;
import com.maywide.biz.inter.pojo.quepatchName.quePatchNameInterResp;
import com.maywide.biz.inter.pojo.quepatchName.quepatchNameInterReq;
import com.maywide.biz.inter.pojo.saveSalesTerminal.saveSalesTerminalInfoBossReq;
import com.maywide.biz.inter.pojo.saveSalesTerminal.saveSalesTerminalInfoBossResp;
import com.maywide.biz.inter.pojo.saveSalesTerminal.saveSalesTerminalInfoInterReq;
import com.maywide.biz.inter.pojo.saveSalesTerminal.saveSalesTerminalInfoInterResp;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.test.InterTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lisongkang on 2019/3/5 0001.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TerminalSalesService extends CommonService {

    @Autowired
    private ParamService paramService;
    @Autowired
    private PubService pubService;

    /**
     * 1.1.终端销售查询终端信息接口
     * @param req
     * @param
     * @return
     * @throws Exception
     */
    public ReturnInfo queTerminalInfo(TerminalInfoInterReq req, TerminallnfoInterResp resp) throws Exception{
        CheckUtils.checkNull(req, "请求信息不能为空");
        ReturnInfo returnInfo = initReturnInfo();
        LoginInfo loginInfo = getLoginInfo();
        TerminalInfoBossReq bossReq = new TerminalInfoBossReq();
        //把请求转成boss入参
        BeanUtils.copyPropertiesNotSuperClass(bossReq, req);
        String serverCode = BizConstant.BossInterfaceService.QUE_TERMINALSALES_PRICE;
        // 调用BOSS接口
        String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
        bossRespOutput = "{\"terminallInfoBossList\":" + bossRespOutput + "}";
        copyBossResp2InterResp4queUserPrd(resp,bossRespOutput);

        return returnInfo;
    }
    /**
     * 1.2.	终端销售下单接口
     * @param req
     * @param
     * @return
     * @throws Exception
     */
    public ReturnInfo saveSalesTerminal(saveSalesTerminalInfoInterReq req, saveSalesTerminalInfoInterResp resp) throws Exception{
        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkEmpty(req.getNums(),"销售数量不能为空");
        CheckUtils.checkEmpty(req.getRsrdfees(),"实际单价不能为空");
        int nums =  Integer.valueOf(req.getNums());
        if(nums <= 0){
            throw new BusinessException("下单数量不能小于1");
        }
        CheckUtils.checkEmpty(req.getKind(),"请选择终端类型");
        CheckUtils.checkEmpty(req.getSupperid(),"请选择终端厂家");
        CheckUtils.checkEmpty(req.getSubkind(),"请选择终端型号");
        if(req.getDelivermode().equals("1")){
            CheckUtils.checkEmpty(req.getReceiptname(),"请选择收货人");
            CheckUtils.checkEmpty(req.getReceipttel(),"请填写收货人电话");
            CheckUtils.checkEmpty(req.getReceiptcity(),"请选择收货人地市");
            CheckUtils.checkEmpty(req.getReceiptarea(),"请选择收货人业务区");
            CheckUtils.checkEmpty(req.getReceiptaddr(),"请填写收货地址");
        }
        ReturnInfo returnInfo = initReturnInfo();
        LoginInfo loginInfo = getLoginInfo();
        //把请求转成boss入参
        saveSalesTerminalInfoBossReq bossReq = getsaveSalesTerminalInfoBossReq(req);
        //通过patchid 查到对应的areaid
        /*List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT areaid FROM res_patch where patchid = ? ");
        params.add(req.getPatchid());
        String areaid =  getDAO().findUniqueObject(sqlBuffer.toString(),params.toArray()).toString();
        bossReq.setReceiptarea(areaid);*/
        String serverCode = BizConstant.BossInterfaceService.BIZ_SALES_TERMINAL;
        // 调用BOSS接口
        String bossRespOutput = this.getBossHttpInfOutput(req.getBizorderid(), serverCode, bossReq, loginInfo);
        saveSalesTerminalInfoBossResp saveSalesTerminalInfoBossResp = (saveSalesTerminalInfoBossResp) BeanUtil.jsonToObject(bossRespOutput, com.maywide.biz.inter.pojo.saveSalesTerminal.saveSalesTerminalInfoBossResp.class);
        //更新BIZ_PORTAL_ORDER表
        BizPortalOrder bizPortalOrder = new BizPortalOrder();
        bizPortalOrder.setId(Long.parseLong(req.getBizorderid()));
        bizPortalOrder.setCreatetime(new Date());
        bizPortalOrder.setFees(saveSalesTerminalInfoBossResp.getSums());
        bizPortalOrder.setFeestr(saveSalesTerminalInfoBossResp.getFeename());
        bizPortalOrder.setOrdertype(saveSalesTerminalInfoBossResp.getOrdertype());
        bizPortalOrder.setResporderid(Long.valueOf(saveSalesTerminalInfoBossResp.getOrderid()));
        bizPortalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
        getDAO().save(bizPortalOrder);
        //更新CustOrder对应表
        CustOrder custOrder = regTerminalSaveInfo(req,loginInfo,bizPortalOrder);
        resp.setOrderid(req.getBizorderid());
        resp.setFeeNums(saveSalesTerminalInfoBossResp.getFeename());
        resp.setFrees(saveSalesTerminalInfoBossResp.getSums());
        resp.setOrderCode(custOrder.getOrdercode());
        return returnInfo;
    }

    private void copyBossResp2InterResp4queUserPrd(TerminallnfoInterResp resp, String jsonStr) throws Exception {

        TerminalInfoBossResp bossResp = (TerminalInfoBossResp) BeanUtil.jsonToObject(jsonStr, TerminalInfoBossResp.class);

        // 如果字段是一样的，这里可以偷懒一下，
        // 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
        BeanUtils.copyProperties(resp, bossResp);
        // resp.setAcctkind(bossResp.getAcctkind());

    }


    //写入业务表
    private CustOrder regTerminalSaveInfo(saveSalesTerminalInfoInterReq req, LoginInfo loginInfo,BizPortalOrder bizPortalOrder) throws Exception {

        CustOrder bizCustOrder = new CustOrder();
        bizCustOrder.setAddr(req.getAddr());
        bizCustOrder.setAreaid(loginInfo.getAreaid());
        bizCustOrder.setCity(loginInfo.getCity());
        bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
        bizCustOrder.setName(req.getCustname());
        bizCustOrder.setOpcode(BizConstant.BossInterfaceService.BIZ_SALES_TERMINAL);
        bizCustOrder.setOperator(loginInfo.getOperid());
        bizCustOrder.setOprdep(loginInfo.getDeptid());
        bizCustOrder.setOptime(new Date());
        bizCustOrder.setOrdercode(pubService.getOrderCode());
        bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
        bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
        bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
        bizCustOrder.setPortalOrder(null);
        bizCustOrder.setPortalOrder(bizPortalOrder);
        getDAO().save(bizCustOrder);
        return bizCustOrder;
    }

    /**
     *根据业务区ID查询业务区名字
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo quePatchName(quepatchNameInterReq req, quePatchNameInterResp resp) throws Exception{
        ReturnInfo returnInfo = initReturnInfo();
        LoginInfo loginInfo = getLoginInfo();
        //通过patchid 查到对应的patchname
        List<Object> params = new ArrayList();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT patchname from RES_PATCH where patchid= ? ");
        params.add(req.getPatchid());
        String patchName =  getDAO().findUniqueObject(sqlBuffer.toString(),params.toArray()).toString();
        resp.setPatchName(patchName);
        return returnInfo;
    }

    private saveSalesTerminalInfoBossReq getsaveSalesTerminalInfoBossReq(saveSalesTerminalInfoInterReq req) {
        saveSalesTerminalInfoBossReq bossReq = new saveSalesTerminalInfoBossReq();
        bossReq.setCustid(req.getCustid());
        bossReq.setPatchid(req.getPatchid());
        bossReq.setKind(req.getKind());
        bossReq.setSupperid(req.getSupperid());
        bossReq.setSubkind(req.getSubkind());
        bossReq.setRsrdfees(req.getRsrdfees());
        bossReq.setNums(req.getNums());
        bossReq.setReceiptaddr(req.getReceiptaddr());
        bossReq.setReceiptcity(req.getReceiptcity());
        bossReq.setReceipttel(req.getReceipttel());
        bossReq.setDelivermode(req.getDelivermode());
        bossReq.setReceiptname(req.getReceiptname());
        bossReq.setReceiptarea(req.getReceiptarea());
        return bossReq;
    }

}


