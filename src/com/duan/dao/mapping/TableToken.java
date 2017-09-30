package com.duan.dao.mapping;

/**
 * 实体类类型信息获取类
 * <p>
 * 用于获得被 {@link Mapping}注解的实体类的注解值
 * 
 * @author 2017/09/13 DuanJiaNing
 * @param T
 *            实体类，该类需要使用{@code Mapping}注解标明其对应数据库的表的名字
 */
public class TableToken<T> implements Token<String> {

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 构建一个实例
	 * 
	 * @param clasz
	 *            实体类类型
	 */
	public TableToken(Class<T> clasz) {
		if (clasz != null) {
			getTableName(clasz);
		}
	}

	/**
	 * 利用反射获得注解标明的表名
	 */
	private void getTableName(Class<T> clasz) {
		Mapping map = clasz.getAnnotation(Mapping.class);
		if (map != null) {
			tableName = map.value();
		}
	}

	/**
	 * 获得类对应表的表名
	 */
	@Override
	public String get() {
		return tableName;
	}

}
