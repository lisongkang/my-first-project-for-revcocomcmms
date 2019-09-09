var SalaryOthersKpiAuditList = {
    $form: $(".form-biz-salary-others-kpi-audit-list"),
    $grid: $(".grid-biz-salary-others-kpi-audit-list"),

    initSearchParam: function(){
        var $form = this.$form;
        var loginCity = Biz.LOGIN_INFO.city;
        var id_grid = $form.find("#grid");
        var id_areaid = $form.find("#areaid");
        var id_operid = $form.find("#operid");

        //初始化支公司
        Biz.initSelect("PRV_AREA_BY_CITY&mcode=" + loginCity,id_areaid);
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
        this.initSearchParam();

        this.$grid.data("gridOptions", {
            url: WEB_ROOT + '/biz/salary/others-kpi-audit!findByPage',
            colModel: [{
                label: '分公司',
                name: 'city',
                search :  false,
                width: 100,
                stype : 'select',
                sortable : false,
                editoptions : {
                    value :SalaryOthersKpiAuditList.getCity()
                }
            }, {
                label: '业务区',
                name: 'areaid',
                search :  false,
                sortable : false,
                width: 90,
                formatter: function (value){
                    return SalaryOthersKpiAuditList.getParam("PRV_AREA_BY_CITY",value);
                }
            }, {
                label: '网格',
                name: 'grid',
                search :  false,
                sortable : false,
                width: 90,
                formatter: function (value){
                    return SalaryOthersKpiAuditList.getParam("PRV_GRID_BY_CITY",value);
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
                label: '提交人',
                name: 'commitUser',
                search :  false,
                sortable : false,
                stype: 'select',
                editoptions: {
                    value: Biz.getCacheParamDatas("PRV_OPERATOR")
                },
                width: 90
            },{
                label: '提交时间',
                name: 'createDate',
                search :  false,
                sortable : false,
                formatter : "date",
                hidden: true,
                width: 90
            }, {
                label: '总积分',
                name: 'scoreCount',
                search :  false,
                sortable : false,
                hidden: true,
                width: 90
            }, {
                label: '状态',
                name: 'status',
                search :  false,
                sortable : false,
                width: 90,
                hidden:true,
                stype: 'select',
                editoptions: {
                    value: Biz.getCacheParamDatas("SALARY-OTHERS-KPI-STATUS")
                }
            }],
            fullediturl : WEB_ROOT + '/biz/salary/others-kpi-audit!inputTabs',
            multiselect: true,
            multiboxonly:true,
            gridComplete: function () {
                var thisGrid = SalaryOthersKpiAuditList.$grid;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
                Biz.hideAddButton(thisGrid);

            },
            operations : function(items) {
               // var btn =  '<div class="dropdown">'+
                //                //   '<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">'+
                //                //   '批量审核'+
                //                //   '<span class="caret"></span>'+
                //                //   '</button>'+
                //                //   '<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">'+
                //                //   '<li><a href="#">审核通过</a></li>'+
                //                //   '<li><a href="#">审核拒绝</a></li>'+
                //                //   '<li role="separator" class="divider"></li>'+
                //                //   '<li><a href="#">Separated link</a></li>'+
                //                //   '</ul>'+
                //                //  '</div>';


                // var auditBtn = Biz.createCustomBtn(false,"批量审核","glyphicon glyphicon-new-window",SalaryOthersKpiAuditList.batchAudit);
                // items.push(btn);
            }

        });
    },
    batchAudit:function(status){
        var ids = "";
        var gridIds = SalaryOthersKpiAuditList.$grid.jqGrid('getGridParam', 'selarrrow');
        for (var i in gridIds){
            var id = SalaryOthersKpiAuditList.$grid.jqGrid('getRowData', gridIds[i]).id;
            ids = ids + "," + id;
        }
        if(ids==""){
            Global.notify("error", "请选择明细项!");
            return;
        }
        // var auditUser = $("#id_batchDuditUser").val();
        $.ajax({
            url:WEB_ROOT + '/biz/salary/others-kpi-audit!batchAudit',
            type:"post",
            dataType:"json",
            contentType:"application/x-www-form-urlencoded",
            data:{"ids":ids.substring(1),"status":status},
            success:function (e) {
                SalaryOthersKpiAuditList.$grid.trigger("reloadGrid");
                if(e.type=="success"){
                    Global.notify("success", "提交成功!");
                }else{
                    Global.notify("error", e.message);
                }
            }
        })
    }

};

$(function () {
    $("#dateMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
    SalaryOthersKpiAuditList.init();
});