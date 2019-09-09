
RoleInfoInputBasic = ({
	
	init : function (){
		$(".form-biz-prv-prv-roleinfo-inputBasic").data("formOptions", {
	        bindEvents : function() {
	            var $form = $(this);
	            var btn = $form.find(".blue");
	            btn.click(function() {
	            	
	            	var stime = $form.find("input[name='stime']").val();
	            	var etime = $form.find("input[name='etime']").val();
	            	
	            	var arr = stime.split("-");
	            	var startTime = new Date(arr[0], new Number(arr[1] - 1), arr[2]).getTime();
	            	arr = etime.split("-");
	            	var endTime = new Date(arr[0], new Number(arr[1] - 1), arr[2]).getTime();
	            	
	            	var curTime = new Date().getTime();
	            	if (endTime < curTime) {
	            		Global.notify("error", "到期时间不能小于当前日期");
	            		return false;
	            	}
	            	if (endTime < startTime) {
	            		Global.notify("error", "到期时间不能小于开始日期");
	            		return false;
	            	}
	            });
	            
	            var $city = $form.find("#id_city");
	            $city.change(function() {
	            	 RoleInfoInputBasic.initParam($city,"PRV_AREA_BY_CITY",$form.find("#id_areaid"));
	            });          
	           
	            if (Biz.LOGIN_INFO && $form.find("#id").val() == "") {
					// 新增数据默认选中操作员地市
					$city.select2("val", Biz.LOGIN_INFO.city);
					$($city).change();
				}
	            Biz.disableSelect2WhenNotAdmin($city);
	        }
	    });
	},

	initParam : function(elem, gcode, obj) {
		$(obj).prev().find(".select2-chosen").text("请选择...");
		obj.empty();

		var cityVal = elem.val();
		if (cityVal == null || cityVal == "") {
			return;
		}

		if (cityVal == "*") {
			option = '<option value=""></option>';
			obj.append(option);

			option = '<option value="*">全部</option>';
			obj.append(option);
			return;
		}

		var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode=" + gcode + "&mcode=" + elem.val();
		elem.ajaxJsonUrl(url, function(data) {
			var option = '';
			option = '<option value=""></option>';
			obj.append(option);

			var areas = "";
			$.each(data, function(i, item) {
				areas += item.mcode + ",";
			})

			if (areas != "") {
				areas = areas.substring(0, areas.length - 1);
				option = '<option value="' + areas + '">全部</option>';
				obj.append(option);
			}

			$.each(data, function(i, item) {
				option = '<option value="' + item.mcode + '">' + item.display + '</option>';
				obj.append(option);
			})
		});
	}
});


$(function() {
	RoleInfoInputBasic.init();
});



