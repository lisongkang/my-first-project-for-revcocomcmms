CustOrderPaymentBasic = ({
	init : function() {
		$(".form-biz-custorder-cust-order-paymentBasic").data("formOptions", {
			bindEvents : function() {
				var $form = $(this);
			}
		});
	},
	doClose : function() {
		var $form = $(".form-biz-custorder-cust-order-paymentBasic");
		var $dialog = $form.closest(".modal");
		$dialog.modal("hide");
	}
});

$(function() {
	CustOrderPaymentBasic.init();
});
