var SalaryOthersKpiList = {
    $form: $(".form-biz-salary-others-kpi-list"),
    $grid: $(".grid-biz-salary-others-kpi-list"),
    $excelgrid: $(".grid-biz-salary-others-kpi-Excel"),
    $modal: $("#batchUploadModal"),

    initSearchParam: function(){
        var $form = this.$form;
        var $modal = this.$modal;
        var loginCity = Biz.LOGIN_INFO.city;
        var id_grid = $form.find("#grid");
        var id_operid = $form.find("#operid");
        var $excel_city = $modal.find("#excel_city");
        var $excel_areaid = $modal.find("#excel_areaid");

        //初始化支公司
        Biz.initSelect("PRV_AREA_BY_CITY&mcode=" + loginCity,$form.find("#areaid"));
        //导入excel地市下拉禁用
        setTimeout(function(){
            $excel_city.select2("val", loginCity);
            Biz.disableSelect2($excel_city);
            Biz.initSelect("PRV_AREA_BY_CITY&mcode=" + loginCity,$excel_areaid);
        },500);

        //初始化表格数据
        $form.data("formOptions",{
            bindEvents : function(){
                var $form = $(this);
                //支公司变更同时变更网格
                $form.find("#areaid").change(function(e){
                    Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + e.val,id_grid);
                });
                //网格变更同时变更网格人员
                $form.find("#grid").change(function(e){
                    Biz.initSelect("PRV_OPERATOR_BY_GRID&mcode=" + e.val,id_operid);
                });
            }
        });
        // $modal.data("formOptions",{
        //     bindEvents : function(){
        //         var $form = $(this);
        //         //支公司变更同时变更网格
        //         $form.find("#areaid").change(function(e){
        //             Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + e.val,id_grid);
        //         });
        //         //网格变更同时变更网格人员
        //         $form.find("#grid").change(function(e){
        //             Biz.initSelect("PRV_OPERATOR_BY_GRID&mcode=" + e.val,id_operid);
        //         });
        //     }
        // });
    },
    checkRequired : function(){
        return true;
    },
    getCity:function(){
        var cityList = Biz.getCacheParamDatas("PRV_CITY");
        cityList['*'] = "所有";
        return cityList;
    },

    getParam:function(type,e){
        if(e=="*"){
            return "所有";
        }
        var result = "";
        var areaList = Biz.getCacheParamDatas(type);
        var areaids = e.split(",");
        for (var i in areaids) {
            var areaid = areaids[i];
            result = result + "," + areaList[areaid];
        }
        return result.substring(1);
    },

    init: function () {
        this.$excelgrid.data("gridOptions", {
            url: WEB_ROOT + '/biz/salary/others-kpi!findExcelTempByPage',
            colModel: [
                {
                    label: '网格名称',
                    name: 'gridName',
                    number: true,
                    editrules :{required:true},
                    editable : true,
                    search :  false,
                    sortable : false,
                    width: 90
                }, {
                    label: '网格人员账号',
                    name: 'account',
                    number: true,
                    editrules :{required:true},
                    editable : true,
                    search :  false,
                    sortable : false,
                    width: 90
                }, {
                    label: '月份',
                    name: 'dateMonth',
                    number: true,
                    editrules :{required:true},
                    editable : true,
                    search :  false,
                    sortable : false,
                    width: 90
                }, {
                    label: '积分类型',
                    name: 'configType',
                    editable : true,
                    search :  false,
                    sortable : false,
                    editrules :{required:true},
                    required:true,
                    width: 90
                    // stype : 'select',
                    // editoptions : {
                    //     value: Biz.getCacheParamDatas("OTHERS-KPI-TEXT-CONFIG-ALL")
                    // }
                }, {
                    label: '积分项目',
                    name: 'configContext',
                    editable : true,
                    search :  false,
                    sortable : false,
                    editrules :{required:true},
                    required:true,
                    width: 90
                    // stype : 'select',
                    // editoptions : {
                    //     value: Biz.getCacheParamDatas("OTHERS-KPI-TEXT-CONFIG-ALL")
                    // }
                },{
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
                    editable : false,
                    search :  false,
                    sortable : false,
                    width: 80
                },{
                    label: '是否通过',
                    name: 'checkFlag',
                    editable : false,
                    search :  false,
                    sortable : false,
                    width: 80,
                    formatter:function (e) {
                        if(e=="0"){
                            return "<span style='color: red;'>未通过</span>"
                        }
                        if(e=="1"){
                            return "<span style='color: green;'>通过</span>"
                        }
                    }
                }, {
                    label: '检查结果',
                    name: 'errorMessage',
                    editable : false,
                    search :  false,
                    sortable : false,
                    width: 100
                }],
            height: 'auto',
            // rowNum: -1,
            // pager: '',
            // pgbuttons: false,
            // pginput:false,
            editurl:WEB_ROOT + '/biz/salary/others-kpi!excelUpdate',
            delurl:WEB_ROOT + '/biz/salary/others-kpi!excelDelete',
            inlineNav:{
                editParams:{
                    aftersavefunc: function( rowid, response ){
                        SalaryOthersKpiList.$excelgrid.trigger("reloadGrid");
                        return true;
                    }
                }
            },
            gridComplete: function () {
                var thisGrid = SalaryOthersKpiList.$excelgrid;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
                Biz.hideTableElement(thisGrid, ".ui-icon-plus");
                Biz.hideTableElement(thisGrid, ".ui-icon-pencil");
            }

        });
        this.initSearchParam();

        this.$grid.data("gridOptions", {
            url: WEB_ROOT + '/biz/salary/others-kpi!findByPage',
            colModel: [{
                label: '分公司',
                name: 'city',
                search :  false,
                sortable : false,
                width: 100,
                stype : 'select',
                editoptions : {
                    value :SalaryOthersKpiList.getCity()
                }
            }, {
                label: '业务区',
                name: 'areaid',
                search :  false,
                sortable : false,
                width: 90,
                formatter: function (value){
                    return SalaryOthersKpiList.getParam("PRV_AREA_BY_CITY",value);
                }
            }, {
                label: '网格',
                name: 'grid',
                search :  false,
                sortable : false,
                width: 90,
                formatter: function (value){
                    return SalaryOthersKpiList.getParam("PRV_GRID_BY_CITY",value);
                }
            }, {
                label: '网格人员',
                name: 'operid',
                search :  false,
                sortable : false,
                width: 80,
                stype: 'select',
                editoptions: {
                    value: Biz.getCacheParamDatas("PRV_OPERATOR")
                }
            }, {
                label: '月份',
                name: 'dateMonth',
                search :  false,
                sortable : false,
                width: 150
            }, {
                label: '总积分',
                name: 'scoreCount',
                search :  false,
                sortable : false,
                width: 90
            }, {
                label: '状态',
                name: 'status',
                search :  false,
                sortable : false,
                width: 90,
                stype: 'select',
                editoptions: {
                    value: Biz.getCacheParamDatas("SALARY-OTHERS-KPI-STATUS")
                }
            }],
            fullediturl : WEB_ROOT + '/biz/salary/others-kpi!inputTabs',
            multiselect: true,
            multiboxonly:true,
            delurl : WEB_ROOT + '/biz/salary/others-kpi!doDelete',
            gridComplete: function () {
                var thisGrid = SalaryOthersKpiList.$grid;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
            },
            operations : function(items) {
                var auditBtn = Biz.createCustomBtn(false,"批量审核","glyphicon glyphicon-new-window", function(){$("#batchAuditModal").modal('show');});
                var importBtn = Biz.createCustomBtn(false,"批量导入","glyphicon glyphicon-open", SalaryOthersKpiList.excelList);
                var exportBtn = Biz.createCustomBtn(false,"模板下载","glyphicon glyphicon-save", SalaryOthersKpiList.excelDown);
                items.push(auditBtn);
                items.push(importBtn);
                items.push(exportBtn);
            }

        });
    },
    excelList: function(){
        SalaryOthersKpiList.$modal.modal('show');
    },

    batchAudit : function(){
        var ids = "";
        var gridIds = SalaryOthersKpiList.$grid.jqGrid('getGridParam', 'selarrrow');
        for (var i in gridIds){
            var id = SalaryOthersKpiList.$grid.jqGrid('getRowData', gridIds[i]).id;
            ids = ids + "," + id;
        }
        if(ids==""){
            Global.notify("error", "请选择审核项!");
            return;
        }
        var auditUser = $("#id_batchAuditUser").val();
        $.ajax({
            url:WEB_ROOT + '/biz/salary/others-kpi!batchAudit',
            type:"post",
            dataType:"json",
            contentType:"application/x-www-form-urlencoded",
            data:{"ids":ids.substring(1),"auditUser":auditUser},
            success:function (e) {
                $("#batchAuditModal").modal('hide');
                SalaryOthersKpiList.$grid.trigger("reloadGrid");
                if(e.type=="success"){
                    Global.notify("success", "提交成功!");
                }else{
                    Global.notify("error", e.message);
                }
            }
        })

    },
    batchImport : function(){
        var auditUser = SalaryOthersKpiList.$modal.find("#id_batchUploadAuditUser").val();
        var city = SalaryOthersKpiList.$modal.find("#excel_city").val();
        var areaid = SalaryOthersKpiList.$modal.find("#excel_areaid").val();

        if(city == null || city==""){
            Global.notify("error", "请选择分公司!");
            return;
        }
        if(areaid == null || areaid==""){
            Global.notify("error", "请选择支公司!");
            return;
        }
        var formData = new FormData();
        formData.append("myFile",$("#id_importExcel")[0].files[0]);
        formData.append("auditUser",auditUser);
        formData.append("city",city);
        formData.append("areaid",areaid);
        if($("#id_importExcel").val()){
            $.ajax({
                url: WEB_ROOT + '/biz/salary/others-kpi!importExcelData',
                type: 'POST',
                cache: false,
                data: formData,
                processData: false,
                contentType: false,
                dataType:'json',
                success : function(response) {
                    if (response.type == "success") {
                        // $("#batchUploadModal").modal('hide');
                        // Global.notify("success",response.message);
                        SalaryOthersKpiList.$excelgrid.trigger("reloadGrid");
                    } else {
                        $("#id_importExcel").val("");
                        Global.notify("error", response.message);
                    }
                }
            });
        } else{
            Global.notify("error", "请选择Excel文件");
        }

    },
    uploadExcel : function(){
        var auditUser = SalaryOthersKpiList.$modal.find("#id_batchUploadAuditUser").val();
        if(auditUser == null || auditUser==""){
            Global.notify("error", "请选择审核人!");
            return;
        }
        $.ajax({
            url:WEB_ROOT + '/biz/salary/others-kpi!uploadExcelTemp',
            type:"post",
            data:{"auditUser":auditUser},
            dataType:"json",
            contentType:"application/x-www-form-urlencoded",
            success:function (e) {
                if(e.type=="success"){
                    SalaryOthersKpiList.$modal.modal('hide');
                    Global.notify("success", "上传成功!");
                    SalaryOthersKpiList.$grid.trigger("reloadGrid");
                    SalaryOthersKpiList.$excelgrid.trigger("reloadGrid");
                }else{
                    Global.notify("error", e.message);
                }
            }
        })
    },
    excelDown : function () {
        window.location.href=WEB_ROOT + '/biz/salary/others-kpi!othersTemplate?templateFileName=othersKpiTemplate.xls';
    }

};

$(function () {
    SalaryOthersKpiList.init();
});