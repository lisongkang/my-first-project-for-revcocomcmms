<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="container-fluid data-view">
	<div class="well form-horizontal">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">网格编码:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="gridcode" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">网格名称:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="gridname" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">小区:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="extraAttributes.patch" />
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">网格经理:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="extraAttributes.manager" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">所属分公司:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="extraAttributes.city" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>