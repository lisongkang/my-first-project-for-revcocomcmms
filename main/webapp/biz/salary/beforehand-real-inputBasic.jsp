<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div id="others-beforehand-real-cachedata" />
<form class="form-horizontal form-bordered form-label-stripped form-validation form-beforehand-real" action="${base}/biz/salary/beforehand-real-audit!doSave"
	method="post">
	<div class="form-body">
		<s:hidden name="beforehandRealBO.status" id="id_status" />
    <div class="row">
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">分公司</label>
				<div class="controls">
					<s:hidden name="beforehandRealBO.city"  class="form-control" />
	                <s:select name="beforehandRealBO.city" id="id_city" list="cityMap" disabled="true" class="form-control" />
				</div>
			</div>
		</div>
		<div class="col-md-5">
			<div class="form-group">
				<label class="control-label">支公司</label>
				<div class="controls">
					<s:hidden name="beforehandRealBO.areaid"   class="form-control" />
					<s:select name="beforehandRealBO.areaid" id="id_areaid" list="areaMap" disabled="true" class="form-control" />
				</div>
			</div>
		</div>
	</div>

		<div class="row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">网格</label>
					<div class="controls">
						<s:hidden name="beforehandRealBO.grid" id="val_grid"    class="form-control" />
						<s:select name="beforehandRealBO.grid" id="id_grid" list="{}" disabled="true" class="form-control" />
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">网格人员</label>
					<div class="controls">
						<s:hidden name="beforehandRealBO.operid" id="val_operid"   class="form-control" />
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
						<s:hidden name="beforehandRealBO.cycleid" class="form-control" />
						<s:textfield  id="id_cycleid" name="beforehandRealBO.cycleid" disabled="true" class="form-control" />
					</div>
				</div>
			</div>
		</div>
		<div class="row" id="btn_edit">
			<div class="col-md-5">
				<div class="form-group">
					<%--<button class="btn blue hidden-inline-xs" style="display: none;" onclick="OthersBeforehandRealInputBasic.show('1')" type="button">--%>
						<%--新增--%>
					<%--</button>--%>
					<button class="btn blue hidden-inline-xs" onclick="OthersBeforehandRealInputBasic.show('2')" type="button">
						修改
					</button>
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
		<button  class="btn blue submitbtn" type="button" data-toggle="modal" data-target="#duitModal">
			<i class="fa fa-check"></i> 提交审核
		</button>
		<%--<button class="btn blue submitbtn" type="submit" name='btn_submitKpi' data-grid-reload=".grid-biz-salary-others-beforehand-real-list">--%>
			<%--<i class="fa fa-check"></i> 保存--%>
		<%--</button>--%>
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
					<button type="submit" class="btn btn-primary" onclick="OthersBeforehandRealInputBasic.doSubmit()"
					>提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>


	<!--新增修改弹出框 -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addLabel">积分调整</h4>
				</div>
				<div class="modal-body" style="height: 180px">
					<s:hidden id="id_srcid" />
					<div class="row" id="srcid" style="display: none;">
						<div class="col-md-10">
							<div class="form-group">
								<label class="control-label">个人积分</label>
								<div class="controls">
									<s:textfield id="id_srccents" name="srccents" disabled="true" />
								</div>
							</div>
						</div>
					</div>
					<div class="row" id="groupcode">
						<div class="col-md-10">
							<div class="form-group">
								<label class="control-label">积分类型</label>
								<div class="controls">
									<select id="id_type" name="type">
										<option value="ZSBUSI">销售类结算</option>
										<option value="ZSSETUP">安装类结算</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-10">
							<div class="form-group">
								<label class="control-label">调整积分</label>
								<div class="controls">
									<s:textfield id="id_cent" type="number"   class="number form-control"  />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-10">
							<div class="form-group">
								<label class="control-label">调整说明</label>
								<div class="controls">
									<s:textfield id="id_memo" name="memo"  />
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick='OthersBeforehandRealInputBasic.updateCents()'
							data-grid-reload=".grid-biz-salary-others-beforehand-real-list">提交</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</form>

<script type="text/javascript">
</script>
<script src="${base}/biz/salary/beforehand-real-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>