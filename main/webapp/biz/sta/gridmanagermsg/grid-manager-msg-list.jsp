<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-biz-sta-grid-manager-msg-list">
	<div class="row search-form-default">
		<div class="col-md-12">
			<form action="#" method="get"
				class="form-inline form-validation form-biz-sta-grid-manager-msg-list"
				data-grid-search=".biz-sta-grid-manager-msg-list">
				<div class="form-group">
					<s:hidden id="backupAreaId" />
					<s:select id="areaid" name="gridManagerMsgParamBo.areaids"
						cssClass="form-control input-medium" list="{}" multiple="true"
						placeholder="请选择业务区..." />
				</div>
				<div class="form-group">
					<s:select id="depart" name="gridManagerMsgParamBo.departs"
						cssClass="form-control input-medium" list="{}" multiple="true"
						placeholder="请选择操作部门..." />
				</div>
				<div class="form-group">
					<s:select id="operator" name="gridManagerMsgParamBo.operators"
						cssClass="form-control input-medium" list="{}" multiple="true"
						placeholder=" 请输入操作员..." />
				</div>

				<button class="btn green" type="submmit"
					onclick="">
					<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
				</button>
				<button class="btn default hidden-inline-xs" type="button"
					onclick="GridManagerMsgList.searchFormObj.reset();">
					<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
				</button>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<table class="biz-sta-grid-manager-msg-list"></table>
		</div>
	</div>
</div>
<script src="${base}/biz/sta/gridmanagermsg/grid-manager-msg-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>