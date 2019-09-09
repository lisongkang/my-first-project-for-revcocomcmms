$(function() {
    $(".grid-common-datatransfer-mapping-config-index").data("gridOptions", {
        url : WEB_ROOT + '/common/datatransfer/mapping-config!findByPage',
        colModel : [ {
            label : '类名',
            name : 'className',
            width : 200,
            editable: true,
            align : 'left'
        }, {
            label : '匹配条件',
            name : 'conditionType',
            editable : true,
            stype : 'select',
            editoptions : {
        		value : Biz.getPrvParamListDatas("MATCH_TYPE")
            },
            width : 120
        }, {
            label : '属性名称',
            name : 'fieldName',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '属性值',
            name : 'fieldValue',
            width : 200,
            editable: true,
            align : 'left'
        }, {
        	label : '状态',
            name : 'status',
            editable : true,
            stype : 'select',
            editoptions : {
            	value : {1:'有效',0:'无效'}
            },
            width : 70
        } ],
        editurl : WEB_ROOT + '/common/datatransfer/mapping-config!doSave',
        delurl : WEB_ROOT + '/common/datatransfer/mapping-config!doDelete',
        fullediturl : WEB_ROOT + '/common/datatransfer/mapping-config!edit'
    });
});
