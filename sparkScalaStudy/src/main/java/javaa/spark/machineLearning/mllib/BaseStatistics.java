package javaa.spark.machineLearning.mllib;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.DoubleFunction;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.mllib.stat.test.ChiSqTestResult;

import scala.Tuple2;

/**
 * 基本统计
 * @author rf
 *
 */
public class BaseStatistics {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("statistics").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		jsc.setLogLevel("WARN");
		JavaRDD<Vector> vectorRDD = getVectorRDD(jsc);
		//summaryStatistics(vectorRDD);
		//correlations(jsc,vectorRDD);
		stratifiedSampling(jsc);
		
		jsc.stop();
	}
	
	
	/**
	 * Summary Statistice  汇总统计
	 */
	public static void summaryStatistics(JavaRDD<Vector> vectorRDD){
		MultivariateStatisticalSummary summary = Statistics.colStats(vectorRDD.rdd());
		//a dense vector containing the mean value for each column    包含每个列平均值的稠密向量。
		System.out.println(summary.mean());
		//column-wise variance   列的方差
		System.out.println(summary.variance());
		//number of nonzeros in each column 在每一列的矩阵的非零元素数
		System.out.println(summary.numNonzeros());
	}
	
	
	/**
	 * 相关性统计
	 * pearson 和 spearman相关度
	 * @param vectorRDD
	 */
	public static void correlations(JavaSparkContext jsc,JavaRDD<Vector> vectorRDD){
		List<Double> x = Arrays.asList(new Double[]{3.0,4.0,5.0,4.0});
		List<Double> y = Arrays.asList(new Double[]{3.0,4.0,5.0,5.0});
		JavaDoubleRDD seriesX = jsc.parallelize(x).mapToDouble(new DoubleFunction<Double>(){
			private static final long serialVersionUID = 1L;
			@Override
			public double call(Double t) throws Exception {
				return t;
			}});
		JavaDoubleRDD seriesY = jsc.parallelize(x).mapToDouble(new DoubleFunction<Double>(){
			private static final long serialVersionUID = 1L;
			@Override
			public double call(Double t) throws Exception {
				return t;
			}});
		//method is not specified, Pearson's method will be used by default.     specified 明确确定
		Double correlation = Statistics.corr(seriesX.srdd(), seriesY.srdd(),"pearson");
		
		// If a method is not specified, Pearson's method will be used by default.
		Matrix correlMatrix = Statistics.corr(vectorRDD.rdd(),"pearson");
		System.out.println(correlation);
		System.out.println(correlMatrix);
	}
	
	
	/**
	 * Stratified Sampling  分层抽样
	 * sampleByKey是不严格按照比例抽样的，
	 * sampleByKeyExact 严格按照比例进行抽样。
	 * 
	 */
	public static void stratifiedSampling(JavaSparkContext jsc){
		List<String> list = Arrays.asList("1","1","1","1","1","1","1","1","1","1","2","2","2","2","2","2","2","2","2","2","2","2","3","3","3","3","3");
		JavaPairRDD<String,String> data = jsc.parallelize(list).mapToPair(new PairFunction<String, String, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<String, String> call(String t) throws Exception {
				return new Tuple2<String, String>(t, t);
			}
		});
		
		Map<String, Object> fractions = new HashMap<String, Object>();
		fractions.put("1", 0.5);
		fractions.put("2", 0.5);
		fractions.put("3", 0.5);
		//exactly 确切的，精确地     boolean值是代表是否有放回的抽样
		JavaPairRDD<String,String> approxSample = data.sampleByKey(false, fractions);
		JavaPairRDD<String,String> exactSample = data.sampleByKeyExact(false, fractions);
		
		approxSample.foreach(new VoidFunction<Tuple2<String,String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call(Tuple2<String, String> t) throws Exception {
				System.out.println(t._1);
			}
		});
		System.out.println("---------------------------------------------");
		exactSample.foreach(new VoidFunction<Tuple2<String,String>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call(Tuple2<String, String> t) throws Exception {
				System.out.println(t._1);
			}
		});
	}
	
	
	/**
	 * Hypothesis testing
	 * 假设检验
	 * 有点懵逼，不知道对一个向量进行假设检验是按照什么标准来的
	 */
	public static void hypothesisTesting(){
		//a vector composed of the frequencies of events  由事件频率组成的向量
		Vector vec = Vectors.dense(new double[]{1.0,2.0,3.0,4.0});
		// 计算拟合优度。如果第二个测试向量不作为参数提供，        则测试将执行一个均匀分布。
		ChiSqTestResult goodnessOfFitTestResult = Statistics.chiSqTest(vec);
		//summary of the test including the p-value, degrees of freedom...   该测试包括P总结，自由度…
		System.out.println(goodnessOfFitTestResult);
		
		Matrix mat = null; // a contingency matrix
		ChiSqTestResult independenceTestResult = Statistics.chiSqTest(mat);
		System.out.println(independenceTestResult);
		
		JavaRDD<LabeledPoint> obs = null;// an RDD of labeled points
		ChiSqTestResult[] featureTestResults = Statistics.chiSqTest(obs.rdd());
		int i = 1;
		for (ChiSqTestResult result : featureTestResults) {
		    System.out.println("Column " + i + ":");
		    System.out.println(result); // summary of the test
		    i++;
		}
		
	}
	
	
	
	/**
	 * 根据文本生成向量
	 * @param jsc
	 * @return 向量rdd
	 */
	public static JavaRDD<Vector> getVectorRDD(JavaSparkContext jsc){
		JavaRDD<String> file = jsc.textFile(BaseStatistics.class.getClassLoader().getResource("vectors.txt").getPath(),2);
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
