$(function() {
    $(".return-visit-template-index").data("gridOptions", {
        url : WEB_ROOT + '/biz/sys/return-visit-template!findByPage',
        colModel : [ {
            label : '模板类型',
            name : 'templateType',
            editable : false,
            stype:'select',
            editoptions:{
            	value:{
            		'0' : '短信',
					'1' : '微信推送'
            	}
            },
            width : 120
        }, {
            label : '标题',
            name : 'templateTitle',
            editable : false,
            width : 120
        }, {
            label : '内容',
            name : 'templateContent',
            editable : false,
            width : 360
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
