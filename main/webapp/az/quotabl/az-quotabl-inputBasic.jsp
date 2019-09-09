<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-az-quotabl-az-quotabl-inputBasic"
	action="${base}/az/quotabl/az-quotabl!doSave" method="post"
	data-editrulesurl="${base}/az/quotabl/az-quotabl!buildValidateRules">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">地市</label>
					<div class="controls">
						<s:select name="city" id="id_city" list="areaMap" disabled="true" />
					</div>
				</div>
			</div>
	    </div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">地市类别</label>
					<div class="controls">
						<s:textfield  id="id_citydivde"  name="citydivde" type="text" disabled="true" class="form-control" required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">定额比例(不超过省公司规定的0.5)</label>
					<div class="controls">
						<s:textfield id="id_quotaratio" name="quotaratio" required="true"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitAdConfig'  data-grid-reload=".grid-biz-az-quota-az-quotabl-azblindex">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>

</form>
<script src="${base}/az/quotabl/az-quotabl-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>