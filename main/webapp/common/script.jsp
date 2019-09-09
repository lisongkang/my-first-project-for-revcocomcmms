<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="loadingDiv"/>
<script src="${base}/assets/plugins/ace/js/jquery-2.0.3.min.js"></script>
<script src="${base}/assets/plugins/ace/js/bootstrap.min.js"></script>
<script src="${base}/assets/plugins/ace/js/typeahead-bs2.min.js"></script>
<script src="${base}/assets/plugins/ace/js/bootbox.min.js"></script>
<script src="${base}/assets/plugins/ace/js/ace-elements.min.js"></script>
<script src="${base}/assets/plugins/ace/js/ace.min.js"></script>
<script src="${base}/assets/plugins/ace/js/fuelux.tree.min.js"></script>
<script src="${base}/assets/plugins/ace/js/spin.min.js"></script>
<script src="${base}/assets/plugins/ace/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="${base}/assets/plugins/ace/js/jquery.ui.touch-punch.min.js"></script>
<script src="${base}/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="${base}/assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>

<script>
	var opts = {
	    //innerImage: {url: '../img/logo.png', width: 56, height: 56 }, //内部图片            
	    lines: 13, // 花瓣数目
	    length: 10, // 花瓣长度
	    width: 4, // 花瓣宽度
	    radius: 10, // 花瓣距中心半径
	    corners: 1, // 花瓣圆滑度 (0-1)
	    rotate: 0, // 花瓣旋转角度
	    direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
	    color: ' #5B5B5B', // 花瓣颜色
	    speed: 1, // 花瓣旋转速度
	    trail: 60, // 花瓣旋转时的拖影(百分比)
	    shadow: false, // 花瓣是否显示阴影
	    hwaccel: false, //spinner 是否启用硬件加速及高速旋转            
	    className: 'spinner', // spinner css 样式名称
	    zIndex: 2e9, // spinner的z轴 (默认是2000000000)
	    top: 'auto', // spinner 相对父容器Top定位 单位 px
	    left: 'auto', // spinner 相对父容器Left定位 单位 px
	    position: 'relative', // element position
	    progress: true,      // show progress tracker
	    progressTop: 0,       // offset top for progress tracker
	    progressLeft: 0       // offset left for progress tracker
	};

	var spinner = new Spinner(opts);
	var target = $("#loadingDiv").get(0);
</script>
