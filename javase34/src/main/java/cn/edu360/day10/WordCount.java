package cn.edu360.day10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单机版的wordCount
 * @author root
 *
 */
public class WordCount {
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try (BufferedReader br = new BufferedReader(new FileReader("d:\\data\\UserServiceImpl.java"));){
			String line = null;
			while((line = br.readLine())!=null){
				//System.out.println(line);
				Pattern p = Pattern.compile("\\b\\w+\\b");
				Matcher matcher = p.matcher(line);
				while(matcher.find()){
					//System.out.println(matcher.group());
					String word = matcher.group();
					Integer orDefault = map.getOrDefault(word, 0);
					orDefault++;
					map.put(word, orDefault);
				}
				
			}
			Set<Entry<String,Integer>> entrySet = map.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				System.out.println(entry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
