MarketBatchInputBasic = ({
	init : function() {
		$(".form-biz-market-market-batch-inputBasic").data("formOptions", {
	        bindEvents : function() {
	            var $form = $(this);
	        }
	    });
		
		MarketBatchInputBasic.initFormInfo2();
	},
	
	initFormInfo2 : function() {
		var $form = $(".tab-content .active .form-biz-market-market-batch-inputBasic");
//		var $form = $(".form-biz-market-market-batch-inputBasic");
		var areaidsSelect = $form.find("#id_areaids");
		var url = WEB_ROOT + "/market/market-batch!getAreaids";
	    $("body").ajaxJsonUrl(url, function(data) {
	    	 areaidsSelect.empty();
	    	 areaidsSelect.select2({placeholder: "请选择业务区"});
	    	 areaidsSelect.append('<option value=""></option>');
	    	 var option = '';
	    		$.each(data, function(i, item) {
	    			var disp = '['+item.id+']' + item.name;
	    			option = '<option value="'+item.id+'">'+disp+'</option>';
	    			areaidsSelect.append(option);
	            });
	     });
	    
		var id =  $form.find('#id').val();
		if (id != ""){
			 var jgrid = $('.grid-biz-market-market-batch-index');
			 var rowData = jgrid.getRowData(id);
			 var h_knowid =  rowData["knowid"];
			 var h_status =  rowData["status"];
			 
	         var knowidSelect = $form.find("#id_knowid");
		     MarketBatchInputBasic.initSelectKnow(knowidSelect,h_knowid);
		     
	         var statusSelect = $form.find("#id_status");
		     statusSelect.select2("val",h_status);
		     
		} else {
	         var knowidSelect = $form.find("#id_knowid");
		     MarketBatchInputBasic.initSelectKnow(knowidSelect);
		}
		
	},
	
	initSelectKnow : function(obj, value){
		var url = WEB_ROOT + "/market/market-batch!getKnowids";
		obj.ajaxJsonUrl(url, function(data) {
			obj.empty();
			obj.select2({placeholder: "请选择营销任务"});
			obj.append('<option value=""></option>');
	    	var option = '';
	        $.each(data, function(i, item) {
	    		var isHasValue = (typeof (value) != "undefined") && (value == item.id);
				option = '<option value="' + item.id + '"'
							+ (isHasValue ? ' selected="selected"' : '') + '>'
							+ item.knowname + '</option>';
				
	    		obj.append(option);
	        });
	        obj.select2("val", value);
	        
		});
	}
	
});

$(function() {
	  // 初始化
	MarketBatchInputBasic.init();
});
