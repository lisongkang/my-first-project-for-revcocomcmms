AssTargetTogridReport = ({
	$searchForm:$(".form-grid-biz-ass-target-togrid-report"),
	init : function (){
		this.initSearchParam();//初始化查询Form
		this.initIframeLink();
		this.initDateFormat();
	},
	initSearchParam: function(){
		
		this.$searchForm.find("#btn_search").click(this.linkReport);
		
		this.initGridMenu();//初始化网格
	    
		this.initAssTarget();//初始化指标
		
    	//初始化考核期
		this.$searchForm.find('#id_cyclenum').jeDate({
			skinCell:"jedategrid", 
			isinitVal : false,
			format : 'YYYY-MM'
		});
		
		//重置按钮需要更新树节点的选中状态
		this.$searchForm.find("#btn-reset").bind('click',function(){
			AssTargetTogridReport.checkedClear();
		});
		
	},
	//初始化指标库
	initAssTarget : function() {
		var $this=this;
		var assGridObj = this.$searchForm.find("#id_assId_View");
		assGridObj.bind("click",this.showAssMenu);
		
		
		var $form =this.$searchForm;
		var url = WEB_ROOT + "/biz/ass/target/ass-target-togrid!getCanPatchStores";
    	url = url + "?rows=-1";
    	$form.ajaxJsonUrl(url, function(data) {
    		
    		var $ass=$form.find("#id_assId");
    		$ass.empty();
    		
    		//设置回调函数
    		$this.treeSetting.callback = {
    			beforeClick: AssTargetTogridReport.beforeAssClick,
    			onCheck: AssTargetTogridReport.onAssCheck
    		}
    	
    		$.fn.zTree.init($("#target_view_tree"), $this.treeSetting,data);
    	});
	},
	createBtn:function(label,id,status){
		return '<a href="javascript:AssTargetTogridReport.doChange('+id+','+status+')" style="color:blue;">'+label+'</span>';
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
    			beforeClick: AssTargetTogridReport.beforeClick,
    			onCheck: AssTargetTogridReport.onGridCheck
    		}
    	
    		$.fn.zTree.init($("#ass_report_tree"), $this.treeSetting,response.userdata);
    		
    		var zTree = $.fn.zTree.getZTreeObj("ass_report_tree");
    		var root = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
    		zTree.expandNode(root);//展开根节点

    	});
	},
	showGridMenu:function(){
		
		var $form =AssTargetTogridReport.$searchForm;
		var gridObj = $form.find("#id_ass_togrid");
		var offset = gridObj.offset();
		
		$form.find("#ass_report_tree").css("width",gridObj.css("width"));	
		$form.find("#menuContent").show();//一定要先show出来,再设置offset才能正确显示，否则第一次会有编移
		$form.find("#menuContent").offset({
			left : offset.left,
			top : offset.top + gridObj.outerHeight()
		});
		$("body").bind("mousedown",AssTargetTogridReport.onBodyDown);
		//设置网格tree隐藏，如果同时显示会有界面小异常(点击body不关闭)
		$form.find("#AssMenuContent").hide();
	},
	hideGridMenu:function(){
		var $form =AssTargetTogridReport.$searchForm;
		$form.find("#menuContent").hide();
		//$("body").unbind("mousedown",AssTargetTogridReport.onBodyDown);
	},
	showAssMenu:function(){ //显示指标tree
		
		var $form =AssTargetTogridReport.$searchForm;
		var gridObj = $form.find("#id_assId_View");
		var offset = gridObj.offset();
		
		$form.find("#target_view_tree").css("width",gridObj.css("width"));	
		$form.find("#AssMenuContent").show();//一定要先show出来,再设置offset才能正确显示，否则第一次会有编移
		$form.find("#AssMenuContent").offset({
			left : offset.left,
			top : offset.top + gridObj.outerHeight()
		});
		$("body").bind("mousedown",AssTargetTogridReport.onBodyDown);
		//设置网格tree隐藏，如果同时显示会有界面小异常(点击body不关闭)
		$form.find("#menuContent").hide();
	},
	hideAssMenu:function(){ //隐藏指标tree
		var $form =AssTargetTogridReport.$searchForm;
		$form.find("#AssMenuContent").hide();
		//$("body").unbind("mousedown",AssTargetTogridReport.onBodyDown);
	},
	onBodyDown:function(event){
		console.log("-------->"+event.target.id);
		console.log("-------->"+$(event.target).parents("#AssMenuContent").length);
		
		if (!(event.target.id == "id_ass_togrid"
			|| event.target.id == "menuContent" || $(event.target).parents(
			"#menuContent").length > 0)) {
			AssTargetTogridReport.hideGridMenu();
		}
			
		if (!(event.target.id == "id_assId_View"
				|| event.target.id == "AssMenuContent" || $(event.target).parents(
				"#AssMenuContent").length > 0)) {
			AssTargetTogridReport.hideAssMenu();
		}
	},
	beforeClick:function(treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj("ass_report_tree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	},
	beforeAssClick:function(treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj("target_view_tree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	},
	onGridCheck:function(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("ass_report_tree");
		var nodes = zTree.getCheckedNodes(true);
		var names = "",ids="";
		for (var i=0, l=nodes.length; i<l; i++) {
			if(nodes[i].isParent) continue;
			names += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}
		if (names.length > 0 ) names = names.substring(0, names.length-1);
		if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
		
		var $form =AssTargetTogridReport.$searchForm;
		var grids_name = $form.find("#id_ass_togrid");
		var grids_id = $form.find("#id_ass_togrid_ids");
		
		grids_name.attr("value", names);
		grids_id.attr("value", ids);
	},
	onAssCheck:function(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("target_view_tree");
		var nodes = zTree.getCheckedNodes(true);
		var names = "",ids="";
		for (var i=0, l=nodes.length; i<l; i++) {
			if(nodes[i].isParent) continue;
			names += nodes[i].name + ",";
			ids += nodes[i].id + ",";
		}
		if (names.length > 0 ) names = names.substring(0, names.length-1);
		if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
		
		var $form =AssTargetTogridReport.$searchForm;
		var ass_names = $form.find("#id_assId_View");
		var ass_ids = $form.find("#id_assId");
		
		ass_names.attr("value", names);
		ass_ids.attr("value", ids);
	},
	checkedClear:function(context){
		var zTree = $.fn.zTree.getZTreeObj("ass_report_tree");
		if(zTree){
			//清空控件
			var $form =AssTargetTogridReport.$searchForm;
			$form.find("#id_ass_togrid").attr("value","");
			$form.find("#id_ass_togrid_ids").attr("value","");
			//将zTree所有节点设为不选中
			zTree.checkAllNodes(false);
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
	linkReport:function(){
		var $form =AssTargetTogridReport.$searchForm;
		var $ass=$form.find("#id_assId");
		var assIds=$ass.val();//获取指标
		var pMonth=$form.find('#id_cyclenum').val();
		var gridIds=$form.find('#id_ass_togrid_ids').val();
		
		if(!$.trim(assIds)){
			Global.notify("error", "请选择指标");
			return;
		}
		if(!$.trim(pMonth)){
			Global.notify("error", "请选择一个统计月分");
			return;
		}
		if(!$.trim(gridIds)){
			Global.notify("error", "请选择网格");
			return;
		}
		
		var url=AssTargetTogridReport.getReportUrl("assreport.rpx");//"http://localhost:"+WEB_PORT+"/report/reportJsp/showReport.jsp?rpx=指标/指标.rpx";
		url=url+"&pMonth="+pMonth+"-01";
		url=url+"&gridIds="+gridIds;
		url=url+"&assIds="+assIds;
		url=url+"&assArr="+assIds;
		
		url=url+"&sDate="+(new Date().Format("yyyy-MM-dd"));//统计时间
		//url=url+"&censusDate="+(pMonth.replace("-0","年")+"月");
		url=url+"&censusDate="+pMonth;
		url=url+"&sAuthor="+Biz.LOGIN_INFO.name;//统计部门
		url=url+"&sDept="+Biz.LOGIN_INFO.deptname;//统计部门
		
		var gridLen=gridIds.split(",").length;
		if(gridLen>300){
			Global.notify("error", "网格选择过多，不能超出300个");
			return;
		}
		
		if(!AssTargetTogridReport.checkURL(url)){
			Global.notify("error", "网格选择过多，超出浏览器长度限制");
			return;
		}
		//跳转到报表
		$("iframe[name='re_frmlink']").attr("src",url);
		
		//给iframe添加事件，隐藏弹出的tree View
//		setTimeout(function(){
//			$(window.frames["re_frmlink"].document.body).click(AssTargetTogridReport.onBodyDown);
//		},2000);
	},
	initIframeLink:function(){ //默认跳转到一个空报表
		$("iframe[name='re_frmlink']").attr("src",AssTargetTogridReport.getReportUrl("blank.rpx"));
	},
	iframeLoadCmp:function(){
		$(window.frames["re_frmlink"].document.body).click(AssTargetTogridReport.onBodyDown);
	},
	getReportUrl:function(reportFileName){
		var host=window.location.host;
		return "http://"+host+"/report/reportJsp/showReport.jsp?rpx="+reportFileName;
		//return "http://"+WEB_IP+":"+WEB_PORT+"/report/reportJsp/showReport.jsp?rpx="+reportFileName;
	},
	checkURL:function(url){
	    //当前url的字节长度。
	    var totalLength = 0,
	        charCode = null;
	    for (var i=0, len=url.length; i<len; i++) {
	        //转化为Unicode编码
	        charCode = url.charCodeAt(i);
	        if (charCode < 0x007f) {
	            totalLength++;
	        } else if ((0x0080 <= charCode) && (charCode <= 0x07ff)) {
	            totalLength += 2;
	        } else if ((0x0800 <= charCode) && (charCode <= 0xffff)) {
	            totalLength += 3;
	        }
	    }
	    return (totalLength < 2000) ? true : false;
	},
	initDateFormat:function(){
		//日期格式化
        Date.prototype.Format = function(fmt) {
            var o = {
                "M+" : this.getMonth() + 1,
                "d+" : this.getDate(),
                "h+" : this.getHours(),
                "m+" : this.getMinutes(),
                "s+" : this.getSeconds(),
                "q+" : Math.floor((this.getMonth() + 3) / 3),
                "S" : this.getMilliseconds()
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }
	}
});


$(function() {
	AssTargetTogridReport.init();
});