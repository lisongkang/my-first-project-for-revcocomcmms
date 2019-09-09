package com.maywide.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.maywide.core.cons.Constant;

/**
 * 该类的静态方法提供了对日期对象的系列操作, 包括<br>
 * <ui>
 * <li>格式化日期
 * <li>解析字符串为日期对象
 * <li>计算日期
 * <li>比较日期 </ui>
 * <p>
 * 对于<i>格式化日期</i>和<i>解析字符串为日期对象</i>, 该类提供了如下7种格式: <br>
 * <ui>
 * <li>默认日期格式 <tt>PATTERN_DEFAULT = "yyyy-MM-dd"</tt>
 * <li>路径格式 <tt>PATTERN_DAYPATH = "yyyy\\MM\\dd\\"</tt>
 * <li>日期时间格式(24小时制) <tt>PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss"</tt>
 * <li>无间隔符的日期时间格式(24小时制) <tt>PATTERN_DATETIME_COMPACT = "yyyyMMddHHmmss"</tt>
 * <li>无间隔符日期格式 <tt>PATTERN_DATE_COMPACT = "yyyyMMdd"</tt>
 * <li>无间隔符日期短格式 <tt>PATTERN_DATESHORT = "yyMMdd"</tt>
 * <li>年月格式 <tt>PATTERN_YEARMONTH = "yyyyMM"</tt> </ui>
 */
public class DateTimeUtil {

	private static final String[] MONTHS_STRING = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	private static final String UTC_0800 = "UTC 0800 ";	
	
	/**
	 * 指定一个Date对象date，返回该日期“YYYY-MM-DD”的字符串
	 * 
	 * @deprecated 请使用<code>DateTimeUtil.formatDate(Date)</code>代替
	 * 
	 * @param date
	 *              指定的Date类型对象
	 * @return String
	 * 				要返回的“YYYY-MM-DD”的字符串
	 */
	static public String getDateStr(Date date) {
		if (null == date || "".equals(date.toString())) {
			return "";
		}
		return formatDate(date);
	}

	/**
	 * 取当前日期的短格式, 格式为<tt>yyMMdd</tt>
	 * 
	 * @deprecated 请使用<code>DateTimeUtil.getCurrentDateShort()</code>代替
	 *           
	 * @return String
	 * 				格式为<tt>yyMMdd</tt>的字符串
	 * @author zhengshumin
	 */
	static public String getSortDate() {
		return formatDate(new Date(), PATTERN_DATESHORT);
	}

	/**
	 * 取当前日期的短格式, 格式为<tt>yyMMdd</tt>
	 * 
	 * @deprecated 请使用<code>DateTimeUtil.getCurrentDateShort()</code>代替
	 *           
	 * @return String
	 * 				格式为<tt>yyMMdd</tt>的字符串
	 */
	static public String getCurrentDateShort() {
		return formatDate(new Date(), PATTERN_DATESHORT);
	}

	/**
	 * 指定一个Date对象date，返回该日期YYYY-MM-DD HH:MM:SS的字符串
	 * 
	 * @deprecated 请使用<code>DateTimeUtil.formatDate(Date, DateTimeUtil.PATTERN_DATETIME)</code>代替
	 * 
	 * @param date
	 * 				指定Date类型对象
	 * @return
	 * 				返回该日期YYYY-MM-DD HH:MM:SS的字符串
	 */
	static public String getDateTimeStr(Date date) {
		return formatDate(date, DateTimeUtil.PATTERN_DATETIME);
	}

	/**
	 * 指定一个Date对象date，返回该日期YYYYMMDDHHMMSS的字符串
	 * 
	 * @deprecated 请使用<code>DateTimeUtil.formatDate(Date, 
	 * DateTimeUtil.PATTERN_DATETIME_COMPACT)</code>代替
	 * 
	 * @param datetime
	 * 				指定Date类型对象
	 * @return
	 * 				返回该日期YYYYMMDDHHMMSS的字符串	
	 */
	static public String FormatDateTime(Date datetime) {
		return DateTimeUtil.formatDate(datetime,
				DateTimeUtil.PATTERN_DATETIME_COMPACT);
	}

