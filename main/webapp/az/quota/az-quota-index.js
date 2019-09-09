var AzQuotaIndex = {
		$form: $(".form-az-quota-index"),
		$grid: $(".grid-az-quota-index"),
		init : function(){
            this.$form.find("#btn_import").click(this.importlistener);
			this.initSearchParam();
			
			this.$grid.data("gridOptions",{
				 url : WEB_ROOT + '/az/quota/az-quota!findByPage',
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
                             value :AzQuotaIndex.getCity()
                         }

				     },
				     {
				            label : '新编号',
				            name : 'newnumber',
				            sortable : false,
				            search :  false,
				            width : 80
				     },
                     {
                         label : '名称',
                         name : 'constname',
                         sortable : false,
                         search :  false,
                         width : 120
                     },
				     {
				            label : '项目明细',
				            name : 'constdetail',
				            sortable : false,
				            search :  false,
				            width : 60,
				            
				     },
				     {
				            label : '单位',
				            name : 'company',
				            sortable : false,
				            search :  false,
				            width : 50,
				            
				     },
				     {
				            label : '一类地区价格',
				            name : 'oneprice',
				            sortable : false,
				            search :  false,
				            width : 100,
				     },
				     {
				            label : '二类地区价格',
				            name : 'twoprice',
				            sortable : false,
				            search :  false,
				            width : 100,
				     },
				     {
				            label : '三类地区价格',
				            name : 'threeprice',
                            sortable : false,
                            search :  false,
                            width : 100,
				     },
				     {
				            label : '四类地区价格',
				            name : 'fourprice',
				            sortable : false,
				            search :  false,
				            width : 100,
				     },
				     {
				            label : '工作内容',
				            name : 'constcontent',
                            sortable : false,
                            search :  false,
                            width : 200,
				     },
				    {
				            label : '附件',
				            name : 'fileids',
				            sortable : false,
				            search :  false,
				            width : 150,
				     }

				 ],
				 fullediturl : WEB_ROOT + '/az/quota/az-quota!edit',
				 multiselect: true,
				 multiboxonly:true,
				 delurl : WEB_ROOT + '/az/quota/az-quota!doDelete',
				 gridComplete:function(){
					     //隐藏表格中的元素
					     Biz.hideTableElement(AzQuotaIndex.$grid,".btn-group-contexts");
					     Biz.hideTableElement(AzQuotaIndex.$grid,".ui-icon-arrowstop-1-w");
					     Biz.hideTableElement(AzQuotaIndex.$grid,".ui-icon-search");
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
		},
    importlistener:function(event){
        var $this=AzQuotaIndex;
        var fd=new FormData($this.$form[0]);
        if($this.$form.find("#id_importExcel").val()){
            $.ajax({
                url: WEB_ROOT + '/az/quota/az-quota!importExcelData',
                type: 'POST',
                cache: false,
                data: fd,
                processData: false,
                contentType: false,
                dataType:'json',
                success : function(response) {
                    if (response.type == "success") {
                        Global.notify("success",response.message);

                    } else {
                        $this.$form.find("#id_importExcel").val("");
                        Global.notify("error", response.message);
                    }
                }
            });
        }
        else{
            Global.notify("error", "请选择Excel文件");
        }
    },
		checkRequired : function(){
			return true;
		},
		getCity:function(){
			var cityList = Biz.getCacheParamDatas("PRV_CITY");
			cityList['*'] = "全省";
            cityList['SZ'] = "深圳市";
			return cityList;
		}
		
};
$(function(){
	AzQuotaIndex.init();
});