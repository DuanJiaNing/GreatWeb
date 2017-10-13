package com.duan.greatweb.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspContext;

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
        if (obj != null) {
            try {
                obj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭一个 {@link AutoCloseable}对象
     */
    public static void closeAutoCloseable(AutoCloseable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private final static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F'};

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

    /**
     * 格式化日期 YYYY-MM-dd-HH:MM:SS
     */
    public static String getFormatedTime(long time) {
        SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd-HH:MM:SS");
        return f.format(new Date(time));
    }

    public static String getFormatedTime(long time, String pattern) {
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        return f.format(new Date(time));
    }

    public static boolean isUpperCaseByte(byte b) {
        return b >= 65 && b <= 90;
    }

    public static boolean isLowerCaseByte(byte b) {
        return b >= 97 && b <= 122;
    }

    public static boolean isNumberCaseByte(byte b) {
        return b >= 48 && b <= 57;
    }

    public static boolean isLetterByte(byte b) {
        return isUpperCaseByte(b) || isLowerCaseByte(b);
    }

    public static boolean isSpecialByte(byte b) {
        return b < 48 || (b > 57 && b < 65) || (b > 90 && b < 97) || b > 122;
    }

    @Deprecated
    public static int parseStringToInt(String str) {
        if (!isReal(str)) {
            return -1;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            // NumberFormatException 异常继承 RuntimeException，抛出次异常一定是由于程序员的疏忽
            // e.printStackTrace();
            System.err.println("NumberFormatException has throwed");
            return -1; // FIXME -1 也是数字
        }
    }

    /**
     * 格式要求 YYYY-MM-dd hh:mm
     */
    public static Calendar parseStringToDateMinute(String time) {

        if (!isReal(time)) {
            return null;
        }

        if (time.indexOf(" ") == -1 || time.indexOf(":") == -1) {
            return null;
        }

        String[] spl = time.split(" ");
        if (spl.length != 2) {
            return null;
        }

        String[] spl2 = spl[0].split("-");
        if (spl2.length != 3) {
            return null;
        }

        String[] spl3 = spl[1].split(":");
        if (spl3.length != 2) {
            return null;
        }

        int year = Utils.parseStringToInt(spl2[0]);
        int month = Utils.parseStringToInt(spl2[1]);
        int day = Utils.parseStringToInt(spl2[2]);
        int hour = Utils.parseStringToInt(spl3[0]);
        int minute = Utils.parseStringToInt(spl3[1]);

        if (year == -1 || month == -1 || day == -1 || hour == -1 || minute == -1) {
            return null;
        }

        Calendar des = Calendar.getInstance();
        des.set(year, month - 1, day, hour, minute);

        return des;

    }

    /**
     * 格式要求 YYYY-MM-dd
     */
    public static Calendar parseStringToDate(String time) {

        if (!Utils.isReal(time)) {
            return null;
        }

        String[] spl = time.split("-");
        if (spl.length != 3) {
            return null;
        }

        int year = Utils.parseStringToInt(spl[0]);
        int month = Utils.parseStringToInt(spl[1]);
        int day = Utils.parseStringToInt(spl[2]);

        if (year == -1 || month == -1 || day == -1) {
            return null;
        }

        Calendar des = Calendar.getInstance();
        des.set(year, month - 1, day);

        return des;

    }

    public static float parseStringToFloat(String str) {
        if (!isReal(str)) {
            return -1;
        }
        try {
            return Float.valueOf(str);
        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException has throwed");
            return -1;
        }
    }

    // FIXME
    public static boolean isNumber(String str) {
        if (!isReal(str)) {
            return false;
        }

        int len = str.length();
        boolean dot = false;
        for (int i = 0; i < str.length(); i++) {
            char r = str.charAt(i);
            String c = r + "";
            byte b = (byte) r;

            if (!isNumberCaseByte(b)) { // 非数字
                if (i == len - 1 && (c.equalsIgnoreCase("d") || c.equalsIgnoreCase("L") || c.equalsIgnoreCase("f"))) {
                    return true;
                } else if (!dot && c.equals(".")) {
                    dot = true;
                } else {
                    return false;
                }
            }
        }

        return true;

    }

    public static boolean isFloatNumber(String str) {
        int l = str.length() - 1;
        return isNumber(str) && (str.contains(".") || str.indexOf("f") == l || str.indexOf("F") == l);
    }

    public static boolean isIntNumber(String str) {
        for (byte b : str.getBytes()) {
            if (!isNumberCaseByte(b)) {
                return false;
            }
        }

        return true;
    }

    // 计算时间差，返回毫秒数，start 和 end 相差不能太大，相差很大时应使用 calcTimeGapOnMinute 方法
    public static long calcTimeGapOnMillisecond(long start, long end) {

        Calendar startC = Calendar.getInstance();
        Calendar endC = Calendar.getInstance();

        startC.setTime(new Date(start));
        endC.setTime(new Date(end));

        if (startC.after(end)) {
            return -1;
        }

        long re = 0;
        while (startC.get(Calendar.MILLISECOND) != endC.get(Calendar.MILLISECOND)
                || startC.get(Calendar.SECOND) != endC.get(Calendar.SECOND)
                || startC.get(Calendar.MINUTE) != endC.get(Calendar.MINUTE)
                || startC.get(Calendar.HOUR_OF_DAY) != endC.get(Calendar.HOUR_OF_DAY)
                || startC.get(Calendar.DAY_OF_MONTH) != endC.get(Calendar.DAY_OF_MONTH)
                || startC.get(Calendar.MONTH) != endC.get(Calendar.MONTH)
                || startC.get(Calendar.YEAR) != endC.get(Calendar.YEAR)) {
            startC.add(Calendar.MILLISECOND, 1);
            re++;
        }

        return re;
    }

    // 计算时间差，返回天数
    public static int calcTimeGapOnDay(long start, long end) {

        Calendar startC = Calendar.getInstance();
        Calendar endC = Calendar.getInstance();

        startC.setTime(new Date(start));
        endC.setTime(new Date(end));

        if (startC.after(end)) {
            return -1;
        }

        int re = 0;
        while (startC.get(Calendar.DAY_OF_MONTH) != endC.get(Calendar.DAY_OF_MONTH)
                || startC.get(Calendar.MONTH) != endC.get(Calendar.MONTH)
                || startC.get(Calendar.YEAR) != endC.get(Calendar.YEAR)) {

            startC.add(Calendar.DAY_OF_MONTH, 1);
            re++;
        }

        return re;

    }

    // 计算时间差，返回分钟数
    public static int calcTimeGapOnMinute(long start, long end) {

        Calendar startC = Calendar.getInstance();
        Calendar endC = Calendar.getInstance();

        startC.setTime(new Date(start));
        endC.setTime(new Date(end));

        if (startC.after(end)) {
            return -1;
        }

        int re = 0;
        while (startC.get(Calendar.MINUTE) != endC.get(Calendar.MINUTE)
                || startC.get(Calendar.HOUR_OF_DAY) != endC.get(Calendar.HOUR_OF_DAY)
                || startC.get(Calendar.DAY_OF_MONTH) != endC.get(Calendar.DAY_OF_MONTH)
                || startC.get(Calendar.MONTH) != endC.get(Calendar.MONTH)
                || startC.get(Calendar.YEAR) != endC.get(Calendar.YEAR)) {

            startC.add(Calendar.MINUTE, 1);
            re++;
        }

        return re;

    }

    @SuppressWarnings("unchecked")
    public static <T> T findAttribute(JspContext pageContext, String name, T defaulz) {
        Object value = pageContext.findAttribute(name);
        if (value == null) {
            return defaulz;
        } else {
            return (T) value;
        }
    }

    @SuppressWarnings("unchecked")
    public static String findAttribute(JspContext pageContext, String name, String defaulz) {
        Object value = pageContext.findAttribute(name);
        if (value == null) {
            return defaulz;
        } else {
            return value.toString();
        }
    }

    public static String readStringFromInputStream(InputStream is) {
        if (is == null) {
            return null;
        }

        byte[] bs = new byte[1024];
        int len;
        StringBuilder builder = new StringBuilder();

        try {
            while ((len = is.read(bs)) > 0) {
                builder.append(new String(bs, 0, len));
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
