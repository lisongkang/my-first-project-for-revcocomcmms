$(function() {
    $(".grid-market-grid-oss-info-index").data("gridOptions", {
        url : WEB_ROOT + '/market/grid-oss-info!findByPage',
        colModel : [ {
            label : '网格编号',
            name : 'gridid',
            width : 150
        }, {
            label : '网格名称',
            name : 'gridname',
            width : 350
        }, {
        	label : '状态',
            name : 'status',
            stype : 'select',
            width : 100,
            editoptions : {
            	value : {'Y':'有效','N':'无效'}
            }
        }, {
            label : '创建时间',
            name : 'created',
            width : 150
        } ],
        rowNum : 10,
        height : 'auto',
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
