
AssIndexStoreInputBasic=({
	
	init : function(){
		 $(".form-biz-ass-store-ass-index-store-inputBasic").data("formOptions", {
		        bindEvents : function() {
		            var $form = $(this);
		            $form.find("button[name='btn_store']").click(function(){
		            	if (!AssIndexStoreInputBasic.checkRequiredInput($form)){
		            		return;
		            	}
		            	var storeData={};
		            	
		            	var ids =  $form.find('#id').val();
		            	if (ids != "") {
		            		storeData.id = ids;
		            	}
		            	
		            	storeData.city = $form.find('#city').val();
		            	storeData.asscontent = $form.find('#asscontent').val();
		            	storeData.assparam = $form.find('#assparam').val();
		            	storeData.unit = $form.find('#unit').val();
		            	storeData.taskunit = $form.find('#taskunit').val();
		            	storeData.totalnum = $form.find('#totalnum').val();
		            	storeData.expdate = $form.find('#expdate').val();
		            	storeData.depart = $form.find('#depart').val();
		            	storeData.expdate = $form.find('#expdate').val();
		            	
		            	// 获取考核对象的值
		            	if(storeData.assparam == 'BUSINCOME') {
//		            		storeData.assobjids = $form.find('#assobjName2').val();
		            		storeData.objid = $form.find('#assobjName2').val();
		            	} else if (storeData.assparam == 'NEWSERV'){
		            		storeData.objid = $form.find('#assobjName3').val();
		            	} else {
		            		storeData.objid = $form.find('#assobj').val();
		            	}
		            	
		            	storeData.assIndexStore = JSON.stringify(storeData);
		            	
		    			var url = WEB_ROOT + '/biz/ass/store/ass-index-store!doSave';
		    			$("body").ajaxJsonUrl(url, function(result) {
		    				Global.notify("success", "考核指标保存成功");
		    				
		    				$form.find('#btn_cancel').click();
		    				
		    				var $indexForm = $(".form-biz-ass-index-store-index");
		    				$indexForm.find('#btn_search').click();
				            
		    			},storeData);
		            	
		            });
		            
		        }
		    });
		 
//			$('.form-biz-ass-store-ass-index-store-inputBasic #deptName').unbind().bind("click", function() {
//    		    var $form = $('.form-biz-ass-store-ass-index-store-inputBasic');
//    			$form.popupDialog({
//    				url : WEB_ROOT + '/prv/prv-department!forward?_to_=selection',
//    	            title : '选取部门',
//    	            callback : function(item) {
//    	            	$(".form-biz-ass-store-ass-index-store-inputBasic #deptName").val(" [" +item.id + "] " + item.name);
//    	            	$(".form-biz-ass-store-ass-index-store-inputBasic #depart").val(item.id);
//    	            }
//    	        });
// 	        });
			
			AssIndexStoreInputBasic.initModifyPageInfo();
	},
	
	initModifyPageInfo : function(){
		var $form = $(".form-biz-ass-store-ass-index-store-inputBasic");
		 var id =  $form.find('#id').val();
		 if (id != ""){
			 
			 var jgrid = $('.grid-biz-ass-store-ass-index-store-index');
			 var rowData = jgrid.getRowData(id);
			 
			 var h_objid =  rowData["objid"];
			 var h_assobj =  rowData["assobj"];
			 var h_city =  rowData["city"];
			 var h_unit =  rowData["unit"];
			 var h_taskunit =  rowData["taskunit"];
			 var h_assparam =  rowData["assparam"];
			 var h_depart =  rowData["depart"];
			 var h_deptName =  rowData["deptName"];
			 var h_expdate = rowData["expdate"];
			 
			 $form.find('#assobj').val(h_objid);
			 $form.find('#depart').val(h_depart);
			 $form.find('#deptName').val(h_deptName);
			 $form.find('#expdate').val(h_expdate);
			 
			 var assobjName3Select = $(".form-biz-ass-store-ass-index-store-inputBasic #assobjName3");
			 var assobjName2Select = $(".form-biz-ass-store-ass-index-store-inputBasic #assobjName2");
			 
			 if (h_assparam == "NEWSERV" ) {
				 // 初始化BIZ_EXTSERV信息,选中
				 Biz.initSelect('BIZ_EXTSERV',assobjName3Select,h_objid);
				 
				// 初始化BIZ_EXTPERMARK信息,不选中
				 Biz.initSelect('BIZ_EXTPERMARK',assobjName2Select);
				 
			 } else if(h_assparam == "BUSINCOME" ){
				// 初始化BIZ_EXTPERMARK信息,选中
				 Biz.initSelect('BIZ_EXTPERMARK',assobjName2Select,h_objid);
				 
				// 初始化BIZ_EXTSERV信息,不选中
				 Biz.initSelect('BIZ_EXTSERV',assobjName3Select);
				 
			 } else {
				 $form.find('#assobjName').val(h_assobj);
				 // 初始化BIZ_EXTSERV信息,不选中
				 Biz.initSelect('BIZ_EXTSERV',assobjName3Select);
				 
				 // 初始化BIZ_EXTPERMARK信息,不选中
				 Biz.initSelect('BIZ_EXTPERMARK',assobjName2Select);
			 }
			 
		     // 初始化city信息
			 var citySelect = $(".form-biz-ass-store-ass-index-store-inputBasic #city");
			 Biz.initSelect('PRV_CITY',citySelect,h_city);
			 if (!Biz.isHighlvlOperaotr()) {
				 citySelect.attr("disabled","disabled");
			 }
			 
			 // 考核参数
			 var assparamSelect = $(".form-biz-ass-store-ass-index-store-inputBasic #assparam");
			 Biz.initSelect('BIZ_CHECKPARAM',assparamSelect,h_assparam);
			 
			 // 考核周期单位参数
			 var unitSelect = $(".form-biz-ass-store-ass-index-store-inputBasic #unit");
			 Biz.initSelect('BIZ_ASS_UNIT',unitSelect,h_unit);
			 
			 // 任务单位参数
			 var taskunitSelect = $(".form-biz-ass-store-ass-index-store-inputBasic #taskunit");
			 Biz.initSelect('BIZ_TASKUNIT',taskunitSelect,h_taskunit);
			 
			 AssIndexStoreInputBasic.changeAssparam2(h_assparam, ".form-biz-ass-store-ass-index-store-inputBasic");
				
		 } else {
			 $(".form-biz-ass-store-ass-index-store-inputBasic #deptName").val(Biz.LOGIN_INFO.deptname);
	                 $(".form-biz-ass-store-ass-index-store-inputBasic #depart").val(Biz.LOGIN_INFO.deptid);
			 
			 // 初始化BIZ_EXTSERV信息,不选中
			 var assobjName3Select = $(".form-biz-ass-store-ass-index-store-inputBasic #assobjName3");
			 Biz.initSelect('BIZ_EXTSERV',assobjName3Select);
			 
			 // 初始化BIZ_EXTPERMARK信息,不选中
			 var assobjName2Select = $(".form-biz-ass-store-ass-index-store-inputBasic #assobjName2");
			 Biz.initSelect('BIZ_EXTPERMARK',assobjName2Select);
			 
			 // 初始化city信息
			 var citySelect = $(".form-biz-ass-store-ass-index-store-inputBasic #city");
			 Biz.initSelect('PRV_CITY',citySelect,Biz.LOGIN_INFO.city);
			 if (!Biz.isHighlvlOperaotr()) {
				 citySelect.attr("disabled","disabled");
			 }
			 
			 // 考核参数
			 var assparamSelect = $(".form-biz-ass-store-ass-index-store-inputBasic #assparam");
			 Biz.initSelect('BIZ_CHECKPARAM',assparamSelect);
				
			 // 考核周期单位参数
			 var unitSelect = $(".form-biz-ass-store-ass-index-store-inputBasic #unit");
			 Biz.initSelect('BIZ_ASS_UNIT',unitSelect);
			 
			 // 任务单位参数
			 var taskunitSelect = $(".form-biz-ass-store-ass-index-store-inputBasic #taskunit");
			 Biz.initSelect('BIZ_TASKUNIT',taskunitSelect);
		 }
	},
	
	checkNull : function(objvalue){
		if(objvalue == undefined || objvalue == "" || objvalue == null || !objvalue){
			return true;
		}
		return false;
	},
	
	checkRequiredInput : function ($form) {
		var text = "";
		if(AssIndexStoreInputBasic.checkNull($form.find('#city').val()))
			text += "分公司、";
		if(AssIndexStoreInputBasic.checkNull($form.find('#asscontent').val()))
			text += "考核内容、";
		
		var assparam = $form.find('#assparam').val();
		if(AssIndexStoreInputBasic.checkNull(assparam))
			text += "考核参数、";
		

		if (assparam == "BUSINCOME") {
			if (AssIndexStoreInputBasic.checkNull($form.find('#assobjName2')
					.val())) {
				text += "考核对象、";
			}
		} else if (assparam == "NEWSERV") {
			if (AssIndexStoreInputBasic.checkNull($form.find('#assobjName3')
					.val())) {
				text += "考核对象、";
			}
		} else {
			if (AssIndexStoreInputBasic.checkNull($form.find('#assobj').val())) {
				text += "考核对象、";
			}
		}
		
		if(AssIndexStoreInputBasic.checkNull($form.find('#unit').val()))
			text += "考核周期单位、";
		if(AssIndexStoreInputBasic.checkNull($form.find('#taskunit').val()))
			text += "任务单位、";
		if(AssIndexStoreInputBasic.checkNull($form.find('#totalnum').val()))
			text += "任务总数、";
		if(AssIndexStoreInputBasic.checkNull($form.find('#expdate').val()))
			text += "失效时间	、";
		if(AssIndexStoreInputBasic.checkNull($form.find('#depart').val()))
			text += "指定部门、";		
		
		if("" != text){
			alert(text.substr(0,text.length-1) + " 为必填项!");
			return false;
		}
		
    	return true;
	
	},
	
	onSelectRow : function(obj) {
        var child = obj.getElementsByTagName("td");
        var rowdata = {}; 
        for(var i=0;i<child.length;i++){
        	var td = child[i];
            rowdata[td.getAttribute("name")] = td.innerHTML;
        }
        
		var $grid = $('#dialog_level_pcodeinfo');
        var $dialog = $grid.closest(".modal");
        $dialog.modal("hide");
        var callback = $dialog.data("callback");
        if (callback) {
            callback.call($grid, rowdata);
        }
    },
    
    changeAssparam : function(selector){
    	var $form = $(selector);
		$form.find('#assobjName').val("");
		$form.find('#assobj').val("");
		
		var assparam = $form.find("#assparam").val();
		
		AssIndexStoreInputBasic.changeAssparam2(assparam, selector);
    },
    
    changeAssparam2 : function(assparam, selector){
    	var $form = $(selector);
		
    	var div_assobjName3 = $form.find("#div_assobjName3");
		var div_assobjName2 = $form.find("#div_assobjName2");
		var div_assobjName = $form.find("#div_assobjName");
		
		
		if (assparam == 'BUSINCOME'){
			div_assobjName2.css("display","block");
			div_assobjName.css("display","none");
			div_assobjName3.css("display","none");
			
		} else if (assparam == 'NEWSERV'){
			div_assobjName2.css("display","none");
			div_assobjName.css("display","none");
			div_assobjName3.css("display","block");
		}else {
			div_assobjName2.css("display","none");
			div_assobjName.css("display","block");
			div_assobjName3.css("display","none");
		}
    },
    
	
	selectOrderPrds : function(selector){
		//selector须包含业务表单的id或class
		var $form = $(selector);
		var assparam = $form.find('#assparam').val();
		if (assparam == ""){
			alert("请选择考核参数");
			return;
		}
		
//		if (assparam == 'NEWSERV'){
//			alert("发展新用户不需要选择考核对象");
//			return;
//		}
		
		var title = "";
		if (assparam == 'SALESPKG'){
			title = '套餐展示';
		} else if (assparam == 'PRODUCT'){
			title = '产品展示';
		}
		
		$form.popupDialog({
            url : WEB_ROOT + '/biz/ass/store/ass-index-store!selectOrderPrds?assparam='+assparam,
            title : title,
            id : 'pcodeinfo',
            callback : function(rowdata) {
            	$form.find('#assobjName').val(rowdata.knowname + " " + rowdata.price);
            	$form.find('#assobj').val(rowdata.objid);
            }
        });
	}

});

$(function() {
    AssIndexStoreInputBasic.init();
});