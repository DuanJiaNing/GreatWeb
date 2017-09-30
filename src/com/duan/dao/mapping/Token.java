package com.duan.dao.mapping;

/**
 * 获得实体类映射到数据库的属性
 * 
 * @author 2017/09/14 DuanJiaNing
 *
 * @param <E> 目标类型
 */
public interface Token<E> {

	/**
	 * 获得属性
	 * @return 属性值
	 */
	E get();
	
}
