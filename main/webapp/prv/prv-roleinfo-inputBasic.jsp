<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form
	class="form-horizontal form-bordered form-label-stripped form-validation form-biz-prv-prv-roleinfo-inputBasic"
	action="${base}/prv/prv-roleinfo!doSave" method="post"
	data-editrulesurl="${base}/prv/prv-roleinfo!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-body">
		<div class="row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">角色名称</label>
					<div class="controls">
						<s:textfield name="name" maxlength="16" />
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">角色级别</label>
				<%--	<div class="controls">
						<s:select name="rolelevel" list="#{'0':'低权限','5':'中权限','9':'高权限'}" />
					</div>--%>
				<%--	<script>
                        console.info("${rolelevel}")
					</script>--%>
					<s:if test="%{loginRolelevel == 9}">
						<div class="controls">
							<s:select name="rolelevel" list="#{'0':'低权限','5':'中权限','9':'高权限'}" />
						</div>
					</s:if>
					<s:elseif test="%{loginRolelevel == 5}">
						<div class="controls">
							<s:select name="rolelevel" list="#{'0':'低权限','5':'中权限'}" />
						</div>
					</s:elseif>
					<s:elseif test="%{loginRolelevel == 0}">
						<div class="controls">
							<s:select name="rolelevel" list="#{'0':'低权限'}" />
						</div>
					</s:elseif>
					<s:else>
						<div class="controls">
							<s:select name="rolelevel" list="#{'0':'低权限'}" />
						</div>
					</s:else>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">地市</label>
					<div class="controls">
						<%-- <select id="id_city">
							<data:option gcode="PRV_CITY" contain="${contain}"/>
						</select> --%>
						<s:select id="id_city" name="extraAttributes.city"
							list="prvCityMap" />
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">区域</label>
					<div class="controls">
						<!-- <select id="id_areaid" name="areas" required="required">
		                </select> -->
						<s:select id="id_areaid" name="areas" required="required"
							list="areasMap" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">启用时间</label>
					<div class="controls">
						<s3:datetextfield name="stime" format="date" />
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">结束时间</label>
					<div class="controls">
						<s3:datetextfield name="etime" format="date" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-5">
				<div class="form-group">
					<label class="control-label">角色类型</label>
					<div class="controls">
						<s:select name="atype" required="required" list="#{'0':'网格','1':'集客'}" />
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-md-10">
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
		<button class="btn blue" type="submit"
			data-grid-reload=".grid-biz-prv-prv-roleinfo-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/prv/prv-roleinfo-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>