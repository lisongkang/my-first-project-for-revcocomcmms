<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
 
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-usernew-user-new-resaddr"
						data-grid-search=".grid-biz-tmpopenlimit-tmp-open-limit-selectopen">
                       <!--  
                       <div class="form-group">
							 <input type="text" name="quekeyword" class="form-control" placeholder="地址...">
						</div>
						<button class="btn green" type="submmit" onclick="">
							<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
						</button>
						-->
					</form>  
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-tmpopenlimit-tmp-open-limit-selectopen"></table>
				</div>
			</div>

<script src="${base}/tmpopenlimit/biz-tmp-open-limit-openTabs.js" />
<%@ include file="/common/ajax-footer.jsp"%>