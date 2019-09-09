$(function() {
	$(".grid-biz-prv-prv-roleinfo-index").data("gridOptions", {    	
    	url : WEB_ROOT + '/prv/prv-roleinfo!findPageRoleByCity',
        colModel : [ {
            label : '角色名称',
            name : 'name',
            width : 60,
            editable: true,
            align : 'left'
        }, {
            label : '启用时间',
            name : 'stime',
            width : 150,
            formatter: 'date',
            editable: true,
            align : 'center'
        }, {
            label : '结束时间',
            name : 'etime',
            width : 150,
            formatter: 'date',
            editable: true,
            align : 'center'
        }, {
            label : '角色级别',
            name : 'rolelevel',
            width : 65,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("ROLE_LEVEL")
            },
            align : 'left'
        }, {
            label : '角色类型',
            name : 'atype',
            width : 65,
            editable: true,
            stype : 'select',
            editoptions : {
                value : Biz.getPrvParamListDatas("ROLE_TYPE")
            },
            align : 'left'
        }, {
            label : '备注',
            name : 'memo',
            width : 155,
            editable: true,
            align : 'left'
        } ],
        editurl : WEB_ROOT + '/prv/prv-roleinfo!doSave',
        delurl : WEB_ROOT + '/prv/prv-roleinfo!doDelete',
        fullediturl : WEB_ROOT + '/prv/prv-roleinfo!inputTabs',
        gridComplete:function(){
	         //移除搜索按钮
	     	var obj = $(".grid-biz-prv-prv-roleinfo-index");
	     	Biz.hideSearchBtn(obj);
	     }
    });
});
