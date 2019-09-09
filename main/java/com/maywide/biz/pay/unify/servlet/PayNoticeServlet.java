package com.maywide.biz.pay.unify.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.cons.BizConstant.BossInterfaceService;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.pojo.chargeFeeBook.PayBackoffReq;
import com.maywide.biz.inter.pojo.chargeFeeBook.PayBackoffResp;
import com.maywide.biz.inter.pojo.chargeFeeBook.QuePayOrderReq;
import com.maywide.biz.inter.pojo.chargeFeeBook.QuePayOrderResp;
import com.maywide.biz.inter.pojo.wechatBinding.WechatBindingReq;
import com.maywide.biz.inter.pojo.wechatBinding.WechatBindingResp;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.pay.unify.entity.PayRecordLog;
import com.maywide.biz.pay.unify.pojo.PayNoticeBean;
import com.maywide.biz.pay.unify.service.PayNoticeService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.exception.BusinessException;
import com.maywide.core.nio.NettyServerBootstrap;
import com.maywide.core.nio.msg.CommonMsg;
import com.maywide.core.nio.msg.MsgType;
import com.maywide.core.security.AuthContextHolder;

public class PayNoticeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	private static final String ENCODE_TYPE = "UTF-8";
	private PayNoticeService noticeService = null;
	private ParamService paramService = null;
	private static final Logger logger = LoggerFactory.getLogger(PayNoticeServlet.class);

	private ObjectMapper jsonMapper = null;
	

	public void init() throws ServletException {
		noticeService = SpringContextHolder.getBean(PayNoticeService.class);
		paramService = SpringContextHolder.getBean(ParamService.class);
		jsonMapper = new ObjectMapper();
		jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding(ENCODE_TYPE);
		doExec(request, response);
	}

	public void doExec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		synchronized (request) {
			response.setCharacterEncoding(ENCODE_TYPE);
			String clientId = request.getParameter("clientId");
			String orderid = request.getParameter("orderid");
			String requestid = request.getParameter("requestid");
			String loginInfoStr = request.getParameter("loginInfo").replace("\\\"", "\"");
			String biztype = request.getParameter("biztype");
			String extra = request.getParameter("extra");
			String multipaywayflag = request.getParameter("multipaywayflag");
			String cashe = request.getParameter("cashe");
			// 绑定新增参数
			String areaid = request.getParameter("areaid");
			String city = request.getParameter("city");
			String permark = request.getParameter("permark");
			String devno = request.getParameter("devno");
			PayNoticeBean payNotice = null;
			String responeStr = "success";
			PayRecordLog recordLog = noticeService.recordPay(orderid);
			try {
				printParams(request);
				String output = request.getParameter("output");
				if(null != recordLog) {
					recordLog.setRedicetStr(output);
					noticeService.updatePayRecordStatus(recordLog);
				}
				payNotice = jsonMapper.readValue(output, PayNoticeBean.class);
				LoginInfo loginInfo = jsonMapper.readValue(loginInfoStr, LoginInfo.class);
				AuthContextHolder.setLoginInfo(loginInfo);
				if ("2".equals(payNotice.getStatus())) {
					QuePayOrderResp order = quePayOrder(requestid);
					if(null != recordLog) {
						recordLog.setPayresult("1");
						noticeService.updatePayRecordStatus(recordLog);
					}
					order.setOrderNo(orderid);
					if(biztype.equalsIgnoreCase("CSJF")) {
						noticeService.handlerSupplementGather(orderid,Double.toString(order.getTotalFee()));
						if(null != recordLog) {
							recordLog.setFees(Double.toString(order.getTotalFee()));
							recordLog.setPayresult("2");
							noticeService.updatePayRecordStatus(recordLog);
						}
						return;
						
					}
					if(BossInterfaceService.WFL_EQUIPINFO_SUBMIT.equalsIgnoreCase(biztype)) {
						noticeService.submitBizCustOrder(orderid, Double.toString(order.getTotalFee()), 
								order.getPayway(), order.getPaycode());
						if(null != recordLog) {
							recordLog.setFees(Double.toString(order.getTotalFee()));
							recordLog.setPayresult("2");
							noticeService.updatePayRecordStatus(recordLog);
						}
						return;
					}
					CustOrder custOrder = (CustOrder) noticeService.getDAO().find(CustOrder.class,
							Long.parseLong(orderid));
					if (custOrder == null) {
						throw new BusinessException("未能找到该订单【" + orderid + "】");
					}
					logger.info("=========start biz business======");
					if (BizConstant.BizOpcode.BIZ_UP_NOTPAIDFEES.equals(custOrder.getOpcode())) {
						noticeService.bizConfirmaPay(order, extra);
					} else if (BizConstant.BizOpcode.BIZ_USER_NEW.equals(custOrder.getOpcode())) {
						noticeService.bizInstallCommit(order, extra,multipaywayflag,cashe);
					} else if (BizConstant.BizOpcode.BIZ_PARTS_SALES.equals(custOrder.getOpcode())) {
						noticeService.bizSaleCommit(orderid,order, extra,multipaywayflag,cashe);
					} else if (BizConstant.PAY_BIZ_TYPE.ORDER.equals(biztype)
							|| BizConstant.BossInterfaceService.BIZ_FEEIN.equals(biztype)
							|| BizConstant.BizOpcode.BIZ_SALES_TERMINAL.equals(custOrder.getOpcode())) {
						noticeService.orderCommit(order, extra,multipaywayflag,cashe);
					} else if (BizConstant.PAY_BIZ_TYPE.CHG_DEV.equals(biztype)) {
						noticeService.changeDevice(order, extra,multipaywayflag,cashe);
					} else {
						throw new BusinessException("传入的业务类型不正确！");
					}
					if(null != recordLog) {
						recordLog.setFees(Double.toString(order.getTotalFee()));
						recordLog.setPayresult("2");
						noticeService.updatePayRecordStatus(recordLog);
					}

					logger.info("订单【" + payNotice.getOrderNo() + "】" + "统一支付平台支付成功");

				} else {
					logger.info("订单【" + payNotice.getOrderNo() + "】" + "统一支付平台支付失败");
					if(null != recordLog) {
						recordLog.setPayresult("5");
						noticeService.updatePayRecordStatus(recordLog);
					}
				}

			} catch (Exception e) {
				logger.error("===submit order =="+orderid+"==== fail reason:"+e.getMessage());
				if(null != recordLog) {
					recordLog.setPayresult("3");
					noticeService.updatePayRecordStatus(recordLog);
				}
				payNotice = refund(orderid, requestid, payNotice,recordLog);
				if (payNotice != null && e instanceof BusinessException) {
					if (StringUtils.isNotBlank(e.getMessage())) {
						payNotice.setMessage(payNotice.getMessage() + "\n" + e.getMessage());
						if(null != recordLog) {
							recordLog.setBizexmsg(e.getMessage());
							noticeService.updatePayRecordStatus(recordLog);
						}
					}
				}
				logger.error("支付回调异常", e);
				responeStr = e.getMessage() == null ? "fail" : e.getMessage();
			} finally {
				if (payNotice == null) {
					payNotice = new PayNoticeBean();
					payNotice.setOrderNo(orderid);
					payNotice.setRequestid(requestid);
					payNotice.setStatus("-3");
					payNotice.setMessage("支付失败");
					responeStr = "fail";
				}
				String content = jsonMapper.writeValueAsString(payNotice);
				CommonMsg askMsg = new CommonMsg();
				askMsg.setType(MsgType.ASK.toString());
				askMsg.setCmdtype(SysConstant.CmdType.UNIFY_PAY_NOTICE);
				askMsg.setClientId(clientId);
				askMsg.setContent(content);
				NettyServerBootstrap.sendAllClient(clientId, askMsg);
				response.getWriter().write(responeStr);
				if (payNotice != null) {
					bindWx(payNotice.getOpenid(), city, areaid, devno, permark, payNotice.getPaycode(),
							payNotice.getOrderNo());
				}

			}
		}

	}

	private void bindWx(final String openid, final String city, final String areaid, final String devno,
			final String permark, final String paycode, String orderNo) {
		try {
			if(StringUtils.isBlank(openid) || StringUtils.isBlank(city)
					|| StringUtils.isBlank(areaid) || StringUtils.isBlank(devno) || StringUtils.isBlank(permark)
					|| StringUtils.isBlank(paycode) || StringUtils.isBlank(orderNo)){
				return;
			}
			List<PrvSysparam> statusParams = paramService.getDataNotCompare("Wechat_Binding_Equipment_Market");
			String wechatBindingCity = statusParams.get(0).getMcode();
			if (city.equals(wechatBindingCity)) {
				// 是微信支付成功的话 异步去微厅绑定智能卡
				if ("041000".equals(paycode) || "041100".equals(paycode)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								WechatBindingReq wechatBindingReq = new WechatBindingReq();
								wechatBindingReq.setAreaid(areaid);
								wechatBindingReq.setCity(city);
								wechatBindingReq.setDevno(devno);
								wechatBindingReq.setOpenid(openid);
								wechatBindingReq.setPermark(permark);
								WechatBindingResp resp = new WechatBindingResp();
								try {
									PubService pubService = ((PubService) SpringContextHolder
											.getBean(PubService.class));
									pubService.bindWechatDevno(wechatBindingReq, resp);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).start();
				}
			}
		} catch (Exception e) {
			logger.info("bind  wxt fail" + e.getMessage());
		}
	}

	private PayNoticeBean refund(String orderid, String requestid, PayNoticeBean payNotice,PayRecordLog recordLog) {
		if (payNotice == null || "2".equals(payNotice.getStatus())) {
			if (payNotice == null) {
				payNotice = new PayNoticeBean();
				payNotice.setOrderNo(orderid);
				payNotice.setRequestid(requestid);
				payNotice.setStatus("-1");
				payNotice.setMessage("支付失败，已回退支付订单");
			} else {
				payNotice.setStatus("-2");
				payNotice.setMessage("业务提交失败，已回退支付订单");
			}

			try {
				PayBackoffReq req = new PayBackoffReq();
				req.setRequestid(requestid);
				String responseStr = noticeService.getPayPlatFormInfOutput(BizConstant.Platform_Service.REFUND, req);
				PayBackoffResp resp = jsonMapper.readValue(responseStr, PayBackoffResp.class);
				if (!"0".equals(resp.getStatus())) {
					payNotice.setStatus("-3");
					String msg = "支付回退失败\n" + (resp.getMessage() == null ? "" : resp.getMessage());
					payNotice.setMessage(msg);
					if(null != recordLog) {
						recordLog.setPayresult("4");
						noticeService.updatePayRecordStatus(recordLog);
					}
				}
			} catch (Exception exception) {
				payNotice.setStatus("-4");
				payNotice.setMessage("支付回退异常，请手动回退");
				if(null != recordLog) {
					recordLog.setPaybackexmsg(exception.getMessage());
					recordLog.setPayresult("4");
					noticeService.updatePayRecordStatus(recordLog);
				}
				logger.error("支付回退冲正失败", exception);
			}
		}
		return payNotice;
	}

	private QuePayOrderResp quePayOrder(String requestid)
			throws Exception, IOException, JsonProcessingException, JsonParseException, JsonMappingException {
		QuePayOrderReq quePayOrderReq = new QuePayOrderReq();
		quePayOrderReq.setRequestid(requestid);
		String responseStr = noticeService.getPayPlatFormInfOutput(BizConstant.Platform_Service.QUEORDER,
				quePayOrderReq);
		if (StringUtils.isBlank(responseStr)) {
			throw new BusinessException("统一支付订单查询为空");
		}
		JsonNode jsonNode = jsonMapper.readTree(responseStr);
		QuePayOrderResp quePayOrderResp = jsonMapper.readValue(jsonNode.get("output").toString(),
				QuePayOrderResp.class);
		if (!"4".equals(quePayOrderResp.getStatus())) {
			throw new BusinessException("统一支付平台订单不是已支付状态");
		}
		if (StringUtils.isBlank(quePayOrderResp.getPayway())) {
			throw new BusinessException("统一支付订单的支付方式或支付编码为空");
		}
		return quePayOrderResp;
	}

	public void destroy() {
	}

	public static Map<String, String> getUrlParams(String param) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	public static void printParams(HttpServletRequest request) {
		logger.info("==========print paramter start============");
		try {
			String content = IOUtils.toString(request.getInputStream());
			System.out.println("InputStream : " + content);
		} catch (IOException e) {
			logger.info("==========IOUtils.toString:print paramter error============");
		}

		try {
			for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				String string = "key : " + entry.getKey() + " value : " + StringUtils.join(entry.getValue(), ",");
				System.out.println(string);
			}
		} catch (Exception e) {
			logger.info("==========Entry<String, String[]> entry:print paramter error============");
		}
		logger.info("==========print paramter end============");
	}

}
