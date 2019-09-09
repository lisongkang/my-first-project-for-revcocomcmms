package com.maywide.biz.inter.pojo.queryOssLoginInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;


public class QueryOssLoginInfoReq {
    private String username;
    private String password;
    
    private Exts exts;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
	
    
	public Exts getExts() {
		return exts;
	}
	public void setExts(Exts exts) {
		this.exts = exts;
	}
//
//
//
//	public static class Exts{
//		private String relatedOrgCuid;
//
//		public String getRelatedOrgCuid() {
//			return relatedOrgCuid;
//		}
//
//		public void setRelatedOrgCuid(String relatedOrgCuid) {
//			this.relatedOrgCuid = relatedOrgCuid;
//		}
//
//	}
}
