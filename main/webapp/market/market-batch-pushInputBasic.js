
MarketBatchPushInputBasic=({
	
	init : function(){
		 $(".form-market-market-batch-pushInputBasic").data("formOptions", {
		        bindEvents : function() {
		            var $form = $(this);
		            $form.find("button[name='btn_push']").click(function(){
		            	
		            });
		        },
		        
		 });
		 
		 
		// 初始化时间
//        var $form = $(".form-market-market-batch-pushInputBasic");
//        var knowxx = $form.find("#knowxx").val();
//        var knowidxx = $form.find("#knowidxx").val();
		 
		 MarketBatchPushInputBasic.initPushOperids();
        
	}, 
	
	checkRequiredInput : function () {
		var $form = $(".form-market-market-batch-pushInputBasic");
		
		var text = "";
		if(MarketBatchPushInputBasic.checkNull($form.find('#pushOperids').val()))
			text += "推送网格人员、";
		if(MarketBatchPushInputBasic.checkNull($form.find('#pushCustomerObj').val()))
			text += "营销客户、";
		
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
	
	initPushOperids : function() {
		var $form = $(".form-market-market-batch-pushInputBasic");
		var pushOperids = $form.find("#pushOperids");
		
		var url = WEB_ROOT + "/market/market-batch!getGridOperList";
    	$form.ajaxJsonUrl(url, function(data) {
    		pushOperids.empty();
    		pushOperids.select2({placeholder: "请选择推送网格人员"});
    		pushOperids.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.operid+'">'+item.opername+'</option>';
    			pushOperids.append(option);
                
            })
    	});
	},
	
	 selectCustMarket : function() {
		 var $form = $(".form-market-market-batch-pushInputBasic");
		 var taskid = $form.find("#taskid").val();
		 var url = WEB_ROOT + '/market/market-batch!selectCustMarket?taskid='+taskid;
		 $form.popupDialog({
				url : url,
				title : '选择营销客户',
	            id : 'selectCustMarket',
	            callback : function(item) {
	            	$(".form-market-market-batch-pushInputBasic #pushCustomerObj").val(item.custsJson);
	            	$(".form-market-market-batch-pushInputBasic #pushCustomerDisp").val(item.custDisp);
	            }
	     });
	 }

});

$(function() {
	MarketBatchPushInputBasic.init();
});