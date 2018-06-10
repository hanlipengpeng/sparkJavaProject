package cn.edu360.day09.dao;

import org.junit.Test;

import cn.edu360.day09.dao.impl.UserDaoImpl;
import cn.edu360.day09.model.User;

public class TestUserDao {
	@Test
	public void testGetUserByName(){
		String name = "超1";
		UserDao userDao = new UserDaoImpl();
		User user = userDao.getUserByName(name);
		System.out.println(user);
	}
	@Test
	public void testSave(){
		User user = new User();
		user.set("超哥1", "123", "超超", "186877364", 18, "男", "汇鑫公寓");
		UserDao userDao = new UserDaoImpl();
		boolean save = userDao.save(user);
		System.out.println(save);
	}

}
