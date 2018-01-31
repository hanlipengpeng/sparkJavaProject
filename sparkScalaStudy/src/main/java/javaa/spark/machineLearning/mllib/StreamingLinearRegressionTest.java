package javaa.spark.machineLearning.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.StreamingLinearRegressionWithSGD;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;
/**
 * 流处理中使用机器学习，能够进行增量学习
 * @author hanlipeng
 *
 */
public class StreamingLinearRegressionTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("svm").setMaster("local");
		
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1l));
		JavaDStream<LabeledPoint> trainingData = jssc.textFileStream("/training/data/dir").map(new Function<String, LabeledPoint>() {
			private static final long serialVersionUID = 1L;
			@Override
			public LabeledPoint call(String v1) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		}).cache();
		
		JavaDStream<LabeledPoint> testData = jssc.textFileStream("/training/data/dir").map(new Function<String, LabeledPoint>() {
			private static final long serialVersionUID = 1L;
			@Override
			public LabeledPoint call(String v1) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		}).cache();
		
		int numFeatures = 3;
		StreamingLinearRegressionWithSGD model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.zeros(numFeatures));
		model.trainOn(trainingData);
		JavaPairDStream<Double,Vector> lableandvetor = testData.mapToPair(new PairFunction<LabeledPoint, Double, Vector>() {

			@Override
			public Tuple2<Double, Vector> call(LabeledPoint t) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2<Double, Vector>(t.label(), t.features());
			}
		});
		
		model.predictOnValues(lableandvetor).print();
		jssc.start();
		jssc.awaitTermination();
		
	}

}
