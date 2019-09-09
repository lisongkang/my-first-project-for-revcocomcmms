$(function() {
	$(".grid-market-res-multiple-patch-index").data("gridOptions", {
		url : trueListUrl,
		colModel : [ {
			label : '小区名称',
			name : 'patchname',
			width : 350
		}, {
			label : '小区代码',
			name : 'defcode',
			width : 350
		}, {
			label : '所属业务区',
			name : 'areaid',
			stype : 'select',
			align : 'left',
			editoptions : {
				value : Biz.getCacheParamDatas("PRV_AREA")
			}
		}, {
			name : 'areaid',
			hidden : true,
			hidedlg : true
		} ],
		rowNum : 10,
		toppager : false,
		filterToolbar : false,
		height : 380
	});

	$("#multiplePatchSureBtn").unbind("click").bind("click", function(e) {
		var patchGrid = $(".grid-market-res-multiple-patch-index");
		var id = patchGrid.jqGrid("getGridParam", "selrow"); // 获取所选行id
		if (id) {
			if (singleSelect == 1 && patchGrid.getAtLeastOneSelectedItem().length > 1) {
				Global.notify("error", "请只选择一条行项目！");
				return;
			}
			var $dialog = $(this).closest(".modal");
			$dialog.modal("hide");
			var callback = $dialog.data("callback");
			if (callback) {
				callback.call(patchGrid);
			}
		} else {
			Global.notify("error", "请" + (singleSelect == 1 ? "只" : "至少") + "选择一条行项目！");
		}
	});
});