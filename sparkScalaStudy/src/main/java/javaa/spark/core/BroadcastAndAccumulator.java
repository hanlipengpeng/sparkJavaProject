package javaa.spark.core;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;



/**
 * 默认的外部变量，在每个task中都会拷贝一份，跟java中的线程类似，每个task只能操作自己的那份变量副本，多线程中的变量不可见
 * spark提供两种共享变量的方式，一种是Broadcast Variable(广播变量)，另一个是Accumulator(累加变量)
 * Briadcast Variable仅仅是在每个节点上拷贝一份，能减少网络消耗，内存消耗。
 * Accumulator则是多的task公用一份变量，主要是进行累加操作，类似于java的可见性和原子性
 * 自定义累加器实现    need to do
 *
 */
public class BroadcastAndAccumulator {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("broadcast").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		
		notBroadcastT(jsc);
		broadcastT(jsc);
		//accumulatorT(jsc);
		jsc.stop();
	}
	
	/**
	 * 自定义累加器
	 */
	private static void myAccumulator(JavaSparkContext jsc){
		//DATA need to implement
	}
	
	/**
	 * 不使用累加器的错误累加实例
	 */
	
	private static void noAccumulatorT(JavaSparkContext jsc){
		final int sum = 0;
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		listRDD.foreach(new VoidFunction<Integer>() {
			@Override
			public void call(Integer t) throws Exception {
				//sum = sum+t;编译报错
			}
		});
		System.out.println(sum);
	}
	
	/**
	 * accumulator使用
	 */
	private static void accumulatorT(JavaSparkContext jsc){
		final Accumulator sum = jsc.accumulator(0);
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		listRDD.foreach(new VoidFunction<Integer>() {
			@Override
			public void call(Integer t) throws Exception {
				sum.add(t);
			}
		});
		System.out.println(sum);
	}
	
	/**
	 * 不使用broadcast
	 */
	private static void notBroadcastT(JavaSparkContext jsc){
		final int value = 3;
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		JavaRDD<Integer> res = listRDD.map(new Function<Integer, Integer>() {
			@Override
			public Integer call(Integer v1) throws Exception {
				return v1*value;
			}
		});
		res.foreach(new VoidFunction<Integer>() {
			@Override
			public void call(Integer t) throws Exception {
				System.out.println(t);
			}
		});
	}
	
	/**
	 * 使用broadcast共享变量
	 */
	private static void broadcastT(JavaSparkContext jsc){
		final Broadcast<Integer> valueBroadcast = jsc.broadcast(3);
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
		JavaRDD<Integer> listRDD = jsc.parallelize(list);
		JavaRDD<Integer> res = listRDD.map(new Function<Integer, Integer>() {
			@Override
			public Integer call(Integer v1) throws Exception {
				int valuebro = valueBroadcast.value();
				return v1*valuebro;
			}
		});
		res.foreach(new VoidFunction<Integer>() {
			@Override
			public void call(Integer t) throws Exception {
				System.out.println(t);
			}
		});
	}

}
