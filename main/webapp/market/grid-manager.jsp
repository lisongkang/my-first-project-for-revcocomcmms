<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form id="form-grid-managers" class="form-horizontal form-bordered form-label-stripped form-validation"
	action="${base}/market/grid!doSave" method="post">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
		<div class="form-actions">
			网格经理列表
		</div>
        <div class="row">
            <div class="col-md-12">
				<div class="controls">
	                <table class="grid-market-grid-manager" data-grid="table"
						data-pk='<s:property value="#parameters.id"/>'></table>                 
				</div>
            </div>
        </div>
	</div>
</form>
<script>var gridId = <s:property value="#parameters.id"/>;</script>
<script src="${base}/market/grid-manager.js" />
<%@ include file="/common/ajax-footer.jsp"%>