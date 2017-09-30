package com.duan.dao;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.duan.dao.db.DBHelper;
import com.duan.dao.db.DataBase;
import com.duan.entitly.User;

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
	public User queryById(int id) {
		User[] users = dataBase.query(User.class, new String[] { "id" }, new String[] { id + "" });
		return (users != null && users.length >= 1) ? users[0] : null;
	}

	@Override
	public User queryByName(String name) {
		// TODO
		User[] users = dataBase.query(User.class, new String[] { "name" }, new String[] { name });
		return users != null && users.length >= 1 ? users[0] : null;
	}

	@Override
	public List<User> queryAll(int state) {
		return Arrays.asList(dataBase.queryAll(User.class));
	}

	@Override
	public int updateById(int id, String name, String password, int state, int age) {
		return dataBase.update(new User(id, name, password, state, age));
	}

	@Override
	public int deleteById(int id) {
		return dataBase.delete(User.class, id);
	}

}
