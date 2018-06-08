package cn.edu360.day02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestMain {
	public static void main(String[] args) throws Exception {
		//获取相同key以及对应的version集合
		Map<String, List<String>> map = getMapInfo();
		//存储封装好的AppBean
		List<AppBean> list = new ArrayList<AppBean>();
		
		//5：对map中的value进行局部排序
		Set<Entry<String,List<String>>> entrySet = map.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
			//获取key   value
			String key = entry.getKey();
			List<String> value = entry.getValue();
			//局部排序
			SortUtiles.sortByVersion(value);
			if(value.size()>1){
				
				//6：获取最大值和最小值
				String minVersion = value.get(0);
				String maxVersion = value.get(value.size()-1);
				//7:重新切分key
				String[] split = key.split(",");
				String date = split[0];
				String userName = split[1];
				String appName = split[2];
				String from = split[3];
				
				//8：封装为bean，放进list集合
				AppBean appBean = new AppBean();
				appBean.set(date, userName, appName, from, minVersion, maxVersion);
				list.add(appBean);
			}
		}
		
		//9：按照时间排序
		SortUtiles.sortByDate(list);
		//create table app(date varchar(100),userName varchar(100),appName varchar(100),fromm varchar(100),minVersion varchar(100),maxVersion varchar(100));
		//10：保存到文件或者数据库    
		ComboPooledDataSource source = new ComboPooledDataSource();
		QueryRunner run = new QueryRunner(source);
		String sql = "insert into app values (?,?,?,?,?,?)";
		//问题都会有哪些：表明，库名是否对应   字符集问题   create database mydb charset = utf8;
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\data\\appResult.txt"));){
			for (AppBean bean : list) {
				run.update(sql, bean.getDate(),bean.getUserName(),bean.getAppName(),bean.getFrom(),bean.getMinVersion(),bean.getMaxVersion());
				System.out.println(bean);
				bw.write(bean.toString());
				bw.newLine();
			}
			bw.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		//System.out.println(map);
		
	}
	/**
	 * 获取相同key以及对应的version集合       的map
	 */
	private static Map<String, List<String>> getMapInfo() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		//1:读取数据        bom可能会出现数据相同，但是不能够归到同意key中
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\app.txt"));){
			String line ;
			//2017-08-14,涛哥,共享女友,360应用,天津,v1.2
			while((line = br.readLine())!=null){
				//System.out.println(line);
				//2:数据切分
				String[] split = line.split(",");
				String date = split[0];
				String userName = split[1];
				String appName = split[2];
				String from = split[3];
				String version = split[5];
				//3:进行联合key
				String key = date+","+userName+","+appName+","+from;
				//4:存放到map中
				if(map.containsKey(key)){//之前有值存放了
					List<String> list = map.get(key);
					list.add(version);
					map.put(key, list);//不put的时候也是可以的
				}else{
					List<String> list = new ArrayList<>();
					list.add(version);
					map.put(key, list);//必须要放回去
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
