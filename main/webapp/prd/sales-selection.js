$(function() {
	var form = $("#form-biz-prd-sales-select");
	var objtype = form.find("input[name='objtype']").val();
	var salesDiv = $("#div_biz-prd-sales-select");
    var modal = salesDiv.parent().parent().parent().find('.modal-title');
	if(objtype=='4'){//objtype=4 表示 商品小类//往标题头添加（“发布商品小类，即不需要在商品中发布非标准资费商品”）
		var html = "<div class='modal-title'><h4>选择商品小类</h4><span style='color:red;font-size=14px'>(发布商品小类，即不需要在商品中发布非标准资费商品)</span></div>";
	    modal.replaceWith(html);
	}else{
		var html = "<div class='modal-title'><h4>选择商品</h4></div>";
	    modal.replaceWith(html);
	}
		
	$("#form-biz-prd-sales-select").data("formOptions", {
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
	
    $(".grid-biz-prd-sales-index").data("gridOptions", {
        url : WEB_ROOT + '/prd/sales!findByPage',
        colModel : [ {
            label : '商品编码',
            name : 'salesCode',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '商品编号',
            name : 'id',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '商品名称',
            name : 'salesName',
            width : 200,
            editable: true,
            align : 'left'
        }, {
            label : '金额',
            name : 'sums',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '生效时间',
            name : 'stime',
            width : 100,
            formatter: 'date',
            editable: true,
            align : 'center'
        }, {
            label : '失效时间',
            name : 'etime',
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
