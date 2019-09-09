MarketBatchCust=({
	init : function(){
		$(".grid-market-market-batch-cust").data("gridOptions", {
	        url : WEB_ROOT + '/market/market-batch!findCust',
	        colModel : [ {
	            label : '客户标识',
	            name : 'custid',
	            width : 170
	        }, {
	            label : '客户名称',
	            name : 'custname',
	            width : 170
	        }, {
	            label : '联系电话/手机',
	            name : 'mobile',
	            width : 170
	        },{
	            label : '地址id',
	            name : 'houseid',
	            hidden : true
	        }, {
	            label : '住宅地址',
	            name : 'whladdr',
	            width : 290
	        },{
	            label : '业务区id',
	            name : 'areaid',
	            hidden : true
	        }, {
	        	label : '片区id',
	            name : 'patchid',
	            hidden : true
	        }]
	 
	    });
		
	},
	
	checkInput : function () {
		var $form = $(".form-market-market-batch-cust");
		
		var text = "";
		if(MarketBatchCust.checkNull($form.find('#patchid').val()))
			text += "片区、";
		if(MarketBatchCust.checkNull($form.find('#netattr').val()))
			text += "单双向属性、";
		
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
	
	initDataFromTree : function(selectGridids) {
		var grids = "";
		for (var i = 0; i < selectGridids.length; i++) {
			grids += selectGridids[i] + ",";
		}
		
		if (grids.length > 0) {
			grids = grids.substring(0, grids.length - 1);
		}
		
		var $form = $(".form-market-market-batch-cust");
		$form.find("#id_strGrids").val(grids);
		
		MarketBatchCust.initPatchids(selectGridids);
	},
	
	initPatchids : function(selectGridids) {
		var $form = $(".form-market-market-batch-cust");
		var patchid = $form.find("#patchid");
		
    	var storeData={};
    	storeData.secondGridids = selectGridids;
    	storeData.assIndexMonprogress = JSON.stringify(storeData);
    	
		var url = WEB_ROOT + '/biz/ass/monstat/ass-index-monprogress!getThirdPatchidBySecondGridid';
		$("body").ajaxJsonUrl(url, function(data) {
			patchid.empty();
			patchid.select2({placeholder: "请选择片区"});
			patchid.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.patchid+'">'+item.patchname+'</option>';
    			patchid.append(option);
            });
		},storeData);
		
	},
	
	selectCusts : function() {
		var $form = $(".form-market-market-batch-cust");
		var $grid = $(".grid-market-market-batch-cust");
		
		var selectedIds = $grid.jqGrid("getGridParam", "selarrrow");
		if (selectedIds.length == 0) {
			alert("请先选中记录！");
			return;
		}
		
		var custDisp = "";
		var custArr = new Array();
		for ( var int = 0; int < selectedIds.length; int++) {
			var selectedId = selectedIds[int];
			var rowData = $grid.jqGrid("getRowData",selectedId);
			
			custArr[int] = rowData;
			custDisp += rowData.custname+",";
		}

		if (custDisp != "") {
			custDisp = custDisp.substring(0, custDisp.length-1);
		}
		
		var custData = {};
		custData.custMarketInfo = custArr;
    	var custsJson = JSON.stringify(custData);
    	
    	var $dialog = $form.closest(".modal");
		$dialog.modal("hide");
		
		var callback = $dialog.data("callback");
		if (callback) {
			var retData = {};
			retData.custsJson = custsJson;
			retData.custDisp = custDisp;
			
		    callback.call($form, retData);
		}
		
	}

});


$(function() {
	MarketBatchCust.init();
	
});