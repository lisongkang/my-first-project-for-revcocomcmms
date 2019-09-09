var StaMarketStatisticList = {
	pageMainDiv : $(".div-biz-sta-marketing-statistic-list"),
	init : function() {
		this.searchFormObj.init();
		this.gridObj.init();
	},

	searchFormObj : {
		form : $(".form-biz-sta-marketing-statistic-list"),
		init : function() {
			var $this = this;
			this.initArea();
            this.initOrderStatus();
            this.initPortalOrderStatus();
		},
        initOrderStatus : function() {
            Biz.initSelect("BIZ_CUSTORDER_ORDERSTATUS", this.form.find("#orderStatus"),"SYNC");
        },
		initPortalOrderStatus:function(){
            Biz.initSelect("BIZ_PORTAL_ORDER_STATUSCOPY", this.form.find("#payStatus"),2);
        },
		initArea : function() {
			var $form = this.form;
			var loginCity = Biz.LOGIN_INFO.city;
			var rolelevel = Biz.LOGIN_INFO.rolelevel;

			var areaSelectGcode;
			if (Biz.isCurrentAdmin()) {
				areaSelectGcode = "PRV_AREA"; // 超级管理员则查询出所有业务区
			} else {
				areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + loginCity; // 否则只能查询所属地市的业务区
			}

			var area = $form.find("#areaid");
			Biz.initSelect(areaSelectGcode, area, Biz.LOGIN_INFO.areaid, function() {
				area.change();
			}); // 字符串中用&mcode增加参数

			if (rolelevel != "9") {
				// 非高权限的角色不能改变业务区
				$(area).attr("disabled", true).removeAttr("name");

				// area表单项被禁用后传不了参数值，因此需要用隐藏表单项传areaid值
				$form.find("#backupAreaId").attr("name", "staMarketResultParamBo.areaids").val(Biz.LOGIN_INFO.areaid);
			}
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


		checkInput : function() {
			var $form = this.form;
			var text = "";
			// if (this.checkNull($form.find('#id_que_grid').val())) {
			// 	text += "地市、";
			// }
			//
			// if ("" != text) {
			// 	Global.notify("error", text.substr(0, text.length - 1)
			// 			+ " 为必填项!");
			// 	return false;
			// }

			var city = $form.find("#id_que_city").val();
			if (this.checkNull(city )) {
				Global.notify("error", "地市为必填项");
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
			var area = $form.find("#id_que_grid");
			area.select2("val", "请选择网格...");
			area.change();
            $form.find("#orderStatus").select2("val", "");
            $form.find("#payStatus").select2("val", "");
            $form.find("#timeRange").val("");
		}


	},


	gridObj : {
		grid : $(".grid-biz-sta-marketing-statistic-list"),
		init : function() {
			this.grid.data("gridOptions", {
				url : WEB_ROOT + '/biz/sta/marketingstatistic/sta-marketing-statistic!querySaleOrderList',
				colModel : [ {
					label : '统计日期',
					name : 'statisticDate',
                    formatter : 'date',
		            width : 100
				},{
					label : '业务区',
					name : 'areaName',
					width : 90
				},{
                    label : '网格名称',
                    name : 'gridName',
                    width : 90,
					hidden : true
                }, {
					label : '网格编码',
					name : 'gridcode',
                    width : 90,
                    hidden : true
				},{
                    label : '所属部门',
                    name : 'depart',
                    width : 90
                },{
					label : '网格名称',
					name : 'gridname',
					width : 90,
				}, {
					label : '网格编码',
					name : 'gridcode',
					width : 90,
				}
                ,{
					label : '网格人员',
					name : 'operName',
					width : 90
				}, {
                    label : '业务编码',
                    name : 'opcode',
                    width : 70,
                    hidden : true
				}, {
					label : '业务名称',
					name : 'opcode2',
					width : 70,
					stype : 'select',
					editoptions : {
						value : Biz.getCacheParamDatas("BIZ_OPCODE")
					}
				}, {
					label : '金额',
					name : 'fees',
					width : 50
				}, {
					label : '业务数量',
					name : 'num',
					width : 50
				} ],
				multiselect : false,
				toppager : true,
				filterToolbar : false,
				gridComplete: function () {
					var thisGrid = StaMarketStatisticList.gridObj.grid;
					
					//隐藏不需要按钮
					Biz.hideTableElement(thisGrid,".ui-icon-arrowthickstop-1-s");
					Biz.hideTableElement(thisGrid,".ui-icon-arrowstop-1-w");

				},
				operations : function(items) {
					var $this=StaMarketStatisticList;
					var exportBtn = $this.createCustomBtn(true, "导出数据", "fa-arrow-down",$this.exportExcel);
		    		items.push(exportBtn);
				 }
			});
		}

	},
	createCustomBtn : function(isPosition, btnTitle, btnClass, btnAction) {
		var newBtn = $('<li '
				+ (isPosition ? 'data-position="multi"' : '')
				+ 'data-toolbar="show"><a modal-size="modal-wide" target="_blank" href="javascript:void(0);"><i class="fa '
				+ btnClass + '"></i> ' + btnTitle + '</a></li>');

		newBtn.children("a").bind("click", btnAction);
		return newBtn;
	},
	exportExcel:function(){
		if(StaMarketStatisticList.searchFormObj.checkInput()){
			var param=$(".form-biz-sta-marketing-statistic-list").serialize()
			var url = WEB_ROOT + '/biz/sta/marketingstatistic/sta-marketing-statistic!exportExcel?'+param;
			location.href=url;
		}
	}
};



StaGridincomeList = ({
	pageMainDiv : $(".div-biz-sta-marketing-statistic-list"),

	init : function() {
		$(".form-biz-sta-marketing-statistic-list").data("formOptions", {
			//@_@!!!! ~…~,原来是form没写form-validation样式，所以怎么都绑定不了事件

			initGrid : function(city) {
				var $form = $(this);
				var $grid = $form.find("#id_que_grid");
				if (!city) {
					$grid.empty();
					$grid.append('<option value=""></option>');
					// grid.select2("val", "");
					$grid.select2({placeholder: "请选择网格"});
					return;
				}

				var gcode = "PRV_GRID_BY_CITY";
				var mcode = city;
				var url = WEB_ROOT + "/prv-sysparam!selectParamList";
				url = url + "?rows=-1&gcode=" + gcode + "&mcode=" + mcode;

				$form.ajaxJsonUrl(url, function(data) {
					$grid.empty();
					$grid.select2({placeholder: "请选择网格"});

					$grid.append('<option value="">查询所有</option>');
					var option = '';
					$.each(data, function(i, item) {
						option = '<option value="'+item.mcode+'">'+item.mname+'</option>';
						$grid.append(option);
					})
					//$grid.select2("val", "");
					$grid.select2({placeholder: "请选择网格"});
				});
			},


			//绑定事件
			bindEvents : function() {
				//地市改变事
				var $form = $(this);
				var $city = $form.find("#id_que_city");
				$city.change(function() {
					$form.data("formOptions").initGrid.call($form, $city.val());
				});

				if (!$form.find("#id_que_city").val()) {
					if (Biz.LOGIN_INFO) {
						$form.find("#id_que_city").select2("val", Biz.LOGIN_INFO.city);
						$form.data("formOptions").initGrid.call($form, Biz.LOGIN_INFO.city);
					}
				}

			}
		});
	}});

$(function() {
	StaMarketStatisticList.init();
	StaGridincomeList.init();
});