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
							class="form-inline form-validation form-biz-salary-beforehand-real-list"
							data-grid-search=".grid-biz-salary-beforehand-real-list">
							<div class="form-group">
								<s:select id="areaid" name="beforehandRealBO.areaid" cssStyle="width:250px"
								   list="{}" placeholder="请选择业务区..."  />
							</div>
							<div class="form-group">
								<s:select id="grid" name="beforehandRealBO.grid" cssStyle="width:250px"
									list="{}" placeholder="请选择网格..."  />
							</div>
							<div class="form-group" style="width:250px">
								<s3:datetextfield  id="dateMonth" name="beforehandRealBO.cycleid"
												   data-timepicker="true"  placeholder="请选择月份..."   />
							</div>
							<div class="form-group">
								<s:select id="operid" name="beforehandRealBO.operid" cssStyle="width:250px"
										  list="{}" placeholder="请选择网格人员..." />
							</div>
							<div class="form-group">
								<s:select id="status" name="beforehandRealBO.status" cssStyle="width:250px"
										  list="#{0:'未审核',1:'审核通过',2:'审核不通过'}" placeholder="请选择状态..." />
							</div>
							<span style="float: right;">
									<button class="btn green" type="submmit"
											onclick="return SalaryBeforehandRealList.checkInput();">
										<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
									</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="grid-biz-salary-beforehand-real-list"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!--审核弹出框 -->
<div class="modal fade" id="batchDuitModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
								<s:select id="id_batchDuditUser"  list="auditMap"  />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" name='btn_audit_submitKpi'
						onclick="SalaryBeforehandRealList.batchAudit()">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<script
	src="${base}/biz/salary/beforehand-real-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>