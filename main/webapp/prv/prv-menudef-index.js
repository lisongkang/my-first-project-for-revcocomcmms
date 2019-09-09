$(function() {
    $(".grid-biz-prv-prv-menudef-index").data("gridOptions", {
        url : WEB_ROOT + '/prv/prv-menudef!findByPage',
        colModel : [ {
            label : '菜单名称',
            name : 'name',
            width : 150,
            editable: true,
            align : 'left'
        }, {
            label : '排序编号',
            name : 'seqno',
            width : 80,
            editable: true,
            align : 'left'
        }, {
            label : '链接地址',
            name : 'linkurl',
            width : 300,
            editable: true,
            align : 'left'
        }, {
            label : '业务编码',
            name : 'bizcode',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '所属系统',
            name : 'sysid',
            width : 150,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("SYS_ID")
            },
            align : 'left'
        }, {
            label : '是否启用',
            name : 'attr',
            width : 150,
            editable: true,
            stype : 'select',
            editoptions : {
        	value : {'Y':'启用','N':'禁用'}
            },
            align : 'left'
        }, {
            label : '备注',
            name : 'memo',
            width : 200,
            editable: true,
            align : 'left'
        } ],
        sortname : 'seqno',
        multiselect : false,
        subGrid : true,
        gridDnD : true,
        subGridRowExpanded : function(subgrid_id, row_id) {
            Grid.initRecursiveSubGrid(subgrid_id, row_id, "premenuid");
        },
        editurl : WEB_ROOT + '/prv/prv-menudef!doSave',
        delurl : WEB_ROOT + '/prv/prv-menudef!doDelete',
        inlineNav : {
            add : true
        }
    });
});
