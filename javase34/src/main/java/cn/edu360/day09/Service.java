package cn.edu360.day09;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import cn.edu360.day09.model.Request;
import cn.edu360.day09.model.Response;
import cn.edu360.day09.model.UserResult;

/**
 * 发布服务
 * @author root
 * 1:链接
 * 2：获取输入流
 * 3：把流解析出来
 * 4：反射执行相应的方法
 * 5：结果写出去
 * 
 * 解决M2_HOME找不到的情况
 * -Dmaven.multiModuleProjectDirectory=$M2_HOME
 *
 */
public class Service {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(9999);
			System.out.println("服务端开始工作。。。");
			while(true){
				//获取到链接
				Socket accept = serverSocket.accept();
				//获取输入输出流
				ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
				
				
				//返回结果
				Response res = new Response();
				try {
					Object readObject = ois.readObject();
					Request req = (Request) readObject;
					
					//反射执行
					Class<?> forName = Class.forName(req.getClassName());
					Object newInstance = forName.newInstance();
					Method method = forName.getMethod(req.getMothedName(), req.getClassType());
					UserResult ret = (UserResult)method.invoke(newInstance, req.getValue());
					res.setStatus("200");
					res.setRet(ret);
				} catch (Exception e) {
					res.setStatus("500");
				}
				ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream());
				oos.writeObject(res);
				oos.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
