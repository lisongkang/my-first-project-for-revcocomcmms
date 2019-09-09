<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div id="others-kpi-cachedata" />
<form class="form-horizontal form-bordered form-label-stripped form-validation form-others-kpi" action="${base}/biz/salary/others-kpi!doSave"
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
	                <s:select name="city" id="id_city" list="cityMap"  required="true" />
				</div>
			</div>
		</div>
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">支公司</label>
				<div class="controls">
					<s:select name="areaid" id="id_areaid" value="extraAttributes.areaids"  list="areaMap2"  required="true" />
				</div>
			</div>
		</div>
	</div>
		<div class="row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">月份</label>
					<div class="controls">
						<s3:datetextfield id="id_dateMonth" name="dateMonth" format="yyyy-MM-dd HH:mm:00" data-timepicker="true" required="true" />
					</div>
				</div>
			</div>
		</div>
		<h6 style="color: #00BBFF">网格配置</h6>
		<div id="grid_div" style=" border:1px solid #ddd">
		<div class="row grid_row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">网格</label>
					<div class="controls grid_controls">
						<s:hidden name="grid" disabled="true" />
						<s:select name="grids" list="{}"  required="true" />
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">网格人员</label>
					<div class="controls operid_controls">
						<s:hidden name="operid" disabled="true" />
						<s:select name="operids"  list="{}"  required="true" />
					</div>
				</div>
			</div>
			<div class="col-md-2 grid-option">
				<div class="form-group" style="margin-top: 12px;">
					<a href="javascript:OthersKpiInputBasic.addGridDiv()"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
					&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="delete_grid"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></a>
				</div>
			</div>
		</div>
		</div>
		<%--<div class="row grid_clone">--%>
			<%--<div class="col-md-5">--%>
				<%--<div class="form-group">--%>
					<%--<label class="control-label">网格</label>--%>
					<%--<div class="controls">--%>
						<%--&lt;%&ndash;<s:hidden name="grid" disabled="true" />&ndash;%&gt;--%>
						<%--<select name="grid"  required="true"></select>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="col-md-5">--%>
				<%--<div class="form-group">--%>
					<%--<label class="control-label">网格人员</label>--%>
					<%--<div class="controls">--%>
						<%--&lt;%&ndash;<s:hidden name="operid" disabled="true" />&ndash;%&gt;--%>
						<%--<select name="operid"  required="true"></select>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="col-md-2">--%>
				<%--<div class="form-group" style="margin-top: 12px;">--%>
					<%--<a href="javascript:OthersKpiInputBasic.addGridDiv()"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>--%>
					<%--&nbsp;&nbsp;&nbsp;--%>
					<%--<a href="#" class="delete_grid"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></a>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
	<h6 style="color: #00BBFF">积分配置</h6>
	<div class="row">
		<div class="col-md-12">
			<table class="grid-biz-salary-others-kpi-detail" data-grid="table"></table>
		</div>
	</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue submitbtn" type="button" data-toggle="modal" data-target="#duitModal">
			<i class="fa fa-check"></i> 保存并审核
		</button>
		<button class="btn blue submitbtn" type="submit" name='btn_submitKpi' data-grid-reload=".grid-biz-salary-others-kpi-list">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>

	<!--审核弹出框 -->
	<div class="modal fade" id="duitModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
									<s:select id="id_auditUser" name="auditUser" list="auditMap"  />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary" name='btn_audit_submitKpi'
							data-grid-reload=".grid-biz-salary-others-kpi-list">提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/others-kpi-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>