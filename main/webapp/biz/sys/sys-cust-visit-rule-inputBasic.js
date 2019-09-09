$(function(){
	$(".form-biz-sys-sys-cust-visit-rule-inputBasic").data("formOptions",{
		bindEvents : function(){
			var $form = $(this);
			$form.find(".fa-select-template").click(function(){
				var sendtype = $form.find("select[name='sendMethod']").val();
				if (sendtype == "") {
            		Global.notify("error", "请选择回访方式");	
            		return;
            	}
				$(this).popupDialog({
					url : WEB_ROOT +'/biz/sys/return-visit-template!toTemplatePage?_to_=selection&sendtype='+sendtype,
					title : '选择模板',
					id : 'template-select',
					callback : function(rowdata){
						$form.find("input[name='rule.template']").val(rowdata['templateTitle'])
						$form.find("input[name='contentTemplateId']").val(rowdata['id'])
					}
				})
			});
		}
	});
	var $form = $(".form-biz-sys-sys-cust-visit-rule-inputBasic");
})