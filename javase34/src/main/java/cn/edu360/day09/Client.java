package cn.edu360.day09;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import cn.edu360.day09.model.Request;
import cn.edu360.day09.model.Response;
import cn.edu360.day09.model.User;

/**
 * 1:建立链接
 * 2：发送参数对象（req）
 * 3：接受返回值
 * @author root
 *
 */
public class Client {
	public static void main(String[] args) throws  Exception {
		Socket socket = new Socket("localhost", 9999);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		
		User user = new User();
		user.set("浩哥", "123456", "好好", "12321234", 20, "男", "龙湖");
		
		Request request = new Request();
		request.setClassName("cn.edu360.day09.service.impl.UserServiceImpl");
		request.setMothedName("register");
		request.setClassType(new Class[]{User.class});
		request.setValue(new Object[]{user});
		//写req对象
		oos.writeObject(request);
		oos.flush();
		
		//读数据
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		Response res =(Response) ois.readObject();
		System.out.println(res);
		
		socket.shutdownInput();
		socket.shutdownOutput();
		
	}

}
