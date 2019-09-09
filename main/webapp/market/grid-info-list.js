var GridListObj = {
	gridClass : ".grid-biz-market-grid-info-list",
	rootUrl : WEB_ROOT + "/market/grid-info!",
	tabNav : $("#gridManageRight").find(" > .tabbable").find(" > .nav"),
	init : function() {
        var rolelevel = Biz.LOGIN_INFO.rolelevel;
		$(this.gridClass).data("gridOptions", {
			url : this.rootUrl + 'findByPage',
			colModel : [ {
				label : '网格代码',
				name : 'gridcode',
				editable : true,
				width : 100,
				align : 'left'
			}, {
				label : '网格名称',
				name : 'gridname',
				editable : true,
				align : 'left',
				width : 120,
			}, {
				label : '网格路径',
				name : 'gridPath',
				width : 200,
				sortable : false
			}, {
				label : '网格负责人',
				name : 'extraAttributes.manager',
				editable : false,
				editrules : {
					required : true
				},
				width : 200,
				search : false,
				sortable : false
			}, {
				label : '网格类型',
				name : 'gtype',
				editable : true,
				stype : 'select',
				width : 80,
				editoptions : {
					value : {
						0 : '管理网格',
						1 : '小区网格',
						2 : '地址网格'
					},
					defaultValue : 0
				}
			}, {
				label : '备注',
				name : 'memo',
				editable : true,
				hidden : true
			} ],
			filterToolbar : false,
			fullediturl : (rolelevel != "0") ? this.rootUrl + 'inputTabs':"",
			operations : function(items) {
				var $this = GridListObj;
	    		var $grid = this;
	    		console.log(rolelevel);
                if(rolelevel != "0"){
                    var deleteBtn = $this.createCustomBtn(false, "删除数据", "fa-trash-o", $this.delBtnAction($grid));
                    items.push(deleteBtn);

                    var bindPatchBtn = $this.createCustomBtn(true, "关联小区", "fa-retweet", $this.bindPatchBtnAction($grid));
                    items.push(bindPatchBtn);
                }
	    	}
		});
	},
	refresh : function() {
		Biz.refreshGrid(this.gridClass); // 表单提交后直接刷新表格
	},
	reload : function(pidName, pidValue) {
		$("#gridManageRight").find(".tab-default").click(); // 点击管理网格则切换回列表tab
		$(this.gridClass).jqGrid("setGridParam", {
			postData : {
				"pidName" : pidName,
				"pidValue" : pidValue
			},
			page : 1
		}).trigger("reloadGrid");

		if (typeof (GridInfoInputBasic) != "undefined") {
			// 表示已生成过一次编辑表单
			GridInfoInputBasic.changePreGridInfoWhenAdd();
		}
	},
	editGrid:function(gridId){
		if (gridId) {
			var editTab = $(this.tabNav).find("a[data-toggle='tab'][data-url*='inputTabs?id=" + gridId + "']");
			if (editTab.length > 0) {
				this.activeEditTab($(editTab).text(), gridId);
			} else {
				// 打开新编辑表单
				this.activeEditTab("编辑: " + gridId + " ", gridId);
			}
		} else {
			var dataUrl = this.rootUrl + "inputTabs";
			this.activeEditTab(" 新增数据  ");
		}
	},
	activeEditTab : function(tabTitle, gridId) {
		var dataUrl = this.rootUrl + "inputTabs" + (gridId ? ("?id=" + gridId) : "");
		Global.addOrActiveTab(this.tabNav, {
			title : tabTitle,
			url : dataUrl
		});
	},
	removeActiveTab : function() {
		// 移除当前编辑tab（代替取消按钮事件，因为会额外弹出提示框）
		var manageRight = $("#gridManageRight");
		var activeTab = $(manageRight).find(".active.tab-closable");
		var tabId = $(activeTab).attr("id");
		$(activeTab).remove(); // 移除当前编辑tab
		$(manageRight).find("a[href='#" + tabId + "']").remove(); // 移除当前编辑tab标题
		var tabs = $(manageRight).find("a[data-toggle='tab'][data-url*='inputTabs']");
		if (tabs.length == 0) {
			// 没有编辑tab则返回到表格tab
			$(manageRight).find(".tab-default").click();
		} else {
			// 有其它编辑tab则选择最后一个编辑tab
			$(tabs[tabs.length - 1]).click();
		}
	},
	removeDeletedEditTab : function(idsArray) {
		// 移除已删记录的编辑tab，不能再操作该tab
		var manageRight = $("#gridManageRight");
		var activeEditId = -1;
		var activeTabId = -1;
		var activeTab = $(manageRight).find(".active.tab-closable");
		if (activeTab.length == 1) {
			// 没有任何编辑表单则activeTab实际为列表tab
			var dataUrl = $(activeTab).attr("data-url");
			activeEditId = dataUrl.substring(dataUrl.indexOf("?id=") + 4, dataUrl.length);
			activeTabId = $(activeTab).attr("id");
		}

		for (var i = 0, size = idsArray.length; i < size; i++) {
			var idToDelete = idsArray[i];
			if (idToDelete == "") {
				// 全部删除失败时传入的nodeIds.length不为0，有一个空字符串元素，直接退出
				break;
			}
			if (activeEditId == idToDelete) {
				// 当前编辑tab的记录已删，则同时移除编辑tab
				$(activeTab).remove();
				$(manageRight).find("a[href='#" + activeTabId + "']").remove();
			} else {
				var editTab = $(this.tabNav).find("a[data-toggle='tab'][data-url*='inputTabs?id=" + idToDelete + "']");
				if (editTab.length > 0) {
					// 已删记录已打开编辑tab
					var href = $(editTab).attr("href");
					var tabContentId = href.substring(1, href.length);
					$(manageRight).find("#" + tabContentId).remove();
					$(editTab).remove();
				}
			}
		}
		$(manageRight).find(".tab-default").click(); //直接返回表格tab
	},
	createCustomBtn : function(isPosition, btnTitle, btnClass, btnAction) {
		var newBtn = $('<li '
				+ (isPosition ? 'data-position="multi"' : '')
				+ 'data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa '
				+ btnClass + '"></i> ' + btnTitle + '</a></li>');

		newBtn.children("a").bind("click", btnAction);
		return newBtn;
	},
	delBtnAction : function($grid) {
		var $this = this;
		return function(e){
			var id = $($this.gridClass).jqGrid("getGridParam", "selrow"); //获取所选行id
			if (id) {
				$this.doDelete($grid.getAtLeastOneSelectedItem());
			} else {
				Global.notify("error", "请至少选择一条行项目！");
			}
		};
	},
	doDelete : function(idsArray) {
		var $this = this;
		var url = $this.rootUrl + "doDelete";
		var params = {
			ids : idsArray.join(",")
		};
		bootbox.confirm("确认批量删除所选记录吗？", function(g){
			if (g) {
				$("body").ajaxJsonUrl(url, function(result) {
    				if (result.type == "success") {
    					var msg = result.message;
    					if (msg.indexOf("success-") > -1) {
    						// 实际上成功
    						Global.notify("success", msg.replace("success-", ""));
    						$this.refresh();
    						GridTreeObj.deleteRefresh(idsArray);
    						if (DataGridTreeObj.isInited) {
    							DataGridTreeObj.deleteRefresh(idsArray);
    						}
    					}else if (msg.indexOf("error-") > -1) {
    						// 实际上失败
    						var errorMsgMap = result.userdata; // 后台返回map，则userdata为json对象
    						for (var gridId in errorMsgMap) {
    							if (gridId == -1) {
    								// 把部分删除成功的id转成数组，用于删除树节点
    								idsArray = errorMsgMap[gridId].split(",");
    							} else {
    								msg += "<br/>" + errorMsgMap[gridId]; //其它错误信息
    							}
    						}
    						
    						Global.notify("error", msg.replace("error-", ""));
    						$this.refresh();
    						GridTreeObj.deleteRefresh(idsArray);
    						if (DataGridTreeObj.isInited) {
    							DataGridTreeObj.deleteRefresh(idsArray);
    						}
    					}
    					$this.removeDeletedEditTab(idsArray);
    				}
    			}, params);
			}
		});
	},
	bindPatchBtnAction : function($grid) {
		return function(e) {
			ContextObj.showBindPatchWindow(GridTreeObj.selectedManageGrid, $grid);
		};
	}
};

$(function() {
	GridListObj.init();
});