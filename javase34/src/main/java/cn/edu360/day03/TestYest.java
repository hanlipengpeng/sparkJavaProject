package cn.edu360.day03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestYest {
	public static void main(String[] args) {
		//io
		try (BufferedReader br = new BufferedReader(new FileReader(""));){
			String line=null;
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Map排序
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
		map.put("3", "3");
		map.put("2", "2");
		Set<Entry<String,String>> entrySet = map.entrySet();
		ArrayList<Entry<String,String>> arrayList = new ArrayList<>(entrySet);
		Collections.sort(arrayList, new Comparator<Entry<String,String>>(){

			@Override
			public int compare(Entry<String, String> o1,
					Entry<String, String> o2) {
				
				return o1.getKey().compareTo(o2.getKey());
			}});

			

		
		//时间比较
		String date1 = "2018-06-01";
		String date2 = "2018-05-30";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date s = sdf.parse(date1);
			Date s1 = sdf.parse(date2);
			s.after(s1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] a = {"a","b"};
		//ArrayList list = Arrays.asList(a);
	}

}
