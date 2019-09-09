<%@ tag pageEncoding="utf-8"%>
<%@ attribute name="gcode" type="java.lang.String" required="true"%>
<%@ attribute name="mcode" type="java.lang.String" required="true"%>
<%@ attribute name="seperator" type="java.lang.String" required="false"%>
<%@ attribute name="index" type="java.lang.Integer" required="false"%>
<%@ tag import="com.maywide.core.web.tag.StaticData"%>
<%
	try {
		if (null == index)
			index = 1;
		String mname = "";

		if (mcode != null && !mcode.equals("") && !"*".equals(mcode)) {
			if (null != seperator && seperator.trim().length() > 0) {
				mname = StaticData.getInstance().translate(gcode,
						mcode, seperator, index);
			} else {
				mname = StaticData.getInstance().translate(gcode,
						mcode, index);
			}
		}
		if ((mname == null || mname.equals("")) && mcode.equals("*"))
			mname = "所有";
		
		if(mname == null){ out.print("");
		return;
		
		}
		out.print(mname);
	} catch (Exception e) {
		out.print("");
	}
%>