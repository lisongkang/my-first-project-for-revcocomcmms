package com.maywide.biz.inter.pojo.quesalesordercontent;

import java.io.Serializable;
import java.util.List;

public class QueSalesorderContentInterResp implements Serializable {
	private String mAcceptanceLabourNumber;
	private String mBusinessArea;
	private String mContactAddress;
	private String mCreateOrderTime;
	private String mCustomerID;
	private String mCustomerName;
	private String mIsSaleBill;
	private String mOperateDepartment;
	private String mOperator;
	private String mPhone;
	private String mSaleOrderId;
	private List<QueSalesorderContentBossChildResp> mOrderCommodityEntities;
	public String getmAcceptanceLabourNumber() {
		return mAcceptanceLabourNumber;
	}
	public void setmAcceptanceLabourNumber(String mAcceptanceLabourNumber) {
		this.mAcceptanceLabourNumber = mAcceptanceLabourNumber;
	}
	public String getmBusinessArea() {
		return mBusinessArea;
	}
	public void setmBusinessArea(String mBusinessArea) {
		this.mBusinessArea = mBusinessArea;
	}
	public String getmContactAddress() {
		return mContactAddress;
	}
	public void setmContactAddress(String mContactAddress) {
		this.mContactAddress = mContactAddress;
	}
	public String getmCreateOrderTime() {
		return mCreateOrderTime;
	}
	public void setmCreateOrderTime(String mCreateOrderTime) {
		this.mCreateOrderTime = mCreateOrderTime;
	}
	public String getmCustomerID() {
		return mCustomerID;
	}
	public void setmCustomerID(String mCustomerID) {
		this.mCustomerID = mCustomerID;
	}
	public String getmCustomerName() {
		return mCustomerName;
	}
	public void setmCustomerName(String mCustomerName) {
		this.mCustomerName = mCustomerName;
	}
	public String getmIsSaleBill() {
		return mIsSaleBill;
	}
	public void setmIsSaleBill(String mIsSaleBill) {
		this.mIsSaleBill = mIsSaleBill;
	}
	public String getmOperateDepartment() {
		return mOperateDepartment;
	}
	public void setmOperateDepartment(String mOperateDepartment) {
		this.mOperateDepartment = mOperateDepartment;
	}
	public String getmOperator() {
		return mOperator;
	}
	public void setmOperator(String mOperator) {
		this.mOperator = mOperator;
	}
	public String getmPhone() {
		return mPhone;
	}
	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	public String getmSaleOrderId() {
		return mSaleOrderId;
	}
	public void setmSaleOrderId(String mSaleOrderId) {
		this.mSaleOrderId = mSaleOrderId;
	}
	public List<QueSalesorderContentBossChildResp> getmOrderCommodityEntities() {
		return mOrderCommodityEntities;
	}
	public void setmOrderCommodityEntities(
			List<QueSalesorderContentBossChildResp> mOrderCommodityEntities) {
		this.mOrderCommodityEntities = mOrderCommodityEntities;
	}
}
