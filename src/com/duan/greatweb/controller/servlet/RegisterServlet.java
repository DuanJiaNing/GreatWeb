package com.duan.greatweb.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duan.greatweb.dao.UserDao;
import com.duan.greatweb.dao.UserDaoImpl;
import com.duan.greatweb.entitly.User;
import com.duan.greatweb.util.Utils;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String method = request.getParameter("method");

		UserDao uDao = new UserDaoImpl();
		String name = request.getParameter("username");
		if ("checkNameUsed".equals(method)) { // 检查用户名是否已经被使用

			User user = uDao.query(name);
			boolean used = false;
			if (user != null) {
				used = true;
			}
			response.getOutputStream().write((used ? "true" : "false").getBytes());

		} else if (method == null) { // 注册

			String password = request.getParameter("password");
			String strAge = request.getParameter("age");
			int age = strAge == null ? 18 : Integer.valueOf(strAge);

			User user = new User(name, password, 0, age);
			User re = uDao.query(name);
			Utils.log(user.toString());

			boolean suc = false;
			String info = "用户已存在";
			if (re == null) { // 用户不存在
				// 用户未存在。添加到数据库
				int state = uDao.addUser(user);
				if (state == UserDao.STATE_ADD_SUCCESS) { // 注册成功
					info = "成功注册，现在可以登陆";
					request.setAttribute("newUser", user);
					suc = true;
				} else {
					info = "注册失败，请检查用户名、密码和年龄是否规范";
				}

			}

			request.setAttribute("info", info);
			request.getRequestDispatcher(suc ? "login.jsp" : "register.jsp").forward(request, response);
		}
	}

}
