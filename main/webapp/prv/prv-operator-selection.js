$(function() {
    $(".grid-auth-selection-user-index").data("gridOptions", {
        url : WEB_ROOT + "/prv/prv-operator!findPageOpersByCity",
        colModel : [ {
            label : '登录账号',
            name : 'loginname',
            editable : true,
            editoptions : {
                updatable : false
            },
            width : 120
        }, {
            label : '名称',
            name : 'name',
            editable : true,
            width : 120
        }, {
            label : '状态',
            name : 'status',
            width : 65,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("PRV_USE_STATUS")
            },
            align : 'left'
        }, {
            label : '启用时间',
            name : 'stime',
            width : 150,
            formatter: 'date',
            editable: true,
            align : 'center'
        }, {
            label : '结束时间',
            name : 'etime',
            width : 150,
            formatter: 'date',
            editable: true,
            align : 'center'
        } ],
        rowNum : 10,
        multiselect : false,
        toppager : false,
        filterToolbar : false,
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