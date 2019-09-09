$(function() {
    $(".grid-core-task-cfg-task-index").data("gridOptions", {
        url : WEB_ROOT + '/common/task/cfg-task!findByPage',
        colModel : [ {
            label : '任务名称',
            name : 'taskName',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '任务编码',
            name : 'taskCode',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '业务实现类',
            name : 'implClass',
            width : 200,
            editable: true,
            align : 'left'
        }, {
            label : '运行方式',
            name : 'taskMethod',
            editable : true,
            stype : 'select',
            editoptions : {
            	value : {'C':'周期','S':'指定时间','I':'立即执行'}
            },
            width : 100
        }, {
            label : '周期表达式',
            name : 'taskExpr',
            width : 200,
            editable: true,
            align : 'left'
        }, {
            label : '状态',
            name : 'status',
            editable : true,
            stype : 'select',
            editoptions : {
            	value : {'Y':'有效','N':'无效'}
            },
            width : 70
        } ],
        editurl : WEB_ROOT + '/common/task/cfg-task!doSave',
        delurl : WEB_ROOT + '/common/task/cfg-task!doDelete'
        //fullediturl : WEB_ROOT + '/common/task/cfg-task!edit'
    });
});
