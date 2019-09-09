<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-que-disupgrade-cust">
	<div class="row search-form-default">
		<div class="col-md-12">
			<form action="#" method="get"
				class="form-inline form-validation form-que-disupgrade-cust"
				data-grid-search=".grid-que-disupgrade-cust">
				<div class="form-group">
					<s:select id="patchids" name="queDisupgradeCustParamBO.patchids"
						cssClass="form-control input-medium" list="{}" multiple="true"
						placeholder="请选择片区..." />
				</div>
				<div class="form-group">
					<s:select id="servtype" name="queDisupgradeCustParamBO.servtype"
						cssClass="form-control input-medium" list="{}"
						placeholder="请选择用户类型..." />
				</div>
				<div class="form-group">
					<s:hidden name="queDisupgradeCustParamBO.netattr" value="0" />
					<s:select id="netattr" cssClass="form-control input-medium"
						list="#{0:'单向设备'}" value="0" disabled="true"
						placeholder="请选择设备单双属性..." />
				</div>
				<div class="form-group">
					<s:textfield id="devno" name="queDisupgradeCustParamBO.devno"
						cssClass="form-control input-medium" placeholder="请输入机顶盒号..."
						maxlength="30" />
				</div>

				<button class="btn green" type="submmit"
					onclick="return QueDisupgradeCust.searchFormObj.checkInput();">
					<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
				</button>
				<button class="btn default hidden-inline-xs" type="button"
					onclick="QueDisupgradeCust.searchFormObj.reset();">
					<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
				</button>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<table class="grid-que-disupgrade-cust"></table>
		</div>
	</div>
</div>
<script src="${base}/market/que-disupgrade-cust-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>