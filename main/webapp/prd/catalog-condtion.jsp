<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form id="form-grid-managers" class="form-horizontal form-bordered form-label-stripped"
	action="#" method="post">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
		<div class="form-actions">
			条件列表
		</div>
        <div class="row">
            <div class="col-md-12">
				<div class="controls">
	                <table class="grid-prd-catalog-condtion" data-grid="table"
						data-pk='<s:property value="#parameters.id"/>'></table>                 
				</div>
            </div>
        </div>
	</div>
</form>
<script>var catalogId = <s:property value="#parameters.id"/>;</script>
<script src="${base}/prd/catalog-condtion.js" />
<%@ include file="/common/ajax-footer.jsp"%>