package javaa.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by root on 2017/12/13.
 * rdd 持久化
 *
 *
 */
public class CacheTest {
    public static void main(String[] args){
        SparkConf conf = new SparkConf().setAppName("cache").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);
    }
}
