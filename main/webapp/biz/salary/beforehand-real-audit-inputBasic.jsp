<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div id="others-beforehand-real-cachedata" />
<form class="form-horizontal form-bordered form-label-stripped form-validation form-beforehand-real-audit" action="${base}/biz/salary/beforehand-real-audit!doSave"
	method="post">
	<div class="form-body">
		<s:hidden name="id"  />
		<s:hidden name="status" id="id_status" />
    <div class="row">
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">分公司</label>
				<div class="controls">
					<s:hidden name="city"  class="form-control" />
	                <s:select name="city" id="id_city" list="cityMap" disabled="true" class="form-control" />
				</div>
			</div>
		</div>
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">支公司</label>
				<div class="controls">
					<s:hidden name="areaid"   class="form-control" />
					<s:select name="areaid" id="id_areaid" list="areaMap" disabled="true" class="form-control" />
				</div>
			</div>
		</div>
	</div>

		<div class="row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">网格</label>
					<div class="controls">
						<s:hidden name="grid" id="val_grid"    class="form-control" />
						<s:select name=".grid" id="id_grid" list="{}" disabled="true" class="form-control" />
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">网格人员</label>
					<div class="controls">
						<s:hidden name="operid" id="val_operid"   class="form-control" />
						<s:textfield  id="show_operid" disabled="true" class="form-control" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">月份</label>
					<div class="controls">
						<s:hidden name="dateMonth" class="form-control" />
						<s:textfield  id="id_cycleid" name="dateMonth" disabled="true" class="form-control" />
					</div>
				</div>
			</div>
		</div>

	<div class="row" style="margin-top: 20px;">
		<div class="col-md-12">
			<table class="grid-biz-salary-beforehand-real-detail" data-grid="table"></table>
		</div>
	</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue submitbtn" type="submit" onclick="OthersBeforehandRealAuditInputBasic.doSubmit('1')"
				data-grid-reload=".grid-biz-salary-beforehand-real-audit-list">
			<i class="fa fa-check"></i> 审核通过
		</button>
		<button class="btn blue submitbtn" type="submit" onclick="OthersBeforehandRealAuditInputBasic.doSubmit('2')"
				data-grid-reload=".grid-biz-salary-beforehand-real-audit-list">
			<i class="glyphicon glyphicon-remove"></i> 审核拒绝
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>





</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/beforehand-real-audit-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>