$(function() {
    $(".visit-comment-record-page-index").data("gridOptions", {
        url : WEB_ROOT + '/biz/sys/visit-comment-record!findByPage',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true 
                                    
        }, {
            label : '地市',
            name : 'city',
            width : 50,
            align : 'left',
            stype : 'select',
            editoptions : {
            	value : Biz.getCacheParamDatas("PRV_CITY")
            }
        },{
            label : '客户名称',
            name : 'custName',
            width : 100,
            align : 'right',
        },{
            label : '手机号码',
            name : 'mobile',
            width : 150,
            align : 'right'
        },  {
            label : '回访时间',
            name : 'sendTime',
            formatter: 'date',
            width : 60,
            align : 'left'
        },{
            label : '回访流水号',
            name : 'bossSerinol',
            width : 120,
            align : 'left'
        },{
            label : '发送内容',
            name : 'sendContent',
            width : 180,
            align : 'right',
        }, {
            label : '发起回访系统',
            name : 'sendSystem',
            width : 90,
            align : 'right'
        }, {
            label : '客户回复时间',
            name : 'commentTime',
            formatter: 'date',
            width : 90,
            align : 'right'
        }, {
            label : '评价分值',
            name : 'commentTotal',
            width : 100,
            align : 'left'
        },{
            label : '客户建议',
            name : 'commentSuggest',
            width : 150,
            align : 'left'
        } ],
        inlineNav : {
            add : false
        },
        filterToolbar : false
    });
});

