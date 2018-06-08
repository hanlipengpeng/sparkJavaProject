package cn.edu360.day05;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 根据给的手机号段归属地规则，计算出总流量最高的省份Top3
 * 1:读数据
 * 2：切分获取手机号 ，流量
 * 3：拿手机号，去上一个map中获取省份
 * 4：把获取的省份和流量保存起来
 * 5：排序
 * 6：保存
 * 
 * @author root
 *
 */
public class TestMain2 {
	public static void main(String[] args) {
		Map<String, String> numProvince = getPNumProvince();
		//System.out.println(numProvince.size());
		
		//保存省份对应流量的数据
		Map<String, Long> map = new HashMap<String, Long>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\http.log"));){
			String line = null;
			//17677347390	http://movie.youku.com 2567 9850
			while((line = br.readLine())!=null){
				//System.out.println(line);
				String[] split = line.split("\t");
				//切分出来手机号，并且截取前七位
				String pNum = split[0].substring(0,7);
				//获取上下行流量
				String[] split2 = split[1].split(" ");
				long up = Long.parseLong(split2[1]);
				long down = Long.parseLong(split2[2]);
				//使用手机号前七位去map中获取省份
				String province = numProvince.get(pNum);
				//把省份和对应的流量数据进行保存
				Long sum = map.getOrDefault(province, 0L);
				sum = sum+up+down;
				map.put(province, sum);
			}
			//排序
			Set<Entry<String,Long>> entrySet = map.entrySet();
			ArrayList<Entry<String,Long>> list = new ArrayList<>(entrySet);
			SortUtiles.sortByLong(list);
			
			//保存，输出
			for(int i = 0;i<Math.min(3, list.size());i++){
				System.out.println(list.get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获取手机号前七位对应的省份map数据
	 * 1:读取数据
	 * 2：切分数据
	 * 3：放map
	 * @return
	 */
	private static Map<String, String> getPNumProvince() {
		Map<String, String> map = new HashMap<String, String>();
		try (BufferedReader br= new BufferedReader(new FileReader("D:\\data\\phone.txt"));){
			String line = null;
			//跳过第一行
			br.readLine();
			//134	1345143	河北	邯郸	移动	056000	0310	130400
			while((line = br.readLine())!=null){
				//System.out.println(line);
				String[] split = line.split("\t");
				String pNum = split[1];
				String province = split[2];
				//System.out.println(province);
				map.put(pNum, province);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

}
