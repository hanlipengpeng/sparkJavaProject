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
 * @author 韩利鹏
 * 
 * 决策树，可以进行分类，也可用作回归
 * 
 * impurity 都有哪些参数可以专递
 * gini 在分类中使用
 * entropy  在分类中使用
 * variance   在回归模型中使用
 * 
 * maxBins 分裂候选集  默认是32 ，没明白这个是干什么用的
 * 
 * maxDepth 节点最大深度
 * 
 * categoricalFraturesInfo 指明哪些特征是类别型的以及每个类别型特征对应值（类别）的数量。
 * 
 * 经常需要调节的参数
 * algo: Classification 或者 Regression
 * numClasses: 分类的类型数量Number of classes (只用于Classification)
 * categoricalFeaturesInfo: 指明哪些特征是类别型的以及每个类别型特征对应值（类别）的数量。通过map来指定，map的key是特征索引，value是特征值数量。不在这个map中的特征默认是连续型的。
 *     例如：Map(0 -> 2, 4->10)表示特征0有两个特征值(0和1)，特征4有10个特征值{0,1,2,3,…,9}。注意特征索引是从0开始的，0和4表示第1和第5个特征。
 *     注意可以不指定参数categoricalFeaturesInfo。算法这个时候仍然会正常执行。但是类别型特征显示说明的话应该会训练出更好的模型。
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
