package com.maywide.biz.inter.pojo.bizfeein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizFeeIntersReq extends BaseApiRequest implements Serializable{

	//boss接口所需参数
		private List<String> fees = new ArrayList<String>();
		private List<String> keyno = new ArrayList<String>();
		private  List<String> permark = new ArrayList<String>();
		private  List<String> isorder = new ArrayList<String>();
		private  List<String> gdnoid = new ArrayList<String>();
		private  List<String> gdno = new ArrayList<String>();
		
		//订单表所需参数
		private  List<String> houseid = new ArrayList<String>();
		private  List<String> patchid = new ArrayList<String>();
		private  String custid;
		private  String name;
		private  String whladdr;
		private  String areaid;
		private  String descrip;
		public List<String> getFees() {
			return fees;
		}
		public void setFees(List<String> fees) {
			this.fees = fees;
		}
		public List<String> getKeyno() {
			return keyno;
		}
		public void setKeyno(List<String> keyno) {
			this.keyno = keyno;
		}
		public List<String> getPermark() {
			return permark;
		}
		public void setPermark(List<String> permark) {
			this.permark = permark;
		}
		public List<String> getIsorder() {
			return isorder;
		}
		public void setIsorder(List<String> isorder) {
			this.isorder = isorder;
		}
		public List<String> getGdnoid() {
			return gdnoid;
		}
		public void setGdnoid(List<String> gdnoid) {
			this.gdnoid = gdnoid;
		}
		public List<String> getGdno() {
			return gdno;
		}
		public void setGdno(List<String> gdno) {
			this.gdno = gdno;
		}
		public List<String> getHouseid() {
			return houseid;
		}
		public void setHouseid(List<String> houseid) {
			this.houseid = houseid;
		}
		public List<String> getPatchid() {
			return patchid;
		}
		public void setPatchid(List<String> patchid) {
			this.patchid = patchid;
		}
		public String getCustid() {
			return custid;
		}
		public void setCustid(String custid) {
			this.custid = custid;
		}
		public String getAreaid() {
			return areaid;
		}
		public void setAreaid(String areaid) {
			this.areaid = areaid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescrip() {
			return descrip;
		}
		public void setDescrip(String descrip) {
			this.descrip = descrip;
		}
		public String getWhladdr() {
			return whladdr;
		}
		public void setWhladdr(String whladdr) {
			this.whladdr = whladdr;
		}
		
		
}
