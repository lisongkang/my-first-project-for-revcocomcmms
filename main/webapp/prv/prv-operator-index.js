var GridAuthUserIndex = {
	fullediturl : WEB_ROOT + "/prv/prv-operator!inputTabs",
	init : function() {
		$(".grid-auth-user-index").data("gridOptions", {
	        url : WEB_ROOT + "/prv/prv-operator!findPageOpersByCity",
	        colModel : [ {
	            label : '登录账号',
	            name : 'loginname',
	            editable : true,
	            editoptions : {
	                updatable : false
	            },
	            width : 120
	        }, {
	            label : '名称',
	            name : 'name',
	            editable : true,
	            width : 120
	        }, {
	            label : '状态',
	            name : 'status',
	            width : 65,
	            editable: true,
	            stype : 'select',
	            editoptions : {
	                value : Biz.getPrvParamListDatas("PRV_USE_STATUS")
	            },
	            align : 'left'
	        }, {
	            label : '启用时间',
	            name : 'stime',
	            width : 150,
	            formatter: 'date',
	            editable: true,
	            align : 'center'
	        }, {
	            label : '结束时间',
	            name : 'etime',
	            width : 150,
	            formatter: 'date',
	            editable: true,
	            align : 'center'
	        } ],
	        editcol : 'loginname',
	        inlineNav : {
	            add : false
	        },
	       // editurl : WEB_ROOT + "/prv/prv-operator!doSave",
	       // delurl : WEB_ROOT + "/prv/prv-operator!doDelete",
	        fullediturl : GridAuthUserIndex.fullediturl,
	        gridComplete:function(){
		         //移除搜索按钮
		     	var obj = $(".grid-auth-user-index");
		     	Biz.hideSearchBtn(obj);
		     	
		     	//隐藏添加按钮
		     	GridAuthUserIndex.hideAddButton(".div_grid-auth-user-index");
		     }
	    });
	},
	//隐藏添加按钮
	hideAddButton:function(parentDiv){
		if(parentDiv){
			var parentObj = $(parentDiv);
			var options = parentObj.find(".jqgrid-options");
			for(var i= 0 ; i < options.length;i++){
				var option = options[i];
				$(option).hide();
			}
		}
	}
};

$(function() {
	GridAuthUserIndex.init();
	
});