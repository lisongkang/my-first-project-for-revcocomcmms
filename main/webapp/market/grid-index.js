$(function() {
    $(".grid-biz-market-grid-index").data("gridOptions", {
        url : WEB_ROOT + '/market/grid!findByPage',
        colModel : [ {
            label : '网格编码',
            name : 'gridcode',
            editable: true,
            width : 200,
            align : 'left'
        }, {
            label : '网格名称',
            name : 'gridname',
            editable: true,
            width : 300,
            align : 'left'
        }, {
			label : '网格类型',
            name : 'gridtype',
            editable: true,
            stype : 'select',
            width : 80,
            editoptions : {
            	value : Biz.getPrvParamListDatas("BIZ_GRID_GRIDTYPE")
            }
        }, {
			label : '所属分公司',
            name : 'city',
            editable: true,
            editrules : {
                required : true
            },
            stype : 'select',
            width : 60,
            editoptions : {
            	value : Biz.getPrvParamListDatas("PRV_CITY"),
            	defaultValue : Biz.LOGIN_INFO.city
            }
        } ],
        //filterToolbar : false,
        editurl : WEB_ROOT + '/market/grid!doSave',
        delurl : WEB_ROOT + '/market/grid!doDelete',
        fullediturl : WEB_ROOT + '/market/grid!inputTabs'
    });
});
