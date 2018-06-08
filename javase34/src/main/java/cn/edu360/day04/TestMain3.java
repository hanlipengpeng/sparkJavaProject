package cn.edu360.day04;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestMain3 {
	public static void main(String[] args) {
		//使用第一个问题的getMap方法，直接获取到uid以及对应的Bean集合
		Map<String, List<MovieBean>> map = TestMain1.getMap();
		
		//存放uidAvg
		List<UidAvgBean> list = new ArrayList<UidAvgBean>();
		
		//遍历map，并对其value的rate求取平均值
		Set<Entry<String,List<MovieBean>>> entrySet = map.entrySet();
		for (Entry<String, List<MovieBean>> entry : entrySet) {
			String key = entry.getKey();
			List<MovieBean> value = entry.getValue();
			//求取平局值
			float avg = getAvg(value);
			//System.out.println(avg);
			//System.out.println("uid:"+key+",avg:"+String.format("%.2f", avg));
			
			//封装到bean  --->保存到list集合中
			UidAvgBean bean = new UidAvgBean();
			bean.set(key, avg);
			list.add(bean);
		}
		SortUtiles.sortByAvg(list);
		
		//保存输出 保存到数据库
		for (int i = 0 ; i<Math.min(3, list.size()) ; i++) {
			UidAvgBean uidAvgBean = list.get(i);
			System.out.println("uid:"+uidAvgBean.getUid()+",avg:"+String.format("%.2f", uidAvgBean.getAvg()));
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
