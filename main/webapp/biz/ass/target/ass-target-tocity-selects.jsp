<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary tocity-selects" >
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-ass-target-selects"
						data-grid-search=".grid-biz-ass-target-ass-target-selects">
						 <div class="input-group">
						     <div class="form-group" style="margin-left: 10px;">
								<input type="text" name="name" class="form-control" placeholder="指标名称" size="30">
						     </div>
						     <div class="form-group" style="margin-left: 10px;">
						     </div>
						     <div class="form-group" style="margin-left: 10px;">
								<s:select id="id_city" name="city" cssStyle="width:150px"
									 list="{}" placeholder="指标归属" >
								</s:select>
						     </div>
						     <div class="form-group" style="margin-left: 10px;">
								<button class="btn green" id="btn_search" type="submmit">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 搜&nbsp;索
								</button>
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
						     </div>
					     </div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-ass-target-ass-target-selects">
					</table>
				</div>
			</div>
			<div class="row">
				<button class="btn green" id="btn_confirm" type="submmit" style="margin-left: 300px;">
					<i class="m-icon-swapright m-icon-white"></i>&nbsp; 确&nbsp;定
				</button>
				<button class="btn default hidden-inline-xs" type="button" id="btn_Cancel" style="margin-right: 0px;"
					data-dismiss="modal" aria-hidden="true">
					<i class="fa fa-undo"></i>&nbsp; 取&nbsp;消
				</button>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/ass/target/ass-target-tocity-selects.js" />
<%@ include file="/common/ajax-footer.jsp"%>
