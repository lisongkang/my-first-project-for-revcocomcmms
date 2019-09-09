var CommonTreeObj = {
	url : null,
	ztreeId : null,
	ztreeObj : {},
	isInited : false,
	gridType : {
		manage : "manageGrid",
		patch : "patchGrid",
		address : "addressGrid"
	},
	setting : {},
	init : function() {
		var $this = this;
		var treeObj = $("#" + $this.ztreeId);
		$(treeObj).ajaxJsonUrl($this.url, function(result) {
			if (result.type == "success") {
				$.fn.zTree.init(treeObj, $this.setting, result.userdata);
				$this.ztreeObj = $.fn.zTree.getZTreeObj($this.ztreeId);
				$this.afterInit();
				$this.isInited = true;
			} else {
				Global.notify("error", result.message);
			}
		});
	},
	afterInit : function() {
	},
	getPidKeyName : function() {
		return this.setting.data.simpleData.pIdKey;
	},
	addOrUpdateRefresh : function(isAdd, newNodeInfo) {
		var kidKeyName = this.getPidKeyName();
		var parentNodeInfo = {
			nodeKey : "id",
			nodeValue : newNodeInfo[kidKeyName]
		};
		var nodeInfo;
		if (isAdd) {
			// 新增记录
			nodeInfo = {
				nodeKey : "id",
				nodeValue : newNodeInfo.id,
				id : newNodeInfo.id,
				name : newNodeInfo.gridname,
				gridcode : newNodeInfo.gridcode,
				gtype : newNodeInfo.gtype,
				previd : newNodeInfo.previd,
				statid : newNodeInfo.statid
			};

			if (kidKeyName == "previd") {
				// 新增基础网格则按gtype设置图标
				var gtype = nodeInfo.gtype;
				if (gtype == 0) {
					nodeInfo.iconSkin = this.gridType.manage;
				} else if (gtype == 1) {
					nodeInfo.iconSkin = this.gridType.patch;
				} else {
					nodeInfo.iconSkin = this.gridType.address;
				}
			} else {
				// 新增数据网格则都为patch图标
				nodeInfo.iconSkin = this.gridType.patch;
			}
		} else {
			// 修改记录
			nodeInfo = {
				nodeKey : "id",
				nodeValue : newNodeInfo.id,
				name : newNodeInfo.gridname,
				gtype : newNodeInfo.gtype
			};
		}
		this.addOrUpdateNode(isAdd, parentNodeInfo, nodeInfo);
	},
	addOrUpdateNode : function(isAdd, parentNodeInfo, nodeInfo) {
		var ztreeObj = this.ztreeObj;
		var parentNode = ztreeObj.getNodeByParam(parentNodeInfo.nodeKey, parentNodeInfo.nodeValue);

		if (isAdd) {
			ztreeObj.addNodes(parentNode, [ nodeInfo ], true);

			if (this.getPidKeyName() == "statid") {
				// 新增数据网格后更新父节点的图标为manage
				if (parentNode.gtype == 0) {
					parentNode.iconSkin = this.gridType.manage;
				} else {
					parentNode.iconSkin = this.gridType.address;
				}
				ztreeObj.updateNode(parentNode, false);
			}
		} else {
			var nodeToUpdate = ztreeObj.getNodeByParam(nodeInfo.nodeKey, nodeInfo.nodeValue, parentNode);
			for ( var attr in nodeInfo) {
				if (attr != "nodeKey" && attr != "nodeValue") {
					nodeToUpdate[attr] = nodeInfo[attr];
				}
			}
			ztreeObj.updateNode(nodeToUpdate, false);
		}

		// 操作完成后展开父节点
		ztreeObj.expandNode(parentNode, true);
	},
	deleteRefresh : function(nodeIds) {
		var ztreeObj = this.ztreeObj;
		var parentNode = null;
		var pidKeyName = this.getPidKeyName();
		for (var i = 0, size = nodeIds.length; i < size; i++) {
			var nodeToDelete = ztreeObj.getNodeByParam("id", nodeIds[i]);
			if (nodeToDelete) {
				// 全部删除失败时传入的nodeIds.length不为0，有一个空字符串元素，所以要判断nodeToDelete
				if (i == 0 && pidKeyName == "statid") {
					parentNode = ztreeObj.getNodeByParam("id", nodeToDelete[pidKeyName]);
				}
				ztreeObj.removeNode(nodeToDelete);
			}
		}

		if (parentNode) {
			// 批量删除数据网格后，如果父数据网格已没有子数据网格，则更新其图标
			var childNodes = ztreeObj.getNodesByParam(pidKeyName, parentNode.id, parentNode);
			if (childNodes.length == 0) {
				parentNode.iconSkin = this.gridType.patch;
				ztreeObj.updateNode(parentNode, false);
			}
		}
	},
	addNodes : function(nodeList) {
		for (var i = 0, size = nodeList.length; i < size; i++) {
			this.addOrUpdateRefresh(true, nodeList[i]);
		}
	}
};

