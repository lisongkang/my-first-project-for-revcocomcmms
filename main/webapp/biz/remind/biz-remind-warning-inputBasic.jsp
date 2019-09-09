<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-remind-biz-remind-warning-inputBasic"
	action="${base}/biz/remind/biz-remind-warning!doSave" method="post" 
	data-editrulesurl="${base}/biz/remind/biz-remind-warning!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">预警类型</label>
					<div class="controls">
		                <s:select id="id_objtype" name="objtype" list="{}" required="required"/>
					</div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">选择对象</label>
					<div class="controls">
		                <s:textfield name="objnames" onclick="BizRemindWarningInputBasic.selectRemObj();" readonly="true" placeholder="点击弹出选择对象页面"/>
		                <input type="hidden" id="objids" name="objids" >
					</div>
				</div>
            </div>
        </div>
       
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">有效期限</label>
					<div class="controls">
		                <s3:datetextfield name="edate" format="date"/>                  
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">优先级</label>
					<div class="controls">
		                <s:textfield name="pri" />
					</div>
				</div>
            </div>
        </div>
         <div class="row">
            <div class="col-md-12">
				<div class="form-group">
					<label class="control-label">预警任务描述</label>
					<div class="controls">
		                <s:textfield name="description" />
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-remind-biz-remind-warning-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/remind/biz-remind-warning-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>