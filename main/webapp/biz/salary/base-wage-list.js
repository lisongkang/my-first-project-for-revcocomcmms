var SalaryBaseWageList = {
    $form: $(".form-biz-salary-base-wage-list"),
    $grid: $(".grid-biz-salary-base-wage-list"),

    initSearchParam: function(){
        var $form = this.$form;
        //初始化支公司
        var loginCity = Biz.LOGIN_INFO.city;
        var areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + loginCity; // 否则只能查询所属地市的业务区
        var areaid =$form.find("#areaid");
        var id_grid = $form.find("#grid");
        var id_operid = $form.find("#operid");
        Biz.initSelect(areaSelectGcode,areaid);

        // //初始化类型
        // var id_type =$form.find("#id_type");
        // Biz.initSelect('SALARY_EXPLICATION_TYPE',id_type);
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
    getArea:function(e){
        if(e=="*"){
            return "所有";
        }
        var result = "";
        var areaList = Biz.getCacheParamDatas("PRV_AREA_BY_CITY");
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
            url: WEB_ROOT + '/biz/salary/base-wage!findByPage',
            colModel: [{
                label: '分公司',
                name: 'city',
                search :  false,
                width: 100,
                stype : 'select',
                sortable : false,
                editoptions : {
                    value :SalaryBaseWageList.getCity()
                }
            }, {
                label: '业务区',
                name: 'areaid',
                search :  false,
                sortable : false,
                width: 90,
                formatter: function (value){
                    return SalaryBaseWageList.getArea(value);
                }
            }, {
                label: '网格',
                name: 'grid',
                search :  false,
                sortable : false,
                width: 90,
                stype: 'select',
                editoptions: {
                    value: Biz.getCacheParamDatas("PRV_GRID_BY_CITY")
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
            },{
                label: '月份',
                name: 'dateMonth',
                search :  false,
                sortable : false,
                width: 80
            }, {
                label: '运维薪酬',
                name: 'achievementAmount',
                search :  false,
                sortable : false,
                width: 80
            }, {
                label: '基本薪酬',
                name: 'amount',
                search :  false,
                sortable : false,
                width: 90
            },{
                label: 'achievementId',
                name: 'achievementId',
                search :  true,
                sortable : false,
                hidden:true,
                key:true,
                width: 90
            }],
            fullediturl : WEB_ROOT + '/biz/salary/base-wage!inputTabs',
            multiselect: true,
            multiboxonly:true,
            delurl : WEB_ROOT + '/biz/salary/achievement-bonus!doDelete',
            ondblClickRow : function(rowid,iRow,iCol,e){
                var data = SalaryBaseWageList.$grid.jqGrid('getRowData', rowid);
                var url = WEB_ROOT + '/biz/salary/base-wage!inputTabs';
                console.log(data);
                url = Util.AddOrReplaceUrlParameter(url, "id", data.id);
                url = Util.AddOrReplaceUrlParameter(url, "achievementId", data.achievementId);
                var nav = $(this).closest(".tabbable").find(" > .nav");
                Global.addOrActiveTab(nav, {
                    title: "编辑: " + rowid,
                    url: url
                })
            },
            gridComplete: function () {
                var thisGrid = SalaryBaseWageList.$grid;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
            }


        });
    },
    excelDown : function (type) {
        if(type=="1"){
            window.location.href=WEB_ROOT + '/biz/salary/others-kpi!othersTemplate?templateFileName=baseWageTemplate.xls';
        }else{
            window.location.href=WEB_ROOT + '/biz/salary/others-kpi!othersTemplate?templateFileName=achievementTemplate.xls';
        }
    },

    excelImport : function(type){
        if(type=="1"){
            Biz.excelUpload( WEB_ROOT + '/biz/salary/base-wage!importExcelData',"",function () {
                SalaryBaseWageList.$grid.trigger("reloadGrid");
            });
        }else{
            Biz.excelUpload( WEB_ROOT + '/biz/salary/achievement-bonus!importExcelData',"",function () {
                SalaryBaseWageList.$grid.trigger("reloadGrid");
            });
        }
    },


};

$(function () {
    SalaryBaseWageList.$form.find("#dateMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
    SalaryBaseWageList.init();
});