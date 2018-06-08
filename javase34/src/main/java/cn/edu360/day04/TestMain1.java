package cn.edu360.day04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;

public class TestMain1 {
	public static void main(String[] args) {
		//获取uid以及对应MovieBean集合
		Map<String, List<MovieBean>> map = getMap();
		//4：遍历map，对map中的value进行排序
		Set<Entry<String,List<MovieBean>>> entrySet = map.entrySet();
		for (Entry<String, List<MovieBean>> entry : entrySet) {
			String key = entry.getKey();
			List<MovieBean> value = entry.getValue();
			SortUtiles.sortByRate(value);
			System.out.println("uid:"+key);
			//取topn
			for(int i = 0; i<Math.min(3, value.size()) ; i++){
				System.err.println(value.get(i));
			}
		}
		
		
	}

	/**
	 * 通过文件获取uid以及对应MovieBean集合
	 * @return
	 */
	public static Map<String, List<MovieBean>> getMap() {
		//存放uid以及对应的MovieBean集合
		Map<String, List<MovieBean>> map = new HashMap<String, List<MovieBean>>();
		//1:读文件
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\rating.json"));){
			String line = null;
			while((line = br.readLine())!=null){
				//System.out.println(line);
				//2:解析数据
				MovieBean movieBean = JSON.parseObject(line, MovieBean.class);
				//System.out.println(movieBean);
				//3：把数据存放到map中
				List<MovieBean> list = map.getOrDefault(movieBean.getUid(), new ArrayList<MovieBean>());
				list.add(movieBean);
				map.put(movieBean.getUid(), list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	

}
