var AzQuotaIndex = {
		$form: $(".form-az-constructors-index"),
		$grid: $(".grid-az-constructors-index"),
		init : function(){
			this.initSearchParam();
			
			this.$grid.data("gridOptions",{
				 url : WEB_ROOT + '/az/constructors/az-constructors!findByPage',
				 colModel :[
					 {
				            label : '序号',
				            name : 'id',
				            hidden:true,
				            width : 0,
				            sortable : false,
				     },
				     {
				            label : '施工人id',
				            name : 'operid',
				            sortable : false,
				            search :  false,
				            width : 40
				     },
                     {
                         label : '账户',
                         name : 'loginname',
                         sortable : false,
                         search :  false,
                         width : 60
                     },
				     {
				            label : '名称',
				            name : 'name',
				            sortable : false,
				            search :  false,
				            width : 60,
				            
				     },
				     {
				            label : '身份证卡号',
				            name : 'idcard',
				            sortable : false,
				            search :  false,
				            width : 200,
				            
				     },
				     {
				            label : '银行卡号',
				            name : 'bankcard',
                            sortable : false,
                            search :  false,
                            width : 200,
				     }
				 ],
				 fullediturl : WEB_ROOT + '/az/constructors/az-constructors!edit',
				 multiselect: true,
				 multiboxonly:true,
				 delurl : WEB_ROOT + '/az/constructors/az-constructors!doDelete',
				 gridComplete:function(){
					     //隐藏表格中的元素
					     Biz.hideTableElement(AzQuotaIndex.$grid,".btn-group-contexts");
					     Biz.hideTableElement(AzQuotaIndex.$grid,".ui-icon-arrowstop-1-w");
					     Biz.hideTableElement(AzQuotaIndex.$grid,".ui-icon-search");
				  }
    			
			});
		},
		initSearchParam: function(){
			
			/*//初始化地市
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
		    });*/

		},
		checkRequired : function(){
			return true;
		},
		/*getCity:function(){
			var cityList = Biz.getCacheParamDatas("PRV_CITY");
			cityList['*'] = "全省";
            cityList['SZ'] = "深圳市";
			return cityList;
		}*/
		
};
$(function(){
	AzQuotaIndex.init();
});