package com.maywide.biz.inter.pojo.quecustorder;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueCustorderInterReq extends BaseApiRequest implements
		java.io.Serializable {
	private String pagesize;//分页大小
	private String currentPage;//请求页
	private String city;// 所属分公司
	private String gridid;// 网格id
	private String oprdep;// 操作员部门id
	private String operator;// 操作员id
	private String opcode;// 操作代码
	private String gt_optime;// 操作时间>，格式：yyyy-mm-dd hh24:mi:ss
	private String lt_optime;// 操作时间<=，格式：yyyy-mm-dd hh24:mi:ss
	private String custid;// 客户id
	private String custorderid;// 客户订单id
	private String paystatus;// 订单支付状态
	private String orderstatus;// 订单状态
	private String custname;// 客户姓名
	private Boolean detail = false;
	private List<SortConditionsBO> sortConditions;// 排序条件
	private String system;

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGridid() {
		return gridid;
	}

	public void setGridid(String gridid) {
		this.gridid = gridid;
	}

	public String getOprdep() {
		return oprdep;
	}

	public void setOprdep(String oprdep) {
		this.oprdep = oprdep;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getGt_optime() {
		return gt_optime;
	}

	public void setGt_optime(String gt_optime) {
		this.gt_optime = gt_optime;
	}

	public String getLt_optime() {
		return lt_optime;
	}

	public void setLt_optime(String lt_optime) {
		this.lt_optime = lt_optime;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public List<SortConditionsBO> getSortConditions() {
		return sortConditions;
	}

	public void setSortConditions(List<SortConditionsBO> sortConditions) {
		this.sortConditions = sortConditions;
	}

	public Boolean getDetail() {
		return detail;
	}

	public void setDetail(Boolean detail) {
		this.detail = detail;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	
	
}
