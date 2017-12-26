package javaa.spark.streaming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import scala.Tuple2;


/**
 * kafka
 * kafka是一个分布式的发布订阅系统
 * 使用的是kafka的高阶api，出故障后出现数据丢失
 *
 */
public class SparkStreamingKafka {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf();
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1l));
		String[] str = args[0].split(",");
		Map<String,Integer> map = new HashMap<>();
		for(int i=0 ; i<str.length ;i++){
			map.put(str[i], 2);
		}
		JavaPairReceiverInputDStream<String,String> messages  = KafkaUtils.createStream(jssc, "zookepper:2181", "group", map,StorageLevel.MEMORY_AND_DISK());
		JavaDStream<String> line = messages.map(new Function<Tuple2<String,String>, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String call(Tuple2<String, String> v1) throws Exception {
				return v1._2;
			}
		});
		
		JavaDStream<String> words = line.flatMap(new FlatMapFunction<String, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterable<String> call(String t) throws Exception {
				return Arrays.asList(t.split(" "));
			}
		});
		
		JavaPairDStream<String,Integer> wordcount = words.mapToPair(new PairFunction<String, String, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<String, Integer> call(String t) throws Exception {
				return new Tuple2<String, Integer>(t, 1);
			}
		});
		
		JavaPairDStream<String,Integer> wordcounts = wordcount.reduceByKey(new Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1+v2;
			}
		});
		
		wordcounts.print();
		
		jssc.start();
		jssc.awaitTermination();
	}

}
