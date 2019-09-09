<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-sys-problem-inputBasic" action="${base}/biz/manage/problem/sys-problem!doDeal"
	method="post">
	<s:hidden name="id" />
	<s:hidden name="suboperid" />
	<s:hidden name="areaid" />
	<s:token />
	<div class="form-body">
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label class="control-label">问题类型</label>
					<div class="controls">
						<s:select name="pltype"  list="pltypeMap" cssStyle="width:200px" disabled="%{persistentedModel}"/>
					</div>
				</div>
			</div>
			
			<div class="col-md-3">
				<div class="form-group">
					<label class="control-label">地市</label>
					<div class="controls">
						<s:select name="city"  list="cityMap" cssStyle="width:150px" disabled="%{persistentedModel}"/>
					</div>
				</div>
			</div>
			
			<div class="col-md-3">
				<div class="form-group">
					<label class="control-label">业务区</label>
					<div class="controls">
						<s:textfield name="extraAttributes.areaname"  cssStyle="width:150px" disabled="%{persistentedModel}"/>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label class="control-label">问题状态</label>
					<div class="controls">
		               <s:select name="plstate" list="plstateMap" cssStyle="width:95px" disabled="%{persistentedModel}"/>
					</div>
				</div>
            </div>
			
		</div>
		<div class="row">
		<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">问题标题</label>
					<div class="controls">
						<s:textfield name="plname" maxlength="32" disabled="%{persistentedModel}" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">问题描述</label>
					<div class="controls">
						<s:textfield name="pldesc" disabled="%{persistentedModel}" />
					
					</div>
				</div>
			</div>
        </div>	
		<div class="row">
            <div class="col-md-4">
				<div class="form-group">
					<label class="control-label">提交人</label>
					<div class="controls">
		               <s:textfield name="extraAttributes.subopername" disabled="%{persistentedModel}" />                
					</div>
				</div>
            </div>
            <div class="col-md-4">
				<div class="form-group">
					<label class="control-label">提交部门</label>
					<div class="controls">
		               <s:textfield name="extraAttributes.suboperdeptname" disabled="%{persistentedModel}" />                
					</div>
				</div>
            </div>
            <div class="col-md-4">
				<div class="form-group">
					<label class="control-label">提交时间</label>
					<div class="controls">
		               <s:textfield name="subtime" disabled="%{persistentedModel}" />                
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-4">
				<div class="form-group">
					<label class="control-label">处理人</label>
					<div class="controls">
		               <s:textfield name="extraAttributes.dealopername" disabled="%{persistentedModel}" />                
					</div>
				</div>
            </div>
             <div class="col-md-4">
				<div class="form-group">
					<label class="control-label">处理部门</label>
					<div class="controls">
		               <s:textfield name="extraAttributes.dealoperdeptname" disabled="%{persistentedModel}" />                
					</div>
				</div>
            </div>
            <div class="col-md-4">
				<div class="form-group">
					<label class="control-label">处理时间</label>
					<div class="controls">
		               <s:textfield name="dealtime" disabled="%{persistentedModel}" />                
					</div>
				</div>
            </div>
        </div>
        
          <div class="row">
             <div class="col-md-12">
				<div class="form-group">
					<label class="control-label">处理描述</label>
					<div class="controls">
		               <s:textfield name="dealdesc" maxlength="128" required="required" />                
					</div>
				</div>
            </div>
        </div>
        
        <div class="row">
             <div class="col-md-12">
				<div class="form-group">
					<label class="control-label">备注</label>
					<div class="controls">
		               <s:textfield name="memo" maxlength="128" />                
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitSysproblem' data-grid-reload=".grid-biz-sys-problem-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/manage/problem/sys-problem-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
