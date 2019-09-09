$(function() {
    $(".grid-biz-ass-togrid-ass-index-togrid-index").data("gridOptions", {
        url : WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!findByPage',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true                          
        }, {
            label : '考核指标id',
            name : 'assid',
            width : 60,
            editable: true,
            hidden : true,
            align : 'right'
        }, {
            label : '网格编码',
            name : 'gridid',
            width : 60,
            editable: true,
            align : 'right'
        }, {
            label : '网格编号',
            name : 'gridcode',
            width : 60,
            editable: true,
            align : 'right'
        },{
            label : '网格名称',
            name : 'gridname',
            width : 60,
            editable: true,
            align : 'right'
        }, {
            label : '考核起始日期',
            name : 'bdate',
            width : 150,
            formatter: 'date',
            editable: true,
            align : 'center'
        }, {
            label : '考核周期(月)',
            name : 'cyclenum',
            width : 60,
            hidden:true,
            editable: true,
            align : 'right'
        }, {
            label : '每月考核数',
            name : 'assnum',
            width : 60,
            editable: true,
            align : 'right'
        }, {
            label : '下达时间',
            name : 'assdate',
            width : 150,
            formatter: 'date',
            editable: true,
            hidden : true,
            align : 'center'
        }, {
            label : '下达部门',
            name : 'depart',
            width : 60,
            editable: true,
            hidden : true,
            align : 'right'
        }, {
            label : '操作员',
            name : 'operator',
            width : 60,
            editable: true,
            hidden : true,
            align : 'right'
        } ],
//        editurl : WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!doSave',
        delurl : WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!doDelete'
//        fullediturl : WEB_ROOT + '/biz/ass/togrid/ass-index-togrid!inputTabs'
    });
});
