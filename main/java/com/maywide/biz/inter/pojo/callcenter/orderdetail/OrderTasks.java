package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("orderTasks")  
public class OrderTasks {
	
	private List<OrderRet> orderTasks = new ArrayList();

	public List<OrderRet> getOrderTasks() {
		return orderTasks;
	}

	public void setOrderTasks(List<OrderRet> orderTasks) {
		this.orderTasks = orderTasks;
	}

	public List<OrderRet> getOrder() {
		return orderTasks;
	}

	
	
}
