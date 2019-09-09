
AssIndexTogridInputBasic=({
	
	init : function(){
		 $(".form-biz-ass-togrid-ass-index-togrid-inputBasic").data("formOptions", {
		        bindEvents : function() {
		            var $form = $(this);
		            
		            
		            $form.find("button[name='btn_togrid']").click(function(){
		            	if (!AssIndexTogridInputBasic.checkRequiredInput($form)){
	            		    return;
	            	    }
		            	
		            	//格式:数组
		        		var gridids = $form.find("#gridid").val();
		            	
		            	var storeData={};
		            	storeData.assid = $form.find('#assid').val();
		            	storeData.asscontent = $form.find('#asscontent').val();
		            	storeData.bdate = $form.find('#bdate').val();
		            	//storeData.cyclenum = $form.find('#cyclenum').val();
		            	storeData.assnum = $form.find('#assnum').val();
		            	storeData.gridids = gridids;
		            	
		            	storeData.assIndexTogrid = JSON.stringify(storeData);
		            	
		    			var url = WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!doSave';
		    			$("body").ajaxJsonUrl(url, function(result) {
		    				
		    				Global.notify("success", "任务下发成功");        
		    			},storeData);
		            	
		            });
		            
		            
		            $form.find("button[name='btn_hidegrid']").click(function(){
		            	var $dialog = $form.closest(".modal");
		                $dialog.modal("hide");
		              //  AssIndexTogridInputBasic.refresh(".form-biz-ass-index-store-togrid");
		                var callback = $dialog.data("callback");
						if (callback) {
							callback.call();
						}
		            });
		            
		        }
		       
		 });
		
		 AssIndexTogridInputBasic.initGrid();
	}, 
	refresh : function(form){
		var $form = $(form);
		var flag = 0;
		var div_obj = $form.parent() ;
		while(flag<10){
			
			var url = div_obj.attr("data-url");
			if(url){
				div_obj.ajaxGetUrl(url);
				//break;
			}
			div_obj = div_obj.parent();
			flag++;
		};
		
	},
	checkRequiredInput : function ($form) {
		
		var text = "";
		if(AssIndexTogridInputBasic.checkNull($form.find('#bdate').val()))
			text += "考核起始日、";
		if(AssIndexTogridInputBasic.checkNull($form.find('#gridid').val()))
			text += "网格、";
		/*if(AssIndexTogridInputBasic.checkNull($form.find('#cyclenum').val()))
			text += "考核周期(月)、";*/
		if(AssIndexTogridInputBasic.checkNull($form.find('#assnum').val()))
			text += "每月考核数	、";
		
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
	
	initGrid : function() {
		var $form = $(".form-biz-ass-togrid-ass-index-togrid-inputBasic");
		
		var grids = $form.find("#grids").val();
		var grid = $form.find("#gridid");
		 //根据操作员关联的网格去过滤网格
		   var url = WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!findGrids';
		   $form.ajaxJsonUrl(url, function(data) {
			    grid.empty();
			    grid.select2({placeholder: "请选择网格"});
			    grid.append('<option value=""></option>');
	    		var option = '';
	    		$.each(data, function(i, item) {
	    			if (grids.indexOf(data[i].mcode) == -1){
		    			option = '<option value="'+item.id+'">'+item.gridname+'</option>';
		    			grid.append(option);
	    			};
	            });
	    		
	    		$(".select2-drop").css("z-index","10101");
	    	});
		   
		//根据地市过滤网格
		/*var city = $form.find("#city").val();
		 * var gcode = "PRV_GRID_BY_CITY";
		var mcode = city;
		var url = WEB_ROOT + "/prv-sysparam!selectParamList";
    	url = url + "?rows=-1&gcode=" + gcode + "&mcode=" + mcode;
    	$form.ajaxJsonUrl(url, function(data) {
    		grid.empty();
    		grid.select2({placeholder: "请选择网格"});
    		grid.append('<option value=""></option>');
    		var option = '';
    		for(var i = 0 ; i < data.length;i++){
    			
    			if (grids.indexOf(data[i].mcode) == -1){
    				option = '<option value="'+data[i].mcode+'">'+data[i].mname+'</option>';
                    grid.append(option);
    			};
    		}
    		});
    		*/
    		//不添加，会直接在窗口下方，可以注释看看效果
	}
});

$(function() {
    AssIndexTogridInputBasic.init();
});