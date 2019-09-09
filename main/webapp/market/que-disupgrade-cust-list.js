var QueDisupgradeCust = {
	pageMainDiv : $(".div-que-disupgrade-cust"),
	init : function() {
		this.searchFormObj.init();
		this.gridObj.init();
	},
	searchFormObj : {
		form : $(".form-que-disupgrade-cust"),
		init : function() {
			this.initPatchids("");// 清空片区
			this.initServType();
//			this.initNetAttr();
		},
		initServType : function() {
			Biz.initSelect("RES_SERVTYPE", this.form.find("#servtype"));
		},
		initNetAttr : function() {
			Biz.initSelect("RES_NETATTR", this.form.find("#netattr"), "0");
		},
		checkInput : function() {
			var $form = this.form;
			var text = "";
			if (this.checkNull($form.find('#patchids').val())) {
				text += "片区、";
			}
			if (this.checkNull($form.find('#netattr').val())) {
				text += "设备单双属性、";
			}

			if ("" != text) {
				Global.notify("error", text.substr(0, text.length - 1)
						+ " 为必填项!");
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
		reset : function() {
			var $form = this.form;
			$form.find("#patchids").select2("val", "");
			$form.find("#servtype").select2("val", "");
			$form.find("#devno").val("");
		},
		initFromTree : function(gridids) {
			var grididsStr = "";
			for (var i = 0; i < gridids.length; i++) {
				grididsStr += gridids[i] + ",";
			}

			if (grididsStr.length > 0) {
				grididsStr = grididsStr.substring(0, grididsStr.length - 1);
			}

			this.initPatchids(gridids);
		},
		initPatchids : function(gridids) {
			var $form = this.form;
			var patchidsObj = $form.find("#patchids");

			var storeData = {};
			storeData.secondGridids = gridids;
			storeData.assIndexMonprogress = JSON.stringify(storeData);

			var url = WEB_ROOT + '/biz/ass/monstat/ass-index-monprogress!getThirdPatchidBySecondGridid';
			$("body").ajaxJsonUrl(
					url,
					function(data) {
						patchidsObj.empty();
						patchidsObj.select2({
							placeholder : "请选择片区"
						});
						patchidsObj.append('<option value=""></option>');
						var option = '';
						$.each(data, function(i, item) {
							option = '<option value="' + item.patchid + '">' + item.patchname + '</option>';
							patchidsObj.append(option);
						});
					}, storeData);
		}
	},
	gridObj : {
		grid : $(".grid-que-disupgrade-cust"),
		init : function() {
			this.grid.data("gridOptions", {
				url : WEB_ROOT + '/market/que-disupgrade-cust!queryDisupgradeCustInfo',
				colModel : [ {
					label : '选择网格',
					name : 'gridpath',
					width : 120
				}, {
					label : '客户名称',
					name : 'custname',
					width : 100
				}, {
					label : '住宅地址',
					name : 'whladdr',
					width : 200
				}, {
					label : '用户类型',
					name : 'servtype',
					width : 80,
					stype : 'select',
					editoptions : {
						value : Biz.getPrvParamListDatas("RES_SERVTYPE", "")
					}
				}, {
					label : '机顶盒号',
					name : 'devno',
					width : 100
				}, {
					label : '开通日期',
					name : 'opentime',
					formatter : 'date',
					width : 80
				}, {
					label : '用户状态',
					name : 'servstatus',
					width : 70,
					stype : 'select',
					editoptions : {
						value : Biz.getPrvParamListDatas("SYS_SERV_STATUS", "")
					}
				} ],
				multiselect : false,
				toppager : true,
				filterToolbar : false
			});
		}
	}
};

$(function() {
	QueDisupgradeCust.init();
});