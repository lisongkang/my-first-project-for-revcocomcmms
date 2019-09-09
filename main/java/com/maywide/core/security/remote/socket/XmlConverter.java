package com.maywide.core.security.remote.socket;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XmlConverter {

	private static final String HEADER_FORMAT = "00000000";
	private static final int HEADER_LENGTH = 8;

	private XStream xstream;

	public XmlConverter() {
		xstream = new XStream() {

			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {

					public boolean shouldSerializeMember(Class definedIn,
							String fieldName) {
						return definedIn != Object.class ? super
								.shouldSerializeMember(definedIn, fieldName)
								: false;
					}

				};
			}

		};

		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias("request", ServiceRequest.class);
		xstream.alias("response", ServiceResponse.class);
		xstream.registerConverter(new DateConverter());
	}

	protected String toCompactXML(Object in) {
		StringWriter out = new StringWriter();
		CompactWriter writer = new CompactWriter(out);
		String result = "";
		try {
			xstream.marshal(in, writer);
			Pattern pattern = Pattern.compile(" class=\"[^\"]+\"");
			Matcher matcher = pattern.matcher(out.toString());
			result = matcher.replaceAll("");

			Pattern p = Pattern.compile("com+[.]+maywide+[^>]*");
			Matcher m = p.matcher(result);
			result = m.replaceAll("void");
		} finally {
			writer.flush();
		}

		return result;
	}
	
	protected String toCompactXML2(Object in) {
		StringWriter out = new StringWriter();
		CompactWriter writer = new CompactWriter(out);
		String result = "";
		try {
			xstream.marshal(in, writer);
			Pattern pattern = Pattern.compile(" class=\"[^\"]+\"");
			Matcher matcher = pattern.matcher(out.toString());
			result = matcher.replaceAll("");

			Pattern p = Pattern.compile("com+[.]+maywide+[^>]*");
			Matcher m = p.matcher(result);
			result = m.replaceAll("request");
		} finally {
			writer.flush();
		}

		return result;
	}

	public Object fromXml(String xml, ServiceResponse response)
			throws Exception {
		try {
			if (xml.indexOf("<output>") > -1) {
				if (response.getOutput() != null) {
					while (xml.toLowerCase().indexOf("void") > 0) {
						Pattern pattern = Pattern
								.compile("<[^>]+>[^>]*<void>.*</void>");
						Matcher matcher = pattern.matcher(xml);
						if (matcher.find()) {
							String s = matcher.group();
							String varName = "";
							varName = s.substring(0, s.indexOf("<void>"));
							varName = varName.trim().replace("<", "");
							varName = varName.trim().replace(">", "");

							if (!varName.equals("")) {
								Class clazz = null;
								Field field = null;
								try{
									field = response.getOutput().getClass()
										.getDeclaredField(varName);
									if (field.getType()
											.equals(java.util.List.class)) {
										Type t = field.getGenericType();
										if (t instanceof ParameterizedType) {
											ParameterizedType pType = (ParameterizedType) t;
											Type type = pType
													.getActualTypeArguments()[0];
											clazz = (Class) type;
										} else {
											throw new Exception(
													"无法转换xml，对象中含有List对象没有指定泛型");
										}
									} else {
										clazz = field.getType().getComponentType();
									}
									String result = s.replaceAll("void",
											clazz.getName());
									xml = xml.replace(s, result);
								}catch(NoSuchFieldException e){
									String result = s.replaceAll("void","java.lang.String");
									xml = xml.replace(s, result);
								}
							}

							// System.out.println(xml);
						}
					}

					xml = xml.replaceAll("<output", "<output class=\""
							+ response.getOutput().getClass().getName() + "\"");
				} else {
					xml = xml.replaceAll("<output.*</output>", "");
				}
			}
			xstream.fromXML(xml, response);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected static String formatLengthHeader(int len) {
		String s = HEADER_FORMAT + (len + HEADER_LENGTH);
		return s.substring(s.length() - HEADER_LENGTH);
	}

	
    public static Object toEntity(String inputXML,Class[] objs){
        XStream xs = new XStream();
        xs.setMode(XStream.NO_REFERENCES);
        //注册使用了注解的VO
        xs.processAnnotations(objs); 
        Object reobj = (Object)xs.fromXML(inputXML);
        return reobj;
    } 
    
    public static String toXML(Class[] objs,Object obj){
        XStream xStream = new XStream();
        xStream.setMode(XStream.NO_REFERENCES);
        //注册使用了注解的VO
        xStream.processAnnotations(objs);
        String xml = xStream.toXML(obj);
        return xml;
    }
	public static String toQueWSXML(Object in) {
		StringWriter out = new StringWriter();
		CompactWriter writer = new CompactWriter(out);
		String result = "";
		try {
			XStream xstream = new XStream();
			xstream.marshal(in, writer);
			Pattern pattern = Pattern.compile(" class=\"[^\"]+\"");
			Matcher matcher = pattern.matcher(out.toString());
			result = matcher.replaceAll("");

			Pattern p = Pattern.compile("com+[.]+maywide+[^>]*");
			Matcher m = p.matcher(result);
			result = m.replaceAll("request-content");
		} finally {
			writer.flush();
		}

		return result;
	}     
}