var PrvAttrruleInputBasic = ({
    $form : $(".form-biz-memo-cfg-inputBasic"),
    init : function(){
        var id = this.$form.find("input[name='id']").val();
        var cityoption = this.$form.find("select[name='city']").find("option[value='"+extendattr_initCity+"']");
        cityoption.attr("selected","selected");

        if(this.checkNull(id)){//新增页面
            var areasoption = this.$form.find("select[name='areas']").find("option[value='*']");
            areasoption.attr("selected","selected");

        }else{
            //初始化业务区  //获取是否可以编辑
            var  selectedAreas = extendattr_initAreas.split(",");
            for(var i = 0 ; i < selectedAreas.length ; i++){
                var areasoption = this.$form.find("select[name='area']").find("option[value='"+selectedAreas[i]+"']");
                areasoption.attr("selected","selected");
            }
            var opcodesoption = this.$form.find("select[name='opcodes']").find("option[value='"+extendattr_initOpcodes+"']");
            areasoption.attr("selected","selected");

        }
    },
    checkRequired : function(){
        var city = this.$form.find("#id_city").val();
        if(this.checkNull(city)){
            Global.notify("error", "城市不能为空!");
            return false;
        }
        var areas = this.$form.find("#id_areas").val();
        if(this.checkNull(city)){
            Global.notify("error", "业务区不能为空!");
            return false;
        }
        var opcodes = this.$form.find("#id_opcodes").val();
        if(this.checkNull(city)){
            Global.notify("error", "业务操作不能为空!");
            return false;
        }
        var message = this.$form.find("#memotxt").val();
        if(this.checkNull(city)){
            Global.notify("error", "备注内容不能为空!");
            return false;
        }
        if(message.length>50){
            Global.notify("error", "备注内容不能超过50长度!");
            return false;
        }
        return  true;
    },

    checkNull : function(objvalue){
        if(objvalue == undefined || objvalue == "" || objvalue == null || !objvalue){
            return true;
        }
        return false;
    },
    submit : function(){

        if(!this.checkRequired()){
            return ;
        }
        var attrruleData={};
        attrruleData.id  = this.$form.find("input[name='id']").val().trim();
        // this.$form.find("#id_city").attr("disabled","");
        attrruleData.city =this.$form.find("#id_city").val();
        attrruleData.areas = this.$form.find("#id_areas").val().trim();
        attrruleData.opcodes = this.$form.find("#id_opcodes").val().trim();
        attrruleData.valuesrc = this.$form.find("#id_valuesrc").val().trim();

        attrruleData.memotxt = this.$form.find("#memotxt").val().trim();
        attrruleData.pri = this.$form.find("#pri").val().trim();

        attrruleData.bizMemoCfg = JSON.stringify(attrruleData);

        var url = WEB_ROOT + '/memo/biz-memo-cfg!doSave';
        $("body").ajaxJsonUrl(url, function(result) {
            Global.notify("success", "保存备注配置成功");
            PrvAttrruleInputBasic.removeThisTab();
            Biz.refreshGrid(".grid-memo-cfg-index"); // 表单提交后直接刷新表格
            var backdrop = $("body").find(".modal-backdrop");
            $(backdrop).remove();
        },attrruleData);

    },
    removeThisTab : function(){
        // 移除当前编辑tab（代替取消按钮事件，因为会额外弹出提示框）
        var bodyElement = $(".div-grid-biz-memo-cfg-index");
        var activeTab = $(bodyElement).find(".active.tab-closable");
        var tabId = $(activeTab).attr("id");
        $(activeTab).remove(); // 移除当前编辑tab
        $(bodyElement).find("a[href='#" + tabId + "']").remove(); // 移除当前编辑tab标题
        $(bodyElement).find(".tab-default").click();
    },
})
$(function() {
    $(".form-biz-memo-cfg-inputBasic").data("formOptions", {
        bindEvents : function() {
            var $form = $(this);
            $form.find("button[name='submitAttrrule']").click(function(){
                PrvAttrruleInputBasic.submit();
            });

        }
    });

    PrvAttrruleInputBasic.init();
});