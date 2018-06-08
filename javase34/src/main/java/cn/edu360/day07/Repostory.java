package cn.edu360.day07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 把现有的位置信息进行转化为本地仓库
 * @author root
 *
 */
public class Repostory {
	public static void main(String[] args){
		try {
			//new FileReader("D:\\data\\bj-poi-1.csv")
			BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\data\\repostry.tsv"));
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\data\\bj-poi-1.csv"), "gbk"));//,分割  tsv \t
			String line = null;
			while((line = br.readLine())!=null){
				try {
					//System.out.println(line);  经纬度，城市，区，具体地址
					String[] split = line.split(",");
					String addr = split[0];
					double localx = Double.parseDouble(split[2]);
					double localy = Double.parseDouble(split[3]);
					String city = split[5];
					String district = split[7];
					//System.out.println(district);
					String geoHashCode = Utiles.getGeoHash(localy, localx);
					String ret = geoHashCode+"\t"+"北京市"+"\t"+city+"\t"+district+"\t"+addr;
					bw.write(ret);
					bw.newLine();
				} catch (NumberFormatException e) {
					e.printStackTrace();
					continue;
					
				}
			}
			bw.flush();
			bw.close();
			br.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
