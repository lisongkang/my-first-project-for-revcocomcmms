<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-target-togrid-toPatch"
	action="${base}/biz/ass/target/ass-target-togrid!targetToPatch" method="post" 
	data-editrulesurl="${base}/biz/ass/store/ass-index-store!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	
	<s:token />

	<div class="form-body">
		<div class="row">
            <div class="col-md-6">
				<div class="form-group" style="margin-left: -50px;">
					<label class="control-label">考核指标<span class="required">*</span></label>
					<div class="controls">
		                <s:select id="id_assId" name="assTargetPatchBo.assId" cssStyle="width:240px"
									 list="{}"/>
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核期<span class="required">*</span></label>
					<div class="controls">
<!-- 		                <input class="workinput wicon" id="id_cyclenum" name="assTargetPatchBo.cycleNumStr"> -->
		                <s:textfield name="assTargetPatchBo.cycleNumStr"  id="id_cyclenum" cssStyle="width:160px;"/>
					</div>
				</div>
            </div>
        </div>
	   <div class="row">
            <div class="col-md-6">
				<div class="form-group"  style="margin-left: -50px;">
					<label class="control-label">下发网格<span class="required">*</span></label>
					<div class="controls">
						<button class="btn default hidden-inline-xs" type="button"
							id="btn_net_grid" style="margin-bottom: 10px;">
							<i class="fa fa-undo"></i>选择网格
						</button>
						<s:textfield name="name" id="view_checked_grid"
							cssStyle="width:420px;" disabled="true"/>
						<input id="view_checked_grid_ids" name="assTargetPatchBo.gridids" hidden="true"/>
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">目标值<span class="required">*</span></label>
					<div class="controls">
		               <s:textfield name="assTargetPatchBo.assNum"  id="id_assNum" cssStyle="width:160px;"/>
					</div>
				</div>
            </div>
        </div>

	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" id="btn_toPatch" name="btn_toPatch" data-grid-reload=".grid-biz-ass-store-ass-index-store-index">
			<i class="fa fa-check"></i> 考核下发
		</button>
		<button class="btn default hidden-inline-xs" type="button"
			id="btn_Cancel" style="margin-right: 0px;" data-dismiss="modal"
			aria-hidden="true">
			<i class="fa fa-undo"></i>&nbsp; 取&nbsp;消
		</button>
	</div>
</form>

<script src="${base}/biz/ass/target/ass-target-togrid-toPatch.js" />
<%@ include file="/common/ajax-footer.jsp"%>
<%-- <%@ include file="ass-target-togrid-tree.jsp"%> --%>