package cn.edu360.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestMain3 {
	public static void main(String[] args) {
		//获取存放用户以及好友列表
		Map<String, List<String>> map = getMap();
		//System.out.println(map.size());
		
		Set<String> keySet = map.keySet();
		//为了使用下标访问，转化为list
		ArrayList<String> list = new ArrayList<>(keySet);
		
		for(int i = 0;i<list.size()-1;i++){//控制轮数的
			//通过下标获取用户
			String uidi = list.get(i);
			//通过用户获取好友列表
			List<String> listi = map.get(uidi);
			
			for(int j = i+1;j<list.size() ;j++){
				String uidj = list.get(j);
				List<String> listj = map.get(uidj);
				//retainAll()   取交集的
				ArrayList<String> newListJ = new ArrayList<>(listj);
				newListJ.retainAll(listi);  
				if(newListJ!=null&&newListJ.size()>0){
					System.out.println(uidi+"和"+uidj+"共同好友是："+newListJ);
					
				}
				
			}
		}
		
	}
	/**
	 * 通过文件获取map   key-uid   value-list列表
	 * @return
	 */
	private static Map<String,List<String>> getMap() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		//1:读文件
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\好友.txt"));){
			String line = null;
			while((line = br.readLine())!=null){
				//System.out.println(line);
				//2：切分
				String[] split = line.split(":");
				String uid = split[0];
				String[] fss = split[1].split(",");
				//System.out.println(Arrays.toString(fss));
				//数组转换为list
				map.put(uid, Arrays.asList(fss)); //Arrays.asList生成的list不可增删
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
