AssTargetInputBasic=({
	$form: $(".form-biz-ass-target-ass-target-inputBasic"),
	init : function(){
		AssTargetInputBasic.initModifyPageInfo();
		this.$form.find("button[name='btn_store']").click(this.saveListener);
	},
	saveListener:function(){
		
		var $form = AssTargetInputBasic.$form;
		if (!AssTargetInputBasic.checkRequiredInput($form)){
    		return;
    	}
    	var storeData={};
    	
    	var ids =  $form.find('#id').val();
    	if (ids != "") {
    		storeData.id = ids;
    	}
    	
    	storeData.name = $form.find('#name').val();
    	storeData.city = $form.find('#id_city').val();
    	storeData.asscontent = $form.find('#asscontent').val();
    	storeData.status = $form.find('#status').val();
    	storeData.fieldId = $form.find('#fieldId').val();
    	storeData.visql = $form.find('#visql').val();
    	storeData.assSttCycle = $form.find('#id_assSttCycle').val();
    	storeData.assTargetStore = JSON.stringify(storeData);
    	
		var url = WEB_ROOT + '/biz/ass/target/ass-target!doSave';
		$form.ajaxJsonUrl(url, function(result) {
			
			Global.notify("success", "指标保存成功");
			AssTargetStoreIndex.$grid.refresh();//刷新数据
			AssTargetInputBasic.closeTab();
			
		}, storeData);
	},
	initModifyPageInfo : function(event){
		 var $form = this.$form;
		 var id =  this.$form.find('#id').val();
		 if (id != ""){
			 
			var jgrid = $('.grid-biz-ass-store-ass-target-store-index');
			var rowData = jgrid.getRowData(id);
			
			//初始化地市
			$form.data("formOptions", {
		        bindEvents : function() {
		        	
					//初始化统计周期
		        	var $assSttCycle = $form.find("#id_assSttCycle");
		        	var fmCycle=rowData["assSttCycle"];
		        	var valCycle;
		        	if(fmCycle=='天'){valCycle=0;}
		        	else if(fmCycle=='周'){valCycle=1;}
		        	else if(fmCycle=='月'){valCycle=2;}
		        	else if(fmCycle=='年'){valCycle=3;}
		        	$assSttCycle.select2("val",valCycle);
		        	
		        	//初始化地市
		        	AssTargetInputBasic.initCity($form.find("#id_city"),rowData["city"],true)
		        	//非本市的指标不能修改数据，所以隐藏保存按钮
		        	if (!Biz.isCurrentAdmin() && Biz.LOGIN_INFO.city!=rowData["city"]) {
		        		$form.find("button[name='btn_store']").hide();
		        	}
		        }
		    });
			 
		 }else {
			//初始化地市
			$form.data("formOptions", {
		        bindEvents : function() {
		        	//初始化地市
		        	if (!Biz.isCurrentAdmin()) {
			        	AssTargetInputBasic.initCity($form.find("#id_city"),Biz.LOGIN_INFO.city,true)
		        	}
		        	else{ //管理员添加时，可以任选地市
		        		AssTargetInputBasic.initCity($form.find("#id_city"),'',false)
		        	}
		        }
		    });
		 }
	},
	initCity:function($city,selVal,disabled){

		Biz.initSelectOpts('PRV_CITY', $city,selVal, {
			value : "*",
			text : "广东省"
		});
		if(disabled){
			Biz.disableSelect2($city);
		}
//		if (!Biz.isCurrentAdmin()) {
//			Biz.initSelect('PRV_CITY', $city, Biz.LOGIN_INFO.city);
//			Biz.disableSelect2WhenNotAdmin($city);
//		} else {
//			Biz.initSelectOpts('PRV_CITY', $city,selVal, {
//				value : "*",
//				text : "广东省"
//			});
//		}
	}
	,
	checkNull : function(objvalue){
		if(objvalue == undefined || objvalue == "" || objvalue == null || !objvalue){
			return true;
		}
		return false;
	},
	checkRequiredInput : function ($form) {
		var text = "";
		if(AssTargetInputBasic.checkNull($form.find('#name').val()))
			text += "指标名称、";
		if(AssTargetInputBasic.checkNull($form.find('#id_city').val()))
			text += "指标归属、";
		if("" != text){
			alert(text.substr(0,text.length-1) + " 为必填项!");
			return false;
		}
		
    	return true;
	
	},
	closeTab : function() {
		$form=AssTargetInputBasic.$form;
		$form.attr("form-data-modified","false");
		$form.find("#btn_cancel").click();
    }

});

$(function() {
	AssTargetInputBasic.init();
});