var SysProblem = {
	$form : $(".form-biz-sys-problem-index"),
	$grid : $(".grid-biz-sys-problem-index"),
	init : function(){
		this.initSearchForm();
		
		this.$grid.data("gridOptions",{
			 url : WEB_ROOT + '/biz/manage/problem/sys-problem!findByPage',
			 colModel :[{
		            label : '流水号',
		            name : 'id',
		            search :  false,
		            hidden : true                          
		        },
		        {
		            label : '问题类型',
		            name : 'pltype',
		            search :  false,
		            width : 100,
		            stype : 'select',
		            editoptions : {
		            	value :Biz.getCacheParamDatas("SYS_PLTYPE")
		            }
		        },
		        {
		            label : '问题标题',
		            name : 'plname',
		            search :  false,
		            width : 150
		        },
		        {
		            label : '问题描述',
		            name : 'pldesc',
		            search :  false,
		            width : 100
		        },
		        {
		            label : '提交人',
		            name : 'suboperid',
		            hidden:true
		        },
		        {
		            label : '提交人',
		            name : 'subopername',
		            search :  false,
		            width : 120
		        },
		        {
		            label : '提交部门',
		            name : 'suboperdept',
		            hidden:true
		        },
		        {
		            label : '提交部门',
		            name : 'suboperdeptname',
		            search :  false,
		            width : 80
		        },
		        {
		            label : '提交时间',
		            name : 'subtime',
		            formatter: 'date',
		            search :  false,
		            width : 80
		        },
		        {
		            label : '处理人',
		            name : 'dealopername',
		            search :  false,
		            width : 120
		        },
		        {
		            label : '问题状态',
		            name : 'plstate',
		            search :  false,
		            width : 80,
		            stype : 'select',
		            editoptions : {
		            	value :Biz.getCacheParamDatas("SYS_PLSTATE")
		            }
		        },
		        {
		            label : '处理描述',
		            name : 'dealdesc',
		            search :  false,
		            editable: true,
		            width : 180
		        } ,{
		            label : '处理人',
		            name : 'dealoperid',
		            hidden:true
		        },
		        {
		            label : '处理时间',
		            name : 'dealtime',
		            formatter: 'date',
		            search :  false,
		            width : 80
		        },
		        {
		            label : '处理部门',
		            name : 'dealoperdeptname',
		            search :  false,
		            width : 80
		        },
		        {
		            label : '处理部门',
		            name : 'dealoperdept',
		            hidden:true
		        },
		        {
		            label : '地市',
		            name : 'city',
		            search :  false,
		            width : 50,
		            stype : 'select',
		            editoptions : {
		            	value :Biz.getCacheParamDatas("PRV_CITY")
		            }
		        },
		        {
		            label : '业务区',
		            name : 'areaid',
		            hidden:true
		        },{
		            label : '业务区',
		            name : 'areaname',
		            search :  false,
		            width : 80
		        },
		        {
		            label : '备注',
		            name : 'memo',
		            search :  false,
		            width : 100
		        }],
		        fullediturl : WEB_ROOT + '/biz/manage/problem/sys-problem!inputTabs',
				multiselect: false,
				 gridComplete:function(){
			 	
			     	//隐藏添加按钮
			     	Biz.hideAddButton(SysProblem.$grid);
			     }
		});
	},
	initSearchForm : function(){
		
		//问题类型
		var pltype = this.$form.find("#id_pltype");
		Biz.initSelect('SYS_PLTYPE',pltype,'');
		
		//问题状态
		var plstate = this.$form.find("#id_plstate");
		Biz.initSelect('SYS_PLSTATE',plstate,'');
		this.$form.data("formOptions", {
			initArea : function(city) {
				var $form = $(this);
				var area = $form.find("#id_area");
				if (!city) {
					area.empty();
					area.select2("val", "");
					return;
				}
		    	var url = WEB_ROOT + "/area!findAreaList?rows=-1&city=" + city;
		    	$form.ajaxJsonUrl(url, function(data) {
	        		area.empty();
	        		var option = '';
	        		area.append("<option value=''></option>");
	        		$.each(data, function(i, item) {
	                    option = '<option value="'+item.id+'">'+item.name+'</option>';
	                    area.append(option);
	                });
	                area.select2("val", '');
		    	});
			},
	        bindEvents : function() {
	            var $form = $(this);
	            var $city = $form.find("#id_city");
	            $city.change(function() {
	            	$form.data("formOptions").initArea.call($form, $city.val());
	            });
	            if (!$city .val()) {
	            	if (Biz.LOGIN_INFO) {
	            		$city .select2("val", Biz.LOGIN_INFO.city);
	            		$form.data("formOptions").initArea.call($form, Biz.LOGIN_INFO.city);
	            	}
	            }
	            if(!Biz.isCurrentAdmin()){
	            	Biz.initSelect('PRV_CITY',$city,Biz.LOGIN_INFO.city);
	 	            Biz.disableSelect2WhenNotAdmin($city);
	            }else{
	            	Biz.initSelect('PRV_CITY',$city);
	            }
	           
	        }
	    });
		
		
	}
	
};

$(function(){
	SysProblem.init();
});