package cn.edu360.day10.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestColabelRun {
	public static void main(String[] args) throws Exception{
		//1:new calable实例
		TestCalable c = new TestCalable();
		//2:放入futureTask
		FutureTask<Integer> futureTask = new FutureTask<>(c);
		//3:放到Thread里面
		Thread t= new Thread(futureTask);
		t.start();
		
		//4:返回值获取
		Integer sum = futureTask.get();
		System.out.println(sum);
		
	}

}
