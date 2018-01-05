package javaa.spark.machineLearning.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

/**
 * Logistic regression
 * 逻辑斯蒂回归
 *
 */
public class LogisticRegressionTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("svm").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		String path = SVMsTest.class.getClassLoader().getResource("sample_libsvm_data.txt").getPath();
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), path).toJavaRDD();
		//split data
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{0.6,0.4},11L);
		JavaRDD<LabeledPoint> training = splits[0].cache();
		JavaRDD<LabeledPoint> test = splits[1];
		
		//Run training algorithm to build the model.
		final LogisticRegressionModel model = new LogisticRegressionWithLBFGS()
			.setNumClasses(10)
			.run(training.rdd());
		
		//Compute raw scores on the test set.
		JavaRDD<Tuple2<Object,Object>> predictionAndLabels = test.map(new Function<LabeledPoint, Tuple2<Object,Object>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Tuple2<Object,Object> call(LabeledPoint v1) throws Exception {
				Double prediction = model.predict(v1.features());
				return new Tuple2<Object,Object>(prediction, v1.label());
			}
		});
		
		//Get evaluation metrics
		MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
		double precision = metrics.precision();
		System.out.println("precision = " + precision);
		
		//dave and load model
		String savePath = "F:\\tmp\\model\\logisticRegression";
		model.save(jsc.sc(), savePath);
		LogisticRegressionModel sameModel = LogisticRegressionModel.load(jsc.sc(), savePath);
		
		
		
		jsc.close();
	}

}
