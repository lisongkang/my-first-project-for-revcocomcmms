<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/echartconfig.jsp" %>
<div class="div-biz-sta-marketing-statistic-list">
    <div class="row search-form-default">
        <div class="col-md-12">
            <form action="#" method="get"
                  class="form-inline form-validation form-biz-sta-marketing-statistic-list"
                  data-grid-search=".grid-biz-sta-marketing-statistic-list">


                <%--<div class="form-group">--%>
                <%--<s:textfield id="customer" name="staMarketStatisticParamBO.gridCode"--%>
                <%--cssClass="form-control input-medium" placeholder="请输入客户名称..."--%>
                <%--maxlength="20" />--%>
                <%--</div>--%>

                <div class="form-group">
                    <s:select id="id_que_city" name="staMarketStatisticParamBO.city"
                              cssClass="form-control input-medium" list="cityMap"
                              placeholder="请选择地市..."/>
                </div>



                <div class="form-group">
                    <s:select id="id_que_grid" name="staMarketStatisticParamBO.gridlist"
                              cssClass="form-control input-medium" list="{}"
                              placeholder="请选择网格..." />
                </div>
                    <div class="form-group">
                        <s:select id="orderStatus"
                                  name="staMarketStatisticParamBO.orderStatuss"
                                  cssClass="form-control input-medium" list="{}" multiple="true"
                                  placeholder="请选择订单状态..." />
                    </div>
                    <div class="form-group">
                        <s:select id="payStatus"
                          name="staMarketStatisticParamBO.payStatuss"
                          cssClass="form-control input-medium" list="{}" multiple="true"
                          placeholder="请选择支付状态..." />
                    </div>

                <div class="form-group" id="timeRangeDiv">
                    <input type="text" id="timeRange"
                           name="staMarketStatisticParamBO.timeRange"
                           class="form-control input-medium input-daterangepicker"
                           placeholder="统计时段">
                </div>


                <button class="btn green" type="submmit"
                        onclick="return StaMarketStatisticList.searchFormObj.checkInput();"
                >
                    <i class="m-icon-swapright m-icon-white"></i>&nbsp; 查&nbsp;询
                </button>
                <button class="btn default hidden-inline-xs" type="button"
                        onclick="StaMarketStatisticList.searchFormObj.reset();">
                    <i class="fa fa-undo"></i>&nbsp; 重&nbsp;置
                </button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="grid-biz-sta-marketing-statistic-list"></table>
        </div>
    </div>
</div>
<script
        src="${base}/biz/sta/marketingstatistic/sta-marketing-statistic-list.js"/>

<%@ include file="/common/ajax-footer.jsp" %>