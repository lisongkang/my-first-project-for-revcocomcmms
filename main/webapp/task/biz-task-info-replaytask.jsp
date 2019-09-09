<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
#biz-task-info-tree{
	min-height: 350px;
	max-height: 350px;
	overflow-y: auto;
	overflow-x: auto;
	border: 1px solid #ddd;
	width:40%
}
</style>
<div class="grid_biz_task_info_replaytask">
	 <div class="controls">
	           已选择任务
		<table width="99%" class="c-table">
			<thead><tr>
				<th width="20%">任务编码</th>
				<th width="50%" >任务名称</th>
				<th width="30%" >网格</th>
			</tr></thead>
			<tbody>
			<s:iterator value="model" status="status" var="bizTaskInfoPO">
			   <tr>
				   <td><s:property value="#bizTaskInfoPO.taskid" /></td>
				   <td><s:property value="#bizTaskInfoPO.tasktitle"/></td>
				   <td><s:property value="#bizTaskInfoPO.gridname"/></td>
			   </tr>
			</s:iterator>
			</tbody>
		</table>
	 </div>
	<div>
		 <div class="controls" id="biz-task-info-tree">
		     选择网格
			 <s:select id="treeType" name="treeType" list="gridTypeMap" value="0" style="margin-top:20px"/>
				<div id="">
					<ul id="BizTaskInfoReplayTree" class="ztree"></ul>
				</div>
		</div>
	</div>
	
	<div class="form-actions right">
		<button class="btn blue btn_replaytask" type="button">
			<i class="fa fa-check"></i> 确定
		</button>
		<button class="btn default btn-cancel1" type="button">取消</button>
	</div>
</div>
<script type="text/javascript">
    var taskids =  '<s:property value="#request.taskid" />'; //字符串要加引号
</script>
<script src="${base}/task/biz-task-info-replaytask.js" />
<%@ include file="/common/ajax-footer.jsp"%>