
AssIndexDayprogressIndex = ({
	
	init : function() {
		
	    $(".grid-biz-ass-daystat-ass-index-dayprogress-index").data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/daystat/ass-index-dayprogress!findByPage',
	        colModel : [ {
	            label : '流水号',
	            name : 'id',
	            hidden : true                          
	        }, {
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
	            width : 120,
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
	            label : '统计对象',
	            name : 'objtype',
	            width : 60,
	            editable: true,
	            align : 'left',
	            hidden : true 
	        }, {
	            label : '对象ID',
	            name : 'objid',
	            width : 60,
	            editable: true,
	            align : 'right',
	            hidden : true  
	        }, {
	            label : '片区',
	            name : 'objname',
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
	        //editurl : WEB_ROOT + '/biz/ass/daystat/ass-index-dayprogress!doSave',
	        //delurl : WEB_ROOT + '/biz/ass/daystat/ass-index-dayprogress!doDelete',
	        //fullediturl : WEB_ROOT + '/biz/ass/daystat/ass-index-dayprogress!inputTabs'
	    });

		
		// 其它初始化
//	    AssIndexDayprogressIndex.initGrids(""); //清空网格
	    AssIndexDayprogressIndex.initPatchids("");//清空片区
	},
	
	changeFirstGridid : function() {
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-index");
		var firstGridid = $form.find("#id_first_gridid").val();
		
		AssIndexDayprogressIndex.initGrids(firstGridid);
	},
	
	initGrids : function(firstGridid) {
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-index");
		var id_second_gridid = $form.find("#id_second_gridid");
		
		var url = WEB_ROOT + "/biz/ass/monstat/ass-index-monprogress!getSecondGrididByfirstGridid?firstGridid="+firstGridid;
    	$form.ajaxJsonUrl(url, function(data) {
    		id_second_gridid.empty();
    		id_second_gridid.select2({placeholder: "请选择网格"});
    		id_second_gridid.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.id+'">'+item.gridname+'</option>';
    			id_second_gridid.append(option);
            });
    	});
	},
	
	changeSecondGridid : function() {
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-index");
		//格式:数组
		var secondGridids = $form.find("#id_second_gridid").val();
		AssIndexDayprogressIndex.initPatchids(secondGridids);
	},
	
	initDataFromTree : function(secondGridids) {
		var grids = "";
		for (var i = 0; i < secondGridids.length; i++) {
			grids += secondGridids[i] + ",";
		}
		
		if (grids.length > 0) {
			grids = grids.substring(0, grids.length - 1);
		}
		
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-index");
		$form.find("#id_second_gridid").val(grids);
		
		AssIndexDayprogressIndex.initPatchids(secondGridids);
	},
	
	initPatchids : function(secondGridids) {
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-index");
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
        	
		var $form = $(".form-biz-ass-daystat-ass-index-dayprogress-index");
		var id_first_gridid = $form.find("#id_first_gridid").val();
		
		//检查参数合法性
		if(AssIndexDayprogressIndex.isEmpty(id_first_gridid)){
			Global.notify("info", "请选择营维中心");
			return false;
		}
	}
	
});


$(function() {
	  // 初始化
	  AssIndexDayprogressIndex.init();
});
