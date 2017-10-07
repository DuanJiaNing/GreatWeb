package com.duan.greatweb.util;

import java.io.Closeable;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static StringBuilder append(StringBuilder b, String tag, String msg) {
		return b.append("<hr>").append(tag).append(": ").append(msg);
	}

	public static void close(ResultSet rs, PreparedStatement ptmt) {
		closeAutoCloseable(rs);
		closeAutoCloseable(ptmt);
	}

	/**
	 * 关闭一个 {@link Closeable}对象
	 */
	public static void close(Closeable obj) {
		if (obj == null) {
			return;
		}

		try {
			obj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭一个 {@link AutoCloseable}对象
	 */
	public static void closeAutoCloseable(AutoCloseable obj) {
		if (obj == null) {
			return;
		}

		try {
			obj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出包含时间信息的日志
	 */
	public static void log(CharSequence msg) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out.println(f.format(new Date(System.currentTimeMillis())) + " " + msg);
	}

	/**
	 * 判断字符串是否是 null 的，没内容的，或内容为 'null' 的
	 * 
	 * @return 满足 null 或 无内容 或 内容为 'null' 返回 true，否则 false
	 */
	public static boolean isReal(CharSequence str) {
		return str != null && !str.equals("null") && str.length() > 0;
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @return 数组为 null 或长度为 0 返回 true，否则 false
	 */
	public static <T> boolean isArrayEmpty(T... ts) {
		return ts == null || ts.length == 0;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @return 集合为 null 或大小为 0 返回 true，否则 false
	 */
	public static <T> boolean isListEmpty(List<T> list) {
		return list == null || list.size() == 0;
	}

	/**
	 * 将指定年份，月份的日期转为从1790-1-1 00:00:00到当前时间总共经过的时间的毫秒数
	 */
	public static long getTimeInMillis(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		return calendar.getTimeInMillis();
	}

	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public final static String md5(String s) {
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 当 defaulz 不能为 null
	// 部分 tomcat 的 JBuilder 不支持泛型，不要在jsp 页面中调用该方法
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(HttpServletRequest req, String name, T defaulz) {
		Object obj = req.getAttribute(name);
		return obj == null ? defaulz : (T) obj;
	}

}
