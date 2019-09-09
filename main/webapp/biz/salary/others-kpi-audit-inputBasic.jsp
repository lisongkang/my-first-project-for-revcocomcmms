<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-others-kpi-audit" action="${base}/biz/salary/others-kpi-audit!doSave"
	method="post">
	<div class="form-body">
    <s:hidden name="id" />
    <s:hidden name="textConfigIds" />
    <s:hidden name="scores" />
    <s:hidden name="remarks" />
    <s:hidden name="status" />
    <div class="row">
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">分公司</label>
				<div class="controls">
	                <s:select name="city" id="id_city" value="extraAttributes.city" list="cityMap" disabled="true" />
				</div>
			</div>
		</div>
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">支公司</label>
				<div class="controls">
					<s:select name="areaid" id="id_areaid" value="extraAttributes.areaid"  list="areaMap2" disabled="true" />
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">网格</label>
				<div class="controls">
					<s:hidden name="grid" id="hid_grid" disabled="true" />
					<s:select name="grid" id="id_grid"  list="{}" disabled="true" />
				</div>
			</div>
		</div>
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">网格人员</label>
				<div class="controls">
					<s:hidden name="operid" id="hid_operid" disabled="true" />
					<s:select name="operid" id="id_operid" list="{}" disabled="true" />
				</div>
			</div>
		</div>
	</div>

    <div class="row">
        <div class="col-md-5">
			  <div class="form-group">   
			    <label class="control-label">月份</label>
			    <div class="controls">
					<s3:datetextfield id="id_dateMonth" name="dateMonth" format="yyyy-MM-dd HH:mm:00" data-timepicker="true" disabled="true" />
		        </div>
		      </div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<table class="grid-biz-salary-others-kpi-audit-detail" data-grid="table"></table>
		</div>
	</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue submitbtn" type="submit" name='btn_submitKpiAudit_Y' data-grid-reload=".grid-biz-salary-others-kpi-audit-list">
			<i class="fa fa-check"></i> 审核通过
		</button>
        <button class="btn blue submitbtn" type="submit" name='btn_submitKpiAudit_N' data-grid-reload=".grid-biz-salary-others-kpi-audit-list">
            <i class="glyphicon glyphicon-remove"></i> 审核拒绝
        </button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>


</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/others-kpi-audit-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>