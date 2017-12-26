package javaa.spark.streaming;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;


/**
 * sparkStreaming
 * 近实时的处理
 * 小规模的批处理，使用的还是sparkcore的
 * Discretized Streams(DStreams) 分离的流   spark的流抽象
 * 
 * 在spark-shell里面执行scala文件，使用 ：load /usr/local/files/spark.scala
 * 
 * 
 * 
 * Transformations 
 * map flatmap filter repartition union count reduce countByValue reduceByKey join cogroup
 * transform updateStateByKey
 * 
 * OutputOperations onSDtreams
 * print savaAsTextFiles savaAsObjectFiles savaAsHadoopFiles foreachRDD
 * 
 * 对于保存到hadoop上可以使用上面的例子，保存到数据库可以使用core的形式来写，先使用foreachRDD 然后再对每个RDD进行foreachPartition操作，创建连接等操作，然后再对每条数据进行保存操作
 *
 */
public class WhatStreaming {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		JavaStreamingContext ssc = getSparkStreaming1();
		JavaReceiverInputDStream<String> lines = ssc.socketTextStream("localhost", 9999);
		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterable<String> call(String t) throws Exception {
				return Arrays.asList(t.split(" "));
			}
		});
		JavaPairDStream<String,Long> pairs = words.mapToPair(new PairFunction<String, String, Long>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<String, Long> call(String t) throws Exception {
				return new Tuple2<String, Long>(t, 1L);
			}
		});
		
		JavaPairDStream<String,Long> wordCounts = pairs.reduceByKey(new Function2<Long, Long, Long>(){
			private static final long serialVersionUID = 1L;

			@Override
			public Long call(Long v1, Long v2) throws Exception {
				return v1+v2;
			}
			
		});
		wordCounts.print();
		wordCounts.foreach(new Function<JavaPairRDD<String,Long>, Void>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Void call(JavaPairRDD<String, Long> v1) throws Exception {
				// TODO Auto-generated method stub
				//System.out.println(v1.);
				
				return null;
			}
		});
		
		
		
		//开始执行
		ssc.start();
		ssc.awaitTermination();
		
		
		
	}
	/**
	 * 创建sparkStreaming的两种方式
	 * 
	 */
	public static JavaStreamingContext getSparkStreaming1(){
		SparkConf conf = new SparkConf();
		JavaStreamingContext ssc = new JavaStreamingContext(conf, Durations.seconds(1l));
		return ssc;
	}
	
	public static JavaStreamingContext getSparkStreaming2(){
		SparkConf conf = new SparkConf();
		SparkContext jsc = new SparkContext(conf);//对于java来说，不能使用JavaSparkContext来生成StreamingContext
		JavaStreamingContext ssc = new JavaStreamingContext(conf, Durations.seconds(1l));
		return ssc;
	}
}


