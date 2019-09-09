
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" id="id_togridTask" href="${base}/biz/ass/togrid/ass-index-togrid!togridTask?storeId=<s:property value="model.id"/>&grids=<s:property value="model.grids"/>">任务下达</a></li>
        <li><a data-toggle="tab" href="${base}/biz/ass/togrid/ass-index-togrid!taskTogridList?storeId=<s:property value="model.id"/>&grids=<s:property value="model.grids"/>">已下达列表</a></li>
        <li class="tools pull-right"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
    </ul>
</div>
<script src="${base}/biz/ass/togrid/ass-index-togrid-viewTabs.js" />
<%@ include file="/common/ajax-footer.jsp"%>

