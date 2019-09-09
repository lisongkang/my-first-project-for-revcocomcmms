

BizRemRulecfgInputBasic = ({
	init : function() {
		$(".form-biz-remind-biz-rem-rulecfg-inputBasic").data("formOptions", {
	        bindEvents : function() {
	            var $form = $(this);
	        }
	    });
		var tritypeHidden = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #tritypeHidden");
		var trivaluesHidden = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #trivaluesHidden");
		var idHidden = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #idHidden");
		var numsHidden = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #numsHidden");
		var iscfmHidden = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #iscfmHidden");
		var elenHidden = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #elenHidden");
		
		if (idHidden.val() !=undefined && idHidden.val() != "" && idHidden.val() != null) {
			 var idText = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #id");
			 idText.val(idHidden.val());
			
			 // 初始化tritypeSelect信息
			 var tritypeSelect = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #tritype");
			 BizRemRulecfgInputBasic.initSelect('BIZ_TRITYPE',tritypeSelect,tritypeHidden.val()); //0-任务超市 1-状态变更
//			 tritypeSelect.attr("disabled","disabled");
			 
			 // 初始化iscfmSelect信息
			 var iscfmSelect = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #iscfm");
			 BizRemRulecfgInputBasic.initSelect('SYS_YESNO',iscfmSelect,iscfmHidden.val()); // 0-是 1-否
			 
			 var trivaluesText = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #trivalues");
			 trivaluesText.val(trivaluesHidden.val());
			 
			 var numsText = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #nums");
			 numsText.val(numsHidden.val());
			 
			 var elenText = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #elen");
			 elenText.val(elenHidden.val());
			 
		} else {
			 // 初始化tritypeSelect信息
			 var tritypeSelect = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #tritype");
			 BizRemRulecfgInputBasic.initSelect('BIZ_TRITYPE',tritypeSelect,"0"); //0-任务超市 1-状态变更
//			 tritypeSelect.attr("disabled","disabled");
			 
			 // 初始化iscfmSelect信息
			 var iscfmSelect = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #iscfm");
			 BizRemRulecfgInputBasic.initSelect('SYS_YESNO',iscfmSelect,"1"); // 0-是 1-否
			 
			 var numsInput = $(".form-biz-remind-biz-rem-rulecfg-inputBasic #nums");
			 numsInput.val("1"); // 默认为1
		}
	},
	
	initSelect : function(gcode,obj,value){
		obj.empty();
		var url = WEB_ROOT + "/prv-sysparam!selectParamList?gcode="+gcode;
		obj.ajaxJsonUrl(url, function(data) {
    		var option = '';
    		$.each(data, function(i, item) {
    			option = '<option value="'+item.mcode+'">'+item.display+'</option>';
    			obj.append(option);
            });
            obj.select2("val", value);
    	});
	}
	
});

$(function() {
	  // 初始化
	BizRemRulecfgInputBasic.init();
});
