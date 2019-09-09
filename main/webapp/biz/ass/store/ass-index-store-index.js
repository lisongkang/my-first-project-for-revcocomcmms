AssIndexStoreIndex = ({
	
	init : function (){

	    $(".grid-biz-ass-store-ass-index-store-index").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/store/ass-index-store!findByPage',
	        colModel : [ {
	            label : '操作',
	            name : 'self_opt',
	            hidden : true,
	            sortable : false,
	            search :  false,
	            width : 100
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
	        }, {
	            label : '分公司',
	            name : 'cityName',
	            width : 100, 
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'left'
	        },{
	            label : '考核内容',
	            name : 'asscontent',
	            width : 200,
	            editable: true,
	            sortable : false,
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
	            sortable : false,
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
	            sortable : false,
	            search :  false,
	            align : 'left'
	        }, {
	            label : '考核周期单位',
	            name : 'unit',
	            width : 100,
	            editable: true,
	            search :  false,
	            hidden : true,
	            align : 'right'
	        }, {
	            label : '考核周期单位',
	            name : 'unitname',
	            width : 100,
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'right'
	        },{
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
	            sortable : false,
	            search :  false,
	            align : 'left'
	        },{
	            label : '任务总数值',
	            name : 'totalnum',
	            width : 100,
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'right'
	        }, {
	            label : '失效时间',
	            name : 'expdate',
	            width : 100,
	            formatter: 'date',
	            editable: true,
	            sortable : false,
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
	            sortable : false,
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
	        } ],
//	        editurl : WEB_ROOT + '/biz/ass/store/ass-index-store!doSave',
	        delurl : WEB_ROOT + '/biz/ass/store/ass-index-store!doDelete',
//	        fullediturl : WEB_ROOT + '/biz/ass/store/ass-index-store!inputTabs',
	        fullediturl : WEB_ROOT + '/biz/ass/store/ass-index-store!edit',
	        
	        gridComplete: function () {
//	            var ids = $(".grid-biz-ass-store-ass-index-store-index").jqGrid('getDataIDs');
//	            for (var i = 0; i < ids.length; i++) {
//	                var id = ids[i];
//	                var rowData = $(".grid-biz-ass-store-ass-index-store-index").getRowData(id);
//	            
//	                var optBtn = "";
//	                optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='AssIndexStoreIndex.deleteStore(\""+id+"\")' >删除</a>";
//	                jQuery(".grid-biz-ass-store-ass-index-store-index").jqGrid('setRowData', ids[i], { self_opt: optBtn });
//	            }
	         }
	    });

	},
	
	deleteStore : function(id) {
		var data={};
    	data.id = id;
    	
    	var $form = $(".form-biz-ass-index-store-index");
		var url = WEB_ROOT + '/biz/ass/store/ass-index-store!deleteStore';
		$("body").ajaxJsonUrl(url, function(result) {
			Global.notify("success", "考核指标删除成功");
			
			$form.find('#btn_search').click();
		},data);
	}

});


$(function() {
    AssIndexStoreIndex.init();
});
