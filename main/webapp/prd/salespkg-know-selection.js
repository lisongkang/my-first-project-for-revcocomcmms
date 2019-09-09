$(function() {
    $(".grid-biz-prd-salespkg-know-index2").data("gridOptions", {
        url : WEB_ROOT + '/prd/salespkg-know!findPageKnowsByCity',
        colModel : [ {
            label : '营销知识库名称',
            name : 'knowname',
            width : 200,
            editable: true,
            align : 'left'
        }, {
            label : '类型',
            name : 'objtype',
            width : 100,
            editable: true,
            stype : 'select',
            editoptions : {
            	value : {'0':'产品','1':'营销方案','3':'商品','4':'商品小类'},
            	defaultValue : "0"
            },
            align : 'left'
        }, {
            label : '商品编码',
            name : 'objcode',
            width : 100,
            editable: true
        }, {
            label : '商品名称',
            name : 'objname',
            width : 150,
            editable: true,
            align : 'right'
        }, {
            label : 'objid',
            name : 'objid',
            hidden : true,
            hidedlg : true,
            editable: true
        }, {
            label : '价格',
            name : 'price',
            width : 60,
            editable: true,
            align : 'right'
        }, {
            label : '可销售次数',
            name : 'maxtimes',
            width : 100,
            editable: true,
            align : 'right'
        }, {
        	label : '地市',
            name : 'city',
            editable : true,
            stype : 'select',
            editoptions : {
        		value : Biz.getPrvParamListDatas("PRV_CITY")
            },
            width : 90
        }, {
            label : '创建时间',
            name : 'createtime',
            width : 150,
            formatter: 'date',
            editable: true,
            align : 'center'
        } ],
        inlineNav : {
            add : false
        },
        rowNum : 10,
        multiselect : false,
        toppager : false,
        filterToolbar : false,
        onSelectRow : function(id) {
            var $grid = $(this);
            var $dialog = $grid.closest(".modal");
            $dialog.modal("hide");
            var callback = $dialog.data("callback");
            if (callback) {
                var rowdata = $grid.jqGrid("getRowData", id);
                rowdata.id = id;
                callback.call($grid, rowdata);
            }
        }
    });
});
