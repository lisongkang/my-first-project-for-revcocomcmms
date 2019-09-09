package com.maywide.biz.market.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "BIZ_PORTAL_ORDER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizPortalOrder implements java.io.Serializable {

	private Long id;
	private Long resporderid;
	private String ordertype;
	private String fees;
	private String feestr;
	private String status;
	private Date createtime;
    private Date paytime;
	private String payway;
	private String payreqid;
	private String paycode;
	private String wgpayway;
	private String payFees;
	private String multipay;
	
	/*@Id
	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	
*/
	public Long getResporderid() {
		return resporderid;
	}

	@Id
	@Column(name = "orderid", unique = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setResporderid(Long resporderid) {
		this.resporderid = resporderid;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getFeestr() {
		return feestr;
	}

	public void setFeestr(String feestr) {
		this.feestr = feestr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getPayreqid() {
		return payreqid;
	}

	public void setPayreqid(String payreqid) {
		this.payreqid = payreqid;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

	public String getWgpayway() {
		return wgpayway;
	}

	public void setWgpayway(String wgpayway) {
		this.wgpayway = wgpayway;
	}

	public String getPayFees() {
		return payFees;
	}

	public void setPayFees(String payFees) {
		this.payFees = payFees;
	}

	public String getMultipay() {
		return multipay;
	}

	public void setMultipay(String multipay) {
		this.multipay = multipay;
	}

    
	
}
