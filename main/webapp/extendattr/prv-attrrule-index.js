
$(function() {
	
	var flag = false;
    $(".grid-biz-extendattr-prv-attrrule-index").data("gridOptions", {
        url : WEB_ROOT + '/extendattr/prv-attrrule!findByPage',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true                          
        },{
            label : '城市',
            name : 'city',
            editable : true           
        },{
            label : '属性名称',
            name : 'attrname',
            width : 100,
            editable: true,
            align : 'left'
        }, {
            label : '属性标识',
            name : 'attrcode',
            width : 100,
            editable: true,
            align : 'left'
        },{
            label : '属性值来源',
            name : 'valuesrc',
            width : 70,
            editable : true,
            stype : 'select',
            editoptions : {
        		value : Biz.getPrvParamListDatas("PRD_VALUESRC")
            }
        },{
            label : '对应参数',
            name : 'valueparam',
            width : 60,
            editable: true,
            align : 'left'
        },{
            label : '是否必填',
            name : 'ifnecessary',
            width : 40,
            editable : true,
            stype : 'select',
            editoptions : {
        		value : Biz.getPrvParamListDatas("SYS_YES_NO")
            }
        },{
            label : '默认值',
            name : 'defaultvalue',
            width : 50,
            editable: true,
            align : 'left'
        },
        {
            label : 'classes',
            name : 'classes',
            hidden: true
        }, {
            label : 'minvalue',
            name : 'minvalue',
            hidden: true
        }, {
            label : 'updateoper',
            name : 'updateoper',
            hidden: true
        }, {
            label : 'maxvalue',
            name : 'maxvalue',
            hidden: true
        }, {
            label : 'updatetime',
            name : 'updatetime',
            width : 150,
            formatter: 'date',
            hidden: true
        }, {
            label : 'classlib',
            name : 'classlib',
            hidden: true
        },    {
            label : 'createtime',
            name : 'createtime',
            width : 150,
            formatter: 'date',
            hidden: true
        }, {
            label : 'uniqueflag',
            name : 'uniqueflag',
            hidden: true
        }, {
            label : 'orderby',
            name : 'orderby',
            hidden: true
        }, {
            label : 'groupby',
            name : 'groupby',
            hidden: true
        }, {
            label : 'scopeflag',
            name : 'scopeflag',
            hidden: true
        }, {
            label : 'subclass',
            name : 'subclass',
            hidden: true
        }, {
            label : 'createoper',
            name : 'createoper',
           hidden: true
        } ],
        delurl : WEB_ROOT + '/extendattr/prv-attrrule!doDelete',
        fullediturl : WEB_ROOT + '/extendattr/prv-attrrule!inputTabs'
        /*beforeSelectRow : function(rowid, e){
        	var url = WEB_ROOT + '/extendattr/prv-attrrule!isOktoDelorUpdate';
        	var data = {id : rowid};
        	
        	$.ajax({ 
        		url : url, 
        		data:data, 
        		cache : false, 
        		async : true, 
        		dataType : 'json',
        		type : "POST", 
        		success : function (result){ 
        			if(result == '1'){
        				flag = true;
        				var rowData = $(".grid-biz-extendattr-prv-attrrule-index").getRowData(rowid);
        				
        				var selector = "tbody tr#"+id;
        				var $thistr = $(".grid-biz-extendattr-prv-attrrule-index").find(selector);
        				
        				//好吧，非要先单击再双击才行
        				$thistr.trigger("click");
        				$thistr.trigger("dblclick");
        			}else{
        				flag = false;
        				Global.notify("error", "该行数据不允许被修改");
        		    }
        		}
        	});
        	
            return flag;
        	
        }*/
    });
    
    
});
