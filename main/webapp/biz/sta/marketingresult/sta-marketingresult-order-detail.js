CustOrderDetailBasic = ({
	init : function() {
		$(".form-biz-custorder-cust-order-detailBasic").data("formOptions", {
			bindEvents : function() {
				var $form = $(this);
			}
		});
	},
	doClose : function() {
		var $form = $(".form-biz-custorder-cust-order-detailBasic");
		var $dialog = $form.closest(".modal");
		$dialog.modal("hide");
	}
});

$(function() {
	CustOrderDetailBasic.init();
});