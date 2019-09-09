$(function() {
    $(".grid-biz-prv-prv-department-index").data("gridOptions", {
        url : WEB_ROOT + '/prv/prv-department!findOneCityByPage',
        colModel : [ {
            label : '部门名称',
            name : 'name',
            width : 955,
            editable: true,
            align : 'left'
        },{
            label : '部门ID',
            name : 'deptid',
            width : 300,
            editable: true,
            align : 'left',
            hidden : true
        }       
        ],
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
