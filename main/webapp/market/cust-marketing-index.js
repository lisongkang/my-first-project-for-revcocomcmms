
CustMarketingIndex = ({
	
	init : function() {
		 $(".grid-biz-market-cust-marketing-index").data("gridOptions", {
		        url : WEB_ROOT + '/market/cust-marketing!findByPage',
		        colModel : [ {
		            label : '流水号',
		            name : 'id',
		            hidden : true                          
		        }, {
		            label : '业务区编号',
		            name : 'areaid',
		            width : 60,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'right',
		            hidden : true 
		        }, {
		            label : '客户标识',
		            name : 'custid',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'right'
		        }, {
		            label : '客户名称',
		            name : 'name',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left'
		        }, {
		            label : '联系电话',
		            name : 'linkphone',
		            width : 30,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : 'sappdate',
		            name : 'sappdate',
		            width : 200,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : '住宅地址',
		            name : 'whladdr',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left'
		        }, {
		            label : '优先级',
		            name : 'pri',
		            width : 8,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : '所属片区',
		            name : 'ptachid',
		            width : 200,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : '所属片区',
		            name : 'patchname',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left'
		        }, {
		            label : '处理状态',
		            name : 'dealstatus',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            stype : 'select',
		            editoptions : {
			            value : Biz.getCacheParamDatas("CUST_MARKETING_DEALSTATUS")
			        }
		        }, {
		            label : '营销批次号',
		            name : 'batchno',
		            width : 30,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : '地市',
		            name : 'city',
		            width : 2,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : '网格人员',
		            name : 'areamger',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : '网格人员',
		            name : 'areamgername',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left'
		        }, {
		            label : '营销套餐标识',
		            name : 'knowid',
		            width : 60,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'right',
		            hidden : true 
		        },{
		            label : '营销套餐',
		            name : 'knowname',
		            width : 200,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : '录入部门',
		            name : 'deptname',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'right'
		        },{
		            label : '录入部门',
		            name : 'deptid',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'right',
		            hidden : true 
		        }, {
		            label : '营销次数',
		            name : 'alnums',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'right'
		        }, {
		            label : '经理联系电话',
		            name : 'mgerphone',
		            width : 30,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            hidden : true 
		        }, {
		            label : '营销结果',
		            name : 'result',
		            width : 100,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'left',
		            stype : 'select',
		            editoptions : {
			            value : Biz.getCacheParamDatas("CUST_MARKETING_RESULT")
			        }
		        }, {
		            label : '录入时间',
		            name : 'appdate',
		            width : 150,
		            formatter: 'date',
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'center',
		            hidden : true 
		        }, {
		            label : '录入操作员',
		            name : 'operid',
		            width : 60,
		            editable: true,
		            sortable:false,
		            search:false,
		            align : 'right',
		            hidden : true 
		        } ],
//		        editurl : WEB_ROOT + '/market/cust-marketing!doSave',
		        delurl : WEB_ROOT + '/market/cust-marketing!doDelete'
//		        fullediturl : WEB_ROOT + '/market/cust-marketing!inputTabs'
		    });
		 
 		
		 CustMarketingIndex.initQueOperids();
	},
	
	initQueOperids : function() {
		var $form = $(".form-market-cust-marketing-index");
		var queOperids = $form.find("#id_queOperids");
		
		var url = WEB_ROOT + "/market/market-batch!getGridOperList";
    	$form.ajaxJsonUrl(url, function(data) {
    		queOperids.empty();
    		queOperids.select2({placeholder: "请选择推送网格人员"});
    		queOperids.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.operid+'">'+item.opername+'</option>';
    			queOperids.append(option);
            })
    	});
	},
	
});

$(function() {
	  // 初始化
	  CustMarketingIndex.init();
});

