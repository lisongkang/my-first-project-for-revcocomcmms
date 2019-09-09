package com.maywide.biz.pay.weixin;

public class Refund {
	private String appid;	//公众账号 ID appid
	
	private String mch_id; // 商户号 mch_id

	private String nonce_str; //随机字符串 nonce_str
	
	private String sign; //签名 sign
	
	private String transaction_id;
	
	//private String out_trade_no;//商户订单号 out_trade_no

	private String total_fee;	//订单价格 total_fee
	
	private String refund_fee;	

	private String refund_fee_type;
	
	private String op_user_id;
	
	private String out_refund_no;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mchId) {
		mch_id = mchId;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonceStr) {
		nonce_str = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transactionId) {
		transaction_id = transactionId;
	}

	/*public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String outTradeNo) {
		out_trade_no = outTradeNo;
	}*/

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String totalFee) {
		total_fee = totalFee;
	}

	public String getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(String refundFee) {
		refund_fee = refundFee;
	}

	public String getRefund_fee_type() {
		return refund_fee_type;
	}

	public void setRefund_fee_type(String refundFeeType) {
		refund_fee_type = refundFeeType;
	}

	public String getOp_user_id() {
		return op_user_id;
	}

	public void setOp_user_id(String opUserId) {
		op_user_id = opUserId;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String outRefundNo) {
		out_refund_no = outRefundNo;
	}
}
