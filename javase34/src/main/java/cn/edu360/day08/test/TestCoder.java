package cn.edu360.day08.test;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码和解码
 * @author root
 *
 */
public class TestCoder {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String encode = URLEncoder.encode("大数据");
		String encode2 = URLEncoder.encode(encode);
		System.out.println(encode2);
		
		
		String decode = URLDecoder.decode("%25E5%25A4%25A7%25E6%2595%25B0%25E6%258D%25AE");
		String decode2 = URLDecoder.decode(decode);
		System.out.println(decode2);
	}

}
