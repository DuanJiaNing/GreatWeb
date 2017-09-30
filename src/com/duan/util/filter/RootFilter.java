package com.duan.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.duan.util.Utils;

public class RootFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Utils.log("初始化过滤器："+this.getClass().getName());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		Utils.log("过滤："+this.getClass().getName());
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Utils.log("销毁过滤器："+this.getClass().getName());
	}

}
