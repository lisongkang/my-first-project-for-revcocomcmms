package com.maywide.biz.inter.pojo.wflqueequipdetinfo;

public class WflQueEquipdetinfoInterResp extends WflQueEquipdetinfoBossResp {

	public WflQueEquipdetinfoInterResp() {
		
	}
	
	public WflQueEquipdetinfoInterResp (WflQueEquipdetinfoBossResp resp) {
		this.setCurrentPage(resp.getCurrentPage());
		this.setPagesize(resp.getPagesize());
		this.setQueEquipDetBo(resp.getQueEquipDetBo());
		this.setTotalRecords(resp.getTotalRecords());
	}
	
	
    private int orderNum;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
    
    

    
}
