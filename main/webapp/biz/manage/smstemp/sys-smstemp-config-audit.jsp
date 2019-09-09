<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
    <ul class="nav nav-pills">
        <li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
    </ul>
   <div class="tab-content">
        <div class="tab-pane fade active in">
			<div class="row search-form-default">
			   <div class="col-md-12">
					<form action="#" method="get"
						class="form-inline form-validation form-search-init from-sys-smstemp-config-audit"
						data-grid-search=".grid-sys-smstemp-config-audit">
						<div class="input-group">
						    <div class="form-group">
								<s:hidden id="backupCityId" />
								<s:select id="id_city" name="sysSmstempConfigPo.city" cssStyle="width:150px"
									 list="{}"
									placeholder="请选择地市" />
							</div>
							<div class="form-group">
								<s:select id="id_tstatus" name="sysSmstempConfigPo.tstatus" cssStyle="width:180px"
									 list="{}"
									placeholder="请选择模板状态"/>
							</div>
							
							<div class="form-group">
									<input type="text" id="id_tname" name="sysSmstempConfigPo.tname" style="width:300px" class="form-control" placeholder="模板标题..."/>
							</div>
							<span class="input-group-btn">
							<button class="btn green" type="submit" id="btn_search">
								<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
							</button>
							<button class="btn default hidden-inline-xs" type="reset">
								<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
							</button>
							</span>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-sys-smstemp-config-audit"></table>
				</div>
			</div>
	    </div>
   </div>
</div>
<script src="${base}/biz/manage/smstemp/sys-smstemp-config-audit.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
