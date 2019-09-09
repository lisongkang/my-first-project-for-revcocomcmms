var GridInfoInputBasic = {
	formClass : ".form-biz-market-grid-info-inputBasic",
	init : function() {
		$(this.formClass).data("formOptions", {
			bindEvents : function() {
				// 多个编辑表单有多个相同id值的元素，但是在bindEvents中根据id值获取到的值是当前表单中的值，具体原理应该是框架本身有处理
				var $this = GridInfoInputBasic;
				var $form = $(this);

				if ($form.find("#id").val() == "") {
					// 表示新增数据，需要先设置所属网格id值和默认gtype
					var previdObj = $form.find("#previd");
					previdObj.addClass("addPrevid");
					
					if (GridTreeObj.isLeftClick) {
						previdObj.val(GridTreeObj.selectedManageGrid.id);
					} else {
						// 右键新增下属管理或地址网格
						previdObj.val(GridTreeObj.rightSelectedGrid.id);
					}

					$form.find("#preGridName").addClass("addPreGridName");
				}

				// 小区网格不能编辑网格名称
				$form.find("#gridname").attr("disabled", ($form.find("#gtype").val() == 1) ? true : false);

				// 根网格除备注外其它项不能修改
				var isRootGrid = $form.find("#previd").val() == -1 ? true : false;
				if (isRootGrid) {
					$form.find("#gridname").attr("disabled", true);
					$form.find("#gridcode").attr("disabled", true);
				}

				// 根据实际情况改变所属网格的名称
				var preGridName = $form.find("#preGridName");
				if (GridTreeObj.isLeftClick) {
					if (GridTreeObj.isClickManageGrid) {
						if ($form.find("#id").val() == "") {
							// 新增需要修改所属网格名称
							preGridName.val(GridTreeObj.selectedManageGrid.name);
						}
					} else {
						// 左键点击地址网格时，按小区网格处理
						// preGridName.val(GridTreeObj.selectedPatchGrid.preGridName);
					}
				} else {
					if ($form.find("#id").val() == "") {
						// 右键新增管理网格的下属网格，此时GridTreeObj.isRightClickManageGrid为true，不用再判断
						preGridName.val(GridTreeObj.rightSelectedGrid.name);
					} else {
						// 修改网格
						// preGridName.val(GridTreeObj.rightSelectedGrid.preGridName);
					}
				}
			
				$form.find("button[name='btn_save']").click(function() {
					if (!$this.checkRequiredInput($form)) {
            		    return false;
            	    }
					
					var url = $form.attr("action");
					var method = $form.attr("method");
					var params = {};
					params.id = $form.find("#id").val();
					params.previd = $form.find("#previd").val();
					params.gridcode = $form.find("#gridcode").val();
					params.gridname = $form.find("#gridname").val();
					params.gtype = $form.find("#gtype").val();
					params.memo = $form.find("#memo").val();
					params.token = $form.find("input[name='token']").val();
					params["struts.token.name"] = $form.find("input[name='struts.token.name']").val();

					$("body").ajaxJsonUrl(url, function(result) {
	    				if (result.type == "success") {
	    					Global.notify("success", result.message);
	    					GridListObj.refresh();
	    					$this.removeThisTab();
	    					GridTreeObj.addOrUpdateRefresh(params.id == "", result.userdata);
	    					if (DataGridTreeObj.isInited) {
    							DataGridTreeObj.addOrUpdateRefresh(params.id == "", result.userdata);
    						}
	    				}
	    			}, params, method);
				});
			},
		});
	},
	changePreGridInfoWhenAdd : function() {
		var $form = $(this.formClass);
		if ($form) {
			var previdObj = $form.find(".addPrevid");
			if (previdObj) {
				if (GridTreeObj.isLeftClick) {
					$(previdObj[0]).val(GridTreeObj.selectedManageGrid.id);
					$($form.find(".addPreGridName")[0]).val(GridTreeObj.selectedManageGrid.name);
				} else {
					$(previdObj[0]).val(GridTreeObj.rightSelectedGrid.id);
					$($form.find(".addPreGridName")[0]).val(GridTreeObj.rightSelectedGrid.name);
				}
			}
		}
	},
	checkRequiredInput : function($form) {
		var text = "";
		if (this.checkNull($form.find('#gridcode').val())) {
			text += "网格编码、";
		}
		if (this.checkNull($form.find('#gridname').val())) {
			text += "网格名称、";
		}
		if (this.checkNull($form.find('#gtype').val())) {
			text += "网格类型、";
		}

		if ("" != text) {
			Global.notify("error", text.substr(0, text.length - 1) + " 为必填项!");
			return false;
		}

		return true;
	},
	checkNull : function(objvalue) {
		if (objvalue == undefined || objvalue == "" || objvalue == null
				|| !objvalue) {
			return true;
		}
		return false;
	},
	removeThisTab : function() {
		GridListObj.removeActiveTab();
	}
};

$(function() {
	GridInfoInputBasic.init();
});