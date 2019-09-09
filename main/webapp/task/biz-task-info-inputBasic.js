var BizTaskInfoInputBasic={
    $form :$(".form-biz-task-info-inputBasic"),
	init : function(){
		this.initSelectParams();
		this.$form.find(".btn_taskinfonew").unbind("click").bind("click",this.sumbitTaskInfo);
	    this.$form.find(".btn-cancel1").unbind("click").bind("click",this.colseDialog);
	},
	/**
	 * @param flag:是否刷新表格
	 */
	colseDialog:function(flag){
		var $this = BizTaskInfoInputBasic.$form;
		var $dialog =$this.closest(".modal");
		$dialog.modal("hide");
		var callback = $dialog.data("callback");
		if (callback) {
			callback.call($dialog,flag);//
		}
	},
	
	sumbitTaskInfo : function(){
		//
		var $this = BizTaskInfoInputBasic;
		var $form  = $this.$form;
		if($this.checkRequiredParam()){
			var data = {};
			var taskinfo = {};
			var taskid = $form.find("#id_taskid").val();
			if( taskid!="")
			{
				taskinfo.taskid =taskid;
			};
			taskinfo.tasktitle = $form.find("#id_tasktitle").val();
			taskinfo.cometype = $form.find("#id_cometype").val();
			taskinfo.cusid = $form.find("#id_cusid").val();
			taskinfo.type = $form.find("#id_type").val();
 			taskinfo.pri = $form.find("#id_pri").val();
			taskinfo.stime = $form.find("#id_stime").val();
			taskinfo.ctime = $form.find("#id_ctime").val();
			taskinfo.taskdesc = $form.find("#id_taskdesc").val();
			data.taskinfo = JSON.stringify(taskinfo);
			var url =  WEB_ROOT + '/task/biz-task-info!doSave';
			$("body").ajaxJsonUrl(url, function(result) {
				Global.notify("success", "保存成功");
				$this.colseDialog(true);
			}, data);
		}
	},
	checkRequiredParam:function(){
		var $this = BizTaskInfoInputBasic;
		var $form  = $this.$form;
		var tasktitle = $form.find("#id_tasktitle").val();
		if(Biz.checkNull(tasktitle)){
			Global.notify("error", "题目标题不能为空");
			return false;
		}
	    var cometype = $form.find("#id_cometype").val();
	    if(Biz.checkNull(cometype)){
			Global.notify("error", "来源分类不能为空");
			return false;
		}
		
		var type = $form.find("#id_type").val();
		if(Biz.checkNull(type)){
			Global.notify("error", "任务类型不能为空");
			return false;
		}
		var pri = $form.find("#id_pri").val();
		if(Biz.checkNull(pri)){
			Global.notify("error", "优先级不能为空");
			return false;
		}
		var stime = $form.find("#id_stime").val();
		var ctime = $form.find("#id_ctime").val();
		if(Biz.checkNull(stime)){
			Global.notify("error", "开始时间不能为空");
			return false;
		}
		if(Biz.checkNull(ctime)){
			Global.notify("error", "截止时间不能为空");
			return false;
		}
		var taskdesc = $form.find("#id_taskdesc").val();
		if(Biz.checkNull(taskdesc)){
			Global.notify("error", "任务内容不能为空");
			return false;
		}
		return true;
	},
	initSelectParams:function(){
		var $form = BizTaskInfoInputBasic.$form;
		var cometype = $form.find("#id_cometype");
		var old_cometype = $form.find("#id_old_cometype").val();
		Biz.initSelect('BIZ_TASK_COMETYPE',cometype,old_cometype);
		
		var type = $form.find("#id_type");
		var old_type = $form.find("#id_old_type").val();
		Biz.initSelect('BIZ_TASK_TYPE',type,old_type);
	}
};
$(function(){
	BizTaskInfoInputBasic.init();
});