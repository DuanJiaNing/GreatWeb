package com.duan.dao.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体类字段信息获取类
 * <p>
 * 用于获得实体类的成员变量中被 {@link Mapping}注解的成员的注解值
 * 
 * @author 2017/09/13 DuanJiaNing
 * @param T
 *            实体类，该类的域需要使用{@code Mapping}注解标明其对应数据库中的字段名
 */
public class FieldToken <T> implements Token<List<FieldToken.FieldHolder>> {

	/**
	 * 保存成员变量与字段名的映射信息
	 */
	private List<FieldHolder> holders;

	/**
	 * 构建一个实例
	 * 
	 * @param clasz
	 *            实体类类型
	 */
	public FieldToken(Class<T> clasz) {
		if (clasz != null) {
			holders = new ArrayList<FieldHolder>();
			initFieldHolder(clasz);
		}
	}

	/**
	 * 初始化类型的映射关系
	 */
	private void initFieldHolder(Class<T> clasz) {
		Field[] fs = clasz.getDeclaredFields();
		for (Field f : fs) {
			Mapping mag = f.getAnnotation(Mapping.class);
			if (mag != null) {
				FieldHolder fh = new FieldHolder();
				fh.name = mag.value();
				fh.field = f;
				holders.add(fh);
			}
		}

		@SuppressWarnings("unchecked")
		Class<T> supez = (Class<T>) clasz.getSuperclass();
		if (supez != null) {
			initFieldHolder(supez);
		}
	}

	/**
	 * 获得数据库表的字段名与实体类域的映射列表
	 */
	@Override
	public List<FieldHolder> get() {
		return holders;
	}

	/**
	 * 保存数据库字段名与数据实体的映射关系
	 * 
	 * @author 2017/09/13 DuanJiaNing
	 *
	 */
	public static class FieldHolder {
		/**
		 * 数据库中取出的数据将要存入的实体类字段
		 */
		public Field field;

		/**
		 * 数据库字段名
		 */
		public String name;
	}

}
