<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${base}/assets/plugins/jquery-ztree/jquery.ztree.excheck-3.5.js"></script>
<form id="form-pub-post-depts" class="form-horizontal form-bordered form-label-stripped form-validation"
	action="${base}/common/pubpost/pub-post!savePubPostDept" method="post">
	<s:hidden name="id" />
	<s:hidden name="deptIds" />
	<s:token />
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">部门列表</label>
					<div class="controls">
		                <ul id="deptTree" class="ztree"></ul>                  
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue pubDeptTreeSaveBtn" type="button">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script>
	$(function() {
		var pubid = '<s:property value="#parameters.id"/>';
		var pubDeptAvtiveTab = $("div[class*='tab-closable'][class*='active'][data-url*='/common/pubpost/pub-post!inputTabs?id']");
		var pubDeptMenuTreeObj = $(pubDeptAvtiveTab).find("#deptTree");
		var pubDeptMenuTreeId = "deptTree-"+pubid;
		$(pubDeptMenuTreeObj).attr("id",pubDeptMenuTreeId);
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

		$(pubDeptMenuTreeObj).ajaxJsonUrl("${base}/common/pubpost/pub-post!queryDepts?id=" + pubid, function(data) {
			if (data.type == "success") {
				$.fn.zTree.init(pubDeptMenuTreeObj, setting, data.userdata);
				var zTree = $.fn.zTree.getZTreeObj(pubDeptMenuTreeId);
				$(pubDeptAvtiveTab).find(".pubDeptTreeSaveBtn").unbind("click").bind("click",function(){
					var nodes = zTree.getCheckedNodes(true);
					var deptIds = "";

					if (zTree.getCheckedNodes(false).length == 0) {//全选
						deptIds = "0";
					} else {
						for (var i = 0; i < nodes.length; i++) {
							deptIds += nodes[i].id + ",";
						}

						if (deptIds.length > 0) {
							deptIds = deptIds.substring(0, deptIds.length - 1);
							$(pubDeptAvtiveTab).find("#deptIds").val(deptIds);
							$(pubDeptAvtiveTab).find("#form-pub-post-depts").submit();
						} else {
							Global.notify("error", "请选择发布部门");			
						}
					}
				});
			} else {
				Global.notify("error", data.message);
			}
        });
	});
</script>
<%@ include file="/common/ajax-footer.jsp"%>