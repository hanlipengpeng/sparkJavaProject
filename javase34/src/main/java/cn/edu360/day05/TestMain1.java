package cn.edu360.day05;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据给的用户上网日志记录数据，计算出总流量最高的网站Top3(网站例如：v.baidu.com，weibo.com)
 * 1:读文件
 * 2：切分
 * 3：提取网站
 * 4：把相同网站的流量数据累加到一起
 * 5：排序
 * 6：保存数据
 *
 */
public class TestMain1 {
	public static void main(String[] args) {
		//存放网站以及对应的流量
		Map<String, Long> map = new HashMap<String, Long>();
		try (BufferedReader br = new BufferedReader(new FileReader("d:\\data\\http.log"));){
			String line = null;
			//13198893287	http://music.baidu.com 6089 11536
			while((line = br.readLine())!=null){
				//System.out.println(line);
				String[] split = line.split("\t")[1].split(" ");
				String oldUrl = split[0];
				long up = Long.parseLong(split[1]);
				long down = Long.parseLong(split[2]);
				String url = getUrlByRegex(oldUrl);
				//System.out.println(url);
				Long sum = map.getOrDefault(url, 0L);
				sum =sum+up+down;
				map.put(url, sum);
			}
			/*for (Entry<String, Long> entry : map.entrySet()) {
				System.out.println(entry);
			}*/
			//排序
			Set<Entry<String,Long>> entrySet = map.entrySet();
			ArrayList<Entry<String,Long>> list = new ArrayList<>(entrySet);
			SortUtiles.sortByLong(list);
			/*for (Entry<String, Long> entry : list) {
				System.out.println(entry);
			}*/
			
			//保存，输出
			for(int i = 0;i<Math.min(3, list.size());i++){
				System.out.println(list.get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 使用正则匹配，匹配所需要的url
	 * @param oldUrl
	 * @return
	 */
	private static String getUrlByRegex(String oldUrl) {
		//v.baidu.com，weibo.com
		Pattern compile = Pattern.compile("(\\w+\\.)?(\\w+\\.){1}\\w+");
		Matcher matcher = compile.matcher(oldUrl);
		while(matcher.find()){
			String ret = matcher.group();
			return ret;
		}
		return null;
	}

}
