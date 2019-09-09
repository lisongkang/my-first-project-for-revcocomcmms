package com.maywide.core.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class DateUtils {

    public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public final static DateFormat DEFAULT_TIME_FORMATER = new SimpleDateFormat(DEFAULT_TIME_FORMAT);

    public final static DateFormat DEFAULT_DATE_FORMATER = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    public final static String FORMAT_YYYY = "yyyy";

    public final static String FORMAT_YYYYMM = "yyyyMM";

    public final static String FORMAT_YYYYMMDD = "yyyyMMdd";
    
    public final static String FORMAT_YYYYMMDD_2 = "yyyy/MM/dd";

    public final static DateFormat FORMAT_YYYY_FORMATER = new SimpleDateFormat(FORMAT_YYYY);

    public final static DateFormat FORMAT_YYYYMM_FORMATER = new SimpleDateFormat(FORMAT_YYYYMM);

    public final static DateFormat FORMAT_YYYYMMDD_FORMATER = new SimpleDateFormat(FORMAT_YYYYMMDD);

    public final static DateFormat FORMAT_YYYYMMDD_FORMATER_2 = new SimpleDateFormat(FORMAT_YYYYMMDD_2);
    
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return DEFAULT_DATE_FORMATER.format(date);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return DEFAULT_TIME_FORMATER.format(date);
    }

    public static String formatDateNow() {
        return formatDate(new Date());
    }

    public static String formatTimeNow() {
        return formatTime(new Date());
    }

    public static Date parseDate(String date, DateFormat df) {
        if (date == null) {
            return null;
        }
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseTime(String date, DateFormat df) {
        if (date == null) {
            return null;
        }
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDate(String date) {
        return parseDate(date, DEFAULT_DATE_FORMATER);
    }

    public static Date parseTime(String date) {
        return parseTime(date, DEFAULT_TIME_FORMATER);
    }

    public static String plusOneDay(String date) {
        DateTime dateTime = new DateTime(parseDate(date).getTime());
        return formatDate(dateTime.plusDays(1).toDate());
    }

    public static String plusOneDay(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        return formatDate(dateTime.plusDays(1).toDate());
    }

    public static String getHumanDisplayForTimediff(Long diffMillis) {
        if (diffMillis == null) {
            return "";
        }
        long day = diffMillis / (24 * 60 * 60 * 1000);
        long hour = (diffMillis / (60 * 60 * 1000) - day * 24);
        long min = ((diffMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long se = (diffMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day + "D");
        }
        DecimalFormat df = new DecimalFormat("00");
        sb.append(df.format(hour) + ":");
        sb.append(df.format(min) + ":");
        sb.append(df.format(se));
        return sb.toString();
    }

    /**
     * 把类似2014-01-01 ~ 2014-01-30格式的单一字符串转换为两个元素数组
     */
    public static String[] parseBetweenDates(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        String[] values = date.split("～");
        values[0] = values[0].trim();
        values[1] = values[1].trim();
        return values;
    }
    
    public static String getFormatDateString(Date date, String pattern){
    	if (FORMAT_YYYYMM.equals(pattern)) {
    		return FORMAT_YYYYMM_FORMATER.format(date);
    	} else if (FORMAT_YYYYMMDD.equals(pattern)) {
    		return FORMAT_YYYYMMDD_FORMATER.format(date);
    	} else if (FORMAT_YYYY.equals(pattern)) {
    		return FORMAT_YYYY_FORMATER.format(date);
    	} else if (DEFAULT_DATE_FORMAT.equals(pattern)) {
    		return DEFAULT_DATE_FORMATER.format(date);
    	} else if (DEFAULT_TIME_FORMAT.equals(pattern)) {
    		return DEFAULT_TIME_FORMATER.format(date);
    	} else {
    		return DEFAULT_TIME_FORMATER.format(date);
    	}
    }
    
    /**
     * 判断两个日期是否为同一天
     * @param data1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date data1,Date date2){
    	Calendar calendar1 = Calendar.getInstance();
    	calendar1.setTime(data1);
    	
    	Calendar calendar2 = Calendar.getInstance();
    	calendar2.setTime(date2);
    	
    	return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) &&
    			(calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) &&
    			(calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH));
    }
    
    
    /**
     * 判断时间是否在某个月份内
     * @param date
     * @param time 0为当前,正值为接下来的，负值为之前的月份
     * @return
     */
    public static boolean isInSomeMonth(Date date,int current){
    	Calendar firstC= Calendar.getInstance();
    	firstC.add(Calendar.MONTH, current);
    	firstC.set(Calendar.DAY_OF_MONTH,1); //为选定的月份的第一天
    	Date first = firstC.getTime();
    	
    	Calendar lastC = Calendar.getInstance();
    	lastC.add(Calendar.MONTH, current);
    	lastC.set(Calendar.DAY_OF_MONTH, lastC.getActualMaximum(Calendar.DAY_OF_MONTH)); 
    	Date last = lastC.getTime();
    	
    	return date.after(first)&&date.before(last);
    }

	public static Date getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getBeforeNumberDate(int num){
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE,now.get(Calendar.DATE)-num);
		return now.getTime();
	}
	
	public static String getPreNMonth(int n,Date date,String format){
		Calendar ca= Calendar.getInstance();
		ca.setTime(date);
    	ca.add(Calendar.MONTH, n);
    	String time = getFormatDateString(ca.getTime(), format);
    	return time;
	}
	
	/**
	 * 
	 * @param current 0为当前月,负数为前，正数为后
	 * @return
	 */
	public static String getMonthFirst(int current,Date date){
		Calendar ca= Calendar.getInstance();
		ca.setTime(date);
    	ca.add(Calendar.MONTH, current);
    	ca.set(Calendar.DAY_OF_MONTH,1);
    	ca.set(Calendar.HOUR_OF_DAY, 0);
    	String time = FORMAT_YYYYMMDD_FORMATER.format(ca.getTime());
    	return time;
	}
	
	public static String getMonthLastest(int current,Date date){
		Calendar ca= Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, current);
    	ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
    	String time = FORMAT_YYYYMMDD_FORMATER.format(ca.getTime());
    	return time;
	}
	
	
	public static String getCurrentDate() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd");
	}

	public static String getNextDate() {
		return getFormatDateAdd(new Date(), 1, "yyyy-MM-dd");
	}

	public static String getFormatDateAdd(Date date, int amount, String format) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return getFormatDateTime(cal.getTime(), format);
	}

	public static String getFormatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
    
}
