package com.maywide.biz.pay.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.inter.service.PayService;
import com.maywide.biz.pay.weixin.entity.PayCallbackNotify;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.nio.NettyServerBootstrap;
import com.maywide.core.nio.msg.CommonMsg;
import com.maywide.core.nio.msg.MsgType;
import com.maywide.core.service.PersistentService;

public class PayNotifyServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String ENCODE_TYPE = "UTF-8";

	private static final Logger logger = LoggerFactory.getLogger(PayNotifyServlet.class);
	
	private PersistentService persistentService = null;
	
	public void init() throws ServletException
	{
		persistentService = (PersistentService) SpringContextHolder.getBean(PersistentService.class);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding(ENCODE_TYPE);
		doExec(request, response);
	}
	
	public void doExec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream inputStream = request.getInputStream();
		
		try {
			String content = IOUtils.toString(inputStream);
			logger.info("PayNotifyServlet.doExec 微信支付结果通知 = " + content);
			
			if (StringUtils.isEmpty(content)) {
				return;
				//content = "<xml><appid><![CDATA[wxb308ad712f6decea]]></appid><attach><![CDATA[1091013]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1220354901]]></mch_id><nonce_str><![CDATA[au9xtb6ktm8oyvcjppsqk263jlgcoljn]]></nonce_str><openid><![CDATA[oW9QCj8OhDJALgITNW2xAFLdppV8]]></openid><out_trade_no><![CDATA[1091013]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[7C893B37A6B4652838DB1F83A1626388]]></sign><time_end><![CDATA[20151130111519]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[1007760930201511301832442957]]></transaction_id></xml>";
			}
			String replyXML = generatePaySuccessReplyXML();
			
			OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), ENCODE_TYPE);
			osw.write(replyXML);
			osw.close();
			
			logger.info("微信支付成功 XXXXXXXXXXXXXXXXXXX");
			
			PayCallbackNotify payCallbackNotify = (PayCallbackNotify) Util.getObjectFromXML(content, PayCallbackNotify.class);
			String status = BizConstant.BizWeixinPaystatus.SUCCESS;
			if (!payCallbackNotify.getResult_code().equals("SUCCESS")) {
				status = BizConstant.BizWeixinPaystatus.FAILED;
			}
			
			payCallbackNotify.setStatus(status);
			persistentService.save(payCallbackNotify);
			
			String clientId = PayService.orderidMap.get(payCallbackNotify.getOut_trade_no());
			logger.info("微信支付成功 222222222222222 = " + clientId);
			if (StringUtils.isEmpty(clientId)) {
				//clientId = "GZCYKFACS";
			}
			if(payCallbackNotify.getResult_code().equals("SUCCESS")
					&& payCallbackNotify.getReturn_code().equals("SUCCESS")){
				logger.info("微信支付成功");
				int totalfee = Integer.valueOf(payCallbackNotify.getTotal_fee());
				double fee = totalfee / 100.0;
				CommonMsg askMsg = new CommonMsg();
	        	askMsg.setType(MsgType.ASK.toString());
	    		askMsg.setCmdtype(SysConstant.CmdType.WEIXIN_REPLY);
	    		String replyMsg = "订单微信支付成功，订单号【" + payCallbackNotify.getOut_trade_no() + "】，金额：" 
	    							+ fee + " 元";
	    		//askMsg.setContent("0," + replyMsg);
	    		askMsg.setContent("0");

	    		logger.info("微信发送支付通知：" + clientId);
	    		NettyServerBootstrap.sendAllClient(clientId, askMsg);
			} else {
				logger.info("微信支付失败");
				CommonMsg askMsg = new CommonMsg();
	        	askMsg.setType(MsgType.ASK.toString());
	    		askMsg.setCmdtype(SysConstant.CmdType.WEIXIN_REPLY);
	    		String replyMsg = "订单微信支付失败，订单号【" + payCallbackNotify.getOut_trade_no() + "】";
	    		//askMsg.setContent("1," + replyMsg);
	    		askMsg.setContent("1");
	    		
	    		NettyServerBootstrap.sendAllClient(clientId, askMsg);
			}
		} catch (Exception e) {
			logger.error("接收微信支付回调出错：", e);
		}
	}
	
	public void destroy() {}
	
	public static String generatePaySuccessReplyXML(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<xml>")
					.append("<return_code><![CDATA[SUCCESS]]></return_code>")
					.append("<return_msg><![CDATA[OK]]></return_msg>")
					.append("</xml>");
		return stringBuffer.toString();
	}
}
