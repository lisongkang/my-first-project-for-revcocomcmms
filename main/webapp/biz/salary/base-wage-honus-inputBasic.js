var BaseWageHonusInputBasic = {
	$form :$(".form-base-wage-honus"),
	init : function(){
        //初始化地市
        setTimeout("BaseWageHonusInputBasic.initCity();",500);

        var id = this.$form.find("#id").val();
        var areaidobj = this.$form.find("#id_areaid");
        var areaid = this.$form.find("#id_areaid").val();
        var City  = this.$form.find("#id_city").val();
        var areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + City; // 中高权限只能查询所属地市的业务区
        Biz.initSelect(areaSelectGcode, areaidobj); // 字符串中用&mcode增加参数
	
		//初始化表格数据
		this.$form.data("formOptions",{
	    	bindEvents : function(){
	    	    var $form = $(this);
                //
                $form.find("#id_city").change(function(e){
                    Biz.initSelect("PRV_AREA_BY_CITY&mcode=" + e.val,$form.find("#id_areaid"));
                });

	    		 //提交按钮
	    		 var btn_submit = $form.find("button[name='btn_submitBaseWageHonusConfig']");
				 btn_submit.click(function(){
				    return BaseWageHonusInputBasic.doSubmit();
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
            var areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + loginCity; // 中高权限只能查询所属地市的业务区

        }
        //Biz.disableSelect2($city);
        Biz.disableSelect2WhenNotAdmin($city);
    },

    /**
	 * 保存
	 */
	doSubmit : function(){
		//如果是修改
		var $form = this.$form;
		var id = $form.find("#id").val();
		if(!Biz.checkNull(id)){

		}
		return true;
	}
};
$(function(){
    BaseWageHonusInputBasic.$form.find("#sdateMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
    BaseWageHonusInputBasic.$form.find("#edateMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
	BaseWageHonusInputBasic.init();
});