<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab"
			href="${base}/ad/adset/ad-set!edit?id=<s:property value='#parameters.id'/>&clone=<s:property value='#parameters.clone'/>">基本信息</a></li>
	</ul>
</div>
<%@ include file="/common/ajax-footer.jsp"%>