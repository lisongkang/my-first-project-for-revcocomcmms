<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-market-market-batch-inputBasic"
	action="${base}/market/market-batch!doSave" method="post" 
	data-editrulesurl="${base}/market/market-batch!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-body">
	   <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">营销标识</label>
					<div class="controls">
<!-- 		                <select id="id_knowid" name="knowid" required="required">
 -->		                <s:select id="id_knowid" name="knowid" list="{}" required="required"/>
		                </select>
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务区范围</label>
					<div class="controls">
		                <s:select id="id_areaids" name="areaids" cssClass="form-control input-medium" list="{}"  multiple="true"
                                      required="required" placeholder="请选择业务区..."/>
					</div>
				</div>
            </div>
        </div>
<%-- 	    <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">营销批次</label>
					<div class="controls">
		                <s:textfield name="batchno" />
					</div>
				</div>
            </div>
        </div> --%>
        
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">状态</label>
					<div class="controls">
		                <s:select id="id_status" name="status" list="#{'0':'无效','1':'有效'}" required="required"/>
					</div>
				</div>
            </div>
            
             <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">总户数</label>
					<div class="controls">
		                <s:textfield name="nums" required="required"/>
					</div>
				</div>
            </div>
        </div>        
        
        <div class="row">
            <div class="col-md-12">
				<div class="form-group">
					<label class="control-label">备注</label>
					<div class="controls">
		                <s:textfield name="memo" maxlength="120" />
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-market-market-batch-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/market/market-batch-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>