package com.maywide.biz.inter.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "BIZ_APPLICATION")
public class BizApplication extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id; // 序列号
	private String proname;//项目名称
	private String pronum;//项目编号
	private String buildaddr;//建设地址
	private String buildunit;//建设单位
	private String builddetp;//建设部门
	private String prodetail;//项目状况
	private String needdept;//需求部门
	private String appopinion;//审批意见
	private String prostatus;//项目状态
	private String fileids;//附件ID
	private String constcost ;//物料费用
	private Double totalprice;//总费用
	private String procost;//施工费用
	private String creator;//创建人
	private Long creatorid;//创建人id
	private String approveor;//审批人
	private Long approveid;//审批人id
	private Long editid;//修改人id
	private String planstarttime;//计划开工日期
	private String planendtime;//计划完工时间
	private String starttime;//开工日期
	private String endtime;//完工日期
	private String edittime;//修改时间
	private String applicationtime;//申请时间
	private String constructors;//施工人集合
	private String accopinion;//验收意见
	private String accepterid;//验收人id
	private String acceptername;//验收人
	private String city;//所属地市
	private String operation;//是否是审批
	private String applyrecallopinion;//申请人撤回原因
	private String applyacceptance;//验收申请说明
	private String buildunitareaid;//建设单位编号(业务区编码)
	private String settlementopinion;//分管领导结算意见
	private String permacceptopinion;//允许验收意见
	@Override
	@Transient
	public String getDisplay() {
		return null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROID", unique = true, length = 16)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermacceptopinion() {
		return permacceptopinion;
	}

	public void setPermacceptopinion(String permacceptopinion) {
		this.permacceptopinion = permacceptopinion;
	}

	public String getSettlementopinion() {
		return settlementopinion;
	}

	public void setSettlementopinion(String settlementopinion) {
		this.settlementopinion = settlementopinion;
	}

	public String getBuildunitareaid() {
		return buildunitareaid;
	}

	public void setBuildunitareaid(String buildunitareaid) {
		this.buildunitareaid = buildunitareaid;
	}

	public String getConstcost() {
		return constcost;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setConstcost(String constcost) {
		this.constcost = constcost;
	}

	public String getApplicationtime() {
		return applicationtime;
	}

	public void setApplicationtime(String applicationtime) {
		this.applicationtime = applicationtime;
	}

	public String getConstructors() {
		return constructors;
	}

	public void setConstructors(String constructors) {
		this.constructors = constructors;
	}

	public String getAccopinion() {
		return accopinion;
	}

	public void setAccopinion(String accopinion) {
		this.accopinion = accopinion;
	}

	public String getAccepterid() {
		return accepterid;
	}

	public void setAccepterid(String accepterid) {
		this.accepterid = accepterid;
	}

	public String getAcceptername() {
		return acceptername;
	}

	public void setAcceptername(String acceptername) {
		this.acceptername = acceptername;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getApproveor() {
		return approveor;
	}

	public void setApproveor(String approveor) {
		this.approveor = approveor;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getPronum() {
		return pronum;
	}

	public void setPronum(String pronum) {
		this.pronum = pronum;
	}

	public String getBuildaddr() {
		return buildaddr;
	}

	public void setBuildaddr(String buildaddr) {
		this.buildaddr = buildaddr;
	}

	public String getBuildunit() {
		return buildunit;
	}

	public void setBuildunit(String buildunit) {
		this.buildunit = buildunit;
	}

	public String getBuilddetp() {
		return builddetp;
	}

	public void setBuilddetp(String builddetp) {
		this.builddetp = builddetp;
	}

	public String getProdetail() {
		return prodetail;
	}

	public void setProdetail(String prodetail) {
		this.prodetail = prodetail;
	}

	public String getNeeddept() {
		return needdept;
	}

	public void setNeeddept(String needdept) {
		this.needdept = needdept;
	}

	public String getAppopinion() {
		return appopinion;
	}

	public void setAppopinion(String appopinion) {
		this.appopinion = appopinion;
	}

	public String getProstatus() {
		return prostatus;
	}

	public void setProstatus(String prostatus) {
		this.prostatus = prostatus;
	}

	public String getFileids() {
		return fileids;
	}

	public void setFileids(String fileids) {
		this.fileids = fileids;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public String getProcost() {
		return procost;
	}

	public void setProcost(String procost) {
		this.procost = procost;
	}

	public Long getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(Long creatorid) {
		this.creatorid = creatorid;
	}

	public Long getApproveid() {
		return approveid;
	}

	public void setApproveid(Long approveid) {
		this.approveid = approveid;
	}

	public Long getEditid() {
		return editid;
	}

	public void setEditid(Long editid) {
		this.editid = editid;
	}

	public String getPlanstarttime() {
		return planstarttime;
	}

	public void setPlanstarttime(String planstarttime) {
		this.planstarttime = planstarttime;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getPlanendtime() {
		return planendtime;
	}

	public void setPlanendtime(String planendtime) {
		this.planendtime = planendtime;
	}

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

	public String getApplyrecallopinion() {
		return applyrecallopinion;
	}

	public void setApplyrecallopinion(String applyrecallopinion) {
		this.applyrecallopinion = applyrecallopinion;
	}

	public String getApplyacceptance() {
		return applyacceptance;
	}

	public void setApplyacceptance(String applyacceptance) {
		this.applyacceptance = applyacceptance;
	}
}
