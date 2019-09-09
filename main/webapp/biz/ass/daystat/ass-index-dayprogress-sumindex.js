
AssIndexDayprogressSumIndex = ({
	
	init : function() {
		
	    $(".grid-biz-ass-daystat-ass-index-dayprogress-sumindex").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/daystat/ass-index-dayprogress!sumFindByPage',
	        colModel : [ {
	            label : '统计日期',
	            name : 'tdate',
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
	            name : 'curnum',
	            width : 70,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false
	        }, {
	            label : '完成率',
	            name : 'rate',
	            width : 70,
	            editable: true,
	            align : 'right',
	            hidden : true  
	        }, {
	            label : '完成率',
	            name : 'ratename',
	            width : 70,
	            editable: true,
	            align : 'right',
	            sortable : false,
	            search : false
	        } ]
	    });

		
		// 其它初始化
	    AssIndexDayprogressSumIndex.initPatchids("");//清空片区
	},
	
	initDataFromTree : function(secondGridids) {
		var grids = "";
		for (var i = 0; i < secondGridids.length; i++) {
			grids += secondGridids[i] + ",";
		}
		
		if (grids.length > 0) {
			grids = grids.substring(0, grids.length - 1);
		}
		
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-sumindex");
		$form.find("#id_second_gridid").val(grids);
		
		AssIndexDayprogressSumIndex.initPatchids(secondGridids);
	},
	
	initPatchids : function(secondGridids) {
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-sumindex");
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
	isEmpty : function(objvalue){
		if(!objvalue || objvalue == "" ){
			return true;
		}
		return false;
	},
	checkInput : function(){
		if (Biz.isHighlvlOperaotr()){
			return true;
		}
        	
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-sumindex");
		var id_first_gridid = $form.find("#id_first_gridid").val();
		
		//检查参数合法性
		if(AssIndexDayprogressSumIndex.isEmpty(id_first_gridid)){
			Global.notify("info", "请选择营维中心");
			return false;
		}
	}
	
});


$(function() {
	  // 初始化
	  AssIndexDayprogressSumIndex.init();
});
