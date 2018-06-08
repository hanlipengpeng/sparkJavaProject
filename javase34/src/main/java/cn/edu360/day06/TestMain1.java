package cn.edu360.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
1：读数据，切分数据
2：提取ip，时间，url信息
3：封装到javaBean里面
4：放到集合中Map<ip,List<JavaBean>>
5:List的局部排序
6：生成sessionid和oder
7：完成（保存数据）

 */
public class TestMain1 {
	public static void main(String[] args) {
		
		//把相同sessionid的数据存放一起
		Map<String, List<SeesionBean>> sessionMap = new HashMap<String, List<SeesionBean>>();
		
		//存放ip以及对应的sessionBean集合
		Map<String, List<SeesionBean>> map = getMap_ipListSeesion();
		//对map的value进行局部排序
		sortBydate(map);
		//生成sessionid和序号
		makeSessionId(map);
		
		//把相同sessionId的数据进行分组
		for (Entry<String, List<SeesionBean>> entry : map.entrySet()) {
			List<SeesionBean> list = entry.getValue();
			for (SeesionBean seesionBean : list) {
				List<SeesionBean> list2 = sessionMap.getOrDefault(seesionBean.getSessionId(), new ArrayList<SeesionBean>());
				list2.add(seesionBean);
				sessionMap.put(seesionBean.getSessionId(), list2);
			}
		}
		
		//取出第一个和对吼一个sessionBean
		for (Entry<String, List<SeesionBean>> entry : sessionMap.entrySet()) {
			List<SeesionBean> list = entry.getValue();
			SeesionBean first = list.get(0);
			SeesionBean end = list.get(list.size()-1);
			//2017-10-11 08:10:30
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String firstDate = format.format(first.getDate());
			String endDate = format.format(end.getDate());
			long time = (end.getDate().getTime() - first.getDate().getTime())/1000;
			String ret = entry.getKey()+"\t"+first.getIp()+"\t"
					+firstDate+"\t"+endDate+"\t"+first.getUrl()+"\t"
					+end.getUrl()+"\t"+time+"s";
			System.out.println(ret);
		}
		
		
		/*//保存输出
		Set<Entry<String,List<SeesionBean>>> entrySet = sessionMap.entrySet();
		for (Entry<String, List<SeesionBean>> entry : entrySet) {
			List<SeesionBean> list = entry.getValue();
			for (SeesionBean seesionBean : list) {
				System.out.println(seesionBean);
				
			}
			System.out.println("------------------");
		}*/
		
		
	}
	/**
	 * 生成sessionid和序号
	 * @param map
	 */
	private static void makeSessionId(Map<String, List<SeesionBean>> map) {
		Set<Entry<String,List<SeesionBean>>> entrySet = map.entrySet();
		for (Entry<String, List<SeesionBean>> entry : entrySet) {
			//相同ip的sessionbean
			List<SeesionBean> list = entry.getValue();
			//list长度为1
			if(list.size()==1){
				SeesionBean seesionBean = list.get(0);
				String uuid = UUID.randomUUID().toString();
				seesionBean.setSessionId(uuid);
				seesionBean.setOder(1);
			}
			//不等于1的时候
			for(int i = 0;i<list.size()-1;i++){
				SeesionBean bean1 = list.get(i);
				SeesionBean bean2 = list.get(i+1);
				//得到是否是同一个session
				boolean flag = isSameSession(bean1,bean2);
				if(flag){//相同session的时候
					if(bean1.getSessionId()!=null){//有值的时候
						bean2.setSessionId(bean1.getSessionId());
						bean2.setOder(bean1.getOder()+1);
					}else{//没有值的时候
						String sessionId = UUID.randomUUID().toString();
						bean1.setSessionId(sessionId);
						bean1.setOder(1);
						bean2.setSessionId(sessionId);
						bean2.setOder(2);
					}
					
				}else{//session不相同
					if(bean1.getSessionId()!=null){//sessionId有值的时候
						String sessionId = UUID.randomUUID().toString();
						bean2.setSessionId(sessionId);
						bean2.setOder(1);
					}else{//sessionId没有值的时候
						String sessionId1 = UUID.randomUUID().toString();
						bean1.setSessionId(sessionId1);
						bean1.setOder(1);
						String sessionId2 = UUID.randomUUID().toString();
						bean2.setSessionId(sessionId2);
						bean2.setOder(1);
					}
					
				}
			}
			
		}
		
	}
	/**
	 * 判断两个bean是否是同一个session
	 * @param bean1
	 * @param bean2
	 * @return
	 */
	public static boolean isSameSession(SeesionBean bean1, SeesionBean bean2) {
		long date1 = bean1.getDate().getTime();
		long date2 = bean2.getDate().getTime();
		if(date2-date1<=1000*60*30){
			return true;
		}
		return false;
	}
	/**
	 * 对map的value进行局部排序
	 * @param map
	 */
	private static void sortBydate(Map<String, List<SeesionBean>> map) {
		Set<Entry<String,List<SeesionBean>>> entrySet = map.entrySet();
		for (Entry<String, List<SeesionBean>> entry : entrySet) {
			List<SeesionBean> list = entry.getValue();
			//按照时间排序
			Collections.sort(list, new Comparator<SeesionBean>() {
				@Override
				public int compare(SeesionBean o1, SeesionBean o2) {
					
					return o1.getDate().after(o2.getDate())?1:-1;
				}
			});
			
		}
		
	}

	/**
	 * 得到存放ip以及对应的sessionBean集合Map<ip,List<JavaBean>>
	 * @return
	 */
	private static Map<String, List<SeesionBean>> getMap_ipListSeesion() {
		Map<String, List<SeesionBean>> map = new HashMap<String, List<SeesionBean>>();
		try (BufferedReader br = new BufferedReader(new FileReader("D:\\data\\access.log.fensi"));){
			String line = null;
			//58.177.135.108 - - [19/Sep/2013:06:20:33 +0000] "GET /favicon.ico HTTP/1.1" 200 0 "-" "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) CriOS/30.0.1599.12 Mobile
			while((line = br.readLine())!=null){
				//System.out.println(line);
				String ipRegex = "(\\d+\\.){3}\\d+";
				String dateRegex = "\\[.+\\d+\\]";
				String urlRegex = "(POST|GET){1}\\s(\\S)*\\s";
				String ip = getDataByRegex(line,ipRegex);
				Date date = parseDate(getDataByRegex(line,dateRegex));
				String url = getDataByRegex(line,urlRegex);
				//数据过滤清洗
				if(ip!=null&&date!=null&&url!=null){
					SeesionBean bean = new SeesionBean();
					bean.set(ip, date, url);
					//System.out.println(bean);
					List<SeesionBean> list = map.getOrDefault(ip, new ArrayList<SeesionBean>());
					list.add(bean);
					map.put(ip, list);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	/**
	 * 字符串转化为日期
	 * @param dataByRegex
	 * @return
	 */
	public static Date parseDate(String dataByRegex) {
		//[19/Sep/2013:06:20:33 +0000]
		String dateStr = dataByRegex.substring(1, dataByRegex.length()-1);
		//第一个是格式     第二个哪个国家的时间标准
		SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss",Locale.US);
		try {
			Date parse = format.parse(dateStr);
			return parse;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 使用正则匹配数据
	 * @param line
	 * @param ipRegex
	 * @return
	 */
	public static String getDataByRegex(String line, String regex) {
		Pattern compile = Pattern.compile(regex);
		Matcher matcher = compile.matcher(line);
		while(matcher.find()){
			return matcher.group();
		}
		return null;
	}

}
