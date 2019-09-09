package com.maywide.biz.inter.pojo.editCart;

public class EditCartReq {
	
	private Long cartid;
	
	private String custid;
	
	private int number;
	
	private Long goodsid;
	
	private Long catalogid;
	

	public Long getCartid() {
		return cartid;
	}

	public void setCartid(Long cartid) {
		this.cartid = cartid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}

	public Long getCatalogid() {
		return catalogid;
	}

	public void setCatalogid(Long catalogid) {
		this.catalogid = catalogid;
	}


}
