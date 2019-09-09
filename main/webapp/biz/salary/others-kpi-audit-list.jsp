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
							class="form-inline form-validation form-search-init form-biz-salary-others-kpi-audit-list"
							data-grid-search=".grid-biz-salary-others-kpi-audit-list">
							<div class="form-group">
								<s:select id="areaid" name="othersKpiAudit.areaid" cssStyle="width:250px"
								   list="{}" placeholder="请选择业务区..." />
							</div>
							<div class="form-group">
								<s:select id="grid" name="othersKpiAudit.grid" cssStyle="width:250px"
									list="{}" placeholder="请选择网格..." />
							</div>
							<div class="form-group">
							<s:select id="operid" name="othersKpiAudit.operid" cssStyle="width:250px"
									  list="{}" placeholder="请选择网格人员..." />
							</div>
							<div class="form-group">
								<s:select  name="othersKpiAudit.status" cssStyle="width:250px"
										  list="statusMap" value="1" placeholder="请选择状态..." />
							</div>
							<div class="form-group" style="width:250px">
								<s3:datetextfield  id="dateMonth" name="dateMonth" placeholder="请选择月份..." format="yyyy-MM-dd HH:mm:00" data-timepicker="true" />
							</div>
							<span style="float: right;">
								<button class="btn green" type="submit" id="btn_search">
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
								<li><a href="javascript:SalaryOthersKpiAuditList.batchAudit('2')">审核通过</a></li>
								<li><a href="javascript:SalaryOthersKpiAuditList.batchAudit('3')">审核拒绝</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<table class="grid-biz-salary-others-kpi-audit-list"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script
	src="${base}/biz/salary/others-kpi-audit-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>