var SysSmstempConfigIndex={
		$form : $(".from-sys-smstemp-config-index"),
		$grid : $(".grid-sys-smstemp-config-index"),
		init : function(){
			this.initSearchForm();
			
			this.$grid.data("gridOptions",{
				 url : WEB_ROOT + '/biz/manage/smstemp/sys-smstemp-config!findByPage',
				 colModel :[{
			            label : '模版ID',
			            name : 'id',
			            search :  false,
			            sortable : false,
			            width : 60                        
			        },
			        {
			            label : '模版标题',
			            name : 'tname',
			            search :  false,
			            width : 200,
			        },
			        {
			            label : '模版内容',
			            name : 'tcontent',
			            search :  false,
			            width : 300
			        },
			        {
			            label : '操作员',
			            name : 'opid',
			            hidden:true
			        },
			        {
			            label : '状态',
			            name : 'tstatus',
			            search :  false,
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
			            width : 120
			        },
			        {
			            label : '操作部门',
			            name : 'opdept',
			            hidden:true
			        },
			        {
			            label : '操作部门',
			            name : 'opdeptname',
			            search :  false,
			            width : 100
			        },
			        {
			            label : '操作时间',
			            name : 'optime',
			            formatter: 'date',
			            search :  false,
			            width : 100
			        },
			        {
			            label : '审核人员',
			            name : 'auditid',
			            hidden:true
			        },
			        {
			            label : '审核人员',
			            name : 'auditidname',
			            search :  false,
			            width : 100
			        },
			        {
			            label : '审核部门',
			            name : 'auditdept',
			            hidden:true
			        },
			        {
			            label : '审核部门',
			            name : 'auditdeptname',
			            search :  false,
			            width : 80
			        },
			        {
			            label : '审核时间',
			            name : 'audittime',
			            search :  false,
			            formatter: 'date',
			            width : 120
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
			            label : '备注',
			            name : 'memo',
			            search :  false,
			            width : 100
			        }],
			        fullediturl : WEB_ROOT + '/biz/manage/smstemp/sys-smstemp-config!inputTabs',
					multiselect: true,
					multiboxonly:true,
					delurl : WEB_ROOT + '/biz/manage/smstemp/sys-smstemp-config!doDelete',
					gridComplete:function(){
					     //隐藏表格中的元素
						 
					     Biz.hideTableElement(SysSmstempConfigIndex.$grid,".btn-group-contexts");
					     Biz.hideTableElement(SysSmstempConfigIndex.$grid,".ui-icon-arrowstop-1-w");
					     Biz.hideTableElement(SysSmstempConfigIndex.$grid,".ui-icon-search");
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
		}
};

$(function(){
	
	SysSmstempConfigIndex.init();
	
});