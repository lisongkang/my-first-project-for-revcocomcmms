<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-grid-biz-extendattr-prv-attrrule-index">
	<div class="tabbable tabbable-primary">
		<ul class="nav nav-pills">
			<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane fade active in">
				<div class="row search-form-default">
					<div class="col-md-12">
						<form action="#" method="get"
							class="form-inline form-validation form-search-init form-biz-salary-others-kpi-list"
							data-grid-search=".grid-biz-salary-others-kpi-list">
							<div class="form-group">
								<s:select id="areaid" name="othersKpi.areaid" cssStyle="width:250px"
								   list="{}" placeholder="请选择业务区..." />
							</div>
							<div class="form-group">
								<s:select id="grid" name="othersKpi.grid" cssStyle="width:250px"
									list="{}" placeholder="请选择网格..." />
							</div>
							<div class="form-group">
							<s:select id="operid" name="othersKpi.operid" cssStyle="width:250px"
									  list="{}" placeholder="请选择网格人员..." />
							</div>
							<button class="btn green" type="submit" id="btn_search">
								<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
							</button>
							<button class="btn default hidden-inline-xs" type="reset">
								<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
							</button>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="grid-biz-salary-others-kpi-list"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!--审核弹出框 -->
<div class="modal fade" id="batchAuditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">提交审核</h4>
			</div>
			<div class="modal-body" style="height: 180px">
				<div class="row">
					<div class="col-md-10">
						<div class="form-group">
							<label class="control-label">审核人</label>
							<div class="controls">
								<s:select id="id_batchAuditUser"  list="auditMap"  />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" name='btn_audit_submitKpi'
						onclick="SalaryOthersKpiList.batchAudit()">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<!--上传弹出框 -->
<div class="modal fade" id="batchUploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="uploadModalLabel">文件上传</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-5">
						<div class='form-group'>
						   <label class='control-label'>地市</label>
						   <div class='controls'>
							   <s:select id="excel_city" list="cityMap" cssStyle="width:200px"  />
						   </div>
						</div>
					</div>
					<div class="col-md-5">
						<div class="form-group">
							<label class="control-label">支公司</label>
							<div class="controls">
								<s:select id="excel_areaid"  list="{}" cssStyle="width:200px"  />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-5">
						<div class="form-group">
							<label class="control-label">审核人</label>
							<div class="controls">
								<s:select id="id_batchUploadAuditUser"  list="auditMap" cssStyle="width:200px"  />
							</div>
						</div>
					</div>
					<div class="col-md-5">
						<div class='form-group'>
							<label class='control-label'>上传文件</label>
							<div class='controls'>
								<input type='file' id='id_importExcel' name='myFile' style='display: block;'
									   accept='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel' />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
					<button type="button" style="float: right;margin-right: 10px;" class="btn btn-primary btn-sm"
						onclick="SalaryOthersKpiList.batchImport()">上传检查</button>
					</div>
				</div>
				<div class="row" style="margin-top: 5px;">
					<div class="col-md-12">
						<table class="grid-biz-salary-others-kpi-Excel" data-grid="table"></table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary"
						onclick="SalaryOthersKpiList.uploadExcel()">导入</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<script
	src="${base}/biz/salary/others-kpi-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>