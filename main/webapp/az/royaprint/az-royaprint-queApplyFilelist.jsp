<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="row">
    <s:hidden name="id" />
    <s:hidden name="version" />
    <s:token />
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <div class="controls">
                    <div style="text-align: center;font-size: 18px">小额工程单(<s:property value="model.bizApplication.proname"/>)验收资料表</div>
                    <table width="99%" class="c-table" border="1" cellspacing="0" cellpadding="0">
                        <tbody>
                            <tr>
                                <td width="20">项目编号 :</td>
                                <td colspan="6"><s:property value="model.bizApplication.pronum" /></td>
                            </tr>
                            <tr>
                                <td width="80">施工地址 :</td>
                                <td colspan="6"><s:property value="model.bizApplication.buildaddr"/></td>
                            </tr>
                        </tbody>
                    </table>
                    <table width="99%" class="c-table" border="1" cellspacing="0" cellpadding="0">
                        <thead>
                        <tr>
                            <th align="left">序号</th>
                            <th align="left">验收资料名称</th>
                            <th align="left">下载</th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="model.accfileidsBOLis" status="status" var="answer">
                            <tr>
                                <td><s:property value="#status.index+1" /></td>
                                <td><s:property value="#answer.realfilename"/></td>
                                <td>
                                    <a href="${base}/az/royaprint/az-royaprint!downTemplate?templateFileName=<s:property value='#answer.realfilename'/>&filepath=<s:property value='#answer.filepath'/>">下载</a>
                                </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</div>
<script type="text/javascript">
    var sid = '<s:property value="#request.proid" />';
</script>
<script type="text/css">

</script>
<style media="print" type="text/css">
    @page {
        size: auto;  /* auto is the initial value */
        margin: 0mm; /* this affects the margin in the printer settings */
    }
</style>
<script type="text/javascript" language="javascript">
    function download(realfilename,filepath){
        window.location.href=WEB_ROOT + '/az/royaprint/az-royaprint!downTemplate?templateFileName=' + realfilename + '&filepath=' + filepath;
    }
</script>
<%@ include file="/common/ajax-footer.jsp"%>