var GridTreeObj = {
	url : WEB_ROOT + "/market/grid-info!gridTree",
	ztreeId : "gridTree",
	selectedManageGrid : {},
	selectedPatchGrid : {},
	isClickManageGrid : true,
	isLeftClick : true,
	isRightClickManageGrid : false,
	selectedSameLevelNodes : true,
	rightSelectedGrid : {},
	setting : {
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
				var $this = GridTreeObj;
				$this.isLeftClick = true;
				if (treeNode.gtype == 0 ) {
					// 点击管理网格查表格
					$this.isClickManageGrid = true;
					$this.selectedManageGrid = {
						id : treeNode.id,
						name : treeNode.name
					};

					$this.ztreeObj.expandNode(treeNode, true);
					GridListObj.reload($this.getPidKeyName(), treeNode.id);
				} else {
					// 小区或地址网格则显示编辑表单
					var parentNode = $this.ztreeObj.getNodeByParam("id", treeNode.previd);
					$this.isClickManageGrid = false;
					$this.selectedPatchGrid = {
						preGridName : parentNode.name
					};
					GridListObj.editGrid(treeNode.id);
				}
			},
			onRightClick : function(event, treeId, treeNode){
				if (treeNode) {
					// 节点处右键treeNode不为null，否则为null
					var $this = GridTreeObj;
					$this.isLeftClick = false;
					var parentNode = $this.ztreeObj.getNodeByParam("id", treeNode.previd); // previd=-1时parentNode为null
					$this.isRightClickManageGrid = treeNode.gtype == 0 ? true : false;
					$this.rightSelectedGrid = {
						id : treeNode.id,
						name : treeNode.name,
						gtype : treeNode.gtype,
						preGridName : parentNode ? parentNode.name : null
					};
					
					ContextObj.show(event);
				}
			},
			beforeDrop : function(treeId, treeNodes, targetNode, moveType, isCopy) {
				if (!targetNode) {
					Global.notify("error", "不能设置为根基础网格！");
					return false;
				}
				if (targetNode.gtype == 1) {
					Global.notify("error", "片区网格不能添加子基础网格！");
					return false;
				}
                
				if (targetNode.gtype == 2) {
					Global.notify("error", "地址网格不能添加子基础网格！");
					return false;
				}
				if(targetNode.city != treeNodes[0].city ){
					Global.notify("error", "网格不允许跨地市移动！");
					return false;
				}
				var $this = GridTreeObj;
				var confirmMsg = "确认移动所选基础网格到【" + targetNode.name + "】吗？";
				if (!$this.selectedSameLevelNodes) {
					confirmMsg = "所选基础网格不是同级节点，只能移动与网格【" + treeNodes[0].name + "】同级的节点<br/>" + confirmMsg;
				}
				bootbox.confirm(confirmMsg, function(g) {
					if (g) {
						var idsArray = [];
						for (var i = 0, size = treeNodes.length; i < size; i++) {
							idsArray.push(treeNodes[i].id);
						}
						
						var url = WEB_ROOT + "/market/grid-info!updatePreBaseGrid";
						var params = {
						    newPreid : targetNode.id,
							ids : idsArray.join(",")
						};
						$("body").ajaxJsonUrl(url, function(result) {
							if (result.type == "success") {
		    					Global.notify("success", result.message);
		    					$this.updateRefresh(targetNode, treeNodes);
		    				}
		    			}, params);
						return true;
					}
				});
				return false; // 直接返回false禁止拖动，在确认的回调中更新节点，实现拖动效果，否则不能先确认能否拖动
			},
			beforeDrag : function(treeId, treeNodes) {
				// 开始拖动前先判断所选基础网格是否同级节点
				var $this = GridTreeObj;
				var selectedNodes = $this.ztreeObj.getSelectedNodes();
				var selectedPrevid = -1;
				var index = 0;
				var size = selectedNodes.length;
				for (; index < size; index++) {
					if (index == 0) {
						selectedPrevid = selectedNodes[index].previd;
					} else {
						if (selectedPrevid != selectedNodes[index].previd) {
							$this.selectedSameLevelNodes = false;
							break;
						}
					}
				}
				if (index == size) {
					$this.selectedSameLevelNodes = true;
				}
				return true;
			}
		}
	},
	afterInit : function() {
		// 生成树后先默认相当于点击了第一个根管理网格，另表格中也默认查询该管理网格的所属网格
		var fisrtNode = this.ztreeObj.getNodesByParam("previd", -1)[0];
		this.isLeftClick = true;
		this.isClickManageGrid = true;
		this.selectedManageGrid = {
			id : fisrtNode.id,
			name : fisrtNode.name
		};
	},
	updateRefresh : function(newParentNode, nodesToUpdate) {
		var ztreeObj = this.ztreeObj;

		// 更新新所属基础网格的图标
		if (newParentNode.gtype == 0) {
			newParentNode.iconSkin = this.gridType.manage;
		} else {
			newParentNode.iconSkin = this.gridType.address;
		}
		ztreeObj.updateNode(newParentNode, false);

		// 更新所选网格的所属基础网格，并移动到新所属网格
		var oldPrevid = nodesToUpdate[0].previd;
		var newPrevid = newParentNode.id;
		for (var i = 0, size = nodesToUpdate.length; i < size; i++) {
			var nodeToUpdate = nodesToUpdate[i];
			nodeToUpdate.previd = newPrevid;
			ztreeObj.updateNode(nodeToUpdate, false);
			ztreeObj.moveNode(newParentNode, nodeToUpdate, "inner", false);
		}

	}
};
GridTreeObj = $.extend({}, CommonTreeObj, GridTreeObj);

