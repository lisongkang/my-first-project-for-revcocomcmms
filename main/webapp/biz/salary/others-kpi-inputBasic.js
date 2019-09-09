var OthersKpiInputBasic = {
	$form :$(".form-others-kpi"),
    $detail :$(".grid-biz-salary-others-kpi-detail"),
	init : function(){
        //初始化地市
        setTimeout("OthersKpiInputBasic.initCity();",500);
        var id = this.$form.find("#id").val();
        var areaidobj = this.$form.find("#id_areaid");
        var grid = this.$form.find("select[name='grids']");
        var operid = this.$form.find("select[name='operids']");
        var dateMonth =  this.$form.find("input[name='dateMonth']").val();

        if(!Biz.checkNull(id)){
            var areaid = this.$form.find("#id_areaid").val();
            var gridVal = this.$form.find("input[name='grid']").val();
            var operidVal = this.$form.find("input[name='operid']").val();

            Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + areaid,grid,gridVal);
            Biz.initSelect("PRV_OPERATOR_BY_GRID&mcode=" + gridVal,operid,operidVal);

            //隐藏网格及人员操作按钮
            this.$form.find(".grid-option").hide();
            //状态为待审核和审核成功时隐藏保存按钮
            var status =  this.$form.find("input[name='status']").val();
            if(status=="1" || status=="2"){
                $(".submitbtn").hide();
            }
        }



		//初始化表格数据
		this.$form.data("formOptions",{
	    	bindEvents : function(){
	    	    var $form = $(this);
                //
                $form.find("#id_city").change(function(e){
                    Biz.initSelect("PRV_AREA_BY_CITY&mcode=" + e.val,areaidobj);
                });
                //支公司变更同时变更网格
                 $form.find("#id_areaid").change(function(e){
                     $form.find("select[name='grids']").each(function(){
                        Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + e.val,$(this));
                    });
                 });
                 //网格变更同时变更网格人员
                 $form.find("#grid_div").on("change","select[name='grids']",function(e){
                    var operidsed = $(this).parent().parent().parent().next().find(".operid_controls").find("select");
                    Biz.initSelect("PRV_OPERATOR_BY_GRID&mcode=" + e.val,operidsed);
                 });
                 //网格变更同时变更网格人员
                 $form.find("#grid_div").on("click",".delete_grid",function(){
                     var length = OthersKpiInputBasic.$form.find(".grid_row").length;
                     if(length>1){
                         $(this).parent().parent().parent().remove();
                     }else{
                         Global.notify("error", "至少保留一项!");
                     }
                 });
	    		 //提交按钮
	    		 var btn_submit = $form.find("button[name='btn_submitKpi']");
				 btn_submit.click(function(){
				    return OthersKpiInputBasic.doSubmit("0");
				 });
				 //提交审核按钮
	    		 var btn_audit_submitKpi = $form.find("button[name='btn_audit_submitKpi']");
                 btn_audit_submitKpi.click(function(){
				    return OthersKpiInputBasic.doSubmit("1");
				 });
	    	}
		});

		//积分明细
        this.$detail.data("gridOptions", {
            url: WEB_ROOT + '/biz/salary/others-kpi!findDetail',
            postData:{
                "othersKpi.grid":this.$form.find("input[name='grid']").val(),
                "othersKpi.dateMonth":dateMonth,
                "othersKpi.operid":this.$form.find("input[name='operid']").val()
            },
            colModel: [
               {
                label: '指标类型',
                name: 'textConfigType',
                editable : true,
                search :  false,
                sortable : false,
                editrules :{required:true},
                width: 100,
                stype : 'select',
                    editoptions : {
                    value : Biz.getCacheParamDatas("SALARY-OTHERS-KPI-TEXT-CONFIG_TYPE","TCJL")
                    // dataEvents:[{
                    //         type:'change',
                    //         fn:function(e){
                    //             var itemName = e.currentTarget.id;
                    //             var value = this.value;
                    //             OthersKpiInputBasic.getTextConfig(itemName,value);
                    //         }
                    // }]
                }
            }, {
                label: '指标项目',
                name: 'textConfigId',
                editable : true,
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
                editable : true,
                search :  false,
                sortable : false,
                width: 90
            }, {
                label: '说明',
                name: 'remark',
                editrules :{required:true},
                editable : true,
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
            },{
                label: '操作',
                name: '',
                editable : false,
                search :  false,
                sortable : false,
                formatter :function(cellvalue,options,rowObject){
                    //状态
                    if(rowObject.status=="1" || rowObject.status=="2") {
                        return "";
                    }
                    return "<button class='btn green' type='button' onclick='OthersKpiInputBasic.rowSave(" + options.rowId + ")'>保存</button>";
                },
                width: 70
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
                var thisGrid = OthersKpiInputBasic.$detail;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
            },
            afterInsertRow :function(rowid,rowdata,rowelem){
                //清空数据项
                OthersKpiInputBasic.$detail.jqGrid('setCell', rowid , "textConfigType" , null);
                OthersKpiInputBasic.$detail.jqGrid('setCell', rowid , "textConfigId" , null);
                OthersKpiInputBasic.$detail.jqGrid('setCell', rowid , "score" , null);
                OthersKpiInputBasic.$detail.jqGrid('setCell', rowid , "remark" , null);
                OthersKpiInputBasic.getTextConfig();
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
         Biz.disableSelect2($city);
         Biz.disableSelect2WhenNotAdmin($city);
	},

    /**
     * 初始化积分项目
     */
    getTextConfig:function(){
        var city = this.$form.find("#id_city").val();
        var areaid = this.$form.find("#id_areaid").val();
        if(areaid == null || areaid==""){
            Global.notify("error", "请先选择支公司!");
            return;
        }
        var params = "TCJL,"+city+","+areaid;
        var obj = Biz.getCacheParamDatas("OTHERS-KPI-TEXT-CONFIG-BY-TYPE",params,null,",");
        var objToStr = JSON.stringify(obj);
        var str = objToStr.replace(/{/g,"").replace(/}/g,"").replace(/,/g,";").replace(/"/g,"");
        OthersKpiInputBasic.$detail.setColProp('textConfigId', {
            editoptions: {value:str}
        })
    },
	/**
	 * 保存
	 */
	doSubmit : function(s){
		//如果是修改
		var $form = this.$form;
		var id = $form.find("#id").val();

        var rowData = OthersKpiInputBasic.$detail.jqGrid('getRowData');
		var textConfigIds = new Array();
		var scores = new Array();
		var remarks = new Array();

		console.log(rowData);
		for (var i in rowData){
            textConfigIds.push(rowData[i].textConfigId);
            scores.push(rowData[i]["score"]);
            remarks.push(rowData[i]["remark"]);
        }
        if(textConfigIds.length==0 || rowData==null){
            Global.notify("error", "请添加明细项!");
            return false;
        }
        if(s=='1'){
            $("#duitModal").modal('hide');
            var auditUser = $("#id_auditUser").val();
            if(auditUser == null || auditUser==""){
                Global.notify("error", "请选择审核人!");
                return false;
            }
        }
        $("input[name='textConfigIds']").val(textConfigIds);
        $("input[name='scores']").val(scores);
        $("input[name='remarks']").val(remarks);
        $("input[name='status']").val(s);


		 return true;
	},
    rowSave:function (rowId) {
        $(".ui-icon-plus").parent().parent().removeClass("ui-state-disabled");
        $(".ui-icon-pencil").parent().parent().removeClass("ui-state-disabled");
        $(".ui-icon-disk").parent().parent().addClass("ui-state-disabled");
        $(".ui-icon-cancel").parent().parent().addClass("ui-state-disabled");
        OthersKpiInputBasic.$detail.jqGrid('saveRow',rowId,{url:"clientArray"});
    },
    addGridDiv:function () {
        var val = OthersKpiInputBasic.$form.find(".grid_row").first().clone(true);
        var gridOption =  $(val).find(".grid_controls").find("select").html();
        var operidOption =  $(val).find(".operid_controls").find("select").html();

        var gridSelect="<select name ='grids' required='true' class='form-control'>"+gridOption+"</select>";
        var operidSelect="<select name ='operids' required='true' class='form-control'>"+operidOption+"</select>";
        $(val).find(".grid_controls").html(gridSelect);
        $(val).find(".operid_controls").html(operidSelect);
        OthersKpiInputBasic.$form.find(".grid_row").last().after(val);
        $(val).find("select").select2();
    }
};
$(function(){
    OthersKpiInputBasic.$form.find("#id_dateMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });

	OthersKpiInputBasic.init();
});