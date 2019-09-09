package com.maywide.biz.inter.pojo.quegridtree;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maywide.biz.inter.pojo.dataReport.GridInfoResp;

@JsonIgnoreProperties(value = { "prev" })
public class GridTreeNode extends GridInfoResp {

	private GridTreeNode prev;

	private Long prevId;

	private List<GridTreeNode> childNodes;

	private int level;

	public GridTreeNode getPrev() {
		return prev;
	}

	public void setPrev(GridTreeNode prev) {
		this.prev = prev;
	}

	public Long getPrevId() {
		return prevId;
	}

	public void setPrevId(Long prevId) {
		this.prevId = prevId;
	}

	public List<GridTreeNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<GridTreeNode> childNodes) {
		this.childNodes = childNodes;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
