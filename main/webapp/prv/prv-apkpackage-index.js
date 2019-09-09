$(function() {
       $(".grid-biz-prv-apk-index").data("gridOptions",{
    	   url : WEB_ROOT + '/prv/prv-apkpackage!findByPage',
    	   colModel : [ {
               label : '包名',
               name : 'packages',
               width : 150,
               editable: true,
               align : 'left'
           }, {
               label : '版本号',
               name : 'version',
               width : 80,
               editable: true,
               align : 'left'
           }, {
               label : '下载地址',
               name : 'downloadUrl',
               width : 300,
               editable: true,
               align : 'left'
           }, {
               label : '版本描述',
               name : 'descript',
               width : 150,
               editable: true,
               align : 'left'
           }, {
               label : '广告图片地址',
               name : 'advertimg',
               width : 150,
               editable: true,
               align : 'left'
           }, {
               label : '操作版本',
               name : 'operate',
               width : 150,
               editable: true,
               align : 'left'
           }, {
               label : '是否强制',
               name : 'force',
               width : 80,
               editable: true,
               stype : 'select',
               editoptions : {
            	   value : {'1':'是','0':'否'}
               },
               align : 'left'
           }],
           sortname : 'version',
           multiselect : false,
           editurl : WEB_ROOT + '/prv/prv-apkpackage!doSave',
           delurl : WEB_ROOT + '/prv/prv-apkpackage!doDelete'
       });
});