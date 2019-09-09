<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/contextjs/context.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/assets/plugins/contextjs/context.css">
<SCRIPT type="text/javascript">
		var setting = {
			key : {
				name : "name"
			},
			data : {
				simpleData : {
					enable : true,
					rootPId : -1,
					idKey : "id",
					pIdKey : "previd"
				}
			},
			check: {
				enable: true,
				chkboxType:{"Y":"ps", "N":"ps"}
			}  
		};
		
		$(document).ready(function(){
			loadData();
			$("#btn_select_grid").click(function(){
				var selNodes=getCheckedLeafNodes();
				AssTargetTogridToPatch.initSelGrid(selNodes);
				$("#btn_select_cancel").click();
			});
			
		});
		
		//获取所有选择的叶子节点
		function getCheckedLeafNodes(){
			var zTree = $.fn.zTree.getZTreeObj("patchGridTree");
			var selNodes=zTree.getCheckedNodes();
			var selLeafNodes=[];
			for(var i in selNodes){
				if(!selNodes[i].isParent){
					selLeafNodes.push(selNodes[i]);
				}
			}
			return selLeafNodes;
		}
		
		function loadData(){
			var url = WEB_ROOT + "/market/grid-info!gridTree";
	    	$("body").ajaxJsonUrl(url, function(response) {
	    		
	    		console.log(response);
	    		$.fn.zTree.init($("#patchGridTree"), setting,response.userdata);
	    		
	    		var zTree = $.fn.zTree.getZTreeObj("patchGridTree");
	    		var node = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
	    		zTree.expandNode(node);//展开根节点
	    		
	    		var ids=AssTargetTogridToPatch.getCheckedIds();
	    		var idArr=ids.split(",");
	    		for(var i=0;i<idArr.length;i++){
	    			if(!idArr[i]) continue;
		    		//选中节点
					var node = zTree.getNodeByParam("id",idArr[i] );
					zTree.checkNode(node, true, true, false);
	    		}
	    	});
		}
	</SCRIPT>
<s:token />
<div id="treeDiv" style="overflow-y:auto;overflow-x:auto;">
	<ul id="patchGridTree" class="ztree ztree_demo" style="min-width: 300px;"></ul>
</div>
<div class="form-actions right">
		<button class="btn blue" type="button" id="btn_select_grid" name="btn_toPatch" 
			data-grid-reload=".grid-biz-ass-store-ass-index-store-index">
			<i class="fa fa-check"></i> 确定
		</button>
		<button class="btn default hidden-inline-xs" type="button"
			id="btn_select_cancel" style="margin-right: 0px;" data-dismiss="modal"
			aria-hidden="true">
			<i class="fa fa-undo"></i>&nbsp; 取&nbsp;消
		</button>
	</div>
<!-- <script src="ass-target-togrid-tree.js" /> -->
<%@ include file="/common/ajax-footer.jsp"%>