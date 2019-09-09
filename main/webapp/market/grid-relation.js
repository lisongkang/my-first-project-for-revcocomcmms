$(function() {
	$(".grid-market-grid-relation").data("gridOptions", {
		url : WEB_ROOT + "/market/grid-relation!findByPage?search['EQ_grid.id']=" + gridId,
		colModel : [ {
            label : '网格编号',
            name : 'gridOssInfo.gridid',
            editable : true,
            editoptions : Biz.getOssGridOptions(),
            width : 220,
            editrules : {
                required : true
            },
            align : 'center'
        }, {
			label : '网格名称',
            name : 'gridOssInfo.gridname',
            editable: true,
            width : 320
        }, {
        	name : 'grid.id',
        	hidden : true,
            hidedlg : true,
            editoptions : {
    			value : gridId
            },
            editable : true
        }, {
        	name : 'gridOssInfo.id',
        	hidden : true,
            hidedlg : true,
            editable : true
        } ],
        filterToolbar : false,
        sortname : "id",
        editurl : WEB_ROOT + '/market/grid-relation!doSave',
        delurl : WEB_ROOT + '/market/grid-relation!doDelete'
	});
});