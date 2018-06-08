package cn.edu360.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestMain2 {
	public static void main(String[] args) throws Exception {
		//数据库相关
		ComboPooledDataSource source = new ComboPooledDataSource();
		QueryRunner run = new QueryRunner(source);
		String sql = "insert into uidcount values (?,?)";
		
		//获取map数据
		Map<String, Integer> map = getMap();
		
		Set<Entry<String,Integer>> entrySet = map.entrySet();
		//把set集合直接放到list
		ArrayList<Entry<String,Integer>> arrayList = new ArrayList<>(entrySet);
		//排序
		Collections.sort(arrayList, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		//保存到数据库     create table uidCount(uid varchar(20),count varchar(20));
		for (Entry<String, Integer> entry : arrayList) {
			//System.out.println(entry);
			run.update(sql, entry.getKey(),entry.getValue()+"");
		}
		
	}
	/**
	 * 通过文件读取切分，得到map数据     key----用户    value---好友数量
	 */
	private static Map<String,Integer> getMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		//1:读取数据
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\好友.txt"));){
			String line = null;
			while((line=br.readLine())!=null){
				//System.out.println(line);
				//2:切分数据
				String[] split = line.split(":");   
				String uid = split[0];
				Integer count = split[1].split(",").length;
				//3:存放到map中
				map.put(uid, count);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