	/**
	 *  <b> 将默认格式(<code>yyyy-MM-dd</code>)的日期字符串转换成<code>java.util.Date</code>类型
	 * 
	 * @deprecated 该方法已过时,请使用<code>DateTimeUtil.(String)</code>代替
	 */
	static public Date FormatStrToDate(String date) {
		return parseDate(date);
	}

	/**
	 * 取得当前时间的字符串,格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @deprecated 该方法已过时,请使用<tt>DateTimeUtil.currentDateTime()</tt>代替
	 */
	static public String getCurrentDateTimeStr() {
// String StartTime = getDateTimeStr(new Date(System.currentTimeMillis()));
// return StartTime;
		return currentDateTime();
	}

	/**
	 * 取得当前时间的字符串,格式为yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String currentDateTime() {
		return formatDate(new java.util.Date(), DateTimeUtil.PATTERN_DATETIME);
	}

	/**
	 * 取得当前时间的字符串,格式为yyyyMM
	 */
	public static String currentYearMonth() {
		return formatDate(new java.util.Date(), DateTimeUtil.PATTERN_YEARMONTH);
	}

	/**
	 * 取得当前时间的字符串,格式为"yyyy-MM-dd"
	 */
	public static String currentDay() {
		return formatDate(new Date(),DateTimeUtil.PATTERN_DEFAULT);
	}

