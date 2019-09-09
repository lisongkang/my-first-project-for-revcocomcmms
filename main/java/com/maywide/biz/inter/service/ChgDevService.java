package com.maywide.biz.inter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
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
import com.maywide.biz.inter.pojo.chgdev.ChgDevTmpReq;
import com.maywide.biz.inter.pojo.chgdev.ChgDevWGReq;
import com.maywide.biz.inter.pojo.chgdev.ChgDevWGResp;
import com.maywide.biz.inter.pojo.chgdev.ChkDevReq;
import com.maywide.biz.inter.pojo.chgdev.CustDevInfoReq;
import com.maywide.biz.inter.pojo.chgdev.CustDevInfoResp;
import com.maywide.biz.inter.pojo.chgdev.DevInfoBean;
import com.maywide.biz.inter.pojo.chgdev.QueCustDeviceReq;
import com.maywide.biz.inter.pojo.chgdev.QueFittingInfoReq;
import com.maywide.biz.inter.pojo.queChgRule.OneTimeFeeBO;
import com.maywide.biz.inter.pojo.queChgRule.QueChgRuleResp;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.pay.unify.manager.ParamsManager;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;


@Service
@Transactional(rollbackFor = Exception.class)
public class ChgDevService extends CommonService {
    private static Long orderCode = null;
    @Autowired
    private InterPrdService interPrdService;
    @Autowired
    private PubService pubService;
    @Autowired
    private BizParamCfgService bizParamCfgService;
    @Autowired
    private RuleService ruleservice;
	@Autowired
	private ParamService paramService;

    /**
     * 查询客户设备接口
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public ReturnInfo queCustDevInfo(CustDevInfoReq req,
    		CustDevInfoResp resp) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo(); 
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        
        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkNull(req.getCustid(), "匹配条件[CUSTID]不能为空");

        CustDevInfoReq sysdev = new CustDevInfoReq();
        sysdev.setCustid(req.getCustid());
        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
                BizConstant.BossInterfaceService.QUE_CUSTDEVINFO, sysdev,
                loginInfo);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodeTree = objectMapper.readTree(bossRespOutput); 
        returnInfo.setMessage(handlerCityDevice(loginInfo,nodeTree,req.getBizcode()));
        return returnInfo;
    }
    
    private String handlerCityDevice(LoginInfo loginInfo,JsonNode nodeTree,String bizcode) throws Exception{
    	Rule rule = null;
    	if (BizConstant.BizOpcode.BIZ_DEV_CHANGE.equals(bizcode)) {
    		rule = ruleservice.getRule(loginInfo.getCity(), "CHG_DEV_TYPE");
		}
    	if(rule == null || StringUtils.isBlank(rule.getValue()) || StringUtils.isBlank(nodeTree.toString())){
    		 return nodeTree.toString();
    	}
    	Gson gson = new Gson();
    	List<DevInfoBean> devList = gson.fromJson(nodeTree.toString(),
                    new TypeToken<List<DevInfoBean>>() {
                    }.getType());
    	List<DevInfoBean> unSelectList = new ArrayList<DevInfoBean>();
    	for(DevInfoBean bean:devList){
    		if(rule.getValue().contains(bean.getKind())){
    			unSelectList.add(bean);
    		}
    	}
    	devList.removeAll(unSelectList);
    	return gson.toJson(devList);
    } 
    
    /**
     * 查询当前地市设备更换规则
     * @return
     * @throws Exception 
     */
    public ReturnInfo queChgDevRule(QueChgRuleResp resp) throws Exception{
    	ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
        
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        
        Rule rule = ruleservice.getRule("*", "BIZ_DEVCHG_NEW_SOURCE");
        
        if(rule != null){
        	String values = rule.getValue();
        	if(values.contains(loginInfo.getCity())){
        		resp.setSourceAble(false);
        	}else{
        		resp.setSourceAble(true);
        	}
        }
        
        Rule typeRule = ruleservice.getRule(loginInfo.getCity(), "BIZ_DEVCHG_REASON");
        if(typeRule != null){
        	resp.setChgTypeAble(false);
        	resp.setTypeCode(typeRule.getValue());
        }
        
        initOneTimeFeesRule(resp, loginInfo);
		initDefValueRule(resp, loginInfo);
		
		return returnInfo;
    }

