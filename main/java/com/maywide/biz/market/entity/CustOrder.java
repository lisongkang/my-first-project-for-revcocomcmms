package com.maywide.biz.market.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.biz.prv.entity.PrvOperinfo;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name = "BIZ_CUSTORDER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustOrder extends PersistableEntity<Long> implements Persistable<Long> {
    @MetaData(value = "订单id")
    @EntityAutoCode
    private Long id;

    @MetaData(value = "订单编码")
    @EntityAutoCode(order = 1, listShow = true)
    private String ordercode;

    @MetaData(value = "同步方式")
    @EntityAutoCode
    private String syncmode;

    @MetaData(value = "boss受理号")
    @EntityAutoCode(order = 20, listShow = true)
    private String bossserialno;

    @MetaData(value = "订单状态")
    @EntityAutoCode(order = 20, listShow = true)
    private String orderstatus;

    @MetaData(value = "取消操时间")
    @EntityAutoCode(order = 20, listShow = true)
    // @Temporal(TemporalType.TIMESTAMP)
    private Date canceltime;
    
    @MetaData(value = "取消操作员")
    @EntityAutoCode(order = 20, listShow = true)
    private Long lockoper;
    
    @MetaData(value = "客户id")
    @EntityAutoCode(order = 20)
    private Long custid;
    
    @MetaData(value = "客户姓名")
    @EntityAutoCode(order = 20, listShow = true)
    private String name;
    
    @MetaData(value = "业务操作")
    @EntityAutoCode(order = 20, listShow = true)
    private String opcode;
    
    @MetaData(value = "操作时间")
    @EntityAutoCode(order = 20, listShow = true)
    // @Temporal(TemporalType.TIMESTAMP)
    private Date optime;
    
    @MetaData(value = "操作员")
    @EntityAutoCode(order = 20, listShow = true)
    private Long operator;
    
    @MetaData(value = "操作员部门")
    @EntityAutoCode(order = 20, listShow = true)
    private Long oprdep;
    
    @MetaData(value = "业务区")
    @EntityAutoCode(order = 20, listShow = true)
    private Long areaid;
    
    @MetaData(value = "网格")
    @EntityAutoCode(order = 20, listShow = true)
    private Long gridid;
    
    @MetaData(value = "业务说明")
    @EntityAutoCode(order = 20, listShow = true)
    private String descrip;
    
    @MetaData(value = "地址")
    @EntityAutoCode(order = 20, listShow = true)
    private String addr;
    
    @MetaData(value = "城市")
    @EntityAutoCode(order = 20, listShow = true)
    private String city;
    
    @MetaData(value = "接收验证码的手机号")
    @EntityAutoCode(order = 20, listShow = true)
    private String verifyphone;
    
    @MetaData(value = "额外业务记录内容")
    @EntityAutoCode(order = 20, listShow = true)
    private String  businessdescipt;

    @MetaData(value = "屏幕截图")
    @EntityAutoCode(order = 20, listShow = false)
    private String  screenshots;
    
    @MetaData(value="是否在网格客户端使用无纸化打印受理单")
    @EntityAutoCode(order = 20, listShow = false)
    private String printed;

    @MetaData(value="是否打印了发票")
    @EntityAutoCode(order = 20, listShow = false)
    private String printedinv;
    
    @MetaData(value="工单设备跟套餐信息")
    @EntityAutoCode(listShow = false)
    private String bizmemo;
    
    private BizPortalOrder portalOrder = new BizPortalOrder();

    @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "orderid", unique = true, length = 16)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getSyncmode() {
        return syncmode;
    }

    public void setSyncmode(String syncmode) {
        this.syncmode = syncmode;
    }

    public String getBossserialno() {
        return bossserialno;
    }

    public void setBossserialno(String bossserialno) {
        this.bossserialno = bossserialno;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Date getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(Date canceltime) {
        this.canceltime = canceltime;
    }

    public Long getLockoper() {
        return lockoper;
    }

    public void setLockoper(Long lockoper) {
        this.lockoper = lockoper;
    }

    public Long getCustid() {
        return custid;
    }

    public void setCustid(Long custid) {
        this.custid = custid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Long getOprdep() {
        return oprdep;
    }

    public void setOprdep(Long oprdep) {
        this.oprdep = oprdep;
    }

    public Long getAreaid() {
        return areaid;
    }

    public void setAreaid(Long areaid) {
        this.areaid = areaid;
    }

    public Long getGridid() {
        return gridid;
    }

    public void setGridid(Long gridid) {
        this.gridid = gridid;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}

	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    public BizPortalOrder getPortalOrder() {
        return portalOrder;
    }

    public void setPortalOrder(BizPortalOrder portalOrder) {
        this.portalOrder = portalOrder;
    }
    
    @Column(name="BUSINESSDESCPIT")
    public String getBusinessdescipt() {
		return businessdescipt;
	}

	public void setBusinessdescipt(String businessdescipt) {
		this.businessdescipt = businessdescipt;
	}

	public String getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(String screenshots) {
		this.screenshots = screenshots;
	}

	
	public String getPrinted() {
		return printed;
	}

	public void setPrinted(String printed) {
		this.printed = printed;
	}

	public String getPrintedinv() {
		return printedinv;
	}

	public void setPrintedinv(String printedinv) {
		this.printedinv = printedinv;
	}
	
	

	public String getBizmemo() {
		return bizmemo;
	}

	public void setBizmemo(String bizmemo) {
		this.bizmemo = bizmemo;
	}

	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
}
