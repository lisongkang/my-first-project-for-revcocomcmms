var SalaryBaseWageHonusList = {
    $form: $(".form-biz-salary-base-wage-honus-list"),
    $grid: $(".grid-biz-salary-base-wage-honus-list"),

    initSearchParam: function(){
        var $form = this.$form;
        //初始化支公司
        var loginCity = Biz.LOGIN_INFO.city;
        var rolelevel = Biz.LOGIN_INFO.rolelevel;
        var area = Biz.LOGIN_INFO.areaid;
        if (Biz.isCurrentAdmin()) {
            var areaSelectGcode = "PRV_AREA"; // 超级管理员则查询出所有业务区
        }else if(rolelevel == "0"){
            console.info(areaid);
            var areaSelectGcode = "PRV_AREA&mcode=" + area; // 对于低权限固定当前操作员业务区
        }else {
            var areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + loginCity; // 中高权限只能查询所属地市的业务区
        }
        var areaid =$form.find("#areaid");
        var id_operid = $form.find("#operid");


        Biz.initSelect(areaSelectGcode, areaid); // 字符串中用&mcode增加参数

        if (rolelevel != "9" && rolelevel!= "5") {
            // 非高权限的角色不能改变业务区
            $(areaid).attr("disabled", true).removeAttr("name");

            // area表单项被禁用后传不了参数值，因此需要用隐藏表单项传areaid值
            $form.find("#backupAreaId").attr("name", "BaseWageHonus.areaid").val(Biz.LOGIN_INFO.areaid);
        }


    },


    getCity:function(){
        var cityList = Biz.getCacheParamDatas("PRV_CITY");
        cityList['*'] = "所有";
        return cityList;
    },
    init: function () {
        this.initSearchParam();
        this.$grid.data("gridOptions", {
            url: WEB_ROOT + '/biz/salary/base-wage-honus!findByPage',
            colModel: [{
                label: '分公司',
                name: 'city',
                search :  false,
                width: 100,
                stype : 'select',
                sortable : false,
                editoptions : {
                    value :SalaryBaseWageHonusList.getCity()
                }
            },
                {
                    label : '业务区',
                    name : 'areaidname',
                    sortable : false,
                    search :  false,
                    width : 40,
                },
                {
                    label: '登录名',
                    name: 'loginname',
                    search :  false,
                    sortable : false,
                    width: 80
                },
                {
                    label: '姓名',
                    name: 'name',
                    search :  false,
                    sortable : false,
                    width: 60
                },
                {
                    label: '生效月份',
                    name: 'sdateMonth',
                    search :  false,
                    sortable : false,
                    width: 60
                },
                {
                label: '失效月份',
                name: 'edateMonth',
                search :  false,
                sortable : false,
                width: 80
            }, {
                label: '基本工资',
                name: 'amount',
                search :  false,
                sortable : false,
                width: 40
            }, {
                label: '绩效奖金基数',
                name: 'honus',
                search :  false,
                sortable : false,
                width: 40
            }],
            fullediturl : WEB_ROOT + '/biz/salary/base-wage-honus!edit',
            multiselect: true,
            multiboxonly:true,
            delurl : WEB_ROOT + '/biz/salary/base-wage-honus!doDelete',
            gridComplete: function () {
                var thisGrid = SalaryBaseWageHonusList.$grid;
                //隐藏不需要按钮
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowthickstop-1-s");
                Biz.hideTableElement(thisGrid, ".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(thisGrid, ".btn-group-contexts");
                Biz.hideTableElement(thisGrid, ".ui-icon-search");
            }
        });
    },
    excelDown : function () {
            window.location.href=WEB_ROOT + '/biz/salary/base-wage-honus!downTemplate?templateFileName=baseWageHounsTemplate.xls';
    },

    excelImport : function(){
            Biz.excelUpload( WEB_ROOT + '/biz/salary/base-wage-honus!importExcelData',"",function () {
            });

    },
};

$(function () {
    SalaryBaseWageHonusList.$form.find("#dateMonth").datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView:'year',
        maxView:'decade',
        language:  'zh-CN',
    });
    SalaryBaseWageHonusList.init();
});