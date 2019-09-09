<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/echartconfig.jsp"%>

<div class="div-biz-sta-gridincome-sta-gridincome-chart">
<div class="row search-form-default">
    <div class="col-md-12">
        <form method="get" class="form-inline form-validation form-biz-sta-gridincome-sta-gridincome-chart" action="#">
            <div class="form-group">
                <s:select id="id_que_city" name="que_city" cssClass="form-control input-medium" list="cityMap"  
                 placeholder="请选择地市..."/>
            </div>
            <div class="form-group">
                <s:select id="id_que_grid" name="que_grid" cssClass="form-control input-medium" list="{}"  multiple="true"
                 placeholder="请选择网格..."/>
            </div>
            <div class="form-group" style="display:none">
                <s:select id="id_que_statype" name="que_statype" cssClass="form-control input-medium" 
                list="#{'1':'按天统计','2':'按月统计','3':'按年统计'}" onchange="StaGridincomeChart.changeStatype();"
                 placeholder="请选择统计方式..."/>
            </div>
            <div class="form-group" id="id_que_statime_div">
                <input type="text" id="id_que_statime" name="statime" class="form-control input-medium input-daterangepicker"
                    placeholder="统计时段">
            </div>
  
            <button class="btn green" type="button" onclick="StaGridincomeChart.doQuery();">
                <i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
            </button>
            <button class="btn default hidden-inline-xs" type="button" onclick="StaGridincomeChart.doReset();">
                <i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
            </button>
        </form>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="id_echart_main" style="height:400px"></div>
    </div>
</div>
</div>
<script src="${base}/biz/sta/gridincome/sta-gridincome-chart.js" />
<%@ include file="/common/ajax-footer.jsp"%>
