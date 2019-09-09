package com.maywide.biz.inter.pojo.queservstinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueServExpireNumReq extends BaseApiRequest implements java.io.Serializable {
	private String   patchids;
	private String   quetype;
	private String   objid;
	public String getPatchids() {
		return patchids;
	}
	public void setPatchids(String patchids) {
		this.patchids = patchids;
	}
	public String getQuetype() {
		return quetype;
	}
	public void setQuetype(String quetype) {
		this.quetype = quetype;
	}
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	
	
	/*@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof QueServExpireNumReq){
			QueServExpireNumReq req = (QueServExpireNumReq)obj;
			if(req.getObjid().equals(objid)){
				if(req.getPatchids().equals(patchids)){
					if(req.getQuetype().equals(quetype)){
						return true;
					}
				}
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		int result = 17;
		result = result * 37 + objid.hashCode();
		result = result * 37 + patchids.hashCode();
		result = result * 37 + quetype.hashCode();
		return result;
	}*/

	
	
}
