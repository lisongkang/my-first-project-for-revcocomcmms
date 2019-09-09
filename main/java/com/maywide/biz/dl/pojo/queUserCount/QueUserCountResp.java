package com.maywide.biz.dl.pojo.queUserCount;

import java.util.List;

public class QueUserCountResp {

	private List<ServcountBean> servcounts;

	public List<ServcountBean> getServcounts() {
		return servcounts;
	}

	public void setServcounts(List<ServcountBean> servcounts) {
		this.servcounts = servcounts;
	}
	
	private int totalcount;
	
	private int pagesize;
	
	private int cntpageno;

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCntpageno() {
		return cntpageno;
	}

	public void setCntpageno(int cntpageno) {
		this.cntpageno = cntpageno;
	}
	
	
	
}
