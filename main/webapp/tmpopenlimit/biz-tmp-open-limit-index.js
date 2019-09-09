$(function() {
    $(".grid-channel-biz-tmpopenlimit-biz-tmp-open-limit-index").data("gridOptions", {
        url : WEB_ROOT + '/tmpopenlimit/biz-tmp-open-limit!findByPage',
        colModel : [ {
            label : '流水号',
            name : 'id',
            hidden : true 
                                    
        }, {
            label : '限制对象类型',
            name : 'objType',
            width : 60,
            align : 'left',
            stype : 'select',
            editoptions : {
            	value : Biz.getCacheParamDatas("BIZ_TMPOPEN_LIMIT_OBJTYPE")
            }
        },{
            label : '限制对象ID',
            name : 'objId',
            width : 60,
            align : 'right',
            hidden : true 
        },{
            label : '限制对象名称',
            name : 'objName',
            width : 60,
            align : 'right'
        },  {
            label : '限制方式',
            name : 'timeType',
            width : 60,
            align : 'left',
            stype : 'select',
            editoptions : {
            	value : Biz.getCacheParamDatas("BIZ_TMPOPEN_LIMIT_TIMETYPE")
            }
        }, {
            label : '地市',
            name : 'city',
            width : 60,
            align : 'right',
            hidden : true ,
            stype : 'select',
            editoptions : {
            	value : Biz.getCacheParamDatas("PRV_CITY")
            }
        }, {
            label : '创建人',
            name : 'createOper',
            width : 60,
            align : 'right',
            hidden : true 
        }, {
            label : '体验授权方案标识',
            name : 'planId',
            width : 60,
            align : 'right',
            hidden : true 
        }, {
            label : '最近修改时间',
            name : 'updateTime',
            width : 150,
            formatter: 'date',
            align : 'center',
            hidden : true 
        }, {
            label : '体验授权名称',
            name : 'name',
            width : 60,
            align : 'left'
        },{
            label : '限制次数',
            name : 'limitNums',
            width : 40,
            align : 'right'
        },  {
            label : '创建时间',
            name : 'createTime',
            width : 150,
            formatter: 'date',
            align : 'center',
            hidden : true 
        }, {
            label : '最近修改人',
            name : 'updateOper',
            width : 60,
            align : 'right',
            hidden : true 
        } ],
        inlineNav : {
            add : false
        },
        filterToolbar : false,
        editurl : WEB_ROOT + '/tmpopenlimit/biz-tmp-open-limit!doSave',
        delurl : WEB_ROOT + '/tmpopenlimit/biz-tmp-open-limit!doDelete',
        fullediturl : WEB_ROOT + '/tmpopenlimit/biz-tmp-open-limit!inputTabs'
    });
    
    $("#objName1").keyup(function() {
    	 if($("#objType1").val()==null||$("#objType1").val()==""){
    		 $("#objName1").val("");
    		 alert("请先选择限制对象类型！");
    	 }
     
    })
});

