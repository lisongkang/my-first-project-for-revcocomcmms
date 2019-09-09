package com.maywide.biz.inter.pojo.querycatalog;

import java.util.ArrayList;
import java.util.List;

public class QueCustMarktInfoResp implements java.io.Serializable  {
	private  String     autoCount        ;
	private  String     cntPageNo        ;
	private  String     first            ;
	private  String     firstSetted      ;
	private  String     hasNext          ;
	private  String     hasPre           ;
	private  String     inverseOrder     ;
	private  String     nextPage         ;
	private  String     order            ;
	private  String     orderBy          ;
	private  String     orderBySetted    ;
	private  String     pageNo           ;
	private  String     pageSize         ;
	private  String     pageSizeSetted   ;
	private  String     prePage          ;
	private  String 	totalCount         ;
	private  String     totalPages       ;

	List<QueCustMarkInfoRespBO> result = new ArrayList<QueCustMarkInfoRespBO>();

	public List<QueCustMarkInfoRespBO> getResult() {
		return result;
	}

	public void setResult(List<QueCustMarkInfoRespBO> result) {
		this.result = result;
	}

	public String getAutoCount() {
		return autoCount;
	}

	public void setAutoCount(String autoCount) {
		this.autoCount = autoCount;
	}

	public String getCntPageNo() {
		return cntPageNo;
	}

	public void setCntPageNo(String cntPageNo) {
		this.cntPageNo = cntPageNo;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getFirstSetted() {
		return firstSetted;
	}

	public void setFirstSetted(String firstSetted) {
		this.firstSetted = firstSetted;
	}

	public String getHasNext() {
		return hasNext;
	}

	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}

	public String getHasPre() {
		return hasPre;
	}

	public void setHasPre(String hasPre) {
		this.hasPre = hasPre;
	}

	public String getInverseOrder() {
		return inverseOrder;
	}

	public void setInverseOrder(String inverseOrder) {
		this.inverseOrder = inverseOrder;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderBySetted() {
		return orderBySetted;
	}

	public void setOrderBySetted(String orderBySetted) {
		this.orderBySetted = orderBySetted;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageSizeSetted() {
		return pageSizeSetted;
	}

	public void setPageSizeSetted(String pageSizeSetted) {
		this.pageSizeSetted = pageSizeSetted;
	}

	public String getPrePage() {
		return prePage;
	}

	public void setPrePage(String prePage) {
		this.prePage = prePage;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}


	

	
}