	/**
	 * 返回两个日期（DATE类型）相差的天数
	 * 
	 * @param dateStart
	 *            开始日期
	 * @param dateEnd
	 *            截止日期
	 * @return 截止日期与开始日期相差的天数(用截止日期减去开始日期)
	 */
	static public int CompareTo(Date dateStart, Date dateEnd) throws Exception {
		if (dateStart == null || dateEnd == null) {
			throw new Exception("开始日期或结束日期为空！");
		}
		return new Long((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60
				/ 60 / 24).intValue();
	}

	/**
	 * 根据给出的日期，计算n天后的日期,n可以为负整数，表示n天前
	 * 
	 * @param givenDate
	 *            给定日期
	 * @param n
	 *            偏移量
	 * @return n天后的日期
	 */
	public static Date addNday(Date givenDate, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(givenDate);
		c.add(Calendar.DATE, n);

		return c.getTime();
	}

	/**
	 * 计算日期
	 * 
	 * @param givenDate
	 *            需要计算的日期
	 * @param amount
	 *            数量(LONG类型) 可以为负数
	 * @param unit
	 *            单位 (TimeUnit.YEAR/TimeUnit.MONTH/TimeUnit.DAY)
	 * @see <tt>DateTimeUtil.accountDate(Date givenDate, int amount, String unit)</tt>
	 * @author LiuYuan
	 * @return 新日期
	 */
	public static Date accountDate(Date givenDate, long amount, String unit) {

		int temp = Integer.valueOf(String.valueOf(amount));
		return accountDate(givenDate, temp, unit);

	}

	/**
	 * 计算日期
	 * 
	 * @param givenDate
	 *            需要计算的日期
	 * @param amount
	 *            数量(INT类型) 可以为负数
	 * @param unit
	 *            单位 (TimeUnit.YEAR/TimeUnit.MONTH/TimeUnit.DAY)
	 * @author LiuYuan
	 * @return
	 */
	public static Date accountDate(Date givenDate, int amount, String unit) {
		Calendar c = Calendar.getInstance();
		c.setTime(givenDate);
		if (unit != null && unit.trim().equals(Constant.TimeUnit.DAY)) {
			c.add(Calendar.DATE, amount);
			return c.getTime();
		} else if (unit != null && unit.trim().equals(Constant.TimeUnit.MONTH)) {
			c.add(Calendar.MONTH, amount);
			return c.getTime();
		} else if (unit != null && unit.trim().equals(Constant.TimeUnit.YEAR)) {
			c.add(Calendar.YEAR, amount);
			return c.getTime();
		}else if(unit != null && unit.trim().equals(Constant.TimeUnit.HOUR)) {
			c.add(Calendar.HOUR, amount);
			return c.getTime();
		}
		else {
			return givenDate;
		}
	}
	

	/**
	 * 统计日期当年有多少天
	 * 
	 * @author LiuYuan
	 */
	public static int getLenientDays(Date date) throws Exception {
		GregorianCalendar c = (GregorianCalendar)GregorianCalendar.getInstance();
		c.setTime(date);
		
		return c.isLeapYear(c.get(Calendar.YEAR)) ? 366 : 365;
	}

	/**
	 * 计算日期月份内有多少天
	 * 
	 * @throws Exception
	 * @author LiuYuan
	 */
	public static int getDateDays(Date date) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 计算两个日期相差的单位数 取整值
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @param unit
	 *            计算单位 (TimeUnit.YEAR/TimeUnit.MONTH/TimeUnit.DAY)
	 * @return 两个日期相差的单位数(用日期2减去日期1)
	 * @throws Exception
	 * @author LiuYuan
	 */
	public static int getDiscrepantDays(Date date1, Date date2, String unit)
			throws Exception {
		int dateUnit = 0;
		if (unit != null && unit.trim().equals(Constant.TimeUnit.DAY)) {
			dateUnit = Calendar.DATE;
		} else if (unit != null && unit.trim().equals(Constant.TimeUnit.MONTH)) {
			dateUnit = Calendar.MONTH;
		} else if (unit != null && unit.trim().equals(Constant.TimeUnit.YEAR)) {
			dateUnit = Calendar.YEAR;
		} else {
			return 0;
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int days = 0;
		boolean flag = false;
		if(c2.before(c1))
		{
			flag = true;
		}
		while (c1.before(c2)) {
			c1.add(dateUnit, 1);
			days++;
		}
		while (c2.before(c1)) {
			c2.add(dateUnit, 1);
			days--;
		}
		if(flag && !unit.trim().equals(Constant.TimeUnit.DAY) && c1.before(c2))
		{
			days++;
		}
		return days;
	}

	/**
	 * 计算两个日期之间相差多少月 精确到日期
	 * 
	 * （截止日期－开始日期 的最小月数) + (剩余天数/开始日期所属月的总天数)；
	 * 
	 * @throws Exception
	 * @author LiuYuan
	 */
	public static double getDiscrepantMonth(Date date1, Date date2)
			throws Exception {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DATE);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		int day2 = c2.get(Calendar.DATE);
		double month = ((year1 - year2) * 12) + (month1 - month2)
				+ (day1 - day2)
				/ (double) c2.getActualMaximum(Calendar.DAY_OF_MONTH);
		return month;
	}

	/**
	 * 计算两个日期之间相差多少月 精确到月
	 * 
	 * @throws Exception
	 * @author LiuYuan
	 */
	public static double getDiscrepantMonthI(Date date1, Date date2)
			throws Exception {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		double month = ((year1 - year2) * 12) + (month1 - month2);

		return month;
	}

	/**
	 * 比较两个日期是否相同
	 * 
	 * @throws Exception
	 */
	public static boolean compareDateTime(Date date1, Date date2)
			throws Exception {
		return (date1.before(date2) || date2.before(date1)) ? false : true;
	}

	/**
	 * 比较两个日期是否相同, 只比较年月日
	 * 
	 * @throws Exception
	 */
	public static boolean compareDay(Date date1, Date date2) throws Exception {
		//return (date1.before(date2) || date2.before(date1)) ? false : true;
//        Calendar c1 = Calendar.getInstance();
//		Calendar c2 = Calendar.getInstance();
//		c1.setTime(date1);
//		c2.setTime(date2);
//
//		return c1.get(Calendar.DATE)  == c2.get(Calendar.DATE)
//		     && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
//			 && c1.get(Calendar.YEAR)  == c2.get(Calendar.YEAR);
		
		if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}
	
	
	/**
	 * 判断beginDate是否在endDate的 N 年之内
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return 在N年内 return true
	 */
	public static boolean compareDateSpec(Date beginDate, Date endDate, int n)   
			throws Exception {
		if (beginDate == null || endDate == null) {
			throw new Exception("参数错误！");
		}
  
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - n);
  		
		return calendar.getTime().before(beginDate);           
	}   


