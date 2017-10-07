package com.duan.greatweb.dao;

import com.duan.greatweb.dao.db.DB;

public interface Dao {

	/**
	 * 添加成功
	 */
	public static int STATE_ADD_SUCCESS = DB.STATE_SUCCESS;

	/**
	 * 添加失败
	 */
	public static int STATE_ADD_FAIL = DB.STATE_ERROR_INSERT;

	/**
	 * 更新成功
	 */
	public static int STATE_UPDATE_SUCCESS = DB.STATE_SUCCESS;

	/**
	 * 更新失败
	 */
	public static int STATE_UPDATE_FAIL = DB.STATE_ERROR_UPDATE;

	/**
	 * 删除成功
	 */
	public static int STATE_DELETE_SUCCESS = DB.STATE_SUCCESS;

	/**
	 * 删除失败
	 */
	public static int STATE_DELETE_FAIL = DB.STATE_ERROR_DELETE;

}
