package com.maywide.biz.inter.constant;

public interface QueConstant {

	/**
	 * 查询关键字类型
	 */
	public interface QueKeywordtype {
		/** 查询关键字类型:智能卡号 */
		public final static String SMNO = "smno";
		/** 查询关键字类型:机顶盒号 */
		public final static String STBNO = "stbno";
		/** 查询关键字类型:cmmac */
		public final static String CMMAC = "cmmacno";
		/** 查询关键字类型:身份证号 */
		public final static String CARDNO = "cardno";
		/** 查询关键字类型:u宽频手机号 */
		public final static String U_CMCCNO = "ucmccno";
		/** 查询关键字类型:客户姓名 */
		public final static String CUSTNAME = "custname";
		/** 查询关键字类型:客户证号 */
		public final static String CUSTMARKNO = "custmarkno";
		/** 查询关键字类型:客户id */
		public final static String CUSTID = "custid";
		/** 查询关键字类型:客户类型 */
		public final static String CUST_TYPE = "custtype";
		/** 查询关键字类型:片区id(格式:patchid1,patchid2,patchid3) */
        public final static String PATCHIDS = "patchids";
        public final static String GRIDCODES = "gridcodes";
        String AREADIDS = "areaid";
        
        
	}
	
	public interface CommonNotice{
		String LOGIN_OUT_NOTICE = "用户未登录或已过期";
	}

}
