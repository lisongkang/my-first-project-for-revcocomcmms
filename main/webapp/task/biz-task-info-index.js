var BizTaskInfoIndex = {
	$form : $(".form_biz_task_info_index"),
	$grid : $(".grid_biz_task_info_index"),
	init:function(){
		this.initSearchParam();
		this.$grid.data("gridOptions",{
			 url : WEB_ROOT + '/task/biz-task-info!findByPage',
			 colModel :[
				
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
				    width : 40,
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
				    width : 40,
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
				    width : 40,
				    stype : 'select',
		            editoptions : {
		            	value :Biz.getCacheParamDatas("BIZ_TASK_ISEXPIRED")
		            }
				},
				{
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
				},
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
			 operations : function(items) {
				 var $this = BizTaskInfoIndex;
		    	 var createTaskBtn = Biz.createCustomBtn(true, " 创建任务", "fa-retweet", $this.createTaskBtnAction);
		    	 items.push(createTaskBtn);
		    	 
		    	 var deployResourceBtn = Biz.createCustomBtn(true, "分配任务", "fa-retweet", $this.deployResourceBtnAction);
		    	 items.push(deployResourceBtn);
		    	 
		    	 var submitTaskBtn = Biz.createCustomBtn(true, "提交任务", "fa-retweet", $this.submitTaskBtnAction);
		    	 items.push(submitTaskBtn);
		    	
			 },
			 gridComplete : function() {
				    //隐藏
				    Biz.hideTableElement(BizTaskInfoIndex.$grid,".btn-group-contexts");
				    
				    //隐藏前方的任务编码
					Biz.addCustomBtnToGrid(".grid_biz_task_info_index", [ {
						subBtns : [ {
							btnName : "查看",
							btnAction : "BizTaskInfoIndex.showAttrDetail",
							canAddBtn : function(grid, rowData) {
								return true;
							}},
							
							{
								btnName : "修改",
								btnAction : "BizTaskInfoIndex.editAttrInfo",
								canAddBtn : function(grid, rowData) {
									return rowData.status == "0";
							}},
							{
								btnName : "删除",
								btnAction : "BizTaskInfoIndex.deleteTask",
								canAddBtn : function(grid, rowData) {
									return rowData.status == "0";
							}
						}]
					}]);
					
					//隐藏列中的
					BizTaskInfoIndex.hideTableCheckBox();
				}
		});
	},
	/**
	 * 查看
	 * @param id
	 */
	showAttrDetail:function(id){
		var gridObj = BizTaskInfoIndex.$grid;
		var rowData = gridObj.jqGrid("getRowData", id);
		var taskid = rowData.taskid;
		$(gridObj).popupDialog({
    		url : WEB_ROOT + '/task/biz-task-info!showTaskInfo?taskid='+taskid,
            id : 'create_task_info',
            size:'modal-wide',
    		title : '查看任务',
    		width:'400px',
    		callback : function(data) {
    			if (data) {
    				 Biz.refreshGrid(".grid_biz_task_info_index");
    			}
    		}
    	});
	},
	/**
	 *修改
	 */
	editAttrInfo : function(id){
		var gridObj = BizTaskInfoIndex.$grid;
		var rowData = gridObj.jqGrid("getRowData", id);
		
		var taskid = rowData.taskid;
		$(gridObj).popupDialog({
    		url : WEB_ROOT + '/task/biz-task-info!inputBasic?taskid='+taskid,
            id : 'create_task_info',
            size:'modal-wide',
    		title : '任务信息',
    		width:'400px',
    		callback : function(data) {
    			if (data) {
    				 Biz.refreshGrid(".grid_biz_task_info_index");
    			}
    		}
    	});
	},
	deleteTask : function(id){
		var gridObj = BizTaskInfoIndex.$grid;
		var rowData = gridObj.jqGrid("getRowData", id);
		var taskid = rowData.taskid;
	    var data={};
	    
		var url =  WEB_ROOT + '/task/biz-task-info!deleteTask?taskid='+taskid;
		$("body").ajaxJsonUrl(url, function(result) {
			
			if(result=="success"){
				Global.notify("success", "删除任务成功");
			}
			Biz.refreshGrid(".grid_biz_task_info_index");
		}, data);
	},
	/**
	 * 隐藏表格中的选中，只有编辑状态才可选择
	 */
	hideTableCheckBox : function(grid){
		var grid = this.$grid;
		var ids = grid.jqGrid("getDataIDs");
		for (var i = 0, size = ids.length; i < size; i++) {
			var id = ids[i];
			var rowData = grid.jqGrid("getRowData", id);
			if(rowData.status != "0"){
				grid.jqGrid("setRowData",id,{cb:""});
			}
		}
		
		
		
	},
	createTaskBtnAction:function(){
		var gridObj = BizTaskInfoIndex.$grid;
			$(gridObj).popupDialog({
	    		url : WEB_ROOT + '/task/biz-task-info!inputBasic',
	            id : 'create_task_info',
	            size:'modal-wide',
	    		title : '任务信息',
	    		width:'400px',
	    		callback : function(data) {
	    			
	    			if (data) {
	    				 Biz.refreshGrid(".grid_biz_task_info_index");
	    			}
	    		}
	    	});
	},
	deployResourceBtnAction:function(){
		var gridObj = BizTaskInfoIndex.$grid;
		var taskids = BizTaskInfoIndex.getSelectItem();
		if(taskids==""){
			Global.notify("error", "请选择至少一行任务(勾选行中复选框才有效)");
			return ;
		}
		var url= WEB_ROOT + '/task/biz-task-info!toDeployPage?taskids='+taskids;
		$(gridObj).popupDialog({
    		url : url,
            id : 'replay_task_info',
            size:'modal-wide',
    		title : '任务分配',
    		callback : function(data) {
    			if (data.type == "success") {
    				$grid.refresh();
    			}
    		}
    	});
	},
	getSelectItem:function(){
		var gridObj = BizTaskInfoIndex.$grid;
		var ids = gridObj.jqGrid('getGridParam','selarrrow');
		
		if(ids.length==0){
			return "";
		}
		var taskids = "";
		for(var i = 0 ; i < ids.length;i++ ){
			var rowData = gridObj.jqGrid("getRowData", ids[i]);
			if(rowData.status == "0"){
				taskids+=rowData.taskid;
			}
			if(i != ids.length-1){
				taskids+=",";
			}
		}
		return taskids ;
	},
	submitTaskBtnAction:function(){
		
		var taskids = BizTaskInfoIndex.getSelectItem();
		if(taskids==""){
			Global.notify("error", "请选择至少一行任务(勾选行中复选框才有效)");
			return ;
		}
	    var data={};
		var url =  WEB_ROOT + '/task/biz-task-info!doSubmitTask?taskids='+taskids;
		$("body").ajaxJsonUrl(url, function(result) {
			
			if(result!=""){
				Global.notify("error", "任务编码："+result+"需先分配资源再提交");
			}else{
				Global.notify("success", "提交任务成功");
			}
			Biz.refreshGrid(".grid_biz_task_info_index");
		}, data);
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
	BizTaskInfoIndex.init();
});