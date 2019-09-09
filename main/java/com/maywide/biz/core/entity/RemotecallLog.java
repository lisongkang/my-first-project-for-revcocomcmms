package com.maywide.biz.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "prv_remotecall_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RemotecallLog implements Serializable {
    private static final long serialVersionUID = 8781512063141806631L;

    private Long id = null; // 日志id
    private Long orderid = null; // 客户订单id，相当于业务流水号
    @Temporal(TemporalType.TIMESTAMP)
    private Date calltime = null; // 调用时间
    private String requestid = null; // 请求流水号，如果没发送到远程服务端则不用记
    private String serviceurl = null;// 调用的服务的地址
    private String protocol = null;// 协议类型:http,socket等
    private String clientcode = null; // 客户端编码
    private String servicecode = null; // 调用的服务名称
    private String request = null; // 请求报文
    private String response = null; // 响应报文
    private String retcode = null; // 响应代码
    private String retmsg = null; // 响应提示
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime = null; // 响应结束时间
    private String respserialno = null; // 响应流水号
    private Long resporderid = null; // 响应订单号

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logid")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "orderid")
    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    @Column(name = "calltime")
    public Date getCalltime() {
        return calltime;
    }

    public void setCalltime(Date calltime) {
        this.calltime = calltime;
    }

    @Column(name = "requestid")
    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    @Column(name = "servicecode")
    public String getServicecode() {
        return servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    @Column(name = "request")
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Column(name = "response")
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Column(name = "retcode")
    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    @Column(name = "retmsg")
    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    @Column(name = "endtime")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Column(name = "respserialno")
    public String getRespserialno() {
        return respserialno;
    }

    public void setRespserialno(String respserialno) {
        this.respserialno = respserialno;
    }

    @Column(name = "resporderid")
    public Long getResporderid() {
        return resporderid;
    }

    public void setResporderid(Long resporderid) {
        this.resporderid = resporderid;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getServiceurl() {
        return serviceurl;
    }

    public void setServiceurl(String serviceurl) {
        this.serviceurl = serviceurl;
    }

    public String getClientcode() {
        return clientcode;
    }

    public void setClientcode(String clientcode) {
        this.clientcode = clientcode;
    }

}
