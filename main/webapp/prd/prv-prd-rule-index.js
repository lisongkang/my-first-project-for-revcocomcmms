$(function() {
	$(".grid-prv-prd-rule-index").data("gridOptions", {
		url : WEB_ROOT + '/prd/prv-prd-rule!findByPage',
		colModel : [ {
			label : '流水号',
			name : 'id',
			hidden : true

		}, {
			label : '操作员',
			name : 'opername',
			width : 60,
			align : 'left'
		}, {
			label : '业务区',
			name : 'areaid',
			width : 60,
			align : 'left',
			formatter : function(cellvalue, options, rowObject){
				var area = Biz.getAreaObj(cellvalue);
				return area && area.name ? area.name : "";
			}
		}, {
			label : '推荐对象类型',
			name : 'objtype',
			width : 60,
			align : 'left',
			stype : 'select',
			editoptions : {
				value : {'0':'商品','1':'促销优惠'}
			}
		}, {
			label : '推荐对象值',
			name : 'prdname',
			width : 100,
			align : 'left'
		}, {
			label : '跳转菜单',
			name : 'menuname',
			width : 60,
			align : 'left'
		}, {
			label : '规则失效时间',
			name : 'exptime',
			width : 80,
			align : 'left'
		}, {
			label : '提示语',
			name : 'message',
			width : 120,
			align : 'right'
		}, {
			label : '创建时间',
			name : 'createtime',
			width : 60,
			formatter : 'date',
			align : 'center',
			hidden : true
		} ],
		inlineNav : {
			add : false
		},
		ondblClickRow : function(rowid,iRow,iCol,e){
			 var data = $(this).jqGrid("getRowData", rowid);
             var url = WEB_ROOT + '/prd/prv-prd-rule!inputTabs';
             url = Util.AddOrReplaceUrlParameter(url, "id", rowid);
             url = Util.AddOrReplaceUrlParameter(url, "prdname", data.prdname);
             url = Util.AddOrReplaceUrlParameter(url, "menuname", data.menuname);
             var nav = $(this).closest(".tabbable").find(" > .nav");
			 console.log(url);
             Global.addOrActiveTab(nav, {
                 title: "编辑: " + rowid,
                 url: url
             })
		},
		filterToolbar : false,
		editurl : WEB_ROOT + '/prd/prv-prd-rule!doSave',
		delurl : WEB_ROOT + '/prd/prv-prd-rule!doDelete',
		fullediturl : WEB_ROOT + '/prd/prv-prd-rule!inputTabs'
	});

});
