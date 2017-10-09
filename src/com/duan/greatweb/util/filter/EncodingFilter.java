package com.duan.greatweb.util.filter;

import com.duan.greatweb.util.Utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter extends RootFilter {

	private String characterEncoding;
	private boolean enable;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		characterEncoding = filterConfig.getInitParameter("charset");
		enable = "true".equalsIgnoreCase(filterConfig.getInitParameter("enable"));
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(request, response, chain);

		if (enable && characterEncoding != null) {
			response.setContentType("text/html");
			request.setCharacterEncoding(characterEncoding);
			response.setCharacterEncoding(characterEncoding);
			Utils.log(this.getClass().getName()+" "+"charset is "+characterEncoding);
		}

		chain.doFilter(request, response);
	}

}
