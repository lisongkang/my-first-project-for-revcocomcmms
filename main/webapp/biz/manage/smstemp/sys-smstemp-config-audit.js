var SysSmstempConfigAudit={
		$form : $(".from-sys-smstemp-config-audit"),
		$grid : $(".grid-sys-smstemp-config-audit"),
		init : function(){
			this.initSearchForm();
			
			this.$grid.data("gridOptions",{
				 url : WEB_ROOT + '/biz/manage/smstemp/sys-smstemp-config!findByPage',
				 colModel :[{
			            label : '模板ID',
			            name : 'id',
			        	sortable : false,
			        	 search :  false,
			        	width : 60
			        },
			         {
						label : '操作',
						name : 'self_opt',
						sortable : false,
						search :  false,
						width : 80
					},
			        {
			            label : '模版标题',
			            name : 'tname',
			            search :  false,
			            sortable : false,
			            width : 200,
			        },
			        {
			            label : '模版内容',
			            name : 'tcontent',
			            search :  false,
			            sortable : false,
			            width : 300
			        },
			        {
			            label : '操作员',
			            name : 'opid',
			            sortable : false,
			            hidden:true
			        },
			        {
			            label : '状态',
			            name : 'tstatus',
			            search :  false,
			            sortable : false,
			            width : 100,
			            stype : 'select',
			            editoptions : {
			            	value :Biz.getCacheParamDatas("SYS_SMSTEMP_STATU")
			            }
			        },
			        {
			            label : '操作员',
			            name : 'opidname',
			            search :  false,
			            sortable : false,
			            width : 120
			        },
			        {
			            label : '操作部门',
			            name : 'opdept',
			            sortable : false,
			            hidden:true
			        },
			        {
			            label : '操作部门',
			            name : 'opdeptname',
			            search :  false,
			            sortable : false,
			            width : 100
			        },
			        {
			            label : '操作时间',
			            name : 'optime',
			            formatter: 'date',
			            sortable : false,
			            search :  false,
			            width : 100
			        },
			        {
			            label : '审核人员',
			            name : 'auditid',
			            sortable : false,
			            hidden:true
			        },
			        {
			            label : '审核人员',
			            name : 'auditidname',
			            search :  false,
			            sortable : false,
			            width : 100
			        },
			        {
			            label : '审核部门',
			            name : 'auditdept',
			            sortable : false,
			            
			            hidden:true
			        },
			        {
			            label : '审核部门',
			            name : 'auditdeptname',
			            sortable : false,
			            search :  false,
			            width : 80
			        },
			        {
			            label : '审核时间',
			            name : 'audittime',
			            search :  false,
			            formatter: 'date',
			            sortable : false,
			            width : 120
			        },
			        {
			            label : '地市',
			            name : 'city',
			            search :  false,
			            width : 50,
			            sortable : false,
			            stype : 'select',
			            editoptions : {
			            	value :Biz.getCacheParamDatas("PRV_CITY")
			            }
			        },
			        {
			            label : '备注',
			            name : 'memo',
			            search :  false,
			            sortable : false,
			            width : 100
			        }],
					multiselect: false,
					gridComplete:function(){
					     //隐藏表格中的元素
					     Biz.hideTableElement(SysSmstempConfigAudit.$grid,".btn-group-contexts");
					     Biz.hideTableElement(SysSmstempConfigAudit.$grid,".ui-icon-arrowstop-1-w");
					     Biz.hideTableElement(SysSmstempConfigAudit.$grid,".ui-icon-search");
				         
					     Biz.addCustomBtnToGrid(".grid-sys-smstemp-config-audit", [ {
								subBtns : [ {
									btnName : "审核",
									//0未提交1待审核2审核通过3审核不通过4失效 .只有待审核的才显示审核按钮
									canAddBtn : function(grid, rowData) {
										return rowData.tstatus == 1;
									},
									
									btnAction : "SysSmstempConfigAudit.auditSmstemp"
								}, {
									btnName : "失效",
									canAddBtn : function(grid, rowData) {
										return rowData.tstatus == 2;
									},
									btnAction : "SysSmstempConfigAudit.editSmsStatusToUseless"
								} ]
							} ]);
					}
			});
		},
	
		initSearchForm : function(){
			//初始化模板状态
			var status = this.$form.find("#id_tstatus");
			Biz.initSelect('SYS_SMSTEMP_STATU',status,'');
			
			//初始化地市
			this.$form.data("formOptions", {
		        bindEvents : function() {
		            var $form = $(this);
		            var $city = $form.find("#id_city");
		            if(!Biz.isCurrentAdmin()){
		            	Biz.initSelect('PRV_CITY',$city,Biz.LOGIN_INFO.city);
		 	            Biz.disableSelect2WhenNotAdmin($city);
		            }else{
		            	Biz.initSelect('PRV_CITY',$city);
		            }
		           
		        }
		    });
		},
		//审核模板 	//0未提交1待审核2审核通过3审核不通过4失效
		auditSmstemp  : function(rowid){
		  var rowData = SysSmstempConfigAudit.$grid.jqGrid("getRowData", rowid);
		  
		  var tipmsg ="<div style='word-wrap:break-word; word-break:break-all;'>" 
		  		         +"<div class='form-group '>"
								+"<label class='control-label' style='width:120px'>评审结果</label>"
					            +"<div class='f-con'>"
					            +"<ul class='f-con c-name' id='id_audit_result' name='auditResult'>"
					            +"  <li onclick='Biz.itemClick(this);' class='item selected' value='2'><span>通过</span><b>　</b></li>"
					            +"  <li onclick='Biz.itemClick(this);' class='item' value='3'><span>不通过</span><b>　</b></li>"
					            +"</ul>"
					            +"</div>"
		  	             +"</div>";;
		  bootbox.dialog({
              message: tipmsg,
              title: "短信模板评审",
            
              buttons: {
                  Cancel: {
                      label: "取消",
                      className: "btn-default",
                      callback: function () {
                      }
                  }
                  , OK: {
                      label: "确定",
                      className: "btn-primary",
                      callback: function () {
                    	  //0未提交1待审核2审核通过3审核不通过4失效
                    	  var auditResult = $("#id_audit_result").find("li.selected").val();
                    	  if(Biz.checkNull(auditResult)){
                    		  Global.notify("error", "未选择评审结果");
                    		  return;
                    	  }
                    	  SysSmstempConfigAudit.editSmstempStatus(auditResult,rowData.id);
                      }
                  }
              }
          });
		},
		
		/**
		 * 修改短信模板状态
		 * 
		 * @param tstatus 状态值 //0未提交1待审核2审核通过3审核不通过4失效
		 * @param id  短信模板id
		 */
		editSmstempStatus : function(tstatus,id){

			var data = {};
			var sysSmstempConfig = {};
			//传过来的id 为 <span>3</span>  
			sysSmstempConfig.id = $(id).html();
			
			sysSmstempConfig.tstatus = tstatus ;
			data.sysSmstempConfig = JSON.stringify(sysSmstempConfig);
			var url = WEB_ROOT + '/biz/manage/smstemp/sys-smstemp-config!editSmstempStatus';
			 $("body").ajaxJsonUrl(url, function(result) {
		            Global.notify("success", "操作成功");
		            Biz.refreshGrid(".grid-sys-smstemp-config-audit");
 			},data);
			
		},
		/**
		 * 让短信模板失效
		 */
		editSmsStatusToUseless : function(rowid){
			
			 bootbox.dialog({
	              message: "确定将所选模板置为“失效”？",
	            //0未提交1待审核2审核通过3审核不通过4失效
	              buttons: {
	                  Cancel: {
	                      label: "取消",
	                      className: "btn-default",
	                      callback: function () {
	                      }
	                  }
	                  , OK: {
	                      label: "确定",
	                      className: "btn-primary",
	                      callback: function () {
	                    	  var rowData = SysSmstempConfigAudit.$grid.jqGrid("getRowData", rowid);
	              			//传过来的id 为 <span>3</span> 
	              			 id = $(rowData.id).html();
	              			 var data = {};
	              			 data.id =  id;
	              			 var url = WEB_ROOT + '/biz/manage/smstemp/sys-smstemp-config!editSmsStatusToUseless';
	              			 $("body").ajaxJsonUrl(url, function(result) {
	              		            Global.notify("success", "操作成功");
	              		            Biz.refreshGrid(".grid-sys-smstemp-config-audit");
	               			},data);
	                      }
	                  }
	              }
	          });
			
		}
};

$(function(){
	
	SysSmstempConfigAudit.init();
	
});