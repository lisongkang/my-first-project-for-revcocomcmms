<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-sys-tag-inputBasic" action="${base}//biz/manage/tag/sys-tag!doSave"
	method="post">
	<s:hidden name="id" />
	<s:token />
	<div class="form-body">
    <div class="row">
		<div class="col-md-3">
			<div class="form-group">
				<label class="control-label">标签名称</label>
				<div class="controls">
				<s:textfield name="tagname" disabled="true"  />
				</div>
			</div>
		</div>
		
		<div class="col-md-3">
			<div class="form-group">
				<label class="control-label">创建者</label>
				<div class="controls">
				<s:textfield name="owner" disabled="true" />
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<label class="control-label">地市</label>
				<div class="controls">
				<s:select name="city" disabled="true"  list="prvCityMap" />
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<label class="control-label">是否显示</label>
				<div class="controls">
				<s:select  name="isshow"  list="#{'Y':'是','N':'否'}" val='Y' required="required"/>
				</div>
			</div>
		</div>
	</div>
	<div class='row'>
	  <div class="col-md-12">
			<div class="form-group">
				<label class="control-label">标签描述</label>
				<div class="controls">
				<s:textfield name="tagdesc"  disabled="true" />
				</div>
			</div>
		</div>
	</div>
	 <div class="row">
		
		<div class="col-md-12">
			<div class="form-group">
				<label class="control-label">备注</label>
				<div class="controls">
				<s:textfield name="memo" maxlength="256" />
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="form-actions right">
		<button class="btn blue" type="button" name="submitSysTag">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>
<script src="${base}/biz/manage/tag/sys-tag-inputBasic.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
