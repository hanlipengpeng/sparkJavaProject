package cn.edu360.day09.service;

import cn.edu360.day09.model.User;
import cn.edu360.day09.model.UserResult;

public interface UserService {
	/**
	 * 登陆
	 * @param user
	 * @return
	 */
	public UserResult login(User user);
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	public UserResult register(User user);

}
