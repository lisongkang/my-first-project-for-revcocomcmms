package com.maywide.biz.prv.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "PRV_CART_INFO")
public class PrvCartInfo implements Serializable{

	private Long id;
	
	//private Long cartid;
	private PrvCartGoods goods;
	
	private Long operid;
	
	private Long custid;
	
	private Long areaid;
	
	private String pathid;
	
	private String whladdr;
	
	private String houseid;
	
	private String servid;
	
	private String keyno;
	
	private String city;
	
	private String custname;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CARTID", nullable = false)
    @NotFound(action=NotFoundAction.IGNORE)
	public PrvCartGoods getGoods() {
		return goods;
	}

	public void setGoods(PrvCartGoods goods) {
		this.goods = goods;
	}
	
	/*public Long getCartid() {
		return cartid;
	}

	public void setCartid(Long cartid) {
		this.cartid = cartid;
	}*/

	@Column(name = "OPERID", nullable = false)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "CUSTID", nullable = false)
	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	@Column(name = "AREAID", nullable = false)
	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	@Column(name = "PATHID", nullable = false)
	public String getPathid() {
		return pathid;
	}

	public void setPathid(String pathid) {
		this.pathid = pathid;
	}

	@Column(name = "WHLADDR", nullable = false)
	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}

	@Column(name = "HOUSEID", nullable = false)
	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	@Column(name = "SERVID")
	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	@Column(name = "KEYNO", nullable = false)
	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	@Column(name = "CITY", nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "CUSTNAME", nullable = false)
	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}
	
	
}