	/**
	 * <b> 根据指定格式转换字符串为日期 </b> <br>
	 * 
	 * 如果字符串格式不正确,则返回null
	 * 
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            指定格式,参照DateTimeUtil类中常量定义
	 * @return java.util.Date
	 */
	public static Date parseDate(String date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
			// log.error("", pe);
			return null;
		}
	}
	
	/**
	 * 将形如
	 * <li>"Thu Dec 6 11:45:13 UTC 0800 2007", 或
	 * <li>"Thu 12 6 11:45:13 UTC 0800 2007", 或
	 * <li>"Thu Dec 6 11:45:13 2007", 或	
	 * <li>"Thu 12 6 11:45:13 2007" 
	 * <br>的字符串解析为Date对象
	 * @return Date对象，解析失败将返回 null 
	 * @since 12/06/07
	 */
	public static Date parseDateUTC(String date)
	{		
		date = date.substring(4);
		date = date.replace(UTC_0800, "");
		for(int i = 0; i < MONTHS_STRING.length; i++)	
		{
			if( date.startsWith(MONTHS_STRING[i]) )
			{
				date = date.replace(MONTHS_STRING[i], String.valueOf(i + 1));
				break;
			}	
		}
		
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("MM dd HH:mm:ss yyyy");		
			return df.parse(date);
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	/**
	 * <b> 将默认格式(<code>yyyy-MM-dd</code>)的日期字符串转换成<code>java.util.Date</code>类型
	 * </b>
	 * 
	 * @param date
	 *            日期字符串
	 * @return java.util.Date
	 */
	public static Date parseDate(String date) {
		return parseDate(date, PATTERN_DEFAULT);
	}

	/**
	 * 根据日期得到当天结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getEndDay(Date date) {
		return formatDate(date) + " 23:59:59";
	}

	/**
	 * 根据指定格式,格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            指定格式,参照DateTimeUtil类中常量定义
	 * @return java.util.Date
	 */
	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 取得今年年底日期,返回Date对象
	 */
	public static Date getEndOfYear() {
		return parseDate(Calendar.getInstance().get(Calendar.YEAR) + "-12-31");// 停止计费时间为年底
	}

	/**
	 * 取得给定年份的年底日期,返回Date对象
	 * 
	 * @param year
	 *            给定年份
	 */
	public static Date getEndOfSpecifyYear(String year) {
		return parseDate(year + "-12-31");// 停止计费时间为年底
	}

	/**
	 * 取得今年年底日期,返回字符串
	 */
	public static String getEndOfYearStr() {
		return Calendar.getInstance().get(Calendar.YEAR) + "-12-31";// 停止计费时间为年底
	}

	/**
	 * 取得当前日期的下个月末的日期,返回Date对象
	 * @throws Exception
	 */
	public static Date getEndOfNextMonth() throws Exception {
		Calendar calendar = Calendar.getInstance();
		int nextMonth = calendar.get(Calendar.MONTH) + 2;
		Date startOfNextMonth = parseDate(calendar.get(Calendar.YEAR) + "-"
				+ nextMonth + "-1");

		int nextMonthDays = getDateDays(startOfNextMonth);

		return parseDate(calendar.get(Calendar.YEAR) + "-" + nextMonth + "-"
				+ nextMonthDays);
	
		
//		return getEndOfNextMonth(new Date());	
	}
	
	/**
	 * 取得给定日期的下个月末的日期,返回Date对象
	 * 
	 * @param date
	 *            给定日期
	 * @throws Exception
	 */
	public static Date getEndOfNextMonth(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();//		
		calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        
		int nextMonth = calendar.get(Calendar.MONTH) + 2;
		
		if(nextMonth>12){
			nextMonth = nextMonth - 12 ;
			year = year + 1 ;
		}
		else{
			
		}
		Date startOfNextMonth = parseDate(year + "-"+ nextMonth + "-1");// 停止计费时间为年底
		int nextMonthDays = getDateDays(startOfNextMonth);
		return parseDate(year + "-" + nextMonth + "-"+ nextMonthDays);
		
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.add(Calendar.MONTH, 1);
//		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));	
//		calendar.set(Calendar.HOUR_OF_DAY, 0);	
//		calendar.set(Calendar.MINUTE, 0);	
//		calendar.set(Calendar.SECOND, 0);	
//		
//		return calendar.getTime();	
	}
	/**
	 * 判断两个日期的大小关系,忽略时分秒，只比较年月日
	 * 
	 * @param d1
	 * @param d2
	 * @return -1 d1在d2之前, 0 d1与d2相等, 1 d1在d2之后
	 * @throws Exception
	 */
	public static int DateRelation(Date d1, Date d2) throws Exception {
		if (d1 == null)
			throw new Exception("判断两个日期的大小关系时d1不能为空!");
		if (d2 == null)
			throw new Exception("判断两个日期的大小关系时d2不能为空!");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		c1.set(Calendar.HOUR, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		c2.set(Calendar.HOUR, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& c1.get(Calendar.DATE) == c2.get(Calendar.DATE))
			return 0;
		else if (c1.before(c2))
			return -1;
		else
			return 1;
	}

	/**
	 * 取得给定日期的年份
	 * 
	 * @param d
	 *            给定日期
	 * @throws Exception
	 */
	public static int getYear(Date d) throws Exception {
		if (d == null)
			throw new Exception("获取日期年份时d不能为空!");
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}
	
	public static Date getMaxDate() throws Exception {
		return parseDate("2999-12-31 23:59:59", PATTERN_DATETIME);
	}

	/**
	 * 根据默认格式(<code>yyyy-MM-dd</code>),格式化日期 
	 * 
	 * @param date
	 *            日期
	 * @return java.util.Date
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return null;
		return new SimpleDateFormat(PATTERN_DEFAULT).format(date);
	}
	
	/**
	 * 计算两个时间相差多少秒
	 * @param startDate
	 * @return
	 */
	public static long calLastedTime(Date startDate, Date endDate) {
		long a = endDate.getTime();
		long b = startDate.getTime();
		long c =  ((a - b) / 1000);
		return c;
	}

	/**
	 * 默认日期格式, <code>yyyy-MM-dd</code>
	 */
	public static final String PATTERN_DEFAULT = "yyyy-MM-dd";

	/**
	 * 路径格式, <code>yyyy\MM\dd\</code>
	 */
	public static final String PATTERN_DAYPATH = "yyyy\\MM\\dd\\";

	/**
	 * 日期时间格式, <code>yyyy-MM-dd HH:mm:ss</code>, 24小时制
	 */
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 无间隔符的日期时间格式, <code>yyyyMMddHHmmss</code>, 24小时制
	 */
	public static final String PATTERN_DATETIME_COMPACT = "yyyyMMddHHmmss";

	/**
	 * 无间隔符日期格式, <code>yyyyMMdd</code>
	 */
	public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";

	/**
	 * 无间隔符日期短格式, <code>yyMMdd</code>
	 */
	public static final String PATTERN_DATESHORT = "yyMMdd";

	/**
	 * 年月格式, <code>yyyyMM</code>
	 */
	public static final String PATTERN_YEARMONTH = "yyyyMM";	
}
