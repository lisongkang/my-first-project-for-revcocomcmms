var GridManagerMsgList = {
	pageMainDiv:$(".div-biz-sta-grid-manager-msg-list"),
	init : function() {
		this.searchFormObj.init();
		this.gridObj.init();
	},
	searchFormObj :{
		form : $(".form-biz-sta-grid-manager-msg-list"),
		init : function(){
			var $this = this;
			this.form.find("#areaid").bind("change",function(){
				// 值改变与部门下拉项关联
				$this.initDepart($(this).val());
			});
			
			this.form.find("#depart").bind("change",function(){
				//值改变与操作人下拉项关联
				$this.initOperator($(this).val());
			});
			
			this.initArea();
		},
		initArea : function(){
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
				$form.find("#backupAreaId").attr("name", "gridManagerMsgParamBo.areaids").val(Biz.LOGIN_INFO.areaid);
			}
		},
		initDepart : function(selectedArea){
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
				deptSelectGcode = "PRV_DEPT_BY_OPERID";
				deptSelectMcode = operid;
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
		reset : function() {
			var $form = this.form;
			var area = $form.find("#areaid");
			var rolelevel = Biz.LOGIN_INFO.rolelevel;
			if (rolelevel == "9") {
				area.select2("val", "");
				area.change(); // 这里change会触发部门change,部门change再触发操作员change
			}else{
				$form.find("#depart").select2("val", "");
				$form.find("#depart").change();
				$form.find("#s2id_depart").find(".select2-search-choice-close").click();
			}
			
		}
	   
	},
	gridObj :{
		grid : $(".biz-sta-grid-manager-msg-list"),
		init :function(){
			this.grid.data("gridOptions", {
				url : WEB_ROOT + '/biz/sta/gridmanagermsg/grid-manager-msg!queryGridManagerMsgList',
				colModel : [{
					label : 'Portal账号',
					name :'portalnum',
					width : 90
				},{
					label : '姓名',
					name :'name',
					width : 90
				},{
					label : 'Boss工号',
					name :'loginname',
					width : 90
				},{
					label : '部门',
					name :'departname',
					width : 90
				},{
					label : '业务区',
					name :'areaname',
					width : 90
				},{
					label : '地市',
					name :'cityname',
					width : 90
				}
				],
				multiselect : false,
				toppager : true,
				filterToolbar : false
			});
		}
	}
	
	
};
$(function(){
	GridManagerMsgList.init();
});