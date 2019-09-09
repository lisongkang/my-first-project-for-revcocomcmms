var AzQuotablInputBasic = {
    $form :$("form-biz-az-quotabl-az-quotabl-inputBasic"),
    init : function(){

        //初始化地市
        setTimeout("AzQuotablInputBasic.initCity();",500);

        //初始化表格数据
        this.$form.data("formOptions",{
            bindEvents : function(){
                var $form = $(this);
                //提交按钮
                var btn_submit = $form.find("button[name='btn_submitAdConfig']");
                btn_submit.click(function(){
                    return AzQuotablInputBasic.doSubmit();
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
        Biz.disableSelect2WhenNotAdmin($city);
    },
    /**
     * 保存
     */
    doSubmit : function(){
        var id = $form.find("#id").val();
        var quotaratio = $form.find("#quotaratio").val();
        if(quotaratio > 0.5){
            Global.notify("error", "定额比列不能大于0.5");
            return false;
        }

        //如果是修改
        if(!Biz.checkNull(id)){

        }
        else {
            Global.notify("error", "新建暂未开启");
            return false;
		}
        return true;
    },
};
$(function(){
    AzQuotablInputBasic.init();
});