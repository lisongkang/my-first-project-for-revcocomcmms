<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div>网格列表</div>
<div class="row">
	<div class="col-md-12">
		<div class="controls">
			<table class="grid-auth-user-grids" data-grid="table"
				data-pk='<s:property value="#parameters.id"/>'></table>
		</div>
	</div>
</div>
<script>
	var operid = <s:property value="#parameters.id"/>;
</script>
<script src="${base}/prv/prv-operator-grids.js" />
<%@ include file="/common/ajax-footer.jsp"%>