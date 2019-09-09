
AssIndexTopatchInputBasic=({
	
	init : function(){
		 $(".form-biz-ass-topatch-ass-index-topatch-inputBasic").data("formOptions", {
		        bindEvents : function() {
		            var $form = $(this);
		            
		            
		            $form.find("button[name='btn_topatch']").click(function(){
		            	if (!AssIndexTopatchInputBasic.checkRequiredInput($form)){
	            		    return false;
	            	    }
		            	
		            	//格式:数组
		        		var patchids = $form.find("#patchid").val();
		        		
		        		var mode = $form.find('#mode').val();
		        		var assnumValue="";
		        		var serialno = "";
		        		if (mode == 0) {
		        			assnumValue = $form.find("#assnum").val();
		        		} else if (mode == 1) {
		        			serialno = $form.find("#serialno").val();
		        		} else if (mode == 9) {
		        			assnumValue = "0";
		        		}
		            	
		            	var storeData={};
		            	storeData.tgridid = $form.find('#tgridid').val();
		            	storeData.patchids = patchids;
		            	storeData.bdate = $form.find('#bdate').val();
		            	storeData.assid = $form.find('#assid').val();
		            	storeData.revnum = $form.find('#revnum').val();
		            	storeData.mode = $form.find('#mode').val();
		            	
		            	if (assnumValue != ""){
		            		storeData.assnum = assnumValue;
		            	}
		            	if (serialno != ""){
		            		storeData.serialno = serialno;
		            	}
		            	
		            	storeData.assIndexTopatch = JSON.stringify(storeData);
		            	
		    			var url = WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!doSave';
		    			$("body").ajaxJsonUrl(url, function(result) {
		    				Global.notify("success", "任务下达成功1");
		    				$form.find('#btn_cancel').click();
		    				var $indexForm = $(".form-biz-ass-index-store-topatch");
		    				$indexForm.find('#btn_search').click();
				            
		    			},storeData);
		            	
		            });
		        },
		        
		 });
		 
		 
		// 初始化时间
		var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasic");
		var curDate =  AssIndexTopatchInputBasic.getCurrYearFirstDate();
		$form.find('#bdate').val(curDate)
		 
		 // 初始化清空片区
		 AssIndexTopatchInputBasic.initPatchs("");
		 
		 AssIndexTopatchInputBasic.initGrids();
		 AssIndexTopatchInputBasic.initModes();
	}, 
	
	checkRequiredInput : function ($form) {
		
		var text = "";
		if(AssIndexTopatchInputBasic.checkNull($form.find('#tgridid').val()))
			text += "所属网格、";
		if(AssIndexTopatchInputBasic.checkNull($form.find('#patchid').val()))
			text += "片区、";
		if(AssIndexTopatchInputBasic.checkNull($form.find('#revnum').val()))
			text += "接受任务数、";
		if(AssIndexTopatchInputBasic.checkNull($form.find('#mode').val()))
			text += "完成目标方式	、";
		
		var mode = $form.find('#mode').val();
		var assnumValue="";
		if (mode == 0) {
			if(AssIndexTopatchInputBasic.checkNull($form.find('#assnum').val()))
				text += "每期目标数、";
		} else if (mode == 1) {
			if(AssIndexTopatchInputBasic.checkNull($form.find('#serialno').val()))
				text += "每期目标数、";
		} else if (mode == 9) {
			if(AssIndexTopatchInputBasic.checkNull($form.find('#assnum').val()))
				text += "每期目标数、";
		}
		
		if(AssIndexTopatchInputBasic.checkNull($form.find('#bdate').val()))
			text += "考核起始日、";
		
		if("" != text){
			alert(text.substr(0,text.length-1) + " 为必填项!");
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
		 var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasic");
		 var mode = $form.find("#mode");
		 Biz.initSelect("BIZ_ASS_MODE",mode);
	},
	
	initPatchs : function(gridid) {
		var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasic");
		var patchid = $form.find("#patchid");
		
		var patchs = $form.find("#grids").val();
		
		var url = WEB_ROOT + "/biz/ass/topatch/ass-index-topatch!getPatchsByGridid?gridid="+ gridid;
    	$form.ajaxJsonUrl(url, function(data) {
    		patchid.empty();
    		patchid.select2({placeholder: "请选择片区"});
    		
    		patchid.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
    			var composition = item.patchid +"~" +item.id;
    			if (patchs.indexOf(item.patchid) == -1){
    				option = '<option value="'+composition+'">'+item.patchname+'</option>';
    				patchid.append(option);
    			}
                
            })
    	});
	},
	
	initGrids : function() {
		var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasic");
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
	
	changeGrid : function(){
	    var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasic");
		var gridid = $form.find("#tgridid").val();
		AssIndexTopatchInputBasic.initPatchs(gridid);
	 },
	 
	 changeMode : function(){
		 var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasic");
		 var mode = $form.find("#mode").val();
		 
		 var sameAssnum = $form.find("#sameAssnum");
		 var diffAssnum = $form.find("#diffAssnum");
		 
		 if (mode == 0){
			 sameAssnum.css("display","block");
			 diffAssnum.css("display","none");
			 var assnum = $form.find("#assnum");
			 assnum.val("");
		 } else if (mode == 1){
			 sameAssnum.css("display","none");
			 diffAssnum.css("display","block");
		 }else if (mode == 9){
			 sameAssnum.css("display","block");
			 diffAssnum.css("display","none");
			 var assnum = $form.find("#assnum");
			 assnum.val("0");
		 }
	 },
	 
	 clickDiffAssnum : function() {
		 var $form = $(".form-biz-ass-topatch-ass-index-topatch-inputBasic");
		 var assid = $form.find("#assid").val();
		 var serialno = $form.find("#serialno").val();
		 var url = WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!toAssnumPage?storeId='+assid + "&serialno="+serialno;
		 $form.popupDialog({
				url : url,
				title : '分期指定目标数',
	            id : 'clickDiffAssnum',
	            callback : function(item) {
	            	$(".form-biz-ass-topatch-ass-index-topatch-inputBasic #serialno").val(item.serialno);
	            	$(".form-biz-ass-topatch-ass-index-topatch-inputBasic #serialno2").val("点击查看每期目标数");
	            }
	     });
	 },
	 
	 getCurrYearFirstDate : function() {
		 var date = new Date();
		 var year = date.getFullYear();
		    
		 var currYearFirstDate = year+"-01-01";
		 return currYearFirstDate;
	 }

});

$(function() {
	AssIndexTopatchInputBasic.init();
});