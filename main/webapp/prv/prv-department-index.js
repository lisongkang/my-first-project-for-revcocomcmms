var DeptTreeObj = {
	url : WEB_ROOT + "/prv/prv-department!deptTree",
	ztreeId : "deptTree",
	ztreeObj : {},
	selectedGrid : {},
	setting : {
		key : {
			name : "name"
		},
		data : {
			simpleData : {
				enable : true,
				rootPId : 0,
				idKey : "id",
				pIdKey : "preid"
			}
		},
		view : {
			dblClickExpand : false
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				var $this = DeptTreeObj;
				$this.selectedGrid = {
					id : treeNode.id
				};
				$this.ztreeObj.expandNode(treeNode, true);
				DeptListObj.reload(treeNode.id);
			}
		}
	},
	init : function() {
		var $this = this;
		var treeObj = $("#" + $this.ztreeId);
		$(treeObj).ajaxJsonUrl($this.url, function(result) {
			if (result.type == "success") {
				$.fn.zTree.init(treeObj, $this.setting, result.userdata);
				$this.ztreeObj = $.fn.zTree.getZTreeObj($this.ztreeId);
				var fisrtNode = $this.ztreeObj.getNodesByParam("preid", 0)[0];
				$this.selectedGrid = {
					id : fisrtNode.id
				};
			} else {
				Global.notify("error", result.message);
			}
		});
	}
};

$(function() {
	DeptTreeObj.init();
});