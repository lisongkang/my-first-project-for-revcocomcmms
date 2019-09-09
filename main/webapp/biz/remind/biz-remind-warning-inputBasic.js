
BizRemindWarningInputBasic=({
	
	init : function(){
		 $(".form-biz-remind-biz-remind-warning-inputBasic").data("formOptions", {
		        bindEvents : function() {
		            var $form = $(this);
		        }
		        
		 });
		 
		 BizRemindWarningInputBasic.initFormInfo();
	}, 
	
	initFormInfo : function() {
		var $form = $(".form-biz-remind-biz-remind-warning-inputBasic");
		var objtypeSelect = $form.find("#id_objtype");
	    
		var id =  $form.find('#id').val();
		if (id != ""){
			 var jgrid = $('.grid-biz-remind-biz-remind-warning-index');
			 var rowData = jgrid.getRowData(id);
			 var h_objtype =  rowData["objtype"];
			 
			 BizRemindWarningInputBasic.initSelect('BIZ_REM_OBJTYPE',objtypeSelect,h_objtype);
		     
		} else {
			 BizRemindWarningInputBasic.initSelect('BIZ_REM_OBJTYPE',objtypeSelect);
		}
		
	},
	
	initSelect : function(gcode,obj,value){
		obj.empty();
		var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode;
		obj.ajaxJsonUrl(url, function(data) {
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.mcode+'">'+item.display+'</option>';
    			obj.append(option);
            });
            obj.select2("val", value);
    	});
	},
	
	selectRemObj : function() {
		 var $form = $(".form-biz-remind-biz-remind-warning-inputBasic");
		 var url = WEB_ROOT + '/market/market-batch!forward?_to_=select';
		 $form.popupDialog({
				url : url,
				title : '选择对象',
	            id : 'selectRemObj',
	            callback : function(item) {
	            	$(".form-biz-remind-biz-remind-warning-inputBasic #objids").val(item.idsJson);
	            	$(".form-biz-remind-biz-remind-warning-inputBasic #objnames").val("已选择对象");
	            }
	     });
	}
	
});

$(function() {
	BizRemindWarningInputBasic.init();
});