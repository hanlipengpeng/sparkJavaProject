package cn.edu360.day10.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 执行jar文件
 * @author root
 *
 */
public class TestRuntime {
	public static void main(String[] args) throws Exception {
		//获取运行环境
		Runtime runtime = Runtime.getRuntime();
		String comd = "java -jar d:\\data\\wc.jar";
		//执行命令
		Process exec = runtime.exec(comd);
		//获取控制台结果
		BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
		String line = null;
		while((line = br.readLine())!=null){
			System.out.println(line);
		}
	}

}
