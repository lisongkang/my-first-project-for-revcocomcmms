<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-core-task-cfg-task-inputBasic"
	action="${base}/common/task/cfg-task!doSave" method="post" 
	data-editrulesurl="${base}/common/task/cfg-task!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-core-task-cfg-task-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
		<div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">任务名称</label>
					<div class="controls">
		                <s:textfield name="taskName" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">任务编码</label>
					<div class="controls">
		                <s:textfield name="taskCode" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务实现类</label>
					<div class="controls">
		                <s:textfield name="implClass" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">运行方式</label>
					<div class="controls">
		                <s:select id="taskMethod" name="taskMethod" list="#{'C':'周期','S':'指定时间','I':'立即执行'}" value='C'/>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">周期表达式</label>
					<div class="controls">
		                <s:textfield name="taskExpr" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">状态</label>
					<div class="controls">
		                <s:select id="status" name="status" list="#{'Y':'有效','N':'无效'}" value='Y'/>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">备注</label>
					<div class="controls">
		                <s:textfield name="remark" />
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-core-task-cfg-task-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/common/task/cfg-task-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>