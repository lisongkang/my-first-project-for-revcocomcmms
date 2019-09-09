package com.maywide.biz.iomproj.pojo;

import org.apache.commons.lang3.StringUtils;

public class QueImoprojListReq {

	private String CREATE_DATE;
	
	private String ORDER_STATE;
	
	private String ACC_NBR;
	
	private String ORDER_CLASS;
	
	private String NETGRID_CODE;

	public String getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public String getORDER_STATE() {
		return ORDER_STATE;
	}

	public void setORDER_STATE(String oRDER_STATE) {
		ORDER_STATE = oRDER_STATE;
	}

	public String getACC_NBR() {
		return ACC_NBR;
	}

	public void setACC_NBR(String aCC_NBR) {
		ACC_NBR = aCC_NBR;
	}

	public String getORDER_CLASS() {
		return ORDER_CLASS;
	}

	public void setORDER_CLASS(String oRDER_CLASS) {
		ORDER_CLASS = oRDER_CLASS;
	}

	public String getNETGRID_CODE() {
		return NETGRID_CODE;
	}

	public void setNETGRID_CODE(String nETGRID_CODE) {
		NETGRID_CODE = nETGRID_CODE;
	}

	public QueImoprojListReq() {
		super();
	}
	
	public QueImoprojListReq(QueImoprojReq req){
		super();
		if(StringUtils.isNotBlank(req.getAccNbr())){
			setACC_NBR(req.getAccNbr());	
		}
		if(StringUtils.isNotBlank(req.getCreateDate())){
			setCREATE_DATE(req.getCreateDate());	
		}
		if(StringUtils.isNotBlank(req.getNetgridCode())){
			setNETGRID_CODE(req.getNetgridCode());	
		}
		if(StringUtils.isNotBlank(req.getOrderClass())){
			setORDER_CLASS(req.getOrderClass());
		}
		if(StringUtils.isNotBlank(req.getOrderState())){
			setORDER_STATE(req.getOrderState());
		}
	}
	
}
