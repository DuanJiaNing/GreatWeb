package com.duan.greatweb.controller.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		// PrintWriter out = response.getWriter();

		String method = request.getParameter("method");
		switch (method) {
		case "checkName": {
			String name = request.getParameter("name");
			OutputStream outS = response.getOutputStream();
			if (checkName(name)) {
				outS.write(("user name is usable -" + name).getBytes());
			} else {
				outS.write(("user name is already used -" + name).getBytes());
			}
			break;
		}
		default:
			break;
		}

		// String password = request.getParameter("password");
		// int age = Integer.valueOf(request.getParameter("age"));
		// String phone = request.getParameter("phone");
		// String email = request.getParameter("email");
		//
		// UserInfo info = new UserInfo(name, password, age, phone, email);
		// if (checkUser(info)) {
		//
		// }

	}

	private boolean checkName(String name) {
		return "admin".equals(name);
	}

	private boolean checkUser(UserInfo info) {
		return false;
	}

	private void out(PrintWriter out, String msg) {
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("<h4>" + msg + "</h4>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public static class UserInfo {
		private String name;
		private String password;
		private int age;
		private String phone;
		private String email;

		public UserInfo() {
		}

		public UserInfo(String name, String password, int age, String phone, String email) {
			super();
			this.name = name;
			this.password = password;
			this.age = age;
			this.phone = phone;
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public String toString() {
			return "UserInfo [name=" + name + ", password=" + password + ", age=" + age + ", phone=" + phone
					+ ", email=" + email + "]";
		}

	}

}
