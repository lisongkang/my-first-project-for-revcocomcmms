$(function() {
    $(".sys-return-visit-template-index").data("gridOptions", {
        url : WEB_ROOT + "/biz/sys/return-visit-template!findByPage",
        colModel : [ {
            label : '模板类型',
            name : 'templateType',
            editable : false,
            stype:'select',
            editoptions:{
            	value:{
            		'0' : '短信',
					'1' : '微信推送'
            	}
            },
            width : 120
        }, {
            label : '标题',
            name : 'templateTitle',
            editable : false,
            width : 120
        }, {
            label : '内容',
            name : 'templateContent',
            editable : false,
            width : 360
        }],
        sortname : "id",
        filterToolbar : false,
        delurl : WEB_ROOT + '/biz/sys/return-visit-template!doDelete',
        fullediturl : WEB_ROOT + '/biz/sys/return-visit-template!edit'
       
    });
});