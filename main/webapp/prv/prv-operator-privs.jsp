<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form id="form-operator-privs" class="form-horizontal form-bordered form-label-stripped form-validation"
	action="${base}/prv/prv-roleinfo!saveRolePrivs" method="post">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
		<div class="form-actions">
			权限列表
		</div>
        <div class="row">
            <div class="col-md-12">
				<div class="controls">
	                <table class="grid-prv-operator-privs" data-grid="table"
						data-pk='<s:property value="#parameters.id"/>'></table>                 
				</div>
            </div>
        </div>
	</div>
</form>
<script>var operid = <s:property value="#parameters.id"/>;</script>
<script src="${base}/prv/prv-operator-privs.js" />
<%@ include file="/common/ajax-footer.jsp"%>