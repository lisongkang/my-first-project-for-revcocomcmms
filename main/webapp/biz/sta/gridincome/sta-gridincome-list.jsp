<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-biz-sta-gridincome-sta-gridincome-list">
<div class="row search-form-default">
	<div class="col-md-12">
		<form action="#" method="get"
			class="form-inline form-validation form-biz-sta-gridincome-sta-gridincome-list"
			data-grid-search=".grid-biz-sta-gridincome-sta-gridincome-list">
			<div class="form-group">
                <s:select id="id_que_city" name="staGridincomeParamBo.city" cssClass="form-control input-medium" list="cityMap"  
                 placeholder="请选择地市..."/>
            </div>
            <div class="form-group">
                <s:select id="id_que_grid" name="staGridincomeParamBo.gridids" cssClass="form-control input-medium" list="{}"  multiple="true"
                 placeholder="请选择网格..."/>
            </div>

            <div class="form-group" id="id_que_statime_div">
                <input type="text" id="id_que_statime" name="que_statime" class="form-control input-medium input-daterangepicker"
                    placeholder="统计时段">
            </div>
  
            <button class="btn green" type="submmit" onclick="return StaGridincomeList.checkInput();">
                <i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
            </button>
            <button class="btn default hidden-inline-xs" type="button" onclick="StaGridincomeList.doReset();">
                <i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
            </button>
			
		</form>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<table class="grid-biz-sta-gridincome-sta-gridincome-list"></table>
	</div>
</div>
</div>
<script src="${base}/biz/sta/gridincome/sta-gridincome-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>