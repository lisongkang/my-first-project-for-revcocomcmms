package com.maywide.core.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtils {
	
	private XmlUtils(){}
	
	 public static String toXml(Object obj) {
	        XStream xstream = new XStream(new DomDriver("utf8"));
	        xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
	        /*
	         // 以压缩的方式输出XML
	         StringWriter sw = new StringWriter();
	         xstream.marshal(obj, new CompactWriter(sw));
	         return sw.toString();
	         */
	        // 以格式化的方式输出XML
	        return xstream.toXML(obj);
	    }
	 
	 public static <T> T toBean(String xmlStr, Class<T> cls) {
	        XStream xstream = new XStream(new DomDriver());
	        xstream.processAnnotations(cls);
	        @SuppressWarnings("unchecked")
	        T t = (T) xstream.fromXML(xmlStr);
	        return t;
	    }

}
