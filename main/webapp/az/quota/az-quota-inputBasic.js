var AzQuotaInputBasic = {
    $form :$(".form-biz-az-quota-az-quota-inputBasic"),
    init : function(){

        //初始化地市
        setTimeout("AzQuotaInputBasic.initCity();",500);

        //初始化表格数据
        this.$form.data("formOptions",{
            bindEvents : function(){
                var $form = $(this);

                $form.find("button[name='uploadAdImgBtn']").click(function(){
                    var targetObj = $form.find("#id_adsite");
                    AzQuotaInputBasic.uploadFile(targetObj);
                });
                
                //提交按钮
                var btn_submit = $form.find("button[name='btn_submitAdConfig']");
                btn_submit.click(function(){
                    return AzQuotaInputBasic.doSubmit();
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
        //如果是修改
        if(!Biz.checkNull(id)){
        }
        else {
		}
        return true;
    },
    uploadFile :function(targetObj0){
        var targetObj =targetObj0 ; //目标对象（返回文件路径）
        var limitFileType0 = "img";               //文件格式
        var maxSize0 = "2048";                      //文件大小
        var relativePath0="adsite_imgs";          //文件存储子目录
        var tip="注：图片格式为.jpg,.jpeg,.bmp,.png,图片大小不大于2MB";
        Biz.fileupload(limitFileType0,maxSize0,relativePath0,tip,targetObj);
    }
};
$(function(){
    AzQuotaInputBasic.init();
});