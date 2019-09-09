package com.maywide.core.cons;

/**
 * <p>Company: maywide</p>
 * <p>Description: </p>
 *  系统常量，保存系统常量，该常量必须是在framework里面使用的，否则请添加到biz下
 */
public interface Constant {
	/**项目内统一的NameSpace定义*/
	public static final String NS_BOOK = "http://book.service.maywide.com";
	public static final String POINT = "\\.";
	public static final String EMPTY = "";
	public static final String APPLICATION = "Application";
	public static final String SQL = "sql";//for sql.properties
	public static final String USYSPARAM_JNDINAME = "com.maywide.stateless.usysparam.UsysparamBean";
	public static final String USYSPARAM_MANAGER = "usysparamManager";
	public static final String ROOT = "@ROOT";
	public static final String ADMINISTRATOR = "GZCYKFA0001";
	public static final String CYYYCS_DEPT_NAME = "诚毅运营测试";
	
	final String COMMA = ",";
	final String NULL_FIELDS = "nullFields";       //保存查询条件为空的字段
	final String NOTNULL_FIELDS = "notNullFields"; //保存查询条件不为空的字段
	
	final String ORDER_ASC = "ASC";          
	final String ORDER_DESC = "DESC";
	
	final int FLAG_NULL = 1;     //为空标志
	final int FLAG_NOTNULL = 2;  //不为空标志
	
	final Integer DEFAULT_SYSID = 1;
	
	interface TimeUnit {
		/**
		 * 日 <code>0</code>
		 */
		public static final String DAY = "0"; // 日

		/**
		 * 月 <code>1</code>
		 */
		public static final String MONTH = "1"; // 月

		/**
		 * 年 <code>2</code>
		 */
		public static final String YEAR = "2";

		/**
		 * 小时 <code>10</code>
		 */
		public static final String HOUR = "10";

	}

	public interface PrvSysparamCode {
		//获取网关升级时需要退订产品的城市
		static final String NEED_UNSUBSCRIBE_CITY="NEED_UNSUBSCRIBE_CITY";
	}
}