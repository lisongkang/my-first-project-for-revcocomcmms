var BizTaskInfoReplayTask={
	$grid:$(".grid_biz_task_info_choices"),
	$form:$(".grid_biz_task_info_replaytask"),
    init:function(){
    	TaskGridTreeObj.init();
    	$("#biz-task-info-tree #treeType").bind("change", function(e) {
    		var treeType = $(this).val();
    		if (treeType == "") {
    			// 取消选中即选回默认的基础网格
    			treeType = "0";
    			$(this).select2("val", 0);
    		}
    		TaskGridTreeObj.init();
    	});
    	
    	this.$form.find(".btn_replaytask").unbind("click").bind("click",this.sumbitRepalyTask);
	    this.$form.find(".btn-cancel1").unbind("click").bind("click",this.colseDialog);
    }
     ,
     colseDialog:function(){
    	var $this = BizTaskInfoReplayTask.$form;
 		var $dialog =$this.closest(".modal");
 		$dialog.modal("hide");
     },
     sumbitRepalyTask :function(){
    	var grids = BizTaskInfoReplayTask.getSelectNodes();
    	if(grids==""){
    		Global.notify("error", "请勾选需要分配到的网格");
    		return;
    	}
        var data ={};
        var taskAndGridRelation = {};
        taskAndGridRelation.grids = grids ;
        taskAndGridRelation.taskids = taskids;
        
        data.taskAndGridRelation = JSON.stringify(taskAndGridRelation);
    	var url =  WEB_ROOT + '/task/biz-task-info!doSaveRelationTaskAndGrid';
		$("body").ajaxJsonUrl(url, function(result) {
			Global.notify("success", "保存成功");
			BizTaskInfoReplayTask.colseDialog(true);
		}, data);
     },
     getSelectNodes:function(){
    	var selectNodes = TaskGridTreeObj.treeObj.getCheckedNodes(true);
  		var grids = "";
  		for(var i =0;i<selectNodes.length;i++)
  		{
  			grids =grids+selectNodes[i].id+",";
  		}
  		
  		grids = grids.substring(0,grids.length-1);
  		return grids;
     }

};

var TaskGridTreeObj={
		url : WEB_ROOT + "/market/grid-info!gridTree",
		treeId : "BizTaskInfoReplayTree",
		treeObj : null,
		setting : {
			check: {
				enable: true,
				chkboxType:{ "Y" : "", "N" : ""}
			},
			key : {
				name : "name"
			},
			data : {	
				key : {
					name : "name"
				},
				simpleData : {
					enable : true,
					rootPId : -1,
					idKey : "id",
					pIdKey : "previd"
				}
			},
			edit:{
				drag : {
					isCopy : false,
					prev : false,
					next : false
				},
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				dblClickExpand : false
			},
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					
					
				}
			}
			
		}
		,
		init: function(){
			var $this = this;
			var treeObj = $("#" + $this.treeId);
		    var treeType = $("#biz-task-info-tree #treeType").val();
			var url = $this.url+"?gtype="+treeType;
			$(treeObj).ajaxJsonUrl(url, function(result) {
				if (result.type == "success") {					
					$.fn.zTree.init(treeObj, $this.setting, result.userdata);
					$this.treeObj = $.fn.zTree.getZTreeObj($this.treeId);
					
				} else {
					Global.notify("error", result.message);
				}
			});
		}
			
};

$(function(){
  BizTaskInfoReplayTask.init();
});