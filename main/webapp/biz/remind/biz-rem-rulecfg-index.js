$(function() {
    $(".grid-biz-remind-biz-rem-rulecfg-index").data("gridOptions", {
        url : WEB_ROOT + '/biz/remind/biz-rem-rulecfg!findByPage',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true                          
        }, {
            label : 'deptname',
            name : 'deptname',
            width : 200,
            editable: true,
            hidden : true,
            align : 'left'
        }, {
            label : '预警任务标识',
            name : 'remid',
            width : 60,
            editable: true,
            hidden : true,
            align : 'right'
        }, {
            label : 'opername',
            name : 'opername',
            width : 200,
            editable: true,
            hidden : true,
            align : 'left'
        }, {
            label : '预警条件',
            name : 'tritype',
            width : 200,
            editable: true,
            align : 'left',
            stype : 'select',
            editoptions : {
            	value : Biz.getCacheParamDatas("BIZ_TRITYPE")
            }
        }, {
            label : '触发条件(超过小时数)',
            name : 'trivalues',
            width : 200,
            editable: true,
            align : 'left'
        }, {
            label : '提醒次数',
            name : 'nums',
            width : 100,
            editable: true,
            align : 'right'
        }, {
            label : '预警有效天数',
            name : 'elen',
            width : 100,
            editable: true,
            align : 'right'
        }, {
            label : '是否自动确认',
            name : 'iscfm',
            width : 100,
            editable: true,
            align : 'left',
            stype : 'select',
            editoptions : {
            	value : Biz.getCacheParamDatas("SYS_YESNO")
            }
        }, {
            label : '录入时间',
            name : 'appdate',
            width : 150,
            formatter: 'date',
            editable: true,
            hidden : true,
            align : 'center'
        }, {
            label : '录入工号',
            name : 'operid',
            width : 60,
            editable: true,
            hidden : true,
            align : 'right'
        }, {
            label : '录入部门',
            name : 'deptid',
            width : 60,
            editable: true,
            hidden : true,
            align : 'right'
        }, {
            label : '所属分公司',
            name : 'city',
            width : 100,
            editable: true,
            align : 'left',
            stype : 'select',
            editoptions : {
            	value : Biz.getCacheParamDatas("PRV_CITY")
            }
        } ],
//        editurl : WEB_ROOT + '/biz/remind/biz-rem-rulecfg!doSave',
        delurl : WEB_ROOT + '/biz/remind/biz-rem-rulecfg!doDelete'
//        fullediturl : WEB_ROOT + '/biz/remind/biz-rem-rulecfg!inputTabs'
    });
});
