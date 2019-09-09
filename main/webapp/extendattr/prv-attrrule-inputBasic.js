var PrvAttrruleInputBasic = ({
	$form : $(".form-biz-extendattr-prv-attrrule-inputBasic"),
	init : function(){
		var id = this.$form.find("input[name='id']").val();
		
		if(this.checkNull(id)){//新增页面
		 var option = this.$form.find("select[name='valuesrc']").find("option[value='0']");
		 option.attr("selected","selected");
		 var cityoption = this.$form.find("select[name='city']").find("option[value='*']");
		 cityoption.attr("selected","selected");
		 
		 var ifnecessaryoption = this.$form.find("select[name='ifnecessary']").find("option[value='Y']");
		 ifnecessaryoption.attr("selected","selected");
		 this.$form.find("#id_valueparm_and_mname").hide();
		}else{
			//初始化地市  //获取是否可以编辑
		  var  selectedCity = extendattr_initCity.split(",");
		  for(var i = 0 ; i < selectedCity.length ; i++){
			  var cityoption = this.$form.find("select[name='city']").find("option[value='"+selectedCity[i]+"']");
			  cityoption.attr("selected","selected");
		  }
		  
		  //参数选项
		  var option = this.$form.find("select[name='valuesrc']").val();
		  if(option =="1"||option=="2"){
			  this.$form.find("input[name='mname']").val(extendattr_mname);
			  this.$form.find("input[name='valueparam']").attr("disabled","disabled");
		   }
		   //是否隐藏参数选项框
			PrvAttrruleInputBasic.hideOrshowDiv();
			//限制修改属性标识和对应参数
			this.$form.find("input[name='attrcode']").attr("disabled","disabled");
		}
		
		
		
	},
	checkRequired : function(){
		var city = this.$form.find("#id_city").val();
		if(this.checkNull(city)){
			Global.notify("error", "适用城市不能为空!");
			return false;
		}
		var attrname = this.$form.find("#id_attrname").val();
		if(this.checkNull(attrname)){
			Global.notify("error", "属性名称不能为空!");
			return false;
		}
		if(attrname.length>10){
			Global.notify("error", "属性名称不能超过10长度!");
			return false;
		}
		var attrcode = this.$form.find("#id_attrcode").val();
		
		if(this.checkNull(attrcode)){
			Global.notify("error", "属性标识不能为空!");
			return false;
		}
		
		if(attrcode.length>40){
			Global.notify("error", "属性标识不能超过40长度!");
			return false;
		}
		var valuesrc = this.$form.find("#id_valuesrc").val();
		
		if(this.checkNull(valuesrc)){
			Global.notify("error", "属性值来源不能为空!");
			return false;
		}
		
		//是否必填
		var  ifnecessary = this.$form.find("#id_ifnecessary").val();
		if(this.checkNull(ifnecessary)){
			Global.notify("error", "是否必填参数 不能为空!");
			return false;
		}

		var valueparam = this.$form.find("#id_valueparam").val();
		var mname = this.$form.find("#id_mname").val();
	    if((valuesrc == "1" || valuesrc =="2")){
	    	if(this.checkNull(valueparam)){
	    		Global.notify("error", "对应参数为必填!");
				return false;
	    	}
	    	if(valueparam.length > 40){
	    		Global.notify("error", "对应参数不能超过40长度!");
				return false;
	    	}
	    	//判断参数选项是否符合标准
	    	if(!this.checkMname()){
	    		return false;
	    	}
	    	
	    }
		var ifnecessary = this.$form.find("#id_ifnecessary").val();
		if(this.checkNull(ifnecessary)){
			Global.notify("error", "是否必填参数 不能为空!");
			return false;
		}
		
        return  true;
	},
	checkMname : function(){
		var mname = this.$form.find("#id_mname").val();
		if(this.checkNull(mname)){
    		Global.notify("error", "参数选项为必填!");
			return false;
    	}
		var replaceStr = "，";
        mname = mname.replace(new RegExp(replaceStr,'gm'),',');//将中文的，转换成英文的,
		replaceStr = " ";
		mname = mname.replace(new RegExp(replaceStr,'gm'),',');//去掉空格
		var arr = mname.split(",");
		//
		for(var i = 0 ; i < arr.length;i++){
			if(this.checkNull(arr[i])){
				Global.notify("error", "参数选项填写不规范,参数选项截取后出现空参数");
				return false;
			}
			if(arr[i].length>60){
				Global.notify("error", "参数选项填写不规范,参数选项截取后:"+ arr[i]+"长度超过60");
				return false;
			}
			for(var j = i+1 ; j < arr.length ; j++){
				if(arr[i]==arr[j]){
					Global.notify("error", "请使用英文逗号隔开,"+"参数选项值："+ arr[i]+"出现重复");
					return false;
				}
			}
		}
		return true;
	},
	checkNull : function(objvalue){
		if(objvalue == undefined || objvalue == "" || objvalue == null || !objvalue){
			return true;
		}
		return false;
	},
	submit : function(){
	   
	   if(extendattr_isOktoUpdate =="1"){
		   Global.notify("error", "该规则已经被使用，不可以修改!");
		   return;
	   }
	   
	   
	   if(!this.checkRequired()){
		   return ;
	   }
	   var attrruleData={};
	   attrruleData.id  = this.$form.find("input[name='id']").val().trim();
	   attrruleData.city = this.$form.find("#id_city").val();
	   attrruleData.attrname = this.$form.find("#id_attrname").val().trim();
	   attrruleData.attrcode = this.$form.find("#id_attrcode").val().trim();
	   attrruleData.valuesrc = this.$form.find("#id_valuesrc").val().trim();
	  
	   attrruleData.ifnecessary = this.$form.find("#id_ifnecessary").val().trim();
	 /*  attrruleData.defaultvalue = this.$form.find("#id_defaultvalue").val().trim();*/
	   if(attrruleData.valuesrc == "1" || attrruleData.valuesrc == "2"){
		   attrruleData.valueparam = this.$form.find("#id_valueparam").val().trim();
		   
		   var mname = this.$form.find("#id_mname").val().trim();
		  // mname = mname.replace("，",",");//避免中文逗号
		   var replaceStr = "，";
		   mname = mname.replace(new RegExp(replaceStr,'gm'),',');//将中文的，转换成英文的,
		   
		   attrruleData.mnames = mname ;//设置参数选项值
	   }
	   attrruleData.prvAttrruleBo = JSON.stringify(attrruleData);
	   
	    var url = WEB_ROOT + '/extendattr/prv-attrrule!doSave';
		$("body").ajaxJsonUrl(url, function(result) {
				Global.notify("success", "保存扩展属性成功");
				PrvAttrruleInputBasic.removeThisTab();
				Biz.refreshGrid(".grid-biz-extendattr-prv-attrrule-index"); // 表单提交后直接刷新表格
				var backdrop = $("body").find(".modal-backdrop");
				$(backdrop).remove();
		},attrruleData);
		
	},
	removeThisTab : function(){
		// 移除当前编辑tab（代替取消按钮事件，因为会额外弹出提示框）
		var bodyElement = $(".div-grid-biz-extendattr-prv-attrrule-index");
		var activeTab = $(bodyElement).find(".active.tab-closable");
		var tabId = $(activeTab).attr("id");
		$(activeTab).remove(); // 移除当前编辑tab
		$(bodyElement).find("a[href='#" + tabId + "']").remove(); // 移除当前编辑tab标题
		$(bodyElement).find(".tab-default").click();
	},
	hideOrshowDiv : function(){
		var option = this.$form.find("select[name='valuesrc']").val();
		if(option =="1"||option=="2"){
			this.$form.find("#id_valueparm_and_mname").show();
		}else{
			this.$form.find("#id_valueparm_and_mname").hide();
		}
	}
})
$(function() {
    $(".form-biz-extendattr-prv-attrrule-inputBasic").data("formOptions", {
        bindEvents : function() {
            var $form = $(this);
        	 $form.find("button[name='submitAttrrule']").click(function(){
        		 PrvAttrruleInputBasic.submit();
        	 });
        	 $form.find("select[name='valuesrc']").bind('change',function(){
        		PrvAttrruleInputBasic.hideOrshowDiv();
        	 })
        	 
        }
    });
    
    PrvAttrruleInputBasic.init();
});