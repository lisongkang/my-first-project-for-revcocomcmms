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
						class="form-inline form-validation form-search-init form-biz-sys-problem-index"
						data-grid-search=".grid-biz-sys-problem-index">
						<div class="input-group">
                          <div class="form-group">
								<s:hidden id="backupCityId" />
								<s:select id="id_city" name="sysProblemPo.city" cssStyle="width:150px"
									 list="{}"
									placeholder="请选择地市" />
							</div>
							<div class="form-group">
								<s:select id="id_area" name="sysProblemPo.area" cssStyle="width:180px"
									 list="{}"
									placeholder="请选择业务区"/>
							</div>
							<div class="form-group">
									<s3:datetextfield name="sysProblemPo.subtime" format="date"  placeholder="提交时间"/>
							</div>
							<div class="form-group">
								<s:select id="id_pltype" name="sysProblemPo.pltype"
									list="{}" placeholder="问题类型" cssStyle="width:150px" />
							</div>
							<div class="form-group">
								<s:select id="id_plstate" name="sysProblemPo.plstate"
									list="{}" placeholder="问题状态" cssStyle="width:150px" />
							</div>
							<span class="input-group-btn">
							<button class="btn green" type="submmit" id="btn_search">
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
					<table class="grid-biz-sys-problem-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/manage/problem/sys-problem-index.js"></script>
<%@ include file="/common/ajax-footer.jsp"%>
