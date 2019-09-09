PrvOperMenuCtrlInputBasic = ({
	init:function(){
		$(".form-grid-prv-prv-oper-menu-ctrl-inputBasic").data("formOptions",{
			bindEvents:function(){
				var $form = $(this);
				
				$form.find("button[name='btn_prvopermenuctrl']").click(function(){
					return PrvOperMenuCtrlInputBasic.doMenuCtrlNew("");
				});
			}
		});
	},
	selectOperTabs:function(operid){
		var $form = $(operid);
		$form.popupDialog({
			url : WEB_ROOT + '/prv/prv-operator-findOneCityByPage.jsp',
			title : '选择工号',
			id: 'operator_select',
			callback : function(rowdata){
				$form.find('#opername').val(rowdata.name);
				$form.find('#operid').val(rowdata.operid);
			}
		});
	},
	selectMenuTabs:function(menu){
		var $form = $(menu);
		$form.popupDialog({
			url : WEB_ROOT + '/prv/prv-menudef-mobileMenus.jsp',
			title : '选择菜单',
			id: 'menu_select',
			callback : function(rowdata){
//				$form.val(rowdata.menuid);
				$form.find('#menuid').val(rowdata.menuid);
				$form.find('#menuname').val(rowdata.name);
				/*if($form.find('#menuid').val()==rowdata.operid){	
					  $form.find('#operid').val(rowdata.operid);
				  }else{
					  $form.find('#operid').val(rowdata.operid);
				  }*/
			}
		});
	}
});