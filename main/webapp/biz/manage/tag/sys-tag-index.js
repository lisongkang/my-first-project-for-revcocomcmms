var SysTagIndex = {
	$form : $(".form-biz-sys-tag-index"),
	$grid : $(".grid-biz-sys-tag-index"),
	init : function(){
		this.$grid.data("gridOptions",{
			url : WEB_ROOT + '/biz/manage/tag/sys-tag!findByPage',
			 colModel :[{
		            label : '流水号',
		            name : 'id',
		            search :  false,
		            hidden : true                          
		        },
		        {
		            label : '标签名称',
		            name : 'tagname',
		            search :  false,
		            width : 100
		        },
		        {
		            label : '标签描述',
		            name : 'tagdesc',
		            search :  false,
		            width : 150
		        },
		        {
		            label : '创建者',
		            name : 'owner',
		            search :  false,
		            width : 50
		        },
		        {
		            label : '是否显示',
		            name : 'isshow',
		            search :  false,
		            width : 100,
		            editable: true,
		            stype : 'select',
		            align : 'left',
		            editoptions : {
						  value :Biz.getCacheParamDatas("SYS_YES_NO")
					}
		        },
		        {
		            label : '地市',
		            name : 'city',
		            search :  false,
		            width : 50,
		            
		            stype : 'select',
		            align : 'left',
		            editoptions : {
						  value :Biz.getCacheParamDatas("PRV_CITY")
					}
		        },
		        {
		            label : '备注',
		            name : 'memo',
		            search :  false,
		            editable: true,
		            width : 150
		        }],
		        editurl : WEB_ROOT + '/biz/manage/tag/sys-tag!doSave',
				multiselect: false,
		        operations : function(items) {
					var $this = SysTagIndex;
		    		var $grid = this;
		    		var bindPatchBtn = $this.createCustomBtn(false, "新增数据", "fa-plus-square", $this.addTagBtnAction($grid));
		    		items.push(bindPatchBtn);
		    	}
		});
	},
	addTagBtnAction : function(gridobj,saveBtnAction){
		return function(e) {
			var dialogUrl = WEB_ROOT + '/biz/manage/tag/sys-tag!forward?_to_=selection';
	    	$(gridobj).popupDialog({
	    		url : dialogUrl,
	    		id : 'add_tag',
	    		title : '添加标签',
	    		callback : function(){
	    			Biz.refreshGrid(".grid-biz-sys-tag-index");
	    		}
	    	});
		};
	},
	createCustomBtn : function(isPosition, btnTitle, btnClass, btnAction) {
		var newBtn = $('<li '
				+ (isPosition ? 'data-position="multi"' : '')
				+ 'data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa '
				+ btnClass + '"></i> ' + btnTitle + '</a></li>');

		newBtn.children("a").bind("click", btnAction);
		return newBtn;
	},
};

$(function(){
	SysTagIndex.init();
});