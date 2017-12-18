package javaa.spark.tuning;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * 序列化机制，spark提供两种
 * 一：使用java的原生态的序列化机制，也就是使用ObjectInputStraem和ObjectOutputStram
 * 二：使用Kryo序列化机制，使用Kryo序列化机制的速度比较快，而且序列化后的数据占用更小的空间，
 * 通常比java小10倍
 * 没有默认使用Kryo序列化机制的原因是，有些类型虽然实现了Seriralizable接口，但是他也不一定能够序列化，
 * 如果需要达到性能更佳，Kryo还要求你在spark应用程序中，对所有你需要的类型进行注册。
 *
 *
 * 什么时候使用Kryo序列化机制。
 * 1：算子函数使用外部大对象的时候，使用Kryo可以减少序列化的速度，还可以减少内存空间的占用
 * 2：一些rdd的shuleful操作。
 */

public class SerializableKryo {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("action")
				.setMaster("local");
		//设置为Kryo序列化机制
		conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
		
		Countter c = new Countter();
		//conf.registerKryoClasses(c.getClass());    ??????????有毛病
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		
		
		
		jsc.stop();
	}
	
	static class Countter implements Serializable{

	}

}
