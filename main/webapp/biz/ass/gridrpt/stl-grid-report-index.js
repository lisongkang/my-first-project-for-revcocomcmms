
StlGridReportIndex = ({
	
	init : function() {
	    $(".grid-biz-ass-gridrpt-stl-grid-report-index").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!findByPage',
	        colModel : [{
	            label : '操作',
	            name : 'self_opt',
	            hidden : false,
	            sortable : false,
	            search : false,
	            width : 60
	        },{
	            label : '流水号',
	            name : 'id',
	            hidden : true                          
	        }, {
	            label : '结算月份',
	            name : 'feemonth',
	            width : 60,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'left'
	        }, {
	            label : '结算网格',
	            name : 'gridid',
	            width : 60,
	            editable: true,
	            align : 'right',
	            hidden : true
	        }, {
	            label : '结算网格',
	            name : 'gridname',
	            width : 60,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        },  {
	            label : '网格经理',
	            name : 'gridmanager',
	            width : 60,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        }, {
	            label : '片区网格',
	            name : 'pgridid',
	            width : 60,
	            editable: true,
	            align : 'right',
	            hidden : true
	        }, {
	            label : '业务类型',
	            name : 'permark',
	            width : 60,
	            editable: true,
	            align : 'left',
	            hidden : true
	        }, {
	            label : '总笔数',
	            name : 'nums',
	            width : 60,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        }, {
	            label : '总金额',
	            name : 'amount',
	            width : 60,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        }, {
	            label : '总结算金额',
	            name : 'setamount',
	            width : 60,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        }, {
	            label : '网格结算金额',
	            name : 'netamount',
	            width : 60,
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'right'
	        }, {
	            label : '生成时间',
	            name : 'optime',
	            width : 100,
	            formatter: 'datetime',
	            editable: true,
	            search : false,
	            sortable : false,
	            align : 'center'
	        } ],
	        //editurl : WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!doSave',
	        //delurl : WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!doDelete',
	        //fullediturl : WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!inputTabs',
	        
	        gridComplete: function () {
	            var ids = $(".grid-biz-ass-gridrpt-stl-grid-report-index").jqGrid('getDataIDs');
	            for (var i = 0; i < ids.length; i++) {
	                var id = ids[i];
	                var optBtn = "<a href='javascript:;' style='color:#f60' onclick='StlGridReportIndex.detail(\""+id+"\")' >详细</a>";
	                jQuery(".grid-biz-ass-gridrpt-stl-grid-report-index").jqGrid('setRowData', ids[i], { self_opt: optBtn });
	            }
	         }
	    });
		
		// 其它初始化
	    var selector = ".form-biz-ass-gridrpt-stl-grid-report-index";
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
	    
	    var currentdate = year +""+ month ;
	    return currentdate;
	},
	
	detail : function(id) {
		if(!id) return ;
		var $form = $(".form-biz-ass-gridrpt-stl-grid-report-index");
		$form.popupDialog({
            url :  WEB_ROOT + '/biz/ass/gridrpt/stl-grid-report!doDetail?id='+id,
            title : '报表明细',
            id : "form-biz-ass-gridrpt-detail",
//            size : "auto",
            callback : function(rowdata) {
            	
            }
        });
	}
	
});

$(function() {
	  // 初始化
	  StlGridReportIndex.initDatePicker('.form-biz-ass-gridrpt-stl-grid-report-index');
	  StlGridReportIndex.init();
});