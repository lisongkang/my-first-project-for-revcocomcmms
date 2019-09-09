
TmpOpenLimitInputBasic = ({
	init : function() {
		// 事件绑定
		$(".form-channel-biz-tmpopenlimit-biz-tmp-open-limit-inputBasic").data("formOptions", {
			bindEvents : function() {
				var $form = $(this);

				 $form.find("button[name='btn_tmpopenlimit']").click(function(){
				    return TmpOpenLimitInputBasic.doTmpOpenNew('.form-channel-biz-tmpopenlimit-biz-tmp-open-limit-inputBasic');  			
				 });
			}
		});
		
	},

	selectOpenTabs : function(selector) {
		 var objType = $('#objType').val();
		 var objId = $('#objId').val();
		 if(objType==null||objId==null||objType==''||objId==''){
			 alert("请先选限限制对象和制对象类型！");
			 return;
		 }

		$form = $(selector);
		$form.popupDialog({
			url : WEB_ROOT
					+ '/tmpopenlimit/biz-tmp-open-limit!selectOpenTabs',
			title : '选择方案',
			id: 'user_new_addr_select',
			callback : function(rowdata) {
				$form.find('#name').val(rowdata.name);
				$form.find('#planId').val(rowdata.planid);

			}
		});
	},
	
	selectDepartmentTabs : function(selector) {
		$form = $(selector);
		var style = $("#objType").val();
		if(style=='0'){
		   $form.popupDialog({
			  url : WEB_ROOT + '/prv/prv-department-findOneCityByPage.jsp',
			  title : '选择部门',
			  id: 'department_select',
			  callback : function(rowdata) {
				 if($form.find('#objId').val()==rowdata.deptid){
					 $form.find('#objName').val(rowdata.name);
					 $form.find('#objId').val(rowdata.deptid);
				 }else{
					 $form.find('#objName').val(rowdata.name);
					 $form.find('#objId').val(rowdata.deptid);
					 $form.find('#planId').val("");
					 $form.find('#name').val("");
				 }
			  }
		   });
		}
		if(style=='1'){
		   $form.popupDialog({
			   url : WEB_ROOT + '/prv/prv-operator-findOneCityByPage.jsp',
			   title : '选择工号',
			   id: 'operator_select',
			   callback : function(rowdata) {
				  if($form.find('#objId').val()==rowdata.operid){	
					  $form.find('#objName').val(rowdata.name);
					  $form.find('#objId').val(rowdata.operid);
				  }else{
					  $form.find('#objName').val(rowdata.name);
					  $form.find('#objId').val(rowdata.operid);
					  $form.find('#planId').val("");
				      $form.find('#name').val("");
				  }
			   }
		  });		
	   }
       if(style==null||style==''){
		   alert("请先选限制对象类型！");
		   return;
	   }
	},
	

	
	doTmpOpenNew : function(selector) {
		
		$form= $(selector);
		if(Biz.checkNull($form.find('#timeType').val())){
			Global.notify("error", "请选择限制方式!");
			return false;
		}
		if(Biz.checkNull($form.find('#objId').val())){
			Global.notify("error", "请限制选择对象ID!");
			return false;
		}
		
		if(Biz.checkNull($form.find('#limitNums').val())){
			Global.notify("error", "请输入限制次数!");
			return false;
		}
		var limitNums = parseInt($form.find('#limitNums').val());
		if(limitNums==0){
			Global.notify("error", "限制次数不能为0!");
			return false;
		}
		if(Biz.checkNull($form.find('#name').val())){
			Global.notify("error", "请选择授权名称!");
			return false;
		}
		if(Biz.checkNull($form.find('#objType').val())){
			Global.notify("error", "请选择限制对象类型!");
			return false;
		}
	},
});

$(function() {
	$("#dates_div").hide();
    $(".form-channel-biz-tmpopenlimit-biz-tmp-open-limit-inputBasic").data("formOptions", {
        bindEvents : function() {
            var $form = $(this);
        }
    });
    TmpOpenLimitInputBasic.init();
});
$('#objType').change(function(){ 
    $("#objId").val("");
    $("#objName").val("");
    $("#planId").val("");
    $("#name").val("");	
}) ;




