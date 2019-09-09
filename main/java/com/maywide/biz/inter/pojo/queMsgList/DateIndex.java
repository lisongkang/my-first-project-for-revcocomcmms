package com.maywide.biz.inter.pojo.queMsgList;

import java.util.Date;

public class DateIndex {

	private int index;
	
	private Date date;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DateIndex(int index, Date date) {
		super();
		this.index = index;
		this.date = date;
	}

	public DateIndex() {
		super();
	}
	
	
	
}
