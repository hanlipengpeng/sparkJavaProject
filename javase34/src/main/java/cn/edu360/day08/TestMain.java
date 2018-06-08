package cn.edu360.day08;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestMain {
	public static void main(String[] args) {
		//通过url获取doument
		String url = "https://search.51job.com/list/010000,000000,0000,00,9,99,%25E5%25A4%25A7%25E6%2595%25B0%25E6%258D%25AE,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
		String end = "https://search.51job.com/list/010000,000000,0000,00,9,99,%25E5%25A4%25A7%25E6%2595%25B0%25E6%258D%25AE,2,142.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
		String zz = "https://search.51job.com/list/170200,000000,0000,00,9,99,%25E5%25A4%25A7%25E6%2595%25B0%25E6%258D%25AE,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
		
		Document documentByUrl = getDocumentByUrl(zz);
		//System.out.println(documentByUrl);
		Page page = new Page();
		page.setDocument(documentByUrl);
		int count = 0;
		while(true){
			System.out.println("第"+(++count)+"页");
			List<Job> jobByPage = getJobByPage(page);
			//保存到数据库
			for (Job job : jobByPage) {
				System.out.println(job);
			}
			getNextPageUrl(page);
			if(page.isHasNextPage()){
				documentByUrl = getDocumentByUrl(page.getNextPageUrl());
				page.setDocument(documentByUrl);
			}else{
				break;
			}
			//休眠1s   
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	/**
	 * 获取下一页地址
	 */
	public static void getNextPageUrl(Page page){
		Document document = page.getDocument();
		Elements select = document.select(".bk");
		Element element2 = select.get(1);
		Elements select2 = element2.select("a");
		if(select2!=null&&select2.size()>0){//有下一页
			String url = select2.get(0).attr("href");
			page.setNextPageUrl(url);
			page.setHasNextPage(true);
		}else{
			page.setHasNextPage(false);
		}
		/*for (Element element : select) {
			System.out.println(element);
			System.out.println("-----------");
		}*/
	}
	
	/**
	 * 通过page获取job集合
	 */
	public static List<Job> getJobByPage(Page page){
		List<Job> list = new ArrayList<Job>();
		Document document = page.getDocument();
		Elements select = document.select("#resultList .el");
		select.remove(0);
		for (Element element : select) {
			Elements jobElement = element.select(".t1 a");
			String jobName = jobElement.attr("title");
			
			Elements comElement = element.select(".t2 a");
			String comName = comElement.attr("title");
			
			Elements addrElement = element.select(".t3");
			String addr = addrElement.text();
			
			Elements salaryElement = element.select(".t4");
			String salary = salaryElement.text();
			
			Elements dateElement = element.select(".t5");
			String date = dateElement.text();
			
			Job job = new Job();
			job.set(jobName, comName, addr, salary, date);
			list.add(job);
			/*System.out.println(date);
			System.out.println("--------------------");*/
		}
		return list;
	}
	
	
	/**
	 * 通过网址获取document对象
	 * @param url
	 * @return
	 */
	public static Document getDocumentByUrl(String url){
		try {
			Document parse = Jsoup.parse(new URL(url), 4000);
			return parse;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

}
