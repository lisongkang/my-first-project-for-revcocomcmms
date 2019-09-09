<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="container-fluid data-view">
	<div class="well form-horizontal">
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">操作员编号:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="operid" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">订单ID:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="orderid" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">操作时间:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:date name="optime" format="yyyy-MM-dd HH:mm:ss" />
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">菜单编号:</label>
					<div class="controls">
						<p class="form-control-static">
							<s:property value="menuid" />
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">地区:</label>
					<div class="controls">
						<p class="form-control-static">
							<data:translate gcode="PRV_CITY" mcode="${city}"/>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>