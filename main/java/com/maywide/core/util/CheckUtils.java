package com.maywide.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.maywide.core.exception.BusinessException;

public class CheckUtils {
	//--后续可改造成，抛出自定义异常，以支持自定义异常代码
	public static boolean checkEmpty(String s, String msg) throws Exception {
		if(StringUtils.isEmpty(s))
			throw new BusinessException(msg);
		return true;
	}
	
	public static boolean checkNull(Object obj, String msg) throws Exception{
		if(obj == null)
			throw new BusinessException(msg);
		return true;
	}
	
	public static int arraysIndexOf(String[] array, String obj) throws Exception{
		int index = -1;
		for (int i = 0; i < array.length; i++) {
			if(array[i].equals(obj)) {
				index = i;
				break;
			}
		}
		return index;
	}
	public static boolean isPhone(String phone,String regex) {
//        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if(!phone.substring(0,1).equals("1")){
			return false;
		}
		if (phone.length() != 11) {
//            System.out.println("手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            /*if (!isMatch) {
               System.out.println("请填入正确的手机号");
            }*/
            return isMatch;
        }
    }
}
