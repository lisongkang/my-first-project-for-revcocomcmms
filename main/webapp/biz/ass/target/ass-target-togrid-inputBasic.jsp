<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-target-togrid-inputBasic"
	action="${base}/biz/ass/target/ass-target-togrid!targetToPatch" method="post" 
	>
	<s:hidden name="id" />
	<s:hidden name="version" />
	
	<s:token />

	<div class="form-body" style="height: 80%;">
		<div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核指标<span class="required">*</span></label>
					<div class="controls">
						 <s:textfield name="assName"  id="id_assName" disabled="true"/>
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核期</label>
					<div class="controls">
		                <s:textfield name="cycleNum"  id="id_cyclenum" cssStyle="width:200px;" readonly="true"/>
					</div>
				</div>
            </div>
        </div>
	   <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">下发网格<span class="required">*</span></label>
					<div class="controls">
						<s:textfield name="name" id="view_checked_grid" />
						<input id="view_checked_grid_ids" name="assTargetPatchBo.gridids" hidden="true"/>
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">目标值</label>
					<div class="controls">
		               <s:textfield name="assNum"  id="id_assNum" cssStyle="width:200px;"/>
					</div>
				</div>
            </div>
        </div>
        <div class="form-actions right">
			<button class="btn blue" type="button" id="btn_confirm" name="btn_confirm" data-grid-reload=".grid-biz-ass-store-ass-index-store-index">
				<i class="fa fa-check"></i> 确定
			</button>
			<button class="btn default btn-cancel"  id="btn_cancel" type="button">取消</button>
		</div>
	</div>
	
	<div id="to_grid_inputBasic_Div"
		style="display: none; position: absolute; z-index: 999999999999999;">
		<ul id="to_grid_inputBasic_tree" class="ztree ztree_demo" style="margin-top: 0;height: 240px;"></ul>
	</div>
</form>

<script src="${base}/biz/ass/target/ass-target-togrid-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>