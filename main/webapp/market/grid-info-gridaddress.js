var GridAddressListObj = {
	gridClass : ".grid-market-grid-info-gridaddress",
	rootUrl : WEB_ROOT + "/market/grid-info!",
	init : function() {
		$(this.gridClass).data("gridOptions", {
			url : this.rootUrl + 'queryAddressByGridid',
			postData : {
				'gridid' : gridid
			},
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
			filterToolbar : false,
			delurl : this.rootUrl + 'doDeleteAddress?gridid=' + gridid,
			operations : function(items) {
				var $this = GridAddressListObj;
	    		var $grid = this;
	    		var bindAddressBtn = $this.createCustomBtn(true, "批量关联", "fa-retweet", $this.bindAddressBtnAction($grid));
	    		items.push(bindAddressBtn);
	    	}
		});
	},
	refresh : function() {
		Biz.refreshGrid(this.gridClass); // 批量关联后直接刷新表格
	},
	createCustomBtn : function(isPosition, btnTitle, btnClass, btnAction) {
		var newBtn = $('<li '
				+ (isPosition ? 'data-position="multi"' : '')
				+ 'data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa '
				+ btnClass + '"></i> ' + btnTitle + '</a></li>');

		newBtn.children("a").bind("click", btnAction);
		return newBtn;
	},
	bindAddressBtnAction : function($grid) {
		return function(e) {
			ContextObj.showBindAddressWindow({id : gridid}, $grid);
		};
	}
};

$(function() {
	GridAddressListObj.init();
});