var ContextObj = {
	init : function() {
		menuContext.init();
	},
	show : function(event) {
		var ct = new Date().getTime();
		$("body").append(menuContext.justBuild(this.menu, ct));
		menuContext.show(ct, event);
	},
	hide : function() {
		menuContext.hide();
	},
	menu : [ {
		header : "基本操作"
	}, {
		text : "新增下属网格",
		enableIf : function() {
			return GridTreeObj.isRightClickManageGrid;
		},
		action : function(e) {
			GridListObj.editGrid();
			if (typeof (GridInfoInputBasic) != "undefined") {
				// 表示已生成过一次编辑表单
				GridInfoInputBasic.changePreGridInfoWhenAdd();
			}
		}
	}, {
		text : "编辑该网格",
		enableIf : function() {
			return true;
		},
		action : function(e) {
			GridListObj.editGrid(GridTreeObj.rightSelectedGrid.id);
		}
	}, {
		text : "删除该网格",
		enableIf : function() {
			return GridTreeObj.rightSelectedGrid.preGridName != null;
		},
		action : function(e) {
			var idsArray = [ GridTreeObj.rightSelectedGrid.id ];
			GridListObj.doDelete(idsArray);
		}
	}, {
		divider : true
	}, {
		header : "其它操作"
	}, {
		text : "批量删除所选网格",
		enableIf : function() {
			return GridTreeObj.rightSelectedGrid.preGridName != null;
		},
		action : function(e) {
			// 批量删除前先判断
			// 1.根网格是否处于选中状态，若是则把该网格置为非选中状态，并把该网格从待删除网格数组中移除
			// 2.当前右键网格是否处于选中状态，若不是则把该网格置为选中状态，并把该网格纳入待删除网格数组中
			var rootPid = GridTreeObj.setting.data.simpleData.rootPId;
			var includeRootGrid = false;
			var rightSelectedGridId = GridTreeObj.rightSelectedGrid.id;
			var includeRightSelectedGrid = false;
			var idsArray = [];
			idsArray.push(rightSelectedGridId);

			var toDeleteGrids = GridTreeObj.ztreeObj.getSelectedNodes();
			for (var index = 0, size = toDeleteGrids.length; index < size; index++) {
				var toDeleteGridId = toDeleteGrids[index].id;
				var toDeleteGridPrevid = toDeleteGrids[index].previd;
				if (toDeleteGridPrevid != rootPid && toDeleteGridId != rightSelectedGridId) {
					idsArray.push(toDeleteGridId);
				} else {
					if (toDeleteGridPrevid == rootPid) {
						includeRootGrid = true;
					} else {
						includeRightSelectedGrid = true;
					}
				}
			}

			if (includeRootGrid) {
				var rootGrid = GridTreeObj.ztreeObj.getNodeByParam("previd", rootPid);
				GridTreeObj.ztreeObj.cancelSelectedNode(rootGrid);
			}

			if (!includeRightSelectedGrid) {
				var rightSelectedGrid = GridTreeObj.ztreeObj.getNodeByParam("id", rightSelectedGridId);
				GridTreeObj.ztreeObj.selectNode(rightSelectedGrid, true);
			}

			GridListObj.doDelete(idsArray);
		}
	}, {
		text : "关联小区",
		enableIf : function() {
			return GridTreeObj.isRightClickManageGrid;
		},
		action : function(e) {
			ContextObj.showBindPatchWindow(GridTreeObj.rightSelectedGrid);
		}
	}, {
		text : "关联网格经理",
		enableIf : function() {
			return true;
		},
		action : function(e) {
			ContextObj.showBindManagerWindow(GridTreeObj.rightSelectedGrid);
		}
	}, {
		text : "关联地址",
		enableIf : function() {
			return GridTreeObj.rightSelectedGrid.gtype == 2;
		},
		action : function(e) {
			ContextObj.showBindAddressWindow(GridTreeObj.rightSelectedGrid);
		}
	} ],
	showBindPatchWindow : function(gridInfo, dialogObj) {
		Biz.showPatchMultipleSelect({
			tabName : "操作网格：" + gridInfo.name
		}, {
			listUrl : WEB_ROOT + "/market/res-patch!findUnbindPatchByGridId",
			gridid : gridInfo.id
		}, function() {
			var patchGrid = this;
			var url = GridListObj.rootUrl + "bindPatch";
			var params = {
				ids : patchGrid.getAtLeastOneSelectedItem().join(","),
				gridid : gridInfo.id
			};
			$("body").ajaxJsonUrl(url, function(result) {
				if (result.type == "success") {
					Global.notify("success", result.message);
					var patchGridList = result.userdata; // 后台返回list，则userdata为数组对象
					GridTreeObj.addNodes(patchGridList);
					if (DataGridTreeObj.isInited) {
						DataGridTreeObj.addNodes(patchGridList);
					}
					GridListObj.refresh();
				}
			}, params);
		}, dialogObj);
	},
	showBindManagerWindow : function(gridInfo, dialogObj) {
		if (!gridInfo.name) {
			// 从编辑tab中的批量关联网格经理按钮调用时，没有name信息，需要先查树
			var node = GridTreeObj.ztreeObj.getNodeByParam("id", gridInfo.id);
			gridInfo.name = node.name;
		}
		
		Biz.showOperatorMultipleSelect({
			tabName : "操作网格：" + gridInfo.name
		}, {
			listUrl : WEB_ROOT + "/prv/prv-operator!findUnbindManagerByGridId",
			gridid : gridInfo.id
		}, function() {
			var managerGrid = this;
			var url = GridListObj.rootUrl + "bindManager";
			var params = {
				ids : managerGrid.getAtLeastOneSelectedItem().join(","),
				gridid : gridInfo.id
			};
			$("body").ajaxJsonUrl(url, function(result) {
				if (result.type == "success") {
					Global.notify("success", result.message);
					if (typeof (GridManagerListObj) != "undefined") {
						GridManagerListObj.refresh();
					}
					GridListObj.refresh();
				}
			}, params);
		}, dialogObj);
	},
	showBindAddressWindow : function(gridInfo, dialogObj) {
		if (!gridInfo.name) {
			// 从编辑tab中的批量关联地址按钮调用时，没有name信息，需要先查树
			var node = GridTreeObj.ztreeObj.getNodeByParam("id", gridInfo.id);
			gridInfo.name = node.name;
		}
		
		Biz.showAddressMultipleSelect({
			tabName : "操作网格：" + gridInfo.name
		}, {
			listUrl : WEB_ROOT + "/market/grid-info!findUnbindAddressByGridId",
			gridid : gridInfo.id
		}, function() {
			var addressGrid = this;
			var url = GridListObj.rootUrl + "bindAddress";
			var params = {
				ids : addressGrid.getAtLeastOneSelectedItem().join(","),
				gridid : gridInfo.id
			};
			$("body").ajaxJsonUrl(url, function(result) {
				if (result.type == "success") {
					Global.notify("success", result.message);
					if (typeof (GridAddressListObj) != "undefined") {
						GridAddressListObj.refresh();
					}
				}
			}, params);
		}, dialogObj);
	}
};

