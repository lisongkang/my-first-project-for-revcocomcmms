package com.maywide.biz.pay.weixin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "biz_weixin_pay")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayCallbackNotify implements java.io.Serializable {
	private String id;
	
	private String appid;
	
	private String attach;
	
	private String bank_type;
	
	private String cash_fee;
	
	private String is_subscribe;
	
	private String mch_id;
	
	private String nonce_str;
	
	private String openid;
	
	private String out_trade_no;
	
	private String result_code;
	
	private String return_code;
	
	private String sign;
	
	private String time_end;
	
	private String total_fee;
	
	private String trade_type;
	
	private String transaction_id;
	
	private String status;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	@Column(name = "bank_type")
	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	@Column(name = "cash_fee")
	public String getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}

	@Column(name = "is_subscribe")
	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	@Column(name = "mch_id")
	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	@Transient
	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "out_trade_no")
	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	@Column(name = "result_code")
	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	@Column(name = "return_code")
	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	@Transient
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Transient
	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	@Column(name = "total_fee")
	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	@Transient
	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	@Column(name = "transaction_id")
	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
