AssIndexTopatchAssnumlist = ({

	init : function() {

	},

	doClose : function() {
		var $form = $(".form-ass-index-topatch-assnumlist");
		var $dialog = $form.closest(".modal");
		//$dialog.modal("close");
		$dialog.modal("hide");
	}

});

$(function() {
	AssIndexTopatchAssnumlist.init();
});
