package com.maywide.core.util;

import java.util.ResourceBundle;

/**
 * @author Jerry
 * 
 */
public class PropertyUtil {
	
	/**
	 * 读取properties配置文件的属性attribute的值
	 * 
	 * @return
	 */
	public static String getValueFromProperites(String properies, String attribute){
		 try {
			ResourceBundle myResource = ResourceBundle.getBundle(properies);
			 return myResource.getString(attribute);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
