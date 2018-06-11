package cn.edu360.day10;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * 使用返回值的线程实现客户端
 * @author root
 *
 */
public class CallableClient implements Callable<String>{
	final static String KEY = "天王盖地虎";
	String path ;
	String comd;
	String ip;
	String localPath;
	
	
	public CallableClient() {
		super();
	}

	public CallableClient(String path, String comd, String ip, String localPath) {
		super();
		this.path = path;
		this.comd = comd;
		this.ip = ip;
		this.localPath = localPath;
	}

	@Override
	public String call() throws Exception {
		String ret = "";
		
		try {
			//1：建立通信
			Socket s = new Socket(ip, 9999);
			//2：发送身份验证信息
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(KEY);
			oos.flush();
			//3：接受验证信息返回结果
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			String value = (String)ois.readObject();
			if("宝塔镇河妖".equals(value)){//验证通过
				//4：发送jar文件位置
				oos.writeObject(path);
				oos.flush();
				
				//5：发送jar文件
				FileInputStream input = new FileInputStream(localPath);
				byte[] b = new byte[1024];
				int length = 0;
				while((length = input.read(b))!=-1){
					oos.write(b,0,length);
				}
				oos.flush();
				input.close();
				
				//6：发送comd
				oos.writeObject(comd);
				oos.flush();
				
				//7：获取结果
				ret = (String)ois.readObject();
				//System.out.println(ret);
				
			}else{//验证失败
				System.out.println("身份验证失败");
			}
			s.shutdownInput();
			s.shutdownOutput();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
