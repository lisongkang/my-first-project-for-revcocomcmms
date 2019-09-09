

StaGridincomeList = ({
	pageMainDiv : $(".div-biz-sta-gridincome-sta-gridincome-list"),
	
	init : function() {
		$(".form-biz-sta-gridincome-sta-gridincome-list").data("formOptions", {
			//@_@!!!! ~…~,原来是form没写form-validation样式，所以怎么都绑定不了事件
			
			initGrid : function(city) {
				var $form = $(this);
				var $grid = $form.find("#id_que_grid");
				if (!city) {
					$grid.empty();
					$grid.append('<option value=""></option>');
					//grid.select2("val", "");
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
	                //$grid.select2("val", "");
	        		//$grid.select2({placeholder: "请选择网格"});
		    	});
			},
			
			
			//绑定事件
	        bindEvents : function() {
	        	//地市改变事
	            var $form = $(this);
	            var $city = $form.find("#id_que_city");
	            $city.change(function() {
	            	$form.data("formOptions").initGrid.call($form, $city.val());
	            });
	            
	            if (!$form.find("#id_que_city").val()) {
	            	if (Biz.LOGIN_INFO) {
	            		$form.find("#id_que_city").select2("val", Biz.LOGIN_INFO.city);
	            		$form.data("formOptions").initGrid.call($form, Biz.LOGIN_INFO.city);
	            	}
	            }
	            
	        }
	    });
		
		
		$(".grid-biz-sta-gridincome-sta-gridincome-list").data("gridOptions", {
	        url : WEB_ROOT + '/biz/sta/gridincome/sta-gridincome!queGridIncomeList',
	        colModel : [ {
	            label : '网格id',
	            name : 'gridid',
	            hidden : true
	        }, {
	            label : '网格名称',
	            name : 'gridname'
	        }, {
	            label : '销售额',
	            name : 'income'
	        }, {
	            label : '统计时间',
	            name : 'statime',
	            align : 'left'
	        }, {
	            label : '业务区id',
	            name : 'areaid',
	            hidden : true
	        }, {
	            label : '地市',
	            name : 'city',
	            hidden : true,
	            width : 150,
	            align : 'center',
	            editoptions : {
	            	value : Biz.getCacheParamDatas("PRV_AREA")
	            }
	            
	        }],
	        rowNum : 10,
	        height :'auto',
	        shrinkToFit : true,
	        multiselect : false,
	        toppager : true,
	        filterToolbar : false,
	        gridComplete: function () {
	        	//var $grid = $(this);
	            //var $button = $(".div-biz-sta-gridincome-sta-gridincome-list .ui-icon-arrowstop-1-w");
	            //$button.click();
	            
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
	
	//业务方法
	doReset : function(){
		var $form = StaGridincomeList.pageMainDiv;
		var $queform=$form.find(".form-biz-sta-gridincome-sta-gridincome-list");
		var $city = $queform.find("#id_que_city");
		//$city.val("");
		$city.select2("val", "");
		$city.select2({placeholder: "请选择网格"});
		
		$city.change();
		
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
		var $form = StaGridincomeList.pageMainDiv;
		var city = $form.find("#id_que_city").val();
		//格式:数组
		var gridids = $form.find("#id_que_grid").val();
		//格式:stime ~ etime
		var statimestr = $form.find("#id_que_statime").val();
		
		//检查参数合法性
		if(StaGridincomeList.isEmpty(city)){
			Global.notify("info", "请选择地市");
			return false;
		}
		if(StaGridincomeList.isEmpty(gridids)){
			//Global.notify("info", "请选择网格");
			//return false;
		}
		if(StaGridincomeList.isEmpty(statimestr)){
			Global.notify("info", "请选择统计时段");
			return false;
		}
		
		var statimeArray = statimestr.split('～');
		if(statimeArray.length!=2 || StaGridincomeList.isEmpty(statimeArray[1].trim())){
			Global.notify("info", "择统计时段有误，请重新选择");
			return false;
		}
		
		
	}
		
	
});

$(function() {
	  // 初始化
	  StaGridincomeList.init();

});

