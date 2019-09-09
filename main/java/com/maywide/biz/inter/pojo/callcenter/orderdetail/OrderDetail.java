package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("orders")
public class OrderDetail {
    
private  List<Order> orders = new ArrayList<Order>();

public List<Order> getOrders() {
	return orders;
}

public void setOrders(List<Order> orders) {
	this.orders = orders;
}  

}
