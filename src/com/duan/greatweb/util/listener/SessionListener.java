package com.duan.greatweb.util.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.duan.greatweb.util.Utils;

public class SessionListener implements HttpSessionListener {

	private int onlineNum = 0;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		onlineNum++;
		calcOnLineNum(se.getSession());
//		Utils.log("session 监听创建");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		onlineNum--;
		calcOnLineNum(se.getSession());
//		Utils.log("session 监听销毁");
	}

	private void calcOnLineNum(HttpSession session) {
		session.setAttribute("onlineNum", onlineNum);
	}

}
