package cn.edu360.day08.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class TestImg {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//jsoup 只能解析文本文档      
		HttpClient client = new HttpClient();
		
		String url = "http:"+"//img02.51jobcdn.com/im/2016/logo/logo_20jubilee_116x46.png";
		HttpMethod method = new GetMethod(url);
		client.executeMethod(method);
		InputStream inputStream = method.getResponseBodyAsStream();
		FileOutputStream outputStream = new FileOutputStream("d:\\logo_20jubilee_116x46.png");
		
		byte[] b = new byte[1024];
		int length = 0;
		while((length = inputStream.read(b))!=-1){
			outputStream.write(b, 0, length);
		}
		
		inputStream.close();
		outputStream.close();
		
		
	}
	

}
