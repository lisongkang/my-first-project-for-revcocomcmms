
BizRemindRecordIndex = ({
	init : function() {

		$(".grid-biz-remind-biz-remind-record-index").data("gridOptions", {
	        url : WEB_ROOT + '/biz/remind/biz-remind-record!findByPage',
	        colModel : [ {
	            label : '流水号',
	            name : 'id',
	            hidden : true                          
	        }, {
	            label : '批次标识',
	            name : 'batid',
	            width : 60,
	            editable: true,
	            align : 'right',
	            hidden : true
	        }, {
	            label : '预警对象标识',
	            name : 'objid',
	            width : 30,
	            editable: true,
	            align : 'left',
	            hidden : true
	        }, {
	            label : '预警说明',
	            name : 'objcaption',
	            width : 255,
	            editable: true,
	            align : 'left',
	            hidden : true
	        }, {
	            label : '客户ID',
	            name : 'custid',
	            width : 60,
	            editable: true,
	            align : 'right'
	        }, 
	        {
	            label : '客户名称',
	            name : 'name',
	            width : 60,
	            editable: true,
	            align : 'right'
	        }, 
	        {
	            label : '营销标识名称',
	            name : 'knowname',
	            width : 60,
	            editable: true,
	            align : 'right'
	        }, 
	        {
	            label : '片区',
	            name : 'areaname',
	            width : 60,
	            editable: true,
	            align : 'right'
	        }, 
	        {
	            label : '住宅地址',
	            name : 'whladdr',
	            width : 60,
	            editable: true,
	            align : 'right'
	        }, 
	        {
	            label : '社区经理',
	            name : 'areamgername',
	            width : 60,
	            editable: true,
	            align : 'right'
	        }, 
	        {
	            label : '记录状态',
	            name : 'status',
	            width : 60,
	            editable: true,
	            align : 'left',
	            stype : 'select',
	            editoptions : {
	            	value : Biz.getCacheParamDatas("REM_REC_STATUS")
	            }
	        } ]
//	        editurl : WEB_ROOT + '/biz/remind/biz-remind-record!doSave',
//	        delurl : WEB_ROOT + '/biz/remind/biz-remind-record!doDelete',
//	        fullediturl : WEB_ROOT + '/biz/remind/biz-remind-record!inputTabs'
		});
		
		BizRemindRecordIndex.initData();
	},
	
	initData : function() {
		
		 // 初始化areaidSelect信息
		 var areaidSelect = $(".form-biz-remind-biz-remind-record-index #id_areaid");
		 BizRemindRecordIndex.initSelect('PRV_AREA',areaidSelect);
		 
		// 初始化statusSelect信息
         var statusSelect = $(".form-biz-remind-biz-remind-record-index #id_status");
         BizRemindRecordIndex.initSelect('REM_REC_STATUS',statusSelect);
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
	}
	
});

$(function() {
	  // 初始化
	BizRemindRecordIndex.init();
});


