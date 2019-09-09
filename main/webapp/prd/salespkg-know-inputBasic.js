var SalepkgKnowInputBasic =({
	
	fileupload:function(){
		$.ajaxFileUpload({
		    url : WEB_ROOT+'/prd/sale-photo-file-upload!uploadSalekgKnowPhto',
		    type : "post",
		    dataType : "json",
		    timeout : 1000,
		    secureuri : false,// 一般设置为false
		    fileElementId : 'salePhotoId',// 文件上传空间的id属性 <input type="file" id="uploadId" />
		    error : function(XMLHttpRequest, textStatus, errorThrown) {
                 
		    },
		    success : function(data) {
		    	if(data.message=="success"){
		    		$form = $(".form-biz-prd-salespkg-know-inputBasic");
			    	$form.find('input[name="icon"]').val(data.imagepath);
			    	Global.notify("success", "图片目录已经改变");	
		    	}else{
		    		Global.notify("error", data.message);
		    	}
		    	
		    }

		});
	},
 
	//上传知识库图片
	uploadSalepkgKnowPhoto:function (){
		$preElement = $("#salepkg-know-inputBasic-upload-photo");	
		var $file = $preElement.find('input[name="salePhoto"]');
	    var  filepath = $file.val();
	    if( filepath==""){   //未选择图片
	    	Global.notify("error", "请选择图片文件后，再点击上传");
	    	return false;
	    }else{
	    	var fileName= filepath.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1");  //正则表达式获取文件名，不带后缀
	    	var fileExt= filepath.replace(/.+\./,"");   //正则表达式获取后缀
	    	var filesize = 0;
	    	if (fileExt != 'jpg' && fileExt != 'gif' && fileExt != 'jpeg' && fileExt != 'png'&& fileExt != 'bmp') {
	    	   Global.notify("error", "图片格式不对，请重新上传");
	           return false;
	        }else{ 
	          SalepkgKnowInputBasic.fileupload();	
	        }
	    }
	  
	    return false;
    }
})

$(function() {
    $(".form-biz-prd-salespkg-know-inputBasic").data("formOptions", {
        bindEvents : function() {
            var $form = $(this);
            
            $form.find("select[name='objtype']").bind("change",function(){
            	$form.find("input[name='extraAttributes.objname']").val("");
				$form.find("input[name='objid']").val("");
            });
            
            $form.find(".fa-select-obj").click(function() {
            	var objtype = $form.find("select[name='objtype']").val();
            	if (objtype == "") {
            		Global.notify("error", "请选择对象类型");	
            		return;
            	}
            	var url = "";
            	var title = "";
				var id = "";
            	if (objtype == '0') {
            		title = '选择产品';
            		url = WEB_ROOT + '/prd/pcode!forward?_to_=selection';
					id = "know-select-pcode";
            	} else if (objtype == '1'){
            		title = '选择营销方案';
            		url = WEB_ROOT + '/prd/salespkg!forward?_to_=selection';
					id = "know-select-salespkg";
            	} else if(objtype =='3'){
            		title = '选择商品';
            		url = WEB_ROOT + '/prd/sales!toSalesPage?_to_=selection&objtype=3';
					id = "know-select-sales";
            	}else{
            		title = '选择商品小类';
            		url = WEB_ROOT + '/prd/sales!toSalesPage?_to_=selection&objtype=4';
					id = "know-select-sales";
            	}
	        	$(this).popupDialog({
	                url : url,
	                title : title,
					id : id,
	                callback : function(rowdata) {
	                	if (objtype == '0') {
	                		$form.find("input[name='extraAttributes.objname']").val(rowdata['pname']);
	        				$form.find("input[name='objid']").val(rowdata['id']);
	        			} else if (objtype == '1') {
	        				$form.find("input[name='extraAttributes.objname']").val(rowdata.display);
	        				$form.find("input[name='objid']").val(rowdata.id);
	        			} else {
	        				$form.find("input[name='extraAttributes.objname']").val(rowdata.display);
	        				$form.find("input[name='objid']").val(rowdata.id);
	        				$form.find("input[name='price']").val(rowdata.sums);
	        			}
	                }
	            })
	        });
            
            var $city = $form.find("select[name='city']");
            Biz.selectCityWhenAddData($city);
			Biz.disableSelect2WhenNotAdmin($city);
			
        }
    });
    
    var $form = $(".form-biz-prd-salespkg-know-inputBasic");
    //设置可销售次数：默认值
	var id = $form.find("input[name='id']").val();
	if(id==''){
		$form.find("input[name='maxtimes']").val('0');
	}
	
});