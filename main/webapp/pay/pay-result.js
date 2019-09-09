function getParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return decodeURIComponent(r[2]);
	return "";
}
function back() {
	window.history.go(-1);
	window.payApp.close();
}

$(function() {
	var action = getParam("action");
	var code = getParam("code");
	var orderNo = getParam("orderNo");
	$('#orderNo').text(orderNo);
	$('#payDetail').text(getParam("payDetail"));
	var paystatus = action == "success";
	$('#payStatus').text(paystatus ? "支付成功" : "支付失败");

	if (code) {
		$('#tipDiv').show();
		$('#tipTitle').text(paystatus ? "提示：" : "错误提示：");
		$('#tipInfo').text(code);
	} else {
		$('#tipDiv').hide();
	}

	window.payApp.payResult(orderNo, paystatus, code);
})