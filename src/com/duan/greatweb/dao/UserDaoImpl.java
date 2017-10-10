package com.duan.greatweb.dao;

import java.util.Arrays;
import java.util.List;

import com.duan.greatweb.dao.db.DBHelper;
import com.duan.greatweb.dao.db.DataBase;
import com.duan.greatweb.entitly.User;

public class UserDaoImpl implements UserDao {

	private final DataBase dataBase;

	public UserDaoImpl() {
		dataBase = DBHelper.getDataBase();
	}

	@Override
	public int addUser(User user) {
		return dataBase.insert(user);
	}

	@Override
	public User query(int userId) {
		User[] users = dataBase.query(User.class, new String[] { "id" }, new String[] { userId + "" });
		return (users != null && users.length >= 1) ? users[0] : null;
	}

	@Override
	public User query(String name) {
		// TODO
		User[] users = dataBase.query(User.class, new String[] { "name" }, new String[] { name });
		return users != null && users.length >= 1 ? users[0] : null;
	}

	@Override
	public List<User> queryAll(int state) {
		return Arrays.asList(dataBase.queryAll(User.class));
	}

	@Override
	public int update(int userId, String name, String password, int state, int age) {
		return dataBase.update(new User(userId, name, password, state, age));
	}

	@Override
	public int delete(int userId) {
		return dataBase.delete(User.class, userId);
	}

}
