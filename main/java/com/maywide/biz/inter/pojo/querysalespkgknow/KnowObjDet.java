package com.maywide.biz.inter.pojo.querysalespkgknow;

public class KnowObjDet implements java.io.Serializable {
	private KnowPrdBO prd;//知识库产品明细
	private KnowSalespkgBO pkg;//知识库营销方案明细
	
	private KnowGoodsBO goods;//商品明细
	
	public KnowGoodsBO getGoods() {
		return goods;
	}
	public void setGoods(KnowGoodsBO goods) {
		this.goods = goods;
	}
	public KnowPrdBO getPrd() {
		return prd;
	}
	public void setPrd(KnowPrdBO prd) {
		this.prd = prd;
	}
	public KnowSalespkgBO getPkg() {
		return pkg;
	}
	public void setPkg(KnowSalespkgBO pkg) {
		this.pkg = pkg;
	}
	
	
}
