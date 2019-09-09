var AzQuotaazblIndex = {
		$form: $(".form-az-quota-azblindex"),
		$grid: $(".grid-az-quota-azblindex"),
		init : function(){
			this.initSearchParam();
			
			this.$grid.data("gridOptions",{
				 url : WEB_ROOT + '/az/quotabl/az-quotabl!findAzblByPage',
				 colModel :[
					 {
				            label : '序号',
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
                             value :AzQuotaazblIndex.getCity()
                         }


                     },
				     {
				            label : '地市类别',
				            name : 'citydivde',
				            sortable : false,
				            search :  false,
				            width : 80,

				     },
				     {
				            label : '安装定额最高比例(不超过0.5)',
				            name : 'quotaratio',
				            sortable : false,
				            search :  false,
				            width : 60,

				     }
				 ],
				 fullediturl : WEB_ROOT + '/az/quotabl/az-quotabl!edit',
				 multiselect: true,
				 multiboxonly:true,
				 delurl : WEB_ROOT + '/az/quotabl/az-quotabl!doDelete',
				 gridComplete:function(){
					     //隐藏表格中的元素
					     Biz.hideTableElement(AzQuotaazblIndex.$grid,".btn-group-contexts");
					     Biz.hideTableElement(AzQuotaazblIndex.$grid,".ui-icon-arrowstop-1-w");
					     Biz.hideTableElement(AzQuotaazblIndex.$grid,".ui-icon-search");
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
 	        
 	        //初始化地市分类
 	        var id_citytype =$form.find("#id_citytype");
			Biz.initSelect('CITY_DIVDED',id_citytype,'');

		},
		checkRequired : function(){
			return true;
		},
		getCity:function(){
			var cityList = Biz.getCacheParamDatas("PRV_CITY");
			cityList['GD'] = "全省";
            cityList['SZ'] = "深圳市";
            cityList['YJ'] = "阳江市";
			return cityList;
		}
		
};
$(function(){
	AzQuotaazblIndex.init();
});