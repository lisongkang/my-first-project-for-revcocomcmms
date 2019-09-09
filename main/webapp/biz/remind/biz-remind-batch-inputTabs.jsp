<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" data-tab-disabled="<s:property value='%{!persistentedModel}'/>" href="${base}/biz/remind/biz-remind-record!toRecord?batid=<s:property value="model.id"/>">明细信息</a></li>	
	    <li class="tools pull-right"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
	</ul>
</div>
<%@ include file="/common/ajax-footer.jsp"%>