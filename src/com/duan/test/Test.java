package com.duan.test;

import java.util.List;

import com.duan.dao.UserDao;
import com.duan.dao.UserDaoImpl;
import com.duan.entitly.User;
import com.duan.other.UserTable;
import com.duan.util.Utils;

public class Test {

	public static void main(String[] args) {
		UserDao uDao = new UserDaoImpl();
		List<User> users = uDao.queryAll(0);
		for (User u : users) {
			Utils.log(u.toString());
		}
	}
}
