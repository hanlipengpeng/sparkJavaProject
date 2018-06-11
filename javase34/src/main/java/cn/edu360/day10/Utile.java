package cn.edu360.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utile {
	/**
	 * 执行程序
	 * @param comd
	 * @return
	 */
	public static String runJar(String comd) {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process exec = runtime.exec(comd);
			BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = br.readLine())!=null){
				sb.append(line+"\n");
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
