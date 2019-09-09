package com.maywide.biz.inter.service;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.PORTAL_ORDER_STATUS;
import com.maywide.biz.cons.BizConstant.SysPayway;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.bizbusback.BizBusRoll2BOSSReq;
import com.maywide.biz.inter.pojo.bizbusback.BizBusRollReq;
import com.maywide.biz.inter.pojo.bizbusback.BossRollBackResp;
import com.maywide.biz.inter.pojo.chargeFeeBook.PayBackoffReq;
import com.maywide.biz.inter.pojo.chargeFeeBook.PayBackoffResp;
import com.maywide.biz.inter.pojo.queCityChgFees.QueCityChgFeesResp;
import com.maywide.biz.inter.pojo.queMbChange.QueMobileChangeReq;
import com.maywide.biz.inter.pojo.queMbChange.QueMobileChangeResp;
import com.maywide.biz.inter.pojo.refresBill.RefreshBill2BossReq;
import com.maywide.biz.inter.pojo.refresBill.RefreshBillReq;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
public class CustReuleService extends CommonService {

	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private ParamService paramService;
	
	@Autowired
	private PubService pubService;

	public ReturnInfo queCitySMSAblity(QueMobileChangeReq req, QueMobileChangeResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();

		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		Rule rule = ruleService.getRule(loginInfo.getCity(), "SMS_PHONE_CHANGE");

		if (rule != null) {
			if (rule.getValue().equals("*")) {
				resp.setChangeAble(true);
			} else if (rule.getValue().contains(req.getBizCode())) {
				resp.setChangeAble(true);
			}
			resp.setBizCode(rule.getValue());
		}
		return returnInfo;
	}

	public ReturnInfo quePageSMSAble(QueMobileChangeReq req, QueMobileChangeResp resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();

		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		// 未配置默认可以修改
		resp.setChangeAble(true);
		Rule rule = ruleService.getRule(loginInfo.getCity(), "PAGE_SMS_PHONE_CHANGE");

		if (rule != null) {
			boolean isMatch = rule.getValue().equals("*") || rule.getValue().contains(req.getBizCode());
			if (isMatch) {
				resp.setChangeAble("Y".equals(rule.getPerMission()));
			}
			resp.setBizCode(rule.getValue());
		}
		return returnInfo;
	}
	
	public ReturnInfo refreshCustBill(RefreshBillReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();

		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		if (StringUtils.isBlank(req.getCustid()) && StringUtils.isBlank(req.getLogicdevno())) {
			CheckUtils.checkNull(null, "缺少必要字段");
		}

		RefreshBill2BossReq req2Boss = makeReq2Boss(req);
		try {
			String bossResult = getBossHttpInfOutput(req.getBizorderid(),
					BizConstant.BossInterfaceService.GEN_REALTIMEARREAR, req2Boss, loginInfo);
		} catch (Exception e) {
			CheckUtils.checkNull(null, e.getMessage());
		}
		return returnInfo;
	}

	private RefreshBill2BossReq makeReq2Boss(RefreshBillReq req) {
		RefreshBill2BossReq req2Boss = new RefreshBill2BossReq();
		req2Boss.setCustid(req.getCustid());
		req2Boss.setLogicdevno(req.getLogicdevno());
		req2Boss.setPermark(req.getPermark());
		return req2Boss;
	}

	/**
	 * 业务回退功能
	 * 
	 * @return
	 * @throws Exception 
	 */
	public ReturnInfo businessRollBack(BizBusRollReq req) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();

		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		CheckUtils.checkEmpty(req.getOrderid(), "订单信息不能为空");
		
		CustOrder custOrder = (CustOrder) getDAO().find(CustOrder.class, Long.parseLong(req.getOrderid()));
		CheckUtils.checkNull(custOrder, "无法根据当前订单id查找到对应的订单信息");
		
		BizPortalOrder bizPortalOrder = custOrder.getPortalOrder();
		CheckUtils.checkNull(bizPortalOrder, "无法根据当前订单id查找到对应的订单信息");
		Rule rollRule = ruleService.getRuleByName("UNABLE_ROLLBACK_PAYWAY");
		if(rollRule != null && StringUtils.isNotBlank(rollRule.getValue())&&StringUtils.isNoneBlank(bizPortalOrder.getPayway())) {
			if(rollRule.getValue().contains(bizPortalOrder.getPayway())) {
				CheckUtils.checkNull(null, "当前支付方式不执行办理业务回退,请到BOSS前台进行操作!");
			}
		}
		if(bizPortalOrder.getStatus().equals(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY)
				||bizPortalOrder.getStatus().equals(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_OVERDUE)
				||bizPortalOrder.getStatus().equals(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_LOGICDEL)
				||bizPortalOrder.getStatus().equals(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_BOSS_ROLLBACK)){
			CheckUtils.checkNull(null, "当前订单状态不支持业务回退");
		}
		PrvSysparam bizCodeParam = findBizCodeParam(req.getOpcode());
		if (bizCodeParam == null) {
			throw new BusinessException("无法找到对应的业务操作码【" + req.getOpcode() + "】");
		}
		
		BizBusRoll2BOSSReq req2Boss = getReq2Boss(req, Long.toString(bizPortalOrder.getResporderid()));
		
