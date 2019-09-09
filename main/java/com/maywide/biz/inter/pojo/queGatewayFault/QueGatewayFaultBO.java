package com.maywide.biz.inter.pojo.queGatewayFault;

public class QueGatewayFaultBO {
	private String name;    //CA
	private String status;  //0 失败 红色  1 成功  绿色   -1 灰色 没数据
	private String desc;    //xxxx
	private String type;    //1--左边  2--右边
	
	private String optime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOptime() {
		return optime;
	}
	public void setOptime(String optime) {
		this.optime = optime;
	}
	
	

}
