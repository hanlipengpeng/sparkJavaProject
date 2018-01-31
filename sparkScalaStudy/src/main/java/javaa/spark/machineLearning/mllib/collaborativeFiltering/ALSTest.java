package javaa.spark.machineLearning.mllib.collaborativeFiltering;

import javaa.spark.machineLearning.mllib.SVMsTest;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * 协同过滤  推荐算法
 * @author 韩利鹏
 *
 */
public class ALSTest {
    public static void main(String[] args){
        SparkConf conf = new SparkConf().setAppName("svm").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        String path = SVMsTest.class.getClassLoader().getResource("als/test.text").getPath();

    }

}
