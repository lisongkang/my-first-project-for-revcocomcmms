$(function() {
    $(".grid-biz-prv-menudef-for-jump").data("gridOptions", {
        url : WEB_ROOT + '/prv/prv-menudef!menuForJump',
        colModel : [ {
            label : '菜单ID',
            name : 'menuid',
            width : 150,
            editable: true,
            hidden : true,
            align : 'left'
        }, {
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
        filterToolbar : false,
        rowNum : 100,
        multiselect : false,
        toppager : false,
        onSelectRow : function(id) {
            var $grid = $(this);
            var $dialog = $grid.closest(".modal");
            $dialog.modal("hide");
            var callback = $dialog.data("callback");
            if (callback) {
                var rowdata = $grid.jqGrid("getRowData", id);
                rowdata.id = id;
                callback.call($grid, rowdata);
            }
        }
    });
});
