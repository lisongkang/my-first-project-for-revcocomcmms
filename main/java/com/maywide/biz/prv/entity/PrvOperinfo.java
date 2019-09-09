package com.maywide.biz.prv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "PRV_OPERINFO")
@SuppressWarnings("serial")
public class PrvOperinfo extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	private String sex;
	private String idcard;
	private String addr;
	private String postcode;
	private String type;
	private String phone;
	private String callno;
	private String memo;
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }

	@Id
	@GeneratedValue(generator = "ud")
	@GenericGenerator(name = "ud", strategy = "assigned")
	@Column(name = "OPERID", nullable = false, unique = true, insertable = true, updatable = true, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SEX", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "IDCARD", nullable = true, unique = false, insertable = true, updatable = true, length = 40)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "ADDR", nullable = true, unique = false, insertable = true, updatable = true, length = 255)
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "POSTCODE", nullable = true, unique = false, insertable = true, updatable = true, length = 6)
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "TYPE", nullable = true, unique = false, insertable = true, updatable = true, length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "PHONE", nullable = true, unique = false, insertable = true, updatable = true, length = 11)
	@Pattern(regexp = "^1\\d{10}$", message = "请输入正确的手机号")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "CALLNO", nullable = true, unique = false, insertable = true, updatable = true, length = 11)
	public String getCallno() {
		return callno;
	}

	public void setCallno(String callno) {
		this.callno = callno;
	}

	@Column(name = "MEMO", nullable = true, unique = false, insertable = true, updatable = true, length = 255)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}	
}
