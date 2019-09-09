var AdSetIndex = {
		$form: $(".form-ad-set-index"),
		$grid: $(".grid-ad-set-index"),
		init : function(){
			this.initSearchParam();
			
			this.$grid.data("gridOptions",{
				 url : WEB_ROOT + '/ad/adset/ad-set!findByPage',
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
				            	value :AdSetIndex.getCity()
				            }
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
				 fullediturl : WEB_ROOT + '/ad/adset/ad-set!inputTabs',
				 multiselect: true,
				 multiboxonly:true,
				 delurl : WEB_ROOT + '/ad/adset/ad-set!doDelete',
				 gridComplete:function(){
					     //隐藏表格中的元素
					     Biz.hideTableElement(AdSetIndex.$grid,".btn-group-contexts");
					     Biz.hideTableElement(AdSetIndex.$grid,".ui-icon-arrowstop-1-w");
					     Biz.hideTableElement(AdSetIndex.$grid,".ui-icon-search");
				  }
    			
			});
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
			var adstatus = $form.find("#id_adstatus");
			Biz.initSelect('AD_STATUS',adstatus,'');
			
			
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
	AdSetIndex.init();
});