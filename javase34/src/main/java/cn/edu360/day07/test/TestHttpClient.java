package cn.edu360.day07.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class TestHttpClient {
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String uri = "http://gc.ditu.aliyun.com/regeocoding?l=41.083778,113.983896&type=010";
		HttpMethod method = new GetMethod(uri);
		client.executeMethod(method);
		String string = method.getResponseBodyAsString();
		System.out.println(string);
		
	}

}
