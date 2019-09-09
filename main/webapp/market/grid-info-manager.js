var GridManagerListObj = {
		gridClass : ".grid-market-grid-info-manager",
		rootUrl : WEB_ROOT + "/market/grid-info-manager!",
		init:function(){
			$(this.gridClass).data('gridOptions', {
				url : this.rootUrl + 'queryManagerByGridid?gridid=' + gridId,
				colModel : [ {
		            label : '网格经理',
		            name : 'extraAttributes.showName',
		            width : 100,
		            align : 'left'
		        }, {
					label : '登录名',
		            name : 'extraAttributes.name',
		            editable : true,
		            editoptions : Biz.getOperatorOptions(),
		            width : 100,
		            editrules : {
		                required : true
		            },
		            align : 'left'
				}, {
					label : '主标识',
					name : 'isMain',
					editable : true,
					editrules : {
						required : true
					},
					stype : 'select',
					width : 60,
					editoptions : {
						value : {
							'Y' : 'Y',
							'N' : 'N'
						},
						defaultValue : 'N'
					}
				}, {
					label : '备注',
					name : 'memo',
					editable : true,
					width : 60
				}, {
		        	name : 'gridid',
		        	hidden : true,
		            hidedlg : true,
		            editoptions : {
		    			value : gridId
		            },
		            editable : true
		        }, {
		        	name : 'areamger',
		        	hidden : true,
		            hidedlg : true,
		            editable : true
		        } ],
				filterToolbar : false,
				sortname : 'id',
				editurl : this.rootUrl + 'doSave',
				delurl : this.rootUrl + 'doDelete',
				operations : function(items) {
					var $this = GridManagerListObj;
		    		var $grid = this;
		    		var bindManagerBtn = $this.createCustomBtn(true, "批量关联", "fa-retweet", $this.bindManagerBtnAction($grid));
		    		items.push(bindManagerBtn);
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
		bindManagerBtnAction : function($grid) {
			return function(e) {
				ContextObj.showBindManagerWindow({id : gridId}, $grid);
			};
		}
};

$(function() {
	GridManagerListObj.init();
});