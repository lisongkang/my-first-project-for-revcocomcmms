<%@ tag pageEncoding="utf-8"%>
<%@ attribute name="className" type="java.lang.String" required="true"%>
<%@ attribute name="propertyName" type="java.lang.String" required="true"%>
<%@ attribute name="conditionProperty" type="java.lang.String" required="true"%>
<%@ attribute name="conditionValue" type="java.lang.String" required="true"%>
<%@ tag import="com.maywide.core.web.tag.TransProperty"%>
<%
	try {
		String mname = TransProperty.getInstance()
					   .transProperty(className, propertyName, conditionProperty, conditionValue);
		out.print(mname);
	} catch (Exception e) {
		e.printStackTrace();
		out.print("");
	}
%>