package com.maywide.biz.inter.pojo.queryOssLoginInfo;

public class OssLoginInfo {
	// "data":{
	// "uid":"87934a8e1118f0c1345396e08f555db9",
	// "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoie1wiYWN0aW9uSWRcIjpcImZiMDFhODU2LWQ3ZmMtNDkzMC1iZDUyLTJlOGRkNDZmODAzZFwiLFwiY3VpZFwiOlwiMTA0MTI5XCIsXCJleHRzXCI6e30sXCJzaG93TmFtZVwiOlwi5rWL6K-V57uEXCIsXCJzdXBlclVzZXJcIjpmYWxzZSxcInVzZXJOYW1lXCI6XCJHWkNZS0ZBQ1NcIn0ifQ.086AAZGXaBL71W58kSTy-LIe15ykWfGGEt3xKX8h3Yc"
	// },
	// "error":"",
	// "message":"",
	// "status":"ok",
	// "statusCode":200,
	// "timestamp":"2019-07-15 16:23:02"
	
	private String error;
	
	private String message;
	
	private String status;
	
	private String statusCode;
	
	private String timestamp;
	
	private OssLoginInfoData data;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public OssLoginInfoData getData() {
		return data;
	}

	public void setData(OssLoginInfoData data) {
		this.data = data;
	}
	
	
   
}
