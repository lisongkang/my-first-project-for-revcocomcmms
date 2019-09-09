$(function() {
	$(".grid-market-grid-info-asstogrid").data("gridOptions", {
		url : WEB_ROOT + '/market/grid-info!queryAssByGridid',
		postData : {
			'gridid' : gridid
		},
		colModel : [ {
			label : '考核内容',
			name : 'asscontent',
			width : 150,
			align : 'center'
		}, {
			label : '考核周期',
			name : 'assStoreUnit',
			width : 70,
			align : 'center',
			stype : 'select',
			editoptions : {
				value : Biz.getPrvParamListDatas("BIZ_ASS_UNIT", "")
			}
		}, {
			label : '考核起始日期',
			name : 'bdate',
			width : 100,
			formatter : 'date',
			align : 'center'
		}, {
			label : '接受任务数',
			name : 'revnum',
			width : 70,
			align : 'right'
		}, {
			label : '完成方式',
			name : 'mode',
			width : 60,
			align : 'left',
			stype : 'select',
			editoptions : {
				value : Biz.getPrvParamListDatas("BIZ_ASS_MODE", "")
			}
		}, {
			label : '每期考核数',
			name : 'extraAttributes.assNums',
			width : 100,
			align : 'right',
			sortable : false
		}, {
			label : '下达时间',
			name : 'assdate',
			width : 100,
			formatter : 'date',
			align : 'center'
		}, {
			label : '下达部门',
			name : 'depart',
			width : 60,
			hidden : true,
			align : 'right'
		}, {
			label : '操作员',
			name : 'operator',
			width : 60,
			hidden : true,
			align : 'right'
		} ],
		filterToolbar : false,
		multiselect : false
	});
});
