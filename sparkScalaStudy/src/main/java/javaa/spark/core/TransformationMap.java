package javaa.spark.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

/**
 * map flatMap filter reduceByKey groupByKey sortByKey的用法
 * join 和 cogroup两者的用法区别
 * @author peng
 *
 */
public class TransformationMap {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("map").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		//groupBykey(jsc);
		//reduceByKey(jsc);
		//sortByKey(jsc);
		join(jsc);
		cogroup(jsc);
		jsc.stop();
		
	}

	//join
	private static void join(JavaSparkContext jsc){
		List<Tuple2<Integer,String>> studentList = Arrays.asList(new Tuple2<Integer,String>(1,"tom"),
				new Tuple2<Integer,String>(2,"marry"),
				new Tuple2<Integer,String>(3,"xiaopeng"),
				new Tuple2<Integer,String>(3,"xiaopeng"));
		List<Tuple2<Integer,Integer>> scoresList = Arrays.asList(new Tuple2<Integer,Integer>(1,59),
				new Tuple2<Integer,Integer>(2,89),
				new Tuple2<Integer,Integer>(3,100));
		JavaPairRDD<Integer,String> studentRDD = jsc.parallelizePairs(studentList);
		JavaPairRDD<Integer,Integer> scoresRDD = jsc.parallelizePairs(scoresList);
		JavaPairRDD<Integer,Tuple2<String,Integer>> joinRDD = studentRDD.join(scoresRDD);
		joinRDD.foreach(new VoidFunction<Tuple2<Integer,Tuple2<String,Integer>>>() {

			@Override
			public void call(Tuple2<Integer, Tuple2<String, Integer>> t)
					throws Exception {
				System.out.println(t._1+" "+t._2._1+" "+t._2()._2);
			}
		});
	}
	
	//cogroup
	private static void cogroup(JavaSparkContext jsc){
		List<Tuple2<Integer,String>> studentList = Arrays.asList(new Tuple2<Integer,String>(1,"tom"),
				new Tuple2<Integer,String>(2,"marry"),
				new Tuple2<Integer,String>(3,"xiaopeng"),
				new Tuple2<Integer,String>(3,"xiaopeng"));
		List<Tuple2<Integer,Integer>> scoresList = Arrays.asList(new Tuple2<Integer,Integer>(1,59),
				new Tuple2<Integer,Integer>(2,89),
				new Tuple2<Integer,Integer>(3,100));
		JavaPairRDD<Integer,String> studentRDD = jsc.parallelizePairs(studentList);
		JavaPairRDD<Integer,Integer> scoresRDD = jsc.parallelizePairs(scoresList);
		JavaPairRDD<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> joinRDD = studentRDD.cogroup(scoresRDD);
		joinRDD.foreach(new VoidFunction<Tuple2<Integer,Tuple2<Iterable<String>,Iterable<Integer>>>>() {

			@Override
			public void call(
					Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> t)
					throws Exception {
				System.out.println(t._1);
				Iterator<String> itStr = t._2._1.iterator();
				while(itStr.hasNext()){
					System.out.println(itStr.next());
				}
				Iterator<Integer> itInt = t._2._2.iterator();
				while(itInt.hasNext()){
					System.out.println(itInt.next());
				}
				System.out.println("========================");
				
			}
		});
	}
	
	//sortByKey
	private static void sortByKey(JavaSparkContext jsc){
		List<Tuple2<String,Integer>> clzz = Arrays.asList(new Tuple2<String,Integer>("class01",90),
				new Tuple2<String,Integer>("class01",80),
				new Tuple2<String,Integer>("class02",89),
				new Tuple2<String,Integer>("class02",98),
				new Tuple2<String,Integer>("class01",87));
		JavaPairRDD<String,Integer> pairRDD = jsc.parallelizePairs(clzz);
		JavaPairRDD<Integer,String> pairRDD2 = pairRDD.mapToPair(new PairFunction<Tuple2<String,Integer>, Integer, String>() {

			@Override
			public Tuple2<Integer, String> call(Tuple2<String, Integer> t)
					throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2<Integer,String>(t._2,t._1);
			}
		});
		JavaPairRDD<Integer,String> sort = pairRDD2.sortByKey(false);
		sort.foreach(new VoidFunction<Tuple2<Integer,String>>() {
			@Override
			public void call(Tuple2<Integer, String> t) throws Exception {
				System.out.println(t._1()+"  "+t._2);
			}
		});
	}
	
	
	//reduceByKey
	private static void reduceByKey(JavaSparkContext jsc){
		List<Tuple2<String,Integer>> clzz = Arrays.asList(new Tuple2<String,Integer>("class01",90),
				new Tuple2<String,Integer>("class01",80),
				new Tuple2<String,Integer>("class02",89),
				new Tuple2<String,Integer>("class02",98),
				new Tuple2<String,Integer>("class01",87));
		JavaPairRDD<String,Integer> pairRDD = jsc.parallelizePairs(clzz);
		JavaPairRDD<String,Integer> reduceByKey = pairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				// TODO Auto-generated method stub
				return v1+v2;
			}
		});
		reduceByKey.foreach(new VoidFunction<Tuple2<String,Integer>>() {

			@Override
			public void call(Tuple2<String, Integer> t) throws Exception {
				System.out.println(t._1()+" " +t._2);
				
			}
		});
	}
	
	//对班级进行聚合
	private static void groupBykey(JavaSparkContext jsc){
		List<Tuple2<String,Integer>> clzz = Arrays.asList(new Tuple2<String,Integer>("class01",90),
				new Tuple2<String,Integer>("class01",80),
				new Tuple2<String,Integer>("class02",89),
				new Tuple2<String,Integer>("class02",98),
				new Tuple2<String,Integer>("class01",87));
		JavaPairRDD<String,Integer> pairRDD = jsc.parallelizePairs(clzz);
		JavaPairRDD<String,Iterable<Integer>> group = pairRDD.groupByKey();
		group.foreach(new VoidFunction<Tuple2<String,Iterable<Integer>>>() {

			@Override
			public void call(Tuple2<String, Iterable<Integer>> t)
					throws Exception {
				System.out.println(t._1());
				Iterator<Integer> it = t._2().iterator();
				while(it.hasNext()){
					System.out.println(it.next());
				}
				System.out.println("=======================");
			}
		});
	}
	
	//元素*2
	private static void map(JavaSparkContext jsc){
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);
		JavaRDD<Integer> numberRDD = jsc.parallelize(numbers);
		JavaRDD<Integer> cheng2 = numberRDD.map(new Function<Integer, Integer>() {

			@Override
			public Integer call(Integer v1) throws Exception {
				// TODO Auto-generated method stub
				return v1*2;
			}
		});
		cheng2.foreach(new VoidFunction<Integer>() {
			
			@Override
			public void call(Integer t) throws Exception {
				System.out.println(t);
				
			}
		});
	}
	
	//过滤出集合中的偶数
	private static void filter(JavaSparkContext jsc){
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);
		JavaRDD<Integer> numberRDD = jsc.parallelize(numbers);
		JavaRDD<Integer> filernum = numberRDD.filter(new Function<Integer, Boolean>() {

			@Override
			public Boolean call(Integer v1) throws Exception {
				return v1%2==0;
			}
		});
		filernum.foreach(new VoidFunction<Integer>() {

			@Override
			public void call(Integer t) throws Exception {
				System.out.println(t);
				
			}
		});
	}
	
	//将行拆分为单词
	private static void flatmap(JavaSparkContext jsc){
		List<String> numbers = Arrays.asList("hello tom","hi xioaming","hello java","hello java","hello scala");
		JavaRDD<String> lineRDD = jsc.parallelize(numbers);
		JavaRDD<String> wordRDD = lineRDD.flatMap(new FlatMapFunction<String, String>() {

			@Override
			public Iterable<String> call(String t) throws Exception {
				// TODO Auto-generated method stub
				return Arrays.asList(t.split(" "));
			}
		});
		wordRDD.foreach(new VoidFunction<String>() {

			@Override
			public void call(String t) throws Exception {
				System.out.println(t);
				
			}
		});
	}
	
	
}
