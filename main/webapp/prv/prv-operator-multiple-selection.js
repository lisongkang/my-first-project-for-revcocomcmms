$(function() {
	$(".grid-auth-multiple-user-index").data("gridOptions", {
		url : trueListUrl,
		colModel : [ {
			label : '登录账号',
			name : 'loginname',
			width : 120
		}, {
			label : '名称',
			name : 'name',
			width : 120
		}, {
			label : '状态',
			name : 'status',
			width : 65,
			stype : 'select',
			editoptions : {
				value : Biz.getPrvParamListDatas("PRV_USE_STATUS")
			},
			align : 'left'
		}, {
			label : '启用时间',
			name : 'stime',
			width : 150,
			formatter : 'date',
			align : 'center'
		}, {
			label : '结束时间',
			name : 'etime',
			width : 150,
			formatter : 'date',
			align : 'center'
		} ],
		rowNum : 10,
		toppager : false,
		filterToolbar : false,
		height : 380
	});

	$("#multipleOperSureBtn").unbind("click").bind("click", function(e) {
		var operGrid = $(".grid-auth-multiple-user-index");
		var id = operGrid.jqGrid("getGridParam", "selrow"); // 获取所选行id
		if (id) {
			if (singleSelect == 1 && operGrid.getAtLeastOneSelectedItem().length > 1) {
				Global.notify("error", "请只选择一条行项目！");
				return;
			}
			var $dialog = $(this).closest(".modal");
			$dialog.modal("hide");
			var callback = $dialog.data("callback");
			if (callback) {
				callback.call(operGrid);
			}
		} else {
			Global.notify("error", "请" + (singleSelect == 1 ? "只" : "至少") + "选择一条行项目！");
		}
	});
});