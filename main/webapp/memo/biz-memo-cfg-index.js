var memoCfgGrid = {
	init : function() {
		$(".grid-memo-cfg-index").data("gridOptions", {
			url : WEB_ROOT + '/memo/biz-memo-cfg!findByPage',
			colModel : [ {
				label : '主键',
				name : 'id',
				hidden : true
			}, {
				label : '适用地市',
				name : 'citynames',
				width : 50
			}, {
				label : '适用业务区',
				name : 'areanames',
				width : 100
			}, {
				label : '适用业务操作',
				name : 'opnames',
				width : 100
			}, {
				label : '备注内容',
				name : 'memotxt',
				width : 100
			}, {
				label : '优先级',
				name : 'pri',
				width : 30
			}, {
				label : '录入时间',
				name : 'intime',
				width : 100,
				formatter : 'date'
			}, {
				label : '录入人员',
				name : 'opername',
				width : 100
			}],
			filterToolbar : false,
			delurl : WEB_ROOT + '/memo/biz-memo-cfg!doDelete',
			fullediturl : WEB_ROOT + '/memo/biz-memo-cfg!inputTabs',

		});
	},

};

$(function() {

	var opcodes = $("#opcodes");
	var bizOpcode = "BIZ_OPCODE"
	Biz.initSelectRession(bizOpcode, opcodes);

	memoCfgGrid.init();
});