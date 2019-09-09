<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-store-ass-index-store-inputBasic"
	action="${base}/biz/ass/store/ass-index-store!doSave" method="post" 
	data-editrulesurl="${base}/biz/ass/store/ass-index-store!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	
	<s:token />

	<div class="form-body">
	
	   <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">分公司<span class="required">*</span></label>
					<div class="controls">
		               <%--  <s:textfield name="city" /> --%>
		                 <select id="city" name="city" />
					</div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核内容<span class="required">*</span></label>
					<div class="controls">
		                <s:textfield name="asscontent" />
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核参数<span class="required">*</span></label>
					<div class="controls">
		                <%-- <s:textfield name="assparam" /> --%>
		                 <select id="assparam" name="assparam" onchange="AssIndexStoreInputBasic.changeAssparam('.form-biz-ass-store-ass-index-store-inputBasic');"/>
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核对象<span class="required">*</span></label>
					<div class="controls" id="div_assobjName" style="display:block">
		                <%-- <s:textfield name="assobj" /> --%>
                        <input type="text" id="assobjName" name="assobjName" placeholder="点击考核对象" 
                        onclick="AssIndexStoreInputBasic.selectOrderPrds('.form-biz-ass-store-ass-index-store-inputBasic');" readonly="readonly">
					</div>
					
					<div class="controls" id="div_assobjName2" style="display:none">
                         <%-- <s:select id="assobjName2" name="assobjName2" list="{}"  multiple="true" placeholder="请选择考核对象..."/> --%>
					     <select id="assobjName2" name="assobjName2" placeholder="请选择考核对象..."/>
					
					</div>
					
					<div class="controls" id="div_assobjName3" style="display:none">
                         <select id="assobjName3" name="assobjName3" placeholder="请选择考核对象..."/>
					</div>					
					
					<input type="hidden" id="assobj" name="assobj">		
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核周期单位<span class="required">*</span></label>
					<div class="controls">
		                <select id="unit" name="unit" />
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">任务单位<span class="required">*</span></label>
					<div class="controls">
		               <%--  <s:textfield name="taskunit" /> --%>
		                <select id="taskunit" name="taskunit" />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">任务总数值<span class="required">*</span></label>
					<div class="controls">
		                <s:textfield name="totalnum" />
					</div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">失效时间<span class="required">*</span></label>
					<div class="controls">
		                <s3:datetextfield name="expdate" format="date"/>                  
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">制定部门<span class="required">*</span></label>
					<div class="controls">
		                <%-- <s:textfield name="depart" /> --%>
		                <input type="text" id="deptName" name="deptName"  disabled="true">
						<input type="hidden" id="depart" name="depart">
					</div>
				</div>
            </div>
            
<%--             <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">操作员</label>
					<div class="controls">
		                <s:textfield name="operator" />
					</div>
				</div>
            </div> --%>
        </div>

<%--         <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">操作时间</label>
					<div class="controls">
		                <s3:datetextfield name="opdate" format="date"/>                  
					</div>
				</div>
            </div>
        </div> --%>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" name="btn_store" data-grid-reload=".grid-biz-ass-store-ass-index-store-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel"  id="btn_cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/ass/store/ass-index-store-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>