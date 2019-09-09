package com.maywide.biz.auth.pojo.queSeqGrant;

import java.util.List;


public class QueSeqGrantResp {

	private String currentpage;
	private String pagesize;
	private String totalrecords;
	private List<GrantlistBean> grantlist;

	public String getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(String totalrecords) {
		this.totalrecords = totalrecords;
	}

	public List<GrantlistBean> getGrantlist() {
		return grantlist;
	}

	public void setGrantlist(List<GrantlistBean> grantlist) {
		this.grantlist = grantlist;
	}

//	public static class GrantlistBean {
//		/**
//		 * devno : 8757004266891340 itype : CA opcode : 开户+授权 optime :
//		 * 2018-02-02 16:47:14 prodlist :
//		 * ["数字基本包(全省)","高清中央1套","高清中央2套","高清中央7套","高清中央3套","高清中央5套","高清中央5+",
//		 * "高清中央6套","高清中央8套","高清中央10套","高清中央12套","高清3D测试频道","高清广东卫视","高清广东体育",
//		 * "高清北京卫视","高清东方卫视","高清浙江卫视","高清黑龙江卫视","高清深圳卫视","高清湖北卫视","高清安徽卫视",
//		 * "高清山东卫视","高清天津卫视","高清湖南卫视","高清江苏卫视","央视文化精品","卫生健康","风云剧场","靓妆",
//		 * "东方财经","动漫秀场","游戏风云","极速汽车","七彩戏剧","生活时尚","高清中央9套","高清中央14套"] status
//		 * : 成功:执行成功 subtype : 佛山同方
//		 */
//
//		private String devno;
//		private String itype;
//		private String opcode;
//		private String optime;
//		private String status;
//		private String subtype;
//		private List<String> prodlist;
//		
//		private String opcode2;
//		private String subtype2;
//
//		public String getDevno() {
//			return devno;
//		}
//
//		public void setDevno(String devno) {
//			this.devno = devno;
//		}
//
//		public String getItype() {
//			return itype;
//		}
//
//		public void setItype(String itype) {
//			this.itype = itype;
//		}
//
//		public String getOpcode() {
//			return opcode;
//		}
//
//		public void setOpcode(String opcode) {
//			this.opcode = opcode;
//		}
//
//		public String getOptime() {
//			return optime;
//		}
//
//		public void setOptime(String optime) {
//			this.optime = optime;
//		}
//
//		public String getStatus() {
//			return status;
//		}
//
//		public void setStatus(String status) {
//			this.status = status;
//		}
//
//		public String getSubtype() {
//			return subtype;
//		}
//
//		public void setSubtype(String subtype) {
//			this.subtype = subtype;
//		}
//
//		public List<String> getProdlist() {
//			return prodlist;
//		}
//
//		public void setProdlist(List<String> prodlist) {
//			this.prodlist = prodlist;
//		}
//
//		public String getOpcode2() {
//			return opcode2;
//		}
//
//		public void setOpcode2(String opcode2) {
//			this.opcode2 = opcode2;
//		}
//
//		public String getSubtype2() {
//			return subtype2;
//		}
//
//		public void setSubtype2(String subtype2) {
//			this.subtype2 = subtype2;
//		}
//		
//		
//	}

//	public QueSeqGrantResp(String currentpage, String pagesize, String totalrecords, List<GrantlistBean> grantlist) {
//		super();
//		this.currentpage = currentpage;
//		this.pagesize = pagesize;
//		this.totalrecords = totalrecords;
//		this.grantlist = grantlist;
//	}
//
//	public QueSeqGrantResp() {
//		super();
//	}
//	
	

}
