package com.maywide.biz.inter.pojo.queOrderBizOpcodeRule;

import java.util.List;

public class QueOrderBizOpcodeRuleResp {

	private boolean hasOrderBiz;
	
	private List<String> opcodes;

	public boolean isHasOrderBiz() {
		return hasOrderBiz;
	}

	public void setHasOrderBiz(boolean hasOrderBiz) {
		this.hasOrderBiz = hasOrderBiz;
	}

	public List<String> getOpcodes() {
		return opcodes;
	}

	public void setOpcodes(List<String> opcodes) {
		this.opcodes = opcodes;
	}
	
	
	
}
