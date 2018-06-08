package cn.edu360.day02;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试时间排序
 * @author root
 *
 */
public class TestDateSort {
	public static void main(String[] args) throws Exception {
		String date1 = "2017-08-15";//15/08/2017
		String date2 = "2017-08-15";
		//date1.compareTo(date2);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// dd/MM/yyyy
		Date d1 = format.parse(date1);
		Date d2 = format.parse(date2);
		//时间的比价有after before
		boolean b = d1.before(d2);
		System.out.println(b);
	}

}
