package com.maywide.biz.salary.reqbo;

public class RealCentQryTotalReq {

  private String cycleid;
  private String status; //0 未审核1 已审核通过2 已审核不通过
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
