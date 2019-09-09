AssTargetTogridQueryImport=({
	$form: $(".form-biz-ass-target-togrid-query-import"),
	init : function(){
		this.$form.find("#btn_import").click(this.importlistener);
	},
	importlistener:function(event){
		var $this=AssTargetTogridQueryImport;
		var fd=new FormData($this.$form[0]);
		if($this.$form.find("#id_importExcel").val()){
			$.ajax({
		        url: WEB_ROOT + '/biz/ass/target/ass-target-togrid-query!importExcelData',
		        type: 'POST',
		        cache: false,
		        data: fd,
		        processData: false,
		        contentType: false,
		        dataType:'json',
		        success : function(response) {
		        	if (response.type == "success") {
						Global.notify("success",response.message);
						AssTargetTogridIndex.$grid.refresh();
						$this.closeWin();
					} else {
						$this.$form.find("#id_importExcel").val("");
						Global.notify("error", response.message);
					}
		        }
		    });
		}
		else{
			Global.notify("error", "请选择Excel文件");
		}
	},
	closeWin:function(){
		AssTargetTogridQueryImport.$form.find("#btn_cancel").click();
	}
});

$(function() {
	AssTargetTogridQueryImport.init();
});