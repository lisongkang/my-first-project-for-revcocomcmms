

StaAssindexList = ({
	pageMainDiv : $(".div-biz-sta-assindex-sta-assindex-list"),
	
	init : function() {
		$(".form-biz-sta-assindex-sta-assindex-list").data("formOptions", {
			// @_@!!!! ~…~,原来是form没写form-validation样式，所以怎么都绑定不了事件
			
			initAss : function(assparam) {
				var $form = $(this);
				var $ass = $form.find("#id_que_ass");
				
				var city = $form.find("#id_que_city").val();
				if (StaAssindexList.isEmpty(city) ||
						StaAssindexList.isEmpty(assparam)) {
					$ass.empty();
					$ass.append('<option value=""></option>');
					// grid.select2("val", "");
					$ass.select2({placeholder: "请选择考核方案"});
					return;
				}
				
				var url = WEB_ROOT + "/biz/sta/assindex/sta-assindex!getAssList";
	        	url = url + "?rows=-1&city=" + city + "&assparam=" + assparam;
	        	
		    	$form.ajaxJsonUrl(url, function(data) {
		    		$ass.empty();
		    		$ass.select2({placeholder: "请选择考核方案"});
		    		
		    		$ass.append('<option value=""></option>');
	        		var option = '';
	        		$.each(data, function(i, item) {
	                    option = '<option value="'+item.mcode+'">'+item.mname+'</option>';
	                    $ass.append(option);
	                })
	                // $ass.select2("val", "");
	        		// $ass.select2({placeholder: "请选择网格"});
		    	});
			},
			
			initGrid : function(city) {
				var $form = $(this);
				var $grid = $form.find("#id_que_grid");
				if (!city) {
					$grid.empty();
					$grid.append('<option value=""></option>');
					// grid.select2("val", "");
					$grid.select2({placeholder: "请选择网格"});
					return;
				}
				
				var gcode = "PRV_GRID_BY_CITY";
				var mcode = city;
				var url = WEB_ROOT + "/prv-sysparam!selectParamList";
	        	url = url + "?rows=-1&gcode=" + gcode + "&mcode=" + mcode;
	        	
		    	$form.ajaxJsonUrl(url, function(data) {
		    		$grid.empty();
		    		$grid.select2({placeholder: "请选择网格"});
		    		
		    		$grid.append('<option value=""></option>');
	        		var option = '';
	        		$.each(data, function(i, item) {
	                    option = '<option value="'+item.mcode+'">'+item.mname+'</option>';
	                    $grid.append(option);
	                })
	                // grid.select2("val", "");
	        		// grid.select2({placeholder: "请选择网格"});
		    	});
			},
			
			
			// 绑定事件
	        bindEvents : function() {
	        	// 地市改变事
	            var $form = $(this);
	            
	            var $city = $form.find("#id_que_city");
	            $city.change(function() {
	            	$form.data("formOptions").initGrid.call($form, $city.val());
	            	
	            	var $assparam = $form.find("#id_que_assparam");
	            	$assparam.select2("val", "");
	            	$assparam.select2({placeholder: "请选择网格"});
	            	
	            });
	            
	            if (!$form.find("#id_que_city").val()) {
	            	if (Biz.LOGIN_INFO) {
	            		$form.find("#id_que_city").select2("val", Biz.LOGIN_INFO.city);
	            		//$form.data("formOptions").initAssparam.call($form, Biz.LOGIN_INFO.city);
	            		$form.data("formOptions").initGrid.call($form, Biz.LOGIN_INFO.city);
	            	}
	            }
	            
	            var $assparam = $form.find("#id_que_assparam");
	            $assparam.change(function() {
	            	$form.data("formOptions").initAss.call($form, $assparam.val());
	            });
	            
	        }
	    });
		
		
		$(".grid-biz-sta-assindex-sta-assindex-list").data("gridOptions", {
	        url : WEB_ROOT + '/biz/sta/assindex/sta-assindex!queGridIncomeList',
	        colModel : [ {
	            label : '考核方案id',
	            name : 'assid',
	            hidden : true
	        }, {
	            label : '统计日期',
	            name : 'stadate',
	            formatter: 'date',
	            align : 'left'
	        }, {
	            label : '考核方案类型',
	            name : 'assparamname'
	        }, {
	            label : '考核方案名称',
	            name : 'assname'
	        }, {
	            label : '网格名称',
	            name : 'gridname'
	        }, {
	            label : '考核起日期',
	            formatter: 'date',
	            name : 'sdate'
	        }, {
	            label : '考核止日期',
	            formatter: 'date',
	            name : 'edate'
	        }, {
	            label : '每月考核数',
	            name : 'assnum'
	        }, {
	            label : '当期完成数',
	            name : 'comnum'
	        }, {
	            label : '完成偏差',
	            name : 'diffnum'
	        },
	        
	        {
	            label : '地市',
	            name : 'city',
	            hidden : true,
	            editoptions : {
	            	value : Biz.getCacheParamDatas("PRV_AREA")
	            }
	        }],
	        rowNum : 10,
	        height : 'auto',
	        multiselect : false,
	        toppager : true,
	        filterToolbar : false,
	        gridComplete: function () {
	        	var $grid = $(this);
	            var $button = $(".div-biz-sta-assindex-sta-assindex-list .ui-icon-arrowstop-1-w");
	            $button.click();
	        },
	        onSelectRow : function(id) {
	            var $grid = $(this);
	            var $dialog = $grid.closest(".modal");
	            $dialog.modal("hide");
	            var callback = $dialog.data("callback");
	            if (callback) {
	                var rowdata = $grid.jqGrid("getRowData", id);
	                rowdata.id = id;
	                callback.call($grid, rowdata);
	            }
	        }
	    });
		
		// 其它初始化
		
	},
	
	// 业务方法
	doReset : function(){
		var $form = StaAssindexList.pageMainDiv;
		var $queform=$form.find(".form-biz-sta-assindex-sta-assindex-list");
		var $city = $queform.find("#id_que_city");
		// $city.val("");
		$city.select2("val", "");
		$city.select2({placeholder: "请选择地市"});
		
		$city.change();
		
		var $grid = $queform.find("#id_que_grid");
		$grid.val("");
		
		var $statime = $queform.find("#id_que_statime");
		$statime.val("");
		
	},
	isEmpty : function(objvalue){
		if(!objvalue || objvalue == "" ){
			return true;
		}
		return false;
	},
	checkInput : function(){
		var $form = StaAssindexList.pageMainDiv;
		var city = $form.find("#id_que_city").val();
		
		var assparam= $form.find("#id_que_assparam").val();
		var assids = $form.find("#id_que_ass").val();
		
		// 格式:数组
		var gridids = $form.find("#id_que_grid").val();
		// 格式:stime ~ etime
		var statimestr = $form.find("#id_que_statime").val();
		
		// 检查参数合法性
		if(StaAssindexList.isEmpty(city)){
			Global.notify("info", "请选择地市");
			return false;
		}
		//if(StaAssindexList.isEmpty(gridids)){
		//	Global.notify("info", "请选择网格");
		//	return false;
		//}
		if(StaAssindexList.isEmpty(statimestr)){
			Global.notify("info", "请选择统计时段");
			return false;
		}
		
		var statimeArray = statimestr.split('～');
		if(statimeArray.length!=2 || StaAssindexList.isEmpty(statimeArray[1].trim())){
			Global.notify("info", "择统计时段有误，请重新选择");
			return false;
		}
		
		
	}
		
	
});

$(function() {
	  // 初始化
	  StaAssindexList.init();

});

