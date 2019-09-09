<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation form-biz-sys-sys-cust-rule-inputBasic"
	action="${base}/biz/sys/return-visit-template!doSave" method="post"
	data-editrulesurl="${base}/biz/sys/return-visit-template!buildValidateRules">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
		<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<label class="control-label">推送类型</label>
				<div class="controls">
					<s:select name="templateType" list="typeMap" />
				</div>
			</div>
		</div>
		</div>
		<div class="row">
			<div class="col-md-8">
				<div class="form-group">
					<label class="control-label">标题</label>
					<div class="controls">
						<s:textfield name="templateTitle" maxlength="20" />
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="form-group">
					<label class="control-label">内容</label>
					<div class="controls">
						<s:textfield name="templateContent" maxlength="40" />
					</div>
				</div>
			</div>
		</div>
			
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit"
			data-grid-reload=".sys-return-visit-template-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/sys/return-visit-template-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>