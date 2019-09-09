var BizQaRelationDetailBasic = {
	form : $(".form-biz-qa-relation-detailBasic"),
	init : function() {
		this.form.data("formOptions", {
			bindEvents : function() {
				var $form = $(this);
			}
		});
	},
	doClose : function() {
		var $dialog = this.form.closest(".modal");
		$dialog.modal("hide");
	}
};

$(function() {
	BizQaRelationDetailBasic.init();
});