$(function() {
	$(".grid-biz-log-biz-trace-index").data("gridOptions", {    	
    	url : WEB_ROOT + '/log/trace!findByPage',
        colModel : [ {
            label : '操作员编号',
            name : 'operid',
            width : 60,
            align : 'left'
        }, {
            label : '操作时间',
            name : 'optime',
            width : 150,
            formatter: 'date',
            align : 'center'
        }, {
            label : '订单编号',
            name : 'orderid',
            width : 60,
            align : 'center'
        }, {
            label : '地区编码',
            name : 'city',
            width : 65,
            align : 'center'
        } ],
        sortorder : "desc",
        sortname : "id",
        addable : false,
        viewurl : WEB_ROOT + '/log/trace!viewTabs'
    });
});
