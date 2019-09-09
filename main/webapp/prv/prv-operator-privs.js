$(function() {
	$(".grid-prv-operator-privs").data("gridOptions", {
		url : WEB_ROOT + "/prv/prv-operator!queryOperRole?id=" + operid,
		colModel : [ {
            label : '部门',
            name : 'department.name',
            editable : true,
            editoptions : Biz.getDepartmentOptionsByOperid(operid),
            width : 300,
            editrules : {
                required : true
            },
            align : 'left'
        }, {
			label : '角色',
            name : 'roleid',
            editable: true,
            editrules : {
                required : true
            },
            stype : 'select',
            width : 200,
            editoptions : {
            	value : Biz.queryRoleByCityCache()
            },
            align : 'left'
        }, {
        	name : 'department.id',
        	hidden : true,
            hidedlg : true,
            editable : true
        }, {
        	name : 'operid',
        	editoptions : {
    			value : operid
            },
        	hidden : true,
            hidedlg : true,
            editable : true
        }, {
            label : '优先级',
            name : 'priority',
            editable : true,
            width : 120
        }, {
            label : '启用时间',
            name : 'stime',
            width : 150,
            formatter: 'date',
            editable: true,
            editrules : {
                required : true
            },
            align : 'center'
        }, {
            label : '结束时间',
            name : 'etime',
            width : 150,
            formatter: 'date',
            editable: true,
            editrules : {
                required : true
            },
            align : 'center'
        } ],
        sortname : "id",
        editurl : WEB_ROOT + '/prv/prv-operrole!doSave',
        delurl : WEB_ROOT + '/prv/prv-operrole!doDelete'
	});
});