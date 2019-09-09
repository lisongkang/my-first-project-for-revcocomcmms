<%@page import="java.net.InetAddress"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="s3" uri="/WEB-INF/tld/struts3-tags.tld"%>
<%@ taglib prefix="data" tagdir="/WEB-INF/tags/staticdata"%>
<%@ page isELIgnored="false" %>
<!-- pageContext.setAttribute("ctxIP", InetAddress.getLocalHost().getHostAddress()); -->
<%
    pageContext.setAttribute("base", request.getContextPath());
    pageContext.setAttribute("ctxPort", request.getLocalPort());
    pageContext.setAttribute("buildVersion", new Date().getTime());
%>