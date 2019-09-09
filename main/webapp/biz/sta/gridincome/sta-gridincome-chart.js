

StaGridincomeChart = ({
	pageMainDiv : $(".div-biz-sta-gridincome-sta-gridincome-chart"),
	
	//echart变量
	myChart : null,
	domMain : null,
	option : null,
	curTheme : 'macarons',//{},
	
	
	
	init : function() {
		$(".form-biz-sta-gridincome-sta-gridincome-chart").data("formOptions", {
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
		
		
		// 其它初始化
		
		StaGridincomeChart.domMain = StaGridincomeChart.pageMainDiv.find('#id_echart_main')[0];

		StaGridincomeChart.option = {
			//grid: {
			//     y:'30%'
			//},
			// 标题  
            title : { 
            	text: "网格营收统计",
            	subtext: '按日统计网格营收数据',
                x : 'left',  
                y : 'top'//,  
//                textStyle : {  
//                    fontSize : 26,  
//                    fontFamily : "微软雅黑",  
//                }  
            }, 
			tooltip : {
				show : true
			},
			legend : {
				orient: 'horizontal', // 'horizontal'|'vertical'
		        x: '150', // 'center' | 'left' | {number},
		        y: 'top', // 'center' | 'bottom' | {number}
		        //backgroundColor: '#eee',
		        //borderColor: 'rgba(178,34,34,0.8)',
		        //borderWidth: 4,
		        padding: 5,    // [5, 10, 15, 20]
		        itemGap: 20,
		        textStyle: {color: 'red'},
				data : [ /*
						'测试网格1','测试网格3','测试网格5','测试网格2'
				         ,'测试网格11','测试网格31','测试网格51','测试网格21'
				         ,'测试网格12','测试网格32','测试网格52','测试网格22'
				         //,'','测试网格13','测试网格33'
				         */
				         ]
			},
		    toolbox: {
		        show : true,
		        orient: 'vertical', // 'horizontal'|'vertical'
		        feature : {
		            mark : {show: true},		            
		            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    dataZoom : {
		        show : true,
		        realtime : true,
		        //orient: 'vertical',   // 'horizontal'
		        //x: 0,
		        //y: 36,
		        //width: 400,
		        height: 20,
		        //backgroundColor: 'rgba(221,160,221,0.5)',
		        //dataBackgroundColor: 'rgba(138,43,226,0.5)',
		        //fillerColor: 'rgba(38,143,26,0.6)',
		        //handleColor: 'rgba(128,43,16,0.8)',
		        //xAxisIndex:[],
		        //yAxisIndex:[],
		        start : 10,
		        end : 90
		    },
			xAxis : [ {
				type : 'category',
				data : [ 
				         /*
				         "周日", "周一", "周二", "周三", "周四", "周五", "周六" 
				         */
				         ]
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [
			          
			/*{        
				"name" : "测试网格1",
				"type" : "bar",
				"data" : [ 25,5, 20, 40, 10, 10, 20 ]
			},
			{
				"name" : "测试网格3",
				"type" : "bar",
				"data" : [ 5,2, 13, 52, 45, 12, 53 ]
			},
			{
				"name" : "测试网格5",
				"type" : "bar",
				"data" : [ 15,33, 24, 41, 7, 5, 23 ]
			},
			{
				"name" : "测试网格2",
				"type" : "bar",
				"data" : [ 11,6, 19, 0, 28, 0, 23 ]
			}*/
			]
		};
		
	},
	
	
    changeStatype : function(){
    	//--未完成,给元素重新绑定时间选择控件未成功。失败，先放一放
    	var $form = $(".form-biz-sta-gridincome-sta-gridincome-chart")
    	var statype =$form.find("#id_que_statype").val();
    	
    	$form.find("#id_que_statime").remove();
    	
    	statimeHtml="<input type='text' id='id_que_statime' name='statime' class='form-control input-medium' placeholder='统计时段'>";
    	
    	$form.find("#id_que_statime_div").append(statimeHtml);
    	
    	StaGridincomeChart.initDateRangePicker($form.find("#id_que_statime"),statype);
    },
	
	//时间范围选择框
	initDateRangePicker : function($selector,type){
		var DateRangePickeData = {};
		
		if(type=="3"){
			//按天统计
			DateRangePickeData.format = "YYYY",
			DateRangePickeData.ranges = {

				"最近三年" : [ moment().subtract("days", 89), moment() ],
				"本年" : [ moment().startOf("month"), moment().endOf("month") ],
				"上年" : [ moment().subtract("month", 1).startOf("month"),
						moment().subtract("month", 1).endOf("month") ],
				"未来一年" : [ moment().subtract("month", -1).startOf("month"),
							moment().subtract("month", -1).endOf("month") ],
			}
		} else if(type='2'){
			DateRangePickeData.format = "YYYY-MM",
			DateRangePickeData.ranges = {
				
				"最近一季度" : [ moment().subtract("month", 5).startOf("month"), moment().endOf("month") ],
				"本月" : [ moment().startOf("month"), moment().endOf("month") ],
				"上月" : [ moment().subtract("month", 1).startOf("month"),
						moment().subtract("month", 1).endOf("month") ],
				"未来一月" : [ moment().subtract("month", -1).startOf("month"),
							moment().subtract("month", -1).endOf("month") ]
				
			}
		} else {
			DateRangePickeData.format = "YYYY-MM-DD",
			DateRangePickeData.ranges = {
				"今天" : [ moment(), moment() ],
				"昨天" : [ moment().subtract("days", 1),
						moment().subtract("days", 1) ],
				"最近一周" : [ moment().subtract("days", 6), moment() ],
				"未来一周" : [ moment(), moment().add("days", 6) ],
				"最近一月" : [ moment().subtract("days", 29), moment() ],
				"未来一月" : [ moment(), moment().add("days", 29) ],
				"最近一季度" : [ moment().subtract("days", 89), moment() ],
				"本月" : [ moment().startOf("month"), moment().endOf("month") ],
				"上月" : [ moment().subtract("month", 1).startOf("month"),
						moment().subtract("month", 1).endOf("month") ]
			}
		}
		
		$selector.daterangepicker({
			
			opens : (App.isRTL() ? "left" : "right"),
			startDate : moment().subtract("days", 29),
			endDate : moment(),
			dateLimit : {
				days : 365
			},
			showDropdowns : true,
			showWeekNumbers : true,
			timePicker : false,
			timePickerIncrement : 1,
			timePicker12Hour : true,
			
			buttonClasses : [ "btn" ],
			applyClass : "green",
			cancelClass : "default",
			
			separator : " ～ ",
			locale : {
				applyLabel : "确定",
				fromLabel : "从",
				toLabel : "到",
				customRangeLabel : "自由选取",
				daysOfWeek : [ "日", "一", "二", "三", "四", "五", "六" ],
				monthNames : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月",
						"8月", "9月", "10月", "11月", "12月" ],
				firstDay : 1
			},
		
			format : DateRangePickeData.format,
			ranges : DateRangePickeData.ranges
			
		});
	},
	
	
	
	launch : function () {
		
	    require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	        StaGridincomeChart.requireCallback
	    );
	},
		
	requireCallback : function(ec, defaultTheme) {
	    //curTheme = themeSelector ? defaultTheme : {};
	    echarts = ec;
	    
	    StaGridincomeChart.drawChart();
	    
	    window.onresize = StaGridincomeChart.myChart.resize;
	},
		
	drawChart : function(){
		
	    if (StaGridincomeChart.myChart && StaGridincomeChart.myChart.dispose) {
	    	StaGridincomeChart.myChart.dispose();
	    }
	    StaGridincomeChart.myChart = echarts.init(StaGridincomeChart.domMain, StaGridincomeChart.curTheme);
	    window.onresize = StaGridincomeChart.myChart.resize;
	    
	    StaGridincomeChart.myChart.setOption(StaGridincomeChart.option, true);
	},
	
	
	
	doRefresh : function(){
		StaGridincomeChart.myChart.showLoading();
        setTimeout(StaGridincomeChart.drawChart, 500);
        
	},
	
	
	
	
	//业务方法
	doReset : function(){
		var $form = StaGridincomeChart.pageMainDiv;
		var $queform=$form.find(".form-biz-sta-gridincome-sta-gridincome-chart");
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
	doQuery : function(){
		var form = StaGridincomeChart.pageMainDiv;
		var city = form.find("#id_que_city").val();
		//格式:数组
		var gridids = form.find("#id_que_grid").val();
		//格式:stime ~ etime
		var statimestr = form.find("#id_que_statime").val();
		
		//检查参数合法性
		if(StaGridincomeChart.isEmpty(city)){
			Global.notify("info", "请选择地市");
			return false;
		}
		if(StaGridincomeChart.isEmpty(gridids)){
			Global.notify("info", "请选择网格");
			return false;
		}
		if(StaGridincomeChart.isEmpty(statimestr)){
			Global.notify("info", "请选择统计时段");
			return false;
		}
		
		var statimeArray = statimestr.split('～');
		if(statimeArray.length!=2 || StaGridincomeChart.isEmpty(statimeArray[1].trim())){
			Global.notify("info", "择统计时段有误，请重新选择");
			return false;
		}
		
		//拼装请求参数
		var staGridincomeParamBo = {};
		staGridincomeParamBo.city = city;
		staGridincomeParamBo.gridids = gridids;
		staGridincomeParamBo.stime = statimeArray[0].trim();
		staGridincomeParamBo.etime = statimeArray[1].trim();
		//staGridincomeParamBo.statype= statype;//按天，按月，按年
		
		var data = {};
		data.staGridincomeParamBo = JSON.stringify(staGridincomeParamBo);
		
		//提交请求
		var url = WEB_ROOT + '/biz/sta/gridincome/sta-gridincome!queGridIncome';
		$("body").ajaxJsonUrl(url, function(retData) {
            //Global.notify("success", "处理成功");
			
			var newOption = StaGridincomeChart.myChart.getOption(); // 深拷贝  
			
			newOption.legend.data = retData.legendData
			newOption.xAxis[0].data = retData.xAxisData;  
			newOption.series = retData.seriesData;  
			StaGridincomeChart.option= newOption;
			StaGridincomeChart.myChart.setOption(newOption,true); // 第二个参数表示不和原先的option合并 
			
			StaGridincomeChart.doRefresh();
		},data,"post");
		
	}
		
	
});



$(function() {
	
	  // 初始化
	  StaGridincomeChart.init();
	  //StaGridincomeChart.initDateRangePicker(".form-biz-sta-gridincome-sta-gridincome-chart #id_que_statime",'2');
	  StaGridincomeChart.launch();

	
});



