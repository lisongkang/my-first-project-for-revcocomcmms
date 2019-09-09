var CmpTaskDetail = {
	$grid: $(".grid-biz-cmp-task-detail"),
	$div :$(".div-biz-cmp-task-detail"),
	init : function(){
		
		//初始化表格数据
		CmpTaskDetail.$grid.data("gridOptions", {
			url :WEB_ROOT + '/biz/manage/cmp/task/cmp-task!queTaskList',
			datatype:"local",  
			colModel : [
	        	{
				    label : '营销方案名称',
				    name : 'saledpkgname',
				    search :  false,
				    width : 170                         
				},      
				{
				    label : '客户姓名',
				    name : 'custname',
				    search :  false,
				    width : 110                         
				},     
				{
				    label : '联系电话',
				    name : 'tel',
				    search :  false,
				    width : 110                         
				},     
				{
				    label : '地址',
				    name : 'addr',
				    search :  false,
				    width : 320                         
				},     
				{
				    label : '创建时间',
				    name : 'cstime',
				    search :  false,
				    formatter: 'date',
				    width : 120                         
				},  
				{
				    label : '失效时间',
				    name : 'cetime',
				    search :  false,
				    formatter: 'date',
				    width : 120                         
				},  
				{
				    label : '客户营销内容说明',
				    name : 'cdesc',
				    search :  false,
				    width : 230                         
				}
			],
			multiselect: false,
		    height : 580,
			toppager : false,
			gridComplete:function(){
			     //隐藏表格中的元素
			    Biz.hideTableElement(CmpTaskDetail.$grid,".btn-group-contexts");
			    Biz.hideTableElement(CmpTaskDetail.$grid,".ui-icon-arrowstop-1-w");
			    Biz.hideTableElement(CmpTaskDetail.$grid,".ui-icon-search");
			}
		});
		
		this.loadGridData();
	},
	/**
	 * 加载表跟数据
	 */
	loadGridData: function(){
		$form = CmpTaskDetail.$div;
		var queTaskListReq = {};
		queTaskListReq.activityid = $form.find("#activityid").val();
		queTaskListReq.gridCode = $form.find("#gridid").val();
		var queTaskListReqJson = JSON.stringify(queTaskListReq);
		CmpTaskDetail.$grid.jqGrid('setGridParam', 
				                  {datatype:'json',
			                       postData:{'queTaskListReq':queTaskListReqJson}
		                           }).trigger("reloadGrid");
	}

    
};
$(function(){
	CmpTaskDetail.init();
});