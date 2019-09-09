<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation form-biz-survey-biz-survey-list-inputBasic"
	action="${base}/survey/biz-survey-list!doSave" method="post">
	<s:hidden name="id" />
	<s:hidden name="status"/>
	<s:hidden name="areaids" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">问卷名称</label>
					<div class="controls">
						<s:textfield id="id_sname" name="sname" maxlength="30"
							required="true" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">地市</label>
					<div class="controls">
						<s:select id="id_city" name="city" list="areaMap" required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务区</label>
					<div class="controls">
						<s:select id="id_area" name="areaid" list="townMap"
							required="true" multiple="true" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否实名制</label>
					<div class="controls">
						<s:select id="id_isreal" name="isreal" list="realNameStutusMap"
							placeholder="是否实名制" required="required" /> 
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">备注</label>
					<div class="controls">
						<s:textfield id="id_memo" name="memo" maxlength="255" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" name="btn_surveynew">
			<i class="fa fa-check"></i> 下一步
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>

<script src="${base}/survey/biz-survey-list-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>