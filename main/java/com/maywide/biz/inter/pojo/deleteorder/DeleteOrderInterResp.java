package com.maywide.biz.inter.pojo.deleteorder;


public class DeleteOrderInterResp  implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6862651932583668794L;
	
	private Boolean success;
	
	private String result;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	

}
