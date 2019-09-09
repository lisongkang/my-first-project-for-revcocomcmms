<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div>关联地址列表</div>
<div class="row">
	<div class="col-md-12">
		<div class="controls">
			<table class="grid-market-grid-info-gridaddress" data-grid="table"
				data-pk='<s:property value="#parameters.id"/>'></table>
		</div>
	</div>
</div>
<script>
	// gridid在对应js中使用
	var gridid = <s:property value="#parameters.id"/>;
</script>
<script src="${base}/market/grid-info-gridaddress.js" />
<%@ include file="/common/ajax-footer.jsp"%>