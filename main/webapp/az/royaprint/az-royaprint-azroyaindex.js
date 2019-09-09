var AzRoyaPrintIndex = {
    $form: $(".form-az-royaprint-az-royaprint-azroyaindex"),
    $grid: $(".grid-az-royaprint-az-royaprint-azroyaindex"),
    init : function(){
        this.initSearchParam();
        //在加js
        this.initOrderStatus();


        this.$grid.data("gridOptions",{
            url : WEB_ROOT + '/az/royaprint/az-royaprint!findRoyaByPage',
            colModel :[
                {
                    label : '申请单ID',
                    name : 'pronum',
                    width : 40,
                    hidden : true
                },
                {
                    label : '地市',
                    name : 'city',
                    width : 40,
                    sortable : false,
                    search :  false,
                    stype : 'select',
                    editoptions : {
                        value :AzRoyaPrintIndex.getCity()
                    }
                },
                {
                    label : '业务区',
                    name : 'buildunit',
                    sortable : false,
                    search :  false,
                    width : 40,

                },
                {
                    label : '项目名称',
                    name : 'proname',
                    sortable : false,
                    search :  false,
                    width : 40,

                },
                {
                    label : '工程状态',
                    name : 'prostatus',
                    sortable : false,
                    search :  false,
                    width : 70,
                    stype : 'select',
                    editoptions : {
                        value :Biz.getCacheParamDatas("BIZ_APPLICATION_PROSTATUS")
                    }
                },
                {
                    label : '完工时间',
                    name : 'endtime',
                    sortable : false,
                    search :  false,
                    width : 80,

                },
                {
                    label : '施工人',
                    name : 'constructors',
                    sortable : false,
                    search :  false,
                    width : 140,

                },
                {
                    label : '打印',
                    name : 'self_opt',
                    sortable : false,
                    search :  false,
                    width : 60,

                },
                {
                    label : '下载',
                    name : 'info_download',
                    sortable : false,
                    search :  false,
                    width : 60,

                }
            ],
            multiselect: true,
            multiboxonly:true,
            filterToolbar : false,
            ondblClickRow:function(){},
            gridComplete : function() {
                //隐藏表格中的元素
                Biz.hideTableElement(AzRoyaPrintIndex.$grid,".btn-group-contexts");
                Biz.hideTableElement(AzRoyaPrintIndex.$grid,".ui-icon-arrowstop-1-w");
                Biz.hideTableElement(AzRoyaPrintIndex.$grid,".ui-icon-search");

                //给申请单价打印查看按钮
                var ids = $(".grid-az-royaprint-az-royaprint-azroyaindex").jqGrid('getDataIDs');
                for(var i = 0 ; i < ids.length ; i++)
                {
                    var id = ids[i];
                    var rowData = $(".grid-az-royaprint-az-royaprint-azroyaindex").getRowData(id);
                    var prostatus = rowData.prostatus;
                    var pronum = rowData.pronum;
                    var buildunit = rowData.buildunit;
                    if(prostatus=='12' || prostatus == '13'){
                        var optBtn = "";
                        optBtn = optBtn + "<a href='javascript:;' style='color:#f60' onclick='AzRoyaPrintIndex.viewPrint(\""+pronum+"\",\""+buildunit+"\")' >打印结单信息</a>";
                        $(".grid-az-royaprint-az-royaprint-azroyaindex").jqGrid('setRowData', ids[i], { 'self_opt': optBtn });
                    }
                    if(prostatus=='6' || prostatus == '7' || prostatus == '11' || prostatus == '12' || prostatus == '13'){
                        var optBtnDownload = "";
                        optBtnDownload = optBtnDownload + "<a href='javascript:;' style='color:#f60' onclick='AzRoyaPrintIndex.downLoadApply(\""+pronum+"\",\""+buildunit+"\")' >下载验收资料</a>";
                        $(".grid-az-royaprint-az-royaprint-azroyaindex").jqGrid('setRowData', ids[i], { 'info_download': optBtnDownload });
                    }

                }
            },

        });
    },
    initOrderStatus : function() {
        var $form = this.$form;
        Biz.initSelect("BIZ_APPLICATION_PROSTATUS", $form.find("#prostatus"));
    },

    viewPrint : function(pronum,buildunit) {
        $(this).popupDialog({
            url : WEB_ROOT + '/az/royaprint/az-royaprint!findPrintInfo?proid=' + pronum,
            title : '结单明细信息表',
            id : "showStatementInfo",
            callback : function(rowdata) {
            }
        });
    },
    downLoadApply : function(pronum,buildunit) {
        $(this).popupDialog({
            url : WEB_ROOT + '/az/royaprint/az-royaprint!findDeliveryInfo?pronum=' + pronum,
            title : '验收资料信息表',
            id : "showDeliveryInfo",
            callback : function(rowdata) {
            }
        });
    },

    checkInput : function() {
        var $form = this.form;
        var text = "";

        var timeRange = $form.find("#timeRange").val();

        var timeRangeArray = timeRange.split('～');
        if (timeRangeArray.length != 2
            || this.checkNull(timeRangeArray[0].trim())
            || this.checkNull(timeRangeArray[1].trim())) {
            Global.notify("error", "择统计时段有误，请重新选择");
            return false;
        }

        if (!this.checkDateRange(timeRangeArray[0].trim(),timeRangeArray[1].trim())) {
            Global.notify("error", "开始日期不能大于结束日期，请重新选择");
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
    checkDateRange : function(startDate, endDate) {
        return this.strToDate(startDate).getTime() <= this.strToDate(
            endDate).getTime();
    },
    strToDate : function(str) {
        var tempStrs = str.split(" ");
        var dateStrs = tempStrs[0].split("-");
        var year = parseInt(dateStrs[0], 10);
        var month = parseInt(dateStrs[1], 10) - 1;
        var day = parseInt(dateStrs[2], 10);
        var date = new Date(year, month, day);
        return date;
    },

    initSearchParam: function(){
        //初始化地市
        var $form = this.$form;
        var $city = $form.find("select[name='AzRoyaSerchParamsBO.city']");
        var area = $form.find("select[name='AzRoyaSerchParamsBO.areaid']");

        //初始化地市
        $form.data("formOptions", {
            bindEvents : function() {
                    Biz.initSelect('PRV_CITY',$city);
            }
        });
        $city.change(function() {
            var tmpVal = $city.val();
            if (!tmpVal) {
                area.empty();
                area.select2("val", "");
                return;
            }

            var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + tmpVal;
            $form.ajaxJsonUrl(url, function(data) {
                var options = {};
                area.empty();

                var option = '';
                $.each(data, function(i, item) {
                    option = '<option value="' + item.id + '">' + item.name
                        + '</option>';
                    area.append(option);
                })
                area.select2("val", "");
            });
        });

    },


    getCity:function(){
        var cityList = Biz.getCacheParamDatas("PRV_CITY");
        return cityList;
    }
};



$(function(){
    AzRoyaPrintIndex.init();
});