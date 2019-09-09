var BizSurveyListAnalysis = {
		toSurveyEchart:function(sid){
			
			if(!sid){
				Global.notify("error", "问卷id不能为空");
				return ;
			}
			
			var rowData = $(".grid-survey-list-analysis-index").getRowData(sid);
			
			var selector = "tbody tr#"+sid;
			var $thistr = $(".grid-survey-list-analysis-index").find(selector);
			
			//好吧，非要先单击再双击才行
			$thistr.trigger("click");
			$thistr.trigger("dblclick");
		}
   
}
$(function() {
	var $form = $(".form-biz-survey-list-index");
	var $city = $form.find("select[name='bizSurveyListParamBo.cityid']");
	var area = $form.find("select[name='bizSurveyListParamBo.areaids']");
	$city.change(function() {
		var tmpVal = $city.val();
    	if(!tmpVal){
    		area.empty();
    		area.select2("val", "");
    		return;
    	}
		
		var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + tmpVal;
		$form.ajaxJsonUrl(url, function(data) {
			var options = {};
			area.empty();

			var option = '';
			$.each(data, function(i, item) {
				option = '<option value="' + item.id + '">' + item.name
						+ '</option>';
				area.append(option);
			})
			area.select2("val", "");
		});
	});

	$(".grid-survey-list-analysis-index").data("gridOptions", {
		url : WEB_ROOT + '/survey/biz-survey-list!findByPage',
		colModel : [  {
			label : '操作',
			name : 'self_opt',
			width : 40,
			editable : true,
			align : 'center'
		},{
			label : '流水号',
			name : 'id',
			hidden : true
		}, {
			label : '问卷名称',
			name : 'sname',
			width : 60,
			editable : true,
			align : 'left'
		}, {
			label : '是否实名制',
			name : 'isreal',
			width : 60,
			editable : true,
			align : 'left',
			stype : 'select',
			editoptions : {
				value : Biz.getCacheParamDatas("REAL_NAME_STATUS")
			}
		}, {
			label : '适用地市',
			name : 'citynames',
			width : 60,
			editable : true,
			align : 'right',
		}, {
			label : '适用业务区',
			name : 'areanames',
			width : 60,
			editable : true,
			align : 'right'

		}, {
			label : '操作员ID',
			name : 'operator',
			width : 60,
			editable : true,
			align : 'right',
			hidden : true
		}, {
			label : '状态',
			name : 'status',
			width : 60,
			editable : true,
			align : 'right',
			stype : 'select',
			editoptions : {
				value : Biz.getCacheParamDatas("SURVEY_STATUS")
			}
		}, {
			label : '录入时间',
			name : 'intime',
			width : 150,
			formatter : 'date',
			editable : true,
			align : 'center'
		}, {
			label : '录入人员',
			name : 'opername',
			width : 60,
			editable : true,
			align : 'center'
		} ],
		filterToolbar : false,
		fullediturl : WEB_ROOT + '/survey/sta-analysis!inputTabs',
		gridComplete:function(){
        	
        	var ids = $(".grid-survey-list-analysis-index").jqGrid('getDataIDs');
        	for(var i = 0 ; i < ids.length ; i++)
        	{
        	     var id = ids[i];
        	     var rowData = $(".grid-survey-list-analysis-index").getRowData(id);
        	     
        	     ////业务区数据库传递过来如果是空，表示数据库对应的业务区为本市所有业务区
        	     var areanames = rowData.areanames;
        	     if(areanames==""||areanames == null ||areanames==undefined){
        	    	 $(".grid-survey-list-analysis-index").jqGrid('setRowData', ids[i], { 'areanames': '本市所有业务区' });   
        	     }
        	     
        	     //给操作添加一个跳转按钮
        	     var optBtn = "";
		         optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='BizSurveyListAnalysis.toSurveyEchart(\""+id+"\")' >统计结果</a>";
		            
	             jQuery(".grid-survey-list-analysis-index").jqGrid('setRowData', ids[i], { self_opt: optBtn });
        	}
        }
	});
});