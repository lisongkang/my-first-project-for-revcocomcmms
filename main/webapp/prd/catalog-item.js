$(function() {
	$(".grid-prd-catalog-item").data("gridOptions", {
		url : WEB_ROOT + "/prd/catalog!queryItem?id=" + catalogId,
		colModel : [ {
            label : '营销知识库名称',
            name : 'extraAttributes.knowname',
            editable : true,
            editoptions : Biz.getCatalogKnowOptions(),
            width : 400,
            editrules : {
                required : true
            },
            align : 'left'
        }, {
        	label : '优先级',
        	name : 'pri',
        	editable : true,
			width : 400,
			editrules : {
                required : true
            },
            align : 'left'
        }, {
        	label : '知识库id',
        	name : 'knowid',
        	hidden : true,
            hidedlg : true,
            editable : true
        }, {
        	label : '地市',
        	name : 'city',
        	hidden : true,
            hidedlg : true,
            editable : true
        }, {
        	label : '目录id',
        	name : 'catalogid',
        	hidden : true,
            hidedlg : true,
            editoptions : {
    			value : catalogId
            },
            editable : true
        } ],
        filterToolbar : false,
        sortname : "id",
        editurl : WEB_ROOT + '/prd/catalog-item!doSave',
        delurl : WEB_ROOT + '/prd/catalog-item!doDelete',
        gridComplete:function(){
            //移除搜索按钮
        	var obj = $(".grid-prd-catalog-item");
        	Biz.hideSearchBtn(obj);
        }
	});
});