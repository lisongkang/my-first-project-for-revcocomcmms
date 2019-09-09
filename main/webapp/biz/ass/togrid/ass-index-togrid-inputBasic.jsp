<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-togrid-ass-index-togrid-inputBasic"
	action="${base}/biz/ass/togrid/ass-index-togrid!doSave" method="post" >
	<s:hidden name="id" />
	<s:hidden name="version" />
	<input type="hidden" id="city" name="city" value="<s:property value="model.city"/>">
	<input type="hidden" id="grids" name="grids" value="<s:property value="model.grids"/>">
	<s:token />
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核指标内容</label>
					<div class="controls">
		                <input type="text" id="asscontent" name="asscontent" value="<s:property value="model.asscontent"/>" readonly="readonly">
						<input type="hidden" id="assid" name="assid" value="<s:property value="model.id"/>">	
					</div>
				</div>
            </div>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">选择网格</label>
					<div class="controls">
					    <!--  <select id="gridid" name="gridid"/>	 -->
					      <s:select id="gridid" name="gridid" list="{}"  multiple="true" placeholder="请选择网格..." />
					     
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核起日期</label>
					<div class="controls">
		               <s3:datetextfield name="bdate" format="date"/>   
					</div>
				</div>
            </div>
            <%-- <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">考核周期(月)</label>
					<div class="controls">
		                <s:textfield name="cyclenum" />
					</div>
				</div>
            </div> --%>
            
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">每月考核数</label>
					<div class="controls">
		                <s:textfield name="assnum" />
					</div>
				</div>
            </div>
        </div>

	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" name="btn_togrid" data-grid-reload=".grid-biz-ass-togrid-ass-index-togrid-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default" type="button" name="btn_hidegrid">取消</button>
	</div>
</form>
<script src="${base}/biz/ass/togrid/ass-index-togrid-inputBasic.js"/>
<%@ include file="/common/ajax-footer.jsp"%>