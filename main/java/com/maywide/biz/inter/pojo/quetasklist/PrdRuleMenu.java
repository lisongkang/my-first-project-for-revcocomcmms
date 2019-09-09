package com.maywide.biz.inter.pojo.quetasklist;

public class PrdRuleMenu {
	private String id;// 菜单标志
	private String premenuid;// 父菜单标志
	private String name;// 菜单名称
	private String linkurl;// 菜单链接
	private String iconid;// 菜单图标
	private String flag;// 有效标志 0:启用，1:停用
	private String bizcode;// 业务代码
	private String attr;// 菜单属性 标识是否常用
	private String pinyin;// 菜单拼音
	private String target;// 页面方式
	private String oflag1;// 菜单权限
	private String oflag2;// 菜单类型
	private String memo;
	private String atype;// app菜单类型

	private String value;
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPremenuid() {
		return premenuid;
	}

	public void setPremenuid(String premenuid) {
		this.premenuid = premenuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public String getIconid() {
		return iconid;
	}

	public void setIconid(String iconid) {
		this.iconid = iconid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getOflag1() {
		return oflag1;
	}

	public void setOflag1(String oflag1) {
		this.oflag1 = oflag1;
	}

	public String getOflag2() {
		return oflag2;
	}

	public void setOflag2(String oflag2) {
		this.oflag2 = oflag2;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
