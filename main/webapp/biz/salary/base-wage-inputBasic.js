var BaseWageInputBasic = {
	$form :$(".form-base-wage"),
	init : function(){
        //初始化地市
        setTimeout("BaseWageInputBasic.initCity();",500);

        var id = this.$form.find("#id").val();
        var grid = this.$form.find("#id_grid");
        var operid = this.$form.find("#id_operid");
        var areaidobj = this.$form.find("#id_areaid");
        if(!Biz.checkNull(id)){
            this.$form.find("#div_base_wage").show();
            this.$form.find("#div_achievement").show();
            var areaid = this.$form.find("#id_areaid").val();
            var hid_grid = this.$form.find("#hid_grid").val();
            var hid_operid = this.$form.find("#hid_operid").val();
            Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + areaid,grid,hid_grid);
            Biz.initSelect("PRV_OPERATOR_BY_GRID&mcode=" + hid_grid,operid,hid_operid);

        }

	
		//初始化表格数据
		this.$form.data("formOptions",{
	    	bindEvents : function(){
	    	    var $form = $(this);
                //
                $form.find("#id_city").change(function(e){
                    Biz.initSelect("PRV_AREA_BY_CITY&mcode=" + e.val,$form.find("#id_areaid"));
                });
                //支公司变更同时变更网格
                $form.find("#id_areaid").change(function(e){
                    Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + e.val,$form.find("#id_grid"));
                });
                //网格变更同时变更网格人员
                $form.find("#id_grid").change(function(e){
                    Biz.initSelect("PRV_OPERATOR_BY_GRID&mcode=" + e.val,$form.find("#id_operid"));
                });
                $form.find("#id_type").change(function(e){
                	if(e.val=="1"){
                        $form.find("#div_base_wage").show();
                        $form.find("#div_achievement").hide();
					}else{
                        $form.find("#div_achievement").show();
                        $form.find("#div_base_wage").hide();
					}
				});
	    		 //提交按钮
	    		 var btn_submit = $form.find("button[name='btn_submitBaseWageConfig']");
				 btn_submit.click(function(){
				    return BaseWageInputBasic.doSubmit();
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
		if(!Biz.checkNull(id)){
            // Global.notify("error", "该广告只允许广告创建者修改");
            // return false;
		}
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
    BaseWageInputBasic.$form.find("#id_dateMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
	BaseWageInputBasic.init();
});