var AdSetInputBasic = {
	$form :$(".form-ad-set-inputBasic"),
	init : function(){
		
		//初始化地市
		setTimeout("AdSetInputBasic.initCity();",500);
	
		//初始化表格数据
		this.$form.data("formOptions",{
	    	bindEvents : function(){
	    	    var $form = $(this);
	    	    var objtype = $form.find("select[name='adtype']");
	    	    
	    		$form.find("button[name='uploadAdImgBtn']").click(function(){
	    			var targetObj = $form.find("#id_adsite");
	    			AdSetInputBasic.uploadFile(targetObj);
	    		});
	    		
	    		objtype.bind("change",function(){
	             	$form.find("input[name='extraAttributes.objname']").val("");
	 				$form.find("input[name='adobj']").val("");
	             });
	    		 
	    		 $form.find(".fa-select-obj").click(function() {
	             	var objtype0 = objtype.val();
	             	if (objtype0 == "") {
	             		Global.notify("error", "请选择对象类型");	
	             		return;
	             	}
	             	if (objtype0 == '1') {//商品
	 					Biz.showSalespkgKnowDialog($form);
	             	} else if (objtype0 == '2'){//连接
	             		//没有反应
	             	} else if(objtype0 =='3'){//图片
	             		var targetObj = $form.find("#id_objname");
		    			AdSetInputBasic.uploadFile(targetObj);
	             	}
	    		 });
	    		 
	    		 
	    		 //提交按钮
	    		 
	    		 var btn_submit = $form.find("button[name='btn_submitAdConfig']");
				 btn_submit.click(function(){
				    return AdSetInputBasic.doSubmit();  			
				 });
	    	}
		});
	},
	/**
	 * 初始化地市
	 */
	initCity :function(){
		var $form = this.$form;
		var id = $form.find("#id").val();
		var $city  = $form.find("#id_city");
		//如果是新增
		if(Biz.checkNull(id)){
			$city.select2("val", Biz.LOGIN_INFO.city);
		}
		 Biz.disableSelect2WhenNotAdmin($city);
	},
	/**
	 * 保存
	 */
	doSubmit : function(){
		//如果是修改
		var $form = this.$form;
		var id = $form.find("#id").val();
		if(!Biz.checkNull(id)){
			
			var opid   = $form.find("input[name='optid']").val();
			if(opid != Biz.LOGIN_INFO.operid){
				Global.notify("error", "该广告只允许广告创建者修改");
				return false;
			}
			var adstatus = $form.find("input[name='adstatus']").val();
			//1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
			if(adstatus != "1" && adstatus != "4"){
				Global.notify("error", "只允许状态为已保存或者审核不通过的广告进行修改");
				return false;
			}
		}
		
        //如果是
		var objtype = $form.find("select[name='adtype']").val();
		if(objtype !='1'){
			var objname = $form.find("input[name='extraAttributes.objname']").val();
		    $form.find("input[name='adobj']").val(objname);
		}
		
		//点击【保存】则弹出提示框‘已保存，是否提交？’，选择否则该条模版的记录状态为【待提交】，选择是则该条模版的记录状态为【待审核】
		if (confirm("广告已保存，确定提交审核？")) {
            //选择提交
			this.$form.find("input[name='adstatus']").val("2");
        }else{
        	this.$form.find("input[name='adstatus']").val("1");
        }
		return true;
	},
	uploadFile :function(targetObj0){
		var targetObj =targetObj0 ; //目标对象（返回文件路径）
		var limitFileType0 = "img";               //文件格式
		var maxSize0 = "2048";                      //文件大小
		var relativePath0="adsite_imgs";          //文件存储子目录
		var tip="注：图片格式为.jpg,.jpeg,.bmp,.png,图片大小不大于2MB";
		Biz.fileupload(limitFileType0,maxSize0,relativePath0,tip,targetObj);
	}
};
$(function(){
	AdSetInputBasic.init();
});