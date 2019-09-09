<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get"
						class="form-inline form-validation form-search-init form-sys-tag-selection"
						data-grid-search=".grid-sys-tag-selection">
						<div class="input-group">
							<div class="input-cont">
								<div class="form-body">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label">地市</label>
												<div class="controls">
									                <s:select cssClass="input-large" name="city" list="{}" />
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
					<div class="controls">
		                <table class="grid-sys-tag-selection"></table>                 
					</div>
	          </div>
            </div>
            
            <div class="row form-actions center" style="width: 102%">
				<button class="btn blue" type="button" id="tagAddBtn"
					style="margin-left: -10%;">&or;</button>
			</div>
			<form class="form-horizontal search-form-default form-bordered form-label-stripped  form-validation form-add-sys-tag-data" action="${base}/biz/manage/tag/sys-tag!saveSysTag" 
	         method="post">
	         <div class="form-actions">
				<button class="btn blue" type="button">
					 新增标签
				</button>
			</div>
		    <div class="form-body">
		    <s:hidden name="tagcode" />
		    <s:hidden name="tagid" />
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
						<s:select name="ods_city" disabled="true"  list="prvCityMap" />
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
			    <button class="btn blue" type="button" id='saveSysTagBtn'>
				<i class="fa fa-check"></i> 保存
				</button>
	         </div>
			</form>
		</div>
		
		  <div class="row form-actions center" style="width: 102%">
				<button class="btn blue" type="button" id="backBtn"
					style="margin-left: -10%;">返回</button>
		  </div>
	</div>	
</div>
<script src="${base}/biz/manage/tag/sys-tag-selection.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>