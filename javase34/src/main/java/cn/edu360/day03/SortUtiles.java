package cn.edu360.day03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

public class SortUtiles {
	/**
	 * 根据count进行排序
	 * @param list
	 */
	public static void sortByCount(ArrayList<Entry<String, Integer>> list) {
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o1.getValue()-o2.getValue();
			}
		});
		
	}

}
