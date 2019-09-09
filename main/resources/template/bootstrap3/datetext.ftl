<#--
/*
 * 基于text扩展日期选取组件
 */
-->

<div data-date-viewmode="years" data-date-format="yyyy-mm-dd" 
	class="input-group ${parameters.dynamicAttributes["dateInputClass"]?html} date date-picker">
<#include "/${parameters.templateDir}/simple/text.ftl" />
<span class="input-group-btn">
		<button type="button" class="btn default date-set">
			<i class="fa fa-calendar"></i>
		</button>
	</span>
</div>