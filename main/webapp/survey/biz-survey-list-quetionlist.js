var BizSurveyListQuetionList = {
		grid : $(".grid-biz-survey-biz-question-list-index"),
		init : function(){
			this.grid.data("gridOptions", {
			      url : WEB_ROOT + '/survey/biz-question-list!findQuestionsBySid?sid='+sid,
			        colModel : [ {
			            label : '流水号',
			            name : 'id',
			            hidden : true                          
			        }, {
			            label : '题目序号',
			            name : 'qno',
			            width : 80,
			            align : 'left'
			        },{
			            label : '题目名称',
			            name : 'qcontent',
			            align : 'left'
			        }, {
			            label : '是否开放式',
			            name : 'isopen',
			            width : 80,
			            stype : 'select',
			            align : 'left',
			            editoptions : {
							  value :Biz.getCacheParamDatas("SYS_YES_NO")
						}
			        } , {
			            label : '是否图片',
			            name : 'isok',
			            width : 80,
			            stype : 'select',
			            align : 'left',
			            editoptions :{
			            	 value :Biz.getCacheParamDatas("SYS_YES_NO")
			            }
			        }, {
			            label : '是否单选',
			            name : 'isonly',
			            width : 80,
			            stype : 'select',
			            align : 'left',
			            editoptions:{
			            	value :Biz.getCacheParamDatas("SYS_YES_NO")
			            }
			        }, {
			            label : '答案',
			            name : 'self_opt',
			            width : 60,
			            align : 'left'
			        },{
			            label : '录入时间',
			            name : 'intime',
			            width : 120,
			            formatter: 'date',
			            editable: true,
			            align : 'center'
			        }, {
			            label : '操作员id',
			            name : 'operator',
			            hidden : true,
			            align : 'right'
			        }, {
			            label : '录入人员',
			            name : 'opername',
			            width : 160,
			            align : 'right'
			        }],
			        rowNum : 50,
			        height : 500,
					toppager : false,
					rownumbers:false,
					multiselect: false,
					scroll : true, // 当为true时，翻页栏被禁用，使用垂直滚动条加载数据
					filterToolbar : false,
					gridComplete : function() {
						BizSurveyListQuetionList.addCustomBtnToGrid(BizSurveyListQuetionList.grid);
					}
			    });
			
			
			// 
		},
		addCustomBtnToGrid : function(grid) {
			Biz.addCustomBtnToGrid(grid, [ {
				btnColName : "self_opt",
				subBtns : [ {
					btnName : "查看",
					canAddBtn : function(thisGrid, rowData) {
						return rowData.isopen == 'N';
					},
					btnAction : "BizSurveyListQuetionList.showAnswer"
				} ]
			} ]);
		},
		showAnswer : function(qid) {
			$(this).popupDialog({
				url : WEB_ROOT + '/survey/biz-qa-relation!findAnSwerByQid?qid=' + qid,
				title : "题目答案",
				id : "showAnswerWhenShowQue"+qid,
				callback : function(rowdata) {
				}
			});
		}
}
$(function() {
	BizSurveyListQuetionList.init();
});
