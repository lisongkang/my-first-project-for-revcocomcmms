package com.maywide.biz.sta.gridincome.pojo;

import java.util.List;

import com.maywide.biz.inter.pojo.bizpreprocess.OrderParamsInterBO;
import com.maywide.biz.inter.pojo.queservstinfo.CustInfosBO;

public class ChartDatasBO implements java.io.Serializable {
  
    private String name;//图例名
    private String type;//图类型:bar,line...
    private List<String> data;//数据:d1,d3,d4,
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getData() {
        return data;
    }
    public void setData(List<String> data) {
        this.data = data;
    }
    
    
}
