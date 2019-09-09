<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- ECharts单文件引入 -->
<script src="${base}/assets/extras/echarts/dist/echarts.js"></script>
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: '${base}/assets/extras/echarts/dist'
        }
    });
</script>
    