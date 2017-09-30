package com.duan.other;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.duan.entitly.User;
import com.duan.util.Utils;

public class UserTable extends DatabaseTable<User> {

	public static final String lABEL_ID = "id";
	public static final String lABEL_NAME = "name";
	public static final String LABEL_PASSWORD = "password";
	public static final String LABEL_STATE = "state";

	@Override
	public User[] query(String sql) {
		if (!Utils.isReal(sql)) {
			return null;
		}

		PreparedStatement stat = null;
		ResultSet set = null;
		List<User> users = new ArrayList<User>();

		try {
			// 使结果集可滚动，方便获取结果集大小
			stat = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			set = stat.executeQuery();

			Utils.log(sql);

			// 获取大小
			int size = getResultSetSize(set);
			if (size <= 0) {
				return null;
			}

			while (set.next()) {
				User user = new User();
				user.setId(set.getInt(lABEL_ID));
				user.setName(set.getString(lABEL_NAME));
				user.setPassword(set.getString(LABEL_PASSWORD));
				user.setState(set.getInt(LABEL_STATE));

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Utils.closeAutoCloseable(set);
			Utils.closeAutoCloseable(stat);
		}

		return users.toArray(new User[1]);

	}

	@Override
	public int insert(User... t) {
		if (!Utils.isReal(getTable()) || t == null || t.length == 0) {
			return -1;
		}

		PreparedStatement stat = null;

		StringBuilder valueBuilder = new StringBuilder();
		// FIXME sql 注入？？
		for (User user : t) {
			valueBuilder.append("(").append("DEFAULT,").append("'").append(user.getName()).append("'").append(",")
					.append("'").append(user.getPassword()).append("'").append(",").append(user.getState()).append(")")
					.append(",");
		}

		String builder = valueBuilder.toString();
		String value = builder.substring(0, builder.length() - 1);
		String sql = "insert into " + getTable() + "(" + lABEL_ID + "," + lABEL_NAME + "," + LABEL_PASSWORD + ","
				+ LABEL_STATE + ") values" + value;
		Utils.log(sql);

		try {

			stat = conn.prepareStatement(sql);
			return stat.executeUpdate();

		} catch (SQLException e) {
			// com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException:
			// Duplicate entry 'A' for key 'name'
			e.printStackTrace();
			String emsg = e.getMessage();
			if ("Duplicate entry".equals(emsg)) {
				return -2;
			} else {
				return -1;
			}
		}

	}

	@Override
	public int update(User... t) {
		// TODO
		return 0;
	}

	@Override
	public int delete(int... ids) {
		// TODO
		return 0;
	}

	@Override
	protected void setTable(Table table) {
		table.setName("user");
	}

}
