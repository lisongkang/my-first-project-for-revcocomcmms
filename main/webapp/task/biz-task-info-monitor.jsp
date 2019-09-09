<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div_biz_task_info_monitor">
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-search-init form-validation form_biz_task_info_monitor"
						data-grid-search=".grid_biz_task_info_monitor">
						<div class="input-group">
                             <div class="form-group">
							      <div class="input-cont">
								    <input type="text" name="taskinfo.taskid" id="id_taskid" cssclass="form-control" class="form-control" placeholder="任务编码">
							     </div>
						     </div>
						     <div class="form-group">
							     <s:select id="id_type" name="taskinfo.type" cssStyle="width:150px"
									 list="{}"
									placeholder="任务类型" />
						     </div>	
						     <div class="form-group">
							      <div class="input-cont">
							      <s:select id="id_status" name="taskinfo.status" cssStyle="width:150px"
									 list="{}"
									placeholder="任务状态" />
							     </div>
						     </div>		
						        <div class="form-group">
							      <div class="input-cont">
								    <input type="text" name="taskinfo.starttimerang" id="id_starttimerang" cssclass="form-control" placeholder="创建时间"   class="form-control input-daterangepicker" style="width:200px">
							    </div>
						     </div>								     
							<span class="input-group-btn">
								<button class="btn green" type="submit" id="btn_search">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid_biz_task_info_monitor"></table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script src="${base}/task/biz-task-info-monitor.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>