package com.maywide.biz.pay.electonic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.market.entity.BizPortalOrder;
import com.maywide.biz.market.entity.CustOrder;
import com.maywide.biz.pay.electonic.entity.PayResultRecord;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.nio.NettyServerBootstrap;
import com.maywide.core.nio.msg.ElectionPayMsg;
import com.maywide.core.nio.msg.MsgType;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

/**
 * Servlet implementation class PayNoticeResultServlet
 */
@WebServlet("/PayNoticeResultServlet")
public class PayNoticeResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String ENCODE_TYPE = "UTF-8";

	private static final Logger logger = LoggerFactory.getLogger(PayNoticeResultServlet.class);

	protected PersistentService persistentService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PayNoticeResultServlet() {
		super();
		persistentService = (PersistentService) SpringContextHolder.getBean(PersistentService.class);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding(ENCODE_TYPE);
		doExcet(request, response);
	}

	private void doExcet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "success";
		PayResultRecord  record = initRecord();
		try {
			Map<String, String[]> paramMap = request.getParameterMap();
			Entry<String, String[]> entry = paramMap.entrySet().iterator().next();
			String content = entry.getKey();
			JSONObject object = new JSONObject(content);
			JSONObject data = new JSONObject(object.getString("data"));
			String resultCode = data.getString("result_code"); // 支付结果编码
			String payment_type = data.getString("payment_type"); // 支付方式编码
			String order_amount = data.getString("order_amount"); // 支付金额
			String orderid = data.getString("out_trade_no"); // 支付订单id
			if(StringUtils.isBlank(orderid)){
				record.setHandlerstatus("0");
				record.setMemo("订单id为空");
				result = "false";
				return;
			}
			record.setOrderid(orderid);
			if(StringUtils.isBlank(order_amount)){
				record.setHandlerstatus("0");
				record.setMemo("订单支付金额为空");
				result = "false";
				return;
			}
			record.setOrdernum(order_amount);
			if(StringUtils.isBlank(payment_type)){
				record.setHandlerstatus("0");
				record.setMemo("订单支付方式为空");
				result = "false";
				return;
			}
			record.setPaytype(payment_type);
			if(StringUtils.isBlank(resultCode)){
				record.setHandlerstatus("0");
				record.setMemo("处理结果编码为空");
				result = "false";
				return;
			}
			record.setResultcode(resultCode);
			if (resultCode.equalsIgnoreCase("SUCCESS")) {
				handlerOrderSuccess(orderid, order_amount, payment_type,record);
				record.setPaystatus("1");
			} else {
				handlerOrderFail(orderid);
				record.setPaystatus("0");
			}
		} catch (Exception e) {
			result = "false";
		}finally{
			try {
				persistentService.save(record);
			} catch (Exception e) {
			}
			response.setCharacterEncoding(ENCODE_TYPE);
			response.setContentType(CONTENT_TYPE);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			PrintWriter writer = response.getWriter();
			writer.println(result);
		}
	}

	/**
	 * 订单支付成功时的处理逻辑
	 * @throws Exception 
	 */
	private void handlerOrderSuccess(String orderid, String acount, String payment_type,PayResultRecord record) throws Exception {
		logger.info("==================start dle pay handler=====================");
		CustOrder order = new CustOrder();
		order.setId(Long.parseLong(orderid));
		List<CustOrder> orders = persistentService.find(order);
		if (orders != null && !orders.isEmpty()) {
			order = orders.get(0);
			PrvOperator operator = getOperator(order.getOperator());
			if (operator != null) {
				BizPortalOrder bizPortalOrder = order.getPortalOrder();
				bizPortalOrder.setWgpayway(BizConstant.SysPayway.SYS_PAYWAY_ELECTIONS);
				bizPortalOrder.setPayway(getSysPayway(payment_type));
				bizPortalOrder.setPaycode(getSysPaycode(payment_type));
				double bizacount = Double.parseDouble(bizPortalOrder.getFees());
				double orderAcount = Double.parseDouble(acount) * 100.00;;
				if(bizacount != orderAcount){
					record.setHandlerstatus("0");
					record.setMemo("支付金额与订单金额不一致");
					handlerOrderFail(orderid);
					return;
				}
				bizPortalOrder.setStatus(BizConstant.PORTAL_ORDER_STATUS.PORTAL_ORDER_STATUS_ELECTION);
				record.setHandlerstatus("1");
				persistentService.update(order);
				sendMsg2Client(operator.getLoginname(), getPayMsg("success"));
				logger.info("==================dl pay handler=====================success");
			}else{
				handlerOrderFail(orderid);
				record.setHandlerstatus("1");
				record.setMemo("操作员编号为空");
				logger.info("==================dl pay handler=====================failed nobody");
			}
		}else{
			handlerOrderFail(orderid);
			record.setHandlerstatus("0");
			record.setMemo("找不到该订单");
			logger.info("==================dl pay handler=====================failed no order");
		}
	}

	/**
	 * 订单支付失败时的处理逻辑
	 * @throws Exception 
	 */
	private void handlerOrderFail(String orderid) throws Exception {
		logger.info("==================dl pay handler=====================failed");
		CustOrder order = new CustOrder();
		order.setId(Long.parseLong(orderid));
		List<CustOrder> orders = persistentService.find(order);
		if (orders != null && !orders.isEmpty()) {
			order = orders.get(0);
			PrvOperator operator = getOperator(order.getOperator());
			if(operator != null){
				sendMsg2Client(operator.getLoginname(), getPayMsg("fail"));
			}
		}
	}

	private ElectionPayMsg getPayMsg(String result) {
		ElectionPayMsg msg = new ElectionPayMsg();
		msg.setPayStatus(result);
		msg.setCmdtype(SysConstant.CmdType.ELECTION_PAY_NOTICE);
		return msg;
	}

	private PrvOperator getOperator(Long operid) throws Exception {
		PrvOperator operator = new PrvOperator();
		operator.setId(operid);
		List<PrvOperator> operators = persistentService.find(operator);
		if (operators == null || operators.isEmpty())
			return null;
		return operators.get(0);
	}

	private void sendMsg2Client(String clientId, Object obj) {
		try {
			NettyServerBootstrap.sendAllClient(clientId, obj, SysConstant.CmdType.ELECTION_PAY_NOTICE,
					MsgType.ASK.toString());
		} catch (Exception e) {

		}
	}

	private String getSysPayway(String payment_type) {
		String payway = "W";
		if (payment_type.equals(BizConstant.DLplatform_Params.ALIPAY_NATIVE)) {
			payway = "Z";
		}
		return payway;
	}

	private String getSysPaycode(String payment_type) {
		if (payment_type.equals(BizConstant.DLplatform_Params.ALIPAY_NATIVE)) {
			return "040400";
		} else {
			return "041000";
		}
	}
	
	private PayResultRecord initRecord(){
		PayResultRecord record = new PayResultRecord();
		record.setHandlerstatus("2");
		record.setRtime(DateUtils.formatDate(new Date(),"yyyyMMdd HHmmss"));
		return record;
	}

}
