package com.maywide.biz.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "prv_access_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccessLog extends PersistableEntity<Long> implements Persistable<Long> {
	private static final long serialVersionUID = -7186799593088159390L;

	private Long id = null;              // 日志ID
    private String clientIP = null;         // 客户端IP地址
    private String clientMAC = null;        // 客户端MAC地址
    private String clientIMEI = null;       // 客户端IMEI号
    private String opCode = null;           // 操作员工号
    private Long areaId = null;             // 所在地市
    @Temporal(TemporalType.TIMESTAMP)
    private Date callTime = null;           // 调用时间
    private String callMethod = null;       // 调用的接口名称
    private String request = null;          // 请求报文
    private String response = null;         // 响应报文
    private Long returnCode = null;         // 响应代码
    private String returnMsg = null;        // 响应提示
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime = null;            // 响应结束时间
    private String orderid =null;
    
    public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
    
    @Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="log_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="client_ip")
	public String getClientIP() {
		return clientIP;
	}
	
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	@Column(name="client_mac")
	public String getClientMAC() {
		return clientMAC;
	}
	public void setClientMAC(String clientMAC) {
		this.clientMAC = clientMAC;
	}
	@Column(name="client_imei")
	public String getClientIMEI() {
		return clientIMEI;
	}
	public void setClientIMEI(String clientIMEI) {
		this.clientIMEI = clientIMEI;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Date getCallTime() {
		return callTime;
	}
	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}
	public String getCallMethod() {
		return callMethod;
	}
	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Long getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(Long returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
