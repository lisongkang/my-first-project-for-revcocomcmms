<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation"
	action='${base}/sys/util!reissueFile' method="post">
	<div class="form-actions">
		<button class="btn blue" type="submit">
			<i class="fa fa-check"></i> 补发文件
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
		<div class="alert alert-info">
			<p>请选择需要补发对账文件的日期</p>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">补发日期</label>
					<div class="controls">
						<s3:datetextfield name="reissueDate" format="date" required="true"
							placeholder="补发日期" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit">
			<i class="fa fa-check"></i> 补发文件
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<%@ include file="/common/ajax-footer.jsp"%>