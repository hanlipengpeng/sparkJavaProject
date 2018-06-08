package cn.edu360.day04;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtiles {
	/**
	 * 根据rate进行排序
	 * @param list
	 */
	public static void sortByRate(List<MovieBean> list) {
		Collections.sort(list, new Comparator<MovieBean>() {

			@Override
			public int compare(MovieBean o1, MovieBean o2) {
				
				return o2.getRate() - o1.getRate();
			}
		});
		
	}
	/**
	 * 根据avg进行排序
	 * @param list
	 */
	public static void sortByAvg(List<UidAvgBean> list) {
		Collections.sort(list, new Comparator<UidAvgBean>() {

			@Override
			public int compare(UidAvgBean o1, UidAvgBean o2) {
				
				return (o2.getAvg()-o1.getAvg())>0?1:-1;
			}
		});
		
	}

}
