<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab"
			href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"><a class="btn default reload"
			href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get"
						class="form-inline form-validation form-search-init"
						data-grid-search=".visit-comment-record-page-index">
						<div class="input-group">
							<div class="form-group">
								<select name="sendMethod1" id="sendMethod1"
									placeholder="回访方式..." class="input-medium">
									<option value="WX">微信公众号</option>
									<option value="SMS">短信</option>
								</select>
							</div>
							<div class="form-group">
								<select name="sendStatus1" id="sendStatus1"
									placeholder="发送状态..." class="input-medium">
									<option value="0">未发送</option>
									<option value="1">已发送</option>
									<option value="2">发送失败</option>
								</select>
							</div>
							<div class="form-group">
								<label class="control-label">发送时间</label>
								<div class="controls">
									<s3:datetextfield name="sendTime1" format="date" />
								</div>
							</div>
							<div class="form-group">
								<input type="text" name="custName1" id="custName1"
									placeholder="客户姓名">
							</div>
							<div class="form-group">
								<input type="text" name="mobile1" id="mobile1"
									placeholder="手机号码">
							</div>

							<button class="btn green" type="submmit">
								<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
							</button>
							<button class="btn default hidden-inline-xs" type="reset">
								<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
							</button>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table
						class="visit-comment-record-page-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/sys/visit-comment-record-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
