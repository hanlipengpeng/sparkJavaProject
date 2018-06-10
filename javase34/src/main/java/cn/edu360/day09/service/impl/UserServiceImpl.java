package cn.edu360.day09.service.impl;

import cn.edu360.day09.dao.UserDao;
import cn.edu360.day09.dao.impl.UserDaoImpl;
import cn.edu360.day09.model.User;
import cn.edu360.day09.model.UserResult;
import cn.edu360.day09.service.UserService;

public class UserServiceImpl implements UserService{
	/**
	 * 登陆
	 * 1：通过用户名查找用户
	 * 2：判断用户是否存在
	 * 3：判断密码是否匹配
	 */
	@Override
	public UserResult login(User user) {
		UserResult userResult = new UserResult();
		UserDao userDao = new UserDaoImpl();
		User userByName = userDao.getUserByName(user.getUserName());
		if(userByName!=null){//找到用户的情况
			if(userByName.getPassWord().equals(user.getPassWord())){
				userResult.setFlag(true);
				userResult.setDes("登陆成功");
			}else{
				userResult.setFlag(false);
				userResult.setDes("密码错误");
			}
			
		}else{//找不到用户的情况
			userResult.setFlag(false);
			userResult.setDes("用户不存在");
		}
		return userResult;
	}
	/**
	 * 注册
	 * 1：根据用户名查找用户
	 * 2：判断用户是否为空
	 * 3：为空的时候保存数据  (不为空的时候，已经被注册过了)
	 */
	@Override
	public UserResult register(User user) {
		UserResult userResult = new UserResult();
		UserDao userDao = new UserDaoImpl();
		User userByName = userDao.getUserByName(user.getUserName());
		if(userByName ==null){//没有人注册过这个用户
			boolean save = userDao.save(user);
			if(save){
				userResult.setFlag(true);
				userResult.setDes("注册成功");
			}else{
				userResult.setFlag(false);
				userResult.setDes("未知异常");
			}
		}else{//已经有人注册过了
			userResult.setFlag(false);
			userResult.setDes("用户名已被注册");
		}
		return userResult;
	}

}
