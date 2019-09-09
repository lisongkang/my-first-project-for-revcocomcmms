$(function() {
    $(".grid-biz-prv-prv-operator-index").data("gridOptions", {
        url : WEB_ROOT + '/prv/prv-operator!findOneCityByPage',
        colModel : [ {
            label : '用户姓名',
            name : 'name',
            width : 555,
            editable: true,
            align : 'left'
        },{
            label : '用户ID',
            name : 'operid',
            width : 400,
            editable: true,
            align : 'left',
            hidden : true
        } ,{
            label : '登录账号',
            name : 'loginname',
            width : 400,
            editable: true,
            align : 'left'
           
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
