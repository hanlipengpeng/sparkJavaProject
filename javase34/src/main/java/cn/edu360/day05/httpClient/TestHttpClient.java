package cn.edu360.day05.httpClient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class TestHttpClient {
	public static void main(String[] args) throws HttpException, IOException {
		
		HttpClient client = new HttpClient();
		String uri = "http://www.edu360.cn/";
		HttpMethod method = new GetMethod(uri);
		client.executeMethod(method);
		
		String cont = method.getResponseBodyAsString();
		System.out.println(cont);
		
		
		
	}

}
