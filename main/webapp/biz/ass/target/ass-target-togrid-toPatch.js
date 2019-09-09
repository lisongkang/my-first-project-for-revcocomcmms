AssTargetTogridToPatch=({
	$form: $(".form-biz-ass-target-togrid-toPatch"),
	init : function(){
		
		//按钮绑定事件
		this.$form.find("button[name='btn_toPatch']").click(this.patchListener);
			
		AssTargetTogridToPatch.initFormInfo();
	},
	
	initFormInfo : function(){
		
		//初始化指标下拉菜单
		var url = WEB_ROOT + "/biz/ass/target/ass-target-togrid!getCanPatchStores";
    	url = url + "?rows=-1";
    	var tmpForm=this.$form;
    	this.$form.ajaxJsonUrl(url, function(data) {
    		$ass=tmpForm.find("#id_assId");
    		$ass.empty();
    		$ass.select2({placeholder: "请选择指标"});
    		$ass.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
                option = '<option value="'+item.id+'">'+item.name+'</option>';
                $ass.append(option);
            })
    	});
		
    	//初始化考核期
    	this.$form.find('#id_cyclenum').jeDate({
			isinitVal : false,
			zIndex : 999999999999,
			format : 'YYYY-MM'// 分隔符可以任意定义，该例子表示只显示年月
		});
    	
    	
    	this.$form.find('#btn_net_grid').click(function(){
    		AssTargetTogridToPatch.$form.popupDialog({
        		url : WEB_ROOT + '/biz/ass/target/ass-target-togrid!toGridTreePage',
                id : 'dialog_grid',
        		title : '选择网格',
        		size:'auto',
        		callback : function(data) {
        			if (data.type == "success") {
        				$grid.refresh();
        			}
        		}
        	});
    	});
	},
	checkNull : function(objvalue){
		if(objvalue == undefined || objvalue == "" || objvalue == null || !objvalue){
			return true;
		}
		return false;
	},
	checkRequiredInput : function () {
		var text = "";
		if(AssTargetTogridToPatch.checkNull(this.$form.find('#id_assId').val()))
			text += "考核指标、";
		if(AssTargetTogridToPatch.checkNull(this.$form.find('#id_cyclenum').val()))
			text += "考核周期、";
		if(AssTargetTogridToPatch.checkNull(this.$form.find('#view_checked_grid_ids').val()))
			text += "下发网格、";
		if(AssTargetTogridToPatch.checkNull(this.$form.find('#id_assNum').val()))
			text += "目标值、";
		
		if("" != text){
			alert(text.substr(0,text.length-1) + " 为必填项!");
			return false;
		}
		
    	return true;
	
	},
	
	patchListener:function(){
		if(AssTargetTogridToPatch.checkRequiredInput()){
			var $form=AssTargetTogridToPatch.$form;
			var storeData={};
			var url = WEB_ROOT + "/biz/ass/target/ass-target-togrid!targetToPatch";
	    	storeData.assId = $form.find('#id_assId').val();
	    	if($form.find('#id_cyclenum')){
	    		storeData.cyclenumStr = $form.find('#id_cyclenum').val()+'-01';
	    	}
	    	storeData.gridids = $form.find('#view_checked_grid_ids').val();
	    	storeData.assNum = $form.find('#id_assNum').val();
	    	storeData.assTargetStore = JSON.stringify(storeData);
	    	
			$("body").ajaxJsonUrl(url, function(result) {
				Global.notify("success", "指标已下发成功");
				$form.find('#btn_cancel').click();
				Biz.refreshGrid(".grid-biz-ass-target-ass-target-togrid");
			}, storeData);
		}
	},
	initSelGrid:function(nodes){ //初始化网格
		var text_ids=null,text_names=null;
		for(var i=0;i<nodes.length;i++){
			text_ids==null?text_ids=nodes[i].id:text_ids=text_ids+','+nodes[i].id;
			text_names==null?text_names=nodes[i].name:text_names=text_names+','+nodes[i].name;
		}
		
		this.$form.find("#view_checked_grid_ids").val(text_ids);
		this.$form.find("#view_checked_grid").val(text_names);
		
	},
	getCheckedIds:function(){
		return this.$form.find("#view_checked_grid_ids").val();
	}
});

$(function() {
	AssTargetTogridToPatch.init();
});