
BizRemindWarningIndex = ({
	init : function() {

	    $(".grid-biz-remind-biz-remind-warning-index").data("gridOptions", {
	        url : WEB_ROOT + '/biz/remind/biz-remind-warning!findByPage',
	        colModel : [  {
	            label : '操作',
	            name : 'self_opt',
	            sortable : false,
	            search :  false,
	            hidden : true,
	            width : 100
	        },{
	            label : '流水号',
	            name : 'id',
	            hidden : true                          
	        }, {
	            label : 'opername',
	            name : 'opername',
	            width : 150,
	            editable: true,
	            hidden : true,
	            align : 'left'
	        }, {
	            label : '预警类型',
	            name : 'objtype',
	            width : 150,
	            editable: true,
	            hidden : true,
	            align : 'left'
	        }, {
	            label : '预警类型',
	            name : 'objtypename',
	            width : 150,
	            editable: true,
	            align : 'left'
	        }, {
	            label : 'deptname',
	            name : 'deptname',
	            width : 150,
	            editable: true,
	            hidden : true,
	            align : 'left'
	        }, {
	            label : '预警对象标识',
	            name : 'objids',
	            width : 150,
	            editable: true,
	            hidden : true,
	            align : 'left'
	        }, {
	            label : '预警对象标识',
	            name : 'objidsname',
	            width : 150,
	            editable: true,
	            align : 'left'
	        },{
	            label : '预警任务描述',
	            name : 'description',
	            width : 150,
	            editable: true,
	            align : 'left'
	        }, {
	            label : '优先级',
	            name : 'pri',
	            width : 150,
	            editable: true,
	            align : 'right'
	        }, {
	            label : '有效期限',
	            name : 'edate',
	            width : 150,
	            formatter: 'date',
	            editable: true,
	            align : 'center'
	        }, {
	            label : '录入时间',
	            name : 'opdate',
	            width : 150,
	            formatter: 'date',
	            editable: true,
	            align : 'center'
	        }, {
	            label : '录入工号',
	            name : 'operid',
	            width : 150,
	            editable: true,
	            align : 'right'
	        }, {
	            label : '录入部门',
	            name : 'deptid',
	            width : 150,
	            editable: true,
	            align : 'right'
	        }, {
	            label : '所属分公司',
	            name : 'city',
	            width : 150,
	            editable: true,
	            align : 'left'
	        } ],
	        editurl : WEB_ROOT + '/biz/remind/biz-remind-warning!doSave',
	        delurl : WEB_ROOT + '/biz/remind/biz-remind-warning!doDelete',
	        fullediturl : WEB_ROOT + '/biz/remind/biz-remind-warning!edit',
	        
	        gridComplete: function () {
//	            var ids = $(".grid-biz-remind-biz-remind-warning-index").jqGrid('getDataIDs');
//	            for (var i = 0; i < ids.length; i++) {
//	                var id = ids[i];
//	                var rowData = $(".grid-biz-remind-biz-remind-warning-index").getRowData(id);
//	                
//	                var optBtn = "";
//		            optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='BizRemindWarningIndex.setRuleCfg(\""+id+"\")' >设置规则</a>";
//		            
//	                jQuery(".grid-biz-remind-biz-remind-warning-index").jqGrid('setRowData', ids[i], { self_opt: optBtn });
//	            }
	         }
	    });
	    
	    
	    BizRemindWarningIndex.initData();
	    
	},
	
	
	initData : function() {
		 // 初始化分公司信息
		 var citySelect = $(".form-biz-remind-biz-remind-warning-index #id_city");
		 citySelect.append('<option value=""></option>');
 		 var option  = '<option value="' + Biz.LOGIN_INFO.city + '"'
						+ ' selected="selected"' + '>'
						+ Biz.LOGIN_INFO.cityname + '</option>';
		 citySelect.append(option);
		 citySelect.attr("disabled","disabled");
		
		 // 初始化id_remobjtype信息
		 var remobjtypeSelect = $(".form-biz-remind-biz-remind-warning-index #id_remobjtype");
		 BizRemindWarningIndex.initSelect('BIZ_REM_OBJTYPE',remobjtypeSelect);
		 
		// 初始化id_dept信息
         var deptSelect = $(".form-biz-remind-biz-remind-warning-index #id_dept");
		 var url = WEB_ROOT + "/biz/remind/biz-remind-warning!getDepts";
		 deptSelect.ajaxJsonUrl(url, function(data) {
	    		deptSelect.empty();
	    		var option = '';
	    		$.each(data, function(i, item) {
	    			option = '<option value="'+item.id+'">'+item.name+'</option>';
	    			deptSelect.append(option);
	            });
	      });
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
	
	setRuleCfg : function(id) {
		 var $form = $(".form-biz-remind-biz-remind-warning-index");
		 var url = WEB_ROOT + '/biz/remind/biz-remind-warning!setRuleCfg?remid='+id;
		 $form.popupDialog({
				url : url,
				title : '设置规则',
	            id : 'setRuleCfg',
	            callback : function(item) {
//	            	$(".form-market-market-batch-pushInputBasic #pushCustomerObj").val(item.custsJson);
//	            	$(".form-market-market-batch-pushInputBasic #pushCustomerDisp").val(item.custDisp);
	            }
	     });
	}
	
});

$(function() {
	  // 初始化
	BizRemindWarningIndex.init();
});
