
AssIndexMonprogressSumIndex = ({
	
	init : function() {
	    $(".grid-biz-ass-monstat-ass-index-monprogress-sumindex").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/monstat/ass-index-monprogress!sumFindByPage',
	        colModel : [ {
	            label : '查询月份',
	            name : 'tmonth',
	            width : 60,
	            editable: true,
	            align : 'left',
	            sortable : false,
	            search : false
	        }, {
	            label : '考核ID',
	            name : 'assid',
	            width : 60,
	            editable: true,
	            align : 'right',
	            hidden : true 
	        },  {
	            label : '考核内容',
	            name : 'asscontent',
	            width : 120,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false
	        },{
	            label : '网格路径',
	            name : 'firstGridName',
	            width : 120,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false
	        },{
	            label : '二级网格',
	            name : 'secondGridName',
	            width : 60,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false,
	            hidden : true 
	        },{
	            label : '统计网格',
	            name : 'pgridid',
	            width : 100,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false,
	            hidden : true 
	        },{
	            label : '统计网格',
	            name : 'pgrididname',
	            width : 100,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false
	        },{
	            label : '任务总数',
	            name : 'total',
	            width : 70,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false
	        }, {
	            label : '累计完成数',
	            name : 'comnum',
	            width : 70,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false
	        }, {
	            label : '当月完成数',
	            name : 'curnum',
	            width : 70,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false
	        } ]
	    });
		
		// 其它初始化
	    AssIndexMonprogressSumIndex.initPatchids("");//清空片区
	},
	
	initDataFromTree : function(secondGridids) {
		var grids = "";
		for (var i = 0; i < secondGridids.length; i++) {
			grids += secondGridids[i] + ",";
		}
		
		if (grids.length > 0) {
			grids = grids.substring(0, grids.length - 1);
		}
		
		var $form = $(".form-biz-ass-monstat-ass-index-monprogress-sumindex");
		$form.find("#id_second_gridid").val(grids);
		
		AssIndexMonprogressSumIndex.initPatchids(secondGridids);
	},
	
	initPatchids : function(secondGridids) {
		var $form = $(".form-biz-ass-monstat-ass-index-monprogress-sumindex");
		var id_third_patchid = $form.find("#id_third_patchid");
		
    	var storeData={};
    	storeData.secondGridids = secondGridids;
    	storeData.assIndexMonprogress = JSON.stringify(storeData);
    	
		var url = WEB_ROOT + '/biz/ass/monstat/ass-index-monprogress!getThirdPatchidBySecondGridid';
		$("body").ajaxJsonUrl(url, function(data) {
			id_third_patchid.empty();
    		id_third_patchid.select2({placeholder: "请选择片区"});
    		id_third_patchid.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.patchid+'">'+item.patchname+'</option>';
    			id_third_patchid.append(option);
                
            });
		},storeData);
		
	},
	
	initDatePicker : function(selector){
		$(selector + ' #_startMonth').datetimepicker({
	        format: 'yyyymm',  
	         weekStart: 1,  
	         autoclose: true,  
	         startView: 3,  
	         minView: 3,  
	         forceParse: false,  
	         language: 'zh-CN'  
	    });
		
		$(selector + ' #_endMonth').datetimepicker({  
	        format: 'yyyymm',  
	         weekStart: 1,  
	         autoclose: true,  
	         startView: 3,  
	         minView: 3,  
	         forceParse: false,  
	         language: 'zh-CN'  
	    });
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
	}
	
});

$(function() {
	  // 初始化
	  AssIndexMonprogressSumIndex.init();
	  AssIndexMonprogressSumIndex.initDatePicker('.form-biz-ass-monstat-ass-index-monprogress-sumindex');
});


