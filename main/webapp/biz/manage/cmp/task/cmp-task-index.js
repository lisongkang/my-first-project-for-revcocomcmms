var CmpTaskIndex = {
    $form : $(".form-biz-cmp-task-index"),
    $grid : $(".grid-biz-cmp-task-index"),
    cmpTaskGrid :{
    	init : function(){
    		CmpTaskIndex.$grid.data("gridOptions", {
    			url :WEB_ROOT + '/biz/manage/cmp/task/cmp-task!queActivityList',
    			datatype:"local",  
    			colModel : [
    				{
    				    label : '活动ID',
    				    name : 'activityid',
    				    search :  false,
    				    hidden:true                     
    				},
    				{
    				    label : 'gridid',
    				    name : 'gridid',
    				    search :  false,
    				    hidden:true                     
    				},
    				{
    				    label : '活动名称',
    				    name : 'activityname',
    				    search :  false,
    				    width : 250                  
    				},
    				{
    				    label : '活动说明',
    				    name : 'activitydesc',
    				    search :  false,
    				    width : 300                  
    				},
    				{
    				    label : '任务数',
    				    name : 'nums',
    				    search :  false,
    				    width : 80                  
    				},
    				{
    				    label : '所属网格',
    				    name : 'gridname',
    				    search :  false,
    				    width : 80                  
    				},
    				{
    				    label : '创建时间',
    				    name : 'begindate',
    				    search :  false,
    				    formatter: 'date',
    				    width : 130                  
    				}
    				,
    				{
    				    label : '失效时间',
    				    name : 'enddate',
    				    search :  false,
    				    formatter: 'date',
    				    width : 130                  
    				}, {
    					label : '操作',
    					name : 'self_opt',
    					search :  false,
    					width : 90
    				} 
    			],
    			multiselect: false,
    			gridComplete:function(){
				     //隐藏表格中的元素
					 
				    Biz.hideTableElement(CmpTaskIndex.$grid,".btn-group-contexts");
				    Biz.hideTableElement(CmpTaskIndex.$grid,".ui-icon-arrowstop-1-w");
				    Biz.hideTableElement(CmpTaskIndex.$grid,".ui-icon-search");
    				
    				Biz.addCustomBtnToGrid(".grid-biz-cmp-task-index", [ {
    					subBtns : [ {
    						btnName : "详情",
    						btnAction : "CmpTaskIndex.showTaskDetail"
    					}]
    				} ]);
    			}
    		});
    	}
    },
	init : function(){
		//绑定事件
		this.$form.find("#btn_search").click(function(){
			CmpTaskIndex.queActivityList();
		});
		this.initSearchParams();
		this.cmpTaskGrid.init();
	},
	/**
	 * 查看详情
	 */
	showTaskDetail : function(id){
		var grid = CmpTaskIndex.$grid;
		var rowData = grid.jqGrid("getRowData", id);
		
		var activityid = rowData.activityid;
		
		var gridcode = rowData.gridid;
		$(this).popupDialog({
            url :  WEB_ROOT + '/biz/manage/cmp/task/cmp-task!detail?activityid='+activityid+"&gridcode="+gridcode,
            title : "活动详情",
            id : "showTaskDetail",
            callback : function(rowdata) {
            }
        });
	},
	/**
	 * 
	 */
	checkRequired : function(){
		//检查网格
		/*var $form = CmpTaskIndex.$form;
		var gridcode  =  $form.find("#id_gridcode").val();
		if(Biz.checkNull(gridcode)){
			Global.notify("error", "网格不能为空，请选择网格!");
			return false;
		}*/
		return true;
	},
	/**
	 * 查询活动用户信息
	 */
	queActivityList: function(){
		var $form = CmpTaskIndex.$form;
		if(!CmpTaskIndex.checkRequired()){
			return ;
		}
		var queActivityListReq = {};
		queActivityListReq.city = $form.find("#id_city").val();
		queActivityListReq.gridCode = $form.find("#id_gridcode").val();
		var queActivityListReqJson = JSON.stringify(queActivityListReq);
		CmpTaskIndex.$grid.jqGrid('setGridParam', 
				                  {datatype:'json',
			                       postData:{'queActivityListReq':queActivityListReqJson}
		                           }).trigger("reloadGrid");
	},
	
	/**
	 *初始化 查询条件
	 */
	initSearchParams:function(){
		var $form = this.$form;
		var $city = $form.find("#id_city");
       
		//初始化地市
		$form.data("formOptions", {
	        bindEvents : function() {
	           
	            Biz.initSelect('PRV_CITY',$city,Biz.LOGIN_INFO.city);
	 	        Biz.disableSelect2($city);
	            $city.change(function() {
	            	CmpTaskIndex.initGridCode();
	            });
	           
	        }
	    });
      
        CmpTaskIndex.initGridCode();
	},
	initGridCode : function(){
		var $form = CmpTaskIndex.$form;
		var $grid = $form.find("#id_gridcode");
		
	    var gcode = "PRV_GRID_BY_CITY";
	    var mcode =    $form.find("#id_city").val();
	    if(Biz.checkNull(mcode)){
	    	mcode = Biz.LOGIN_INFO.city;
	    }
	    var url = WEB_ROOT + "/prv-sysparam!selectParamList";
        url = url + "?rows=-1&gcode=" + gcode + "&mcode=" + mcode;
        $form.ajaxJsonUrl(url, function(data) {
    		$grid.empty();
    		$grid.select2({placeholder: "请选择网格"});
    		$grid.append('<option value=""></option>');
    		var option = '';
    		$.each(data, function(i, item) {
                option = '<option value="'+item.data+'">'+item.mname+'</option>';
                $grid.append(option);
            });
        });
	}
};
$(function(){
	CmpTaskIndex.init();
});