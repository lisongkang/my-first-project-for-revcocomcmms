package com.maywide.biz.sys.entity;

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

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name ="SYS_CUST_VISIT_RULE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysCustVisitRule extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	
	private String city;
	
	private String area = "*";
	
	private String opcode;
	
	private Integer delayType;
	
	private Integer delayValue;
	
	private String sendMethod;
	
	private Integer contentTemplateId;
	
	private Integer mobileIndex;
	
	private Integer maxTimes;
	
	
	@Id
	@Column(name="rule_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public Long getId() {
		return id;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public Integer getDelayType() {
		return delayType;
	}

	public void setDelayType(Integer delayType) {
		this.delayType = delayType;
	}

	public Integer getDelayValue() {
		return delayValue;
	}

	public void setDelayValue(Integer delayValue) {
		this.delayValue = delayValue;
	}

	public String getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}

	public Integer getContentTemplateId() {
		return contentTemplateId;
	}

	public void setContentTemplateId(Integer contentTemplateId) {
		this.contentTemplateId = contentTemplateId;
	}

	public Integer getMobileIndex() {
		return mobileIndex;
	}

	public void setMobileIndex(Integer mobileIndex) {
		this.mobileIndex = mobileIndex;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Integer getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}

	@Transient
	@Override
	public String getDisplay() {
		return null;
	}

}
