<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-prd-catalog-inputBasic"
	action="${base}/prd/catalog!doSave" method="post" 
	data-editrulesurl="${base}/prd/catalog!buildValidateRules">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-prd-catalog-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">名称</label>
					<div class="controls">
						<s:textfield name="catalogname" maxlength="20" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">地市</label>
					<div class="controls">
		                <s:select name="city" list="areaMap" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">区域</label>
					<div class="controls">
		                <s:select id="areaid" name="area.id" list="townMap" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">排序优先级</label>
					<div class="controls">
		                <s:textfield name="pri" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">状态</label>
					<div class="controls">
		                <s:radio name="catalogstatus" list="#{'Y':'有效','N':'无效'}" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">目录类型</label>
					<script>
                        console.info("${ctype}")
					</script>
					<s:if test="%{ctype == 0}">
						<div class="controls">
							<s:radio name="ctype" list="#{'0':'销售目录','1':'场景目录'}"/>
						</div>
					</s:if>
					<s:elseif test="%{ctype == 1}">
						<div class="controls">
							<s:radio name="ctype" list="#{'0':'销售目录','1':'场景目录'}"/>
						</div>
					</s:elseif>
					<s:else>
						<div class="controls">
							<s:radio name="ctype" list="#{'0':'销售目录','1':'场景目录'}" value="0"/>
						</div>
					</s:else>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">描述</label>
					<div class="controls">
		                <s:textfield name="catalogdesc" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-prd-catalog-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/prd/catalog-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>