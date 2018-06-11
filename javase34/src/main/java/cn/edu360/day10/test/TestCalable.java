package cn.edu360.day10.test;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * 1:继承Thread
 * 2:实现Runnable接口     --->run
 * 
 * callable接口 带有返回值的线程
 * 累加求和
 * @author root
 *
 */
public class TestCalable implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		//相当于 run方法，只是有返回值
		Random random = new Random();
		int sum = 0;
		for(int i = 0;i<100;i++){
			int r = random.nextInt(10)+1;
			//System.out.println(r);
			sum+=r;
		}
		return sum;
	}

}
