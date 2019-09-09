package com.maywide.biz.inter.pojo.oss.order;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("orderTasks")  
public class OssOrderTasks {
	
	private List<OssOrderRet> orderTasks = new ArrayList();

	public List<OssOrderRet> getOrderTasks() {
		return orderTasks;
	}

	public void setOrderTasks(List<OssOrderRet> orderTasks) {
		this.orderTasks = orderTasks;
	}
	
	public List<OssOrderRet> getOrder() {
		return orderTasks;
	}	


	
	
}
