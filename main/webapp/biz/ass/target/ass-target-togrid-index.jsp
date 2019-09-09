<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right" style="opacity:0"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-ass-target-togrid"
						data-grid-search=".grid-biz-ass-target-ass-target-togrid">
						 <div class="input-group">
							 <div class="form-group">
								 <s:select id="id_assId" name="assTargetPatchBo.assId" 
								 	cssClass="form-control input-large" cssStyle="width:300px;max-width: 300px;"
 										 list="{}" placeholder="选择考核指标..." />
						     </div>
						     <div class="form-group" style="margin-left: 10px;">
								<s:textfield class="workinput wicon" id="id_cyclenum" readonly="true" 
									name="assTargetPatchBo.cycleNumStr"  placeholder="双击选择考核期" />
						     </div>
						    <div class="form-group" style="margin-left: 10px;">
								<s:textfield id="id_ass_togrid" type="text" value="" size="45" readonly="true" 
									placeholder="双击选择网格" />
								<input id="id_ass_togrid_ids" name="assTargetPatchBo.gridids" type="hidden" />
							</div>
						</div>
						<div class="input-group">
						 	<div class="form-group">
								 <s:select id="id_status" name="assTargetPatchBo.status" 
								 	cssClass="form-control input-large" cssStyle="width:300px;max-width: 300px;"
 										 list="#{'0':'待启用','1':'已启用','2':'已停用'}" placeholder="选择状态..." />
						     </div>
						     <div class="form-group" style="margin-left: 10px;">
								<button class="btn green" id="btn_search" type="submmit">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								<button class="btn default hidden-inline-xs" type="reset" id="btn-reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
						     </div>
						</div>
						<!-- 下拉网格 -->
						<div id="menuContent"
							style="display:none;position: absolute;z-index: 99999999999;">
							<ul id="grid_tree" class="ztree ztree_demo" style="margin-top: 0;"></ul>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-ass-target-ass-target-togrid"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/ass/target/ass-target-togrid-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
