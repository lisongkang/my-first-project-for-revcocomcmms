AssTargetTocityIndex = ({
	$searchForm:$(".form-biz-ass-target-tocity"),
	$grid:$(".grid-biz-ass-target-ass-target-tocity"),
	init : function (){
		this.initSearchParam();//初始化查询Form
		this.$grid.data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/target/ass-target-tocity!findByPage',
	        colModel : [{
	            label : 'tocityId',
	            name : 'tocityId',
	            hidden:true,
	            width : 100,  
	            editable: false,
	            search :  false,
	            align : 'left',
	            key:true
	        },{
	            label : '指标归属',
	            name : 'assCityName',
	            width : 70,   
	            editable: true,
	            search :  false,
	            align : 'left',
	            formatter:function(cellvalue, options, rowObject){
	            	return cellvalue=='*'?'广东省':cellvalue;
	            }
	        },{
	            label : '使用地市',
	            name : 'cityName',
	            width : 70,   
	            editable: true,
	            search :  false,
	            align : 'left',
	            formatter:function(cellvalue, options, rowObject){
	            	return cellvalue=='*'?'广东省':cellvalue;
	            }
	        },{
	            label : '指标名称',
	            name : 'name',
	            sortable : false,
	            search :  false,
	            width : 130
	        },{
	            label : '下发状态',
	            name : 'toPatchStatus',
	            width : 100,
	            editable: true,
	            search :  false,
	            align : 'left',
	            formatter:function(cellvalue, options, rowObject){
	            	var noPatch='<span style="color:green;">未下发</span>';
	            	var patch='<span style="color:red;">已下发</span>';
	            	return cellvalue=='0'?noPatch:patch;
	            }
	        }],
	        gridComplete: function () {
			     Biz.hideTableElement(AssTargetTocityIndex.$grid,".btn-group-contexts");
			     Biz.hideTableElement(AssTargetTocityIndex.$grid,".ui-icon-arrowstop-1-w");
			     Biz.hideTableElement(AssTargetTocityIndex.$grid,".ui-icon-search");
			     Biz.hideTableElement(AssTargetTocityIndex.$grid,".ui-icon-disk");
			     Biz.hideTableElement(AssTargetTocityIndex.$grid,".ui-icon-battery-2");
			     Biz.hideTableElement(AssTargetTocityIndex.$grid,".ui-state-disabled");
	        }, 
	        filterToolbar : false,
	        operations : function(items) {
	        	
	    		var $grid = AssTargetTocityIndex.$grid;
	    		var $push = $('<li data-position="multi" data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa fa-plus-square"></i> 选择指标库</a></li>');
	    		
	    		$push.children("a").bind(
	                    "click",
	                    function(e) {
	                    	$grid.popupDialog({
	                    		url : WEB_ROOT + '/biz/ass/target/ass-target-tocity!getNewTarget',
	        	                id : 'select_target',
	                    		title : '选择新指标库',
	                    		size:'modal-wide',
	                    		callback : function(data) {
	                    			if (data.type == "success") {
	                    				$grid.refresh();
	                    			}
	                    		}
	                    	});
	                    });
	            items.push($push);
	            
	            //添加删除按钮
	    		var $del = $('<li data-position="multi" data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa fa-trash-o"></i> 删除</a></li>');
	            $del.children("a").bind(
	                    "click",
	                    function(e) {
	                    	var ids=$grid.jqGrid('getGridParam','selarrrow');
	                    	if(!ids || ids.length<1){
	                    		alert("请选择数据");
	                    		return;
	                    	}
	                    	
	                		bootbox.confirm("您确定要删除数据嘛？", function(g) {
	                			if (g) {
	                            	var url = WEB_ROOT + '/biz/ass/target/ass-target-tocity!doDelete?ids='+ids;
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
	                    });
	            items.push($del);
	            
	        }
	    
	    });

	},
	initSearchParam: function(){
		
		var $city = this.$searchForm.find("#id_city");
		this.$searchForm.data("formOptions", {
	        bindEvents : function() {
	   		  if(!Biz.isCurrentAdmin()){
//	   			  	Biz.initSelect('PRV_CITY',$city,Biz.LOGIN_INFO.city);
//	 	            Biz.disableSelect2WhenNotAdmin($city);
	   			  AssTargetTocityIndex.initLocalCity($city);
	          }
	   		  else{
	   			  Biz.initSelectOpts('PRV_CITY',$city,'',{value:"*",text:"广东省"});
	          }
	        }
	    });
		
	},
	doDelete : function(id) {
		var data={};
    	data.id = id;
    	
    	var $form = $(".form-biz-ass-index-store-index");
		var url = WEB_ROOT + '/biz/ass/store/ass-index-store!deleteStore';
		$("body").ajaxJsonUrl(url, function(result) {
			Global.notify("success", "考核指标删除成功");
			
			$form.find('#btn_search').click();
		},data);
	}
	,
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
	AssTargetTocityIndex.init();
});
