package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.applyinstall.ApplyInstallInterReq;
import com.maywide.biz.inter.pojo.applyinstall.ApplyInstallInterResp;
import com.maywide.biz.inter.pojo.applyinstall.KnowProducts;
import com.maywide.biz.inter.pojo.applyinstall.SelectProduct;
import com.maywide.biz.inter.pojo.bizbindbank.BindbankServInfoBO;
import com.maywide.biz.inter.pojo.bizbindbank.BizBindbankBossReq;
import com.maywide.biz.inter.pojo.bizbindbank.BizBindbankInterReq;
import com.maywide.biz.inter.pojo.bizbindbank.BizBindbankInterResp;
import com.maywide.biz.inter.pojo.bizfreshauth.BizFreshauthBossReq;
import com.maywide.biz.inter.pojo.bizfreshauth.BizFreshauthInterReq;
import com.maywide.biz.inter.pojo.bizfreshauth.BizFreshauthInterResp;
import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessBossReq;
import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessBossResp;
import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessInterReq;
import com.maywide.biz.inter.pojo.bizpreprocess.BizPreprocessInterResp;
import com.maywide.biz.inter.pojo.bizpreprocess.ChopcodesBO;
import com.maywide.biz.inter.pojo.bizpreprocess.ChopcodesBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.DeveloperBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectParamsBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectParamsInterBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectPkgsBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectPkgsInterBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectPrdsBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.EjectPrdsInterBO;
import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsBossBO;
import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;
import com.maywide.biz.inter.pojo.bizpreprocess.SalespkgKnowObjInfoBO;
import com.maywide.biz.inter.pojo.biztempopen.BizTempopenBossReq;
import com.maywide.biz.inter.pojo.biztempopen.BizTempopenBossResp;
import com.maywide.biz.inter.pojo.biztempopen.BizTempopenInterReq;
import com.maywide.biz.inter.pojo.biztempopen.BizTempopenInterResp;
import com.maywide.biz.inter.pojo.deleteorder.DeleteOrderBossReq;
import com.maywide.biz.inter.pojo.deleteorder.DeleteOrderInterReq;
import com.maywide.biz.inter.pojo.deleteorder.DeleteOrderInterResp;
import com.maywide.biz.inter.pojo.install.InstallAddrParams;
import com.maywide.biz.inter.pojo.install.InstallOrderAsyncReq;
import com.maywide.biz.inter.pojo.querysalespkgknow.KnowPrdBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.KnowSalespkgBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgKnowsBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgSelectDetsBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgSelectsBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.SalespkgSoftsBO;
import com.maywide.biz.market.entity.ApplyBank;
import com.maywide.biz.market.entity.ApplyInstall;
import com.maywide.biz.market.entity.ApplyProduct;
import com.maywide.biz.market.entity.ApplyProductSelect;
import com.maywide.biz.market.entity.ApplyProductSoft;
import com.maywide.biz.market.entity.ApplyRefresh;
import com.maywide.biz.market.entity.ApplyTmpOpen;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustAttrecord;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.market.pojo.BizAttprdProcReq;
import com.maywide.biz.prd.entity.Pcode;
import com.maywide.biz.prd.entity.Sales;
import com.maywide.biz.prd.entity.Salespkg;
import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.biz.tmpopenlimit.entity.BizTmpOpenLimit;
import com.maywide.biz.tmpopenlimit.service.BizTmpOpenLimitService;
import com.maywide.core.cons.Separator;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class InterApplyService extends CommonService {

    private static Long orderCode = null;
    @Autowired
	private ParamService paramService;
    @Autowired
    private InterPrdService interPrdService;
    @Autowired
    private PubService pubService;
    @Autowired
    private BizParamCfgService bizParamCfgService;
    @Autowired
    private InstallService installService;
    @Autowired
    private BizTmpOpenLimitService bizTmpOpenLimitService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private PersistentService persistentService;
    
    /**
     * 删除订单接口
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo deleteOrder(DeleteOrderInterReq req,
            DeleteOrderInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "删除订单信息:请求参数不能为空");
        CheckUtils.checkNull(req.getOrderid(), "删除订单信息:订单编号不能为空");

        // 修改订单数据
        modBizdata4DeleteOrder(req);

        // 如果是异步模式，BOSS侧没有订单，不需要调用BOSS接口进行删除
        if (BizConstant.BizCustorderSyncmode.ASYNC.equals(req.getSyncmode())) {
        	resp.setSuccess(true);
        	resp.setResult("已经成功删除该订单");
            return returnInfo;
        }

        // 调用BOSS接口进行删除
        callBossInf4DeleteOrder(req, resp, loginInfo);

        return returnInfo;
    }

    private void modBizdata4DeleteOrder(DeleteOrderInterReq req)
            throws Exception {
        CustOrder bizCustOrder = new CustOrder();
        bizCustOrder.setId(Long.parseLong(req.getOrderid()));

        List<CustOrder> custOrderList = getDAO().find(bizCustOrder);
        if (null == custOrderList || custOrderList.size() <= 0
                || custOrderList.isEmpty()) {
            throw new BusinessException("获取客户订单:根据订单id【" + req.getOrderid()
                    + "】查询不到客户订单信息");
        }

        bizCustOrder = custOrderList.get(0);
        bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.CANCEL);
        getDAO().save(bizCustOrder);

        // 释放占用的资源
        // releaseRes4DeleteOrder(req.getOrderid());

        // 设置订单原来的状态以及同步模式
        req.setOrderstatus(bizCustOrder.getOrderstatus());
        req.setSyncmode(bizCustOrder.getSyncmode());

        // 如果是异步模式，则不需要改biz_portal_order表状态
        if (BizConstant.BizCustorderSyncmode.ASYNC.equals(bizCustOrder
                .getSyncmode())) {
            return;
        }

        BizPortalOrder portalOrder = bizCustOrder.getPortalOrder();
        if (portalOrder == null) {
            throw new BusinessException("获取portal客户订单:根据订单id【"
                    + req.getOrderid() + "】查询不到portal客户订单信息");
        }
        portalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_LOGICDEL);
        getDAO().save(portalOrder);

    }

    private void callBossInf4DeleteOrder(DeleteOrderInterReq req,
            DeleteOrderInterResp resp, LoginInfo loginInfo)  {
        // 将请求做一下转换，并赋默认值
    	String bossRespOutput = "";
    	try{
        DeleteOrderBossReq req2Boss = getDeleteOrderBossReq2Boss(req);

        bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
                BizConstant.BossInterfaceService.BIZ_DELORDER, req2Boss,
                loginInfo);
        if(bossRespOutput == null||bossRespOutput.equals("null")){
        	resp.setSuccess(true);
        	resp.setResult("已经成功删除该订单");
        }
    	}catch(Exception e){
    		resp.setSuccess(false);
    		if(StringUtils.isNotBlank(bossRespOutput)){
    			resp.setResult(bossRespOutput);
    		}else{
    			resp.setResult("订单删除失败");
    			if(e instanceof BusinessException){
    				resp.setResult(e.getMessage());
    			}
    		}
        	
    	}
        // bossRespOutput为空
//        copyBossResp2InterResp4DeleteOrder(resp, bossRespOutput);
    }

    private DeleteOrderBossReq getDeleteOrderBossReq2Boss(
            DeleteOrderInterReq req) throws Exception {
        DeleteOrderBossReq deleteOrderBossReq = new DeleteOrderBossReq();

        // 根据orderId从biz_portal_order表中获取boss侧的订单号
        BizPortalOrder portalOrder = new BizPortalOrder();
        portalOrder.setId(Long.valueOf(req.getOrderid()));
        List<BizPortalOrder> portalOrderList = getDAO().find(portalOrder);
        if (null == portalOrderList || portalOrderList.size() <= 0
                || portalOrderList.isEmpty()) {
            throw new BusinessException("获取portal客户订单:根据订单id【"
                    + req.getOrderid() + "】查询不到portal客户订单信息");
        }

        portalOrder = portalOrderList.get(0);
        if(portalOrder.getResporderid() == null || portalOrder.getResporderid() == 0){
        	throw new BusinessException("BOSS订单不存在");
        }
        deleteOrderBossReq.setOrderid(String.valueOf(portalOrder
                .getResporderid()));

        return deleteOrderBossReq;
    }

   /* private void copyBossResp2InterResp4DeleteOrder(DeleteOrderInterResp resp,
            String jsonStr) throws Exception {
    	
    }
*/
    /**
     * 报装登记接口
     * 
     * @param req
     * @return
     * @throws Exception
     */
    /* @Transactional(readOnly = true) */
    public ReturnInfo applyInstall(ApplyInstallInterReq req,
            ApplyInstallInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        // 参数验证 --TODO
        //  登记轨迹表(BIZ_TRACE)
        //  登记客户订单表 (BIZ_CUSTORDER) OPCODE= BIZ_ASYNC_USER_NEW
        // ,SYNCMODE=ASYNC-异步
        //  登记申请报装信息表(BIZ_APPLY_INSTALL)
        //  登记申请产品信息表(BIZ_APPLY_PRODUCT)
        //  登记申请银行登记表 (BIZ_APPLY_BANK)
        //	     登记选择性产品信息表(BIZ_APPLY_PRODUCT_SELECT)

        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求对象不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "业务订单号不能为空");
        CheckUtils.checkNull(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkNull(req.getPatchid(), "获取客户订单:片区id不能为空");
        
        if(req.isCheckGrid()){
	        pubService.checkBizAcceptRight(req.getHouseid(), req.getPatchid(),
	                loginInfo);
        }

        //  登记轨迹表(BIZ_TRACE)--已经在接口入口处做了处理，这里不用再写

        //  登记客户订单表 (BIZ_CUSTORDER)
        CustOrder bizCustOrder = regBizCustorder4applyInstall(req, loginInfo);

        //  登记申请报装信息表(BIZ_APPLY_INSTALL)
        ApplyInstall bizApplyInstall = regBizApplyInstall4applyInstall(req,
                loginInfo);

        //  登记申请产品信息表(BIZ_APPLY_PRODUCT)(临时增加新入参用来支持江门的临时活动)
        List<ApplyProduct> bizApplyProductList = regBizApplyProduct4applyInstall(
	                req, loginInfo);

        //  登记申请银行登记表 (BIZ_APPLY_BANK)
        ApplyBank bizApplyBank = regBizApplyBank4applyInstall(req, loginInfo);

        // 保存payway、paycode信息到BIZ_PORTAL_ORDER表
        BizPortalOrder order = regBizPortalOrder4applyInstall(req);
        
        //   登记选择性产品信息表(BIZ_APPLY_PRODUCT_SELECT)
        regBizApplyProductSelect4applyInstall(req,loginInfo);
        
        resp.setCustorderid(req.getBizorderid());
        
        
        // 报装、升级登记成功后， 调用BOSS的同步接口，将报装升级信息同步给BOSS
        InstallOrderAsyncReq installOrderAsyncReq = new InstallOrderAsyncReq();
        installOrderAsyncReq.setOrderid(req.getBizorderid());
        installOrderAsyncReq.setBizorderid(req.getBizorderid());
        if(StringUtils.isNotBlank(req.getBizCode())){
        	installOrderAsyncReq.setOpcode(req.getBizCode());
        }
        if(req.getAddrs() != null && req.getAddrs().size() > 0 && req.getAddrs().get(0) != null){
        	InstallAddrParams in = new InstallAddrParams();
        	in.setHouseid(req.getAddrs().get(0).getHouseId());
        	in.setAddr7(req.getAddrs().get(0).getLevel7());
        	in.setAddr8(req.getAddrs().get(0).getLevel8());
        	in.setAddr9(req.getAddrs().get(0).getLevel9());
        	in.setAddr10(req.getAddrs().get(0).getLevel10());
        	in.setAddr11(req.getAddrs().get(0).getLevel11());
        	installOrderAsyncReq.setAddrparam(in);
        }
        if(req.getFeeParams() != null && !req.getFeeParams().isEmpty()){
        	installOrderAsyncReq.setOnecefeeparams(req.getFeeParams());
        }
        if(StringUtils.isNotBlank(req.getSystem())){
        	installOrderAsyncReq.setSystemid(req.getSystem());
        }
        if(StringUtils.isNotBlank(req.getOmode())){
        	installOrderAsyncReq.setOmode(req.getOmode());
        }
        installService.installOrderSync(installOrderAsyncReq);

        return returnInfo;
    }

    private ApplyBank regBizApplyBank4applyInstall(ApplyInstallInterReq req,
            LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "登记银行信息:请求参数不能为空");

        if (StringUtils.isBlank(req.getAcctno())) {
            // 如果银行账号为空，则不进行银行信息的存取
            return null;
        }

        ApplyBank bizApplyBank = getApplyBankInfoFromApplyInstallReq(req,
                loginInfo);

        getDAO().save(bizApplyBank);

        return bizApplyBank;
    }
    
    
    private void regBizApplyProductSelect4applyInstall(ApplyInstallInterReq req,LoginInfo loginInfo){
    	try{
	    	List<KnowProducts> selectProducts = req.getSelectProducts();
	    	if(selectProducts != null && selectProducts.size() > 0){
	    		for(KnowProducts kProducts:selectProducts){
	    			ApplyProduct applyProduct = new ApplyProduct();
	    			applyProduct.setKnowid(Long.parseLong(kProducts.getKnowId()));
	    			List<ApplyProduct> applyProductList = getDAO().find(applyProduct);
	    			if(applyProductList != null && applyProductList.size() > 0 && applyProductList.get(0) != null){
	    				applyProduct = applyProductList.get(0);
		    			for(SelectProduct product:kProducts.getProducts()){
		    				ApplyProductSelect applyProductSelect = new ApplyProductSelect();
		        			applyProductSelect.setCity(loginInfo.getCity());
		        			applyProductSelect.setKnowid(Long.parseLong(kProducts.getKnowId()));
		        			applyProductSelect.setOrderid(Long.parseLong(req.getBizorderid()));
		        			applyProductSelect.setPrecid(applyProduct.getRecid());
		        			applyProductSelect.setPid(product.getProductId());
		        			applyProductSelect.setSelectid(Long.parseLong(kProducts.getSelectId()));
		        			getDAO().save(applyProductSelect);
		    			}
	    			}
	    		}
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private BizPortalOrder regBizPortalOrder4applyInstall(ApplyInstallInterReq req) throws Exception {
    	BizPortalOrder order = new BizPortalOrder();

    	order.setPayway(req.getPayway());
    	order.setPaycode(req.getPayCode());
        order.setId(Long.valueOf(req.getBizorderid()));
        order.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_BOSSPAD);
        getDAO().save(order);
        
        return order;
    }

    private ApplyBank getApplyBankInfoFromApplyInstallReq(
            ApplyInstallInterReq req, LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "获取银行信息:请求参数不能为空");

        CheckUtils.checkEmpty(req.getAcctno(), "获取银行信息:银行账号不能为空");
        CheckUtils.checkEmpty(req.getAcctname(), "登记银行信息:账户名不能为空");
        CheckUtils.checkEmpty(req.getBankcode(), "登记银行信息:银行名编号不能为空");
        CheckUtils.checkEmpty(req.getCardtype(), "登记银行信息:银行证件类型不能为空");
        CheckUtils.checkEmpty(req.getCardno(), "登记银行信息:银行证件号码不能为空");
        CheckUtils.checkEmpty(req.getAcctkind(), "登记银行信息:帐号类型不能为空");

        String payway = BizConstant.SysFeeway.BANK;

        ApplyBank bizApplyBank = new ApplyBank();
        bizApplyBank.setAcctkind(req.getAcctkind());
        bizApplyBank.setAcctname(req.getAcctname());
        bizApplyBank.setAcctno(req.getAcctno());
        bizApplyBank.setAccttype(req.getAccttype());
        bizApplyBank.setBankcode(req.getBankcode());
        bizApplyBank.setCity(loginInfo.getCity());
        bizApplyBank.setOrderid(Long.valueOf(req.getBizorderid()));
        // 如果银行信息不为空，则payway为银行托收，否则为现金缴费。那如果是升级的业务又没传银行信息呢，默认为用原来的?
        bizApplyBank.setPayway(payway);
        // bizApplyBank.setRecid(recid);
        bizApplyBank.setServid(null);

        return bizApplyBank;
    }
    
    private List<ApplyProduct> regBizApplyProduct4applyInstall(
            ApplyInstallInterReq req, LoginInfo loginInfo) throws Exception {

        ApplyInstallInterReq reqBO = new ApplyInstallInterReq();
        BeanUtils.copyProperties(reqBO, req);

        List<ApplyProduct> retList = new ArrayList();
        String[] knowidArray = req.getKnowids().split(",");
        String[] countArray = req.getCounts().split(",");
        String[] unitArray = req.getUnits().split(",");
        if (null == knowidArray || knowidArray.length <= 0) {
            throw new BusinessException("登记报装申请产品信息:产品信息不能为空");
        } 
        if(countArray == null || countArray.length <= 0){
        	throw new BusinessException("登记报装申请产品信息:产品订购周期不能为空");
        }
        if(unitArray == null || unitArray.length <= 0){
        	throw new BusinessException("登记报装申请产品信息:产品周期单位不能为空");
        }
        if(knowidArray.length != countArray.length){
        	throw new BusinessException("登记报装申请产品信息:产品订购周期与产品数不符");
        }
        if(unitArray.length != countArray.length){
        	throw new BusinessException("登记报装申请产品信息:产品订购周期数与产品单位数不符");
        }
        for (int i = 0;i<knowidArray.length;i++) {
        	String knowid = knowidArray[i];
        	String count = countArray[i];
        	String unit = unitArray[i];
            if (StringUtils.isBlank(knowid)) {
                continue;
            }

            // 检查一下知识库营销信息--先这样写，后面可以再改进
            SalespkgKnowObjInfoBO knowObjInfo = getSalespkgKnowObjInfo(knowid);
            if (null == knowObjInfo) {
                throw new BusinessException("登记报装申请产品信息:根据营销标识[" + knowid
                        + "]查询不营销配置信息");
            }

            reqBO.setKnowids(knowid);
            reqBO.setUnits(unit);
            reqBO.setCounts(count);
            ApplyProduct bizApplyProduct = getBizApplyProductInfoFromApplyInstallReq(
                    reqBO, knowObjInfo, loginInfo);

            getDAO().save(bizApplyProduct);

            retList.add(bizApplyProduct);
        }

        return retList;
    }
    
    /*private ApplyProduct getBizApplyProductInfo4TmpAct(ApplyInstallInterReq req,LoginInfo loginInfo,TmpSalespkg tmpSalespkg) throws Exception{
    	 CheckUtils.checkNull(req, "获取申请产品信息:请求参数不能为空");
         CheckUtils.checkNull(loginInfo, "获取申请产品信息:登录信息不能为空");
         CheckUtils.checkEmpty(req.getBizorderid(), "获取申请产品信息:订单id不能为空");
         CheckUtils.checkEmpty(req.getKnowids(), "获取申请产品信息:产品id不能为空");
         
         
         
         return bizApplyProduct;
    }*/

    private ApplyProduct getBizApplyProductInfoFromApplyInstallReq(
            ApplyInstallInterReq req, SalespkgKnowObjInfoBO knowObjInfo, LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "获取申请产品信息:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取申请产品信息:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取申请产品信息:订单id不能为空");
        CheckUtils.checkEmpty(req.getKnowids(), "获取申请产品信息:产品id不能为空");

        ApplyProduct bizApplyProduct = new ApplyProduct();
        bizApplyProduct.setCity(loginInfo.getCity());
        bizApplyProduct.setCount(Long.parseLong(req.getCounts()));// 默认写1
        bizApplyProduct.setKnowid(Long.valueOf(req.getKnowids()));// 前面已经做了处理，用req.getKnowids()字段保存单个knowid
        bizApplyProduct.setLogicdevno(req.getLogicdevno());
        bizApplyProduct.setOrderid(Long.valueOf(req.getBizorderid()));
        bizApplyProduct.setOstatus(BizConstant.BizApplyProductOstatus.ORDER);
        bizApplyProduct.setSalespkgid(knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.SALESPKG)? Long.valueOf(knowObjInfo.getId()):null);
        bizApplyProduct.setServid(null);// 这里要写新用id
        if(knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.PRD)){
        	 bizApplyProduct.setPid(Long.valueOf(knowObjInfo.getId()));
        }else if(knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS) 
        		|| knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE)){
        	 bizApplyProduct.setPid(Long.parseLong(knowObjInfo.getPid()));
        	 bizApplyProduct.setSalesid(Long.parseLong(knowObjInfo.getId()));
        }
        if(req.getUnits().equals("次")){
        	bizApplyProduct.setUnit("0");
        }else{
        	bizApplyProduct.setUnit("1");
        }
        // 默认写1

        return bizApplyProduct;
    }

    private ApplyInstall getApplyInstallInfoFromApplyInstallReq(
            ApplyInstallInterReq req, LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "获取申请信息:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取申请报装信息:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取申请报装信息:订单id不能为空");

        ApplyInstall bizApplyInstall = new ApplyInstall();
        bizApplyInstall.setAreaid(req.getAreaid());
        bizApplyInstall.setCardaddr(null);
        bizApplyInstall.setCardno(req.getCardno());
        bizApplyInstall.setCardtype(req.getCardtype());
        bizApplyInstall.setCity(loginInfo.getCity());
        bizApplyInstall.setCustid(req.getCustid());
        // bizApplyInstall.setDetaddr(dataddr);//接口没传进来，先不保存
        bizApplyInstall.setDevback(req.getDevback());
        bizApplyInstall.setStbback(req.getStbback());
        bizApplyInstall.setHouseid(req.getHouseid());
        bizApplyInstall.setLinkphone(req.getLinkphone());
        bizApplyInstall.setLogicdevno(req.getLogicdevno());
        bizApplyInstall.setName(req.getName());
        bizApplyInstall.setOlogicdevno(req.getOlogicdevno());
        bizApplyInstall.setOrderid(Long.valueOf(req.getBizorderid()));
        bizApplyInstall.setOservid(req.getOservid());
        bizApplyInstall.setPatchid(req.getPatchid());
        bizApplyInstall.setPredate(req.getPredate());
        // bizApplyInstall.setRecid(recid);
        bizApplyInstall.setStbno(req.getStbno());
        bizApplyInstall.setWhladdr(req.getWhladdr());
        bizApplyInstall.setPermark("1"); // 默认为1
        bizApplyInstall.setPercomb(req.getPercombCode());
        bizApplyInstall.setSmuseprop(req.getCardSource());//智能卡来源 
        bizApplyInstall.setStbuseprop(req.getDeviceSource());//机顶盒来源 
        bizApplyInstall.setPservid(req.getPservid());
        return bizApplyInstall;
    }

    private CustOrder getCustOrderInfoFromApplyInstallReq(
            ApplyInstallInterReq req, LoginInfo loginInfo) throws Exception {
        //  登记客户订单表 (BIZ_CUSTORDER) OPCODE= BIZ_ASYNC_USER_NEW
        // ,SYNCMODE=ASYNC-异步

        CheckUtils.checkNull(req, "获取客户订单信息:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取客户订单信息:登录信息不能为空");
        CheckUtils.checkNull(req.getHouseid(), "获取客户订单信息:地址id不能为空");
        CheckUtils.checkNull(req.getPatchid(), "获取客户订单信息:片区id不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取申请报装信息:订单id不能为空");
        CheckUtils.checkEmpty(req.getDealType(), "获取申请报装信息:处理类型不能为空");

        String bossserialno = null;
        BizGridInfo bizGrid = pubService.getGrid4Biz(req.getHouseid(),
                req.getPatchid(), loginInfo);

        String bizcode = "";
        if(StringUtils.isBlank(req.getBizCode())){
	        if (BizConstant.ApplyInstallDealtype.USER_NEW.equals(req.getDealType())) {
	            bizcode = BizConstant.BizOpcode.BIZ_ASYNC_USER_NEW;
	        } else if (BizConstant.ApplyInstallDealtype.USER_UPG.equals(req
	                .getDealType())) {
	            bizcode = BizConstant.BizOpcode.BIZ_ASYNC_USER_UPG;
	        } else {
	            throw new BusinessException("登记客户订单:处理类型【" + req.getDealType()
	                    + "】未定义");
	        }
        }else{
        	bizcode = req.getBizCode();
        }

        CustOrder bizCustOrder = new CustOrder();
        bizCustOrder.setAddr(req.getWhladdr());
        bizCustOrder.setAreaid(req.getAreaid());
        bizCustOrder.setBossserialno(bossserialno);
        bizCustOrder.setCanceltime(null);
        bizCustOrder.setCity(loginInfo.getCity());
        bizCustOrder.setCustid(req.getCustid());
        bizCustOrder.setDescrip(req.getDescrip());
        bizCustOrder.setGridid(bizGrid.getId());
        bizCustOrder.setLockoper(null);
        bizCustOrder.setName(req.getName());
        bizCustOrder.setOpcode(bizcode);
        bizCustOrder.setOperator(loginInfo.getOperid());
        bizCustOrder.setOprdep(loginInfo.getDeptid());
        bizCustOrder.setOptime(new Date());
        bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
        bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
        bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
        bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.ASYNC);
        bizCustOrder.setPortalOrder(null);
        bizCustOrder.setVerifyphone(req.getVerifyphone());

        return bizCustOrder;

    }

    private ApplyInstall regBizApplyInstall4applyInstall(
            ApplyInstallInterReq req, LoginInfo loginInfo) throws Exception {
        // 登记申请报装信息表(BIZ_APPLY_INSTALL)
        ApplyInstall bizApplyInstall = getApplyInstallInfoFromApplyInstallReq(
                req, loginInfo);

        getDAO().save(bizApplyInstall);

        return bizApplyInstall;
    }

    private CustOrder regBizCustorder4applyInstall(ApplyInstallInterReq req,
            LoginInfo loginInfo) throws Exception {
        CustOrder bizCustOrder = getCustOrderInfoFromApplyInstallReq(req,
                loginInfo);
        bizCustOrder.setId(Long.valueOf(req.getBizorderid()));

        getDAO().save(bizCustOrder);

        return bizCustOrder;

    }

    /**
     * 用户点通
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo bizTempopen(BizTempopenInterReq req,
            BizTempopenInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        // 参数验证 --TODO
        // 登记客户订单表 (BIZ_CUSTORDER), OPCODE= BIZ_TEMPOPEN，SYNCMODE=SYNC
        // 登记BIZ_APPLY_TMPOPEN

        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "业务订单号不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");

        pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()),
                Long.valueOf(req.getPatchid()), loginInfo);

        // 检查业务受理次数--先只做到点通方案级别的限制--旧版
        // checkOperTmpenNum(req.getPlanid(), loginInfo);

        // 检查业务受理次数
		checkWgOperTmpenNum(Long.parseLong(req.getPlanid()));
        
        // 写业务表
        regBizdata4bizTempopen(req, loginInfo);

        // 调boss接口,并将结果存入resp
        callBossInf4bizTempopen(req, resp, loginInfo);

        resp.setCustorderid(req.getBizorderid());
        ReturnVisitTaskService.addTask(Long.parseLong(req.getBizorderid()));
        return returnInfo;
    }
    
	private void checkWgOperTmpenNum(Long planid) throws Exception {
		// 方案授权次数限制
		checkWgOperTmpenNum("0", "0", planid);
		checkWgOperTmpenNum("0", "1", planid);
		checkWgOperTmpenNum("1", "0", planid);
		checkWgOperTmpenNum("1", "1", planid);
	}

	private void checkWgOperTmpenNum(String objType, String timeType, Long planid) throws Exception {
		BizTmpOpenLimit tmp = bizTmpOpenLimitService.findLimitNums(objType, timeType, planid);
		Long c = bizTmpOpenLimitService.queryOpenNums(objType, timeType, planid);
		if (tmp != null && c >= tmp.getLimitNums()) {
			String msg = String.format("您%s%s对该方案的累计体验授权次数已达%d次，最大限制%s次",
					"0".equals(objType) ? "所在的本部门" : "", "0".equals(timeType) ? "当月" : "当天", c,
					tmp.getLimitNums());
			throw new BusinessException(msg);
		}
	}

    private void checkOperTmpenNum(String planid, LoginInfo loginInfo)
            throws Exception {

        // 操作员体验授权操作次数
        checkTmpopenOptNum(loginInfo);

        // 同一方案同一方案授权次数
        checkSameplanTmpopenOptNum(planid, loginInfo);

    }

    private void checkSameplanTmpopenOptNum(String planid, LoginInfo loginInfo)
            throws Exception {
        CheckUtils.checkNull(loginInfo, "检查体验授权次数:操作员登录信息不能为空");

        if (StringUtils.isBlank(planid)) {
            return;
        }

        String sameplanTmpopenNumParma = bizParamCfgService
                .getSamePlanTmpopenNumRight(planid, loginInfo);
        if (StringUtils.isBlank(sameplanTmpopenNumParma)) {
            throw new BusinessException("同一方案体验授权次数参数配置有误，请联系管理员");
        }
        int sameplanTmpopenNum = Integer.valueOf(sameplanTmpopenNumParma);
        if (sameplanTmpopenNum > 0) {

            StringBuffer sql = new StringBuffer();
            List paramList = new ArrayList();
            sql.setLength(0);
            sql.append(" SELECT * from ( ");
            sql.append(" SELECT DISTINCT o.ORDERID id ");
            sql.append("   FROM biz_apply_tmpopen t, biz_custorder o ");
            sql.append("  WHERE t.ORDERID = O.ORDERID ");
            sql.append("    AND o.OPTIME > DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY) ");
            sql.append("    AND o.OPERATOR = ? ");
            paramList.add(loginInfo.getOperid());
            sql.append("    AND t.PLANID = ? ");
            paramList.add(planid);
            sql.append("    ) v ");

            List<CustOrder> custorderList = getDAO().find(sql.toString(),
                    CustOrder.class, paramList.toArray());
            if (BeanUtil.isListNotNull(custorderList)) {
                int hasOptCnt = custorderList.size();
                if (hasOptCnt >= sameplanTmpopenNum) {
                    throw new BusinessException("您本月对该方案已经达到最大可操作次数");
                }

            }
        } else if (0 == sameplanTmpopenNum) {
            throw new BusinessException("您无权操作此业务，请联系管理员");
        } else {
            // 小于0表示无限制

        }

    }

    private void checkTmpopenOptNum(LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(loginInfo, "检查体验授权次数:操作员登录信息不能为空");

        String tmpopenNumParma = bizParamCfgService
                .getTmpopenNumRight(loginInfo);
        if (StringUtils.isBlank(tmpopenNumParma)) {
            throw new BusinessException("体验授权操作次数参数配置有误，请联系管理员");
        }
        int tmpopenNum = Integer.valueOf(tmpopenNumParma);
        if (tmpopenNum > 0) {

            StringBuffer sql = new StringBuffer();
            List paramList = new ArrayList();
            sql.setLength(0);
            sql.append(" SELECT * from ( ");
            sql.append(" SELECT DISTINCT o.ORDERID id ");
            sql.append("   FROM biz_apply_tmpopen t, biz_custorder o ");
            sql.append("  WHERE t.ORDERID = O.ORDERID ");
            sql.append("    AND o.OPTIME > DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY) ");
            sql.append("    AND o.OPERATOR = ? ");
            paramList.add(loginInfo.getOperid());
            sql.append("    ) v ");

            List<CustOrder> custorderList = getDAO().find(sql.toString(),
                    CustOrder.class, paramList.toArray());
            if (BeanUtil.isListNotNull(custorderList)) {
                int hasOptCnt = custorderList.size();
                if (hasOptCnt >= tmpopenNum) {
                    throw new BusinessException("您本月已经达到最大可操作次数");
                }

            }
        } else if (0 == tmpopenNum) {
            throw new BusinessException("您无权操作此业务，请联系管理员");
        } else {
            // 小于0表示无限制

        }

    }

    private void regBizdata4bizTempopen(BizTempopenInterReq req,
            LoginInfo loginInfo) throws Exception {
        // 登记客户订单表 (BIZ_CUSTORDER), OPCODE= BIZ_TEMPOPEN，SYNCMODE=SYNC
        // 登记BIZ_APPLY_TMPOPEN
        regBizCustorder4bizTempopen(req, loginInfo);

        regBizApplyTmpOpen4bizTempopen(req, loginInfo);

    }

    private List<ApplyTmpOpen> regBizApplyTmpOpen4bizTempopen(
            BizTempopenInterReq req, LoginInfo loginInfo) throws Exception {

        BizTempopenInterReq reqBO = new BizTempopenInterReq();
        BeanUtils.copyProperties(reqBO, req);

        List<ApplyTmpOpen> retList = new ArrayList();
        String[] pidArray = req.getPids().split(",");
        for (String pid : pidArray) {
            if (StringUtils.isBlank(pid)) {
                continue;
            }

            reqBO.setPids(pid);
            ApplyTmpOpen bizApplyTmpopen = getBizApplyTmpOpenInfoFromBizTempopenInterReq(
                    reqBO, loginInfo);

            getDAO().save(bizApplyTmpopen);

            retList.add(bizApplyTmpopen);
        }

        return retList;
    }

    private ApplyTmpOpen getBizApplyTmpOpenInfoFromBizTempopenInterReq(
            BizTempopenInterReq req, LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "获取体验授权信息:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取体验授权信息:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取体验授权信息:订单id不能为空");
        CheckUtils.checkEmpty(req.getPlanid(), "获取体验授权信息:点通方案id不能为空");
        CheckUtils.checkEmpty(req.getServid(), "获取体验授权信息:用户id不能为空");
        CheckUtils.checkEmpty(req.getPids(), "获取体验授权信息:授权产品不能为空");
        CheckUtils.checkEmpty(req.getDays(), "获取体验授权信息:授权时长不能为空");

        ApplyTmpOpen applyTmpopen = new ApplyTmpOpen();
        applyTmpopen.setCity(loginInfo.getCity());
        applyTmpopen.setDays(Integer.valueOf(req.getDays()));
        applyTmpopen.setLogicdevno(req.getKeyno());
        applyTmpopen.setLogicstbno(req.getLogicstbno());
        applyTmpopen.setOrderid(Long.valueOf(req.getBizorderid()));
        applyTmpopen.setPid(Long.valueOf(req.getPids()));
        applyTmpopen.setPlanid(Long.valueOf(req.getPlanid()));
        applyTmpopen.setPlanname(req.getPlanname());
        // applyTmpopen.setRecid(recid);
        applyTmpopen.setServid(Long.valueOf(req.getServid()));

        return applyTmpopen;

    }

    private CustOrder regBizCustorder4bizTempopen(BizTempopenInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(loginInfo, "登记客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "登记客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "登记客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "登记客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "登记客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "登记客户订单:地址不能为空");

        CustOrder bizCustOrder = getBizCustOrderInfoFromBizTempopenInterReq(
                req, loginInfo);

        getDAO().save(bizCustOrder);

        return bizCustOrder;

    }

    private CustOrder getBizCustOrderInfoFromBizTempopenInterReq(
            BizTempopenInterReq req, LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "获取客户订单:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "获取客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "获取客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "获取客户订单:地址不能为空");

        String bossserialno = null;
        BizGridInfo bizGrid = pubService.getGrid4Biz(
                Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()),
                loginInfo);

        CustOrder bizCustOrder = new CustOrder();
        bizCustOrder.setAddr(req.getWhladdr());
        bizCustOrder.setAreaid(Long.valueOf(req.getAreaid()));
        bizCustOrder.setBossserialno(bossserialno);
        bizCustOrder.setCanceltime(null);
        bizCustOrder.setCity(loginInfo.getCity());
        bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
        bizCustOrder.setDescrip(req.getDescrip());
        bizCustOrder.setGridid(bizGrid.getId());
        bizCustOrder.setLockoper(null);
        bizCustOrder.setName(req.getName());
        bizCustOrder.setOpcode(BizConstant.BizOpcode.BIZ_TEMPOPEN);
        bizCustOrder.setOperator(loginInfo.getOperid());
        bizCustOrder.setOprdep(loginInfo.getDeptid());
        bizCustOrder.setOptime(new Date());
        bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
        bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
        bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
        bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
        bizCustOrder.setPortalOrder(null);

        return bizCustOrder;
    }

    private void callBossInf4bizTempopen(BizTempopenInterReq req,
            BizTempopenInterResp resp, LoginInfo loginInfo) throws Exception {

        // 将请求做一下转换，并赋默认值
        BizTempopenBossReq req2Boss = getBizTempopenBossReq2Boss(req);

        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
                BizConstant.BossInterfaceService.BIZ_TEMPOPEN, req2Boss,
                loginInfo);

        copyBossResp2InterResp4bizTempopen(resp, bossRespOutput);

    }

    private void copyBossResp2InterResp4bizTempopen(BizTempopenInterResp resp,
            String jsonStr) throws Exception {
        BizTempopenBossResp bossResp = (BizTempopenBossResp) BeanUtil
                .jsonToObject(jsonStr, BizTempopenBossResp.class);

        // 因为字段是一样的，这里可以偷懒一下，
        // 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
        // BeanUtils.copyProperties(resp, bossResp);
        // resp.setAcctkind(bossResp.getAcctkind());

    }

    private BizTempopenBossReq getBizTempopenBossReq2Boss(
            BizTempopenInterReq req) {
        BizTempopenBossReq bizTempopenBossReq = new BizTempopenBossReq();
        bizTempopenBossReq.setKeyno(req.getKeyno());
        bizTempopenBossReq.setPcode(req.getPcode());
        bizTempopenBossReq.setPlanid(req.getPlanid());

        return bizTempopenBossReq;
    }

    /**
     * 刷新授权
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo bizFreshauth(BizFreshauthInterReq req,
            BizFreshauthInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        // 参数验证 --TODO
        //  登记轨迹表(BIZ_TRACE)
        //  登记客户订单表 (BIZ_CUSTORDER) OPCODE= BIZ_REFLESH_AUTH，SYNCMODE=SYNC
        // 登记BIZ_APPLY_REFRESH

        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "业务订单号不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");

        pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()),
                Long.valueOf(req.getPatchid()), loginInfo);

        // 写业务表
        regBizdata4bizFreshauth(req, loginInfo);

        // 调boss接口,并将结果存入resp
        callBossInf4bizFreshauth(req, resp, loginInfo);

        resp.setCustorderid(req.getBizorderid());

        return returnInfo;
    }

    private void callBossInf4bizFreshauth(BizFreshauthInterReq req,
            BizFreshauthInterResp resp, LoginInfo loginInfo) throws Exception {

        // 将请求做一下转换，并赋默认值
        BizFreshauthBossReq req2Boss = getBizFreshauthBossReq2Boss(req);

        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
                BizConstant.BossInterfaceService.BIZ_FRESHAUTH, req2Boss,
                loginInfo);

        copyBossResp2InterResp4bizFreshauth(resp, bossRespOutput);
        ReturnVisitTaskService.addTask(Long.parseLong(req.getBizorderid()));

    }

    private void copyBossResp2InterResp4bizFreshauth(
            BizFreshauthInterResp resp, String bossRespOutput) {
        // 因为字段是一样的，这里可以偷懒一下，
        // 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
        // BeanUtils.copyProperties(resp, bossResp);
        // resp.setAcctkind(bossResp.getAcctkind());

    }

    private BizFreshauthBossReq getBizFreshauthBossReq2Boss(
            BizFreshauthInterReq req) {
        BizFreshauthBossReq bizFreshauthBossReq = new BizFreshauthBossReq();
        bizFreshauthBossReq.setKeyno(req.getKeyno());
        bizFreshauthBossReq.setPermark(req.getPermark());
        bizFreshauthBossReq.setServid(req.getServid());

        return bizFreshauthBossReq;
    }

    private void regBizdata4bizFreshauth(BizFreshauthInterReq req,
            LoginInfo loginInfo) throws Exception {
        //  登记客户订单表 (BIZ_CUSTORDER) OPCODE= BIZ_REFLESH_AUTH，SYNCMODE=SYNC
        // 登记BIZ_APPLY_REFRESH
        regBizCustorder4bizFreshauth(req, loginInfo);

        regBizApplyRefresh4bizFreshauth(req, loginInfo);

    }

    private ApplyRefresh regBizApplyRefresh4bizFreshauth(
            BizFreshauthInterReq req, LoginInfo loginInfo) throws Exception {

        ApplyRefresh bizApplyTmpopen = getBizApplyRefreshInfoFromBizFreshauthInterReq(
                req, loginInfo);

        getDAO().save(bizApplyTmpopen);

        return bizApplyTmpopen;

    }

    private ApplyRefresh getBizApplyRefreshInfoFromBizFreshauthInterReq(
            BizFreshauthInterReq req, LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "获取刷新授权信息:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取刷新授权信息:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取刷新授权信息:订单id不能为空");
        CheckUtils.checkEmpty(req.getServid(), "获取刷新授权信息:用户id不能为空");
        // CheckUtils.checkEmpty(req.getPid(), "获取刷新授权信息:产品id不能为空");

        String opkind = BizConstant.SysFreshKind.FREASH;
        if (StringUtils.isNotBlank(req.getOpkind())) {
            opkind = req.getOpkind();
        }

        ApplyRefresh bizApplyRefresh = new ApplyRefresh();
        bizApplyRefresh.setCity(loginInfo.getCity());
        bizApplyRefresh.setLogicdevno(req.getKeyno());
        bizApplyRefresh.setLogicstbno(req.getLogicstbno());
        bizApplyRefresh.setOpkind(opkind);
        bizApplyRefresh.setOrderid(Long.valueOf(req.getBizorderid()));
        if (StringUtils.isNotBlank(req.getPid())) {
            bizApplyRefresh.setPid(Long.valueOf(req.getPid()));
        }
        // bizApplyRefresh.setRecid(recid);
        bizApplyRefresh.setServid(Long.valueOf(req.getServid()));

        return bizApplyRefresh;
    }

    private CustOrder regBizCustorder4bizFreshauth(BizFreshauthInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(loginInfo, "登记客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "登记客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "登记客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "登记客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "登记客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "登记客户订单:地址不能为空");

        CustOrder bizCustOrder = getBizCustOrderInfoFromBizFreshauthInterReq(
                req, loginInfo);

        getDAO().save(bizCustOrder);

        return bizCustOrder;

    }

    private CustOrder getBizCustOrderInfoFromBizFreshauthInterReq(
            BizFreshauthInterReq req, LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "获取客户订单:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "获取客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "获取客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "获取客户订单:地址不能为空");

        String bossserialno = null;
        BizGridInfo bizGrid = pubService.getGrid4Biz(
                Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()),
                loginInfo);

        CustOrder bizCustOrder = new CustOrder();
        bizCustOrder.setAddr(req.getWhladdr());
        bizCustOrder.setAreaid(Long.valueOf(req.getAreaid()));
        bizCustOrder.setBossserialno(bossserialno);
        bizCustOrder.setCanceltime(null);
        bizCustOrder.setCity(loginInfo.getCity());
        bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
        bizCustOrder.setDescrip(req.getDescrip());
        bizCustOrder.setGridid(bizGrid.getId());
        bizCustOrder.setLockoper(null);
        bizCustOrder.setName(req.getName());
        bizCustOrder.setOpcode(BizConstant.BizOpcode.BIZ_FRESHAUTH);
        bizCustOrder.setOperator(loginInfo.getOperid());
        bizCustOrder.setOprdep(loginInfo.getDeptid());
        bizCustOrder.setOptime(new Date());
        bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
        bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
        bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
        bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
        bizCustOrder.setPortalOrder(null);

        return bizCustOrder;

    }

    /**
     * 绑定银行卡
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo bizBindbank(BizBindbankInterReq req,
            BizBindbankInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        // 参数验证 --TODO
        // 登记轨迹表(BIZ_TRACE)
        // 登记客户订单表 (BIZ_CUSTORDER),OPCODE= BIZ_BIND_BANK，SYNCMODE=SYNC
        // 登记申请银行登记表 (BIZ_APPLY_BANK)

        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "业务订单号不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");

        pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()),
                Long.valueOf(req.getPatchid()), loginInfo);

        // 写业务表
        regBizdata4bizBindbank(req, loginInfo);

        // 调boss接口,并将结果存入resp
        callBossInf4bizBindbank(req, resp, loginInfo);

        resp.setCustorderid(req.getBizorderid());

        return returnInfo;
    }

    private void regBizdata4bizBindbank(BizBindbankInterReq req,
            LoginInfo loginInfo) throws Exception {
        // 登记客户订单表 (BIZ_CUSTORDER),OPCODE= BIZ_BIND_BANK，SYNCMODE=SYNC
        // 登记申请银行登记表 (BIZ_APPLY_BANK)

        regBizCustorder4bizBindbank(req, loginInfo);

        regBizApplyBank4bizBindbank(req, loginInfo);

    }

    private List<ApplyBank> regBizApplyBank4bizBindbank(
            BizBindbankInterReq req, LoginInfo loginInfo) throws Exception {

        CheckUtils.checkEmpty(req.getAcctno(), "获取银行信息:银行账号不能为空");
        CheckUtils.checkEmpty(req.getAcctname(), "登记银行信息:账户名不能为空");
        CheckUtils.checkEmpty(req.getBankcode(), "登记银行信息:银行名编号不能为空");
        CheckUtils.checkEmpty(req.getCardtype(), "登记银行信息:银行证件类型不能为空");
        CheckUtils.checkEmpty(req.getCardno(), "登记银行信息:银行证件号码不能为空");
        CheckUtils.checkEmpty(req.getAcctkind(), "登记银行信息:帐号类型不能为空");

        CheckUtils.checkEmpty(req.getCustid(), "登记银行信息:客户id不能为空");
        CheckUtils.checkNull(req.getServs(), "登记银行信息:用户信息不能为空");

        List<ApplyBank> bizApplyBankList = getApplyBankInfoFromBizBindbankInterReq(
                req, loginInfo);

        if (null != bizApplyBankList && bizApplyBankList.size() > 0
                && !bizApplyBankList.isEmpty()) {

            for (ApplyBank bizApplyBank : bizApplyBankList) {
                getDAO().save(bizApplyBank);
            }
        }

        return bizApplyBankList;
    }

    private List<ApplyBank> getApplyBankInfoFromBizBindbankInterReq(
            BizBindbankInterReq req, LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "获取银行信息:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取银行信息登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取银行信息:订单id不能为空");

        String payway = BizConstant.SysFeeway.BANK;

        ApplyBank bizApplyBank = new ApplyBank();
        bizApplyBank.setAcctkind(req.getAcctkind());
        bizApplyBank.setAcctname(req.getAcctname());
        bizApplyBank.setAcctno(req.getAcctno());
        bizApplyBank.setAccttype(req.getAccttype());
        bizApplyBank.setBankcode(req.getBankcode());
        bizApplyBank.setCity(loginInfo.getCity());
        bizApplyBank.setOrderid(Long.valueOf(req.getBizorderid()));
        // 如果银行信息不为空，则payway为银行托收，否则为现金缴费。那如果是升级的业务又没传银行信息呢，默认为用原来的?
        bizApplyBank.setPayway(payway);
        // bizApplyBank.setRecid(recid);
        bizApplyBank.setServid(null);

        List<ApplyBank> retBizApplyBankList = new ArrayList();
        for (BindbankServInfoBO serv : req.getServs()) {
            if (StringUtils.isBlank(serv.getServid())) {
                continue;
            }
            ApplyBank bizApplyBankBO = new ApplyBank();
            BeanUtils.copyProperties(bizApplyBankBO, bizApplyBank);

            bizApplyBankBO.setServid(Long.valueOf(serv.getServid()));
            retBizApplyBankList.add(bizApplyBankBO);
        }

        if (retBizApplyBankList.isEmpty()) {
            // 走到这里，说明没传用户id进来，则返回没有用户id的对象
            retBizApplyBankList.add(bizApplyBank);
        }

        return retBizApplyBankList;
    }

    private CustOrder regBizCustorder4bizBindbank(BizBindbankInterReq req,
            LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(loginInfo, "登记客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "登记客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "登记客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "登记客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "登记客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "登记客户订单:地址不能为空");

        CustOrder bizCustOrder = getBizCustOrderInfoFromBizBindbankInterReq(
                req, loginInfo);

        getDAO().save(bizCustOrder);

        return bizCustOrder;

    }

    private CustOrder getBizCustOrderInfoFromBizBindbankInterReq(
            BizBindbankInterReq req, LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "获取客户订单:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "获取客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "获取客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "获取客户订单:地址不能为空");

        String bossserialno = null;
        BizGridInfo bizGrid = pubService.getGrid4Biz(
                Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()),
                loginInfo);

        CustOrder bizCustOrder = new CustOrder();
        bizCustOrder.setAddr(req.getWhladdr());
        bizCustOrder.setAreaid(Long.valueOf(req.getAreaid()));
        bizCustOrder.setBossserialno(bossserialno);
        bizCustOrder.setCanceltime(null);
        bizCustOrder.setCity(loginInfo.getCity());
        bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
        bizCustOrder.setDescrip(req.getDescrip());
        bizCustOrder.setGridid(bizGrid.getId());
        bizCustOrder.setLockoper(null);
        bizCustOrder.setName(req.getName());
        bizCustOrder.setOpcode(BizConstant.BizOpcode.BIZ_BIND_BANK);
        bizCustOrder.setOperator(loginInfo.getOperid());
        bizCustOrder.setOprdep(loginInfo.getDeptid());
        bizCustOrder.setOptime(new Date());
        bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
        bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
        bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
        bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
        bizCustOrder.setPortalOrder(null);
        bizCustOrder.setVerifyphone(req.getVerifyphone());

        return bizCustOrder;
    }

    private void callBossInf4bizBindbank(BizBindbankInterReq req,
            BizBindbankInterResp resp, LoginInfo loginInfo) throws Exception {

        // 将请求做一下转换，并赋默认值
        BizBindbankBossReq req2Boss = getBizBindbankBossReq2Boss(req);

        String serverCode = "";
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			serverCode = BizConstant.ServerCityBossInterface.BIZ_BINDBANK;
		}else{
			serverCode = BizConstant.BossInterfaceService.BIZ_BINDBANK;
		}
        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
        		serverCode, req2Boss,
                loginInfo);

        copyBossResp2InterResp4bizBindbank(resp, bossRespOutput);

    }

    private void copyBossResp2InterResp4bizBindbank(BizBindbankInterResp resp,
            String bossRespOutput) {
        // 因为字段是一样的，这里可以偷懒一下，
        // 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
        // BeanUtils.copyProperties(resp, bossResp);
        // resp.setAcctkind(bossResp.getAcctkind());

    }

    private BizBindbankBossReq getBizBindbankBossReq2Boss(
            BizBindbankInterReq req) throws Exception {
        BizBindbankBossReq bizBindbankBossReq = new BizBindbankBossReq();

        bizBindbankBossReq.setBankcode(req.getBankcode());
        bizBindbankBossReq.setAcctno(req.getAcctno());
        bizBindbankBossReq.setAcctname(req.getAcctname());
        bizBindbankBossReq.setAcctkind(req.getAcctkind());
        
        bizBindbankBossReq.setBankid(req.getBankid());
        bizBindbankBossReq.setCardno(req.getCardno());
        bizBindbankBossReq.setCardtype(req.getCardtype());
        bizBindbankBossReq.setCustid(req.getCustid());
        
        bizBindbankBossReq.setServs(req.getServs());
        
        //可以默认的参数
        bizBindbankBossReq.setAccttype(req.getAccttype());
        bizBindbankBossReq.setDeductrate(req.getDeductrate());
        bizBindbankBossReq.setDefaulted(req.getDefaulted());
        bizBindbankBossReq.setTransacctno(req.getTransacctno());
        
    	//Action 1表示绑定；2表示签约；3表示重新签约；4删除签约;5删除银行信息;6删除旧卡重新绑定
        bizBindbankBossReq.setAction(req.getAction());
        if(req.getAction().equals("6")){
        	if(StringUtils.isBlank(req.getOldbankid())){
        		CheckUtils.checkNull(null, "旧银行卡信息不能为空");
        	}
        }
        bizBindbankBossReq.setOldbankid(req.getOldbankid());
        
        //设置默认值
        if(StringUtils.isBlank(bizBindbankBossReq.getAccttype())){
            bizBindbankBossReq.setAccttype(BizConstant.SysAcctType.SYS_ACCT_TYPE_PRIVATE);
        }
        if(StringUtils.isBlank(bizBindbankBossReq.getDeductrate())){
            bizBindbankBossReq.setDeductrate("0");
        }
        if(StringUtils.isBlank(bizBindbankBossReq.getDefaulted())){
            bizBindbankBossReq.setDefaulted("N");
        }

        return bizBindbankBossReq;

    }
    
    public BizPreprocessInterResp bizOrder4Cart(BizPreprocessInterReq req) throws Exception{
    	BizPreprocessInterResp resp = new BizPreprocessInterResp();
    	bizPreprocess(req,resp);
    	return resp;
    }

    // --================订购鉴权接口 begin ========================//
    /**
     * 订购鉴权接口
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo bizPreprocess(BizPreprocessInterReq req,
            BizPreprocessInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        // 参数验证 --TODO
        // 登记轨迹表(BIZ_TRACE)
        // 登记客户订单表 (BIZ_CUSTORDER),OPCODE= BIZ_PRD_ORDER，SYNCMODE=SYNC
        // 登记申请产品信息表(BIZ_APPLY_PRODUCT)
        // 登记BIZ_APPLY_PRODUCT_SOFT
        // 登记BIZ_APPLY_PRODUCT_SELECT

        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
      //ywp 设置对应的业务区不判断网格权限
        /*Rule rule = ruleService.getRule(loginInfo.getCity(), BizConstant.BizRuleParams.NOT_JUDGE_GRID);
		boolean flag = true;
		if(null!=rule){
			String areaids = rule.getAreaIds();
			if(null!=areaids){
				if("*".equals(areaids)||areaids.indexOf(loginInfo.getAreaid()+"")>-1){
					flag = false;
				}
			}
		}
		System.out.println("NOT_JUDGE_GRID==flag="+flag);
		if(flag){
			pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()),
	                Long.valueOf(req.getPatchid()), loginInfo);
		}*/
		pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()),
                Long.valueOf(req.getPatchid()), loginInfo);
        // 检查知识库营销产品可销售的数量
        checkKnowSalesNum(req.getOrderparams(), loginInfo);

        // 为接口参数设置默认值
        setParamDefaultValue4bizPreprocess(req, loginInfo);

        // 写业务表
        CustOrder custOrder = regBizCustorder4bizPreprocess(req, loginInfo);
        procBizApplyProduct4bizPreprocess(custOrder.getId(),req, loginInfo);//写入选择性产品信息表

        // 调boss接口,并将结果存入resp
        callBossInf4bizPreprocessnewgz(req, resp, loginInfo,custOrder);
        return returnInfo;
    }
    
    /**
     * 订购鉴权接口
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo bizPreprocessZs(BizPreprocessInterReq req,
            BizPreprocessInterResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
      //ywp 设置对应的业务区不判断网格权限

		pubService.checkBizAcceptRight(Long.valueOf(req.getHouseid()),
                Long.valueOf(req.getPatchid()), loginInfo);
        // 检查知识库营销产品可销售的数量
        checkKnowSalesNum(req.getOrderparams(), loginInfo);

        // 为接口参数设置默认值
        setParamDefaultValue4bizPreprocess(req, loginInfo);

        // 写业务表
        CustOrder custOrder = regBizCustorder4bizPreprocess(req, loginInfo);
        procBizApplyProduct4bizPreprocessZs(custOrder.getId(),req, loginInfo);//写入选择性产品信息表

        // 调boss接口,并将结果存入resp
        callBossInf4bizPreprocess(req, resp, loginInfo,custOrder);

        return returnInfo;
    }
    

    private void setParamDefaultValue4bizPreprocess(BizPreprocessInterReq req,
            LoginInfo loginInfo) throws Exception {
        if (BeanUtil.isListNull(req.getOrderparams())) {
            return;
        }

        // count~uint~ispostphone
        String orderparamDefValue = bizParamCfgService.getBizParamCfg(
                BizConstant.BizParamCfgName.BIZ_PRD_ORDERPARAM_DEFVALUE_CFG,
                null, loginInfo);
        if (StringUtils.isBlank(orderparamDefValue)) {
            throw new BusinessException(
                    "参数[BIZ_PRD_ORDERPARAM_DEFVALUE_CFG]配置有误，请联系管理员");
        }
        String[] defValueArray = orderparamDefValue.split(Separator.WAVE);
        if (defValueArray.length != 3 || StringUtils.isBlank(defValueArray[0])
                || StringUtils.isBlank(defValueArray[1])
                || StringUtils.isBlank(defValueArray[2])) {
            throw new BusinessException("参数[BIZ_PRD_ORDERPARAM_DEFVALUE_CFG]配置有误，请联系管理员");
        }

        String defCount = defValueArray[0];
        String defUnit = defValueArray[1];
        String defispostpone = defValueArray[2];
        for (OrderParamsInterBO obj : req.getOrderparams()) {
            if (StringUtils.isBlank(obj.getCount())) {
                obj.setCount(defCount);
            }
            if (StringUtils.isBlank(obj.getUnit())) {
                obj.setUnit(defUnit);
            }
            if (StringUtils.isBlank(obj.getIspostpone())) {
                obj.setIspostpone(defispostpone);
            }
        }

    }

    private void checkKnowSalesNum(List<OrderParamsInterBO> orderparams,
            LoginInfo loginInfo) throws Exception {
        if (BeanUtil.isListNull(orderparams)) {
            return;
        }

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();

        for (OrderParamsInterBO obj : orderparams) {
            if (StringUtils.isBlank(obj.getKnowid())) {
                continue;
            }

            SalespkgKnow salespkgKnow = new SalespkgKnow();
            salespkgKnow.setKnowid(Long.valueOf(obj.getKnowid()));
            List<SalespkgKnow> salespkgKnowList = getDAO().find(salespkgKnow);
            if (BeanUtil.isListNull(salespkgKnowList)) {
                throw new BusinessException("检查产品可销售次数:根据知识库营销id["
                        + obj.getKnowid() + "]查询不到营销产品信息");
            }
            salespkgKnow = salespkgKnowList.get(0);
            if (salespkgKnow.getMaxtimes()==null || salespkgKnow.getMaxtimes() <= 0 ) {
                continue;
            }

            sql.setLength(0);
            paramList.clear();
            sql.append(" SELECT * FROM ( ");
            sql.append(" SELECT DISTINCT p.orderid id");
            sql.append("   FROM biz_apply_product t, biz_custorder o, BIZ_PORTAL_ORDER P ");
            sql.append("  WHERE t.ORDERID = O.ORDERID ");
            sql.append("    AND P.orderid = O.ORDERID ");
            // sql.append("    AND o.OPTIME > DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY) ");
            sql.append("    AND t.OSTATUS = ? ");
            paramList.add(BizConstant.BizApplyProductOstatus.ORDER);
            sql.append("    AND p.status IN (?, ?) ");
            paramList
                    .add(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
            paramList
                    .add(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
            sql.append("    AND o.ORDERSTATUS != 'CANCEL' ");

            sql.append("    AND t.KNOWID = ? ");
            paramList.add(obj.getKnowid());
            sql.append("    ) v ");

            List<CustOrder> salesList = getDAO().find(sql.toString(),
                    CustOrder.class, paramList.toArray());
            if (BeanUtil.isListNotNull(salesList)) {
                long hasSalesCnt = salesList.size();
                if (hasSalesCnt >= salespkgKnow.getMaxtimes()) {
                    throw new BusinessException("该产品已经达到最大可销售数量");
                }

            }

        }

    }

    private void callBossInf4bizPreprocess(BizPreprocessInterReq req,
            BizPreprocessInterResp resp, LoginInfo loginInfo,CustOrder custOrder) throws Exception {

        // 将请求做一下转换，并赋默认值
        BizPreprocessBossReq req2Boss = getBizPreprocessBossReq2Boss(req,loginInfo);
        String serverCode = "";
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			serverCode = BizConstant.ServerCityBossInterface.BIZ_PREPROCESS;
		}else{
			serverCode = BizConstant.BossInterfaceService.BIZ_PREPROCESS;
		}
        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
        		serverCode, req2Boss,loginInfo);

        // copy boss返回结果
        copyBossResp2InterResp4bizPreprocess(resp, bossRespOutput);
//        resp.setCustorderid(req.getBizorderid());
        resp.setCustorderid(String.valueOf(custOrder.getId()));

        // 保存boss订单信息
        this.savePortalOrder(String.valueOf(custOrder.getId()), bossRespOutput);

    }
    private void callBossInf4bizPreprocessnewgz(BizPreprocessInterReq req,
                                           BizPreprocessInterResp resp, LoginInfo loginInfo,CustOrder custOrder) throws Exception {

        // 将请求做一下转换，并赋默认值
        BizPreprocessBossReq req2Boss = getBizPreprocessBossReq2Boss(req,loginInfo);
        String serverCode = "";
        if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
            serverCode = BizConstant.ServerCityBossInterface.BIZ_PREPROCESS;
        }else{
            serverCode = BizConstant.BossInterfaceService.BIZ_PREPROCESS;
        }
        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
                serverCode, req2Boss,loginInfo);

        // copy boss返回结果
        copyBossResp2InterResp4bizPreprocess(resp, bossRespOutput);
//        resp.setCustorderid(req.getBizorderid());
        resp.setCustorderid(String.valueOf(custOrder.getId()));

        // 保存boss订单信息
        this.savePortalOrdernew(String.valueOf(custOrder.getId()), bossRespOutput,req);

    }
    private void copyBossResp2InterResp4bizPreprocess(
            BizPreprocessInterResp resp, String jsonStr) throws Exception {

        BizPreprocessBossResp bossResp = (BizPreprocessBossResp) BeanUtil
                .jsonToObject(jsonStr, BizPreprocessBossResp.class);

        // 如果字段是一样的，这里可以偷懒一下，
        // 后面如果接口对外字段和boss返回字段不一致，则自行写使用set方法设置值
        // BeanUtils.copyProperties(resp, bossResp);
        resp.setBossorderid(bossResp.getOrderid());
        resp.setFeename(bossResp.getFeename());
        resp.setOrdertype(bossResp.getOrdertype());
        resp.setSums(bossResp.getSums());

        resp.setCminfo(bossResp.getCminfo());
    }
    
    public BizPreprocessBossReq getBizPreprocessBossReq2Boss(
            BizPreprocessInterReq req,LoginInfo loginInfo) throws Exception {

        List<OrderParamsBossBO> orderParamList = new ArrayList();
        List<EjectParamsBossBO> ejectParamList = new ArrayList();

        if (null != req.getEjectparams()) {
            for (EjectParamsInterBO obj : req.getEjectparams()) {
                if (StringUtils.isBlank(obj.getEjecttype())) {
                    throw new BusinessException("获取订购参数:退订类型不能为空");
                }
                List<EjectPrdsBossBO> ejectPrdList = null;
                List<EjectPkgsBossBO> ejectPkgList = null;
                if (BizConstant.BizEjectType.PRD.equals(obj.getEjecttype())) {
                    ejectPrdList = copyEjectPrdInterBO2EjectPrdsBossBO(obj
                            .getEjectPrds());
                } else if (BizConstant.BizEjectType.PKG.equals(obj
                        .getEjecttype())) {
                    ejectPkgList = copyEjectPkgInterBO2EjectPkgsBossBO(obj
                            .getEjectPkgs());
                }

                EjectParamsBossBO ejectParam = new EjectParamsBossBO();
                ejectParam.setEjecttype(obj.getEjecttype());
                ejectParam.setEjectPkgs(ejectPkgList);
                ejectParam.setEjectPrds(ejectPrdList);
                ejectParamList.add(ejectParam);
            }

        }

        if (null != req.getOrderparams()) {
            for (OrderParamsInterBO obj : req.getOrderparams()) {
                if (StringUtils.isBlank(obj.getKnowid())) {
                    throw new BusinessException("获取订购参数:营销标识不能为空");
                }

                // 取知识库营销信息
                SalespkgKnowObjInfoBO knowObjInfo = getSalespkgKnowObjInfo(obj
                        .getKnowid());
                if (null == knowObjInfo) {
                    throw new BusinessException("获取订购参数:根据营销标识["
                            + obj.getKnowid() + "]查询不到营销配置信息");
                }

                List<ChopcodesBossBO> chopcodeList = copyChopcodes2ChopcodesBossBO(obj
                        .getChopcodes());

                OrderParamsBossBO orderParam = new OrderParamsBossBO();
                orderParam.setChopcodes(chopcodeList);
                orderParam.setCount(obj.getCount());
                //ywp 加上最小期限
                orderParam.setMindate(obj.getMindate());
                /**此处设置顺延规则*/
                
                if(StringUtils.isNotBlank(obj.getIspostpone())){
                	orderParam.setIspostpone(obj.getIspostpone());
                }else{
                	 if(knowObjInfo.getType().equals(BizConstant.PrdSalespkgKnowObjtype.SALESPKG)){
                     	if(orderParam.getCount().equals("0")){
                     		orderParam.setIspostpone("Y");
                     	}else{
                     		if(StringUtils.isBlank(knowObjInfo.getPermark()) || knowObjInfo.getPermark().equals("N")){
                     			orderParam.setIspostpone("N");
                     		}else{
                     			orderParam.setIspostpone("Y");
                     		}
                     	}
                     }else{
                     	orderParam.setIspostpone("Y");
                     }
                }
                
                orderParam.setKeyno(obj.getKeyno());
                orderParam.setOrdertype(knowObjInfo.getType());
                if(knowObjInfo.getPermark() == null || knowObjInfo.getPermark().equals("")){
                	orderParam.setPermark("A");
                }else{
                	 orderParam.setPermark(knowObjInfo.getPermark());
                }
                orderParam.setSalescode(knowObjInfo.getSalescode());
                orderParam.setSelpcodes(obj.getSelpcodes());
                orderParam.setUnit(obj.getUnit());
                orderParam.setStime(obj.getStime());
                orderParamList.add(orderParam);
            }

        }

        BizPreprocessBossReq bossReq = new BizPreprocessBossReq();
        bossReq.setBuff(req.getBuff());
        bossReq.setCustid(req.getCustid());
        bossReq.setGdno(req.getGdno());
        bossReq.setGdnoid(req.getGdnoid());
        bossReq.setIscrtorder(req.getIscrtorder());
        bossReq.setEjectparams(ejectParamList);
        bossReq.setOrderparams(orderParamList);
        if(StringUtils.isNotBlank(req.getDivide())){
        	bossReq.setDivide(req.getDivide());
        }

        if(req.getPreacceptserialno()!= null&& !"".equals(req.getPreacceptserialno())){
            bossReq.setPreacceptserialno(req.getPreacceptserialno());
        }
        //1员工发展
        bossReq.setDeveloper(new DeveloperBossBO("1", loginInfo.getDeptid(), loginInfo.getName()));
        return bossReq;
    }

    private List<EjectPkgsBossBO> copyEjectPkgInterBO2EjectPkgsBossBO(
            List<EjectPkgsInterBO> ejectPkgs) throws Exception {

        if (BeanUtil.isListNull(ejectPkgs)) {
            return null;
        }

        List<EjectPkgsBossBO> retEjectPkgsBossList = new ArrayList();
        for (EjectPkgsInterBO interBO : ejectPkgs) {
            EjectPkgsBossBO bossBO = new EjectPkgsBossBO();
            bossBO.setPkginsid(interBO.getPkginsid());
            bossBO.setServids(interBO.getServids());
            retEjectPkgsBossList.add(bossBO);
        }
        return retEjectPkgsBossList;
    }

    private List<EjectPrdsBossBO> copyEjectPrdInterBO2EjectPrdsBossBO(
            List<EjectPrdsInterBO> ejectPrds) {
        if (null == ejectPrds || ejectPrds.size() <= 0 || ejectPrds.isEmpty()) {
            return null;
        }

        List<EjectPrdsBossBO> retEjectPrdsBossList = new ArrayList();
        for (EjectPrdsInterBO interBO : ejectPrds) {
            EjectPrdsBossBO bossBO = new EjectPrdsBossBO();
            bossBO.setPids(interBO.getPids());
            bossBO.setServid(interBO.getServid());
            retEjectPrdsBossList.add(bossBO);
        }
        return retEjectPrdsBossList;
    }

    private List<ChopcodesBossBO> copyChopcodes2ChopcodesBossBO(
            List<ChopcodesBO> chopcodeList) throws Exception {
        if (null == chopcodeList) {
            return null;
        }

        List<ChopcodesBossBO> retChopcodesBossList = new ArrayList();
        for (ChopcodesBO chopcode : chopcodeList) {
            if (StringUtils.isBlank(chopcode.getPidstr())) {
                continue;
            }

            // 选择产品编码串不为空，则选择方案id不能为空
            if (StringUtils.isBlank(chopcode.getSelectid())) {
                throw new BusinessException("获取订购参数:选择方案id不能为空");
            }

            ChopcodesBossBO copcodesBossBO = new ChopcodesBossBO();
            copcodesBossBO.setSelectid(chopcode.getSelectid());

            // pcodestr格式:pcode1,pcode2,pcode3
            String pcodestr = chopcode.getPidstr();
            // String[] pcodeArray = pcodestr.split(",");
            // if (null != pcodeArray || pcodeArray.length <= 0) {
            // throw new BusinessException("获取订购参数:选择方案["
            // + chopcode.getSelectid() + "]中选择的选性产品编码串不能为空");
            // }
            //
            // String pidstr = "";
            // for(String pcode : pcodeArray){
            // Pcode pcodeVO = new Pcode();
            // pcodeVO.setPcode(pcode);
            //
            // List<Pcode> pcodeList = getDAO().find(pcodeVO);
            // if(null==pcodeList || pcodeList.size()<=0 ||
            // pcodeList.isEmpty()){
            // throw new BusinessException("获取订购参数:根产品编码["
            // + pcode + "]查询不到产品信息");
            // }
            //
            // pidstr = pidstr + pcodeList.get(0).getIsbase() + ",";
            // }
            // copcodesBossBO.setPidstr(pidstr);

            // 好吧，实际上boss接口的pidstr字段实际上是产品编码串,只不过是用~分割的
            String bossPcodestr = pcodestr.replaceAll(",", "~");
            copcodesBossBO.setPidstr(bossPcodestr);

            retChopcodesBossList.add(copcodesBossBO);
        }

        return retChopcodesBossList;

    }

    public SalespkgKnowObjInfoBO getSalespkgKnowObjInfo(String knowid)
            throws Exception {
        CheckUtils.checkEmpty(knowid, "获取营销对象信息:根据营销标识不能为空");

        SalespkgKnowObjInfoBO retBO = null;
        SalespkgKnow salespkgKnow = (SalespkgKnow) getDAO().find(
                SalespkgKnow.class, Long.valueOf(knowid));
        if (null == salespkgKnow || null == salespkgKnow.getObjid()) {
            throw new BusinessException("获取营销对象信息:根据营销标识[" + knowid
                    + "]查询不到销售配置信息");
        }
        if (BizConstant.PrdSalespkgKnowObjtype.PRD.equals(salespkgKnow
                .getObjtype())) {
            Pcode pcode = (Pcode) getDAO().find(Pcode.class,
                    salespkgKnow.getObjid());
            if (null == pcode) {
                throw new BusinessException("获取营销对象信息:根据营销对象id[" + knowid
                        + "]查询不到产品配置信息");
            }

            retBO = new SalespkgKnowObjInfoBO();
            retBO.setId(pcode.getId().toString());
            retBO.setName(pcode.getPname());
            retBO.setPermark(pcode.getPermark());
            retBO.setSalescode(pcode.getPcode());
            retBO.setType(BizConstant.PrdSalespkgKnowObjtype.PRD);
        } else if (BizConstant.PrdSalespkgKnowObjtype.SALESPKG
                .equals(salespkgKnow.getObjtype())) {
            Salespkg salespkg = (Salespkg) getDAO().find(Salespkg.class,
                    salespkgKnow.getObjid());
            if (null == salespkg) {
                throw new BusinessException("获取营销对象信息:根据营销对象id["
                        + salespkgKnow.getObjid() + "]查询不到营销方案配置信息");
            }
            

            retBO = new SalespkgKnowObjInfoBO();
            retBO.setId(salespkg.getId().toString());
            retBO.setName(salespkg.getSalespkgname());
            retBO.setPermark(salespkg.getPostflag());// boss接口该字段不能为空，但订营销方案时实际上没啥用，随便给个值
            retBO.setSalescode(salespkg.getSalespkgcode());
            retBO.setType(BizConstant.PrdSalespkgKnowObjtype.SALESPKG);
        }else if(BizConstant.PrdSalespkgKnowObjtype.GOODS.equals(salespkgKnow.getObjtype())
        		|| BizConstant.PrdSalespkgKnowObjtype.GOODS_TYPE.equals(salespkgKnow.getObjtype())){
        	Sales sales = (Sales) getDAO().find(Sales.class,
                    salespkgKnow.getObjid());
        	 if (null == sales) {
                 throw new BusinessException("获取商品信息:根据商品id["
                         + salespkgKnow.getObjid() + "]查询不到商品配置信息");
             }
        	 Pcode pcode = (Pcode) getDAO().find(Pcode.class,
                     sales.getPid());
             if (null == pcode) {
                 throw new BusinessException("获取营销对象信息:根据营销对象id[" + knowid
                         + "]查询不到产品配置信息");
             }
        	 retBO = new SalespkgKnowObjInfoBO();
             retBO.setId(sales.getId()+"");
             retBO.setName(sales.getSalesName());
             retBO.setSalescode(sales.getSalesCode());
             retBO.setPermark(pcode.getPermark());
             retBO.setType(salespkgKnow.getObjtype());
             retBO.setPid(sales.getPid()+"");
        }

        return retBO;

    }

    private void procBizApplyProduct4bizPreprocess(Long orderId,BizPreprocessInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "登记申请的产品信息:请求对象不能为空");
        // CheckUtils.checkNull(req.getOrderparams(), "登记订购产品信息:订购参数不能为空");
        if (null == req.getOrderparams() && null == req.getEjectparams()) {
            throw new BusinessException("登记申请的产品信息:退订参数和订购参数不能同时为空");
        }

        BizPreprocessInterReq reqBO = new BizPreprocessInterReq();
        BeanUtils.copyProperties(reqBO, req);

        if (null != reqBO.getEjectparams()) {
            for (EjectParamsInterBO ejectParam : reqBO.getEjectparams()) {
                List<ApplyProduct> ejectBizApplyProductList = regEjectBizApplyProduct4bizPreprocess(
                        reqBO, ejectParam, loginInfo);
            }
        }

        // 处理订购的
        if (null != reqBO.getOrderparams()) {
            for (OrderParamsInterBO orderParam : reqBO.getOrderparams()) {
                CheckUtils.checkEmpty(orderParam.getKnowid(),
                        "登记申请的产品信息:营销标识不能为空");

                // 取知识库存营销对象信息，备用
                SalespkgKnowsBO salespkgKnowsBO = interPrdService
                        .queSalespkgKnowDetByKnowid(Long.valueOf(orderParam
                                .getKnowid()));
                if (null == salespkgKnowsBO) {
                    throw new BusinessException("获取知识库营销对象明细信息:根据营销标识["
                            + orderParam.getKnowid() + "]询不到知识库营销对象信息");
                }

                ApplyProduct orederBizApplyProduct = regOrderBizApplyProduct4bizPreprocess(
                		orderId, orderParam, loginInfo);

                List<ApplyProductSoft> bizApplyProductSoftList = regOrderBizApplyProductSoft4bizPreprocess(
                        orderParam, salespkgKnowsBO,
                        orederBizApplyProduct.getRecid(), reqBO, loginInfo);

                List<ApplyProductSelect> bizApplyProductSelectList = regOrderBizApplyProductSelect4bizPreprocess(
                        orderParam, salespkgKnowsBO,
                        orederBizApplyProduct.getRecid(), reqBO, loginInfo);

            }
        }

    }
    
    private void procBizApplyProduct4bizPreprocessZs(Long orderId,BizPreprocessInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "登记申请的产品信息:请求对象不能为空");
        // CheckUtils.checkNull(req.getOrderparams(), "登记订购产品信息:订购参数不能为空");
        if (null == req.getOrderparams() && null == req.getEjectparams()) {
            throw new BusinessException("登记申请的产品信息:退订参数和订购参数不能同时为空");
        }

        BizPreprocessInterReq reqBO = new BizPreprocessInterReq();
        BeanUtils.copyProperties(reqBO, req);

        if (null != reqBO.getEjectparams()) {
            for (EjectParamsInterBO ejectParam : reqBO.getEjectparams()) {
                List<ApplyProduct> ejectBizApplyProductList = regEjectBizApplyProduct4bizPreprocess(
                        reqBO, ejectParam, loginInfo);
            }
        }

        // 处理订购的
        if (null != reqBO.getOrderparams()) {
            for (OrderParamsInterBO orderParam : reqBO.getOrderparams()) {
                CheckUtils.checkEmpty(orderParam.getKnowid(),
                        "登记申请的产品信息:营销标识不能为空");

                // 取知识库存营销对象信息，备用
                SalespkgKnowsBO salespkgKnowsBO = interPrdService
                        .queSalespkgKnowDetByKnowidZs(Long.valueOf(orderParam
                                .getKnowid()));
                if (null == salespkgKnowsBO) {
                    throw new BusinessException("获取知识库营销对象明细信息:根据营销标识["
                            + orderParam.getKnowid() + "]询不到知识库营销对象信息");
                }

                ApplyProduct orederBizApplyProduct = regOrderBizApplyProduct4bizPreprocess(
                		orderId, orderParam, loginInfo);

                List<ApplyProductSoft> bizApplyProductSoftList = regOrderBizApplyProductSoft4bizPreprocess(
                        orderParam, salespkgKnowsBO,
                        orederBizApplyProduct.getRecid(), reqBO, loginInfo);

                List<ApplyProductSelect> bizApplyProductSelectList = regOrderBizApplyProductSelect4bizPreprocess(
                        orderParam, salespkgKnowsBO,
                        orederBizApplyProduct.getRecid(), reqBO, loginInfo);

            }
        }

    }

    private List<ApplyProduct> regEjectBizApplyProduct4bizPreprocess(
            BizPreprocessInterReq req, EjectParamsInterBO ejectParam,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "登记退订产品:请求参数不能为空");
        CheckUtils.checkNull(ejectParam, "登记退订产品:退订参数不能为空");

        List<ApplyProduct> bizApplyProductList = getEjectBizApplyProductInfoFromEjectParamsInterInfo(
                req, ejectParam, loginInfo);

        if (null != bizApplyProductList && bizApplyProductList.size() >= 0
                && !bizApplyProductList.isEmpty()) {
            for (ApplyProduct applyProduct : bizApplyProductList) {

                getDAO().save(applyProduct);
            }
        }

        return bizApplyProductList;
    }

    private List<ApplyProduct> getEjectBizApplyProductInfoFromEjectParamsInterInfo(
            BizPreprocessInterReq req, EjectParamsInterBO ejectParam,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "获取退订产品:请求参数不能为空");
        CheckUtils.checkNull(ejectParam, "获取退订产品:退订参数对象不能为空");
        CheckUtils.checkEmpty(ejectParam.getEjecttype(), "获取退订产品:退订类型不能为空");

        List<ApplyProduct> retApplyProductList = new ArrayList();
        if (BizConstant.BizEjectType.PRD.equals(ejectParam.getEjecttype())) {
            if (null == ejectParam.getEjectPrds()
                    || ejectParam.getEjectPrds().size() <= 0
                    || ejectParam.getEjectPrds().isEmpty()) {
                throw new BusinessException("获取退订产品信息:退订产品不能为空");
            }

            for (EjectPrdsInterBO ejectPrd : ejectParam.getEjectPrds()) {
                List<ApplyProduct> applyProductList = getEjectPrdBizApplyProductInfo(
                        req, ejectPrd, loginInfo);
                if (null == applyProductList || applyProductList.size() <= 0
                        || applyProductList.isEmpty()) {
                    continue;
                }

                retApplyProductList.addAll(applyProductList);
            }
        } else if (BizConstant.BizEjectType.PKG.equals(ejectParam
                .getEjecttype())) {
            if (null == ejectParam.getEjectPkgs()
                    || ejectParam.getEjectPkgs().size() <= 0
                    || ejectParam.getEjectPkgs().isEmpty()) {
                throw new BusinessException("获取退订产品信息:退营销方案不能为空");
            }

            for (EjectPkgsInterBO ejectPkg : ejectParam.getEjectPkgs()) {
                List<ApplyProduct> applyProductList = getEjectPkgBizApplyProductInfo(
                        req, ejectPkg, loginInfo);

                if (null == applyProductList || applyProductList.size() <= 0
                        || applyProductList.isEmpty()) {
                    continue;
                }

                retApplyProductList.addAll(applyProductList);
            }

        } else {
            throw new BusinessException("获取退订产品:退订类型未["
                    + ejectParam.getEjecttype() + "]定义");
        }

        return retApplyProductList;
    }

    private List<ApplyProduct> getEjectPkgBizApplyProductInfo(
            BizPreprocessInterReq req, EjectPkgsInterBO ejectPkg,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "获取退订产品信息:请求参数不能为空");
        CheckUtils.checkNull(ejectPkg, "获取退订产品信息:退订信息不能为空");
        CheckUtils
                .checkEmpty(ejectPkg.getPkginsid(), "获取退订产品信息:退订营销方案实例id不能为空");
        /*CheckUtils
                .checkEmpty(ejectPkg.getSalespkgid(), "获取退订产品信息:退订营销方案id不能为空");*/
        // CheckUtils.checkEmpty(ejectPkg.getServids(), "获取退订产品信息:退订用户id不能为空");
        CheckUtils.checkNull(loginInfo, "获取退订产品信息:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取退订产品信息:客户订单id不能为空");

        List<ApplyProduct> retApplyProductList = new ArrayList();

        ApplyProduct bizApplyProduct = new ApplyProduct();
        bizApplyProduct.setCity(loginInfo.getCity());
        bizApplyProduct.setCount(null);// 默认写1
        bizApplyProduct.setKnowid(null);
        bizApplyProduct.setLogicdevno(null);
        bizApplyProduct.setOrderid(Long.valueOf(req.getBizorderid()));
        bizApplyProduct.setOstatus(BizConstant.BizApplyProductOstatus.CANCEL);
        bizApplyProduct.setPid(null);
        // bizApplyProduct.setRecid(recid);
        bizApplyProduct.setPkginsid(Long.valueOf(ejectPkg.getPkginsid()));
        if(StringUtils.isNotBlank(ejectPkg.getSalespkgid())){
        	bizApplyProduct.setSalespkgid(Long.valueOf(ejectPkg.getSalespkgid()));	
        }
        bizApplyProduct.setServid(null);
        bizApplyProduct.setUnit(null);// 默认写1

        retApplyProductList.add(bizApplyProduct);

        return retApplyProductList;
    }

    private List<ApplyProduct> getEjectPrdBizApplyProductInfo(
            BizPreprocessInterReq req, EjectPrdsInterBO ejectPrd,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(req, "获取退订产品信息:请求参数不能为空");
        CheckUtils.checkNull(ejectPrd, "获取退订产品信息:退订信息不能为空");
        CheckUtils.checkEmpty(ejectPrd.getServid(), "获取退订产品信息:退订用户id不能为空");
        CheckUtils.checkEmpty(ejectPrd.getPids(), "获取退订产品信息:退订产品id串不能为空");
        CheckUtils.checkNull(loginInfo, "获取退订产品信息:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取退订产品信息:客户订单id不能为空");

        List<ApplyProduct> retApplyProductList = new ArrayList();

        String pids = ejectPrd.getPids();
        String[] pidArray = pids.split(",");
        if (null == pidArray || pidArray.length <= 0) {
            throw new BusinessException("获取退订产品信息:退订产品不能为空");
        }

        for (String ejectPid : pidArray) {
            if (StringUtils.isBlank(ejectPid)) {
                continue;
            }
            ApplyProduct bizApplyProduct = new ApplyProduct();
            bizApplyProduct.setCity(loginInfo.getCity());
            if (StringUtils.isNotBlank(ejectPrd.getCycle())) {
                bizApplyProduct.setCount(Long.valueOf(ejectPrd.getCycle()));// 默认写1
            }
            bizApplyProduct.setKnowid(null);
            bizApplyProduct.setLogicdevno(ejectPrd.getKeyno());
            bizApplyProduct.setOrderid(Long.valueOf(req.getBizorderid()));
            bizApplyProduct
                    .setOstatus(BizConstant.BizApplyProductOstatus.CANCEL);
            bizApplyProduct.setPid(Long.valueOf(ejectPid));
            // bizApplyProduct.setRecid(recid);
            bizApplyProduct.setSalespkgid(null);
            bizApplyProduct.setServid(Long.valueOf(ejectPrd.getServid()));
            bizApplyProduct.setUnit("1");// 默认写1

            retApplyProductList.add(bizApplyProduct);
        }

        return retApplyProductList;
    }

    private List<ApplyProductSelect> regOrderBizApplyProductSelect4bizPreprocess(
            OrderParamsInterBO orderParam, SalespkgKnowsBO salespkgKnowsBO,
            Long precid, BizPreprocessInterReq req, LoginInfo loginInfo)
            throws Exception {

        CheckUtils.checkNull(req, "登记营销选择性产品优惠:请求参数不能为空");
        CheckUtils.checkNull(orderParam, "登记营销选择性产品优惠:订购参数对象不能为空");
        CheckUtils.checkNull(salespkgKnowsBO, "登记营销选择性产品优惠:知识库营销对象信息不能为空");
        CheckUtils.checkNull(precid, "登记营销选择性产品优惠:申请产品信息表id不能为空");
        CheckUtils.checkNull(loginInfo, "登记营销选择性产品优惠:操作员信息不能为空");

        List<ApplyProductSelect> bizApplyProductSelectList = getOrderBizApplyProductSelect4bizPreprocess(
                orderParam, salespkgKnowsBO, precid, req, loginInfo);

        if (null != bizApplyProductSelectList
                && bizApplyProductSelectList.size() >= 0
                && !bizApplyProductSelectList.isEmpty()) {
            for (ApplyProductSelect applyProductSelect : bizApplyProductSelectList) {

                getDAO().save(applyProductSelect);
            }
        }

        return bizApplyProductSelectList;
    }

    private List<ApplyProductSelect> getOrderBizApplyProductSelect4bizPreprocess(
            OrderParamsInterBO orderParam, SalespkgKnowsBO salespkgKnowsBO,
            Long precid, BizPreprocessInterReq req, LoginInfo loginInfo)
            throws Exception {

        CheckUtils.checkNull(req, "获取营销选择性产品优惠:请求参数不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "登记营销软件产品优惠:业务订单号不能为空");
        CheckUtils.checkNull(orderParam, "获取营销选择性产品优惠:订购参数对象不能为空");
        CheckUtils.checkNull(salespkgKnowsBO, "获取营销选择性产品优惠:知识库营销对象信息不能为空");
        CheckUtils.checkNull(salespkgKnowsBO.getKnowObjDet(),
                "获取营销选择性产品优惠:知识库营销对象明细信息不能为空");
        CheckUtils.checkNull(precid, "获取营销选择性产品优惠:申请产品信息表id不能为空");
        CheckUtils.checkNull(loginInfo, "获取营销选择性产品优惠:操作员信息不能为空");

        List<ApplyProductSelect> retBizApplyProductSelectList = new ArrayList();
        ApplyProductSelect bizApplyProductSelect = null;

        if (BizConstant.PrdSalespkgKnowObjtype.PRD.equals(salespkgKnowsBO
                .getObjtype())) {
            // 如果订购的是单产品，则不用记ApplyProductSelect

        } else if (BizConstant.PrdSalespkgKnowObjtype.SALESPKG
                .equals(salespkgKnowsBO.getObjtype())) {
            KnowSalespkgBO salespkg = salespkgKnowsBO.getKnowObjDet().getPkg();
            if (null == salespkg) {
                throw new BusinessException("获取营销选择性产品优惠:营销方案信息不能为空");
            }

            List<SalespkgSelectsBO> salespkgSelectList = getSalespkgSelect4bizPreprocess(
                    salespkg, orderParam.getChopcodes());
            if (null == salespkgSelectList || salespkgSelectList.size() <= 0
                    || salespkgSelectList.isEmpty()) {
                return retBizApplyProductSelectList;
            }

            for (SalespkgSelectsBO salespkgSelect : salespkgSelectList) {
                if (null == salespkgSelect.getPrds()
                        || salespkgSelect.getPrds().size() <= 0
                        || salespkgSelect.getPrds().isEmpty()) {
                    continue;
                }

                for (SalespkgSelectDetsBO selectDet : salespkgSelect.getPrds()) {
                    bizApplyProductSelect = new ApplyProductSelect();

                    bizApplyProductSelect.setCity(loginInfo.getCity());
                    bizApplyProductSelect
                            .setKnowid(salespkgKnowsBO.getKnowid());
                    bizApplyProductSelect.setOrderid(Long.valueOf(req
                            .getBizorderid()));
                    bizApplyProductSelect.setOptionflag(salespkgSelect
                            .getOptionflag());// 订单产品，不写吧
                    bizApplyProductSelect.setPid(Long.valueOf(selectDet
                            .getPid()));
                    bizApplyProductSelect.setPrecid(precid);
                    // bizApplyProductSelect.setRecid(recid);
                    bizApplyProductSelect.setSelectid(Long
                            .valueOf(salespkgSelect.getSelectid()));

                    retBizApplyProductSelectList.add(bizApplyProductSelect);
                }
            }
        }

        return retBizApplyProductSelectList;

    }

    private List<SalespkgSelectsBO> getSalespkgSelect4bizPreprocess(
            KnowSalespkgBO salespkg, List<ChopcodesBO> selpcodes)
            throws Exception {
        CheckUtils.checkNull(salespkg, "获取营销选择性产品优惠:营销方案对象信息不能为空");

        if (null == selpcodes || selpcodes.size() <= 0 || selpcodes.isEmpty()) {
            return null;
        }

        // 必须选的选择性优惠
        List<SalespkgSelectsBO> selectList = new ArrayList();
        if (null != salespkg.getRequired() && salespkg.getRequired().getSelectPrds() != null) {
            selectList.addAll(salespkg.getRequired().getSelectPrds());
        }
        // 可选的选择性优惠
        if (null != salespkg.getOptional() && salespkg.getOptional().getSelectPrds() != null) {
            selectList.addAll(salespkg.getOptional().getSelectPrds());
        }

        if (null == selectList || selectList.size() <= 0
                || selectList.isEmpty()) {
            throw new BusinessException("获取营销选择性产品优惠:营销方案中不存可选选择性优惠["
                    + selpcodes.get(0).getSelectid() + "]");
        }

        List<SalespkgSelectsBO> retSalespkgSelectsList = new ArrayList();

        List<String> selectidList = new ArrayList();
        for (SalespkgSelectsBO soft : selectList) {
            selectidList.add(soft.getSelectid());
        }

        for (ChopcodesBO chopcodes : selpcodes) {
            if (!selectidList.contains(chopcodes.getSelectid())) {
                throw new BusinessException("登记营销选择性产品优惠:营销方案中不存在可选选择性优惠方案["
                        + chopcodes.getSelectid() + "]");
            }

            for (SalespkgSelectsBO select : selectList) {

                if (!select.getSelectid().equals(chopcodes.getSelectid())) {
                    continue;
                }

                SalespkgSelectsBO softBO = getSalespkgSelectsBO4bizPreprocess(
                        select, chopcodes);

                retSalespkgSelectsList.add(softBO);
            }

        }

        return retSalespkgSelectsList;
    }

    private SalespkgSelectsBO getSalespkgSelectsBO4bizPreprocess(
            SalespkgSelectsBO select, ChopcodesBO chopcodes) throws Exception {

        CheckUtils.checkNull(select, "获取选择方案已选择的产品:选择方案对象不能为空");
        CheckUtils.checkNull(chopcodes, "获取选择方案已选择的产品:已选产品参数不能为空");

        // 先择方案id为空，或者选择方案id不相等，直接返回null
        if (StringUtils.isBlank(select.getSelectid())
                || StringUtils.isBlank(chopcodes.getSelectid())) {
            return null;
        }
        if (!select.getSelectid().equals(chopcodes.getSelectid())) {
            return null;
        }

        SalespkgSelectsBO softBO = new SalespkgSelectsBO();
        BeanUtils.copyProperties(softBO, select);
        softBO.setPrds(null);

        List<SalespkgSelectDetsBO> prdList = getSalespkgSelectedPrd4bizPreprocess(
                select, chopcodes);

        if (null == prdList || prdList.size() <= 0 || prdList.isEmpty()) {
            throw new BusinessException("登记营销选择性产品优惠:选择性方案中["
                    + chopcodes.getSelectid() + "]不存在可选选择性优惠产品["
                    + chopcodes.getPidstr() + "]");
        }

        // 检查选择数量
        checkSalespkgSelectedNum4bizPreprocess(select, prdList);

        softBO.setPrds(prdList);

        return softBO;
    }

    private void checkSalespkgSelectedNum4bizPreprocess(
            SalespkgSelectsBO select, List<SalespkgSelectDetsBO> prdList)
            throws Exception {
        if (null == select || null == prdList) {
            return;
        }

        if (StringUtils.isBlank(select.getSelectnum())) {
            int selectnum = Integer.valueOf(select.getSelectnum());
            int hasSelectnum = prdList.size();
            if (selectnum > 0) {
                if (selectnum != hasSelectnum) {
                    // 如果配置的选择数量大于0，且已选数量和配置的数量不相等，则报错
                    throw new BusinessException("检查已选择的选择性优惠:选择方案["
                            + select.getSelectid() + "]中选择数量应该为["
                            + select.getSelectnum() + "]");
                }
            }
        }

    }

    private List<SalespkgSelectDetsBO> getSalespkgSelectedPrd4bizPreprocess(
            SalespkgSelectsBO select, ChopcodesBO chopcodes) {
        if (null == select || null == select.getPrds() || null == chopcodes) {
            return null;
        }

        List<SalespkgSelectDetsBO> prdList = new ArrayList();
        for (SalespkgSelectDetsBO prd : select.getPrds()) {
            if (StringUtils.isBlank(prd.getPcode())) {
                continue;
            }
            if (("," + chopcodes.getPidstr() + ",").contains(","
                    + prd.getPcode() + ",")) {
                prdList.add(prd);
            }
        }
        return prdList;
    }

    private ApplyProduct regOrderBizApplyProduct4bizPreprocess(
            Long orderId, OrderParamsInterBO orderParam,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(orderParam, "登记订购产品信息:订购参数不能为空");

        ApplyProduct bizApplyProduct = getOrderBizApplyProductInfoFromOrderParamsInterInfo(
                orderParam, orderId, loginInfo);

        getDAO().save(bizApplyProduct);

        return bizApplyProduct;
    }

    private ApplyProduct getOrderBizApplyProductInfoFromOrderParamsInterInfo(
            OrderParamsInterBO req, Long bizorderid, LoginInfo loginInfo)
            throws Exception {
        CheckUtils.checkNull(req, "获取订购产品信息:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取订购产品信息:登录信息不能为空");
        CheckUtils.checkNull(bizorderid, "获取订购产品信息:订单id不能为空");
        CheckUtils.checkEmpty(req.getKnowid(), "获取订购产品信息:产品id不能为空");
        CheckUtils.checkEmpty(req.getServid(), "获取订购产品信息:用户id不能为空");

        ApplyProduct bizApplyProduct = new ApplyProduct();
        bizApplyProduct.setCity(loginInfo.getCity());
        bizApplyProduct.setCount(Long.valueOf(req.getCount()));// 默认写1
        bizApplyProduct.setKnowid(Long.valueOf(req.getKnowid()));
        bizApplyProduct.setLogicdevno(req.getKeyno());
        bizApplyProduct.setOrderid(bizorderid);
        bizApplyProduct.setOstatus(BizConstant.BizApplyProductOstatus.ORDER);
        bizApplyProduct.setPid(null);
        // bizApplyProduct.setRecid(recid);
        bizApplyProduct.setSalespkgid(null);
        bizApplyProduct.setServid(Long.valueOf(req.getServid()));
        bizApplyProduct.setUnit(req.getUnit());// 默认写1
        bizApplyProduct.setStime(req.getStime());

        return bizApplyProduct;
    }

    private List<ApplyProductSoft> regOrderBizApplyProductSoft4bizPreprocess(
            OrderParamsInterBO orderParam, SalespkgKnowsBO salespkgKnowsBO,
            Long precid, BizPreprocessInterReq req, LoginInfo loginInfo)
            throws Exception {

        CheckUtils.checkNull(req, "登记营销软件产品优惠:请求参数不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "登记营销软件产品优惠:业务订单号不能为空");
        CheckUtils.checkNull(orderParam, "登记营销软件产品优惠:订购参数对象不能为空");
        CheckUtils.checkNull(salespkgKnowsBO, "登记营销软件产品优惠:知识库营销对象信息不能为空");
        CheckUtils.checkNull(precid, "登记营销软件产品优惠:申请产品信息表id不能为空");
        CheckUtils.checkNull(loginInfo, "登记营销软件产品优惠:操作员信息不能为空");

        List<ApplyProductSoft> bizApplyProductSoftList = getOrderBizApplyProductSoft4bizPreprocess(
                orderParam, salespkgKnowsBO, precid, req, loginInfo);

        if (null != bizApplyProductSoftList
                && bizApplyProductSoftList.size() >= 0
                && !bizApplyProductSoftList.isEmpty()) {
            for (ApplyProductSoft applyProductSoft : bizApplyProductSoftList) {

                getDAO().save(applyProductSoft);
            }
        }

        return bizApplyProductSoftList;

    }

    private List<ApplyProductSoft> getOrderBizApplyProductSoft4bizPreprocess(
            OrderParamsInterBO orderParam, SalespkgKnowsBO salespkgKnowsBO,
            Long precid, BizPreprocessInterReq req, LoginInfo loginInfo)
            throws Exception {

        CheckUtils.checkNull(req, "获取营销软件产品优惠:请求参数不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "登记营销软件产品优惠:业务订单号不能为空");
        CheckUtils.checkNull(orderParam, "获取营销软件产品优惠:订购参数对象不能为空");
        CheckUtils.checkNull(salespkgKnowsBO, "获取营销软件产品优惠:知识库营销对象信息不能为空");
        CheckUtils.checkNull(salespkgKnowsBO.getKnowObjDet(),
                "获取营销软件产品优惠:知识库营销对象明细信息不能为空");
        CheckUtils.checkNull(precid, "获取营销软件产品优惠:申请产品信息表id不能为空");
        CheckUtils.checkNull(loginInfo, "获取营销软件产品优惠:操作员信息不能为空");

        List<ApplyProductSoft> retBizApplyProductSoftList = new ArrayList();
        ApplyProductSoft bizApplyProductSoft = null;

        if (BizConstant.PrdSalespkgKnowObjtype.PRD.equals(salespkgKnowsBO
                .getObjtype())) {
            KnowPrdBO pcode = salespkgKnowsBO.getKnowObjDet().getPrd();
            if (null == pcode || StringUtils.isBlank(pcode.getPid())) {
                throw new BusinessException("获取营销软件产品优惠:产品信息不能为空");
            }

            bizApplyProductSoft = new ApplyProductSoft();
            bizApplyProductSoft.setCity(loginInfo.getCity());
            bizApplyProductSoft.setKnowid(salespkgKnowsBO.getKnowid());
            // bizApplyProductSoft.setOptionflag();//订购单产品，不写吧
            bizApplyProductSoft.setOrderid(Long.valueOf(req.getBizorderid()));
            bizApplyProductSoft.setPid(Long.valueOf(pcode.getPid()));
            bizApplyProductSoft.setPrecid(precid);
            // bizApplyProductSoft.setRecid(recid);

            retBizApplyProductSoftList.add(bizApplyProductSoft);

        } else if (BizConstant.PrdSalespkgKnowObjtype.SALESPKG
                .equals(salespkgKnowsBO.getObjtype())) {
            KnowSalespkgBO salespkg = salespkgKnowsBO.getKnowObjDet().getPkg();
            if (null == salespkg) {
                throw new BusinessException("获取营销软件产品优惠:营销方案信息不能为空");
            }

            List<SalespkgSoftsBO> salespkgSoftList = getSalespkgSoft4bizPreprocess(
                    salespkg, orderParam.getSelpcodes());
            if (null == salespkgSoftList || salespkgSoftList.size() <= 0
                    || salespkgSoftList.isEmpty()) {
                return retBizApplyProductSoftList;
            }

            for (SalespkgSoftsBO salespkgSoft : salespkgSoftList) {
                bizApplyProductSoft = new ApplyProductSoft();
                bizApplyProductSoft.setCity(loginInfo.getCity());
                bizApplyProductSoft.setKnowid(salespkgKnowsBO.getKnowid());
                bizApplyProductSoft
                        .setOrderid(Long.valueOf(req.getBizorderid()));
                bizApplyProductSoft.setOptionflag(salespkgSoft.getOptionflag());
                bizApplyProductSoft.setPid(Long.valueOf(salespkgSoft.getPid()));
                bizApplyProductSoft.setPrecid(precid);
                // bizApplyProductSoft.setRecid(recid);

                retBizApplyProductSoftList.add(bizApplyProductSoft);
            }
        }

        return retBizApplyProductSoftList;
    }

    private List<SalespkgSoftsBO> getSalespkgSoft4bizPreprocess(
            KnowSalespkgBO salespkg, String selpcodes) throws Exception {
        CheckUtils.checkNull(salespkg, "获取营销软件产品优惠:营销方案对象信息不能为空");

        // 必须的软件优惠
        List<SalespkgSoftsBO> retSalespkgSoftList = new ArrayList();
        if (null != salespkg.getRequired() && salespkg.getRequired().getSoftPrds() != null) {
            retSalespkgSoftList.addAll(salespkg.getRequired().getSoftPrds());
        }

        if (StringUtils.isBlank(selpcodes)) {
            return retSalespkgSoftList;
        }

        // 可选的软件优惠
        // selpcodes格式为:pcode1,pcode2,pcode3
        // 实际上应该传优惠id进来才能确保唯一性的，现在先就这样吧
        String[] softOptinalPcodeArray = selpcodes.split(",");
        if (null == softOptinalPcodeArray || softOptinalPcodeArray.length <= 0) {
            return retSalespkgSoftList;
        }

        List<SalespkgSoftsBO> softList = salespkg.getOptional().getSoftPrds();
        if (null == softList || softList.size() <= 0 || softList.isEmpty()) {
            // 如果传入的已选择的可选软件产品不为空，但营销方案配置中可选软件产品为空。则报错
            throw new BusinessException("登记营销软件产品优惠:营销方案中不存可选软件产品优惠["
                    + selpcodes + "]");
        }

        Set<String> softPcodeList = new HashSet();
        for (SalespkgSoftsBO soft : softList) {
            softPcodeList.add(soft.getPcode());
        }

        for (String softOptinalPcode : softOptinalPcodeArray) {
            if (!softPcodeList.contains(softOptinalPcode)) {
                throw new BusinessException("登记营销软件产品优惠:营销方案中不存可选软件产品优惠["
                        + softOptinalPcode + "]");
            }
            for (SalespkgSoftsBO soft : softList) {
                if (soft.getPcode().equals(softOptinalPcode)) {
                    retSalespkgSoftList.add(soft);
                }
            }
        }

        return retSalespkgSoftList;
    }

    private CustOrder regBizCustorder4bizPreprocess(BizPreprocessInterReq req,
            LoginInfo loginInfo) throws Exception {
        CheckUtils.checkNull(loginInfo, "登记客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "登记客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "登记客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "登记客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "登记客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "登记客户订单:地址不能为空");

        CustOrder bizCustOrder = getBizCustOrderInfoFromBizPreprocessInterReq(
                req, loginInfo);

        getDAO().save(bizCustOrder);

        return bizCustOrder;
    }

    private CustOrder getBizCustOrderInfoFromBizPreprocessInterReq(
            BizPreprocessInterReq req, LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "获取客户订单:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "获取客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "获取客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "获取客户订单:地址不能为空");

        String bossserialno = null;
        BizGridInfo bizGrid = pubService.getGrid4Biz(
                Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()),
                loginInfo);

        String opcode = getOpcode4BizPreprocess(req);

        CustOrder bizCustOrder = new CustOrder();
        bizCustOrder.setAddr(req.getWhladdr());
        bizCustOrder.setAreaid(Long.valueOf(req.getAreaid()));
        bizCustOrder.setBossserialno(bossserialno);
        bizCustOrder.setCanceltime(null);
        bizCustOrder.setCity(loginInfo.getCity());
        bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
        bizCustOrder.setDescrip(req.getDescrip());
        bizCustOrder.setGridid(bizGrid.getId());
        bizCustOrder.setLockoper(null);
        bizCustOrder.setName(req.getName());
        bizCustOrder.setOpcode(opcode);
        bizCustOrder.setOperator(loginInfo.getOperid());
        bizCustOrder.setOprdep(loginInfo.getDeptid());
        bizCustOrder.setOptime(new Date());
        bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
//        bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
//        bizCustOrder.setId(getDAO().getSequence("SEQ_BIZ_CUSTORDER_ID"));
        bizCustOrder.setId(Long.valueOf(getBizorderid()));
        bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
        bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
        bizCustOrder.setPortalOrder(null);
    	bizCustOrder.setVerifyphone(req.getVerifyphone());

        return bizCustOrder;
    }

    private String getOpcode4BizPreprocess(BizPreprocessInterReq req)
            throws Exception {
        CheckUtils.checkNull(req, "获取鉴权操作业务代码：请求参数不能为空");
        // if(null==req.getEjectparams() && null==req.getOrderparams()){
        // throw new BusinessException("获取鉴权操作业务代码：订购参数和请求参数不能同时为空");
        // }

        String opcode = BizConstant.BizOpcode.BIZ_PRD_ORDER;
        if (null == req.getEjectparams()) {
            // 退订参数为空，产品订购的情况
            opcode = BizConstant.BizOpcode.BIZ_PRD_ORDER;
            return opcode;
        }

        if (null == req.getOrderparams()) {
            // 退订参数不为空，但订购参数为空，产品退订的情况
            opcode = BizConstant.BizOpcode.BIZ_PRD_CANCEL;
            return opcode;
        }

        // 退订参数和订购参数都不为空，产品转购的情况
        opcode = BizConstant.BizOpcode.BIZ_PRD_TRANS;

        return opcode;
    }

    // --================订购鉴权接口 end ========================//
    
    /**
	 * 
		2.6.1.7.营销用户关注操作（仅网格后台接口）
	 * @param
	 * @param
	 * @throws Exception
	 */
    public ReturnInfo bizAttprdProc(BizAttprdProcReq req) throws Exception {        
    	ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
         
//        LoginInfo loginInfo = new LoginInfo();
//        loginInfo.setOperid(391213L);
//        loginInfo.setLoginname("WGYX_NG");
//        loginInfo.setCity("GZ");
//        loginInfo.setDeptid(3004151L);
        CheckUtils.checkNull(req, "请求信息不能为空");  
        CustAttrecord attrecord = new CustAttrecord();
        attrecord.setServid((Long)BeanUtil.strToObject(req.getServid(), Long.class));
        attrecord.setObjtype(req.getObjtype());
        attrecord.setObjid((Long)BeanUtil.strToObject(req.getObjid(), Long.class));
        attrecord.setEdate(DateUtils.parseDate(req.getEdate())); 
        
        List atts =  getDAO().find(attrecord);
        if(atts!=null && atts.size()>0 ){
        	attrecord = (CustAttrecord)atts.get(0);
        	if(SysConstant.SysYesNo.YES.equals(attrecord.getIsatten())){
        		returnInfo.setMessage(SysConstant.SysYesNo.YES);
        	}else{
        		returnInfo.setMessage(SysConstant.SysYesNo.YES);
        	}
        	if(req.getOpt().equals("0")){
        		attrecord.setIsatten(SysConstant.SysYesNo.YES);
        		attrecord.setLastdate(new Date());
        	}
        	if(req.getOpt().equals("1")){
        		attrecord.setIsatten(SysConstant.SysYesNo.NO);
        	}
        }
        else{
        	if(req.getOpt().equals("0")){
                attrecord.setServid((Long)BeanUtil.strToObject(req.getServid(), Long.class));
                attrecord.setObjtype(req.getObjtype());
                attrecord.setObjid((Long)BeanUtil.strToObject(req.getObjid(), Long.class));
                attrecord.setEdate(DateUtils.parseDate(req.getEdate())); 
                attrecord.setAttkind(req.getAttkind());
                attrecord.setCustid((Long)BeanUtil.strToObject(req.getCustid(), Long.class));
                attrecord.setLogicdevno(req.getLogicdevno());
                attrecord.setLastdate(new Date());
                attrecord.setOperid(loginInfo.getOperid());
                attrecord.setDeptid(loginInfo.getDeptid());
                attrecord.setCity(loginInfo.getCity());
                attrecord.setIsatten(SysConstant.SysYesNo.YES);
        	}
        	if(req.getOpt().equals("1")){
        		returnInfo.setMessage("未查询到相应记录！");
        	}
    		
        }
        getDAO().save(attrecord);
        return  returnInfo;
    }     
}
