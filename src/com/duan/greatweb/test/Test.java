package com.duan.greatweb.test;

import java.util.List;

import com.duan.greatweb.dao.UserDao;
import com.duan.greatweb.dao.UserDaoImpl;
import com.duan.greatweb.entitly.User;
import com.duan.greatweb.util.Utils;

public class Test {

	public static void main(String[] args) {
		UserDao uDao = new UserDaoImpl();
		List<User> users = uDao.queryAll(0);
		for (User u : users) {
			Utils.log(u.toString());
		}
	}
}
