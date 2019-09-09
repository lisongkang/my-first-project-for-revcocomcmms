<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right" style="opacity:0"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-sys-tag-index"
						data-grid-search=".grid-biz-sys-tag-index">
						<div class="input-group">
						   <div class="input-cont">
								<input type="text" name="search['CN_tagname']" class="form-control"  placeholder="标签名称...">
							</div> 
							<span class="input-group-btn">
								<button class="btn green" id="btn_search" type="submmit">
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
					<table class="grid-biz-sys-tag-index"></table>
				</div>
			</div>
		</div>
	</div>
<script src="${base}/biz/manage/tag/sys-tag-index.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
