package javaa.spark.machineLearning.mllib;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;


/**
 * 决策树，可以进行分类，也可用作回归
 * @author 韩利鹏
 *
 */
public class DecisionTreeTest {
	public static void main(String[] args) {
		//decisionTreeClassification();
		regressionDicisionTree();
	}
	
	/**
	 * 分类
	 */
	public static void decisionTreeClassification(){
		SparkConf conf = new SparkConf().setAppName("svm").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		String path = SVMsTest.class.getClassLoader().getResource("sample_libsvm_data.txt").getPath();
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), path).toJavaRDD();
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{0.7,0.3});
		JavaRDD<LabeledPoint> trainingData = splits[0];
		JavaRDD<LabeledPoint> testData = splits[1];
		
		int numClasses = 2;
		Map<Integer,Integer> categoricalFraturesInfo = new HashMap<>();
		String imourity = "gini";
		int maxDepth = 5;
		int maxBins = 32;
		
		final DecisionTreeModel model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFraturesInfo, imourity, maxDepth, maxBins);
		JavaPairRDD<Double,Double> predictionAndLabel = testData.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Double, Double> call(LabeledPoint t) throws Exception {
				double predict = model.predict(t.features());
				return new Tuple2<Double, Double>(predict, t.label());
			}
		});
		
		Double testErr = predictionAndLabel.filter(new Function<Tuple2<Double,Double>, Boolean>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean call(Tuple2<Double, Double> v1) throws Exception {
				// TODO Auto-generated method stub
				return !v1._1.equals(v1._2);
			}
		}).count() * 1.0 /testData.count();
		
		System.out.println("test Error:"+testErr);
		System.out.println("Learned calssicfication tree model:\n" + model.toDebugString());
		
		//Save and load model 
		String savePath = "F:\\tmp\\model\\DecisionTreeClassification";
		model.save(jsc.sc(), savePath);
		//加载模型
		//DecisionTreeModel sameModel = DecisionTreeModel.load(jsc.sc(), savePath);
	}
	
	public static void regressionDicisionTree(){
		SparkConf conf = new SparkConf().setAppName("svm").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		String path = SVMsTest.class.getClassLoader().getResource("sample_libsvm_data.txt").getPath();
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), path).toJavaRDD();
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{0.7,0.3});
		JavaRDD<LabeledPoint> trainingData = splits[0];
		JavaRDD<LabeledPoint> testData = splits[1];
		
		//set parameters.
		Map<Integer,Integer> categoricalFeaturesInfo = new HashMap<>();
		String impurity = "variance";
		int maxDepth = 5;
		int maxBins = 32;
		
		final DecisionTreeModel model = DecisionTree.trainRegressor(trainingData, categoricalFeaturesInfo, impurity, maxDepth, maxBins);
		
		JavaPairRDD<Double,Double> predictAndLabel = testData.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Double, Double> call(LabeledPoint t) throws Exception {
				double predict = model.predict(t.features());
				return new Tuple2<Double, Double>(predict, t.label());
			}
		});
		
		double testMSE = predictAndLabel.map(new Function<Tuple2<Double,Double>, Double>() {

			@Override
			public Double call(Tuple2<Double, Double> v1) throws Exception {
				double diff = v1._1 - v1._2;
				return diff*diff;
			}
		}).reduce(new Function2<Double, Double, Double>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Double call(Double v1, Double v2) throws Exception {
				return v1+v2;
			}
		}) / testData.count();
		
		System.out.println("Test Mean Squared Error:"+testMSE);
		System.out.println("learned regresion tree model:\n" + model.toDebugString());
		
		//save and load model,the path also can change to hdfs
		String savePath = "F:\\tmp\\model\\regressionDicisionTree";
		model.save(jsc.sc(), savePath);
		DecisionTreeModel sameModel = DecisionTreeModel.load(jsc.sc(), savePath);
		
	}

}
