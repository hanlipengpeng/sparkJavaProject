package cn.edu360.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class IpUtile {
	//目的是为了减少读文件的次数（只加载一次）
	static List<IpBean> list = null;
	static{
		list = loadIpData();
	}
	
	public static void main(String[] args) {
		/*Long strIpToLingIp = strIpToLingIp("1.0.3.255");
		System.out.println(strIpToLingIp);*/
		List<IpBean> loadIpData = loadIpData();
		for (IpBean ipBean : loadIpData) {
			System.out.println(ipBean);
		}
		//System.out.println(size);
	}
	
	/**
	 * 字符串ip转化为长整型ip    位移运算
	 * @param ip
	 * @return
	 */
	public static Long strIpToLingIp(String ip){
		if(ip==null){
			return 0L;
		}
		Long newIp = 0L;
		String[] split = ip.split("\\.");
		for(int i = 0;i<=3;i++){
			Long ll = Long.parseLong(split[i]);
			newIp |= ll<<((3-i)<<3);
		}
		return newIp;
	}
	
	/**
	 * 通过文件加载数据，封装成list集合
	 */
	public static List<IpBean> loadIpData(){
		//存放IpBean集合
		List<IpBean> list = new ArrayList<IpBean>();
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\ip.txt"));){
			String line = null;
			
			//117.8.0.0|117.8.80.255|1963458560|1963479295|亚洲|中国|天津|天津||联通|120100|China|CN|117.190182|39.125596
			while((line = br.readLine())!=null){
				//System.out.println(line);
				String[] split = line.split("\\|");
				String startIp = split[0];
				String endIp = split[1];
				Long startDecIp = Long.parseLong(split[2]);
				Long endDecIp = Long.parseLong(split[3]);
				String province = split[6];
				String city = split[7];
				String optioner = split[9];
				//System.out.println(optioner);
				IpBean bean = new IpBean();
				bean.set(startIp, endIp, startDecIp, endDecIp, province, city, optioner);
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 通过ip获取相应的IpBean
	 * @param ip 
	 * @return
	 */
	@Deprecated
	public static IpBean getBeanByIp(Long ip) {
		
		for (IpBean ipBean : list) {
			if(ip<=ipBean.getEndDecIp()&&ip>=ipBean.getStartDecIp()){
				return ipBean;
			}
		}
		return null;
	}
	
	/**
	 * 通过二分查找获取bean
	 * @return
	 */
	public static IpBean getBeanByIpNew(Long ip){
		int start = 0;
		int end = list.size()-1;
		while(start<=end){
			int middle = (start+end)/2;
			IpBean ipBean = list.get(middle);
			if(ip<=ipBean.getEndDecIp()&&ip>=ipBean.getStartDecIp()){
				return ipBean;
			}
			if(ip<ipBean.getStartDecIp()){
				end = middle-1;
			}
			if(ip>ipBean.getEndDecIp()){
				start = middle+1;
			}
		}
		return null;
	}

}
