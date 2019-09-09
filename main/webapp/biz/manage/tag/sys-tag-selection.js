var SysTagSelection={
	    $form: $(".form-sys-tag-selection"),
	    $grid: $(".grid-sys-tag-selection"),
		init : function(){
			this.initCity();
			this.$grid.data("gridOptions",{
				url : WEB_ROOT + '/biz/manage/tag/sys-tag!selection',
				colModel :[{
		            label : '流水号',
		            name : 'id',
		            search :  false,
		            hidden : true ,
		            width : 100
		        },{
		            name : 'tagcode',
		            search :  false,
		            hidden : true ,
		            width : 100
		        },{
		            label : '地市',
		            name : 'city',
		            search :  false,
		            hidden : true ,
		            width : 100
		        }, {
		            label : '标签名称',
		            name : 'tagname',
		            search :  false,
		            width : 350
		        },
		        {
		            label : '标签描述',
		            name : 'tagdesc',
		            search :  false,
		            width : 400
		        },
		        {
		            label : '创建者',
		            name : 'owner',
		            search :  false,
		            width : 350
		        }],
		        rowNum : 50,
		        height : 300,
				toppager : false,
				scroll : true, // 当为true时，翻页栏被禁用，使用垂直滚动条加载数据
				filterToolbar : false,
				bottompager:false,
			});
			
			this.bindAddSysTagAction();
			this.bindSaveSysTagAction();
			this.bindBackToSysTagPageAction();
			
		
		},
		initCity : function(){
			var city = this.$form.find("select[name='city']");
			var loginCity = Biz.LOGIN_INFO.city;
			if(Biz.isCurrentAdmin()){
				var citySelectGcode = "PRV_CITY";
				Biz.initSelect(citySelectGcode, city,loginCity,function(){
				});
			}else{
				 city.append('<option value=""></option>');
		 		 var option  = '<option value="' + Biz.LOGIN_INFO.city + '"'
								+ ' selected="selected"' + '>'
								+ Biz.LOGIN_INFO.cityname + '</option>';
				 city.append(option);
			}
		},
		bindBackToSysTagPageAction:function(){
			$("#backBtn").unbind("click").bind("click", this.backToTagPageBtnAction());
		},
		backToTagPageBtnAction:function(){
			return function(){
				var $dialog = $(this).closest(".modal");
				$dialog.modal("hide");
				var callback = $dialog.data("callback");
				if (callback) {
					callback.call();
				}
			};
		},
		bindSaveSysTagAction : function(){
			$("#saveSysTagBtn").unbind("click").bind("click", this.saveSysTagBtnAction);
		},
		saveSysTagBtnAction:function(){
			if(SysTagSelection.checkRequired()){
				var $form = $(".form-add-sys-tag-data");
				var systag = {};
				var data = {};
				systag.tagcode = $form.find("input[name='tagcode']").val();//tagcode 记录cmp获取的标签ID
				systag.tagname = $form.find("input[name='tagname']").val();
				systag.tagdesc = $form.find("input[name='tagdesc']").val();
				systag.city = $form.find("select[name='ods_city']").val();
				systag.owner = $form.find("input[name='owner']").val();
				systag.isshow = $form.find("select[name='isshow']").val();
				systag.memo = $form.find("input[name='memo']").val();
				data.systag = JSON.stringify(systag);
				var url =WEB_ROOT + '/biz/manage/tag/sys-tag!saveSysTag';
    		    $("body").ajaxJsonUrl(url, function(result) {
    		    	var id = $form .find("input[name='tagid']").val();
    		    	SysTagSelection.$grid.jqGrid("delRowData", id );
    		    	//清除表单数据
    		    	SysTagSelection.clearSaveTagFormData();
		            Global.notify("success", "添加成功");
    			},data);
			}
		},
		clearSaveTagFormData : function(){
			 var $form = $(".form-add-sys-tag-data");
			 $form.find("input[name='tagid']").val("");
			 $form.find("input[name='tagcode']").val("");
			 $form.find("input[name='tagname']").val("");
		     $form.find("input[name='tagdesc']").val("");
		     $form.find("select[name='ods_city']").select2('val',"");
			 $form.find("input[name='owner']").val("");
		     $form.find("select[name='isshow']").select2('val',"");
		     $form.find("input[name='memo']").val("");
		},
		checkRequired : function(){
			var $form = $(".form-add-sys-tag-data");
			var tagcode = $form.find("input[name='tagcode']").val();
			var isshow =  $form.find("select[name='isshow']").val();
		    if(Biz.checkNull(tagcode)){
		    	 Global.notify("error", "请先选择表格中的数据，点击“V”加载到表格下方表单中");
		        return false;
		    }
		    if(Biz.checkNull(isshow)){
		    	 Global.notify("error", "‘是否显示’不能为空");
			        return false;
		    }
		    return true;
		},
		bindAddSysTagAction :function(){
			$("#tagAddBtn").unbind("click").bind("click", this.addSysTagBtnAction);
		},
		addSysTagBtnAction:function(){
			var origGrid = SysTagSelection.$grid;
			var singleSelect =1;//1代表单选
			var id= Biz.isGridRightlyMultipleSelected(origGrid, singleSelect);
			var origRowData = origGrid.jqGrid("getRowData", id);
			
			//初始化下方数据
			if(origRowData){
				var $form = $(".form-add-sys-tag-data");
				$form.find("input[name='tagid']").val(id);
				$form.find("input[name='tagcode']").val(origRowData.tagcode);
				$form.find("input[name='tagname']").val(origRowData.tagname);
				$form.find("input[name='tagdesc']").val(origRowData.tagdesc);
				$form.find("select[name='ods_city']").select2('val',origRowData.city);
				$form.find("input[name='owner']").val(origRowData.owner);
				$form.find("select[name='isshow']").select2('val','Y');
			}		
		}
		
};
$(function(){
	SysTagSelection.init();
});