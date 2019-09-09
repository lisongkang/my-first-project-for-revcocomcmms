$(function() {
	$(".grid-market-grid-info-gridaddress-multiple").data("gridOptions", {
		url : trueListUrl,
		colModel : [ {
			label : '住宅状态',
			name : 'status',
			width : 70,
			align : 'center',
			stype : 'select',
			editoptions : {
				value : Biz.getPrvParamListDatas("RES_HOUSE_STATUS", "")
			}
		}, {
			label : '所属片区',
			name : 'patchName',
			width : 80,
			align : 'center',
			sortable : false
		}, {
			label : '所属业务区',
			name : 'areaName',
			width : 70,
			align : 'center',
			sortable : false
		}, {
			label : '网络结构',
			name : 'netstruct',
			width : 70,
			align : 'right',
			stype : 'select',
			editoptions : {
				value : Biz.getPrvParamListDatas("RES_NET_TYPE", "")
			}
		}, {
			label : '完整住宅地址',
			name : 'whladdr',
			align : 'center'
		} ],
		rowNum : 100,
		toppager : false,
		filterToolbar : false,
		height : 380
	});

	$("#multipleAddressSureBtn").unbind("click").bind("click", function(e) {
		var addressGrid = $(".grid-market-grid-info-gridaddress-multiple");
		var id = addressGrid.jqGrid("getGridParam", "selrow"); // 获取所选行id
		if (id) {
			if (singleSelect == 1 && addressGrid.getAtLeastOneSelectedItem().length > 1) {
				Global.notify("error", "请只选择一条行项目！");
				return;
			}
			var $dialog = $(this).closest(".modal");
			$dialog.modal("hide");
			var callback = $dialog.data("callback");
			if (callback) {
				callback.call(addressGrid);
			}
		} else {
			Global.notify("error", "请" + (singleSelect == 1 ? "只" : "至少") + "选择一条行项目！");
		}
	});
});