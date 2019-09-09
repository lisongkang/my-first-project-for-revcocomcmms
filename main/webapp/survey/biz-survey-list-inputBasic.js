var BizSurveyListInputBaisc = {
    form : $(".form-biz-survey-biz-survey-list-inputBasic"),
	init : function(){
		var $this = this;
        
		//给实名制设置默认选项“否”
		var isreal = this.form.find("#id_isreal");
		var yes = $(isreal).find("option[value='Y']");
		var value = yes.attr("selected");
		if(value == undefined){
			$(isreal).find("option[value='N']").attr("selected","selected");
		}
		
		$(".form-biz-survey-biz-survey-list-inputBasic").data("formOptions", {
	        bindEvents : function() {
	            var $form = $(this);
	            var $city = $form.find("select[name='city']");
	            var area = $form.find("select[name='areaid']");
	           
	            $city.change(function() {
	            	var tmpVal = $city.val();
	            	if(!tmpVal){
	            		area.empty();
	            		area.select2("val", "");
	            		return;
	            	}
	            	var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + tmpVal;
	    	    	$form.ajaxJsonUrl(url, function(data) {
	    	    		var options = {};
	            		area.empty();
	            		
	            		area.append('<option value="*">全部业务区</option>');
	            		var option = '';
	            		$.each(data, function(i, item) {
	            			option = '<option value="'+item.id+'">'+item.name+'</option>';
	            			area.append(option);
	            		})
	            		area.select2("val", "*");
	    	    	});
	            });
	            
	            if (!$($city).val()) {
	            	Biz.selectCityWhenAddData($city);
	            	$($city).change();
				}
	            Biz.disableSelect2WhenNotAdmin($city);

	            if($form.find("#id").val() != ""){
	            	var areaids = $form.find("#areaids").val();
	            	area.select2("val", areaids.split(",")); // 修改问卷时要显式设置多选框的值
	            	
	            	$form.find("button[name='btn_surveynew']").html("<i class='fa fa-check'></i> 保存");
	            }
	            if($form.find("#status").val() == 1){
	            	//状态为截止的问卷则移除按钮，不可保存
	            	$form.find(".form-actions").remove();
	            }
	            $form.find("button[name='btn_surveynew']").click(function(){
	            	BizSurveyListInputBaisc.doSurveyNew($form);
	            });
	        }
	    });
	},
	doSurveyNew : function($form){
		if (!BizSurveyListInputBaisc.checkRequired($form)) {
			return false;
		}

		var url = $form.attr("action");
		var params = {};
		params.sname = $form.find("#id_sname").val();
		params.city = $form.find("#id_city").val();
		params.areaid = $form.find("#id_area").val();
		params.isreal = $form.find("#id_isreal").val();
		params.memo = $form.find("#id_memo").val();
		
		if($form.find("#id").val() == ""){
			//新增问卷，需要先关联题目
			Biz.showQuestionMultipleSelect({
				tabName : "可选题目列表",
				sname : params.sname
			}, {
				listUrl : WEB_ROOT + "/survey/biz-question-list!findByPage"
			}, function() {
				var questionGrid = this;
				params.qids = questionGrid.jqGrid("getDataIDs").join(",");
				BizSurveyListInputBaisc.submitForm(url,params);
			}, $form);
		}else{
			//修改问卷，只能改基本信息，不能改关联题目
			params.id = $form.find("#id").val();
			BizSurveyListInputBaisc.submitForm(url,params);
		}
	},
	checkRequired : function($form){
		var text = "";
		if(this.checkNull($form.find('#id_sname').val())){
			text += "问卷名称、";
		}
		if(this.checkNull($form.find('#id_city').val())){
			text += "地市、";
		}
		if(this.checkNull($form.find("#id_area").val())){
			text += "业务区、";
		}
		if(this.checkNull($form.find("#id_isreal").val())){
			text += "是否实名制、";		
		}
		if(""!= text){
			Global.notify("error",text.substr(0,text.length-1)+" 为必填项!");
			return false;
		}
		return true;
	},
	checkNull : function(objvalue){
		if(objvalue == undefined || objvalue == ""|| objvalue==null 
				||!objvalue){
			return true;
		}
		return false;
	},
	submitForm : function(url, params) {
		$("body").ajaxJsonUrl(url, function(result) {
			if (result.type == "success") {
				Global.notify("success", result.message);
				Biz.removeActiveTab("div-biz-survey-list-index");
				Biz.refreshGrid(".grid-survey-list-index");
			}
		}, params);
	}
}

$(function() {
	BizSurveyListInputBaisc.init();
});