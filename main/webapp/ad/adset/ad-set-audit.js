var AdauditIndex = {
		$form: $(".form-ad-audit-index"),
		$grid: $(".grid-ad-audit-index"),
		init : function(){
			this.initSearchParam();
			
			this.$grid.data("gridOptions",{
				 url : WEB_ROOT + '/ad/adset/ad-set!findByPageFromAudit',
				 colModel :[
					 {
				            label : '广告ID',
				            name : 'id',
				            hidden:true,
				            width : 0,
				            sortable : false,
				     },
				     {
				            label : '地市',
				            name : 'city',
				            width : 40,
				            sortable : false,
				            search :  false,
				            stype : 'select',
				            editoptions : {
				            	value :AdauditIndex.getCity()
				            }
				     },
				     {
							label : '操作',
							name : 'self_opt',
							sortable : false,
							search :  false,
							width : 80
						},
				     {
				            label : '广告名称',
				            name : 'adname',
				            sortable : false,
				            search :  false,
				            width : 80,
				     },
				     {
				            label : '广告类型',
				            name : 'adtype',
				            sortable : false,
				            search :  false,
				            width : 80,
				            stype : 'select',
				            editoptions : {
				            	value :Biz.getCacheParamDatas("AD_TYPE")
				            }
				     },
				     {
				            label : '广告对象',
				            name : 'adobj',
				            sortable : false,
				            search :  false,
				            width : 120,
				            
				     },
				     {
				            label : '优先级',
				            name : 'pri',
				            sortable : false,
				            search :  false,
				            width : 50,
				            
				     },
				     {
				            label : '状态',
				            name : 'adstatus',
				            sortable : false,
				            search :  false,
				            width : 80,
				            stype : 'select',
				            editoptions : {
				            	value :Biz.getCacheParamDatas("AD_STATUS")
				            }
				     },
				     {
				            label : '录入人员',
				            name : 'optname',
				            sortable : false,
				            search :  false,
				            width : 80,
				     },
				     {
				            label : '录入时间',
				            name : 'opttime',
				            sortable : false,
				            search :  false,
				            formatter: 'date',
				            width : 80,
				     },
				     {
				            label : '审核人员',
				            name : 'auditid',
				            width : 10,
				            hidden:true
				     },
				     {
				            label : '审核人员',
				            name : 'auditname',
				            sortable : false,
				            search :  false,
				            width : 80,
				     },
				     {
				            label : '审核时间',
				            name : 'audittime',
				            sortable : false,
				            formatter: 'date',
				            search :  false,
				            width : 80,
				     },
				    {
				            label : '备注',
				            name : 'memo',
				            sortable : false,
				            search :  false,
				            width : 150,
				     }
				    
				     
				 ],
				 multiselect: false,
				 gridComplete:function(){
					     //隐藏表格中的元素
					     Biz.hideTableElement(AdauditIndex.$grid,".btn-group-contexts");
					     Biz.hideTableElement(AdauditIndex.$grid,".ui-icon-arrowstop-1-w");
					     Biz.hideTableElement(AdauditIndex.$grid,".ui-icon-search");
					     
					     Biz.addCustomBtnToGrid(".grid-ad-audit-index", [ {
								subBtns : [ {
									btnName : "审核",
									//1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
									canAddBtn : function(grid, rowData) {
										return rowData.adstatus == "2";
									},
									
									btnAction : "AdauditIndex.auditAdconfig"
								}, {
									btnName : "上架",
									canAddBtn : function(grid, rowData) {
										return rowData.adstatus == "3";
									},
									btnAction : "AdauditIndex.upAdConfig"
								}, {
									btnName : "下架",
									canAddBtn : function(grid, rowData) {
										return rowData.adstatus == "5";
									},
									btnAction : "AdauditIndex.downAdConfig"
								} ]
							} ]);
				  }
    			
			});
		},
		/**
		 * 审核
		 */
		auditAdconfig : function(rowid){
		  var rowData = AdauditIndex.$grid.jqGrid("getRowData", rowid);
		  //1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
		  var tipmsg ="<div style='word-wrap:break-word; word-break:break-all;'>" 
		  		         +"<div class='form-group '>"
								+"<label class='control-label' style='width:120px'>评审结果</label>"
					            +"<div class='f-con'>"
					            +"<ul class='f-con c-name' id='id_adconfig_audit_result' name='auditResult'>"
					            +"  <li onclick='Biz.itemClick(this);' class='item selected' value='3'><span>通过</span><b>　</b></li>"
					            +"  <li onclick='Biz.itemClick(this);' class='item' value='4'><span>不通过</span><b>　</b></li>"
					            +"</ul>"
					            +"</div>"
		  	             +"</div>";;
		  bootbox.dialog({
              message: tipmsg,
              title: "广告评审",
            
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
                    	//1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
                    	  var auditResult = $("#id_adconfig_audit_result").find("li.selected").val();
                    	  if(Biz.checkNull(auditResult)){
                    		  Global.notify("error", "未选择评审结果");
                    		  return;
                    	  }
                    	  AdauditIndex.editAdConfigStatus(auditResult,rowData.id);
                      }
                  }
              }
		  });
		},
		/**
		 * 上架
		 */
		upAdConfig : function(rowid){
			 bootbox.dialog({
	              message: "确定将所选广告“上架”？",
	            // //1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
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
	                    	  var rowData = AdauditIndex.$grid.jqGrid("getRowData", rowid);
	                    	  AdauditIndex.editAdConfigStatus('5',rowData.id);
	                      }
	                  }
	              }
			 });
		},
		/**
		 * 下架
		 */
		downAdConfig : function(rowid){
			bootbox.dialog({
	              message: "确定将所选广告“下架”？",
	            // //1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
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
	                    	  var rowData = AdauditIndex.$grid.jqGrid("getRowData", rowid);
	                    	  AdauditIndex.editAdConfigStatus('6',rowData.id);
	                      }
	                  }
	              }
			 });
		},
		/**
		 * 修改广告状态
		 * 
		 * @param adstatus 状态值 //1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
		 * @param id 广告id
		 */
		editAdConfigStatus:function(adstatus,id){
			var data = {};
			var adConfig = {};
			//传过来的id 为 <span>3</span>  
			adConfig.id = $(id).html();
			
			adConfig.adstatus = adstatus ;
			data.adConfig = JSON.stringify(adConfig);
			var url = WEB_ROOT + '/ad/adset/ad-set!editAdConfigStatus';
			 $("body").ajaxJsonUrl(url, function(result) {
		            Global.notify("success", "操作成功");
		            Biz.refreshGrid(".grid-ad-audit-index");
 			},data);
		},
		initSearchParam: function(){
			
			//初始化地市
			var $form = this.$form;
			var $city = $form.find("#id_city");
			//初始化地市
			$form.data("formOptions", {
		        bindEvents : function() {
		            if(!Biz.isCurrentAdmin()){
		            	Biz.initSelect('PRV_CITY',$city,Biz.LOGIN_INFO.city);
		 	            Biz.disableSelect2WhenNotAdmin($city);
		            }else{
		            	Biz.initSelect('PRV_CITY',$city);
		            }
		           
		        }
		    });
 	        
 	       
			//初始化广告类型
 	        var adtype =$form.find("#id_adtype");
			Biz.initSelect('AD_TYPE',adtype,'');
			
			 //初始化广告状态
			this.initAdStatus();
		},
		/**
		 * 初始化广告状态//展示【待审核/审核通过/已上架】的
		 */
		initAdStatus : function(){
			//1已保存2待审核 3审核通过 4审核不通过 5已上架 6已下架
			var $form = this.$form;
 	        var adtype =$form.find("#id_adstatus");
 	        adtype.empty();
			var adtypes =[
			              {id:2,name:'待审核'},
			              {id:3,name:'审核通过'},
			              {id:5,name:'已上架'},
			             ];
			var option = "";
			$.each(adtypes,function(index,item){
				option = '<option value="' + item.id + '">' + item.name+ '</option>';
				adtype.append(option);
			});
		},
		checkRequired : function(){
			return true;
		},
		getCity:function(){
			var cityList = Biz.getCacheParamDatas("PRV_CITY");
			cityList['*'] = "所有"; 
			return cityList;
		}
};

$(function(){
	AdauditIndex.init();
});