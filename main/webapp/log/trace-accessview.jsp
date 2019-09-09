<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="container-fluid data-view">
	<div class="well form-horizontal">
	<s:if test="%{#request.log!=null}">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">客户端IP:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.log.clientIP" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">调用时间:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:date name="#request.log.callTime" format="yyyy-MM-dd HH:mm:ss" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">调用接口:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.log.callMethod" />
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">响应代码:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.log.returnCode" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">响应提示:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.log.returnMsg" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">响应结束时间:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:date name="#request.log.endTime" format="yyyy-MM-dd HH:mm:ss" />
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">请求报文:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.log.request" />
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">响应报文:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="#request.log.response" />
						</p>
					</div>
				</div>
			</div>
		</div>
	</s:if>
	</div>
</div>