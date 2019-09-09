var SalaryOthersExchangeConfigList = {
    $form: $(".form-biz-salary-others-exchange-config-list"),
    $grid: $(".grid-biz-salary-others-exchange-config-list"),

    initSearchParam: function(){
        var $form = this.$form;
        var loginCity = Biz.LOGIN_INFO.city;
        var id_type = $form.find("#id_type");
        var id_grid = $form.find("#grid");
        var id_areaid = $form.find("#areaid");
        //初始化支公司
        Biz.initSelect("PRV_AREA_BY_CITY&mcode=" + loginCity,id_areaid);
        //初始化类型

        Biz.initSelect('SALARY_OTHERS_EXCHANGE_CONFIG_TYPE',id_type);
        //初始化表格数据
        $form.data("formOptions",{
            bindEvents : function(){
                var $form = $(this);
                //支公司变更同时变更网格
                $form.find("#areaid").change(function(e){
                    Biz.initSelect("PRV_GRID_BY_AREAID&mcode=" + e.val,id_grid);
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
            url: WEB_ROOT + '/biz/salary/others-exchange-config!findByPage',
            colModel: [{
                label: '分公司',
                name: 'city',
                search :  false,
                width: 100,
                sortable : false,
                stype : 'select',
                editoptions : {
                    value :SalaryOthersExchangeConfigList.getCity()
                }
            }, {
                label: '业务区',
                name: 'areaid',
                search :  false,
                sortable : false,
                width: 90,
                formatter: function (value){
                    return SalaryOthersExchangeConfigList.getParam("PRV_AREA_BY_CITY",value);
                }
            }, {
                label: '类型',
                name: 'type',
                search :  false,
                sortable : false,
                width: 80,
                stype: 'select',
                editoptions: {
                    value: Biz.getCacheParamDatas("SALARY_OTHERS_EXCHANGE_CONFIG_TYPE")
                }
            },{
                label: '生效年月',
                name: 'sdateMonth',
                search :  false,
                sortable : false,
                width: 80
            },{
                label: '截至年月',
                name: 'edateMonth',
                search :  false,
                sortable : false,
                width: 80
            },{
                label: '起始控制线',
                name: 'scontrol',
                search :  false,
                sortable : false,
                formatter:function(e){
                    if(e!=null){
                        return parseFloat((e*100).toPrecision(12))+"%";
                    }
                    return "";
                },
                width: 80
            },{
                label: '截至控制线',
                name: 'econtrol',
                search :  false,
                sortable : false,
                formatter:function(e){
                    if(e!=null){
                        return parseFloat((e*100).toPrecision(12))+"%";
                    }
                    return "";
                },
                width: 80
            },{
                label: '最低保有率',
                name: 'minCentsPrice',
                search :  false,
                sortable : false,
                formatter:function(e){
                    if(e!=null){
                        return parseFloat((e*100).toPrecision(12))+"%";
                    }
                },
                width: 80
            },{
                label: '最高保有率',
                name: 'maxCentsPrice',
                search :  false,
                sortable : false,
                formatter:function(e){
                    if(e!=null){
                        return parseFloat((e*100).toPrecision(12))+"%";
                    }
                },
                width: 80
            }],
            fullediturl : WEB_ROOT + '/biz/salary/others-exchange-config!inputTabs',
            multiselect: true,
            multiboxonly:true,
            delurl : WEB_ROOT + '/biz/salary/others-exchange-config!doDelete',
            gridComplete: function () {
                var thisGrid = SalaryOthersExchangeConfigList.$grid;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
            }

        });
    }


};

$(function () {
    SalaryOthersExchangeConfigList.init();
});