<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-market-market-batch-pushInputBasic"
	action="${base}/market/market-batch!doSavePush" method="post">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<input type="hidden" id="taskid" name="taskid" value="<s:property value="model.id"/>">
	
	<s:token />
	<div class="form-body">
        
        <div class="row">
            <div class="col-md-12">
				<div class="form-group">
					<label class="control-label">营销标识</label>
					<div class="controls">
		                <input type="text" id="knowname" name="knowname" value="<s:property value="model.knowname"/>" readonly="readonly">
						<input type="hidden" id="taskId" name="marketBatchParamBO.taskId" value="<s:property value="model.id"/>">
					</div>
				</div>
            </div>            
        </div>
        
	    <div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">推送网格人员</label>
					<div class="controls">
		                <s:select id="pushOperids" name="marketBatchParamBO.pushOperids" list="{}"  multiple="true" placeholder="请选择推送网格人员"/>
					</div>
				</div>
            </div>     
        </div>
	
		<div class="row">
            <div class="col-md-12">
				<div class="form-group">
					<label class="control-label">选择营销客户</label>
					<div class="controls">
		                <s:textfield name="pushCustomerDisp" onclick="MarketBatchPushInputBasic.selectCustMarket();" readonly="true" placeholder="点击选择营销客户"/>
		                <input type="hidden" id=pushCustomerObj name="marketBatchParamBO.pushCustomerObj" >
					</div>
				</div>
            </div>
        </div>
        
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submmit" onclick="return MarketBatchPushInputBasic.checkRequiredInput();">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel"  id="btn_cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/market/market-batch-pushInputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>