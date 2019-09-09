AssTargetTogridQuery = ({
	$searchForm:$(".form-grid-biz-ass-target-togrid-query"),
	$grid:$(".grid-biz-ass-target-togrid-query"),
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
	            label : '当前值',
	            name : 'currentValue',
	            width : 70, 
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'left',
	            resizable:true
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
	            label : '最终评分',
	            name : 'finalGrade',
	            width : 70, 
	            editable: true,
	            sortable : false,
	            search :  false,
	            align : 'left',
	            resizable:true
	        }],
	        gridComplete: function () {
			     Biz.hideTableElement(AssTargetTogridQuery.$grid,".btn-group-contexts");
			     Biz.hideTableElement(AssTargetTogridQuery.$grid,".ui-icon-arrowstop-1-w");
			     Biz.hideTableElement(AssTargetTogridQuery.$grid,".ui-icon-search");
			     Biz.hideTableElement(AssTargetTogridQuery.$grid,".ui-icon-disk");
			     Biz.hideTableElement(AssTargetTogridQuery.$grid,".ui-icon-battery-2");
			     Biz.hideTableElement(AssTargetTogridQuery.$grid,"button");//隐藏新增按钮
	        }, 
	        filterToolbar : false,
	        operations : function(items) {
	        	
//	    		var $this = AssTargetTogridQuery;
//	    		var startBtn = $this.createCustomBtn(true, "导入指标数据", "fa-arrow-up",$this.importExcel);
//	    		items.push(startBtn);
//	            
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
			AssTargetTogridQuery.checkedClear();
		});
		
	},
	//初始化指标库
	initAssTarget : function() {
		var $form =this.$searchForm;
		var url = WEB_ROOT + "/biz/ass/target/ass-target-togrid!getGridStores";
    	url = url + "?rows=-1";
    	$form.ajaxJsonUrl(url, function(data) {
    		var $ass=$form.find("#id_assId");
    		$ass.select2({placeholder: "请选择指标"});
    		var option = '';
    		$.each(data, function(i, item) {
                option = '<option value="'+item.id+'">'+item.name+'</option>';
                $ass.append(option);
            })
    	});
	},
	createBtn:function(label,id,status){
		return '<a href="javascript:AssTargetTogridQuery.doChange('+id+','+status+')" style="color:blue;">'+label+'</span>';
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
    			beforeClick: AssTargetTogridQuery.beforeClick,
    			onCheck: AssTargetTogridQuery.onCheck
    		}
    	
    		$.fn.zTree.init($("#grid_query_tree"), $this.treeSetting,response.userdata);
    		
    		var zTree = $.fn.zTree.getZTreeObj("grid_query_tree");
    		var root = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
    		zTree.expandNode(root);//展开根节点

    	});
	},
	importExcel:function(){
		var $grid=AssTargetTogridQuery.$grid;
		$grid.popupDialog({
    		url : WEB_ROOT + '/biz/ass/target/ass-target-togrid-query!toImportPage',
            id : 'to_import_page',
    		title : '导入指标数据',
    		size:'auto',
    		callback : function(data) {
    			if (data.type == "success") {
    				$grid.refresh();
    			}
    		}
    	});
	},
	downTemplate:function(){
		var url = WEB_ROOT + "/biz/ass/target/ass-target-togrid-query!downTemplate";
    	url = url + "?rows=-1";
    	window.location.href=url;
    	
	},
	showGridMenu:function(){
		
		var $form =AssTargetTogridQuery.$searchForm;
		var gridObj = $form.find("#id_ass_togrid");
		var offset = gridObj.offset();
		
		$form.find("#grid_query_tree").css("width",gridObj.css("width"));	
		$form.find("#menuContent").show();//一定要先show出来,再设置offset才能正确显示，否则第一次会有编移
		$form.find("#menuContent").offset({
			left : offset.left,
			top : offset.top + gridObj.outerHeight()
		});
		$("body").bind("mousedown",AssTargetTogridQuery.onBodyDown);
	},
	hideGridMenu:function(){
		var $form =AssTargetTogridQuery.$searchForm;
		$form.find("#menuContent").hide();
		$("body").unbind("mousedown",AssTargetTogridQuery.onBodyDown);
	},
	onBodyDown:function(event){
		
		if (!(event.target.id == "id_ass_togrid"
				|| event.target.id == "menuContent" || $(event.target).parents(
				"#menuContent").length > 0)) {
			AssTargetTogridQuery.hideGridMenu();
		}
	},
	beforeClick:function(treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj("grid_query_tree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	},
	onCheck:function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("grid_query_tree");
		var nodes = zTree.getCheckedNodes(true);
		var names = "",ids="";
		for (var i=0, l=nodes.length; i<l; i++) {
			if(nodes[i].isParent) continue;
			names += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}
		if (names.length > 0 ) names = names.substring(0, names.length-1);
		if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
		
		var $form =AssTargetTogridQuery.$searchForm;
		var grids_name = $form.find("#id_ass_togrid");
		var grids_id = $form.find("#id_ass_togrid_ids");
		
		grids_name.attr("value", names);
		grids_id.attr("value", ids);
	},
	checkedClear:function(context){
		var zTree = $.fn.zTree.getZTreeObj("grid_query_tree");
		if(zTree){
			//清空控件
			var $form =AssTargetTogridQuery.$searchForm;
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
	}
});


$(function() {
	AssTargetTogridQuery.init();
});