<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
	    <li><a data-toggle="tab" data-tab-disabled="<s:property value='%{!persistentedModel}'/>" href="${base}/market/cust-marketing!toQueMarketPush?taskId=<s:property value="model.id"/>">查询营销推送</a></li>
		<li class="active"><a data-toggle="tab" data-tab-disabled="<s:property value='%{!persistentedModel}'/>" href="${base}/market/market-batch!toMarketPush?taskId=<s:property value="model.id"/>">营销推送</a></li>
	    <li class="tools pull-right" style="opacity:0"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
</div>
<%@ include file="/common/ajax-footer.jsp"%>