package javaa.spark.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.flume.FlumeUtils;
import org.apache.spark.streaming.flume.SparkFlumeEvent;

/**
 * flume使用
 * 两种方式，一种push主动获取的形式   pull被动的获取数据
 *  pull被动的获取数据，需要先开启spark程序，
 *  pull使用的是avro的形式进行传输的
 *
 */
public class SparkStramingFlume {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf();
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1l));
		
		JavaReceiverInputDStream<SparkFlumeEvent> flumeStream = FlumeUtils.createStream(jssc, "master", 9999);//host选择集群中的一个主机
		flumeStream.count().map(new Function<Long, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String call(Long v1) throws Exception {
				return "Received"+v1+"flume events";
			}
		}).print();
		
		jssc.start();
		jssc.awaitTermination();
		
	}

}

/**
 * flume的配置
agent.sinks = avroSink
agent.sinks.avroSink.type = avro
agent.sinks.avroSink.channel = memoryChannel
agent.sinks.avroSink.hostname = <chosen machine's hostname>
agent.sinks.avroSink.port = <chosen port on the machine> 
 */
