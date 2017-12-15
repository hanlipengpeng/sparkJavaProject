package javaa.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

/**
 * Created by root on 2017/12/13.
 * rdd 持久化
 * 直接使用cache
 * 使用persist，自己设定持久化策略
 * 使用的是StorageLevel
 * MEMORY_AND_DISK() 非序列化的java对象的方式持久化在内存中，如果内存无法完全存储rdd，则会持久化到磁盘上，下次从磁盘上读取。
 * DISK_ONLY()
 * MEMORY_ONLY() 非序列化的java对象的方式持久化在内存中，如果内存无法完全存储rdd，则没有持久化的partition则会在下次使用的时候被重新计算
 * MEMORY_ONLY_SER() 同MEMORY_ONLY，但是会使用java的序列化方式，将java对象进行持久化，可以减少内存的开销，但是会增大cpu的开销
 * MEMORY_ONLY_2()  双副本机制
 */
public class CacheTest {
    public static void main(String[] args){
        SparkConf conf = new SparkConf().setAppName("cache").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        JavaRDD<String> file = jsc.textFile("");
        file.cache().persist(StorageLevel.MEMORY_ONLY_2());
    }
}
