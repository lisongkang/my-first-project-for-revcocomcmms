AssIndexStoreTopatch = ({
	$grid : $(".grid-biz-ass-store-ass-index-store-topatch"),
	init : function (){

	    $(".grid-biz-ass-store-ass-index-store-topatch").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/store/ass-index-store!findByPage?flag=topatch',
	        colModel : [ {
	            label : '操作',
	            name : 'self_opt',
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
	        },{
	            label : '分公司',
	            name : 'cityName',
	            width : 100, 
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'left'
	        }, {
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
	            hidden : true,
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
	            hidden : true,
	            search :  false,
	            align : 'left'
	        }, {
	            label : '考核周期单位',
	            name : 'unit',
	            width : 100,
	            editable: true,
	            hidden : true,
	            sortable : false,
	            search :  false,
	            align : 'right'
	        }, {
	            label : '考核周期单位',
	            name : 'unitname',
	            width : 100,
	            editable: true,
	            sortable : false,
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
	            label : '片区数量',
	            name : 'gridcount',
	            width : 100,
	            sortable : false,
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
	        fullediturl : WEB_ROOT + '/biz/ass/store/ass-index-store!inputTabs',
	        gridComplete: function () {
	            var ids = $(".grid-biz-ass-store-ass-index-store-topatch").jqGrid('getDataIDs');
	            for (var i = 0; i < ids.length; i++) {
	                var id = ids[i];
	                var rowData = $(".grid-biz-ass-store-ass-index-store-topatch").getRowData(id);
	                
	                var optBtn = "";
	                optBtn = "<a href='javascript:;' style='color:#f60' onclick='AssIndexStoreTopatch.topatch(\""+id+"\")' >下达片区</a>";
	                
	                jQuery(".grid-biz-ass-store-ass-index-store-topatch").jqGrid('setRowData', ids[i], { self_opt: optBtn });
	            }
	            //隐藏表格中的导航钮
	            //下拉按钮
	            Biz.hideTableElement(AssIndexStoreTopatch.$grid,".btn-group-contexts");
	            
	            //新增按钮
	            Biz.hideTableElement(AssIndexStoreTopatch.$grid,".jqgrid-options");

	            //搜索按钮
	            Biz.hideTableElement(AssIndexStoreTopatch.$grid,".ui-icon-search");
	        }
	    });

	},
	
	topatch : function(id) {
		if(!id){
			Global.notify("error", "考核id不能为空");
			return ;
		}
		var rowData = $(".grid-biz-ass-store-ass-index-store-topatch").getRowData(id);
		
		var selector = "tbody tr#"+id;
		var $thistr = $(".grid-biz-ass-store-ass-index-store-topatch").find(selector);
		
		//好吧，非要先单击再双击才行
		$thistr.trigger("click");
		$thistr.trigger("dblclick");
			
	},
	
	retopatch : function(id,grids) {
		
		var $form = $('.form-biz-ass-index-store-topatch');
		$form.popupDialog({
			url : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!viewTopatchTabs?storeId='+id+'&grids='+grids,
            title : '继续下达',
            callback : function(item) {
//            	Global.notify("success", "考核指标删除成功");
//            	$form.find('#btn_search').click();
//            	$(".form-biz-chnl-settle-result-index #_deptName").val(" [" +item.id + "] " + item.name);
//            	$(".form-biz-chnl-settle-result-index #_deptId").val(item.id);
            }
        });
	},
	
	deltopatch : function(id,grids) {
		
		var $form = $('.form-biz-ass-index-store-topatch');
		$form.popupDialog({
			url : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!viewTopatchTabs?storeId='+id+'&grids='+grids,
            title : '取消下达',
            callback : function(item) {
//            	Global.notify("success", "考核指标删除成功");
//            	$form.find('#btn_search').click();
//            	$(".form-biz-chnl-settle-result-index #_deptName").val(" [" +item.id + "] " + item.name);
//            	$(".form-biz-chnl-settle-result-index #_deptId").val(item.id);
            }
        });
	},
	
	detailtopatch : function(id,grids) {
		
		var $form = $('.form-biz-ass-index-store-topatch');
		$form.popupDialog({
			url : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!forward?_to_=viewTabs',
            title : '任务下达',
            callback : function(item) {
//            	Global.notify("success", "考核指标删除成功");
//            	$form.find('#btn_search').click();
//            	$(".form-biz-chnl-settle-result-index #_deptName").val(" [" +item.id + "] " + item.name);
//            	$(".form-biz-chnl-settle-result-index #_deptId").val(item.id);
            }
        });
	}

});


$(function() {
    AssIndexStoreTopatch.init();
});
