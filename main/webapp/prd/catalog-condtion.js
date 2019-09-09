$(function() {
	$(".grid-prd-catalog-condtion").data("gridOptions", {
		url : WEB_ROOT + "/prd/catalog!queryCondtion?id=" + catalogId,
		colModel : [ {
			label : '条件类型',
            name : 'contiontype',
            editable: true,
            editrules : {
                required : true
            },
            stype : 'select',
            width : 160,
            editoptions : {
            	value : {'0':'指定操作员','1':'指定部门','2':'指定业务操作'},
            	defaultValue : "1"
            }
        }, {
            label : '条件值',
            name : 'extraAttributes.condtionvalue',
            editable : true,
            editoptions : Biz.getCatalogCondtionOptions(),
            width : 400,
            editrules : {
                required : true
            },
            align : 'left'
        }, {
        	name : 'contionvalue',
        	hidden : true,
            hidedlg : true,
            editable : true
        }, {
        	name : 'catalogid',
        	hidden : true,
            hidedlg : true,
            editoptions : {
    			value : catalogId
            },
            editable : true
        } ],
        filterToolbar : false,
        sortname : "id",
        editurl : WEB_ROOT + '/prd/catalog-condtion!doSave',
        delurl : WEB_ROOT + '/prd/catalog-condtion!doDelete'
	});
});