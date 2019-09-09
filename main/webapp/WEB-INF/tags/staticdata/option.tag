<%@ tag pageEncoding="UTF-8"  %>
<%@tag import="java.util.List"%>
<%@tag import="java.util.Iterator"%>
<%@tag import="java.util.Arrays"%>
<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@tag import="com.maywide.core.util.PubUtil"%>
<%@tag import="com.maywide.biz.system.entity.PrvSysparam"%>
<%@tag import="com.maywide.core.web.tag.StaticData"%>
<%@tag import="com.maywide.core.web.tag.DefaultData"%>

<%@ attribute name="gcode" type="java.lang.String" required="true"  %>
<%@ attribute name="scode" type="java.lang.String" required="false"  %>
<%@ attribute name="link" type="java.lang.Boolean" required="false"  %>
<%@ attribute name="plzSelect" type="java.lang.Boolean" required="false"  %>
<%@ attribute name="all" type="java.lang.Boolean" required="false"  %>
<%@ attribute name="filter" type="java.lang.String" required="false"  %>
<%@ attribute name="contain" type="java.lang.String" required="false"  %>
<%@ attribute name="value" type="java.lang.String" required="false"  %>
<%@ attribute name="multiflag" type="java.lang.Boolean" required="false"  %>
<%@ attribute name="defaultname" type="java.lang.String" required="false"  %>

<%
	try {
			
			if(value == null && defaultname != null){
				value = DefaultData.getInstance().getData(defaultname);
			}
			if(link == null)
				link = false;
			if(plzSelect == null)
				plzSelect = false;
			if(all == null)
				all = false;
			if (plzSelect) {
				out.print("<option value=''>--请选择--</option>");
			}
			List list = null;
			if(scode != null){
				list = StaticData.getInstance().getSubData(gcode, scode);
			} else {
				list = StaticData.getInstance().getData(gcode);
			}
			
			List valueList = null;
			if(null!=multiflag && multiflag && !PubUtil.isEmpty(value)){
				valueList = Arrays.asList(PubUtil.split(value, ","));
			}

			if (all) {
				if((value!=null && value.equals("*")) 
						|| ((multiflag!=null && multiflag && valueList!=null && valueList.contains("*")))){
					// 单选、多选 下拉框，将*号默认选上
					out.print("<option selected value='*'>所有</option>");
				}else{
					out.print("<option value='*'>所有</option>");
				}
			}
			String[] filters = null;
			List filterList = null;
			if (!PubUtil.isEmpty(filter)) {
				filters = PubUtil.split(filter, ",");
				filterList = Arrays.asList(filters);
			}

			String[] contains = null;
			List containList = null;
			if (!StringUtils.isEmpty(contain) && contain.indexOf("*") < 0) {
				contains = PubUtil.split(contain, "~");
				containList = Arrays.asList(contains);
			}

			if (list != null) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					PrvSysparam item = (PrvSysparam) iter.next();
					// 过滤掉filter指定的编号
					if (filterList != null) {
						if (filterList.contains(item.getMcode()))
							continue;
					}
					if (containList != null) {
						if (!containList.contains(item.getMcode()))
							continue;
					}
					String sname = "";
					if (link) {
						sname = item.getMcode() + " - " + item.getMname();
					} else {
						sname = item.getMname();
					}
					if(multiflag!=null && multiflag){// 多选下拉框
						boolean isMutiValue = false;
						if(valueList != null){
							if(valueList.contains(item.getMcode())){
								isMutiValue = true;
							}
						}
						if(isMutiValue){
							out.print("<option  selected value='" + item.getMcode()
									+ "'>" + sname + "</option>");
						}else{
							out.print("<option value='" + item.getMcode()
									+ "'>" + sname + "</option>");
						}
					}else{// 单选下拉框
						if(value!=null && item.getMcode().equals(value)){
							out.print("<option  selected value='" + item.getMcode()
									+ "'>" + sname + "</option>");
						}else
							out.print("<option value='" + item.getMcode()
												+ "'>" + sname + "</option>");
					}
				}
			}
		} catch (Exception ex) {
			out.print("");
		}
%>