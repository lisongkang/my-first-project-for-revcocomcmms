AssIndexTopatchAssnum = ({

	init : function() {

		$(".form-ass-index-topatch-assnum").data("formOptions", {
			bindEvents : function() {
				var $form = $(this);
			}
		});

		// 其它初使化
		var $form = $(".form-ass-index-topatch-assnum");
		var pno = $form.find("#pno");
		
		var unit = $form.find("#unit").val(); // 0-每月，1-每季度
		if (unit == 0) {
			for ( var i = 0; i < 12; i++) {
				var option = "<option value="+(i+1)+">"+(i+1)+"月</option>";
				pno.append(option);
			}
		} else {
			for ( var i = 0; i < 4; i++) {
				var option = "<option value="+(i+1)+">"+(i+1)+"季度</option>";
				pno.append(option);
			}
		}
	},
	
	doSubmit : function() {
		var $form = $(".form-ass-index-topatch-assnum");
		
		var serialno = $form.find("#serialno").val();
		
		// 格式：  pno~assnum#pno~assnum
		var tr = $form.find("#prds_tbody").find("tr");
		var assnumString = "";
		var isExist = "false";
		$(tr).each(function(key) {
			isExist = "true";
			var value = $(tr[key]).attr("id");
			var pno = value.substring(3,value.length);
//			var pno = $(tr[key]).find("td:eq(0)").html();
			var assnum = $(tr[key]).find("td:eq(1)").html();
			assnumString += pno+"~"+assnum+"#";
		});
		
		if (isExist == "false"){
			alert("请设定分期目标数！");
			return;
		}
		
		assnumString = assnumString.substring(0,assnumString.length-1);
		var reqData = {};
		reqData.assnumString = assnumString;
		reqData.serialno = serialno;
		
		$.ajax({
			type : "POST",
			url : WEB_ROOT + '/biz/ass/topatch/ass-index-topatch!saveTmpAssnum',
			dataType : 'json',
			data : reqData,
			success : function(result){
				var $dialog = $form.closest(".modal");
				$dialog.modal("hide");
				
				var callback = $dialog.data("callback");
				if (callback) {
					var retData = {};
					retData.serialno = result;
					
				   callback.call($form, retData);
				}
			}
		});
		
	},

	doClose : function() {
		var $form = $(".form-ass-index-topatch-assnum");
		var $dialog = $form.closest(".modal");
		//$dialog.modal("close");
		$dialog.modal("hide");
	},
	
	addNewRow : function(){
		var $form = $(".form-ass-index-topatch-assnum");
		var pno = $form.find("#pno").val();
		var pnotext = $form.find("#pno").find("option:selected").text();
		var assnum = $form.find("#assnum").val();
		
		if($("#pno"+pno).length>0){
			alert("该期数已设置目标数！");
			return;
		}
		
		AssIndexTopatchAssnum.addNewRow2(pno, pnotext, assnum);
	},
	
	addNewRow2 : function(pno, pnotext, assnum){
		var table = $("#prds_table");
		var row = $("<tr id='pno"+pno+"'></tr>");
		var tdrtr = "<td>"+pnotext+"</td>";
		tdrtr += "<td>"+assnum+"</td>";
		tdrtr += "<td><span class='btn-delete' onclick='AssIndexTopatchAssnum.delRow("+pno+")'>删除</span></td>";
		var td = $(tdrtr);
		row.append(td);
        table.append(row);
	},
	
	delRow : function(pno){
		$("#pno"+pno).remove();
	}

});

$(function() {

	AssIndexTopatchAssnum.init();

});
