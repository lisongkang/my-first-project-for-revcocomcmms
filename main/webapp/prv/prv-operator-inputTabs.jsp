<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
		<li class="tools pull-right"><a href="javascript:;"
			class="btn default reload"><i class="fa fa-refresh"></i></a></li>
		<li class="active"><a data-toggle="tab"
			href="${base}/prv/prv-operator!edit?id=<s:property value='#parameters.id'/>&clone=<s:property value='#parameters.clone'/>">基本信息</a></li>
		<li><a data-toggle="tab"
			data-tab-disabled="<s:property value='%{!persistentedModel}'/>"
			href="${base}/prv/prv-operator!privs?id=<s:property value='#parameters.id'/>">关联权限</a></li>
		<li><a data-toggle="tab"
			data-tab-disabled="<s:property value='%{!persistentedModel}'/>"
			href="${base}/prv/prv-operator!grids?id=<s:property value='#parameters.id'/>">网格权限</a></li>
		<s:if test="model">
		<li><a data-toggle="tab"
		data-tab-disabled="<s:property value='%{!persistentedModel}'/>"
		href="${base}/prv/prv-operator!portalinfo?id=<s:property value='#parameters.id'/>">portal信息</a></li>
		</s:if>
	</ul>
</div>
<%@ include file="/common/ajax-footer.jsp"%>