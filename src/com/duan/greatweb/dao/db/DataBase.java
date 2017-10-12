package com.duan.greatweb.dao.db;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.xml.crypto.Data;

import com.duan.greatweb.dao.mapping.FieldToken;
import com.duan.greatweb.dao.mapping.TableToken;
import com.duan.greatweb.dao.mapping.Token;
import com.duan.greatweb.dao.mapping.Mapping;
import com.duan.greatweb.util.Utils;

/**
 * 数据库 CRUD 操作实现类，T 类型参数对应为实体类，实体类要使用{@link Mapping}注解
 * 提供表名和字段信息，实体类成员变量数据类型要与数据库中类型兼容。
 * <p>
 * 在不再使用时要{@code #close()}方法关闭数据库连接
 * <p>
 * 使用{@link DBHelper#getDataBase()}方法指定要操作的数据库，亦可指定数据库连接直接调用构造方法获得实例
 *
 * @author 2017/09/13 DuanJiaNing
 * @see DBHelper
 * @see DB
 * @see Data
 */
public class DataBase implements DB {

    /**
     * 数据库连接
     */
    private final Connection conn;

    /**
     * 构造一个<code>DataBase</code>实例，应通过{@link DBHelper#getDataBase(String)}方法创建
     *
     * @param connection 数据库连接
     */
    public DataBase(Connection connection) {
        this.conn = connection;
    }

    /**
     * 根据实体类类型信息获得其对应的数据表名称
     */
    private <T> String getTableName(Class<T> clasz) {
        Token<String> token = new TableToken<>(clasz);
        return token.get();
    }

    /**
     * 获取结果集大小，调用前提为 prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
     * ResultSet.CONCUR_READ_ONLY)
     */
    private int getResultSetSize(ResultSet set) throws SQLException {
        int size = -1;
        if (set != null) {
            set.last();
            size = set.getRow();
            set.beforeFirst();
        }
        return size;
    }

