package com.maywide.biz.prv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="prv_app_user")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class AppUser
  implements Serializable
{
  private Long id;
  private String operid;
  private String userid;
  private String client_id;
  private String status;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  public Long getId()
  {
    return this.id; }

  public void setId(Long id) {
    this.id = id; }

  public String getOperid() {
    return this.operid; }

  public void setOperid(String operid) {
    this.operid = operid; }

  public String getUserid() {
    return this.userid; }

  public void setUserid(String userid) {
    this.userid = userid; }
  @Column(name="appid")
  public String getClient_id() {
    return this.client_id; }

  public void setClient_id(String clientId) {
    this.client_id = clientId; }

  public String getStatus() {
    return this.status; }

  public void setStatus(String status) {
    this.status = status;
  }
}