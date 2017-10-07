package com.duan.greatweb.dao.db;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

/**
 * 获得数据库连接
 * 
 * @author DuanJiaNing
 *
 */
final public class DBHelper {

	private static String DRIVER;
	private static String DATABASE;
	private static String URL;
	private static String USER;
	private static String PASSWORD;

	private DBHelper() {
	}

	private static void init() {
		try {
			// FIXME 相对路径无法找到文件
			InputStream in = new BufferedInputStream(
					new FileInputStream("D:/workspace/eclipse/GreatWeb/src/dbConfig.properties"));

			Properties pro = new Properties();
			pro.load(in);

			DATABASE = pro.getProperty("database");
			URL = pro.getProperty("url") + DATABASE
					+ "?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8";
			USER = pro.getProperty("user");
			PASSWORD = pro.getProperty("password");
			DRIVER = pro.getProperty("driver");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		init();
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static DataBase getDataBase() {
		return new DataBase(getConnection());
	}

}
