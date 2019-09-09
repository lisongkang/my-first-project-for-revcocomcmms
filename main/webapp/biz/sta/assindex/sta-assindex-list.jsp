<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="div-biz-sta-assindex-sta-assindex-list">
<div class="row search-form-default">
	<div class="col-md-12">
		<form action="#" method="get"
			class="form-inline form-validation form-biz-sta-assindex-sta-assindex-list"
			data-grid-search=".grid-biz-sta-assindex-sta-assindex-list">
			<div class="form-group">
                <s:select id="id_que_city" name="staAssindexParamBo.city" cssClass="form-control input-large" list="cityMap"  
                 placeholder="请选择地市..."/>
            </div>
            <div class="form-group">
                <s:select id="id_que_assparam" name="staAssindexParamBo.assparam" cssClass="form-control input-large" list="assparamMap" 
                 placeholder="请选择考核方案类型..."/>
            </div>
            <div class="form-group">
                <s:select id="id_que_ass" name="staAssindexParamBo.assid" cssClass="form-control input-large" list="{}" 
                 placeholder="请选择考核方案..."/>
            </div>
            <div class="form-group">
                <s:select id="id_que_grid" name="staAssindexParamBo.gridids" cssClass="form-control input-large" list="{}"  multiple="true"
                 placeholder="请选择网格..."/>
            </div>

            <div class="form-group" id="id_que_statime_div">
                <input type="text" id="id_que_statime" name="que_statime" class="form-control input-large input-daterangepicker"
                    placeholder="统计时段">
            </div>
  
            <button class="btn green" type="submmit" onclick="return StaAssindexList.checkInput();">
                <i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
            </button>
            <button class="btn default hidden-inline-xs" type="button" onclick="StaAssindexList.doReset();">
                <i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
            </button>
			
		</form>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<table class="grid-biz-sta-assindex-sta-assindex-list"></table>
	</div>
</div>
</div>
<script src="${base}/biz/sta/assindex/sta-assindex-list.js" />
<%@ include file="/common/ajax-footer.jsp"%>