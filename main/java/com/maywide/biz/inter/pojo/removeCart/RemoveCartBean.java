package com.maywide.biz.inter.pojo.removeCart;

public class RemoveCartBean {
	
	private Long cartid;

	private String goodsId;
	
	private String catalogId;
	
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public Long getCartid() {
		return cartid;
	}

	public void setCartid(Long cartid) {
		this.cartid = cartid;
	}

	
}
