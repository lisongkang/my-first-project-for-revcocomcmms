var SurveyQuestionMultiple = {
	init : function() {
		this.origGridObj.init();
		this.destGridObj.init();
	},
	origGridObj : {
		grid : $(".grid-survey-quesiont-multiple"),
		init : function() {
			this.grid.data("gridOptions", {
				url : WEB_ROOT + '/survey/biz-question-list!findByPage',
				colModel : [ {
					label : '题目ID',
					name : 'id',
					width : 100
				}, {
					label : '题目名称',
					name : 'qcontent'
				}, {
					label : '是否开放式',
					name : 'isopen',
					width : 100,
					stype : 'select',
					editoptions : {
						value : Biz.getCacheParamDatas("SYS_YES_NO")
					}
				}, {
					label : '是否图片',
					name : 'isok',
					width : 100,
					stype : 'select',
					editoptions : {
						value : Biz.getCacheParamDatas("SYS_YES_NO")
					}
				}, {
					label : '是否单选',
					name : 'isonly',
					width : 100,
					stype : 'select',
					editoptions : {
						value : Biz.getCacheParamDatas("SYS_YES_NO")
					}
				}, {
					label : '答案',
					name : 'self_opt',
					width : 100
				}, {
					label : '录入时间',
					name : 'intime',
					width : 150,
					formatter : 'date'
				}, {
					label : '录入人员',
					name : 'opername',
					width : 150
				} ],
				rowNum : 10,
				toppager : false,
				filterToolbar : false,
				height : 200,
				gridComplete : function() {
					SurveyQuestionMultiple.addCustomBtnToGrid(SurveyQuestionMultiple.origGridObj.grid);
				}
			});

			this.bindAddQuestionAction();
		},
		bindAddQuestionAction : function() {
			$("#multipleQuestionAddBtn").unbind("click").bind("click", this.addQuestionBtnAction);
		},
		addQuestionBtnAction : function(e) {
			var origGrid = SurveyQuestionMultiple.origGridObj.grid;
			var ids = Biz.isGridRightlyMultipleSelected(origGrid, singleSelect);
			if (ids) {
				var destGrid = SurveyQuestionMultiple.destGridObj.grid;
				for (var i = 0, size = ids.length; i < size; i++) {
					var id = ids[i];
					var origRowData = origGrid.jqGrid("getRowData", id);
					var destRowData = destGrid.jqGrid("getRowData", id);
					if (destRowData.id) {
						// 先删除已添加的题目，实现越后选择的题目序号越大
						destGrid.jqGrid("delRowData", id);
					}
					destGrid.jqGrid("addRowData", id, origRowData);
					destGrid.jqGrid("setRowData", id, {id : id}); // 要另外设置一次id值，否则会带有<span>标签
					SurveyQuestionMultiple.addCustomBtnToGrid(destGrid); // 要重新设置自定义按钮，否则事件失效
				}
			}
		}
	},
	destGridObj : {
		grid : $(".grid-survey-quesiont-dest"),
		init : function() {
			this.grid.data("gridOptions", {
				url : WEB_ROOT + '/survey/biz-question-list!findEmptyResult',
				colModel : [ {
					label : '题目编号',
					name : 'id',
					width : 100,
					sortable : false
				}, {
					label : '题目名称',
					name : 'qcontent',
					sortable : false
				}, {
					label : '是否开放式',
					name : 'isopen',
					width : 100,
					stype : 'select',
					editoptions : {
						value : Biz.getCacheParamDatas("SYS_YES_NO")
					},
					sortable : false
				}, {
					label : '是否图片',
					name : 'isok',
					width : 100,
					stype : 'select',
					editoptions : {
						value : Biz.getCacheParamDatas("SYS_YES_NO")
					},
					sortable : false
				}, {
					label : '是否单选',
					name : 'isonly',
					width : 100,
					stype : 'select',
					editoptions : {
						value : Biz.getCacheParamDatas("SYS_YES_NO")
					},
					sortable : false
				}, {
					label : '答案',
					name : 'self_opt',
					width : 100,
					sortable : false
				}, {
					label : '录入时间',
					name : 'intime',
					width : 150,
					formatter : 'date',
					sortable : false
				}, {
					label : '录入人员',
					name : 'opername',
					width : 150
				} ],
				rowNum : 100,
				toppager : false,
				scroll : true, // 当为true时，翻页栏被禁用，使用垂直滚动条加载数据
				filterToolbar : false,
				height : 200
			});
			
			this.bindDelQuestionAction();
			this.bindSubmitAction();
		},
		bindDelQuestionAction : function() {
			$("#multipleQuestionDeleteBtn").unbind("click").bind("click", this.delQuestionBtnAction);
		},
		delQuestionBtnAction : function(e) {
			var destGrid = SurveyQuestionMultiple.destGridObj.grid;
			var ids = Biz.isGridRightlyMultipleSelected(destGrid, singleSelect);
			if (ids) {
				for (var i = 0, size = ids.length; i < size; i++) {
					destGrid.jqGrid("delRowData", ids[i]);
				}
			}
		},
		bindSubmitAction : function() {
			$("#submitSurveyBtnUp").unbind("click").bind("click", this.submitBtnAction);
			$("#submitSurveyBtnDown").unbind("click").bind("click", this.submitBtnAction);
		},
		submitBtnAction : function(e) {
			var destGrid = SurveyQuestionMultiple.destGridObj.grid;
			if (destGrid.jqGrid("getDataIDs").length > 0) {
				var $dialog = $(this).closest(".modal");
				$dialog.modal("hide");
				var callback = $dialog.data("callback");
				if (callback) {
					callback.call(destGrid);
				}
			} else {
				Global.notify("error", "还没有添加题目！");
			}
		}
	},
	addCustomBtnToGrid : function(grid) {
		Biz.addCustomBtnToGrid(grid, [ {
			btnColName : "self_opt",
			subBtns : [ {
				btnName : "查看",
				canAddBtn : function(thisGrid, rowData) {
					return rowData.isopen == 'N';
				},
				btnAction : "SurveyQuestionMultiple.showAnswer"
			} ]
		} ]);
	},
	showAnswer : function(qid) {
		$(this).popupDialog({
			url : WEB_ROOT + '/survey/biz-qa-relation!findAnSwerByQid?qid=' + qid,
			title : "题目编号" + qid + "答案",
			id : "showQueAnswerWhenAddSurvey"+qid,//添加qid是为了让title可以改变
			callback : function(rowdata) {
			}
		});
	}
};

$(function() {
	SurveyQuestionMultiple.init();
});