var SalaryExplicationConfigList = {
    $form: $(".form-biz-salary-explication-config-list"),
    $grid: $(".grid-biz-salary-explication-config-list"),

    initSearchParam: function(){
        var $form = this.$form;
        //初始化支公司
        var loginCity = Biz.LOGIN_INFO.city;
        var areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + loginCity; // 否则只能查询所属地市的业务区
        var areaid =$form.find("#areaid");
        Biz.initSelect(areaSelectGcode,areaid);

        //初始化类型
        var id_type =$form.find("#id_type");
        Biz.initSelect('SALARY_EXPLICATION_TYPE',id_type);

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
            url: WEB_ROOT + '/biz/salary/explication-config!findByPage',
            colModel: [{
                label: '分公司',
                name: 'city',
                search :  false,
                width: 100,
                sortable:false,
                stype : 'select',
                editoptions : {
                    value :SalaryExplicationConfigList.getCity()
                }
            }, {
                label: '业务区',
                name: 'areaid',
                search :  false,
                width: 90,
                sortable:false,
                formatter: function (value){
                    return SalaryExplicationConfigList.getArea(value);
                }
            }, {
                label: '类型',
                name: 'type',
                search :  false,
                width: 80,
                sortable:false,
                stype: 'select',
                editoptions: {
                    value: Biz.getCacheParamDatas("SALARY_EXPLICATION_TYPE")
                }
            }, {
                label: '内容',
                name: 'context',
                search :  false,
                sortable:false,
                width: 150
            }, {
                label: '生效时间',
                name: 'stime',
                search :  false,
                sortable:false,
                width: 90
            }, {
                label: '结束时间',
                name: 'etime',
                search :  false,
                sortable:false,
                width: 90
            }],
            fullediturl : WEB_ROOT + '/biz/salary/explication-config!inputTabs',
            multiselect: true,
            multiboxonly:true,
            delurl : WEB_ROOT + '/biz/salary/explication-config!doDelete',
            gridComplete: function () {
                var thisGrid = SalaryExplicationConfigList.$grid;
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
    SalaryExplicationConfigList.init();
});