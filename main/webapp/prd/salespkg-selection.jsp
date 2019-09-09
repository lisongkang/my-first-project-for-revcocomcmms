<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" id="form-biz-prd-salespkg-select" method="get" class="form-inline form-validation form-search-init"
						data-grid-search=".grid-biz-prd-salespkg-index">
						<div class="input-group">
							<div class="input-cont">
								<div class="form-body">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label">方案名称</label>
												<div class="controls">
													<input type="text" class="form-control input-large" name="search['CN_salespkgcode_OR_salespkgname']" placeholder="名称、代码...">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label">地市</label>
												<div class="controls">
									                <s:select cssClass="input-large" name="city" list="#application.enums.prvcity" />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label">区域</label>
												<div class="controls">
									                <s:select id="areaid" name="areaid" cssClass="input-large" list="{}" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<span class="input-group-btn">
								<button class="btn green" type="submmit">
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
					<table class="grid-biz-prd-salespkg-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/prd/salespkg-selection.js" />
<%@ include file="/common/ajax-footer.jsp"%>
