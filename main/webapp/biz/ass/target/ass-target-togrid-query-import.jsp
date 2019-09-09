<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-ass-target-togrid-query-import"
	action="${base}/biz/ass/target/ass-target-togrid!targetToPatch" method="post" 
	data-editrulesurl="${base}/biz/ass/store/ass-index-store!buildValidateRules" id="id-form-biz-ass-target-togrid-query-import">
	<s:hidden name="id" />
	<s:hidden name="version" />
	<div class="form-body">
		<div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">选择EXCEL <span class="required">*</span></label>
					<div class="controls">
		               <input type="file" id="id_importExcel" name="myFile" style="display: block;"
		               accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
					</div>
				</div>
            </div>
        </div>
	</div>
	<div class="form-actions right">
		<a href="${base}/biz/ass/target/ass-target-togrid-query!downTemplate" 
			style="float: left;margin-left: 5%;margin-bottom: 1px;color: red;text-decoration:underline;">下载Excel模板</a>
		<button class="btn blue" type="button" id="btn_import" name="btn_import" data-grid-reload=".grid-biz-ass-store-ass-index-store-index">
			<i class="fa fa-check"></i> 确定导入
		</button>
		<button class="btn default hidden-inline-xs" type="button"
			id="btn_Cancel" style="margin-right: 0px;" data-dismiss="modal"
			aria-hidden="true">
			<i class="fa fa-undo"></i>&nbsp; 取&nbsp;消
		</button>
	</div>
</form>

<script src="${base}/biz/ass/target/ass-target-togrid-query-import.js" />
<%@ include file="/common/ajax-footer.jsp"%>
<%-- <%@ include file="ass-target-togrid-tree.jsp"%> --%>