package com.maywide.biz.auth.pojo.queSeqGrantLog;

import java.util.List;

public class QueSeqGrantLogResp {

	 private String totalrecords;

	private String currentpage;
	private String pagesize;
	private List<GrantBean> grantlist;

	public String getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(String totalrecords) {
		this.totalrecords = totalrecords;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}

	public List<GrantBean> getGrantlist() {
		return grantlist;
	}

	public void setGrantlist(List<GrantBean> grantlist) {
		this.grantlist = grantlist;
	}

	public static class GrantBean {
		private String devno;
		private String opcode;
		private String optime;
		private String rownum;
		private String subtype;
		private String itype;
		private List<String> prodlist;

		public String getDevno() {
			return devno;
		}

		public void setDevno(String devno) {
			this.devno = devno;
		}

		public String getSubtype() {
			return subtype;
		}

		public void setSubtype(String subtype) {
			this.subtype = subtype;
		}

		public String getOpcode() {
			return opcode;
		}

		public void setOpcode(String opcode) {
			this.opcode = opcode;
		}

		public String getOptime() {
			return optime;
		}

		public void setOptime(String optime) {
			this.optime = optime;
		}

		public List<String> getProdlist() {
			return prodlist;
		}

		public void setProdlist(List<String> prodlist) {
			this.prodlist = prodlist;
		}

		public String getRownum() {
			return rownum;
		}

		public void setRownum(String rownum) {
			this.rownum = rownum;
		}

		public String getItype() {
			return itype;
		}

		public void setItype(String itype) {
			this.itype = itype;
		}

	}
}