		String outputResult = getBossHttpInfOutput(req.getBizorderid(), BizConstant.BossInterfaceService.DO_ROLLBACK, req2Boss, loginInfo);
		if(bizPortalOrder.getPayway().equalsIgnoreCase(SysPayway.SYS_PAYWAY_UNIFY) || bizPortalOrder.getPayway().equalsIgnoreCase(SysPayway.SYS_PAYWAY_UNIFY2)) {
			PayBackoffReq payBackReq = new PayBackoffReq();
			payBackReq.setOrderNo(bizPortalOrder.getId().toString());
			String responseStr = getPayPlatFormInfOutput(BizConstant.Platform_Service.REFUND, payBackReq);
			PayBackoffResp resp = (PayBackoffResp) BeanUtil.jsonToObject(responseStr, PayBackoffResp.class);
			if (!"0".equals(resp.getStatus())) {
					custOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.ROLLBACK);
					bizPortalOrder.setStatus(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_PLATM_FAIT);
					getDAO().update(custOrder);
					getDAO().update(bizPortalOrder);
					throw new BusinessException("订单【"+custOrder.getOrdercode()+"】BOSS业务回退成功,统一支付平台回退失败。");	
			}
		}
		bizPortalOrder.setStatus(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_BOSS_ROLLBACK);
		getDAO().saveOrUpdate(bizPortalOrder);
		hanlderBusiness(outputResult,req,loginInfo,custOrder,bizCodeParam);
		return returnInfo;
	}

	private PrvSysparam findBizCodeParam(String opcode) throws Exception {
		PrvSysparam sysparam = new PrvSysparam();
		sysparam.setGcode(BizConstant.SysparamGcode.BIZ_OPCODE);
		sysparam.setMcode(opcode);
		List<PrvSysparam> paramList = getDAO().find(sysparam);
		if (paramList == null || paramList.isEmpty()) {
			return null;
		}
		return paramList.get(0);
	}
	
	private BizBusRoll2BOSSReq getReq2Boss(BizBusRollReq req,String orderid){
		BizBusRoll2BOSSReq req2Boss = new BizBusRoll2BOSSReq();
		if(StringUtils.isNotBlank(req.getFlag())){
			req2Boss.setFlag(req.getFlag());	
		}else{
			req2Boss.setFlag("1");
		}
		req2Boss.setOpcode(req.getOpcode());
		req2Boss.setMemo(req.getMemo());
		req2Boss.setOrderid(orderid);
		return req2Boss;
	}

	private void hanlderBusiness(String outputResult, BizBusRollReq req, LoginInfo loginInfo, CustOrder oldOrder, PrvSysparam bizCodeParam)
			throws Exception {
		BossRollBackResp bossResp = (BossRollBackResp) BeanUtil.jsonToObject(outputResult, BossRollBackResp.class);
		String opcode = "R_" + req.getOpcode();
		PrvSysparam rollbackParam = findBizCodeParam(opcode);
		if (rollbackParam == null) {
			PrvSysparam param = new PrvSysparam();
			param.setGcode(BizConstant.SysparamGcode.BIZ_OPCODE);
			param.setMcode(opcode);
			param.setMname("回退" + bizCodeParam.getMname());
			getDAO().save(param);
		}
		saveCustOrder4Rollback(bossResp, req, loginInfo, oldOrder, opcode);
	}

	private CustOrder saveCustOrder4Rollback(BossRollBackResp bossResp, BizBusRollReq req, LoginInfo loginInfo, CustOrder oldOrder, String opcode)
			throws Exception {
		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(oldOrder.getAddr());
		bizCustOrder.setAreaid(loginInfo.getAreaid());
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setBossserialno(bossResp.getSerialno());
		bizCustOrder.setCanceltime(null);
		bizCustOrder.setCustid(oldOrder.getCustid());
		bizCustOrder.setDescrip(null);
		bizCustOrder.setLockoper(null);
		bizCustOrder.setName(oldOrder.getName());
		bizCustOrder.setOpcode(opcode);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode().toString());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.SYNC);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);
		bizCustOrder.setVerifyphone(null);
		oldOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.ROLLBACK);
		getDAO().save(bizCustOrder);
		getDAO().update(oldOrder);
		return bizCustOrder;
	}

	/**
	 * 查询地市更换设备参数
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queCityChgFees(QueCityChgFeesResp resp) throws Exception{
		
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);
		
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);
		
		Rule rule = ruleService.getRule(loginInfo.getCity(), "CITY_CHG_FEES_RULE");
		if(rule != null){
			try{
				String[] fees = rule.getValue().split(",");
				String name = paramService.getMname(rule.getPerMission(), loginInfo.getCity());
				resp.setFeesName(name);
				resp.setFeeList(Arrays.asList(fees));
			}catch(Exception e){
				CheckUtils.checkNull(null, "获取费用名称失败，请配置正确的规则、参数数据");
			}
		}
		
		Rule resourceRule = ruleService.getRule(loginInfo.getCity(), "CITY_DEFAULT_RESOURCE");
		if(resourceRule != null){
			String resource = resourceRule.getValue();
			resp.setResource(resource);
		}
		
		return returnInfo;
	}	
}
