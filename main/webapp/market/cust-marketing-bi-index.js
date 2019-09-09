$(function() {
    $(".grid-biz-market-cust-marketing-bi-index").data("gridOptions", {
        url : WEB_ROOT + '/market/cust-marketing-bi!findByPage',
        colModel : [ {
            label : '产品名称',
            name : 'pname',
            width : 150,
            editable: true,
            align : 'left'
        }, {
            label : '客户名称',
            name : 'name',
            width : 70,
            editable: true,
            align : 'left'
        }, {
            label : '业务区',
            name : 'area.name',
            width : 90,
            editable: true,
            align : 'right'
        }, {
            label : '所属片区',
            name : 'ptach.patchname',
            width : 90,
            editable: true,
            align : 'right'
        }, {
            label : '处理结果',
            name : 'dealstatus',
            width : 70,
            editable: true,
            stype : 'select',
            editoptions : {
            	value : {'0':'未处理','1':'已处理'}
            },
            align : 'left'
        }, {
            label : '录入日期',
            name : 'appdate',
            width : 150,
            formatter: 'date',
            editable: true,
            align : 'center'
        }, {
            label : '客户地址',
            name : 'whladdr',
            width : 200,
            editable: true,
            align : 'left'
        } ],
        filterToolbar : false,
        operations : function(items) {
    		var $grid = $(this);
    		var $push = $('<li data-position="multi" data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa fa-trash-o"></i> 客户推送</a></li>');
    		
    		$push.children("a").bind(
                    "click",
                    function(e) {
                    	$(this).popupDialog({
                    		url : WEB_ROOT + '/market/cust-marketing-bi!push?ids='+$grid.getAtLeastOneSelectedItem().join(","),
        	                id : 'cust_push',
                    		title : '客户推送',
                    		callback : function(data) {
                    			if (data.type == "success") {
                    				$grid.refresh();
                    			}
                    		}
                    	});
                    });
            items.push($push);
    	}
    });
    
    $("#form-marketing-bi-index").data("formOptions", {
		initArea : function() {
			var $form = $(this);
			var area = $form.find("#areaid");
			var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + Biz.LOGIN_INFO.city;
	    	$form.ajaxJsonUrl(url, function(data) {
	    		var options = {};
        		area.empty();
        		
        		var option = '';
        		area.append('<option value="">请选择业务区</option>');
        		$.each(data, function(i, item) {
                    option = '<option value="'+item.id+'">'+item.name+'</option>';
                    area.append(option);
                })
                
                area.select2("val", "");
	    	});
		},
        bindEvents : function() {
            var $form = $(this);
            $form.data("formOptions").initArea.call($form);
        }
    });
});
