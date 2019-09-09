<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.excheck-3.5.js"></script>
<form id="form-role-privs" class="form-horizontal form-bordered form-label-stripped form-validation"
	action="${base}/prv/prv-roleinfo!saveRolePrivs" method="post">
	<s:hidden name="id" />
	<s:hidden name="menuIds" />
	<s:token />
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">菜单列表</label>
					<div class="controls">
		                <ul id="userMenuTree" class="ztree"></ul>                  
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue menuTreeSaveBtn" type="button">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script>
	$(function() {
		var roleid = <s:property value="#parameters.id"/>;
		var avtiveTab = $("div[class*='tab-closable'][class*='active'][data-url*='/prv/prv-roleinfo!inputTabs?id']");
		var menuTreeObj = $(avtiveTab).find("#userMenuTree");
		var newMenuTreeId = "userMenuTree-"+roleid;
		$(menuTreeObj).attr("id",newMenuTreeId);
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey : "id",
					pIdKey : "pid"
				}
			}
		};

		$(menuTreeObj).ajaxJsonUrl("${base}/prv/prv-roleinfo!menus?id=" + roleid, function(data) {
			if (data.type == "success") {
				//因为getZTreeObj只能获取到最新生成的树对象，必须生成树后直接获取树对象，再绑定按钮事件
				$.fn.zTree.init(menuTreeObj, setting, data.userdata);
				var zTree = $.fn.zTree.getZTreeObj(newMenuTreeId);
				$(avtiveTab).find(".menuTreeSaveBtn").bind("click",function(){
					var nodes = zTree.getCheckedNodes(true);
					var menuIds = "";
					
					for (var i = 0; i < nodes.length; i++) {
						menuIds += nodes[i].id + ",";
					}
					
					if (menuIds.length > 0) {
						menuIds = menuIds.substring(0, menuIds.length - 1);
						$(avtiveTab).find("#menuIds").val(menuIds);
						$(avtiveTab).find("#form-role-privs").submit();
					} else {
						Global.notify("error", "请选择给角色赋权");			
					}
				});
			} else {
				Global.notify("error", data.message);
			}
        });
	});
</script>
<%@ include file="/common/ajax-footer.jsp"%>