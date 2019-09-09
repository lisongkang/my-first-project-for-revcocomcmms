$(function() {
	$(".grid-market-grid-patch").data("gridOptions", {
		url : WEB_ROOT + "/market/grid!queryPatch?id=" + gridId,
		colModel : [ {
            label : '小区名称',
            name : 'extraAttributes.patchname',
            editable : true,
            editoptions : Biz.getPatchOptions(),
            width : 350,
            editrules : {
                required : true
            },
            align : 'center'
        }, {
			label : '类型',
            name : 'objtype',
            editable: true,
            editrules : {
                required : true
            },
            stype : 'select',
            width : 120,
            editoptions : {
            	value : {'0':'片区型','1':'网格型','2':'地址型'},
            	defaultValue : "0"
            }
        }, {
			label : '所属业务区',
            name : 'extraAttributes.areaname',
			editable : true,
            width : 200,
            align : 'center'
        }, {
        	name : 'recid',
        	hidden : true,
            hidedlg : true,
            editable : true
        }, {
        	name : 'gridid',
        	hidden : true,
            hidedlg : true,
            editoptions : {
    			value : gridId
            },
            editable : true
        }, {
        	name : 'objid',
        	hidden : true,
            hidedlg : true,
            editable : true
        }, {
        	name : 'city',
        	hidden : true,
            hidedlg : true,
            editable : true
        } ],
        filterToolbar : false,
        sortname : "id",
        editurl : WEB_ROOT + '/market/grid-obj!doSave',
        delurl : WEB_ROOT + '/market/grid-obj!doDelete'
	});
});