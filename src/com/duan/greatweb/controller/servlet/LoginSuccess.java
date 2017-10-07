package com.duan.greatweb.controller.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.duan.greatweb.entitly.User;
import com.duan.greatweb.util.Utils;

public class LoginSuccess extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		Object count = session.getAttribute("success_login_user_count");
		if (count == null) { // 第一个用户
			session.setAttribute("success_login_user_count", 1);
			count = 1;
		} else {
			session.setAttribute("success_login_user_count", (int) count + 1);
		}

		StringBuilder builder = new StringBuilder();
		Utils.append(builder, "session 统计", "第" + count + "个成功登陆用户");
		Utils.append(builder, "用户信息", ((User) session.getAttribute("user")).toString());

		out(out, builder.toString());
	}

	public void out(PrintWriter out, String msg) {
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("<h3>登录成功 " + msg + "</h3>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
