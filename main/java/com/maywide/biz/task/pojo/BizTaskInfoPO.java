package com.maywide.biz.task.pojo;

import com.maywide.biz.task.entity.BizTaskInfo;
/**
 * 
 *<p> 
 *  用于页面显示
 *<p>
 * Create at 2017-11-15
 *
 *@autor zhuangzhitang
 */
public class BizTaskInfoPO extends BizTaskInfo{
	//查询内容
    private String starttimerang;//时间
    
    //图表展示内容
    private  String opername; //操作员名称
    
    //展示关联网格
    private String gridname;
    //是否超时
    private String isOverTime;
    
	public String getIsOverTime() {
		return isOverTime;
	}

	public void setIsOverTime(String isOverTime) {
		this.isOverTime = isOverTime;
	}

	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public String getStarttimerang() {
		return starttimerang;
	}

	public void setStarttimerang(String starttimerang) {
		this.starttimerang = starttimerang;
	}

	public String getGridname() {
		return gridname;
	}

	public void setGridname(String gridname) {
		this.gridname = gridname;
	}
	
}
