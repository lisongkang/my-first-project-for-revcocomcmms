<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-common-datatransfer-mapping-config-inputBasic"
	action="${base}/common/datatransfer/mapping-config!doSave" method="post" 
	data-editrulesurl="${base}/common/datatransfer/mapping-config!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-common-datatransfer-mapping-config-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">类名</label>
					<div class="controls">
		                <s:textfield name="className" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">匹配条件</label>
					<div class="controls">
		                <s:textfield name="conditionType" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">属性名称</label>
					<div class="controls">
		                <s:textfield name="fieldName" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">属性值</label>
					<div class="controls">
		                <s:textfield name="fieldValue" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">状态</label>
					<div class="controls">
						<s:if test="status==null">
		                	<s:radio name="status" list="#{1:'有效',0:'无效'}" value="'1'"/>
		                </s:if>
		                <s:else>
		                	<s:radio name="status" list="#{1:'有效',0:'无效'}"/>
		                </s:else>
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-common-datatransfer-mapping-config-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/common/datatransfer/mapping-config-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>