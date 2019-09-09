$(function() {
    $(".form-prv-operator-inputBasic").data("formOptions", {
    	bindEvents : function() {
            var $form = $(this);
            
	        // 客户元素处理（没有部门选项暂时注释代码）
	        /*$form.find(".fa-select-department").click(function() {
	        	$(this).popupDialog({
	                url : WEB_ROOT + '/prv/prv-department!forward?_to_=selection',
	                title : '选取部门',
	                callback : function(rowdata) {
	            		$form.find("input[name='departmentName']").val(rowdata.name);
	                    $form.find("input[name='departmentId']").val(rowdata.id);
	                }
	            })
	        });
	        $form.find("input[name='departmentName']").autocomplete({
	            autoFocus : true,
	            source : function(request, response) {
	                var data = Biz.queryDepartmentCache(request.term);
	                return response(data);
	            },
	            minLength : 2,
	            select : function(event, ui) {
	                var item = ui.item;
	                this.value = item.display;
	                $form.find("input[name='departmentName']").val(item.display);
	                $form.find("input[name='departmentId']").val(item.id);
	                event.stopPropagation();
	                event.preventDefault();
	                return false;
	            },
	            change : function(event, ui) {
	                if (ui.item == null || ui.item == undefined) {
	                    $(this).val("");
	                    $(this).focus();
	                }
	            }
	        }).focus(function() {
	            $(this).select();
	        }).dblclick(function(event) {
	            $form.find(".fa-select-department").click();
	        });*/
        }
    });
});