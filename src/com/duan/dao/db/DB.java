package com.duan.dao.db;

/**
 * 使用注解和反射实现对数据库的 CRUD 操作；
 * <p>
 * 1 通过注解将数据库中的表对应到具体的 java 实体类 <br>
 * 2 使用反射解析表信息和实体类间的映射关系
 * 
 * @author 2017/09/14 DuanJiaNing
 *
 */
public interface DB {

	public static int STATE_ERROR_UNKNOW = -1;
	public static int STATE_ERROR_INSERT = -2;
	public static int STATE_ERROR_UPDATE = -3;
	public static int STATE_ERROR_DELETE = -4;
	public static int STATE_SUCCESS = 1;

	/**
	 * 查询指定表中的所有数据
	 * 
	 * @param clasz
	 *            表对应的数据实体类类型
	 * @return 查询结果
	 */
	public <T> T[] query(Class<T> clasz, String sql);

	/**
	 * 插入数据到指定表中，
	 * 
	 * @param ts
	 *            要插入的数据
	 * @return 插入成功返回 true，否则 false
	 */
	public <T> int insert(T... ts);

	/**
	 * 更新特定表中的数据
	 * 
	 * @param ts
	 *            更新的数据
	 * @return 更新成功返回 true，否则 false
	 */
	public <T> int update(T... ts);

	/**
	 * 删除特定表中的数据
	 * 
	 * @param clasz
	 * @param ids
	 *            数据的 id 值
	 * @return 成功返回 true，否则 false
	 */
	public <T> int delete(Class<T> clasz, int... ids);
	
	/**
	 * 获得特定表中的数据条数
	 * @param clasz 
	 * @return 条数
	 */
	public <T> int size(Class<T> clasz);
}
