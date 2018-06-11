package cn.edu360.day10;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 用来调用Callable任务，以及合并数据
 * @author root
 *
 */
public class TestCallableMain {
	public static void main(String[] args) {
		//结果
		String ret1 =null;
		String ret2 = null;
		
		String path ="D:\\data\\wc3.jar";
		String comd = "java -jar "+path;
		String ip = "localhost";
		String localPath = "D:\\data\\wc.jar";
		
		CallableClient cc = new CallableClient(path, comd, ip, localPath);
		FutureTask<String> futureTask = new FutureTask<>(cc);
		new Thread(futureTask).start();
		
		try {
			ret1 = futureTask.get(10, TimeUnit.SECONDS);
			//System.out.println(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//第二台电脑
		String path2 ="D:\\data\\wc4.jar";
		String comd2 = "java -jar "+path2;
		String ip2 = "localhost";
		String localPath2 = "D:\\data\\wc.jar";
		
		CallableClient cc2 = new CallableClient(path2, comd2, ip2, localPath2);
		FutureTask<String> futureTask2 = new FutureTask<>(cc2);
		new Thread(futureTask2).start();
		
		try {
			ret2 = futureTask2.get(10, TimeUnit.SECONDS);
			//System.out.println(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		String ret = ret1+ret2;
		String[] split = ret.split("\n");
		for (String string : split) {
			String[] split2 = string.split("=");
			String word = split2[0];
			int count = Integer.parseInt(split2[1]);
			Integer orDefault = map.getOrDefault(word, 0);
			orDefault+=count;
			map.put(word, orDefault);
		}
		
		Set<Entry<String,Integer>> entrySet = map.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			System.out.println(entry);
		}
		
	}
}
