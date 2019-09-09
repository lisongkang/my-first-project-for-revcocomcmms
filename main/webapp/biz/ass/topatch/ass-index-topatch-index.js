
AssIndexTopatchIndex = ({
	
	init : function() {
	    $(".grid-biz-ass-topatch-ass-index-topatch-index").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!findByPage',
	        colModel : [ {
	            label : '流水号',
	            name : 'id',
	            hidden : true                          
	        }, {
	            label : '考核指标id',
	            name : 'assid',
	            width : 60,
	            editable: true,
	            hidden : true,
	            align : 'right'
	        }, {
	            label : '所属网格',
	            name : 'tgridid',
	            width : 60,
	            editable: true,
	            hidden : true,
	            align : 'right'
	        }, {
	            label : '所属网格',
	            name : 'gridname',
	            width : 60,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search: false
	        }, {
	            label : '片区代码',
	            name : 'patchid',
	            width : 60,
	            editable: true,
	            hidden : true,
	            align : 'right'
	        }, {
	            label : '片区名称',
	            name : 'patchName',
	            width : 100,
	            editable: true,
	            align : 'left',
	            sortable : false,
	            search: false
	        }, {
	            label : '片区对应网格',
	            name : 'pgridid',
	            width : 60,
	            editable: true,
	            hidden : true,
	            align : 'right'
	        }, {
	            label : '考核起始日期',
	            name : 'bdate',
	            width : 100,
	            formatter: 'date',
	            editable: true,
	            align : 'center',
	            sortable : false,
	            search: false
	        }, {
	            label : '接受任务数',
	            name : 'revnum',
	            width : 60,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search: false
	        }, {
	            label : '完成目标方式',
	            name : 'mode',
	            width : 60,
	            editable: true,
	            hidden : true,
	            align : 'left'
	        }, {
	            label : '完成目标方式',
	            name : 'modename',
	            width : 60,
	            editable: true,
	            align : 'left',
	            sortable : false,
	            search: false
	        }, {
	            label : '每期目标数',
	            name : 'assnum',
	            width : 60,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search: false
	        }, {
	            label : '操作时间',
	            name : 'assdate',
	            width : 100,
	            formatter: 'date',
	            editable: true,
	            align : 'center',
	            sortable : false,
	            search: false
	        }, {
	            label : '操作部门',
	            name : 'depart',
	            width : 60,
	            editable: true,
	            hidden : true,
	            align : 'right'
	        }, {
	            label : '操作员',
	            name : 'operator',
	            width : 60,
	            editable: true,
	            hidden : true,
	            align : 'right'
	        } ],
	        //editurl : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!doSave',
	        delurl : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!doDelete',
	        //fullediturl : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!inputTabs'
	        
	        gridComplete: function () {
	            var ids = $(".grid-biz-ass-topatch-ass-index-topatch-index").jqGrid('getDataIDs');
	            for (var i = 0; i < ids.length; i++) {
	                var id = ids[i];
	                var rowData = $(".grid-biz-ass-topatch-ass-index-topatch-index").getRowData(id);
	            
	                var mode = rowData["mode"];
	                
	                var optBtn = "";
	                // MODE : 0-每期相同；1-分别指定;9-无固定
	                if (mode == 1) {
	                	 optBtn = "<a href='javascript:;' style='color:#f60' onclick='AssIndexTopatchIndex.detail(\""+id+"\")' >详情</a>";
	                }
	                
	                if (optBtn != "") {
	                	jQuery(".grid-biz-ass-topatch-ass-index-topatch-index").jqGrid('setRowData', ids[i], { assnum: optBtn });
	                }
	            }
	         }
	    });

	},
	
	detail : function(id) {
		 if(!id) return ;

		 var $form = $(".form-biz-ass-topatch-ass-index-topatch-index");
		 var assid = $form.find("#assid").val();
		 $form.popupDialog({
				url : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!toAssnumPagelist?storeId='+assid+"&taskid="+id,
	            title : '分期指定目标数',
	            id : 'diffAssnumDetail',
	            callback : function(item) {
	            	//$(".form-biz-ass-topatch-ass-index-topatch-inputBasic #serialno").val(item.serialno);
	            }
	     });
	 
	}
	
});

$(function() {
	  // 初始化
	  AssIndexTopatchIndex.init();
});
