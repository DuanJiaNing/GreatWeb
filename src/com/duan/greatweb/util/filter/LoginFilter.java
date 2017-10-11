package com.duan.greatweb.util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.duan.greatweb.dao.UserDao;
import com.duan.greatweb.dao.UserDaoImpl;
import com.duan.greatweb.entitly.User;
import com.duan.greatweb.util.Utils;

public class LoginFilter extends RootFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(request, response, chain);

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String name = request.getParameter("username");
		String password = request.getParameter("password");
		boolean rememberMe = "on".equals(request.getParameter("rememberMe"));

		String info = "输入格式错误！";
		if (Utils.isReal(name) && Utils.isReal(password)) {

			UserDao userDao = new UserDaoImpl();
			User user = userDao.query(name);
			if (user != null ) {
				// 验证成功 将用户保存到 session 和 cookie 中
				HttpSession session = req.getSession();
				session.setMaxInactiveInterval(60 * 30); // 30分钟
				session.setAttribute("user", user);
				session.setAttribute("rememberMe", rememberMe);

				chain.doFilter(request, response);

				// 将用户信息保存到 cookie 中
				Cookie cookie = new Cookie("user", user.getId() + "");
				Cookie cookieRememberMe = new Cookie("rememberMe", rememberMe ? "true" : "false");

				cookie.setMaxAge(60 * 30); // 30分钟
				cookieRememberMe.setMaxAge(60 * 30);

				res.addCookie(cookie);
				res.addCookie(cookieRememberMe);
				return;
			} else {// 验证失败
				info = "用户不存在或密码错误！";
			}
		}

		request.getRequestDispatcher("/note-os/login.jsp?info=" + info).forward(request, response);

	}

}
