
AssIndexTopatchInputBasicBat=({
	init : function(){
		 $(".form-biz-ass-topatch-ass-index-topatch-inputBasicBat").data("formOptions", {
		        bindEvents : function() {
		            var $form = $(this);
		            
		            $form.find("button[name='btn_topatchBat']").click(function(){
		            	if (!AssIndexTopatchInputBasicBat.checkRequiredInput($form)){
	            		    return false;
	            	    }
		            	
		            	//格式:数组
		            	var storeData={};
		            	storeData.tgridid = $form.find('#tgridid').val();
		            	storeData.bdate = $form.find('#bdate').val();
		            	storeData.assid = $form.find('#assid').val();
		            	storeData.mode = $form.find('#mode').val();
		            	storeData.attachmentId = $($form.find("table input[name='_attachment_default']")[0]).val();
		            	storeData.assIndexTopatch = JSON.stringify(storeData);
		            	
		    			var url = WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!doSaveBat';
		    			$("body").ajaxJsonUrl(url, function(result) {
		    				Global.notify("success", "批量任务下达成功");
		    				
		    				//清空表单值
		    				$form.find('#tgridid').select2("val", "");
		    				$form.find('#assid').select2("val", "");
		    				$form.find('#bdate').val(new Date().getFullYear()+"-01-01");
		    				$form.find("#fileDiv table button")[0].click();
		    			},storeData);
		            });
		        },
		        
		 });
		 
		 $('#bdate').val(new Date().getFullYear()+"-01-01"); //默认为本年度1月1日
		 AssIndexTopatchInputBasicBat.initModes();
		 AssIndexTopatchInputBasicBat.initGrids();
		 AssIndexTopatchInputBasicBat.initStores();
	}, 
	
	checkRequiredInput : function ($form) {
		var text = "";
		var afterText = "";
		if(AssIndexTopatchInputBasicBat.checkNull($form.find('#tgridid').val()))
			text += "所属网格、";
		if(AssIndexTopatchInputBasicBat.checkNull($form.find('#assid').val()))
			text += "考核内容、";
		if(AssIndexTopatchInputBasicBat.checkNull($form.find('#mode').val()))
			text += "完成目标方式、";
		if(AssIndexTopatchInputBasicBat.checkNull($form.find('#bdate').val()))
			text += "考核起始日、";
		
		var files = $form.find("table input[name='_attachment_default']");
		var fileNum = parseInt(files.length);
		if (fileNum == 0) {
			text += "附件、";
		}else if(fileNum == 1){
			var fileName = $($form.find("table a")[0]).text() + "";
			var fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length);
			if(fileType.toLowerCase() != ".txt"){
				afterText += "只能选择txt文件!";
			}
			
			var fileSize = $($($form.find("table .size")[0])).text();
			if(fileSize == "0 bytes"){
				afterText += "文件大小不能为0!";
			}
		}else{
			afterText += "只能上传一个文件!";
		}
		
		if("" != text||"" != afterText){
			alert(("" != text ? (text.substr(0, text.length - 1) + " 为必填项!")
					: "")
					+ ("" != afterText ? afterText : ""));
			return false;
		}
		
    	return true;
	},
	
	checkNull : function(objvalue){
		if(objvalue == undefined || objvalue == "" || objvalue == null || !objvalue){
			return true;
		}
		return false;
	},
	
	initModes : function() {
		var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasicBat");
		Biz.initSelect("BIZ_ASS_MODE", $form.find("#mode"), 9);
	},
	
	initGrids : function() {
		var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasicBat");
		var tgridid = $form.find("#tgridid");
		
		var url = WEB_ROOT + "/biz/ass/topatch/ass-index-topatch!getGrids";
    	$form.ajaxJsonUrl(url, function(data) {
    		tgridid.empty();
    		tgridid.select2({placeholder: "请选择网格"});
    		tgridid.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.id+'">'+item.gridname+'</option>';
    			tgridid.append(option);
            })
    	});
	},
	
	initStores : function() {
		var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasicBat");
		var assid = $form.find("#assid");
		
		var url = WEB_ROOT + "/biz/sta/assindex/sta-assindex!getAssListByCity";
    	$form.ajaxJsonUrl(url, function(data) {
    		assid.empty();
    		assid.select2({placeholder: "请选择考核内容"});
    		assid.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.mcode+'">'+item.mname+'</option>';
    			assid.append(option);
            })
    	});
	},
	
	changeGrid : function(){
	    var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasicBat");
		var gridid = $form.find("#tgridid").val();
	 }
});

$(function() {
	AssIndexTopatchInputBasicBat.init();
});