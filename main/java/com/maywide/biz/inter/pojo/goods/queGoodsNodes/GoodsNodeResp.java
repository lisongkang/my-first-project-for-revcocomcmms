package com.maywide.biz.inter.pojo.goods.queGoodsNodes;

import java.util.List;

public class GoodsNodeResp {
	private String nodeId;
	private String preNodeId;
	private String rootNodeId;
	private String ruleName;
	private String nodeName;
	private String percentage;
	
	private List<GoodsNodeBean> childNodeList;

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
	
	

}
