package com.maywide.biz.inter.pojo.goods.queGoodsNodes;

import java.util.ArrayList;
import java.util.List;

public class GoodsNodeBean {
	private String nodeId;
	private String preNodeId;
	private String rootNodeId;
	private String ruleName;
	private String nodeName;
	private String valueSql;
	private String percentage;
	
	private String nodeValue;
	
	private List<GoodsNodeBean> childNodeList = new ArrayList<GoodsNodeBean>();
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getPreNodeId() {
		return preNodeId;
	}
	public void setPreNodeId(String preNodeId) {
		this.preNodeId = preNodeId;
	}
	public String getRootNodeId() {
		return rootNodeId;
	}
	public void setRootNodeId(String rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public List<GoodsNodeBean> getChildNodeList() {
		return childNodeList;
	}
	public void setChildNodeList(List<GoodsNodeBean> childNodeList) {
		this.childNodeList = childNodeList;
	}
	public String getValueSql() {
		return valueSql;
	}
	public void setValueSql(String valueSql) {
		this.valueSql = valueSql;
	}
	public String getNodeValue() {
		return nodeValue;
	}
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
	
	

}
