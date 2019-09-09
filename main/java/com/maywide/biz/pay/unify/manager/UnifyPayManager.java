package com.maywide.biz.pay.unify.manager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.pojo.quecustorder.CustordersBO;
import com.maywide.biz.pay.unify.pojo.MultiPayBean;
import com.maywide.biz.pay.unify.pojo.QueUnifyPayInfoResp;
import com.maywide.biz.pay.unify.pojo.UnifyPayBean;
import com.maywide.biz.pay.unify.pojo.UnifyPayCondition;
import com.maywide.biz.pay.unify.pojo.UnifyPayWechatBingBean;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.util.DateUtils;
import com.maywide.payplat.security.SecureLink;

public final class UnifyPayManager {

	private UnifyPayManager() {
	}

	public static QueUnifyPayInfoResp generatePayReq(CustordersBO order, UnifyPayCondition condition, 
			UnifyPayWechatBingBean unifyPayWechatBingBean,MultiPayBean multiPayBean) throws BusinessException {

		String requestId = getRequestId();
		UnifyPayBean payBean = getUnifyPayBean(order, requestId, condition,unifyPayWechatBingBean, multiPayBean);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("clienttype", BizConstant.PAY_CLIENT_TYPE.MERCHANT_MOBILE);
		map.put("servicecode", BizConstant.Platform_Service.PAY);
		map.put("clientcode", SysConfig.getPlatform_code());
		map.put("clientpwd", SysConfig.getPlatform_pwd());
		map.put("requestid", requestId);
		String requestContent = new Gson().toJson(payBean);
		System.out.println("==dataSign的requestContent为:====="+requestContent);
		map.put("requestContent", requestContent);

		String dataSign = getDataSign(map);
		map.put("dataSign", dataSign);

		String url = getUrlWithMap(SysConfig.getPlatform_outer_url(), map);
		QueUnifyPayInfoResp resp = new QueUnifyPayInfoResp(requestId, requestContent, url);
		return resp;
	}
	
	
	public static QueUnifyPayInfoResp generatePayReq(String fees,String custOdrerid,String opcode,String custName
			,String custid,String bizType) {
		String requestId = getRequestId();
		UnifyPayBean payBean = genrateUnifyPayBean(fees,custOdrerid,opcode,custName,custid,requestId,bizType);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("clienttype", BizConstant.PAY_CLIENT_TYPE.MERCHANT_MOBILE);
		map.put("servicecode", BizConstant.Platform_Service.PAY);
		map.put("clientcode", SysConfig.getPlatform_code());
		map.put("clientpwd", SysConfig.getPlatform_pwd());
		map.put("requestid", requestId);
		String requestContent = new Gson().toJson(payBean);
		map.put("requestContent", requestContent);

		String dataSign = getDataSign(map);
		map.put("dataSign", dataSign);

		String url = getUrlWithMap(SysConfig.getPlatform_outer_url(), map);
		QueUnifyPayInfoResp resp = new QueUnifyPayInfoResp(requestId, requestContent, url);
		return resp;
	}
	
	private static UnifyPayBean genrateUnifyPayBean(String fees,String custOdrerid,String opcode,String custName
			,String custid,String requestId,String bizType){
		UnifyPayBean payBean = new UnifyPayBean();
		payBean.setIsOrder("N");
		payBean.setTotalFee(fees);
		String redirectUrl = String.format("%s?orderNo=%s&payDetail=%s", SysConfig.getPlatform_redirect_url(),
				custOdrerid, opcode);
		payBean.setRedirectUrl(redirectUrl);
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		HashMap<String, String> noticeParam = new HashMap<String, String>();
		noticeParam.put("clientId", loginInfo.getLoginname());
		noticeParam.put("requestid", requestId);
		noticeParam.put("orderid", custOdrerid);
		noticeParam.put("loginInfo", getLoginInfoJson(loginInfo));
		noticeParam.put("biztype", bizType);
		noticeParam.put("city",loginInfo.getCity());
		noticeParam.put("areaid",loginInfo.getAreaid().toString());
		
		String noticeAction = getUrlWithMap(SysConfig.getPlatform_notice_url(), noticeParam);
		noticeAction = encodeUrl(noticeAction.replace("\"", "\\\""));
//		noticeAction = noticeAction.replace("\"", "\\\"");
		payBean.setNoticeAction(noticeAction);

		payBean.setOrderNum(custOdrerid);
		
		UnifyPayBean.CustInfo custInfo = new UnifyPayBean.CustInfo();
		custInfo.setCustid(custid);
		custInfo.setCustname(custName);
		custInfo.setCity(loginInfo.getCity());
		custInfo.setArea(loginInfo.getAreaid().toString());
		payBean.setCustInfo(custInfo);
		
		UnifyPayBean.OrderInfo orderInfo = new UnifyPayBean.OrderInfo();
		orderInfo.setOrderNo(custOdrerid);

		UnifyPayBean.OrderInfo.Product product = new UnifyPayBean.OrderInfo.Product();
		product.setProductName(opcode);
		product.setFee(fees);

		List<UnifyPayBean.OrderInfo.Product> productList = new ArrayList<UnifyPayBean.OrderInfo.Product>();
		productList.add(product);
		orderInfo.setProductList(productList);
		payBean.setOrderInfo(orderInfo);
		return payBean;
	}

