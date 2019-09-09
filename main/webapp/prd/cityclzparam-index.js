$(function() {
    $(".grid-biz-prd-cityclzparam-index").data("gridOptions", {
    	/*initArea : function(elem, defaultvalue) {
    		if (elem.val() == null) return;
	    	var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + elem.val();
	    	elem.ajaxJsonUrl(url, function(data) {
	    		var options = {};
	    		var rowid = elem.closest("tr.jqgrow").attr("id");
	    		var area = $("#"+rowid+"_areaid");
	    		area.empty();
	    		debugger
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
    	},*/
        url : WEB_ROOT + "/prd/cityclzparam!findByPage",
        colModel : [ {
            label : '设备大类',
            name : 'kind',
            editable : true,
            stype:'select',
            editoptions:{
            	value:Biz.getPrvParamListDatas("RES_KIND")
            },
            width : 120
        }, {
            label : '地市',
            name : 'city',
            editable : true,
            stype : 'select',
            editoptions : {
        		value : Biz.getPrvParamListDatas("PRV_CITY")
            },
            width : 120
        }, {
            label : '区域',
            name : 'extraAttributes.areaname',
            editable : false,
            width : 120
        }, {
            label : '设备小类',
            name : 'subkind',
            editable : true,
            width : 120
        }, {
            label : '设备小类名称',
            name : 'subname',
            editable : true,
            width : 150
        }],
        sortname : "id",
        filterToolbar : false,
        delurl : WEB_ROOT + '/prd/cityclzparam!doDelete',
        fullediturl : WEB_ROOT + '/prd/cityclzparam!inputTabs'
       
    });
});