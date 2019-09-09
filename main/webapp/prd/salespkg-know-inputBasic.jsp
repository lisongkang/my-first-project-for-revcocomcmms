<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-prd-salespkg-know-inputBasic"
	action="${base}/prd/salespkg-know!doSave" method="post" 
	data-editrulesurl="${base}/prd/salespkg-know!buildValidateRules">
	<s:hidden name="id" />
	<s:token />
	<div class="form-actions">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-prd-salespkg-know-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
	<div class="form-body">
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">营销名称</label>
					<div class="controls">
						<s:textfield name="knowname" maxlength="256" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">简要描述</label>
					<div class="controls">
						<s:textfield name="brief" maxlength="256" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">对象类型</label>
					<div class="controls">
						<s:select name="objtype" list="objTypeMap" />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">对象名称</label>
					<div class="controls">
						<div class="input-icon right">
							<i class="fa fa-ellipsis-horizontal fa-select-obj"></i>
							<s:textfield name="extraAttributes.objname" />
							<s:hidden name="objid" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">办理业务</label>
					<div class="controls">
						<s:iterator var="item" value="#request.opcodes" status="s">
		                	<div class="col-md-4 <s:property value="%{#item.extraAttributes.related?'text-success':''}" />">
								<s:checkbox name="r2ids" fieldValue="%{#item.mcode}" value="%{#item.extraAttributes.related}"
									label="%{#item.display}" />
							</div>
		                </s:iterator> 
					</div>
				</div>
			</div>
		</div>
		 -->
        <div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">价格</label>
					<div class="controls">
						<s:textfield name="price" required="true"/>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">可销售次数</label>
					<div class="controls">
						<s:textfield name="maxtimes" />
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">地市</label>
					<div class="controls">
						<s:select name="city" list="areaMap" />
					</div>
				</div>
			</div>
		</div>
        <div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">建议推荐说明</label>
					<div class="controls">
						<s:textfield name="tocust" maxlength="256" />
					</div>
				</div>
			</div>
		</div>
        <div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">推荐导语</label>
					<div class="controls">
						<s:textfield name="wordexp" maxlength="256" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">优惠内容</label>
					<div class="controls">
						<s:textfield name="feeexp" maxlength="256" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">图片目录</label>
					<div class="controls">
						<s:textfield name="icon" maxlength="128" />
					</div>
				</div>
			</div>
		</div>
	   
	   <div class="row" id="salepkg-know-inputBasic-upload-photo">
	      
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">图片更改</label>
					<div class="controls" id="fileDiv">
						<input type="file"  id="salePhotoId" name="salePhoto"><br>
					</div>
				</div>
			</div>
			<div class="col-md-2">
				<input type="button" class="btn blue" value="上传" onclick = "SalepkgKnowInputBasic.uploadSalepkgKnowPhoto()"/>
			</div>
			<div class="col-md-3">
				<label class="control-label" style="position: absolute;left: 10px;color: red;width: 350px;margin-left: 0;">图片格式:jpg,png,jpeg;图片大小:不超过50K</label>
			</div>
	   </div>
	 </div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" data-grid-reload=".grid-biz-prd-salespkg-know-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/prd/salespkg-know-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>