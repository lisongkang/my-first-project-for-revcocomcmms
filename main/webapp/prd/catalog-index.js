var catalog = {
    init : function() {
        var ctype = $("#ctype");
        var bizcatalogctype = "BIZ_CATALOG_CTYPE"
        Biz.initSelectRession(bizcatalogctype, ctype);
    },
};

$(function() {
    $(".grid-biz-prd-catalog-index").data("gridOptions", {
    	initArea : function(elem, defaultvalue) {
    		if (elem.val() == null) return;
	    	var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + elem.val();
	    	elem.ajaxJsonUrl(url, function(data) {
	    		var options = {};
	    		var rowid = elem.closest("tr.jqgrow").attr("id");
	    		var area = $("#"+rowid+"_areaid");
	    		area.empty();
	    		
	    		var option = '';
	    		$.each(data, function(i, item) {
	    			option = '<option value="'+item.id+'">'+item.name+'</option>';
	    			area.append(option);
	            });
	            
	            if (defaultvalue) {
	            	area.select2("val", defaultvalue);
	            } else {
	            	area.select2("val", "");
	            }
	    	});
    	},
        url : WEB_ROOT + "/prd/catalog!findByPage",
        colModel : [ {
            label : '目录名称',
            name : 'catalogname',
            editable : true,
            width : 120
        }, {
                label : '目录类型',
                name : 'ctype',
                editable : true,
                stype : 'select',
                editoptions : {
                    value : {'0':'销售目录','1':'场景目录'}
                },
                width : 120
            },
            {
            label : '地市',
            name : 'city',
            editable : true,
            stype : 'select',
            editoptions : {
        		value : Biz.getPrvParamListDatas("PRV_CITY")
            },
            width : 120
        },
            {
            label : '区域',
            name : 'area.name',
            editable : true,
            width : 120
        },
            {
            label : '优先级',
            name : 'pri',
            editable : true,
            width : 120
        }, {
            label : '状态',
            name : 'catalogstatus',
            editable : true,
            stype : 'select',
            editoptions : {
        		value : {'Y':'有效','N':'无效'}
            },
            width : 80
        }],
        sortname : "id",
        filterToolbar : false,
        //editurl : WEB_ROOT + '/prd/catalog!doSave',
        delurl : WEB_ROOT + '/prd/catalog!doDelete',
        fullediturl : WEB_ROOT + '/prd/catalog!inputTabs'
    });
    catalog.init();
});

