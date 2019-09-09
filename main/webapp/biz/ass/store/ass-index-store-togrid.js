AssIndexStoreTogrid = ({
	$grid : $(".grid-biz-ass-store-ass-index-store-togrid"),
	init : function (){

	    $(".grid-biz-ass-store-ass-index-store-togrid").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/store/ass-index-store!findByPage?flag=togrid',
	        colModel : [ {
	            label : '操作',
	            name : 'self_opt',
	            hidden : false,
	            sortable : false,
	            search :  false,
	            width : 300
	        },{
	            label : '流水号',
	            name : 'id',
	            search :  false,
	            hidden : true                          
	        }, {
	            label : '分公司',
	            name : 'city',
	            width : 100, 
	            editable: true,
	            search :  false,
	            hidden : true,
	            align : 'left'
	        },{
	            label : '分公司',
	            name : 'cityName',
	            width : 100, 
	            editable: true,
	            search :  false,
	            align : 'left'
	        }, {
	            label : '考核内容',
	            name : 'asscontent',
	            width : 200,
	            editable: true,
	            search :  false,
	            align : 'left'
	        }, {
	            label : '考核参数',
	            name : 'assparam',
	            width : 100,
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'left'
	        },{
	            label : '考核参数名称',
	            name : 'assparamName',
	            width : 100,
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'left'
	        },{
	            label : '考核对象id',
	            name : 'objid',
	            width : 100,
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'left'
	        },{
	            label : '考核对象',
	            name : 'assobj',
	            width : 100,
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'left'
	        }, {
	            label : '考核周期单位',
	            name : 'unit',
	            width : 100,
	            editable: true,
	            search :  false,
	            align : 'right'
	        }, {
	            label : '任务单位',
	            name : 'taskunit',
	            width : 100,
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'left'
	        }, {
	            label : '任务单位名称',
	            name : 'taskunitName',
	            width : 100,
	            editable: true,
	            search :  false,
	            align : 'left'
	        },{
	            label : '任务总数值',
	            name : 'totalnum',
	            width : 100,
	            editable: true,
	            search :  false,
	            align : 'right'
	        }, {
	            label : '失效时间',
	            name : 'expdate',
	            width : 100,
	            formatter: 'date',
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'center'
	        }, {
	            label : '制定部门',
	            name : 'depart',
	            width : 100,
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'right'
	        }, {
	            label : '制定部门名称',
	            name : 'deptName',
	            width : 100,
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'right'
	        },{
	            label : '操作员',
	            name : 'operator',
	            width : 100,
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'right'
	        }, {
	            label : '操作时间',
	            name : 'opdate',
	            width : 100,
	            formatter: 'date',
	            editable: true,
	            hidden : true,
	            search :  false,
	            align : 'center'
	        },{
	            label : '网格数量',
	            name : 'gridcount',
	            width : 100,
	            search :  false,
	            editable: true
	        },{
	            label : '网格IDs',
	            name : 'grids',
	            width : 100,
	            hidden : true,
	            search :  false,
	            editable: true
	        } ],	        
	        gridComplete: function () {
	            var ids = $(".grid-biz-ass-store-ass-index-store-togrid").jqGrid('getDataIDs');
	            for (var i = 0; i < ids.length; i++) {
	                var id = ids[i];
	                var rowData = $(".grid-biz-ass-store-ass-index-store-togrid").getRowData(id);
	                var grids = rowData["grids"];
	                
	                var optBtn = "";
	                var gridcount = parseInt(rowData["gridcount"]);
	                if (gridcount > 0) {
		                optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='AssIndexStoreTogrid.retogrid(\""+id+"\",\""+grids+"\")' >继续下达</a>";
		                optBtn = optBtn + "&nbsp;|&nbsp;";
		                optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='AssIndexStoreTogrid.deltogrid(\""+id+"\",\""+grids+"\")' >取消下达</a>";
	                } else {
	                	 optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='AssIndexStoreTogrid.togrid(\""+id+"\",\""+grids+"\")' >任务下达</a>";
	                }
	                
	                jQuery(".grid-biz-ass-store-ass-index-store-togrid").jqGrid('setRowData', ids[i], { self_opt: optBtn });
	                
	                
	                //隐藏搜索按钮
		            Biz.hideTableElement(AssIndexStoreTogrid.$grid,".ui-icon-search");
	            }
	         }
	    });

	},
	
	togrid : function(id,grids) {
		var $form = $('.form-biz-ass-index-store-togrid');
		//强行修改
		AssIndexStoreTogrid.changeDialogTitle("#dialog_level_togrid","任务下达");
		$form.popupDialog({
			url : WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!viewTogridTabs?storeId='+id+'&grids='+grids,
            title : '任务下达',
            id:"togrid",
            callback : function(item) {
            	//清除代码
            	$form.find('#btn_search').click();
            }
        });
		
	},
	
	retogrid : function(id,grids) {
		
		var $form = $('.form-biz-ass-index-store-togrid');
		
		//强行修改
		AssIndexStoreTogrid.changeDialogTitle("#dialog_level_togrid","继续下达");
		
		$form.popupDialog({
			url : WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!viewTogridTabs?storeId='+id+'&grids='+grids,
            title : '继续下达',
            id:"togrid",
            callback : function(item) {
            	//清除代码
            	$form.find('#btn_search').click();
            }
        });
	},
	
	deltogrid : function(id,grids) {
		
		//强行修改
		AssIndexStoreTogrid.changeDialogTitle("#dialog_level_togrid","取消下达");
		
		var $form = $('.form-biz-ass-index-store-togrid');
		$form.popupDialog({
			url : WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!viewTogridTabs?storeId='+id+'&grids='+grids,
            title : '取消下达',
            id:"togrid",
            callback : function(item) {
            	$form.find('#btn_search').click();
            }
        });
	},
	
	detailtogrid : function(id,grids) {
		
		var $form = $('.form-biz-ass-index-store-togrid');
		//强行修改
		AssIndexStoreTogrid.changeDialogTitle("#dialog_level_togrid","任务下达");
		
		$form.popupDialog({
			url : WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!forward?_to_=viewTabs',
            title : '任务下达',
            id : 'togrid',
            callback : function(item) {
            	$form.find('#btn_search').click();
            }
        });
	},
	/**
	 * 
	 * @param dialogId : 弹出框Id(例如 ："#dialog_level_deltogrid")
	 * @param title：修改成的弹出框名称
	 */
	changeDialogTitle: function(dialogId,title){
		
		var dialog_deltogrid = $(dialogId);
		if(dialog_deltogrid){
			var modal_title = dialog_deltogrid.find(".modal-title");
			modal_title.html(title);
		}
	}

});


$(function() {
    AssIndexStoreTogrid.init();
});
