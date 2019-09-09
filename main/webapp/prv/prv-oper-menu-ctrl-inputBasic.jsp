<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation form-grid-prv-prv-oper-menu-ctrl-inputBasic"
	action="${base}/prv/prv-oper-menu-ctrl!doSave" method="post"
	data-editrulesurl="${base}/prv/prv-oper-menu-ctrl!buildValidateRules">

	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">权限编码</label>
					<div class="controls">
							<s:select id="controlcode" name="controlcode"   list="valuesrcMap"  placeholder="请选择属性值来源" required="true" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">权限值</label>
					<div class="controls">
						<s:select name="controlvalue" id="controlvalue" required="true"
							list="#{'0':'低权','5':'中权','9':'高权'}" />
					</div>
				</div>
			</div>
		</div>

		<div id="operobj">
			<div class="row" id="operidrow">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label">限制对象</label>
						<div class="controls">
							<s:textfield type="text" id="operid" name="operid"
								readonly="readonly" required="true" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label">限制对象</label>
						<div class="controls">
							<s:textfield type="text" id="opername" name="opername"
								placeholder="点击选择操作员"  required="true"
								onclick="PrvOperMenuCtrlInputBasic.selectOperTabs(operobj);"
								readonly="readonly" onfocus="this.blur()" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="menus">
		<div class="row" id="menuidrow">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">限制菜单</label>
					<div class="controls">
						<s:textfield type="text" id="menuid" name="menuid"
							placeholder="点击选择菜单"  required="true"
							onclick="PrvOperMenuCtrlInputBasic.selectMenuTabs(this);"
							readonly="readonly" onfocus="this.blur()" />
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">限制菜单</label>
					<div class="controls">
						<s:textfield type="text" id="menuname" name="menuname"
							placeholder="点击选择菜单"
							onclick="PrvOperMenuCtrlInputBasic.selectMenuTabs(menus);" required="true"
							readonly="readonly" onfocus="this.blur()" />
					</div>
				</div>
			</div>
		</div>
</div>

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">限制次数</label>
					<div class="controls">
						<s:textfield id="value" name="value" maxlength="5" required="true"/>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name="btn_prvopermenuctrl"
			data-grid-reload=".grid-channel-biz-tmpopenlimit-biz-tmp-open-limit-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script>
	document.getElementById("operidrow").style.display = "none";
	document.getElementById("menuidrow").style.display = "none";
</script>
<script src="${base}/prv/prv-oper-menu-ctrl-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>