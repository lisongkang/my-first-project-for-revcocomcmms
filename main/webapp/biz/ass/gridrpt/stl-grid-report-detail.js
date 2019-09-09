
StlGridReportDetail = ({
	
	init : function() {
	    $(".grid-biz-ass-gridrpt-stl-grid-report-detail").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!findByPageForDetail',
	        colModel : [{
	            label : '流水号',
	            name : 'id',
	            hidden : true                          
	        }, {
	            label : '结算月份',
	            name : 'feemonth',
	            width : 150,
	            editable: true,
	            align : 'left',
	            hidden : true
	        }, {
	            label : '结算网格',
	            name : 'gridid',
	            width : 150,
	            editable: true,
	            align : 'right',
	            hidden : true
	        }, {
	            label : '片区网格',
	            name : 'pgridid',
	            width : 150,
	            editable: true,
	            align : 'right',
	            hidden : true
	        },{
	            label : '用户ID',
	            name : 'servid',
	            width : 150,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        },{
	            label : '业务类型',
	            name : 'permark',
	            width : 150,
	            editable: true,
	            align : 'left',
	            hidden : true
	        }, {
	            label : '业务类型',
	            name : 'permarkname',
	            width : 150,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'left'
	        }, {
	            label : '所属片区',
	            name : 'patchid',
	            width : 150,
	            editable: true,
	            align : 'right',
	            hidden : true
	        },{
	            label : '所属片区',
	            name : 'patchname',
	            width : 150,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        },{
	            label : '用户类型',
	            name : 'servtype',
	            width : 150,
	            editable: true,
	            align : 'left',
	            hidden : true
	        },{
	            label : '用户类型',
	            name : 'servtypename',
	            width : 150,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'left'
	        },{
	            label : '总金额',
	            name : 'amount',
	            width : 150,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        }, {
	            label : '总结算金额',
	            name : 'setamount',
	            width : 150,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        }, {
	            label : '网格结算金额',
	            name : 'netamount',
	            width : 150,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        }, {
	            label : '所属规则',
	            name : 'ruleid',
	            width : 150,
	            editable: true,
	            align : 'right',
	            hidden : true
	        } ]
	        //editurl : WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!doSave',
	        //delurl : WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!doDelete',
	        //fullediturl : WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!inputTabs'
	    });
		
		// 其它初始化
	    var selector = ".form-biz-ass-gridrpt-stl-grid-report-detail";
	    var curDate =  StlGridReportIndex.getNowFormatDate();
	    $(selector + ' #_startMonth').val(curDate);
	    $(selector + ' #_endMonth').val(curDate);
	},
	
	initDatePicker : function(selector){
		$(selector + ' #_startMonth').datetimepicker({
	        format: 'yyyymm',  
	         weekStart: 1,  
	         autoclose: true,  
	         startView: 3,  
	         minView: 3,  
	         forceParse: false,  
	         language: 'zh-CN'  
	    });
		
		$(selector + ' #_endMonth').datetimepicker({  
	        format: 'yyyymm',  
	         weekStart: 1,  
	         autoclose: true,  
	         startView: 3,  
	         minView: 3,  
	         forceParse: false,  
	         language: 'zh-CN'  
	    });
	},
	
	getNowFormatDate : function() {
	    var date = new Date();
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    
	    var currentdate = year + month ;
	    return currentdate;
	}
	
});

$(function() {
	  // 初始化
	  StlGridReportDetail.initDatePicker('.form-biz-ass-gridrpt-stl-grid-report-detail');
	  StlGridReportDetail.init();
});