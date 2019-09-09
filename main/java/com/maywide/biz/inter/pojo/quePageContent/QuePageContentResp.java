package com.maywide.biz.inter.pojo.quePageContent;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class QuePageContentResp {

	private String mCustomerName;
	
	private String mCustomerNumber;
	
	private String mTransactionBusiness;
	
	private String mTransactionTime;
	
	private String mIdType;
	
	private String mIdNumber;
	
	private String mPhone;
	
	private String mSerialNumber;
	
	private String mContactAddress;
	
	private String mInstallType;
	
	private String mInstallTime;
	
	private String mInstallContact;
	
	private String mOperator;
	
	private String mOperateDepartment;
	
	private List<ContentEntity> mOrderCommodityEntities;
	
	private String mPublishNum;
	
	private String mBusinessType;
	
	private String mCustomerID;
	
	private String mAcceptanceLabourNumber;
	
	private String mRegionalCode;
	
	private String deptId;
	
	private String businessCode;
	
	private String mBusinessCode;
	
	private String mIsSaleBill = "0";
	
	
	public String getmIsSaleBill() {
		return mIsSaleBill;
	}

	public void setmIsSaleBill(String mIsSaleBill) {
		this.mIsSaleBill = mIsSaleBill;
	}

	public String getmCustomerName() {
		return mCustomerName;
	}

	public void setmCustomerName(String mCustomerName) {
		this.mCustomerName = mCustomerName;
	}

	public String getmCustomerNumber() {
		return mCustomerNumber;
	}

	public void setmCustomerNumber(String mCustomerNumber) {
		this.mCustomerNumber = mCustomerNumber;
	}


	public String getmIdType() {
		return mIdType;
	}

	public void setmIdType(String mIdType) {
		this.mIdType = mIdType;
	}

	public String getmIdNumber() {
		return mIdNumber;
	}

	public void setmIdNumber(String mIdNumber) {
		this.mIdNumber = mIdNumber;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getmSerialNumber() {
		return mSerialNumber;
	}

	public void setmSerialNumber(String mSerialNumber) {
		this.mSerialNumber = mSerialNumber;
	}

	public String getmContactAddress() {
		return mContactAddress;
	}

	public void setmContactAddress(String mContactAddress) {
		this.mContactAddress = mContactAddress;
	}

	public String getmInstallType() {
		return mInstallType;
	}

	public void setmInstallType(String mInstallType) {
		this.mInstallType = mInstallType;
	}

	public String getmInstallTime() {
		return mInstallTime;
	}

	public void setmInstallTime(String mInstallTime) {
		this.mInstallTime = mInstallTime;
	}

	public String getmInstallContact() {
		return mInstallContact;
	}

	public void setmInstallContact(String mInstallContact) {
		this.mInstallContact = mInstallContact;
	}

	public String getmOperator() {
		return mOperator;
	}

	public void setmOperator(String mOperator) {
		this.mOperator = mOperator;
	}

	public String getmOperateDepartment() {
		return mOperateDepartment;
	}

	public void setmOperateDepartment(String mOperateDepartment) {
		this.mOperateDepartment = mOperateDepartment;
	}

	public List<ContentEntity> getmOrderCommodityEntities() {
		return mOrderCommodityEntities;
	}

	public void setmOrderCommodityEntities(List<ContentEntity> mOrderCommodityEntities) {
		this.mOrderCommodityEntities = mOrderCommodityEntities;
	}

	public String getmTransactionBusiness() {
		return mTransactionBusiness;
	}

	public void setmTransactionBusiness(String mTransactionBusiness) {
		this.mTransactionBusiness = mTransactionBusiness;
	}

	public String getmTransactionTime() {
		return mTransactionTime;
	}

	public void setmTransactionTime(String mTransactionTime) {
		this.mTransactionTime = mTransactionTime;
	}

	public String getmPublishNum() {
		return mPublishNum;
	}

	public void setmPublishNum(String mPublishNum) {
		this.mPublishNum = mPublishNum;
	}

	public String getmBusinessType() {
		return mBusinessType;
	}

	public void setmBusinessType(String mBusinessType) {
		this.mBusinessType = mBusinessType;
	}

	public String getmCustomerID() {
		return mCustomerID;
	}

	public void setmCustomerID(String mCustomerID) {
		this.mCustomerID = mCustomerID;
	}

	public String getmAcceptanceLabourNumber() {
		return mAcceptanceLabourNumber;
	}

	public void setmAcceptanceLabourNumber(String mAcceptanceLabourNumber) {
		this.mAcceptanceLabourNumber = mAcceptanceLabourNumber;
	}

	public String getmRegionalCode() {
		return mRegionalCode;
	}

	public void setmRegionalCode(String mRegionalCode) {
		this.mRegionalCode = mRegionalCode;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getmBusinessCode() {
		if(StringUtils.isBlank(mBusinessCode)){
			this.mBusinessCode = businessCode;
		}
		return mBusinessCode;
	}

	public void setmBusinessCode(String mBusinessCode) {
		this.mBusinessCode = mBusinessCode;
		if(StringUtils.isBlank(mBusinessCode)){
			this.mBusinessCode = businessCode;
		}
	}
	
	
	
}
