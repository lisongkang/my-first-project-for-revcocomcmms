AssTargetTocitySelect = ({
	$searchForm:$(".form-biz-ass-target-selects"),
	$grid:$(".grid-biz-ass-target-ass-target-selects"),
	init : function (){
		
		this.initSearchParam();//初始化查询Form
		
		this.$grid.data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/target/ass-target-tocity!getNewTarget',
	        colModel : [{
	            label : 'id',
	            name : 'id',
	            sortable : false,
	            search :  false,
	            hidden:true,
	            key :true,
	            width : 50
	        },{
	            label : '指标归属',
	            name : 'cityName',
	            width : 100, 
	            editable: true,
	            search :  false,
	            align : 'left',
	            formatter:function(cellvalue, options, rowObject){
	            	return cellvalue=='*'?'广东省':cellvalue;
	            }	
	        },{
	            label : '指标名称',
	            name : 'name',
	            sortable : false,
	            search :  false,
	            width : 200
	        },{
	            label : '指标说明',
	            name : 'assContent',
	            width : 200,
	            editable: true,
	            search :  false,
	            align : 'left'
	        }],
	        gridComplete: function () {
			     Biz.hideTableElement(AssTargetTocitySelect.$grid,".btn-group-contexts");
			     Biz.hideTableElement(AssTargetTocitySelect.$grid,".ui-icon-arrowstop-1-w");
			     Biz.hideTableElement(AssTargetTocitySelect.$grid,".ui-icon-search");
			     Biz.hideTableElement(AssTargetTocitySelect.$grid,".ui-icon-disk");
			     Biz.hideTableElement(AssTargetTocitySelect.$grid,".ui-icon-cancel");
			     Biz.hideTableElement(AssTargetTocitySelect.$grid,"button");//隐藏新增按钮
			     Biz.hideTableElement(AssTargetTocitySelect.$grid,".ui-state-disabled");
			     Biz.hideTableElement(AssTargetTocitySelect.$grid,".ui-icon-trash");
			     
			     var gridBox = $("#gbox_"+ AssTargetTocitySelect.$grid.attr("id"));
				 $(gridBox).find(".ui-jqgrid-bdiv").css("height","200");
	        }
	    });

	},
	initSearchParam: function(){
		//初始化地市
		var $form = this.$searchForm;
		var $city = $form.find("#id_city");
		$city.empty();
		$city.append("<option value='"+Biz.LOGIN_INFO.city+"'>"+Biz.LOGIN_INFO.cityname+"</option>");
		$city.append("<option value='*'>广东省</option>");
		
		//初始化确定按钮
		this.initConfirm();
	},
	initConfirm:function(){
		var btnConfirm=$(".tocity-selects").find("#btn_confirm");
		var btnCancel=$(".tocity-selects").find("#btn_Cancel");
		
		btnConfirm.click(function(){
			
			var selectedIds = AssTargetTocitySelect.$grid.jqGrid("getGridParam", "selarrrow");
			if (selectedIds.length == 0) {
				Global.notify("error", "请先选择指标数据");
				return;
			}
			var url = WEB_ROOT + '/biz/ass/target/ass-target-tocity!doSave?ids='+selectedIds;
			$("body").ajaxJsonUrl(url, function(result) {
				if (result.type == "failure" ){
					Global.notify("error", "数据保存失败，请联系管理员！");
				} else {
					Global.notify("success", "获取指标数据成功");
					btnCancel.click();
					var $indexForm = $(".form-biz-ass-target-tocity");
					$indexForm.find('#btn_search').click();
				}
			});
		});
	},
	deleteStore : function(id) {
		var data={};
    	data.id = id;
    	
    	var $form = $(".form-biz-ass-index-store-index");
		var url = WEB_ROOT + '/biz/ass/store/ass-index-store!deleteStore';
		$("body").ajaxJsonUrl(url, function(result) {
			Global.notify("success", "考核指标删除成功");
			$form.find('#btn_search').click();
		},data);
	}

});


$(function() {
	AssTargetTocitySelect.init();
});
