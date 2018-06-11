package cn.edu360.day10;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 1:建立链接
 * 2：接受验证信息
 * 3：返回验证结果
 * 4：接受jar文件位置
 * 5：接受jar文件‘
 * 6：接受comd命令     ----> 执行jar程序
 * 7：返回结果
 * @author root
 *
 */
public class Server {
	final static String KEY = "天王盖地虎";
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(9999);
			System.out.println("服务端开启");
			while(true){
				//1:建立链接
				Socket accept = ss.accept();
				System.out.println("建立链接");
				//2：接受验证信息
				ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
				String readObject = (String)ois.readObject();
				//获取输出流
				ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream());
				//3：返回验证结果
				if(KEY.equals(readObject)){//验证成功
					oos.writeObject("宝塔镇河妖");
					oos.flush();
					
					//4：接受jar文件位置
					String path = (String)ois.readObject();
					
					//5：接受jar文件
					FileOutputStream outputStream = new FileOutputStream(path);//多层目录可能出现问题
					byte[] b = new byte[1024];
					int length = 0;
					while((length = ois.read(b))!=-1){
						outputStream.write(b, 0, length);
					}
					outputStream.close();
					
					//6：接受comd命令
					String comd = (String)ois.readObject();
					
					//执行程序
					String ret = Utile.runJar(comd);
					oos.writeObject(ret);
					oos.flush();
					
				}else{//验证不成功的时候
					oos.writeObject("宝塔镇不住河妖");
					oos.flush();
				}
				accept.shutdownInput();
				accept.shutdownOutput();
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
