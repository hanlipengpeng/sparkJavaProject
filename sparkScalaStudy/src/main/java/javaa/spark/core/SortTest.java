package javaa.spark.core;

import java.util.Arrays;

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
 * sort排序，和sort自定义的二次排序
 * 二次排序需要实现Ordered Serializable
 */
public class SortTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("broadcast").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		//sortWordCount(jsc);
		secendarySort(jsc);
		
		jsc.stop();
	}
	
	/**
	 * 二次排序，自定义的排序类是SecondarySortKey
	 */
	private static void secendarySort(JavaSparkContext jsc){
		JavaRDD<String> file = jsc.textFile(SortTest.class.getClassLoader().getResource("sort.txt").getPath());
		JavaPairRDD<SecondarySortKey,String> pairKV = file.mapToPair(new PairFunction<String, SecondarySortKey, String>() {

			@Override
			public Tuple2<SecondarySortKey, String> call(String t)
					throws Exception {
				String[] split = t.split(" ");
				return new Tuple2<SecondarySortKey, String>(new SecondarySortKey(Integer.parseInt(split[0]),Integer.parseInt(split[1])), t);
			}
		});
		JavaRDD<String> sortRDD = pairKV.sortByKey().map(new Function<Tuple2<SecondarySortKey,String>, String>() {

			@Override
			public String call(Tuple2<SecondarySortKey, String> v1)
					throws Exception {
				return v1._2;
			}
		});
		sortRDD.foreach(new VoidFunction<String>() {

			@Override
			public void call(String t) throws Exception {
				System.out.println(t);
				
			}
		});
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

