<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-remind-biz-rem-rulecfg-inputBasic"
	action="${base}/biz/remind/biz-rem-rulecfg!doSave" method="post" 
	data-editrulesurl="${base}/biz/remind/biz-rem-rulecfg!buildValidateRules">
    <input type="hidden" id="idHidden" name="idHidden" value="<s:property value="model.bizRemRulecfg.id"/>" />
	<input type="hidden" id="tritypeHidden" name="tritypeHidden" value="<s:property value="model.bizRemRulecfg.tritype"/>" />
	<input type="hidden" id="trivaluesHidden" name="trivaluesHidden" value="<s:property value="model.bizRemRulecfg.trivalues"/>" />
	<input type="hidden" id="numsHidden" name="numsHidden" value="<s:property value="model.bizRemRulecfg.nums"/>" />
	<input type="hidden" id="iscfmHidden" name="iscfmHidden" value="<s:property value="model.bizRemRulecfg.iscfm"/>" />
	<input type="hidden" id="elenHidden" name="elenHidden" value="<s:property value="model.bizRemRulecfg.elen"/>" /> 
	<s:hidden name="version" />
	<s:token />
    <div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">预警对象类型</label>
					<div class="controls">
		                 <input type="text" id="objtype" name="objtypename" value="<s:property value="model.bizRemindWarning.objtypename"/>" readonly="readonly">
		                 <input type="hidden" id="remid" name="remid" value="<s:property value="model.bizRemindWarning.id"/>">
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">预警任务描述</label>
					<div class="controls">
		               <input type="text" id="description" name="description" value="<s:property value="model.bizRemindWarning.description"/>" readonly="readonly">
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">预警条件</label>
					<div class="controls">
		                <s:select id="tritype" name="tritype" list="{}" required="required"/>
		                <input type="hidden" id="id" name="id" />
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">触发条件(超过小时数)</label>
					<div class="controls">
		                <s:textfield name="trivalues" required="required"/>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">提醒次数</label>
					<div class="controls">
		                <s:textfield name="nums" />
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">是否自动确认</label>
					<div class="controls">
		                 <s:select id="iscfm" name="iscfm" list="{}" required="required"/>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">预警有效天数</label>
					<div class="controls">
		                <s:textfield name="elen" required="required"/>
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-remind-biz-rem-rulecfg-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/remind/biz-rem-rulecfg-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>