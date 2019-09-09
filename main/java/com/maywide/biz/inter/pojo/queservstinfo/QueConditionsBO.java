package com.maywide.biz.inter.pojo.queservstinfo;

public class QueConditionsBO implements java.io.Serializable {

	private String keywordtype;
	private String quekeyword;
	
	public QueConditionsBO(){
	}
	
	public QueConditionsBO(String keywordtype, String quekeyword) {
		super();
		this.keywordtype = keywordtype;
		this.quekeyword = quekeyword;
	}

	public String getKeywordtype() {
		return keywordtype;
	}

	public void setKeywordtype(String keywordtype) {
		this.keywordtype = keywordtype;
	}

	public String getQuekeyword() {
		return quekeyword;
	}

	public void setQuekeyword(String quekeyword) {
		this.quekeyword = quekeyword;
	}

}
