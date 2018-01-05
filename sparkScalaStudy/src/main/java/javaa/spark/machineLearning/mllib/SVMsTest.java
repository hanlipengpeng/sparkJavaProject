package javaa.spark.machineLearning.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.mllib.optimization.L1Updater;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;

import scala.Tuple2;

/**
 * SVM模型
 * 
 * @author rf
 *
 */
public class SVMsTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("svm").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		String path = SVMsTest.class.getClassLoader().getResource("sample_libsvm_data.txt").getPath();
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), path).toJavaRDD();
		
		//split data
		JavaRDD<LabeledPoint> training = data.sample(false, 0.6, 11L);
		training.cache();
		JavaRDD<LabeledPoint> test = data.subtract(training);
		
		int numIterations = 100;
		final SVMModel model = SVMWithSGD.train(training.rdd(), numIterations);
		
		//clear the default threshold
		model.clearThreshold();
		
		//Compute raw scores on the test set.
		JavaRDD<Tuple2<Object,Object>> scoreAndLables = test.map(new Function<LabeledPoint, Tuple2<Object,Object>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Object, Object> call(LabeledPoint v1)
					throws Exception {
				Double score = model.predict(v1.features());
				return new Tuple2<Object, Object>(score, v1.label());
			}
		});
		
		//get evaluation metrics  评估指标：错误/总数
		BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(scoreAndLables.rdd());
		double auROC = metrics.areaUnderROC();
		
		System.out.println("Area under ROC = "+ auROC);
		
		//save and load model
		String savePath = "F:\\tmp\\model\\svm";
		model.save(jsc.sc(), savePath);
		
		jsc.stop();
	}
	
	
	/**
	 * svm模型常用的调节参数
	 * regularization parameter 正则化参数要怎么设置，使用L1范式，和L2范式有什么区别
	 * 正则化参数是用来优化拟合的，防止过度拟合
	 */
	public static void svmAgrs(){
		SVMWithSGD svmAlg = new SVMWithSGD();
		svmAlg.optimizer()
			.setNumIterations(200)
			.setRegParam(0.1)
			.setUpdater(new L1Updater());
	}

}
