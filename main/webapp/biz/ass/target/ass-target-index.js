AssTargetStoreIndex = ({
	$searchForm:$(".form-biz-ass-index-store-index"),
	$grid:$(".grid-biz-ass-store-ass-target-store-index"),
	init : function (){
		
		//初始化查询Form
		this.initSearchParam();
		
	    this.$grid.data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/target/ass-target!findByPage',
	        colModel : [ {
	            label : '指标名称',
	            name : 'name',
	            sortable : false,
	            search :  false,
	            width : 160
	        },{
	            label : 'city编码',
	            name : 'city',
	            width : 100, 
	            editable: false,
	            search :  false,
	            hidden:true,
	            align : 'left'
	        },{
	            label : '指标归属',
	            name : 'cityName',
	            width : 70, 
	            editable: true,
	            search :  false,
	            align : 'left',
	            formatter:function(cellvalue, options, rowObject){
	            	return cellvalue=='*'?'广东省':cellvalue;
	            }
	        }, {
	            label : '指标状态',
	            name : 'status',
	            width : 60, 
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'left',
	            hidden:true,
	            resizable:true,
	            stype : 'select',
	            editoptions : {
	            	value :Biz.getCacheParamDatas("DATA_STATUS")
	            }
	        },{
	            label : 'KIP-ID',
	            name : 'fieldId',
	            width : 100,
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'left'
	        },{
	            label : '统计周期',
	            name : 'assSttCycle',
	            width : 50,
	            hidden:false,
	            editable: true,
	            search :  false,
	            align : 'left',
	            formatter:function(cellvalue, options, rowObject){
	            	if(cellvalue==0) return '天';
	            	else if(cellvalue==1) return '周';
	            	else if(cellvalue==2) return '月';
	            	else if(cellvalue==3) return '年';
	            	else return '其他';
	            }
	        }, {
	            label : '取数SQL',
	            name : 'visql',
	            width : 100,
	            editable: true,
	            search :  false,
	            sortable : false,
	            align : 'left'
	        },{
	            label : '指标库说明',
	            name : 'asscontent',
	            width : 100,
	            hidden:false,
	            editable: true,
	            search :  false,
	            align : 'left'
	        }],
	        fullediturl : WEB_ROOT + '/biz/ass/target/ass-target!edit',
	        gridComplete: function () {
			     Biz.hideTableElement(AssTargetStoreIndex.$grid,".ui-icon-arrowstop-1-w");
			     Biz.hideTableElement(AssTargetStoreIndex.$grid,".ui-icon-search");
			     Biz.hideTableElement(AssTargetStoreIndex.$grid,".ui-icon-disk");
			     Biz.hideTableElement(AssTargetStoreIndex.$grid,".ui-icon-battery-2");
			     Biz.hideTableElement(AssTargetStoreIndex.$grid,".ui-icon-cancel");
			     Biz.hideTableElement(AssTargetStoreIndex.$grid,".ui-state-disabled");
	        },
	        operations : function(items) {
				var $this = AssTargetStoreIndex;
	    		var deleteBtn = $this.createCustomBtn(false, "删除指标", "fa-trash-o", AssTargetStoreIndex.deleteStore);
	    		items.push(deleteBtn);
	    	}
	    });

	},
	initSearchParam: function(){
		//初始化地市
		var $city = this.$searchForm.find("#id_city");
		
		this.$searchForm.data("formOptions", {
	        bindEvents : function() {
	   		  if(!Biz.isCurrentAdmin()){
	   			  AssTargetStoreIndex.initLocalCity($city);
	          }
	   		  else{
	   			  Biz.initSelectOpts('PRV_CITY',$city,'',{value:"*",text:"广东省"});
	          }
	        }
	    });
	},
	deleteStore : function() {
		
		var $grid=AssTargetStoreIndex.$grid;
		var ids=AssTargetStoreIndex.$grid.jqGrid('getGridParam','selarrrow');
    	if(!ids || ids.length<1){
    		alert("请选择数据");
    		return;
    	}
    	
		bootbox.confirm("您确定要删除指标嘛？", function(g) {
			if (g) {
            	var url = WEB_ROOT + '/biz/ass/target/ass-target!doDelete?ids='+ids;
        		$("body").ajaxJsonUrl(url, function(response) {
        			if (response.type == "success") {
        				Global.notify("success",response.message);
        				$grid.refresh();
        			} else {
        				Global.notify("error", response.message);
        			}
            	});
			}
		});

	},
	createCustomBtn : function(isPosition, btnTitle, btnClass, btnAction) {
		var newBtn = $('<li '
				+ (isPosition ? 'data-position="multi"' : '')
				+ 'data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa '
				+ btnClass + '"></i> ' + btnTitle + '</a></li>');

		newBtn.children("a").bind("click", btnAction);
		return newBtn;
	},
	initLocalCity:function($city){ //在查询界面，初始化非管理员的City列表，只显示本市和广东省的下拉项
		$city.empty();
		$city.append("<option value=''></option>");
		$city.append("<option value='"+Biz.LOGIN_INFO.city+"'>"+Biz.LOGIN_INFO.cityname+"</option>");
		$city.append("<option value='*'>广东省</option>");
	}
});


$(function() {
    AssTargetStoreIndex.init();
});
