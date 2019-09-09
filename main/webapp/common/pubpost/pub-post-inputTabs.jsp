<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
		<li class="tools pull-right"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
		<li class="active"><a data-toggle="tab"
			href="${base}/common/pubpost/pub-post!edit?id=<s:property value='#parameters.id'/>">基本信息</a></li>
		<li><a data-toggle="tab" data-tab-disabled="<s:property value='%{!persistentedModel}'/>" href="${base}/common/pubpost/pub-post!depts?id=<s:property value='#parameters.id'/>">关联发布部门</a></li>
	</ul>
</div>
