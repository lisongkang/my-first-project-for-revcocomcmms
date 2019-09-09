package com.maywide.biz.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.maywide.core.util.SignUtil;

/**
 * 菜单交互Servlet
 * Servlet implementation class McrCoreServlet
 */
public class McrCoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() {
		
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public McrCoreServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 安全校验
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature,
				timestamp, nonce)) {
			System.out.println("校验成功。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * 信息交互，以POST形式请求
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 将请求响应的编码均设置为UTF-8(防止中午乱码)
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 调用核心业务类接收消息、处理消息
		String respMessage = "";//McrService.processRequest(request);
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

}
