package com.maywide.biz.inter.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.PORTAL_ORDER_ORDERTYPE;
import com.maywide.biz.cons.BizConstant.PORTAL_ORDER_STATUS;
import com.maywide.biz.core.entity.Rule;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.core.service.RuleService;
import com.maywide.biz.core.servlet.IErrorDefConstant;
import com.maywide.biz.inter.constant.QueConstant.CommonNotice;
import com.maywide.biz.inter.pojo.otherfee.BizOtherFeeInBossReq;
import com.maywide.biz.inter.pojo.otherfee.BizOtherFeeInBossResp;
import com.maywide.biz.inter.pojo.otherfee.BizOtherFeeInReq;
import com.maywide.biz.inter.pojo.otherfee.BizOtherFeeInResp;
import com.maywide.biz.inter.pojo.otherfee.OtherFeeBean;
import com.maywide.biz.inter.pojo.otherfee.OtherFeeParam;
import com.maywide.biz.inter.pojo.otherfee.QueOtherFeeReq;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.core.cons.Constant;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.CheckUtils;

@Service
public class OtherFeeService extends CommonService {

	@Autowired
	private PubService pubService;

	@Autowired
	private RuleService ruleservice;

	/**
	 * 杂项收费查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo queOtherFee(QueOtherFeeReq req,
			ArrayList<OtherFeeBean> resp) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		String result = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.QUE_OTHER_FEE, req, loginInfo);

		List<OtherFeeBean> datas = new Gson().fromJson(result,
				new TypeToken<List<OtherFeeBean>>() {
				}.getType());

		if (datas != null && !datas.isEmpty()) {
			Rule rule = ruleservice.getRule(loginInfo.getCity(),
					"QUE_OTHER_FEE_CHOOSE");
			if (rule != null && rule.getPerMission().equals("Y")) {
				String[] valueArray = rule.getValue().split(",");
				List<String> valueList = Arrays.asList(valueArray);
				for (OtherFeeBean otherFeeBean : datas) {
					if (valueList.contains(otherFeeBean.getId())) {
						resp.add(otherFeeBean);
					}
				}
			} else {
				resp.addAll(datas);
			}
		}

		return returnInfo;
	}

	/**
	 * 杂项收费
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ReturnInfo bizOtherFeeIn(BizOtherFeeInReq req, BizOtherFeeInResp resp)
			throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setCode(IErrorDefConstant.ERROR_SUCCESS_CODE);
		returnInfo.setMessage(IErrorDefConstant.ERROR_SUCCESS_MSG);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		CheckUtils.checkNull(loginInfo, CommonNotice.LOGIN_OUT_NOTICE);

		CheckUtils.checkNull(req, "请求信息不能为空");
		CheckUtils.checkEmpty(req.getCustid(), "客户编码不能为空");
		CheckUtils.checkEmpty(req.getCustname(), "客户名称不能为空");
		CheckUtils.checkEmpty(req.getAddr(), "客户地址不能为空");
		if (StringUtils.isBlank(req.getServid())
				&& StringUtils.isBlank(req.getHouseid())) {
			throw new BusinessException("用户编号和地址编号不能同时为空");
		}
		CheckUtils.checkNull(req.getOtherFeeList(), "收费对象不能为空");

		BigDecimal total = BigDecimal.ZERO;
		try {
			for (OtherFeeParam param : req.getOtherFeeList()) {
				total = total.add(new BigDecimal(param.getFee()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("收费金额有误");
		}

		BizOtherFeeInBossReq bossReq = getBossReq4OtherFeeIn(req, loginInfo);
		String bossRespOutput = getBossHttpInfOutput(req.getBizorderid(),
				BizConstant.BossInterfaceService.BIZ_OTHER_FEE_IN, bossReq,
				loginInfo);
		BizOtherFeeInBossResp bossResp = (BizOtherFeeInBossResp) BeanUtil
				.jsonToObject(bossRespOutput, BizOtherFeeInBossResp.class);

		// 写业务表
		CustOrder custOrder = regBizCustorder4OtherFeeIn(req, loginInfo);
		registerPortalOrder4OtherFeeIn(custOrder.getId(), total.toString(),
				bossResp);
		resp.setCustorderid(String.valueOf(custOrder.getId()));

		return returnInfo;
	}

	private BizOtherFeeInBossReq getBossReq4OtherFeeIn(BizOtherFeeInReq req,
			LoginInfo loginInfo) {
		BizOtherFeeInBossReq bossReq = new BizOtherFeeInBossReq();
		bossReq.setCustid(req.getCustid());
		bossReq.setServid(req.getServid());
		bossReq.setHouseid(req.getHouseid());
		bossReq.setOtherFeeList(req.getOtherFeeList());
		return bossReq;
	}

	private CustOrder regBizCustorder4OtherFeeIn(BizOtherFeeInReq req,
			LoginInfo loginInfo) throws Exception {

		CustOrder bizCustOrder = new CustOrder();
		bizCustOrder.setAddr(req.getAddr());
		bizCustOrder.setAreaid(loginInfo.getAreaid());
		bizCustOrder.setCity(loginInfo.getCity());
		bizCustOrder.setCustid(Long.valueOf(req.getCustid()));
		bizCustOrder.setName(req.getCustname());
		bizCustOrder.setOpcode(BizConstant.BizOpcode.BIZ_OTHERFEE);
		bizCustOrder.setOperator(loginInfo.getOperid());
		bizCustOrder.setOprdep(loginInfo.getDeptid());
		bizCustOrder.setOptime(new Date());
		bizCustOrder.setOrdercode(pubService.getOrderCode());
		bizCustOrder.setId(Long.valueOf(req.getBizorderid()));
		bizCustOrder.setOrderstatus(BizConstant.BizCustorderOrderstatus.INIT);
		bizCustOrder.setSyncmode(BizConstant.BizCustorderSyncmode.SYNC);
		bizCustOrder.setPortalOrder(null);

		getDAO().save(bizCustOrder);

		return bizCustOrder;
	}

	private BizPortalOrder registerPortalOrder4OtherFeeIn(Long orderid,
			String fees, BizOtherFeeInBossResp bossResp) throws Exception {
		BizPortalOrder portalOrder = new BizPortalOrder();
		portalOrder.setCreatetime(new Date());
		portalOrder.setOrdertype(PORTAL_ORDER_ORDERTYPE.PORTAL_ORDER_TYPE_PRD);
		portalOrder.setId(orderid);
		portalOrder.setStatus(PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_NOPAY);
		portalOrder.setResporderid(Long.parseLong(bossResp.getOrderid()));
		portalOrder.setFees(fees);
		getDAO().save(portalOrder);
		return portalOrder;
	}

}
