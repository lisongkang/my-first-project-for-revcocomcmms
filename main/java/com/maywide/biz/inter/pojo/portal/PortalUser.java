package com.maywide.biz.inter.pojo.portal;

/**
 * 
 *<p> 
 *
 *<p>
 * Create at 2016-11-2
 *
 *@autor zhuangzhitang
 */
public class PortalUser {
	
	private Integer id;
	private String operid;
	private String userid;
	private String client_id;
	private String status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperid() {
		return operid;
	}
	public void setOperid(String operid) {
		this.operid = operid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
