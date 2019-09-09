<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-biz-market-cust-marketing-bi-push"
	action="${base}/market/cust-marketing-bi!doPush" method="post">
	<s:token />
	<div class="form-body">
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label" style="width:70px">优先级</label>
					<div class="controls">
		                <!--<s:select id="pri" name="pri" list="#{'H':'高','M':'中','L':'低'}" value='L'/>-->
		                <select id="pri" name="pri" class="form-control input-large" >
		                	<option value="H">高</option>
		                	<option value="M">中</option>
		                	<option value="L" selected>低</option>
		                </select>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel2" type="button">取消</button>
	</div>
</form>
<%@ include file="/common/ajax-footer.jsp"%>
<script>
$(function() {
	var form = $(".form-biz-market-cust-marketing-bi-push");
	form.find('.blue').click(function() {
		var ids = '<s:property value="#request.ids" />';
    	var url = WEB_ROOT + "/market/cust-marketing-bi!doPush?ids=" + ids + "&pri=" + $('#pri').val();
    	form.ajaxJsonUrl(url, function(data) {
    		if (data.type == "success") {
    			Global.notify("success", "推送信息成功");
    		} else {
    			Global.notify("error", data.message);
    		}
    		var $dialog = form.closest(".modal");
    		$dialog.modal("hide");

    		var callback = $dialog.data("callback");
			if (callback) {
				callback.call(form, data);
			}
    	});
    });
	form.find('.btn-cancel2').click(function() {
        var $dialog = form.closest(".modal");
		$dialog.modal("hide");
    });
});
</script>