package com.duan.other;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.duan.dao.db.DBHelper;
import com.duan.util.Utils;

/**
 * @author DuanJiaNing
 */
public abstract class DatabaseTable<T> {

	protected final Connection conn;
	private final Table mTable;

	protected abstract void setTable(Table table);

	public String getTable() {
		return mTable.getName();
	}

	public DatabaseTable(Connection connection) {
		this.conn = connection;
		mTable = new Table();
		setTable(mTable);
	}

	public DatabaseTable() {
		this(DBHelper.getConnection());
	}

	// 构造条件语句
	protected String constructConditions(String[] whereCase, String[] whereValues) {
		if (whereCase == null || whereCase.length == 0 || whereValues == null || whereValues.length == 0
				|| whereCase.length != whereValues.length) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < whereCase.length; i++) {
			String where = whereCase[i];
			String value = whereValues[i];
			if (i > 0) {
				builder.append(" and");
			}
			builder.append(' ').append(where).append(" like ").append('\'').append(value).append('\'');
		}

		return builder.toString();
	}

	/**
	 * 获取结果集大小，调用前提为 prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY)
	 */
	protected int getResultSetSize(ResultSet set) throws SQLException {
		int size = -1;
		if (set != null) {
			set.last();
			size = set.getRow();
			set.beforeFirst();
		}
		return size;
	}

	public void close() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 构造条件语句
	protected String constructWhich(String var, String[] which) {
		StringBuilder b = new StringBuilder();
		for (String s : which) {
			b.append(var).append(".").append(which).append(",");
		}

		String s = b.toString();
		return s.substring(0, s.length() - 1);
	}

	public T[] query(String[] which, String[] whereCase, String[] whereValues) {

		if (Utils.isReal(mTable.getName())) {
			String where = constructConditions(whereCase, whereValues);
			String var = "t";
			String wh = (which == null || which.length == 0) ? "*" : constructWhich(var, which);
			String sql = "select " + wh + " from " + mTable.getName() + " " + var
					+ (where == null ? "" : " where" + where);
			return query(sql);
		} else {
			return null;
		}
	}

	public T[] queryAll() {
		if (Utils.isReal(mTable.getName())) {
			String sql = "select * from " + mTable.getName();
			return query(sql);
		} else {
			return null;
		}
	}

	public abstract T[] query(String sql);

	public abstract int insert(T... t);

	public abstract int update(T... t);

	public abstract int delete(int... ids);

}
