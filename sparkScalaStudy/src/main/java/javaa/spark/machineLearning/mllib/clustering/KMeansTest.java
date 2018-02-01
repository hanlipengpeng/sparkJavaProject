package javaa.spark.machineLearning.mllib.clustering;

import javaa.spark.machineLearning.mllib.SVMsTest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

/**
 * KMeans
 * @author hanlipeng
 * 
 * spark中的聚类方法
 * K-means
 * Gaussian mixture  混合高斯模型
 * Power iteration clustering (PIC)   幂迭代聚类
 * Latent Dirichlet allocation (LDA)   隐含狄利克雷分布
 * Bisecting k-means    平分 k-means
 * Streaming k-means    流处理中增量 k-means
 * 
 *
 */
public class KMeansTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("LinnearRegression");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		String path = SVMsTest.class.getClassLoader().getResource("kmeans_data.txt").getPath();
		
		JavaRDD<String> data = jsc.textFile(path);
		JavaRDD<Vector> parsedData = data.map(new Function<String, Vector>() {

			@Override
			public Vector call(String s) throws Exception {
				String[] sarray = s.split(" ");
				double[] values = new double[sarray.length];
				for(int i=0;i<sarray.length;i++){
					values[i] = Double.parseDouble(sarray[i]);
				}
				return Vectors.dense(values);
			}
		}).cache();
		
		int numClusters = 2;
		int numIterations = 20;
		KMeansModel clusters = KMeans.train(parsedData.rdd(),numClusters,numIterations);
		
		clusters.k();
		
		double WSSSE = clusters.computeCost(parsedData.rdd());
	    System.out.println("Within Set Sum of Squared Errors = " + WSSSE);
	    
	    String savePath = "F:\\tmp\\model\\KMean";
	    
	    clusters.save(jsc.sc(), savePath);
	    KMeansModel sameModel = KMeansModel.load(jsc.sc(), savePath);
	}

}
