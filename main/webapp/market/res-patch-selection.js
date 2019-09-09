$(function() {
    $(".grid-market-res-patch-index").data("gridOptions", {
        url : WEB_ROOT + '/market/res-patch!findByPage',
        colModel : [ {
            label : '小区名称',
            name : 'patchname',
            width : 350
        },  {
            label : '小区代码',
            name : 'defcode',
            width : 350
        }, {
            label : '所属业务区',
            name : 'areaid',
            editable: true,
            stype : 'select',
            align : 'left',
            editoptions : {
            	value : Biz.getCacheParamDatas("PRV_AREA")
            }
        }, 
//        {
//        	label : '所属业务区1',
//            name : 'extraAttributes.areaname',
//            width : 350
//        },
        {
        	name : 'areaid',
        	hidden : true,
            hidedlg : true
        } ],
        rowNum : 10,
        multiselect : false,
        toppager : false,
        filterToolbar : false,
        height :'auto',
        shrinkToFit : true,
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
