var OthersBeforehandRealInputBasic = {
	$form :$(".form-beforehand-real"),
    $detail :$(".grid-biz-salary-beforehand-real-detail"),
	init : function(){
        var operid = this.$form.find("#val_operid").val();
        var status = this.$form.find("#id_status").val();
        var cycleid = this.$form.find("#id_cycleid").val();
        Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + this.$form.find("#id_areaid").val(),
            this.$form.find("#id_grid"),this.$form.find("#val_grid").val());
        var operator = Biz.getCacheParamDatas("PRV_OPERATOR", operid);
        this.$form.find("#show_operid").val(operator[operid]);
        //如果是审核通过则不能修改了
        if(status=="1"){
            OthersBeforehandRealInputBasic.$form.find("#btn_edit").hide();
            OthersBeforehandRealInputBasic.$form.find(".submitbtn").hide();
        }

		//积分明细
        this.$detail.data("gridOptions", {
            url: WEB_ROOT + '/biz/salary/beforehand-real!findDetail',
            postData:{
                "beforehandRealBO.operid":operid,
                "beforehandRealBO.status":status,
                "beforehandRealBO.cycleid":cycleid
            },
            colModel: [{
                label: 'srcid',
                name: 'srcid',
                editable : false,
                search :  false,
                sortable : false,
                hidden: true,
                width: 100,

            },{
                label: '操作日期',
                name: 'optime',
                editable : false,
                search :  false,
                sortable : false,
                width: 100,

            },{
                label: '客户姓名',
                name: 'custname',
                editable : false,
                search :  false,
                sortable : false,
                width: 100,

            }, {
                label: '积分类型',
                name: 'groupcode',
                editable : true,
                search :  false,
                sortable : false,
                editrules :{required:true},
                required:true,
                width: 90,
                stype : 'select',
                editoptions : {
                      value : {"ZSBUSI":"销售积分","ZSSETUP":"安装积分"}
                }
            }, {
                label: '产品',
                name: 'pcodename',
                number: true,
                editable : false,
                search :  false,
                sortable : false,
                width: 100
            },{
                label: '个人预积分',
                name: 'srccents',
                number: true,
                editable : false,
                search :  false,
                sortable : false,
                width: 100
            }, {
                label: '分享积分',
                name: 'sharecents',
                editable : false,
                search :  false,
                sortable : false,
                width: 80
            },{
                label: '调整积分',
                name: 'adjustcents',
                editable : false,
                search :  false,
                sortable : false,
                width: 80
            }, {
                label: '说明',
                name: 'reason',
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
                    value: {"0":"待审核","1":"审核通过","2":"审核失败"}
                },
                search :  false,
                sortable : false,
                width: 80
            }],
            height: 'auto',
            rowNum: -1,
            pager: '',
            pgbuttons: false,
            pginput:false,
            editurl:"clientArray",
            delurl:"clientArray",
            inlineNav:{
                save:false
            },
            gridComplete: function () {
                var thisGrid = OthersBeforehandRealInputBasic.$detail;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
                Biz.hideAddButton(thisGrid);
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
         // Biz.disableSelect2WhenNotAdmin($city);
	},
    show:function(e){
	    if(e=="2"){
            var ids = OthersBeforehandRealInputBasic.$detail.jqGrid('getGridParam', 'selarrrow');
            if(ids.length>1 || ids.length==0){
                Global.notify("error", "明细项只能单条调整!");
                return false;
            }
            var srccents = OthersBeforehandRealInputBasic.$detail.jqGrid('getRowData', ids[0]).srccents;
            var srcid = OthersBeforehandRealInputBasic.$detail.jqGrid('getRowData', ids[0]).srcid;
            var groupcode = OthersBeforehandRealInputBasic.$detail.jqGrid('getRowData', ids[0]).groupcode;
            OthersBeforehandRealInputBasic.$form.find("#id_srcid").val(srcid);
            OthersBeforehandRealInputBasic.$form.find("#id_srccents").val(srccents);
            OthersBeforehandRealInputBasic.$form.find("#id_type").val(groupcode);
            OthersBeforehandRealInputBasic.$form.find("#srcid").show();
            OthersBeforehandRealInputBasic.$form.find("#groupcode").hide();
        }else{
            OthersBeforehandRealInputBasic.$form.find("#id_srcid").val("");
            OthersBeforehandRealInputBasic.$form.find("#id_srccents").val("");
            OthersBeforehandRealInputBasic.$form.find("#id_type").val("");
            OthersBeforehandRealInputBasic.$form.find("#srcid").hide();
            OthersBeforehandRealInputBasic.$form.find("#groupcode").show();
        }
        OthersBeforehandRealInputBasic.$form.find("#addModal").modal('show');
    },
    updateCents:function(){
        var cent = OthersBeforehandRealInputBasic.$form.find("#id_cent").val();
	    if(cent==null || cent==""){
            Global.notify("error", "请输入调整分数!");
            return false;
        }
        var param = new Object();
        param["adjustment.groupcode"] =  OthersBeforehandRealInputBasic.$form.find("#id_type").val();
        param["adjustment.cent"] = cent;
        param["adjustment.memo"] = OthersBeforehandRealInputBasic.$form.find("#id_memo").val();
        param["adjustment.srcid"] =  OthersBeforehandRealInputBasic.$form.find("#id_srcid").val();
        param["adjustment.objvalue"] =  OthersBeforehandRealInputBasic.$form.find("#val_operid").val();

        $.ajax({
            url: WEB_ROOT + '/biz/salary/beforehand-real!updateSrccents',
            type: 'POST',
            cache: false,
            data: param,
            dataType:'json',
            success : function(response) {
                if (response.type == "success") {
                    OthersBeforehandRealInputBasic.$form.find("#addModal").modal('hide');
                    Global.notify("success",response.message);
                    OthersBeforehandRealInputBasic.$detail.trigger("reloadGrid");
                } else {
                    Global.notify("error", response.message);
                }
            }
        });
    },
	/**
	 * 保存
	 */
	doSubmit : function(){
        var auditUser = OthersBeforehandRealInputBasic.$form.find("#id_auditUser").val();
        if(auditUser == null || auditUser==""){
            Global.notify("error", "请选择审核人!");
            return false;
        }
        OthersBeforehandRealInputBasic.$form.find("#duitModal").modal('hide');
		//如果是修改
		// var $form = this.$form;
		// var id = $form.find("#id").val();
        //
        // var rowData = OthersBeforehandRealInputBasic.$detail.jqGrid('getRowData');
		// var textConfigIds = new Array();
		// var scores = new Array();
		// var remarks = new Array();
        //
		// console.log(rowData);
		// for (var i in rowData){
         //    textConfigIds.push(rowData[i].textConfigId);
         //    scores.push(rowData[i]["score"]);
         //    remarks.push(rowData[i]["remark"]);
        // }
        // if(textConfigIds.length==0 || rowData==null){
         //    Global.notify("error", "请添加明细项!");
         //    return false;
        // }
        // if(s=='1'){
         //    OthersBeforehandRealInputBasic.$form.find("#duitModal").modal('hide');

        // }
        // $("input[name='textConfigIds']").val(textConfigIds);
        // $("input[name='scores']").val(scores);
        // $("input[name='remarks']").val(remarks);
        // $("input[name='status']").val(s);


		 return true;
	},
    rowSave:function (rowId) {
        $(".ui-icon-plus").parent().parent().removeClass("ui-state-disabled");
        $(".ui-icon-pencil").parent().parent().removeClass("ui-state-disabled");
        $(".ui-icon-disk").parent().parent().addClass("ui-state-disabled");
        $(".ui-icon-cancel").parent().parent().addClass("ui-state-disabled");
        OthersBeforehandRealInputBasic.$detail.jqGrid('saveRow',rowId,{url:"clientArray"});
    }
};
$(function(){

	OthersBeforehandRealInputBasic.init();
});