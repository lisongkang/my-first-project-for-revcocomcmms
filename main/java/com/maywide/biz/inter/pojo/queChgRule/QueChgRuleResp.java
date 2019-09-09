package com.maywide.biz.inter.pojo.queChgRule;

import java.util.List;

public class QueChgRuleResp {

	private boolean sourceAble = true;
	
	private boolean chgTypeAble = true;
	
	private String typeCode;

	private String reason;
	
	private String newuseprop;
	
	private List<OneTimeFeeBO> oneTimeFees;
	
	public boolean isSourceAble() {
		return sourceAble;
	}

	public void setSourceAble(boolean sourceAble) {
		this.sourceAble = sourceAble;
	}

	public boolean isChgTypeAble() {
		return chgTypeAble;
	}

	public void setChgTypeAble(boolean chgTypeAble) {
		this.chgTypeAble = chgTypeAble;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getNewuseprop() {
		return newuseprop;
	}

	public void setNewuseprop(String newuseprop) {
		this.newuseprop = newuseprop;
	}

	public List<OneTimeFeeBO> getOneTimeFees() {
		return oneTimeFees;
	}

	public void setOneTimeFees(List<OneTimeFeeBO> oneTimeFees) {
		this.oneTimeFees = oneTimeFees;
	}
	
	
}