	private static UnifyPayBean getUnifyPayBean(CustordersBO order, String requestId,
			UnifyPayCondition condition,UnifyPayWechatBingBean unifyPayWechatBingBean,MultiPayBean multiPayBean) throws BusinessException {
		UnifyPayBean payBean = new UnifyPayBean();
		payBean.setIsOrder("N");
		String totalFees = order.getFees();
		if(multiPayBean != null) {
			if(StringUtils.isNotBlank(multiPayBean.getMultipaywayflag())
					&& multiPayBean.getMultipaywayflag().equalsIgnoreCase("Y")) {
				if(StringUtils.isNotBlank(multiPayBean.getCashe())) {
					double casheFees = Double.parseDouble(multiPayBean.getCashe());
					double orderFees = Double.parseDouble(order.getFees());
					if(casheFees >= orderFees) {
						throw new BusinessException("当前账本余额大于订单金额,无需使用第三方支付！");
					}
					double totalfees = orderFees - casheFees;
					totalFees = Double.toString(totalfees);
				}
			}
		}
		payBean.setTotalFee(totalFees);
		String redirectUrl = String.format("%s?orderNo=%s&payDetail=%s", SysConfig.getPlatform_redirect_url(),
				order.getCustorderid(), order.getOpcodename());

		payBean.setRedirectUrl(redirectUrl);

		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();

		HashMap<String, String> noticeParam = new HashMap<String, String>();
		noticeParam.put("clientId", loginInfo.getLoginname());
		noticeParam.put("requestid", requestId);
		noticeParam.put("orderid", order.getCustorderid());
		noticeParam.put("loginInfo", getLoginInfoJson(loginInfo));
		noticeParam.put("biztype", condition.getBizType());
		noticeParam.put("city",order.getCity());
		noticeParam.put("areaid",order.getAreaid());
		if(null != multiPayBean && StringUtils.isNotBlank(multiPayBean.getMultipaywayflag())
				&& StringUtils.isNotBlank(multiPayBean.getCashe())) {
			if(multiPayBean.getMultipaywayflag().equalsIgnoreCase("Y")) {
				noticeParam.put("multipaywayflag","Y");
				noticeParam.put("cashe",multiPayBean.getCashe());
			}
		}
		
		if(unifyPayWechatBingBean != null && !"{}".equals(unifyPayWechatBingBean))
		{
			if(unifyPayWechatBingBean.getDevno() != null && !"{}".equals(unifyPayWechatBingBean.getDevno())){
				noticeParam.put("devno",unifyPayWechatBingBean.getDevno());
				noticeParam.put("permark",unifyPayWechatBingBean.getPermark());
			}
		}

		if (condition.getExtra() != null && !"{}".equals(condition.getExtra())) {
			noticeParam.put("extra", condition.getExtra());
		}
		String noticeAction = getUrlWithMap(SysConfig.getPlatform_notice_url(), noticeParam);
//		noticeAction = encodeUrl(noticeAction.replace("\"", "\\\""));
		noticeAction = noticeAction.replace("\"", "\\\"");
		payBean.setNoticeAction(noticeAction);
		payBean.setOrderNum(order.getCustorderid());

		UnifyPayBean.CustInfo custInfo = new UnifyPayBean.CustInfo();
		custInfo.setCustid(order.getCustid());
		custInfo.setCustname(order.getCustname());
		custInfo.setCity(order.getCity());
		custInfo.setArea(order.getAreaid());
		payBean.setCustInfo(custInfo);

		UnifyPayBean.OrderInfo orderInfo = new UnifyPayBean.OrderInfo();
		orderInfo.setOrderNo(order.getCustorderid());

		UnifyPayBean.OrderInfo.Product product = new UnifyPayBean.OrderInfo.Product();
		product.setProductName(order.getOpcodename());
		product.setFee(order.getFees());

		List<UnifyPayBean.OrderInfo.Product> productList = new ArrayList<UnifyPayBean.OrderInfo.Product>();
		productList.add(product);
		orderInfo.setProductList(productList);
		payBean.setOrderInfo(orderInfo);
		return payBean;
	}

	private static String getLoginInfoJson(LoginInfo loginInfo) {
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("operid", loginInfo.getOperid());
		jsonObj.addProperty("loginname", loginInfo.getLoginname());
		jsonObj.addProperty("deptid", loginInfo.getDeptid());
		jsonObj.addProperty("areaid", loginInfo.getAreaid());
		jsonObj.addProperty("city", loginInfo.getCity());
		jsonObj.addProperty("roleid", loginInfo.getRoleid());
		return jsonObj.toString();
	}

	private static String encodeUrl(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return url;
		}
	}

	private static String getUrlWithMap(String url, HashMap<String, String> map) {
		String params = getUrlParamWithMap(map);
		return String.format("%s?%s", url, params);
	}

	private static String getUrlParamWithMap(HashMap<String, String> map) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append("&");
			}
			sb.append(entry.getKey()).append("=").append(entry.getValue());
		}
		return sb.toString();
	}

	private static String getDataSign(HashMap<String, String> map) {
		String dataSign = null;
		System.out.println(map);
		try {
			dataSign = SecureLink.sign(map, SysConfig.getPlatform_MD5());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSign;
	}

	private static String getRequestId() {
		return SysConfig.getPlatform_code() + DateUtils.formatDate(new Date(), DateUtils.FORMAT_YYYYMMDD)
				+ UUID.randomUUID().toString().substring(0, 8);
	}

}
