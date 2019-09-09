TmpOpenLimit=({
	
	init : function(){
		 var $form = $(".form-biz-tmpopenlimit-tmp-open-limit-selectopen");
		 var objType = $('#objType', parent.document).val();
		 var objId = $('#objId', parent.document).val();
		$(".grid-biz-tmpopenlimit-tmp-open-limit-selectopen").data("gridOptions", {
	        url : WEB_ROOT + '/tmpopenlimit/biz-tmp-open-limit!findTmpOpen?objType='+objType+'&objId='+objId,
	        colModel : [ {
	            label : '方案编号',
	            name : 'planid',
	            hidden : true
	        }, {
	            label : '方案名称',
	            width : 955,
	            name : 'name'
	        }, {
	            label : '业务区id',
	            name : 'areaid',
	            hidden : true
	        }],
	        filterToolbar : false,
	        rowNum : 100,
	        multiselect : false,
	        toppager : false,
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
		
		// 用户设备的change事件
		$(".form-biz-tmpopenlimit-tmp-open-limit-selectopen #city").unbind().bind("change", function() {
			TmpOpenLimit.initTown(".grid-biz-tmpopenlimit-tmp-open-limit-selectopen #areaid",'');
			return true;
		});
		
		// 初始化city信息
		var citySelect = $(".form-biz-tmpopenlimit-tmp-open-limit-selectopen #city");
		Biz.initSelect('PRV_CITY',citySelect,Biz.LOGIN_INFO.city);
		
		// 高权限支持点击查询部门
		if (Biz.LOGIN_INFO.rolelevel != '9'){
			citySelect.attr("disabled","disabled");
		}
		
		TmpOpenLimit.initTown(".form-biz-tmpopenlimit-tmp-open-limit-selectopen #areaid",Biz.LOGIN_INFO.city);
		
	},
	

	initTown : function(selector, incity) {
		$(selector + " option").remove();
		
		var city = incity;
		if (city == ''){
			// 获取到选中的用户设备，其value值是serv对象json串
			city = $(".form-biz-usernew-user-new-resaddr #city").val();
		}
		
		var areaidSelector = $(selector);
		
		Biz.initSelectParam2('PRV_AREA_BY_CITY',areaidSelector,city);
	}

});


$(function() {
	TmpOpenLimit.init();
	
});