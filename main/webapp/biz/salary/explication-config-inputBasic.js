var ExplicationConfigInputBasic = {
	$form :$(".form-explication-config"),
	init : function(){
        var id = this.$form.find("#id").val();
        if(Biz.checkNull(id)){
            this.$form.find("#id_context").val("");
        }

		//初始化地市
		setTimeout("ExplicationConfigInputBasic.initCity();",500);
	
		//初始化表格数据
		this.$form.data("formOptions",{
	    	bindEvents : function(){
	    	    var $form = $(this);
	    		 //提交按钮
	    		 var btn_submit = $form.find("button[name='btn_submitExpConfig']");
				 btn_submit.click(function(){
				    return ExplicationConfigInputBasic.doSubmit();
				 });
	    	}
		});
	},
	/**
	 * 初始化地市
	 */
	initCity :function(){
		var $form = this.$form;
		var id = $form.find("#id").val();
		var $city  = $form.find("#id_city");
		//如果是新增
		if(Biz.checkNull(id)){
			$city.select2("val", Biz.LOGIN_INFO.city);
		}
         Biz.disableSelect2($city);
		 // Biz.disableSelect2WhenNotAdmin($city);
	},
	/**
	 * 保存
	 */
	doSubmit : function(){
		//如果是修改
		var $form = this.$form;
		var id = $form.find("#id").val();
		var stime = $form.find("input[name='stime']").val();
		var etime = $form.find("input[name='etime']").val();

		if(etime<stime){
            Global.notify("error", "结束日期不能小于开始日期!");
            return false;
		}
		// if(!Biz.checkNull(id)){
            // Global.notify("error", "该广告只允许广告创建者修改");
            // return false;
		// }
        // var context = $form.find("#id_context").val();
        // this.$form.find("input[name='context']").val(context);
		//点击【保存】则弹出提示框‘已保存，是否提交？’，选择否则该条模版的记录状态为【待提交】，选择是则该条模版的记录状态为【待审核】
		// if (confirm("广告已保存，确定提交审核？")) {
         //    //选择提交
		// 	this.$form.find("input[name='adstatus']").val("2");
        // }else{
        	// this.$form.find("input[name='adstatus']").val("1");
        // }
		return true;
	}
};
$(function(){
	ExplicationConfigInputBasic.init();
});