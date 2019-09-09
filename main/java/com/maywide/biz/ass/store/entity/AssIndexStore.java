package com.maywide.biz.ass.store.entity;

import java.util.Date;
import java.util.List;

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
@Table(name = "ASS_INDEX_STORE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssIndexStore extends PersistableEntity<Long> implements Persistable<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8943450486747769072L;
	
	@MetaData(value = "考核指标id")
    @EntityAutoCode
    private Long id;
	
    @MetaData(value = "分公司")
    @EntityAutoCode(order = 1, listShow = true)
	private String city;
    
    private String cityName;
    
    @MetaData(value = "考核内容")
    @EntityAutoCode(order = 1, listShow = true)
	private String asscontent;
    
    @MetaData(value = "考核参数")
    @EntityAutoCode(order = 1, listShow = true)
	private String assparam;
    
    private String assparamName;
    
    private String objid;

    private List<String> assobjids;
    
    @MetaData(value = "考核对象")
    @EntityAutoCode(order = 1, listShow = true)
	private String assobj;
    
    @MetaData(value = "考核周期单位")
    @EntityAutoCode(order = 1, listShow = true)
	private String unit;
    
    private String unitname;
    
    @MetaData(value = "任务单位")
    @EntityAutoCode(order = 1, listShow = true)
	private String taskunit; // 任务单位 0:数量，1：金额
    
    private String taskunitName;
    
    @MetaData(value = "任务总数值")
    @EntityAutoCode(order = 1, listShow = true)
	private Double totalnum;
    
    @MetaData(value = "失效时间")
    @EntityAutoCode(order = 1, listShow = true)
	private Date expdate;
    
    @MetaData(value = "制定部门")
    @EntityAutoCode(order = 1, listShow = true)
	private Long depart;
    
    private String deptName;
    
    @MetaData(value = "操作员")
    @EntityAutoCode(order = 1, listShow = true)
	private Long operator;
    
    private String opername;
    
    @MetaData(value = "操作时间")
    @EntityAutoCode(order = 1, listShow = true)
	private Date opdate;

    private Long gridcount;
    
    private String grids;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "assid", unique = true, length = 16)
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
	
	@Column(name = "city", nullable = true, length = 2)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Transient
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "asscontent", nullable = true, length = 1024)
	public String getAsscontent() {
		return asscontent;
	}

	public void setAsscontent(String asscontent) {
		this.asscontent = asscontent;
	}

	@Column(name = "assparam", nullable = true, length = 10)
	public String getAssparam() {
		return assparam;
	}

	public void setAssparam(String assparam) {
		this.assparam = assparam;
	}

	@Transient
	public String getAssobj() {
		String assobjString = "";
		if ("BUSINCOME".equals(getAssparam())){
//			String objid = getObjid();
//			if (objid!=null && !"".equals(objid)){
//				String[] s = objid.split(",");
//				for (int i = 0; i < s.length; i++) {
//					if ("1".equals(s[i])) {
//						assobjString += "基础业务,";
//					} else if ("2".equals(s[i])) {
//						assobjString += "高清互动业务,";
//					} else if ("3".equals(s[i])) {
//						assobjString += "宽带业务,";
//					}
//				}
//				
//				if (!"".equals(assobjString)){
//					assobjString = assobjString.substring(0,assobjString.length()-1);
//				}
//			}
			
			if ("1".equals(objid)) {
				assobjString = "基础业务";
			} else if ("2".equals(objid)) {
				assobjString = "高清互动业务";
			} else if ("3".equals(objid)) {
				assobjString = "宽带业务";
			}
		} else if ("NEWSERV".equals(getAssparam())){
			if ("1".equals(objid)) {
				assobjString = "数字电视主机有效用户数";
			} else if ("2".equals(objid)) {
				assobjString = "高清双向机顶盒到达数";
			} else if ("3".equals(objid)) {
				assobjString = "宽带业务有效用户数";
			}
		} else {
			assobjString = assobj;
		}
		return assobjString;
	}

	public void setAssobj(String assobj) {
		this.assobj = assobj;
	}

	@Column(name = "unit", nullable = true)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "taskunit", nullable = true, length = 2)
	public String getTaskunit() {
		return taskunit;
	}

	public void setTaskunit(String taskunit) {
		this.taskunit = taskunit;
	}

	@Column(name = "totalnum", nullable = true)
	public Double getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(Double totalnum) {
		this.totalnum = totalnum;
	}

	@Column(name = "expdate", nullable = true)
	public Date getExpdate() {
		return expdate;
	}

	public void setExpdate(Date expdate) {
		this.expdate = expdate;
	}

	@Column(name = "depart", nullable = true)
	public Long getDepart() {
		return depart;
	}

	public void setDepart(Long depart) {
		this.depart = depart;
	}

	@Column(name = "operator", nullable = true)
	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Column(name = "opdate", nullable = true)
	public Date getOpdate() {
		return opdate;
	}

	public void setOpdate(Date opdate) {
		this.opdate = opdate;
	}

	@Transient
	public String getAssparamName() {
		return assparamName;
	}

	public void setAssparamName(String assparamName) {
		this.assparamName = assparamName;
	}

	@Transient
	public String getObjid() {
//		String objidString = "";
//		
//		if (getId()==null && "BUSINCOME".equals(getAssparam())){
//			List<String> list = getAssobjids();
//			if (list != null) {
//				for (String string : list) {
//					objidString += string +",";
//				}
//				
//				if (!"".equals(objidString)){
//					objidString = objidString.substring(0,objidString.length()-1);
//				}
//			}
//		} else {
//			objidString = objid;
//		}
//		
//		return objidString;
		
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	@Transient
	public String getTaskunitName() {
		return taskunitName;
	}

	public void setTaskunitName(String taskunitName) {
		this.taskunitName = taskunitName;
	}

	@Transient
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Transient
	public Long getGridcount() {
		return gridcount;
	}

	public void setGridcount(Long gridcount) {
		this.gridcount = gridcount;
	}

	@Transient
	public String getGrids() {
		return grids;
	}

	public void setGrids(String grids) {
		this.grids = grids;
	}

	@Transient
	public List<String> getAssobjids() {
		return assobjids;
	}
	
	public void setAssobjids(List<String> assobjids){
		this.assobjids = assobjids;
	}

	@Transient
	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	@Transient
	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}
	
	
}
