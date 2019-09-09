var TaskMonitorInfo={
    $form :$(".form-biz-task-info-taskmonitorinfo"),
	init : function(){
		this.initSelectParams();
	},
	/**
	 * @param flag:是否刷新表格
	 */
	colseDialog:function(flag){
		var $this = TaskMonitorInfo.$form;
		var $dialog =$this.closest(".modal");
		$dialog.modal("hide");
		var callback = $dialog.data("callback");
		if (callback) {
			callback.call($dialog,flag);//
		}
	},
	
	initSelectParams:function(){
		var $form = TaskMonitorInfo.$form;
		var cometype = $form.find("#id_cometype");
		var old_cometype = $form.find("#id_old_cometype").val();
		Biz.initSelect('BIZ_TASK_COMETYPE',cometype,old_cometype);
		
		var type = $form.find("#id_type");
		var old_type = $form.find("#id_old_type").val();
		Biz.initSelect('BIZ_TASK_TYPE',type,old_type);
	}
};
$(function(){
	TaskMonitorInfo.init();
});