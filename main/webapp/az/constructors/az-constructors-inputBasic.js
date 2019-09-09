var AzQuotaInputBasic = {
    $form :$("form-biz-az-constructors-az-constructors-inputBasic"),
    init : function(){

        //初始化表格数据
        this.$form.data("formOptions",{
            bindEvents : function(){
                var $form = $(this);
                //提交按钮
                var btn_submit = $form.find("button[name='btn_submitAdConfig']");
                btn_submit.click(function(){
                    return AzQuotaInputBasic.doSubmit();
                });
            }
        });
    },
    /**
     * 保存
     */
    doSubmit : function(){
        var id = $form.find("#id").val();
        //如果是修改
        if(!Biz.checkNull(id)){
        }
        else {
		}
        return true;
    },
};
$(function(){
    AzQuotaInputBasic.init();
});