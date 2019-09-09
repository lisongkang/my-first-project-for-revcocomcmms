var OthersExchangeConfigInputBasic = {
	$form :$(".form-others-exchange-config"),
	init : function(){
        var id = this.$form.find("#id").val();
        var grid = this.$form.find("#id_grid");
        //初始化地市
        setTimeout("OthersExchangeConfigInputBasic.initCity();",500);
        // if(!Biz.checkNull(id)){
        //     var areaid = this.$form.find("#id_areaid").val();
        //     Biz.initSelect("PRV_GRID_BY_AREAID&mcode="+areaid,grid,$("#hid_grid").val());
        // }
		//初始化表格数据
		this.$form.data("formOptions",{
	    	bindEvents : function(){
	    	    var $form = $(this);
                //支公司变更同时变更网格
                //  $form.find("#id_areaid").change(function(e){
                //     Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + e.val,grid);
                //  });
	    		 //提交按钮
	    		 var btn_submit = $form.find("button[name='btn_submitOecConfig']");
				 btn_submit.click(function(){
				    return OthersExchangeConfigInputBasic.doSubmit();
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
		var sdateMonth = $form.find("input[name='sdateMonth']").val();
		var edateMonth = $form.find("input[name='edateMonth']").val();
		if(sdateMonth>edateMonth){
            Global.notify("error", "结束日期不能小于开始日期!");
            return false;
		}

		return true;
	}
};
$(function(){
    OthersExchangeConfigInputBasic.$form.find("input[name='sdateMonth']").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
    OthersExchangeConfigInputBasic.$form.find("input[name='edateMonth']").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
	OthersExchangeConfigInputBasic.init();
});