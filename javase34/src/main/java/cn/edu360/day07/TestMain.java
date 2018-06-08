package cn.edu360.day07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.edu360.day07.model.AddrList;
import cn.edu360.day07.model.BikeBean;
import cn.edu360.day07.model.ResultBean;

import com.alibaba.fastjson.JSON;

public class TestMain {
	static Map<String, String> map = null;
	static {
		map = loadData();
	}
	
	public static void main(String[] args) {
		//Map<String, String> map = loadData();
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\bikes.log"));){
			String line = null;
			while((line = br.readLine())!=null){
				//System.out.println(line);
				BikeBean bean = JSON.parseObject(line, BikeBean.class);
				double lat = Double.parseDouble(bean.getLatitude());
				double lng = Double.parseDouble(bean.getLongitude());
				//查找位置
				String addr = findAddr(lat,lng);
				//做相应的业务处理
				System.out.println(addr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查找位置
	 * 1：本地查找
	 * 2：网络查找
	 * @param lat
	 * @param lng
	 * @return
	 */
	private static String findAddr(double lat, double lng) {
		String addr = findAddrByLocal(lat,lng);
		if(addr==null){//本地没找到
			addr = findAddrByNet(lat,lng);
		}
		return addr;
	}
	/**
	 * 从网络中查找
	 * @param lat
	 * @param lng
	 * @return
	 */
	private static String findAddrByNet(double lat, double lng) {
		String json = Utiles.getJsonByNet(lat, lng);
		ResultBean resultBean = JSON.parseObject(json, ResultBean.class);
		String geoHash = Utiles.getGeoHash(lat, lng);
		AddrList addrList = resultBean.getAddrList()[0];
		String province_city = addrList.getAdmName();
		String[] split = province_city.split(",");
		if(split.length>2){
			String province = split[0];
			String city = split[1];
			String district = split[2];
			String ret = geoHash+"\t"+province+"\t"+city+"\t"+district+"\t"+addrList.getName();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\data\\repostry.tsv",true));){
				bw.write(ret);
				bw.newLine();
				bw.flush();
				System.err.println(ret);
				//更新内存
				map.put(geoHash, ret);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ret;
		}
		return null;
	}

	/**
	 * 从本地查找
	 * @param lat
	 * @param lng
	 * @return
	 */
	private static String findAddrByLocal(double lat, double lng) {
		String geoHash = Utiles.getGeoHash(lat, lng);
		String addr = map.get(geoHash);
		return addr;
	}

	/**
	 * 把本地的仓库数据（生成的tsv）加载过来，形成Map数据
	 * @return
	 */
	public static Map<String, String> loadData(){
		Map<String, String> map = new HashMap<String, String>();
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\repostry.tsv"));){
			String line = null;
			while((line = br.readLine())!=null){
				//System.out.println(line);
				String geoHashCode = line.split("\t")[0];
				map.put(geoHashCode, line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
