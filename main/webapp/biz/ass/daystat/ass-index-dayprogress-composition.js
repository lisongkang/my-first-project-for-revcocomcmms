var DayGridTreeObj = {
	ztreeObj : {},
	url : WEB_ROOT + "/biz/ass/monstat/ass-index-monprogress!gridTreeForManagerGrid",
	setting : {
		check: {
			enable: true
		},
		key : {
			name : "name"
		},
		data : {
			simpleData : {
				enable : true,
				rootPId : -1,
				idKey : "id",
				pIdKey : "statid"
			}
		},
		view : {
			dblClickExpand : false
		},
		callback : {
			
			onCheck : function(event, treeId, treeNode) {
				var $this = DayGridTreeObj;
				if (treeNode.gtype == 0) {
					$this.ztreeObj.expandNode(treeNode, true);
					
					var grids = [];
					var nodes = $this.ztreeObj.getCheckedNodes(true);
					for (var i = 0; i < nodes.length; i++) {
						
						// 查询gtype=1的子节点，如果不是叶子的管理网格则丢掉
						var subnodes = $this.ztreeObj.getNodesByParam("gtype", 1, nodes[i]);
						
						if (subnodes.length > 0) {
							var isLeafManagerGrid = true;
							for (var j = 0; j < subnodes.length; j++) {
								if (subnodes[j].statid != nodes[i].id) {
									isLeafManagerGrid = false;
									break;
								}
							}
							
							if (isLeafManagerGrid) {
								grids.push(nodes[i].id);
							}
						}
					}
					
					AssIndexDayprogressIndex.initDataFromTree(grids);
				}
					
			},
			onRightClick : function(event, treeId, treeNode){
				//console.info(treeNode.name);
			}
		}
	},
	init : function() {
		var $this = this;
//		$.getJSON($this.url, function(result) {
		$("#dayGridTree").ajaxJsonUrl($this.url, function(result) {	
			if (result.type == "success" ){
				$.fn.zTree.init($("#dayGridTree"), $this.setting, result.userdata);
				$this.ztreeObj = $.fn.zTree.getZTreeObj("dayGridTree");
			} else {
				Global.notify("error", result.message);
			}
		});
	}
};

$(function() {
	DayGridTreeObj.init();
});