<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="tabbable tabbable-custom tabbable-secondary">
    <%-- 
    <ul>
        <li><a href="ass-index-topatch!view?id=<s:property value='#parameters.id'/>">
        <span>基本信息</span>
        </a></li>
        <%-- 去掉注释添加功能Tab
        <li><a href="ass-index-topatch!forward?_to_=TODO&id=<s:property value='#parameters.id'/>">
        <span>TODO关联</span>
        </a></li>
    </ul>--%>
    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" id="id_topatchTask" href="${base}/biz/ass/topatch/ass-index-topatch!topatchTask?storeId=<s:property value="model.id"/>&grids=<s:property value="model.grids"/>">任务下达</a></li>
        <li><a data-toggle="tab" href="${base}/biz/ass/topatch/ass-index-topatch!taskTopatchList?storeId=<s:property value="model.id"/>&grids=<s:property value="model.grids"/>">已下达列表</a></li>
        <li class="tools pull-right"><a href="javascript:;" class="btn default reload"><i class="fa fa-refresh"></i></a></li>
    </ul>
</div>
<script src="${base}/biz/ass/topatch/ass-index-topatch-viewTabs.js" />
<%@ include file="/common/ajax-footer.jsp"%>