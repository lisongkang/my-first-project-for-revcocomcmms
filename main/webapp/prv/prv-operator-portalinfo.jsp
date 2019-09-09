<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-prv-operator-portalinfo" action="${base}/prv/prv-operator!doSavePortalInfo"
	method="post">
	<s:hidden name="id" />
	<s:token />
	<!-- <div class="form-actions">
		<button class="btn blue" type="button" onclick='PrvOperatorPortalInfo.checkRequireBeforeCommit();' data-grid-reload=".grid-auth-user-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div> -->
	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">BOSS工号</label>
					<div class="controls">
						<s:textfield name="loginname" maxlength="32" disabled="%{persistentedModel}" placeholder="创建之后不可修改，请仔细填写" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">portal账户</label>
					<div class="controls">
						<s:textfield id="portalnum" name="extraAttributes.portalnum" maxlength="11" placeholder="请输入登录账号（手机号）"/>
					</div>
				</div>
			</div>
		</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" onclick='PrvOperatorPortalInfo.checkRequireBeforeCommit();'  data-grid-reload=".grid-auth-user-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/prv/prv-operator-portalinfo.js" />
<%@ include file="/common/ajax-footer.jsp"%>
