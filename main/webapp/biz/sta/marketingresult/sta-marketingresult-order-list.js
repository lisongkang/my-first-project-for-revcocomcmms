var StaMarketOrderList = {
	pageMainDiv : $(".div-biz-sta-marketing-result-order-list"),
	init : function() {
		this.searchFormObj.init();
		this.gridObj.init();
	},
	searchFormObj : {
		form : $(".form-biz-sta-marketing-result-order-list"),
		init : function() {
			var $this = this;

			// 绑定关联事件
			this.form.find("#areaid").bind("change", function(e) {
				// 值改变与部门下拉项关联
				$this.initDepart($(this).val());
			});

			this.form.find("#depart").bind("change", function(e) {
				$this.initOperator($(this).val());
			});
			
			this.initArea();
			this.initOrderStatus();
			this.initOpCode();
			this.initIfFilterRegression();
			//this.initPortalOrderStatus();
		},
      /*  initPortalOrderStatus:function(){
            Biz.initSelect("BIZ_PORTAL_ORDER_STATUSCOPY", this.form.find("#payStatus"),2);
		},*/

        initIfFilterRegression : function(){
            var $form = this.form;
            var ifFilterRegression = $form.find("#ifFilterRegression");
            var bizcustorderregession = "BIZ_CUSTORDER_REGRESSION"
            Biz.initSelect(bizcustorderregession, this.form.find("#ifFilterRegression"));

		},
		initArea : function() {
			var $form = this.form;
			var loginCity = Biz.LOGIN_INFO.city;
			var rolelevel = Biz.LOGIN_INFO.rolelevel;
			var areaid = Biz.LOGIN_INFO.areaid;
			//对于低权限固定当前操作员业务区  中高权限为操作员地市的业务区
			console.info(rolelevel);
			console.info(areaid);
			var areaSelectGcode;
			if (Biz.isCurrentAdmin()) {
				areaSelectGcode = "PRV_AREA"; // 超级管理员则查询出所有业务区
			}else if(rolelevel == "0"){
                console.info(areaid);
                areaSelectGcode = "PRV_AREA&mcode=" + areaid; // 对于低权限固定当前操作员业务区
			}else {
				areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + loginCity; // 中高权限只能查询所属地市的业务区
			}

			var area = $form.find("#areaid");
			Biz.initSelect(areaSelectGcode, area, Biz.LOGIN_INFO.areaid, function() {
				area.change();
			}); // 字符串中用&mcode增加参数

			if (rolelevel != "9" && rolelevel!= "5") {
				// 非高权限的角色不能改变业务区
				$(area).attr("disabled", true).removeAttr("name");

				// area表单项被禁用后传不了参数值，因此需要用隐藏表单项传areaid值
				$form.find("#backupAreaId").attr("name", "staMarketResultParamBo.areaids").val(Biz.LOGIN_INFO.areaid);
			}
		},
		initDepart : function(selectedArea) {
			var rolelevel = Biz.LOGIN_INFO.rolelevel;
			var operid = Biz.LOGIN_INFO.operid;

			if (!selectedArea || selectedArea == "") {
				selectedArea = -1; // 清空时限制后台查不到部门
			}

			var deptSelectGcode;
			var deptSelectMcode;
			if (rolelevel == "9") {
				// 高权限能查询业务区下的所有部门
				deptSelectGcode = "PRV_DEPT_BY_AREA";
				deptSelectMcode = selectedArea;
			} else {
				// 否则只能查询操作员所属的部门
				//deptSelectGcode = "PRV_DEPT_BY_OPERID";
				//deptSelectMcode = operid;
				//改成不管什么权限部门都和业务区联动
                deptSelectGcode = "PRV_DEPT_BY_AREA";
                deptSelectMcode = selectedArea;
			}

			var depart = this.form.find("#depart");
			Biz.initSelectByMcodes(deptSelectGcode, deptSelectMcode, depart, Biz.LOGIN_INFO.deptid, function(){
				depart.change();
			});
		},
		initOperator : function(selectedDepart) {
			if (!selectedDepart || selectedDepart == "") {
				selectedDepart = -1; // 清空时限制后台查不到操作员
			}
			var operator = this.form.find("#operator");
			Biz.initSelectByMcodes("PRV_OPERATOR_BY_DEPT", selectedDepart, operator, Biz.LOGIN_INFO.operid, function(){
				operator.select2("val", Biz.LOGIN_INFO.operid); //这里即使initSelect有默认值，也要在回调中select2才能选中值，原因不明
			});
		},
		initOrderStatus : function() {
			Biz.initSelect("BIZ_CUSTORDER_ORDERSTATUS", this.form.find("#orderStatus"));
		},
		initOpCode : function() {
			Biz.initSelect("BIZ_OPCODE", this.form.find("#opCode"));
		},
		checkInput : function() {
			var $form = this.form;
			var text = "";
			if (this.checkNull($form.find('#areaid').val())) {
				text += "业务区、";
			}

			if ("" != text) {
				Global.notify("error", text.substr(0, text.length - 1)
						+ " 为必填项!");
				return false;
			}

			var timeRange = $form.find("#timeRange").val();
			if (this.checkNull(timeRange)) {
				Global.notify("error", "请选择统计时段");
				return false;
			}

			var timeRangeArray = timeRange.split('～');
			if (timeRangeArray.length != 2
					|| this.checkNull(timeRangeArray[0].trim())
					|| this.checkNull(timeRangeArray[1].trim())) {
				Global.notify("error", "择统计时段有误，请重新选择");
				return false;
			}
			
			if (!this.checkDateRange(timeRangeArray[0].trim(),timeRangeArray[1].trim())) {
				Global.notify("error", "开始日期不能大于结束日期，请重新选择");
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
		checkDateRange : function(startDate, endDate) {
			return this.strToDate(startDate).getTime() <= this.strToDate(
					endDate).getTime();
		},
		strToDate : function(str) {
			var tempStrs = str.split(" ");
			var dateStrs = tempStrs[0].split("-");
			var year = parseInt(dateStrs[0], 10);
			var month = parseInt(dateStrs[1], 10) - 1;
			var day = parseInt(dateStrs[2], 10);
			var date = new Date(year, month, day);
			return date;
		},
		reset : function() {
			var $form = this.form;
			var area = $form.find("#areaid");
			area.select2("val", "");
			area.change(); // 这里change会触发部门change,部门change再触发操作员change
			
			$form.find("#customer").val("");
            $form.find("#custid").val("");
			$form.find("#orderStatus").select2("val", "");
            //$form.find("#payStatus").select2("val", "");
            $form.find("#ifFilterRegression").select2("val", "");
			$form.find("#bossSerialNo").val("");
			$form.find("#opCode").select2("val", "");
			$form.find("#timeRange").val("");
		}
	},
	gridObj : {
		grid : $(".grid-biz-sta-marketing-result-order-list"),
		init : function() {
			this.grid.data("gridOptions", {
				url : WEB_ROOT + '/biz/sta/marketingresult/sta-marketingresult!querySaleOrderList',
				colModel : [ {
		            label : '操作',
		            name : 'self_opt',
		            hidden : false,
		            sortable : false,
		            width : 60
		        }, {
					label : '订单编号',
					name : 'orderid',
					hidden : false,
					key : true,
		            width : 70
				},{
					label : '客户名称',
					name : 'customer',
					width : 90
				},{
                    label : '客户编号',
                    name : 'custid',
                    width : 90
                }, {
					label : '订单状态',
					name : 'orderStatus',
					width : 70,
					stype : 'select',
					editoptions : {
						value :Biz.getCacheParamDatas("BIZ_CUSTORDER_ORDERSTATUS")
					}
				},
					{
                    label : '业务区',
                    name : 'areaName',
                    width : 90
                    },
                    {
                        label : '网格名称',
                        name : 'gridname',
                        width : 90
                    },
                    {
                        label : '网格编码',
                        name : 'gridcode',
                        width : 90
                    },
					{
					label : 'BOSS流水号',
					name : 'bossSerialNo',
					width : 140,
					hidden : true
				}, {
					label : '业务',
					name : 'opCode',
					width : 70,
					stype : 'select',
					editoptions : {
						value : Biz.getCacheParamDatas("BIZ_OPCODE")
					}
				}, {
					label : '操作部门',
					name : 'depart',
					width : 130
				}, {
					label : '操作人',
					name : 'operator',
					width : 70
				}, {
					label : '操作时间',
					name : 'optime',
					formatter : 'date',
					width : 100
				}, {
					label : '产品/营销方案<br/>/商品',
					name : 'saleName',
					width : 100
				}, {
					label : '金额',
					name : 'fees',
					width : 50
				}, {
					label : '支付状态',
					name : 'payStatus',
					width : 70,
					stype : 'select',
					editoptions : {
						value :Biz.getCacheParamDatas("BIZ_PORTAL_ORDER_STATUS")
					}
				}, {
					label : '支付时间',
					name : 'paytime',
					formatter : 'date',
					width : 100
				} ],
				multiselect : false,
				toppager : true,
				filterToolbar : false,
				gridComplete: function () {
					var thisGrid = StaMarketOrderList.gridObj.grid;
					
					//隐藏不需要按钮
					Biz.hideTableElement(thisGrid,".ui-icon-arrowthickstop-1-s");
					Biz.hideTableElement(thisGrid,".ui-icon-arrowstop-1-w");
					
					var ids = $(thisGrid).jqGrid("getDataIDs");
					for (var i = 0; i < ids.length; i++) {
						var id = ids[i];
						var optBtn = "<a href='javascript:;' style='color:#f60' onclick='StaMarketOrderList.gridObj.detailOpt(\"" + id + "\")' >详细</a>";
						$(thisGrid).jqGrid("setRowData", id, { self_opt : optBtn });
					}
				},
				operations : function(items) {
					var $this=StaMarketOrderList;
					var exportBtn = $this.createCustomBtn(true, "导出数据", "fa-arrow-down",$this.exportExcel);
		    		items.push(exportBtn);
				 }
			});
		},
		detailOpt : function(custorderid) {
			$(this).popupDialog({
	            url :  WEB_ROOT + '/biz/sta/marketingresult/sta-marketingresult!detailPage?custorderid='+custorderid,
	            title : '详情',
	            id : "custOrderDetail",
	            callback : function(rowdata) {
	            }
	        });
		}
	},
	createCustomBtn : function(isPosition, btnTitle, btnClass, btnAction) {
		var newBtn = $('<li '
				+ (isPosition ? 'data-position="multi"' : '')
				+ 'data-toolbar="show"><a modal-size="modal-wide" target="_blank" href="javascript:;"><i class="fa '
				+ btnClass + '"></i> ' + btnTitle + '</a></li>');

		newBtn.children("a").bind("click", btnAction);
		return newBtn;
	},
	exportExcel:function(){
		if(StaMarketOrderList.searchFormObj.checkInput()){
			var param=$(".form-biz-sta-marketing-result-order-list").serialize()
			var url = WEB_ROOT + '/biz/sta/marketingresult/sta-marketingresult!exportExcel?'+param;
			location.href=url;
		}
	}
};

$(function() {
	StaMarketOrderList.init();
});