var SysProblemInputBasic={
	$form : $(".form-sys-problem-inputBasic"),
	init:function(){
		// 事件绑定
		this.$form.data("formOptions", {
			bindEvents : function() {
				var $form = $(this);

				var btn_submit = $form.find("button[name='btn_submitSysproblem']");
				btn_submit.click(function(){
				    return SysProblemInputBasic.doDealSysproblem();  			
				 });
			}
		});
	},
	doDealSysproblem : function(){
		var plstate = this.$form.find("select[name='plstate']").val();
		if(plstate=="1")// 1:代表 待处理  状态 可以提交
		{
		    return true;
		}else{
			Global.notify("error", "只有待处理状态下的问题可编辑保存");
			return false;
		}
	}
       
};
$(function(){
	SysProblemInputBasic.init();
});