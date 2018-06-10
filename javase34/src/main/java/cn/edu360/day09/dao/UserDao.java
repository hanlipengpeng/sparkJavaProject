package cn.edu360.day09.dao;

import cn.edu360.day09.model.User;

public interface UserDao {
	/**
	 * 通过用户名查找用户
	 * @param userName
	 * @return
	 */
	public User getUserByName(String userName);
	
	/**
	 * 保存用户数据
	 * @param user
	 * @return
	 */
	public boolean save(User user);

}
