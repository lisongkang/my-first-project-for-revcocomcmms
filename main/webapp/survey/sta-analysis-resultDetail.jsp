<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/echartconfig.jsp"%> 

结果明细
<script type="text/javascript">
	var sid = '<s:property value="#request.sid" />';
	var dataJson = '<s:property value="#request.dataJson" />'
</script>
 <script src="${base}/survey/sta-analysis-resultDetail.js"/>
 <%@ include file="/common/ajax-footer.jsp"%> 