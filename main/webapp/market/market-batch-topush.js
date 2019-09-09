
MarketBatchTopush = ({
	
	init : function() {
		 $(".grid-biz-market-market-batch-topush").data("gridOptions", {
		        url : WEB_ROOT + '/market/market-batch!findByPage',
		        colModel : [ {
		            label : '操作',
		            name : 'self_opt',
		            sortable : false,
		            search :  false,
		            width : 150
		        },{
		            label : '流水号',
		            name : 'id',
		            hidden : true 
		        }, {
		            label : 'sappdate',
		            name : 'sappdate',
		            width : 100,
		            editable: true,
		            align : 'left',
		            hidden : true
		        }, {
		            label : '营销标识',
		            name : 'knowid',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'right'
		        }, {
		            label : '营销说明',
		            name : 'knowname',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'left'
		        }, {
		            label : '营销批次',
		            name : 'batchno',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'left'
		        }, {
		            label : '当前状态',
		            name : 'status',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'left',
		            stype : 'select',
		            editoptions : {
		 	            value : Biz.getCacheParamDatas("MARKET_BATCH_STATUS")
		 	        }
		        }, {
		            label : '业务区范围',
		            name : 'areaids',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'left',
		            hidden : true
		        }, {
		            label : '业务区范围',
		            name : 'areanames',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'left'
		        }, {
		            label : '总户数',
		            name : 'nums',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'right'
		        }, {
		            label : '录入时间',
		            name : 'appdate',
		            width : 100,
		            formatter: 'date',
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'center'
		        }, {
		            label : '操作员',
		            name : 'operid',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'right',
		            hidden : true
		        }, {
		            label : '地市',
		            name : 'city',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'left',
		            hidden : true
		        },  {
		            label : '录入部门',
		            name : 'deptname',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'right'
		        },{
		            label : '录入部门',
		            name : 'deptid',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'right',
		            hidden : true
		        }, {
		            label : '备注',
		            name : 'memo',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'left',
		            hidden : true
		        } ],
		        editurl : WEB_ROOT + '/market/market-batch!doSave',
		        delurl : WEB_ROOT + '/market/market-batch!doDelete',
		        fullediturl : WEB_ROOT + '/market/market-batch!inputTabs',
		        
		        gridComplete: function () {
		            var ids = $(".grid-biz-market-market-batch-topush").jqGrid('getDataIDs');
		            for (var i = 0; i < ids.length; i++) {
		                var id = ids[i];
		                var rowData = $(".grid-biz-market-market-batch-topush").getRowData(id);
		                
		                var optBtn = "";
			            optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='MarketBatchTopush.toMarketPush(\""+id+"\")' >营销推送</a>";
			            
		                jQuery(".grid-biz-market-market-batch-topush").jqGrid('setRowData', ids[i], { self_opt: optBtn });
		            }
		         }
		 
		    });
		 
		 var citySelect = $(".form-market-market-batch-topush #id_city");
		 citySelect.append('<option value=""></option>');
 		 var option  = '<option value="' + Biz.LOGIN_INFO.city + '"'
						+ ' selected="selected"' + '>'
						+ Biz.LOGIN_INFO.cityname + '</option>';
		 citySelect.append(option);
		 citySelect.attr("disabled","disabled");
		 
		 
		 var deptSelect = $(".form-market-market-batch-topush #id_dept");
		 
		 var url = WEB_ROOT + "/market/market-batch!getDepts";
	     $("body").ajaxJsonUrl(url, function(data) {
	    		deptSelect.empty();
		    	deptSelect.select2({placeholder: "请选择片区"});
		    	deptSelect.append('<option value=""></option>');
	    		var option = '';
	    		$.each(data, function(i, item) {
	    			option = '<option value="'+item.id+'">'+item.name+'</option>';
	    			deptSelect.append(option);
	                
	            });
	    		
	      });
 		
	},
	
    toMarketPush : function(id) {
	    if(!id){
		    Global.notify("error", "营销任务id不能为空");
		    return ;
	    }
	    var rowData = $(".grid-biz-market-market-batch-topush").getRowData(id);
	
	    var selector = "tbody tr#"+id;
	    var $thistr = $(".grid-biz-market-market-batch-topush").find(selector);
	
	    // 好吧，非要先单击再双击才行
	    $thistr.trigger("click");
	    $thistr.trigger("dblclick");
    }

});

$(function() {
	  // 初始化
	  MarketBatchTopush.init();
});
