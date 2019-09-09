var SalaryBeforehandRealList = {
    $form: $(".form-biz-salary-beforehand-real-list"),
    $grid: $(".grid-biz-salary-beforehand-real-list"),

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
        var $form = SalaryBeforehandRealList.$form;
        var text = "";
        if (this.checkNull($form.find('#areaid').val())) {
            text += "业务区、";
        }
        if (this.checkNull($form.find('#grid').val())) {
            text += "网格、";
        }
        if (this.checkNull($form.find('#dateMonth').val())) {
            text += "月份、";
        }
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
            url: WEB_ROOT + '/biz/salary/beforehand-real!findByPage',
            colModel: [{
                label: '分公司',
                name: 'city',
                search :  false,
                sortable : false,
                width: 100,
                stype : 'select',
                editoptions : {
                    value :SalaryBeforehandRealList.getCity()
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
                    // return SalaryBeforehandRealList.getParam("PRV_GRID_BY_CITY",value);
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
            fullediturl : WEB_ROOT + '/biz/salary/beforehand-real!inputTabs',
            multiselect: true,
            multiboxonly:true,
            jsonReader:{"id":"rn"},
            inlineNav:{
                save:false
            },
            // delurl : WEB_ROOT + '/biz/salary/beforehand-real!doDelete',
            gridComplete: function () {
                var thisGrid = SalaryBeforehandRealList.$grid;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
                $("#gbox_"+ thisGrid.attr("id")).find(".fa-plus-square").parent().hide();
                // Biz.hideAddButton(thisGrid);
            },
            ondblClickRow : function(rowid,iRow,iCol,e){
                var data = SalaryBeforehandRealList.$grid.jqGrid('getRowData', rowid);
                var url = WEB_ROOT + '/biz/salary/beforehand-real!inputTabs';
                url = Util.AddOrReplaceUrlParameter(url, "city", data.city);
                url = Util.AddOrReplaceUrlParameter(url, "areaid", data.areaid);
                url = Util.AddOrReplaceUrlParameter(url, "grid", data.grid);
                url = Util.AddOrReplaceUrlParameter(url, "operid", data.operid);
                url = Util.AddOrReplaceUrlParameter(url, "cycleid", data.cycleid);
                url = Util.AddOrReplaceUrlParameter(url, "status", data.status);
                var nav = $(this).closest(".tabbable").find(" > .nav");
                Global.addOrActiveTab(nav, {
                    title: "编辑: " + rowid,
                    url: url
                })
            },
            operations : function(items) {
                var auditBtn = Biz.createCustomBtn(false,"批量审核","glyphicon glyphicon-new-window",function(){$("#batchDuitModal").modal('show')});
                items.push(auditBtn);
            }

        });
    },

    batchAudit : function(){
        var citys = "";
        var areaids = "";
        var grids = "";
        var operids = "";
        var cycleids = "";
        var gridIds = SalaryBeforehandRealList.$grid.jqGrid('getGridParam', 'selarrrow');
        for (var i in gridIds){
            var status = SalaryBeforehandRealList.$grid.jqGrid('getRowData', gridIds[i]).status;
            if(status=="1"){
                Global.notify("error", "审核通过无法再次提交审核!");
                return false;
            }
            var city = SalaryBeforehandRealList.$grid.jqGrid('getRowData', gridIds[i]).city;
            var areaid = SalaryBeforehandRealList.$grid.jqGrid('getRowData', gridIds[i]).areaid;
            var grid = SalaryBeforehandRealList.$grid.jqGrid('getRowData', gridIds[i]).grid;
            var operid = SalaryBeforehandRealList.$grid.jqGrid('getRowData', gridIds[i]).operid;
            var cycleid = SalaryBeforehandRealList.$grid.jqGrid('getRowData', gridIds[i]).cycleid;
            citys = citys + "," + city;
            areaids = areaids + "," + areaid;
            grids = grids + "," + grid;
            operids = operids + "," + operid;
            cycleids = cycleids + "," + cycleid;

        }
        var auditUser = $("#id_batchDuditUser").val();
        $.ajax({
            url:WEB_ROOT + '/biz/salary/beforehand-real-audit!batchAudit',
            type:"post",
            dataType:"json",
            contentType:"application/x-www-form-urlencoded",
            data:{"citys":citys.substring(1),"areaids":areaids.substring(1),
                  "grids":grids.substring(1),"cycleids":cycleids.substring(1),
                  "operids":operids.substring(1),"auditUser":auditUser},
            success:function (e) {
                $("#batchDuitModal").modal('hide');
                SalaryBeforehandRealList.$grid.trigger("reloadGrid");
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
    SalaryBeforehandRealList.$form.find("#dateMonth").datetimepicker({
        format: 'yyyymm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
    SalaryBeforehandRealList.initSearchParam();
    SalaryBeforehandRealList.init();
});