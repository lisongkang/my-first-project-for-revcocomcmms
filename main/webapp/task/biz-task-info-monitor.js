var BizTaskInfoMonitor={
		$form:$(".form_biz_task_info_monitor"),
		$grid:$(".grid_biz_task_info_monitor"),
		init:function(){
			this.initSearchParam();
			this.$grid.data("gridOptions",{
				 url : WEB_ROOT + '/task/biz-task-info!findPageForMonitor',
				 colModel :[
					{
					    label : '是否超时',
					    name : 'isOverTime',
					    sortable : false,
					    search :  false,
					    width : 40,
					    stype : 'select',
					    editoptions : {
					    	value :{'Y':'是','N':'否'}
					    }
					},
					{
					    label : '任务编码',
					    name : 'taskid',
					    sortable : false,
					    search :  false,
					    width : 40,
					},
					{
					    label : '任务名称',
					    name : 'tasktitle',
					    sortable : false,
					    search :  false,
					    width : 100,
					},
					{
					    label : '任务类型',
					    name : 'type',
					    sortable : false,
					    search :  false,
					    width : 80,
					    stype : 'select',
			            editoptions : {
			            	value :Biz.getCacheParamDatas("BIZ_TASK_TYPE")
			            }
					},
					{
					    label : '任务状态',
					    name : 'status',
					    sortable : false,
					    search :  false,
					    width : 50,
					    stype : 'select',
			            editoptions : {
			            	value :Biz.getCacheParamDatas("BIZ_TASK_STATUS")
			            }
					},
					{
					    label : '是否过期',
					    name : 'isexpired',
					    sortable : false,
					    search :  false,
					    width : 20,
					    stype : 'select',
			            editoptions : {
			            	value :Biz.getCacheParamDatas("BIZ_TASK_ISEXPIRED")
			            }
					},
					{
					    label : '网格',
					    name : 'gridname',
					    sortable : false,
					    search :  false,
					    width : 80,
					},
					/*{
					    label : '创建时间',
					    name : 'optime',
					    sortable : false,
					    search :  false,
					    width : 80,
					    formatter: 'date',
					},
					{
					    label : '创建人',
					    name : 'opname',
					    sortable : false,
					    search :  false,
					    width : 50,
					},*/
					{
					    label : '操作',
					    name : 'self_opt',
					    sortable : false,
					    search :  false,
					    width : 50,
					}
				 ],
				 ondblClickRow:function(){
					 
				 },
				 gridComplete : function() {
					    //隐藏
					    Biz.hideTableElement(BizTaskInfoMonitor.$grid,".btn-group-contexts");
					    
					    //隐藏前方的任务编码
						Biz.addCustomBtnToGrid(".grid_biz_task_info_monitor", [{
							subBtns : [{
								btnName : "查看",
								btnAction : "BizTaskInfoMonitor.showAttrDetail",
								canAddBtn : function(grid, rowData) {
									return true;
								}},
								{
								btnName : "督办",
								btnAction : "BizTaskInfoMonitor.monitorTheTask",
								canAddBtn : function(grid, rowData) {
									return rowData.isOverTime == "Y";
								}}
							]
						}]);
					}
			});
		},
		showAttrDetail:function(id){
			var gridObj = BizTaskInfoMonitor.$grid;
			var rowData = gridObj.jqGrid("getRowData", id);
			
			var taskid = rowData.taskid;
			$(gridObj).popupDialog({
	    		url : WEB_ROOT + '/task/biz-task-info!showTaskInfoFromMonitor?taskid='+taskid,
	            id : 'show_biz_task_info_monitor',
	            size:'modal-wide',
	    		title : '任务信息',
	    		width:'400px',
	    		callback : function(data) {
	    			
	    			if (data) {
	    				 Biz.refreshGrid(".grid_biz_task_info_monitor");
	    			}
	    		}
	    	});
		},
		monitorTheTask : function(){
			
		},
		initSearchParam:function(){
			//初始化地市
			var $form = this.$form;
			var type = $form.find("#id_type");
			Biz.initSelect('BIZ_TASK_TYPE',type,'');
			
			var status = $form.find("#id_status");
			Biz.initSelect('BIZ_TASK_STATUS',status,'');
			
		}
};

$(function(){
	BizTaskInfoMonitor.init();
});