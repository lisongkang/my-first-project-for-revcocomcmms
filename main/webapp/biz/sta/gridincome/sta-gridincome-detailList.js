UserNewResaddr=({
	
	init : function(){
		 var $form = $(".form-biz-sta-gridincome-sta-gridincome-detail-list");
		 
		$(".grid-biz-sta-gridincome-sta-gridincome-detail-list").data("gridOptions", {
	        url : WEB_ROOT + '/channel/biz/usernew/user-new!findAddr',
	        colModel : [ {
	            label : '地址id',
	            name : 'houseid',
	            hidden : true
	        }, {
	            label : '头地址',
	            name : 'addr',
	            hidden : true
	        }, {
	            label : '尾地址',
	            name : 'endaddr',
	            hidden : true
	        }, {
	            label : '住宅地址',
	            name : 'whladdr',
	            width : 700,
	            align : 'left'
	        }, {
	            label : '业务区id',
	            name : 'areaid',
	            hidden : true
	        }, {
	            label : '片区',
	            name : 'patchid',
	            hidden : true,
	            width : 150,
	            align : 'center',
	            editoptions : {
	            	//value : Biz.getPrvParamListDatas("RES_HOUSE_STATUS")
	            	value : Biz.getCacheParamDatas("RES_PATCH")
	            }
	            
	        }, {
	            label : '可安装业务',
	            name : 'permark',
	            width : 200,
	            align : 'center',
	            hidden : true
	        }, {
	            label : '住宅状态',
	            name : 'status',
	            width : 200,
	            align : 'center',
	            hidden : true,
	            editoptions : {
	            	//value : Util.getCacheSelectOptionDatas(WEB_ROOT + "/prv-sysparam!selectParamList?gcode=RES_HOUSE_STATUS")
	            	value : Biz.getCacheParamDatas("RES_HOUSE_STATUS")
	            	//value : Biz.getCacheParamDatas("RES_HOUSE_STATUS")
	            }
	        }],
	        rowNum : 10,
	        multiselect : false,
	        toppager : false,
	        onSelectRow : function(id) {
	            var $grid = $(this);
	            var $dialog = $grid.closest(".modal");
	            $dialog.modal("hide");
	            var callback = $dialog.data("callback");
	            if (callback) {
	                var rowdata = $grid.jqGrid("getRowData", id);
	                rowdata.id = id;
	                callback.call($grid, rowdata);
	            }
	        }
	    });

	}
	
	
	
});


$(function() {
	UserNewResaddr.init();
	
});