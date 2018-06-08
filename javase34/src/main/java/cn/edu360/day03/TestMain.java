package cn.edu360.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 1:读数据
 * 2：切分数据
 * 3：取出ip（192.168.2.23）    -->转换为整数（73278387）
 * 4：获取想用的省份
 * 5：添加到对应的省分中计数     Map<省份，计数>    --->Map<String,Integer>
 * 6:根据数量排序（访问量排序）
 *
 * 1:程序抛出异常
 * 2：死循环    死在哪里了
 */
public class TestMain {
	public static void main(String[] args) {
		//存放省份以及对应的访问量
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		//1:读数据
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\access.log"));){
			String line = null;
			//20090121001940054826000|125.213.101.43|www.86sel.com|/html/13/200901/20-169376.html|Mozilla/4.0 (co
			while((line = br.readLine())!=null){
				//System.out.println(line);
				//2：切分数据
				String strIp = line.split("\\|")[1];
				//3：取出ip（192.168.2.23）    -->转换为整数（73278387）
				Long ip = IpUtile.strIpToLingIp(strIp);
				
				IpBean bean = IpUtile.getBeanByIpNew(ip);
				//System.out.println(bean);
				String province = bean.getProvince();
				
				//5：添加到对应的省分中计数     Map<省份，计数>    --->Map<String,Integer>
				//第一个参数是key值，第二个是默认值，，，，如果能够通过key获取到value则直接返回value，不过相对应的value，则返回默认值   1.8之后才有的
				Integer count = map.getOrDefault(province, 0);
				count++;
				map.put(province, count);
				/*if(map.containsKey(province)){
					Integer count = map.get(province);
					count++;
					map.put(province, count);
				}else{
					map.put(province, 1);
				}*/
			}
			//6:根据数量排序（访问量排序）
			Set<Entry<String,Integer>> entrySet = map.entrySet();
			ArrayList<Entry<String,Integer>> list = new ArrayList<>(entrySet);
			SortUtiles.sortByCount(list);
			for (Entry<String, Integer> entry : list) {
				System.out.println(entry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
