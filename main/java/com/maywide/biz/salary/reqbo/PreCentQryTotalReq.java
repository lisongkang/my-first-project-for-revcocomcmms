package com.maywide.biz.salary.reqbo;

public class PreCentQryTotalReq {

  private String cycleid;
  private String status; // Y有效 N无效
  private String groupcode; //ZSBUSI 销售积分  ZSSETUP 安装积分
  private String operator; //操作员


    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }
}
