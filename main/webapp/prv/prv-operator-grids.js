$(function() {
	$(".grid-auth-user-grids").data("gridOptions", {
		url : WEB_ROOT + '/market/grid-info!queryGridsByOperid',
		postData : {
			'operid' : operid
		},
		colModel : [ {
			label : '网格编码',
			name : 'gridcode',
			width : 100,
			align : 'left'
		}, {
			label : '网格名称',
			name : 'gridname',
			align : 'left'
		}, {
			label : '网格路径',
			name : 'gridPath',
			width : 200,
			sortable : false
		}, {
			label : '其他负责人',
			name : 'extraAttributes.manager',
			width : 200,
			sortable : false
		}, {
			label : '网格类型',
			name : 'gtype',
			stype : 'select',
			width : 80,
			editoptions : {
				value : {
					0 : '管理网格',
					1 : '小区网格'
				},
				defaultValue : 0
			}
		} ],
		filterToolbar : false,
		multiselect : false
	});
});