    /**
     * 构造 sql 语句 where 条件
     */
    private String constructConditions(String[] whereCase, String[] whereValues) {
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

    // 构造条件语句
    private String constructWhich(String var, String[] which) {
        StringBuilder b = new StringBuilder();
        for (String s : which) {
            b.append(var).append(".").append(which).append(",");
        }

        String s = b.toString();
        return s.substring(0, s.length() - 1);
    }

    /**
     * 关闭数据库
     *
     * @param conn 数据库连接对象
     */
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据条件查询表中数据
     *
     * @param clasz       要查询的表对应的实体数据类类型
     * @param whereCase   sql 语句 where 对应列名
     * @param whereValues whereCase 对应的值
     * @return 查询结果
     */
    public <T> T[] query(Class<T> clasz, String[] whereCase, String[] whereValues) {
        if (clasz == null) {
            return null;
        }

        String table = getTableName(clasz);
        if (Utils.isReal(table)) {
            String where = constructConditions(whereCase, whereValues);
            String sql = "select * from " + table + (where == null ? "" : " where" + where);
            return query(clasz, sql);
        } else {
            return null;
        }
    }

    public <T> T[] queryAll(Class<T> clasz) {
        if (clasz == null) {
            return null;
        }

        String table = getTableName(clasz);
        if (Utils.isReal(table)) {
            String sql = "select * from " + table;
            return query(clasz, sql);
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] query(Class<T> clasz, String sql) {

        if (clasz == null || !Utils.isReal(sql)) {
            return null;
        }

        PreparedStatement stat = null;
        ResultSet set = null;
        List<T> result = null;

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

            // 获取映射关系
            Token<List<FieldToken.FieldHolder>> token = new FieldToken<>(clasz);
            List<FieldToken.FieldHolder> fh = token.get();
            result = new ArrayList<>();

            // 利用反射将查询结果赋给目标类型
            while (set.next()) {
                T item = clasz.newInstance();
                for (FieldToken.FieldHolder h : fh) {
                    Field field = h.field;
                    String label = h.name;
                    // Class<?> type = field.getType();

                    // jdk 1.8 getObject 未实现，不可用
                    // Object value = set.getObject(label, type);

                    // jdk 1.8 可用
                    Object value = set.getObject(label);
                    field.setAccessible(true);
                    field.set(item, value);
                }
                result.add(item);
            }

        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            Utils.closeAutoCloseable(set);
            Utils.closeAutoCloseable(stat);
        }

        List<T> rs = new ArrayList<T>();
        for (T t : result) {
            if (t != null) {
                rs.add(t);
            }
        }

        return rs.toArray((T[]) Array.newInstance(clasz, 1));
    }

    /**
     * 除了 id ，其它不能为 null
     * {@inheritDoc}
     */
    @Override
    public <T> int insert(T... ts) {
        if (Utils.isArrayEmpty(ts)) {
            return STATE_ERROR_UNKNOW;
        }

        @SuppressWarnings("unchecked")
        Class<T> clasz = (Class<T>) ts[0].getClass();
        String table = getTableName(clasz);
        if (!Utils.isReal(table)) {
            return STATE_ERROR_UNKNOW;
        }

        // 获取映射关系
        Token<List<FieldToken.FieldHolder>> token = new FieldToken<>(clasz);
        List<FieldToken.FieldHolder> fh = token.get();
        if (Utils.isListEmpty(fh)) {
            return STATE_ERROR_UNKNOW;
        }

        StringBuilder caseBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        PreparedStatement stat = null;
        try {

            for (T t : ts) {
                caseBuilder.delete(0, caseBuilder.length());
                valueBuilder.delete(0, valueBuilder.length());
                int size = fh.size();
                for (int i = 0; i < size; i++) {
                    FieldToken.FieldHolder f = fh.get(i);
                    String v;
                    String tn = f.name;
                    Field field = f.field;
                    if (tn.equals("id")) {
                        continue;
                        // v = "DEFAULT";
                    } else {
                        v = getFiledValue(field, t);
                        if (v == null) {
                            return STATE_ERROR_INSERT;
                        }
                    }

                    caseBuilder.append(tn);
                    if (field.getType().getName().equals(String.class.getName())) {
                        valueBuilder.append('\'').append(v).append('\'');
                    } else {
                        valueBuilder.append(v);
                    }

                    if (i < size - 1) {
                        caseBuilder.append(',');
                        valueBuilder.append(',');
                    }
                }

                String sql = "insert into " + table + "(" + caseBuilder.toString() + ") values("
                        + valueBuilder.toString() + ")";
                Utils.log(sql);

                stat = conn.prepareStatement(sql);
                stat.executeUpdate();
            }

            return STATE_SUCCESS;

        } catch (SQLException e) {
            e.printStackTrace();
            return STATE_ERROR_INSERT;
        } finally {
            Utils.closeAutoCloseable(stat);
        }

    }

    private <T> String getFiledValue(Field field, T t) {

        try {

            // 利用反射获得值
            field.setAccessible(true);
            Object obj = field.get(t);
            if (obj == null) {
                return null;
            } else {
                return obj.toString();
            }

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> int update(T... ts) {
        if (Utils.isArrayEmpty(ts)) {
            return STATE_ERROR_UNKNOW;
        }

        @SuppressWarnings("unchecked")
        Class<T> clasz = (Class<T>) ts[0].getClass();
        String table = getTableName(clasz);
        if (!Utils.isReal(table)) {
            return STATE_ERROR_UNKNOW;
        }

        // 获取映射关系
        Token<List<FieldToken.FieldHolder>> token = new FieldToken<>(clasz);
        List<FieldToken.FieldHolder> fh = token.get();
        if (Utils.isListEmpty(fh)) {
            return STATE_ERROR_UNKNOW;
        }

        PreparedStatement stat = null;
        try {

            for (T t : ts) {
                StringBuilder valueBuilder = new StringBuilder();
                int size = fh.size();
                String id = "";
                for (int i = 0; i < size; i++) {
                    FieldToken.FieldHolder f = fh.get(i);
                    String v = "";
                    String tn = f.name;
                    Field field = f.field;

                    v = getFiledValue(field, t);
                    if (v == null) {
                        continue;
                    }

                    if (!tn.equals("id")) {
                        valueBuilder.append(tn).append('=');
                        if (field.getType().getName().equals(String.class.getName())) {
                            valueBuilder.append('\'').append(v).append('\'');
                        } else {
                            valueBuilder.append(v);
                        }

                        valueBuilder.append(',');
                    } else {
                        id = v;
                    }

                }

                String where = constructConditions(new String[]{"id"}, new String[]{id});
                String temp = valueBuilder.toString();
                String set = temp.substring(0, temp.length() - 1);
                String sql = "update " + table + " set " + set + " where" + where;
                Utils.log(sql);

                stat = conn.prepareStatement(sql);
                stat.executeUpdate();
            }

            return STATE_SUCCESS;

        } catch (SQLException e) {
            e.printStackTrace();
            return STATE_ERROR_UNKNOW;
        } finally {
            Utils.closeAutoCloseable(stat);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> int delete(Class<T> clasz, int... ids) {
        if (Utils.isArrayEmpty(ids)) {
            return STATE_ERROR_UNKNOW;
        }

        String table = getTableName(clasz);
        if (!Utils.isReal(table)) {
            return STATE_ERROR_UNKNOW;
        }

        PreparedStatement stat = null;
        try {

            for (int id : ids) {

                String where = constructConditions(new String[]{"id"}, new String[]{id + ""});
                String sql = "delete from " + table + " where" + where;
                Utils.log(sql);

                stat = conn.prepareStatement(sql);
                stat.executeUpdate();
            }

            return STATE_SUCCESS;

        } catch (SQLException e) {
            e.printStackTrace();
            return STATE_ERROR_UNKNOW;
        } finally {
            Utils.closeAutoCloseable(stat);
        }

    }

    @Override
    public <T> int size(Class<T> clasz) {
        return size(clasz, null, null);
    }

    public <T> int size(Class<T> clasz, String[] whereCase, String[] whereValues) {
        if (clasz == null) {
            return -1;
        }

        String table = getTableName(clasz);

        if (Utils.isReal(table)) {
            String where = constructConditions(whereCase, whereValues);
            String sql = "selcet count(*) from " + table + where == null ? "" : " where" + where;

            PreparedStatement pre = null;
            ResultSet set = null;
            try {
                pre = conn.prepareStatement(sql);
                set = pre.executeQuery();
                int size = -1;
                while (set.next()) {
                    size = set.getInt(1);
                }

                return size;
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            } finally {
                Utils.close(set, pre);
            }

        }

        return -1;
    }

    /**
     * 查询指定数目的数据
     *
     * @param clasz
     * @param start 起始行下标，从 0 开始
     * @param count 行数
     * @return 小余、等于或空的数组
     */
    public <T> T[] query(Class<T> clasz, int start, int count) {
        return query(clasz, start, count, null, null);
    }

    public <T> T[] query(Class<T> clasz, int start, int count, String[] whereCase, String[] whereValues) {
        if (clasz == null || start < 0 || count < 0) {
            return null;
        }

        String table = getTableName(clasz);
        if (!Utils.isReal(table)) {
            return null;
        }

        String where = constructConditions(whereCase, whereValues);
        String sql = "select from " + table + where == null ? "" : " where" + where + " limit " + start + "," + count;
        return query(clasz, sql);
    }

}
