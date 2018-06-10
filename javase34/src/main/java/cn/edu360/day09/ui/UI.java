package cn.edu360.day09.ui;

import java.lang.reflect.Method;

import cn.edu360.day09.model.User;
import cn.edu360.day09.model.UserResult;

public class UI {
	public static void main(String[] args) throws Exception {
		//登陆，注册
		//详情见service的测试类
		
		
		//反射使用 调用登陆
		User user = new User();
		user.setUserName("强哥");
		user.setPassWord("123");
		
		//1:获取class对象
		Class<?> forName = Class.forName("cn.edu360.day09.service.impl.UserServiceImpl");
		//2:new 对象
		Object newInstance = forName.newInstance();
		//3：获取方法
		Method method = forName.getMethod("login", User.class);
		//4:执行方法
		UserResult invoke = (UserResult)method.invoke(newInstance, user);
		System.out.println(invoke);
		
		
		
	}

}
