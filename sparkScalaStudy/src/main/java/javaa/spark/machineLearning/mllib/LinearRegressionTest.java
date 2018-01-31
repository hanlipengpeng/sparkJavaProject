package javaa.spark.machineLearning.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.LinearRegressionModel;
import org.apache.spark.mllib.regression.LinearRegressionWithSGD;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

/**
 * 线性回归
 * @author hanlipeng
 *
 */
public class LinearRegressionTest {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.setAppName("LinnearRegression");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		String path = SVMsTest.class.getClassLoader().getResource("sample_libsvm_data.txt").getPath();
		//加载全部数据
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), path).toJavaRDD();
		
		JavaRDD<LabeledPoint>[] split = data.randomSplit(new double[]{0.6,0.4});
		JavaRDD<LabeledPoint> training = split[0].cache();
		JavaRDD<LabeledPoint> testing = split[1].cache();
		
		//Building the model
		int numIterations = 100;
		double stepSize = 0.0000001;
		
		final LinearRegressionModel model = LinearRegressionWithSGD.train(training.rdd(), numIterations,stepSize);
		
		JavaRDD<Tuple2<Double,Double>> valuesAndPreds = testing.map(new Function<LabeledPoint, Tuple2<Double,Double>>() {

			@Override
			public Tuple2<Double, Double> call(LabeledPoint v1)
					throws Exception {
				double prediction = model.predict(v1.features());
				return new Tuple2<Double, Double>(prediction, v1.label());
			}
		});
		//差值的二次方  求平均数
		double MSE = new JavaDoubleRDD(valuesAndPreds.map(new Function<Tuple2<Double,Double>, Object>() {

			@Override
			public Object call(Tuple2<Double, Double> v1) throws Exception {
				// Math.pow(a,3) 对a开三次方
				return Math.pow(v1._1 - v1._2, 2.0);
			}
		}).rdd()).mean();
		
		System.out.println("training Mean Squared Error = "+ MSE);
		String savePath = "F:\\tmp\\model\\LinearRegression";
		model.save(jsc.sc(), savePath);
	    LinearRegressionModel sameModel = LinearRegressionModel.load(jsc.sc(), savePath);
	    jsc.close();
	}
}
