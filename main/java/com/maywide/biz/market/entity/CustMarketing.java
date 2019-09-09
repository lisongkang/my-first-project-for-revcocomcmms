package com.maywide.biz.market.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name = "BIZ_CUST_MARKETING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustMarketing extends PersistableEntity<Long> implements Persistable<Long> {
	@MetaData(value = "ID编号")
	private Long id;
	
	@MetaData(value = "营销批次号")
	private String batchno;
	
	@MetaData(value = "客户编号")
	@EntityAutoCode(order = 1, listShow = true)
	private Long custid;
	
	@MetaData(value = "客户名称")
	@EntityAutoCode(order = 2, listShow = true)
	private String name;
	
	@MetaData(value = "联系电话")
	private String linkphone;
	
	@MetaData(value = "住宅地址编号")
	@EntityAutoCode(order = 3, listShow = false)
	private Long houseid;
	
	@MetaData(value = "住宅地址")
	@EntityAutoCode(order = 3, listShow = true)
	private String whladdr;
	
	@MetaData(value = "业务区编号")
	private Long areaid;
	
	@MetaData(value = "片区编号")
	@EntityAutoCode(order = 4, listShow = false)
	private Long ptachid;
	
	@MetaData(value = "所属片区")
	@EntityAutoCode(order = 4, listShow = true)
	private String patchname;
	
	@MetaData(value = "处理状态")
	@EntityAutoCode(order = 5, listShow = true)
	private String dealstatus;
	
	@MetaData(value = "网格人员")
	@EntityAutoCode(order = 6, listShow = false)
	private Long areamger;
	
	@MetaData(value = "网格人员")
	@EntityAutoCode(order = 6, listShow = true)
	private String areamgername;
	
	@MetaData(value = "录入部门")
	@EntityAutoCode(order = 7, listShow = true)
	private Long deptid;
	
	@MetaData(value = "营销次数")
	@EntityAutoCode(order = 8, listShow = true)
	private Long alnums;
	
	@MetaData(value = "营销结果")
	@EntityAutoCode(order = 9, listShow = true)
	private String result;
	
	@MetaData(value = "结果描述")
	@EntityAutoCode(order = 10, listShow = false)
	private String resultexp;
	
	@MetaData(value = "优先级")
	private String pri;
	
	@MetaData(value = "营销套餐标识")
	private Long knowid;
	
	@MetaData(value = "经理联系电话")
	private String mgerphone;
	
	@MetaData(value = "录入时间")
	private Date appdate;
	
	@MetaData(value = "录入操作员")
	private Long operid;
	
	@MetaData(value = "地市")
	private String city;
	
	private String deptname;
	private String knowname;
	private String sappdate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "markid", unique = true, length = 16)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@Transient
	public String getDisplay() {
		return "";
	}
	
	@Column(name = "batchno", nullable = true, length = 30)
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@Column(name = "custid", nullable = true, length = 20)
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
	
	@Column(name = "name", nullable = true, length = 255)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "linkphone", nullable = true, length = 30)
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	
	@Column(name = "houseid", nullable = true, length = 20)
	public Long getHouseid() {
		return houseid;
	}
	public void setHouseid(Long houseid) {
		this.houseid = houseid;
	}
	
	@Column(name = "whladdr", nullable = true, length = 255)
	public String getWhladdr() {
		return whladdr;
	}
	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}
	
	@Column(name = "areaid", nullable = true, length = 20)
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	
	@Column(name = "ptachid", nullable = true, length = 20)
	public Long getPtachid() {
		return ptachid;
	}
	public void setPtachid(Long ptachid) {
		this.ptachid = ptachid;
	}
	
	@Column(name = "pri", nullable = true, length = 8)
	public String getPri() {
		return pri;
	}
	public void setPri(String pri) {
		this.pri = pri;
	}
	
	@Column(name = "knowid", nullable = true, length = 20)
	public Long getKnowid() {
		return knowid;
	}
	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}
	
	@Column(name = "areamger", nullable = true, length = 20)
	public Long getAreamger() {
		return areamger;
	}
	public void setAreamger(Long areamger) {
		this.areamger = areamger;
	}
	
	@Column(name = "mgerphone", nullable = true, length = 30)
	public String getMgerphone() {
		return mgerphone;
	}
	public void setMgerphone(String mgerphone) {
		this.mgerphone = mgerphone;
	}
	
	@Column(name = "appdate", nullable = true)
	public Date getAppdate() {
		return appdate;
	}
	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}
	
	@Column(name = "operid", nullable = true, length = 20)
	public Long getOperid() {
		return operid;
	}
	public void setOperid(Long operid) {
		this.operid = operid;
	}
	
	@Column(name = "dealstatus", nullable = true, length = 2)
	public String getDealstatus() {
		return dealstatus;
	}
	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}
	
	@Column(name = "alnums", nullable = true, length = 20)
	public Long getAlnums() {
		return alnums;
	}
	public void setAlnums(Long alnums) {
		this.alnums = alnums;
	}
	
	@Column(name = "result", nullable = true, length = 30)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	@Column(name = "resultexp", nullable = true, length = 512)
	public String getResultexp() {
		return resultexp;
	}
	public void setResultexp(String resultexp) {
		this.resultexp = resultexp;
	}
	
	@Column(name = "city", nullable = true, length = 2)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Transient
	public String getAreamgername() {
		return areamgername;
	}
	public void setAreamgername(String areamgername) {
		this.areamgername = areamgername;
	}
	
	@Transient
	public String getKnowname() {
		return knowname;
	}
	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}
	
	@Transient
	public String getPatchname() {
		return patchname;
	}
	public void setPatchname(String patchname) {
		this.patchname = patchname;
	}
	
	@Transient
	public String getSappdate() {
		return sappdate;
	}
	public void setSappdate(String sappdate) {
		this.sappdate = sappdate;
	}
	
	@Column(name = "deptid", nullable = true, length = 20)
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	@Transient
	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
}
