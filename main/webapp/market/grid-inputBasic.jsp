<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-market-grid-inputBasic"
	action="${base}/market/grid!doSave" method="post" 
	data-editrulesurl="${base}/market/grid!buildValidateRules">
	<s:hidden name="id" />
	<s:token />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-market-grid-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">网格编码</label>
					<div class="controls">
		                <s:textfield name="gridcode" maxlength="30" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">网格名称</label>
					<div class="controls">
		                <s:textfield name="gridname" maxlength="30" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">所属分公司</label>
					<div class="controls">
		                <s:select name="city" list="#application.enums.prvcity" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-market-grid-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<%@ include file="/common/ajax-footer.jsp"%>
<script>
$(function() {
    $(".form-biz-market-grid-inputBasic").data("formOptions", {
        bindEvents : function() {
            var $form = $(this);
            
            if (!$form.find("select[name='city']").val()) {
            	if (Biz.LOGIN_INFO) {
            		$form.find("select[name='city']").select2("val", Biz.LOGIN_INFO.city);
            	}
            }
        }
    });
});
</script>