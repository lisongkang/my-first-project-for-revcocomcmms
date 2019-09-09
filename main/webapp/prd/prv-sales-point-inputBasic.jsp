<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-biz-prd-prv-sales-point-inputBasic">
<form class="form-horizontal form-bordered form-label-stripped form-validation form-biz-prd-prv-sales-point-inputBasic"
	action="${base}/prd/prv-sales-point!doSave" method="post" data-editrulesurl="${base}/prd/prv-sales-point!buildValidateRules" >
	<s:hidden name="id" />
	<s:hidden name="version" />
	<s:token />
	<div class="form-body">
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
		                <label class="control-label">地市</label>
						<div class="controls">
			                <s:select id="id_city" name="city"  list="{}" required="true" placeholder="请选择地市"   />
						</div>
				</div>
            </div>
        </div>
       <%--  <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务区</label>
					<div class="controls">
		                 <s:select id="id_area" name=""  list="{}"  multiple="true" required="true" placeholder="请选择业务区"  />
					</div>
				</div>
            </div>
        </div> --%>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">业务操作</label>
					<div class="controls">
		                   <s:select id="id_opcode" name="opcodename" required="true"  list="{}"
                      placeholder="业务操作"  />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">商品名称</label>
					<div class="controls">
		              <div class="input-icon right">
							<i class="fa fa-ellipsis-horizontal fa-select-obj"></i>
							<s:textfield name="salesname" />
							<s:hidden id="id_salesid" name="salesid" />
						</div>
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">用工类型</label>
					<div class="controls">
		                 <s:select id="id_wtype" name="wtype" required="true"  list="#{0:'正式员工',1:'临时员工',2:'劳务派遣' }"
                      placeholder="业务操作"  />
					</div>
				</div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
				<div class="form-group">
					<label class="control-label">销售积分</label>
					<div class="controls">
		                <s:textfield id="id_point" name="points" required="true" placeholder="请输入正数" />
					</div>
				</div>
            </div>
        </div>
	</div>
	
	<div class="form-actions right">
		<button class="btn blue btn_salespointnew" type="button" data-grid-reload=".grid-biz-prd-prv-sales-point-index">
			<i class="fa fa-check"></i> 保存
		</button>
		<button class="btn default btn-cancel" type="button">取消</button>
	</div>
</form>

<!-- 信息删除确认 -->  

<div class="modal fade" id="showTipModel">  
  <div class="modal-dialog">  
    <div class="modal-content message_align">  
      <div class="modal-header">  
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>  
        <h4 class="modal-title">提示信息</h4>  
      </div>  
      <div class="modal-body">  
          
      </div>  
      <div class="modal-footer">  
         <input type="hidden" id="url"/>  
         <button type="button" class="btn btn-default " data-dismiss="modal">取消</button>  
         <a  onclick="PrvSalesPointInputBasic.urlSubmit()" class="btn btn-success" data-dismiss="modal">确定</a>  
      </div>  
    </div><!-- /.modal-content -->  
  </div><!-- /.modal-dialog -->  
</div><!-- /.modal -->  
</div>
<script src="${base}/prd/prv-sales-point-inputBasic.js" />
<%@ include file="/common/ajax-footer.jsp"%>