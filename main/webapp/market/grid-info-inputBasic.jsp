<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation form-biz-market-grid-info-inputBasic"
	action="${base}/market/grid-info!doSave" method="post"
	data-editrulesurl="${base}/market/grid-info!buildValidateRules">
	<s:hidden name="id" id="id" />
	<s:hidden name="previd" id="previd" />
	<s:token />
	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">网格代码</label>
					<div class="controls">
						<s:textfield name="gridcode" id="gridcode" maxlength="30"
							required="true" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">网格名称</label>
					<div class="controls">
						<s:textfield name="gridname" id="gridname" maxlength="30" required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">网格类型</label>
					<div class="controls">
						<!-- list属性要赋值才能根据gtype值预先选中，不能在js中生成下拉列表 -->
						<s:select name="gtype" id="gtype" list="gtypeMap" required="true" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">所属网格</label>
					<div class="controls">
						<s:textfield name="preGridName" id="preGridName" disabled="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">备注</label>
					<div class="controls">
						<s:textfield name="memo" id="memo" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" name="btn_save"
			data-grid-reload=".grid-biz-market-grid-info-list">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" name="btn_cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/market/grid-info-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>