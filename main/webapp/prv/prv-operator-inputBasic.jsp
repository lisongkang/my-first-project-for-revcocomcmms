<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-prv-operator-inputBasic" action="${base}/prv/prv-operator!doSave"
	method="post">
	<s:hidden name="id" />
	<s:hidden name="operinfo.id" />
	<s:token />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-auth-user-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">登录帐号</label>
					<div class="controls">
						<s:textfield name="loginname" maxlength="32" disabled="%{persistentedModel}" placeholder="创建之后不可修改，请仔细填写" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">名称</label>
					<div class="controls">
						<s:textfield name="name" maxlength="32" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">启用状态</label>
					<div class="controls">
						<s:radio name="status" list="#{'0':'启用','1':'停用'}" requiredLabel="true"/>
						<span class="help-block"></span>
					</div>
				</div>
			</div>
	        <div class="row">
	            <div class="col-md-6">
	                <div class="form-group">
	                    <label class="control-label">移动电话</label>
	                    <div class="controls">
	                        <s:textfield name="operinfo.phone" maxlength="11" />
	                    </div>
	                </div>
	            </div>
	        </div>	
        </div>	
		<div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">启用时间</label>
					<div class="controls">
		                <s3:datetextfield name="stime" next="etime" format="date"/>                  
					</div>
				</div>
            </div>
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">结束时间</label>
					<div class="controls">
		                <s3:datetextfield name="etime" format="date"/>                  
					</div>
				</div>
            </div>
        </div>
        <!-- 
        <div class="row">
        	<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">所属部门</label>
					<div class="controls">
						<div class="input-icon right">
							<i class="fa fa-ellipsis-horizontal fa-select-department"></i>
							<s:textfield name="departmentName" />
							<s:hidden name="departmentId" />
						</div>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
        	<div class="form-group">
				<label class="control-label">用户角色</label>
				<div class="controls">
	                <s:iterator var="item" value="#request.roles" status="s">
	                	<div class="col-md-4 <s:property value="%{#item.extraAttributes.related?'text-success':''}" />">
							<s:checkbox name="r2ids" fieldValue="%{#item.id}" value="%{#item.extraAttributes.related}"
								label="%{#item.name}" />
						</div>
	                </s:iterator>                 
				</div>
			</div>
        </div>
         -->
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-auth-user-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/prv/prv-operator-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>
