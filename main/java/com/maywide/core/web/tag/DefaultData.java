package com.maywide.core.web.tag;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.maywide.core.cons.Constant;
import com.maywide.core.util.PropertyUtil;

public class DefaultData {
	private static DefaultData instance = null;
	
	private Properties propertiesCache;
	
	private DefaultData() {
		propertiesCache = new Properties();
	}

	public static DefaultData getInstance() {
		if (instance == null)
			instance = new DefaultData();
		return instance;
	}

	public String getData(String key) {
		try
        {
			loadPropertiesCache();
			String value = propertiesCache.getProperty(key);
			
            return value == null ? "" : value;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
	}
	
	private void loadPropertiesCache()throws Exception{
		if(propertiesCache.isEmpty()){
			String locale = PropertyUtil.getValueFromProperites(Constant.APPLICATION, "locale");
			String locale_properties = "";
			if(StringUtils.isNotBlank(locale)){
				locale_properties = "defaultval_"+locale+".properties";
			}else{
				locale_properties = "defaultval.properties";
			}
            try {
				propertiesCache.load(getClass().getClassLoader().getResourceAsStream(locale_properties));
			} catch (Exception e) {
				propertiesCache.load(getClass().getClassLoader().getResourceAsStream("defaultval.properties"));
			}
		}
	}
}
