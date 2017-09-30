package com.duan.test;

import com.duan.entitly.User;
import com.duan.other.UserTable;
import com.duan.util.Utils;

public class UserController {

	private final UserTable userTable;

	public UserController() {
		this.userTable = new UserTable();
	}

	public User checkUser(User user) {
		String name = null;
		String password = null;

		if (user != null) {
			name = user.getName();
			password = user.getPassword();

			if (!Utils.isReal(name) || !Utils.isReal(password)) {
				return null;
			}
		}

		// User[] users = userTable.queryAll();
		User[] users = userTable.query(null, new String[] { UserTable.lABEL_NAME, UserTable.LABEL_PASSWORD },
				new String[] { name, password });
		if (users == null || users.length == 0) {
			return null;
		}

		for (User u : users) {
			String n = u.getName();
			String p = u.getPassword();
			if (name.equals(n) && password.equals(p)) {
				return u;
			}
		}

		return null;
	}

}
