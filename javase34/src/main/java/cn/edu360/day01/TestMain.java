package cn.edu360.day01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestMain {
	public static void main(String[] args) {
		//获取map数据
		Map<String, Integer> map = getMap();
		
		//存放uidCountBean
		List<UidCountBean> list = new ArrayList<UidCountBean>();
		
		//4:map   ---> list
		Set<Entry<String,Integer>> entrySet = map.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			//entry 封装到bean里面
			String uid = entry.getKey();
			Integer count = entry.getValue();
			UidCountBean uidCountBean = new UidCountBean(uid, count);
			list.add(uidCountBean);
		}
		
		//5：排序
		Collections.sort(list, new Comparator<UidCountBean>() {
			@Override
			public int compare(UidCountBean o1, UidCountBean o2) {
				return o2.getCount() - o1.getCount();
			}
		});
		//输出，保存，数据库 
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\data\\retDay01.txt"));){
			for (UidCountBean bean : list) {
				System.out.println(bean);
				bw.write(bean.toString());
				bw.newLine();
			}
			bw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
