var DeptListObj = {
	gridClass : ".grid-prv-department-index",
	rootUrl : WEB_ROOT + "/prv/prv-department!",
	init : function() {
		$(this.gridClass).data("gridOptions", {
			url : this.rootUrl + 'findByPage',
			colModel : [ {
				label : '部门名称',
				name : 'name',
				width : 120
			}, {
				label : '所属业务区',
				name : 'extraAttributes.areaname',
				width : 100,
				sortable : false
			}, {
				label : '上级部门',
				name : 'preDeptname',
				width : 120,
				sortable : false
			}, {
				label : '部门级别',
				name : 'deplevel',
				width : 70,
				stype : 'select',
				editoptions : {
					value : Biz.getPrvParamListDatas("PRV_DEPTLEVEL")
				}
			}, {
				label : '部门类型',
				name : 'kind',
				width : 100,
				stype : 'select',
				editoptions : {
					value : Biz.getPrvParamListDatas("PRV_DEPT_TYPE")
				}
			}, {
				label : '排序',
				name : 'sortby',
				width : 40
			} ],
			filterToolbar : false
		});
	},
	reload : function(preid) {
		$(this.gridClass).jqGrid("setGridParam", {
			postData : {
				"preid" : preid
			},
			page : 1
		}).trigger("reloadGrid");
	}
};

$(function() {
	DeptListObj.init();
});