package com.maywide.biz.task.pojo;

public class TaskMessageQueueBean {

	private String filePath;
	
	private String jobName;
	
	private String jobGroup;
	
	private String jvmInstruct;
	
	private String paramsInstruct;
	
	private String jobType;
	
	private String expression;
	
	private String descrption;
	
	private int timeType;
	
	private int timeValue;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJvmInstruct() {
		return jvmInstruct;
	}

	public void setJvmInstruct(String jvmInstruct) {
		this.jvmInstruct = jvmInstruct;
	}


	public String getParamsInstruct() {
		return paramsInstruct;
	}

	public void setParamsInstruct(String paramsInstruct) {
		this.paramsInstruct = paramsInstruct;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	public int getTimeType() {
		return timeType;
	}

	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}

	public int getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(int timeValue) {
		this.timeValue = timeValue;
	}
	
	
	
}
