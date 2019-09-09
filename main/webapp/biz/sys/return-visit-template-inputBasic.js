$(function() {
	$(".form-biz-sys-sys-cust-rule-inputBasic").data("formOptions", {
        bindEvents : function() {
            var $form = $(this);
            var $city = $form.find("select[name='city']");
            var area = $form.find("select[name='areaid']");
            $city.change(function() {
            	var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + $city.val();
    	    	$form.ajaxJsonUrl(url, function(data) {
    	    		var options = {};
            		area.empty();
            		debugger
            		var option = '';
            		$.each(data, function(i, item) {
                        option = '<option value="'+item.id+'">'+item.name+'</option>';
                        area.append(option);
                    })
                    area.select2("val", "");
    	    	});
            });
            
            if (!$($city).val()) {
            	Biz.selectCityWhenAddData($city);
            	$($city).change();
			}
            Biz.disableSelect2WhenNotAdmin($city);
        }
    });
	
});