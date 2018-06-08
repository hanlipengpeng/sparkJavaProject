package cn.edu360.day08.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

public class TestJsoup {
	static Document dom = null;
	
	public static void main(String[] args) throws Exception {
		
	}
	
	/**
	 * 获取节点的内容
	 * 1:获取标签对中间的值    text方法获取
	 * 2：获取属性值 attribute attr("标签属性")
	 */
	@Test
	public void getContent(){
		//<span class="list" height="18">简</span>
		Elements select = dom.select("#resultList .el .t2 a");
		for (Element element : select) {
			element.parent();
			element.children();
			element.nextSibling();
			element.siblingElements();
			element.previousElementSibling();
			System.out.println(element.text());
			System.out.println(element.attr("href"));
			System.out.println("**************************");
		}
		
	}
	
	
	/**
	 * 查找节点：
	 * 1：通过id
	 * 2：通过class
	 * 3：通过tag（标签）
	 * 
	 * 通用的select    #id      .class     body      (上下级连续查找，定位使用 空格)  
	 */
	
	@Test
	public void getDocumentCh(){
		//通过id查找     Element和Document有父子关系     单个节点
		Element elementById = dom.getElementById("languagelist");
		//System.out.println(elementById);
		
		//通过class查找节点         Elements和element的关系，集合      多个节点
		Elements elementsByClass = dom.getElementsByClass("el");
		//System.out.println(elementsByClass);
		/*for (Element element : elementsByClass) {
			System.out.println(element);
			System.out.println("-------------------------------");
		}*/
		
		//通过标签查找
		Elements elementsByTag = dom.getElementsByTag("body");
		//System.out.println(elementsByTag);
		/*for (Element element : elementsByTag) {
			Element elementById2 = element.getElementById("languagelist");
			Elements elementsByClass2 = elementById2.getElementsByClass("tle");
			System.out.println(elementsByClass2);
		}*/
		Element element = elementsByTag.get(0);
		//System.out.println(element);
		// .class    #id    tag
		Elements select = dom.select("body #languagelist .tle");
		/*for (Element element2 : select) {
			System.out.println(element2);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
		}*/
		
	}
	/**
	 * 获取文档对象 document
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@Before
	public void getDocument() throws MalformedURLException, IOException {
		String jburl = "https://search.51job.com/list/010000,000000,0000,00,9,99,%25E5%25A4%25A7%25E6%2595%25B0%25E6%258D%25AE,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
		URL url = new URL(jburl);
		//document对象就是网站源代码
		dom = Jsoup.parse(url, 4000);
		//System.out.println(dom);
		//return dom;
	}

}
