<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
	    <li><a data-toggle="tab" data-tab-disabled="<s:property value='%{!persistentedModel}'/>" href="${base}/biz/ass/topatch/ass-index-topatch!toStoreInfo?storeId=<s:property value="model.id"/>">详情</a></li>
		<li class="active"><a data-toggle="tab" data-tab-disabled="<s:property value='%{!persistentedModel}'/>" href="${base}/biz/ass/topatch/ass-index-topatch!topatchTask?storeId=<s:property value="model.id"/>">任务下达</a></li>
	    <li><a data-toggle="tab" data-tab-disabled="<s:property value='%{!persistentedModel}'/>" href="${base}/biz/ass/topatch/ass-index-topatch!taskTopatchList?storeId=<s:property value="model.id"/>">已下达列表</a></li>
	    <li class="tools pull-right" style="opacity:0"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
	</ul>
</div>
<%@ include file="/common/ajax-footer.jsp"%>