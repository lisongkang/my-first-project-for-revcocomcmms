<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-az-quota-az-quota-inputBasic"
	action="${base}/az/quota/az-quota!doSave" method="post">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">地市</label>
					<div class="controls">
						<s:select name="city" id="id_city" list="areaMap" />
					</div>
				</div>
			</div>
	    </div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">编号</label>
					<div class="controls">
						<s:textfield  id="id_newnumber"  name="newnumber" type="text"  class="form-control" required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">项目名称</label>
					<div class="controls">
						<s:textfield id="id_constname" name="constname" type="text"  class="form-control"  required="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="control-label">项目单位</label>
					<div class="controls">
						<s:textfield id="id_company" name="company" type="text"  class="form-control" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<label class="control-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*地市操作员如果不知道属于几类地区，可将4类单价设置成一样</label>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">一类地区单价</label>
					<div class="controls">
						<s:textfield name="oneprice" required="true"/>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">二类地区单价</label>
					<div class="controls">
						<s:textfield name="twoprice" required="true"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">三类地区单价</label>
					<div class="controls">
						<s:textfield name="threeprice" required="true"/>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<label class="control-label">四类地区单价</label>
					<div class="controls">
						<s:textfield name="fourprice" required="true"/>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">项目明细</label>
					<div class="controls">
						<s:textfield id="id_constdetail" name="constdetail" type="text"  class="form-control"  />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">工作内容</label>
					<div class="controls">
						<s:textarea id="id_constcontent" name="constcontent"   placeholder="可在此编辑备注信息" class="form-control"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label class="control-label">凭据附件</label>
				<div class="controls">
					<s:textfield id="id_adsite" name="fileids" type="text"  class="form-control"  required="true" />
				</div>
			</div>
		</div>
		<div style ="margin-top:5px" >
			<button class="btn blue"  name='uploadAdImgBtn' >
				<i class="fa "></i>上传
			</button>
		</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="submit" name='btn_submitAdConfig'  data-grid-reload=".grid-biz-az-quota-az-quota-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>

</form>
<script src="${base}/az/quota/az-quota-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>