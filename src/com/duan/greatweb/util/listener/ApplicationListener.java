package com.duan.greatweb.util.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.duan.greatweb.util.Utils;

public class ApplicationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		Utils.log("application 监听创建");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
//		Utils.log("application 监听创建");
	}

}
