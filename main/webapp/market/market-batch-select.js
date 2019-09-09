
MarketBatchSelect = ({
	
	init : function() {
		 $(".grid-biz-market-market-batch-select").data("gridOptions", {
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
//		            var ids = $(".grid-biz-market-market-batch-select").jqGrid('getDataIDs');
//		            for (var i = 0; i < ids.length; i++) {
//		                var id = ids[i];
//		                var rowData = $(".grid-biz-market-market-batch-select").getRowData(id);
//		                
//		                var optBtn = "";
//			            optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='MarketBatchIndex.deleteMarketBatch(\""+id+"\")' >删除</a>";
//		                jQuery(".grid-biz-market-market-batch-select").jqGrid('setRowData', ids[i], { self_opt: optBtn });
//		            }
		         }
		 
		    });
		 
		 // 初始化city信息
//		 var citySelect = $(".form-market-market-batch-select #id_city");
//		 Biz.initSelect('PRV_CITY',citySelect,Biz.LOGIN_INFO.city);
//		 if (!Biz.isHighlvlOperaotr()) {
//			 citySelect.attr("disabled","disabled");
//		 }
		 
		 var citySelect = $(".form-market-market-batch-select #id_city");
		 citySelect.append('<option value=""></option>');
 		 var option  = '<option value="' + Biz.LOGIN_INFO.city + '"'
						+ ' selected="selected"' + '>'
						+ Biz.LOGIN_INFO.cityname + '</option>';
		 citySelect.append(option);
		 citySelect.attr("disabled","disabled");
		 
		 
		 var deptSelect = $(".form-market-market-batch-select #id_dept");
		 
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
	
	selectMarketBatchs : function() {
		
		var $form = $(".form-market-market-batch-select");
		var $grid = $(".grid-biz-market-market-batch-select");
		
		var selecttype = $form.find("input[name='selecttype']:checked").val();
		
		var disp = "";
		var idsString = "";
		if (selecttype == '0') {
			disp = "*";
			idsString = "*"
		} else if (selecttype == '1') {
			var selectedIds = $grid.jqGrid("getGridParam", "selarrrow");
			if (selectedIds.length == 0) {
				alert("请先选中记录！");
				return;
			}
			
			for ( var int = 0; int < selectedIds.length; int++) {
				var selectedId = selectedIds[int];
				var rowData = $grid.jqGrid("getRowData",selectedId);
				idsString += selectedId +",";
				disp += rowData.knowname+",";
			}
			
			if (idsString != "") {
				idsString = idsString.substring(0, idsString.length-1);
			}
			
			if (disp != "") {
				disp = disp.substring(0, disp.length-1);
			}
		}
		
    	var $dialog = $form.closest(".modal");
		$dialog.modal("hide");
		
		var callback = $dialog.data("callback");
		if (callback) {
			var retData = {};
			retData.idsJson = idsString;
			retData.disp = disp;
			
		    callback.call($form, retData);
		}
	}
	
});

$(function() {
	  // 初始化
	  MarketBatchSelect.init();
});
