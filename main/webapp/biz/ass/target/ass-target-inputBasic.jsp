<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-target-ass-target-inputBasic"
	action="${base}/biz/ass/store/ass-index-store!doSave" method="post" 
	data-editrulesurl="${base}/biz/ass/store/ass-index-store!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	
	<s:token />

	<div class="form-body">
		<div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">指标名称<span class="required">*</span></label>
					<div class="controls">
		                 <s:textfield name="name"  id="name"/>
					</div>
				</div>
            </div>
        </div>
	   <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">指标归属<span class="required">*</span></label>
					<div class="controls">
<!-- 		                 <select id="city" name="city" /> -->
		                 <s:select id="id_city" name="city" 
									 list="{}"
									placeholder="指标归属" />
					</div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label"><span class="required">*</span>统计周期</label>
					<div class="controls">
		                 <select id="id_assSttCycle" name=assSttCycle >
		                 	<option value="0">天</option>
		                 	<option value="1">周</option>
		                 	<option value="2">月</option>
		                 	<option value="3">年</option>
		                 </select>
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">KPI-ID</label>
					<div class="controls">
		               <s:textfield name="fieldId" id="fieldId"/>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">取数SQL</label>
					<div class="controls">
		                <s:textarea id="visql" name="visql" rows="3"  maxlength="60"  placeholder="可在此编辑信息" class="form-control"/>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">指标说明</label>
					<div class="controls">
		                <s:textarea id="asscontent" name="asscontent" rows="3"  maxlength="60"  placeholder="可在此编辑信息" class="form-control"/>
					</div>
				</div>
            </div>
        </div>

	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" name="btn_store" data-grid-reload=".grid-biz-ass-store-ass-index-store-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel"  id="btn_cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/ass/target/ass-target-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>