var DataGridTreeObj = {
	url : WEB_ROOT + "/market/grid-info!gridTreeForDataGrid",
	ztreeId : "dataGridTree",
	selectedSameLevelNodes : true,
	selectedGrid : {},
	setting : {
		key : {
			name : "name"
		},
		data : {
			simpleData : {
				enable : true,
				rootPId : -1,
				idKey : "id",
				pIdKey : "statid" /* 数据网格用statid */
			}
		},
		view : {
			dblClickExpand : false
		},
		edit : {
			drag : {
				isCopy : false,
				prev : false,
				next : false
			},
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				var $this = DataGridTreeObj;
				$this.selectedGrid = {
					id : treeNode.id
				};

				var childNodes = $this.ztreeObj.getNodesByParam("statid", treeNode.id);
				if (childNodes.length > 0) {
					$this.ztreeObj.expandNode(treeNode, true);
					GridListObj.reload($this.getPidKeyName(), treeNode.id);
				}
			},
			beforeDrop : function(treeId, treeNodes, targetNode, moveType, isCopy) {
				if (!targetNode) {
					Global.notify("error", "不能设置为根数据网格！");
					return false;
				}
				if (targetNode.gtype == 1) {
					Global.notify("error", "片区网格不能添加子数据网格！");
					return false;
				}
                
                if(targetNode.city != treeNodes[0].city ){
					Global.notify("error", "网格不允许跨地市移动！");
					return false;
				}
				var $this = DataGridTreeObj;
				var confirmMsg = "确认移动所选数据网格到【" + targetNode.name + "】吗？";
				if (!$this.selectedSameLevelNodes) {
					confirmMsg = "所选数据网格不是同级节点，只能移动与网格【" + treeNodes[0].name + "】同级的节点<br/>" + confirmMsg;
				}
				bootbox.confirm(confirmMsg, function(g) {
					if (g) {
						var idsArray = [];
						for (var i = 0, size = treeNodes.length; i < size; i++) {
							idsArray.push(treeNodes[i].id);
						}
						
						var url = WEB_ROOT + "/market/grid-info!updatePreDataGrid";
						var params = {
							newStatid : targetNode.id,
							ids : idsArray.join(",")
						};
						$("body").ajaxJsonUrl(url, function(result) {
							if (result.type == "success") {
		    					Global.notify("success", result.message);
		    					$this.updateRefresh(targetNode, treeNodes);
		    				}
		    			}, params);
						return true;
					}
				});
				return false; // 直接返回false禁止拖动，在确认的回调中更新节点，实现拖动效果，否则不能先确认能否拖动
			},
			beforeDrag : function(treeId, treeNodes) {
				// 开始拖动前先判断所选数据网格是否同级节点
				var $this = DataGridTreeObj;
				var selectedNodes = $this.ztreeObj.getSelectedNodes();
				var selectedStatid = -1;
				var index = 0;
				var size = selectedNodes.length;
				for (; index < size; index++) {
					if (index == 0) {
						selectedStatid = selectedNodes[index].statid
					} else {
						if (selectedStatid != selectedNodes[index].statid) {
							$this.selectedSameLevelNodes = false;
							break;
						}
					}
				}
				if (index == size) {
					$this.selectedSameLevelNodes = true;
				}
				return true;
			}
		}
	},
	afterInit : function() {
		this.scanAndUpdateIcon();
	},
	scanAndUpdateIcon : function() {
		// 修改有子节点的patchGrid节点图标为manageGrid，没有子节点的managGrid和addressGrid节点图标为patchGrid
		this.changeIconSkin(this.gridType.manage, this.gridType.patch, false);
		this.changeIconSkin(this.gridType.patch, this.gridType.manage, true);
		this.changeIconSkin(this.gridType.address, this.gridType.patch, false);
	},
	changeIconSkin : function(oriSkin, destSkin, isHasChildNode) {
		var ztreeObj = this.ztreeObj;
		var gridNodes = ztreeObj.getNodesByParam("iconSkin", oriSkin);
		for (var i = 0, size = gridNodes.length; i < size; i++) {
			var gridNode = gridNodes[i];
			var childNodes = ztreeObj.getNodesByParam("statid", gridNode.id, gridNode);
			var canChange = false;
			if (isHasChildNode) {
				if (childNodes.length > 0) {
					canChange = true;
				}
			} else {
				if (childNodes.length == 0) {
					canChange = true;
				}
			}
			if (canChange) {
				gridNode.iconSkin = destSkin;
				ztreeObj.updateNode(gridNode, false);
			}
		}
	},
	updateRefresh : function(newParentNode, nodesToUpdate) {
		var ztreeObj = this.ztreeObj;

		// 更新新所属数据网格的图标
		if (newParentNode.gtype == 0) {
			newParentNode.iconSkin = this.gridType.manage;
		} else {
			newParentNode.iconSkin = this.gridType.address;
		}
		ztreeObj.updateNode(newParentNode, false);

		// 更新所选网格的所属数据网格，并移动到新所属网格
		var oldStatid = nodesToUpdate[0].statid;
		var newStatid = newParentNode.id;
		for (var i = 0, size = nodesToUpdate.length; i < size; i++) {
			var nodeToUpdate = nodesToUpdate[i];
			nodeToUpdate.statid = newStatid;
			ztreeObj.updateNode(nodeToUpdate, false);
			ztreeObj.moveNode(newParentNode, nodeToUpdate, "inner", false);
		}

		// 更新旧所属数据网格的图标
		var oldChildNodes = ztreeObj.getNodesByParam("statid", oldStatid);
		if (oldChildNodes.length == 0) {
			var oldParentNode = ztreeObj.getNodeByParam("id", oldStatid);
			oldParentNode.iconSkin = this.gridType.patch;
			ztreeObj.updateNode(oldParentNode, false);
		}
	}
};
DataGridTreeObj = $.extend({}, CommonTreeObj, DataGridTreeObj);

