$(function() {
	$(".grid-market-grid-manager").data("gridOptions", {
		url : WEB_ROOT + "/market/grid!queryManager?id=" + gridId,
		colModel : [ {
            label : '网格经理',
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
            name : 'ismain',
            editable: true,
            editrules : {
                required : true
            },
            stype : 'select',
            width : 60,
            editoptions : {
            	value : {'Y':'Y','N':'N'},
            	defaultValue : "Y"
            }
        }, {
			label : '所属分公司',
            name : 'city',
            editable: true,
            editrules : {
                required : true
            },
            stype : 'select',
            width : 60,
            editoptions : {
            	value : Biz.getPrvParamListDatas("PRV_CITY")
            }
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
        	name : 'areamger',
        	hidden : true,
            hidedlg : true,
            editable : true
        } ],
        filterToolbar : false,
        sortname : "id",
        editurl : WEB_ROOT + '/market/grid-manager!doSave',
        delurl : WEB_ROOT + '/market/grid-manager!doDelete'
	});
});