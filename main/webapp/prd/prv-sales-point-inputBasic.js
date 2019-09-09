var PrvSalesPointInputBasic = {
	form : $(".form-biz-prd-prv-sales-point-inputBasic"),
	params:{},
	init : function(){
	 $(".form-biz-prd-prv-sales-point-inputBasic").data("formOptions", {
	        bindEvents : function() {
	            var $form = $(this);
	            $form.find(".fa-select-obj").click(function(){
	            	var title = "选择商品";
	            	var url = WEB_ROOT + '/prd/sales!forward?_to_=selection';
	            	id = "know-select-sales";
	            	$(this).popupDialog({
		                url : url,
		                title : title,
						id : id,
		                callback : function(rowdata) {
		        				$form.find("input[name='salesname']").val(rowdata.display);
		        				$form.find("input[name='salesid']").val(rowdata.id);
		                }
		            })
	            })
	            
	            //判断
	            $form.find(".btn_salespointnew").click(function(){
	            	PrvSalesPointInputBasic.doSalesPointNew(".form-biz-prd-prv-sales-point-inputBasic");
	            })
	        }
	    });
	    var $this = this;
	   //绑定关联事件
	  /*this.form.find("#id_city").bind("change",function(e){
			$this.initArea($(this).val());
		});*/
		this.initCity();
		this.initOpCode();
	},
	initCity:function(){
		
		var $form = this.form;
		var city = $form.find("#id_city");
		var loginCity = Biz.LOGIN_INFO.city;
		if(Biz.isCurrentAdmin()){
			var citySelectGcode = "PRV_CITY";
			Biz.initSelect(citySelectGcode, city,loginCity,function(){
			//	city.change();
			});
		}else{
			city.append('<option value=""></option>');
			var option  = '<option value="' + Biz.LOGIN_INFO.city + '"'
			               + ' selected="selected"' + '>'
			               + Biz.LOGIN_INFO.cityname + '</option>';
			city.append(option);
			city.attr("disabled",true);
		//	city.change();
		}
	},
	initArea : function(selectCity){
		
		var $form = this.form;
		var rolelevel = Biz.LOGIN_INFO.rolelevel;
		var area = $form.find("#id_area");
		if(rolelevel != 9){
			 area.append('<option value=""></option>');
	 		 var option  = '<option value="' + Biz.LOGIN_INFO.areaid + '"'
							+ ' selected="selected"' + '>'
							+ Biz.LOGIN_INFO.areaname + '</option>';
			 area.append(option);
			$(area).attr("disabled", true);
		}else{
			areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + selectCity;
			Biz.initSelect(areaSelectGcode,area,Biz.LOGIN_INFO.areaid);
		}
	},
	initOpCode : function() {
		Biz.initSelect("BIZ_OPCODE", this.form.find("#id_opcode"));
	},
	doSalesPointNew : function(selector){
		$form = $(selector);
		
		$this = this;
		if(!PrvSalesPointInputBasic.checkRequired($form)){
			return false;
		}
	    var point = $form.find("#id_point").val();
		if(!PrvSalesPointInputBasic.validateNum(point)){
			Global.notify("error", "销售积分为数字");
			return false;
		}
		
		if(point.length > 3){
			Global.notify("error", "销售积分过大");
			return false;
		}
		var url  = $form.attr("action");
		var method = $form.attr("method");
		//var params = {};
		$this.params.city = $form.find("#id_city").val();
	//	params.areaids = $form.find("#id_area").val();
		$this.params.opcode = $form.find("#id_opcode").val();
		$this.params.wtype = $form.find("#id_wtype").val();
		$this.params.salesid = $form.find("#id_salesid").val();
		$this.params.points = $form.find("#id_point").val();
		var checkurl = WEB_ROOT + '/prd/prv-sales-point!checkSalePoint';
		$("body").ajaxJsonUrl(checkurl,function(result){
			if(result.type == "success"){
				
				if(result.message == "id"){
					var model = $(".div-biz-prd-prv-sales-point-inputBasic").find("#showTipModel");
					var $showTipContent = $(model).find(".modal-body");
					var data = result.userdata;
					var wtypename =( data.wtype=='0'?'正式工':'派遣工');
					var tipsalename = PrvSalesPointInputBasic.checkNull(data.salesname)?"":"、"+data.salesname;
					var text = data.cityname + "已存在有效状态的" +data.opcodename+"、"+tipsalename+wtypename;
					text += "的销售积分规则，确定将其置为无效？";
					$showTipContent.html("");
					$showTipContent.append(text);
					$this.params.id = result.userdata.id;//用于回傳数据时
					PrvSalesPointInputBasic.showTip();
					
				}else{
					PrvSalesPointIndex.refresh();
					$this.removeThisTab();
					Global.notify("success", result.message);
				}
			}
		},$this.params,method);
 	},
	checkRequired : function($form){
	
		var text = "";
		if(this.checkNull($form.find("#id_city").val())){
			text += "地市、";
		}
		if(this.checkNull($form.find("#id_opcode").val())){
			text += "业务操作、";
		}
		if(this.checkNull($form.find("#id_wtype").val())){
			text += "用工类型、";
		}
		if(this.checkNull($form.find("#id_point").val())){
			text += "销售积分、";
		}
		if("" != text){
			Global.notify("error",text.substr(0,text.length-1)+" 为必填项!");
			return false;
		}
		return true;
	},
	validateNum : function(num)
	{
	  var reg = /^\d+(?=\.{0,1}\d+$|$)/
	  if(reg.test(num)) return true;
	  return false ;  
	},
	checkNull : function(objvalue){
		if(objvalue == undefined || objvalue == ""|| objvalue==null 
				||!objvalue){
			return true;
		}
		return false;
	},
	showTip:function(){
		var model = $(".div-biz-prd-prv-sales-point-inputBasic").find('#showTipModel');
	    $(model).modal();  
	},
	urlSubmit:function(){
		var url = WEB_ROOT + '/prd/prv-sales-point!addNewDataAndUpdateData';
		var method = "post";
		$("body").ajaxJsonUrl(url,function(result){
			if(result.type == "success"){
				Global.notify("success", result.message);
				PrvSalesPointIndex.refresh();
				PrvSalesPointInputBasic.removeThisTab();
				var backdrop = $("body").find(".modal-backdrop");
				$(backdrop).remove();
			}
		},this.params,method);
	
	},
	removeThisTab : function(){
		// 移除当前编辑tab（代替取消按钮事件，因为会额外弹出提示框）
		
		var bodyElement = $(".div-biz-prd-prv-sales-point-index");
		var activeTab = $(bodyElement).find(".active.tab-closable");
		var tabId = $(activeTab).attr("id");
		$(activeTab).remove(); // 移除当前编辑tab
		$(bodyElement).find("a[href='#" + tabId + "']").remove(); // 移除当前编辑tab标题
		$(bodyElement).find(".tab-default").click();
	}
}

$(function() {
	PrvSalesPointInputBasic.init();
});
