$(function() {
	$("#form-biz-prd-salespkg-select").data("formOptions", {
		initArea : function(city) {
			var $form = $(this);
			var area = $form.find("#areaid");
			if (!city) {
				area.empty();
				area.select2("val", "");
				return;
			}
	    	var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + city;
	    	$form.ajaxJsonUrl(url, function(data) {
	    		var options = {};
        		area.empty();
        		
        		var option = '';
        		$.each(data, function(i, item) {
                    option = '<option value="'+item.id+'">'+item.name+'</option>';
                    area.append(option);
                })
                area.select2("val", "");
	    	});
		},
        bindEvents : function() {
            var $form = $(this);
            var $city = $form.find("select[name='city']");
            $city.change(function() {
            	$form.data("formOptions").initArea.call($form, $city.val());
            });
            
            if (!$form.find("select[name='city']").val()) {
            	if (Biz.LOGIN_INFO) {
            		$form.find("select[name='city']").select2("val", Biz.LOGIN_INFO.city);
            		$form.data("formOptions").initArea.call($form, Biz.LOGIN_INFO.city);
            	}
            }
            
            Biz.disableSelect2WhenNotAdmin($city);
        }
    });
	
    $(".grid-biz-prd-salespkg-index").data("gridOptions", {
        url : WEB_ROOT + '/prd/salespkg!findByPage',
        colModel : [ {
            label : '营销方案编码',
            name : 'salespkgcode',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '营销方案名称',
            name : 'salespkgname',
            width : 200,
            editable: true,
            align : 'left'
        }, {
            label : '产品类型',
            name : 'sclass',
            width : 100,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("PRD_PCLASS")
            },
            align : 'left'
        }, {
            label : '产品子类型',
            name : 'ssubclass',
            width : 100,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("PRD_PSUBCLASS")
            },
            align : 'left'
        },{
            label : '生效时间',
            name : 'sdate',
            width : 100,
            formatter: 'date',
            editable: true,
            align : 'center'
        }, {
            label : '失效时间',
            name : 'edate',
            width : 100,
            formatter: 'date',
            editable: true,
            align : 'center'
        }, {
            label : '状态',
            name : 'status',
            width : 70,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("PRODSTATUS")
            },
            align : 'left'
        }, {
            name : 'display',
            hidden : true,
            hidedlg : true,
            editable: true
        } ],
        rowNum : 10,
        multiselect : false,
        toppager : false,
        filterToolbar : false,
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
});
