<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
		<li class="tools pull-right"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
		<li class="active"><a data-toggle="tab"
			href="${base}/log/trace!view?id=<s:property value='#parameters.id'/>">基本信息</a></li>
		<s:if test="%{orderid!=null}">
			<li><a data-toggle="tab" href="${base}/log/trace!orderview?orderid=<s:property value='orderid'/>">订单信息</a></li>
		</s:if>
		<s:if test="%{accesslogid!=null}">
			<li><a data-toggle="tab" href="${base}/log/trace!accessview?accesslogid=<s:property value='accesslogid'/>">接口信息</a></li>
		</s:if>
	</ul>
</div>
