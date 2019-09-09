package com.maywide.biz.inter.pojo.createGroupCustInfo;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class CreateGroupCustInfoReq extends BaseApiRequest {

	private SysCustBean sysCust;

	public SysCustBean getSysCust() {
		return sysCust;
	}

	public void setSysCust(SysCustBean sysCust) {
		this.sysCust = sysCust;
	}

	public static class SysCustBean {
		/**
		 * areaid : 201 attrs :
		 * [{"attrcode":"JKCUST_LEVEL","attrruleid":8253,"attrvalue":"A1"}]
		 * cardno : 441324199608010313 cardtype : 1 city : null credit : null
		 * custstatus : null custtype : 2 email : null faxno : null id : null
		 * intime : null linkaddr : null linkman : test111 markno : 0 memo :
		 * null mobile : 13600258700 name : test111 optime : null phone : null
		 * pubinfo :
		 * {"birth":null,"company":null,"grade":null,"id":null,"interest":null,
		 * "occupation":null,"trade":null} grpinfo : {"memo":null} grpManager :
		 * {"grpmanagerdept":"100361","grpmanagerid":"104126"} industry :
		 * {"id":"9123"} secsubtype : service : null servkind : null subtype :
		 * 40 zip : null
		 */

		private long areaid;
		private String cardno;
		private String cardtype;
		private String city;
		private String credit;
		private String custstatus;
		private String custtype;
		private String email;
		private String faxno;
		private String id;
		private String intime;
		private String linkaddr;
		private String linkman;
		private String markno;
		private String memo;
		private String mobile;
		private String name;
		private String optime;
		private String phone;
		private PubinfoBean pubinfo;
		private GrpinfoBean grpinfo;
		private GrpManagerBean grpManager;
		private IndustryBean industry;
		private String secsubtype;
		private String service;
		private String servkind;
		private String subtype;
		private String zip;
		private List<AttrsBean> attrs;

		public long getAreaid() {
			return areaid;
		}

		public void setAreaid(long areaid) {
			this.areaid = areaid;
		}

		public String getCardno() {
			return cardno;
		}

		public void setCardno(String cardno) {
			this.cardno = cardno;
		}

		public String getCardtype() {
			return cardtype;
		}

		public void setCardtype(String cardtype) {
			this.cardtype = cardtype;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCredit() {
			return credit;
		}

		public void setCredit(String credit) {
			this.credit = credit;
		}

		public String getCuststatus() {
			return custstatus;
		}

		public void setCuststatus(String custstatus) {
			this.custstatus = custstatus;
		}

		public String getCusttype() {
			return custtype;
		}

		public void setCusttype(String custtype) {
			this.custtype = custtype;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFaxno() {
			return faxno;
		}

		public void setFaxno(String faxno) {
			this.faxno = faxno;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIntime() {
			return intime;
		}

		public void setIntime(String intime) {
			this.intime = intime;
		}

		public String getLinkaddr() {
			return linkaddr;
		}

		public void setLinkaddr(String linkaddr) {
			this.linkaddr = linkaddr;
		}

		public String getLinkman() {
			return linkman;
		}

		public void setLinkman(String linkman) {
			this.linkman = linkman;
		}

		public String getMarkno() {
			return markno;
		}

		public void setMarkno(String markno) {
			this.markno = markno;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOptime() {
			return optime;
		}

		public void setOptime(String optime) {
			this.optime = optime;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public PubinfoBean getPubinfo() {
			return pubinfo;
		}

		public void setPubinfo(PubinfoBean pubinfo) {
			this.pubinfo = pubinfo;
		}

		public GrpinfoBean getGrpinfo() {
			return grpinfo;
		}

		public void setGrpinfo(GrpinfoBean grpinfo) {
			this.grpinfo = grpinfo;
		}

		public GrpManagerBean getGrpManager() {
			return grpManager;
		}

		public void setGrpManager(GrpManagerBean grpManager) {
			this.grpManager = grpManager;
		}

		public IndustryBean getIndustry() {
			return industry;
		}

		public void setIndustry(IndustryBean industry) {
			this.industry = industry;
		}

		public String getSecsubtype() {
			return secsubtype;
		}

		public void setSecsubtype(String secsubtype) {
			this.secsubtype = secsubtype;
		}

		public String getService() {
			return service;
		}

		public void setService(String service) {
			this.service = service;
		}

		public String getServkind() {
			return servkind;
		}

		public void setServkind(String servkind) {
			this.servkind = servkind;
		}

		public String getSubtype() {
			return subtype;
		}

		public void setSubtype(String subtype) {
			this.subtype = subtype;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public List<AttrsBean> getAttrs() {
			return attrs;
		}

		public void setAttrs(List<AttrsBean> attrs) {
			this.attrs = attrs;
		}

		public static class PubinfoBean {
			/**
			 * birth : null company : null grade : null id : null interest :
			 * null occupation : null trade : null
			 */

			private String birth;
			private String company;
			private String grade;
			private String id;
			private String interest;
			private String occupation;
			private String trade;

			public String getBirth() {
				return birth;
			}

			public void setBirth(String birth) {
				this.birth = birth;
			}

			public String getCompany() {
				return company;
			}

			public void setCompany(String company) {
				this.company = company;
			}

			public String getGrade() {
				return grade;
			}

			public void setGrade(String grade) {
				this.grade = grade;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getInterest() {
				return interest;
			}

			public void setInterest(String interest) {
				this.interest = interest;
			}

			public String getOccupation() {
				return occupation;
			}

			public void setOccupation(String occupation) {
				this.occupation = occupation;
			}

			public String getTrade() {
				return trade;
			}

			public void setTrade(String trade) {
				this.trade = trade;
			}
		}

		public static class GrpinfoBean {
			/**
			 * memo : null
			 */

			private String memo;

			public String getMemo() {
				return memo;
			}

			public void setMemo(String memo) {
				this.memo = memo;
			}
		}

		public static class GrpManagerBean {
			/**
			 * grpmanagerdept : 100361 grpmanagerid : 104126
			 */

			private String grpmanagerdept;
			private String grpmanagerid;

			public String getGrpmanagerdept() {
				return grpmanagerdept;
			}

			public void setGrpmanagerdept(String grpmanagerdept) {
				this.grpmanagerdept = grpmanagerdept;
			}

			public String getGrpmanagerid() {
				return grpmanagerid;
			}

			public void setGrpmanagerid(String grpmanagerid) {
				this.grpmanagerid = grpmanagerid;
			}
		}

		public static class IndustryBean {
			/**
			 * id : 9123
			 */

			private String id;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}
		}

		public static class AttrsBean {
			/**
			 * attrcode : JKCUST_LEVEL attrruleid : 8253 attrvalue : A1
			 */

			private String attrcode;
			private int attrruleid;
			private String attrvalue;

			public String getAttrcode() {
				return attrcode;
			}

			public void setAttrcode(String attrcode) {
				this.attrcode = attrcode;
			}

			public int getAttrruleid() {
				return attrruleid;
			}

			public void setAttrruleid(int attrruleid) {
				this.attrruleid = attrruleid;
			}

			public String getAttrvalue() {
				return attrvalue;
			}

			public void setAttrvalue(String attrvalue) {
				this.attrvalue = attrvalue;
			}
		}
	}

}
