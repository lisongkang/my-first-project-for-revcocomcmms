var OthersKpiAuditInputBasic = {
	$form :$(".form-others-kpi-audit"),
    $detail :$(".grid-biz-salary-others-kpi-audit-detail"),
	init : function(){
        //初始化地市
        setTimeout("OthersKpiAuditInputBasic.initCity();",500);
        var id = this.$form.find("#id").val();
        var areaidobj = this.$form.find("#id_areaid");
        var grid = this.$form.find("#id_grid");
        var operid = this.$form.find("#id_operid");
        var dateMonth =  this.$form.find("input[name='dateMonth']").val();

        if(!Biz.checkNull(id)){
            var areaid = this.$form.find("#id_areaid").val();
            var hid_grid = this.$form.find("#hid_grid").val();
            var hid_operid = this.$form.find("#hid_operid").val();
            var status =  this.$form.find("input[name='status']").val();
            Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + areaid,grid,hid_grid);
            Biz.initSelect("PRV_OPERATOR_BY_GRID&mcode=" + hid_grid,operid,hid_operid);
            //状态为待审核和审核成功时隐藏保存按钮
            if(status=="2" || status=="3"){
                $(".submitbtn").hide();
            }
        }



		//初始化表格数据
		this.$form.data("formOptions",{
	    	bindEvents : function(){
	    	    var $form = $(this);

	    		 //提交按钮
	    		 var btn_submit = $form.find("button[name='btn_submitKpiAudit_Y']");
				 btn_submit.click(function(){
				    return OthersKpiAuditInputBasic.doSubmit("2");
				 });
				 //提交审核按钮
	    		 var btn_audit_submitKpi = $form.find("button[name='btn_submitKpiAudit_N']");
                 btn_audit_submitKpi.click(function(){
				    return OthersKpiAuditInputBasic.doSubmit("3");
				 });
	    	}
		});

		//积分明细
        this.$detail.data("gridOptions", {
            url: WEB_ROOT + '/biz/salary/others-kpi!findDetail',
            postData:{
                "othersKpi.grid":OthersKpiAuditInputBasic.$form.find("#hid_grid").val(),
                "othersKpi.dateMonth":dateMonth,
                "othersKpi.operid":OthersKpiAuditInputBasic.$form.find("#hid_operid").val()
            },
            colModel: [
               {
                label: '指标类型',
                name: 'textConfigType',
                editable : false,
                search :  false,
                sortable : false,
                editrules :{required:true},
                width: 100,
                stype : 'select',
                    editoptions : {
                    value : Biz.getCacheParamDatas("SALARY-OTHERS-KPI-TEXT-CONFIG_TYPE"),
                }
            }, {
                label: '指标项目',
                name: 'textConfigId',
                editable : false,
                search :  false,
                sortable : false,
                editrules :{required:true},
                required:true,
                width: 90,
                stype : 'select',
                editoptions : {
                      value : Biz.getCacheParamDatas("OTHERS-KPI-TEXT-CONFIG-ALL")
                }
            }, {
                label: '得分',
                name: 'score',
                number: true,
                editrules :{required:true,number:true},
                editable : false,
                search :  false,
                sortable : false,
                width: 90
            }, {
                label: '扣分说明',
                name: 'remark',
                editable : false,
                search :  false,
                sortable : false,
                width: 80
            }, {
                label: '状态',
                name: 'status',
                editable : false,
                stype : 'select',
                editoptions : {
                    value : Biz.getCacheParamDatas("SALARY-OTHERS-KPI-STATUS")
                },
                search :  false,
                sortable : false,
                width: 80
            }],
            height: 'auto',
            rowNum: -1,
            // pager: '',
            // pgbuttons: false,
            // pginput:false,
            editurl:"clientArray",
            delurl:"clientArray",
            inlineNav:{
                save:false
            },
            gridComplete: function () {
                var thisGrid = OthersKpiAuditInputBasic.$detail;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
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
         // Biz.disableSelect2($city);
         // Biz.disableSelect2WhenNotAdmin($city);
	},
	/**
	 * 保存
	 */
	doSubmit : function(s){
		//如果是修改
		// var $form = this.$form;
		// var id = $form.find("#id").val();
        $("input[name='status']").val(s);
        // $("input[name='textConfigIds']").val();
        // var rowData = OthersKpiAuditInputBasic.$detail.jqGrid('getRowData');
        // var textConfigIds = new Array();
        // var scores = new Array();
        // var remarks = new Array();
        //
        // console.log(rowData);
        // for (var i in rowData){
        //     textConfigIds.push(rowData[i].textConfigId);
        //     scores.push(rowData[i]["score"]);
        //     remarks.push(rowData[i]["remark"]);
        // }
        // if(textConfigIds.length==0 || rowData==null){
        //     Global.notify("error", "请添加明细项!");
        //     return false;
        // }
        // if(s=='1'){
        //     $("#duitModal").modal('hide');
        //     var auditUser = $("#id_auditUser").val();
        //     if(auditUser == null || auditUser==""){
        //         Global.notify("error", "请选择审核人!");
        //         return false;
        //     }
        // }
        // $("input[name='textConfigIds']").val(textConfigIds);
        // $("input[name='scores']").val(scores);
        // $("input[name='remarks']").val(remarks);
        // $("input[name='status']").val(s);
		 return true;
	}
};
$(function(){
    OthersKpiAuditInputBasic.$form.find("#id_dateMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });

	OthersKpiAuditInputBasic.init();
});