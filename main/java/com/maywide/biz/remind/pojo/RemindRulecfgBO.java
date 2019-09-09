package com.maywide.biz.remind.pojo;

import com.maywide.biz.remind.entity.BizRemRulecfg;
import com.maywide.biz.remind.entity.BizRemindWarning;

public class RemindRulecfgBO {
	private BizRemindWarning bizRemindWarning;
	
	private BizRemRulecfg bizRemRulecfg;

	public BizRemindWarning getBizRemindWarning() {
		return bizRemindWarning;
	}

	public void setBizRemindWarning(BizRemindWarning bizRemindWarning) {
		this.bizRemindWarning = bizRemindWarning;
	}

	public BizRemRulecfg getBizRemRulecfg() {
		return bizRemRulecfg;
	}

	public void setBizRemRulecfg(BizRemRulecfg bizRemRulecfg) {
		this.bizRemRulecfg = bizRemRulecfg;
	}
	
}
