<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init"
						data-grid-search=".grid-biz-prd-salespkg-know-index2">
						<div class="input-group">
							<div class="input-cont">
								<input type="text"
									name="search['CN_knowname_OR_objcode_OR_objname']"
									class="form-control" placeholder="知识库名称、商品编码、商品名称">
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
					<table class="grid-biz-prd-salespkg-know-index2"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/prd/salespkg-know-selection.js" />
<%@ include file="/common/ajax-footer.jsp"%>
