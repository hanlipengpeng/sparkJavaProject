package cn.edu360.day02;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SortUtiles {
	
	/**
	 * 根据version进行排序
	 * @param value
	 */
	public static void sortByVersion(List<String> value) {
		Collections.sort(value, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
	}
	/**
	 * 按照时间排序
	 * @param list
	 */
	public static void sortByDate(List<AppBean> list) {
		Collections.sort(list, new Comparator<AppBean>() {
			@Override
			public int compare(AppBean o1, AppBean o2) {
				String date1 = o1.getDate();
				String date2 = o2.getDate();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date d1 = format.parse(date1);
					Date d2 = format.parse(date2);
					return d1.after(d2)?1:-1;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		
	}

}
