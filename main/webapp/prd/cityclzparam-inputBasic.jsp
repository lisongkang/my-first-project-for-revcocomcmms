<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation form-cityclzparam-inputBasic"
	action="${base}/prd/cityclzparam!doSave" method="post">
	<div class="form-body">
		<s:hidden name="id" />
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">资源大类</label>
					<div class="controls">
						<s:select name="kind" id="kind" list="objtype" required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">地市</label>
					<div class="controls">
						<s:select id="city" name="city" list="areaMap" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">区域</label>
					<div class="controls">
						<s:select id="id_areaid" name="areaid" list="townMap" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">

			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">资源小类编号</label>
					<div class="controls">
						<s:textfield id="id_subkind" name="subkind" type="text"
							class="form-control" maxlength="150" required="true" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">资源小类名称</label>
					<div class="controls">
						<s:textfield id="id_subname" name="subname" type="text"
							class="form-control" maxlength="150" required="true" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitAdConfig'
			data-grid-reload="form-cityclzparam-inputBasic">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script type="text/javascript">
	
</script>
<script src="${base}/prd/cityclzparam-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>