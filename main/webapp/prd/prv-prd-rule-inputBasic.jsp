<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation form-prv-prd-rule-inputBasic"
	action="${base}/prd/prv-prd-rule!doSave" method="post"
	data-editrulesurl="${base}/prd/prv-prd-rule!buildValidateRules">
	<s:hidden id="id" name="id" />
	<s:hidden id="value" name="value" />
	<s:hidden id="jumpmenuid" name="jumpmenuid" />
	
	<s:token />

	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务区</label>
					<div class="controls">
						<s:select id="areaid" name="areaid" list="cityAreaMap" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">推荐对象类型</label>
					<div class="controls">
						<s:select name="objtype" id="objtype"
							list="#{'0':'商品','1':'促销优惠'}" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">推荐对象值</label>
					<div class="controls">
						<s:textfield type="text" id="valuename" name="valuename"
							value="%{#parameters.prdname}"
							placeholder="点击选择推荐对象值..."
							onclick="PrvPrdRuleInputBasic.selectProductTabs('.form-prv-prd-rule-inputBasic');"
							readonly="readonly" onfocus="this.blur()" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">跳转菜单</label>
					<div class="controls">
						<s:textfield type="text" id="jumpmenuname" name="jumpmenuname"
							value="%{#parameters.menuname}"
							placeholder="点击选择跳转菜单..."
							onclick="PrvPrdRuleInputBasic.selectMenuTabs('.form-prv-prd-rule-inputBasic');"
							readonly="readonly" onfocus="this.blur()" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label">规则失效日期</label>
						<div class="controls">
							<s3:datetextfield id="exptime" name="exptime"
								format="yyyy-MM-dd HH:mm:ss" />
						</div>
					</div>
				</div>
			</div>

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">提示语</label>
					<div class="controls">
						<s:textarea id="message" name="message" maxlength="50" rows="4" />
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name="btn_submit"
			data-grid-reload=".grid-prv-prd-rule-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/prd/prv-prd-rule-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>