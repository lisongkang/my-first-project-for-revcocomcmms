
MarketBatchIndex = ({
	
	init : function() {
		 $(".grid-biz-market-market-batch-index").data("gridOptions", {
		        url : WEB_ROOT + '/market/market-batch!findByPage',
		        colModel : [ {
		            label : '操作',
		            name : 'self_opt',
		            sortable : false,
		            search :  false,
		            width : 150,
		            hidden : true 
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
		        }, {
		            label : '录入部门',
		            name : 'deptname',
		            width : 100,
		            editable: true,
		            sortable : false,
		            search :  false,
		            align : 'right'
		        }, {
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
		        //delurl : WEB_ROOT + '/market/market-batch!doDelete',
		        fullediturl : WEB_ROOT + '/market/market-batch!edit',
		        
		        gridComplete: function () {
//		            var ids = $(".grid-biz-market-market-batch-index").jqGrid('getDataIDs');
//		            for (var i = 0; i < ids.length; i++) {
//		                var id = ids[i];
//		                var rowData = $(".grid-biz-market-market-batch-index").getRowData(id);
//		                
//		                var optBtn = "";
//			            optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='MarketBatchIndex.deleteMarketBatch(\""+id+"\")' >删除</a>";
//		                jQuery(".grid-biz-market-market-batch-index").jqGrid('setRowData', ids[i], { self_opt: optBtn });
//		            }
		         }
		 
		    });
		 
		 // 初始化city信息
//		 var citySelect = $(".form-market-market-batch-index #id_city");
//		 Biz.initSelect('PRV_CITY',citySelect,Biz.LOGIN_INFO.city);
//		 if (!Biz.isHighlvlOperaotr()) {
//			 citySelect.attr("disabled","disabled");
//		 }
		 
		 var citySelect = $(".form-market-market-batch-index #id_city");
		 citySelect.append('<option value=""></option>');
 		 var option  = '<option value="' + Biz.LOGIN_INFO.city + '"'
						+ ' selected="selected"' + '>'
						+ Biz.LOGIN_INFO.cityname + '</option>';
		 citySelect.append(option);
		 citySelect.attr("disabled","disabled");
		 
		 
		 var deptSelect = $(".form-market-market-batch-index #id_dept");
		 
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
	
//	deleteMarketBatch : function(id) {
//		var ids = id;
//		return MarketBatchIndex.deleteMarketBatchLast(ids);
//	},
	
	deleteMarketBatchs : function() {
		var $grid = $(".grid-biz-market-market-batch-index");
		
		var selectedIds = $grid.jqGrid("getGridParam", "selarrrow");
		if (selectedIds.length == 0) {
			alert("请先选中记录！");
			return;
		}
		var ids = selectedIds;
		return MarketBatchIndex.deleteMarketBatchLast(ids);
	},
	
	deleteMarketBatchLast : function(ids) {
		var $form = $(".form-market-market-batch-index");
		var confirmMsg = "删除营销任务会同时把关联的营销推送删除，确认删除吗？";
		bootbox.confirm(confirmMsg, function(g) {
			if (g) {
				var url = WEB_ROOT + "/market/market-batch!deleteMarketBatch?ids=" + ids;
				$("body").ajaxJsonUrl(url, function(data) {
					Global.notify("success", "删除营销任务成功");
					$form.find('#searchBtn').click();
			    });
				
				return true;
			}
		});
	}
	
});

$(function() {
	  // 初始化
	  MarketBatchIndex.init();
});
