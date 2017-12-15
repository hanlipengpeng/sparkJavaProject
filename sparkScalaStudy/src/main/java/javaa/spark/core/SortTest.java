package javaa.spark.core;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class SortTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("broadcast").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		sortWordCount(jsc);
		
		
		jsc.stop();
	}
	
	/**
	 * 排序的wordCount
	 */
	private static void sortWordCount(JavaSparkContext jsc){
		JavaRDD<String> file = jsc.textFile(SortTest.class.getClassLoader().getResource("sougou.txt").getPath());
		JavaRDD<String> words = file.flatMap(new FlatMapFunction<String, String>() {

			@Override
			public Iterable<String> call(String t) throws Exception {
				// TODO Auto-generated method stub
				return Arrays.asList(t.split(" "));
			}
		});
		JavaPairRDD<String,Integer> pairRDD = words.mapToPair(new PairFunction<String, String, Integer>() {

			@Override
			public Tuple2<String, Integer> call(String t) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2<String, Integer>(t, 1);
			}
		});
		JavaPairRDD<String,Integer> wordCount = pairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				// TODO Auto-generated method stub
				return v1+v2;
			}
		});
		
		JavaPairRDD<Integer,String> countWord = wordCount.mapToPair(new PairFunction<Tuple2<String,Integer>, Integer, String>() {
			@Override
			public Tuple2<Integer, String> call(Tuple2<String, Integer> t)
					throws Exception {
				return new Tuple2<Integer, String>(t._2, t._1);
			}
		});
		JavaPairRDD<Integer,String> sortRDD = countWord.sortByKey(false);
		sortRDD.foreach(new VoidFunction<Tuple2<Integer,String>>() {
			@Override
			public void call(Tuple2<Integer, String> t) throws Exception {
				System.out.println("word:"+t._2+" count:"+t._1);
			}
			
		});
	}

}

