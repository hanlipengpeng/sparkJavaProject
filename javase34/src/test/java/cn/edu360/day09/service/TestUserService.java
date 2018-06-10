package cn.edu360.day09.service;

import org.junit.Test;

import cn.edu360.day09.model.User;
import cn.edu360.day09.model.UserResult;
import cn.edu360.day09.service.impl.UserServiceImpl;

public class TestUserService {
	@Test
	public void testLogin(){
		User user = new User();
		user.setUserName("超哥2");
		user.setPassWord("1234");
		UserService userService = new UserServiceImpl();
		UserResult login = userService.login(user);
		System.out.println(login);
	}
	
	@Test
	public void testRegister(){
		User user = new User();
		user.set("强哥1", "123", "啊强","1838888888", 19, "男", "上海滩");
		UserService userservice = new UserServiceImpl();
		UserResult register = userservice.register(user);
		System.out.println(register);
	}

}
