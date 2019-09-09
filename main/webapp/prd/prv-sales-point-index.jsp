<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-biz-prd-prv-sales-point-index">
   <div class="tabbable tabbable-primary">
	<ul class="nav nav-pills">
		<li class="active"><a class="tab-default" data-toggle="tab" href="#tab-auto">列表查询</a></li>
		<li class="tools pull-right"><a class="btn default reload" href="javascript:;"><i class="fa fa-refresh"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade active in">
	<div class="row search-form-default">
		<div class="col-md-12">
			<form action="#" method="get" class="form-inline form-validation form-search-init form-biz-prd-prv-sales-point-index"
				data-grid-search=".grid-biz-prd-prv-sales-point-index">
				<div class="input-group">
		        <div class="form-group">
		          <s:hidden id="backupCityId" />
                  <s:select id="id_city" name="quePrvSalesPointParams.city" 
                             cssClass="form-control input-medium" list="{}"
                                placeholder="请选择地市" />
               </div>
             <%--   <div class="form-group">
                    <s:hidden id="backupAreaId" />
                    <s:select id="id_area" name="quePrvSalesPointParams.areaid" 
                               cssClass="form-control input-medium" list="{}"
                                placeholder="请选择业务区"  />
               </div> --%>
                <div class="form-group">
                     <s:select id="id_isvalid" name="quePrvSalesPointParams.isvalid" 
                                cssClass="form-control input-medium" list="#{0:'有效',1:'失效'}"
                                 placeholder="是否有效"  />
                </div>
                   
                <div class="form-group">
                     <s:select id="id_opcode" name="quePrvSalesPointParams.opcode" cssClass="form-control input-medium" list="{}"
                          placeholder="业务操作"  />
                </div>
                 <div class="form-group">
                     <s:select id="id_wtype" name="quePrvSalesPointParams.wtype" cssClass="form-control input-medium" list="#{0:'正式员工',1:'临时员工',2:'劳务派遣' }"
                          placeholder="用工类型" />
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
			<table class="grid-biz-prd-prv-sales-point-index"></table>
		</div>
	</div>
</div>
</div>
</div>
</div>
<script src="${base}/prd/prv-sales-point-index.js" />
<%@ include file="/common/ajax-footer.jsp"%>