$(function() {
	GridTreeObj.init();
	ContextObj.init();
	
	var type4ObjInfo = [ {
		treeObj : GridTreeObj
	}, {
		treeObj : DataGridTreeObj
	} ];
	
	$("#treeType").bind("change", function(e) {
		var treeType = $(this).val();
		if (treeType == "") {
			// 取消选中即选回默认的基础网格
			treeType = "0";
			$(this).select2("val", 0);
		}

		// 先全部隐藏，再根据选中的类型显示树并刷新表格
		var treeTypeInt = parseInt(treeType);
		$("#treeDiv").find(".ztree").hide();
		var objInfo = type4ObjInfo[treeTypeInt];
		var treeObj = objInfo.treeObj;
		$("#" + treeObj.ztreeId).show();
		if (!treeObj.isInited) {
			treeObj.init();
		}
		
		if (treeTypeInt == 0) {
			$("#gridRight").find(".maskDiv").hide();
			GridListObj.reload(treeObj.getPidKeyName(), treeObj.selectedManageGrid.id);
		} else if (treeTypeInt == 1) {
			$("#gridRight").find(".maskDiv").show();
			var selectedId = treeObj.selectedGrid.id;
			if (typeof (selectedId) == "undefined") {
				selectedId = "";
			}
			GridListObj.reload(treeObj.getPidKeyName(), selectedId);
		} else {
		}

		ContextObj.hide(); //切换网格类型时隐藏右键菜单
	});
});