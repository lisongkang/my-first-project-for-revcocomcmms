package com.maywide.biz.prv.bo;

/**
 * 用于保存树结点对象；应用于prv_querysql的树类型
 * @author maywide
 *
 */
public class PrvTreeObj  implements java.io.Serializable{
	/** 树结点标识 */
	private String key;
	/** 父结点标识 */
	private String parentid;
	/** 结点标题 */
	private String title;
	/** 是否叶子结点 */
	private String isleaf;
	/** 以“~”分隔的串，映射到树结点后，成为结点属性（data_1、data_2、data_n） */
	private String data;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
 	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
