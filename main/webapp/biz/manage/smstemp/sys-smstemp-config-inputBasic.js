var SysSmstempConfigInputBasic = {
		$form : $(".form-sys-smstemp-config-inputBasic"),
		init : function(){
			this.initSysSmstempVar();
			// 事件绑定showMore
			this.$form.data("formOptions", {
				bindEvents : function() {
					var $form = $(this);

					var btn_submit = $form.find("button[name='btn_submitSysSmstempConfig']");
					btn_submit.click(function(){
					    return SysSmstempConfigInputBasic.doSubmit();  			
					 });
				}
			});
		},
		doSubmit : function(){
			//如果是修改
			var $form = this.$form;
			var id = $form.find("#id").val();
			if(!Biz.checkNull(id)){
				
				var opid   = $form.find("input[name='opid']").val();
				if(opid != Biz.LOGIN_INFO.operid){
					Global.notify("error", "该模板只允许模板创建者修改");
					return false;
				}
				var tstatus = $form.find("input[name='tstatus']").val();
				//0未提交1待审核2审核通过3审核不通过4失效
				if(tstatus != "0" && tstatus != "3"){
					Global.notify("error", "只允许状态为未提交或者审核不通过的模板进行修改");
					return false;
				}
			}
			//点击【保存】则弹出提示框‘已保存，是否提交？’，选择否则该条模版的记录状态为【待提交】，选择是则该条模版的记录状态为【待审核】
			if (confirm("模板已存档，确定提交审核？")) {
	            //选择提交
				this.$form.find("input[name='tstatus']").val("1");
	        }else{
	        	this.$form.find("input[name='tstatus']").val("0");
	        }
			return true;
		},
		initSysSmstempVar : function(){
			var $form = this.$form;
			var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode=SYS_SMSTEMP_VAR";
			$("body").ajaxJsonUrl(url, function(data) {
    			var li = '';
    			var li1 = "";
    			$.each(data, function(i, item) {
	    			var selected = "";
	    			if(i == 0){
	    				selected = "selected";
	    			}
	    			if(i<=5)
	    				li += "<li class='item "+selected+"' onclick='Biz.itemClick2(this);SysSmstempConfigInputBasic.clickSmsVar(this)'><input type='hidden' value='"+item.mcode+"'/><span>"+item.display+"</span><b></b></li>";

	    			else 
	    				li1 += "<li class='item "+selected+"' onclick='Biz.itemClick2(this);SysSmstempConfigInputBasic.clickSmsVar(this)'><input type='hidden' value='"+item.mcode+"'/><span>"+item.display+"</span><b></b></li>";
    			});
    			$form.find("#id_smsvar").html(li);
    			$form.find("#id_smsvar1").html(li1);
    			if(li1==""){
    				SysSmstempConfigInputBasic.$form.find("#showMore").hide();
    			}
	    	});
    		
			$form.find("#id_smstempvar").attr("style","");
		},
		/**
		 * 将选中的模板变量添加到模板内容中
		 * @param obj 选中的模板变量 
		 */
		clickSmsVar : function(obj){
			var $form = SysSmstempConfigInputBasic.$form;
			var smsVar = $(obj).find("input[type='hidden']").val();
			var txt = "#"+smsVar+"#";
			var tcontent = $form.find("textarea[name='tcontent']");
			tcontent.val(tcontent.val()+txt);
			tcontent.focus();
		}
} ;

$(function(){
	SysSmstempConfigInputBasic.init();
});