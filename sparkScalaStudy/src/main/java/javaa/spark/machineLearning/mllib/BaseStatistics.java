package javaa.spark.machineLearning.mllib;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;

/**
 * 基本统计
 * @author rf
 *
 */
public class BaseStatistics {
	public static void main(String[] args) {
		
		System.out.println(BaseStatistics.class.getClass().getClassLoader().getResource("vectors.txt").getPath());
		SparkConf conf = new SparkConf().setAppName("statistics").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		JavaRDD<Vector> vectorRDD = getVectorRDD(jsc);
		summaryStatistics(vectorRDD);
		jsc.stop();
	}
	
	
	/**
	 * Summary Statistice  汇总统计
	 */
	public static void summaryStatistics(JavaRDD<Vector> vectorRDD){
		MultivariateStatisticalSummary summary = Statistics.colStats(vectorRDD.rdd());
		System.out.println(summary.mean());
		System.out.println(summary.variance());
		System.out.println(summary.numNonzeros());
		
	}
	
	
	public static JavaRDD<Vector> getVectorRDD(JavaSparkContext jsc){
		JavaRDD<String> file = jsc.textFile(BaseStatistics.class.getClass().getClassLoader().getResource("vectors").getPath());
		JavaRDD<Vector> vectorRDD = file.map(new Function<String, Vector>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Vector call(String v1) throws Exception {
				String[] strs = v1.split(",");
				return Vectors.dense(Double.parseDouble(strs[0]),Double.parseDouble(strs[1]),Double.parseDouble(strs[2]),Double.parseDouble(strs[3]));
			}
		});
		return vectorRDD;
	}

}
