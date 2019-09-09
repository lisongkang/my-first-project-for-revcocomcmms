<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
		<li class="tools pull-right"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
		<li class="active"><a data-toggle="tab"
			href="${base}/market/grid!view?id=<s:property value='#parameters.id'/>">基本信息</a></li>
		<li><a data-toggle="tab" href="${base}/market/grid!manager?id=<s:property value='#parameters.id'/>">关联网格经理</a></li>
		<!-- <li><a data-toggle="tab" href="${base}/market/grid!patch?id=<s:property value='#parameters.id'/>">关联小区</a></li> -->
		<li><a data-toggle="tab" href="${base}/market/grid!relation?id=<s:property value='#parameters.id'/>">关联OSS网格</a></li>
	</ul>
</div>
