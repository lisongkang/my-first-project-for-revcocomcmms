package com.maywide.biz.inter.pojo.queorderstatis;

@SuppressWarnings("serial")
public class OrderStatisBO implements java.io.Serializable {

	private String businessName;

	private long successCount;

	private long failedCount;

	private long unDoneCount;

	private long count;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(long successCount) {
		this.successCount = successCount;
	}

	public long getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(long failedCount) {
		this.failedCount = failedCount;
	}

	public long getUnDoneCount() {
		return unDoneCount;
	}

	public void setUnDoneCount(long unDoneCount) {
		this.unDoneCount = unDoneCount;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
