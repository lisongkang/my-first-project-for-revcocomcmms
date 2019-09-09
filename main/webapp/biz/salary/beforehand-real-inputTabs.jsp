<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab"
			href="${base}/biz/salary/beforehand-real!edit?operid=<s:property value='#parameters.operid'/>
			&city=<s:property value='#parameters.city'/>&areaid=<s:property value='#parameters.areaid'/>
			&grid=<s:property value='#parameters.grid'/>&cycleid=<s:property value='#parameters.cycleid'/>
			&status=<s:property value='#parameters.status'/>&clone=<s:property value='#parameters.clone'/>">预转实积分</a></li>
	</ul>
</div>
<%@ include file="/common/ajax-footer.jsp"%>