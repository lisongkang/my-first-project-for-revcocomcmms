$(function() {
    $(".sys-sys-cust-visit-rule-index").data("gridOptions", {
        url : WEB_ROOT + "/biz/sys/sys-cust-visit-rule!findPageByCity",
        colModel : [ {
            label : '地市',
            name : 'cityName',
            editable : false,
            /*stype:'select',
            editoptions:{
            	value:Biz.getPrvParamListDatas("RES_KIND")
            },*/
            width : 120
        }, {
            label : '片区',
            name : 'areaName',
            editable : false,
            /*stype : 'select',
            editoptions : {
        		value : Biz.getPrvParamListDatas("PRV_CITY")
            },*/
            width : 120
        }, {
            label : '业务类型',
            name : 'opcodeName',
            editable : false,
            width : 120
        }, {
            label : '发送方式',
            name : 'sendMethodName',
            editable : false,
            width : 120
        }, {
            label : '最大次数',
            name : 'maxTimes',
            editable : false,
            width : 90
        }, {
            label : '延时长度',
            name : 'delayValue',
            editable : false,
            width : 90
        }, {
            label : '时间类型',
            name : 'delayTypeName',
            editable : true,
            width : 90
        }],
        sortname : "id",
        filterToolbar : false,
        delurl : WEB_ROOT + '/biz/sys/sys-cust-visit-rule!doDelete',
        fullediturl : WEB_ROOT + '/biz/sys/sys-cust-visit-rule!edit'
       
    });
});