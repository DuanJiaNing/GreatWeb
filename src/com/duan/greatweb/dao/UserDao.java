package com.duan.greatweb.dao;

import java.util.List;

import com.duan.greatweb.entitly.User;

public interface UserDao extends Dao{

	/**
	 * 用户账号状态为<b>冻结</b>，用户无法使用系统功能
	 */
	public static int STATE_FREEZE = -1;

	/**
	 * 用户账号状态为<b>正常</b>，用户可以使用系统功能
	 */
	public static int STATE_NORMAL = 0;

	/**
	 * 添加用户
	 * 
	 * @param user
	 *            要添加的用户，不能为空
	 * @return 执行结果：
	 *         <li>STATE_ADD_SUCCESS
	 *         <li>STATE_ADD_FAIL
	 */
	public int addUser(User user);

	/**
	 * 根据用户 id 查询用户信息
	 * 
	 * @param id
	 *            用户 id
	 * @return 存在该用户返回用户，否则为 null
	 */
	public User queryById(int id);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param name
	 *            用户名
	 * @return 存在该用户返回用户，否则为 null
	 */
	public User queryByName(String name);

	/**
	 * 返回指定状态下的用户信息
	 * 
	 * @param state
	 *            <li>STATE_FREEZE
	 *            <li>STATE_NORMAL
	 * @return 满足状态的用户集合
	 */
	public List<User> queryAll(int state);

	/**
	 * 根据用户id更新用户信息
	 * 
	 * @param id
	 *            用户 id
	 * @param name
	 *            新的用户名，该项无需修改时传 null
	 * @param password
	 *            新的密码，该项无需修改时传 null
	 * @param state
	 *            用户账号状态，该项无需修改时传 -1，可选状态有：
	 *            <li>STATE_FREEZE
	 *            <li>STATE_NORMAL
	 * @param age
	 *            用户年龄，该项无需修改时传 -1
	 * @return 更新结果，
	 *         <li>STATE_UPDATE_FAIL
	 *         <li>STATE_UPDATE_SUCCESS
	 */
	public int updateById(int id, String name, String password, int state, int age);

	/**
	 * 根据用户id删除用户
	 * 
	 * @param id
	 *            用户id
	 * @return 删除结果，
	 *         <li>STATE_DELETE_SUCCESS
	 *         <li>STATE_DELETE_FAIL
	 */
	public int deleteById(int id);
}
