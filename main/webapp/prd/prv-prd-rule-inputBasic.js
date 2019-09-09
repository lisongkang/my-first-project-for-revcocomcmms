PrvPrdRuleInputBasic = ({
	init : function() {
		// 事件绑定
		$(".form-prv-prd-rule-inputBasic").data("formOptions",{
			bindEvents: function(){
				var $form=$(this);
				$form.find("button[name='btn_submit']").click(function(){
					return PrvPrdRuleInputBasic.doPrvPrdRuleNew('.form-prv-prd-rule-inputBasic');
				});
			}
		});

	},

	selectProductTabs : function(selector) {
		$form = $(selector);
		var objtype = $form.find("#objtype").val();

		if (objtype == null || objtype == '') {
			alert("请先选择推荐对象类型！");
			return;
		}
    	var url = "";
    	var title = "";
		var id = "";
		if (objtype == '0') {
			title = '选择商品';
    		url = WEB_ROOT + '/prd/sales-selection-for-jump.jsp';
			id = "know-select-sales";
    	} else if (objtype == '1'){
    		title = '选择促销优惠';
    		url = WEB_ROOT + '/prd/salespkg-selection-for-jump.jsp';
			id = "know-select-salespkg";
    	}
    	$(this).popupDialog({
            url : url,
            title : title,
			id : id,
            callback : function(rowdata) {
            	console.log(rowdata);
            	if (objtype == '0') {
            		$form.find("#value").val(rowdata.salesCode);
            		$form.find("#valuename").val(rowdata.display);
    			} else {
            		$form.find("#value").val(rowdata.salespkgcode);
            		$form.find("#valuename").val(rowdata.display);
    			}
            }
        });
		
	},

	selectMenuTabs : function(selector) {
		$form = $(selector);
    	$(this).popupDialog({
            url : WEB_ROOT + '/prv/prv-menudef-for-jump.jsp',
            title : '选择跳转菜单',
			id : 'menu_select',
            callback : function(rowdata) {
            	console.log(rowdata);
        		$form.find("#jumpmenuid").val(rowdata.menuid);
        		$form.find("#jumpmenuname").val(rowdata.name);
            }
        });
		
	},
	
	doPrvPrdRuleNew : function(selector) {

		$form = $(selector);
		if (Biz.checkNull($form.find('#areaid').val())) {
			Global.notify("error", "请选择业务区!");
			return false;
		}
		if (Biz.checkNull($form.find('#objtype').val())) {
			Global.notify("error", "请选择推荐对象类型!");
			return false;
		}

		if (Biz.checkNull($form.find('#value').val())
				|| Biz.checkNull($form.find('#valuename').val())) {
			Global.notify("error", "请选择推荐对象值!");
			return false;
		}
		if (Biz.checkNull($form.find('#jumpmenuid').val())
				|| Biz.checkNull($form.find('#jumpmenuname').val())) {
			Global.notify("error", "请选择跳转菜单!");
			return false;
		}
		if (Biz.checkNull($form.find('#exptime').val())) {
			Global.notify("error", "请选择规则失效时间!");
			return false;
		}
		if (Biz.checkNull($form.find('#message').val())) {
			Global.notify("error", "请输入提示语!");
			return false;
		}
	},
});

$(function() {
	$("#dates_div").hide();
	$(".form-channel-biz-tmpopenlimit-biz-tmp-open-limit-inputBasic").data(
			"formOptions", {
				bindEvents : function() {
					var $form = $(this);
				}
			});
	PrvPrdRuleInputBasic.init();
});