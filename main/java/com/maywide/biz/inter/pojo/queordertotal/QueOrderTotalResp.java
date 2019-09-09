package com.maywide.biz.inter.pojo.queordertotal;

public class QueOrderTotalResp {

	private int timeoutnum;// 超时工单数
	private int prealarmnum;// 预警工单数
	private int todonum;// 待处理工单数
	private int todoInsNum;// 待处理的安装工单数
	private int todoRepeatNum;// 待处理维修工单数
	private int timeoutInsNum;// 超时的安装工单数
	private int timeoutRepeatNum;// 超时维修工单数
	private int preInsNum;// 预警安装单数
	private int preRepateNum;// 预警维修单数

	public int getTimeoutnum() {
		return timeoutnum;
	}

	public void setTimeoutnum(int timeoutnum) {
		this.timeoutnum = timeoutnum;
	}

	public int getPrealarmnum() {
		return prealarmnum;
	}

	public void setPrealarmnum(int prealarmnum) {
		this.prealarmnum = prealarmnum;
	}

	public int getTodonum() {
		return todonum;
	}

	public void setTodonum(int todonum) {
		this.todonum = todonum;
	}

	public int getTodoInsNum() {
		return todoInsNum;
	}

	public void setTodoInsNum(int todoInsNum) {
		this.todoInsNum = todoInsNum;
	}

	public int getTodoRepeatNum() {
		return todoRepeatNum;
	}

	public void setTodoRepeatNum(int todoRepeatNum) {
		this.todoRepeatNum = todoRepeatNum;
	}

	public int getTimeoutInsNum() {
		return timeoutInsNum;
	}

	public void setTimeoutInsNum(int timeoutInsNum) {
		this.timeoutInsNum = timeoutInsNum;
	}

	public int getTimeoutRepeatNum() {
		return timeoutRepeatNum;
	}

	public void setTimeoutRepeatNum(int timeoutRepeatNum) {
		this.timeoutRepeatNum = timeoutRepeatNum;
	}

	public int getPreInsNum() {
		return preInsNum;
	}

	public void setPreInsNum(int preInsNum) {
		this.preInsNum = preInsNum;
	}

	public int getPreRepateNum() {
		return preRepateNum;
	}

	public void setPreRepateNum(int preRepateNum) {
		this.preRepateNum = preRepateNum;
	}

}
