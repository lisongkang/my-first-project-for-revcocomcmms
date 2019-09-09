var bizQuestionListInputBasic = {
		form :$(".form-biz-survey-biz-question-list-inputBasic"),
		data : [],
		init : function(){
			  $(".form-biz-survey-biz-question-list-inputBasic").data("formOptions", {
			        bindEvents : function() {
			            var $form = $(this);
			            $form.find("select[name='isopen']").bind("change",function(){
			            	var isopen = $form.find("select[name='isopen']").val();
			            	var $openDiv = $form.find("#id_IsopenData");
			            	var $nopenDiv = $form.find("#id_NopenData");
			            	var $nopenDiv1 = $form.find("#id_NopenData1");
			            	if(isopen == 'Y'){
			                   $openDiv.css("display","block"); 
			                   $nopenDiv.css("display","none"); 
			                   $nopenDiv1.css("display","none"); 
			                }
			                if(isopen == 'N'){
			                  $openDiv.css("display","none");
			                  $nopenDiv.css("display","block");
			                  $nopenDiv1.css("display","block");
			                }
			            });
			            
			            
			        }
			    });
		},
		addNewAnswer :function(){
			var acode = this.form.find("#answerType").val();
			var acontent = this.form.find("#answerContent").val();
			if(this.checkNull(acode)||this.checkNull(acontent)){
				Global.notify("error","答案不能为空");
				return false;
			}
			if(acontent.length>=255){
				Global.notify("error","答案内容过长，请重新输入");
				return false;
			}
			if(this.containKey(acode)){
				Global.notify("error",acode+"选项已经存在");
				return false;
			}
			
			//添加数据到data中
			var d = {acode:acode,acontent:acontent};
			this.data.push(d);
			
			//给新生成的div添加id ，用于删除时能够定位到该div
			
			var newDivID = "newDivID_"+acode;
		    var text = "<div class='row' id='"+ newDivID +"'>"
		                   +"<div class='col-md-3'>"
		                   +"<div class='form-group'>"
		                   +" <label class='control-label'></label>"
		                        +"<div class='controls'>"
		                            +acode
		                        +"</div>"
		                   +"</div>"
		                   +"</div>"
		                   
		                   +"<div class='col-md-6' >"
		                   +"<div class='form-group'>"
		                      +"<span style='font-size:18px'>"+acontent+"</span>"
		                   +"</div>"
		                   +"</div>"
		                   
                           + "<div class='col-md-1'>"
					       +"<div class='form-group'>"
						   +"<button class='btn blue' type='button'  onclick=\"bizQuestionListInputBasic.removeAnswer('"+acode+"');\" style='margin-top:2px'>"
		                   +"  删除</button>" 
		                   +"</div>"
		                   +"</div>"
		                 +"</div>";
		   var  openData= this.form.find("#id_NopenData");
		  
		   this.form.find("#answerContent").val("");
		  $(openData).append(text+"");
		},
		removeAnswer : function(key1){
			var removeDivID = "#newDivID_"+key1;//用来标识要移除的div
			var rmDiv = this.form.find(removeDivID);
			$(rmDiv).remove();
			//删除data中的对应的值
			
			var rmindex = null;
			 $(this.data).each(function(index,obj){
		    	 if(key1 == obj.acode){
		    		 rmindex = index;
		    		 return false;
		    	 }
		     })
		     if(rmindex !=null){
		    	 this.data.splice(rmindex,1);
		     }
		},
		checkNull : function(objvalue){
			if(objvalue == undefined || objvalue == ""|| objvalue==null 
					||!objvalue){
				return true;
			}
			return false;
		},
		containKey : function(key1){
			var flag = false;
		    $(this.data).each(function(index,obj){
		    	 if(key1 == obj.acode){
		    		 flag = true;
		    		 return false;
		    	 }
		     })
		     return flag;
		},
		saveQuetionData :function(){
			$this = this
			if(!this.checkRequiredData()){
				return false;
			}
			//这里需要进行长度限制的只有题目内容，答案内容已经在存入data之前做过判断
			var qcontent = this.form.find("#id_qcontent").val();
			if(qcontent.length>250){
				Global.notify("error","题目内容过长，请重新输入");
				return false;
			}
			
			var param = {}
			param.qcontent = qcontent;
			param.isopen = this.form.find("#id_isopen").val();
			
			if(param.isopen == "Y"){
				param.isok = this.form.find("#id_ok").val();
			}
			if(param.isopen == "N"){
				param.isonly = this.form.find("#id_isonly").val();
				param.data = this.data;
			}
			param.bizQuestionBO = JSON.stringify(param);
			var test = JSON.stringify(param);
			var url  = this.form.attr("action");
			var method = this.form.attr("method");
			$("body").ajaxJsonUrl(url,function(result){
				if(result.type == "success"){
					Global.notify("success", result.message);
					BizQuestionListIndex.refresh();
					$this.removeThisTab();
				}
			},param,method);
		},
		checkRequiredData: function(){
			var text = "";
			if(this.checkNull(this.form.find("#id_qcontent").val())){
				text += "题目内容、";
			}
			var isopen = this.form.find("#id_isopen");
			if(this.checkNull(this.form.find("#id_isopen").val())){
				text +="是否开发式、";
			}else if(isopen.val() == 'Y'){
				if(this.checkNull(this.form.find("#id_ok").val())){
					text +="是否图片、";
				}
			}else if(isopen.val() == 'N'){
				if(this.checkNull(this.form.find("#id_isonly").val())){
					text +="是否单选、"
				}
				if(this.data.length == 0){
					text +="答案内容、";
				}
			}
			if("" != text){
				Global.notify("error",text.substr(0,text.length-1)+" 为必填项!");
				return false;
			}
			return true;
		},
		removeThisTab : function(){
			// 移除当前编辑tab（代替取消按钮事件，因为会额外弹出提示框）
			
			
			var bodyElement = $(".root-biz-survey-biz-question-list-index");
			var activeTab = $(bodyElement).find(".active.tab-closable");
			var tabId = $(activeTab).attr("id");
			$(activeTab).remove(); // 移除当前编辑tab
			$(bodyElement).find("a[href='#" + tabId + "']").remove(); // 移除当前编辑tab标题
			$(bodyElement).find(".tab-default").click();
		}
}
$(function() {
	bizQuestionListInputBasic.init();
});