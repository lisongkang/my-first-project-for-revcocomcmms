StaGridincomeTabs = ({
	init : function(){
		//由于不能自动加载默认的tab,模拟下点击事件
		$("#id_chart_tab").click();
		
//		   $(".form-biz-sta-gridincodme-tabs").data("formOptions",{
//		    	bindEvents : function(){
//		    		var $form = $(this);
//		    		
//		    	}
//		    });
	}
});


$(function() {
	
	StaGridincomeTabs.init();

});
