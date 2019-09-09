

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
    <ul class="nav nav-tabs">
        <!-- 
        <li class="active"><a data-toggle="tab" id = "id_chart_tab" href="${base}/biz/sta/gridincome/sta-gridincome!forward?_to_=chart">汇总图表</a></li>
        <li><a data-toggle="tab" href="${base}/biz/sta/gridincome/sta-gridincome!forward?_to_=list">汇总列表</a></li>
        <li><a data-toggle="tab" href="${base}/biz/sta/gridincome/sta-gridincome!forward?_to_=detailList">明细列表</a></li>
         -->
        <li class="active"><a data-toggle="tab" id = "id_chart_tab" href="${base}/biz/sta/gridincome/sta-gridincome!forward?_to_=chart">统计图表</a></li>
        <li><a data-toggle="tab" href="${base}/biz/sta/gridincome/sta-gridincome!forward?_to_=list">统计列表</a></li>
         
        <li class="tools pull-right"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
    </ul>
</div>
<script src="${base}/biz/sta/gridincome/sta-gridincome-tabs.js" />
<%@ include file="/common/ajax-footer.jsp"%>

