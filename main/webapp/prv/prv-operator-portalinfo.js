var PrvOperatorPortalInfo={
		form : $(".form-prv-operator-portalinfo"),
		init: function(){
			//隐藏保存按钮
			var actionDiv  = this.form.find(".form-actions");
			actionDiv.hide();
		},
		checkRequireBeforeCommit:function(){
			var portalnum = this.form.find("#portalnum").val();
			if(!(/^1[34578]\d{9}$/.test(portalnum))){
				  Global.notify("error", "portal账户(手机号)有误，请重填");
			}else{
				this.form.submit();
			}
		
		}
};
$(function(){
	//PrvOperatorPortalInfo.init();
});