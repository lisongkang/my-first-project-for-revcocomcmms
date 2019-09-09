AssTargetTogridInpubBasic=({
	$form: $(".form-biz-ass-target-togrid-inputBasic"),
	init : function(){
		
		var rowData = this.getSelGridRow();
		if (rowData){
			//初始化值
			this.$form.find('#id_assName').val(rowData["assName"]);
			var cycleNum=rowData["cycleNum"].replace('年','-').replace('月','');
			this.$form.find('#id_cyclenum').val(cycleNum);
			this.$form.find('#id_assNum').val(rowData["assNum"]);
		 }
		
    	//初始化考核期
    	this.$form.find('#id_cyclenum').jeDate({
    		skinCell:"jedategrid", 
			isinitVal : false,
			theme:{bgcolor:"#4d90fe",pnColor:"#4d90fe"},
			zIndex : 999999999999,
			format : 'YYYY-MM'// 分隔符可以任意定义，该例子表示只显示年月
		});
    	
    	this.initTree();
    	
    	//按钮绑定事件
		this.$form.find("button[name='btn_confirm']").click(this.saveListener);
	},
	checkNull : function(objvalue){
		if(objvalue == undefined || objvalue == "" || objvalue == null || !objvalue){
			return true;
		}
		return false;
	},
	checkRequiredInput : function () {
		var text = "";
		if(AssTargetTogridInpubBasic.checkNull(this.$form.find('#id_cyclenum').val()))
			text += "考核周期、";
		if(AssTargetTogridInpubBasic.checkNull(this.$form.find('#view_checked_grid_ids').val()))
			text += "下发网格、";
		if(AssTargetTogridInpubBasic.checkNull(this.$form.find('#id_assNum').val()))
			text += "目标值、";
		
		if("" != text){
			alert(text.substr(0,text.length-1) + " 为必填项!");
			return false;
		}
		
		var ids=this.$form.find('#view_checked_grid_ids').val();
		var idArr=ids.split(",");
		if(idArr.length>1){
			alert("下发网格只能选择一个");
			return false;
		}
		
    	return true;
	
	},
	getSelGridRow:function(){
		 var id =  this.$form.find('#id').val();
		 if (id != ""){
			var jgrid =AssTargetTogridIndex.$grid;
			return jgrid.getRowData(id);
		 }
		 return null;
	},
	saveListener:function(){
		
		if(AssTargetTogridInpubBasic.checkRequiredInput()){
			
			var $form=AssTargetTogridInpubBasic.$form;
			var storeData={};
			var url = WEB_ROOT + "/biz/ass/target/ass-target-togrid!doSave";
	    	
	    	if($form.find('#id_cyclenum')){
	    		storeData.cyclenumStr = $form.find('#id_cyclenum').val()+'-01';
	    	}
	    	storeData.id=$form.find('#id').val();;
	    	storeData.gridId = $form.find('#view_checked_grid_ids').val();
	    	storeData.assNum = $form.find('#id_assNum').val();
	    	storeData.cycleNumStr =$form.find('#id_cyclenum').val()+'-01';;
	    	storeData.data = JSON.stringify(storeData);
	    	
			$("body").ajaxJsonUrl(url, function(response) {
				
				if (response.type == "success") {
					Global.notify("success",response.message);
					AssTargetTogridIndex.$grid.refresh();
					AssTargetTogridInpubBasic.closeTab();
				} else {
					Global.notify("error", response.message);
				}
				
			}, storeData);
		}
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
	initTree:function(){
		
		var $this=this;
		var gridObj = this.$form.find("#view_checked_grid");
		gridObj.bind("click",$this.showGridMenu);
		
		var url = WEB_ROOT + "/market/grid-info!gridTree";
    	$("body").ajaxJsonUrl(url, function(response) {
    		
    		//设置回调函数
    		$this.treeSetting.callback = {
    			beforeClick: AssTargetTogridInpubBasic.beforeClick,
    			onCheck: AssTargetTogridInpubBasic.onCheck
    		}
    	
    		$.fn.zTree.init($("#to_grid_inputBasic_tree"), $this.treeSetting,response.userdata);
    		
    		//展开根节点
    		var zTree=$this.getzTree();
    		var root=$this.getzTreeRoot();
    		if(zTree && root) zTree.expandNode(root);
    		
    		//选中节点
    		var rowData = $this.getSelGridRow();
    		if(zTree && rowData){
    			var nodes=zTree.getNodeByParam("id",rowData["gridId"]);
    			if(nodes){
    				zTree.checkNode(nodes, true, true, true);
    			}
    		}
    		
    	});
	},
	showGridMenu:function(){
		
		var $form =AssTargetTogridInpubBasic.$form;
		var gridObj = $form.find("#view_checked_grid");
		var offset = gridObj.offset();
		
		$form.find("#to_grid_inputBasic_tree").css("width",gridObj.css("width"));	
		$form.find("#to_grid_inputBasic_Div").show();//一定要先show出来,再设置offset才能正确显示，否则第一次会有编移
		$form.find("#to_grid_inputBasic_Div").offset({
			left : offset.left,
			top : offset.top + gridObj.outerHeight()
		});
		$("body").bind("mousedown",AssTargetTogridInpubBasic.onBodyDown);
	},
	hideGridMenu:function(){
		var $form =AssTargetTogridInpubBasic.$form;
		$form.find("#to_grid_inputBasic_Div").hide();
		$("body").unbind("mousedown",AssTargetTogridInpubBasic.onBodyDown);
	},
	onBodyDown:function(event){
		
		if (!(event.target.id == "view_checked_grid"
				|| event.target.id == "to_grid_inputBasic_Div" || $(event.target).parents(
				"#to_grid_inputBasic_Div").length > 0)) {
			AssTargetTogridInpubBasic.hideGridMenu();
		}
	},
	beforeClick:function(treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj("to_grid_inputBasic_tree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	},
	onCheck:function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("to_grid_inputBasic_tree");
		var nodes = zTree.getCheckedNodes(true);
		var names = "",ids="";
		for (var i=0, l=nodes.length; i<l; i++) {
			if(nodes[i].isParent) continue;
			names += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}
		if (names.length > 0 ) names = names.substring(0, names.length-1);
		if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
		
		var $form =AssTargetTogridInpubBasic.$form;
		var grids_name = $form.find("#view_checked_grid");
		var grids_id = $form.find("#view_checked_grid_ids");
		
		grids_name.attr("value", names);
		grids_id.attr("value", ids);
	},
	checkedClear:function(context){
		var zTree = $.fn.zTree.getZTreeObj("to_grid_inputBasic_tree");
		if(zTree){
			var root = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
			zTree.checkNode(root, false, true, true);
		}
	},
	getzTreeRoot:function(){
		var zTree = this.getzTree();
		if(zTree){
			var root = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
			return root;
		}
		return null;
	},
	getzTree:function(){
		var zTree = $.fn.zTree.getZTreeObj("to_grid_inputBasic_tree");
		return zTree;
	},
	closeTab : function() {
		$form=AssTargetTogridInpubBasic.$form;
		$form.attr("form-data-modified","false");
		$form.find("#btn_cancel").click();
    }
});

$(function() {
	AssTargetTogridInpubBasic.init();
});