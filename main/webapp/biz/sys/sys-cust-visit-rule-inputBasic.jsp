<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation form-biz-sys-sys-cust-visit-rule-inputBasic"
	action="${base}/biz/sys/sys-cust-visit-rule!doSave" method="post"
	data-editrulesurl="${base}/biz/sys/sys-cust-visit-rule!buildValidateRules">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
		<div class="col-md-8">
			<div class="form-group">
				<label class="control-label">地市</label>
				<div class="controls">
					<s:select name="city" list="cityMap" />
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div class="form-group">
				<label class="control-label">业务类型</label>
				<div class="controls">
					<s:select name="opcode" list="opcodeMap" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8">
				<div class="form-group">
					<label class="control-label">发送方式</label>
					<div class="controls">
						<s:select name="sendMethod" list="sendMethodMap" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">内容模板</label>
					<div class="input-icon right">
						<i class="fa fa-ellipsis-horizontal fa-select-template"></i>
						<s:textfield name="rule.template" />
						<s:hidden name="contentTemplateId" />
					</div>
					<div class="controls"></div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">延迟时间</label>
					<div class="controls">
						<s:textfield name="delayValue" maxlength="16" />
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">最大次数</label>
					<div class="controls">
						<s:textfield name="maxTimes" maxlength="9" />
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div class="form-group">
				<label class="control-label">时间类型</label>
				<div class="controls">
					<s:select name="delayType" list="typeMap" />
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div class="form-group">
				<label class="control-label">号码索引</label>
				<div class="controls">
					<s:select name="mobileIndex" list="indexMap" />
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit"
			data-grid-reload=".sys-sys-cust-visit-rule-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/sys/sys-cust-visit-rule-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>