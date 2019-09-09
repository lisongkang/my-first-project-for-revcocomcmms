$(function() {
    $(".grid-biz-prv-prv-department-index").data("gridOptions", {
        url : WEB_ROOT + '/prv/prv-department!findPageDepartByCity',
        colModel : [ {
            label : '部门名称',
            name : 'name',
            width : 350,
            editable: true,
            align : 'left'
        }, {
            label : '上级部门',
            name : 'preDeptname',
            width : 350,
            editable: true,
            align : 'left',
            sortable : false
        }, {
            label : '部门级别',
            name : 'deplevel',
            width : 200,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("PRV_DEPTLEVEL")
            },
            align : 'left'
        }, {
            label : '部门类型',
            name : 'kind',
            width : 200,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("PRV_DEPT_TYPE")
            },
            align : 'left'
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
