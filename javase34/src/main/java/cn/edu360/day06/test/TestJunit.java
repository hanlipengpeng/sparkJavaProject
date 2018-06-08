package cn.edu360.day06.test;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.edu360.day06.SeesionBean;
import cn.edu360.day06.TestMain1;

public class TestJunit {
	public static void main(String[] args) {
		
	}
	
	@Before
	public void testBefore(){
		System.out.println("提前加载的");
	}
	
	@Test
	public void testIsSameSession(){
		SeesionBean bean1 = new SeesionBean();
		bean1.setDate(new Date());
		
		SeesionBean bean2 = new SeesionBean();
		bean2.setDate(new Date(new Date().getTime() +1000*60*32));
		boolean flag = TestMain1.isSameSession(bean1, bean2);
		if(!flag){
			System.out.println("test----");
		}else{
			throw new RuntimeException("error");
		}
		//System.out.println("----main");
	}
	
	@Test
	public void testJunit(){
		String line = "58.177.135.108 - - [19/Sep/2013:06:20:33 +0000]";
		String regex = "(\\d+\\.){2}\\d+";
		String dataByRegex = TestMain1.getDataByRegex(line, regex);
		if(dataByRegex.equals("58.177.135.108")){
			System.out.println("ok");
		}else{
			throw new RuntimeException("error");
		}
	}
	
	@After
	public void testAfter(){
		System.out.println("test之后执行的");
	}

}
