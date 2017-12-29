package javaa.spark.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * sparkStreaming 状态更新
 * @author rf
 *
 */
public class SparkStreamingUpStatusByKey {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf();
		JavaStreamingContext jssc = new JavaStreamingContext(conf,Durations.seconds(1l));
		
		
		jssc.start();
		jssc.awaitTermination();
	}

}
