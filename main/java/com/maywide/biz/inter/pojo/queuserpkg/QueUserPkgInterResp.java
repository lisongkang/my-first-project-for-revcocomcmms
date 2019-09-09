package com.maywide.biz.inter.pojo.queuserpkg;

import java.util.List;

public class QueUserPkgInterResp implements java.io.Serializable {
	
	private List<UserPkgsBO> pkgs;

	public List<UserPkgsBO> getPkgs() {
		return pkgs;
	}

	public void setPkgs(List<UserPkgsBO> pkgs) {
		this.pkgs = pkgs;
	}


}
