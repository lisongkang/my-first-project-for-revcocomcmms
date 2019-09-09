$(function() {
       $(".grid-biz-prv-oper-menu-ctrl-index").data("gridOptions",{
    	   url : WEB_ROOT + '/prv/prv-oper-menu-ctrl!findByPage',
    	   colModel : [ {
               label : '记录号',
               name : 'id',
               hidden : true 
           }, {
               label : '操作员ID',
               name : 'operid',
               width : 60,
               align : 'right',
               hidden : true 
           }, {
               label : '操作员',
               name : 'opername',
               width : 60,
               align : 'right'
           },{
               label : '权限菜单ID',
               name : 'menuid',
               width : 60,
               align : 'right',
               hidden : true 
           }, {
               label : '权限菜单',
               name : 'menuname',
               width : 60,
               align : 'right'
           },{
               label : '权限代码',
               name : 'controlcode',
               width : 60,
               align : 'right',
               stype : 'select',
               editoptions : {
               	value : Biz.getCacheParamDatas("MENU_CONTROLCODE")
               }
           }, {
        	label : '权限点',
            name : 'controlvalue',
            width : 60,
            align : 'right',
            stype : 'select',
            editoptions : {
            	value : Biz.getCacheParamDatas("MENU_CONTROLCODE_POWER")
            }
        }, {
            label : '限制时长',
            name : 'timelength',
            width : 60,
            align : 'right',
        }, {
            label : '限制值',
            name : 'value',
            width : 60,
            align : 'right',
        }],
        inlineNav : {
            add : false
        },
        filterToolbar : false,
        editurl : WEB_ROOT + '/prv/prv-oper-menu-ctrl!doSave',
        delurl : WEB_ROOT + '/prv/prv-oper-menu-ctrl!doDelete',
        fullediturl : WEB_ROOT + '/prv/prv-oper-menu-ctrl!inputTabs'
       });
});




