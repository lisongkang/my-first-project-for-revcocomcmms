AssTargetTogridIndex = ({
	$searchForm:$(".form-biz-ass-target-togrid"),
	$grid:$(".grid-biz-ass-target-ass-target-togrid"),
	init : function (){
		
		this.initSearchParam();//初始化查询Form
		
		this.$grid.data("gridOptions", {
	        url : WEB_ROOT + '/biz/ass/target/ass-target-togrid!findByPage',
	        rowNum:100,
	        rowList : [ 100, 200, 300, 400, 500 ,600 ,800,1000 ],
	        colModel : [{
	            label : 'id',
	            name : 'id',
	            hidden:true,
	            width : 100,  
	            editable: false,
	            search :  false,
	            align : 'left',
	            key:true
	        },{
	            label : '地市',
	            name : 'cityName',
	            width : 50,   
	            editable: true,
	            search :  false,
	            align : 'left',
	            formatter:function(cellvalue, options, rowObject){
	            	return cellvalue=='*'?'广东省':cellvalue;
	            }
	        },
	        {
	            label : '考核期',
	            name : 'cycleNum',
	            sortable : false,
	            search :  false,
	            width : 60,
	            formatter:function(cellvalue, options, rowObject){
	            	if(cellvalue && cellvalue.length>=7)
	            	return cellvalue.substr(0,7).replace("-","年")+"月";
	            }
	        },{
	            label : 'gridId',
	            name : 'gridId',
	            hidden:true
	        },{
	            label : '网格名称',
	            name : 'gridName',
	            sortable : false,
	            search :  false,
	            width : 100
	        },{
	            label : '指标名称',
	            name : 'assName',
	            sortable : false,
	            search :  false,
	            width : 120
	        },{
	            label : '目标值',
	            name : 'assNum',
	            width : 70, 
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'left',
	            resizable:true
	        },{
	            label : '当前值',
	            name : 'currentValue',
	            width : 70, 
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'left',
	            resizable:true
	        },{
	            label : '状态',
	            name : 'status',
	            width : 70, 
	            sortable : false,
	            search :  false,
	            align : 'left',
	            resizable:true,
	            formatter:function(cellvalue, options, rowObject){
	            	if(cellvalue=='0') return '<span style="color:green;">待启用</span>';
	            	else if(cellvalue=='1') return '<span style="color:red;">已启用</span>';
	            	else if(cellvalue=='2') return '<span style="color:grey;"><del>已停用</del></span>';
	            	else return "其他";
	            }
	        },{
	            label : '操作',
	            width : 60, 
	            sortable : false,
	            search :  false,
	            align : 'left',
	            resizable:true,
	            formatter:function(cellvalue, options, rowData){
	            	var $this=AssTargetTogridIndex;
	            	var id=rowData.id;
	            	var status=rowData.status;
	            	if(status=='0' || status=='2'){
	            		return $this.createBtn('启用',id,1);
	            		//return $this.createBtn('启用',id,1)+' | '+$this.createEditBtn('修改',id);
	            	}
	            	else if(status=='1'){
	            		return  $this.createBtn('停用',id,2);
	            	}
	            }
	        },{
	            label : '',
	            name : 'statusVal',
	            hidden: true,
	            align : 'left',
	            formatter:function(cellvalue, options, rowData){
	            	return rowData.status;
	            }
	        }],
	        fullediturl : WEB_ROOT + '/biz/ass/target/ass-target-togrid!edit',
	        gridComplete: function () {
			     Biz.hideTableElement(AssTargetTogridIndex.$grid,".btn-group-contexts");
			     Biz.hideTableElement(AssTargetTogridIndex.$grid,".ui-icon-arrowstop-1-w");
			     Biz.hideTableElement(AssTargetTogridIndex.$grid,".ui-icon-search");
			     Biz.hideTableElement(AssTargetTogridIndex.$grid,".ui-icon-disk");
			     Biz.hideTableElement(AssTargetTogridIndex.$grid,".ui-icon-battery-2");
			     Biz.hideTableElement(AssTargetTogridIndex.$grid,"button");//隐藏新增按钮
	        }, 
	        filterToolbar : false,
	        operations : function(items) {
	        	var $this = AssTargetTogridIndex;
	    		var $grid = AssTargetTogridIndex.$grid;
	            
	            var toPatchBtn = $this.createCustomBtn(true, "下发指标", "fa-plus-square",$this.toPatchPage);
	    		items.push(toPatchBtn);
	    		
	    		var startBtn = $this.createCustomBtn(true, "批量下发指标", "fa-arrow-up",$this.importExcel);
	    		items.push(startBtn);
	            
	    		var startBtn = $this.createCustomBtn(true, "批量启用", "fa-arrow-circle-right",$this.batchChange);
	    		items.push(startBtn);
	            
	    		//ywp 批量停用
	    		var stopBtn = $this.createCustomBtn(true, "批量停用", "fa-arrow-circle-left",$this.batchStop);
	    		items.push(stopBtn);
	    		
	    		var delBtn = $this.createCustomBtn(true, "删除", "fa-trash-o",$this.del);
	    		items.push(delBtn);
	            
	    		
//				var href = WEB_ROOT + "/biz/ass/target/ass-target-togrid-query!downTemplate";
//	    		var startBtn = $this.createCustomBtn(true, "下载模板", "fa-arrow-down",$this.downTemplate);
//	    		items.push(startBtn);
	            
	        }
	    });

	},
	initSearchParam: function(){
		
		this.initGridMenu();
	    
		this.initAssTarget();
		
    	//初始化考核期
		this.$searchForm.find('#id_cyclenum').jeDate({
			skinCell:"jedategrid", 
			isinitVal : false,
			format : 'YYYY-MM'
		});
		
		//重置按钮需要更新树节点的选中状态
		this.$searchForm.find("#btn-reset").bind('click',function(){
			AssTargetTogridIndex.checkedClear();
		});
	},
	//初始化指标库
	initAssTarget : function() {
		var $form =this.$searchForm;
		var url = WEB_ROOT + "/biz/ass/target/ass-target-togrid!getGridStores";
    	url = url + "?rows=-1";
    	$form.ajaxJsonUrl(url, function(data) {
    		$ass=$form.find("#id_assId");
    		$ass.select2({placeholder: "请选择指标"});
    		var option = '';
    		$.each(data, function(i, item) {
                option = '<option value="'+item.id+'">'+item.name+'</option>';
                $ass.append(option);
            })
    	});
	},
	toPatchPage:function(){
		var $grid = AssTargetTogridIndex.$grid;
		$grid.popupDialog({
    		url : WEB_ROOT + '/biz/ass/target/ass-target-togrid!toPatchPage',
            id : 'toPatch_page',
    		title : '考核指标下发',
    		size:'modal-wide',
    		callback : function(data) {
    			if (data.type == "success") {
    				$grid.refresh();
    			}
    		}
    	});
	},
	createBtn:function(label,id,status){
		return '<a href="javascript:AssTargetTogridIndex.doChange('+id+','+status+')" style="color:blue;">'+label+'</span>';
	},
	createEditBtn:function(label,id){
		return '<a href="javascript:AssTargetTogridIndex.toEdit('+id+')" style="color:blue;">'+label+'</span>';
	},
	batchChange:function(){ //批量启用
		var idArr=AssTargetTogridIndex.$grid.jqGrid('getGridParam','selarrrow');
		if(idArr.length==0){
			alert("请选择数据!");
			return;
		}
		AssTargetTogridIndex.doChange(idArr.join(","),1);
	},
	batchStop:function(){ //ywp批量停用
		var idArr=AssTargetTogridIndex.$grid.jqGrid('getGridParam','selarrrow');
		if(idArr.length==0){
			alert("请选择数据!");
			return;
		}
		AssTargetTogridIndex.doChange(idArr.join(","),2);
	},
	doChange:function(id,status){//启用，停用
		
		if(!id || !status) {
			alert("参数错误");
			return;
		}
		var url = WEB_ROOT + '/biz/ass/target/ass-target-togrid!changeSt?rows=-1&rowid='+id+'&status='+status;
		$("body").ajaxJsonUrl(url, function(response) {
			if (response.type == "success") {
				
				Global.notify("success",response.message);
				$(".form-biz-ass-target-togrid").find("#btn_search").click();
			} else {
				Global.notify("error", response.message);
			}
    	});
	},
	toEdit:function(id){
		var $grid=AssTargetTogridIndex.$grid;
		var row=$grid.find("tr[id="+id+"]");
		if(row){
			$grid.jqGrid('resetSelection');//取消所有选中的行
			$grid.jqGrid('setSelection',id);//选中当前行
			row.dblclick();//触发双击事件
		}
	},
	del:function(){
		var $grid = AssTargetTogridIndex.$grid;
		var ids=$grid.jqGrid('getGridParam','selarrrow');
    	for(var i=0;i<ids.length;i++){
    		var rowData = $grid.jqGrid('getRowData',ids[i]);
    		var st=rowData.statusVal;
    		if(st!=0 && st!=2){
    			alert("只有停用和待启用的数据可以删除");
    			return;
    		}
    	}
    	
    	var url = WEB_ROOT + '/biz/ass/target/ass-target-togrid!doDelete?ids='+ids;
		$("body").ajaxJsonUrl(url, function(response) {
			if (response.type == "success") {
				Global.notify("success",response.message);
				$grid.refresh();
			} else {
				Global.notify("error", response.message);
			}
    	});
	},
	treeSetting:{
		key : {
			name : "name"
		},
		data : {
			simpleData : {
				enable : true,
				rootPId : -1,
				idKey : "id",
				pIdKey : "previd"
			}
		},
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		}
	},
	initGridMenu:function(){
		
		var $this=this;
		var gridObj = this.$searchForm.find("#id_ass_togrid");
		gridObj.bind("click",$this.showGridMenu);
		
		var url = WEB_ROOT + "/market/grid-info!gridTree";
    	$("body").ajaxJsonUrl(url, function(response) {
    		
    		//设置回调函数
    		$this.treeSetting.callback = {
    			beforeClick: AssTargetTogridIndex.beforeClick,
    			onCheck: AssTargetTogridIndex.onCheck
    		}
    	
    		$.fn.zTree.init($("#grid_tree"), $this.treeSetting,response.userdata);
    		
    		var zTree = $.fn.zTree.getZTreeObj("grid_tree");
    		var root = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
    		zTree.expandNode(root);//展开根节点

    	});
	},
	showGridMenu:function(){
		
		var $form =AssTargetTogridIndex.$searchForm;
		var gridObj = $form.find("#id_ass_togrid");
		var offset = gridObj.offset();
		
		$form.find("#grid_tree").css("width",gridObj.css("width"));	
		$form.find("#menuContent").show();//一定要先show出来,再设置offset才能正确显示，否则第一次会有编移
		$form.find("#menuContent").offset({
			left : offset.left,
			top : offset.top + gridObj.outerHeight()
		});
		$("body").bind("mousedown",AssTargetTogridIndex.onBodyDown);
	},
	hideGridMenu:function(){
		var $form =AssTargetTogridIndex.$searchForm;
		$form.find("#menuContent").hide();
		$("body").unbind("mousedown",AssTargetTogridIndex.onBodyDown);
	},
	onBodyDown:function(event){
		
		if (!(event.target.id == "id_ass_togrid"
				|| event.target.id == "menuContent" || $(event.target).parents(
				"#menuContent").length > 0)) {
			AssTargetTogridIndex.hideGridMenu();
		}
	},
	beforeClick:function(treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj("grid_tree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	},
	onCheck:function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("grid_tree");
		var nodes = zTree.getCheckedNodes(true);
		var names = "",ids="";
		for (var i=0, l=nodes.length; i<l; i++) {
			if(nodes[i].isParent) continue;
			names += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}
		if (names.length > 0 ) names = names.substring(0, names.length-1);
		if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
		
		var $form =AssTargetTogridIndex.$searchForm;
		var grids_name = $form.find("#id_ass_togrid");
		var grids_id = $form.find("#id_ass_togrid_ids");
		
		grids_name.attr("value", names);
		grids_id.attr("value", ids);
	},
	checkedClear:function(context){
		var zTree = $.fn.zTree.getZTreeObj("grid_tree");
		if(zTree){
			//清空控件
			var $form =AssTargetTogridIndex.$searchForm;
			$form.find("#id_ass_togrid").attr("value","");
			$form.find("#id_ass_togrid_ids").attr("value","");
			//将zTree所有节点设为不选中
			zTree.checkAllNodes(false);
			//非管理员只要将root设为不选中即可，管理员不行
//			var root = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
//			zTree.checkNode(root, false, true, true);
		}
	},
	createCustomBtn : function(isPosition, btnTitle, btnClass, btnAction) {
		var newBtn = $('<li '
				+ (isPosition ? 'data-position="multi"' : '')
				+ 'data-toolbar="show"><a modal-size="modal-wide" href="javascript:;"><i class="fa '
				+ btnClass + '"></i> ' + btnTitle + '</a></li>');

		newBtn.children("a").bind("click", btnAction);
		return newBtn;
	},
	importExcel:function(){
		var $grid=AssTargetTogridIndex.$grid;
		$grid.popupDialog({
    		url : WEB_ROOT + '/biz/ass/target/ass-target-togrid-query!toImportPage',
            id : 'to_import_page',
    		title : '导入指标数据',
    		size:'auto',
    		callback : function(data) {
    			
    		}
    	});
	}
//	downTemplate:function(){
//		var url = WEB_ROOT + "/biz/ass/target/ass-target-togrid-query!downTemplate";
//    	url = url + "?rows=-1";
//    	window.location.href=url;
//    	
//	}
});


$(function() {
	AssTargetTogridIndex.init();
});