	private void initDefValueRule(QueChgRuleResp resp, LoginInfo loginInfo)
			throws Exception, NoSuchFieldException {
		PrvSysparam defValueParam = paramService.getData("BIZ_DEV_CHG_PARAM_DEFVALUE", loginInfo.getCity());
		if (defValueParam != null && StringUtils.isNotBlank(defValueParam.getData()) ) {
			String[] contents = defValueParam.getData().split("~");
			for (String content : contents) {
				String[] params = content.split(":");
				if (params.length == 2) {
					try {
						BeanUtils.setFieldValue(resp, params[0], params[1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void initOneTimeFeesRule(QueChgRuleResp resp, LoginInfo loginInfo) throws Exception {
		List<OneTimeFeeBO> list = new ArrayList<OneTimeFeeBO>();
		PrvSysparam oneTimeFeesParam = paramService.getData("BIZ_DEV_CHG_ONETIME_FEES", loginInfo.getCity());
		if (oneTimeFeesParam != null && StringUtils.isNotBlank(oneTimeFeesParam.getData()) ) {
			String[] contents = oneTimeFeesParam.getData().split(";");
			for (String content : contents) {
				String[] params = content.split("~");
				if (params.length == 3) {
					OneTimeFeeBO fee = new OneTimeFeeBO();
					fee.setpCode(params[0]);
					fee.setpName(params[1]);
					fee.setPrices(Arrays.asList(params[2].split(",")));
					list.add(fee);
				}
			}
		}
		resp.setOneTimeFees(list);
	}



    /**
     * 查询配件信息
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */   
    public ReturnInfo getCustFittingInfo(QueFittingInfoReq req) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkEmpty(req.getKind(),
                "匹配条件[kind]设备类型不能为空"); 
 

        /*String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
                BizConstant.BossInterfaceService.QUE_FITTING_INFO, req,
                loginInfo);*/
        String bossRespOutput =getBossHttpInfo(req.getBizorderid(),
                BizConstant.BossInterfaceService.QUE_FITTING_INFO, req,
                loginInfo);
        
        ObjectMapper objectMapper = new ObjectMapper(); 
        JsonNode nodeTree = objectMapper.readTree(bossRespOutput); 
        System.out.println("！！！！！！"+nodeTree.toString());
        returnInfo.setMessage(nodeTree.get("output").toString());
        
        return returnInfo;
    }

    /**
     * 设备销售校验
     * 
     * @param req
     * @return
     * @throws Exception
     */
    public ReturnInfo checkNewStbDev(ChkDevReq req) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
        
        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkNull(req.getCustid 			(), "智能卡号[Custid 		 ]不能为空");
        CheckUtils.checkEmpty(req.getOldkind(), "智能卡号[Oldkind       ]不能为空");
        CheckUtils.checkEmpty(req.getNewdevno(), "智能卡号[newdevno      ]不能为空");
        CheckUtils.checkEmpty(req.getReason(), "智能卡号[Reason     ]不能为空");
        CheckUtils.checkEmpty(req.getOlddevno(), "智能卡号[olddevno    ]不能为空");
        CheckUtils.checkEmpty(req.getNewuseprop(), "智能卡号[Newuseprop ]不能为空");
        CheckUtils.checkEmpty(req.getRectype(), "智能卡号[Rectype       ]不能为空");
        CheckUtils.checkEmpty(req.getServiceFlag(), "智能卡号[ServiceFlag]不能为空");


        String serverCode = "";
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			serverCode = BizConstant.ServerCityBossInterface.CHK_NEWDEV;
		}else{
			serverCode = BizConstant.BossInterfaceService.CHK_NEWDEV;
		}
        
        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
        		serverCode, req,
                loginInfo);
        
        ObjectMapper objectMapper = new ObjectMapper(); 
        JsonNode nodeTree = objectMapper.readTree(bossRespOutput); 
        returnInfo.setMessage(nodeTree.toString());

        return returnInfo;
    }

    /**
     * 设备更换鉴权
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo saveTmp(ChgDevTmpReq req) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkNull(req.getBo().getType(), "请求信息不能为空是否回收设备（TYPE）");

        
        String serverCode = "";
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			serverCode = BizConstant.ServerCityBossInterface.BIZ_SAVEDEVTMP;
		}else{
			serverCode = BizConstant.BossInterfaceService.BIZ_SAVEDEVTMP;
		}
        
        // 将请求做一下转换，并赋默认值
        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
        		serverCode, req,
                loginInfo);

        ObjectMapper objectMapper = new ObjectMapper(); 
        JsonNode nodeTree = objectMapper.readTree(bossRespOutput); 
     
        // 写业务表
        CustOrder custOrder = regBizCustorder4bizPreprocess(req, loginInfo);
//        procBizApplyProduct4bizPreprocess(req, loginInfo);        
        // 保存boss订单信息
        this.savePortalOrder(req.getBizorderid(), bossRespOutput);
        
        returnInfo.setMessage(req.getBizorderid());

        return returnInfo;
    }
    private CustOrder regBizCustorder4bizPreprocess(ChgDevTmpReq req,
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
    		ChgDevTmpReq req, LoginInfo loginInfo) throws Exception {

        CheckUtils.checkNull(req, "获取客户订单:请求参数不能为空");
        CheckUtils.checkNull(loginInfo, "获取客户订单:登录信息不能为空");
        CheckUtils.checkEmpty(req.getBizorderid(), "获取客户订单:订单id不能为空");
        CheckUtils.checkEmpty(req.getHouseid(), "获取客户订单:地址id不能为空");
        CheckUtils.checkEmpty(req.getPatchid(), "获取客户订单:片区id不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "获取客户订单:客户id不能为空");
        CheckUtils.checkEmpty(req.getName(), "获取客户订单:客户姓名不能为空");
        CheckUtils.checkEmpty(req.getWhladdr(), "获取客户订单:地址不能为空");

        String bossserialno = null;
//        BizGridInfo bizGrid = pubService.getGrid4Biz(
//                Long.valueOf(req.getHouseid()), Long.valueOf(req.getPatchid()),
//                loginInfo);
//
//        String opcode = getOpcode4BizPreprocess(req);
        String opcode = "BIZ_DEV_CHANGE";

        CustOrder bizCustOrder = new CustOrder();
        bizCustOrder.setAddr(req.getWhladdr());
        bizCustOrder.setAreaid(Long.valueOf(req.getAreaid()));
        bizCustOrder.setBossserialno(bossserialno);
        bizCustOrder.setCanceltime(null);
        bizCustOrder.setCity(loginInfo.getCity());
        bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
        bizCustOrder.setDescrip(req.getDescrip());
//        bizCustOrder.setGridid(bizGrid.getId());
        bizCustOrder.setLockoper(null);
        bizCustOrder.setName(req.getName());
        bizCustOrder.setOpcode(opcode);
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
    /**
     * 设备更换确认接口
     * 
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    public ReturnInfo save(ChgDevWGReq req) throws Exception {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
        returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

        CheckUtils.checkNull(req, "请求信息不能为空");
        CheckUtils.checkEmpty(req.getCustid(), "匹配条件[custid]不能为空");
        CheckUtils.checkEmpty(req.getCustorderid(), "查询订单信息：订单号[custorderid]不能为空");

        CustOrder custOrder = (CustOrder) getDAO().find(CustOrder.class, Long.valueOf(req.getCustorderid()));
        CheckUtils.checkNull(custOrder, "获取订单信息：根据订单id["+ req.getCustorderid()+"]查询不到订单信息");
        BizPortalOrder order = (BizPortalOrder) getDAO().find(
                BizPortalOrder.class, Long.valueOf(req.getCustorderid()));
        if (null == order) {
            throw new BusinessException("获取订单信息：根据订单id[" + req.getCustorderid()
                    + "]查询不到订单信息");
        }
        if(order.getStatus().equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_UNIFY) ||
				order.getStatus().equals(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_ELECTION)){
			req.setPaycode(order.getPaycode());
			req.setPayway(order.getPayway());
			if (StringUtils.isNotBlank(order.getPayreqid())) {
				req.setPayreqid(order.getPayreqid());
			}
		}
        req.setOrderid(String.valueOf(order.getResporderid()));
        ParamsManager.isCorrectData(req.getPayway(),req.getPaycode());
        String serverCode = "";
		if(StringUtils.isNotBlank(SysConfig.getServiceCity())){
			serverCode = BizConstant.ServerCityBossInterface.BIZ_CHGDEV;
			if(req.getInstalldate() == null){
				String format = "yyyy-MM-dd'T'HH:mm:ss";
				req.setInstalldate(DateUtils.formatDate(new Date(), format));
			}
		}else{
			serverCode = BizConstant.BossInterfaceService.BIZ_CHGDEV;
		}
		String multipay = "N";
        if(StringUtils.isNotBlank(req.getMultipaywayflag()) && req.getMultipaywayflag().equalsIgnoreCase("Y")) {
        	if(StringUtils.isNotBlank(req.getCashe())) {
        		double payFees = Double.parseDouble(order.getFees()) - Double.parseDouble(req.getCashe());
        		if(payFees > 0.0) {
        			order.setMultipay("Y");
        			multipay = "Y";
        		}
        		order.setPayFees(Double.toString(payFees));
        	}
        }
        ChgDevWGReq bossReq = getReq2Boss(req,multipay);
        String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
        		serverCode, bossReq,
                loginInfo);


        order.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PAYED);
		order.setPayway(req.getPayway());
        order.setPaycode(req.getPaycode());
        if (StringUtils.isNotEmpty(req.getPayreqid())) {
            order.setPayreqid(req.getPayreqid());
        }
        //getWgpaywayChangegetPayway
        if (StringUtils.isNotBlank(req.getPayway())) {
        	order.setWgpayway(req.getPayway());
		}
        order.setPaytime(new Date());
        getDAO().saveOrUpdate(order);
        
        ObjectMapper objectMapper = new ObjectMapper(); 
        JsonNode nodeTree = objectMapper.readTree(bossRespOutput); 
        ChgDevWGResp bossResp = transactionBean(bossRespOutput);
        custOrder.setBossserialno(bossResp.getSerialno());
        getDAO().save(custOrder);
        returnInfo.setMessage(nodeTree.toString());
        ReturnVisitTaskService.addTask(custOrder.getId());

        return returnInfo;
    }
    
    
    private ChgDevWGReq getReq2Boss(ChgDevWGReq req,String multipay) {
    	ChgDevWGReq req2Boss = new ChgDevWGReq();
    	req2Boss.setCustid(req.getCustid());
    	req2Boss.setInstalldate(req.getInstalldate());
    	req2Boss.setInstalltype(req.getInstalltype());
    	req2Boss.setKeyno(req.getKeyno());
    	req2Boss.setMultipaywayflag(multipay);
    	req2Boss.setOrderid(req.getOrderid());
    	req2Boss.setBankaccno(req.getBankaccno());
    	req2Boss.setPaycode(req.getPaycode());
    	req2Boss.setPayreqid(req.getPayreqid());
    	req2Boss.setPayway(req.getPayway());
    	req2Boss.setRequestBody(req.getRequestBody());
    	return req2Boss;
    }
    
    private ChgDevWGResp transactionBean(String outputStr) throws Exception {
    	return (ChgDevWGResp) BeanUtil.jsonToObject(outputStr, ChgDevWGResp.class);
    }
    
    /**
     * 查询当前客户设备属性
     * @param req
     * @return
     * @throws Exception
     */
    public ReturnInfo queCustDevicePropertites(QueCustDeviceReq req,DevInfoBean resp) throws Exception{
    	
    	 ReturnInfo returnInfo = new ReturnInfo();
         returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
         returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

         LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
         CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
         CheckUtils.checkNull(req.getCustid(), "定位客户不能为空");
         if(StringUtils.isNotBlank(req.getCmmac()) || StringUtils.isNotBlank(req.getSmno()) || StringUtils.isNotBlank(req.getStbno())){
	         QueCustDeviceReq req2Boss = new QueCustDeviceReq();
	         req2Boss.setCustid(req.getCustid());
	         
	         String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
	                 BizConstant.BossInterfaceService.QUE_CUSTDEVINFO, req2Boss,
	                 loginInfo);
	         
	         ObjectMapper objectMapper = new ObjectMapper();
	         JsonNode nodeTree = objectMapper.readTree(bossRespOutput); 
	         handlerCustDevice(req,nodeTree.toString(),resp);
         }
         return returnInfo;
    }
    
    private void handlerCustDevice(QueCustDeviceReq req,String jsonStr,DevInfoBean resp) throws Exception{
    	if(StringUtils.isNotBlank(jsonStr)){
    		Gson gson = new Gson();
        	List<DevInfoBean> devList = gson.fromJson(jsonStr,
                        new TypeToken<List<DevInfoBean>>() {
                        }.getType());
        	for(int i = 0; i< devList.size() ;i++){
        		DevInfoBean bean = devList.get(i);
        		if(req.getCmmac().equals(bean.getDevno()) || req.getStbno().equals(bean.getDevno()) || req.getSmno().equals(bean.getDevno())){
        			BeanUtils.copyProperties(resp, bean);
        			break;
        		}
        	}
    	}
    }


}
