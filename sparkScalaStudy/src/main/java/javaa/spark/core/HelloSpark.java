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

public class HelloSpark {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("hello").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		JavaRDD<String> file = jsc.textFile(HelloSpark.class.getClassLoader().getResource("sougou.txt").getPath());
		JavaRDD<String> words= file.flatMap(new FlatMapFunction<String, String>() {

			private static final long serialVersionUID = 1L;
			@Override
			public Iterable<String> call(String t) throws Exception {
				return Arrays.asList(t.split(" "));
			}
		});
		
		JavaPairRDD<String,Integer> pair = words.mapToPair(new PairFunction<String, String, Integer>() {

			@Override
			public Tuple2<String, Integer> call(String t) throws Exception {
				
				return new Tuple2<String, Integer>(t, 1);
			}
		});
		
		JavaPairRDD<String,Integer> reduce = pair.reduceByKey(new Function2<Integer, Integer, Integer>(){
			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}});
		reduce.foreach(new VoidFunction<Tuple2<String,Integer>>() {
			
			@Override
			public void call(Tuple2<String, Integer> t) throws Exception {
				System.out.println("word:"+t._1()+" count:"+t._2());
			}
		});
		jsc.close();
	}

}
