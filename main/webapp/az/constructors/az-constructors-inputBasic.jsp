<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-az-constructors-az-constructors-inputBasic"
	action="${base}/az/constructors/az-constructors!doSave" method="post"
	data-editrulesurl="${base}/az/constructors/az-constructors!buildValidateRules">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
		<%--<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">施工人id</label>
					<div class="controls">
						<s:textfield  id="id_operid"  name="operid" type="text"  class="form-control" required="true" />
					</div>
				</div>
			</div>
		</div>--%>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">施工人账号</label>
					<div class="controls">
						<s:textfield id="id_loginname" name="loginname" type="text"  class="form-control"  required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">施工人名称</label>
					<div class="controls">
						<s:textfield id="id_name" name="name" type="text"  class="form-control" required="true"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">身份证号码</label>
					<div class="controls">
						<s:textfield id="id_idcard" name="idcard" type="text"  class="form-control"  />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">银行卡号</label>
					<div class="controls">
						<s:textarea id="id_bankcard" name="bankcard"   placeholder="可在此添加银行卡号" class="form-control"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitAdConfig'  data-grid-reload=".grid-biz-az-constructors-az-constructors-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>

</form>
<script src="${base}/az/constructors/az-constructors-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>