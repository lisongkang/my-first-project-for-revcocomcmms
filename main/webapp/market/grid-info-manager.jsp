<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div>网格经理列表</div>
<div class="row">
	<div class="col-md-12">
		<div class="controls">
			<table class="grid-market-grid-info-manager" data-grid="table"
				data-pk='<s:property value="#parameters.id"/>'></table>
		</div>
	</div>
</div>
<script>
	// gridId在对应js中使用
	var gridId = <s:property value="#parameters.id"/>;
</script>
<script src="${base}/market/grid-info-manager.js" />
<%@ include file="/common/ajax-footer.jsp"%>