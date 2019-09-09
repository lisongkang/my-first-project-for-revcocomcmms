package com.maywide.biz.inter.pojo.bizpreprocess;

public class DeveloperBossBO {

	private String type;// 发展类型
	private Long place;// 发展渠道
	private String devname;// 发展人
	private String memo; // 发展备注

	public DeveloperBossBO() {

	}

	public DeveloperBossBO(String type, Long place, String devname) {
		super();
		this.type = type;
		this.place = place;
		this.devname = devname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPlace() {
		return place;
	}

	public void setPlace(Long place) {
		this.place = place;
	}

	public String getDevname() {
		return devname;
	}

	public void setDevname(String devname) {
		this.devname = devname;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
