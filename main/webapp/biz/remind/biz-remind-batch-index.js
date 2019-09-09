
BizRemindBatchIndex = ({
	init : function() {

		$(".grid-biz-remind-biz-remind-batch-index").data("gridOptions", {
	        url : WEB_ROOT + '/biz/remind/biz-remind-batch!findByPage',
	        colModel : [ {
	            label : '操作',
	            name : 'self_opt',
	            sortable : false,
	            search :  false,
	            width : 100
	        },{
	            label : '流水号',
	            name : 'id',
	            hidden : true                          
	        }, {
	            label : '预警任务标识',
	            name : 'remid',
	            width : 100,
	            editable: true,
	            align : 'right'
	        },{
	            label : '预警对象类型',
	            name : 'objtypename',
	            width : 100,
	            editable: true,
	            align : 'right'
	        }, {
	            label : '预警任务描述',
	            name : 'description',
	            width : 100,
	            editable: true,
	            align : 'left'
	        }, {
	            label : '预警生成日期',
	            name : 'buildate',
	            width : 200,
	            formatter: 'date',
	            editable: true,
	            align : 'center'
	        },{
	            label : '是否已确认',
	            name : 'iscfm',
	            width : 100,
	            editable: true,
	            align : 'left',
	            stype : 'select',
	            editoptions : {
	            	value : Biz.getCacheParamDatas("SYS_YESNO")
	            }
	        }],
//	        editurl : WEB_ROOT + '/biz/remind/biz-remind-batch!doSave',
//	        delurl : WEB_ROOT + '/biz/remind/biz-remind-batch!doDelete',
	        fullediturl : WEB_ROOT + '/biz/remind/biz-remind-batch!inputTabs',
	        
	        gridComplete: function () {
	            var ids = $(".grid-biz-remind-biz-remind-batch-index").jqGrid('getDataIDs');
	            for (var i = 0; i < ids.length; i++) {
	                var id = ids[i];
	                var rowData = $(".grid-biz-remind-biz-remind-batch-index").getRowData(id);
	                
	                var optBtn = "";
		            optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='BizRemindBatchIndex.toRecordInfo(\""+id+"\")' >查看明细</a>";
		            
	                jQuery(".grid-biz-remind-biz-remind-batch-index").jqGrid('setRowData', ids[i], { self_opt: optBtn });
	            }
	        }
	    });
		
	    BizRemindBatchIndex.initData();
	},
	
	initData : function() {
		 // 初始化分公司信息
		 var citySelect = $(".form-biz-remind-biz-remind-batch-index #id_city");
		 citySelect.append('<option value=""></option>');
 		 var option  = '<option value="' + Biz.LOGIN_INFO.city + '"'
						+ ' selected="selected"' + '>'
						+ Biz.LOGIN_INFO.cityname + '</option>';
		 citySelect.append(option);
		 citySelect.attr("disabled","disabled");
		
		 // 初始化id_remobjtype信息
		 var remobjtypeSelect = $(".form-biz-remind-biz-remind-batch-index #id_remobjtype");
		 BizRemindBatchIndex.initSelect('BIZ_REM_OBJTYPE',remobjtypeSelect);
		 
		 // 初始化iscfmSelect信息
         var iscfmSelect = $(".form-biz-remind-biz-remind-batch-index #id_iscfm");
         BizRemindBatchIndex.initSelect('SYS_YESNO',iscfmSelect);
	},
	
	initSelect : function(gcode,obj,value){
		obj.empty();
		var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode;
		obj.ajaxJsonUrl(url, function(data) {
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.mcode+'">'+item.display+'</option>';
    			obj.append(option);
            });
            obj.select2("val", value);
    	});
	},
	
	toRecordInfo : function(id) {
	    if(!id){
		    Global.notify("error", "预警批次id不能为空");
		    return ;
	    }
	    var rowData = $(".grid-biz-remind-biz-remind-batch-index").getRowData(id);
	
	    var selector = "tbody tr#"+id;
	    var $thistr = $(".grid-biz-remind-biz-remind-batch-index").find(selector);
	
	    // 好吧，非要先单击再双击才行
	    $thistr.trigger("click");
	    $thistr.trigger("dblclick");
    },
    
    confirmRemindBatchs : function() {
		var $grid = $(".grid-biz-remind-biz-remind-batch-index");
		
		var selectedIds = $grid.jqGrid("getGridParam", "selarrrow");
		if (selectedIds.length == 0) {
			alert("请先选中记录！");
			return;
		}
		var ids = selectedIds;
		return BizRemindBatchIndex.confirmRemindBatchsLast(ids);
	},
	
	confirmRemindBatchsLast : function(ids) {
		var $form = $(".form-biz-remind-biz-remind-batch-index");
		var url = WEB_ROOT + "/biz/remind/biz-remind-batch!confirmRemindBatch?ids=" + ids;
		$("body").ajaxJsonUrl(url, function(data) {
			Global.notify("success", "确认预警任务成功");
			$form.find('#searchBtn').click();
	    });
	}
	
});

$(function() {
	  // 初始化
	BizRemindBatchIndex.init();
});

