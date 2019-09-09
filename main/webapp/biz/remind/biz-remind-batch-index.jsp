<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
			<div class="row search-form-default">
				<div class="col-md-12">
					<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-remind-biz-remind-batch-index"
						data-grid-search=".grid-biz-remind-biz-remind-batch-index">
						<div class="input-group">
							<div class="form-group">
                                 <s:select id="id_city" name="remindBatchParamBO.city" cssClass="form-control input-medium" list="{}"
                                      placeholder="请选择分公司..."/>
                            </div>
                            
                            <div class="form-group">
                                 <s:select id="id_remobjtype" name="remindBatchParamBO.remobjtype" cssClass="form-control input-medium" list="{}" 
                                      placeholder="请选择预警对象类型..."/>
                            </div> 
						
                            <div class="form-group">
                                 <s:select id="id_iscfm" name="remindBatchParamBO.iscfm" cssClass="form-control input-medium" list="{}"  
                                      placeholder="请选择确认状态..."/>
                            </div>                   
            
						    <div class="form-group" id="id_que_apptime_div">
                                 <input type="text" id="id_que_apptime" name="que_apptime" class="form-control input-medium input-daterangepicker"
                                    placeholder="录入时间">
                            </div> 
                            &nbsp;
								<button class="btn green" type="submmit" id="searchBtn">
									<i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
								</button>
								&nbsp;
								<button class="btn default hidden-inline-xs" type="reset">
									<i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
								</button>
								&nbsp;
								<button class="btn blue" id="confirmBtn" onclick="BizRemindBatchIndex.confirmRemindBatchs()">
									<i class="m-icon-swapright m-icon-white"></i>确认预警
								</button>
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<table class="grid-biz-remind-biz-remind-batch-index"></table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${base}/biz/remind/biz-remind-batch-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
