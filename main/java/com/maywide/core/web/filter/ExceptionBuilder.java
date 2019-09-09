package com.maywide.core.web.filter;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成统一异常信息页面
 * @author xiaobai
 *
 */
public class ExceptionBuilder {
	private static String DEFUALT_HTML = null;
	private static ExceptionBuilder instance = null;

	private static final Logger logger = LoggerFactory
			.getLogger(ExceptionBuilder.class);

	private ExceptionBuilder() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<HTML><HEAD><TITLE>JBossWeb/2.0.1.GA - Error report</TITLE>                                                                 ");

		buffer.append("<STYLE>H1 {                                                                                                                 ");

		buffer.append("\tBACKGROUND-COLOR: #525d76; FONT-FAMILY: Tahoma,Arial,sans-serif; COLOR: white; FONT-SIZE: 22px                             ");

		buffer.append("}                                                                                                                           ");

		buffer.append("H2 {                                                                                                                        ");

		buffer.append("\tBACKGROUND-COLOR: #525d76; FONT-FAMILY: Tahoma,Arial,sans-serif; COLOR: white; FONT-SIZE: 16px                             ");

		buffer.append("}                                                                                                                           ");

		buffer.append("H3 {                                                                                                                        ");

		buffer.append("\tBACKGROUND-COLOR: #525d76; FONT-FAMILY: Tahoma,Arial,sans-serif; COLOR: white; FONT-SIZE: 14px                             ");

		buffer.append("}                                                                                                                           ");

		buffer.append("BODY {                                                                                                                      ");

		buffer.append("\tBACKGROUND-COLOR: white; FONT-FAMILY: Tahoma,Arial,sans-serif; COLOR: black                                                ");

		buffer.append("}                                                                                                                           ");

		buffer.append("B {                                                                                                                         ");

		buffer.append("\tBACKGROUND-COLOR: #525d76; FONT-FAMILY: Tahoma,Arial,sans-serif; COLOR: white                                              ");

		buffer.append("}                                                                                                                           ");

		buffer.append("P {                                                                                                                         ");

		buffer.append("\tFONT-FAMILY: Tahoma,Arial,sans-serif; BACKGROUND: white; COLOR: black; FONT-SIZE: 12px                                     ");

		buffer.append("}                                                                                                                           ");

		buffer.append("A {                                                                                                                         ");

		buffer.append("\tCOLOR: black                                                                                                               ");

		buffer.append("}                                                                                                                           ");

		buffer.append("A.name {                                                                                                                    ");

		buffer.append("\tCOLOR: black                                                                                                               ");

		buffer.append("}                                                                                                                           ");

		buffer.append("HR {                                                                                                                        ");

		buffer.append("\tCOLOR: #525d76                                                                                                             ");

		buffer.append("}                                                                                                                           ");

		buffer.append("</STYLE>                                                                                                                    ");

		buffer.append("</HEAD>                                                                                                                     ");

		buffer.append("<BODY>                                                                                                                      ");

		buffer.append("<H1>HTTP Status 500 - </H1>                                                                                                 ");

		buffer.append("<HR SIZE=1 noShade>                                                                                                         ");

		buffer.append("<P><B>type</B> Exception report</P>                                                                                         ");

		buffer.append("<P><B>message</B> <U></U></P>                                                                                               ");

		buffer.append("<P><B>description</B> <U>The server encountered an internal error () that prevented it from fulfilling this request.</U></P>");

		buffer.append("<P><B>exception</B> <PRE>%{exception}                                                                                                   ");

		buffer.append("</PRE>                                                                                                                      ");

		buffer.append("<P></P>                                                                                                                     ");

		buffer.append("<P><B>note</B> <U>The full stack trace of the root cause is available in the JBossWeb/2.0.1.GA logs.</U></P>                ");

		buffer.append("<HR SIZE=1 noShade>                                                                                                         ");

		buffer.append("</BODY></HTML>                                                                                    ");

		DEFUALT_HTML = buffer.toString();
	}

	private static ExceptionBuilder getInstance() {
		if (instance == null)
			instance = new ExceptionBuilder();
		return instance;
	}

	public static String build(Exception e) {
		getInstance();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(out);
		e.printStackTrace(stream);
		String error = new String(out.toByteArray());
		logger.error(error);
		return DEFUALT_HTML.replace("%{exception}", error + "\n");
	}
}