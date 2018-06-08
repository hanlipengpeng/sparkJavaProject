package cn.edu360.day04;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestMain2 {
	public static void main(String[] args) {
		//使用第一个问题的getMap方法，直接获取到uid以及对应的Bean集合
		Map<String, List<MovieBean>> map = TestMain1.getMap();
		//遍历map，并对其value的rate求取平均值
		Set<Entry<String,List<MovieBean>>> entrySet = map.entrySet();
		for (Entry<String, List<MovieBean>> entry : entrySet) {
			String key = entry.getKey();
			List<MovieBean> value = entry.getValue();
			//求取平局值
			float avg = getAvg(value);
			//System.out.println(avg);
			System.out.println("uid:"+key+",avg:"+String.format("%.2f", avg));
		}
		
	}
	/**
	 * 遍历list求平均值
	 * @param value
	 * @return
	 */
	private static float getAvg(List<MovieBean> list) {
		int sum = 0;
		for (MovieBean movieBean : list) {
			sum+=movieBean.getRate();
		}
		//int / int -->int
		float avg = sum*1.0f/list.size();
		return avg;
	}

}
