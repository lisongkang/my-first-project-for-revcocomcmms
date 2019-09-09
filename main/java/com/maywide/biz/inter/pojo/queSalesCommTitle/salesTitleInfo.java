package com.maywide.biz.inter.pojo.queSalesCommTitle;

/**
 * Created by lisongkang on 2019/5/17 0001.
 */
public class salesTitleInfo implements java.io.Serializable {
    private String fieldname;//对应数据字段
    private String name;//前端显示内容

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
