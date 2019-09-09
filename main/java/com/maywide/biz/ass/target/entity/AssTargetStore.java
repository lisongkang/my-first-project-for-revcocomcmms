package com.maywide.biz.ass.target.entity;

import java.text.SimpleDateFormat;
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
@Table(name = "ASS_TARGET_STORE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssTargetStore extends PersistableEntity<Long> implements Persistable<Long> {
	

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8943450486747769072L;
	
	@MetaData(value = "考核指标id")
    @EntityAutoCode
    private Long id;
	
	@MetaData(value = "指标库编码")
    @EntityAutoCode
	private String assCode;
	
	@MetaData(value = "分公司")
    @EntityAutoCode(order = 1, listShow = true)
	private String city;
    
	@MetaData(value = "指标库名")
    @EntityAutoCode(order = 1, listShow = true)
    private String name;
    
    @MetaData(value = "指标库内容")
    @EntityAutoCode(order = 1, listShow = true)
	private String asscontent;
    
    @MetaData(value = "取数字段")
    @EntityAutoCode(order = 1, listShow = true)
	private String fieldId;
    
    @MetaData(value = "取数SQL")
    @EntityAutoCode(order = 1, listShow = true)
	private String visql;
    
    @MetaData(value = "状态0启用1失效")
    @EntityAutoCode(order = 1, listShow = true)
	private Integer status;
    
    @MetaData(value = "过期时间")
    @EntityAutoCode(order = 1, listShow = true)
	private Date expDate;
    
    @MetaData(value = "创建时间")
    @EntityAutoCode(order = 1, listShow = true)
	private Date opttime;
    
    @MetaData(value = "创建人")
    @EntityAutoCode(order = 1, listShow = true)
	private Long operator;
    
    @MetaData(value = "统计周期")
    @EntityAutoCode(order = 1, listShow = true)
	private Integer assSttCycle;
    
    @MetaData(value = "是否删除")
    @EntityAutoCode(order = 1, listShow = true)
	private Integer isDel;
    
    
    /**
     * 非数据库字段
     */
    private String cityName; //地市名

    private Long tocityId;//地市指标下发id
    
    private String toPatchStatus; //下发状态
    
    private String assCity; //使用地市
    
    private String assCityName; //指标归属地市名
    
    
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ASSID", unique = true)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ASSCODE", nullable = false, length = 30)
	public String getAssCode() {
		return assCode;
	}

	public void setAssCode(String assCode) {
		this.assCode = assCode;
	}

	@Override
	@Transient
	public String getDisplay() {
		return "";
	}
	
	@Column(name = "CITY", nullable = true, length = 2)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "NAME", nullable = true, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ASSCONTENT", nullable = true, length = 1024)
	public String getAsscontent() {
		return asscontent;
	}

	public void setAsscontent(String asscontent) {
		this.asscontent = asscontent;
	}
	
	@Column(name = "FIELDID", nullable = true, length = 32)
	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	@Column(name = "STATUS", nullable = true, length = 2)
	public Integer getStatus() {
		return status;
	}

	@Column(name = "VISQL", nullable = true, length = 256)
	public String getVisql() {
		return visql;
	}

	public void setVisql(String visql) {
		this.visql = visql;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "EXPDATE", nullable = true)
	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	
	
	@Column(name = "OPTTIME", nullable = true)
	public Date getOpttime() {
		return opttime;
	}

	public void setOpttime(Date opttime) {
		this.opttime = opttime;
	}

	@Column(name = "OPERATOR", nullable = true)
	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Column(name = "ASS_STT_CYCLE", nullable = true)
	public Integer getAssSttCycle() {
		return assSttCycle;
	}

	public void setAssSttCycle(Integer assSttCycle) {
		this.assSttCycle = assSttCycle;
	}

	@Column(name = "ISDEL", nullable = true)
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Transient
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Transient
	public Long getTocityId() {
		return tocityId;
	}

	public void setTocityId(Long tocityId) {
		this.tocityId = tocityId;
	}

	
	@Transient
	public String getToPatchStatus() {
		return toPatchStatus;
	}

	public void setToPatchStatus(String toPatchStatus) {
		this.toPatchStatus = toPatchStatus;
	}
	
	
	@Transient
	public String getAssCity() {
		return assCity;
	}

	public void setAssCity(String assCity) {
		this.assCity = assCity;
	}

	@Transient
	public String getAssCityName() {
		return assCityName;
	}

	public void setAssCityName(String assCityName) {
		this.assCityName = assCityName;
	}

	/**
	 * 按时间生成一个指标库编码
	 * @return
	 */
	public static String genStoreCode(){
		SimpleDateFormat sdf=new SimpleDateFormat();
		sdf.applyPattern("yyyyMMddhhmmss");
		return sdf.format(new Date());
	}
}
