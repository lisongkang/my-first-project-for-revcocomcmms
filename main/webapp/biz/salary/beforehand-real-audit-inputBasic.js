var OthersBeforehandRealAuditInputBasic = {
	$form :$(".form-beforehand-real-audit"),
    $detail :$(".grid-biz-salary-beforehand-real-detail"),
	init : function(){
        var operid = this.$form.find("#val_operid").val();
        var status = this.$form.find("#id_status").val();
        var cycleid = this.$form.find("#id_cycleid").val();
        Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + this.$form.find("#id_areaid").val(),this.$form.find("#id_grid"),this.$form.find("#val_grid").val());
        var operator = Biz.getCacheParamDatas("PRV_OPERATOR", operid);
        this.$form.find("#show_operid").val(operator[operid]);
        //如果是审核通过,审核拒绝则不能修改了
        if(status=="1"|| status=='2'){
            this.$form.find(".submitbtn").hide();
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
            multiselect:false,
            pginput:false,
            editurl:"clientArray",
            delurl:"clientArray",
            inlineNav:{
                save:false
            },
            gridComplete: function () {
                var thisGrid = OthersBeforehandRealAuditInputBasic.$detail;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
                Biz.hideAddButton(thisGrid);
                OthersBeforehandRealAuditInputBasic.$detail.jqGrid('hideCol','cb');
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

	/**
	 * 保存
	 */
	doSubmit : function(status){

        OthersBeforehandRealAuditInputBasic.$form.find("#id_status").val(status);
		//如果是修改
		// var $form = this.$form;
		// var id = $form.find("#id").val();
        //
        // var rowData = OthersBeforehandRealAuditInputBasic.$detail.jqGrid('getRowData');
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
         //    $("#duitModal").modal('hide');

        // }
        // $("input[name='textConfigIds']").val(textConfigIds);
        // $("input[name='scores']").val(scores);
        // $("input[name='remarks']").val(remarks);
        // $("input[name='status']").val(s);


		 return true;
	}
};
$(function(){
	OthersBeforehandRealAuditInputBasic.init();
});