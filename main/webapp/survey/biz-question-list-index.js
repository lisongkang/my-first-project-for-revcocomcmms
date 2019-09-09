var BizQuestionListIndex = {
		gridClass:".grid-biz-survey-biz-question-list-index",
		init : function (){
			this.gridObj.init();
		},
		gridObj:{
			init :function (){
				 $(".grid-biz-survey-biz-question-list-index").data("gridOptions", {
				        url : WEB_ROOT + '/survey/biz-question-list!findByPage',
				        colModel : [ {
				            label : '题目ID',
				            name : 'id',
				            width : 20
				            //hidden : true                          
				        }, {
				            label : '题目名称',
				            name : 'qcontent',
				            width : 80,
				            editable: true,
				            align : 'left'
				        },{
				            label : '是否开放式',
				            name : 'isopen',
				            width : 40,
				            editable: true,
				            stype : 'select',
				            align : 'left',
				            editoptions : {
								  value :Biz.getCacheParamDatas("SYS_YES_NO")
							}
				        }, {
				            label : '是否图片',
				            name : 'isok',
				            width : 40,
				            editable: true,
				            stype : 'select',
				            align : 'left',
				            editoptions :{
				            	 value :Biz.getCacheParamDatas("SYS_YES_NO")
				            }
				        }, {
				            label : '是否单选',
				            name : 'isonly',
				            width : 40,
				            editable: true,
				            stype : 'select',
				            align : 'left',
				            editoptions :{
				            	 value :Biz.getCacheParamDatas("SYS_YES_NO")
				            }
				        }, {
				            label : '答案',
				            name : 'self_opt',
				            width : 40,
				            align : 'left'
				        }, {
				            label : '录入时间',
				            name : 'intime',
				            width : 80,
				            formatter: 'date',
				            editable: true,
				            align : 'center'
				        }, {
				            label : '录入人员ID',
				            name : 'operator',
				            width : 60,
				            editable: true,
				            align : 'right',
				            hidden:true
				        }, {
				            label : '录入人员',
				            name : 'opername',
				            width : 60,
				            editable: true,
				            align : 'right'
				        }, {
				            label : '备注',
				            name : 'memo',
				            width : 200,
				            editable: true,
				            align : 'left',
				            hidden : true
				        } ],
				        delurl : WEB_ROOT + '/survey/biz-question-list!doDelete',
				        fullediturl : WEB_ROOT + '/survey/biz-question-list!inputTabs',
				        filterToolbar : false,
				        ondblClickRow:function(){},
				        
				        gridComplete:function(){
				        	//给答案列名添加查看按钮，根据【是否开放】来添加
				        	var ids = $(".grid-biz-survey-biz-question-list-index").jqGrid('getDataIDs');
				        	for(var i = 0 ; i < ids.length ; i++)
				        	{
				        	     var id = ids[i];
				        	     var rowData = $(".grid-biz-survey-biz-question-list-index").getRowData(id);
				        	     var isopen = rowData.isopen;
				        	     var title = rowData.qcontent;
				        	     if(isopen=='N'){
				        	         var optBtn = "";
							         optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='BizQuestionListIndex.showAnswer(\""+id+"\",\""+title+"\")' >查看</a>";
							         $(".grid-biz-survey-biz-question-list-index").jqGrid('setRowData', ids[i], { 'self_opt': optBtn });   
				        	     }
				        	}
				        }
				 });
			}
		},
		
		refresh : function() {
			Biz.refreshGrid(this.gridClass); // 表单提交后直接刷新表格
		},
		showAnswer :function(qid,atitle){
			
			$(this).popupDialog({
	            url :  WEB_ROOT + '/survey/biz-qa-relation!findAnSwerByQid?qid='+qid,
	            title :"题目:"+atitle,
	            id : "showQueAnswer"+qid,
	            callback : function(rowdata) {
	            }
	        });
		}
} 
$(function() {
	BizQuestionListIndex.init();
});
