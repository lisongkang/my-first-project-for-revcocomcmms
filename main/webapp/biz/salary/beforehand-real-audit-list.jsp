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
							class="form-inline form-search-init form-validation form-biz-salary-beforehand-real-audit-list"
							data-grid-search=".grid-biz-salary-beforehand-real-audit-list">
							<div class="form-group">
								<s:select id="areaid" name="beforehandRealAudit.areaid" cssStyle="width:250px"
								   list="{}" placeholder="请选择业务区..."  />
							</div>
							<div class="form-group">
								<s:select id="grid" name="beforehandRealAudit.grid" cssStyle="width:250px"
									list="{}" placeholder="请选择网格..."  />
							</div>
							<div class="form-group" style="width:250px">
								<s3:datetextfield  id="dateMonth" name="beforehandRealAudit.dateMonth"
												   data-timepicker="true"  placeholder="请选择月份..."   />
							</div>
							<div class="form-group">
								<s:select id="operid" name="beforehandRealAudit.operid" cssStyle="width:250px"
										  list="{}" placeholder="请选择网格人员..." />
							</div>
							<div class="form-group">
								<s:select id="status" name="beforehandRealAudit.status" value="0" cssStyle="width:250px"
										  list="#{0:'未审核',1:'审核通过',2:'审核不通过'}" placeholder="请选择状态..." />
							</div>
							<span style="float: right;">
									<button class="btn green" type="submmit"
											onclick="return SalaryBeforehandRealAuditList.checkInput();">
										<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
									</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
						</form>
					</div>
					<div class="col-md-12">
						<div class="dropdown">
							<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								批量审核
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="javascript:SalaryBeforehandRealAuditList.batchAudit('1')">审核通过</a></li>
								<li><a href="javascript:SalaryBeforehandRealAuditList.batchAudit('2')">审核拒绝</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="grid-biz-salary-beforehand-real-audit-list"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!--审核弹出框 -->
<%--<div class="modal fade" id="batchDuitModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
	<%--<div class="modal-dialog">--%>
		<%--<div class="modal-content">--%>
			<%--<div class="modal-header">--%>
				<%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
				<%--<h4 class="modal-title" id="myModalLabel">提交审核</h4>--%>
			<%--</div>--%>
			<%--<div class="modal-body" style="height: 180px">--%>
				<%--<div class="row">--%>
					<%--<div class="col-md-10">--%>
						<%--<div class="form-group">--%>
							<%--<label class="control-label">审核人</label>--%>
							<%--<div class="controls">--%>
								<%--<s:select id="id_batchDuditUser"  list="auditMap"  />--%>
							<%--</div>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="modal-footer">--%>
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>--%>
				<%--<button type="button" class="btn btn-primary" name='btn_audit_submitKpi'--%>
						<%--onclick="SalaryBeforehandRealAuditList.batchAudit()">提交</button>--%>
			<%--</div>--%>
		<%--</div><!-- /.modal-content -->--%>
	<%--</div><!-- /.modal -->--%>
<%--</div>--%>
<script
	src="${base}/biz/salary/beforehand-real-audit-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>