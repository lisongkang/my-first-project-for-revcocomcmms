var PrvSalesPointIndex = {
		pageMainDiv : $(".div-biz-prd-prv-sales-point-index"),
		init :function(){
			this.searchFormObj.init();
			this.gridObj.init();
		},
		searchFormObj:{
			form : $(".form-biz-prd-prv-sales-point-index"),
			init : function(){
				var $this = this;
				//绑定关联事件
			/*	this.form.find("#id_city").bind("change",function(e){
					//值改变与业务区下拉项关
					$this.initArea($(this).val());
				});*/
     		   this.initCity();
     		   this.initOpCode();
			},
			initCity : function(){
				var $form = this.form;
				var loginCity = Biz.LOGIN_INFO.city;
				var rolelevel = Biz.LOGIN_INFO.rolelevel;
				var city = $form.find("#id_city");
				if(Biz.isCurrentAdmin()){
					var citySelectGcode = "PRV_CITY";
					Biz.initSelect(citySelectGcode, city,loginCity,function(){
					//city.change();
					});
				}else{
					 city.append('<option value=""></option>');
			 		 var option  = '<option value="' + Biz.LOGIN_INFO.city + '"'
									+ ' selected="selected"' + '>'
									+ Biz.LOGIN_INFO.cityname + '</option>';
					 city.append(option);
					 city.attr("disabled",true).removeAttr("name");
					 
					//city表单项被禁用后传不了参数值，因此需要用隐藏表单项传areaid值
					 $form.find("#backupCityId").attr("name", "quePrvSalesPointParams.city").val(Biz.LOGIN_INFO.city);
					// city.change();
				}
			},
			initArea : function(selectCity){
				
				var $form = this.form;
				var rolelevel = Biz.LOGIN_INFO.rolelevel;
				var area = $form.find("#id_area");
				if(rolelevel!=9){
					 area.append('<option value=""></option>');
			 		 var option  = '<option value="' + Biz.LOGIN_INFO.areaid + '"'
									+ ' selected="selected"' + '>'
									+ Biz.LOGIN_INFO.areaname + '</option>';
					 area.append(option);
					$(area).attr("disabled", true).removeAttr("name");
					$form.find("#backupAreaId").attr("name","quePrvSalesPointParams.areaid").val(Biz.LOGIN_INFO.areaid);
				}else{
					areaSelectGcode = "PRV_AREA_BY_CITY&mcode=" + selectCity;
					Biz.initSelect(areaSelectGcode, area,Biz.LOGIN_INFO.areaid);
				}
			},
			initOpCode : function() {
				Biz.initSelect("BIZ_OPCODE", this.form.find("#id_opcode"));
			}
//			
		},
		gridObj :{
			grid : $(".grid-biz-prd-prv-sales-point-index"),
			init : function(){
				this.grid.data("gridOptions",{
					url : WEB_ROOT + '/prd/prv-sales-point!findByPage',
			        colModel : [{
			            label : '流水号',
			            name : 'id',
			            hidden : true                          
			        },{
			            label : '地市ID',
			            name : 'city',
			            hidden : true,
			            editoptions : {
							value : Biz.getPrvParamListDatas("PRV_CITY")
						}
			        }, {
			            label : '地市',
			            name : 'cityname',
			            width : 50,
			            align : 'left'
			        }/*, {
			            label : '业务区ID',
			            name : 'areaid',
			            hidden : true
			        }, {
			            label : '业务区',
			            name : 'areaname',
			            width : 50,
			            align : 'right'
			        } ,*/ ,{
			            label : 'opcode',
			            name : 'opcode',
			            hidden : true
			        }, {
			            label : '业务操作',
			            name : 'opcodename',
			            width : 40,
			            align : 'left'
			        }, {
			            label : '商品ID',
			            name : 'salesid',
			            width : 60,
			            align : 'left'
			        }, {
			            label : '商品名称',
			            name : 'salesname',
			            width : 60,
			            align : 'left'
			        }, {
			            label : '用工类型',
			            name : 'wtype',
			            width : 60,
			            align : 'right',
			            stype : 'select',
						editoptions : {
							value : {0:'正式员工',1:'临时员工',2:'劳务派遣' }
						}
			        }, {
			            label : '销售积分',
			            name : 'points',
			            width : 40,
			            align : 'right',
			            editable: false
			        }, {
			            label : '状态',
			            name : 'isvalid',
			            width : 40,
			            align : 'right',
			            stype : 'select',
			            editoptions : {
								value : {0:'有效',1:'无效'}
					    },
					    editable:true
			        },{
			            label : '录入时间',
			            name : 'intime',
			            width : 80,
			            formatter: 'date',
			            align : 'center'
			        }, {
			            label : '失效时间',
			            name : 'etime',
			            width : 80,
			            formatter: 'date',
			            align : 'center'
			        }],
			    	filterToolbar : false,
			        ondblClickRow:function(){},
			      editurl : WEB_ROOT + '/prd/prv-sales-point!doUpdate',
			     fullediturl : WEB_ROOT + '/prd/prv-sales-point!inputTabs'
				});
			}
		},
		refresh : function() {
			Biz.refreshGrid(".grid-biz-prd-prv-sales-point-index"); // 表单提交后直接刷新表格
		}
}
$(function() {
    PrvSalesPointIndex.init();
});
