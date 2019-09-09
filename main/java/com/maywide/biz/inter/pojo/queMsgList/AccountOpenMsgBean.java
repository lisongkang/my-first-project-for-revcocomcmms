package com.maywide.biz.inter.pojo.queMsgList;

import com.maywide.biz.inter.entity.AccountOpenMsg;

public class AccountOpenMsgBean extends AccountOpenMsg {

	private String actchartimeMemo;

	private String billoptimeMemo;

	public String getActchartimeMemo() {
		return actchartimeMemo;
	}

	public void setActchartimeMemo(String actchartimeMemo) {
		this.actchartimeMemo = actchartimeMemo;
	}

	public String getBilloptimeMemo() {
		return billoptimeMemo;
	}

	public void setBilloptimeMemo(String billoptimeMemo) {
		this.billoptimeMemo = billoptimeMemo;
	}

	public AccountOpenMsgBean() {
		super();
	}
	
	public AccountOpenMsgBean(AccountOpenMsg msg){
		super();
		setAccetime(msg.getAccetime());
		setAccountrule(msg.getAccountrule());
		setAccstime(msg.getAccstime());
		setAccstimetoaccetime(msg.getAccstimetoaccetime());
		setActchartime(msg.getActchartime());
		setBilloptime(msg.getBilloptime());
		setChargetime(msg.getChargetime());
		setDelaycycle(msg.getDelaycycle());
		setFee(msg.getFee());
		setId(msg.getId());
		setMid(msg.getMid());
		setNlevel(msg.getNlevel());
		setPayway(msg.getPayway());
		setPfeetype(msg.getPfeetype());
		setTjmonth(msg.getTjmonth());
	}

}
