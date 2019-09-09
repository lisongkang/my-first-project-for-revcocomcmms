var StaAnalysisEchartBasic = {
	form : $(".form-sta-analysis-echartBasic"),
	init : function() {
		this.form.data("formOptions", {
			bindEvents : function() {
				var $form = $(this);
			}
		});
	  
      this.launch();//导入echarts
	}
	,
	launch : function () {
			$this = this;
		    require(
		        [
		            'echarts',
		            'echarts/chart/bar',
		            'echarts/chart/pie',
		            'echarts/chart/line'
		        ],function(ec){
		        	
		        	 echarts = ec;
		        	 var charts =  $this.form.find(".div_chart");
		        	 var url = WEB_ROOT + '/survey/sta-analysis!findEchartData?sid='+sid;
			      	 for(var i=0; i<charts.length; i++){
			      		  var id = charts.eq(i).attr("id");
			      		  var name = charts.eq(i).attr("name");//多选和单选属性有来配置不同的图表（饼图和）
			      		  var qid = id.split("_")[1];  //qid
			      		  
			      		  //获取统计数据
			      		$("body").ajaxJsonUrl(url+"&qid="+qid, function(data) {
			      			
			      			if(data.length>0){
				      			var qid = data[0].qid; //获取同步的qid；
				      			var divID = "chart_"+qid;
				      			var isonly =$(".form-sta-analysis-echartBasic").find("#"+divID).attr("name");
				      			
				      			if(isonly=="Y"){
					      			  $this.initPieChart(divID,echarts,data);//扇形图
					      		}else{
					      			  $this.initBarChart(divID,echarts,data);//多选是柱状图
					      		}
			      			}
			    		});
			      	 } 
		        }
		        
		    );
		},
		checkNull : function(objvalue){
			if(objvalue == undefined || objvalue == ""|| objvalue==null 
					||!objvalue){
				return true;
			}
			return false;
		},
		
	   initPieChart :function(id,echarts,data){
			
			//
			var echartData = [];
			for(var i = 0 ; i < data.length ; i++){
				var d = {value : data[i].anumber,name : data[i].acode};
				echartData.push(d);
			}
			var initDiv = StaAnalysisEchartBasic.form.find("#"+id)[0];
			var chart =   echarts.init(initDiv);
		
			option = {
					 tooltip: {
					        trigger: 'item',
					        formatter: "{a} <br/>{b}: {c} ({d}%)"
					    },
				    series : [
				        {
				            name: '数量',
				            type: 'pie',
				            radius: '55%',
				            data:echartData
				        }
				    ]
				};
			
		  chart.setOption(option);
	},
	initBarChart:function(id,echarts,data){
		
		var initDiv = StaAnalysisEchartBasic.form.find("#"+id)[0];
		var chart =   echarts.init(initDiv);
		
		var xAxisData = [];
		var echartData =[];
		for(var i = 0 ; i < data.length ; i++){
			xAxisData.push( data[i].acode);
			echartData.push(data[i].anumber);
		}
		
		var option = {
                tooltip: {},
                xAxis: {
                    data: xAxisData
                },
                yAxis: {},
                series: [{
                    name: '数量',
                    type: 'bar',
                    data: echartData
                }]
            };
		chart.setOption(option);
	}
};

$(function() {
	StaAnalysisEchartBasic.init();
});