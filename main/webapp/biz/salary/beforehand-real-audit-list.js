var SalaryBeforehandRealAuditList = {
    $form: $(".form-biz-salary-beforehand-real-audit-list"),
    $grid: $(".grid-biz-salary-beforehand-real-audit-list"),

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
    checkInput : function(){
        var $form = SalaryBeforehandRealAuditList.$form;
        var text = "";
        // if (this.checkNull($form.find('#areaid').val())) {
        //     text += "业务区、";
        // }
        // if (this.checkNull($form.find('#grid').val())) {
        //     text += "网格、";
        // }
        // if (this.checkNull($form.find('#dateMonth').val())) {
        //     text += "月份、";
        // }
         if (this.checkNull($form.find('#status').val())) {
            text += "状态、";
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
        // this.initSearchParam();
        this.$grid.data("gridOptions", {
            url: WEB_ROOT + '/biz/salary/beforehand-real-audit!findByPage',
            colModel: [{
                label: '分公司',
                name: 'city',
                search :  false,
                sortable : false,
                width: 100,
                stype : 'select',
                editoptions : {
                    value :SalaryBeforehandRealAuditList.getCity()
                }
            }, {
                label: '业务区',
                name: 'areaid',
                search :  false,
                sortable : false,
                width: 90,
                stype : 'select',
                editoptions:{
                    value:  Biz.getCacheParamDatas("PRV_AREA_BY_CITY")
                }
            }, {
                label: '网格',
                name: 'grid',
                search :  false,
                sortable : false,
                width: 90,
                stype : 'select',
                editoptions:{
                     value:  Biz.getCacheParamDatas("PRV_GRID_BY_CITY")
                    // return SalaryBeforehandRealAuditList.getParam("PRV_GRID_BY_CITY",value);
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
                name: 'cycleid',
                search :  false,
                sortable : false,
                width: 150
            }, {
                label: '个人积分',
                name: 'realcents',
                search :  false,
                sortable : false,
                width: 90
            }, {
                label: '增减积分',
                name: 'adjustcents',
                search :  false,
                sortable : false,
                width: 90
            }, {
                label: '分享积分',
                name: 'sharecents',
                search :  false,
                sortable : false,
                width: 90
            }, {
                label: '状态',
                name: 'status',
                search :  false,
                sortable : false,
                width: 90,
                stype : 'select',
                editoptions: {
                    value: {"0":"待审核","1":"审核通过","2":"审核失败"}
                }
            }],
            fullediturl : WEB_ROOT + '/biz/salary/beforehand-real-audit!inputTabs',
            multiselect: true,
            multiboxonly:true,
            inlineNav:{
                save:false
            },
            // delurl : WEB_ROOT + '/biz/salary/beforehand-real-audit!doDelete',
            gridComplete: function () {
                var thisGrid = SalaryBeforehandRealAuditList.$grid;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
                Biz.hideAddButton(thisGrid);
            },
            // ondblClickRow : function(rowid,iRow,iCol,e){
            //     var data = SalaryBeforehandRealAuditList.$grid.jqGrid('getRowData', rowid);
            //     var url = WEB_ROOT + '/biz/salary/beforehand-real-audit!inputTabs';
            //     url = Util.AddOrReplaceUrlParameter(url, "city", data.city);
            //     url = Util.AddOrReplaceUrlParameter(url, "areaid", data.areaid);
            //     url = Util.AddOrReplaceUrlParameter(url, "grid", data.grid);
            //     url = Util.AddOrReplaceUrlParameter(url, "operid", data.operid);
            //     url = Util.AddOrReplaceUrlParameter(url, "cycleid", data.cycleid);
            //     url = Util.AddOrReplaceUrlParameter(url, "status", data.status);
            //     var nav = $(this).closest(".tabbable").find(" > .nav");
            //     Global.addOrActiveTab(nav, {
            //         title: "编辑: " + rowid,
            //         url: url
            //     })
            // },
            operations : function(items) {
                var auditBtn = Biz.createCustomBtn(false,"批量审核","glyphicon glyphicon-new-window",function(){
                    SalaryBeforehandRealAuditList.$form.find("#batchDuitModal").modal('show')
                });
                items.push(auditBtn);
            }

        });
    },

    batchAudit : function(e){
        var ids = "";
        var gridIds = SalaryBeforehandRealAuditList.$grid.jqGrid('getGridParam', 'selarrrow');
        for (var i in gridIds){
            var status = SalaryBeforehandRealAuditList.$grid.jqGrid('getRowData', gridIds[i]).status;
            if(status!="0"){
                Global.notify("error", "已审批通过或审批失败的无法再次审批!");
                return false;
            }
            var id = SalaryBeforehandRealAuditList.$grid.jqGrid('getRowData', gridIds[i]).id;
            ids = ids + "," + id;
        }
        if(ids==""){
            Global.notify("error", "请选择明细项!");
            return;
        }
        $.ajax({
            url:WEB_ROOT + '/biz/salary/beforehand-real-audit!batchUpdate',
            type:"post",
            dataType:"json",
            contentType:"application/x-www-form-urlencoded",
            data:{"ids":ids.substring(1),"status":e},
            success:function (e) {
                SalaryBeforehandRealAuditList.$grid.trigger("reloadGrid");
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
    SalaryBeforehandRealAuditList.$form.find("#dateMonth").datetimepicker({
        format: 'yyyymm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
    SalaryBeforehandRealAuditList.initSearchParam();
    SalaryBeforehandRealAuditList.init();
});