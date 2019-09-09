var SurverListGrid = {
	init : function() {
		$(".grid-survey-list-index").data("gridOptions", {
			url : WEB_ROOT + '/survey/biz-survey-list!findByPage',
			colModel : [ {
				label : '流水号',
				name : 'id',
				hidden : true
			}, {
				label : '问卷名称',
				name : 'sname',
				align : 'left'
			}, {
				label : '是否实名制',
				name : 'isreal',
				width : 50,
				stype : 'select',
				editoptions : {
					value : Biz.getCacheParamDatas("REAL_NAME_STATUS")
				}
			}, {
				label : '适用地市',
				name : 'citynames',
				width : 50
			}, {
				label : '适用业务区',
				name : 'areanames',
				width : 100
			}, {
				label : '操作员ID',
				name : 'operator',
				hidden : true
			}, {
				label : '状态',
				name : 'status',
				width : 50,
				stype : 'select',
				editoptions : {
					value : Biz.getCacheParamDatas("SURVEY_STATUS")
				}
			}, {
				label : '录入时间',
				name : 'intime',
				width : 100,
				formatter : 'date'
			}, {
				label : '录入人员',
				name : 'opername',
				width : 100
			}, {
				label : '操作',
				name : 'self_opt',
				width : 80
			} ],
			filterToolbar : false,
			delurl : WEB_ROOT + '/survey/biz-survey-list!doDelete',
			fullediturl : WEB_ROOT + '/survey/biz-survey-list!inputTabs',
			gridComplete : function() {
				Biz.addCustomBtnToGrid(".grid-survey-list-index", [ {
					subBtns : [ {
						btnName : "查看明细",
						btnAction : "SurverListGrid.showQuestion"
					}, {
						btnName : "截止",
						canAddBtn : function(grid, rowData) {
							return rowData.status == 0;
						},
						btnAction : "SurverListGrid.stopSurvey"
					} ]
				} ]);
			}
		});
	},
	showQuestion : function(sid) {
		$(this).popupDialog({
            url :  WEB_ROOT + '/survey/biz-survey-list!showQuestion?sid='+sid,
            title : '题目明细',
            id : "showQuestion",
            callback : function(rowdata) {
            }
        });
	},
	stopSurvey : function(sid) {
		var url = WEB_ROOT + '/survey/biz-survey-list!stopSurveyBySid';
		$("body").ajaxJsonUrl(url, function(result) {
			if (result.type == "success") {
				Global.notify("success", result.message);
				Biz.refreshGrid(".grid-survey-list-index");
			}
		}, {
			sid : sid
		});
	}
};

$(function() {
	var $form = $(".form-biz-survey-list-index");
	var $city = $form.find("select[name='bizSurveyListParamBo.cityid']");
	var area = $form.find("select[name='bizSurveyListParamBo.areaids']");
	$city.change(function() {
		var tmpVal = $city.val();
		if (!tmpVal) {
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

	SurverListGrid.init();
});